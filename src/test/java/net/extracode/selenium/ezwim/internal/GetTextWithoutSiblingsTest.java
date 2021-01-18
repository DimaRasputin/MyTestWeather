package net.extracode.selenium.ezwim.internal;

import net.extracode.selenium.common.Navigate;
import net.extracode.selenium.common.ext.GetAct;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.common.SaveArtifactsOnError;
import net.extracode.selenium.ezwim.exception.EzwimException;
import net.extracode.selenium.ezwim.util.TestConfiguration;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class GetTextWithoutSiblingsTest {

    private static final Logger logger = LogManager.getLogger(GetTextWithoutSiblingsTest.class.getSimpleName());

    private DriverWrapper driver;
    private Context context;
    private Reporter reporter;

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
    }

    @Test
    public void test() throws MalformedURLException {
        try {
            File file = new File("test_data/get_text_without_siblings.html");
            new Navigate(file.toURI().toURL().toString()).act(driver, context, reporter);
            String text = GetAct.getElementTextWithoutSiblings("#test", driver, context, reporter);
            logger.debug("text: " + text);
            if (!text.trim().equals("TestText")) {
                throw new EzwimException("text not equals with expected");
            }
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
