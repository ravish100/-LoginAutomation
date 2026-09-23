package testngPractice.demo;

import org.testng.annotations.*;

public class LifecycleDemo {

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("BEFORE SUITE");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("BEFORE TEST");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("BEFORE METHOD");
    }

    @Test
    public void testOne() {
        System.out.println("TEST ONE");
    }

    @Test
    public void testTwo() {
        System.out.println("TEST TWO");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("AFTER METHOD");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("AFTER TEST");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("AFTER SUITE");
    }
}