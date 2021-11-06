package com.pFrame.controller;

import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;

import com.pFrame.controller.alogrithm.MazeSolver;
import com.pFrame.creature.Floor;
import com.pFrame.creature.Thing;
import com.pFrame.widget.Position;

public class MazeThingController extends AlogrithmThingController {
    private MazeSolver solver;

    private String solution;
    private String[] sortSteps;
    private int stepN = 0;
    private boolean[][] maze;
    private Position end;
 
    private Position oldPos;

    public MazeThingController(Class<? extends MazeSolver> alogrithmClass) {
        super();
        try {
            solver = alogrithmClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        solution = "";
    }

    @Override
    public void respondToUserInput(KeyEvent event) {
        if (!this.thing.getPosition().isEqualWith(this.oldPos)) {
            this.updateMaze(this.maze, this.thing.getPosition(), end);
            this.respondToUserInput(event);
        } else {
            if (this.stepN < this.sortSteps.length) {
                String step = sortSteps[this.stepN];
                if (step != "") {
                    String[] xy = step.substring(1, step.length() - 1).split(",");
                    int x = this.thing.getX();
                    int y = this.thing.getY();
                    this.thing.getWorld().put(new Floor(this.thing.getWorld()), x, y);
                    this.thing.getWorld().put(this.thing, Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
                }
            }
            this.stepN++;
            this.oldPos = this.thing.getPosition();
        }
    }

    public void loadMaze(boolean[][] maze, Position start, Position end) {
        this.maze=maze;
        this.end=end;
        boolean[][] maze_copy=new boolean[maze[0].length][maze.length];
        for(int i=0;i<maze.length;i++){
            maze_copy[i]=maze[i].clone();
        }
        this.solver.loadMaze(maze_copy, start, end);
        this.solution = this.solver.getSolution();
        this.sortSteps = this.solution.split("\n");
    }

    public void updateMaze(boolean[][] maze,Position start,Position end1){
        this.loadMaze(maze, start, end1);
        this.oldPos=this.thing.getPosition();
        this.stepN=0;
    }

    @Override
    public void setThing(Thing thing) {
        if(this.thing!=thing){
            super.setThing(thing);
            this.oldPos=this.thing.getPosition();
        }
    }
}
