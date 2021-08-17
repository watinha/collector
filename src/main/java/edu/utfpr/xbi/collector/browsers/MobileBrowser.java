package edu.utfpr.xbi.collector.browsers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import edu.utfpr.xbi.collector.writer.SimpleWriter;
import edu.utfpr.xbi.collector.BrowserCollector;
import edu.utfpr.xbi.collector.BrowserCollectorJS;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.coordinates.CoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class MobileBrowser implements Browsers {
	private DesiredCapabilities capabilities;
	private String appium_uri;
	private String folder;
	private int header;
	private float dpr = -1;
	private String device;
	private CoordsProvider provider;
    private int viewportWidth;
    private int deviceWidth;
	public MobileBrowser (DesiredCapabilities capabilities, String appium_uri,
					  String folder, int header, String device, CoordsProvider provider) {
		this.capabilities = capabilities;
		this.appium_uri = appium_uri;
		this.folder = folder;
		this.header = header;
		this.device = device;
		this.provider = provider;
	}

	public MobileBrowser (DesiredCapabilities capabilities, String appium_uri,
					  String folder, int header, String device, CoordsProvider provider, float dpr) {
		this.capabilities = capabilities;
		this.appium_uri = appium_uri;
		this.folder = folder;
		this.header = header;
		this.device = device;
		this.provider = provider;
    this.dpr = dpr;
	}

	public BrowserCollector createCollector (String url) throws Exception {
        String browserName = this.capabilities.getCapability("platformName") + " " +
            this.capabilities.getCapability("platformVersion") + " - " +
            this.capabilities.getCapability("browserName") + " -- " + this.device;
        float dpr = this.dpr;
		WebDriver driver = new RemoteWebDriver(new URL(this.appium_uri), this.capabilities);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        driver.get(url);

        Thread.sleep(5000);
        //executor.executeScript(
        //    "window.$ = undefined; window.jQuery = undefined;");
        //executor.executeScript(IOUtils.toString(Thread.currentThread().getContextClassLoader()
        //            .getResourceAsStream("js/jquery.js"), StandardCharsets.UTF_8));
        //executor.executeScript(IOUtils.toString(Thread.currentThread().getContextClassLoader()
        //            .getResourceAsStream("js/visibility.js"), StandardCharsets.UTF_8));

        Thread.sleep(3000);

        System.out.println(String.format("=== %s  ===", this.device));
        File screenshot2 = new File(this.folder + "complete-resolution.png");
        File temp = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(temp, screenshot2);
        temp.delete();
        BufferedImage std_image = ImageIO.read(screenshot2);
        this.deviceWidth = std_image.getWidth();
        this.viewportWidth = (int)((Number) executor.executeScript(
            "return window.innerWidth || " +
            "document.documentElement.clientWidth || " +
            "document.getElementsByTagName('body')[0].clientWidth;")).floatValue();
        if (dpr == -1) {
            dpr = ((float)deviceWidth) / ((float)viewportWidth);
        }
        System.out.println(deviceWidth);
        System.out.println(viewportWidth);
        System.out.println(dpr);

        executor.executeScript(
            "return (function () {" +
            "    var all = document.querySelectorAll(\"*\");" +
            "    for (var i = 0; i < all.length; i++) {" +
            "    	var s = window.getComputedStyle(all[i], null);" +
            "       if (s.position.search(\"fixed\") >= 0){" +
            "            all[i].style.cssText = \"position:absolute !important;\";" +
            "       }" +
            "    }" +
            "    return \"\";" +
            "})();"
        );

        AShot ashot = new AShot().shootingStrategy(
			ShootingStrategies.viewportRetina(100, this.header, 0, dpr))
			.coordsProvider(provider);

        BrowserCollectorJS collector = new BrowserCollectorJS();
        SimpleWriter writer = new SimpleWriter(this.folder, url, this.deviceWidth,
                                               this.viewportWidth, dpr, browserName);
        writer.setWriter(new FileWriter(this.folder + "results.csv"));
        writer.setProvider(provider);
        writer.setAShot(ashot);
        writer.setExecutor(executor);
        collector.setDriver(driver);
        collector.setWriter(writer);
        collector.setExecutor(executor);

        return collector;
	}

}
