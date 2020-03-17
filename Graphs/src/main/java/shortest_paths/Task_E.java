package shortest_paths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class Task_E {

    private static long MAX_VALUE = 100000;

    static class Graph {

        LinkedList<Integer> path;
        long[] distances;
        long[][] adjacencyMatrix;
        boolean[] visited;
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
            this.visited = new boolean[n];
            this.path = new LinkedList<>();
        }

        void addLine(int i, String[] line) {
            for (int j = 0; j < this.n; j++) {
                adjacencyMatrix[j][i] = Long.parseLong(line[j]);
            }
        }

        void findNegativeCircle() {
            for (int j = 0; j < n; j++) {
                visited[j] = false;
            }

            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    if (BellmanFord(i)) {
                        System.out.println("YES");
                        System.out.println(path.size());
                        path.forEach(v -> System.out.print((v + 1) + " "));
                    }
                    return;
                }
            }
            System.out.println("NO");
        }

        boolean BellmanFord(int source) {
            int[] parent = new int[n];
            path.clear();

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

//            boolean[] negativeCircle = new boolean[n];
            for (int sourceNode = 0; sourceNode < n; sourceNode++) {
                for (int destinationNode = 0; destinationNode < n; destinationNode++) {
                    if (adjacencyMatrix[sourceNode][destinationNode] != MAX_VALUE) {
                        if (distances[destinationNode] > distances[sourceNode] + adjacencyMatrix[sourceNode][destinationNode]) {
                            parent[destinationNode] = sourceNode;
                            int current = destinationNode;
                            do {
//                                negativeCircle[current] = true;
                                current = parent[current];
                                path.addFirst(current);
                            } while (current != destinationNode && current != -1);

                            return current != -1;
                        }
                    }
                }
            }
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        Graph g = new Graph(n);
        for (int i = 0; i < n; i++) {
            g.addLine(i, br.readLine().split(" "));
        }

        g.findNegativeCircle();
    }
}

//6
//0 10 100 100000 100000 100000
//100000 0 5 100000 100000 100000
//100000 100000 0 100000 7 100000
//100000 100000 -18 0 100000 100000
//100000 100000 100000 10 0 100000
//-1 100000 100000 100000 100000 0