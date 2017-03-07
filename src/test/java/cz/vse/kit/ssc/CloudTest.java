package cz.vse.kit.ssc;

import static org.junit.Assert.*;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import cz.vse.kit.ssc.core.CompatibilityTester;
import cz.vse.kit.ssc.repository.Screenshot;
import cz.vse.kit.ssc.utils.SscFilenameUtils;

/**
 * Example test with running selenium in cloud
 *
 * @author pavel.sklenar
 *
 */
public class CloudTest {
    private WebDriver driver;
    private static final String BASE_URL = "https://www.google.com";
    private CompatibilityTester compatibilityTester;
    private Path outputDirectory;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabillities = DesiredCapabilities.firefox();
        capabillities.setCapability("platform", Platform.WINDOWS);
        capabillities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
        capabillities.setCapability("name", "Testing Selenium 2");

        this.driver = new RemoteWebDriver(
                new URL(
                        "http://your_username:your_token@ondemand.saucelabs.com:80/wd/hub"),
                capabillities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        outputDirectory = Files.createTempDirectory("CLOUD_TEST");
        compatibilityTester = new CompatibilityTester(outputDirectory);
    }

    @Test
    @Ignore("Setup cloud credentials first")
    public void test() throws Exception {
        driver.get(BASE_URL);
        Screenshot screenshot = compatibilityTester.takeScreenshotAndSaveToRepo("home", driver);
        assertTrue(Files.exists(outputDirectory.resolve(SscFilenameUtils.getFilename(screenshot))));
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
