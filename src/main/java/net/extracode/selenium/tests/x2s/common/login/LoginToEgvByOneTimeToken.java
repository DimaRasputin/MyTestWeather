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

public class LoginToEgvByOneTimeToken implements Action {

    private static final Logger logger = LogManager.getLogger(LoginToEgvByOneTimeToken.class.getSimpleName());

    private String egvUrl;
    private String loginName;
    private String oneTimeToken;
    private String newLoginPassword;

    public LoginToEgvByOneTimeToken(String egvUrl, String loginName, String oneTimeToken, String newLoginPassword) {
        this.egvUrl = egvUrl;
        this.loginName = loginName;
        this.oneTimeToken = oneTimeToken;
        this.newLoginPassword = newLoginPassword;
    }

    @Override
    public void act(DriverWrapper driver, Context context, Reporter reporter) {
        logger.info("go to egv login page");
        new Navigate(context.evaluateString(egvUrl)).act(driver, context, reporter);

        logger.info("type username and one time token password");
        new WaitFor(By.id("P101_USERNAME"), Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);
        new ClearAndType(By.id("P101_USERNAME"), context.evaluateString(loginName)).act(driver, context, reporter);
        new WaitFor(By.id("P101_PASSWORD"), Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);
        new ClearAndType(By.id("P101_PASSWORD"), context.evaluateString(oneTimeToken)).act(driver, context, reporter);

        logger.info("click login button");
        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"), Const.PAGE_COMPLETELY_LOADED_TIMEOUT)
                .act(driver, context, reporter);
        new Click(By.id("login_button")).act(driver, context, reporter);

        logger.info("type new password");
        new WaitFor(By.id("P101_NEW"), Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);
        new ClearAndType(By.id("P101_NEW"), context.evaluateString(newLoginPassword)).act(driver, context, reporter);
        new WaitFor(By.id("P101_CONFIRM"), Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);
        new ClearAndType(By.id("P101_CONFIRM"), context.evaluateString(newLoginPassword)).act(driver, context, reporter);

        logger.info("click login button");
        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"), Const.PAGE_COMPLETELY_LOADED_TIMEOUT)
                .act(driver, context, reporter);
        new Click(By.id("login_button")).act(driver, context, reporter);

        logger.info("check logout button is exist");
        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"), Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);
        new IsExist(By.xpath("//div[contains(@onclick, 'LOGOUT')]"));
    }
}
