package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import runner.BaseClass;

import java.util.List;

public class SentPage extends BaseClass {
    static final Logger log = Logger.getLogger(InboxPage.class);

    @FindBy(xpath="//*[contains(text(),'Compose')]")
    private WebElement button_Compose;

    @FindBy(css="a[aria-label='Sent']")
    private WebElement link_Sent;

    @FindBy(css="button[aria-label='Clear search']")
    private WebElement button_ClearSearch;

    @FindBy(css="input[aria-label='Search mail']")
    private WebElement input_Searchmail;

    @FindBy(xpath="//table/tbody/tr[@role='row']")
    private List<WebElement> list_row;

    @FindBy(xpath="//table/tbody/tr[@role='row'][1]/td")
    private List<WebElement> list_col;

    @FindBy(css="h2")
    private List<WebElement> list_subject;

    @FindBy(xpath="//h3/span/span[contains(@email,'.com')]")
    private List<WebElement> label_senderEmail;

    @FindBy(xpath="//span[contains(text(),'ago')]")
    private List<WebElement> label_timeSent;

    @FindBy(xpath="//*[@class=\"hi\"]//preceding-sibling::div/div")
    private List<WebElement> text_messagebody;

    @FindBy(css="a[aria-label^='Google Account']")
    private WebElement link_profile;

    public SentPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

}
