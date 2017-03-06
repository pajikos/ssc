package cz.vse.kit.ssc.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import cz.vse.kit.ssc.exception.FileSaveException;
import cz.vse.kit.ssc.repository.Screenshot;

/**
 * Utils class for manipulate {@link Screenshot} over file system
 *
 * @author pavel.sklenar
 *
 */
public final class SscFileUtils {

    private SscFileUtils() {
    }

    /**
     * Save the Screenshot to the file
     *
     * @param screenshot
     * @param pathToSave
     * @param filename
     */
    public static void saveScreenshotToFile(Screenshot screenshot, String pathToSave, String filename) {
        String finalFilename;
        if (filename == null || filename.isEmpty()) {
            finalFilename = SscFilenameUtils.getFilename(screenshot);
        } else {
            finalFilename = filename + SscFilenameUtils.IMAGE_EXTENSION;
        }
        Path absolutePath = Paths.get(pathToSave, finalFilename).toAbsolutePath();
        saveScreenshotToFile(screenshot, absolutePath.toFile());

    }

    /**
     * Save the Screenshot to the file
     *
     * @param screenshot
     * @param file
     */
    public static void saveScreenshotToFile(Screenshot screenshot, File file) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(screenshot.getImageData());
        } catch (IOException e) {
            throw new FileSaveException("Cannot save screenshot to the file: " + file.getAbsolutePath(), e);
        }
    }

    /**
     * Save the Screenshot to the file
     *
     * @param screenshot
     * @param pathToSave
     */
    public static void saveScreenshotToFile(Screenshot screenshot, String pathToSave) {
        saveScreenshotToFile(screenshot, pathToSave, null);
    }

}
