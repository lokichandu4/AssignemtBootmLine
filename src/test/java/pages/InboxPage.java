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

public class InboxPage extends BaseClass {
    static final Logger log = Logger.getLogger(InboxPage.class);

    @FindBy(css = "input[aria-label='Search mail']")
    private WebElement input_searchMail;

    @FindBy(css = "[data-tooltip=\"Inbox\"]")
    private WebElement link_inbox;

    @FindBy(css = "[data-tooltip^='Starred']")
    private WebElement link_Starred;

    @FindBy(css = "a[aria-label^='Snoozed']")
    private WebElement link_Snoozed;

    @FindBy(css = "a[aria-label^='Sent']")
    private WebElement link_Sent;

    @FindBy(css = "a[aria-label^='Drafts']")
    private WebElement link_Drafts;

    @FindBy(xpath = "//*[contains(text(),'Compose')]")
    private WebElement button_Compose;

    @FindBy(css = "a[aria-label^='Google Account']")
    private WebElement link_profile;

    @FindBy(xpath = "//table/tbody/tr[@role='row']")
    private List<WebElement> list_row;

    @FindBy(xpath = "//table/tbody/tr[@role='row'][1]/td")
    private List<WebElement> list_col;

    @FindBy(css = "h2")
    private List<WebElement> list_subject;

    @FindBy(xpath = "//h3/span/span[contains(@email,'.com')]")
    private List<WebElement> label_senderEmail;

    @FindBy(xpath = "//span[contains(text(),'ago')]")
    private List<WebElement> label_timeSent;

    @FindBy(xpath = "//*[@class=\"hi\"]//preceding-sibling::div/div")
    private List<WebElement> text_messagebody;

    public InboxPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /*
   Checks Inbox Page Displayed
  */
    public void verifyInboxPageLoaded() {
        GenericUtilities.intWaitForSecs(4000);
        Assert.assertTrue(GenericUtilities.verifyListOfWebElementsDisplayed(driver,input_searchMail,link_inbox,link_Starred,
                link_Snoozed,link_Sent,link_Drafts,button_Compose,link_profile ));
        Assert.assertEquals(input_searchMail.getAttribute("aria-label").trim(),"Search mail");
        Assert.assertTrue(list_row.size()>0);
    }

    /*
   Validate Sent Message Recieved in the inbox by iteration over Sender Name, Subject, Message Body, SentMail TimeStamp
  */
    public boolean verifyEmailReceivedintheInbox(){
        boolean flag = false;
        for(int i=1;i<=list_row.size();i++){
            List<WebElement> senderNameList = driver.findElements(By.xpath("//table/tbody/tr[@role='row']["+i+"]/td[4]/div/span/span"));
            log.info("Sender Name ==> "+GenericUtilities.checkMatchingTextPresentInList(driver,senderNameList,senderName));
            WebElement subjectAndMessageBody = driver.findElement(By.xpath("//table/tbody/tr[@role='row']["+i+"]/td[5]/div[@role='link']"));
            String subjectAndMessageBodyStr = subjectAndMessageBody.getText();
            log.info("Subject And MessageBody ==> "+subjectAndMessageBodyStr);
            WebElement sentTimeStampWeb = driver.findElement(By.xpath("//table/tbody/tr[@role='row']["+i+"]/td[8]/span"));
            String sentTimeStampStr= sentTimeStampWeb.getAttribute("aria-label");
            log.info("SentTimeStamp Text ==> "+sentTimeStampWeb.getText()+"  SentTimeStamp  ==> "+sentTimeStampStr);
            if(subjectAndMessageBodyStr.contains(Message_Subject) && subjectAndMessageBodyStr.contains(Message_Body)
                && sentTimeStampStr.equalsIgnoreCase(sentTimeStamp)) return  true;
        }
        return flag;
    }



}