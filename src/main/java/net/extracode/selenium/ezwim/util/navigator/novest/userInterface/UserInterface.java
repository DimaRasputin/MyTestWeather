package net.extracode.selenium.ezwim.util.navigator.novest.userInterface;

import net.extracode.selenium.common.ClearAndType;
import net.extracode.selenium.common.Click;
import net.extracode.selenium.common.WaitFor;
import net.extracode.selenium.driver.DriverWrapper;
import net.extracode.selenium.ezwim.util.ByHandler;
import net.extracode.selenium.ezwim.util.Const;
import net.extracode.selenium.ezwim.util.navigator.NOVEST;
import net.extracode.selenium.ezwim.util.navigator.Navigable;

import net.extracode.selenium.ezwim.util.navigator.novest.Register;
import net.extracode.selenium.ezwim.util.navigator.novest.userInterface.userInterfaceMenu.*;
import net.extracode.test.context.Context;
import net.extracode.test.reporter.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class UserInterface implements Navigable {

    private static final Logger logger = LogManager.getLogger(UserInterface.class.getSimpleName());

    private Register register;
    private Capabilities capabilities;
    private TryIt tryIt;
    private Optimum optimum;
    private Enterprise enterprise;
    private SuperPlus superPlus;
    private Gallery gallery;
    private Profile profile;



    public UserInterface() {
        register = new Register();
        capabilities = new Capabilities();
        tryIt = new TryIt();
        optimum = new Optimum();
        enterprise = new Enterprise();
        superPlus = new SuperPlus();
        gallery = new Gallery();
        profile = new Profile();

    }


    @Override
    public void navigate(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        logger.info("navigate");
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        new Click(ByHandler.getBy(context, "novest.solveTheProblem.login"))
                .act(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
    }

    public Capabilities getCapabilities() {
        return capabilities;
    }

    public Capabilities getCapabilities(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        capabilities.navigate(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return capabilities;
    }

    public TryIt getTryIt() {
        return tryIt;
    }

    public TryIt getTryIt(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        tryIt.navigate(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return tryIt;
    }

    public Optimum getOptimum() {
        return optimum;
    }

    public Optimum getOptimum(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        optimum.navigate(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return optimum;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public Enterprise getEnterprise(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        enterprise.navigate(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return enterprise;
    }

    public SuperPlus getSuperPlus() {
        return superPlus;
    }

    public SuperPlus getSuperPlus(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        superPlus.navigate(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return superPlus;
    }

    public Gallery getGallery() {
        return gallery;
    }

    public Gallery getGallery(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        gallery.navigate(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return gallery;
    }


    public Profile getProfile() {
        return profile;
    }

    public Profile getProfile(DriverWrapper driverWrapper, Context context, Reporter reporter) {
        profile.navigate(driverWrapper, context, reporter);
        new WaitFor(ByHandler.getBy(context, "common.flagPageLoad"),
                Const.PAGE_COMPLETELY_LOADED_TIMEOUT).act(driverWrapper, context, reporter);
        return profile;
    }
}