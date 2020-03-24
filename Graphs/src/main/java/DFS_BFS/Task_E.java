package DFS_BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

//Задача E. Поиск цикла [1 sec, 256 mb]

public class Task_E {

    static class Graph {
        int n;
        int[] parent;
        int circleStart;

        LinkedList<Integer> path;
        ArrayList<Integer>[] connections;

        Graph(int n) {
            this.n = n;
            this.connections = new ArrayList[n];
            for (int i = 0; i < n; i++)
                connections[i] = new ArrayList<>();

            this.parent = new int[n];
            Arrays.fill(this.parent, -1);

            this.path = new LinkedList<>();
        }

        void addEdge(int vertex, int neighbor) {
            if (this.connections[vertex] == null) {
                this.connections[vertex] = new ArrayList<>();
            }
            this.connections[vertex].add(neighbor);
        }


        void findCircle() {
            int[] color = new int[this.n];
            circleStart = -1;
            boolean result = false;
            for (int v = 0; v < this.n; v++) {
                if (!result) {
                    if (color[v] == 0) {
                        result = DFS(v, color);
                        if (result) {
                            break;
                        }
                    }
                }
            }

            if (!result) {
                System.out.println("NO");
            } else {
                System.out.println("YES");
//                System.out.println(" " + circleStart);
                int current = this.parent[this.circleStart];
                this.path.addFirst(current);
                while (current != this.circleStart) {
                    current = this.parent[current];
                    this.path.addFirst(current);
                }
                path.forEach(v -> System.out.print(((v + 1) + " ")));
            }
        }

        boolean DFS(int v, int[] color) {
            color[v] = 1;

            if (this.connections[v] != null) {
                for (int u : this.connections[v]) {
                    this.parent[u] = v;
                    if (color[u] == 0) {
                        if (DFS(u, color)) {
                            return true;
                        }
                    } else if (color[u] == 1) {
                        this.circleStart = u;
                        return true;
                    }
                }
            }

            color[v] = 2;
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String temp[] = br.readLine().split(" ");
        int n = Integer.parseInt(temp[0]);
        int m = Integer.parseInt(temp[1]);

//        System.out.println(n + " " + m);

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

        g.findCircle();
    }

}