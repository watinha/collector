package edu.utfpr.xbi.collector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import edu.utfpr.xbi.collector.writer.Writer;
import edu.utfpr.xbi.collector.JSCodes;

@RunWith(Parameterized.class)
public class CrawlTest {
    @Parameters
    public static Collection<Object[]> data () {
        return Arrays.asList(new Object[][] {
            {0}, {1}, {5}
        });
    }

    private int size;

    public CrawlTest (int size) {
        this.size = size;
    }

    @Test
    public void test_crawl_webpage_with_n_webelements () throws Exception {
        BrowserCollector collector = spy(new BrowserCollector());
        Writer writer = mock(Writer.class);
        JavascriptExecutor executor = mock(JavascriptExecutor.class);
        Integer size = new Integer(this.size);
        WebElement target, parent;
        List <WebElement> list = new ArrayList <WebElement> ();
        List <WebElement> parentList = new ArrayList <WebElement> ();
        JSCodes js = new JSCodes();

        doReturn(size).when(executor).executeScript(JSCodes.QUERY_ALL_ELEMENTS);
        doNothing().when(writer).close();
        doNothing().when(collector)._takeScreenshot();
        collector.setWriter(writer);
        for (int i = 0; i < size.intValue(); i++) {
            target = mock(WebElement.class);
            parent = mock(WebElement.class);
            list.add(target);
            doReturn(target).when(executor).executeScript(String.format(JSCodes.GET_ELEMENT, i));
            parentList.add(parent);
            doReturn(parent).when(executor).executeScript(String.format(JSCodes.GET_PARENT, i));
            doNothing().when(collector)._saveWebElement(target, parent);
        }

        collector.setExecutor(executor);
        collector.crawl();

        verify(executor).executeScript(JSCodes.QUERY_ALL_ELEMENTS);
        verify(writer).close();
        verify(collector)._takeScreenshot();
        for (int j = 0; j < size.intValue(); j++) {
            verify(executor).executeScript(String.format(JSCodes.GET_ELEMENT, j));
            verify(executor).executeScript(String.format(JSCodes.GET_PARENT, j));
            verify(collector)._saveWebElement(list.get(j), parentList.get(j));
        }
    }
}
