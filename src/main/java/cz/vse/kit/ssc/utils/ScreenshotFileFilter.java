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
    private static final int MINIMUM_FILENAME_PARAMS = 4;

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
        String[] parameters = filenameWithoutExtension.split(SscFilenameUtils.PARAMETR_DELIMITER);
        // if actual file hasn't all parameters in his name
        if (parameters.length < MINIMUM_FILENAME_PARAMS) {
            return false;
        }
        Screenshot actualScreenshot = new Screenshot();

        if (screenshot.getId() != null){
            actualScreenshot.setId(parameters[0]);
        }

        if (screenshot.getCaptureDate() != null){
            actualScreenshot.setCaptureDate(new Date(Long.valueOf(parameters[1])));
        }

        if (screenshot.getPlatform() != null) {
            actualScreenshot.setPlatform(Platform.valueOf(parameters[2]));
        }


        if (screenshot.getBrowserName() != null){
            actualScreenshot.setBrowserName(parameters[3]);
        }

        if (screenshot.getBrowserVersion() != null && parameters.length > MINIMUM_FILENAME_PARAMS) {
            actualScreenshot.setBrowserVersion(parameters[4]);
        }

        if (actualScreenshot.equals(screenshot)) {
            return true;
        } else
            return false;

    }
}
