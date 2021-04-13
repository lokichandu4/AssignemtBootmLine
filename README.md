## Setup Instructions
1. Extract this repository into local system
2. Download and install Eclipse(Oxygen is preferred)/IntelliJ Community Edition
3. Launch Eclipse/IntelliJ CE and navigate to this project import it as a maven project.
4. Maven should automatically begin resolving dependencies, this may take sometime.
5. Once complete, the project should be able to compile.
6. Click Alt+F5 and update the maven dependencies to resolve the errorrs if there are any
7. Open command prompt from the base folder of this project and run mvn compile, you should see
'BUILD SUCCESS' in console. If you see build failed then Maven did not successfully resolve
all dependencies.
​
### Install Plugins Intellij
1. Click IntelliJ IDEA -> Preferences from the File menu.
2. Click Plugins and then click the Marketplace tab.
3. Search for and install 'Cucumber for Java' and 'Gherkin'. Restart the IDE

### Install Plugins Eclipse
1. Click Help -->Eclipse MarketPlace
2. Search for Cucumber Eclipse plugin, TestNg plugin and install. Restart the IDE
​
### About this Framework
This framework employs a Cucumber/Gherkin/BDD structure. The Cucumber Official Website provides
[documentation and tutorials.](https://docs.cucumber.io/guides/) Give the Introduction and 10 minute tutorial
a skim to understand the general structure.
​
### Run a Test
1. Add a unique tag to the test cases you want to run e.g., `@RunThisTestNow`
2. Open src/test/java/runner/TestRunner
3. replace the tag   `
4. Click the green play icon to the test runner class
​
### Run Several Tests
1. Expand /src/test/java/runner
2. Double click TestRunner to open it
3. Change Tag property in the CucumberOptions of TestRunner to select a group of Tests to execute.
4. Click the Play arrows next to the TestRunner class name.

### Run a Test through mvn command
1. Add a unique tag to the test cases you want to run e.g., `@RunThisTestNow`
2. Open command prompt and navigate to the project base folder
3. Run command "mvn clean"`
4. Run command "mvn verify"
5. Right click and refresh the project
6. Open the file present at "/Lokesh_Kumar_SIXTAssignment/MavenReports/CucumberReports/cucumber-html-reports/overview-features.html" with System Editor to view the results

##Cross browser testing
*Please set the Browser variable in BaseClass to chrome or firefox based on the requirement
​
## Automation Coding Practices
### 1. Page Objects
A new "Page Object" should be created for anywhere in the webapp that is effectively a new page.
​
A Page Object should contain the following:
* Test Hooks
* Methods that interact with that page by clicking elements and getting text
* Methods that perform validations by comparing actual page values to expected values
* Small & compact methods with easy to follow logic.
* Organization - method with similar outcomes should be categorized accordingly. The use of Regions can assist in
performing such organization.
​
A Page Object should not contain the following:
* Methods with similar or matching logic as existing methods.
* Methods that are Generic and reusable by other pages - these should be moved to Generic Utilities only if methods with
similar or matching logic do not already exist.
* Hard-coded copy text inside methods used for validations, these should be declared at the top of the Page Object so they
can be easily identified and updated.
​
### 2. Step Definitions
A Step Definition file can be Feature-wide and use more than one Page Object. 
A Step Definition File should contain the following:
* Page Objects instantiated in every step instead of globally.
* TestNG Assertions containing useful failure messages.
* Implementation of a Content Management Class for String comparision in Assertions.
​
A Step Definitions File should not contain the following:
* Logic such as for loops, if statements, etc, that impact the outcome of the test.
* Hard-coded copy text such as expected text values.
​
​
### 3. Feature Files
A Feature File should contain all relevant Test Cases for a particular feature. Specific Test Cases should be categorized
by the Test Types using Tags.
​
Feature Files should contain the following:
* Test Data needed to perform the tests.
​
Feature File should not contain the following:
* Inconsistent Tags across Tests of the Same Type
* Test Cases that are not categorized by some tag.
​
### 4. Generic Utilities
Generic Utilities contains methods that are applicable to more than on Page.
​
Generic Utilities should contain the following:
* Reusable and unique methods
* Methods categorized into Regions as appropriate
​
Generic Utilities should not contain the following:
* Duplicate Methods with similar or matching logic.
​
## Style Requirements
### Feature Files
#### File Names:
Use PascalCase when naming a Feature file, for example, PillowPurchase.feature.
​
Please review the [Official Gherkin Documentation](https://docs.cucumber.io/gherkin/reference/)
here. Follow indentation rules defined here. Gherkin files can automatically be reformatted using IntelliJ's built-in
"Reformat Code" function and by pretty format in eclipse.
​
#### Additional Formatting Rules:
Scenario separators should be used between test cases. The standard is to use `#************************` with no indentation.
​
#### Tags:
Every test in a particular file should contain a tag relating to that feature, e.g., `@Purchase`
​
## Step Definitions
#### Class Names:
Use PascalCase when naming a class, for example, PurchaseSteps.
```
@Then("^user should navigate to home page$")
public void userShouldNavigateToHomePage() {
    Assert.assertTrue(objHome_Page.homePageSuccessfullyLoaded(), "Homepage did not successfully load");
}
```
​
IntelliJ/Eclipse is able to automatically generate Step Definitions from Gherkin written in feature files.
#### Methods:
Name using camelCase. Step definitions will always be voids as they do not return anything.
​
**Assertions** should occur within the Step Definitions as shown above. Always include a detailed message of why the test
has failed as this message will be included on the final report.

#### Class Names:
Use PascalCase when naming a class, for example, HomePage.
#### Test hooks:
```
@FindBy(id="snap-midtrans")
	private WebElement frame_popUp;
	
private static final By frame_otpPage =By.xpath("//iframe[contains(@src,'https://api.sandbox.veritrans.co.id/v2/token')]");
	
```
**Access Scope:** public\
**Naming Convention:** elementtype_elementDescription\
**Element Type Examples:**\
link, button, text\
**Element Description Examples:**\
cardNumber\
expiryDate\
**Naming Convention Examples:**\
input_cardNumber\
button_checkOut
​
#### Methods:
**Access Scope:**\
Make public if being used from an outside class\
Make private if only being used from within the class
​
**Naming Convention**\
Convention should differ depending on whether a method is an interaction or
if it is a verification. Interactions are actions like clicking, moving the cursor, entering text, etc.
Verifications are checks such as comparing text to an element, checking if something is present, etc.
There is no need to return a boolean for interactions.
For interactions:
```
public void clickContinueButton();
```
Camelcase named according to actionObject format, other examples include enterTextInField.
​
For verifications:
```
public boolean isPresentWait
```
Camelcase named according the the check being performed, examples include isPresent.

**Best Practices**
1. Never perform Assertions within the Page Object, these should happen at the Step Definition level.
1. Always use proper indentation and formatting. From IntelliJ, right click  a file and
select Reformat Code to automatically get correct formatting, and select all and press ctrl + i in eclipse.
1. Always perform Code Analysis on your code and resolve warnings. Code Analysis is performed during a Git Commit.
​
