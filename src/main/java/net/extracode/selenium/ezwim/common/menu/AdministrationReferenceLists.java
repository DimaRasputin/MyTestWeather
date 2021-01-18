package net.extracode.selenium.ezwim.common.menu;

import net.extracode.selenium.common.Click;
import net.extracode.selenium.common.WaitFor;
import net.extracode.selenium.core.Action;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

public class AdministrationReferenceLists implements Action {

    @Override
    public void act(DriverWrapper driver, Context context, Reporter reporter) {
        new WaitFor(By.id("pageCompletelyLoaded"), "90").act(driver, context, reporter);
        new Click(By.xpath("//a[contains(text(), 'Administration')]")).act(driver, context, reporter);
        new WaitFor(By.id("pageCompletelyLoaded"), "90").act(driver, context, reporter);
        Actions builder = new Actions(driver.getDriver());
        org.openqa.selenium.interactions.Action action = builder.moveToElement(driver.findElement(By.xpath("//a[@id='ADM_REFLISTS']"))).build();
        action.perform();
        new WaitFor(By.id("pageCompletelyLoaded"), "90").act(driver, context, reporter);
        new Click(By.xpath("//div[@id='ADM_REFRES']")).act(driver, context, reporter);

    }
}
