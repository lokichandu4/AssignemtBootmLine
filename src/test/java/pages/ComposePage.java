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

public class ComposePage extends BaseClass {
    static final Logger log = Logger.getLogger(InboxPage.class);

    @FindBy(xpath="//div[contains(text(),'Compose')][@role='button']")
    private WebElement button_Compose;

    @FindBy(xpath="//*[contains(text(),'New Message')]")
    private WebElement text_NewMessage;

    @FindBy(css="[role='dialog'] [alt=\"Minimize\"]")
    private WebElement image_Minimize;

    @FindBy(css="[role='dialog'] [alt=\"Pop-out\"]")
    private WebElement image_Popout;

    @FindBy(css="[role='dialog'] [alt=\"Close\"]")
    private WebElement image_Close;

    @FindBy(css="textarea[name='to']")
    private WebElement inbox_to;

    @FindBy(css="input[placeholder='Subject']")
    private WebElement inbox_Subject;

    @FindBy(css="[aria-label=\"Message Body\"][role='textbox']")
    private WebElement inbox_MessageBody;

    @FindBy(css="[aria-label^=\"Send\"][role='button']")
    private WebElement button_Send;

    @FindBy(css="a[aria-label^='Google Account']")
    private WebElement link_profile;

    By msgAlert = By.cssSelector("//*[contains(text(),'Message sent.')]");

    public ComposePage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void clickOnComposeMail(){
        GenericUtilities.waitUntilElementIsClickable(driver, button_Compose);
        button_Compose.click();
        log.info("clicked On Compose Mail");
    }

    /*
    Checks Compose Mail Box Displayed
   */
    public void verifyComposeBoxDisplayed() {
        GenericUtilities.intWaitForSecs(4000);
        Assert.assertTrue(GenericUtilities.verifyListOfWebElementsDisplayed(driver,text_NewMessage,image_Minimize,
                image_Popout,image_Close));
        log.info("New Message pop-up displayed");
    }

    /*
    Compose the mail, Send and Record Current Time Stamp
   */
    public void createMailAndSend() {
        inbox_to.sendKeys(receiverEmail);
        inbox_Subject.sendKeys(Message_Subject);
        inbox_MessageBody.sendKeys(Message_Body);
        GenericUtilities.clickUsingAction(driver,button_Send);
        sentTimeStamp = GenericUtilities.currentDate("EEE, MMM dd, yyyy, h:mm a");
        GenericUtilities.intWaitForSecs(4000);
        log.info("Mail Sent..."+sentTimeStamp);
    }

}
