package net.extracode.selenium.ezwim.util.navigator.yandex;

import net.extracode.selenium.common.*;
import net.extracode.selenium.core.Action;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.util.ByHandler;
import net.extracode.selenium.ezwim.util.Const;
import net.extracode.selenium.ezwim.util.navigator.Navigable;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import java.util.HashMap;

public class YANDEX implements Navigable {

    private static final Logger logger = LogManager.getLogger(YANDEX.class.getSimpleName());

    private Action navigateAction;




    public YANDEX() {
    }

    @Override
    public void navigate(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("navigate");
        navigateAction.act(driverWrapper, context, reporter);
    }

    public YANDEX fillCityField(String text, DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("fill City  field");
        logger.debug(this.getClass().getSimpleName() + " company text: " + text);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoadYandex"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new ClearAndType(By.cssSelector("input[class='mini-suggest-form__input mini-suggest__input']"),
                context.evaluateString(text)).act(driverWrapper, context, reporter);

        new WaitFor(ByHandler.getBy(context, "common.flagPageLoadYandex"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }


    public YANDEX clickRightCity(String cityName, DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.debug("сity: " + cityName);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoadYandex"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);

        HashMap<String, String> byParameters = new HashMap<>();
        byParameters.put("cityName", cityName);

        By invoiceCheckboxBy = ByHandler.getCompositeBy(context,
                "yandex.rightCity", byParameters);

        logger.info("click City row");
        new Click(invoiceCheckboxBy).act(driverWrapper, context, reporter);

        new WaitFor(ByHandler.getBy(context, "common.flagPageLoadYandex"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }

}
