package game.graphic.effect;

import com.pFrame.PFont;
import com.pFrame.PTimerTask;
import com.pFrame.Pixel;
import com.pFrame.Position;
import com.pFrame.pgraphic.PGraphicItem;
import imageTransFormer.GraphicItemGenerator;

public class Dialog extends Effect {
    String string ;

    public Dialog(String text, Position position) {
        super();
        this.string=text;
        Pixel[][] pixels=Pixel.valueOf(GraphicItemGenerator.generateItem(Dialog.class.getClassLoader().getResource("image/dialog.png").getFile(),8*string.length()+10,12));
        for(int i=0;i<string.length();i++)
            Pixel.pixelsAdd(pixels, PFont.getCharByPixels(string.charAt(i)),Position.getPosition(2,2+8*i));
        this.graphic=pixels;
        this.width=8*string.length()+4;
        this.height=12;
        repeat=false;
        time=2000;
        task=new Task(this);
        this.p=Position.getPosition(position.getX()-4,position.getY()-8*string.length()-4);
    }

    class Task implements PTimerTask{
        Dialog dialog;

        public Task(Dialog dialog){
            this.dialog=dialog;
        }

        @Override
        public void doTask() {
            dialog.world.removeItem(dialog);
        }
    }
}
