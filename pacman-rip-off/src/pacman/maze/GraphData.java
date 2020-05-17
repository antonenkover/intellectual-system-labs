package pacman.maze;

import javafx.util.Pair;

import java.util.ArrayList;

public class GraphData<T> {

    public ArrayList<GraphVertex<T>> vertices;
    public ArrayList<Pair<GraphVertex<T>, GraphVertex<T>>> edges;

    public GraphData(ArrayList<GraphVertex<T>> vertices, ArrayList<Pair<GraphVertex<T>, GraphVertex<T>>> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public ArrayList<GraphVertex<T>> findChildren(GraphVertex<T> parent) {
        ArrayList<GraphVertex<T>> res = new ArrayList<>();
        for (Pair<GraphVertex<T>, GraphVertex<T>> edge: edges) {
            if (edge.getValue() == parent) res.add(edge.getKey());
        }
        return res;
    }

}
