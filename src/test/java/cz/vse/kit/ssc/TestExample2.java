package cz.vse.kit.ssc;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cz.vse.kit.ssc.core.CompatibilityTester;
import cz.vse.kit.ssc.repository.Screenshot;

/**
 * Taking {@link Screenshot} example
 * @author Pajik
 *
 */
public class TestExample2 {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();
	private CompatibilityTester compatibilityTester;
	
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "https://www.vse.cz/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		compatibilityTester = new CompatibilityTester();
	}

	@Test
	public void takeScreenshot() throws Exception {
		driver.get(baseUrl + "/");
		compatibilityTester.takeScreenshot("home", driver);
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
