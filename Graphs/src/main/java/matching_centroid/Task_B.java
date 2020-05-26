package matching_centroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

//Задача B. Pairs. Паросочетание [2 sec, 256 mb]

public class Task_B {

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

        boolean[] used;
        int[] mt;

        ArrayList<Edge>[] edges;

        Graph(int n, int m) {
            this.n = n;
            this.m = m;

            this.used = new boolean[n];
            this.mt = new int[m];

            this.edges = new ArrayList[n];
            for (int i = 0; i < n; i++)
                edges[i] = new ArrayList<>();
        }

        void addEdge(int vertex, int neighbor) {
            this.edges[vertex].add(new Edge(neighbor));
        }

        void findMatching() {
            Arrays.fill(mt, -1);
            for (int i = 0; i < n; i++) {
                Arrays.fill(used, false);
                dfs(i);
            }

            int flow = 0;
            for (int i = 0; i < m; i++)
                if (mt[i] != -1) flow++;
            System.out.println(flow);
            for (int i = 0; i < m; i++) {
                if (mt[i] != -1) {
                    System.out.println((mt[i] + 1) + " " + (i + 1));
                }
            }
        }

        boolean dfs(int v) {
            if (used[v]) {
                return false;
            }

            used[v] = true;
            for (Edge edge : edges[v]) {
                int to = edge.d;
                if (mt[to] == -1 || dfs(mt[to])) {
                    mt[to] = v;
                    return true;
                }
            }

            return false;
        }

        void print() {
            for (int i = 0; i < n; i++) {
                System.out.println(i + ": " + edges[i]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");
        int n = Integer.parseInt(temp[0]);
        int m = Integer.parseInt(temp[1]);

        Graph g = new Graph(n, m);

        for (int v = 0; v < n; v++) {
            temp = br.readLine().split(" ");
            int j = 0;
            while (!temp[j].equals("0")) {
                int u = Integer.parseInt(temp[j]) - 1;
                g.addEdge(v, u);
                j++;
            }
        }

        g.findMatching();
    }
}

//2 2
//1 2 0
//2 0