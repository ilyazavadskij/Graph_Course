package matching_centroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//Задача C. Центроиды дерева [2.5 sec, 256 mb]

public class Task_C {

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
        int k;

        int center;
        int[] level;
        int[] parent;
        int[][] f;

        int[] colour;
        Map<Integer, Integer>[] distance;

        ArrayList<Edge>[] edges;

        Graph(int n) {
            this.n = n;

            this.level = new int[n];
            this.parent = new int[n];
            this.k = 1;
            while ((1 << k) <= n) {
                this.k++;
            }
            this.f = new int[k][n];

            this.colour = new int[n];
            this.edges = new ArrayList[n];
            this.distance = new HashMap[n];
            for (int i = 0; i < n; i++) {
                edges[i] = new ArrayList<>();
                distance[i] = new HashMap<>();
            }

            Arrays.fill(parent, -1);
            Arrays.fill(colour, -1);
            Arrays.fill(level, -1);
        }

        void addEdge(int vertex, int neighbor) {
            this.edges[vertex].add(new Edge(neighbor));
            this.edges[neighbor].add(new Edge(vertex));
        }

        void addColour(String[] c) {
            for (int i = 0; i < n; i++) {
                colour[i] = Integer.parseInt(c[i]);
            }
        }

        void build(int v, int size, int depth, int last) {
//            System.out.println("build: " + v + " " + size + " " + depth + " " + last);
            center = -1;
            DFS(v, size, -1);
            int c = center;
            level[c] = depth;
            parent[c] = last;
            calc(c, depth, -1, c, 0);
            for (Edge edge : edges[c]) {
                if (level[edge.d] == -1) {
                    build(edge.d, size / 2, depth + 1, c);
                }
            }
        }

        int DFS(int v, int size, int p) {
//            System.out.println("  dfs: " + v + " " + size + " " + p);
            int sum = 1;
            for (Edge edge : edges[v])
                if (level[edge.d] == -1 && edge.d != p)
                    sum += DFS(edge.d, size, v);
            if (center == -1 && (2 * sum >= size || p == -1)) {
//                System.out.println("  center: " + center + " -> " + v);
//                System.out.println("    parent: " + p);
                center = v;
            }
            return sum;
        }

        void calc(int v, int depth, int p, int c, int d) {
            f[depth][v] = d;

            if (distance[c].containsKey(colour[v])) {
                distance[center].put(colour[v], Math.min(distance[center].get(colour[v]), d));
            } else {
                distance[center].put(colour[v], d);
            }

            for (Edge e : edges[v]) {
                if (level[e.d] == -1 && e.d != p) {
                    calc(e.d, depth, v, c, d + 1);
                }
            }
        }

        int getDistance(int v, int c) {
            int d = 2 * n;
            for (int i = v; i != -1; i = parent[i]) {
                if (distance[i].containsKey(c)) {
                    d = Math.min(d, f[level[i]][v] + distance[i].get(c));
                }
            }
            if (d == 2 * n) {
                return -1;
            }
            return d;
        }

        void print() {
            for (int i = 0; i < n; i++) {
                System.out.println(i + ": " + edges[i]);
            }
            System.out.println();
        }

        void printAll() {
            System.out.println("colour: " + Arrays.toString(colour));
            System.out.println("parent: " + Arrays.toString(parent));
            System.out.println("level: " + Arrays.toString(level));

            for (int i = 0; i < k; i++) {
                System.out.println("f" + i + ": " + Arrays.toString(f[i]));
            }

            for (int i = 0; i < n; i++) {
                System.out.println("distance" + i + ": " + distance[i]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");
        int n = Integer.parseInt(temp[0]);

        Graph g = new Graph(n);

        temp = br.readLine().split(" ");
        for (int v = 0; v < n - 1; v++) {
            int u = Integer.parseInt(temp[v]);
            g.addEdge(u, v + 1);
        }

        temp = br.readLine().split(" ");
        g.addColour(temp);

        g.build(0, n, 0, -1);

        temp = br.readLine().split(" ");
        int q = Integer.parseInt(temp[0]);
        for (int i = 0; i < q; i++) {
            temp = br.readLine().split(" ");
            int v = Integer.parseInt(temp[0]);
            int c = Integer.parseInt(temp[1]);
            System.out.print(g.getDistance(v, c) + " ");
        }
    }

}

//5
//0 1 1 3
//1 2 3 2 1
//9
//0 1
//0 2
//0 3
//1 0
//2 1
//2 2
//3 3
//3 1
//4 2