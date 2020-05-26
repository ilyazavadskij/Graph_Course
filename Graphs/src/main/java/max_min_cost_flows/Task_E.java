package max_min_cost_flows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task_E {

    static class Edge {
        int from;
        int to;
        long capacity;
        long flow;

        Edge residual;

        Edge(int from, int to, long capacity) {
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

        final long MAX_FLOW;

        List<Integer>[] graph;
        List<Edge> edges;

        int[] ptr;
        int[] d;

        Graph(int n) {
            this.n = n;
            this.s = 0;
            this.t = n - 1;

            this.MAX_FLOW = 1_000_000_000 * (long) n;

            this.ptr = new int[n];
            this.d = new int[n];

            this.edges = new ArrayList<>();
            this.graph = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                graph[i] = new ArrayList<>();
            }

        }

        void addEdge(int from, int to, long capacity) {
            Edge edge = new Edge(from, to, capacity);
            Edge residualEdge = new Edge(to, from, 0);

            edge.residual = residualEdge;
            residualEdge.residual = edge;

            this.graph[from].add(edges.size());
            this.edges.add(edge);

            this.graph[to].add(edges.size());
            this.edges.add(residualEdge);
        }

        void Dinic() {
//            System.out.println("Dinic");
            long flow = 0;

            while (bfs()) {
                Arrays.fill(ptr, 0);
                long pushed = dfs(s, MAX_FLOW);
                while (pushed != 0) {
                    flow += pushed;
                    pushed = dfs(s, MAX_FLOW);
                }
            }

            printFlow(flow);
        }

        boolean bfs() {
//            System.out.println(" bfs");
            int qHead = 0;
            int qTail = 0;

            int[] q = new int[n + 1];
            q[qTail++] = s;

            Arrays.fill(d, -1);
            d[s] = 0;

            while (qHead < qTail && d[t] == -1) {
                int v = q[qHead++];
                for (int id : graph[v]) {
                    Edge edge = edges.get(id);
                    if (d[edge.to] == -1 && edge.flow < edge.capacity) {
                        q[qTail++] = edge.to;
                        d[edge.to] = d[v] + 1;
                    }
                }
            }
            return d[t] != -1;
        }

        long dfs(int v, long flow) {
//            System.out.println("  dfs: " + v + " - " + flow);
            if (flow == 0) {
                return 0;
            }

            if (v == t) {
                return flow;
            }

            for (; ptr[v] < graph[v].size(); ++ptr[v]) {
                Edge edge = edges.get(graph[v].get(ptr[v]));

                if (d[edge.to] != d[v] + 1) {
                    continue;
                }

                long pushed = dfs(edge.to, Math.min(flow, edge.capacity - edge.flow));
                if (pushed != 0) {
                    edge.flow += pushed;
                    edge.residual.flow -= pushed;
                    return pushed;
                }
            }

            return 0;
        }

        void printFlow(long maxFlow) {
            StringBuilder sb = new StringBuilder();
            sb.append(maxFlow).append("\n");
            for (int i = 0; i < edges.size(); i += 2) {
                sb.append(edges.get(i).flow).append("\n");
            }

            System.out.println(sb);
        }

        void print() {
            for (int i = 0; i < n; i++) {
                System.out.println(i + ": " + graph[i]);
            }
            System.out.println();
        }

//        void printAll() {
//            for (int i = 0; i < n; i++) {
//                System.out.print("flow" + i + ": ");
//                graph[i].forEach(edge -> System.out.print("{" + edge.to + ";" + edge.flow + "} "));
//                System.out.println();
//            }
//            System.out.println();
//
//            for (int i = 0; i < n; i++) {
//                System.out.print("capacity" + i + ": ");
//                graph[i].forEach(edge -> System.out.print("{" + edge.to + ";" + edge.capacity + "} "));
//                System.out.println();
//            }
//            System.out.println();
//        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");

        int n = Integer.parseInt(temp[0]);
        int m = Integer.parseInt(temp[1]);

        Graph g = new Graph(n);

        for (int i = 0; i < m; i++) {
            temp = br.readLine().split(" ");
            int v = Integer.parseInt(temp[0]) - 1;
            int u = Integer.parseInt(temp[1]) - 1;
            long weight = Long.parseLong(temp[2]);
            g.addEdge(v, u, weight);
        }

        g.Dinic();
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

//4 5
//1 2 1
//1 3 2
//3 2 1
//2 4 2
//3 4 1