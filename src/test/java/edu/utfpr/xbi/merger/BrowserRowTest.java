package edu.utfpr.xbi.merger;

import java.io.BufferedReader;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class BrowserRowTest {
    @Test
    public void testBuildSingleRow() throws Exception {
        String row = "somefolder/,http://abobrinha.com,1,a,Android 2.0 - Chrome,220,1013,19,90,8,993,1080,360,3.0,0,12,/body[1]/div[1],12,13,14,15,16,Times";
        BrowserRow r = BrowserRow.readRow(row);
        assertEquals("http://abobrinha.com", r.getURL());
        assertEquals(1, r.getId());
        assertEquals("a", r.getTagName());
        assertEquals(220, r.getX());
        assertEquals(1013, r.getY());
        assertEquals(19, r.getHeight());
        assertEquals(90, r.getWidth());
        assertEquals(8, r.getParentX());
        assertEquals(993, r.getParentY());
        assertEquals(1080, r.getDeviceWidth());
        assertEquals(360, r.getViewportWidth());
        assertEquals(3.0, r.getDPI(), 0.01);
        assertEquals(0, r.getChildsNumber());
        assertEquals(12, r.getTextLength());
        assertEquals("/body[1]/div[1]", r.getXPath());
        assertEquals("Android 2.0 - Chrome", r.getBrowserName());
        assertEquals("somefolder/", r.getFolder());
        assertEquals(12, r.getPreviousSiblingLeft());
        assertEquals(13, r.getPreviousSiblingTop());
        assertEquals(14, r.getNextSiblingLeft());
        assertEquals(15, r.getNextSiblingTop());
        assertEquals(16, r.getTextNodes());
        assertEquals("Times", r.getFontFamily());
    }

    @Test
    public void testBuildSingleRowWithDifferentValues() throws Exception {
        String row = "results/chrome/,http://pepino.com,2,section,iOS 4.3.2 - Safari,113,12,49,100,98,7,960,320,1.8,9,133,/body[2]/a,12,13,14,15,16,Times";
        BrowserRow r = BrowserRow.readRow(row);
        assertEquals("http://pepino.com", r.getURL());
        assertEquals(2, r.getId());
        assertEquals("section", r.getTagName());
        assertEquals(113, r.getX());
        assertEquals(12, r.getY());
        assertEquals(49, r.getHeight());
        assertEquals(100, r.getWidth());
        assertEquals(98, r.getParentX());
        assertEquals(7, r.getParentY());
        assertEquals(960, r.getDeviceWidth());
        assertEquals(320, r.getViewportWidth());
        assertEquals(1.8, r.getDPI(), 0.01);
        assertEquals(9, r.getChildsNumber());
        assertEquals(133, r.getTextLength());
        assertEquals("/body[2]/a", r.getXPath());
        assertEquals("iOS 4.3.2 - Safari", r.getBrowserName());
        assertEquals("results/chrome/", r.getFolder());
    }

    @Test
    public void testGetScreenshotPath1 () throws Exception {
        String row = "results/chrome/,bla,2,section,iOS,113,12,49,100,98,7,960,320,1.8,0,133,/body[2]/div[5],12,13,14,15,16,Times";
        BrowserRow r = spy(BrowserRow.readRow(row));
        BufferedImage completeImage = mock(BufferedImage.class),
                      targetScreenshot = mock(BufferedImage.class);
        File f = mock(File.class),
             result;

        doReturn(1080).when(completeImage).getWidth();
        doReturn(1080).when(completeImage).getHeight();
        doReturn(completeImage).when(r)._getCompleteScreenshot();
        doReturn(targetScreenshot).when(completeImage).getSubimage(113, 12, 100, 49);
        doNothing().when(r)._saveScreenshot(targetScreenshot, "results/chrome/2.png");
        doReturn(f).when(r).getFile(anyString());

        result = r.getScreenshot();
        assertEquals(f, result);
        verify(r).getFile("results/chrome/2.png");
        verify(r)._saveScreenshot(targetScreenshot, "results/chrome/2.png");
    }

    @Test
    public void testGetScreenshotPath2 () throws Exception {
        String row = "somefolder/,abr,1,a,Android,220,1013,19,90,8,993,1080,360,3.0,0,12,/body[1]/div[3]/span[3],12,13,14,15,16,Times";
        BrowserRow r = spy(BrowserRow.readRow(row));
        BufferedImage completeImage = mock(BufferedImage.class),
                      targetScreenshot = mock(BufferedImage.class);
        File f = mock(File.class),
             result;

        doReturn(1080).when(completeImage).getWidth();
        doReturn(1080).when(completeImage).getHeight();
        doReturn(completeImage).when(r)._getCompleteScreenshot();
        doReturn(targetScreenshot).when(completeImage).getSubimage(220, 1013, 90, 19);
        doNothing().when(r)._saveScreenshot(targetScreenshot, "somefolder/1.png");
        doReturn(f).when(r).getFile(anyString());

        result = r.getScreenshot();
        assertEquals(f, result);
        verify(r).getFile("somefolder/1.png");
        verify(r)._saveScreenshot(targetScreenshot, "somefolder/1.png");
    }

    @Test
    public void testGetScreenshotOutsideRaster () throws Exception {
        String row = "somefolder/,abr,1,a,Android,1070,1013,90,80,8,993,1080,360,3.0,0,12,/body[1]/div[3]/span[3],12,13,14,15,16,Times";
        BrowserRow r = spy(BrowserRow.readRow(row));
        BufferedImage completeImage = mock(BufferedImage.class),
                      targetScreenshot = mock(BufferedImage.class),
                      wrongScreenshot = mock(BufferedImage.class);
        File f = mock(File.class),
             result;
        RasterFormatException ex = mock(RasterFormatException.class);

        doReturn(1080).when(completeImage).getWidth();
        doReturn(1080).when(completeImage).getHeight();
        doReturn(completeImage).when(r)._getCompleteScreenshot();
        doReturn(wrongScreenshot).when(completeImage).getSubimage(1070, 1013, 80, 90);
        doReturn(targetScreenshot).when(completeImage).getSubimage(1070, 1013, 10, 67);
        doNothing().when(r)._saveScreenshot(targetScreenshot, "somefolder/1.png");
        doReturn(f).when(r).getFile(anyString());

        result = r.getScreenshot();
        assertEquals(f, result);
        verify(r).getFile("somefolder/1.png");
        verify(r)._saveScreenshot(targetScreenshot, "somefolder/1.png");
    }

    @Test
    public void testGetScreenshotOutsideRaster2 () throws Exception {
        String row = "somefolder/,abr,1,a,Android,1081,1081,90,80,8,993,1080,360,3.0,0,12,/body[1]/div[3]/span[3],12,13,14,15,16,Times";
        BrowserRow r = spy(BrowserRow.readRow(row));
        BufferedImage completeImage = mock(BufferedImage.class),
                      targetScreenshot = mock(BufferedImage.class),
                      wrongScreenshot = mock(BufferedImage.class);
        File f = mock(File.class),
             result;
        RasterFormatException ex = mock(RasterFormatException.class);

        doReturn(1080).when(completeImage).getWidth();
        doReturn(1080).when(completeImage).getHeight();
        doReturn(completeImage).when(r)._getCompleteScreenshot();
        doReturn(wrongScreenshot).when(completeImage).getSubimage(1081, 1081, 80, 90);
        doNothing().when(r)._saveScreenshot(targetScreenshot, "somefolder/1.png");
        doReturn(f).when(r).getFile(anyString());

        result = r.getScreenshot();
        assertNull(result);
    }

    @Test
    public void testGetScreenshotOutsideRaster3 () throws Exception {
        String row = "somefolder/,abr,1,a,Android,-1,-1,90,80,8,993,1080,360,3.0,0,12,/body[1]/div[3]/span[3],12,13,14,15,16,Times";
        BrowserRow r = spy(BrowserRow.readRow(row));
        BufferedImage completeImage = mock(BufferedImage.class),
                      targetScreenshot = mock(BufferedImage.class),
                      wrongScreenshot = mock(BufferedImage.class);
        File f = mock(File.class),
             result;
        RasterFormatException ex = mock(RasterFormatException.class);

        doReturn(1080).when(completeImage).getWidth();
        doReturn(1080).when(completeImage).getHeight();
        doReturn(completeImage).when(r)._getCompleteScreenshot();
        doReturn(wrongScreenshot).when(completeImage).getSubimage(-1, -1, 80, 90);
        doReturn(targetScreenshot).when(completeImage).getSubimage(0, 0, 80, 90);
        doNothing().when(r)._saveScreenshot(targetScreenshot, "somefolder/1.png");
        doReturn(f).when(r).getFile(anyString());

        result = r.getScreenshot();
        assertEquals(f, result);
        verify(r).getFile("somefolder/1.png");
        verify(r)._saveScreenshot(targetScreenshot, "somefolder/1.png");
    }

    @Test
    public void testGetScreenshotOutsideRaster4 () throws Exception {
        String row = "somefolder/,abr,1,a,Android,-81,-91,90,80,8,993,1080,360,3.0,0,12,/body[1]/div[3]/span[3],12,13,14,15,16,Times";
        BrowserRow r = spy(BrowserRow.readRow(row));
        BufferedImage completeImage = mock(BufferedImage.class),
                      targetScreenshot = mock(BufferedImage.class),
                      wrongScreenshot = mock(BufferedImage.class);
        File f = mock(File.class),
             result;
        RasterFormatException ex = mock(RasterFormatException.class);

        doReturn(1080).when(completeImage).getWidth();
        doReturn(1080).when(completeImage).getHeight();
        doReturn(completeImage).when(r)._getCompleteScreenshot();
        doReturn(targetScreenshot).when(completeImage).getSubimage(0, 0, 80, 90);
        doNothing().when(r)._saveScreenshot(targetScreenshot, "somefolder/1.png");
        doReturn(f).when(r).getFile(anyString());

        result = r.getScreenshot();
        assertNull(result);
    }

    @Test
    public void testGetScreenshotOutsideRaster5 () throws Exception {
        String row = "somefolder/,abr,1,a,Android,-81,0,90,80,8,993,1080,360,3.0,0,12,/body[1]/div[3]/span[3],12,13,14,15,16,Times";
        BrowserRow r = spy(BrowserRow.readRow(row));
        BufferedImage completeImage = mock(BufferedImage.class),
                      targetScreenshot = mock(BufferedImage.class),
                      wrongScreenshot = mock(BufferedImage.class);
        File f = mock(File.class),
             result;
        RasterFormatException ex = mock(RasterFormatException.class);

        doReturn(1080).when(completeImage).getWidth();
        doReturn(1080).when(completeImage).getHeight();
        doReturn(completeImage).when(r)._getCompleteScreenshot();
        doReturn(targetScreenshot).when(completeImage).getSubimage(0, 0, 80, 90);
        doNothing().when(r)._saveScreenshot(targetScreenshot, "somefolder/1.png");
        doReturn(f).when(r).getFile(anyString());

        result = r.getScreenshot();
        assertNull(result);
    }

    @Test
    public void testGetScreenshotOutsideRaster6_negative_or_zero_height () throws Exception {
        String row = "somefolder/,abr,1,a,Android,30,40,0,0,8,993,1080,360,3.0,0,12,/body[1]/div[3]/span[3],12,13,14,15,16,Times";
        BrowserRow r = spy(BrowserRow.readRow(row));
        BufferedImage completeImage = mock(BufferedImage.class),
                      targetScreenshot = mock(BufferedImage.class);
        File f = mock(File.class),
             result;
        RasterFormatException ex = mock(RasterFormatException.class);

        doReturn(1080).when(completeImage).getWidth();
        doReturn(1080).when(completeImage).getHeight();
        doReturn(completeImage).when(r)._getCompleteScreenshot();
        doThrow(new RasterFormatException("Should not be here with 0 height or width"))
            .when(completeImage).getSubimage(anyInt(), anyInt(), anyInt(), anyInt());
        doNothing().when(r)._saveScreenshot(targetScreenshot, "somefolder/1.png");
        doReturn(f).when(r).getFile(anyString());

        result = r.getScreenshot();
        assertNull(result);
    }

    @Test
    public void testGetScreenshotOutsideRaster7_negative_or_zero_height_and_width () throws Exception {
        String row = "somefolder/,abr,1,a,Android,1080,1080,1080,1080,8,993,1080,360,3.0,0,12,/body[1]/div[3]/span[3],12,13,14,15,16,Times";
        BrowserRow r = spy(BrowserRow.readRow(row));
        BufferedImage completeImage = mock(BufferedImage.class),
                      targetScreenshot = mock(BufferedImage.class);
        File f = mock(File.class),
             result;
        RasterFormatException ex = mock(RasterFormatException.class);

        doReturn(1080).when(completeImage).getWidth();
        doReturn(1080).when(completeImage).getHeight();
        doReturn(completeImage).when(r)._getCompleteScreenshot();
        doThrow(new RasterFormatException("Should not be here with 0 height or width"))
            .when(completeImage).getSubimage(anyInt(), anyInt(), anyInt(), anyInt());
        doNothing().when(r)._saveScreenshot(targetScreenshot, "somefolder/1.png");
        doReturn(f).when(r).getFile(anyString());

        result = r.getScreenshot();
        assertNull(result);
    }

    @Test
    public void testGetScreenshotWith10Childs () throws Exception {
        String row = "somefolder/,http://abobrinha.com,1,a,Android 2.0 - Chrome,113,12,49,100,98,7,960,320,1.8,10,133,/body[1]/div[1],12,13,14,15,16,Times";
        BrowserRow r = spy(BrowserRow.readRow(row));
        BufferedImage completeImage = mock(BufferedImage.class),
                      targetScreenshot = mock(BufferedImage.class);
        File f = mock(File.class),
             result;

        assertEquals("http://abobrinha.com", r.getURL());
        assertEquals(10, r.getChildsNumber());

        doReturn(1080).when(completeImage).getWidth();
        doReturn(1080).when(completeImage).getHeight();
        doReturn(completeImage).when(r)._getCompleteScreenshot();
        doReturn(targetScreenshot).when(completeImage).getSubimage(113, 12, 100, 49);
        doNothing().when(r)._saveScreenshot(targetScreenshot, "somefolder/1.png");
        doReturn(f).when(r).getFile(anyString());

        result = r.getScreenshot();
        assertNull(result);
        //assertEquals(f, result);
        //verify(r).getFile("somefolder/1.png");
        //verify(r)._saveScreenshot(targetScreenshot, "somefolder/1.png");
    }

    @Test
    public void testGetScreenshotWith15Childs () throws Exception {
        String row = "somefolder/,http://abobrinha.com,1,a,Android 2.0 - Chrome,113,12,49,100,98,7,960,320,1.8,15,133,/body[1]/div[1],12,13,14,15,16,Times";
        BrowserRow r = spy(BrowserRow.readRow(row));
        BufferedImage completeImage = mock(BufferedImage.class),
                      targetScreenshot = mock(BufferedImage.class);
        File f = mock(File.class),
             result;

        assertEquals("http://abobrinha.com", r.getURL());
        assertEquals(15, r.getChildsNumber());

        doReturn(1080).when(completeImage).getWidth();
        doReturn(1080).when(completeImage).getHeight();
        doReturn(completeImage).when(r)._getCompleteScreenshot();
        doReturn(targetScreenshot).when(completeImage).getSubimage(113, 12, 100, 49);
        doNothing().when(r)._saveScreenshot(targetScreenshot, "somefolder/1.png");
        doReturn(f).when(r).getFile(anyString());

        result = r.getScreenshot();
        assertNull(result);
        //assertEquals(f, result);
        //verify(r).getFile("somefolder/1.png");
        //verify(r)._saveScreenshot(targetScreenshot, "somefolder/1.png");
    }

    @Test
    public void testGetScreenshotWith20Childs () throws Exception {
        String row = "somefolder/,http://abobrinha.com,1,a,Android 2.0 - Chrome,113,12,49,100,98,7,960,320,1.8,20,133,/body[1]/div[1],12,13,14,15,16,Times";
        BrowserRow r = spy(BrowserRow.readRow(row));
        BufferedImage completeImage = mock(BufferedImage.class),
                      targetScreenshot = mock(BufferedImage.class);
        File f = mock(File.class),
             result;

        assertEquals("http://abobrinha.com", r.getURL());
        assertEquals(20, r.getChildsNumber());

        doReturn(1080).when(completeImage).getWidth();
        doReturn(1080).when(completeImage).getHeight();
        doReturn(completeImage).when(r)._getCompleteScreenshot();
        doReturn(targetScreenshot).when(completeImage).getSubimage(113, 12, 100, 49);
        doNothing().when(r)._saveScreenshot(targetScreenshot, "somefolder/1.png");
        doReturn(f).when(r).getFile(anyString());

        result = r.getScreenshot();
        assertNull(result);
        //assertEquals(f, result);
        //verify(r).getFile("somefolder/1.png");
        //verify(r)._saveScreenshot(targetScreenshot, "somefolder/1.png");
    }

    @Test
    public void testGetScreenshotWith21Childs () throws Exception {
        String row = "somefolder/,http://abobrinha.com,1,a,Android 2.0 - Chrome,220,1013,19,90,8,993,1080,360,3.0,21,12,/body[1]/div[1],12,13,14,15,16,Times";
        BrowserRow r = spy(BrowserRow.readRow(row));
        assertEquals("http://abobrinha.com", r.getURL());
        assertEquals(21, r.getChildsNumber());
        assertNull(r.getScreenshot());
    }
}
