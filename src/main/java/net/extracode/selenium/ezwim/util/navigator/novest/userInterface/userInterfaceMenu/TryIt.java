package net.extracode.selenium.ezwim.util.navigator.novest.userInterface.userInterfaceMenu;

import net.extracode.selenium.common.ClearAndType;
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


public class TryIt implements Navigable {

    private static final Logger logger = LogManager.getLogger(TryIt.class.getSimpleName());

    private Register register;



    public TryIt() {
        register = new Register();
    }


    @Override
    public void navigate(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("navigate");
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new Click(ByHandler.getBy(context, "novest.testNovest.userInterface.tryIt"))
                .act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
    }

    public TryIt clickCalculate (DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("click Calculate ");
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new Click(ByHandler.getBy(context, "novest.testNovest.userInterface.tryIt.calculate")).act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }

    public TryIt fillBudget(String Budget, DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("fill Budget");
        logger.debug("text: " + Budget);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new ClearAndType(ByHandler.getBy(context, "novest.testNovest.userInterface.tryIt.budget"),
                context.evaluateString(Budget)).act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }

    public TryIt clickAddObject (DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("click AddObject ");
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new Click(ByHandler.getBy(context, "novest.testNovest.userInterface.tryIt.addObject")).act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }
}