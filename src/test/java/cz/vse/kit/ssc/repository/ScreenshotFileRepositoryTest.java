package cz.vse.kit.ssc.repository;

import static org.junit.Assert.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

import cz.vse.kit.ssc.repository.Screenshot.ScreenshotBuilder;

/**
 * Test class for {@link ScreenshotFileRepository}
 * @author pavel.sklenar
 *
 */
public class ScreenshotFileRepositoryTest {

    private ScreenshotFileRepository repository;

    private Path repoDirectory;

    @Before
    public void setUp() throws Exception {
        repoDirectory = Files.createTempDirectory("FileRepoTest");
        repository = new ScreenshotFileRepository(repoDirectory);
        Files.write(repoDirectory.resolve(Paths.get("home_1489154876552_WIN10_firefox_45.png")),
                "SCREENSHOT1".getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        Files.write(repoDirectory.resolve(Paths.get("home_1489154876998_LINUX_chrome_45.png")),
                "SCREENSHOT2".getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    @Test
    public void testSaveScreenshot() throws Exception {
        Screenshot screenshot = new Screenshot();
        screenshot.setBrowserName("firefox");
        screenshot.setBrowserVersion("57");
        screenshot.setCaptureDate(new Date());
        screenshot.setImageData("TEST".getBytes());
        screenshot.setPlatform(Platform.VISTA);

        assertEquals(2, Files.list(repoDirectory).count());
        repository.saveScreenshot(screenshot);
        assertEquals(3, Files.list(repoDirectory).count());

    }

    @Test
    public void testGetScreenshotsByExample() throws Exception {
        assertEquals(2, repository.getScreenshotsByExample(new ScreenshotBuilder().withId("home").build()).size());
        assertEquals(0, repository.getScreenshotsByExample(new ScreenshotBuilder().withId("test").build()).size());
        assertEquals(1,
                repository.getScreenshotsByExample(new ScreenshotBuilder().withBrowserName("firefox").build()).size());
        assertEquals(1, repository.getScreenshotsByExample(new ScreenshotBuilder().withPlatform(Platform.LINUX).build())
                .size());
        assertEquals(1,
                repository.getScreenshotsByExample(new ScreenshotBuilder().withPlatform(Platform.UNIX).build()).size());
    }

    @Test
    public void testGetLastScreenshotByExample() throws Exception {
        assertArrayEquals("SCREENSHOT2".getBytes(),
                repository.getLastScreenshotByExample(new ScreenshotBuilder().withId("home").build()).getImageData());
        assertNull(repository.getLastScreenshotByExample(new ScreenshotBuilder().withId("test").build()));
    }

    @Test
    public void testGetLastTwoScreenshotsByExample() throws Exception {
        List<Screenshot> lastTwoScreenshotsByExample = repository
                .getLastTwoScreenshotsByExample(new ScreenshotBuilder().withId("home").build());
        assertEquals(2, lastTwoScreenshotsByExample.size());
        assertArrayEquals("SCREENSHOT2".getBytes(), lastTwoScreenshotsByExample.get(0).getImageData());

        assertEquals(1, repository
                .getLastTwoScreenshotsByExample(new ScreenshotBuilder().withBrowserName("firefox").build()).size());
        assertEquals(0,
                repository.getLastTwoScreenshotsByExample(new ScreenshotBuilder().withId("test").build()).size());
    }

    @Test
    public void testGetLastScreenshotById() throws Exception {
        assertArrayEquals("SCREENSHOT2".getBytes(), repository.getLastScreenshotById("home").getImageData());
        assertNull(repository.getLastScreenshotById("test"));
    }

    @Test
    public void testGetLastTwoScreenshotsById() throws Exception {
        List<Screenshot> lastTwoScreenshotById = repository.getLastTwoScreenshotsById("home");
        assertEquals(2, lastTwoScreenshotById.size());
        assertArrayEquals("SCREENSHOT2".getBytes(), lastTwoScreenshotById.get(0).getImageData());
        assertEquals(0, repository.getLastTwoScreenshotsById("test").size());
    }

    @Test
    public void testGetScreenshotsById() throws Exception {
        assertEquals(2, repository.getScreenshotsById("home").size());
        assertEquals(0, repository.getScreenshotsById("test").size());
    }

    @Test
    public void testGetLastScreenshotByDesiredCapabilities() throws Exception {
        assertArrayEquals("SCREENSHOT2".getBytes(), repository.getLastScreenshotByDesiredCapabilities(DesiredCapabilities.chrome(), "home").getImageData());
    }

    @Test
    public void testGetScreenshotsByDesiredCapabilitiesDesiredCapabilities() throws Exception {
       assertEquals(1, repository.getScreenshotsByDesiredCapabilities(DesiredCapabilities.chrome()).size());
    }

    @Test
    public void testGetScreenshotsByDesiredCapabilitiesDesiredCapabilitiesString() throws Exception {
        assertEquals(1, repository.getScreenshotsByDesiredCapabilities(DesiredCapabilities.chrome(), "home").size());
        assertEquals(0, repository.getScreenshotsByDesiredCapabilities(DesiredCapabilities.chrome(), "test").size());
    }

}
