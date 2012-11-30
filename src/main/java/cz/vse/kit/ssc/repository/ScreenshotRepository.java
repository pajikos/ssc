package cz.vse.kit.ssc.repository;

import java.util.List;

import org.openqa.selenium.remote.DesiredCapabilities;


public interface ScreenshotRepository {


	void saveScreenshot(Screenshot screenshot);


	public List<Screenshot> getScreenshotsByExample(Screenshot screenshot);


	public List<Screenshot> getScreenshotsByDesiredCapabilities(DesiredCapabilities desiredCapabilities);


	public List<Screenshot> getScreenshotsByDesiredCapabilities(DesiredCapabilities desiredCapabilities, String id);
	

	public Screenshot getLastScreenshotById(String id);


	public List<Screenshot> getScreenshotsById(String id);


	public Screenshot getLastScreenshotByDesiredCapabilities(DesiredCapabilities desiredCapabilities, String id);


	public Screenshot getLastScreenshotByExample(Screenshot screenshot);


	public List<Screenshot> getLastTwoScreenshotsByExample(Screenshot screenshot);


	public List<Screenshot> getLastTwoScreenshotById(String id);

}