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
 * <p>
 * <a href="https://www.imagemagick.org/script/download.php">ImageMagick</a>
 * command line utility needs to be installed.
 *
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

    @Before
    public void setUp() throws Exception {
        screenshot1 = createScreenshot(image1);
        screenshot2 = createScreenshot(image2);
    }

    /**
     * Create a testing {@link Screenshot}
     *
     * @throws IOException
     */
    private Screenshot createScreenshot(ResourceFile file) throws IOException {
        return new Screenshot.ScreenshotBuilder().withId("test").withBrowserName("firefox").withCaptureDate(new Date())
                .withPlatform(Platform.UNIX).withImageData(file.getContentAsBytes()).build();
    }

    @Test
    public void testResizeImages2SameSize() throws Exception {
        Screenshot resizedImages2SameSize = ImageSizeComparator.resizeImages2SameSize(screenshot1, screenshot2);
        assertEquals("300x300", new InfoImage(resizedImages2SameSize).getPageGeometry());
    }

    @Test
    public void testGetInfoAboutImage() throws Exception {
        assertEquals("300x300", ImageSizeComparator.getInfoAboutImage(screenshot1).getPageGeometry());
    }

    @Test
    public void testDoCropIfRequired() throws Exception {
        Screenshot resizedImages2SameSize = ImageSizeComparator.doCropIfRequired(
                ImageSizeComparator.getInfoAboutImage(screenshot1), ImageSizeComparator.getInfoAboutImage(screenshot2));
        assertEquals("300x300", new InfoImage(resizedImages2SameSize).getPageGeometry());
    }

    @Test
    public void testDoExtentIfRequired() throws Exception {
        Screenshot resizedImages2SameSize = ImageSizeComparator.doExtentIfRequired(ImageSizeComparator.getInfoAboutImage(screenshot1),
                ImageSizeComparator.getInfoAboutImage(screenshot2));
        assertEquals("300x300", new InfoImage(resizedImages2SameSize).getPageGeometry());
    }

}
