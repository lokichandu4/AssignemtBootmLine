package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import runner.BaseClass;
import utility.GenericUtilities;
import java.util.List;

public class LoginPage extends BaseClass{

    static final Logger log = Logger.getLogger(ComposeMailPage.class);



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

    @FindBy(css="a[href^='https://mail.google.com/mail/']")
    private WebElement link_gmail;

    @FindBy(css="input[aria-label='Search mail']")
    private WebElement input_searchMail;

    @FindBy(css="[data-tooltip=\"Inbox\"]")
    private WebElement link_inbox;

    @FindBy(css="a[aria-label^='Google Account']")
    private WebElement link_profile;

    @FindBy(xpath="//div[contains(text(),'Remove an account')]")
    private WebElement button_RemoveAnAccount;

    @FindBy(css="[id=\"profileIdentifier\"]")
    private WebElement text_emailAddress;


    By header_signIn = By.cssSelector("[id='headingText']");
    By input_emailOrPhone = By.cssSelector("[id='identifierId']");
    By button_useAnotherAccount = By.xpath("//div[contains(text(),'Use another account')]");
    By button_next = By.xpath("//button/*[contains(text(),'Next')]");
    By button_signout  = By.xpath("//a[contains(text(),'Sign out')]");

    //*[contains(text(),'Compose')][@role='button']



    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void verifyHomePageLoaded(){
        WebElement signIn = GenericUtilities.waitUntilElementPresent(header_signIn);
        WebElement emailOrPhone = GenericUtilities.waitUntilElementPresent(input_emailOrPhone);
        WebElement next = GenericUtilities.waitUntilElementPresent(button_next);
        Assert.assertTrue(GenericUtilities.verifyListOfWebElementsDisplayed(driver,signIn,emailOrPhone,next));
        log.info("Home Page Loaded");
    }

    public void clickOnNext(){
        GenericUtilities.clickUsingAction(driver,GenericUtilities.waitUntilElementPresent(button_next));
    }

    /*
     Fill Email and click on Next Button
     */
    public void fillEmailOrPhoneAndClickNext(String email){
        //GenericUtilities.intWaitForSecs(4000);
        WebElement emailOrPhone = GenericUtilities.waitUntilElementPresent(input_emailOrPhone);
        GenericUtilities.waitUntilElementDisplayed(emailOrPhone).sendKeys(email);
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
    public void fillPasswordAndClickNext(String passWord){
        input_password.sendKeys(passWord);
        clickOnNext();
        log.info("Entered Password");
        try{ Thread.sleep(900); }
        catch (Exception ignored) {}
    }

    /*
    Checks if Welcome User Page loaded and redirect Gmail Application
    */
    public void verifyWelcomeUserPageLoaded(){
        GenericUtilities.intWaitForSecs(4000);
        if(!driver.getCurrentUrl().contains("https://mail.google.com/mail/u/0/#inbox")) {
            log.info("Current Page url ="+driver.getCurrentUrl());
            Assert.assertTrue(GenericUtilities.verifyListOfWebElementsDisplayed(driver, label_welcome, input_search, label_googleApps));
            GenericUtilities.clickUsingAction(driver, label_googleApps);
            driver.get("https://mail.google.com/mail/?tab=km");
            log.info("Clicked on gmail app");
        } Assert.assertTrue(GenericUtilities.verifyListOfWebElementsDisplayed(driver,input_searchMail,link_inbox,link_profile));
        log.info("gmail page loaded");
    }

    /*
    Signout the user from Gmail Application
    */
    public void userSignout() {
        GenericUtilities.waitUntilElementIsClickable(driver, link_profile);
        link_profile.click();
        GenericUtilities.intWaitForSecs(2000);
        int retry =3;
        while(retry>=3){
            log.info("inside logout"+retry);
            List<WebElement> btn_accountLogout = driver.findElements(button_signout);
            if(btn_accountLogout.size()>0){
                btn_accountLogout.get(0).click(); break;
            } else {
                link_profile.click();
                GenericUtilities.intWaitForSecs(2000);
            } retry--;
        }
        WebElement signIn  = GenericUtilities.waitUntilElementPresent(header_signIn);
        Assert.assertTrue(GenericUtilities.verifyListOfWebElementsDisplayed(driver,signIn));
    }

    /*
    Checks Add Another User, If present continue as Another User
    */
    public void addAnotherUser(){
        WebElement signIn  = GenericUtilities.waitUntilElementPresent(header_signIn);
        List<WebElement> useAnotherAccount = driver.findElements(button_useAnotherAccount);
        if(useAnotherAccount.size()>0) {
            WebElement useAnotherAccountBtn = GenericUtilities.waitUntilElementPresent(button_useAnotherAccount);
            Assert.assertTrue(GenericUtilities.verifyListOfWebElementsDisplayed(driver, signIn, useAnotherAccountBtn, button_RemoveAnAccount, text_emailAddress));
            Assert.assertEquals(signIn.getText().trim(), "Choose an account");
            Assert.assertTrue(text_emailAddress.getText().trim().equalsIgnoreCase(senderEmail));
            useAnotherAccountBtn.click();
        }
        log.info("Clicked on Use Another Account");
    }

}
