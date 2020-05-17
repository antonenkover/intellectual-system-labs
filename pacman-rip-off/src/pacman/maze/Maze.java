package pacman.maze;

import javafx.util.Pair;
import pacman.graphs.Graph;
import pacman.main.Finals;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Maze {
    private ArrayList<Cell> cells;
    private Graph<Cell> structure;

    public Maze(Graph<Cell> structure) {
        this.structure = structure;
    }

    public Maze(String path) {
        ArrayList<Cell> newCells = new ArrayList<>();
        ArrayList<Cell> vertexes = new ArrayList<>();
        ArrayList<Pair<Cell, Cell>> edges;

        try {
            Scanner scanner = new Scanner(new File(path));
            while (scanner.hasNextLine()) {
                String[] cellInfo = scanner.nextLine().split(" ");
                CellType ct;
                switch (cellInfo[2]) {
                    case "0":
                        ct = CellType.Empty;
                        break;
                    case "1":
                        ct = CellType.Wall;
                        break;
                    case "2":
                        ct = CellType.Tablet;
                        break;
                    default:
                        ct = CellType.Empty;
                        break;
                }
                newCells.add(new Cell(Integer.parseInt(cellInfo[0]), Integer.parseInt(cellInfo[1]), ct));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        this.cells = newCells;

        for (Cell cell : newCells) {
            if (cell.getType() != CellType.Wall) {
                vertexes.add(cell);
            }
        }

        edges = createEdges(vertexes);

        this.structure = new Graph<>(vertexes, edges);
    }

    public Maze(Maze maze) {
        this.cells = new ArrayList<>(maze.getCells());
        this.structure = new Graph<>(maze.getStructure());
    }


    private ArrayList<Pair<Cell, Cell>> createEdges(ArrayList<Cell> vertexes) {
        ArrayList<Pair<Cell, Cell>> result = new ArrayList<>();

        for (Cell cell : vertexes) {
            for (Cell anotherCell : vertexes) {
                if (cell != anotherCell) {
                    int x1 = cell.getX();
                    int x2 = anotherCell.getX();
                    int y1 = cell.getY();
                    int y2 = anotherCell.getY();
                    if (((x2 == x1 + 1 || x2 == x1 - 1) && y1 == y2) ||
                            ((y2 == y1 + 1 || y2 == y1 - 1) && x1 == x2)) {
                        if (!containsEdge(result, cell, anotherCell)) {
                            result.add(new Pair<>(cell, anotherCell));
                        }
                    }
                }
            }
        }

        return result;
    }

    private boolean containsEdge(ArrayList<Pair<Cell, Cell>> edges, Cell c1, Cell c2) {
        for (Pair<Cell, Cell> edge : edges) {
            if (edge.getKey() == c1 && edge.getValue() == c2) return true;
            if (edge.getKey() == c2 && edge.getValue() == c1) return true;
        }
        return false;
    }

    public ArrayList<Cell> getAllTablets() {
        ArrayList<Cell> result = new ArrayList<>();
        for (Cell cell : cells) {
            if (cell.getType() == CellType.Tablet) result.add(cell);
        }
        return result;
    }

    public ArrayList<Cell> getAllTabletsNotAtGhosts(ArrayList<Cell> ghosts) {
        ArrayList<Cell> res = new ArrayList<>(getAllTablets());
        res.removeAll(ghosts);
        return res;
    }

    public int tabletsCount() {
        return getAllTablets().size();
    }

    public boolean areThereTablets() {
        for (Cell cell : cells) {
            if (cell.getType() == CellType.Tablet) return true;
        }
        return false;
    }

    public void newRandomTablet(Cell currentCell) {
        Random random = new Random();
        int i = 0;
        int currentI = structure.getVertices().indexOf(currentCell);
        i = random.nextInt() % structure.getVertices().size();
        if (i < 0) i += structure.getVertices().size();
        while (i == currentI) {
            i = random.nextInt() % structure.getVertices().size();
            if (i < 0) i += structure.getVertices().size();
        }
        structure.getVertices().get(i).setType(CellType.Tablet);
    }

    public int getHeight() {
        int height = 0;
        for (Cell cell : cells) {
            if (cell.getY() > height) height = cell.getY();
        }
        return height + 1;
    }

    public int getWidth() {
        int width = 0;
        for (Cell cell : cells) {
            if (cell.getX() > width) width = cell.getX();
        }
        return width + 1;
    }

    public GraphData<Cell> formMMGraph(GraphVertex<Cell> parent, Cell pacwoman, ArrayList<Cell> ghosts, int i, int depth,
                                       boolean who, int wait, int ghostSpeed) {
        ArrayList<GraphVertex<Cell>> vertices = new ArrayList<>();
        ArrayList<Pair<GraphVertex<Cell>, GraphVertex<Cell>>> edges = new ArrayList<>();

        if (ghosts.contains(pacwoman)) {
            int val = getValueForVertex(pacwoman, ghosts);
            GraphVertex<Cell> newPair = new GraphVertex<>(val, pacwoman, ghosts);
            vertices.add(newPair);
            if (parent != null) edges.add(new Pair<>(newPair, parent));
            return new GraphData<>(vertices, edges);
        }

        if (pacwoman.getType() == CellType.Tablet && tabletsCount() == 1) {
            int val = getValueForVertex(pacwoman, ghosts);
            GraphVertex<Cell> newPair = new GraphVertex<>(val, pacwoman, ghosts);
            vertices.add(newPair);
            if (parent != null) edges.add(new Pair<>(newPair, parent));
            return new GraphData<>(vertices, edges);
        }

        if (who) {
            // pacman
            ArrayList<Cell> neighbours = structure.getNeighbours(pacwoman);
            int val = 0;
            if (i == depth) {
                val = getValueForVertex(pacwoman, ghosts);
            }
            GraphVertex<Cell> newPair = new GraphVertex<>(val, pacwoman, ghosts);
            vertices.add(newPair);
            if (parent != null) edges.add(new Pair<>(newPair, parent));
            if (i < depth) {
                for (Cell neighbour : neighbours) {
                    GraphData<Cell> nextData = formMMGraph(newPair, neighbour, ghosts, i + 1, depth, !who, wait, ghostSpeed);
                    vertices.addAll(nextData.vertices);
                    edges.addAll(nextData.edges);
                }
            }
        } else {

            if (wait != 0) {
                int val = 0;
                if (i >= depth) {
                    val = getValueForVertex(pacwoman, ghosts);
                }
                GraphVertex<Cell> newPair = new GraphVertex<>(val, pacwoman, ghosts);
                vertices.add(newPair);
                if (parent != null) edges.add(new Pair<>(newPair, parent));

                if (i < depth) {
                    GraphData<Cell> nextData = formMMGraph(newPair, pacwoman, ghosts, i + 1, depth, !who, wait - 1, ghostSpeed);
                    vertices.addAll(nextData.vertices);
                    edges.addAll(nextData.edges);
                }
            } else {
                int val = 0;
                if (i >= depth) {
                    val = getValueForVertex(pacwoman, ghosts);
                }
                GraphVertex<Cell> newPair = new GraphVertex<>(val, pacwoman, ghosts);
                vertices.add(newPair);
                if (parent != null) edges.add(new Pair<>(newPair, parent));

                ArrayList<Cell> ghostsLeft = new ArrayList<>(ghosts);
                ArrayList<GraphVertex<Cell>> ghostsVariations = getAllGhostVariations(ghosts, pacwoman, i == depth - 1, ghostsLeft);
                for (GraphVertex<Cell> variation : ghostsVariations) {
                    if (i < depth) {
                        GraphData<Cell> nextData = formMMGraph(newPair, pacwoman, variation.ghosts, i + 1, depth, !who, ghostSpeed - 1, ghostSpeed);
                        vertices.addAll(nextData.vertices);
                        edges.addAll(nextData.edges);
                    }
                }
            }
        }
        return new GraphData<>(vertices, edges);
    }

    private ArrayList<GraphVertex<Cell>> getAllGhostVariations(ArrayList<Cell> ghosts, Cell pacwoman,
                                                               boolean fin, ArrayList<Cell> ghostsLeft) {
        Cell currentGhost = ghostsLeft.get(0);
        ArrayList<GraphVertex<Cell>> res = new ArrayList<>();

        ArrayList<Cell> neighbours = structure.getNeighbours(currentGhost);

        for (Cell neighbour : neighbours) {
            ArrayList<Cell> ghostsR = new ArrayList<>(ghosts);
            ArrayList<Cell> ghostsLeftR = new ArrayList<>(ghostsLeft);
            ghostsR.set(ghostsR.indexOf(currentGhost), neighbour);
            int val = 0;
            if (fin) {
                val = getValueForVertex(pacwoman, ghosts);
            }
            ghostsLeftR.remove(currentGhost);
            if (ghostsLeftR.isEmpty()) {
                res.add(new GraphVertex<>(val, pacwoman, ghostsR));
            } else {
                res.addAll(getAllGhostVariations(ghostsR, pacwoman, fin, ghostsLeftR));
            }
        }

        return res;
    }

    private int getValueForVertex(Cell pacwomanPos, ArrayList<Cell> ghostsPos) {
        if (ghostsPos.contains(pacwomanPos)) return Finals.COST_ON_PACMAN;
        int sum = 0;
        for (Cell ghost : ghostsPos) {
            sum += Finals.COST_OF_GHOST * (Finals.MAX_LENGTH_OF_PATH - pathLengthToCell(pacwomanPos, ghost, new ArrayList<>()));
        }
        ArrayList<Cell> ghostsPosR = new ArrayList<>(ghostsPos);
        for (Cell ghost : ghostsPos) {
            if (ghost.getType() == CellType.Tablet) ghostsPosR.remove(ghost);
        }
        for (Cell tablet : getAllTabletsNotAtGhosts(ghostsPosR)) {
            int distance = pathLengthToCell(pacwomanPos, tablet, ghostsPosR);
            sum += Finals.COST_OF_TABLET * (Finals.MAX_LENGTH_OF_PATH - distance);
        }
        if (pacwomanPos.getType() == CellType.Tablet) sum += Finals.COST_ON_TABLET;
        return sum;
    }

    private int pathLengthToCell(Cell start, Cell target, ArrayList<Cell> prohibited) {
        return structure.getRange(start, target, prohibited);
    }


    public Graph<Cell> getStructure() {
        return structure;
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public void setStructure(Graph<Cell> structure) {
        this.structure = structure;
    }

    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }
}
