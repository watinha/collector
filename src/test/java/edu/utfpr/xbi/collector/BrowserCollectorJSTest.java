import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import edu.utfpr.xbi.collector.writer.SimpleWriter;
import edu.utfpr.xbi.collector.JSCodes;
import edu.utfpr.xbi.collector.BrowserCollector;
import edu.utfpr.xbi.collector.BrowserCollectorJS;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;


@RunWith(JUnit4.class)
public class BrowserCollectorJSTest {

    @Test
    public void test_constructor_with_webdriver () {
        BrowserCollectorJS collector = new BrowserCollectorJS();
        Assert.assertNotNull(collector);
        Assert.assertTrue(collector instanceof BrowserCollector);
    }

    @Test
    public void test_crawl_should_call_executor_with_collect_script () throws Exception {
        BrowserCollectorJS collector = new BrowserCollectorJS();
        SimpleWriter writer = mock(SimpleWriter.class);
        WebDriver driver = mock(WebDriver.class);

        doNothing().when(writer).saveScreenshot(driver);
        doNothing().when(writer).saveAllJS();
        doNothing().when(writer).close();

        collector.setDriver(driver);
        collector.setWriter(writer);
        collector.crawl();

        verify(writer).saveScreenshot(driver);
        verify(writer).saveAllJS();
        verify(writer).close();
    }

}
