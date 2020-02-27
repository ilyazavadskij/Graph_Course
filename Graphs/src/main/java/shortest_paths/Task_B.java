package shortest_paths;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Task_B {

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
        LinkedList<Edge>[] adjacencylist;
        int[] distance;

        final int MAX_WEIGHT = 100;

        Graph(int n) {
            this.n = n;
            this.distance = new int[n];
            adjacencylist = new LinkedList[n];
            for (int i = 0; i < n; i++) {
                adjacencylist[i] = new LinkedList<>();
            }
        }

        void addEdge(int source, int destination, int weight) {
            Edge edge = new Edge(source, destination, weight);
            adjacencylist[source].addFirst(edge);

            edge = new Edge(destination, source, weight);
            adjacencylist[destination].addFirst(edge);
        }

        void findMinDistance(int s, int t) {
            boolean[] SPT = new boolean[n];

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

            distance[0] = 0;
            Pair<Integer, Integer> p0 = new Pair<>(distance[0], 0);
            pq.offer(p0);
            while (!pq.isEmpty()) {
                System.out.println(pq);
                Pair<Integer, Integer> extractedPair = pq.poll();
                System.out.println("  " + extractedPair);

                int extractedVertex = extractedPair.getValue();
                if (!SPT[extractedVertex]) {
                    SPT[extractedVertex] = true;

                    LinkedList<Edge> list = adjacencylist[extractedVertex];
                    for (Edge edge : list) {
                        int destination = edge.destination;

                        if (!SPT[destination]) {
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

            System.out.println(distance[t]);
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
