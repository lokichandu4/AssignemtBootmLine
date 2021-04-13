package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseClass;
import utility.GenericUtilities;

import java.util.Set;

public class LoginPage extends BaseClass{

    static final Logger log = Logger.getLogger(ComposeMailPage.class);

    @FindBy(css="[id='identifierId']")
    private WebElement input_emailOrPhone;

    @FindBy(css="[id='profileIdentifier']")
    private WebElement text_profile;

    @FindBy(css="[name='password']")
    private WebElement input_password;

    @FindBy(xpath="//h1[contains(text(),'Welcome')]")
    private WebElement label_welcome;

    @FindBy(css="input[aria-label='Search Google Account']")
    private WebElement input_search;

    @FindBy(css="a[aria-label='Google apps']")
    private WebElement label_googleApps;

    @FindBy(xpath="//a[@href='https://mail.google.com/mail/?tab=km']")
    private WebElement link_gmail;

    @FindBy(css="input[aria-label='Search mail']")
    private WebElement input_searchMail;

    @FindBy(css="[data-tooltip=\"Inbox\"]")
    private WebElement link_inbox;

    @FindBy(css="a[aria-label^='Google Account']")
    private WebElement link_profile;



    By header_signIn = By.cssSelector("[id='headingText']");
    By button_next = By.xpath("//button/*[contains(text(),'Next')]");


    //*[contains(text(),'Compose')][@role='button']



    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void verifyHomePageLoaded(){
        WebElement signIn = GenericUtilities.waitUntilElementPresent(header_signIn);
        WebElement next = GenericUtilities.waitUntilElementPresent(button_next);
        Assert.assertTrue(GenericUtilities.verifyListOfWebElementsDisplayed(driver,signIn,input_emailOrPhone,next));
        log.info("Home Page Loaded");
    }

    public void clickOnNext(){
        GenericUtilities.clickUsingAction(driver,GenericUtilities.waitUntilElementPresent(button_next));
    }

    /*
     Fill Email and click on Next Button
     */
    public void fillEmailOrPhoneAndClickNext(){
        input_emailOrPhone.sendKeys(userName);
        clickOnNext();
        log.info("Entered Email or Phone");
    }

    /*
    Waiting for password button if  Couldn't sign you in header found test fail's
     */
    public String verifyHiUserPageLoaded(){
        GenericUtilities.intWaitForSecs(4000);
        WebElement signIn = GenericUtilities.waitUntilElementPresent(header_signIn);
        WebElement next = GenericUtilities.waitUntilElementPresent(button_next);
        Assert.assertTrue(GenericUtilities.verifyListOfWebElementsDisplayed(driver,signIn));
        Assert.assertNotEquals(signIn.getText().toLowerCase(),"Couldn't sign you in","Oauth 2.0 not stoping browser automation");
        Assert.assertTrue(GenericUtilities.verifyListOfWebElementsDisplayed(driver,text_profile,input_password,next));
        log.info("Enter Password page Loaded");
        return text_profile.getText().trim();
    }

    /*
     Fill Password and click on Next Button
     */
    public void fillPasswordAndClickNext(){
        input_password.sendKeys(passWord);
        clickOnNext();
        log.info("Entered Password");
        try{ Thread.sleep(900); }
        catch (Exception ignored) {}
    }

    public void verifyWelcomeUserPageLoaded(){
        GenericUtilities.intWaitForSecs(4000);
        Assert.assertTrue(GenericUtilities.verifyListOfWebElementsDisplayed(driver,label_welcome,input_search,label_googleApps));
        /*GenericUtilities.clickUsingAction(driver,label_googleApps);*/
        /*if(!link_gmail.isDisplayed()) GenericUtilities.clickUsingAction(driver,label_googleApps);
        GenericUtilities.moveMouseToWebElementUsingJScript(driver,link_gmail);
        GenericUtilities.clickUsingAction(driver,link_gmail);*/
        String url = link_gmail.getAttribute("href");
        log.info("url ==== > "+url);
        driver.get("https://mail.google.com/mail/?tab=km");
        log.info("Clicked on gmail app");
        Assert.assertTrue(GenericUtilities.verifyListOfWebElementsDisplayed(driver,input_searchMail,link_inbox,link_profile));
        log.info("gmail page loaded");
    }
}
