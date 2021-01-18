package net.extracode.selenium.ezwim.common.menu;

import net.extracode.selenium.common.Click;
import net.extracode.selenium.common.WaitFor;
import net.extracode.selenium.core.Action;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.util.ByHandler;
import net.extracode.selenium.ezwim.util.Const;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.openqa.selenium.By;

public class UserManagement implements Action {

    @Override
    public void act(DriverWrapper driver, Context context, Reporter reporter) {

        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"), Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);
        new Click(By.xpath("//a[ contains(text(), 'Administration')]")).act(driver, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"), Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);
        new Click(By.xpath("//a[@id='ADM_UM']")).act(driver, context, reporter);

    }
}