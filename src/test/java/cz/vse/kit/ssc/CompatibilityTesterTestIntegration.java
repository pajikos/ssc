package cz.vse.kit.ssc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import cz.vse.kit.ssc.core.CompatibilityTester;
import cz.vse.kit.ssc.repository.Screenshot;
import cz.vse.kit.ssc.repository.ScreenshotFileRepository;
import cz.vse.kit.ssc.repository.ScreenshotRepository;
import cz.vse.kit.ssc.utils.SscFilenameUtils;

/**
 * Test class demonstrates ssc library usage
 *
 * @author pavel.sklenar
 *
 */
public class CompatibilityTesterTestIntegration extends AbstractRealBrowserTest {
    private static final String BASE_URL = "http://www.example.org";
    private Path outputDirectory;
    private WebDriver driver;

    @Before
    public void setup() throws IOException {
        outputDirectory = Files.createTempDirectory("SSC_COMMON");
        driver = new FirefoxDriver();
    }

    /**
     * Base initialization and taking a screenshot
     *
     * @throws Exception
     */
    @Test
    public void testTakeScreenshot() throws Exception {
        CompatibilityTester compatibilityTester = new CompatibilityTester();
        driver.get(BASE_URL);
        Screenshot screenshot = compatibilityTester.takeScreenshot("home", driver);
        assertNotNull(screenshot);
        assertNotNull(screenshot.getImageData());
    }

    /**
     * Setting screenshot repository - the first way
     *
     * @throws Exception
     */
    @Test
    public void testTakeScreenshotAndSaveToRepoWithRepositoryFromConstructor() throws Exception {
        CompatibilityTester compatibilityTester = new CompatibilityTester(outputDirectory);
        driver.get(BASE_URL);
        Screenshot screenshot = compatibilityTester.takeScreenshotAndSaveToRepo("home", driver);
        assertTrue(Files.exists(outputDirectory.resolve(SscFilenameUtils.getFilename(screenshot))));
    }

    /**
     * Setting screenshot repository - the second way
     *
     * @throws Exception
     */
    @Test
    public void testTakeScreenshotAndSaveToRepoWithSetRepositoryAfterObjectCreation() throws Exception {
        CompatibilityTester compatibilityTester = new CompatibilityTester();
        compatibilityTester.setScreenshotRepository(new ScreenshotFileRepository(outputDirectory));
        driver.get(BASE_URL);
        Screenshot screenshot = compatibilityTester.takeScreenshotAndSaveToRepo("home", driver);
        assertTrue(Files.exists(outputDirectory.resolve(SscFilenameUtils.getFilename(screenshot))));
    }

    /**
     * Comparing screenshots
     *
     * @throws Exception
     */
    @Test
    public void testCompareTwoTakenScreenshots() throws Exception {
        CompatibilityTester compatibilityTester = new CompatibilityTester(outputDirectory);
        driver.get(BASE_URL);
        Screenshot homeScreenshot = compatibilityTester.takeScreenshotAndSaveToRepo("home", driver);
        driver.get(BASE_URL + "/test");
        Screenshot profilScreenshot = compatibilityTester.takeScreenshotAndSaveToRepo("test", driver);
        Screenshot compareResult = compatibilityTester.compare(homeScreenshot, profilScreenshot);
        assertNotNull(compareResult);
    }

    /**
     * Computing similarity of screenshots
     */
    @Test
    public void testComputeSimilarity() {
        CompatibilityTester compatibilityTester = new CompatibilityTester(outputDirectory);
        driver.get(BASE_URL);
        Screenshot homeScreenshot = compatibilityTester.takeScreenshotAndSaveToRepo("home", driver);
        // Actually no changes, /test does not change any text on the page
        driver.get(BASE_URL + "/test");
        Screenshot profilScreenshot = compatibilityTester.takeScreenshotAndSaveToRepo("test", driver);
        assertEquals(1.0, compatibilityTester.computeSimilarity(homeScreenshot, profilScreenshot), 0.0);
    }

    /**
     * Base functionality of the repository
     *
     * @throws Exception
     */
    @Test
    public void testGetLastScreenshotsAndCompositeDifference() throws Exception {
        CompatibilityTester compatibilityTester = new CompatibilityTester(outputDirectory);
        driver.get(BASE_URL);
        compatibilityTester.takeScreenshotAndSaveToRepo("home", driver);
        // Actually no changes, /test does not change any text on the page
        driver.get(BASE_URL + "/test");
        compatibilityTester.takeScreenshotAndSaveToRepo("test", driver);
        ScreenshotRepository repository = compatibilityTester.getScreenshotRepository();

        List<Screenshot> screenshotsById = repository.getScreenshotsByDesiredCapabilities(DesiredCapabilities.firefox());
        assertEquals(2, screenshotsById.size());

        assertNotNull(compatibilityTester.compositeDifference(screenshotsById.get(0), screenshotsById.get(1)));
    }

}
