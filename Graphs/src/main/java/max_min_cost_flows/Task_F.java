package max_min_cost_flows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Task_F {

    static class Edge {
        int id;

        int from;
        int to;

        int flow;
        int capacity;
        int cost;

        Edge residual;

        Edge(int id, int from, int to, int capacity, int cost) {
            this.id = id;

            this.from = from;
            this.to = to;
            this.flow = 0;
            this.capacity = capacity;
            this.cost = cost;
        }

        public String toString() {
            return "{" + from + "->" + to + " = " + flow + "/" + capacity + "}";
        }
    }

    static class Graph {
        int n;
        int k;
        int s;
        int t;

        final int INF;

        final int WHITE = 0;
        final int GREY = 1;
        final int BLACK = 2;

        double minCost;

        int[] distance;
        Edge[] parent;

        Set<Edge> visited;
        List<Edge>[] graph;
        LinkedList<Integer>[] groomPaths;

        Graph(int n) {
            this.n = n;

            this.s = 0;
            this.t = n - 1;

            this.INF = Integer.MAX_VALUE;
            this.minCost = 0;

            this.distance = new int[n];
            this.parent = new Edge[n];

            this.visited = new HashSet<>();

            this.graph = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                graph[i] = new ArrayList<>();
            }
        }

        void addEdge(int i, int from, int to, int capacity, int cost) {
            Edge edge = new Edge(i, from, to, capacity, cost);
            Edge residualEdge = new Edge(-1, to, from, 0, -cost);

            edge.residual = residualEdge;
            residualEdge.residual = edge;

            this.graph[from].add(edge);
            this.graph[to].add(residualEdge);
        }

        void findBrides(int k) {
            this.k = k;

            this.groomPaths = new LinkedList[k];
            for (int i = 0; i < k; i++) {
                groomPaths[i] = new LinkedList<>();
            }

            if (EdmondsKarp() == k) {
                for (int i = 0; i < k; i++) {
                    int current = s;
                    while (current != t) {
                        for (Edge edge : graph[current]) {
                            if (edge.flow > 0 && !visited.contains(edge)) {
                                minCost += (edge.flow * edge.cost);

                                visited.add(edge);
                                current = edge.to;
                                groomPaths[i].add(edge.id + 1);
                                break;
                            }
                        }
                    }
                }
                printPath();
            } else {
                System.out.println("-1");
            }
        }

        long EdmondsKarp() {
            int flow = 0;

            while (k > flow) {
                Arrays.fill(distance, -1);
                distance[s] = 0;

                int[] color = new int[n];
                int[] q = new int[n];

                int qHead = 0;
                int qTail = 0;

                q[qTail++] = s;

                while (qHead != qTail) {
                    int v = q[qHead++];
                    color[v] = BLACK;

                    if (qHead == n) {
                        qHead = 0;
                    }

                    for (Edge edge : graph[v]) {
                        if (edge.flow < edge.capacity) {
                            if (distance[edge.to] == -1 || distance[edge.from] + edge.cost < distance[edge.to]) {
                                distance[edge.to] = distance[edge.from] + edge.cost;

                                if (color[edge.to] == WHITE) {
                                    q[qTail++] = edge.to;

                                    if (qTail == n) {
                                        qTail = 0;
                                    }
                                } else if (color[edge.to] == BLACK) {
                                    qHead--;
                                    if (qHead == -1) {
                                        qHead = n - 1;
                                    }
                                    q[qHead] = edge.to;
                                }

                                color[edge.to] = GREY;
                                parent[edge.to] = edge;
                            }
                        }
                    }
                }

                if (distance[t] == -1) {
                    break;
                }

                int f = INF;
                int current = t;
                while (current != s) {
                    f = Math.min(f, parent[current].capacity - parent[current].flow);
                    current = parent[current].from;
                }

                current = t;
                while (current != s) {
                    parent[current].flow += f;
                    parent[current].residual.flow -= f;

                    current = parent[current].from;
                }

                flow += f;
            }

            return flow;
        }

        void printPath() {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(minCost / k).append("\n");
            for (int i = 0; i < k; i++) {
                stringBuilder.append(groomPaths[i].size()).append(" ");
                groomPaths[i].forEach(v -> stringBuilder.append(v).append(" "));
                stringBuilder.append("\n");
            }

            System.out.println(stringBuilder);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");

        int n = Integer.parseInt(temp[0]);
        int m = Integer.parseInt(temp[1]);
        int k = Integer.parseInt(temp[2]);

        Graph g = new Graph(n);

        for (int i = 0; i < m; i++) {
            temp = br.readLine().split(" ");
            int v = Integer.parseInt(temp[0]) - 1;
            int u = Integer.parseInt(temp[1]) - 1;
            int capacity = 1;
            int cost = Integer.parseInt(temp[2]);
            g.addEdge(i, v, u, capacity, cost);
            g.addEdge(i, u, v, capacity, cost);
        }

        g.findBrides(k);
    }
}

//5 8 2
//1 2 1
//1 3 1
//1 4 3
//2 5 5
//2 3 1
//3 5 1
//3 4 1
//5 4 1
