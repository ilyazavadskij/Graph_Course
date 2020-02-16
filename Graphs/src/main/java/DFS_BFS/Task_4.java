package DFS_BFS;

import java.util.*;

public class Task_4 {

    static class Graph {

        int n;
        List<Set<Integer>> connections;

        Graph(int n) {
            this.n = n;
            this.connections = new ArrayList();
            for (int i = 0; i < n; i++)
                connections.add(new HashSet<>());
        }

        void addEdge(int vertex, int neighbor) {
            this.connections.get(vertex).add(neighbor);
        }


        void DFS() {
            boolean[] visited = new boolean[this.n];
            List<Integer> path = new ArrayList<>();

            DFSUtil(0, visited);
            for (boolean v : visited) {
                if (!v) {
                    System.out.println("NO");
                    return;
                }
            }
            System.out.println("YES");
        }

        void DFSUtil(int v, boolean[] visited) {
            if (visited[v]) {
                return;
            }

            this.connections.get(v).stream()
                    .filter(vertex -> !visited[vertex])
                    .forEach(vertex -> DFSUtil(vertex, visited));
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

//        g.DFS();
    }
}
