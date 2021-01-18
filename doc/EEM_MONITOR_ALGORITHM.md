# EEM Monitoring test algorithm

## EXPECTED

+ Login to EEM
+ If Error occurs
    + Send email with information that system is failed
    + For 24 times (every 5 seconds for 2 minutes)
        + Login to EEM
        + If Error occurs
            + Continue
        + Else
            + Send email with information that system is back to stable
            + break test with successful code
        + Sleep 5 seconds
    + Finish test with unsuccessful code
+ Else 
    + Finish test with successful code
