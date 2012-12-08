package cz.vse.kit.ssc;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import cz.vse.kit.ssc.core.CompatibilityTester;

/**
 * Base example tests
 * @author pavel.sklenar
 *
 */
public class ExampleTest extends TestCase {
	private WebDriver  driver;
	private CompatibilityTester compatibilityTester;


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
		this.compatibilityTester = new CompatibilityTester("d:\\temp");
		
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void saveScreenshotToFile() throws Exception {
		this.driver.get("http://kit.vse.cz/");
		
		compatibilityTester.takeScreenshotAndSaveToRepo("test2", driver);
	}
	

	@After
	public void tearDown() throws Exception {
		this.driver.quit();
	}
}
