# Selenium Ezwim Tutorial - Jenkins Usage

## Job Creation

+ Login to [Jenkins](http://ezwim.ci.lan)
+ ![](https://image.ibb.co/cpKO4a/2017_07_11_13_35_23.png)
+ Home page
+ ![](https://image.ibb.co/hb8BqF/2017_07_11_13_36_29.png)
+ Ezwim page
+ ![](https://image.ibb.co/inHhxv/2017_07_11_13_37_16.png)
+ AutoTest page
+ ![](https://image.ibb.co/igGaHv/2017_07_11_13_37_48.png)
+ We choose stack which we needed
+ ![](https://image.ibb.co/cwjZAF/2017_07_11_13_40_26.png)
+ For job creation we copy some job name which will be template,
    for example it will be 'auotest_selenium_ezwim_template', 
    and click button 'Создать Item'
+ Then we type new job name in appropriate field
+ ![](https://image.ibb.co/gmgLja/2017_07_11_13_42_36.png)
+ Then we paste copied job name to field Copy from
+ ![](https://image.ibb.co/fcdGPa/2017_07_11_13_43_23.png)
+ Click button 'OK'
+ Job page will open automatically
+ Now we have new job, but it have the same configuration with job
    which we used as template, let's change it, click button 'Настройки'
+ ![](https://image.ibb.co/f9J2VF/2017_07_11_13_51_37.png)
+ We need redefine section - 'Сборка'
+ ![](https://image.ibb.co/czEHVF/2017_07_11_13_44_33.png)
+ One thing that must be changed is test location
+ ![](https://image.ibb.co/jNbxVF/2017_07_11_13_45_39.png)
+ You can copy it from test which you want to launch on Jenkins
+ Package name
+ ![](https://image.ibb.co/jwbEAF/2017_07_11_13_46_36.png)
+ Test class name
+ ![](https://image.ibb.co/dorQHv/2017_07_11_13_47_18.png)
+ Now let's change the test location
+ ![](https://image.ibb.co/nsKbqF/2017_07_11_13_49_32.png)
+ Click 'Сохранить' button

## Job Launching

+ Go to Job page
+ ![](https://image.ibb.co/f9J2VF/2017_07_11_13_51_37.png)
+ Click 'Собрать сейчас' button
+ You will see in left panel that build is processing
+ ![](https://image.ibb.co/nC9sVF/2017_07_11_13_52_10.png)
+ After successful job completion you will see blue marker
+ ![](https://image.ibb.co/dgdfHv/2017_07_11_13_55_46.png)

## Job Status checking

+ You can check tests in 'Последние результаты тестов'
+ ![](https://image.ibb.co/dgdfHv/2017_07_11_13_55_46.png)
+ Here you see that we have one test and it's successful
+ ![](https://image.ibb.co/dr3fHv/2017_07_11_13_58_44.png)
+ For checking log you can click by blue marker and you will see it
+ ![](https://image.ibb.co/jf21Pa/2017_07_11_13_54_58.png)
+ If test will be unsuccessful you will see something like this
+ ![](https://image.ibb.co/jWtQja/2017_07_11_13_59_24.png)
+ For getting message of failure you can open Error Details section
+ ![](https://image.ibb.co/bRdfHv/2017_07_11_14_00_00.png)
+ For getting stacktrace you can open Stack Trace section
+ ![](https://image.ibb.co/mxh1Pa/2017_07_11_14_00_56.png)
+ For getting only log of test you can open Standard Output section
+ ![](https://image.ibb.co/j2ymqF/2017_07_11_14_02_02.png)
