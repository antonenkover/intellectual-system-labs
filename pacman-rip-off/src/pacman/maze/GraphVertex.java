package pacman.maze;

import java.util.ArrayList;

public class GraphVertex<T> {

    public int value;
    public T pacwoman;
    public ArrayList<T> ghosts;

    public GraphVertex(int value, T pacwoman, ArrayList<T> ghosts) {
        this.value = value;
        this.pacwoman = pacwoman;
        this.ghosts = ghosts;
    }
}
