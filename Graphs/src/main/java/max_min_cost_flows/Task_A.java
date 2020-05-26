package max_min_cost_flows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task_A {

    static class Edge {
        int from;
        int to;
        double capacity;
        double flow;

        Edge residual;


        Edge(int from, int to, double capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.flow = 0;
        }

        public String toString() {
            return "{" + from + "," + to + "," + flow + "," + capacity + "}";
        }
    }

    static class Graph {
        int n;
        int s;
        int t;

        final int MAX_FLOW;

        int[] visited;

        List<Edge>[] pipes;
        List<Edge> edges;

        Graph(int n) {
            this.n = n;
            this.s = 0;
            this.t = n - 1;

            this.MAX_FLOW = 10000 * n;

            this.visited = new int[n];

            this.pipes = new ArrayList[n];
            this.edges = new ArrayList<>();
            for (int i = 0; i < n; i++)
                pipes[i] = new ArrayList<>();

        }

        void addEdge(int from, int to, double capacity) {
            Edge edge = new Edge(from, to, capacity);
            Edge residualEdge = new Edge(to, from, capacity);

            edge.residual = residualEdge;
            residualEdge.residual = edge;

            this.pipes[from].add(edge);
            this.pipes[to].add(residualEdge);

            this.edges.add(edge);
        }

        void getFlow() {
            int i = 0;
            Arrays.fill(visited, -1);
            double f = DFS(0, i, MAX_FLOW);
            double maxFlow = 0;


            while (f != 0) {
                maxFlow += f;
                i++;
                f = DFS(0, i, MAX_FLOW);
            }

            printMaxFlow(maxFlow);
        }

        double DFS(int u, int i, double f) {
            if (u == t) {
                return f;
            }

            visited[u] = i;
            for (Edge v : pipes[u]) {
                int to = v.to;
                if (visited[to] != i && v.flow < v.capacity) {
                    double delta = DFS(to, i, Math.min(f, v.capacity - v.flow));
                    if (delta > 0) {
                        v.flow += delta;
                        v.residual.flow -= delta;
                        return delta;
                    }
                }
            }

            return 0;
        }

        void printMaxFlow(double maxFlow) {
            System.out.println(maxFlow);

            for (Edge edge : edges) {
                System.out.println(edge.flow);
            }

        }

        void print() {
            for (int i = 0; i < n; i++) {
                System.out.println(i + ": " + pipes[i]);
            }
            System.out.println();
        }

        void printAll() {
//            System.out.println("parent: " + Arrays.toString(parent));
//            System.out.println("level: " + Arrays.toString(level));

            for (int i = 0; i < n; i++) {
                System.out.print("flow" + i + ": ");
                pipes[i].forEach(edge -> System.out.print("{" + edge.to + ";" + edge.flow + "} "));
                System.out.println();
            }
            System.out.println();
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");

        int n = Integer.parseInt(temp[0]);
        int m;
        if (temp.length == 2) {
            m = Integer.parseInt(temp[1]);
        } else {
            m = Integer.parseInt(br.readLine().split(" ")[0]);
        }
        Graph g = new Graph(n);

        for (int i = 0; i < m; i++) {
            temp = br.readLine().split(" ");
            int v = Integer.parseInt(temp[0]) - 1;
            int u = Integer.parseInt(temp[1]) - 1;
            double weight = Double.parseDouble(temp[2]);
            g.addEdge(v, u, weight);
        }

        g.getFlow();
    }
}

//2
//2
//1 2 1
//2 1 3

//7
//11
//1 2 7
//1 2 7
//1 3 7
//1 4 7
//2 3 7
//2 5 7
//3 6 7
//4 7 7
//5 4 7
//5 6 7
//6 7 7

//6
//8
//1 2 3
//1 3 5
//3 2 4
//2 4 5
//3 5 2
//4 5 6
//4 6 5
//5 6 7

//6
//8
//1 2 3
//1 3 3
//2 3 2
//2 4 3
//3 5 2
//4 5 4
//4 6 2
//5 6 3

//5
//10
//1 2 5.3
//1 3 10.2
//1 4 12.9
//2 4 7.4
//2 5 16.2
//3 2 1.4
//3 4 2.3
//5 3 2.7
//4 5 8.5
//5 1 13.7

//5
//7
//1 2 2
//2 5 5
//1 3 6
//3 4 2
//4 5 1
//3 2 3
//2 4 1