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

/**
 * Test class for {@link ImageSimilarityComparator}
 * <p>
 * <a href="https://www.imagemagick.org/script/download.php">ImageMagick</a>
 * command line utility needs to be installed.
 *
 * @author pavel.sklenar
 *
 */
public class ImageSimilarityComparatorTest {

    @Rule
    public ResourceFile image1 = new ResourceFile("/screenshots/java1.png");

    @Rule
    public ResourceFile image2 = new ResourceFile("/screenshots/java2.png");

    private Screenshot screenshot1;

    private Screenshot screenshot2;

    /**
     * Create a testing {@link Screenshot}
     *
     * @throws IOException
     */
    private Screenshot createScreenshot(ResourceFile file) throws IOException {
        return new Screenshot.ScreenshotBuilder().withId("test").withBrowserName("firefox").withCaptureDate(new Date())
                .withPlatform(Platform.UNIX).withImageData(file.getContentAsBytes()).build();
    }

    @Before
    public void setUp() throws Exception {
        screenshot1 = createScreenshot(image1);
        screenshot2 = createScreenshot(image2);
    }

    @Test
    public void testComputeSimilarity() throws Exception {
        assertEquals(0.29, ImageSimilarityComparator.computeSimilarity(screenshot1, screenshot2), 0.1);
    }

}
