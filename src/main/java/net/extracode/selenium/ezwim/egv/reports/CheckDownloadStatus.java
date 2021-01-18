package net.extracode.selenium.ezwim.egv.reports;

import net.extracode.selenium.common.GetText;
import net.extracode.selenium.common.WaitFor;
import net.extracode.selenium.core.Action;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.exception.EzwimException;
import net.extracode.selenium.ezwim.util.ByHandler;
import net.extracode.selenium.ezwim.util.Const;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class CheckDownloadStatus implements Action {

    public static final String STATUS_TEXT = "CheckDownloadStatus.statusText";
    public static final String STATUS_VAR = "CheckDownloadStatus.status";

    private static final Logger logger = LogManager.getLogger(CheckDownloadStatus.class.getSimpleName());

    private String id;

    public CheckDownloadStatus(String id) {
        this.id = id;
    }

    @Override
    public void act(DriverWrapper driver, Context context, Reporter reporter) {

        logger.info("extract status text from download row");
        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"), Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);
        new GetText(By.xpath("//table[@id='table_reptable']//tr[@id='" + id + "']/td[5]"), STATUS_TEXT).act(driver, context, reporter);

        logger.info("compare status text with pattern");
        boolean errorExists = ((String) context.getVars().get(STATUS_TEXT)).matches("Error");
        if (errorExists) {
            throw new EzwimException("Report has error status");
        }
        boolean status = ((String) context.getVars().get(STATUS_TEXT)).matches("Ready");
        context.getVars().put(STATUS_VAR, status);
    }
}
