import java.util.*;

public class Maze extends UnionFind{

    private static final int right = 0;
    private static final int down = 1;
    private static final int left = 2;
    private static final int up = 3;
    private static Random randomGenerator;  // for random numbers

    private static int size;


    // A board is an SizexSize array whose values are Points
    private static Point[][] board;

    // A graph is simply a set of edges: graph[i][d] is the edge
    // where i is the index for a Point and d is the direction
    private static Edge[][] graph;
    private static int N;   // number of points in the graph

    /**
     * Initializes an empty union–find data structure with {@code n} sites
     * {@code 0} through {@code n-1}. Each site is initially in its own
     * component.
     *
     * @param n the number of sites
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public Maze(int n) {
        super(n);
    }

    /**
     * this is a class that describes one point's location in a maze with x and y
     */
    private static class Point {

        private int x, y;

        // Constructor
        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void copy(Point p) {
            this.x = p.x;
            this.y = p.y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    /**
     * This class describes an edge that links two neighboring points.
     * It could be represented by a point and a direction.
     */
    private static class Edge {
        Point point;
        int direction;    // one of right, down, left, up
        boolean used;     // for maze creation
        boolean deleted;  // for maze creation

        // Constructor with a point (location) and direction
        private Edge(Point p, int d) {
            this.point = p;
            this.direction = d;
            this.used = false;
            this.deleted = false;
        }
    }

    private static void setupEmptyMaze() {

        // Create one dummy edge for all boundary edges.
        Edge dummy = new Edge(new Point(0, 0), 0);
        dummy.used = true;

        // Create board and graph.
        board = new Point[size][size];
        N = size*size;  // number of points
        graph = new Edge[N][4];

        /*
         *  This part generates and connects the edges
         */
        for (int i = 0; i < size; ++i)
            for (int j = 0; j < size; ++j) {
                Point p = new Point(i, j);
                int pindex = i*size+j;   // Point(i, j)'s index is i*Size + j

                board[i][j] = p;

                graph[pindex][right] = (j < size-1)? new Edge(p, right): dummy;
                graph[pindex][down] = (i < size-1)? new Edge(p, down) : dummy;
                graph[pindex][left] = (j > 0)? graph[pindex-1][right] : dummy;
                graph[pindex][up] = (i > 0)? graph[pindex-size][down] : dummy;

            }
    }


    /**
     * This function prints a maze of how it looks when it's first initialized
     */
    private static void displayInitBoard() {
        System.out.println("\nInitial Configuration:");

        for (int i = 0; i < size; ++i) {
            System.out.print("\t-");
            for (int j = 0; j < size; ++j) System.out.print("----");
            System.out.println();
            if (i == 0) System.out.print("Start");
            else System.out.print("\t|");
            for (int j = 0; j < size; ++j) {
                if (i == size-1 && j == size-1)
                    System.out.print("\tEnd");
                else System.out.print("\t|");
            }
            System.out.println();
        }
        System.out.print("\t-");
        for (int j = 0; j < size; ++j) System.out.print("----");
        System.out.println();
    }

    private static void printMaze() {
        System.out.println("\nPrint the generated maze...");

        for(int i = 0; i < size; i++) {
            System.out.print("\t-");
            for(int j = 0; j < size; j++) System.out.print("----");
            System.out.println();
        }
    }

    private static void getMazeSize() {
        // Read in the Size of a maze
        Scanner scan = new Scanner(System.in);
        try {
            System.out.println("What's the size of your maze? ");
            size = scan.nextInt();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        scan.close();
    }

    private static int getARandomNumber() {
        // Hint: To randomly pick an edge in the maze, you may
        // randomly pick a point first, then randomly pick
        // a direction to get the edge associated with the point.
        randomGenerator = new Random();
        return randomGenerator.nextInt(N);
    }

    private static int createMaze() {

    }

    /**
     * main gets user input of the size of the maze.
     * @param args arguments? In this case, we don't have any source outside
     */
    public static void main(String[] args) {

        getMazeSize();
        setupEmptyMaze();
        displayInitBoard();

        int i = getARandomNumber();
        System.out.println("\nA random number between 0 and " + (N-1) + ": " + i);

    }
}

