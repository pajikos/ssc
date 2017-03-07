package cz.vse.kit.ssc.utils;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Platform;

import cz.vse.kit.ssc.repository.Screenshot;

/**
 * Test class for {@link SscFilenameUtils}
 * @author pavel.sklenar
 *
 */
public class SscFilenameUtilsTest {

    private Screenshot screenshot;

    @Before
    public void setup() throws IOException {
        screenshot = new Screenshot();
        screenshot.setId("test");
        screenshot.setBrowserName("firefox");
        screenshot.setCaptureDate(new Date());
        screenshot.setPlatform(Platform.UNIX);
    }

    @Test
    public void testGetDefaultMaxFilenameLength() throws Exception {
        assertEquals(255, SscFilenameUtils.getMaxFilenameLength());
    }

    @Test
    public void testSetMaxFilenameLength() throws Exception {
        SscFilenameUtils.setMaxFilenameLength(140);
        assertEquals(140, SscFilenameUtils.getMaxFilenameLength());
    }

    @Test
    public void testHaveIllegalCharToFilename() throws Exception {
        assertTrue(SscFilenameUtils.haveIllegalCharToFilename("*.png"));
        assertTrue(SscFilenameUtils.haveIllegalCharToFilename("test?.png"));
        assertTrue(SscFilenameUtils.haveIllegalCharToFilename("*.png"));
        assertTrue(SscFilenameUtils.haveIllegalCharToFilename("<test>.png"));
        assertFalse(SscFilenameUtils.haveIllegalCharToFilename("images.png"));
        assertFalse(SscFilenameUtils.haveIllegalCharToFilename("8image.png"));
    }

    @Test
    public void testGetPrettyPrintIllegalChar() throws Exception {
        throw new RuntimeException("not yet implemented");
    }

    @Test
    public void testReadByteArrayFromFile() throws Exception {
        throw new RuntimeException("not yet implemented");
    }

    @Test
    public void testGetFilename() throws Exception {
        throw new RuntimeException("not yet implemented");
    }

    @Test
    public void testLoadScreenshotInfoFromFilename() throws Exception {
        throw new RuntimeException("not yet implemented");
    }

}
