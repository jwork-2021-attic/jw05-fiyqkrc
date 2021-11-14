package tools;

import com.pFrame.Pixel;
import com.pFrame.Position;
import com.pFrame.pgraphic.PGraphicItem;

import imageTransFormer.GraphicItemGenerator;
import imageTransFormer.GraphicItemImageGenerator;

public class SplitImage {
    public static void main(String[] args) {
        PGraphicItem largeImage = GraphicItemGenerator.generateItem("/home/fiyqkrc/Pictures/ProjectUtumno_supplemental_0.png", 2048, 1536);
        Pixel[][] pixels = largeImage.getPixels();
        for (int i = 0; i < 48; i++) {
            for (int j = 0; j < 64; j++) {
                Pixel.subPixels(pixels, Position.getPosition(i * 32, j * 32), 32, 32);
                GraphicItemImageGenerator.toImage(
                        new PGraphicItem(Pixel.subPixels(pixels, Position.getPosition(i * 32, j * 32), 32, 32)),
                        String.format("/home/fiyqkrc/Pictures/output/%d-%d.png", i, j));
            }
        }
    }
}
