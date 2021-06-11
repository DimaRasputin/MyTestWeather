package net.extracode.selenium.ezwim.util.testers.dmitriyRasputin;


import net.extracode.selenium.common.IsExist;
import net.extracode.selenium.common.Pause;
import net.extracode.selenium.common.WaitFor;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.common.SaveArtifactsOnError;
import net.extracode.selenium.ezwim.util.ByHandler;
import net.extracode.selenium.ezwim.util.Const;
import net.extracode.selenium.ezwim.util.GetAct;
import net.extracode.selenium.ezwim.util.TestConfiguration;
import net.extracode.selenium.ezwim.util.navigator.Navigator;
import net.extracode.selenium.ezwim.util.remote.RemoteServerAct;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import java.io.IOException;
import java.util.Properties;

public class CheckWorkOfConsultancyReport {

    private static final Logger logger = LogManager.getLogger(CheckWorkOfConsultancyReport.class.getSimpleName());

    private DriverWrapper driver;
    private Context context;
    private Reporter reporter;
    private String dbNumber;
    private String dbStaging = "";
    private String dbDemo = "";
    String downloadPath;


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

        if (System.getProperties().containsKey("test.config")) {
            configParam = System.getProperty("test.config");
            downloadPath = "/home/ezwim/tmp/autotests/downloads/n-00017/";
        } else {
            configParam = "config_local.properties";
            downloadPath = "F:\\\\Selenium local file\\\\";
        }

        Properties properties = new Properties();
        properties.load(TestConfiguration.class.getResourceAsStream("/" + configParam));
        properties.setProperty("downloadDir", downloadPath);

        Dimension dimension = new Dimension(1820, 1080);
        driver.manage().window().setSize(dimension);
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
     * @since May 2021
     * To automate ticket(s): Novestio-00017<br>
     * Expected: description of the new site<br>
     * <p>
     * PREPARE FOR TEST:<br>
     * AUTOTEST Steps:<br>
     * 1 Go to URL -  novest.io<br>
     * 2 go to Test Novest<br>
     * 3 changing the language to Russian<br>
     * 4 Go to page:<br>
     *  - Consultancy<br>
     * 5 fill in the necessary filters<br>
     * 6 Download this report and check the content<br>
     * 7 Result<br>
     *  - the content of the page must match the expected one<br>
     * <p>
     */
    @Test
    public void test() throws Exception {
        try {
            String email = "patrick+Consultancy@ezwim-mail.com";
            String password = "eHxu^2z)111";
            String budget = "1000000";
            String ExampleNumber  = "568";

            logger.info("Created by Rasputin D 05.2021.");

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

            logger.info("Go to page nConsultancy");
            Navigator.getInstance().getNovest().getUserInterface()
                    .getConsultancy(driver,context,reporter)
                    //.fillBudget(budget,driver,context,reporter)
                    .selectNumberAssetsText("15",driver,context,reporter)
                    .fillExampleNumber(ExampleNumber,driver,context,reporter)
                    .clickBuildAnExample(driver,context,reporter);
            new Pause("10000").act(driver, context, reporter);
            Navigator.getInstance().getNovest().getUserInterface()
                    .getConsultancy()
                    .clickBuildAnExample(driver,context,reporter);

            new Pause("10000").act(driver, context, reporter);

            Navigator.getInstance().getNovest().getUserInterface()
                    .getConsultancy().clickDownload(driver,context,reporter);

            CheckConsultancy();

            logger.info("Test passed");

        } catch (Exception e) {
            new SaveArtifactsOnError(this.getClass().getSimpleName()).act(driver, context, reporter);
            throw e;
        }
    }

    @After
    public void after() {

        driver.quit();
    }

    private void CheckConsultancy() throws IOException {

        logger.info("Check report.");

        String[] Chart = new String[2];
        Chart[0] = "Бюджет";
        Double[] Chart1 = new Double[2];
        Chart1[1] = 1000000.0;

        String[] Chart2 = new String[9];
        Chart2[0] = "expenses";
        Chart2[1] = "income";
        Chart2[2] = "profit";
        Chart2[3] = "expenses";
        Chart2[4] = "income";
        Chart2[5] = "profit";
        Chart2[6] = "expenses";
        Chart2[7] = "income";
        Chart2[8] = "profit";

        Double[] Chart3 = new Double[6];
        Chart3[0] = 456800.0;
        Chart3[1] = 484300.0;
        Chart3[2] = 27500.0;
        Chart3[3] = 456800.0;
        Chart3[4] = 484300.0;
        Chart3[5] = 27500.0;

        Double[] Chart4 = new Double[9];
        Chart4[0] = 281600.0;
        Chart4[1] = 307600.0;
        Chart4[2] = 26000.0;
        Chart4[3] = 281600.0;
        Chart4[4] = 307600.0;
        Chart4[5] = 26000.0;
        Chart4[6] = 281600.0;
        Chart4[7] = 307600.0;
        Chart4[8] = 26000.0;

        Double[] Chart5 = new Double[9];
        Chart5[0] = 223000.0;
        Chart5[1] = 247700.0;
        Chart5[2] = 24700.0;
        Chart5[3] = 223000.0;
        Chart5[4] = 247700.0;
        Chart5[5] = 24700.0;
        Chart5[6] = 223000.0;
        Chart5[7] = 247700.0;
        Chart5[8] = 24700.0;

        Double[] Chart6 = new Double[3];
        Chart6[0] = 489400.0;
        Chart6[1] = 514000.0;
        Chart6[2] = 24600.0;

        Double[] Chart7 = new Double[3];
        Chart7[0] = 280100.0;
        Chart7[1] = 304100.0;
        Chart7[2] = 24000.0;

        Double[] Chart8 = new Double[3];
        Chart8[0] = 254400.0;
        Chart8[1] = 277800.0;
        Chart8[2] = 23400.0;

        Double[] Chart9 = new Double[3];
        Chart9[0] = 279400.0;
        Chart9[1] = 300500.0;
        Chart9[2] = 21100.0;

        Double[] Chart10 = new Double[9];
        Chart10[0] = 183700.0;
        Chart10[1] = 203900.0;
        Chart10[2] = 20200.0;
        Chart10[3] = 0.0;
        Chart10[4] = 0.0;
        Chart10[5] = 0.0;
        Chart10[6] = 183700.0;
        Chart10[7] = 203900.0;
        Chart10[8] = 20200.0;

        Double[] Chart11 = new Double[9];
        Chart11[0] = 100700.0;
        Chart11[1] = 118100.0;
        Chart11[2] = 17400.0;
        Chart11[3] = 0.0;
        Chart11[4] = 0.0;
        Chart11[5] = 0.0;
        Chart11[6] = 100700.0;
        Chart11[7] = 118100.0;
        Chart11[8] = 17400.0;

        Double[] Chart12 = new Double[3];
        Chart12[0] = 428700.0;
        Chart12[1] = 446000.0;
        Chart12[2] = 17300.0;

        Double[] Chart13 = new Double[9];
        Chart13[0] = 141000.0;
        Chart13[1] = 155900.0;
        Chart13[2] = 14900.0;
        Chart13[3] = 0.0;
        Chart13[4] = 0.0;
        Chart13[5] = 0.0;
        Chart13[6] = 141000.0;
        Chart13[7] = 155900.0;
        Chart13[8] = 14900.0;

        Double[] Chart14 = new Double[3];
        Chart14[0] = 225600.0;
        Chart14[1] = 240200.0;
        Chart14[2] = 14600.0;

        Double[] Chart15 = new Double[3];
        Chart15[0] = 319800.0;
        Chart15[1] = 332900.0;
        Chart15[2] = 13100.0;

        Double[] Chart16 = new Double[3];
        Chart16[0] = 189500.0;
        Chart16[1] = 200500.0;
        Chart16[2] = 11000.0;

        Double[] Chart17 = new Double[3];
        Chart17[0] = 418900.0;
        Chart17[1] = 429000.0;
        Chart17[2] = 10100.0;

        String[] Chart18 = new String[2];
        Chart18[0] = "Total";
        Double[] Chart19 = new Double[9];
        Chart19[3] = 961400.0;
        Chart19[4] = 1039600.0;
        Chart19[5] = 78200.0;
        Chart19[6] = 930000.0;
        Chart19[7] = 1033200.0;
        Chart19[8] = 103200.0;

        logger.info("row from XLSX checked Page- Chart, line 1");
        for (int c = 0; c <= 0; c++) {
            logger.info("Start check XLSX:" + Chart[c]);
            Assert.assertEquals("MESSAGE", Chart[c], GetAct.getStringFromExcelFile
                    (downloadPath + "report.xlsx", "Worksheet", 0, c));
            logger.info("row from XLSX checked Page- Consultancy");
        }
        logger.info("row from XLSX checked Page- Chart, line 1");
        for (int c = 1; c <= 1; c++) {
            logger.info("Start check XLSX:" + Chart1[c]);
            Assert.assertEquals("MESSAGE", Chart1[c], GetAct.getNumberFromExcelFile
                    (downloadPath + "report.xlsx", "Worksheet", 0, c), 0.1);
            logger.info("row from XLSX checked Page- Consultancy");
        }

        logger.info("row from XLSX checked Page- Chart, line 3");
        for (int c = 0; c <= 8; c++) {
            logger.info("Start check XLSX:" + Chart2[c]);
            Assert.assertEquals("MESSAGE", Chart2[c], GetAct.getStringFromExcelFile
                    (downloadPath + "report.xlsx", "Worksheet", 2, c));
            logger.info("row from XLSX checked Page- Consultancy");
        }

        logger.info("row from XLSX checked Page- Chart, line 4");
        for (int c = 0; c <= 5; c++) {
            logger.info("Start check XLSX:" + Chart3[c]);
            Assert.assertEquals("MESSAGE", Chart3[c], GetAct.getNumberFromExcelFile
                    (downloadPath + "report.xlsx", "Worksheet", 3, c), 0.1);
            logger.info("row from XLSX checked Page- Trend per cost type");
        }

        logger.info("row from XLSX checked Page- Chart, line 5");
        for (int c = 0; c <= 8; c++) {
            logger.info("Start check XLSX:" + Chart4[c]);
            Assert.assertEquals("MESSAGE", Chart4[c], GetAct.getNumberFromExcelFile
                    (downloadPath + "report.xlsx", "Worksheet", 4, c), 0.1);
            logger.info("row from XLSX checked Page- Trend per cost type");
        }

        logger.info("row from XLSX checked Page- Chart, line 6");
        for (int c = 0; c <= 8; c++) {
            logger.info("Start check XLSX:" + Chart5[c]);
            Assert.assertEquals("MESSAGE", Chart5[c], GetAct.getNumberFromExcelFile
                    (downloadPath + "report.xlsx", "Worksheet", 5, c), 0.1);
            logger.info("row from XLSX checked Page- Trend per cost type");
        }

        logger.info("row from XLSX checked Page- Chart, line 7");
        for (int c = 0; c <= 2; c++) {
            logger.info("Start check XLSX:" + Chart6[c]);
            Assert.assertEquals("MESSAGE", Chart6[c], GetAct.getNumberFromExcelFile
                    (downloadPath + "report.xlsx", "Worksheet", 6, c), 0.1);
            logger.info("row from XLSX checked Page- Trend per cost type");
        }

        logger.info("row from XLSX checked Page- Chart, line 8");
        for (int c = 0; c <= 2; c++) {
            logger.info("Start check XLSX:" + Chart7[c]);
            Assert.assertEquals("MESSAGE", Chart7[c], GetAct.getNumberFromExcelFile
                    (downloadPath + "report.xlsx", "Worksheet", 7, c), 0.1);
            logger.info("row from XLSX checked Page- Trend per cost type");
        }

        logger.info("row from XLSX checked Page- Chart, line 9");
        for (int c = 0; c <= 2; c++) {
            logger.info("Start check XLSX:" + Chart8[c]);
            Assert.assertEquals("MESSAGE", Chart8[c], GetAct.getNumberFromExcelFile
                    (downloadPath + "report.xlsx", "Worksheet", 8, c), 0.1);
            logger.info("row from XLSX checked Page- Trend per cost type");
        }

        logger.info("row from XLSX checked Page- Chart, line 10");
        for (int c = 0; c <= 2; c++) {
            logger.info("Start check XLSX:" + Chart9[c]);
            Assert.assertEquals("MESSAGE", Chart9[c], GetAct.getNumberFromExcelFile
                    (downloadPath + "report.xlsx", "Worksheet", 9, c), 0.1);
            logger.info("row from XLSX checked Page- Trend per cost type");
        }

        logger.info("row from XLSX checked Page- Chart, line 11");
        for (int c = 0; c <= 8; c++) {
            logger.info("Start check XLSX:" + Chart10[c]);
            Assert.assertEquals("MESSAGE", Chart10[c], GetAct.getNumberFromExcelFile
                    (downloadPath + "report.xlsx", "Worksheet", 10, c), 0.1);
            logger.info("row from XLSX checked Page- Trend per cost type");
        }

        logger.info("row from XLSX checked Page- Chart, line 12");
        for (int c = 0; c <= 8; c++) {
            logger.info("Start check XLSX:" + Chart11[c]);
            Assert.assertEquals("MESSAGE", Chart11[c], GetAct.getNumberFromExcelFile
                    (downloadPath + "report.xlsx", "Worksheet", 11, c), 0.1);
            logger.info("row from XLSX checked Page- Trend per cost type");
        }

        logger.info("row from XLSX checked Page- Chart, line 13");
        for (int c = 0; c <= 2; c++) {
            logger.info("Start check XLSX:" + Chart12[c]);
            Assert.assertEquals("MESSAGE", Chart12[c], GetAct.getNumberFromExcelFile
                    (downloadPath + "report.xlsx", "Worksheet", 12, c), 0.1);
            logger.info("row from XLSX checked Page- Trend per cost type");
        }

        logger.info("row from XLSX checked Page- Chart, line 14");
        for (int c = 0; c <= 8; c++) {
            logger.info("Start check XLSX:" + Chart13[c]);
            Assert.assertEquals("MESSAGE", Chart13[c], GetAct.getNumberFromExcelFile
                    (downloadPath + "report.xlsx", "Worksheet", 13, c), 0.1);
            logger.info("row from XLSX checked Page- Trend per cost type");
        }

        logger.info("row from XLSX checked Page- Chart, line 15");
        for (int c = 0; c <= 2; c++) {
            logger.info("Start check XLSX:" + Chart14[c]);
            Assert.assertEquals("MESSAGE", Chart14[c], GetAct.getNumberFromExcelFile
                    (downloadPath + "report.xlsx", "Worksheet", 14, c), 0.1);
            logger.info("row from XLSX checked Page- Trend per cost type");
        }

        logger.info("row from XLSX checked Page- Chart, line 16");
        for (int c = 0; c <= 2; c++) {
            logger.info("Start check XLSX:" + Chart15[c]);
            Assert.assertEquals("MESSAGE", Chart15[c], GetAct.getNumberFromExcelFile
                    (downloadPath + "report.xlsx", "Worksheet", 15, c), 0.1);
            logger.info("row from XLSX checked Page- Trend per cost type");
        }

        logger.info("row from XLSX checked Page- Chart, line 17");
        for (int c = 0; c <= 2; c++) {
            logger.info("Start check XLSX:" + Chart16[c]);
            Assert.assertEquals("MESSAGE", Chart16[c], GetAct.getNumberFromExcelFile
                    (downloadPath + "report.xlsx", "Worksheet", 16, c), 0.1);
            logger.info("row from XLSX checked Page- Trend per cost type");
        }

        logger.info("row from XLSX checked Page- Chart, line 18");
        for (int c = 0; c <= 2; c++) {
            logger.info("Start check XLSX:" + Chart17[c]);
            Assert.assertEquals("MESSAGE", Chart17[c], GetAct.getNumberFromExcelFile
                    (downloadPath + "report.xlsx", "Worksheet", 17, c), 0.1);
            logger.info("row from XLSX checked Page- Trend per cost type");
        }


        logger.info("row from XLSX checked Page- Chart, line 19");
        for (int c = 0; c <= 0; c++) {
            logger.info("Start check XLSX:" + Chart18[c]);
            Assert.assertEquals("MESSAGE", Chart18[c], GetAct.getStringFromExcelFile
                    (downloadPath + "report.xlsx", "Worksheet", 18, c));
            logger.info("row from XLSX checked Page- Consultancy");
        }
        logger.info("row from XLSX checked Page- Chart, line 19");
        for (int c = 3; c <= 8; c++) {
            logger.info("Start check XLSX:" + Chart19[c]);
            Assert.assertEquals("MESSAGE", Chart19[c], GetAct.getNumberFromExcelFile
                    (downloadPath + "report.xlsx", "Worksheet", 18, c), 0.1);
            logger.info("row from XLSX checked Page- Consultancy");
        }



        logger.info("status: finished.");
    }





}
