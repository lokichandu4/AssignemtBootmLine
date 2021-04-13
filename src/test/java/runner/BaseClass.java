package runner;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.Thread;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.jayway.restassured.path.json.JsonPath;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import utility.BrowserFactory;
import utility.GenericUtilities;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class BaseClass {

    public static final String APP_URL = "https://accounts.google.com/signin";
    public static final String CHROME_DRIVER_PATH = System.getProperty("user.dir") + "/Executables/chromedriver";
    public static final String CHROME_DRIVER_PATH_WIN = System.getProperty("user.dir") + "/Executables/chromedriver.exe";
    //Global Variables
    public static WebDriver driver;
    public static WebDriver wait;
    public static GenericUtilities gu;
    public static JavascriptExecutor js;
    public static BrowserFactory refBrowserFactory;

    //Configuration options
    public static String BROWSER = "Chrome"; // set it to Firefox to run on Firefox
    protected static final int ELEMENT_INTERACTION_TIMEOUT_SECS = 60; //setting WebDriver wait to 2 minutes

    public static String userName ="aprilAutomationSendMail@gmail.com";
    public static String passWord ="PUYdlbO$XORY";
    protected static String senderEmail =  "aprilautomationsendmail@gmail.com";
    public static String senderEmailPassWord ="PUYdlbO$XORY";
    protected static String receiverEmail =  "aprilAutomationreceivemail@gmail.com";
    public static String receiverEmailPassWord ="PUYdlbO$XORY";
    protected static final String SENDER_APPLICATION_NAME = "Send_Desktop_Client";
    protected static final String RECEIVER_APPLICATION_NAME = "Receive_Desktop_Client";
    protected static final String APPLICATION_NAME = "Send_Desktop_Client";
    protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    protected static final String USER_ID = "me";
    protected static final String Message_Subject = "This my demo test subject";
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.MAIL_GOOGLE_COM);
    public static String threadId ="";
    protected static final String SENDER_CREDENTIALS_FILE_PATH = System.getProperty("user.dir") +
            File.separator + "src" +
            File.separator + "test" +
            File.separator + "resources" +
            File.separator + "credentials_sender" +
            File.separator + "credentials_sender.json";

    protected static final String RECEIVER_CREDENTIALS_FILE_PATH = System.getProperty("user.dir") +
            File.separator + "src" +
            File.separator + "test" +
            File.separator + "resources" +
            File.separator + "credentials_receiver" +
            File.separator + "credentials_receiver.json";

    protected static final String SENDER_TOKENS_DIRECTORY_PATH = System.getProperty("user.dir") +
            File.separator + "src" +
            File.separator + "test" +
            File.separator + "resources" +
            File.separator + "credentials_sender";

    protected static final String RECEIVER_TOKENS_DIRECTORY_PATH = System.getProperty("user.dir") +
            File.separator + "src" +
            File.separator + "test" +
            File.separator + "resources" +
            File.separator + "credentials_receiver";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT,String CREDENTIALS_FILE_PATH,String TOKENS_DIRECTORY_PATH) throws IOException {
        // Load client secrets.
        InputStream in = new FileInputStream(new File(CREDENTIALS_FILE_PATH));
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);//
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(9999).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static Gmail getService(String CREDENTIALS_FILE_PATH,String TOKENS_DIRECTORY_PATH,String APPLICATION_NAME) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT,CREDENTIALS_FILE_PATH,TOKENS_DIRECTORY_PATH))
                .setApplicationName(APPLICATION_NAME)
                .build();
        return service;
    }





}
