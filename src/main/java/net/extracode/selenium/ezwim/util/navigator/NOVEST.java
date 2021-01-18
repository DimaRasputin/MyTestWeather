package net.extracode.selenium.ezwim.util.navigator;

import net.extracode.selenium.common.*;
import net.extracode.selenium.core.Action;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.util.ByHandler;
import net.extracode.selenium.ezwim.util.Const;
import net.extracode.selenium.ezwim.util.ContextExpression;
import net.extracode.selenium.ezwim.util.navigator.eem.Login;
import net.extracode.selenium.ezwim.util.navigator.novest.SolveProblem;
import net.extracode.selenium.ezwim.util.navigator.novest.TestNovest;
import net.extracode.selenium.ezwim.util.navigator.novest.userInterface.UserInterface;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NOVEST implements Navigable {

    private static final Logger logger = LogManager.getLogger(NOVEST.class.getSimpleName());

    private Action navigateAction;
    private Login login;
    private SolveProblem solveProblem;
    private TestNovest testNovest;
    private UserInterface userInterface;



    public NOVEST() {
        login = new Login();
        solveProblem = new SolveProblem();
        testNovest = new TestNovest();
        userInterface = new UserInterface();



        navigateAction = new Action() {
            @Override
            public void act(DriverWrapper driver, Context context, Reporter reporter) {
                logger.info("navigate to eem url");
                new Navigate(ContextExpression.getVar(context, "eem.url"))
                        .act(driver, context, reporter);
                new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"),
                        Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);
                logger.info("fill credential fields");
                new ClearAndType(ByHandler.getBy(context, "eem.login.usernameField"),
                        ContextExpression.getVar(context, "eem.corpadmin.login"))
                        .act(driver, context, reporter);
                new ClearAndType(ByHandler.getBy(context, "eem.login.passwordField"),
                        ContextExpression.getVar(context, "eem.corpadmin.password"))
                        .act(driver, context, reporter);
                logger.info("click login button");
                new Click(ByHandler.getBy(context, "eem.login.loginButton"))
                        .act(driver, context, reporter);
                new WaitFor(ByHandler.getBy(context, "eem.logoutButton"),
                        Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driver, context, reporter);
            }
        };
    }

    @Override
    public void navigate(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("navigate");
        navigateAction.act(driverWrapper, context, reporter);
    }

    public NOVEST getURL() {
        return this;
    }

    public NOVEST getURL(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        login.navigate(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return this;
    }

    public UserInterface getUserInterface() {
        return userInterface;
    }

    public UserInterface getUserInterface(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        login.navigate(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return userInterface;
    }

    public NOVEST getLanguageChange(String text, DriverWrapper driverWrapper, Context context, Reporter reporter) {
        /**
         * @param delineter - Set the language type
         *                    where en - English
         *                      and de - German
         *                      and ru - Russian
         */
        logger.info("Click on language display button");

        new Click(ByHandler.getBy(context, "novest.language"))
                .act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new Pause("4000").act(driverWrapper, context, reporter);
        if (text.equals("en")) {
            logger.info("Set language - English");
            new Click(ByHandler.getBy(context, "novest.language.en"))
                    .act(driverWrapper, context, reporter);
        }
        if (text.equals("de")) {
            logger.info("Set language - German");
            new Click(ByHandler.getBy(context, "novest.language.de"))
                    .act(driverWrapper, context, reporter);
            new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                    Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        }
        if (text.equals("ru")) {
            logger.info("Set language - Russian");
            new Click(ByHandler.getBy(context, "novest.language.ru"))
                    .act(driverWrapper, context, reporter);
            new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                    Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        }
        return this;
    }

    public SolveProblem clickSolveTheProblem(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("click Solve  the Problem ");
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new Click(ByHandler.getBy(context, "novest.solveTheProblem")).act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return solveProblem;
    }

    public TestNovest clickTestNovest(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("click Test Novest.io ");
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new Click(ByHandler.getBy(context, "novest.testNovest")).act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return testNovest;
    }

}
