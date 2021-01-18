package net.extracode.selenium.ezwim.util.navigator.eem;

import net.extracode.selenium.common.*;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.util.ByHandler;
import net.extracode.selenium.ezwim.util.Const;
import net.extracode.selenium.ezwim.util.ContextExpression;
import net.extracode.selenium.ezwim.util.WaitForAlert;
import net.extracode.selenium.ezwim.util.navigator.Navigable;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Login implements Navigable {

    private static final Logger logger = LogManager.getLogger(Login.class.getSimpleName());

    public Login() {
    }

    @Override
    public void navigate(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("navigate");
        new Navigate(ContextExpression.wrap("eem.url")).act(driverWrapper, context, reporter);
        /*new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);*/
    }

    public Login fillUsernameField(String username, DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("fill username field");
        logger.debug("text: " + username);
        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new ClearAndType(ByHandler.getBy(context, "eem.login.usernameField"), username)
                .act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }

    public Login fillPasswordField(String password, DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("fill password field");
        logger.debug("text: " + password);
        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new ClearAndType(ByHandler.getBy(context, "eem.login.passwordField"), password)
                .act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }

    public Login clickLoginButton(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("click login button");
        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new Click(ByHandler.getBy(context, "eem.login.loginButton")).act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }

    public Login clickLostPasswordButton(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("click lost password button");
        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new Click(ByHandler.getBy(context, "eem.login.lostPasswordButton")).act(driverWrapper, context, reporter);
        new Click(ByHandler.getBy(context, "eem.login.lostPasswordAcceptButton")).act(driverWrapper, context, reporter);
        new WaitForAlert("20000").act(driverWrapper,context,reporter);
        new AcceptDialog().act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }


}
