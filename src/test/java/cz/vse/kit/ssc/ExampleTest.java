package cz.vse.kit.ssc;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import cz.vse.kit.ssc.core.CompatibilityTester;
import cz.vse.kit.ssc.repository.Screenshot;


public class ExampleTest extends TestCase {
	private WebDriver  driver;
	private CompatibilityTester compatibilityTester;


	@Before
	public void setUp() throws Exception {

		DesiredCapabilities capabillities = DesiredCapabilities.firefox();
		//capabillities.setCapability("version", "19.0.1084.56");
		//capabillities.setCapability("browserName", "firefox");
		
		capabillities.setCapability("platform", Platform.WINDOWS);
		capabillities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
		capabillities.setCapability("name", "Testing Selenium 2");
		
//		this.driver = new RemoteWebDriver(
//				new URL(
//						"http://8dbe273704bc5a7c818c2e41641a2b67:2981eb2ca98ef93955f3f24a6b885e44@hub.testingbot.com:4444/wd/hub"),
//				capabillities);
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
	public void testSimple() throws Exception {
		this.driver.get("http://kit.vse.cz/");
		
		Screenshot sc1 = compatibilityTester.takeScreenshotAndSaveToRepo("test2", driver);
		//assertEquals("Vysoká škola ekonomická v Praze | VŠE", this.driver.getTitle());
		
//		driver.findElement(By.linkText("Profil školy")).click();
//		Screenshot sc2 = compatibilityTester.takeScreenshotAndSaveToRepo("test2", driver);
//		System.err.println(compatibilityTester.computeSimilarity(sc1, sc1));
	}
	

	@After
	public void tearDown() throws Exception {
		this.driver.quit();
	}
}
