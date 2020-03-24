package DFS_BFS;

import java.util.*;

//Задача B. Связность [1 sec, 256 mb]

public class Task_B {

    static class Graph {
        int n;
        List<LinkedList<Integer>> connections;

        Graph(int n) {
            this.n = n;
            this.connections = new ArrayList();
            for (int i = 0; i < n; i++)
                connections.add(new LinkedList<>());
        }

        void addEdge(int vertex, int neighbor) {
            this.connections.get(vertex).add(neighbor);
        }


        void DFS() {
            boolean[] visited = new boolean[this.n];
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
            visited[v] = true;

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
            g.addEdge(second, first);
        }

        g.DFS();
    }

}
