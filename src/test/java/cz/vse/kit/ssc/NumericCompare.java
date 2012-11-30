package cz.vse.kit.ssc;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import cz.vse.kit.ssc.core.CompatibilityTester;
import cz.vse.kit.ssc.repository.Screenshot;

public class NumericCompare {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();
	private CompatibilityTester compatibilityTester;
	private static final String PATH_TO_SAVE = "D:/Dropbox/Plocha/diplomka_screen/use_case4";
	
	@Before
	public void setUp() throws Exception {        
        DesiredCapabilities caps = DesiredCapabilities.firefox();
        caps.setCapability("platform", "Windows 2012");
        caps.setCapability("version", "3.0");
        caps.setCapability("name", "Firefox 3.0 test");

//        this.driver = new RemoteWebDriver(
//					  new URL("http://your_username:your_token@ondemand.saucelabs.com:80/wd/hub"),
//					  caps);
//
//		baseUrl = "https://www.vse.cz";
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		compatibilityTester = new CompatibilityTester(PATH_TO_SAVE);
	}

	@Test
	public void test() throws Exception {
		driver.get(baseUrl + "/");
		compatibilityTester.takeScreenshotAndSaveToRepo("home", driver);
	}
	
	@Test
	public void compare(){
		List<Screenshot> lastTwoScreenshotById = compatibilityTester.getScreenshotRepository().getLastTwoScreenshotById("home");
		compatibilityTester.saveScreenshotToFile(compatibilityTester.compare(lastTwoScreenshotById.get(0),lastTwoScreenshotById.get(1), 10), PATH_TO_SAVE, "same_browser");
		compatibilityTester.saveScreenshotToFile(compatibilityTester.compositeDifference(lastTwoScreenshotById.get(0),lastTwoScreenshotById.get(1)), PATH_TO_SAVE, "diffe");
		float similarity = compatibilityTester.computeSimilarity(lastTwoScreenshotById.get(0), lastTwoScreenshotById.get(1));
		System.err.println(similarity);
		assertTrue("The screenshots are not the same.",similarity >= 0.72); 
		
	}

	@After
	public void tearDown() throws Exception {
		//driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
