package DFS_BFS;

import java.util.*;

//Задача B. Связность [1 sec, 256 mb]
//        В этой задаче требуется проверить, что граф является связным, то есть что из любой
//        вершины можно по рёбрам этого графа попасть в любую другую.
//
//      Формат входных данных
//        В первой строке входного файла заданы числа N и M через пробел — количество вершин
//        и рёбер в графе, соответственно (1 <= N <= 100, 0 <= M <= 10 000). Следующие �строк
//        содержат по два числа u и v через пробел (1 <= u,v <= N); каждая такая строка означает,
//        что в графе существует ребро между вершинами u и v.
//
//      Формат выходных данных
//        Выведите “YES”, если граф является связным, и “NO” в противном случае.

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
