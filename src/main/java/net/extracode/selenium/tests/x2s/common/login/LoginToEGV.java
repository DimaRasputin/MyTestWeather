package net.extracode.selenium.tests.x2s.common.login;

import net.extracode.selenium.common.*;
import net.extracode.selenium.core.Action;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.util.ByHandler;
import net.extracode.selenium.ezwim.util.Const;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class LoginToEGV implements Action {

    private static final Logger logger = LogManager.getLogger(LoginToEGV.class.getSimpleName());

    private String egvUrl;
    private String loginName;
    private String loginPassword;

    public LoginToEGV(String egvUrl, String loginName, String loginPassword) {
        this.egvUrl = egvUrl;
        this.loginName = loginName;
        this.loginPassword = loginPassword;
    }

    @Override
    public void act(DriverWrapper driver, Context context, Reporter reporter) {
        logger.info("go to egv login page");
        new Navigate(context.evaluateString(egvUrl)).act(driver, context, reporter);

        logger.info("type username and password");
        new WaitFor(By.id("P101_USERNAME"), Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);
        new ClearAndType(By.id("P101_USERNAME"), context.evaluateString(loginName)).act(driver, context, reporter);
        new WaitFor(By.id("P101_PASSWORD"), Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);
        new ClearAndType(By.id("P101_PASSWORD"), context.evaluateString(loginPassword)).act(driver, context, reporter);

        logger.info("click login button");
        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"), Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);
        new Click(By.id("login_button")).act(driver, context, reporter);

        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"), Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);
        new IsExist(By.xpath("//td[contains(@onclick, 'LOGOUT')]")).act(driver, context, reporter);
        logger.info("check logout button is exist");
    }
}
