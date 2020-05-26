package matching_centroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

//Задача E. Замощение доминошками [2 sec, 256 mb]

public class Task_E {

    static class Edge {
        int d;

        Edge(int d) {
            this.d = d;
        }

        public String toString() {
            return "{" + d + "}";
        }
    }

    static class Graph {
        int n;
        int m;

        int empty;

        boolean[] used;
        int[] mt;

        boolean[][] field;
        ArrayList<Edge>[] edges;

        Graph(int n, int m) {
            this.n = n;
            this.m = m;

            this.used = new boolean[m * n];
            this.mt = new int[m * n];

            this.field = new boolean[n][m];

            this.edges = new ArrayList[m * n];
            for (int i = 0; i < m * n; i++) {
                edges[i] = new ArrayList<>();
            }
        }

        void addLine(int i, String[] temp) {
            for (int j = 0; j < m; j++) {
                this.field[i][j] = !temp[j].equals(".");
            }
        }

        void preProcess() {
            Arrays.fill(mt, -1);

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (!field[i][j]) {
                        continue;
                    }

                    empty++;

                    if ((i + j) % 2 != 0) {
                        continue;
                    }

                    int vertex = i * m + j;
                    if (j != 0 && field[i][j - 1]) {
                        edges[vertex].add(new Edge(vertex - 1));
                    }

                    if ((j < m - 1) && field[i][j + 1]) {
                        edges[vertex].add(new Edge(vertex + 1));
                    }
                    if (i != 0 && field[i - 1][j]) {
                        edges[vertex].add(new Edge(vertex - m));
                    }

                    if ((i < n - 1) && field[i + 1][j]) {
                        edges[vertex].add(new Edge(vertex + m));
                    }
                }
            }
        }

        void fieldTilting(int a, int b) {
            preProcess();

            if (2 * b <= a) {
                System.out.println(empty * b);
                return;
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if ((i + j) % 2 != 0) {
                        continue;
                    }
                    Arrays.fill(used, false);
                    DFS(i * m + j);
                }
            }

            getCost(a, b);
        }

        boolean DFS(int v) {
            if (used[v]) return false;

            used[v] = true;
            for (Edge edge : edges[v]) {
                int to = edge.d;
                if (mt[to] == -1 || DFS(mt[to])) {
                    mt[to] = v;
                    return true;
                }
            }

            return false;
        }

        void getCost(int a, int b) {
            int counter = 0;
            for (int i = 0; i < n * m; i++) {
                if (mt[i] != -1) {
                    counter++;
                }
            }

            System.out.println(counter * a + (empty - 2 * counter) * b);
        }

        void print() {
            for (int i = 0; i < n; i++) {
                System.out.println(i + ": " + Arrays.toString(field[i]));
            }
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");
        int n = Integer.parseInt(temp[0]);
        int m = Integer.parseInt(temp[1]);
        int a = Integer.parseInt(temp[2]);
        int b = Integer.parseInt(temp[3]);

        Graph g = new Graph(n, m);

        for (int i = 0; i < n; i++) {
            temp = br.readLine().split("");
            g.addLine(i, temp);
        }

        g.fieldTilting(a, b);
    }

}

//2 3 3 2
//.**
//.*.