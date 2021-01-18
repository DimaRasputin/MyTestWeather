package net.extracode.selenium.ezwim.common.login;

import net.extracode.selenium.common.*;
import net.extracode.selenium.core.Action;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.util.ByHandler;
import net.extracode.selenium.ezwim.util.Const;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginToEEM implements Action {

    private static final Logger logger = LogManager.getLogger(LoginToEEM.class.getSimpleName());

    private String url;
    private String usrlogin;
    private String usrpass;

    public LoginToEEM(String url, String usrLogin, String usrpass) {
        this.url = url;
        this.usrlogin = usrLogin;
        this.usrpass = usrpass;
    }

    @Override
    public void act(DriverWrapper driver, Context context, Reporter reporter) {

        logger.info("Go by url :" + url);
        new Navigate(url)
                .act(driver, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"), "90")
                .act(driver, context, reporter);
        logger.info("enter credentials");
        new ClearAndType(ByHandler.getBy(context, "eem.login.usernameField"), usrlogin)
                .act(driver, context, reporter);
        new ClearAndType(ByHandler.getBy(context, "eem.login.passwordField"), usrpass)
                .act(driver, context, reporter);
        logger.info("Click login");
        new Click(ByHandler.getBy(context, "eem.login.loginButton"))
                .act(driver, context, reporter);
        new WaitFor(ByHandler.getBy(context, "eem.logoutButton"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);
    }
}