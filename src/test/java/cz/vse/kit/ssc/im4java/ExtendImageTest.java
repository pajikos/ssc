package cz.vse.kit.ssc.im4java;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.Platform;

import cz.vse.kit.ssc.ResourceFile;
import cz.vse.kit.ssc.repository.Screenshot;

/**
 * Test class for {@link ExtendImage} command
 * @author pavel.sklenar
 *
 */
public class ExtendImageTest {

    @Rule
    public ResourceFile image = new ResourceFile("/screenshots/java1.png");

    private Screenshot screenshot;

    @Before
    public void setUp() throws Exception {
        screenshot = new Screenshot();
        screenshot.setId("test");
        screenshot.setBrowserName("firefox");
        screenshot.setCaptureDate(new Date());
        screenshot.setPlatform(Platform.UNIX);
        screenshot.setImageData(image.getContentAsBytes());
    }

    @Test
    public void testProcessSuccessfullySameProportion() throws Exception {
        assertEquals("300x300", new InfoImage(screenshot).getPageGeometry());
        Screenshot result = ExtendImage.process(screenshot, 400, 400);
        assertEquals("400x400", new InfoImage(result).getPageGeometry());
    }

    @Test
    public void testProcessSuccessfullyDifferentProportion() throws Exception {
        assertEquals("300x300", new InfoImage(screenshot).getPageGeometry());
        Screenshot result = ExtendImage.process(screenshot, 400, 500);
        assertEquals("400x500", new InfoImage(result).getPageGeometry());
    }

    @Test
    public void testProcessSuccessfullySmallerWidth() throws Exception {
        assertEquals("300x300", new InfoImage(screenshot).getPageGeometry());
        Screenshot result = ExtendImage.process(screenshot, 200, 300);
        assertEquals("200x300", new InfoImage(result).getPageGeometry());
    }

}
