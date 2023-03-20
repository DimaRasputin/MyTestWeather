package net.extracode.selenium.ezwim.util.testers.dmitriyRasputin;


import net.extracode.selenium.common.ClearAndType;
import net.extracode.selenium.common.WaitFor;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.common.SaveArtifactsOnError;
import net.extracode.selenium.ezwim.util.ByHandler;
import net.extracode.selenium.ezwim.util.Const;
import net.extracode.selenium.ezwim.util.TestConfiguration;
import net.extracode.selenium.ezwim.util.navigator.Navigator;
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
import org.openqa.selenium.Keys;

import java.io.IOException;

public class TestWeather {

    private static final Logger logger = LogManager.getLogger(TestWeather.class.getSimpleName());

    private DriverWrapper driver;
    private Context context;
    private Reporter reporter;
    private final String location = "Краснодар";
    private final String expectedValue = "Погода в " + location + "е сегодня";



    @Before
    public void before() throws IOException {
       /* System.setProperty("webdriver.chrome.driver", "C:\\autotests\\chromedriver.exe");
        driver.manage().window().maximize();*/
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

        Dimension dimension = new Dimension(1820, 1080);
        driver.manage().window().setSize(dimension);

    }

    /**
     * @author Rasputin D<br>
     * @since March 2023
     * Expected: compare the weather on Yandex and Gismeteo<br>
     * <p>
     * PREPARE FOR TEST:<br>
     * AUTOTEST Steps:<br>
     * 1 Go to URL -  Yandex<br>
     *  select the desired city - Краснодар<br>
     *  get the weather result for today<br>
     * 2 Go to URL -  Gismeteo<br>
     * -  select the desired city - Краснодар<br>
     * -  get the weather result for today<br>
     * <br>
     * Compare the results obtained<br>
     * 3 Result<br>
     *  - the temperature should not differ by more than 3 degrees<br>
     * <p>
     */
    @Test
    public void test() throws Exception {
        try {
            logger.info("Created by Rasputin D 03.2023.");

            logger.info("Go to Yandex");
            Navigator.getInstance().getYandex(driver, context, reporter);

            logger.info("Search for the right city - Краснодар");
           Navigator.getInstance().getYandex()
                    .fillCityField(location +"\n",driver,context,reporter)
                    .clickRightCity(location,driver,context,reporter);

            logger.info(expectedValue);
            String weatherY = driver.findElement(By.cssSelector("a[href*='/pogoda/details']>div>div>span"))
                    .getText();
            logger.info(weatherY);


            logger.info("Go to Gismeteo");
            Navigator.getInstance().getGismeteo(driver, context, reporter);

            logger.info("Search for the right city - Краснодар");
            new ClearAndType(By.cssSelector("input[class='input js-input']"),
                    context.evaluateString(location)).act(driver, context, reporter);
            new WaitFor(By.cssSelector("a[href*='/search/Кра']>span"), "90").act(driver, context, reporter);

            logger.info("Click ENTER");
            driver.findElement(By.cssSelector("input[class='input js-input']")).sendKeys(Keys.ENTER);
            new WaitFor(ByHandler.getBy(context, "common.flagPageLoadGismeteo"),
                    Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);

            logger.info(expectedValue);
            String weatherG = driver.findElement(By.cssSelector("a[href*='/weather-krasnodar']>div>div>div>div>span"))
                    .getText();
            logger.info(weatherG);

            logger.info("Convert the received values into a number");
            Integer ResultsG = Integer.valueOf(weatherG);
            Integer ResultsY = Integer.valueOf(weatherY);

            logger.info("Comparison of the results obtained from both sites");
            Assert.assertEquals(ResultsY,ResultsG,5.0);

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


}
