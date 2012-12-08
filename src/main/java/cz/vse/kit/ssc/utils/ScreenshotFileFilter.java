/**
 * 
 */
package cz.vse.kit.ssc.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.Platform;

import cz.vse.kit.ssc.repository.Screenshot;

/**
 * Filter for searching screenshots on the file system
 * @author pavel.sklenar
 * 
 */
public class ScreenshotFileFilter implements FilenameFilter {
	private static final int FILENAME_NUMER_PARAMS = 5;

	private static final String ACCEPT_FILENAME_EXTENSION = ".png";
	
	private Screenshot screenshot;

	public ScreenshotFileFilter(Screenshot findScreenshot) {
		this.screenshot = findScreenshot;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
	 */
	@Override
	public boolean accept(File dir, String filename) {
		if (!(new File(dir + File.separator + filename)).isFile()){
			return false;
		}
		// needed only screenshots from the actual dir
		if (!filename.endsWith(ACCEPT_FILENAME_EXTENSION)) {
			return false;
		}
		
		String filenameWithoutExtension = FilenameUtils.removeExtension(filename);
		String[] parametrs = filenameWithoutExtension.split(SscFilenameUtils.PARAMETR_DELIMITER);
		// if actual file hasn't all parameters in his name
		if (parametrs.length != FILENAME_NUMER_PARAMS) {
			return false;
		}
		Screenshot actualScreenshot = new Screenshot();
		
		if (screenshot.getId() != null){
			actualScreenshot.setId(parametrs[0]);
		}
		
		if (screenshot.getCaptureDate() != null){
			actualScreenshot.setCaptureDate(new Date(Long.valueOf(parametrs[1])));
		}
		
		if (screenshot.getPlatform() != null) {
			actualScreenshot.setPlatform(Platform.valueOf(parametrs[2]));
		}
		
		
		if (screenshot.getBrowserName() != null){
			actualScreenshot.setBrowserName(parametrs[3]);
		}
		
		if (screenshot.getBrowserVersion() != null) {
			actualScreenshot.setBrowserVersion(parametrs[4]);
		}

		if (actualScreenshot.equals(screenshot)) {
			return true;
		} else
			return false;

	}
}
