package com.pFrame.controller.alogrithm;

import com.pFrame.widget.Position;

public interface MazeSolver {
    public void loadMaze(boolean[][] maze,Position start,Position end);
    public String getSolution();
}
