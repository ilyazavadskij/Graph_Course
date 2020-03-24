package shortest_paths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

//Задача E. Цикл отрицательного веса [2 sec, 256 mb]

public class Task_E {

    static class Graph {
        final int MAX_VALUE = 100000;

        int n;
        int start;
        int[] parent;
        int[][] adjacencyMatrix;

        LinkedList<Integer> circle;

        Graph(int n) {
            this.n = n;
            this.start = -1;

            this.adjacencyMatrix = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    adjacencyMatrix[i][j] = MAX_VALUE;
                }
            }

            this.parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = -1;
            }

            this.circle = new LinkedList<>();
        }

        void addLine(int i, String[] line) {
            for (int j = 0; j < this.n; j++) {
                adjacencyMatrix[i][j] = Integer.parseInt(line[j]);
            }
        }

        void findNegativeCircle() {
            for (int i = 0; i < n; i++) {
                BellmanFord(i);
                if (start != -1) {
                    findCircle();
                    System.out.println("YES");
                    System.out.println(circle.size());
                    circle.forEach(v -> System.out.print((v + 1) + " "));
                    return;
                }
            }
            System.out.println("NO");
        }

        void findCircle() {
            int y = start;
            for (int i = 0; i < n; i++) {
                y = parent[y];
            }

            int current = y;
            do {
                circle.addFirst(current);
                current = parent[current];
            } while (current != y);
        }

        void BellmanFord(int source) {
            int[] distance = new int[n];
            for (int node = 0; node < n; node++) {
                distance[node] = MAX_VALUE;
            }
            distance[source] = 0;

            for (int node = 0; node < n - 1; node++) {
                for (int sourceNode = 0; sourceNode < n; sourceNode++) {
                    for (int destinationNode = 0; destinationNode < n; destinationNode++) {
                        if (adjacencyMatrix[sourceNode][destinationNode] != MAX_VALUE) {
                            if (distance[destinationNode] > distance[sourceNode] + adjacencyMatrix[sourceNode][destinationNode]) {
                                distance[destinationNode] = distance[sourceNode] + adjacencyMatrix[sourceNode][destinationNode];
                                parent[destinationNode] = sourceNode;
                            }
                        }
                    }
                }
            }

            for (int sourceNode = 0; sourceNode < n; sourceNode++) {
                for (int destinationNode = 0; destinationNode < n; destinationNode++) {
                    if (adjacencyMatrix[sourceNode][destinationNode] != MAX_VALUE) {
                        if (distance[destinationNode] > distance[sourceNode] + adjacencyMatrix[sourceNode][destinationNode]) {
                            start = destinationNode;
                            parent[destinationNode] = sourceNode;
                            return;
                        }
                    }
                }
            }
        }
    }


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        Graph g = new Graph(n);
        for (int i = 0; i < n; i++) {
            g.addLine(i, br.readLine().replace("  ", " ").split(" "));
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

//4
//100000 100000 100000 -1
//100000 100000 100000 100000
//100000 100000 100000 100000
//100000 -1 -1 100000

//4
//100000 1000 100000 -1000
//100000 100000 -2 1000
//100000 1 100000 100000
//100000 100000 100000 100000