/**
 * 
 */
package cz.vse.kit.ssc.core;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import cz.vse.kit.ssc.exception.RemoteWebdriverException;
import cz.vse.kit.ssc.exception.ScreenshotRepositoryException;
import cz.vse.kit.ssc.repository.Screenshot;
import cz.vse.kit.ssc.repository.ScreenshotRepository;
import cz.vse.kit.ssc.repository.ScreenshotFileRepository;
import cz.vse.kit.ssc.utils.SscFileUtils;

/**
 * @author pavel.sklenar
 * 
 */
public class CompatibilityTester {

	private ScreenshotRepository screenshotRepository;

	public CompatibilityTester(ScreenshotRepository screenshotRepository) {
		this.screenshotRepository = screenshotRepository;

	}

	public CompatibilityTester(String path) {
		this.screenshotRepository = new ScreenshotFileRepository(path);
	}
	
	public CompatibilityTester() {
		//dummy 
	}

	/**
	 * Take the Screenshot from the WebDriver and save to the Repository
	 * 
	 * @param id
	 * @param webDriver
	 */
	public Screenshot takeScreenshotAndSaveToRepo(String id, WebDriver webDriver) {
		RemoteWebDriver remoteWebDriver;
		try {
			remoteWebDriver = (RemoteWebDriver) webDriver;
		} catch (ClassCastException e) {
			throw new RemoteWebdriverException(e);
		}
		Screenshot screenshot = new ScreenshotTaker().takeScreenshot(id, remoteWebDriver);
		if (getScreenshotRepository() == null) {
			throw new ScreenshotRepositoryException("Must be set the ScreenshotRepositoty");
		}
		getScreenshotRepository().saveScreenshot(screenshot);
		return screenshot;
	}
	
	/**
	 * Take the Screenshot from the WebDriver without the saving to Repository
	 * 
	 * @param id
	 * @param webDriver
	 */
	public Screenshot takeScreenshot(String id, WebDriver webDriver) {
		RemoteWebDriver remoteWebDriver;
		try {
			remoteWebDriver = (RemoteWebDriver) webDriver;
		} catch (ClassCastException e) {
			throw new RemoteWebdriverException(e);
		}
		Screenshot screenshot = new ScreenshotTaker().takeScreenshot(id, remoteWebDriver);
		return screenshot;
	}
	
	/**
	 * Save the Screenshot to the file
	 * @param screenshot
	 * @param pathToSave
	 * @param filename
	 */
	public void saveScreenshotToFile(Screenshot screenshot, String pathToSave, String filename) {
		SscFileUtils.saveScreenshotToFile(screenshot, pathToSave, filename);
	}
	
	/**
	 * Save the Screenshot to the file
	 * @param screenshot
	 * @param file
	 */
	public void saveScreenshotToFile(Screenshot screenshot, File file){
		SscFileUtils.saveScreenshotToFile(screenshot, file);
	}
	
	/**
	 * Save the Screenshot to the file
	 * @param screenshot
	 * @param pathToSave
	 */
	public void saveScreenshotToFile(Screenshot screenshot, String pathToSave) {
		SscFileUtils.saveScreenshotToFile(screenshot, pathToSave);
	}

	/**
	 * @return the screenshotRepository
	 */
	public ScreenshotRepository getScreenshotRepository() {
		return screenshotRepository;
	}

	/**
	 * 
	 * @param baseScreenshot
	 * @param otherScreenshot
	 * @return
	 */
	public Screenshot compare(Screenshot baseScreenshot, Screenshot otherScreenshot) {
		return new ImageScreenComparator().compare(baseScreenshot, otherScreenshot, 0);
	}
	
	/**
	 * 
	 * @param baseScreenshot
	 * @param otherScreenshot
	 * @return
	 */
	public Screenshot compare(Screenshot baseScreenshot, Screenshot otherScreenshot, int fuzzPercent) {
		return new ImageScreenComparator().compare(baseScreenshot, otherScreenshot, fuzzPercent);
	}
	
	/**
	 * Compose difference of two Screenshots
	 * @param baseScreenshot
	 * @param otherScreenshot
	 * @return
	 */
	public Screenshot compositeDifference(Screenshot baseScreenshot, Screenshot otherScreenshot) {
		return new ImageScreenComparator().composeDifference(baseScreenshot, otherScreenshot, false);
	}
	
	/**
	 * Compose difference of two Screenshots
	 * @param baseScreenshot
	 * @param otherScreenshot
	 * @return
	 */
	public Screenshot compositeDifference(Screenshot baseScreenshot, Screenshot otherScreenshot, boolean autoLevel) {
		return new ImageScreenComparator().composeDifference(baseScreenshot, otherScreenshot, autoLevel);
	}

	/**
	 * 
	 * @param baseScreenshot
	 * @param otherScreenshot
	 * @return
	 */
	public float computeSimilarity(Screenshot baseScreenshot, Screenshot otherScreenshot) {
		return new ImageSimilarityComparator().computeSimilarity(baseScreenshot, otherScreenshot);
	}

	/**
	 * @param screenshotRepository the screenshotRepository to set
	 */
	public void setScreenshotRepository(ScreenshotRepository screenshotRepository) {
		this.screenshotRepository = screenshotRepository;
	}	

}
