package net.extracode.selenium.ezwim.common;

import net.extracode.selenium.common.*;
import net.extracode.selenium.core.Action;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.util.ByHandler;
import net.extracode.selenium.ezwim.util.ConditionAct;
import net.extracode.selenium.ezwim.util.GetAct;
import net.extracode.selenium.ezwim.util.SshServer;
import net.extracode.selenium.ezwim.util.remote.RemoteServerAct;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveArtifactsOnError implements Action {

    private static final Logger logger = LogManager.getLogger(SaveArtifactsOnError.class.getSimpleName());

    private String testName;

    public SaveArtifactsOnError(String testName) {
        this.testName = testName;
    }

    @Override
    public void act(DriverWrapper driver, Context context, Reporter reporter) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy-HH_mm_ss");
        String nowDateTime = sdf.format(new Date());
        String fileNamesPrefix = "./log/" + testName + "_" + nowDateTime;
        try {
            new Screenshot(fileNamesPrefix + "_screenshotOnFail.png")
                    .act(driver, context, reporter);
        } catch (Exception e) {
            logger.error("can't make screenshot", e);
        }
        try {
            new Snapshot(fileNamesPrefix + "_snapshotOnFail.html")
                    .act(driver, context, reporter);
        } catch (Exception e) {
            logger.error("can't make snapshot", e);
        }
        new GetCurrentUrl(this.getClass().getSimpleName() + ".currentUrl").act(driver, context, reporter);
        String currentUrl = (String) context.getVars().get(this.getClass().getSimpleName() + ".currentUrl");
        logger.info("current url: " + currentUrl);


        /////////////////// temporary solution for apache issue
        try {
            if (new RemoteServerAct().getServerName().equals("staging")) {
                String output = "";
                int num = 0;
                Boolean restartFlag = true;
                String pathToPrivateKey = this.getClass().getResource("/id_rsa_autoload_staging").toExternalForm();
                String hostName = null;
                try {
                    hostName = new RemoteServerAct().getHostName();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
//                    output = new HttpUtil().sendGet("http://pp-stagdup.eztest.nu", "text/xml");
                    driver.navigate().to("https://pp-stagdup.eztest.nu");
                    new WaitFor(By.id("P101_USERNAME"), "30").act(driver, context, reporter);
                    num = GetAct.getCount(By.id("P101_USERNAME"), driver, context, reporter);
                } catch (Exception e) {
                    driver.navigate().to("https://pp-stagdup.eztest.nu");
                    new Pause("10000").act(driver, context, reporter);
                    num = GetAct.getCount(By.id("P101_USERNAME"), driver, context, reporter);
                    if (num == 0) {
                        logger.info("do restart of apache");
                        SshServer SshServ = new SshServer(hostName, "autoload", pathToPrivateKey);
                        SshServ.execute("sudo /etc/init.d/apache2 stop");
                        SshServ.execute("sudo /etc/init.d/apache2 start");
                        SshServ.execute("exit");
                        restartFlag = false;
                    }
                }
//                if (!output.contains("P101_USERNAME") && restartFlag) {
                if (num == 0 && restartFlag) {
                    driver.navigate().to("https://pp-stagdup.eztest.nu");
                    new Pause("10000").act(driver, context, reporter);
                    num = GetAct.getCount(By.id("P101_USERNAME"), driver, context, reporter);
                    if (num == 0) {
                        logger.info("do restart of apache 2");
                        SshServer SshServ = new SshServer(hostName, "autoload", pathToPrivateKey);
                        SshServ.execute("sudo /etc/init.d/apache2 stop");
                        SshServ.execute("sudo /etc/init.d/apache2 start");
                        SshServ.execute("exit");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ///////////////////


        By handleErrorDebugInfoBy = ByHandler.getBy(context, "common.handleErrorDebugInfo");

        new Pause("3000").act(driver, context, reporter);
        try {
            new Snapshot(fileNamesPrefix + "_snapshotOnFailForHandleError.html")
                    .act(driver, context, reporter);
        } catch (Exception e) {
            logger.error("can't make snapshot for handle error", e);
        }
        if (ConditionAct.elementExist(handleErrorDebugInfoBy, driver, context, reporter)) {
            // text from hidden element can't be gotten
            new Script("$('#handleErrorLog').show()").act(driver, context, reporter);
            String handleErrorDebugInfoText = GetAct.getElementText(handleErrorDebugInfoBy, driver, context, reporter);
            try {
                IOUtils.write(handleErrorDebugInfoText, new FileOutputStream(fileNamesPrefix + "_handleErrorInfo" +
                        ".txt"), "UTF-8");
            } catch (IOException e) {
                logger.error("can't save handleError info to file: " + fileNamesPrefix + "_handleErrorInfo.txt");
                logger.error("handleError text: \n" + handleErrorDebugInfoText);
            }
        } else {
            logger.warn("handleError debug info element " + handleErrorDebugInfoBy + " doesn't exist");
        }

        try {
            IOUtils.write(currentUrl, new FileOutputStream(fileNamesPrefix + "_urlOnFail.txt"), "UTF-8");
        } catch (IOException e) {
            logger.error("can't save url: " + currentUrl + " to file: " + fileNamesPrefix + "_urlOnFail.txt");
        }
    }

}
