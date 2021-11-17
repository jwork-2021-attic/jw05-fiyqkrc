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

public class Vine extends Effect {
    public Pixel[][] image;

    public Vine(Creature creature, Position position){
        super();
        this.width= World.tileSize;
        this.height=World.tileSize;
        image= GraphicItemGenerator.generateItem(this.getClass().getClassLoader().getResource("image/shoot/vine/"+String.valueOf(new Random().nextInt(28)+1)+".png").getFile(),width,height).getPixels();
        this.graphic=image;
        time=1500;
        repeat=true;
        task=new Vine.Task(creature,timer,position,this);
        this.p=position;
        this.beCoverAble=false;

    }

    class Task implements PTimerTask {
        int timers=0;
        Creature creature;
        PTimer timer;
        Position position;
        Vine vine;


        public Task(Creature creature,PTimer timer,Position position,Vine vine){
            this.creature=creature;
            this.timer=timer;
            this.position=position;
            this.vine=vine;
        }

        @Override
        public void doTask() {
            timers++;
            if(timers<=3){
                ArrayList<Location> affectList=new ArrayList<Location>();
                affectList.add(creature.getWorld().getTileByLocation(this.position));
                Attack attack=new Attack(Attack.HIT,affectList,creature.getAttack(),creature.getGroup());
                creature.getWorld().handleAttack(attack);
            }
            else{
                world.removeItem(vine);
                this.timer.stop();
            }
        }
    }
}
