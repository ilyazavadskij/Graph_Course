package DFS_BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Task_D {

    static class Graph {

        int n;
        List<Set<Integer>> connections;
        LinkedList<Integer> sortQueue;

        LinkedList<Integer> path;
        boolean isCircled;

        Graph(int n) {
            this.n = n;
            this.connections = new ArrayList();
            for (int i = 0; i < n; i++)
                connections.add(new HashSet<>());
            this.path = new LinkedList<>();
            this.sortQueue = new LinkedList<>();
            this.isCircled = false;
        }

        void addEdge(int vertex, int neighbor) {
            this.connections.get(vertex).add(neighbor);
        }

        void findCircle() {
            int[] color = new int[this.n];

            for (int v = 0; v < this.n; v++) {
                if (!this.isCircled) {
                    if (color[v] == 0) {
                        findCircleDFS(v, color);
                    }
                }
            }
        }

        void findCircleDFS(int v, int[] color) {
            if (this.isCircled) {
                return;
            }

            path.add(v);
            color[v] = 1;

            if (this.connections.get(v) != null) {
                for (int u : this.connections.get(v)) {
                    if (color[u] == 0) {
                        findCircleDFS(u, color);
                    }
                    if (color[u] == 1) {
                        this.isCircled = true;
                        return;
                    }
                }
            }

            path.removeLast();
            color[v] = 2;
        }

        void topologicalSort() {
            findCircle();

            if (this.isCircled) {
                System.out.println("-1");
                return;
            }

            boolean[] visited = new boolean[this.n];

            for (int v = 0; v < this.n; v++) {
                if (!visited[v]) {
                    DFS(v, visited);
                }
            }

            sortQueue.forEach(v -> System.out.print(v + " "));
        }

        void DFS(int v, boolean[] visited) {
            visited[v] = true;

            for (int u : this.connections.get(v)) {
                if (!visited[u]) {
                    DFS(u, visited);
                }
            }
            this.sortQueue.addFirst(v + 1);
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
        }

//        for (int v = 0; v < g.connections.size(); v++) {
//            System.out.println(v + ": " + g.connections.get(v));
//        }

        g.topologicalSort();
    }
}

//6 6
//1 2
//3 2
//4 2
//2 5
//6 5
//4 6