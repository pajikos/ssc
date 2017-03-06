package cz.vse.kit.ssc.core;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.Platform;

import cz.vse.kit.ssc.ResourceFile;
import cz.vse.kit.ssc.repository.Screenshot;

public class ImageSimilarityComparatorTest {

    @Rule
    public ResourceFile image1 = new ResourceFile("/screenshots/java1.png");

    @Rule
    public ResourceFile image2 = new ResourceFile("/screenshots/java2.png");

    private Screenshot screenshot1;

    private Screenshot screenshot2;

    private ImageSimilarityComparator comparator;

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

    @Before
    public void setUp() throws Exception {
        screenshot1 = createScreenshot(image1);
        screenshot2 = createScreenshot(image2);
        comparator = new ImageSimilarityComparator();
    }

    @Test
    public void testComputeSimilarity() throws Exception {
        assertEquals(0.29, comparator.computeSimilarity(screenshot1, screenshot2), 0.1);
    }

}
