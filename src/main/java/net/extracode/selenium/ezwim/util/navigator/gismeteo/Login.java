package net.extracode.selenium.ezwim.util.navigator.gismeteo;

import net.extracode.selenium.common.*;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.util.ContextExpression;
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
        new Navigate(ContextExpression.wrap("gismeteo.url")).act(driverWrapper, context, reporter);

    }


    public Login navigateGismeteo(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("navigate");
        new Navigate(ContextExpression.wrap("gismeteo.url")).act(driverWrapper, context, reporter);
        return this;
    }

}
