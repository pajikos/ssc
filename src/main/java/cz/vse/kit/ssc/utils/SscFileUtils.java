package cz.vse.kit.ssc.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import cz.vse.kit.ssc.exception.FileReadException;
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
     * Save the Screenshot to a defined directory with a defined filename
     *
     * @param screenshot
     *            to be saved
     * @param directory
     *            where a {@link Screenshot} will be saved
     * @param filename
     *            with a {@link Screenshot} will be saved
     * @return full path of the saved {@link Screenshot}
     */
    public static String saveScreenshotToFile(Screenshot screenshot, String directory, String filename) {
        String finalFilename;
        if (filename == null || filename.isEmpty()) {
            finalFilename = SscFilenameUtils.getFilename(screenshot);
        } else {
            finalFilename = filename + SscFilenameUtils.IMAGE_EXTENSION;
        }
        Path absolutePath = Paths.get(directory, finalFilename).toAbsolutePath();
        saveScreenshotToFile(screenshot, absolutePath.toFile());
        return absolutePath.toString();
    }

    /**
     * Save the Screenshot to the input file
     *
     * @param screenshot
     *            to be saved
     * @param file to be {@link Screenshot} saved to
     */
    public static void saveScreenshotToFile(Screenshot screenshot, File file) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(screenshot.getImageData());
        } catch (IOException e) {
            throw new FileSaveException("Cannot save screenshot to the file: " + file.getAbsolutePath(), e);
        }
    }

    /**
     * Save the Screenshot to the input path
     *
     * @param screenshot
     * @param path to be {@link Screenshot} saved to
     */
    public static void saveScreenshotToFile(Screenshot screenshot, Path path) {
        saveScreenshotToFile(screenshot, path.toFile());
    }

    /**
     * Save the {@link Input} {@link Screenshot} into input directory
     * <p>
     * Filename will be created dynamically from {@link Screenshot} attributes
     *
     * @param screenshot
     *            to be saved
     * @param directory
     *            where a {@link Screenshot} will be saved
     * @return full path of the saved {@link Screenshot}
     */
    public static String saveScreenshotToFile(Screenshot screenshot, String directory) {
        return saveScreenshotToFile(screenshot, directory, null);
    }

    /**
     * Read the {@link File} to the byte array
     *
     * @param file
     * @return
     */
    public static byte[] readByteArrayFromFile(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new FileReadException("Can't read from the file " + file.getAbsolutePath(), e);
        }
    }

}
