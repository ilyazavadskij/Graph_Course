package shortest_paths;

import java.util.*;
import java.lang.*;
import java.io.*;

//Задача C. Path. Кратчайший путь [2 sec, 64 mb]

public class Task_C {

    static class Edge {
        int d;
        long w;

        Edge(int d, long w) {
            this.d = d;
            this.w = w;
        }

        public String toString() {
            return d + "=" + w;
        }
    }

    static class Graph {
        final long MAX_VALUE;

        int n;
        long[] distance;
        boolean[] negativeCircle;
        List<Edge>[] edges;

        Graph(int n) {
            this.n = n;
            this.MAX_VALUE = n * (long) Math.pow(10, 15);

            this.edges = new ArrayList[n];
            this.distance = new long[n];
            this.negativeCircle = new boolean[n];
            for (int node = 0; node < n; node++) {
                edges[node] = new ArrayList<>();
                distance[node] = MAX_VALUE;
                negativeCircle[node] = false;
            }
        }

        void addEdge(int s, int d, long w) {
            edges[s].add(new Edge(d, w));
        }

        void findShortestPath(int source) {
            if (!BellmanFord(source)) {
                for (int sourceNode = 0; sourceNode < n; sourceNode++) {
                    if (distance[sourceNode] != MAX_VALUE) {
                        for (Edge edge : edges[sourceNode]) {
                            int destination = edge.d;
                            long weight = edge.w;
                            if (distance[destination] > distance[sourceNode] + weight) {
                                BFS(destination);
                            }
                        }
                    }
                }
            }
            printSourceDistance();
        }

        boolean BellmanFord(int s) {
            int[] count = new int[n];
            boolean[] in = new boolean[n];

            for (int i = 0; i < n; ++i) {
                distance[i] = MAX_VALUE;
                in[i] = false;
                count[i] = 0;
            }

            distance[s] = 0;
            LinkedList<Integer> queue = new LinkedList<>();
            queue.add(s);
            in[s] = true;

            while (!queue.isEmpty()) {
                int v = queue.pollFirst();
                in[v] = false;

                for (Edge edge : edges[v]) {
                    int destination = edge.d;
                    long weight = edge.w;
                    if (distance[v] + weight < distance[destination]) {
                        distance[destination] = distance[v] + weight;

                        if (!in[destination]) {
                            queue.addLast(destination);
                            in[destination] = true;
                            count[destination]++;
                            if (count[destination] > n) {
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        }

        void BFS(int s) {
            LinkedList<Integer> queue = new LinkedList<>();
            queue.add(s);

            while (!queue.isEmpty()) {
                int next = queue.poll();

                for (Edge edge : edges[next]) {
                    int destination = edge.d;
                    if (!negativeCircle[destination]) {
                        queue.addLast(destination);
                        negativeCircle[destination] = true;
                    }
                }
            }
        }

        private void printSourceDistance() {
            for (int vertex = 0; vertex < n; vertex++) {
                if (negativeCircle[vertex]) {
                    System.out.println("-");
                    continue;
                }
                if (distance[vertex] == MAX_VALUE) {
                    System.out.println("*");
                    continue;
                }
                System.out.println(distance[vertex]);
            }
        }
    }


    public static void main(String[] arg) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");

        int n = Integer.parseInt(temp[0]);
        int m = Integer.parseInt(temp[1]);
        int s = Integer.parseInt(temp[2]) - 1;

        Graph g = new Graph(n);

        for (int i = 0; i < m; i++) {
            temp = br.readLine().split(" ");
            int b = Integer.parseInt(temp[0]) - 1;
            int c = Integer.parseInt(temp[1]) - 1;
            long w = Long.parseLong(temp[2]);
            g.addEdge(b, c, w);
        }

        g.findShortestPath(s);
    }

}

//6 7 1
//1 2 10
//2 3 5
//1 3 100
//3 5 7
//5 4 10
//4 3 -18
//6 1 -1

//5 8 1
//1 2 -1
//1 3 4
//2 3 3
//2 4 2
//2 5 2
//4 3 5
//4 2 1
//5 4 -3

//4 5 1
//1 2 1000000000000000
//2 3 -2
//3 2 1
//2 4 1000000000000000
//1 4 -1000000000000000

//6 8 1
//1 2 1000000000000000
//2 3 -2
//3 2 1
//2 4 1000000000000000
//1 4 -1000000000000000
//1 5 1000000000000000
//5 6 1
//6 5 -2