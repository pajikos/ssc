package cz.vse.kit.ssc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cz.vse.kit.ssc.core.CompatibilityTester;
import cz.vse.kit.ssc.repository.Screenshot;
import cz.vse.kit.ssc.repository.ScreenshotFileRepository;
import cz.vse.kit.ssc.utils.SscFilenameUtils;

public class TestExample1 extends AbstractRealBrowserTest {
    private static final String BASE_URL = "http://www.google.com";
    private Path outputDirectory;
    private WebDriver driver;

    @Before
    public void setup() throws IOException {
        outputDirectory = Files.createTempDirectory("SSC_COMMON");
        driver = new FirefoxDriver();
    }



    /**
     * Base initialization and taking a screenshot
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        CompatibilityTester compatibilityTester = new CompatibilityTester();
        driver.get(BASE_URL);
        Screenshot screenshot = compatibilityTester.takeScreenshot("home", driver);
        assertNotNull(screenshot);
        assertNotNull(screenshot.getImageData());
    }

    /**
     * Setting screenshot repository - the first way
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        CompatibilityTester compatibilityTester = new CompatibilityTester(outputDirectory);
        driver.get(BASE_URL);
        Screenshot screenshot = compatibilityTester.takeScreenshotAndSaveToRepo("home", driver);
        assertTrue(Files.exists(outputDirectory.resolve(SscFilenameUtils.getFilename(screenshot))));
    }

    /**
     * Setting screenshot repository - the second way
     * @throws Exception
     */
    @Test
    public void testSetRepositoryAfterObjectCreation() throws Exception {
        CompatibilityTester compatibilityTester = new CompatibilityTester();
        compatibilityTester.setScreenshotRepository(new ScreenshotFileRepository(outputDirectory));
        driver.get(BASE_URL);
        Screenshot screenshot = compatibilityTester.takeScreenshotAndSaveToRepo("home", driver);
        assertTrue(Files.exists(outputDirectory.resolve(SscFilenameUtils.getFilename(screenshot))));
    }



    /**
     * Comparing screenshots
     * @throws Exception
     */
    @Test
    public void test5() throws Exception {
        CompatibilityTester compatibilityTester = new CompatibilityTester(outputDirectory);
        driver.get(BASE_URL);
        Screenshot homeScreenshot = compatibilityTester.takeScreenshotAndSaveToRepo("home", driver);
        driver.get(BASE_URL + "/gmail");
        Screenshot profilScreenshot = compatibilityTester.takeScreenshotAndSaveToRepo("gmail", driver);

        Screenshot compareResult = compatibilityTester.compare(homeScreenshot, profilScreenshot);

        compatibilityTester.saveScreenshotToFile(compareResult, outputDirectory.toString(), "result");
    }

    /**
     * Computing similarity of screenshots
     */
    @Test
    public void test6(){
        CompatibilityTester compatibilityTester = new CompatibilityTester(outputDirectory);
        driver.get(BASE_URL);
        Screenshot homeScreenshot = compatibilityTester.takeScreenshotAndSaveToRepo("home", driver);
        driver.findElement(By.linkText("Profil školy")).click();
        Screenshot profilScreenshot = compatibilityTester.takeScreenshotAndSaveToRepo("profil", driver);

        compatibilityTester.computeSimilarity(homeScreenshot, profilScreenshot);


    }


}
