package cz.vse.kit.ssc.core;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

import cz.vse.kit.ssc.AbstractRealBrowserTest;
import cz.vse.kit.ssc.repository.Screenshot;

/**
 * Test for {@link ScreenshotTaker}<p>
 * <a href="https://www.mozilla.org/en-US/firefox/new/?scene=2">Mozilla Firefox</a> needs to be installed.
 * @author pavel.sklenar
 *
 */
public class ScreenshotTakerTest extends AbstractRealBrowserTest {

    private static final String BASE_URL = "http://www.google.com";

    private FirefoxDriver driver;

    private ScreenshotTaker taker;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        taker = new ScreenshotTaker();
    }

    @Test
    public void testTakeScreenshot() throws Exception {
        driver.get(BASE_URL);
        Screenshot screenshot = taker.takeScreenshot("home", driver);
        assertNotNull(screenshot);
        assertNotNull(screenshot.getImageData());
        assertTrue(screenshot.getImageData().length != 0);
    }

    @After
    public void close() throws Exception {
        driver.close();
    }

}
