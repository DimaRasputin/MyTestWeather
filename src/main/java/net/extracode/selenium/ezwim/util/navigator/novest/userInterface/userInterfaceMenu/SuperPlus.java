package net.extracode.selenium.ezwim.util.navigator.novest.userInterface.userInterfaceMenu;

import net.extracode.selenium.common.Click;
import net.extracode.selenium.common.WaitFor;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.util.ByHandler;
import net.extracode.selenium.ezwim.util.Const;
import net.extracode.selenium.ezwim.util.navigator.Navigable;
import net.extracode.selenium.ezwim.util.navigator.novest.Register;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SuperPlus implements Navigable {

    private static final Logger logger = LogManager.getLogger(SuperPlus.class.getSimpleName());

    private Register register;



    public SuperPlus() {
        register = new Register();
    }


    @Override
    public void navigate(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("navigate");
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new Click(ByHandler.getBy(context, "novest.testNovest.userInterface.superPlus"))
                .act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
    }

    public SuperPlus clickRequestACall (DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("click Request a call ");
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new Click(ByHandler.getBy(context, "novest.testNovest.userInterface.optimum.requestACall")).act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }

}