package runner;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import utility.BrowserFactory;
import utility.GenericUtilities;

public class BaseClass {
    public static final String APP_URL = "https://accounts.google.com/servicelogin";
    public static final String CHROME_DRIVER_PATH = System.getProperty("user.dir") + "/Executables/chromedriver";
    public static final String CHROME_DRIVER_PATH_WIN = System.getProperty("user.dir") + "/Executables/chromedriver.exe";
    //Global Variables
    public static WebDriver driver;
    public static WebDriver wait;
    public static GenericUtilities gu;
    public static JavascriptExecutor js;
    public static BrowserFactory refBrowserFactory;

    //Configuration options
    public static String BROWSER = "Chrome"; // set it to Firefox to run on Firefox
    protected static final int ELEMENT_INTERACTION_TIMEOUT_SECS = 60; //setting WebDriver wait to 2 minutes
}
