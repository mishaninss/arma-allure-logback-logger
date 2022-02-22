package com.github.mishaninss.arma.reporting;

import com.github.mishaninss.arma.data.DataObject;
import io.qameta.allure.Allure;
import io.qameta.allure.util.PropertiesUtils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AllureSlf4jReporter implements IReporter {

  private Logger logger = LoggerFactory.getLogger(AllureSlf4jReporter.class);

  public static void attachScreenDiff(File expected, File actual, File diff) throws IOException {
    Map<String, String> data = new HashMap<>();
    if (expected.exists()) {
      data.put("expected", "data:image/png;base64," +
          new String(Base64.getEncoder().encode(FileUtils.readFileToByteArray(expected))));
    }
    if (actual.exists()) {
      data.put("actual", "data:image/png;base64," +
          new String(Base64.getEncoder().encode(FileUtils.readFileToByteArray(actual))));
    }
    if (diff.exists()) {
      data.put("diff", "data:image/png;base64," +
          new String(Base64.getEncoder().encode(FileUtils.readFileToByteArray(diff))));
    }
    Allure.addAttachment("screen diff", "application/vnd.allure.image.diff",
        DataObject.GSON.toJson(data));
  }

  @Override
  public void setReporterName(String reporterName) {
    logger = LoggerFactory.getLogger(reporterName);
  }

  @Override
  public void attachScreenshot(byte[] screenshot, String msg) {
    if (screenshot.length > 0) {
      Allure.addByteAttachmentAsync(msg, "image/png", () -> screenshot);
    }
  }

  @Override
  public void attachScreenshot(byte[] screenshot) {
    attachScreenshot(screenshot, "Screenshot");
  }

  @Override
  public void attachFile(String pathToFile, String msg) {
    Allure.addByteAttachmentAsync(msg, "", () ->
    {
      try {
        return FileUtils.readFileToByteArray(new File(pathToFile));
      } catch (IOException e) {
        ignoredException(e);
        return new byte[0];
      }
    });
  }

  @Override
  public void attachText(String text, String msg) {
    Allure.addAttachment(msg, "text/html", text);
  }

  @Override
  public void trace(String msg) {
    logger.trace(msg);
    if (logger.isTraceEnabled()) {
      allureStep(msg);
    }
  }

  @Override
  public void trace(String msg, Object... args) {
    if (logger.isTraceEnabled()) {
      String message = getMassage(msg, args);
      logger.trace(message);
      allureStep(message);
    }
  }

  @Override
  public void trace(String msg, Throwable e) {
    logger.trace(msg, e);
    if (logger.isTraceEnabled()) {
      allureStep(msg + " " + e.getMessage());
    }
  }

  @Override
  public void debug(String msg) {
    logger.debug(msg);
    if (logger.isDebugEnabled()) {
      allureStep(msg);
    }
  }

  @Override
  public void debug(String msg, Object... args) {
    if (logger.isDebugEnabled()) {
      String message = getMassage(msg, args);
      logger.debug(message);
      allureStep(message);
    }
  }

  @Override
  public void debug(String msg, Throwable e) {
    logger.debug(msg, e);
    if (logger.isDebugEnabled()) {
      allureStep(msg + " " + e.getMessage());
    }
  }

  @Override
  public void info(String msg) {
    logger.info(msg);
    if (logger.isInfoEnabled()) {
      allureStep(msg);
    }
  }

  @Override
  public void info(String msg, Object... args) {
    if (logger.isInfoEnabled()) {
      String message = getMassage(msg, args);
      logger.info(message);
      allureStep(message);
    }
  }

  @Override
  public void info(String msg, Throwable e) {
    logger.info(msg, e);
    if (logger.isInfoEnabled()) {
      allureStep(msg + " " + e.getMessage());
    }
  }

  @Override
  public void warn(String msg) {
    logger.warn(msg);
    if (logger.isWarnEnabled()) {
      allureStep(msg);
    }
  }

  @Override
  public void warn(String msg, Object... args) {
    if (logger.isWarnEnabled()) {
      String message = getMassage(msg, args);
      logger.warn(message);
      allureStep(message);
    }
  }

  @Override
  public void warn(String msg, Throwable e) {
    logger.warn(msg, e);
    if (logger.isWarnEnabled()) {
      allureStep(msg + " " + e.getMessage());
    }
  }

  @Override
  public void error(String msg) {
    logger.error(msg);
    if (logger.isErrorEnabled()) {
      allureStep(msg);
    }
  }

  @Override
  public void error(String msg, Object... args) {
    if (logger.isErrorEnabled()) {
      String message = getMassage(msg, args);
      logger.error(message);
      allureStep(message);
    }
  }

  @Override
  public void error(String msg, Throwable e) {
    logger.error(msg, e);
    if (logger.isErrorEnabled()) {
      allureStep(msg + " " + e.getMessage());
    }
  }

  @Override
  public void ignoredException(Exception ex) {
    trace("Ignored exception", ex);
  }

  private void allureStep(String msg) {
    Allure.step(msg);
  }

  private String getMassage(String format, Object... args) {
    return String.format(format, args);
  }

  public void writeEnvironmentProperties(Map<String, Object> envPropsMap) {
    try {
      final Properties properties = PropertiesUtils.loadAllureProperties();
      final String path = properties.getProperty("allure.results.directory", "allure-results");
      Properties envProps = new Properties();
      envPropsMap.forEach((key, value) -> envProps
          .put(StringUtils.normalizeSpace(key).replace(" ", "."), value.toString()));
      envProps.store(new FileWriter(new File(path, "environment.properties")),
          "Allure Environment Properties");
    } catch (Exception ex) {
      warn("Не смогли записать Allure Environment Properties", ex);
    }
  }
}
