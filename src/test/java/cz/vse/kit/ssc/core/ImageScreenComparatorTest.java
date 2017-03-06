package cz.vse.kit.ssc.core;

import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;
import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.Platform;

import cz.vse.kit.ssc.ResourceFile;
import cz.vse.kit.ssc.repository.Screenshot;

/**
 * Test for {@link ImageScreenComparator}  command
 * @author pavel.sklenar
 *
 */
public class ImageScreenComparatorTest {

    @Rule
    public ResourceFile image1 = new ResourceFile("/screenshots/java1.png");

    @Rule
    public ResourceFile imageCompare = new ResourceFile("/screenshots/compare_java1_java1.png");

    @Rule
    public ResourceFile differenceImage = new ResourceFile("/screenshots/composite_difference_java1_java1.png");

    private Screenshot screenshot1;

    private ImageScreenComparator comparator;

    @Before
    public void setUp() throws Exception {
        screenshot1 = createScreenshot(image1);
        comparator = new ImageScreenComparator();
    }

    /**
     * Create a testing {@link Screenshot}
     *
     * @throws IOException
     */
    private Screenshot createScreenshot(ResourceFile file) throws IOException {
        Screenshot screenshot = new Screenshot();
        screenshot.setId("test");
        screenshot.setBrowserName("firefox");
        screenshot.setCaptureDate(new Date());
        screenshot.setPlatform(Platform.UNIX);
        screenshot.setImageData(file.getContentAsBytes());
        return screenshot;
    }

    @Test
    public void testCompare() throws Exception {
        Screenshot result = comparator.compare(screenshot1, screenshot1, 0);
        assertArrayEquals(imageCompare.getContentAsBytes(), result.getImageData());
    }

    @Test
    public void testComposeDifference() throws Exception {
        Screenshot result = comparator.composeDifference(screenshot1, screenshot1, false);
        assertArrayEquals(differenceImage.getContentAsBytes(), result.getImageData());
    }

}
