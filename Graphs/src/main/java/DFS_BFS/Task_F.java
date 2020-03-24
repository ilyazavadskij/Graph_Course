package DFS_BFS;

import java.io.*;
import java.util.*;

//Задача F. Сумма расстояний [1 sec, 256 mb]

public class Task_F {

    static class Graph {
        int n;
        int[][] distance;

        LinkedList<Integer>[] connections;

        Graph(int n) {
            this.n = n;
            this.connections = new LinkedList[n];
            for (int i = 0; i < n; ++i)
                connections[i] = new LinkedList();
            this.distance = new int[n][n];
        }

        void addEdge(int vertex, int neighbor) {
            this.connections[vertex].add(neighbor);
        }

        void distanceSum() {
            for (int i = 0; i < n; i++) {
                BFS(i);
            }

            int total = 0;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    total += distance[i][j];
                }
            }
            System.out.println(total);
        }

        void BFS(int v) {
            LinkedList<Integer> queue = new LinkedList<>();
            boolean[] visited = new boolean[n];

            visited[v] = true;
            queue.add(v);

            while (queue.size() != 0) {
                int u = queue.poll();

                for (int c : connections[u]) {
                    if (!visited[c]) {
                        distance[v][c] = distance[v][u] + 1;
                        visited[c] = true;
                        queue.add(c);
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String temp[] = br.readLine().split(" ");
        int n = Integer.parseInt(temp[0]);
        int m = Integer.parseInt(temp[1]);

        Graph g = new Graph(n);

        for (int i = 0; i < m; i++) {
            temp = br.readLine().split(" ");
            int first = Integer.parseInt(temp[0]) - 1;
            int second = Integer.parseInt(temp[1]) - 1;
            g.addEdge(first, second);
            g.addEdge(second, first);
        }

//        for (int v = 0; v < n; v++) {
//            System.out.println(v + ": " + g.connections[v]);
//        }

        g.distanceSum();
    }

}

//5 5
//1 2
//2 3
//3 4
//5 3
//1 5