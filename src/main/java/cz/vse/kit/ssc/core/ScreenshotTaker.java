/**
 * 
 */
package cz.vse.kit.ssc.core;

import java.io.IOException;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.vse.kit.ssc.exception.RemoteWebdriverException;
import cz.vse.kit.ssc.repository.Screenshot;

/**
 * @author pavel.sklenar
 * 
 */
final class ScreenshotTaker {
	private static final Logger LOG = LoggerFactory.getLogger(ScreenshotTaker.class);

	protected ScreenshotTaker() {
	}

	/**
	 * Get Screenshot in the defined format
	 * 
	 * @param target
	 * @return
	 * @throws IOException
	 */
	private <X> X getScreenshotAs(OutputType<X> target, RemoteWebDriver webDriver) {
		if ((Boolean) webDriver.getCapabilities().getCapability(CapabilityType.TAKES_SCREENSHOT)) {
			try {
				return target
						.convertFromBase64Png(webDriver.getCommandExecutor()
								.execute(new Command(webDriver.getSessionId(), DriverCommand.SCREENSHOT)).getValue()
								.toString());
			} catch (IOException e) {
				throw new RemoteWebdriverException("Cannot take a screenshot from the WebDriver.");
			}
		} else {
			throw new RemoteWebdriverException("The used WebDriver doesn't have a" + CapabilityType.TAKES_SCREENSHOT
					+ " capability.");
		}
	}

	/**
	 * Take a screenshot from {@see RemoteWebDriver}
	 * 
	 * @param id
	 * @throws IOException
	 */
	protected <T extends RemoteWebDriver> Screenshot takeScreenshot(String id, T webDriver) {
		Screenshot screenshot = new Screenshot();
		screenshot.setId(id);
		screenshot.setPlatform(webDriver.getCapabilities().getPlatform());
		screenshot.setBrowserName(webDriver.getCapabilities().getBrowserName());
		screenshot.setBrowserVersion(webDriver.getCapabilities().getVersion());
		screenshot.setImageData(getScreenshotAs(OutputType.BYTES, webDriver));
		screenshot.setCaptureDate(new Date());
		if (LOG.isDebugEnabled()) {
			LOG.debug("The Snapshot with id " + id + " was taken and saved.");
		}
		return screenshot;
	}

}
