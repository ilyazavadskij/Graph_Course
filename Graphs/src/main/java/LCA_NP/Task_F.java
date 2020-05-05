package LCA_NP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

//Задача F. LCA Problem Revisited [4 sec, 256 mb]

public class Task_F {

    static class Edge {
        int d;

        Edge(int d) {
            this.d = d;
        }
    }

    static class Graph {
        int n;
        int m;
        int l;

        int timer;

        int[] inTime;
        int[] outTime;
        int[][] up;

        ArrayList<Edge>[] connections;

        Graph(int n, int m) {
            this.n = n;
            this.m = m;

            inTime = new int[n];
            outTime = new int[n];

            this.l = 1;
            while ((1 << l) <= n) {
                this.l++;
            }
            up = new int[n][l + 1];

            this.connections = new ArrayList[n];
            for (int i = 0; i < n; i++)
                connections[i] = new ArrayList<>();
        }

        void addEdge(int vertex, int neighbor) {
            this.connections[vertex].add(new Edge(neighbor));
            this.connections[neighbor].add(new Edge(vertex));
        }

        void initialize() {
            timer = 0;
            DFS(0, 0);
        }

        void DFS(int v, int p) {
            inTime[v] = timer++;
            up[v][0] = p;
            for (int i = 1; i <= l; ++i)
                up[v][i] = up[up[v][i - 1]][i - 1];

            for (Edge edge : connections[v]) {
                if (edge.d != p) {
                    DFS(edge.d, v);
                }
            }
            outTime[v] = timer++;
        }

        int LCA(int a, int b) {
            if (upper(a, b)) return a;
            if (upper(b, a)) return b;

            for (int i = l; i >= 0; --i)
                if (!upper(up[a][i], b))
                    a = up[a][i];

            return up[a][0];
        }

        boolean upper(int a, int b) {
            return (inTime[a] <= inTime[b]) && (outTime[a] >= outTime[b]);
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");
        int n = Integer.parseInt(temp[0]);
        int m = Integer.parseInt(temp[1]);

        Graph g = new Graph(n, m);

        temp = br.readLine().split(" ");
        for (int i = 0; i < n - 1; i++) {
            int vertex = Integer.parseInt(temp[i]);
            g.addEdge(i + 1, vertex);
        }

        g.initialize();

        temp = br.readLine().split(" ");
        int a1 = Integer.parseInt(temp[0]);
        int a2 = Integer.parseInt(temp[1]);

        temp = br.readLine().split(" ");
        long x = Long.parseLong(temp[0]);
        long y = Long.parseLong(temp[1]);
        long z = Long.parseLong(temp[2]);

        long v = 0;
        long s = 0;
        for (int i = 0; i < m; i++) {
            v = g.LCA((int) ((a1 + v) % n), a2);
            s += v;
            a1 = (int) ((x * a1 + y * a2 + z) % n);
            a2 = (int) ((x * a2 + y * a1 + z) % n);
        }

        System.out.println(s);
    }

}

//3 2
//0 1
//2 1
//1 1 0

//1 2
//
//0 0
//1 1 1