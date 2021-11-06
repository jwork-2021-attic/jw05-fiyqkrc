import com.pFrame.widget.PFrame;
import com.pFrame.widget.view.WorldView;
import com.pFrame.world.World;

import mazeGenerator.MazeGenerator;

import java.awt.Color;

import com.pFrame.controller.KeyBoardThingController;
import com.pFrame.controller.MazeThingController;
import com.pFrame.controller.alogrithm.DeepSearchMazeSolver;
import com.pFrame.creature.Calabash;
import com.pFrame.creature.Thing;
import com.pFrame.creature.Wall;
import com.pFrame.widget.*;

public class Main {
    public static void main(String[] args) {
        PHeadWidget app = new PHeadWidget(null, null, new PFrame(60, 50));
        app.getLayout().setRCNumStyle(3, 1, "1x,2,1x", "");
        Layout layout = new Layout(app, new Position(1, 1), 1, 3);
        layout.setColumnLayout("1x,2,1x");
        layout.setRowLayout("1x");

        

        for (int i = 0; i < 2; i++) {
            World world = new World(400, 400);
            WorldView worldView = new WorldView(layout, new Position(1, 2*i+1), world);
            int mazeDim = 400;
            MazeGenerator maze = new MazeGenerator(mazeDim);
            maze.generateMaze();
            boolean[][] mazeArray = DeepSearchMazeSolver.getArrayRawMaze(maze, mazeDim);
            for (int x = 0; x < mazeDim; x++)
                for (int y = 0; y < mazeDim; y++) {
                    if (mazeArray[x][y] == false) {
                        Wall wall = new Wall(world);
                        world.put(wall, x, y);
                    }
                }
            Position start = DeepSearchMazeSolver.getRandomNode(mazeArray);
            Position end = DeepSearchMazeSolver.getRandomNode(mazeArray);
            Thing aim = new Calabash(new Color(200, 0, 0), 1, world);
            world.put(aim, start.getX(), start.getY());
            world.put(new Calabash(new Color(0, 200, 0), 2, world), end.getX(), end.getY());
            worldView.setFocus(aim);
            MazeThingController solver=new MazeThingController(DeepSearchMazeSolver.class);
            solver.loadMaze(mazeArray, start, end);
            aim.setController(solver);
        }

        Layout layout2=new Layout(app,new Position(3, 1),1,3);
        layout2.setColumnLayout("1x,2,1x");
        for (int i = 0; i < 2; i++) {
            World world = new World(400, 400);
            WorldView worldView = new WorldView(layout2, new Position(1, 2*i+1), world);
            int mazeDim = 400;
            MazeGenerator maze = new MazeGenerator(mazeDim);
            maze.generateMaze();
            boolean[][] mazeArray = DeepSearchMazeSolver.getArrayRawMaze(maze, mazeDim);
            for (int x = 0; x < mazeDim; x++)
                for (int y = 0; y < mazeDim; y++) {
                    if (mazeArray[x][y] == false) {
                        Wall wall = new Wall(world);
                        world.put(wall, x, y);
                    }
                }
            Position start = DeepSearchMazeSolver.getRandomNode(mazeArray);
            Position end = DeepSearchMazeSolver.getRandomNode(mazeArray);
            Thing aim = new Calabash(new Color(200, 0, 0), 1, world);
            aim.setController(new KeyBoardThingController());
            world.put(aim, start.getX(), start.getY());
            world.put(new Calabash(new Color(0, 200, 0), 2, world), end.getX(), end.getY());
            worldView.setFocus(aim);
        }

    }

}
