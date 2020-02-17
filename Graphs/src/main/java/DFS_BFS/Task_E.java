package DFS_BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

public class Task_E {

    static class Graph {

        int n;
        ArrayList<Integer>[] connections;

        LinkedList<Integer> path;
        boolean isCircled;
        int circleStart;

        Graph(int n) {
            this.n = n;
            this.connections = new ArrayList[n];
            this.path = new LinkedList<>();
            this.isCircled = false;
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

            for (int v = 0; v < this.n; v++) {
                if (!this.isCircled) {
                    if (color[v] == 0) {
                        DFS(v, color);
                    }
                }
            }

            if (!this.isCircled) {
                System.out.println("NO");
            } else {
                System.out.println("YES");
//                System.out.println(" " + circleStart);
                boolean start = false;
                for (int v : path) {
                    if (v == circleStart) {
                        start = true;
                    }
//                    System.out.println(v + " " + start);
                    if (start) {
                        System.out.printf("%d ", v + 1);
                    }
                }
            }
        }

        void DFS(int v, int[] color) {
            if (this.isCircled) {
                return;
            }

            path.add(v);
            color[v] = 1;

            if (this.connections[v] != null) {
                for (int u : this.connections[v]) {
                    if (color[u] == 0) {
                        DFS(u, color);
                    }

                    if (color[u] == 1) {
                        if (this.circleStart == -1) {
                            this.circleStart = u;
                        }
                        this.isCircled = true;
                        return;
                    }
                }
            }

            path.removeLast();
            color[v] = 2;
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