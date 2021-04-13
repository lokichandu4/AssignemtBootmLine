package step;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.testng.Assert;
import pages.ComposeMailPage;
import pages.ReadMailPage;
import runner.BaseClass;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;

public class ReadMailSteps extends BaseClass {
    static final Logger log = Logger.getLogger(ReadMailPage.class);
    @When("User check received email from sender using subject")
    public void userCheckReceivedEmailFromSenderUsingSubject() {
        try {
            int count =  ReadMailPage.getTotalCountOfMails(RECEIVER_APPLICATION_NAME);
            if(count==-1) Assert.fail("No email's found");
            log.info("=================");
            log.info("Total count of emails is :"+ count);
            Assert.assertTrue(ReadMailPage.isMailExist(Message_Subject));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("User prints message subject and message in log")
    public void userPrintsMessageSubjectAndMessageInLog() {
        try {
            HashMap<String, String> emailBody = ReadMailPage.getGmailData("subject:"+Message_Subject);//.getMailBody(Message_Subject);
            log.info("emailBody.get(\"subject\") "+emailBody.get("subject"));
            log.info("=================");
            log.info("emailBody.get(\"body\")"+emailBody.get("body"));
            log.info("=================");
            log.info("emailBody.get(\"from\")"+emailBody.get("from"));
            log.info("=================");
            log.info("emailBody.get(\"to\")"+emailBody.get("to"));
            log.info("=================");
            log.info("emailBody.get(\"date\")"+emailBody.get("date"));
            log.info("=================");
            log.info("emailBody.get(\"threadId\")"+emailBody.get("threadId"));
            log.info("=================");
            Assert.assertEquals(emailBody.get("from"),senderEmail);
            Assert.assertEquals(emailBody.get("to"),receiverEmail);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
