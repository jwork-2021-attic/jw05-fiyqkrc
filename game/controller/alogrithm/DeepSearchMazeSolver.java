package game.controller.alogrithm;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import com.pFrame.Position;

import mazeGenerator.*;

public class DeepSearchMazeSolver implements MazeSolver {

    private boolean maze[][];
    private String solution = "";

    private Position start;
    private Position end;

    @Override
    public void loadMaze(boolean[][] maze, Position start, Position end) {
        this.maze = maze;
        this.start = start;
        this.end = end;
        this.solution="";
    }

    @Override
    public String getSolution() {
        if (solution == "") {
            this.execute();
            return solution;
        } else
            return solution;
    }

    private int width;
    private int height;

    private void execute() {
        width = maze[0].length;
        height = maze.length;
        Stack<Position> cross = new Stack<>();
        Stack<Position> path = new Stack<>();

        Position current = start;
        while (!current.isEqualWith(end)) {
            //solution+= "{" + current.getX() + "," + current.getY() + "}\n";
            ArrayList<Position> selectable = detectRoad(current);
            if (selectable.isEmpty()) {
                while (!path.isEmpty()) {
                    Position node = path.pop();
                    if (node.isEqualWith(cross.peek())) {
                        maze[current.getX()][current.getY()] = false;
                        cross.pop();
                        current = node;
                        break;
                    } else {
                        maze[current.getX()][current.getY()] = false;
                        current = node;
                    }
                }
                if (current.isEqualWith(start)&&detectRoad(current).isEmpty()) {
                    solution = "";
                    break;
                }
            } else {
                Position selectPath = selectable.get(0);
                selectable.remove(0);
                cross.push(current);
                maze[current.getX()][current.getY()] = false;
                path.push(current);
                current = selectPath;
            }
        }
        if (!current.isEqualWith(start))
            path.push(current);

        for (Position iterable_element : path) {
            solution += "{" + iterable_element.getX() + "," + iterable_element.getY() + "}\n";
        }
    }

    private ArrayList<Position> detectRoad(Position p) {
        ArrayList<Position> res = new ArrayList<>();
        if (positionValid(new Position(p.getX(), p.getY() + 1)))
            res.add(new Position(p.getX(), p.getY() + 1));
        if (positionValid(new Position(p.getX() + 1, p.getY())))
            res.add(new Position(p.getX() + 1, p.getY()));
        if (positionValid(new Position(p.getX(), p.getY() - 1)))
            res.add(new Position(p.getX(), p.getY() - 1));
        if (positionValid(new Position(p.getX() - 1, p.getY())))
            res.add(new Position(p.getX() - 1, p.getY()));
        return res;
    }

    private boolean positionValid(Position p) {
        if (p.getX() >= 0 && p.getX() < height)
            if (p.getY() >= 0 && p.getY() < width)
                if (maze[p.getX()][p.getY()] == true)
                    return true;
        return false;
    }

    public static void main(String[] args) {
        int maxRange = 2000;
        int testCounts = 100;
        for (int testCount = 1; testCount < testCounts; testCount++) {
            int testDim = 5+(new Random()).nextInt(1000000) % (maxRange-5);
            DeepSearchMazeSolver s = new DeepSearchMazeSolver();
            MazeGenerator maze = new MazeGenerator(testDim);
            maze.generateMaze();
            boolean[][] mazeRawArray = getArrayRawMaze(maze, testDim);
            Position startNode = getRandomNode(mazeRawArray);
            Position endNode = getRandomNode(mazeRawArray);
            s.loadMaze(getArrayRawMaze(maze, testDim), startNode , endNode);
            System.out.println(maze.getRawMaze());
            System.out.println(String.format("start:%s", startNode));
            System.out.println(String.format("end:%s\nSolution:", endNode));
            System.out.println(s.getSolution());
        }
    }
    public static Position getRandomNode(boolean[][] mazeRawArray){
        int maze_width=mazeRawArray[0].length;
        int maze_height=mazeRawArray.length;
        int x=0;
        int y=0;
        Position p=new Position(0, 0);
        boolean success=false;
        while(!success){
            x=((new Random()).nextInt(1010000))%maze_height;
            y=((new Random()).nextInt(1024000))%maze_width;
            p= new Position(x, y);
            if (mazeRawArray[p.getX()][p.getY()] == true)
                success=true;
            else
                success=false;
        }
        return p;
    }

    public static boolean[][] getArrayRawMaze(MazeGenerator maze, int mazeDim) {
        String mazeStr = maze.getRawMaze();
        String[] mazeStrs = mazeStr.split("\n");
        boolean[][] res = new boolean[mazeDim][mazeDim];
        int x = 0, y = 0;
        for (String str : mazeStrs) {
            y = 0;
            str = str.substring(1, str.length() - 1);
            String[] strs = str.split(", ");
            for (String s : strs) {
                res[x][y] = Integer.parseInt(s) == 1 ? true : false;
                y++;
            }
            x++;
        }
        return res;
    }

}


