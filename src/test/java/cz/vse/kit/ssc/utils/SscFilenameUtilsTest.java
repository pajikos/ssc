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
 *
 * @author pavel.sklenar
 *
 */
public class SscFilenameUtilsTest {

    private Screenshot screenshot;

    @Before
    public void setup() throws IOException {
        screenshot = new Screenshot.ScreenshotBuilder().withId("test").withBrowserName("firefox")
                .withCaptureDate(new Date()).withPlatform(Platform.UNIX).withImageData("TEST".getBytes()).withBrowserVersion("46").build();
    }

    @Test
    public void testGetDefaultMaxFilenameLength() throws Exception {
        assertEquals(255, SscFilenameUtils.getMaxFilenameLength());
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
    public void testGetFilename() throws Exception {
        assertEquals("test_"+ screenshot.getCaptureDate().getTime() + "_UNIX_firefox_46.png", SscFilenameUtils.getFilename(screenshot));
    }

    @Test
    public void testLoadScreenshotInfoFromFilename() throws Exception {
        assertEquals(screenshot, SscFilenameUtils.loadScreenshotInfoFromFilename("test_" + screenshot.getCaptureDate().getTime() + "_UNIX_firefox_46.png"));
    }

}
