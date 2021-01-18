package net.extracode.selenium.ezwim.util;

import net.extracode.test.context.Context;

public class ContextExpression {
    public static String wrap(String expression) {
        return "${" + expression + "}";
    }

    public static String getVar(Context context, String key) {
        return context.evaluateString(wrap(key));
    }
}
