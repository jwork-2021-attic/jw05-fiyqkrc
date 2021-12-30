package game.graphic.creature.operational;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pFrame.Position;

import org.junit.Before;
import org.junit.Test;

import game.graphic.drop.buff.Addition;
import game.graphic.drop.buff.AttackBuff;

public class CalabashTest {
    Calabash calabash;

    @Before
    public void setUp(){
        calabash=new Calabash();
    }

    @Test
    public void testContinue() {

    }

    @Test
    public void testAddAddition() throws InterruptedException {
        calabash.setPosition(Position.getPosition(0, 0));
        calabash.addAddition(new Addition(new AttackBuff(), calabash ,-100, 100, 100, 0.5));
        assertFalse(calabash.getAttack()==calabash.getAttackLimit());
        assertFalse(calabash.getHealth()==calabash.getHealthLimit());
        assertTrue(calabash.getAdditions().isEmpty());
        assertFalse(calabash.getSpeed()==calabash.getSpeedLimit());
    }

    @Test
    public void testAddAttack() {
        Calabash calabash=new Calabash();
        double attack=calabash.getAttack();
        calabash.addAttack(0.5);
        assertEquals(calabash.getAttack(), attack+0.5);
    }

    @Test
    public void testAddCoin() {
        int coin=calabash.getCoin();
        calabash.addCoin(10);
        assertEquals(10+coin, calabash.getCoin());
    }

    @Test
    public void testAddSpeed() {
        int speed=calabash.getSpeed();
        calabash.addSpeed(10);
        assertEquals(10+speed, calabash.getSpeed());
    }

    @Test
    public void testDeHealth() {
        double health=calabash.getHealth();
        calabash.deHealth(10);
        assertEquals(health-10, calabash.getHealth());
        calabash.deHealth(-10-calabash.getHealthLimit());
        assertTrue(calabash.getHealth()<=calabash.getHealthLimit());
    }

    @Test
    public void testDead() {

    }

    @Test
    public void testIsDead() {

    }

    @Test
    public void testMove() {

    }

    @Test
    public void testPause() {

    }

    @Test
    public void testSaveState() {

    }

    @Test
    public void testSearchAim() {

    }
}
