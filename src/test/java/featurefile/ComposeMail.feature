Feature: ComposeMail

#Web Scenario using Selenium: Working,Sent mail Verified by Sender Name, Subject, Message Body, SentMail TimeStamp
  @ComposeMail
  Scenario:Verify that an email sent from one Gmail account can be received by another Gmail account Using
    Given User launches the browser and navigates to the Gmail Signin
    When Sender Email logged in using username and password
    Then home page should be displayed
    And User redirects to Gmail Application
    Then User Compose An Mail and Send to another Gmail
    And User Logouts from SenderMail
    When Receiver Email logged in using username and password
    Then home page should be displayed
    And User redirects to Gmail Application
    Then User verifies Email in Inbox
    And User Logouts from ReceiverMail


#Api Scenario using Restassured: Working, implemented using Gmail API
  @ComposeMail1
  Scenario:Verify that an email sent from one Gmail account can be received by another Gmail account
    Given User sends email using the predefined subject and message to sender email
    When User check received email from sender using subject
    Then User prints message subject and message in log