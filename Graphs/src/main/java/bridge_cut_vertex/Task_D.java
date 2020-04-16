package bridge_cut_vertex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.LinkedList;

//Задача D. Остовное дерево 2 [2 sec, 256 mb]

public class Task_D {

    static class Edge {
        int u;
        int v;
        int weight;

        Edge(int u, int v, int dist) {
            this.u = u;
            this.v = v;
            this.weight = dist;
        }

        int getWeight() {
            return weight;
        }

        public String toString() {
            return "({" + u + "-" + v + "}=" + weight + ")";
        }
    }

    static class Graph {
        int n;
        int[] mas;
        int[] size;
        LinkedList<Edge> connections;

        Graph(int n) {
            this.n = n;
            this.mas = new int[n];
            this.size = new int[n];
            this.connections = new LinkedList<>();
        }

        void addEdge(int vertex, int neighbor, int weight) {
            this.connections.addLast(new Edge(vertex, neighbor, weight));
        }

        void Kruskal() {
            for (int i = 0; i < n; i++) {
                mas[i] = i;
                size[i] = 1;
            }

            connections.sort(Comparator.comparing(Edge::getWeight));

            int count = 0;
            for (Edge edge : connections) {
                if (union(edge.u, edge.v)) {
                    count += edge.weight;
                }
            }
            System.out.println(count);
        }

        int root(int n) {
            if (n == mas[n]) {
                return n;
            }
            return mas[n] = root(mas[n]);
        }


        boolean union(int x, int y) {
            x = root(x);
            y = root(y);
            if (x == y) {
                return false;
            }
            if (size[x] < size[y]) {
                int k = x;
                x = y;
                y = k;
            }

            mas[y] = x;
            size[x] += size[y];
            return true;
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

        g.Kruskal();
    }

}

//4 4
//1 2 1
//2 3 2
//3 4 5
//4 1 4