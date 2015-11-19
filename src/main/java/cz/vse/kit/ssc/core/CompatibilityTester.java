package cz.vse.kit.ssc.core;

import java.io.File;
import java.nio.file.Path;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import cz.vse.kit.ssc.exception.RemoteWebdriverException;
import cz.vse.kit.ssc.exception.ScreenshotRepositoryException;
import cz.vse.kit.ssc.repository.Screenshot;
import cz.vse.kit.ssc.repository.ScreenshotRepository;
import cz.vse.kit.ssc.repository.ScreenshotFileRepository;
import cz.vse.kit.ssc.utils.SscFileUtils;

/**
 * Main class of library Point of contact for all functionality
 * 
 * @author pavel.sklenar
 * 
 */
public class CompatibilityTester {

	private ScreenshotRepository screenshotRepository;

	/**
	 * Constructor for construct instance with {@link ScreenshotFileRepository}
	 * parameter
	 * 
	 * @param screenshotRepository
	 *            set instance of {@link ScreenshotFileRepository}
	 */
	public CompatibilityTester(ScreenshotRepository screenshotRepository) {
		this.screenshotRepository = screenshotRepository;

	}

	/**
	 * Contuctor for construct for setting {@link ScreenshotFileRepository}
	 * 
	 * @param path
	 *            to saving screenshots
	 */
	public CompatibilityTester(String path) {
		this.screenshotRepository = new ScreenshotFileRepository(path);
	}
	
	/**
	 * Contuctor for construct for setting {@link ScreenshotFileRepository}
	 * 
	 * @param path
	 *            to saving screenshots
	 */
	public CompatibilityTester(File dir) {
		this.screenshotRepository = new ScreenshotFileRepository(dir);
	}
	
	/**
	 * Contuctor for construct for setting {@link ScreenshotFileRepository}
	 * 
	 * @param path
	 *            to saving screenshots
	 */
	public CompatibilityTester(Path dir) {
		this.screenshotRepository = new ScreenshotFileRepository(dir.toFile());
	}

	/**
	 * Simple constructor
	 */
	public CompatibilityTester() {
		// dummy
	}

	/**
	 * Take the Screenshot from the WebDriver and save to the Repository
	 * 
	 * @param id
	 *            the unique identifier of a screenshot
	 * @param webDriver
	 *            instance of {@link WebDriver}
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
	 *            the unique identifier of a screenshot
	 * @param webDriver
	 *            instance of {@link WebDriver}
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
	 * 
	 * @param screenshot
	 *            {@link Screenshot} to save
	 * @param pathToSave
	 *            The path to save new file
	 * @param filename
	 *            The file name of new file to save
	 */
	public void saveScreenshotToFile(Screenshot screenshot, String pathToSave, String filename) {
		SscFileUtils.saveScreenshotToFile(screenshot, pathToSave, filename);
	}

	/**
	 * Save the Screenshot to the file
	 * 
	 * @param screenshot
	 *            {@link Screenshot} to save
	 * @param file
	 *            The instance to save
	 */
	public void saveScreenshotToFile(Screenshot screenshot, File file) {
		SscFileUtils.saveScreenshotToFile(screenshot, file);
	}

	/**
	 * Save the Screenshot to the file
	 * 
	 * @param screenshot
	 *            {@link Screenshot} to save
	 * @param pathToSave
	 *            The path to save new file
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
	 * Compare two screenshot
	 * 
	 * @param baseScreenshot
	 *            first {@link Screenshot} to compare
	 * @param otherScreenshot
	 *            second {@link Screenshot} to compare
	 * @return {@link Screenshot} with highlighted the differences
	 */
	public Screenshot compare(Screenshot baseScreenshot, Screenshot otherScreenshot) {
		return new ImageScreenComparator().compare(baseScreenshot, otherScreenshot, 0);
	}

	/**
	 * Compare two screenshot
	 * 
	 * @param baseScreenshot
	 *            first {@link Screenshot} to compare
	 * @param otherScreenshot
	 *            second {@link Screenshot} to compare
	 * @return {@link Screenshot} with highlighted the differences
	 */
	public Screenshot compare(Screenshot baseScreenshot, Screenshot otherScreenshot, int fuzzPercent) {
		return new ImageScreenComparator().compare(baseScreenshot, otherScreenshot, fuzzPercent);
	}

	/**
	 * Compose difference of two Screenshots (other type of comparing)
	 * 
	 * @param baseScreenshot
	 *            first {@link Screenshot} to composite
	 * @param otherScreenshot
	 *            second {@link Screenshot} to composite
	 * @return {@link Screenshot} with highlighted the differences in negative
	 */
	public Screenshot compositeDifference(Screenshot baseScreenshot, Screenshot otherScreenshot) {
		return new ImageScreenComparator().composeDifference(baseScreenshot, otherScreenshot, false);
	}

	/**
	 * Compose difference of two Screenshots (other type of
	 * {@link CompatibilityTester#compare(Screenshot, Screenshot, int)})
	 * 
	 * @param baseScreenshot
	 *            first {@link Screenshot} to composite
	 * @param otherScreenshot
	 *            second {@link Screenshot} to composite
	 * @return {@link Screenshot} with highlighted the differences in negative
	 */
	public Screenshot compositeDifference(Screenshot baseScreenshot, Screenshot otherScreenshot, boolean autoLevel) {
		return new ImageScreenComparator().composeDifference(baseScreenshot, otherScreenshot, autoLevel);
	}

	/**
	 * Compute the similarity of the input images
	 * 
	 * @param baseScreenshot
	 *            first {@link Screenshot}
	 * @param otherScreenshot
	 *            second {@link Screenshot}
	 * @return {@link Float} of mutual similarity
	 */
	public float computeSimilarity(Screenshot baseScreenshot, Screenshot otherScreenshot) {
		return new ImageSimilarityComparator().computeSimilarity(baseScreenshot, otherScreenshot);
	}

	/**
	 * @param screenshotRepository
	 *            the screenshotRepository to set
	 */
	public void setScreenshotRepository(ScreenshotRepository screenshotRepository) {
		this.screenshotRepository = screenshotRepository;
	}

}
