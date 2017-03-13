package cz.vse.kit.ssc.im4java;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.Platform;

import cz.vse.kit.ssc.ResourceFile;
import cz.vse.kit.ssc.repository.Screenshot;

/**
 * Test class for {@link InfoImage} command
 * @author pavel.sklenar
 *
 */
public class InfoImageTest {

    @Rule
    public ResourceFile image = new ResourceFile("/screenshots/java1.png");

    private InfoImage infoImage;

    private Screenshot screenshot;


    @Before
    public void setUp() throws Exception {
        screenshot = new Screenshot();
        screenshot.setId("test");
        screenshot.setBrowserName("firefox");
        screenshot.setCaptureDate(new Date());
        screenshot.setPlatform(Platform.UNIX);
        screenshot.setImageData(image.getContentAsBytes());
        infoImage = new InfoImage(screenshot);
    }

    @Test
    public void testGetImageFormat() throws Exception {
        assertEquals("PNG", infoImage.getImageFormat());
    }

    @Test
    public void testGetImageFormatInt() throws Exception {
        assertEquals("PNG", infoImage.getImageFormat(0));
    }

    @Test
    public void testGetImageWidth() throws Exception {
        assertEquals(300, infoImage.getImageWidth());
    }

    @Test
    public void testGetImageWidthInt() throws Exception {
        assertEquals(300, infoImage.getImageWidth(0));
    }

    @Test
    public void testGetImageHeight() throws Exception {
        assertEquals(300, infoImage.getImageWidth());
    }

    @Test
    public void testGetImageHeightInt() throws Exception {
        assertEquals(300, infoImage.getImageWidth(0));
    }

    @Test
    public void testGetImageGeometry() throws Exception {
        assertEquals("300x300+0+0", infoImage.getImageGeometry());
    }

    @Test
    public void testGetImageGeometryInt() throws Exception {
        assertEquals("300x300+0+0", infoImage.getImageGeometry(0));
    }

    @Test
    public void testGetImageDepth() throws Exception {
        assertEquals(8, infoImage.getImageDepth());
    }

    @Test
    public void testGetImageDepthInt() throws Exception {
        assertEquals(8, infoImage.getImageDepth(0));
    }

    @Test
    public void testGetImageClass() throws Exception {
        assertEquals("PseudoClass sRGB ", infoImage.getImageClass());
    }

    @Test
    public void testGetImageClassInt() throws Exception {
        assertEquals("PseudoClass sRGB ", infoImage.getImageClass(0));
    }

    @Test
    public void testGetPageWidth() throws Exception {
        assertEquals(300, infoImage.getPageWidth());
    }

    @Test
    public void testGetPageWidthInt() throws Exception {
        assertEquals(300, infoImage.getPageWidth(0));
    }

    @Test
    public void testGetPageHeight() throws Exception {
        assertEquals(300, infoImage.getPageHeight());
    }

    @Test
    public void testGetPageHeightInt() throws Exception {
        assertEquals(300, infoImage.getPageHeight(0));
    }

    @Test
    public void testGetPageGeometry() throws Exception {
        assertEquals("300x300", infoImage.getPageGeometry());
    }

    @Test
    public void testGetPageGeometryInt() throws Exception {
        assertEquals("300x300", infoImage.getPageGeometry(0));
    }

    @Test
    public void testGetPropertyString() throws Exception {
        assertEquals("300", infoImage.getProperty("Height"));
    }

    @Test
    public void testGetPropertyStringInt() throws Exception {
        assertEquals("300", infoImage.getProperty("Height", 0));
    }

    @Test
    public void testGetPropertyNames() throws Exception {
        ArrayList<String> properties = Collections.list(infoImage.getPropertyNames());
        assertEquals(9, properties.size());
        assertEquals("[Class, PageGeometry, Height, Depth, PageHeight, Geometry, Format, Width, PageWidth]", Arrays.toString(properties.toArray(new String[]{})));
    }

    @Test
    public void testGetScreenshot() throws Exception {
        assertEquals(screenshot, infoImage.getScreenshot());
    }

}
