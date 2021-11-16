package game.graphic.effect;

import com.pFrame.PFont;
import com.pFrame.PTimerTask;
import com.pFrame.Pixel;
import com.pFrame.Position;

import java.util.Random;

public class BloodChange extends Effect {
    int change;

    public BloodChange(int change, Position position) {
        super();
        this.change = change;
        String string = String.valueOf(change);
        if(this.change>=0){
            string="+"+string;
        }
        Pixel[][] pixels = Pixel.emptyPixels(string.length()*8,8);
        for (int i = 0; i < string.length(); i++)
            Pixel.pixelsAdd(pixels, PFont.getCharByPixels(string.charAt(i)), Position.getPosition(0, 8 * i));
        this.graphic = pixels;
        this.width = 8 * string.length();
        this.height = 8;
        repeat = false;
        time = 200;
        task = new Task(this);
        Random random=new Random();
        int x=random.nextInt(10);
        int y=random.nextInt(10)-5;
        this.p = Position.getPosition(position.getX()-x, position.getY()+y);
    }

    class Task implements PTimerTask {
        Effect dialog;

        public Task(Effect dialog) {
            this.dialog = dialog;
        }

        @Override
        public void doTask() {
            dialog.getWorld().removeItem(dialog);
        }
    }
}
