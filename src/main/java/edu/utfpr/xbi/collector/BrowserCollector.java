package edu.utfpr.xbi.collector;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import edu.utfpr.xbi.collector.writer.Writer;
import edu.utfpr.xbi.collector.JSCodes;

public class BrowserCollector {
    private WebDriver driver;
    private Writer writer;
    private JavascriptExecutor executor;
    public WebDriver getDriver () { return this.driver; }
    public void setDriver (WebDriver driver) { this.driver = driver; }
    public Writer getWriter() { return writer; }
    public void setWriter(Writer writer) { this.writer = writer; }
    public void setExecutor(JavascriptExecutor executor){ this.executor = executor; }

    public void _takeScreenshot () throws Exception {
        this.writer.saveScreenshot(this.driver);
    }

    public void _saveWebElement (WebElement target, WebElement parent) throws Exception {
        if (parent == null)
            return ;
        this.writer.saveWebElement(this.driver, target, parent);
    }

    public void crawl () throws Exception {
        WebElement target, parent;
        this._takeScreenshot();
        int size = Integer.parseInt(
                this.executor.executeScript(JSCodes.QUERY_ALL_ELEMENTS).toString());

        for (int i = (size-1); i >= 0; i--) {
            try {
                target = (WebElement)(this.executor.executeScript(
                            String.format(JSCodes.GET_ELEMENT, i)));
                parent = (WebElement)(this.executor.executeScript(
                            String.format(JSCodes.GET_PARENT, i)));
                this._saveWebElement(target, parent);
            } catch (WebDriverException e) {
                System.out.println("Element not stored in the cache anymore... " + i);
            }
        }
        this.writer.close();
    }
}
