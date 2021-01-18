# Selenium Ezwim Tutorial - Lesson 1

+ In this lesson we will learn about test code structure
+ We will consider each row of code in that test:

```
package net.extracode.selenium.ezwim;

import net.extracode.selenium.common.GetCurrentUrl;
import net.extracode.selenium.common.Navigate;
import net.extracode.selenium.common.Screenshot;
import net.extracode.selenium.common.Snapshot;
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

import java.io.IOException;

public class TemplateTest {

    private static final Logger logger = LogManager.getLogger(TemplateTest.class.getSimpleName());

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
    public void test() {
        try {
            new Navigate("http://google.com").act(driver, context, reporter);
            new Screenshot(this.getClass().getSimpleName() + "_" + context.getLaunchId() + "_testScreenshot.png")
                    .act(driver, context, reporter);
            new Snapshot(this.getClass().getSimpleName() + "_" + context.getLaunchId() + "_testSnapshot.html")
                    .act(driver, context, reporter);
            new GetCurrentUrl(this.getClass().getSimpleName() + ".currentUrl").act(driver, context, reporter);
            logger.info("current url: " + context.getVars().get(this.getClass().getSimpleName() + ".currentUrl"));
        } catch (Exception e) {
            new SaveArtifactsOnError(this.getClass().getSimpleName()).act(driver, context, reporter);
            throw new EzwimException("error on test", e);
        }
    }

    @After
    public void after() {
        driver.quit();
    }
}
```

+ First row - `package net.extracode.selenium.ezwim;` - here we defined in what package our test is placed
    + Packages is needed for grouping of classes (tests in that case) 
+ Next rows is imports:

```
import net.extracode.selenium.common.GetCurrentUrl;
import net.extracode.selenium.common.Navigate;
import net.extracode.selenium.common.Screenshot;
import net.extracode.selenium.common.Snapshot;
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
```

+ Imports is needed for including classes from other packages
    + If you wrote some class in another package and want to use it here, you need to use import

+ Next row is definition of class name and visibility - `public class TemplateTest {`
    + You can read about visibility in java documentation, now class name is only one what important for us

+ Next row is definition and initialization of static field of class which is logger

`private static final Logger logger = LogManager.getLogger(TemplateTest.class.getSimpleName());`

+ Here we create field of class - logger for all instances of this class (one for all because it's `static`),
    you can learn more about static and non static fields in java classes from java documentation
+ logger is needed for making logs
+ We create logger once and use it while during test
+ Logger has name of class where it was defined, this is necessary because we need to know which
    class make message which we see in log
+ You can learn more about it if you will read about Log4j java library

+ Next three rows is non static class fields definition

```
    private DriverWrapper driver;
    private Context context;
    private Reporter reporter;
```

+ These fields is needed for test working, here we have three main objects of library
    + DriverWrapper is used for communication with browser instance
    + Context is used for information storing and actions communication
    + Reporter will be used for reports, but not now
+ These rows is just definition, creation and using will be below
+ We use non static fields for these object for operation in different steps (Before, Test, After),
    it's because in each step we need to have the same configuration, data and browser

+ Next rows is Before block, is needed for test preparation

```
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
```

+ Here we create DriverWrapper, Context and Reporter with using configuration file
+ First we read system property - `test.config`,  it's needed if we start test on Jenkins server,
    if this property not passed from start command (we use this way), then configuration
    will be readed from config_local.properties file in src/test/resources folder

```
    String configParam;
    if (System.getProperties().containsKey("test.config")) {
        configParam = System.getProperty("test.config");
    } else {
        configParam = "config_local.properties";
    }
```

+ Then TestConfiguration read `contextProps` parameter from `configParam` file
+ Then TestConfiguration load all properties from file `contextProps`, which placed in
    src/test/resources, and put them all to context object
+ Then you can read it from context for example: `context.getVars().get("someKey")`

+ In configuration we have:
    + Browser type: `driverType=firefox`
    + Browser path: `driverPath=/home/bioker/crypto/dev/firefox/firefox`
    + Context initial configuration file: `contextProps=staging.properties`
    + Understanding about another properties is not necessary for test development now
+ TestConfiguration is class which read properties file and create DriverWrapper, Context and
    Reporter, we put them to fields which defined above

+ Also you have `by.properties` file in your src/test/resources, this file is used for storing
    web elements locations (By) in json format, they will be loaded into context with configuration
    and you can use this `By`s with next method calling
    `ByHandler.getBy(context, "keyFromByProperties")`

+ Next lines is Test block, it's main block with logic of test, when you create a test you place here
    your code

```
    @Test
    public void test() {
        try {
            new Navigate("http://google.com").act(driver, context, reporter);
            new Screenshot(this.getClass().getSimpleName() + "_" + context.getLaunchId() + "_testScreenshot.png")
                    .act(driver, context, reporter);
            new Snapshot(this.getClass().getSimpleName() + "_" + context.getLaunchId() + "_testSnapshot.html")
                    .act(driver, context, reporter);
            new GetCurrentUrl(this.getClass().getSimpleName() + ".currentUrl").act(driver, context, reporter);
            logger.info("current url: " + context.getVars().get(this.getClass().getSimpleName() + ".currentUrl"));
        } catch (Exception e) {
            new SaveArtifactsOnError(this.getClass().getSimpleName()).act(driver, context, reporter);
            throw new EzwimException("error on test", e);
        }
    }
```

+ Inside this block you have another nested block - try catch, it's needed for handling errors and
    saving artifacts (screenshot, snapshot, current url)

+ Next rows is After block, usually we just quit from browser here, but if
    in our test we made some changes which must me reverted, it's the best place for
    placing code which will do it

```
    @After
    public void after() {
        driver.quit();
    }
```
