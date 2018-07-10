package edu.utfpr.xbi.merger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;
import java.math.BigInteger;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class BrowserRow {
    private String URL;
    private int id;
    private String tagName;
    private int x;
    private int y;
    private int height;
    private int width;
    private int parentX;
    private int parentY;
    private int deviceWidth;
    private int viewportWidth;
    private float DPI;
    private int childsNumber;
    private int textLength;
    private String browserName;
    private String folder;
    private String xpath;
    public String getURL() { return URL; }
    public int getId() { return id; }
    public String getTagName() { return tagName; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getHeight() { return height; }
    public int getWidth() { return width; }
    public int getParentX() { return parentX; }
    public int getParentY() { return parentY; }
    public int getDeviceWidth() { return deviceWidth; }
    public int getViewportWidth() { return viewportWidth; }
    public float getDPI() { return DPI; }
    public int getChildsNumber() { return childsNumber; }
    public int getTextLength() { return textLength; }
    public String getBrowserName() { return browserName; }
    public String getFolder() {return this.folder; }
    public String getXPath() { return this.xpath; }

    private BrowserRow(String URL, int id, String tagName, int x, int y, int height, int width, int parentX,
                int parentY, int deviceWidth, int viewportWidth, float DPI, int childsNumber,
                int textLength, String browserName, String folder, String xpath) {
        this.URL = URL;
        this.id = id;
        this.tagName = tagName;
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.parentX = parentX;
        this.parentY = parentY;
        this.deviceWidth = deviceWidth;
        this.viewportWidth = viewportWidth;
        this.DPI = DPI;
        this.childsNumber = childsNumber;
        this.textLength = textLength;
        this.browserName = browserName;
        this.folder = folder;
        this.xpath = xpath;
    }

    public File getScreenshot () {
        if (this.childsNumber > 20)
            return null;

        int x = this.getX(),
            y = this.getY(),
            height = this.getHeight(),
            width = this.getWidth();

        try {
            BufferedImage completeScreenshot = this._getCompleteScreenshot(),
                          targetScreenshot;
            if (y > completeScreenshot.getHeight() || x > completeScreenshot.getWidth())
                return null;
            if (y < 0) {
                if (y + height < 0)
                    return null;
                y = 0;
            } if (x < 0) {
                if (x + width < 0)
                    return null;
                x = 0;
            }
            if (height == 0 || width == 0)
                return null;

            width = (x + width > completeScreenshot.getWidth() ? completeScreenshot.getWidth() - x : width);
            height = (y + height > completeScreenshot.getHeight() ? completeScreenshot.getHeight() - y : height);
            if (width <= 0 || height <= 0)
                return null;
            targetScreenshot = completeScreenshot.getSubimage(x, y, width, height);

            this._saveScreenshot(targetScreenshot, String.format("%s%d.png", this.folder, this.id));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return this.getFile(String.format("%s%d.png", this.folder, this.id));
    }

    public BigInteger getXPathId () {
        String [] numbers = this.xpath.replaceAll("(/(\\w|-)+\\[(\\d+)\\])", "$3,")
                                      .split(",");
        BigInteger xpathId = BigInteger.valueOf(0),
                   aux;
        int expoent = 0;
        for (int i = (numbers.length - 1); i >= 0; i--) {
            aux = BigInteger.valueOf(100).pow(expoent++);
            aux = aux.multiply(BigInteger.valueOf(Integer.parseInt(numbers[i])));
            xpathId = xpathId.add(aux);
        }
        return xpathId;
    }

    public File getFile (String file) { return new File(file); }

    public BufferedImage _getCompleteScreenshot () throws IOException {
        return ImageIO.read(new File(
            String.format("%s/complete.png", this.folder)));
    }

    public void _saveScreenshot (BufferedImage image, String path)
            throws IOException {
        ImageIO.write(image, "PNG", new File(path));
    }

    public static BrowserRow readRow (String row) throws Exception {
        String [] fields = row.split(",");
        String folder = fields[0],
               URL = fields[1],
               tagName = fields[3],
               browserName = fields[4],
               xpath = fields[16];
        float dpi = (float) Double.parseDouble(fields[13]);
        int id = Integer.parseInt(fields[2]),
            x = Integer.parseInt(fields[5]),
            y = Integer.parseInt(fields[6]),
            height = Integer.parseInt(fields[7]),
            width = Integer.parseInt(fields[8]),
            parentX = Integer.parseInt(fields[9]),
            parentY = Integer.parseInt(fields[10]),
            deviceWidth = Integer.parseInt(fields[11]),
            viewportWidth = Integer.parseInt(fields[12]),
            childsNumber = Integer.parseInt(fields[14]),
            textLength = Integer.parseInt(fields[15]);
        return new BrowserRow(URL, id, tagName, x, y, height, width, parentX, parentY,
                       deviceWidth, viewportWidth, dpi, childsNumber, textLength,
                       browserName, folder, xpath);
    }
}
