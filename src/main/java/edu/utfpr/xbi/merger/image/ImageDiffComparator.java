package edu.utfpr.xbi.merger.image;

import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageDiffComparator implements Comparator {

    private BufferedImage resize (BufferedImage bufImage, int height, int width) {
        Image image = bufImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage resized = new BufferedImage(
                image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = resized.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        return resized;
    }

    public double compare (File screenshot1, File screenshot2) throws Exception {
        if (screenshot1 == null && screenshot2 == null)
            return 0;

        if (screenshot1 == null || screenshot2 == null)
            return -1;

        BufferedImage base = ImageIO.read(screenshot1),
                      target = ImageIO.read(screenshot2);
        double result = 0;
        int width = base.getWidth(null),
            height = base.getHeight(null);

        target = this.resize(target, height, width);

        for (int y = 0; y < height; y++) {
          for (int x = 0; x < width; x++) {
            int rgb1 = base.getRGB(x, y);
            int rgb2 = target.getRGB(x, y);
            int r1 = (rgb1 >> 16) & 0xff;
            int g1 = (rgb1 >>  8) & 0xff;
            int b1 = (rgb1      ) & 0xff;
            int r2 = (rgb2 >> 16) & 0xff;
            int g2 = (rgb2 >>  8) & 0xff;
            int b2 = (rgb2      ) & 0xff;
            result += Math.abs(r1 - r2);
            result += Math.abs(g1 - g2);
            result += Math.abs(b1 - b2);
          }
        }
        return result;
    }
}
