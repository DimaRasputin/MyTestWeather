# You can share in this file any specific actions or implementation of difficult tasks
#### Create company, calls, hierarchy etc from sql script
All sql files for creating company, calls etc can be found in package `net.extracode.selenium.ezwim.util.remote`.<BR>
Naming of these classes is following:<BR>
+ `CreateCompanySql`
+ `CreateCallsSql`
+ `CreateHierarchySql`
+ etc.

Java creates sql file on local machine, moves file on remote server and executes it in sqlplus.<BR>
Check test `CheckAssetsExtraAttrsAreShownInSubscriptionInventory_EEM_CR_01396` to see how `CreateCompanySql` and `CreateCallsSql` can be used.<BR>
Method `save()` is used to save all changes in file and upload file in db, so it should be put at the end of all actions.<BR>
Methods like `addCall()`, `addHierarchy()`, `addPolicy` are used to add several calls, hierarchies, policies in file.
<BR>
For example adding calls:<BR>
`.setDestination("us")`<BR>
`.setCost("100")`<BR>
`.setAbonent("01893467673")`<BR>
`.addCall`<BR>
`.setDestination("gb")`<BR>
`.setCost("500")`<BR>
`.addCall`<BR>
`.save()`<BR>
`addCall()` means all the lines before this are inserted in file. When you call second `addCall()` all the lines between first and second `addCall` are inserted in file.<BR>
Some parameters requires codes from db reference book. They can be found in ***egv*** project > directory *init* (or ask sql programmers!).<BR>
CreateCallsSql can create Overhead cost lines, for this you just need to set phone number equal to `OVERHEAD`.<BR>
For more details check the implemetation and comments in classes with this feature (CreateCompanySql.java, CreateCallsSql.java etc).

#### Upload users, assets by IDM
Example of IDM use you can find in test `CheckAssetsExtraAttrsAreShownInSubscriptionInventory_EEM_CR_01396`. Check methods `uploadUserInEem()` and `uploadAssetInEem()`.

#### Working with HTTP requests
It is possible to send simple POST and GET requests using following methods from net.extracode.selenium.common.HttpUtil class :<br>
`sendPost(String url, String contentType, String body)`<br>
`sendGet(String url)`

#### Working with remote server
There is package `net.extracode.selenium.ezwim.util.remote` which contains useful utilities such as getting server name where tests are run, host name of frontend server where tests are run, sftp actions like get and put file from/to frontend server.<br>
##### net.extracode.selenium.ezwim.util.remote.RemoteServerAct
`new RemoteServerAct().getServerName()` - gets server name, eg returns "staging" or "demo".<br>
`new RemoteServerAct().getHostName()` - gets frontend host name on which tests are run, eg returns "frontend-06.lan"<br>
`new RemoteServerAct().getFileOnServer()` - method which receives file from frontend host where tests are run by sftp.
`new RemoteServerAct().putFileOnServer()` - overloaded method which sends file on frontend host using sftp, with possibility to define destination dir or both source directory and destination directory.
 For example putFileOnServer(String userName, String remoteDirectory) or putFileOnServer(String userName, String pathToLocalFile, String remoteDirectory), where userName is user on remote host.<br>

#####net.extracode.selenium.ezwim.util.remote.SqlAct
SqlAct allows to execute specific SQL queries in sqlplus on server where tests are executed.<br>
`deleteCompany()` - deletes company<br>
`approvePeriod()` - approves period<br>
`autoShare()` - automatically shares reports by e-mail (SHARE function in EEM)<br>

#### Change password of users
It is possible to change password of EEM user (including switching off check on outdated password) using this method `new SqlAct().passwordActionsSql().changePasswordEEM(userName, userPassword, companyNumber, dbStaging, dbDemo);`<br>
For EGV users it is possible to reset date of last password change using `new SqlAct().passwordActionsSql().resetDateOfLastPasswordChangeEGV(moniker);`


#### Working with report content (html page)
##### Sort a column in table (same method is available for EEM in ReportActEEM() class)
new ReportActEGV().sortColumnDescEGV("Company",driver,context,reporter);<br />
new ReportActEGV().sortColumnAscEGV("Company",driver,context,reporter);<br />
where first parameter is column name<br />
##### Click Serch button in EEM reports (works for Standard and Advanced search)
new ReportActEEM().clickSearch(driver,context,reporter)
##### Get value of a field in EEM table (same method is available for EGV in ReportActEGV() class)
new ReportActEEM().getFieldValueTableByColumnName("Column name","1",driver,context,reporter);
#####Select period in EEM reports
 new ReportActEEM().selectPeriodByText("DATE",driver,context,reporter);

#### Working with reports (downloaded files)
##### Get value of excel cell by line and column number (will return String which can be saved in variable or used in Assert)
GetAct.getStringFromExcelFile(pathToDownload+fileName, sheetName,0,16);
##### Get value of csv cell by line and column number (will return String which can be saved in variable or used in Assert)
GetAct.getStringFromCSVFile(path+"AllActionsSynchronous.csv",CSVFormat.DEFAULT.withDelimiter(','),1,0));
##### Get number of lines in csv file
IOUtils.readLines(new FileInputStream(path+"AllActionsSynchronous.csv"),"UTF-8").size()

##### Working with zip
In most cases this method is enough. It returns content of file from zip:
`GetAct.getFileContentFromZip(getFileContentFromZip(pathToZipWithZipFilename,filenameInZip));` 
<BR><BR>
In some cases it can't be used. In this case you can use following methods:
`new ZipArchive().getNumberOfFiles(pathToZipWithZipFilename);`<BR>
`new ZipArchive().getfileName(pathToZipWithZipFilename); -- works only if there is 1 file in zip`<BR>
`new ZipArchive().getNamesOfFiles(pathToZipWithZipFilename); -- get file names in ArrayList when zip contains several files`<BR>
`new ZipArchive().zip(pathToContentFileWithFilename, zipFilePathWithZipFilename); --  pack file in ZipArchive`<BR>
`new ZipArchive().unzip(pathToZipWithZipFilename,unzipDir);`<BR>
e.g. pathToZipWithZipFilename can be like `/home/test/test.zip`, unzipDir can be like `/home/test/unzip/`.<BR>
