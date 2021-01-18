package net.extracode.selenium.ezwim.egv.reports;

import net.extracode.selenium.common.Click;
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

public class WaitDownload implements Action {

    private static final Logger logger = LogManager.getLogger(WaitDownload.class.getSimpleName());

    private String id;
    private String maxWaitSeconds;
    private String iterationPause;

    public WaitDownload(String id, String maxWaitSeconds, String iterationPause) {
        this.id = id;
        this.maxWaitSeconds = maxWaitSeconds;
        this.iterationPause = iterationPause;
    }

    @Override
    public void act(DriverWrapper driver, Context context, Reporter reporter) {

        CheckDownloadStatus checkDownloadStatus = new CheckDownloadStatus(id);
        Integer maxWaitSeconds = Integer.valueOf(this.maxWaitSeconds);
        Integer iterationPause = Integer.valueOf(this.iterationPause);

        Boolean status = false;
        Integer sumWaitTime = 0;

        while (!status) {

            logger.info("reports isn't ready, waiting...");
            new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"), Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);

            checkDownloadStatus.act(driver, context, reporter);
            status = (Boolean) context.getVars().get(CheckDownloadStatus.STATUS_VAR);

            logger.info("summary waiting time in seconds: " + sumWaitTime);

            if (sumWaitTime >= maxWaitSeconds) {
                throw new EzwimException("report waiting is too long");
            }

            try {
                Thread.sleep(iterationPause * 1000);
            } catch (InterruptedException e) {
                throw new EzwimException("waiting is interrupted", e);
            }

            sumWaitTime += iterationPause;

            new Click(ByHandler.getBy(context, "egv.reports.download")).act(driver, context, reporter);
        }
    }
}
