package pacman.entities;

import pacman.maze.Cell;
import pacman.maze.GraphData;
import pacman.maze.GraphVertex;
import pacman.maze.Maze;

import java.lang.reflect.Method;

public class Ghost extends Entity {
    private String picturePath;
    private int speed; // 1 - the fastest, 5 - the slowest
    private int wait;

    public Ghost(Cell position, int picture, int speed) {
        this.currentPosition = position;
        this.startPosition = position;
        this.picturePath = "src/resources/ghost" + picture + ".png";
        this.speed = speed;
        this.wait = 0;
    }

    public void nextPosition(Method algorithm, Maze maze, GraphVertex<Cell> start, GraphData<Cell> graph, int depth, boolean pacwoman) {
        try {
            if (wait == 0) {

                int i = start.ghosts.indexOf(currentPosition);

                currentPosition = (Cell) algorithm.invoke(maze.getStructure(), i, start, graph, depth, pacwoman);
                wait = speed;
            }
            wait--;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    protected boolean isOnTarget() {
        return false;
    }

    public boolean isOnTarget(Pacman pacman) {
        return pacman.getCurrentPosition() == this.getCurrentPosition();
    }

    public String getPicturePath() {
        return this.picturePath;
    }

    public int getSpeed() {
        return speed;
    }

    public int getWait() {
        return wait;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        this.wait = 0;
    }
}
