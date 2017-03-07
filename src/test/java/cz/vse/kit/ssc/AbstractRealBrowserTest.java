package cz.vse.kit.ssc;

import org.apache.commons.lang3.SystemUtils;

public abstract class AbstractRealBrowserTest {

    static {
        if (SystemUtils.IS_OS_WINDOWS) {
            System.setProperty("webdriver.chrome.driver","lib/chromedriver.exe");
            System.setProperty("webdriver.gecko.driver","lib/geckodriver.exe");
        }
    }
}
