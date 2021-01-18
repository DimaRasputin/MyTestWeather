package net.extracode.selenium.ezwim.util;

import net.extracode.selenium.common.GetAttribute;
import net.extracode.selenium.common.GetSize;
import net.extracode.selenium.common.GetText;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class ConditionAct {

    private static final Logger logger = LogManager.getLogger(ConditionAct.class.getSimpleName());

    public static boolean elementExist(By by, DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("check element exist");
        String tempKey = ConditionAct.class.getSimpleName() + "elementCount";
        new GetSize(by, tempKey).act(driverWrapper, context, reporter);
        Integer elementCount = Integer.valueOf((String) context.getVars().get(tempKey));
        logger.debug("count: " + elementCount);
        return elementCount > 0;
    }

    public static boolean elementContainsText(By by, String text, DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("check element contains text");
        String tempKey = ConditionAct.class.getSimpleName() + "elementText";
        new GetText(by, tempKey).act(driverWrapper, context, reporter);
        String elementText = (String) context.getVars().get(tempKey);
        logger.debug("wanted text: " + text);
        logger.debug("element text: " + elementText);
        return elementText.contains(text);
    }

    public static boolean elementValueContainsText(By by, String text, DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("check element contains text");
        String tempKey = ConditionAct.class.getSimpleName() + "elementValue";
        new GetAttribute(by, tempKey, "value").act(driverWrapper, context, reporter);
        String elementValue = (String) context.getVars().get(tempKey);
        logger.debug("wanted text: " + text);
        logger.debug("element value: " + elementValue);
        return elementValue.contains(text);
    }
}
