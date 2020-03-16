package shortest_paths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class Task_E {

    private static long MAX_VALUE = 100000;

    static class Graph {

        long[] distances;
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

        void addLine(int i, String[] line) {
            for (int j = 0; j < this.n; j++) {
                adjacencyMatrix[j][i] = Integer.parseInt(line[j]);
            }
        }

        boolean BellmanFord(int source) {
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
                if (negativeCircle[vertex]) {
                    System.out.println("YES");
                    printCircle(vertex, parent);
                    return true;
                }
            }
            return false;
        }

        private void printCircle(int start, int[] parent) {
            LinkedList<Integer> path = new LinkedList<>();
            int current = start;
            do {
                current = parent[current];
                path.addFirst(current + 1);
            } while (current != start);
            System.out.println(path.size());
            path.forEach(v -> System.out.print(v + " "));
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");

        int n = Integer.parseInt(temp[0]);

        Graph g = new Graph(n);

        for (int i = 0; i < n; i++) {
            g.addLine(i, br.readLine().split(" "));
        }

        for (int i = 0; i < n; i++) {
            if (g.BellmanFord(i)) {
                return;
            }
        }
        System.out.println("NO");
    }
}