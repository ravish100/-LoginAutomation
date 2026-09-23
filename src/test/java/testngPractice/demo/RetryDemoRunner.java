package testngPractice.demo;

import org.testng.annotations.Listeners;
import utils.RetryListener;

@Listeners(RetryListener.class)
public class RetryDemoRunner {
}