package max_min_cost_flows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Task_C {

    static class Edge {
        int from;
        int to;
        int capacity;
        int flow;

        Edge residual;


        Edge(int from, int to, int capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.flow = 0;
        }

        public String toString() {
            return "{" + to + "," + flow + "," + capacity + "}";
        }
    }

    static class Graph {
        int n;
        int s;
        int t;

        final int INF;

        int[] distance;
        int[] parent;
        boolean[] visited;

        List<Edge>[] pipes;
        List<Edge> edges;

        Graph(int n) {
            this.n = n;
            this.s = 0;
            this.t = n - 1;

            this.INF = 1_000_000_000;

            this.distance = new int[n];
            this.parent = new int[n];
            this.visited = new boolean[n];

            this.edges = new ArrayList<>();

            this.pipes = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                pipes[i] = new ArrayList<>();
            }

        }

        void addEdge(int from, int to, int capacity) {
            Edge edge = new Edge(from, to, capacity);
            Edge residualEdge = new Edge(to, from, capacity);

            edge.residual = residualEdge;
            residualEdge.residual = edge;

            this.pipes[from].add(edge);
            this.pipes[to].add(residualEdge);

            this.edges.add(edge);
        }

        void getMinCut() {
            int flow = getMaxFlow();

            Arrays.fill(visited, false);

            DFS(s);

            int size = 0;
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < edges.size(); i++) {
                Edge edge = edges.get(i);
                if ((visited[edge.from] && !visited[edge.to]) || (!visited[edge.from] && visited[edge.to])) {
                    size++;
                    stringBuilder.append(i + 1).append(" ");
                }
            }

            System.out.println(size + " " + flow);
            System.out.println(stringBuilder);
        }

        int getMaxFlow() {
            int flow = 0;
            while (BFS()) {
                Arrays.fill(parent, 0);

                int pushed = DFS(s, INF);
                while (pushed != 0) {
                    flow += pushed;
                    pushed = DFS(s, INF);
                }
            }
            return flow;
        }

        boolean BFS() {
            Arrays.fill(distance, -1);

            LinkedList<Integer> queue = new LinkedList<>();
            queue.add(s);

            distance[s] = 0;
            while (!queue.isEmpty()) {
                int v = queue.pop();

                for (Edge edge : pipes[v]) {
                    if (distance[edge.to] == -1 && edge.flow < edge.capacity) {
                        queue.add(edge.to);
                        distance[edge.to] = distance[v] + 1;
                    }
                }
            }

            return distance[t] != -1;
        }

        int DFS(int v, int flow) {
            if (v == t) {
                return flow;
            }

            for (; parent[v] < pipes[v].size(); ++parent[v]) {
                Edge edge = pipes[v].get(parent[v]);
                if (distance[edge.to] == distance[v] + 1 && edge.flow < edge.capacity) {
                    int pushed = DFS(edge.to, Math.min(flow, edge.capacity - edge.flow));
                    if (pushed > 0) {
                        edge.flow += pushed;
                        edge.residual.flow -= pushed;
                        return pushed;
                    }
                }
            }

            return 0;
        }

        void DFS(int v) {
            visited[v] = true;
            for (Edge edge : pipes[v]) {
                if (!visited[edge.to] && edge.flow < edge.capacity) {
                    DFS(edge.to);
                }
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
            int v = Integer.parseInt(temp[0]) - 1;
            int u = Integer.parseInt(temp[1]) - 1;
            int capacity = Integer.parseInt(temp[2]);
            g.addEdge(v, u, capacity);
        }

        g.getMinCut();
    }
}

//3 3
//1 2 3
//1 3 5
//3 2 7

//https://algs4.cs.princeton.edu/64maxflow/GlobalMincut.java.html

//6 8
//1 2 3
//1 3 3
//2 4 2
//2 5 2
//3 4 2
//3 5 2
//5 6 3
//4 6 3