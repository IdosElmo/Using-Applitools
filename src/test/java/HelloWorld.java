/*
Add the following dependency to your project OR download and add equivalent jar file to your project
 <dependency>
      <groupId>com.applitools</groupId>
      <artifactId>eyes-selenium-java3</artifactId>
      <version>RELEASE</version>
 </dependency>
*/

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.MatchLevel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.RectangleSize;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.URL;

public class HelloWorld {
    public static final String USERNAME = "idoelmaleh_g68rFC";
    public static final String AUTOMATE_KEY = "3G5dp8W5ybwwNkXv2Jgf";
    public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

    public static void main(String[] args) throws Exception {

        WebDriver driver = new ChromeDriver(new ChromeOptions().setHeadless(getCI()));

        // Initialize the eyes SDK and set your private API key.
        Eyes eyes = new Eyes();
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));

        //Set only once per Jenkins job
        BatchInfo mybatch = new BatchInfo(System.getenv("APPLITOOLS_BATCH_NAME"));
        mybatch.setId(System.getenv("APPLITOOLS_BATCH_ID"));
        //End of - Set only once per Jenkins job
        eyes.setBatch(mybatch);
        eyes.setBaselineEnvName("cross-env");
        eyes.setMatchLevel(MatchLevel.LAYOUT);

        try{

            // Start the test and set the browser's viewport size to 800x600.
            eyes.open(driver, "Hello World!", "Jenkins Java",
                    new RectangleSize(800, 600));

            // Navigate the browser to the "hello world!" web-site.
            driver.get("https://applitools.com/helloworld");

            // Visual checkpoint #1.
            eyes.checkWindow("Hello!");

            // Click the "Click me!" button.
            driver.findElement(By.tagName("button")).click();

            // Visual checkpoint #2.
            eyes.checkWindow("Click!");

            // End the test.
            eyes.close();

        } finally {

            // Close the browser.
            driver.quit();

            // If the test was aborted before eyes.close was called, ends the test as aborted.
            eyes.abortIfNotClosed();
        }

    }

    public static boolean getCI() {
        String env = System.getenv("CI");
        return Boolean.parseBoolean(env);
    }


}