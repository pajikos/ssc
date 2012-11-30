package cz.vse.kit.ssc;

import static org.junit.Assert.fail;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import cz.vse.kit.ssc.core.CompatibilityTester;
import cz.vse.kit.ssc.repository.Screenshot;

public class UseCase1 {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();
	private CompatibilityTester compatibilityTester;
	private static final String PATH_TO_SAVE = "D:/temp/ssc";
	
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
		compatibilityTester.saveScreenshotToFile(compatibilityTester.compare(lastTwoScreenshotById.get(0),lastTwoScreenshotById.get(1), 7), PATH_TO_SAVE, "same_browser");
		compatibilityTester.saveScreenshotToFile(compatibilityTester.compositeDifference(lastTwoScreenshotById.get(0),lastTwoScreenshotById.get(1)), PATH_TO_SAVE, "diffe");
		//System.err.println(compatibilityTester.computeSimilarity(lastTwoScreenshotById.get(0), lastTwoScreenshotById.get(1)));
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
