package step;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import runner.BaseClass;
import utility.GenericUtilities;

import java.io.File;
import java.nio.file.Files;

public class Hooks extends BaseClass {
    @Before
    public void cucumberBefore(){
        System.out.println("This will run before the scenario");
         gu = new GenericUtilities();
    }

    /*
    Takes screenshot of browser window if pass or fails at end of test case
     */
    public void reporting(Scenario scenario){
        String ScenarioName = scenario.getName();
        String currentTimeStamp = GenericUtilities.currentDate("yyyy.MM.dd.HH.mm.ss");
        if (scenario.isFailed() && driver!=null) {
            try {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                File destinationPath = new File(System.getProperty("user.dir") +"/ScreenPrints/fail_"+currentTimeStamp+".png" );
                FileUtils.writeByteArrayToFile(destinationPath, screenshot);
                scenario.embed(Files.readAllBytes(destinationPath.toPath()), "image/png");
            } catch (Exception ignored) {
            }
        } else {
            try {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                File destinationPath = new File(System.getProperty("user.dir") +"/ScreenPrints/pass_"+currentTimeStamp+".png");
                FileUtils.writeByteArrayToFile(destinationPath, screenshot);
                scenario.embed(Files.readAllBytes(destinationPath.toPath()), "image/png");
            } catch (Exception ignored) {
            }
        }
    }


    @After
    public void cucumberAfter(Scenario scenario){
        reporting(scenario);
        System.out.println("This will run after the scenario");
        if(driver!=null)driver.quit();
    }
}
