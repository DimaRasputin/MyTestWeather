package net.extracode.selenium.ezwim.util.navigator;

import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;

public interface Navigable {

    void navigate(DriverWrapper driverWrapper, Context context, Reporter reporter);
}
