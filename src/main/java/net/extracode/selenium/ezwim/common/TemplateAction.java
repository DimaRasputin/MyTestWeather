package net.extracode.selenium.ezwim.common;

import net.extracode.selenium.common.GetCurrentUrl;
import net.extracode.selenium.common.Screenshot;
import net.extracode.selenium.common.Snapshot;
import net.extracode.selenium.core.Action;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TemplateAction implements Action {

    private static final Logger logger = LogManager.getLogger(TemplateAction.class.getSimpleName());

    @Override
    public void act(DriverWrapper driver, Context context, Reporter reporter) {



    }

}
