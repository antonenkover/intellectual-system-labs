package pacman.main;

public class Finals {

    public final static String ALGORITHM_USED = "MinMax";

    public final static int SCALE = 1;

    public final static int TEXT_GAP = 25 * SCALE;
    public final static int TEXT_FONT = 25 * SCALE;

    public final static int WINDOW_HEIGHT = 600 * SCALE;
    public final static int WINDOW_WIDTH = 500 * SCALE;

    public final static int CELL_SIZE = 25 * SCALE;

    public final static int TABLET_SIZE = 5 * SCALE;
    public final static int TABLET_GAP = (CELL_SIZE - TABLET_SIZE)/2;

    public final static int PACWOMAN_SIZE = 15 * SCALE;
    public final static int PACWOMAN_GAP = (CELL_SIZE - PACWOMAN_SIZE)/2;

    public static int COST_OF_TABLET = 1;
    public final static int COST_OF_GHOST = -37;
    public final static int COST_ON_TABLET = 10000000;
    public final static int COST_ON_PACMAN = -10000000;

    public final static int PROBABILITY_OF_RANDOM = 3;

    public final static int MAX_WIDTH = 36;
    public final static int MAX_HEIGHT = 28;
    public final static int MAX_LENGTH_OF_PATH = MAX_WIDTH * MAX_HEIGHT;

    public final static int MINIMAX_DEPTH = 3;

    public final static int MIN_SPEED = 3;
}
