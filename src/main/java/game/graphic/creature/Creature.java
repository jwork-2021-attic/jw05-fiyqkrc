package game.graphic.creature;


import com.pFrame.Pixel;
import com.pFrame.Position;
import game.Location;
import game.controller.AlgorithmController;
import game.controller.CreatureController;
import game.controller.KeyBoardThingController;
import game.graphic.Thing;
import game.graphic.Tombstone;
import game.graphic.effect.BloodChange;
import game.graphic.effect.Dialog;
import game.graphic.drop.Coin;
import game.graphic.drop.buff.Addition;
import imageTransFormer.GraphicItemGenerator;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Creature extends Thing implements Controllable {
    protected static HashMap<String, Body[]> SourceMap = new HashMap<>();

    protected Body[] Bodys;
    protected int lastImageIndex;

    protected CreatureController controller;

    public double direction;

    protected int group = 0;
    protected int speed = 2;
    protected double health;
    protected double attack;
    protected double resistance;
    protected boolean beControlled;

    protected double healthLimit=health;
    protected double attackLimit=attack;
    protected double resistanceLimit=resistance;
    protected int speedLimit=speed;
    protected int coin;

    protected long lastMove;

    protected CopyOnWriteArrayList<Addition> additions;


    public Creature(String path, int width, int height) {
        super(null);

        additions=new CopyOnWriteArrayList<>();

        beCoverAble = false;

        health = 100;
        attack = 10;
        resistance = 0.2;
        beControlled = false;
        coin=1;

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

    public CopyOnWriteArrayList<Addition> getAdditions()
    {
        return additions;
    }

    public void addAddition(Addition addition){
        additions.add(addition);
        addition.useAddition();
    }

    public int getGroup(){
        return group;
    }

    public double getHealth(){
        return health;
    }

    public double getAttack(){
        return attack;
    }

    public double getAttackLimit(){
        return attackLimit;
    }

    public void addAttack(double attack){
        this.attack+=attack;
    }

    public double getHealthLimit(){
        return healthLimit;
    }

    public void addSpeed(int speed){
        this.speed+=speed;
    }

    public void addResistance(double resistance){
        this.resistance+=resistance;
    }

    public void addCoin(int n){
        this.coin+=n;
    }

    public int getCoin(){
        return coin;
    }

    public int getSpeed()
    {
        return speed;
    }

    public int getSpeedLimit(){
        return speedLimit;
    }

    @Override
    public boolean isDead() {
        return health<=0;
    }

    public void deHealth(double i){
        if(i>0) {
            i = (1 - resistance) * i;
            health -= i;
        }
        else if(i<0){
            health=Math.min(health-i,healthLimit);
        }
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
    public void responseToEnemy() {
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
            return this.world.ThingMove(this, Position.getPosition(this.getCentralPosition().getX() - (int) y, this.getCentralPosition().getY() + (int) x));
        }
    }

    protected void switchImage(int i) {
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
        if(controller instanceof AlgorithmController)
            ((AlgorithmController) controller).stop();
        else if(controller instanceof KeyBoardThingController){
            pause();
        }
        world.removeItem(this);
        Tombstone tombstone=new Tombstone();
        tombstone.setPosition(this.getPosition());
        world.addItem(tombstone);
        Coin coin=new Coin(this);
        world.addItem(coin);
    }

    @Override
    public Creature searchAim() {
        Location location=world.searchNearestEnemy(this,7);
        Thing thing=world.findThing(location);
        if(thing instanceof Creature)
            return (Creature)thing;
        else 
            return null;
    }
}


