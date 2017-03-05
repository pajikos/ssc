package cz.vse.kit.ssc;

import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cz.vse.kit.ssc.core.CompatibilityTester;
import cz.vse.kit.ssc.repository.Screenshot;

/**
 * Example of the numeric comparing
 * @author pavel.sklenar
 *
 */
public class NumericCompare {
	private WebDriver driver;
	private String baseUrl;
	private CompatibilityTester compatibilityTester;
	private Path outputDirectory;
	
	@Before
	public void setUp() throws Exception {        
		driver = new FirefoxDriver();
        outputDirectory = Files.createTempDirectory("SSC_NUMERIC_COMPARE");
		compatibilityTester = new CompatibilityTester(outputDirectory);
	}
	
	@Test
	public void compare(){
		driver.get(baseUrl);
		compatibilityTester.takeScreenshotAndSaveToRepo("home", driver);
		List<Screenshot> lastTwoScreenshotById = compatibilityTester.getScreenshotRepository().getLastTwoScreenshotById("home");
		compatibilityTester.saveScreenshotToFile(compatibilityTester.compare(lastTwoScreenshotById.get(0), lastTwoScreenshotById.get(1), 10), outputDirectory.toString(), "same_browser");
		compatibilityTester.saveScreenshotToFile(compatibilityTester.compositeDifference(lastTwoScreenshotById.get(0),lastTwoScreenshotById.get(1)), outputDirectory.toString(), "diffe");
		float similarity = compatibilityTester.computeSimilarity(lastTwoScreenshotById.get(0), lastTwoScreenshotById.get(1));
		assertTrue("The screenshots are not the same.",similarity >= 0.72); 
		
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
}
