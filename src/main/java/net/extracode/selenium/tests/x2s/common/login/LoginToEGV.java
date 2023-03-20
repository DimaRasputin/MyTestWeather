package net.extracode.selenium.tests.x2s.common.login;

import net.extracode.selenium.common.*;
import net.extracode.selenium.core.Action;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginToEGV implements Action {

    private static final Logger logger = LogManager.getLogger(LoginToEGV.class.getSimpleName());

    private String egvUrl;

    public LoginToEGV(String egvUrl) {
        this.egvUrl = egvUrl;

    }

    @Override
    public void act(DriverWrapper driver, Context context, Reporter reporter) {
        logger.info("go to page");
        new Navigate(context.evaluateString(egvUrl)).act(driver, context, reporter);

        logger.info("");
    }
}
