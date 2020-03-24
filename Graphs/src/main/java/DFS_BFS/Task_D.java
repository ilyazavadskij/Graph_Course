package DFS_BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

//Задача D. TopSort. Топологическая сортировка [1 sec, 256 mb]

public class Task_D {

    static class Graph {
        int n;
        ArrayList<Integer>[] connections;
        LinkedList<Integer> sortQueue;

        Graph(int n) {
            this.n = n;
            this.connections = new ArrayList[n];
            for (int i = 0; i < n; i++)
                connections[i] = new ArrayList<>();
            this.sortQueue = new LinkedList<>();
        }

        void addEdge(int vertex, int neighbor) {
            this.connections[vertex].add(neighbor);
        }

        boolean findCircle() {
            int[] color = new int[this.n];
            boolean result = false;
            for (int v = 0; v < this.n; v++) {
                if (color[v] == 0) {
                    result = findCircleDFS(v, color);
                    if (result) {
                        return result;
                    }
                }
            }
            return result;
        }

        boolean findCircleDFS(int v, int[] color) {
            color[v] = 1;

            if (this.connections[v] != null) {
                for (int u : this.connections[v]) {
                    if (color[u] == 0) {
                        if (findCircleDFS(u, color)) {
                            return true;
                        }
                    } else if (color[u] == 1) {
                        return true;
                    }
                }
            }

            color[v] = 2;
            return false;
        }

        void topologicalSort() {
            if (findCircle()) {
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

            for (int u : this.connections[v]) {
                if (!visited[u]) {
                    DFS(u, visited);
                }
            }
            this.sortQueue.addFirst(v + 1);
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