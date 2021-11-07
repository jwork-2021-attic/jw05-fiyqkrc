package imageTransFormer;

import com.pFrame.Pixel;
import com.pFrame.pgraphic.PGraphicItem;

import java.awt.image.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GraphicItemGenerator {
    public static PGraphicItem generateItem(String path){
        BufferedImage image=null;
        try {
            image=ImageIO.read(new FileInputStream(new File(path)));
        } catch (FileNotFoundException e) {
            System.out.println("file not exist");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(image!=null){
            Pixel[][] pixels=ObjectTransFormer.transform(image, 10, 10);
            return new PGraphicItem(pixels);
        }
        else{
            return null;
        }
    }
}
