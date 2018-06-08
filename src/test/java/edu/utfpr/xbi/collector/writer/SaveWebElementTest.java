package edu.utfpr.xbi.collector.writer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import edu.utfpr.xbi.collector.JSCodes;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.Coords;
import ru.yandex.qatools.ashot.coordinates.CoordsProvider;
import ru.yandex.qatools.ashot.cropper.ImageCropper;

import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class SaveWebElementTest {
    @Parameters
    public static Collection<Object[]> data () {
        return Arrays.asList(new Object[][] {
            {"results/ios/", 12,13,6,7,42,43,"DIV", "pepino.com", 1, true, 1080, 360, 3f, 0, "abobrinha legal legal legal", "chrome",""},
            {"r/android/", 42,32,9,13,55,59,"SPAN", "abacaxi.com", 2, true, 640, 320, 2f, 0, "pepino supimpa supimpa", "ios chrome","d"},
            {"s/supimpa/", 0,0,7,79,120,2,"IMG", "legal.is", 5, true, 800, 400, 2f, 0, "nada", "safari ios","/body[1]/div[1]"},
            {"r/iphone/", 0,0,7,20,120,2,"IMG", "legal.is", 5, false, 1050, 150, 7f, 0, "fjiodsafjiodasjoi", "firefox","/body/div[2]/h1"},
            {"n/motog4/", 0,0,9,79,120,2,"IMG", "legal.is", 5, false, 1080, 540, 2f, 0, "", "Android chrome","/body[1]/header[1]/div"},
            {"sup/", 0,0,2,79,120,2,"SPAN", "legal.is", 5, false, 2000, 1000, 2f, 0, "aoifdioajfioajsdiof", "Android Firefox","/div/b"},
            {"sup/", 0,0,2,79,120,2,"SCRIPT", "legal.is", 5, false, 2000, 1000, 2f, 0, "aoifdioajfioajsdiof", "Android Firefox","/span"},
            {"sup/", 0,0,2,79,120,2,"script", "legal.is", 5, false, 2000, 1000, 2f, 0, "aoifdioajfioajsdiof", "Android Firefox","/span"},
            {"sup/", 0,0,2,79,120,2,"STYLE", "legal.is", 5, false, 2000, 1000, 2f, 0, "aoifdioajfioajsdiof", "Android Firefox","/body"},
            {"sup/", 0,0,2,79,120,2,"LINK", "legal.is", 5, false, 2000, 1000, 2f, 0, "aoifdioajfioajsdiof", "Android Firefox","/video"},
            {"sup/", 0,0,2,79,120,2,"NOSCRIPT", "legal.is", 5, false, 2000, 1000, 2f, 0, "aoifdioajfioajsdiof", "Android Firefox","/a"},
            {"sup/", 0,0,2,79,120,2,"DIV", "legal.is", 5, false, 2000, 1000, 2f, 10, "aoifdioajfioajsdiof", "Android Firefox","/a"},
            {"sup/", 0,0,2,79,120,2,"DIV", "legal.is", 5, false, 2000, 1000, 2f, 20, "aoifdioajfioajsdiof", "Android Firefox","/a"},
            {"results/ios/", 12,13,0,19,42,43,"DIV", "pepino.com", 1, false, 1080, 360, 3f, 0, "abobrinha legal legal legal", "chrome",""},
            {"r/android/", 42,32,9,0,55,59,"SPAN", "abacaxi.com", 2, false, 640, 320, 2f, 0, "pepino supimpa supimpa", "ios chrome","d"},
        });
    }

    private int x;
    private int y;
    private int x_parent;
    private int y_parent;
    private int height;
    private int width;
    private int iteration;
    private String tagName;
    private String url;
    private boolean empty;
    private int deviceWidth = 1080;
    private int viewportWidth = 360;
    private float dpi = 3;
    private int childsNumber;
    private String textContent;
    private String browser;
    private String folder;
    private String xpath;

    public SaveWebElementTest (String folder, int x, int y, int height, int width, int x_parent, int y_parent,
                               String tagName, String url, int iteration, boolean empty,
                               int deviceWidth, int viewportWidth, float dpi, int childsNumber,
                               String textContent, String browser, String xpath) {
        this.folder = folder;
        this.x = x;
        this.y = y;
        this.x_parent = x_parent;
        this.y_parent = y_parent;
        this.height = height;
        this.width = width;
        this.tagName = tagName;
        this.url = url;
        this.iteration = iteration;
        this.empty = empty;
        this.deviceWidth = deviceWidth;
        this.viewportWidth = viewportWidth;
        this.dpi = dpi;
        this.childsNumber = childsNumber;
        this.textContent = textContent;
        this.browser = browser;
        this.xpath = xpath;
    }

    @Test
    public void test_saveWebElement () throws IOException {
        SimpleWriter writer = spy(new SimpleWriter(this.folder, this.url, this.deviceWidth,
                                             this.viewportWidth, this.dpi, this.browser));

        WebDriver driver = mock(WebDriver.class);
        AShot ashot = mock(AShot.class);
        writer.setAShot(ashot);
        JavascriptExecutor executor = mock(JavascriptExecutor.class);
        writer.setExecutor(executor);

        InOrder inorder = inOrder(ashot, writer);

        for (int i = 0; i < this.iteration; i++) {
            Screenshot screenshot = mock(Screenshot.class);
            BufferedImage image = mock(BufferedImage.class);
            WebElement target = mock(WebElement.class),
                       parent = mock(WebElement.class);
            List <WebElement> childs = mock(List.class);
            Dimension dimension = mock(Dimension.class);
            CoordsProvider provider = mock(CoordsProvider.class);
            Coords coords = mock(Coords.class),
                   coords_parent = mock(Coords.class);

            doReturn(image).when(screenshot).getImage();
            doReturn(this.height).when(dimension).getHeight();
            doReturn(this.width).when(dimension).getWidth();
            doReturn(coords).when(provider).ofElement(driver, target);
            doReturn(this.empty).when(coords).isEmpty();
            doReturn((double)this.x).when(coords).getX();
            doReturn((double)this.y).when(coords).getY();
            doReturn(coords_parent).when(provider).ofElement(driver, parent);
            doReturn((double)this.x_parent).when(coords_parent).getX();
            doReturn((double)this.y_parent).when(coords_parent).getY();
            doReturn(this.tagName).when(target).getTagName();
            doReturn(dimension).when(target).getSize();
            doReturn(childs).when(target).findElements(By.cssSelector("*"));
            doReturn(this.childsNumber).when(childs).size();
            doReturn(this.textContent).when(target).getText();
            doReturn(this.xpath).when(executor).executeScript(JSCodes.GET_XPATH, target);

            if (!this.empty && !this.tagName.equals("SCRIPT") && !this.tagName.equals("STYLE") &&
                               !this.tagName.equals("LINK") && !this.tagName.equals("NOSCRIPT") &&
                               !this.tagName.equals("script") && this.height != 0 && this.width != 0) {
                doNothing().when(writer)._saveData(anyString());
            } else {
                doThrow(new IOException("Should not save this element (0 dimention)...")).when(writer)._saveData(anyString());
            }

            writer.setProvider(provider);
            writer.saveWebElement(driver, target, parent);

            if (!this.empty && !this.tagName.equals("SCRIPT") && !this.tagName.equals("STYLE")
                            && !this.tagName.equals("LINK") && !this.tagName.equals("NOSCRIPT")
                            && !this.tagName.equals("script") && this.height != 0 && this.width != 0) {
                verify(writer)._saveData(this.folder + "," + this.url + ","+ (i+1) + "," +
                                         this.tagName + "," + this.browser + "," +
                                         this.x + "," + this.y + "," +
                                         this.height + "," + this.width + "," +
                                         this.x_parent + "," + this.y_parent + "," +
                                         this.deviceWidth + "," + this.viewportWidth + "," +
                                         this.dpi + "," + this.childsNumber + "," +
                                         this.textContent.length() + "," + this.xpath);
            }
        }
    }
}
