# Selenium Ezwim Tutorial - Lesson 2

+ Now we will learn what is action and how to use four most used actions - Navigate, Type, Click and WaitFor, also we will learn
    what is it - by.properties and staging.properties files, and for what they is needed
+ We will create a test for login to EGV, somebody made it already of course, 
    but we need to understand how it works, we will call it YetAnotherLoginToEGVTest
+ First thing you need to do it's determine steps and checks which will be done during test execution, so,
    login test, three steps and one check, let's do it manually:
    + Step 1 - Navigate our browser to url with login page
    + ![](https://image.ibb.co/dctJXv/2017_07_06_12_35_01.png)
    + Step 2 - Type username and password values to relevant fields
    + Step 3 - Click login button
    + ![](https://image.ibb.co/imOkCv/2017_07_06_12_35_23.png)
    + Check - Wait for logout button is exist on page
    + ![](https://image.ibb.co/m3HZ5F/2017_07_06_12_35_54.png)
+ Here we have three values which is permanent and we can add them to configuration, 
    values is - egv url, login, password.
    + Open staging.properties file
    + ![](https://image.ibb.co/ntbNsv/2017_07_06_12_39_22.png)
    + Add values with test name prefix and relevant keys
        + `YetAnotherLoginToEGVTest.egvUrl=someValue`
        + `YetAnotherLoginToEGVTest.egvUsername=someValue`
        + `YetAnotherLoginToEGVTest.egvPassword=someValue`
    + ![](https://image.ibb.co/fhKLea/2017_07_06_13_24_35.png)
+ Also we have four web elements which we need to put into By configuration, web elements - 
    username field, password field, login button, logout button
    + Open browser manually and make same steps as in test, when you doing it, use firebug or something like it
        for web element identification with id or xpath or another By type, xpath is preferable
        + NOTE than you need to make sure that your identification get only ONE element which you need
        + Login field identification data getting
        + ![](https://image.ibb.co/iyfAea/2017_07_06_13_17_16.png)
        + Password field identification data getting
        + ![](https://image.ibb.co/kx5MkF/2017_07_06_12_36_57.png)
        + Login button identification data getting
        + ![](https://image.ibb.co/b9OJXv/2017_07_06_12_37_15.png)
        + Logout button identification data getting
        + ![](https://image.ibb.co/iHARKa/2017_07_06_12_38_53.png)
        + NOTE than we use xpath expression with `contains(@onclick, 'LOGOUT')` function.
            It's necessary because this element doesn't have any id or specific class.
            When you will face with elements without id or specific classes you can use
            `contains()` function for any attributes of this html element
    + Open by.properties file
    + Add values with relevant keys
        + `egv.login.usernameField=someValue`
        + `egv.login.passwordField=someValue`
        + `egv.login.loginButton=someValue`
        + `egv.main.logoutButton=someValue`
    + ![](https://image.ibb.co/hHdfea/2017_07_06_13_21_55.png)
+ Ok, we know what we need to do and we added all data what we will use into configuration, also we put all web elements By
    to By configuration, now we will learn how use it in code
    + Some words about actions you need to know
        + Actions is Java classes with one main method - `act(DriverWrapper driver, Context context, Reporter reporter);`
        + Method act will execute this action, so if you just create action like this - `new Click(By.id("someId"));` then
            NOTHING gonna happen, you need to paste act method EVERYWHERE when you NEED to PERFORM action;
        + So, for perform action just create new and act it - `new Click(By.id("someId")).act(driver, context, reporter);`
    + First step is copy template (TemplateTest) of test with new name (YetAnotherLoginToEGVTest)
    + ![](https://image.ibb.co/gBb0ea/2017_07_06_12_28_49.png)
    + Adding to Git is not needed now, we just training, click No
    + ![](https://image.ibb.co/mXHOza/2017_07_06_12_30_18.png)
    + Delete template actions
    + ![](https://image.ibb.co/dO6Epa/2017_07_06_14_29_02.png)
    + Create variable which will be used and put relevant values from context (configuration values here):
        + `String url = (String) context.getVars().get("YetAnotherLoginToEGVTest.egvUrl");`
        + `String username = (String) context.getVars().get("YetAnotherLoginToEGVTest.egvUsername");`
        + `String password = (String) context.getVars().get("YetAnotherLoginToEGVTest.egvPassword");`
    + ![](https://image.ibb.co/fZJVvF/2017_07_06_14_40_04.png)
    + Translate our steps to actions string by string (we use ByHandler for getting By from context (configuration)):
        + Step 1 - `new Navigate(url).act(driver, context, reporter);`
        + Step 2 - `new Type(ByHandler.getBy(context, "egv.login.usernameField"), username).act(driver, context, reporter);`
        + Step 2 - `new Type(ByHandler.getBy(context, "egv.login.passwordField"), password).act(driver, context, reporter);`
        + Step 3 - `new Click(ByHandler.getBy(context, "egv.login.loginButton")).act(driver, context, reporter);`
        + Check - `new WaitFor(ByHandler.getBy(context, "egv.main.logoutButton"), "90").act(driver, context, reporter);`
    + ![](https://image.ibb.co/h27Upa/2017_07_06_14_41_48.png)
    + One important thing, ezwim system interface can be loaded slowly and before actions with elements, 
        we need to do check if page is completely loaded. 
        For this checking we have special element with id attribute - pageCompletelyLoaded, you can found it in by.properties,
        let's paste action of waiting for this element before our actions
        + Page completely loaded checking - `new WaitFor(ByHandler.getBy(context, "common.pageCompletelyLoaded"), "90").act(driver, context, reporter);`
    + ![](https://image.ibb.co/f6rdhv/2017_07_06_14_43_06.png)
    + For test launching you can use shortcut - Ctrl + Shift + F10 when you current file is what test you need (YetAnotherLoginToEGVTest in our case)
    + If you see green bar and word - passed, my congratulations, you have wrote your first autotest.
    + ![](https://image.ibb.co/dBD5ea/2017_07_06_13_08_52.png)
