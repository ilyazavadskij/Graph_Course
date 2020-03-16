package shortest_paths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Task_C {

    private static long MAX_VALUE = Long.MAX_VALUE;

    static class Graph {

        long distances[];
        long[][] adjacencyMatrix;
        int n;


        Graph(int n) {
            this.n = n;
            this.adjacencyMatrix = new long[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    adjacencyMatrix[i][j] = MAX_VALUE;
                }
            }
            this.distances = new long[n];
        }

        void addEdge(int b, int c, long w) {
            adjacencyMatrix[b][c] = w;
        }

        void BellmanFord(int source) {
            int[] parent = new int[n];

            for (int node = 0; node < n; node++) {
                distances[node] = MAX_VALUE;
            }

            distances[source] = 0;
            for (int node = 0; node < n - 1; node++) {
                for (int sourceNode = 0; sourceNode < n; sourceNode++) {
                    for (int destinationNode = 0; destinationNode < n; destinationNode++) {
                        if (adjacencyMatrix[sourceNode][destinationNode] != MAX_VALUE) {
                            if (distances[destinationNode] > distances[sourceNode] + adjacencyMatrix[sourceNode][destinationNode]) {
                                distances[destinationNode] = distances[sourceNode] + adjacencyMatrix[sourceNode][destinationNode];
                                parent[destinationNode] = sourceNode;
                            }
                        }
                    }
                }
            }

            boolean[] negativeCircle = new boolean[n];
            for (int sourceNode = 0; sourceNode < n; sourceNode++) {
                for (int destinationNode = 0; destinationNode < n; destinationNode++) {
                    if (adjacencyMatrix[sourceNode][destinationNode] != MAX_VALUE) {
                        if (distances[destinationNode] > distances[sourceNode] + adjacencyMatrix[sourceNode][destinationNode]) {
                            parent[destinationNode] = sourceNode;
                            int current = destinationNode;
                            do {
                                negativeCircle[current] = true;
                                current = parent[current];
//                                System.out.println("  " + current);
                            } while (current != destinationNode);
                        }
                    }
                }
            }

            for (int vertex = 0; vertex < n; vertex++) {
                if (distances[vertex] == MAX_VALUE) {
                    System.out.println("*");
                    continue;
                }
                if (negativeCircle[vertex]) {
                    System.out.println("-");
                    continue;
                }
                System.out.println(distances[vertex]);
            }
        }
    }

    public static void main(String[] arg) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");

        int n = Integer.parseInt(temp[0]);
        int m = Integer.parseInt(temp[1]);
        int s = Integer.parseInt(temp[2]) - 1;

        Graph g = new Graph(n);

        for (int i = 0; i < m; i++) {
            temp = br.readLine().split(" ");
            int b = Integer.parseInt(temp[0]) - 1;
            int c = Integer.parseInt(temp[1]) - 1;
            long w = Long.parseLong(temp[2]);
            g.addEdge(b, c, w);
        }

//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                System.out.print(g.adjacencyMatrix[i][j] + " ");
//            }
//            System.out.println();
//        }

        g.BellmanFord(s);
    }

}
