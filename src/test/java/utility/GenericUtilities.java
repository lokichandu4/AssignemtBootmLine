package utility;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import runner.BaseClass;

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

}
