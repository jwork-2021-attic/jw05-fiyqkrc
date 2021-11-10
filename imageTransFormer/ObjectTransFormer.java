package imageTransFormer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.*;
import com.pFrame.Pixel;

import log.Log;

public class ObjectTransFormer {

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public static Pixel[][] transform(BufferedImage image, int width, int height) {
        if (image == null || width <= 0 || height <= 0) {
            Log.ErrorLog(ObjectTransFormer.class, "illgel input");
            return null;
        }

        Image image2 = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage bufferImage = ObjectTransFormer.toBufferedImage(image2);

        Pixel[][] pixels = new Pixel[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixels[i][j] = Pixel.getPixel(new Color(bufferImage.getRGB(j, i)), (char) 0xf0);
            }
        }
        return pixels;
    }
}
