package step;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.testng.Assert;
import pages.InboxPage;
import pages.LoginPage;
import runner.BaseClass;

public class InboxSteps extends BaseClass {
    @And("User redirects to Gmail Application")
    public void userRedirectsToGmailApplication() {
        new InboxPage(driver).verifyInboxPageLoaded();
    }

    @Then("User verifies Email in Inbox")
    public void userVerifiesEmailInInbox() {
        Assert.assertTrue(new InboxPage(driver).verifyEmailReceivedintheInbox());
    }
}
