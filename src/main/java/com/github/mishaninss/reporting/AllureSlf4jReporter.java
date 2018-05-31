/*
 * Copyright 2018 Sergey Mishanin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mishaninss.reporting;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class AllureSlf4jReporter implements IReporter{
    private Logger logger = LoggerFactory.getLogger(AllureSlf4jReporter.class);

    @Override
    public void setReporterName(String reporterName) {
        logger = LoggerFactory.getLogger(reporterName);
    }

    @Override
    public void attachScreenshot(byte[] screenshot, String msg) {
        allureAttachment(screenshot, msg, "image/png");
    }

    @Attachment(value = "{msg}", type = "{type}")
    private byte[] allureAttachment(byte[] attachment, String msg, String type){
        return attachment;
    }

    @Override
    public void attachScreenshot(byte[] screenshot) {
        attachScreenshot(screenshot, "Screenshot");
    }

    @Override
    public void attachFile(String pathToFile, String msg) {
        try {
            byte[] attachment = FileUtils.readFileToByteArray(new File(pathToFile));
            allureAttachment(attachment, msg, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void attachText(String text, String msg) {
        allureAttachment(text.getBytes(), msg, "text/html");
    }

    @Override
    public void trace(String msg) {
        logger.trace(msg);
        if (logger.isTraceEnabled()){
            allureStep(msg);
        }
    }

    @Override
    public void trace(String msg, Object... args) {
        logger.trace(msg, args);
        if (logger.isTraceEnabled()){
            allureStep(msg);
        }
    }

    @Override
    public void trace(String msg, Throwable e) {
        logger.trace(msg, e);
        if (logger.isTraceEnabled()){
            allureStep(msg + " " + e.getMessage());
        }
    }

    @Override
    public void debug(String msg) {
        logger.debug(msg);
        if (logger.isDebugEnabled()){
            allureStep(msg);
        }
    }

    @Override
    public void debug(String msg, Object... args) {
        logger.debug(msg, args);
        if (logger.isDebugEnabled()){
            allureStep(msg);
        }
    }

    @Override
    public void debug(String msg, Throwable e) {
        logger.debug(msg, e);
        if (logger.isDebugEnabled()){
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
        logger.info(msg, args);
        if (logger.isInfoEnabled()) {
            allureStep(msg);
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
        logger.warn(msg, args);
        if (logger.isWarnEnabled()) {
            allureStep(msg);
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
        logger.error(msg, args);
        if (logger.isErrorEnabled()) {
            allureStep(msg);
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

    @Step(value = "{msg}")
    private void allureStep(String msg){

    }
}
