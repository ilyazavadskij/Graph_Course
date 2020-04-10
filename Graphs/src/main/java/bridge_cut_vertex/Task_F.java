package bridge_cut_vertex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Task_F {

    static class Graph {

        final int MAX_VALUE;
        int n;
        int[][] coordinates;
        double[][] connections;
        double[] minEdge;
        boolean[] visited;
        int[] selEdge;

        int[] parent;

        Graph(int n) {
            this.n = n;
            this.MAX_VALUE = n * 100000;

            this.coordinates = new int[n][2];
            this.connections = new double[n][n];

            this.visited = new boolean[n];
            this.minEdge = new double[n];
            this.selEdge = new int[n];
        }

        void addCoordinate(int i, int x, int y) {
            this.coordinates[i][0] = x;
            this.coordinates[i][1] = y;
        }

        void initialize() {
            for (int i = 0; i < n; i++) {
                for (int j = i; j < n; j++) {
                    if (i == j) {
                        connections[i][i] = 0;
                        continue;
                    }
                    double d = distance(coordinates[i][0], coordinates[i][1], coordinates[j][0], coordinates[j][1]);
                    connections[i][j] = d;
                    connections[j][i] = d;
                }
            }
        }

        double distance(int x1, int y1, int x2, int y2) {
            return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        }

        void Prim() {
            for (int i = 0; i < n; i++) {
                visited[i] = false;
                minEdge[i] = MAX_VALUE;
                selEdge[i] = -1;
            }

            double total = 0;
            minEdge[0] = 0;

            for (int i = 0; i < n; i++) {
                int v = -1;
                for (int j = 0; j < n; j++) {
                    if (!visited[j] && (v == -1 || minEdge[j] < minEdge[v])) {
                        v = j;
                    }
                }
                visited[v] = true;
                total += minEdge[v];

                for (int to = 0; to < n; to++) {
                    if (connections[v][to] < minEdge[to]) {
                        minEdge[to] = connections[v][to];
                        selEdge[to] = v;
                    }
                }
            }

            System.out.println(total);
        }
    }


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");
        int n = Integer.parseInt(temp[0]);

        Graph g = new Graph(n);

        for (int i = 0; i < n; i++) {
            temp = br.readLine().split(" ");
            int x = Integer.parseInt(temp[0]);
            int y = Integer.parseInt(temp[1]);
            g.addCoordinate(i, x, y);
        }
        g.initialize();

        g.Prim();
    }

}

//6
//1 1
//7 1
//2 2
//6 2
//1 3
//7 3