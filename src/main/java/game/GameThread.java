package game;

import java.util.concurrent.CopyOnWriteArraySet;

public class GameThread {
    //public static Set<Thread> threadSet=new HashSet<>();
    public static final CopyOnWriteArraySet<Thread> threadSet=new CopyOnWriteArraySet<>();
}
