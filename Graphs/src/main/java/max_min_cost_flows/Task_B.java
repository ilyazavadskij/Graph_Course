package max_min_cost_flows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task_B {

    static class Edge {
        int from;
        int to;
        int capacity;
        int cost;

        int flow;

        Edge residual;


        Edge(int from, int to, int capacity, int cost) {
            this.from = from;
            this.to = to;
            this.flow = 0;
            this.capacity = capacity;
            this.cost = cost;
        }

        public String toString() {
            return "{" + to + "," + flow + "," + capacity + "," + cost + "}";
        }
    }

    static class Graph {
        int n;
        int s;
        int t;

        final int INF;

        int[] distance;
        int[] parent;

        List<Edge> edges;

        Graph(int n) {
            this.n = n;
            this.s = 0;
            this.t = n - 1;

            this.INF = 1_000_000_000;

            this.distance = new int[n];
            this.parent = new int[n];

            this.edges = new ArrayList<>();
        }

        void addEdge(int from, int to, int capacity, int cost) {
            Edge edge = new Edge(from, to, capacity, cost);
            Edge residualEdge = new Edge(to, from, 0, -cost);

            edge.residual = residualEdge;
            residualEdge.residual = edge;

            this.edges.add(edge);
            this.edges.add(residualEdge);
        }

        boolean BellmanFord() {
            distance[s] = 0;
            boolean improved = true;
            while(improved) {
                improved = false;
                for (int i = 0; i < edges.size(); i++) {
                    Edge edge = edges.get(i);
                    if (distance[edge.from] != INF && edge.flow < edge.capacity) {
                        if (distance[edge.to] > distance[edge.from] + edge.cost) {
                            distance[edge.to] = distance[edge.from] + edge.cost;
                            parent[edge.to] = i;
                            improved = true;
                        }
                    }
                }
            }
            return distance[t] != INF;
        }

        void getMinCost() {
            Arrays.fill(distance, INF);

            long cost = 0;
            while(BellmanFord()) {
                long f = INF;
                int i = t;

                while (i != s) {
                    f = Math.min(f, edges.get(parent[i]).capacity - edges.get(parent[i]).flow);
                    i = edges.get(parent[i]).from;
                }

                i = t;
                while (i != s) {
                    edges.get(parent[i]).flow += f;
                    edges.get(parent[i]).residual.flow -= f;

                    cost += (long) edges.get(parent[i]).cost * f;
                    i = edges.get(parent[i]).from;
                }

                Arrays.fill(distance, INF);
            }
            System.out.println(cost);
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
            int cost = Integer.parseInt(temp[3]);
            g.addEdge(v, u, capacity, cost);
        }

        g.getMinCost();
    }
}

//4 5
//1 2 1 2
//1 3 2 2
//3 2 1 1
//2 4 2 1
//3 4 2 3