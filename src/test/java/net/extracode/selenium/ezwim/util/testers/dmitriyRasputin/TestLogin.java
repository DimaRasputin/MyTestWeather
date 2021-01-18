package net.extracode.selenium.ezwim.util.testers.dmitriyRasputin;


import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.common.SaveArtifactsOnError;
import net.extracode.selenium.ezwim.util.TestConfiguration;
import net.extracode.selenium.ezwim.util.navigator.Navigator;
import net.extracode.selenium.ezwim.util.remote.RemoteServerAct;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class TestLogin {

    private static final Logger logger = LogManager.getLogger(TestLogin.class.getSimpleName());

    private DriverWrapper driver;
    private Context context;
    private Reporter reporter;
    private String dbNumber;
    private String dbStaging = "";
    private String dbDemo = "";
    private String selenium = "only_5_numbers_of_selenium_ticket";

    @Before
    public void before() throws IOException {

        String configParam;
        if (System.getProperties().containsKey("test.config")) {
            configParam = System.getProperty("test.config");
        } else {
            configParam = "config_local.properties";
        }
        TestConfiguration testConfiguration = TestConfiguration.getFromResourcesConfig(configParam);
        driver = testConfiguration.getDriverWrapper();
        context = testConfiguration.getContext();
        reporter = testConfiguration.getReporter();
        String server = new RemoteServerAct().getServerName();
        if (server.equals("demo")) {
            dbNumber = dbDemo;
        }
        if (server.equals("staging") || server.equals("staging2")) {
            dbNumber = dbStaging;
        }
    }

    /**
     * @author Rasputin D<br>
     * @since january 2021
     * To automate ticket(s): Novestio-00008 	<br>
     * Expected: description of the new site<br>
     * <p>
     * PREPARE FOR TEST:<br>
     * AUTOTEST Steps:<br>
     *  Go to URL -  novest.io<br>
     *  changing the language to Russian<br>
     *  go to Solve The Problem:<br>
     *  we perform user registration<br>
     *  Result<br>
     *      the user must be created (now there is no automatic deletion of the user) <br>
     *      (and there are no rights for this action at all)<br>
     */
    @Test
    public void test() throws Exception {
        try {
            String name = "novest_RDN";
            String email = "patrick+Novestio-00008@ezwim-mail.com";
            String password = "eHxu^2z)111";

            logger.info("Created by Rasputin D 01.2021.");

            logger.info("Go to Novest.io");
            Navigator.getInstance().getNovest()
                    .getURL(driver, context, reporter);
            //new Pause("4000").act(driver, context, reporter);

            logger.info("changing the language to Russian");
            Navigator.getInstance().getNovest()
            .getLanguageChange("ru",driver,context,reporter);

            logger.info("go to Solve The Problem");
            Navigator.getInstance().getNovest()
                    .clickTestNovest(driver,context,reporter)
                    .clickRegister(driver,context,reporter)
                    .fillName(name,driver,context,reporter)
                    .fillEMailAddress(email,driver,context,reporter)
                    .fillPassword(password,driver,context,reporter)
                    .fillConfirmPassword(password,driver,context,reporter)
                    .clickRegister(driver,context,reporter);


            logger.info("Test passed");

            //PRESS CTRL+ALT+L for reformat source code

        } catch (Exception e) {
            new SaveArtifactsOnError(this.getClass().getSimpleName()).act(driver, context, reporter);
            throw e;
        }
    }

    @After
    public void after() {

        driver.quit();
    }

    private void step1() throws IOException {

    }


}
