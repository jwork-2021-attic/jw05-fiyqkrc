package game.controller.alogrithm;

import com.pFrame.Position;

public interface MazeSolver {
    public void loadMaze(boolean[][] maze,Position start,Position end);
    public String getSolution();
}
