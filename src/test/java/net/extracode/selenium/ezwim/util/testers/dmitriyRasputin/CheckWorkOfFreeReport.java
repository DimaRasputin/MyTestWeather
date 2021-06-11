package net.extracode.selenium.ezwim.util.testers.dmitriyRasputin;


import net.extracode.selenium.common.IsExist;
import net.extracode.selenium.common.Pause;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.common.SaveArtifactsOnError;
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

public class CheckWorkOfFreeReport {

    private static final Logger logger = LogManager.getLogger(CheckWorkOfFreeReport.class.getSimpleName());

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
     * To automate ticket(s): Novestio-00018<br>
     * Expected: description of the new site<br>
     * <p>
     * PREPARE FOR TEST:<br>
     * AUTOTEST Steps:<br>
     * 1 Go to URL -  novest.io<br>
     * 2 go to Test Novest<br>
     * 3 changing the language to Russian<br>
     * 4 Go to page:<br>
     *  - NFREE<br>
     * 5 fill in the necessary filters<br>
     * 6 check its contents<br>
     * 7 Result<br>
     *  - the content of the page must match the expected one<br>
     * <p>
     */
    @Test
    public void test() throws Exception {
        try {
            String email = "patrick+FreeReport@ezwim-mail.com";
            String password = "eHxu^2z)111";
            String budget = "1000000";
            String ExampleNumber  = "2386";

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

            logger.info("Go to page nFree");
            Navigator.getInstance().getNovest().getUserInterface()
                    .getFree(driver,context,reporter);
            new Pause("10000").act(driver, context, reporter);
                    //.fillBudget(budget,driver,context,reporter)
            Navigator.getInstance().getNovest().getUserInterface()
                    .getFree()
                    .selectNumberAssetsText("15",driver,context,reporter);
            Navigator.getInstance().getNovest().getUserInterface()
                    .getFree()
                    .fillExampleNumber(ExampleNumber,driver,context,reporter)
                    .clickBuildAnExample(driver,context,reporter);
            new Pause("10000").act(driver, context, reporter);

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


        logger.info("check the value in the - NOVESTIO field");
        String valueNOVESTIO = GetAct.getElementAttr(By.xpath("//*[@id='brutforse-result']"),"data-attr", driver, context, reporter);
        Assert.assertEquals("Value in message log is incorrect", valueNOVESTIO, "+9,0%");
        logger.info("'Процент в NOVESTIO совпадает с ожиаемым" +  "+9,0%");

        logger.info("check the value in the - «ENTWICKLER» field");
        String valueENTWICKLER = GetAct.getElementAttr(By.xpath("//*[@id='simple-greedy-result']"),"data-attr", driver, context, reporter);
        Assert.assertEquals("Value in message log is incorrect", valueENTWICKLER, "+4,6%");
        logger.info("'Процент в «ENTWICKLER» совпадает с ожиаемым" +  "+4,6%");

        logger.info("check the value in the - «SEKRETÄR» field");
        String valueSEKRETAR = GetAct.getElementAttr(By.xpath("//*[@id='dumb-greedy-result']"),"data-attr", driver, context, reporter);
        Assert.assertEquals("Value in message log is incorrect", valueSEKRETAR, "100%");
        logger.info("'Процент в «SEKRETÄR» совпадает с ожиаемым" +  "100%");

        logger.info("check the value in the -  «SEKRETÄR»  Investitionsobjekte field");
        String valueInvestitionsobjekteSEKRETAR = GetAct.getElementAttr(By.xpath("//*[@id='secretary-number']"),"value", driver, context, reporter);
        Assert.assertEquals("Value in message log is incorrect", valueInvestitionsobjekteSEKRETAR, "9");
        logger.info("'Данные в «Anzahl der Investitionsobjekte:» совпадает с ожиаемым" +  "- 9");

        logger.info("check the value in the - «ENTWICKLER» field");
        String valueInvestitionsobjekteENTWICKLER = GetAct.getElementAttr(By.xpath("//*[@id='simple-number']"),"value", driver, context, reporter);
        Assert.assertEquals("Value in message log is incorrect", valueInvestitionsobjekteENTWICKLER, "10");
        logger.info("'Данные в «Anzahl der Investitionsobjekte:» совпадает с ожиаемым" +  "- 10");

        logger.info("check the value in the - «NOVESTIO» field");
        String valueInvestitionsobjekteNOVESTIO = GetAct.getElementAttr(By.xpath("//*[@id='beast-number']"),"value", driver, context, reporter);
        Assert.assertEquals("Value in message log is incorrect", valueInvestitionsobjekteNOVESTIO, "11");
        logger.info("'Данные в «Anzahl der Investitionsobjekte:» совпадает с ожиаемым" +  "- 11");

        logger.info("check Income");
        logger.info("check the value in the -  «SEKRETÄR»  Investitionsobjekte field");
        String valueIncomeSEKRETAR = GetAct.getElementAttr(By.xpath("//*[@id='secretary-income']"),"value", driver, context, reporter);
        Assert.assertEquals("Value in message log is incorrect", valueIncomeSEKRETAR, "$ 1 375 900");
        logger.info("'Данные совпадают с ожиаемым" +  "- $ 1 375 900");

        logger.info("check the value in the - «ENTWICKLER» field");
        String valueIncomeENTWICKLER = GetAct.getElementAttr(By.xpath("//*[@id='simple-income']"),"value", driver, context, reporter);
        Assert.assertEquals("Value in message log is incorrect", valueIncomeENTWICKLER, "$ 1 458 000");
        logger.info("'Данные совпадают с ожиаемым" +  "- $ 1 458 000");

        logger.info("check the value in the - «NOVESTIO» field");
        String valueIncomeNOVESTIO = GetAct.getElementAttr(By.xpath("//*[@id='beast-income']"),"value", driver, context, reporter);
        Assert.assertEquals("Value in message log is incorrect", valueIncomeNOVESTIO, "$ 1 518 000");
        logger.info("'Данные совпадают с ожиаемым" +  "- $ 1 518 000");


        logger.info("check Expenses");
        logger.info("check the value in the -  «SEKRETÄR»  Investitionsobjekte field");
        String valueExpensesSEKRETAR = GetAct.getElementAttr(By.xpath("//*[@id='secretary-expenses']"),"value", driver, context, reporter);
        Assert.assertEquals("Value in message log is incorrect", valueExpensesSEKRETAR, "$ 168 100");
        logger.info("'Данные совпадают с ожиаемым" +  "- $ 168 100");

        logger.info("check the value in the - «ENTWICKLER» field");
        String valueExpensesENTWICKLER = GetAct.getElementAttr(By.xpath("//*[@id='simple-expenses']"),"value", driver, context, reporter);
        Assert.assertEquals("Value in message log is incorrect", valueExpensesENTWICKLER, "$ 191 900");
        logger.info("'Данные совпадают с ожиаемым" +  "- $ 191 900");

        logger.info("check the value in the - «NOVESTIO» field");
        String valueExpensesNOVESTIO = GetAct.getElementAttr(By.xpath("//*[@id='beast-expenses']"),"value", driver, context, reporter);
        Assert.assertEquals("Value in message log is incorrect", valueExpensesNOVESTIO, "$ 191 100");
        logger.info("'Данные совпадают с ожиаемым" +  "- $ 191 100");

        logger.info("check Profit");
        logger.info("check the value in the -  «SEKRETÄR»  Investitionsobjekte field");
        String valueProfitSEKRETAR = GetAct.getElementAttr(By.xpath("//*[@id='secretary-profit']"),"value", driver, context, reporter);
        Assert.assertEquals("Value in message log is incorrect", valueProfitSEKRETAR, "$ 1 207 800");
        logger.info("'Данные совпадают с ожиаемым" +  "- $ 1 207 800");

        logger.info("check the value in the - «ENTWICKLER» field");
        String valueProfitENTWICKLER = GetAct.getElementAttr(By.xpath("//*[@id='simple-profit']"),"value", driver, context, reporter);
        Assert.assertEquals("Value in message log is incorrect", valueProfitENTWICKLER, "$ 1 266 100");
        logger.info("'Данные совпадают с ожиаемым" +  "- $ 1 266 100");

        logger.info("check the value in the - «NOVESTIO» field");
        String valueProfitNOVESTIO = GetAct.getElementAttr(By.xpath("//*[@id='beast-profit']"),"value", driver, context, reporter);
        Assert.assertEquals("Value in message log is incorrect", valueProfitNOVESTIO, "$ 1 326 900");
        logger.info("'Данные совпадают с ожиаемым" +  "- $ 1 326 900");

        logger.info("check the availability of the image № 1");
        new IsExist(By.xpath("//*[@id='secretary-graph-canvas']")).act(driver, context, reporter);

        logger.info("check the availability of the image № 2");
        new IsExist(By.xpath("//*[@id='simple-graph-canvas']")).act(driver, context, reporter);

        logger.info("check the availability of the image № 3");
        new IsExist(By.xpath("//*[@id='brutforse-graph-canvas']")).act(driver, context, reporter);

        logger.info("status: finished.");
    }





}
