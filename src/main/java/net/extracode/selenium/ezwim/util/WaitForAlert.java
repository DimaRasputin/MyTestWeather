package net.extracode.selenium.ezwim.util;

import net.extracode.selenium.core.Action;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;

public class WaitForAlert implements Action {

    private static final Logger logger = LogManager.getLogger(WaitForAlert.class.getSimpleName());

    private String timeoutMilliseconds;

    public WaitForAlert(String timeoutMilliseconds) {
        this.timeoutMilliseconds = timeoutMilliseconds;
    }

    @Override
    public void act(DriverWrapper driver, Context context, Reporter reporter) {
        int i=0;
        while(i++<((Integer.parseInt(this.timeoutMilliseconds))/1000)) {
            try {
                Alert alert = driver.switchTo().alert();
                break;
            } catch(NoAlertPresentException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                continue;
            }
        }
    }
}
