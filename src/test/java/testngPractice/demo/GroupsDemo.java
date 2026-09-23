package testngPractice.demo;

import org.testng.annotations.Test;

public class GroupsDemo {

    @Test(groups = {"smoke"})
    public void smokeTest() {

        System.out.println(
                "SMOKE TEST EXECUTED | smokeTest"
        );
    }

    @Test(groups = {"regression"})
    public void regressionTest() {

        System.out.println(
                "REGRESSION TEST EXECUTED | regressionTest"
        );
    }

    @Test(groups = {"smoke", "regression"})
    public void smokeAndRegressionTest() {

        System.out.println(
                "SMOKE + REGRESSION TEST EXECUTED | smokeAndRegressionTest"
        );
    }
}