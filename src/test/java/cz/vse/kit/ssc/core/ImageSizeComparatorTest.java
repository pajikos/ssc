package cz.vse.kit.ssc.core;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.Platform;

import cz.vse.kit.ssc.ResourceFile;
import cz.vse.kit.ssc.im4java.InfoImage;
import cz.vse.kit.ssc.repository.Screenshot;

/**
 * Test class for {@link ImageSizeComparator}
 * @author pavel.sklenar
 *
 */
public class ImageSizeComparatorTest {

    @Rule
    public ResourceFile image1 = new ResourceFile("/screenshots/java1.png");

    @Rule
    public ResourceFile image2 = new ResourceFile("/screenshots/java2.png");

    private Screenshot screenshot1;

    private Screenshot screenshot2;

    private ImageSizeComparator comparator;

    @Before
    public void setUp() throws Exception {
        screenshot1 = createScreenshot(image1);
        screenshot2 = createScreenshot(image2);
        comparator = new ImageSizeComparator();
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
    public void testResizeImages2SameSize() throws Exception {
        Screenshot resizedImages2SameSize = comparator.resizeImages2SameSize(screenshot1, screenshot2);
        assertEquals("300x300", new InfoImage(resizedImages2SameSize).getPageGeometry());
    }

    @Test
    public void testGetInfoAboutImage() throws Exception {
        assertEquals("300x300", comparator.getInfoAboutImage(screenshot1).getPageGeometry());
    }

    @Test
    public void testDoCropIfRequired() throws Exception {
        Screenshot resizedImages2SameSize = comparator.doCropIfRequired(comparator.getInfoAboutImage(screenshot1), comparator.getInfoAboutImage(screenshot2));
        assertEquals("300x300", new InfoImage(resizedImages2SameSize).getPageGeometry());
    }

    @Test
    public void testDoExtentIfRequired() throws Exception {
        Screenshot resizedImages2SameSize = comparator.doExtentIfRequired(comparator.getInfoAboutImage(screenshot1), comparator.getInfoAboutImage(screenshot2));
        assertEquals("300x300", new InfoImage(resizedImages2SameSize).getPageGeometry());
    }

}
