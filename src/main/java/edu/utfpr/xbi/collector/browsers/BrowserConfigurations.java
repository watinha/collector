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
        capabilities.setCapability("nativeWebScreenshot", true);
		return new MobileBrowser(capabilities, appium_uri, "results/motog4/",
							 header + 82, "Android - MotoG4", new WebDriverCoordsProvider(), -1f);
    }

	public static Browsers pixel_xl(String appium_uri, int header, float dpr)
			throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "pixel_xl");
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("nativeWebScreenshot", true);
		return new MobileBrowser(capabilities, appium_uri, "results/pixel_xl/",
							 header + 82, "Android API28 - Pixel", new WebDriverCoordsProvider(), -1f);
    }

	public static Browsers pixel(String appium_uri, int header, float dpr)
			throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "pixel");
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("nativeWebScreenshot", true);
		return new MobileBrowser(capabilities, appium_uri, "results/pixel/",
							 header + 82, "Android API28 - Pixel", new WebDriverCoordsProvider(), -1f);
    }

	public static Browsers motoZ2(String appium_uri, int header, float dpr)
			throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("browserName", "Chrome");
		return new MobileBrowser(capabilities, appium_uri, "results/motoz2/",
							 header, "Android - MotoZ2", new WebDriverCoordsProvider(), -1f);
    }

    public static Browsers iphoneSE (String appium_uri, int header, float dpr)
            throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone SE");
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("platformVersion", "13.1");
        //capabilities.setCapability("automationName", "XCUITest");
        //capabilities.setCapability("wdaLocalPort", "8102");
        //capabilities.setCapability("udid", "D2EE345E-342C-49AE-A91A-4F6B0C02A35F");
		return new MobileBrowser(capabilities, appium_uri, "results/iphonese/",
							 header + 70, "iOS - iPhoneSE", new JqueryCoordsProvider(), 2.0f);
    }

    public static Browsers iphone8 (String appium_uri, int header, float dpr)
            throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone 8");
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("platformVersion", "13.1");
        //capabilities.setCapability("automationName", "XCUITest");
        //capabilities.setCapability("wdaLocalPort", "8101");
        //capabilities.setCapability("udid", "CD726EF1-3A29-4EC0-A463-A7BD5FCC654B");
		return new MobileBrowser(capabilities, appium_uri, "results/iphone8/",
							 header + 70, "iOS - iPhone 8", new JqueryCoordsProvider(), 2.0f);
    }

    public static Browsers iphone8plus (String appium_uri, int header, float dpr)
            throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone 8 Plus");
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("platformVersion", "13.1");
        //capabilities.setCapability("automationName", "XCUITest");
        //capabilities.setCapability("wdaLocalPort", "8100");
        //capabilities.setCapability("udid", "0AAAF7EC-9608-4F27-A604-B4FFA584C011");
		return new MobileBrowser(capabilities, appium_uri, "results/iphone8plus/",
							 header + 70, "iOS - iPhone 8 Plus", new JqueryCoordsProvider(), dpr);
    }

    public static Browsers iphone12max (String appium_uri, int header, float dpr)
            throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone 12 Pro Max");
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("platformVersion", "14.5");
        capabilities.setCapability("automationName", "XCUITest");
        capabilities.setCapability("appium:noReset", true);
        //capabilities.setCapability("appium:useNewWDA", true);
        capabilities.setCapability("appium:usePrebuiltWDA", true);
        capabilities.setCapability("appium:wdaLocalPort", 8101);
        //capabilities.setCapability("udid", "CD726EF1-3A29-4EC0-A463-A7BD5FCC654B");
		return new MobileBrowser(capabilities, appium_uri, "results/iphone12max/",
							 header + 100, "iOS 14.5 - iPhone 12 Pro Max", new JqueryCoordsProvider(), 3.0f);
    }

    public static Browsers iphone12 (String appium_uri, int header, float dpr)
            throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone 12");
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("platformVersion", "14.5");
        capabilities.setCapability("automationName", "XCUITest");
        capabilities.setCapability("appium:noReset", true);
        //capabilities.setCapability("appium:useNewWDA", true);
        capabilities.setCapability("appium:usePrebuiltWDA", true);
        capabilities.setCapability("wdaLocalPort", 8102);
        //capabilities.setCapability("udid", "CD726EF1-3A29-4EC0-A463-A7BD5FCC654B");
		return new MobileBrowser(capabilities, appium_uri, "results/iphone12/",
							 header + 100, "iOS 14.5 - iPhone 12", new JqueryCoordsProvider(), 3.0f);
    }

    public static Browsers iphone12mini (String appium_uri, int header, float dpr)
            throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone 12 mini");
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("platformVersion", "14.5");
        capabilities.setCapability("automationName", "XCUITest");
        capabilities.setCapability("appium:noReset", true);
        //capabilities.setCapability("appium:useNewWDA", true);
        capabilities.setCapability("appium:usePrebuiltWDA", true);
        capabilities.setCapability("wdaLocalPort", 8103);
        //capabilities.setCapability("udid", "CD726EF1-3A29-4EC0-A463-A7BD5FCC654B");
		return new MobileBrowser(capabilities, appium_uri, "results/iphone12mini/",
							 header + 100, "iOS 14.5 - iPhone 12 mini", new JqueryCoordsProvider(), 3.0f);
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
