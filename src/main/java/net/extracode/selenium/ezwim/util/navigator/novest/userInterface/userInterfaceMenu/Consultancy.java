package net.extracode.selenium.ezwim.util.navigator.novest.userInterface.userInterfaceMenu;

import net.extracode.selenium.common.*;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.util.ByHandler;
import net.extracode.selenium.ezwim.util.Const;
import net.extracode.selenium.ezwim.util.navigator.Navigable;
import net.extracode.selenium.ezwim.util.navigator.novest.Register;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Consultancy implements Navigable {

    private static final Logger logger = LogManager.getLogger(Consultancy.class.getSimpleName());

    private Register register;


    public Consultancy() {
        register = new Register();
    }


    @Override
    public void navigate(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("navigate");
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new Click(ByHandler.getBy(context, "novest.testNovest.userInterface.consultancy"))
                .act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "novest.testNovest.userInterface.consultancy.download"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
    }

    public Consultancy fillBudget(String Budget,DriverWrapper driverWrapper, Context context, Reporter reporter) {
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

    public Consultancy selectNumberAssetsText(String value, DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("select Number of objects by text");
        logger.debug(this.getClass().getSimpleName() + " role text: " + value);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new SelectByValue(ByHandler.getBy(context, "novest.testNovest.userInterface.capabilities.numberOfObjects"), value)
                .act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }


    public Consultancy fillExampleNumber(String Number,DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("click Example number ");
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new ClearAndType(ByHandler.getBy(context, "novest.testNovest.userInterface.consultancy.exampleNumber"),
                context.evaluateString(Number)).act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }

    public Consultancy clickBuildAnExample(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("click Build an example ");
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new Click(ByHandler.getBy(context, "novest.testNovest.userInterface.consultancy.buildAnExample")).act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "novest.testNovest.userInterface.consultancy.download"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }

    public Consultancy clickDownload(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("click download");
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new Click(ByHandler.getBy(context, "novest.testNovest.userInterface.consultancy.download"))
                .act(driverWrapper, context, reporter);
        new Pause("10000").act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }

}