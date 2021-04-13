package utility;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import runner.BaseClass;

import java.util.Collections;
import java.util.HashMap;

public class BrowserFactory extends BaseClass {
    public void launchBrowser(String browser){
        try{
            if(browser.equalsIgnoreCase("Chrome")){
                if (System.getProperty("os.name").toLowerCase().startsWith("linux")) {
                    System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
                } else if(System.getProperty("os.name").toLowerCase().startsWith("windows")){
                    System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH_WIN);
                } HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                //chromePrefs.put("profile.default_content_settings.popups", 0);
                //chromePrefs.put("credentials_enable_service", false);
                //chromePrefs.put("profile.password_manager_enabled", false);
                chromePrefs.put("disable-popup-blocking", "true");
                ChromeOptions options = new ChromeOptions();
                options.addArguments("start-maximized");
                options.addArguments("--incognito");
                options.setExperimentalOption("useAutomationExtension", false);
                options.setExperimentalOption("prefs", chromePrefs);

                //options.setExperimentalOption("credentials_enable_service", false);
                //options.setExperimentalOption("profile.password_manager_enabled", false);
                options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));//new String[]{"enable-automation"});
                options.setAcceptInsecureCerts(true);
                options.addArguments("--disable-web-security");
                options.addArguments("--allow-running-insecure-content");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-notifications");
                options.addArguments("disable-infobars");
                options.addArguments("--start-maximized");
                options.addArguments("--disable-save-password-bubble");
                options.addArguments("disable-popup-blocking", "true");
                driver = new ChromeDriver(options);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Browser launching failed");
        }


    }
}
