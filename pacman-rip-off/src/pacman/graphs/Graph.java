package pacman.graphs;

import javafx.util.Pair;
import pacman.main.AlgorithmData;
import pacman.main.Finals;
import pacman.maze.GraphData;
import pacman.maze.GraphVertex;

import java.util.*;

public class Graph<T> {

    private ArrayList<T> vertices;
    private ArrayList<Pair<T, T>> edges;

    public Graph() {
    }
    public Graph(Graph graph) {
        this.vertices = new ArrayList<>(graph.getVertices());
        this.edges = new ArrayList<>(graph.getEdges());
    }
    public Graph(ArrayList<T> vertices, ArrayList<Pair<T, T>> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public ArrayList<Pair<T, T>> getEdges(T vertex) {
        ArrayList<Pair<T, T>> result = new ArrayList<>();
        for(Pair<T, T> edge: edges) {
            if (edge.getKey() == vertex || edge.getValue() == vertex) result.add(edge);
        }
        return result;
    }

    public ArrayList<T> getNeighbours(T vertex) {
        ArrayList<T> result = new ArrayList<>();
        for (Pair<T, T> edge: edges) {
            if (edge.getValue() == vertex) {
                result.add(edge.getKey());
            }
            else if (edge.getKey() == vertex) {
                result.add(edge.getValue());
            }
        }
        return result;
    }

    public AlgorithmData<T> BFS(T start, ArrayList<T> targets) {
        if (targets.size() == 0) return null;
        boolean visited[] = new boolean[vertices.size()];
        LinkedList<T> queue = new LinkedList<T>();

        visited[vertices.indexOf(start)]=true;
        queue.add(start);

        T s = start;
        ArrayList<T> result = new ArrayList<>();
        HashMap<T, T> parentList = new HashMap<>();
        while (queue.size() != 0)
        {
            s = queue.poll();

            ArrayList<T> neighbours = getNeighbours(s);
            for (T neighbour: neighbours) {
                if (!visited[vertices.indexOf(neighbour)]) {
                    parentList.put(neighbour, s);
                    if (targets.contains(neighbour)) {

                        result.add(neighbour);
                        T parent = parentList.get(neighbour);
                        while (parent != start) {
                            result.add(parent);
                            parent = parentList.get(parent);
                        }
                        Collections.reverse(result);
                        return new AlgorithmData(parentList.size(), result);
                    }
                    visited[vertices.indexOf(neighbour)] = true;
                    queue.add(neighbour);
                }
            }
        }
        return null;
    }

    public AlgorithmData<T> DFS(T start, ArrayList<T> targets) {
        if (targets.size() == 0) return null;
        boolean visited[] = new boolean[vertices.size()];
        Stack<T> stack = new Stack<>();
        stack.push(start);
        T s = start;
        while (stack.size() != 0) {
            visited[vertices.indexOf(s)] = true;
            ArrayList<T> neighbours = getNeighbours(s);
            ArrayList<T> unvisitedNeighbours = new ArrayList<>();
            for (T neighbour: neighbours) {
                if (!visited[vertices.indexOf(neighbour)]) unvisitedNeighbours.add(neighbour);
            }
            if (unvisitedNeighbours.size() == 0) {
                stack.pop();
                s = stack.peek();
            }
            for (int i = 0; i < unvisitedNeighbours.size(); i++) {
                T neighbour  = unvisitedNeighbours.get(i);
                if (!visited[vertices.indexOf(neighbour)]) {
                    stack.push(neighbour);
                    s = neighbour;
                    if (targets.contains(neighbour)) {
                        ArrayList<T> result = new ArrayList<>(stack);
                        result.remove(0);
                        int count = 0;
                        for (boolean bool: visited) {
                            if (bool) count++;
                        }
                        return new AlgorithmData<T>(count, result);
                    }
                    break;
                }
            }
        }
        return null;
    }

    // return next vertex to go
    public T MinMax(int i, GraphVertex<T> start, GraphData<T> graph, int depth, boolean pacwoman) {
        int bestOption = MMrecursive(start, graph, depth, pacwoman);
        Random random = new Random();
        int prob = random.nextInt() % Finals.PROBABILITY_OF_RANDOM;
        ArrayList<GraphVertex<T>> children = graph.findChildren(start);
        if (prob == 0 && !pacwoman) {
            prob = random.nextInt() % children.size();
            while (prob < 0) prob += children.size();
            return children.get(prob).ghosts.get(i);
        }
        else {
            for (GraphVertex<T> child: children) {
                if (child.value == bestOption) {
                    if (pacwoman) {
                        return child.pacwoman;
                    }
                    else {
                        return child.ghosts.get(i);
                    }
                }
            }
            return null;
        }
    }

    private int MMrecursive(GraphVertex<T> position, GraphData<T> graph, int depth, boolean pacwoman) {
        if (depth == 0) {
            return position.value;
        }
        else {
            if (pacwoman) {
                int max = Integer.MIN_VALUE;
                ArrayList<GraphVertex<T>> children = graph.findChildren(position);
                if (children.size() == 0) max = position.value;
                for (GraphVertex<T> child: children) {
                    int eval = MMrecursive(child, graph, depth-1, !pacwoman);
                    max = Math.max(max, eval);
                }
                position.value = max;
                return max;
            }
            else {
                int min = Integer.MAX_VALUE;
                ArrayList<GraphVertex<T>> children = graph.findChildren(position);
                if (children.size() == 0) min = position.value;
                for (GraphVertex<T> child: children) {
                    int eval = MMrecursive(child, graph, depth-1, !pacwoman);
                    min = Math.min(min, eval);
                }
                position.value = min;
                return min;
            }
        }
    }

    public int getRange(T from, T to, ArrayList<T> prohibited) {
        if (from == to) return 0;
        int res = 0;
        ArrayList<T> newVerts = new ArrayList<>(vertices);
        ArrayList<Pair<T, T>> newEdges = new ArrayList<>(edges);
        newVerts.removeAll(prohibited);
        ArrayList<Pair<T, T>> toRemove = new ArrayList<>();
        for (Pair<T, T> edge: newEdges) {
            if (prohibited.contains(edge.getValue()) || prohibited.contains(edge.getKey())) toRemove.add(edge);
        }
        newEdges.removeAll(toRemove);
        Graph<T> newGraph = new Graph<>(newVerts, newEdges);
        ArrayList<T> targets = new ArrayList<>(Arrays.asList(to));
        try {
            res = newGraph.BFS(from, targets).result.size();
        }
        catch (Exception e) {
            res = Finals.MAX_LENGTH_OF_PATH;
        }
        return res;
    }

    public ArrayList<T> getVertices() {
        return vertices;
    }
    public ArrayList<Pair<T, T>> getEdges() {
        return edges;
    }

    public void setVertices(ArrayList<T> vertices) {
        this.vertices = vertices;
    }
    public void setEdges(ArrayList<Pair<T, T>> edges) {
        this.edges = edges;
    }
}
