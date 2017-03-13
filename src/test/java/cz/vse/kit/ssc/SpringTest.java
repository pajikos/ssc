package cz.vse.kit.ssc;

import static org.junit.Assert.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.vse.kit.ssc.core.CompatibilityTester;
import cz.vse.kit.ssc.repository.Screenshot;
import cz.vse.kit.ssc.repository.ScreenshotRepository;
import cz.vse.kit.ssc.utils.SscFilenameUtils;

/**
 * Example test with spring
 *
 * @author pavel.sklenar
 *
 */
@ContextConfiguration("file:src/test/resources/testContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringTest {
    @Autowired
    private ScreenshotRepository repository;
    private CompatibilityTester compatibilityTester;
    private WebDriver driver;
    private static final String BASE_URL = "http://www.example.org";
    private Path outputDirectory;

    @Before
    public void setUp() throws Exception {
        compatibilityTester = new CompatibilityTester();
        compatibilityTester.setScreenshotRepository(repository);
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        outputDirectory = Files.createTempDirectory("SPRING_TEST");
    }

    @Test
    public void saveScreenshotToFile() {
        driver.get(BASE_URL);
        Screenshot screenshot = compatibilityTester.takeScreenshotAndSaveToRepo("springTest", driver);
        assertTrue(Files.exists(outputDirectory.resolve(SscFilenameUtils.getFilename(screenshot))));
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
