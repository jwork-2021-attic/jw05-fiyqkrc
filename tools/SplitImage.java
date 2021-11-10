package tools;


import com.pFrame.Pixel;
import com.pFrame.Position;
import com.pFrame.pgraphic.PGraphicItem;

import imageTransFormer.GraphicItemGenerator;
import imageTransFormer.GraphicItemImageGenerator;

public class SplitImage {
    public static void main(String[] args) {
        PGraphicItem largeImage = GraphicItemGenerator.generateItem("/home/fiyqkrc/test.png", 476, 1020);
        Pixel[][] pixels=largeImage.getPixels();
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 14; j++) {
                Pixel.subPixels(pixels, Position.getPosition(i*34,j*34), 34, 34);
                GraphicItemImageGenerator.toImage(new PGraphicItem(Pixel.subPixels(pixels, Position.getPosition(i*34,j*34), 34, 34)), 
                    String.format("/home/fiyqkrc/output/%d-%d.png", i,j));
            }
        }
    }
}
