package game.graphic.creature.monster;

import com.pFrame.PTimer;
import com.pFrame.PTimerTask;
import com.pFrame.Pixel;
import com.pFrame.Position;
import game.Attack;
import game.Location;
import game.graphic.creature.Creature;
import game.graphic.effect.Effect;
import game.world.World;
import imageTransFormer.GraphicItemGenerator;

import java.util.ArrayList;
import java.util.Random;

public class IceAttack extends Effect {
    public Pixel[][] image;

    public IceAttack(Creature creature,Position position){
        super();
        this.width= World.tileSize;
        this.height=World.tileSize;
        image= GraphicItemGenerator.generateItem(this.getClass().getClassLoader().getResource("image/shoot/ice/"+String.valueOf(new Random().nextInt(3)+1)+".png").getFile(),width,height).getPixels();
        this.graphic=image;
        time=1500;
        repeat=true;
        this.p=position;
        task=new IceAttack.Task(creature,timer,position,this);
    }

    class Task implements PTimerTask{
        int timers=0;
        Creature creature;
        PTimer timer;
        Position position;
        IceAttack iceAttack;


        public Task(Creature creature,PTimer timer,Position position,IceAttack iceAttack){
            this.creature=creature;
            this.timer=timer;
            this.position=position;
            this.iceAttack=iceAttack;
        }

        @Override
        public void doTask() {
            timers++;
            if(timers<=4){
                ArrayList<Location> affectList=new ArrayList<Location>();
                affectList.add(creature.getWorld().getTileByLocation(this.position));
                Attack attack=new Attack(Attack.ICE,affectList,creature.getAttack(),creature.getGroup());
                creature.getWorld().handleAttack(attack);
            }
            else{
                world.removeItem(iceAttack);
                this.timer.stop();
            }
        }
    }
}
