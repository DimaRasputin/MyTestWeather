package net.extracode.selenium.ezwim.egv.reports;

import net.extracode.selenium.common.Click;
import net.extracode.selenium.common.GetAttribute;
import net.extracode.selenium.common.WaitFor;
import net.extracode.selenium.core.Action;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.util.ByHandler;
import net.extracode.selenium.ezwim.util.Const;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetLastDownloadId implements Action {

    public static final String LAST_DOWNLOAD_ID = "GetLastDownloadId.lastDownloadRowId";

    private static final Logger logger = LogManager.getLogger(GetLastDownloadId.class.getSimpleName());

    @Override
    public void act(DriverWrapper driver, Context context, Reporter reporter) {

        logger.info("go to download");
        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"), Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);
        new Click(ByHandler.getBy(context, "egv.reports.download")).act(driver, context, reporter);

        logger.info("getting id attribute from first row in table");
        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"), Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);
        new GetAttribute(ByHandler.getBy(context, "egv.reports.download.lastDownloadRow"),
                LAST_DOWNLOAD_ID, "id").act(driver, context, reporter);

        logger.info("download id: " + context.getVars().get(LAST_DOWNLOAD_ID));

    }
}
