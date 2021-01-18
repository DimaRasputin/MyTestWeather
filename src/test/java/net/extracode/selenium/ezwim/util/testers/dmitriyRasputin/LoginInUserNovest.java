package net.extracode.selenium.ezwim.util.testers.dmitriyRasputin;


import net.extracode.selenium.common.IsExist;
import net.extracode.selenium.common.WaitFor;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.common.SaveArtifactsOnError;
import net.extracode.selenium.ezwim.util.ByHandler;
import net.extracode.selenium.ezwim.util.Const;
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
import org.openqa.selenium.By;

import java.io.IOException;

public class LoginInUserNovest {

    private static final Logger logger = LogManager.getLogger(LoginInUserNovest.class.getSimpleName());

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
            String name = "novest_RDN";
            String email = "patrick+Novestio-00008@ezwim-mail.com";
            String password = "eHxu^2z)111";

            logger.info("Created by Rasputin D 01.2021.");

            logger.info("Go to Novest.io");
            Navigator.getInstance().getNovest()
                    .getURL(driver, context, reporter);

            logger.info("go to Test Novest");
            Navigator.getInstance().getNovest()
                    .clickTestNovest(driver,context,reporter)
                    .fillEMailAddress(email,driver,context,reporter)
                    .fillPassword(password,driver,context,reporter)
                    .clickLogin(driver,context,reporter);

            logger.info("changing the language to Russian");
            Navigator.getInstance().getNovest()
                    .getLanguageChange("ru",driver,context,reporter);

            logger.info("Go to page Try It");
            Navigator.getInstance().getNovest().getUserInterface()
                    .getTryIt(driver,context,reporter)
                    .fillBudget("1",driver,context,reporter);
            new IsExist(By.xpath("//*[@id='limit']")).act(driver,context,reporter);
            new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                    Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);

            logger.info("Go to page Optimum");
            Navigator.getInstance().getNovest().getUserInterface()
                    .getOptimum(driver,context,reporter);

            new IsExist(By.xpath("//*[@type='submit']")).act(driver,context,reporter);
            new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                    Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);

            logger.info("Go to page Enterprise");
            Navigator.getInstance().getNovest().getUserInterface()
                    .getEnterprise(driver,context,reporter);

            new IsExist(By.xpath("//*[@type='submit']")).act(driver,context,reporter);
            new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                    Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);

            logger.info("Go to page SuperPlus");
            Navigator.getInstance().getNovest().getUserInterface()
                    .getSuperPlus(driver,context,reporter);

            new IsExist(By.xpath("//*[@type='submit']")).act(driver,context,reporter);
            new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                    Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);

            logger.info("Go to page Gallery");
            Navigator.getInstance().getNovest().getUserInterface()
                    .getGallery(driver,context,reporter);

            new IsExist(By.xpath("//h2[contains (text(), 'Галерея')]")).act(driver,context,reporter);
            new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                    Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);

            logger.info("Go to page Profile");
            Navigator.getInstance().getNovest().getUserInterface()
                    .getProfile(driver,context,reporter);

            new IsExist(By.xpath("//h2[contains (text(), 'Профиль')]")).act(driver,context,reporter);
            new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                    Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);

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
