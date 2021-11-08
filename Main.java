import mazeGenerator.MazeGenerator;

import java.awt.Color;

import com.pFrame.PFrame;
import com.pFrame.PLayout;
import com.pFrame.Position;
import com.pFrame.pgraphic.PGraphicScene;
import com.pFrame.pgraphic.PGraphicView;
import com.pFrame.pwidget.*;

import asciiPanel.AsciiFont;
import game.controller.KeyBoardThingController;
import game.controller.MazeThingController;
import game.controller.alogrithm.DeepSearchMazeSolver;
import game.creature.Calabash;
import game.creature.Thing;
import game.creature.Wall;
import game.world.GameWorld;

public class Main {
    public static void main(String[] args) {
        PHeadWidget app = new PHeadWidget(null, null, new PFrame(350,200,AsciiFont.pFrame_4x4));
        app.getLayout().setRCNumStyle(2, 1, "1x,1x", "");
        PLayout layout = new PLayout(app, new Position(1, 1), 1, 2);
        layout.setColumnLayout("1x,1x");
        layout.setRowLayout("1x");

        

        for (int i = 0; i < 2; i++) {
            GameWorld world = new GameWorld(400, 400);
            PGraphicView worldView = new PGraphicView(layout, new Position(1, i+1), new PGraphicScene(400,400));
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
            //worldView.setFocus(aim);
            MazeThingController solver=new MazeThingController(DeepSearchMazeSolver.class);
            solver.loadMaze(mazeArray, start, end);
            aim.setController(solver);
        }

        PLayout layout2=new PLayout(app,new Position(2, 1),1,2);
        layout2.setColumnLayout("1x,1x");
        for (int i = 0; i < 2; i++) {
            GameWorld world = new GameWorld(400, 400);
            PGraphicView worldView = new PGraphicView(layout2, new Position(1, i+1), new PGraphicScene(400, 400));
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
            //worldView.setFocus(aim);
        }

    }

}
