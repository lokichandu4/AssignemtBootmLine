package pages;

import com.google.api.client.util.StringUtils;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.Thread;
//import com.jayway.jsonpath.DocumentContext;
//import com.jayway.jsonpath.JsonPath;
import io.restassured.path.json.JsonPath;
import org.apache.log4j.Logger;
import runner.BaseClass;
//import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

public class ReadMailPage extends BaseClass {
    static final Logger log = Logger.getLogger(ReadMailPage.class);

    public ReadMailPage (){
        try {
            Gmail service = BaseClass.getService(RECEIVER_CREDENTIALS_FILE_PATH, RECEIVER_TOKENS_DIRECTORY_PATH, RECEIVER_APPLICATION_NAME);
            log.info("service   "+ service);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
    /*Check Message exist from list of all Messages of the user's mailbox matching the query
        returns true if exist or false
     */
    public static boolean isMailExist(String messageTitle) {
        try {
            Gmail service = BaseClass.getService(RECEIVER_CREDENTIALS_FILE_PATH,RECEIVER_TOKENS_DIRECTORY_PATH,RECEIVER_APPLICATION_NAME);
            //ListMessagesResponse response = service.users().messages().list("me").setQ("subject:" + messageTitle).execute();
            //List<Message> messages = getMessages(response);
            List<Message> messages = listMessagesMatchingQuery(service, USER_ID, messageTitle);
            log.info("messages size == >"+messages+"  "+messages.size());
            return messages.size() != 0;
        } catch (Exception e) {
            log.info("Exception log" + e);
            return false;
        }
    }

    /*Get a Message and use it to create a MIME Message.
    Args:
        service: Authorized Gmail API service instance.
        user_id: User's email address. The special value "me"
        can be used to indicate the authenticated user.
        msg_id: The ID of the Message required.

    Returns: A MIME Message, consisting of data from Message.
        The format can vary there are many as full, "full": Returns the full email message data with body
        content parsed in the payload field; the raw field is not used. (default)
     */
    public static int getTotalCountOfMails(String RECEIVER_APPLICATION_NAME) {
        int size;
        try {
            Gmail service = BaseClass.getService(RECEIVER_CREDENTIALS_FILE_PATH,RECEIVER_TOKENS_DIRECTORY_PATH,RECEIVER_APPLICATION_NAME);
            List<Thread> threads = service.users().threads().list("me").execute().getThreads();
            size = threads.size();
        } catch (Exception e) {
            log.info("Exception log " + e);
            size = -1;
        }
        return size;
    }

    /*List all Messages of the user's mailbox matching the query.

        Args:
            service: Authorized Gmail API service instance.
            user_id: User's email address. The special value "me"
                can be used to indicate the authenticated user.
            query: String used to filter messages returned.
                Eg.- 'from:user@some_domain.com' for Messages from a particular sender.

        Returns: List of Messages that match the criteria of the query. Note that the
            returned list contains Message IDs, you must use get with the appropriate ID to get the details of a Message.
    */
    public static List<Message> listMessagesMatchingQuery(Gmail service, String userId, String query) throws IOException {
        ListMessagesResponse response = service.users().messages().list(userId).setQ(query).execute();
        List<Message> messages = new ArrayList<Message>();
        while (response.getMessages() != null) {
            messages.addAll(response.getMessages());
            if (response.getNextPageToken() != null) {
                String pageToken = response.getNextPageToken();
                response = service.users().messages().list(userId).setQ(query)
                        .setPageToken(pageToken).execute();
            } else {
                break;
            }
        }
        return messages;
    }

    public static Message getMessage(Gmail service, String userId, List<Message> messages, int index)
            throws IOException {
        Message message = service.users().messages().get(userId, messages.get(index).getId()).execute();
        //log.info("message "+message);
        return message;
    }

    /* List all Messages of the user's mailbox matching the query.

        Args:
            service: Authorized Gmail API service instance.
            user_id: User's email address. The special value "me"
                can be used to indicate the authenticated user.
            query: String used to filter messages returned.
                Eg.- 'from:user@some_domain.com' for Messages from a particular sender.

        Returns: Hash map contains threadId, From, To, Subject, Date and Body
    */
    public static HashMap<String, String> getGmailData(String query) {
        HashMap<String, String> hm = new HashMap<String, String>();
        try {
            Gmail service = getService(RECEIVER_CREDENTIALS_FILE_PATH,RECEIVER_TOKENS_DIRECTORY_PATH,RECEIVER_APPLICATION_NAME);
            List<Message> messages = listMessagesMatchingQuery(service, USER_ID, query);
            //log.info("messages size == >"+messages.size());
            for( int i =0; i <messages.size();i++) {
                Message message = getMessage(service, USER_ID, messages, i);
                JsonPath jp = new JsonPath(message.toString());
                /*DocumentContext context = JsonPath.parse(message.toString());
                List<Object> revenueList = context.read("$[*]['box office']");
                int length = context.read("$.payload.headers.length()");
                log.info(" length === > "+length);
                log.info("email ");
                String Subject = "",String name,String value;
                for (int i = 0; i < length; i++) {
                    name = context.read("$.payload.headers[" + i + "].name", String.class);
                    value = context.read("$.payload.headers[" + i + "].value", String.class);
                    if(name.equalsIgnoreCase("Subject")) {

                    }
                }*/
                String threadIdResponse = jp.getString("threadId");
                if(!threadIdResponse.equalsIgnoreCase(threadId)) continue;
                String subject = jp.getString("payload.headers.find { it.name == 'Subject' }.value");
                String from = jp.getString("payload.headers.find { it.name == 'From' }.value");
                String to = jp.getString("payload.headers.find { it.name == 'To' }.value");
                String date = jp.getString("payload.headers.find { it.name == 'Date' }.value");
                String body = jp.getString("snippet");
                //log.info("body === " + body);
                hm.put("subject", subject);
                hm.put("body", body);
                hm.put("from", from);
                hm.put("to", to);
                hm.put("date", date);
                hm.put("threadId", threadIdResponse);
                break;
            }
        } catch (Exception e) {
            log.info("email not found....");
            throw new RuntimeException(e);
        }
        return hm;
    }
}
