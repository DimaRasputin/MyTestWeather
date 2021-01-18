package net.extracode.selenium.ezwim.common.login;

import net.extracode.selenium.common.*;
import net.extracode.selenium.core.Action;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.openqa.selenium.By;

public class MyLoginToEGV implements Action {

    private String url;
    private String userName;
    private String userPassword;

    public MyLoginToEGV(String url, String userName, String userPassword) {
        this.url = url;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    @Override
    public void act(DriverWrapper driver, Context context, Reporter reporter) {

        new Navigate(context.evaluateString(url)).act(driver, context, reporter);
        new WaitFor(By.id("pageCompletelyLoaded"), "90").act(driver, context, reporter);
        new ClearAndType(By.id("P101_USERNAME"), context.evaluateString(userName)).act(driver, context, reporter);
        new WaitFor(By.id("pageCompletelyLoaded"), "90").act(driver, context, reporter);
        new ClearAndType(By.id("P101_PASSWORD"), context.evaluateString(userPassword)).act(driver, context, reporter);
        new WaitFor(By.id("pageCompletelyLoaded"), "90").act(driver, context, reporter);
        new Click(By.id("login_button")).act(driver, context, reporter);
        new WaitFor(By.id("pageCompletelyLoaded"), "90").act(driver, context, reporter);
    }
}
