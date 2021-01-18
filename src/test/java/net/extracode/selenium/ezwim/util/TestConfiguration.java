package net.extracode.selenium.ezwim.util;

import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.factory.DriverFactory;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.LogReporter;
import net.extracode.test.reporter.Reporter;
import org.json.JSONObject;
import org.openqa.selenium.Dimension;

import java.io.IOException;
import java.util.Properties;

public class TestConfiguration {

    private static final String DRIVER_TYPE_PROP = "driverType";
    private static final String DRIVER_PATH_PROP = "driverPath";
    private static final String DRIVER_PATH_GECKO = "driverGecko";
    private static final String DRIVER_CUST_SIZE_PROP = "driverCustomSize";
    private static final String DRIVER_WIDTH_PROP = "driverCustomSize.width";
    private static final String DRIVER_HEIGHT_PROP = "driverCustomSize.height";
    private static final String DRIVER_MAXIMIZE_PROP = "driverMaximize";
    private static final String CONTEXT_PROPS_PROP = "contextProps";
    private static final String DRIVER_DOWNLOAD_DIR = "downloadDir";
    private static final String DRIVER_BINARY_BROWSER_DIR = "setBinaryBrowser";

    public static TestConfiguration getFromResourcesConfig(String propertiesPath) throws IOException {
        return new TestConfiguration(getPropertiesFromResource(propertiesPath));
    }

    public static Properties getPropertiesFromResource(String propertiesPath) throws IOException {
        Properties properties = new Properties();
        properties.load(TestConfiguration.class.getResourceAsStream("/" + propertiesPath));
        return properties;
    }


    private DriverWrapper driverWrapper;
    private Context context;
    private Reporter reporter;

    public TestConfiguration(Properties properties) throws IOException {

        String driverType = properties.getProperty(DRIVER_TYPE_PROP);
        String driverPath = properties.getProperty(DRIVER_PATH_PROP);
        String driverGecko = properties.getProperty(DRIVER_PATH_GECKO);
        String driverDownloadDir= properties.getProperty(DRIVER_DOWNLOAD_DIR);
        String driverBinaryBrowserDir= properties.getProperty(DRIVER_BINARY_BROWSER_DIR);
        String driverCustomSize = properties.getProperty(DRIVER_CUST_SIZE_PROP);
        String driverWidth = properties.getProperty(DRIVER_WIDTH_PROP);
        String driverHeight = properties.getProperty(DRIVER_HEIGHT_PROP);
        String driverMaximize = properties.getProperty(DRIVER_MAXIMIZE_PROP);
        String contextProps = properties.getProperty(CONTEXT_PROPS_PROP);

        JSONObject driverJsonConfig = new JSONObject();
        driverJsonConfig.put("type", driverType);
        driverJsonConfig.put("path", driverPath);
        driverJsonConfig.put("gecko", driverGecko);
        driverJsonConfig.put("download", driverDownloadDir);
        driverJsonConfig.put("binary", driverBinaryBrowserDir);

        if (driverType.equals("firefox")) {
            if (properties.containsKey("driver.firefox.profile")) {
                driverJsonConfig.put("firefoxProfile", new JSONObject(properties.getProperty("driver.firefox.profile")));
            }
        }

        driverWrapper = new DriverWrapper(DriverFactory.createFromJson(driverJsonConfig.toString()));

        if (Boolean.valueOf(driverCustomSize)) {
            driverWrapper.manage().window().setSize(new Dimension(
                    Integer.valueOf(driverWidth),
                    Integer.valueOf(driverHeight)
            ));
        }

        if (Boolean.valueOf(driverMaximize)) {
            driverWrapper.manage().window().maximize();
        }

        context = new Context();

        Properties contextProperties = new Properties();
        contextProperties.load(TestConfiguration.class.getResourceAsStream("/" + contextProps));

        Properties byProperties = new Properties();
        byProperties.load(TestConfiguration.class.getResourceAsStream("/by.properties"));

        context.loadProperties("", contextProperties);
        context.loadJsonBys("", byProperties);

        reporter = new LogReporter();
    }

    public DriverWrapper getDriverWrapper() {
        return driverWrapper;
    }

    public Context getContext() {
        return context;
    }

    public Reporter getReporter() {
        return reporter;
    }
}
