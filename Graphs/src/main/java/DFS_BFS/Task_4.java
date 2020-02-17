package DFS_BFS;

import java.util.*;

public class Task_4 {

    static class Graph {

        int n;
        List<Set<Integer>> connections;
        LinkedList<Integer> sortQueue;

        Graph(int n) {
            this.n = n;
            this.connections = new ArrayList();
            for (int i = 0; i < n; i++)
                connections.add(new HashSet<>());
            this.sortQueue = new LinkedList<>();
        }

        void addEdge(int vertex, int neighbor) {
            this.connections.get(vertex).add(neighbor);
        }


        void topologicalSort() {
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

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int m = input.nextInt();

        Graph g = new Graph(n);

        for (int i = 0; i < m; i++) {
            int first = input.nextInt() - 1;
            int second = input.nextInt() - 1;
            g.addEdge(first, second);
        }

        for (int v = 0; v < g.connections.size(); v++) {
            System.out.println(v + ": " + g.connections.get(v));
        }

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