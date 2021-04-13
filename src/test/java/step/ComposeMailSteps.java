package step;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import pages.ComposeMailPage;
import pages.ReadMailPage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class ComposeMailSteps {

    @Then("home page should be displayed")
    public void homePageShouldBeDisplayed() {

    }

    @Given("User sends email using the predefined subject and message to sender email")
    public void userSendsEmailUsingThePredefinedSubjectAndMessageToSenderEmail() {
        try {
            new ComposeMailPage().sendEmail();
        } catch (IOException | GeneralSecurityException | MessagingException e) {
            e.printStackTrace();
        }
    }




}

