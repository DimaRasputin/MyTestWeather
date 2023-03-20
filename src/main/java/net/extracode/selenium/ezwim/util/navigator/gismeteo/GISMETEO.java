package net.extracode.selenium.ezwim.util.navigator.gismeteo;

import net.extracode.selenium.common.*;
import net.extracode.selenium.core.Action;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.util.ByHandler;
import net.extracode.selenium.ezwim.util.Const;
import net.extracode.selenium.ezwim.util.navigator.Navigable;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GISMETEO implements Navigable {

    private static final Logger logger = LogManager.getLogger(GISMETEO.class.getSimpleName());

    private Action navigateAction;
    private Login login;



    public GISMETEO() {
        login = new Login();

    }

    @Override
    public void navigate(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("navigate");
        navigateAction.act(driverWrapper, context, reporter);
    }

    public GISMETEO getURL() {
        return this;
    }

    public GISMETEO getURL(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        login.navigate(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }

}
