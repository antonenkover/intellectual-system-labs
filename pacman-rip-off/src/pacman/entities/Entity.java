package pacman.entities;

import pacman.maze.Cell;
import pacman.maze.GraphData;
import pacman.maze.GraphVertex;
import pacman.maze.Maze;

import java.lang.reflect.Method;

public abstract class Entity {

    protected Cell currentPosition;
    protected Cell startPosition;
    protected int pathLength;

    protected abstract void nextPosition(Method algorithm, Maze maze, GraphVertex<Cell> start, GraphData<Cell> graph, int depth, boolean pacwoman);

    protected abstract boolean isOnTarget();

    public abstract String getPicturePath();

    public Cell getCurrentPosition() {
        return currentPosition;
    }

    public Cell getStartPosition() {
        return startPosition;
    }

    public int getPathLength() {
        return pathLength;
    }

    public void setCurrentPosition(Cell currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setStartPosition(Cell startPosition) {
        this.startPosition = startPosition;
    }

    public void setPathLength(int pathLength) {
        this.pathLength = pathLength;
    }
}
