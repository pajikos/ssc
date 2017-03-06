package cz.vse.kit.ssc.im4java;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.Platform;

import cz.vse.kit.ssc.ResourceFile;
import cz.vse.kit.ssc.repository.Screenshot;

/**
 * Test class for {@link ConvertImage}
 * @author pavel.sklenar
 *
 */
public class ConvertImageTest {

    @Rule
    public ResourceFile image = new ResourceFile("/screenshots/java1.png");

    @Rule
    public ResourceFile grayImage = new ResourceFile("/screenshots/java1_gray.png");


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
    public void testConvertToGray() throws Exception {
        Screenshot gray = ConvertImage.convertToGray(screenshot);
        assertArrayEquals(grayImage.getContentAsBytes(), gray.getImageData());
    }

    @Test
    public void testComputeMean() throws Exception {
        assertEquals(0.95, ConvertImage.computeMean(screenshot), 0.1);
    }

    @Test
    public void testComputeCommonMean() throws Exception {
        assertEquals(0.79, ConvertImage.computeCommonMean(0.9f, 0.88f), 0.1);
    }

    @Test
    public void testComputeStandardDeviation() throws Exception {
        assertEquals(0.19, ConvertImage.computeStandardDeviation(screenshot), 0.1);
    }

    @Test
    public void testCompute() throws Exception {
        assertNotNull(ConvertImage.compute(screenshot, screenshot, 0.95f, 0.95f, 0.95f));
    }

    @Test
    public void testComputeCommonMeanAndStandardDeviation() throws Exception {
        assertEquals(0.19, ConvertImage.computeStandardDeviation(screenshot), 0.1);
    }

    @Test
    public void testAutoLevel() throws Exception {
        assertNotNull(ConvertImage.autoLevel(screenshot));
    }

}
