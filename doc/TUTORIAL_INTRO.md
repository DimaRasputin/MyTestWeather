# Selenium Ezwim Tutorial Introduction

+ Hi there! It's tutorial for selenium-ezwim autotest library using
+ You need to have installed environment (instruction on project repository site)
+ So, let's go. This library will be used for Ezwim system autotesting
+ You need to be familiar with Java and Selenium basics, please read about it before you start
+ So, you have installed environment and opened selenium-ezwim project in Intellij IDEA
+ Now we gonna figure out what we can use for autotest creation
+ First of all we need to know which objects take part of test actions, here they are:
    + DriverWrapper - object for browser control and for our communications with it;
    + Context - object for information storing and passing it between actions;
        + In context we will store configuration and data which needed while testing
        + Before test creation you need to know which data will be used and if them 
            is permanent then you need to add that data into configuration file by key 
            which will be understandable and say what exactly value it is
        + Configuration file is located in src/test/resources folder, now it's called
            staging.properties, if it will be necessary we will create new file for other stack or
            tests group
        + For getting value from configuration file you will use next call:
            + `context.getVars().get("veryUnderstandableKey");`
    + Reporter - it's object which not used yet, in future, if we will need some specific reports, we
        will use it
+ Next step we will know about some additional objects which also important, here they are:
    + By - Selenium object for web elements determination which we need
        + By can be different for example:
            + `By.id("div")` - this By help us find all web elements on page with id = 'div'
            + `By.xpath("//div[id='anotherDiv']")` - this By help us find all web elements on
                page which are div and have id = 'anotherDiv'
    + For operations with All types of By we can use ByHandler and config file where these Bys
        will be storing
        + For this we need to do next things
            + Add to configuration file by key which will say what exactly this element is, 
                for example:
                + I think this key more clear: `egv.customers.users.companyField` than
                    `By.xpath("//*[id='a_company']")` are you agree?
            + Format for storing By in configuration file is json object, example here:
                + `{"type":"id","value":"webElementId"}`
            + Complete row in configuration file:
                + `egv.customers.users.companyField={"type":"id","value":"a_company"}`
            + Configuration file for Bys is located in src/test/resources folder and it's called
                by.properties
            + Call ByHandler.getBy() method with Context object:
                `ByHandler.getBy(context, "egv.customers.users.companyField");`
