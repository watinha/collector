package edu.utfpr.xbi.collector.writer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import edu.utfpr.xbi.collector.JSCodes;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.Coords;
import ru.yandex.qatools.ashot.coordinates.CoordsProvider;
import ru.yandex.qatools.ashot.cropper.ImageCropper;

public class SimpleWriter implements Writer {

    private String path;
    private String url;
    private FileWriter writer = null;
    private int size = 0;
    private int count = 1;
    private AShot ashot;
    private int deviceWidth;
    private int viewportWidth;
    private float dpi;
    private CoordsProvider provider;
    private String browser;
    private JavascriptExecutor executor;
    public void setWriter(FileWriter writer) { this.writer = writer; }
    public void setAShot(AShot ashot) { this.ashot = ashot; }
    public void setProvider(CoordsProvider provider) { this.provider = provider; }
    public void setExecutor(JavascriptExecutor executor) { this.executor = executor; }

    public SimpleWriter (String path, String url, int deviceWidth,
                         int viewportWidth, float dpi, String browser) {
        this.path = path;
        this.url = url;
        this.deviceWidth = deviceWidth;
        this.viewportWidth = viewportWidth;
        this.dpi = dpi;
        this.browser = browser;
    }

    @Override
    public void saveScreenshot(WebDriver driver)
            throws IOException {
        String completePath = this.path + "complete.png";
        Screenshot screenshot = this.ashot.takeScreenshot(driver);
        this._saveCompleteScreenshot(screenshot.getImage(), completePath);
    }

    @Override
    public void saveWebElement(WebDriver driver, WebElement target, WebElement parent)
            throws IOException {
        int x, y,
            parentX, parentY,
            height, width, childsNumber;
        String tagName, text, xpath;
        Coords coords, coordsParent;

        try {
            height = target.getSize().getHeight();
            width = target.getSize().getWidth();

            if (height == 0 || width == 0) {
                this.count++;
                return ;
            }

            childsNumber = target.findElements(By.cssSelector("*")).size();
            tagName = target.getTagName();
            text = target.getText();
            xpath = this.executor.executeScript(JSCodes.GET_XPATH, target).toString();
            coords = this.provider.ofElement(driver, target);
            coordsParent = this.provider.ofElement(driver, parent);
            x = (int) coords.getX();
            y = (int) coords.getY();
            parentX = (int) coordsParent.getX();
            parentY = (int) coordsParent.getY();
        } catch (WebDriverException e) {
            System.out.println("Element not in cache anymore...");
            //e.printStackTrace();
            return ;
        }

        if (tagName.equalsIgnoreCase("SCRIPT") || tagName.equalsIgnoreCase("NOSCRIPT") ||
            tagName.equalsIgnoreCase("STYLE") || tagName.equalsIgnoreCase("LINK"))
            return ;
        if (coords.isEmpty()) {
            this.count++;
            return ;
        }

        this._saveData(String.format("%s,%s,%d,%s,%s,%d,%d,%d,%d,%d,%d,%d,%d,%.1f,%d,%d,%s",
                       this.path, this.url, (this.count++), tagName, this.browser,
                       x, y, height, width, parentX, parentY, this.deviceWidth,
                       this.viewportWidth, this.dpi, childsNumber, text.length(), xpath));
    }

    public void saveAllJS () throws IOException {
        int i = 0;
        this.size = Integer.parseInt(this.executor.executeScript(JSCodes.QUERY_ALL_ELEMENTS).toString());
        String result = this.executor.executeScript(
               JSCodes.COLLECTOR_JS, this.path, this.url, this.browser,
               this.deviceWidth, this.viewportWidth, this.dpi, i).toString();
        this._saveData(result);
    }

    public void _saveCompleteScreenshot (BufferedImage image, String path)
            throws IOException {
        ImageIO.write(image, "PNG", new File(path));
    }

    public void _saveData (String data) throws IOException {
        this.writer.write(data + "\n");
    }

    public void close() throws IOException {
       this.writer.close();
    }
}
