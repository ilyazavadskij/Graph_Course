package shortest_paths;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Task_D {

    static class Edge {
        int source;
        int destination;
        int weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    static class Graph {
        int n;
        LinkedList<Edge>[] adjacencyList;
        int[] distance;

        final int MAX_WEIGHT;

        Graph(int n) {
            this.n = n;
            MAX_WEIGHT = n * 100;
            this.distance = new int[n];
            adjacencyList = new LinkedList[n];
            for (int i = 0; i < n; i++) {
                adjacencyList[i] = new LinkedList<>();
            }
        }

        void addEdge(int source, int destination, int weight) {
            Edge edge = new Edge(source, destination, weight);
            adjacencyList[source].addFirst(edge);

            edge = new Edge(destination, source, weight);
            adjacencyList[destination].addFirst(edge);
        }

        void findMinDistance(int s, int t) {
            boolean[] visited = new boolean[n];

            for (int i = 0; i < n; i++) {
                distance[i] = MAX_WEIGHT;
            }

            PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(n, new Comparator<Pair<Integer, Integer>>() {
                @Override
                public int compare(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
                    int key1 = p1.getKey();
                    int key2 = p2.getKey();
                    return key1 - key2;
                }
            });

            distance[s] = 0;
            Pair<Integer, Integer> p0 = new Pair<>(distance[s], s);
            pq.offer(p0);
            while (!pq.isEmpty() && !visited[t]) {
                Pair<Integer, Integer> extractedPair = pq.poll();

                int extractedVertex = extractedPair.getValue();

                if (!visited[extractedVertex]) {
                    visited[extractedVertex] = true;

                    LinkedList<Edge> list = adjacencyList[extractedVertex];
                    for (Edge edge : list) {
                        int destination = edge.destination;

                        if (!visited[destination]) {
                            int newKey = distance[extractedVertex] + edge.weight;
                            int currentKey = distance[destination];
                            if (currentKey > newKey) {
                                Pair<Integer, Integer> p = new Pair<>(newKey, destination);
                                pq.offer(p);
                                distance[destination] = newKey;
                            }
                        }
                    }
                }
            }

            if (distance[t] != MAX_WEIGHT) {
                System.out.println(distance[t]);
            } else {
                System.out.println(-1);
            }
        }
    }


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] temp = br.readLine().split(" ");
        int n = Integer.parseInt(temp[0]);
        int m = Integer.parseInt(temp[1]);

        Graph g = new Graph(n);

        temp = br.readLine().split(" ");
        int s = Integer.parseInt(temp[0]) - 1;
        int t = Integer.parseInt(temp[1]) - 1;

        for (int i = 0; i < m; i++) {
            temp = br.readLine().split(" ");
            int b = Integer.parseInt(temp[0]) - 1;
            int c = Integer.parseInt(temp[1]) - 1;
            int w = Integer.parseInt(temp[2]);
            g.addEdge(b, c, w);
        }

        g.findMinDistance(s, t);
    }
}