package game.graphic.interactive;

import com.pFrame.Position;
import com.pFrame.pwidget.ObjectUserInteractive;
import game.graphic.Thing;
import game.graphic.creature.operational.Operational;
import game.graphic.effect.Dialog;
import game.world.World;
import imageTransFormer.GraphicItemGenerator;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class ExitPlace extends Thing implements Runnable, ObjectUserInteractive,GameThread {
    Thread thread;

    public ExitPlace() {
        super(null);
        graphic = GraphicItemGenerator.generateItem(this.getClass().getClassLoader().getResource("image/exit.png").getFile(), World.tileSize, World.tileSize).getPixels();
        width = World.tileSize;
        height = World.tileSize;
        beCoverAble = true;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (world != null) {
                    Thing thing = world.findThing(getLocation());
                    if (thing instanceof Operational) {
                        Dialog dialog = new Dialog("Press f to exit maze", p, 3000);
                        world.addItem(dialog);
                        world.addKeyListener('f',this);
                    }
                    else{
                        world.addKeyListener('f',null);
                    }
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        GameThread.threadSet.remove(Thread.currentThread());
    }


    @Override
    public void whenBeAddedToScene() {
        super.whenBeAddedToScene();
        thread = new Thread(this);
        thread.start();
        GameThread.threadSet.add(thread);
    }

    @Override
    public void mouseClicked(MouseEvent e, Position p) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'f' || e.getKeyChar() == 'F') {
            System.out.println("you choose to leave");
            world.gameFinish();
        }
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
        return Position.getPosition(0, 0);
    }

    @Override
    public Thread getThread() {
        return thread;
    }

    @Override
    public void stop() {
        thread.interrupt();
    }
}
