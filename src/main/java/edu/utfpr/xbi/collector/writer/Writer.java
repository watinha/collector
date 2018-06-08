package edu.utfpr.xbi.collector.writer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface Writer {
    public void saveScreenshot (WebDriver driver) throws Exception;
    public void saveWebElement (WebDriver driver, WebElement target, WebElement parent) throws Exception;
    public void close () throws Exception;
}
