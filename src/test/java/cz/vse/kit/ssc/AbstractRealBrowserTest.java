package cz.vse.kit.ssc;

import org.apache.commons.lang3.SystemUtils;

public abstract class AbstractRealBrowserTest {

	static {
		if (SystemUtils.IS_OS_WINDOWS) {
			System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
			System.setProperty("webdriver.gecko.driver", "lib/geckodriver.exe");
			System.setProperty("webdriver.firefox.marionette", "lib/geckodriver.exe");
		} else if (SystemUtils.IS_OS_MAC) {
			System.setProperty("webdriver.gecko.driver", "lib/geckodriver_mac");
			System.setProperty("webdriver.firefox.marionette", "lib/geckodriver_mac");
		} else {
			//Linux - test platform architecture
			if (SystemUtils.OS_ARCH.startsWith("arm")) {
				System.setProperty("webdriver.gecko.driver", "lib/geckodriver_arm");
				System.setProperty("webdriver.firefox.marionette", "lib/geckodriver_arm");
			} else {
				System.setProperty("webdriver.gecko.driver", "lib/geckodriver");
				System.setProperty("webdriver.firefox.marionette", "lib/geckodriver");
			}
		}
	}
}
