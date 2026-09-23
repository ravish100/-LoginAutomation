package testngPractice.demo;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RetryDemo{

    private static int attempts = 0;

    @Test
    public void retryDemo() {

        attempts++;

        System.out.println(
                "RetryDemoTest execution attempt = "
                        + attempts
        );

        Assert.assertTrue(
                attempts >= 2,
                "Intentional failure on first attempt"
        );
    }
}