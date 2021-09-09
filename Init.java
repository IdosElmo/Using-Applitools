import com.applitools.eyes.*;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;

import com.applitools.eyes.selenium.StitchMode;
import com.applitools.eyes.selenium.fluent.Target;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.Runner;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.google.common.base.Strings.isNullOrEmpty;

public class Init {

    private static Eyes eyes;
    private static EyesRunner runner;
    private static WebDriver driver;
    private static BatchInfo batch;
    private static JavascriptExecutor js;

    @BeforeClass
    public static void setBatch() {
        // Must be before ALL tests (at Class-level)
        batch = new BatchInfo("Java");
    }

    @Before
    public void setUp() {

        runner = new ClassicRunner();
        eyes = new Eyes(runner);

        eyes.setBatch(batch);

        // Raise an error if no API Key has been found.
        if(isNullOrEmpty(System.getenv("APPLITOOLS_API_KEY"))) {
            throw new RuntimeException("No API Key found; Please set environment variable 'APPLITOOLS_API_KEY'.");
        }

        // Set your personal Applitols API Key from your environment variables.
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));

        eyes.setMatchLevel(MatchLevel.CONTENT);

        eyes.setBranchName("test branch");
//        eyes.setParentBranchName("test-branch");

        eyes.setForceFullPageScreenshot(true);
        eyes.setStitchMode(StitchMode.CSS);

        // Use Chrome browser
        driver = new ChromeDriver();

    }

    @Test
    public void injectionTest() {
        // Set AUT's name, test name and viewport size (width X height)
        // We have set it to 800 x 600 to accommodate various screens. Feel free to
        // change it.
        eyes.open(driver, "Demo Java #5", "Java Test #5", new RectangleSize(800, 600));

        js = (JavascriptExecutor) driver;

        // Navigate the browser to the "ACME" demo app.
        driver.get("https://www.applitools.com");

        // To see visual bugs after the first run, use the commented line below instead.
        //driver.get("https://demo.applitools.com/index_v2.html");

        eyes.check("full check", Target.window());

        // End the test.
        eyes.closeAsync();
    }

//    @Test
//    public void basicTest() {
//        // Set AUT's name, test name and viewport size (width X height)
//        // We have set it to 800 x 600 to accommodate various screens. Feel free to
//        // change it.
//        eyes.open(driver, "Demo Java #5", "Java Test #5", new RectangleSize(800, 600));
//
//        // Navigate the browser to the "ACME" demo app.
//        driver.get("file:///Users/idos/ido_js_folder/simple%20web%20page/index.html");
//
//        // To see visual bugs after the first run, use the commented line below instead.
//        //driver.get("https://demo.applitools.com/index_v2.html");
//
////        for (int i = 0; i < 5; i++) {
//        eyes.check("region check", Target.region(By.id("h")));
//
//        driver.findElement(By.id("h1")).click();
//        js.executeScript("document.querySelector(CssSelectore).style.background = \\'rgba(0,0,0,1)\\'");
//
//        // Visual checkpoint #2
//        eyes.check("region check", Target.region(By.id("h1")));
//
//        // End the test.
//        eyes.closeAsync();
//    }

    @After
    public void tear() {
        // Close the browser.
        driver.quit();

        // If the test was aborted before eyes.close was called, ends the test as
        // aborted.
        eyes.abortIfNotClosed();

        // Wait and collect all test results
        TestResultsSummary allTestResults = runner.getAllTestResults();

        // Print results
        System.out.println(allTestResults);
    }

}
