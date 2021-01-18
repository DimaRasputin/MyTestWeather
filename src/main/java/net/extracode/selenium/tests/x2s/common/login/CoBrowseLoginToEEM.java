package net.extracode.selenium.tests.x2s.common.login;

import net.extracode.selenium.common.*;
import net.extracode.selenium.core.Action;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class CoBrowseLoginToEEM implements Action {

    private static final Logger logger = LogManager.getLogger(CoBrowseLoginToEEM.class.getSimpleName());

    private String userLastName;
    private String userRole;
    private String skipLoadingCheck;
    private String getRandomUser;
    private String companyName;
    private String skiplogin;
    private String userLevel;

    public CoBrowseLoginToEEM(String userLastName, String userRole, String skipLoadingCheck, String getRandomUser, String companyName, String skiplogin, String userLevel) {
        this.userLastName = userLastName;
        this.userRole = userRole;
        this.skipLoadingCheck = skipLoadingCheck;
        this.getRandomUser = getRandomUser;
        this.companyName = companyName;
        this.skiplogin = skiplogin;
        this.userLevel = userLevel;
    }

    public CoBrowseLoginToEEM(String userLastName, String companyName, String userRole) {
        this.userLastName = userLastName;
        this.companyName = companyName;
        this.userRole = userRole;
        skipLoadingCheck = "false";
        getRandomUser = "false";
        skiplogin = "false";
        userLevel = "0";
    }

    public CoBrowseLoginToEEM(String userLastName, String companyName) {
        this.userLastName = userLastName;
        this.companyName = companyName;
        userRole = "E";
        skipLoadingCheck = "false";
        getRandomUser = "false";
        skiplogin = "false";
        userLevel = "0";
    }

    @Override
    public void act(DriverWrapper driver, Context context, Reporter reporter) {
        new DefaultDelay("90").act(driver, context, reporter);
        logger.info("clicking the Dasboard tab");
        new Click(By.xpath(".//*[@id='TR_PARENT_TAB_CELLS']//a[contains(text(), 'Customers')]")).act(driver, context, reporter);
        logger.info("clicking the Customers tab");
        new WaitFor(By.xpath("//td[@id='CUST']//a[contains(text(), 'Customers')]"), "90").act(driver, context, reporter);
        logger.info("Set the company name: " + context.evaluateString(companyName));
        new ClearAndType(By.xpath("//input[contains(@id,'corp_name')]"), context.evaluateString(companyName)).act(driver, context, reporter);
        logger.info("Set the role to: " + context.evaluateString(userRole));
        new SelectByValue(By.id("a_role"), context.evaluateString(userRole)).act(driver, context, reporter);
        logger.info("Set the level to: " + context.evaluateString(userLevel));
        new SelectByIndex(By.id("a_hierarchy_level"), context.evaluateString(userLevel)).act(driver, context, reporter);
        logger.info("Set the level to: " + context.evaluateString(userLevel));
        new Click(By.xpath("//td[contains(@onclick, 'filter') and contains(./font/text(), 'Go')]")).act(driver, context, reporter);
        logger.info("Set the userName to: " + context.evaluateString(userLastName));
        new ClearAndType(By.id("a_user_name"), context.evaluateString(userLastName)).act(driver, context, reporter);
        new WaitFor(By.xpath("//td[contains(text(), 'Co-browse')]"), "90").act(driver, context, reporter);
        new Screenshot(context.getLaunchId() + "-CoBrowseLoginToEEM-userFiltered.png").act(driver, context, reporter);

        if (!Boolean.parseBoolean(context.evaluateString(skipLoadingCheck))) {
            login(driver, context, reporter);
        }

    }

    private void login(DriverWrapper driver, Context context, Reporter reporter) {
        String number = "1";
        if (Boolean.parseBoolean(context.evaluateString(getRandomUser))) {
            number = getRandomUserNumber(driver, context, reporter);
        }
        new Click(By.xpath(".//*[@id='table_reptable']//tr[td[text()='" +
                context.evaluateString(companyName) + "']]" +
                "//img[contains(@onclick, 'browse_login')])[" + number + "]")).act(driver, context, reporter);
        new WaitFor(By.xpath(".//td//*[contains (@onclick, 'LOGOUT')]"), "90").act(driver, context, reporter);
    }

    private String getRandomUserNumber(DriverWrapper driver, Context context, Reporter reporter) {
        new GetSize(By.xpath(".//*[@id='table_reptable']//tr[td[text()='" + context.evaluateString(companyName) + "']]"),
                "CoBrowseLoginToEEM.count").act(driver, context, reporter);
        int count = Integer.valueOf((String) context.getVars().get("CoBrowseLoginToEEM.count"));
        int random = (int) (Math.floor(Math.random() * (count - 1)) + 1);
        logger.info("getting random user by number: " + String.valueOf(random));
        return String.valueOf(random);
    }
}
