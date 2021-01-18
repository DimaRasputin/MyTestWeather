#!/usr/bin/env groovy
@Grab('org.seleniumhq.selenium:selenium-java:2.53.0')

import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.JavascriptExecutor

System.setProperty('webdriver.firefox.bin', '/home/wls/dev/firefox/firefox')

WebDriver driver = new FirefoxDriver()

try {
    driver.get 'http://stackoverflow.com'
    println 'done: ' + driver.title
    def element = driver.findElement(By.xpath("//input[@name='q']"))
    element.sendKeys("Test")
    println 'value: ' + element.getAttribute("value")
    def shadow = ((JavascriptExecutor)driver).executeScript("return arguments[0].shadowRoot", element)
    println 'shadow value: ' + shadow.getAttribute("value")
} finally{
    driver.close()
}
