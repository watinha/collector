package edu.utfpr.xbi.collector;

import edu.utfpr.xbi.collector.writer.SimpleWriter;

import org.openqa.selenium.WebDriver;
import java.io.IOException;

public class BrowserCollectorJS extends BrowserCollector {
    private WebDriver driver;
    private SimpleWriter writer;
    public void setDriver(WebDriver driver){
        this.driver = driver;
    }
    public WebDriver getDriver () {
        return this.driver;
    }
    public void setWriter(SimpleWriter writer){
        this.writer = writer;
    }
    public void crawl () throws IOException {
        this.writer.saveScreenshot(this.driver);
        this.writer.saveAllJS();
        this.writer.close();
    }
}
