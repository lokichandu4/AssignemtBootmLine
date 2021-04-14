package pages;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import com.google.api.client.util.Base64;
import org.apache.log4j.Logger;
import runner.BaseClass;

public class ComposeMailPage extends BaseClass {
    /*Created log object*/
    static final Logger log = Logger.getLogger(ComposeMailPage.class);

    /**
     * Create a message from an email.
     *
     * @param emailContent Email to be set to raw of message
     * @return a message containing a base64url encoded email
     * @throws IOException
     * @throws MessagingException
     */
    public static Message createMessageWithEmail(MimeMessage emailContent)
            throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }

    /*Created MimeMessage using the parameters provided.
     *
     * @param to email address of the receiver
     * @param from email address of the sender, the mailbox account
     * @param subject subject of the email
     * @param bodyText body text of the email
     * @return the MimeMessage to be used to send email
     * @throws MessagingException*/
    public static MimeMessage createEmail(String to, String from, String subject, String bodyText)
            throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }

    /**
     * Send an email from the user's mailbox to its recipient.
     *
     * @param : service Authorized Gmail API instance.
     * @param : userId User's email address. The special value "me"
     * can be used to indicate the authenticated user.
     * @param : emailContent Email to be sent.
     * @return The sent message
     * @throws MessagingException
     * @throws IOException
     */
    public static void sendEmail() throws IOException, GeneralSecurityException, MessagingException {
        Gmail service = BaseClass.getService(SENDER_CREDENTIALS_FILE_PATH,SENDER_TOKENS_DIRECTORY_PATH,SENDER_APPLICATION_NAME);
        MimeMessage Mimemessage = createEmail(receiverEmail,"me",Message_Subject,Message_Body);//"This my demo test subject","This is my body text"
        Message message = createMessageWithEmail(Mimemessage);
        message = service.users().messages().send("me", message).execute();
        threadId=message.getId();
        log.info("Message id: " + threadId);
        //log.info(message.toPrettyString());
    }

}
