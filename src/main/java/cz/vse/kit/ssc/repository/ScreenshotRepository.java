package cz.vse.kit.ssc.repository;

import java.util.List;

import org.openqa.selenium.remote.DesiredCapabilities;

public interface ScreenshotRepository {

	/**
	 * Save screenshot to the repository
	 * 
	 * @param screenshot
	 *            {@link Screenshot} to save to repository
	 */
	void saveScreenshot(Screenshot screenshot);

	/**
	 * Return {@link List} of {@link Screenshot} that meet the criteria
	 * 
	 * @param screenshot
	 *            {@link Screenshot} example (specifics criteria)
	 * @return {@link List} with result ({@link Screenshot})
	 */
	public List<Screenshot> getScreenshotsByExample(Screenshot screenshot);

	/**
	 * Return {@link List} of {@link Screenshot} that meet the criteria
	 * 
	 * @param desiredCapabilities
	 *            {@link DesiredCapabilities} object with specific search
	 *            criteria
	 * @return {@link List} with result ({@link Screenshot})
	 */
	public List<Screenshot> getScreenshotsByDesiredCapabilities(DesiredCapabilities desiredCapabilities);

	/**
	 * Return {@link List} of {@link Screenshot} that meet the criteria
	 * 
	 * @param desiredCapabilities
	 *            {@link DesiredCapabilities} object with specific search
	 *            criteria
	 * @param id
	 *            The unique identifier of {@link Screenshot}
	 * @return {@link List} with result ({@link Screenshot})
	 */
	public List<Screenshot> getScreenshotsByDesiredCapabilities(DesiredCapabilities desiredCapabilities, String id);

	/**
	 * Get the last {@link Screenshot} from repository with defined id
	 * 
	 * @param id
	 *            The unique identifier of {@link Screenshot}
	 * @return founded {@link Screenshot}
	 */
	public Screenshot getLastScreenshotById(String id);

	/**
	 * Return {@link List} of {@link Screenshot} that meet the criteria
	 * 
	 * @param id
	 *            The unique identifier of {@link Screenshot}
	 * @return {@link List} with result ({@link Screenshot})
	 */
	public List<Screenshot> getScreenshotsById(String id);

	/**
	 * Get The last two {@link Screenshot} from repository with the same id
	 * 
	 * @param desiredCapabilities
	 *            {@link DesiredCapabilities} object with specific search
	 *            criteria
	 * @param id
	 *            The unique identifier of {@link Screenshot}
	 * @return founded {@link Screenshot}
	 */
	public Screenshot getLastScreenshotByDesiredCapabilities(DesiredCapabilities desiredCapabilities, String id);

	/**
	 * Get The last {@link Screenshot} by example
	 * 
	 * @param screenshot
	 *            {@link Screenshot} example (specifics criteria)
	 * @return founded {@link Screenshot}
	 */
	public Screenshot getLastScreenshotByExample(Screenshot screenshot);

	/**
	 * Return {@link List} of {@link Screenshot} that meet the criteria
	 * 
	 * @param screenshot
	 *            {@link Screenshot} example (specifics criteria)
	 * @return {@link List} with result ({@link Screenshot})
	 */
	public List<Screenshot> getLastTwoScreenshotsByExample(Screenshot screenshot);

	/**
	 * Return {@link List} of {@link Screenshot} that meet the criteria
	 * 
	 * @param id
	 *            The unique identifier of {@link Screenshot}
	 * @return {@link List} with result ({@link Screenshot})
	 */
	public List<Screenshot> getLastTwoScreenshotsById(String id);

}