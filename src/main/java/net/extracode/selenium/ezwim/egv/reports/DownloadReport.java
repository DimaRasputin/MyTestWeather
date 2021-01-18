package net.extracode.selenium.ezwim.egv.reports;

import net.extracode.selenium.common.Click;
import net.extracode.selenium.common.Pause;
import net.extracode.selenium.common.WaitFor;
import net.extracode.selenium.core.Action;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.util.ByHandler;
import net.extracode.selenium.ezwim.util.Const;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class DownloadReport implements Action {

    private static final Logger logger = LogManager.getLogger(DownloadReport.class.getSimpleName());

    private String id;

    public DownloadReport(String id) {
        this.id = id;
    }

    @Override
    public void act(DriverWrapper driver, Context context, Reporter reporter) {

        logger.info("go to downloads");
        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"), Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);
        new Click(ByHandler.getBy(context, "egv.reports.download")).act(driver, context, reporter);

        logger.info("click by row with id - " + context.evaluateString(id));
        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"), Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);
        new Click(By.xpath("//table[@id='table_reptable']//tr[@id='" + context.evaluateString(id) + "']")).act(driver, context, reporter);

        logger.info("waiting 10 seconds for downloading");
        new Pause("10000").act(driver, context, reporter);

    }
}
