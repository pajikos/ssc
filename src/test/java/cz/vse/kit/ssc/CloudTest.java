package cz.vse.kit.ssc;

import static org.junit.Assert.fail;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import cz.vse.kit.ssc.core.CompatibilityTester;

/**
 * Example test with running selenium in cloud
 * @author pavel.sklenar
 *
 */
public class CloudTest {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();
	private CompatibilityTester compatibilityTester;
	private static final String PATH_TO_SAVE = "D:/temp/ssc";
	
	@Before
	public void setUp() throws Exception {
		DesiredCapabilities capabillities = DesiredCapabilities.ipad();
        capabillities.setCapability("version", "5.0");
        capabillities.setCapability("name", "Cloud iphone test");
        
        DesiredCapabilities caps = DesiredCapabilities.android();
        caps.setCapability("platform", "Linux");
        caps.setCapability("version", "4");
        caps.setCapability("name", "Cloud android test");

        this.driver = new RemoteWebDriver(
					  new URL("http://your_username:your_token@ondemand.saucelabs.com:80/wd/hub"),
					  capabillities);
		baseUrl = "https://www.vse.cz";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		compatibilityTester = new CompatibilityTester(PATH_TO_SAVE);
	}

	@Test
	public void test() throws Exception {
		driver.get(baseUrl + "/");
		compatibilityTester.takeScreenshotAndSaveToRepo("home", driver);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
