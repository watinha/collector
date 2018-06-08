package edu.utfpr.xbi.collector;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import edu.utfpr.xbi.collector.writer.Writer;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;


@RunWith(JUnit4.class)
public class BrowserCollectorTest {

    @Test
    public void test_constructor_with_webdriver () {
        BrowserCollector collector = new BrowserCollector();
        Assert.assertNotNull(collector);
    }

    @Test
    public void test_setDriver () {
        BrowserCollector collector = new BrowserCollector();
        WebDriver driver = mock(WebDriver.class);
        collector.setDriver(driver);
        Assert.assertEquals(driver, collector.getDriver());
    }

    @Test
    public void test_setWriter () {
        BrowserCollector collector = new BrowserCollector();
        Writer writerMock = mock(Writer.class);
        collector.setWriter(writerMock);
        Assert.assertEquals(writerMock, collector.getWriter());
    }

    @Test
    public void test_takeScreenshot () throws Exception {
        BrowserCollector collector = new BrowserCollector();
        WebDriver driverMock = mock(WebDriver.class);
        Writer writerMock = mock(Writer.class);

        collector.setDriver(driverMock);
        collector.setWriter(writerMock);

        doNothing().when(writerMock).saveScreenshot(driverMock);

        collector._takeScreenshot();
        verify(writerMock).saveScreenshot(driverMock);
    }

    @Test
    public void test_saveWebElement_with_parameter () throws Exception {
        BrowserCollector collector = new BrowserCollector();
        WebDriver driverMock = mock(WebDriver.class);
        Writer writerMock = mock(Writer.class);
        WebElement stub = mock(WebElement.class),
                   parentStub = mock(WebElement.class);

        collector.setDriver(driverMock);
        collector.setWriter(writerMock);

        doNothing().when(writerMock).saveWebElement(driverMock, stub, parentStub);

        collector._saveWebElement(stub, parentStub);
        verify(writerMock).saveWebElement(driverMock, stub, parentStub);
    }

    @Test
    public void test_saveWebElement_with_no_parent () throws Exception {
        BrowserCollector collector = new BrowserCollector();
        WebDriver driverMock = mock(WebDriver.class);
        Writer writerMock = mock(Writer.class);
        WebElement stub = mock(WebElement.class),
                   parentStub = null;

        collector.setDriver(driverMock);
        collector.setWriter(writerMock);

        doNothing().when(writerMock).saveWebElement(driverMock, stub, parentStub);

        collector._saveWebElement(stub, parentStub);
        verify(writerMock, never()).saveWebElement(driverMock, stub, parentStub);
    }

    @Test
    public void test_crawl_throws_webdriverException () throws Exception {
        BrowserCollector collector = spy(new BrowserCollector());
        JavascriptExecutor executorMock = mock(JavascriptExecutor.class);
        Writer writerMock = mock(Writer.class);
        WebElement stub = mock(WebElement.class),
                   parentStub = mock(WebElement.class);

        doReturn(2).when(executorMock).executeScript(JSCodes.QUERY_ALL_ELEMENTS);
        doNothing().when(collector)._takeScreenshot();
        doThrow(new WebDriverException()).when(executorMock).executeScript(
                String.format(JSCodes.GET_ELEMENT, 1));
        doReturn(stub).when(executorMock).executeScript(
                String.format(JSCodes.GET_ELEMENT, 0));
        doReturn(parentStub).when(executorMock).executeScript(
                String.format(JSCodes.GET_PARENT, 0));
        doNothing().when(collector)._saveWebElement(stub, parentStub);
        doNothing().when(writerMock).close();

        collector.setExecutor(executorMock);
        collector.setWriter(writerMock);
        collector.crawl();

        verify(collector)._saveWebElement(stub, parentStub);
    }
}
