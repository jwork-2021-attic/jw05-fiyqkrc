package imageTransFormer;

import com.pFrame.pgraphic.PGraphicItem;

public class Test {
    public static void main(String[] args){
        PGraphicItem item=GraphicItemGenerator.generateItem("/home/fiyqkrc/Icon-120.png");
        GraphicItemImageGenerator.toImage(item, "/home/fiyqkrc/testImage.png");
    }
}
