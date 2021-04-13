package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseClass;
import utility.GenericUtilities;

public class LoginPage extends BaseClass{

    static final Logger log = Logger.getLogger(ComposeMailPage.class);

    @FindBy(css="[id='identifierId']")
    private WebElement input_emailOrPhone;

    @FindBy(css="[id='profileIdentifier']")
    private WebElement text_profile;

    @FindBy(css="[id='password']")
    private WebElement input_password;


    By header_signIn = By.cssSelector("[id='headingText']");
    By button_next = By.xpath("//button/*[contains(text(),'Next')]");

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

    public void fillEmailOrPhoneAndClickNext(){
        input_emailOrPhone.sendKeys(userName);
        clickOnNext();
        log.info("Entered Email or Phone");
    }

    public String verifyHiUserPageLoaded(){
        WebElement signIn = GenericUtilities.waitUntilElementPresent(header_signIn);
        WebElement next = GenericUtilities.waitUntilElementPresent(button_next);
        Assert.assertTrue(GenericUtilities.verifyListOfWebElementsDisplayed(driver,signIn));
        Assert.assertEquals(signIn.getText().toLowerCase(),"Couldn't sign you in","Oauth 2.0 not stoping browser automation");
        Assert.assertTrue(GenericUtilities.verifyListOfWebElementsDisplayed(driver,text_profile,input_password,next));
        log.info("Enter Password page Loaded");
        return text_profile.getText().trim();
    }

    public void fillPasswordAndClickNext(){
        input_password.sendKeys(passWord);
        clickOnNext();
        log.info("Entered Password");
        try{ Thread.sleep(60000); }
        catch (Exception ignored) {}
    }
}
