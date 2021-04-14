package step;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import pages.*;
import runner.BaseClass;
import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class ComposeMailSteps extends BaseClass {

    @Given("User sends email using the predefined subject and message to sender email")
    public void userSendsEmailUsingThePredefinedSubjectAndMessageToSenderEmail() {
        try {
             ComposeMailPage.sendEmail();
        } catch (IOException | GeneralSecurityException | MessagingException e) {
            e.printStackTrace();
        }
    }

    @Then("User Compose An Mail and Send to another Gmail")
    public void userComposeMailAndSendToAnotherGmail() {
        new ComposePage(driver).clickOnComposeMail();
        new ComposePage(driver).verifyComposeBoxDisplayed();
        new ComposePage(driver).createMailAndSend();
    }
}

