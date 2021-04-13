package utility;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import runner.BaseClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

public class GenericUtilities extends BaseClass {

    public static void intWaitForSecs(long sec) {
        try {
            Thread.sleep(sec);
        } catch (Exception ignored) {
        }
    }

    public static void waitForPageLoad(WebDriver pageDriver) {
        try {
            intWaitForSecs(8000);
        } catch (Exception ignored) { }
        Wait<WebDriver> wait = new WebDriverWait(pageDriver, ELEMENT_INTERACTION_TIMEOUT_SECS);
        wait.until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                //log.info("Current Window State : "+ String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")));
                return String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")).equals("complete");
            }
        });
    }

    public static void clickUsingJScript(WebDriver driverweb, WebElement element) {
        ((JavascriptExecutor) driverweb).executeScript("arguments[0].style.transform='scale(1)';", element);
    }

    public static void clickUsingAction(WebDriver driver, WebElement webElement) {
        try {
            WebElement scanEle = new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(webElement));
            Actions action = new Actions(driver);
            action.moveToElement(scanEle).click().build().perform();
        } catch (Exception e) {
            webElement.click();
        }
    }

    public static WebElement waitUntilElementPresent(By locator) {
        try {
            return new WebDriverWait(driver, ELEMENT_INTERACTION_TIMEOUT_SECS).until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            Assert.fail("Failed to verify element is displayed: " + locator);
            throw e;
        } catch (Exception ignored) {
            return null;
        }
    }

    public static WebElement waitUntilElementDisplayed(WebElement element) {
        try {
            return new WebDriverWait(driver, ELEMENT_INTERACTION_TIMEOUT_SECS).until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            Assert.fail("Failed to verify element is displayed: " + element);
            throw e;
        } catch (Exception ignored) {
            return null;
        }
    }

    public static boolean verifyListOfWebElementsDisplayed(WebDriver driver, WebElement... webElements) {
        for (WebElement element : webElements) {
            GenericUtilities.waitUntilElementDisplayed(element);
            if (!element.isDisplayed()) return false;
        }
        return true;
    }

    public static String currentDate(String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());
    }
}
