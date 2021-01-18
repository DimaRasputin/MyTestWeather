package net.extracode.selenium.ezwim.util.navigator.novest;

import net.extracode.selenium.common.ClearAndType;
import net.extracode.selenium.common.Click;
import net.extracode.selenium.common.WaitFor;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.util.ByHandler;
import net.extracode.selenium.ezwim.util.Const;
import net.extracode.selenium.ezwim.util.navigator.Navigable;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Register implements Navigable {

    private static final Logger logger = LogManager.getLogger(Register.class.getSimpleName());



    public Register() {

    }


    @Override
    public void navigate(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("navigate");
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new Click(ByHandler.getBy(context, "novest.solveTheProblem.register"))
                .act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
    }

    public Register fillName(String Name, DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("fill Name");
        logger.debug("text: " + Name);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new ClearAndType(ByHandler.getBy(context, "novest.solveTheProblem.name"),
                context.evaluateString(Name)).act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }

    public Register fillEMailAddress(String Mail, DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("fill E-Mail Address");
        logger.debug("text: " + Mail);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new ClearAndType(ByHandler.getBy(context, "novest.solveTheProblem.mail"),
                context.evaluateString(Mail)).act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }

    public Register fillPassword(String Password, DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("fill Password");
        logger.debug("text: " + Password);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new ClearAndType(ByHandler.getBy(context, "novest.solveTheProblem.password"),
                context.evaluateString(Password)).act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }

    public Register fillConfirmPassword(String Password, DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("fill Confirm Password");
        logger.debug("text: " + Password);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new ClearAndType(ByHandler.getBy(context, "novest.solveTheProblem.confirmPassword"),
                context.evaluateString(Password)).act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }





    public Register clickRegister (DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("click Register ");
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new Click(ByHandler.getBy(context, "novest.testNovest.register")).act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }


}