package testngPractice.demo;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParametersDemo {

    @Test
    @Parameters({"browser", "environment"})
    public void parameterTest(
            String browser,
            String environment) {

        System.out.println(
                "BROWSER = " + browser
        );

        System.out.println(
                "ENVIRONMENT = " + environment
        );
    }
}