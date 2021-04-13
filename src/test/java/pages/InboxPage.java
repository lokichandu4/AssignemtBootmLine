package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import runner.BaseClass;

public class InboxPage extends BaseClass {
    static final Logger log = Logger.getLogger(InboxPage.class);

    @FindBy(css="input[aria-label='Search mail']")
    private WebElement input_searchMail;

    @FindBy(css="[data-tooltip=\"Inbox\"]")
    private WebElement link_inbox;

    @FindBy(css="[data-tooltip='Starred']")
    private WebElement link_Starred;

    @FindBy(css="a[aria-label='Snoozed']")
    private WebElement link_Snoozed;

    @FindBy(css="a[aria-label='Sent']")
    private WebElement link_Sent;

    @FindBy(css="a[aria-label='Drafts']")
    private WebElement link_Drafts;

    @FindBy(xpath="//*[contains(text(),'Compose')]")
    private WebElement button_Compose;

    @FindBy(css="a[aria-label^='Google Account']")
    private WebElement link_profile;

    public InboxPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }
}
