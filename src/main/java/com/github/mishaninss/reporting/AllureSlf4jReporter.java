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
    private static final Logger LOGGER = LoggerFactory.getLogger(AllureSlf4jReporter.class);

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
        allureAttachment(text.getBytes(), msg, "");
    }

    @Override
    public void trace(String msg) {
        LOGGER.trace(msg);
        if (LOGGER.isTraceEnabled()){
            allureStep(msg);
        }
    }

    @Override
    public void trace(String msg, Object... args) {
        LOGGER.trace(msg, args);
        if (LOGGER.isTraceEnabled()){
            allureStep(msg);
        }
    }

    @Override
    public void trace(String msg, Throwable e) {
        LOGGER.trace(msg, e);
        if (LOGGER.isTraceEnabled()){
            allureStep(msg);
        }
    }

    @Override
    public void debug(String msg) {
        LOGGER.debug(msg);
        if (LOGGER.isDebugEnabled()){
            allureStep(msg);
        }
    }

    @Override
    public void debug(String msg, Object... args) {
        LOGGER.debug(msg, args);
        if (LOGGER.isDebugEnabled()){
            allureStep(msg);
        }
    }

    @Override
    public void debug(String msg, Throwable e) {
        LOGGER.debug(msg, e);
        if (LOGGER.isDebugEnabled()){
            allureStep(msg);
        }
    }

    @Override
    public void info(String msg) {
        LOGGER.info(msg);
        if (LOGGER.isInfoEnabled()) {
            allureStep(msg);
        }
    }

    @Override
    public void info(String msg, Object... args) {
        LOGGER.info(msg, args);
        if (LOGGER.isInfoEnabled()) {
            allureStep(msg);
        }
    }

    @Override
    public void info(String msg, Throwable e) {
        LOGGER.info(msg, e);
        if (LOGGER.isInfoEnabled()) {
            allureStep(msg);
        }
    }

    @Override
    public void warn(String msg) {
        LOGGER.warn(msg);
        if (LOGGER.isWarnEnabled()) {
            allureStep(msg);
        }
    }

    @Override
    public void warn(String msg, Object... args) {
        LOGGER.warn(msg, args);
        if (LOGGER.isWarnEnabled()) {
            allureStep(msg);
        }
    }

    @Override
    public void warn(String msg, Throwable e) {
        LOGGER.warn(msg, e);
        if (LOGGER.isWarnEnabled()) {
            allureStep(msg);
        }
    }

    @Override
    public void error(String msg) {
        LOGGER.error(msg);
        if (LOGGER.isErrorEnabled()) {
            allureStep(msg);
        }
    }

    @Override
    public void error(String msg, Object... args) {
        LOGGER.error(msg, args);
        if (LOGGER.isErrorEnabled()) {
            allureStep(msg);
        }
    }

    @Override
    public void error(String msg, Throwable e) {
        LOGGER.error(msg, e);
        if (LOGGER.isErrorEnabled()) {
            allureStep(msg);
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
