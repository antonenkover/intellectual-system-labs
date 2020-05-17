package pacman.entities;

import pacman.main.AlgorithmData;
import pacman.maze.*;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class Pacman extends Entity {

    public final String pathToPicture1 = ("src/resources/pm_opened.png");
    public final String pathToPicture2 = ("src/resources/pm_closed.png");
    private ArrayList<Cell> path;
    private Direction direction;
    private boolean state;
    private int score;

    public Pacman(Cell position) {
        this.startPosition = position;
        this.currentPosition = position;
        this.path = null;
        this.direction = Direction.Left;
        this.state = true;
        this.pathLength = 0;
        this.score = 0;
    }

    public String getPicturePath() {
        return state ? pathToPicture1 : pathToPicture2;
    }

    public void nextPosition(Method algorithm, Maze maze, GraphVertex<Cell> start, GraphData<Cell> graph, int depth, boolean pacwoman) {
        try {
            if (path != null && path.size() != 0) {
                currentPosition = path.get(0);
                path.remove(currentPosition);
            }
            else {
                currentPosition = (Cell) algorithm.invoke(maze.getStructure(), 0, start, graph, depth, pacwoman);
            }
            if (isOnTarget()) {
                currentPosition.setType(CellType.Empty);
                score++;
            }
            pathLength++;
            state = !state;
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void pathToTarget(Method algorithm, Maze maze) {
        try {
            AlgorithmData<Cell> algorithmResult = (AlgorithmData<Cell>) algorithm.invoke(maze.getStructure(), currentPosition, maze.getAllTablets());
            path = algorithmResult.result;
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean isOnTarget() {
        return currentPosition.getType() == CellType.Tablet;
    }
    public Direction getDirection() {
        return direction;
    }
    public boolean getState() {
        return state;
    }
    public ArrayList<Cell> getPath() {
        return path;
    }
    public int getScore() {
        return score;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public void setState(boolean state) {
        this.state = state;
    }
    public void setPath(ArrayList<Cell> path) {
        this.path = path;
    }
    public void setScore(int score) {
        this.score = score;
    }
}
