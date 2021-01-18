# Git branching rules

## Types of branches

+ `master` - stable branch for launching on Jenkins server
+ `develop` - current complete tasks
+ `feature-<Ticket number or name of test>` - special branch for certain test
+ `fix-<Ticket number or name of trouble>` - special branch for certain fix

### Branch name

+ Replace all spaces to `_` symbol
    + `Selenium 000001` will be `Selenium_000001`


## Roles and rights

+ Developer
    + Create `feature-` and `fix-` branches from `develop`
    + Merge `feature-` and `fix-` branches to `develop`
+ Release Manager
    + Merge `develop` to `master`

## Algorithms

+ Do new task
    + Make sure that you don't have not commited changes
        + Run `git status`
        + Message must contains `nothing to commit, working directory clean`
    + Checkout to develop branch
        + Run `git checkout develop`
    + Pull latest changes from remote repository
        + Run `git pull`
    + Checkout to new branch
        + Run `git checkout -b feature-<Ticket number or name of test>`
        + Or
        + Run `git checkout -b fix-<Ticket number or name of trouble>`
    + Start working
    + Work cycle
        + Add changes
            + Run `git add .`
        + Commit changes
            + Run `git commit -m "meningful message"`
        + Push new branch to remote server first time or
            + Run `git push -u origin <branchName>`
        + Push branch changes to remote server all next times
            + Run `git push`
    + After work completion merge branch to develop
        + Make sure that you don't have not commited changes
            + Run `git status`
            + Message must contains `nothing to commit, working directory clean`
        + Run `git checkout develop`
        + Run `git pull`
        + Run `git merge --no-ff <yourBranch>`
    + Push develop branch changes to remote server
        + Run `git push`
    + Delete task branch from remote server
        + Run `git push --delete origin <yourBranch>`
