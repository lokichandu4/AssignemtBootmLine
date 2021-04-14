package step;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.EncoderConfig;
import com.jayway.restassured.config.HttpClientConfig;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.response.Response;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.apache.http.params.CoreConnectionPNames;
import org.testng.Assert;
import pages.ComposeMailPage;
import pages.LoginPage;
import runner.BaseClass;
import utility.GenericUtilities;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import com.jayway.restassured.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.mail.MessagingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.HashMap;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class LoginSteps extends BaseClass {
    @Given("User launches the browser and navigates to the Gmail Signin")
    public void userNavigatesToTheWebsiteGmailSignin() {
        refBrowserFactory.launchBrowser(BROWSER);
        driver.manage().deleteAllCookies();
        driver.get(APP_URL);
        GenericUtilities.waitForPageLoad(driver);
        new LoginPage(driver).verifyHomePageLoaded();
    }

    @When("Sender Email logged in using username and password")
    public void userLoggedInUsingUsernameAndPassword() {
        new LoginPage(driver).fillEmailOrPhoneAndClickNext(senderEmail);
        GenericUtilities.waitForPageLoad(driver);
        Assert.assertTrue(new LoginPage(driver).verifyHiUserPageLoaded().equalsIgnoreCase(senderEmail));
        GenericUtilities.waitForPageLoad(driver);
        new LoginPage(driver).fillPasswordAndClickNext(senderEmailPassWord);
    }

    @Then("home page should be displayed")
    public void homePageShouldBeDisplayed() {
        GenericUtilities.waitForPageLoad(driver);
        new LoginPage(driver).verifyWelcomeUserPageLoaded();
    }


    @And("User Logouts from SenderMail")
    public void userLogoutsFromSenderMail() {
        new LoginPage(driver).userSignout();
    }

    @When("Receiver Email logged in using username and password")
    public void sendermailRemovedFromLoginPage() {
        new LoginPage(driver).addAnotherUser();
        new LoginPage(driver).fillEmailOrPhoneAndClickNext(receiverEmail);
        GenericUtilities.waitForPageLoad(driver);
        Assert.assertTrue(new LoginPage(driver).verifyHiUserPageLoaded().equalsIgnoreCase(receiverEmail));
        GenericUtilities.waitForPageLoad(driver);
        new LoginPage(driver).fillPasswordAndClickNext(receiverEmailPassWord);
    }

    @And("User Logouts from ReceiverMail")
    public void userLogoutsFromReceiverMail() {
        new LoginPage(driver).userSignout();
    }
}
