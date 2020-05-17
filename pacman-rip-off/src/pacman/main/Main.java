package pacman.main;

import pacman.maze.LevelManager;
import pacman.maze.Maze;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Main extends Canvas {

    private LevelManager levelManager;

    private Main(String path) {
        this.levelManager = new LevelManager(new Maze(path));
    }

    public static void main(String[] args) {
        JFrame window = new JFrame("Pacman");
        Canvas canvas = new Main("src/resources/maze2.txt");
        canvas.setSize(Finals.WINDOW_WIDTH, Finals.WINDOW_HEIGHT);
        window.add(canvas);
        window.pack();
        window.setVisible(true);
    }

    public void paint(Graphics g) {
        boolean victory = true;
        while (victory) {
            levelManager.nextLevel();
            levelManager.restoreMaze();
            victory = levelManager.play(g);
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (Exception e) {
                System.err.println(e.getCause());
            }
        }
    }
}
