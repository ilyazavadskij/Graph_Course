package bridge_cut_vertex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Task_D {

    static class Graph {
        int n;
        final int MAX_VALUE;

        boolean[] visited;
        int[][] connections;
        int[] minEdge;
        int[] selEdge;

        Graph(int n) {
            this.n = n;
            this.MAX_VALUE = n * 100000;

            this.visited = new boolean[n];
            this.minEdge = new int[n];
            this.selEdge = new int[n];

            this.connections = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    this.connections[i][j] = MAX_VALUE;
                }
            }
        }

        void addEdge(int vertex, int neighbor, int weight) {
            this.connections[vertex][neighbor] = weight;
        }

        void minSpanningTree() {
            for (int i = 0; i < n; i++) {
                visited[i] = false;
                minEdge[i] = MAX_VALUE;
                selEdge[i] = -1;
            }
            Prim();
            System.out.println(Arrays.stream(minEdge).sum());
        }

        void Prim() {
            minEdge[0] = 0;

            for (int i = 0; i < n; i++) {
                int v = -1;
                for (int j = 0; j < n; j++) {
                    if (!visited[j] && (v == -1 || minEdge[j] < minEdge[v])) {
                        v = j;
                    }
                }
                visited[v] = true;

                for (int to = 0; to < n; to++) {
                    if (connections[v][to] < minEdge[to]) {
                        minEdge[to] = connections[v][to];
                        selEdge[to] = v;
                    }
                }
            }
        }

        void print() {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    System.out.print(connections[i][j] + " ");
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");
        int n = Integer.parseInt(temp[0]);
        int m = Integer.parseInt(temp[1]);

        Graph g = new Graph(n);

        for (int i = 0; i < m; i++) {
            temp = br.readLine().split(" ");
            int s = Integer.parseInt(temp[0]) - 1;
            int d = Integer.parseInt(temp[1]) - 1;
            int w = Integer.parseInt(temp[2]);
            g.addEdge(s, d, w);
            g.addEdge(d, s, w);
        }

        g.minSpanningTree();
    }

}

//4 4
//1 2 1
//2 3 2
//3 4 5
//4 1 4