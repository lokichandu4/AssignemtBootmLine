package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import runner.BaseClass;

public class ComposePage extends BaseClass {
    static final Logger log = Logger.getLogger(InboxPage.class);

    @FindBy(xpath="//*[contains(text(),'Compose')]")
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



    public ComposePage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }



}
