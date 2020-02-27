package shortest_paths;

import java.util.Scanner;

public class Task_C {

    static int MAX_VALUE = 100;

    static class Graph {

        int distances[];
        int n;


        public Graph(int n) {
            this.n = n;
            distances = new int[n + 1];
        }

        public void BellmanFordEvaluation(int source, int adjacencymatrix[][]) {
            for (int node = 1; node <= n; node++) {
                distances[node] = MAX_VALUE;
            }

            distances[source] = 0;
            for (int node = 1; node <= n - 1; node++) {
                for (int sourcenode = 1; sourcenode <= n; sourcenode++) {
                    for (int destinationnode = 1; destinationnode <= n; destinationnode++) {
                        if (adjacencymatrix[sourcenode][destinationnode] != MAX_VALUE) {
                            if (distances[destinationnode] > distances[sourcenode]
                                    + adjacencymatrix[sourcenode][destinationnode])
                                distances[destinationnode] = distances[sourcenode]
                                        + adjacencymatrix[sourcenode][destinationnode];
                        }
                    }
                }
            }

            for (int sourcenode = 1; sourcenode <= n; sourcenode++) {
                for (int destinationnode = 1; destinationnode <= n; destinationnode++) {
                    if (adjacencymatrix[sourcenode][destinationnode] != MAX_VALUE) {
                        if (distances[destinationnode] > distances[sourcenode]
                                + adjacencymatrix[sourcenode][destinationnode])
                            System.out.println("The Graph contains negative egde cycle");
                    }
                }
            }

            for (int vertex = 1; vertex <= n; vertex++) {
                System.out.println("distance of source  " + source + " to "
                        + vertex + " is " + distances[vertex]);
            }
        }
    }

    public static void main(String... arg) {
        int n = 0;
        int source;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of vertices");
        n = scanner.nextInt();

        int adjacencymatrix[][] = new int[n + 1][n + 1];
        System.out.println("Enter the adjacency matrix");
        for (int sourcenode = 1; sourcenode <= n; sourcenode++) {
            for (int destinationnode = 1; destinationnode <= n; destinationnode++) {
                adjacencymatrix[sourcenode][destinationnode] = scanner.nextInt();
                if (sourcenode == destinationnode) {
                    adjacencymatrix[sourcenode][destinationnode] = 0;
                    continue;
                }
                if (adjacencymatrix[sourcenode][destinationnode] == 0) {
                    adjacencymatrix[sourcenode][destinationnode] = MAX_VALUE;
                }
            }
        }

        System.out.println("Enter the source vertex");
        source = scanner.nextInt();

        Graph g = new Graph(n);
        g.BellmanFordEvaluation(source, adjacencymatrix);
        scanner.close();
    }

}
