package edu.utfpr.xbi.collector.writer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

import static org.mockito.Mockito.*;

import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;

@RunWith(JUnit4.class)
public class SimpleWriterTest {

    @Test
    public void test_constructor_exists () {
        String path = "resulst1/",
               url = "abobrinha.com";
        int deviceWidth = 1080,
            viewportWidth = 360;
        float dpi = 3;
        Writer writer = spy(new SimpleWriter(path, url, deviceWidth,
                                             viewportWidth, dpi, "chrome"));

        Assert.assertNotNull(writer);
    }

    @Test
    public void test_saveScreenshot () throws IOException {
        String path = "results1/",
               url = "pepino.com/";
        int deviceWidth = 1080,
            viewportWidth = 360;
        float dpi = 3;
        SimpleWriter writer = spy(new SimpleWriter(path, url, deviceWidth,
                                             viewportWidth, dpi, "firefox"));
        Screenshot screenshot = mock(Screenshot.class);
        BufferedImage image = mock(BufferedImage.class);
        AShot ashot = mock(AShot.class);
        WebDriver driver = mock(WebDriver.class);

        doReturn(screenshot).when(ashot).takeScreenshot(driver);
        doReturn(image).when(screenshot).getImage();
        doNothing().when(writer)._saveCompleteScreenshot(
                image, "results1/complete.png");

        writer.setAShot(ashot);
        writer.saveScreenshot(driver);

        verify(writer)._saveCompleteScreenshot(
                image, "results1/complete.png");
    }

    @Test
    public void test__saveData () throws IOException {
        String path = "r/",
               url = "pepino.org/";
        int deviceWidth = 1080,
            viewportWidth = 360;
        float dpi = 3;
        SimpleWriter writer = spy(new SimpleWriter(path, url, deviceWidth,
                                             viewportWidth, dpi, "safari"));
        FileWriter mock = mock(FileWriter.class);
        writer.setWriter(mock);
        doNothing().when(mock).write(anyString());
        writer._saveData("legal legal legal");
        verify(mock).write("legal legal legal\n");
    }

    @Test
    public void test_close () throws IOException {
        String path = "r/",
               url = "pepino.org/";
        int deviceWidth = 1080,
            viewportWidth = 360;
        float dpi = 3;
        SimpleWriter writer = spy(new SimpleWriter(path, url, deviceWidth,
                                             viewportWidth, dpi, "opera"));
        FileWriter mock = mock(FileWriter.class);
        writer.setWriter(mock);
        doNothing().when(mock).close();
        writer.close();
        verify(mock).close();
    }

    @Test
    public void test_saveWebElement_throws_exception_of_no_webelement_in_cache () throws IOException {
        String path = "r/",
               url = "pepino.org/";
        int deviceWidth = 1080,
            viewportWidth = 360;
        float dpi = 3;
        WebDriver driver = mock(WebDriver.class);
        WebElement target = mock(WebElement.class),
                   parent = mock(WebElement.class);
        Dimension d = mock(Dimension.class);
        when(d.getHeight()).thenReturn(10);
        when(d.getWidth()).thenReturn(100);
        when(target.getSize()).thenReturn(d);

        SimpleWriter writer = new SimpleWriter(path, url, deviceWidth, viewportWidth, dpi, "opera");
        doThrow(new WebDriverException()).when(target).findElements(By.cssSelector("*"));
        writer.saveWebElement(driver, target, parent);
    }
}


