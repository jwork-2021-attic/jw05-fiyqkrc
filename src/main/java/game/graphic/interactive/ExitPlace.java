package game.graphic.interactive;

import com.pFrame.Pixel;
import com.pFrame.Position;
import com.pFrame.pwidget.ObjectUserInteractive;
import game.controller.KeyBoardThingController;
import game.graphic.Thing;
import game.graphic.creature.operational.Operational;
import game.graphic.effect.Dialog;
import game.world.World;
import imageTransFormer.GraphicItemGenerator;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class ExitPlace extends Thing implements Runnable , ObjectUserInteractive {
    KeyBoardThingController oldController;

    public ExitPlace() {
        super(null);
        graphic= GraphicItemGenerator.generateItem(this.getClass().getClassLoader().getResource("image/exit.png").getFile(), World.tileSize,World.tileSize).getPixels();
        width=World.tileSize;
        height=World.tileSize;
        beCoverAble=true;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (world != null) {
                    Thing thing = world.findThing(getLocation());
                    if (thing != null && thing instanceof Operational) {
                        Dialog dialog=new Dialog("Press f to exit maze",p,3000);
                        world.addItem(dialog);
                        oldController=(KeyBoardThingController) ((Operational) thing).getController();
                        world.getParentView().getKeyMouseListener(this);
                    }
                }
                Thread.sleep(100);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public void whenBeAddedToScene() {
        super.whenBeAddedToScene();
        Thread thread=new Thread(this);
        thread.start();
    }

    @Override
    public void mouseClicked(MouseEvent e, Position p) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar()=='f'||e.getKeyChar()=='F'){
            System.out.println("you choose to leave");

        }
        else{
            world.getParentView().freeKeyMouseListener();
            world.getParentView().getKeyMouseListener(oldController);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {

    }

    @Override
    public void mouseExited(MouseEvent arg0) {

    }

    @Override
    public void mousePressed(MouseEvent arg0, Position p) {

    }

    @Override
    public void mouseReleased(MouseEvent arg0, Position p) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    @Override
    public Position getRealPosition() {
        return Position.getPosition(0,0);
    }
}
