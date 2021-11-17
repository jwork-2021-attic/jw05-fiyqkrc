package game.graphic.creature;


import com.pFrame.Pixel;
import com.pFrame.Position;
import game.Location;
import game.controller.CreatureController;
import game.graphic.Controllable;
import game.graphic.Thing;
import game.graphic.Tombstone;
import game.graphic.effect.BloodChange;
import game.graphic.effect.Dialog;
import imageTransFormer.GraphicItemGenerator;

import java.util.HashMap;

public abstract class Creature extends Thing implements Controllable {
    protected static HashMap<String, Body[]> SourceMap = new HashMap<>();

    protected Body[] Bodys;
    protected int lastImageIndex;

    protected CreatureController controller;

    public double direction;

    protected int group = 0;

    protected int speed = 5;

    protected double health;
    protected double attack;
    protected double resistance;
    protected boolean beControlled;


    public Creature(String path, int width, int height) {
        super(null);

        beCoverAble = false;

        health = 100;
        attack = 10;
        resistance = 0.2;
        beControlled = false;

        Bodys = new Body[8];
        if (!SourceMap.containsKey(path)) {
            for (int i = 0; i < 8; i++) {
                Pixel[][] pixels = Pixel.valueOf(GraphicItemGenerator.generateItem(path + String.format("/%d.png", i + 1), width, height));
                Bodys[i] = new Body(pixels, width, height);
            }
            SourceMap.put(path, Bodys);
        } else {
            Bodys = SourceMap.get(path);
        }
        switchImage(1);
    }

    public int getGroup(){
        return group;
    }

    public double getHealth(){
        return health;
    }

    @Override
    public boolean isDead() {
        return health<=0;
    }

    public void deHealth(double i){
        health-=i;
        BloodChange bloodChange=new BloodChange((int)-i,this.getPosition());
        world.addItem(bloodChange);
    }

    @Override
    public void speak(String text) {
        Dialog dialog = new Dialog(text, this.getPosition());
        this.world.addItem(dialog);
    }

    @Override
    public void setController(CreatureController controller) {
        this.controller = controller;
    }

    @Override
    public CreatureController getController() {
        return this.controller;
    }

    @Override
    public void attack() {
    }

    @Override
    public boolean move(double direction) {
        this.direction = direction;
        double y = Math.sin(direction) * speed;
        double x = Math.cos(direction) * speed;

        int nextImage = 1;
        int nextDirection = (int) Math.floor((direction + Math.PI / 4) * 2 / Math.PI);
        nextImage = ((lastImageIndex % 2) + 1) % 2 + nextDirection * 2;
        if(nextImage<0)
            nextImage=0;
        else if(nextImage>=8)
            nextImage=7;
        switchImage(nextImage);
        lastImageIndex = nextImage;

        if (world == null) {
            this.setPosition(Position.getPosition(this.p.getX() - (int) y, this.p.getY() + (int) x));
            return true;
        }
        else {
            return this.world.ThingMove(this, Position.getPosition(this.p.getX() - (int) y, this.p.getY() + (int) x));
        }
    }

    private void switchImage(int i) {
        if (Bodys[i] != null) {
            graphic = Bodys[i].pixels();
            width = Bodys[i].width();
            height = Bodys[i].height();
        }
    }

    abstract public void pause();

    abstract public void Continue();

    record Body(Pixel[][] pixels, int width, int height) {
    }

    @Override
    public void dead() {
        System.out.println("I am dead");
        world.removeItem(this);
        Tombstone tombstone=new Tombstone();
        tombstone.setPosition(this.getPosition());
        world.addItem(tombstone);
    }

    @Override
    public boolean searchAim() {
        Location location=world.searchNearestEnemy(this,7);
        return location!=null;
    }
}


