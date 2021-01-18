# Rules of code writing

In General you should follow general Java code conventions mostly, but with some differences.
## Style

+ Code should be readable. Lines should be short (max 100-120 characters).<BR>
Use line wrapping if line contains more than 120 characters.
+ Add description of test steps in comments.
+ Comment your code as much as possible (especially if you add new complex code).
+ Create variables in the head of your your test or before the method in which it wil be used.
+ Use variables in method's parameters.
    + Such using is not suitable:<BR> `fillLoginName("Autotest_EGVUserLevelReseller",driver,context,reporter);`<BR>
    Instead of this create variable and use it in all further actions:<BR>
`String loginName = "Autotest_EGVUserLevelReseller";`<BR>
`fillLoginName(loginName,driver,context,reporter);`
+ `test()` method should contain clear actions.<BR>
Big logical parts of code (especially if it is not Navigator) should be moved to methods at the end of your class after method `after()`.<BR>
For example in test `CheckAssetsExtraAttrsAreShownInSubscriptionInventory_EEM_CR_01396` were created separate methods
`uploadUserInEem()`,`uploadAssetInEem()`,`clickEditColumnsAndAddExtraAttrsColumns()`,
`checkTableContainsExpectedValues()`. As result test became clearer and shorter.

## Naming
+ Name of test (java package and its class) should contain short description and ticket number
    + Example: `CheckDownloadingFlexByEgvUser_Selenium_00500` or `CheckSortingInReportAllCalls_EEM_BR_01489`.
+ Names of classes, methods, variables:<BR>
    + Names of classes should start from capital. If the name consist from several words then all the words should start from capital.<BR>
    Example: `public class SystemMonitoring{}`
    + Classes names convention
      + Get... - if you want to get something
      + Set... - if you want to set something
      + Login... - if you want to log in somewhere
      + Create... - if you want to create something
    + First letter in name of methods and variables should be lower case. If the name consist from several words then all the next words should start from capital.<BR>
     Names of methods should reflect actions.<BR>
    Example:<BR>
    `private void getCompanyNumber(){}`<BR>
    `String companyName;`
+ Names of companies should contain number of ticket and word autotest/autotesting/automated testing.
    + For example `Autotest Selenium-00472` or `Autotest Check rights Selenium-00500`.
    
## Use of Xpath
+ Xpathes should not be long and should contain id or any other unique attributes.
    + Example:<BR>This Xpath is too long `//table[@id='ee-top-table']/tbody/tr/td/table/tbody/tr/td/span[@id='result_per_page']`.<BR>
    Instead it use short version `//table[@id='ee-top-table']//span[@id='result_per_page']`.