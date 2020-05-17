package pacman.maze;

import pacman.graphs.Graph;
import pacman.main.Finals;
import pacman.entities.Ghost;
import pacman.entities.Pacman;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LevelManager {

    private Pacman pacman;
    private ArrayList<Ghost> ghosts;
    private int totalScore;
    private int levelCounter;

    private Maze maze;
    private int currentSpeed;


    public LevelManager(Maze maze) {
        this.maze = maze;
        this.pacman = new Pacman(this.maze.getStructure().getVertices().get(0));
        this.totalScore = 0;
        this.ghosts = new ArrayList<>();
        this.currentSpeed = Finals.MIN_SPEED;
        this.levelCounter = 0;
    }

    public void nextLevel() {
        if (ghosts.size() == 0) {
            addGhost();
        }
        if (currentSpeed > 1) {
            addSpeed();
        } else {
            speedDown();
            addGhost();
        }
        levelCounter++;
    }

    public boolean play(Graphics g) {
        try {
            DrawManager.drawMaze(maze, g);
            DrawManager.drawEntity(pacman, maze.getHeight(), maze.getWidth(), g);
            DrawManager.drawScore(levelCounter, pacman.getScore(), maze, g);
            for (Ghost ghost : ghosts) {
                DrawManager.drawEntity(ghost, maze.getHeight(), maze.getWidth(), g);
            }

            while (maze.areThereTablets()) {

                Finals.COST_OF_TABLET = maze.getStructure().getVertices().size() - maze.tabletsCount();

                TimeUnit.MILLISECONDS.sleep(100);

                GraphData<Cell> graph = maze.formMMGraph(null, pacman.getCurrentPosition(), ghostPositions(),
                        0, Finals.MINIMAX_DEPTH, true, ghosts.get(0).getWait(), ghosts.get(0).getSpeed());
                GraphVertex<Cell> start = graph.vertices.get(0);

                pacman.nextPosition(Graph.class.getMethod(Finals.ALGORITHM_USED, int.class, GraphVertex.class,
                        GraphData.class, int.class, boolean.class),
                        maze, start, graph, Finals.MINIMAX_DEPTH, true);

                DrawManager.drawMaze(maze, g);

                DrawManager.drawEntity(pacman, maze.getHeight(), maze.getWidth(), g);

                DrawManager.drawScore(levelCounter, pacman.getScore(), maze, g);

                for (Ghost ghost : ghosts) {
                    if (ghost.isOnTarget(pacman)) {
                        DrawManager.drawText("Game over!  Total score: " + totalScore, maze.getHeight(), g);
                        DrawManager.drawScore(levelCounter, pacman.getScore(), maze, g);
                        return false;
                    }
                }

                for (Ghost ghost : ghosts) {
                    graph = maze.formMMGraph(null, pacman.getCurrentPosition(), ghostPositions(),
                            0, Finals.MINIMAX_DEPTH, false, ghost.getWait(), ghost.getSpeed());
                    start = graph.vertices.get(0);
                    ghost.nextPosition(Graph.class.getMethod(Finals.ALGORITHM_USED, int.class, GraphVertex.class,
                            GraphData.class, int.class, boolean.class),
                            maze, start, graph, Finals.MINIMAX_DEPTH, false);
                    DrawManager.drawEntity(ghost, maze.getHeight(), maze.getWidth(), g);

                    if (ghost.isOnTarget(pacman)) {
                        DrawManager.drawText("Game over!  Total score: " + totalScore, maze.getHeight(), g);
                        DrawManager.drawScore(levelCounter, pacman.getScore(), maze, g);
                        return false;
                    }
                }

                DrawManager.drawScore(levelCounter, pacman.getScore(), maze, g);

            }
            DrawManager.drawText("Victory! Total score: " + totalScore, maze.getHeight(), g);
            addScore();
        } catch (Exception e) {
            System.err.println(e);
        }
        return true;
    }

    public void restoreMaze() {
        this.maze = new Maze(maze);
        for (Cell cell : maze.getStructure().getVertices()) {
            if (cell != maze.getStructure().getVertices().get(0)) {
                cell.setType(CellType.Tablet);
            }
        }
        this.pacman = new Pacman(maze.getStructure().getVertices().get(0));
        scatterGhosts();
    }

    private void scatterGhosts() {
        for (Ghost ghost : ghosts) {
            ghost.setCurrentPosition(ghost.getStartPosition());
        }
    }

    private void addScore() {
        totalScore += pacman.getScore();
    }

    private void speedDown() {
        currentSpeed = Finals.MIN_SPEED;
        for (Ghost ghost : ghosts) {
            ghost.setSpeed(currentSpeed);
        }
    }

    private void addSpeed() {
        for (Ghost ghost : ghosts) {
            currentSpeed--;
            ghost.setSpeed(currentSpeed);
        }
    }

    private void addGhost() {
        Random random = new Random();
        int rand = random.nextInt() % maze.getStructure().getVertices().size();
        while (rand < 0) rand += maze.getStructure().getVertices().size();
        if (rand == 0) rand = maze.getStructure().getVertices().size() - 1;
        Cell newPosition = maze.getStructure().getVertices().get(rand);
        int picID = 1 + ghosts.size() % 4;
        Ghost newGhost = new Ghost(newPosition, picID, currentSpeed);
        ghosts.add(newGhost);
    }

    private ArrayList<Cell> ghostPositions() {
        ArrayList<Cell> res = new ArrayList<>();
        for (Ghost ghost : ghosts) {
            res.add(ghost.getCurrentPosition());
        }
        return res;
    }
}
