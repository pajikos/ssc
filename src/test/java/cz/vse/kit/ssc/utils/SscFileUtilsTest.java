package cz.vse.kit.ssc.utils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.Platform;

import cz.vse.kit.ssc.ResourceFile;
import cz.vse.kit.ssc.repository.Screenshot;

/**
 * Test class for {@link SscFileUtils}
 * @author pavel.sklenar
 *
 */
public class SscFileUtilsTest {

    private Path outputDirectory;

    @Rule
    public ResourceFile image = new ResourceFile("/screenshots/java1.png");

    private Screenshot screenshot;

    @Before
    public void setup() throws IOException {
        screenshot = new Screenshot();
        screenshot.setId("test");
        screenshot.setBrowserName("firefox");
        screenshot.setCaptureDate(new Date());
        screenshot.setPlatform(Platform.UNIX);
        screenshot.setImageData(image.getContentAsBytes());
        outputDirectory = Files.createTempDirectory("SSC_COMMON");
    }

    @Test
    public void testSaveScreenshotToFileScreenshotStringString() throws Exception {
        String savedImage = SscFileUtils.saveScreenshotToFile(screenshot, outputDirectory.toString(), "image.png");
        assertTrue(Files.exists(Paths.get(savedImage)));
        assertArrayEquals(image.getContentAsBytes(), Files.readAllBytes(Paths.get(savedImage)));
    }

    @Test
    public void testSaveScreenshotToFileScreenshotFile() throws Exception {
        Path tempFile = Files.createTempFile("IMAGE_TEMP", ".png");
        SscFileUtils.saveScreenshotToFile(screenshot, tempFile.toFile());
        assertTrue(Files.exists(tempFile));
        assertArrayEquals(image.getContentAsBytes(), Files.readAllBytes(tempFile));
    }

    @Test
    public void testSaveScreenshotToFileScreenshotPath() throws Exception {
        Path tempFile = Files.createTempFile("IMAGE_TEMP", ".png");
        SscFileUtils.saveScreenshotToFile(screenshot, tempFile);
        assertTrue(Files.exists(tempFile));
        assertArrayEquals(image.getContentAsBytes(), Files.readAllBytes(tempFile));
    }

    @Test
    public void testSaveScreenshotToFileScreenshotString() throws Exception {
        String savedImage = SscFileUtils.saveScreenshotToFile(screenshot, outputDirectory.toString());
        assertTrue(Files.exists(Paths.get(savedImage)));
        assertArrayEquals(image.getContentAsBytes(), Files.readAllBytes(Paths.get(savedImage)));
    }

}
