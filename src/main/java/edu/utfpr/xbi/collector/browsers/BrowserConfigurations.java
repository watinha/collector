package edu.utfpr.xbi.collector.browsers;

import org.openqa.selenium.remote.DesiredCapabilities;

import ru.yandex.qatools.ashot.coordinates.JqueryCoordsProvider;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;

public class BrowserConfigurations {

    public static Browsers safari(String appium_uri, int header, float dpr)
            throws Exception {
        DesiredCapabilities capabilities = DesiredCapabilities.safari();
        return new DesktopBrowser(capabilities, appium_uri, "results/safari/",
                            header, "MacOS Sierra 10.12.6 - MacBook Air 13 - Safari",
                            new JqueryCoordsProvider(), 1);
    }

    public static Browsers ie(String appium_uri, int header, float dpr)
            throws Exception {
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        return new DesktopBrowser(capabilities, appium_uri, "results/ie/",
                            header, "Windows 8.1 - Internet Explorer 11",
                            new WebDriverCoordsProvider(), 1);
    }

    public static Browsers chromeWin(String appium_uri, int header, float dpr)
            throws Exception {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        return new DesktopBrowser(capabilities, appium_uri, "results/chrome_win/",
                            header, "Windows 8.1 - Chrome 60.0.3112.113",
                            new WebDriverCoordsProvider(), 1);
    }

    public static Browsers firefoxWin(String appium_uri, int header, float dpr)
            throws Exception {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        return new DesktopBrowser(capabilities, appium_uri, "results/firefox_win/",
                            header, "Windows 8.1 - Firefox 53.0.3",
                            new WebDriverCoordsProvider(), 1);
    }

    public static Browsers operaWin(String appium_uri, int header, float dpr)
            throws Exception {
        DesiredCapabilities capabilities = DesiredCapabilities.opera();
        return new DesktopBrowser(capabilities, appium_uri, "results/opera_win/",
                            header, "Windows 8.1 - Opera 48.0",
                            new WebDriverCoordsProvider(), 1);
    }

    public static Browsers chromeMac(String appium_uri, int header, float dpr)
            throws Exception {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        return new DesktopBrowser(capabilities, appium_uri, "results/chrome_mac/",
                            header, "MacOS Sierra 10.12.6 - MacBook Air 13 - Chrome 60.0.3112.113",
                            new WebDriverCoordsProvider(), dpr);
    }

    public static Browsers firefoxMac(String appium_uri, int header, float dpr)
            throws Exception {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        return new DesktopBrowser(capabilities, appium_uri, "results/firefox_mac/",
                            header, "MacOS Sierra 10.12.6 - MacBook Air 13 - Firefox 56",
                            new WebDriverCoordsProvider(), dpr);
    }

	public static Browsers motoG4(String appium_uri, int header, float dpr)
			throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("browserName", "Chrome");
		return new MobileBrowser(capabilities, appium_uri, "results/motog4/",
							 header, "Android - MotoG4", new WebDriverCoordsProvider(), -1f);
    }

    public static Browsers iphoneSE (String appium_uri, int header, float dpr)
            throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone SE");
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("platformVersion", "11.0");
        capabilities.setCapability("automationName", "XCUITest");
		return new MobileBrowser(capabilities, appium_uri, "results/iphonese/",
							 header + 70, "iOS - iPhoneSE", new JqueryCoordsProvider(), 2.0f);
    }

    public static Browsers iphone8 (String appium_uri, int header, float dpr)
            throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone 8");
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("platformVersion", "11.2");
        capabilities.setCapability("automationName", "XCUITest");
		return new MobileBrowser(capabilities, appium_uri, "results/iphone8/",
							 header + 70, "iOS - iPhone 8", new JqueryCoordsProvider(), 2.0f);
    }

    public static Browsers iphone8plus (String appium_uri, int header, float dpr)
            throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone 8 Plus");
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("platformVersion", "11.2");
        capabilities.setCapability("automationName", "XCUITest");
		return new MobileBrowser(capabilities, appium_uri, "results/iphone8plus/",
							 header + 70, "iOS - iPhone 8 Plus", new JqueryCoordsProvider(), dpr);
    }

    public static Browsers ipadAir2 (String appium_uri, int header, float dpr)
            throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPad Air 2");
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("platformVersion", "11.2");
        capabilities.setCapability("automationName", "XCUITest");
		return new MobileBrowser(capabilities, appium_uri, "results/ipadAir2/",
							 header + 70, "iOS - iPad Air 2", new JqueryCoordsProvider(), dpr);
    }

    public static Browsers galaxyTabS2 (String appium_uri, int header, float dpr)
            throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Samsung Galaxy Tab S2");
        capabilities.setCapability("browserName", "Chrome");
		return new MobileBrowser(capabilities, appium_uri, "results/galaxyTabS2/",
							 header, "Samsung - Galaxy Tab S2", new WebDriverCoordsProvider(), dpr);
    }
}
