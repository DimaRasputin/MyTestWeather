package net.extracode.selenium.ezwim.util.navigator.novest.userInterface.userInterfaceMenu;

import net.extracode.selenium.common.ClearAndType;
import net.extracode.selenium.common.Click;
import net.extracode.selenium.common.SelectByText;
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


public class Capabilities implements Navigable {

    private static final Logger logger = LogManager.getLogger(Capabilities.class.getSimpleName());

    private Register register;


    public Capabilities() {
        register = new Register();
    }


    @Override
    public void navigate(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("navigate");
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new Click(ByHandler.getBy(context, "novest.testNovest.userInterface.capabilities"))
                .act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
    }

    public Capabilities fillBudget(String Budget, DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("fill Budget");
        logger.debug("text: " + Budget);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new ClearAndType(ByHandler.getBy(context, "novest.testNovest.userInterface.capabilities.budget"),
                context.evaluateString(Budget)).act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }

    public Capabilities selectNumberOfObjectsText(String text, DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("select Number of objects by text");
        logger.debug(this.getClass().getSimpleName() + " role text: " + text);
        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new SelectByText(ByHandler.getBy(context, "novest.testNovest.userInterface.capabilities.numberOfObjects"),
                context.evaluateString(text)).act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }

    public Capabilities selectObjectTypesText(String text, DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("select Object types by text");
        logger.debug(this.getClass().getSimpleName() + " role text: " + text);
        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new SelectByText(ByHandler.getBy(context, "novest.testNovest.userInterface.capabilities.objectTypes"),
                context.evaluateString(text)).act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }

    public Capabilities clickSeed(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("click Seed ");
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new Click(ByHandler.getBy(context, "novest.testNovest.userInterface.capabilities.seed")).act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }

    public Capabilities clickCalculate(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("click Calculate ");
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new Click(ByHandler.getBy(context, "novest.testNovest.userInterface.capabilities.calculate")).act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }

}