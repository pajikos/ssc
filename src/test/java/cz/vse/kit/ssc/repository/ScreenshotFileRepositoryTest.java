package cz.vse.kit.ssc.repository;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cz.vse.kit.ssc.AbstractRealBrowserTest;
import cz.vse.kit.ssc.core.CompatibilityTester;

public class ScreenshotFileRepositoryTest extends AbstractRealBrowserTest {

    private static final String BASE_URL = "http://www.google.com/";
    private Path outputDirectory;
    private WebDriver driver;

    @Before
    public void setup() throws IOException {
        outputDirectory = Files.createTempDirectory("SSC_COMMON");
        driver = new FirefoxDriver();
    }

    /**
     * Base functionality of the repository
     * @throws Exception
     */
    @Test
    public void testGetScreenshotsFromRepo() throws Exception {
        CompatibilityTester compatibilityTester = new CompatibilityTester(outputDirectory);
        driver.get(BASE_URL);
        Screenshot firstScreenshot = compatibilityTester.takeScreenshotAndSaveToRepo("home", driver);
        Screenshot secondScreenshot = compatibilityTester.takeScreenshotAndSaveToRepo("home", driver);
        ScreenshotRepository repository = compatibilityTester.getScreenshotRepository();

        assertEquals(2, repository.getScreenshotsById("home").size());

        Screenshot queryScreenshot = new Screenshot();
        queryScreenshot.setBrowserName("firefox");
        queryScreenshot.setId("home");

        assertEquals(secondScreenshot, repository.getLastScreenshotByExample(queryScreenshot));
        assertEquals(2, repository.getScreenshotsByExample(queryScreenshot).size());
        List<Screenshot> lastTwoScreenshotsByExample = repository.getLastTwoScreenshotsByExample(queryScreenshot);
        assertEquals(2, lastTwoScreenshotsByExample.size());
        assertEquals(firstScreenshot, lastTwoScreenshotsByExample.get(1));
        assertEquals(secondScreenshot, lastTwoScreenshotsByExample.get(0));
    }

}
