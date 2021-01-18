package net.extracode.selenium.ezwim.egv.reports;

import net.extracode.selenium.common.Click;
import net.extracode.selenium.common.MoveTo;
import net.extracode.selenium.common.WaitFor;
import net.extracode.selenium.core.Action;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.util.ByHandler;
import net.extracode.selenium.ezwim.util.Const;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OpenReportsList implements Action {

    private static final Logger logger = LogManager.getLogger(OpenReportsList.class.getSimpleName());

    @Override
    public void act(DriverWrapper driver, Context context, Reporter reporter) {

        logger.info("go to reports");
        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"), Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);
        new Click(ByHandler.getBy(context, "egv.reports")).act(driver, context, reporter);

        logger.info("move mouse to 'standard' menu item");
        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"), Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);
        new MoveTo(ByHandler.getBy(context, "egv.reports.standard")).act(driver, context, reporter);

    }
}
