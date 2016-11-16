import java.util.*;

public class Maze {

    private static final int right = 0;
    private static final int down = 1;
    private static final int left = 2;
    private static final int up = 3;
    private static Random randomGenerator;  // for random numbers

    public static int Size;

    public static int[] parent;
    public static int[] height;

    public static class Point {  // a Point is a position in the maze

        public int x, y;

        // Constructor
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void copy(Point p) {
            this.x = p.x;
            this.y = p.y;
        }
    }

    public static class Edge {
        // an Edge links two neighboring Points:
        // For the grid graph, an edge can be represented by a point and a direction.
        Point point;
        int direction;    // one of right, down, left, up
        boolean used;     // for maze creation
        boolean deleted;  // for maze creation

        // Constructor
        public Edge(Point p, int d) {
            this.point = p;
            this.direction = d;
            this.used = false;
            this.deleted = false;
        }
    }

    public static int find (int toFind) {
        if(false) {
            int r = toFind;
            while (parent[r] != -1) {
                if (r != parent[r])
                    r = parent[r];
                System.out.println("stuck in loop in find...");
            }
            System.out.println("didn't stuck find loop...");
            if (toFind != r) {
                int k = parent[toFind];
                while (k != r) {
                    parent[toFind] = r;
                    toFind = k;
                    k = parent[k];
                }
            }
            System.out.println("Survived find...");
            return r;
        } else {
            int n = parent.length;
            if (toFind < 0 || toFind >= n) {
                throw new IndexOutOfBoundsException("index " + toFind + " is not between 0 and " + (n - 1));
            }
            while (toFind != parent[toFind]) {
                parent[toFind] = parent[parent[toFind]];    // path compression by halving
                toFind = parent[toFind];
            }
            return toFind;
        }
    }

    public static void unionByHeight (int i, int j) {
        int ri = height[i];
        int rj = height[j];
        if (ri < rj) parent[i] = j;
        else if(ri > rj) parent[j] = i;
        else {
            height[j]++;
            parent[j] = i;
        }
    }

    public static Edge[][] createMaze(Edge[][] graph) {
        //System.out.println("Hey I'm just outside the loop...");
        //System.out.printf("find 0: %d, find N - 1: %d", find(0), find(N - 1));
        while(find(0) != find(N - 1)) {
            System.out.println("======================= NEW ROUND ===========================");
            System.out.printf("find 0: %d, find N - 1: %d%n", find(0), find(N - 1));
            //System.out.println("Hey I'm in the loop...");
            int randomCell, randomDirection;
            Edge dummy = new Edge(new Point(0, 0), 0);
            do {
                randomCell = randomGenerator.nextInt(N);
                randomDirection = randomGenerator.nextInt(4);
                //System.out.println("random edge direction picked...");
            }while(graph[randomCell][randomDirection].used);
            System.out.println("a random edge picked...");
            Edge temp = graph[randomCell][randomDirection];
            int x, y = 0;
            x = randomCell;
            switch (randomDirection) {
                case 0: // right
                    if((x + 1) % Size != 0) y = x + 1;
                    break;
                case 1: //down
                    if(x < Size * (Size - 1)) y = x + Size;
                    break;
                case 2: //left
                    if(x % Size != 0) y = x - 1;
                    break;
                case 3: // up
                    if(x >= Size) y = x - Size;
                    break;
            }
            int u = find(x);
            int v = find(y);
            System.out.printf("x: %d, y: %d%n", x, y);
            System.out.printf("u: %d, v: %d%n", u, v);
            if(u != v) {
                unionByHeight(u, v);
                graph[randomCell][randomDirection].deleted = true;
                System.out.printf("Edge deleted: cell#: %d, cellDir: %d%n", randomCell + 1, randomDirection);
                graph[randomCell][randomDirection].used = true;
            } else graph[randomCell][randomDirection].used = true;
            printMaze();
        }
        System.out.printf("find 0: %d, find N - 1: %d%n", find(0), find(N - 1));

        return graph;
    }

    // A board is an SizexSize array whose values are Points
    public static Point[][] board;

    // A graph is simply a set of edges: graph[i][d] is the edge
    // where i is the index for a Point and d is the direction
    public static Edge[][] graph;
    public static int N;   // number of points in the graph

    public static void displayInitBoard() {
        System.out.println("\nInitial Configuration:");

        for (int i = 0; i < Size; ++i) {
            System.out.print("    -");
            for (int j = 0; j < Size; ++j) System.out.print("----");
            System.out.println();
            if (i == 0) System.out.print("Start");
            else System.out.print("    |");
            for (int j = 0; j < Size; ++j) {
                if (i == Size-1 && j == Size-1)
                    System.out.print("    End");
                else System.out.print("   |");
            }
            System.out.println();
        }
        System.out.print("    -");
        for (int j = 0; j < Size; ++j) System.out.print("----");
        System.out.println();
    }

    public static void printMaze() {
        System.out.println("\nPrinting maze");
        /*first line*/
        System.out.print("    -");
        for (int j = 0; j < Size; ++j) System.out.print("----");
        /*to right and down*/
        for (int i = 0; i < Size; ++i) {
            /* right */
            System.out.println();
            if (i == 0) System.out.print("Start");
            else System.out.print("    |");
            for (int j = 0; j < Size; ++j) {
                int currentCell = i * Size + j;
                if (i == Size-1 && j == Size-1)
                    System.out.print("    End");
                else {
                    if(graph[currentCell][0].deleted) {
                        System.out.print("    ");
                    }
                    else {
                        System.out.print("   |");
                    }
                }
            }
            System.out.println();

            /* down */
            System.out.print("    -");
            for (int j = 0; j < Size; ++j) {
                int currentCell = i * Size + j;
                if(graph[currentCell][down].deleted) {
                    System.out.print("    ");
                } else {
                    System.out.print("----");
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {

        // Read in the Size of a maze
        Scanner scan = new Scanner(System.in);
        try {
            System.out.println("What's the size of your maze? ");
            Size = scan.nextInt();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        scan.close();

        // Create one dummy edge for all boundary edges.
        Edge dummy = new Edge(new Point(0, 0), 0);
        dummy.used = true;

        // Create board and graph.
        board = new Point[Size][Size];
        N = Size*Size;  // number of points
        parent = new int[N];
        System.out.printf("parent length: %d%n", parent.length);
        height = new int[N];
        for(int i = 0; i < N; i++) {
            parent[i] = i;
            height[i] = 0;
        }
        graph = new Edge[N][4];

        for (int i = 0; i < Size; ++i)
            for (int j = 0; j < Size; ++j) {
                Point p = new Point(i, j);
                int pindex = i*Size+j;   // Point(i, j)'s index is i*Size + j

                board[i][j] = p;

                graph[pindex][right] = (j < Size-1)? new Edge(p, right): dummy;
                graph[pindex][down] = (i < Size-1)? new Edge(p, down) : dummy;
                graph[pindex][left] = (j > 0)? graph[pindex-1][right] : dummy;
                graph[pindex][up] = (i > 0)? graph[pindex-Size][down] : dummy;

            }

        displayInitBoard();

        // Hint: To randomly pick an edge in the maze, you may
        // randomly pick a point first, then randomly pick
        // a direction to get the edge associated with the point.
        randomGenerator = new Random();
        int i = randomGenerator.nextInt(N);
        System.out.println("\nA random number between 0 and " + (N-1) + ": " + i);
        createMaze(graph);
        printMaze();

    }
}

