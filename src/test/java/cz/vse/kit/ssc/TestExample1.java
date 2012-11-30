package cz.vse.kit.ssc;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import cz.vse.kit.ssc.core.CompatibilityTester;
import cz.vse.kit.ssc.repository.Screenshot;
import cz.vse.kit.ssc.repository.ScreenshotFileRepository;
import cz.vse.kit.ssc.repository.ScreenshotRepository;
import cz.vse.kit.ssc.utils.SscFileUtils;

public class TestExample1 {
	private static final String PATH_TO_SAVE = "D:/temp/ssc";
	private static final String BASE_URL = "http://www.vse.cz/";

	/**
	 * Base initialization and taking a screenshot
	 * @throws Exception
	 */
	@Test
	public void test1() throws Exception {
		CompatibilityTester compatibilityTester = new CompatibilityTester();
		FirefoxDriver driver = new FirefoxDriver();
		driver.get(BASE_URL + "/");
		Screenshot screenshot = compatibilityTester.takeScreenshot("home", driver);
		SscFileUtils.saveScreenshotToFile(screenshot, PATH_TO_SAVE);
	}
	
	/**
	 * Setting screenshot repository - the first way
	 * @throws Exception
	 */
	@Test
	public void test2() throws Exception {
		CompatibilityTester compatibilityTester = new CompatibilityTester(PATH_TO_SAVE);
		FirefoxDriver driver = new FirefoxDriver();
		driver.get(BASE_URL + "/");
		compatibilityTester.takeScreenshotAndSaveToRepo("home", driver);
	}
	
	/**
	 * Setting screenshot repository - the second way
	 * @throws Exception
	 */
	@Test
	public void test3() throws Exception {
		CompatibilityTester compatibilityTester = new CompatibilityTester();
		compatibilityTester.setScreenshotRepository(new ScreenshotFileRepository(PATH_TO_SAVE));
		FirefoxDriver driver = new FirefoxDriver();
		driver.get(BASE_URL + "/");
		compatibilityTester.takeScreenshotAndSaveToRepo("home", driver);
	}
	
	/**
	 * Base functionality of the repository
	 * @throws Exception
	 */
	@Test
	public void test4() throws Exception {
		CompatibilityTester compatibilityTester = new CompatibilityTester(PATH_TO_SAVE);
		ScreenshotRepository repository = compatibilityTester.getScreenshotRepository();
		Screenshot queryScreenshot = new Screenshot();
		queryScreenshot.setBrowserName("firefox");
		queryScreenshot.setId("home");
		Screenshot screenshot = repository.getLastScreenshotByExample(queryScreenshot);
		List<Screenshot> lastTwoScreens = repository.getLastTwoScreenshotsByExample(queryScreenshot);
		List<Screenshot> listOfScreens = repository.getScreenshotsByExample(queryScreenshot);
		
		System.err.println(screenshot.getBrowserName());
	}
	
	/**
	 * Comparing screenshots
	 * @throws Exception
	 */
	@Test
	public void test5() throws Exception {
		CompatibilityTester compatibilityTester = new CompatibilityTester(PATH_TO_SAVE);
		WebDriver driver = new FirefoxDriver();
		driver.get(BASE_URL + "/");
		Screenshot homeScreenshot = compatibilityTester.takeScreenshotAndSaveToRepo("home", driver);
		driver.findElement(By.linkText("Profil školy")).click();
		Screenshot profilScreenshot = compatibilityTester.takeScreenshotAndSaveToRepo("profil", driver);
		
		Screenshot compareResult = compatibilityTester.compare(homeScreenshot, profilScreenshot);
		
		compatibilityTester.saveScreenshotToFile(compareResult, PATH_TO_SAVE, "result");
	}
	
	/**
	 * Computing similarity of screenshots
	 */
	@Test
	public void test6(){
		CompatibilityTester compatibilityTester = new CompatibilityTester(PATH_TO_SAVE);
		WebDriver driver = new FirefoxDriver();
		driver.get(BASE_URL + "/");
		Screenshot homeScreenshot = compatibilityTester.takeScreenshotAndSaveToRepo("home", driver);
		driver.findElement(By.linkText("Profil školy")).click();
		Screenshot profilScreenshot = compatibilityTester.takeScreenshotAndSaveToRepo("profil", driver);
		
		float similarity = compatibilityTester.computeSimilarity(homeScreenshot, profilScreenshot);
		
		
	}


}
