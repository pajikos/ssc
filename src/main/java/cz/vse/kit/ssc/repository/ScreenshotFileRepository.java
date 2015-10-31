/**
 * 
 */
package cz.vse.kit.ssc.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.vse.kit.ssc.exception.ScreenshotDirectoryException;
import cz.vse.kit.ssc.utils.ScreenshotFileFilter;
import cz.vse.kit.ssc.utils.SscFileUtils;
import cz.vse.kit.ssc.utils.SscFilenameUtils;

/**
 * The DAO for {@link Screenshot} with the file purpose
 * 
 * @author pavel.sklenar
 * 
 */
public class ScreenshotFileRepository implements ScreenshotRepository {
	private static final Logger LOG = LoggerFactory.getLogger(ScreenshotFileRepository.class);
	private File dir;

	public ScreenshotFileRepository(String path) {
		createDir(path);
	}
	
	public ScreenshotFileRepository(File dir) {
		this.dir = dir;
	}

	/**
	 * Constructor
	 * 
	 * @param path
	 * @param maxLengthOfFilename
	 */
	public ScreenshotFileRepository(String path, int maxLengthOfFilename) {
		createDir(path);
		setMaxLengthOfFilename(maxLengthOfFilename);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.vse.kit.ssc.repository.ScreenshotRepository#saveScreenshot(cz.vse.
	 * kit.ssc.repository.Screenshot)
	 */
	public synchronized void saveScreenshot(Screenshot screenshot) {
		SscFileUtils.saveScreenshotToFile(screenshot, dir);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.vse.kit.ssc.core.repository.ScreenshotRepository#getScreenshotById()
	 */
	@Override
	public List<Screenshot> getScreenshotsByExample(Screenshot screenshot) {
		List<Screenshot> result = new ArrayList<Screenshot>();
		File[] filesByScreenshotExample = getFilesByScreenshotExample(screenshot);
		if (filesByScreenshotExample == null || filesByScreenshotExample.length == 0) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("No Screenshot was found");
			}
			return result;
		}
		Arrays.sort(filesByScreenshotExample, SscFilenameUtils.SCREENSHOT_CREATION_FILENAME_COMP_DESC);

		for (int i = 0; i < filesByScreenshotExample.length; i++) {
			result.add(loadScreenshot(filesByScreenshotExample[i]));
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.vse.kit.ssc.repository.ScreenshotRepository#getLastScreenshotByExample
	 * (cz.vse.kit.ssc.repository.Screenshot)
	 */
	public Screenshot getLastScreenshotByExample(Screenshot screenshot) {
		File[] filesByScreenshotExample = getFilesByScreenshotExample(screenshot);
		if (filesByScreenshotExample == null || filesByScreenshotExample.length == 0) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("No Screenshot was found");
			}
			return null;
		}
		Arrays.sort(filesByScreenshotExample, SscFilenameUtils.SCREENSHOT_CREATION_FILENAME_COMP_DESC);
		return loadScreenshot(filesByScreenshotExample[0]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.vse.kit.ssc.core.repository.ScreenshotRepository#
	 * getLastTwoScreenshotsByExample
	 */
	@Override
	public List<Screenshot> getLastTwoScreenshotsByExample(Screenshot screenshot) {
		List<Screenshot> result = new ArrayList<Screenshot>();
		File[] filesByScreenshotExample = getFilesByScreenshotExample(screenshot);
		if (filesByScreenshotExample == null || filesByScreenshotExample.length == 0) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("No Screenshot was found.");
			}
			return result;
		}
		Arrays.sort(filesByScreenshotExample, SscFilenameUtils.SCREENSHOT_CREATION_FILENAME_COMP_DESC);
		result.add(loadScreenshot(filesByScreenshotExample[0]));
		if (filesByScreenshotExample.length == 1) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Only one Screenshot found in method getLastTwoScreenshotsByExample.");
			}
		} else {
			result.add(loadScreenshot(filesByScreenshotExample[1]));
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.vse.kit.ssc.core.repository.ScreenshotRepository#getScreenshotById()
	 */
	@Override
	public Screenshot getLastScreenshotById(String id) {
		Screenshot exampleScreenshot = new Screenshot();
		exampleScreenshot.setId(id);
		return getLastScreenshotByExample(exampleScreenshot);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.vse.kit.ssc.repository.ScreenshotRepository#getLastTwoScreenshotById
	 * (java.lang.String)
	 */
	public List<Screenshot> getLastTwoScreenshotById(String id) {
		Screenshot exampleScreenshot = new Screenshot();
		exampleScreenshot.setId(id);
		return getLastTwoScreenshotsByExample(exampleScreenshot);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.vse.kit.ssc.repository.ScreenshotRepository#getScreenshotsById(java
	 * .lang.String)
	 */
	public List<Screenshot> getScreenshotsById(String id) {
		Screenshot screenshot = new Screenshot();
		screenshot.setId(id);
		return getScreenshotsByExample(screenshot);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.vse.kit.ssc.repository.ScreenshotRepository#
	 * getLastScreenshotByDesiredCapabilities
	 * (org.openqa.selenium.remote.DesiredCapabilities, java.lang.String)
	 */
	public Screenshot getLastScreenshotByDesiredCapabilities(DesiredCapabilities desiredCapabilities, String id) {
		Screenshot screenshot = new Screenshot();
		screenshot.setBrowserName(desiredCapabilities.getBrowserName());
		screenshot.setBrowserVersion(desiredCapabilities.getVersion());
		screenshot.setPlatform(desiredCapabilities.getPlatform());
		screenshot.setId(id);
		return getLastScreenshotByExample(screenshot);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.vse.kit.ssc.repository.ScreenshotRepository#
	 * getScreenshotsByDesiredCapabilities
	 * (org.openqa.selenium.remote.DesiredCapabilities)
	 */
	public List<Screenshot> getScreenshotsByDesiredCapabilities(DesiredCapabilities desiredCapabilities) {
		return getScreenshotsByDesiredCapabilities(desiredCapabilities, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.vse.kit.ssc.repository.ScreenshotRepository#
	 * getScreenshotsByDesiredCapabilities
	 * (org.openqa.selenium.remote.DesiredCapabilities, java.lang.String)
	 */
	public List<Screenshot> getScreenshotsByDesiredCapabilities(DesiredCapabilities desiredCapabilities, String id) {
		Screenshot screenshot = new Screenshot();
		screenshot.setBrowserName(desiredCapabilities.getBrowserName());
		screenshot.setBrowserVersion(desiredCapabilities.getVersion());
		screenshot.setPlatform(desiredCapabilities.getPlatform());
		screenshot.setId(id);
		return getScreenshotsByExample(screenshot);
	}

	/**
	 * @param maxLengthOfFilename
	 *            the maxLengthOfFilename to set
	 */
	private void setMaxLengthOfFilename(int maxLengthOfFilename) {
		SscFilenameUtils.setMaxFilenameLength(maxLengthOfFilename);
	}

	/**
	 * @param path
	 *            the path to set
	 */
	private void createDir(String path) {
		File dir = new File(path);
		boolean exists = dir.isDirectory();
		if (exists) {
			this.dir = dir;
		} else {
			throw new ScreenshotDirectoryException();
		}
	}

	/**
	 * Load the {@link Screenshot} object from the {@link File}
	 * 
	 * @param screenshotFile
	 * @return
	 */
	private Screenshot loadScreenshot(File screenshotFile) {
		Screenshot resultScreenshot = SscFilenameUtils.loadScreenshotInfoFromFilename(screenshotFile.getName());
		resultScreenshot.setImageData(SscFilenameUtils.readByteArrayFromFile(screenshotFile));
		return resultScreenshot;
	}

	/**
	 * Load files by screenshot
	 * 
	 * @param screenshot
	 * @return
	 */
	private File[] getFilesByScreenshotExample(Screenshot screenshot) {
		return dir.listFiles(new ScreenshotFileFilter(screenshot));
	}

}
