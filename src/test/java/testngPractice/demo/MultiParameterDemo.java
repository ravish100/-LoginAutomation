package testngPractice.demo;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MultiParameterDemo {

    @Test
    @Parameters({"browser"})
    public void browserTest(String browser) {

        System.out.println(
                "Executing test with browser = " + browser
        );
    }
}