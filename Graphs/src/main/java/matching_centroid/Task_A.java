package matching_centroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

//Задача A. Самое дешевое ребро [2 sec, 256 mb]

public class Task_A {

    static class Edge {
        int d;
        int w;

        Edge(int d, int w) {
            this.d = d;
            this.w = w;
        }

        public String toString() {
            return "{" + d + "," + w + "}";
        }
    }

    static class Graph {
        int n;
        int k;

        int center;
        int[] level;
        int[] parent;
        int[][] f;

        ArrayList<Edge>[] connections;

        Graph(int n) {
            this.n = n;

            this.level = new int[n];
            this.parent = new int[n];

            this.k = 1;
            while ((1 << k) <= n) {
                this.k++;
            }
            this.f = new int[k][n];

            this.connections = new ArrayList[n];
            for (int i = 0; i < n; i++)
                connections[i] = new ArrayList<>();

            Arrays.fill(level, -1);
        }

        void addEdge(int vertex, int neighbor, int weight) {
            this.connections[vertex].add(new Edge(neighbor, weight));
            this.connections[neighbor].add(new Edge(vertex, weight));
        }

        void build(int v, int size, int depth, int last) {
            center = -1;
            dfs(v, size, -1);
            int c = center;
            level[c] = depth;
            parent[c] = last;
            calc(c, depth, -1, Integer.MAX_VALUE);
            for (Edge edge : connections[c])
                if (level[edge.d] == -1)
                    build(edge.d, size / 2, depth + 1, c);
        }

        int dfs(int v, int size, int p) {
            int sum = 1;
            for (Edge edge : connections[v])
                if (level[edge.d] == -1 && edge.d != p)
                    sum += dfs(edge.d, size, v);
            if (center == -1 && (2 * sum >= size || p == -1)) {
                center = v;
            }
            return sum;
        }

        void calc(int v, int depth, int p, int minimum) {
            f[depth][v] = minimum;
            for (Edge e : connections[v]) {
                if (level[e.d] == -1 && e.d != p) {
                    calc(e.d, depth, v, Math.min(minimum, e.w));
                }
            }
        }

        int get(int a, int b) {
            int c = getCenter(a, b);
            return Math.min(f[level[c]][a], f[level[c]][b]);
        }

        int getCenter(int a, int b) {
//            System.out.println("center a & b: " + a + " " + b);
//            System.out.println("level  a & b: " + level[a] + " " + level[b]);
            while (level[a] > level[b]) {
                a = parent[a];
//                System.out.println(" center a: " + a);
//                System.out.println(" level  a: " + level[a]);
            }
            while (level[a] < level[b]) {
                b = parent[b];
//                System.out.println(" center b: " + a);
//                System.out.println(" level  b: " + level[b]);
            }
            while (a != b) {
//                System.out.println("  center a & b: " + a + " " + b);
//                System.out.println("    " + "level  a & b: " + level[a] + " " + level[b]);
                a = parent[a];
                b = parent[b];
            }
            return a;
        }


        void print() {
            for (int i = 0; i < n; i++) {
                System.out.println(i + ": " + connections[i]);
            }
            System.out.println();
        }

        void printAll() {
            System.out.println("parent: " + Arrays.toString(parent));
            System.out.println("level: " + Arrays.toString(level));

            for (int i = 0; i < k; i++) {
                System.out.println("f" + i + ": " + Arrays.toString(f[i]));
            }
            System.out.println();
        }


    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");
        int n = Integer.parseInt(temp[0]);

        Graph g = new Graph(n);

        for (int v = 1; v < n; v++) {
            temp = br.readLine().split(" ");
            int u = Integer.parseInt(temp[0]) - 1;
            int weight = Integer.parseInt(temp[1]);
            g.addEdge(u, v, weight);
        }
        g.build(0, n, 0, -1);

        temp = br.readLine().split(" ");
        int m = Integer.parseInt(temp[0]);
        for (int i = 0; i < m; i++) {
            temp = br.readLine().split(" ");
            int v = Integer.parseInt(temp[0]) - 1;
            int u = Integer.parseInt(temp[1]) - 1;
            System.out.println(g.get(v, u));
        }

//        g.print();
//        g.printAll();
    }
}

//5
//1 2
//1 3
//2 5
//3 2
//6
//2 3
//2 5
//3 4
//4 5
//2 4
//3 5
//
