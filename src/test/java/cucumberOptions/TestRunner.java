package cucumberOptions;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
//import utils.RetryListener;
import utils.TestExecutionListener;
import utils.TestNGInvocationListener;

import utils.TestNGListener;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinitions", "hooks"},
      //  tags = "@smoke",
       tags = "@regression",
       // tags = "@login and @negative",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-html-report.html",
                "json:target/cucumber-reports/cucumber.json",
                "rerun:target/cucumber-reports/failed-scenarios.txt",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true
)
//@Listeners(RetryListener.class)


@Listeners({
        TestExecutionListener.class,
        TestNGListener.class,
        TestNGInvocationListener.class
})
public class TestRunner extends AbstractTestNGCucumberTests {
        @Override
        @DataProvider(parallel = true)
        public Object[][] scenarios() {
                return super.scenarios();
        }
}
