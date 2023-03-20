package net.extracode.selenium.ezwim.util.navigator;

import net.extracode.selenium.common.WaitFor;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.util.ByHandler;
import net.extracode.selenium.ezwim.util.Const;
import net.extracode.selenium.ezwim.util.ContextExpression;
import net.extracode.selenium.ezwim.util.navigator.gismeteo.GISMETEO;
import net.extracode.selenium.ezwim.util.navigator.yandex.YANDEX;
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


    private final YANDEX yandex;
    private GISMETEO gismeteo;

    private Navigator() {
        yandex = new YANDEX();
        gismeteo = new GISMETEO();
    }

    public YANDEX getYandex() {
        return yandex;
    }

    public YANDEX getYandex(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        new LoginToEGV(ContextExpression.wrap("yandex.url")).act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoadYandex"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return yandex;
    }

    public GISMETEO getGismeteo() {
        return gismeteo;
    }

    public GISMETEO getGismeteo(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        new LoginToEGV(ContextExpression.wrap("gismeteo.url")).act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoadGismeteo"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return gismeteo;
    }

}
