package net.extracode.selenium.ezwim.util.testers;


import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.common.SaveArtifactsOnError;
import net.extracode.selenium.ezwim.util.TestConfiguration;
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
     * @author o.pushkov
     * @since september 2020
     * To automate ticket(s): EGV BR-02247 NameOfTicket<br>
     * Time: 0 m 0 s
     * AIM: AIM_details<br>
     * Expected: name_of_the_elements_to_be_checked<br>
     * <p>
     * PREPARE FOR TEST:<br>
     * Steps_for_manual_reproduce_static_data
     * AUTOTEST Steps:<br>
     * <p>
     * before()<br>
     * <p>
     * test()<br>
     * <p>
     * after()<br>
     * <p>
     */
    @Test
    public void test() throws Exception {
        try {

            logger.info("Created by o.pushkov. september 2019.");

            step1(); //logical steps

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
