package cz.vse.kit.ssc;

import static org.junit.Assert.fail;

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
import cz.vse.kit.ssc.repository.ScreenshotRepository;

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
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		compatibilityTester = new CompatibilityTester();
		compatibilityTester.setScreenshotRepository(repository);
		driver = new FirefoxDriver();
		baseUrl = "https://www.vse.cz/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void saveScreenshotToFile() {
		driver.get(baseUrl + "/");
		compatibilityTester.takeScreenshotAndSaveToRepo("springTest", driver);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	/**
	 * @param repository
	 *            the repository to set
	 */
	public void setRepository(ScreenshotRepository repository) {
		this.repository = repository;
	}

}
