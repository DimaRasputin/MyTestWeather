package net.extracode.selenium.ezwim.util;

import net.extracode.selenium.factory.ByFactory;
import net.extracode.test.context.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import java.util.HashMap;

public class ByHandler {

    private static final Logger logger = LogManager.getLogger(ByHandler.class);

    public static By getBy(Context context, String key) {
        return ByFactory.createFromJson(context.getJsonBy(key));
    }

    public static By getCompositeBy(Context context, String key, HashMap<String, String> parameters) {
        String template = context.getJsonBy(key);
        String result = parseTemplate(template, parameters);
        if (template.equals(result)) {
            logger.warn("composite By template not been changed");
        }
        return ByFactory.createFromJson(result);
    }

    private static String parseTemplate(String template, HashMap<String, String> parameters) {
        for (String parameterKey : parameters.keySet()) {
            template = template.replaceAll("\\$\\{" + parameterKey + "\\}", parameters.get(parameterKey));
        }
        return template;
    }

    public static void main(String[] args) {
        String testJsonBy = "{\"type\":\"xpath\",\"value\":\"//test/test[@test='${parameter1}']/test2/test2[contains(@click, '${parameter2}')]\"}";
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("parameter1", "parameter1Value");
        parameters.put("parameter2", "parameter2Value");
        System.out.println(parseTemplate(testJsonBy, parameters));
    }
}
