Feature: ComposeMail

#Web Scenario using Selenium: Failing due Oauth2.0 from google is not allowing browser automation
  @ComposeMail
  Scenario:Verify that an email sent from one Gmail account can be received by another Gmail account Using
    Given User launches the browser and navigates to the Gmail Signin
    When User logged in using username and password
    Then home page should be displayed

#Api Scenario using Restassured: Working, implemented using Gmail API
  @ComposeMail1
  Scenario:Verify that an email sent from one Gmail account can be received by another Gmail account
    Given User sends email using the predefined subject and message to sender email
    When User check received email from sender using subject
    Then User prints message subject and message in log