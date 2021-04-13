package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.PickleEventWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import org.apache.log4j.BasicConfigurator;
import org.testng.annotations.*;
import utility.BrowserFactory;

@CucumberOptions(features ="src/test/java/featurefile/",
        tags=("@ComposeMail"),
        glue={"step"},
        dryRun = false,
        plugin = {"pretty", "html:target/cucumber-reports", "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber.xml"},
        monochrome = true)

public class TestRunner extends BaseClass{
    private TestNGCucumberRunner testNGCucumberRunner;

    @BeforeSuite()
    public void beforeSuite(){
        BasicConfigurator.configure();
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {

        refBrowserFactory = new BrowserFactory();
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @AfterClass
    public void tearDown() {
        testNGCucumberRunner.finish();
    }

    @Test(groups = "cucumber scenarios", dataProvider = "scenarios")
    public void scenario(PickleEventWrapper pickleEvent, CucumberFeatureWrapper cucumberFeature) throws Throwable {
        testNGCucumberRunner.runScenario(pickleEvent.getPickleEvent());
    }

    @DataProvider(parallel=false)
    public Object[][] scenarios() {
        return testNGCucumberRunner.provideScenarios();
    }
}
