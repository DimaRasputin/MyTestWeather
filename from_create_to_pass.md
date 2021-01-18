
# From Create to Push


# Steps:



## Create new test:

 **You must create local branch for test:**


<br>![enter image description here](https://preview.ibb.co/k5Y6nU/Branch_Selector.png)
<br>Click on New Branch
<br>![enter image description here](https://preview.ibb.co/fKqrMp/new_Local_Branch.png)
<br>Set name of local branch
<br>![enter image description here](https://image.ibb.co/fLzqE9/Branch_Name.png)

**You must create your test in YOUR directory**
like this :
> ***C:\Autotest\selenium-ezwim\src\test\java\net\extracode\selenium\testers\”NAME”***

**The name of the test should describe its steps and the ticket number (briefly)**
like this:
>***CreateCompanyOnDB101_Selenium_00001***

**Before "@Test" there should be commented out steps in javadoc format
like this**:

    /**
     * Steps:
     * Log in as root admin 00447
     * Create user Username
     * send reg. mail
     * Try login
     * Check that login success without acceptance screen
     * Open company card activate acceptance screen
     * Create new user
     * send reg. mail
     * Try login
     * Check that login success with acceptance screen
     */
    @Test

## Commit your changes

**When you finish you test you must commit your changes:**

<br>You can commit directory or file
<br>- directory:
<br>![enter image description here](https://preview.ibb.co/ihBFgp/Directory_Commit.png)
<br>- file:
<br>![enter image description here](https://preview.ibb.co/ew6RnU/File_Commit.png)

**Select files that must be committed, fill commit message and click Commit**
<br>![enter image description here](https://preview.ibb.co/iAtHu9/Commit.png)


## Make  pull in your local branch

**After commit you must make pull**
<br>![enter image description here](https://preview.ibb.co/i1nSu9/GITPull.png)
<br>Select “develop" and click Pull button
<br>![enter image description here](https://preview.ibb.co/mQewnU/select_Remote_Branch_For_Pull.png)

**Make push to remote branch**
<br>![enter image description here](https://preview.ibb.co/me3p7U/GITPush.png)
>Check that path is YourLocalBrachName->origin:YourLocalBrachName
<br>![enter image description here](https://preview.ibb.co/kxOp7U/Push_In_Branch.png)
<br>Click push

## Create merge request for your remote branch with develop

Go to GIT: https://gitlab1.ezwim.cyso.net/Ezwim/autotest/selenium/selenium-ezwim
<br> 	Go to Merge Requests tab
<br>	Click “New merge request” button
<br>![enter image description here](https://preview.ibb.co/hBPU7U/Git_Create_Merge.png)
<br>on left side select your remote branch  - on right side select “develop” branch
<br>Click “Compare branches and continue”
<br>![enter image description here](https://preview.ibb.co/cyuqE9/Branches_For_Merge.png)

## Merge


<br>![enter image description here](https://image.ibb.co/bEyd8p/merge.png)

<br>!Check that you select exacly develop branch!
<br>!Check that count of your files is mach files in "Changes" tab

<br>![enter image description here](https://image.ibb.co/jrreyp/Final-merge.png)

<br>!Click green button "Merge"

## Finish

<br>Find you test in your directory
<br>Get url to your test.java
<br>![enter image description here](https://image.ibb.co/n0E7Jp/final1.png)
<br>Fill all colum in Doc
<br>https://docs.google.com/spreadsheets/d/1hM6FKciKm0HuUE7_aeSfiFrqdjH0yteTk7xH4zSEkW4/edit#gid=466468775
<br>![enter image description here](https://image.ibb.co/m2Nuyp/final2.png)
<br>Add in ticket Steps, URL to DOC and URL to your test.java on git(develop branch)
<br>Pass the ticket
<br>![enter image description here](https://image.ibb.co/fLYdPU/final3.png)
