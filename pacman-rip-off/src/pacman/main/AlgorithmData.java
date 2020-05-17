package pacman.main;

import java.util.ArrayList;

public class AlgorithmData<T> {

    public int stepCount;
    public ArrayList<T> result;

    public AlgorithmData(int stepCount, ArrayList<T> result) {
        this.stepCount = stepCount;
        this.result = result;
    }
}
