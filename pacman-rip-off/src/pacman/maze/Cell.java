package pacman.maze;

public class Cell {

    private int x;
    private int y;
    private CellType type;

    public Cell(int x, int y, CellType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public Cell(Cell cell) {
        this.x = cell.getX();
        this.y = cell.getY();
        this.type = cell.getType();
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public CellType getType() {
        return type;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setType(CellType type) {
        this.type = type;
    }
}
