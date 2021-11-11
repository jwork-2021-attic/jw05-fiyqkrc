package tools;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;

public class AsciiFontCreate {
    protected BufferedImage baseFonts() {
        BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                int tmp = i;
                boolean[] pixels = new boolean[4];
                for (int k = 0; k < 4; k++) {
                    pixels[k] = ((tmp % 2) == 1);
                    tmp = tmp / 2;
                }
                image.setRGB(2 * j, 2 * i, (pixels[3] == true) ? 0xffffffff : 0xff000000);
                image.setRGB(2 * j + 1, 2 * i, (pixels[2] == true) ? 0xffffffff : 0xff000000);
                image.setRGB(2 * j, 2 * i + 1, (pixels[1] == true) ? 0xffffffff : 0xff000000);
                image.setRGB(2 * j + 1, 2 * i + 1, (pixels[0] == true) ? 0xffffffff : 0xff000000);
            }
        }
        return image;
    }

    public BufferedImage createFonts(int scale, BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage res = new BufferedImage(width * scale, height * scale, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int a = 0; a < scale; a++) {
                    for (int b = 0; b < scale; b++) {
                        res.setRGB(i * scale + a, j * scale + b, image.getRGB(i, j));
                    }
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        AsciiFontCreate ac = new AsciiFontCreate();
        try {
            ImageIO.write(ac.createFonts(1, ac.baseFonts()), "png",
                    new FileImageOutputStream(new File("./resources/pFrame_2x2.png")));
            ImageIO.write(ac.createFonts(2, ac.baseFonts()), "png",
                    new FileImageOutputStream(new File("./resources/pFrame_4x4.png")));
            ImageIO.write(ac.createFonts(3, ac.baseFonts()), "png",
                    new FileImageOutputStream(new File("./resources/pFrame_6x6.png")));
            ImageIO.write(ac.createFonts(4, ac.baseFonts()), "png",
                    new FileImageOutputStream(new File("./resources/pFrame_8x8.png")));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
