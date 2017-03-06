package cz.vse.kit.ssc.utils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.Platform;

import cz.vse.kit.ssc.exception.FileReadException;
import cz.vse.kit.ssc.exception.FilenameException;
import cz.vse.kit.ssc.repository.Screenshot;

/**
 * Utils for manipulate with filename of {@link Screenshot}
 *
 * @author pavel.sklenar
 */
public final class SscFilenameUtils {
    public static final String IMAGE_EXTENSION = ".png";
    private static int maxLengthFileName = 255;
    public static final char[] ILLEGAL_CHARACTERS = { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>',
            '|', '\"', ':', '_' };
    public static final String PARAMETR_DELIMITER = "_";

    private SscFilenameUtils() {
    }

    public static int getMaxFilenameLength() {
        return maxLengthFileName;
    }

    public static void setMaxFilenameLength(int max) {
        if (max <= 0) {
            throw new FilenameException("Max length of file name should be positive number.");
        }
        maxLengthFileName = max;
    }

    public static boolean haveIllegalCharToFilename(String filename) {
        CharSequence seq = new String(ILLEGAL_CHARACTERS);
        if (filename.contains(seq)) {
            return true;
        }
        return false;
    }

    public static String getPrettyPrintIllegalChar() {
        return Arrays.toString(ILLEGAL_CHARACTERS);
    }

    /**
     * Read the {@link File} to the byte array
     *
     * @param file
     * @return
     */
    public static byte[] readByteArrayFromFile(File file) {
        try {
            return FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            throw new FileReadException("Can't read from the file " + file.getAbsolutePath(), e);
        }
    }

    /**
     * Get the filename from the {@link Screenshot} instance
     *
     * @param id
     * @return
     */
    public static String getFilename(Screenshot screenshot) {
        if (SscFilenameUtils.haveIllegalCharToFilename(screenshot.getId())) {
            throw new FilenameException("Id must not contain these characters: "
                    + SscFilenameUtils.getPrettyPrintIllegalChar());
        }
        String filename = screenshot.getId() + "_" + screenshot.getCaptureDate().getTime() + "_"
                + screenshot.getPlatform() + "_" + screenshot.getBrowserName() + "_" + (screenshot.getBrowserVersion() != null ? screenshot.getBrowserVersion() : "")
                + IMAGE_EXTENSION;
        if (SscFilenameUtils.getMaxFilenameLength() < filename.length()) {
            throw new FilenameException("Max length of filename is not enough to use compatibility tester.");
        }
        return filename;
    }

    /**
     * Create instance of the screenshot only from screenshot filename Doesn't
     * return loaded {@link Screenshot} imageData
     *
     * @param screenshotFile
     * @return
     */
    public static Screenshot loadScreenshotInfoFromFilename(String filename) {
        Screenshot resultScreenshot = new Screenshot();

        String filenameWithoutExtension = FilenameUtils.removeExtension(filename);
        String[] parameters = filenameWithoutExtension.split(SscFilenameUtils.PARAMETR_DELIMITER);
        resultScreenshot.setId(parameters[0]);
        resultScreenshot.setCaptureDate(new Date(Long.valueOf(parameters[1])));
        resultScreenshot.setPlatform(Platform.valueOf(parameters[2]));
        resultScreenshot.setBrowserName(parameters[3]);
        if (parameters.length > 4) {
            resultScreenshot.setBrowserVersion(parameters[4]);
        }
        return resultScreenshot;
    }

    public static final Comparator<File> SCREENSHOT_CREATION_FILENAME_COMP_ASC = new Comparator<File>() {
        @Override
        public int compare(File o1, File o2) {
            Screenshot screenshot1 = loadScreenshotInfoFromFilename(o1.getName());
            Screenshot screenshot2 = loadScreenshotInfoFromFilename(o2.getName());
            return screenshot1.getCaptureDate().compareTo(screenshot2.getCaptureDate());
        }

    };

    public static final Comparator<File> SCREENSHOT_CREATION_FILENAME_COMP_DESC = new Comparator<File>() {
        @Override
        public int compare(File o1, File o2) {
            Screenshot screenshot1 = loadScreenshotInfoFromFilename(o1.getName());
            Screenshot screenshot2 = loadScreenshotInfoFromFilename(o2.getName());
            return screenshot2.getCaptureDate().compareTo(screenshot1.getCaptureDate());
        }

    };

}
