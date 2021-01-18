package net.extracode.selenium.ezwim.util.navigator;

import net.extracode.selenium.common.WaitFor;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.util.ByHandler;
import net.extracode.selenium.ezwim.util.Const;
import net.extracode.selenium.ezwim.util.ContextExpression;
import net.extracode.selenium.tests.x2s.common.login.LoginToEGV;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;

public class Navigator {

    private static Navigator instance;

    public static Navigator getInstance() {
        if (instance == null) {
            instance = new Navigator();
        }
        return instance;
    }


    private NOVEST novest;

    private Navigator() {

        novest = new NOVEST();
    }


    public NOVEST getNovest() {
        return novest;
    }
}
