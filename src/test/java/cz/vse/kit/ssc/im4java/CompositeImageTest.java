package cz.vse.kit.ssc.im4java;

import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;
import java.util.Date;

import org.im4java.core.IM4JavaException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.Platform;

import cz.vse.kit.ssc.ResourceFile;
import cz.vse.kit.ssc.repository.Screenshot;

/**
 * Test class for {@link CompositeImage} command
 *
 * @author pavel.sklenar
 *
 */
public class CompositeImageTest {

    @Rule
    public ResourceFile image1 = new ResourceFile("/screenshots/java1.png");

    @Rule
    public ResourceFile image2 = new ResourceFile("/screenshots/java2.png");

    @Rule
    public ResourceFile differenceImage = new ResourceFile("/screenshots/java1_java2_difference.png");

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
        Screenshot screenshot = new Screenshot();
        screenshot.setId("test");
        screenshot.setBrowserName("firefox");
        screenshot.setCaptureDate(new Date());
        screenshot.setPlatform(Platform.UNIX);
        screenshot.setImageData(file.getContentAsBytes());
        return screenshot;
    }

    @Test
    public void testCompositeSuccessfully() throws IM4JavaException, IOException {
        Screenshot result = CompositeImage.process(screenshot1, screenshot2);
        assertArrayEquals(differenceImage.getContentAsBytes(), result.getImageData());
    }

}
;
