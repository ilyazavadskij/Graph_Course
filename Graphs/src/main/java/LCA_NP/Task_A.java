package LCA_NP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

//Задача A. 2-раскраска [1 sec, 256 mb]

public class Task_A {

    static class Edge {
        int d;

        Edge(int d) {
            this.d = d;
        }
    }

    static class Graph {
        int n;

        final int WHITE = 1;
        final int BLACK = 2;

        boolean[] visited;
        int[] color;

        ArrayList<Edge>[] connections;

        Graph(int n) {
            this.n = n;
            this.visited = new boolean[n];
            this.color = new int[n];

            this.connections = new ArrayList[n];
            for (int i = 0; i < n; i++)
                connections[i] = new ArrayList<>();
        }

        void addEdge(int vertex, int neighbor) {
            this.connections[vertex].add(new Edge(neighbor));
            this.connections[neighbor].add(new Edge(vertex));
        }

        void paintTwo() {
            for (int i = 0; i < n; i++) {
                visited[i] = false;
                color[i] = -1;
            }

            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    if (!DFS(i, WHITE)) {
                        System.out.println("NO");
                        return;
                    }
                }
            }

            System.out.println("YES");
            for (int i = 0; i < n; i++) {
                System.out.print(color[i] + " ");
            }
        }

        boolean DFS(int v, int c) {
            visited[v] = true;
            color[v] = c;

            for (Edge edge : this.connections[v]) {
                int d = edge.d;

                if (!visited[d]) {
                    if (!DFS(d, c == WHITE ? BLACK : WHITE)) {
                        return false;
                    }
                } else {
                    if (color[v] == color[d]) {
                        return false;
                    }
                }
            }
            return true;
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

        g.paintTwo();
    }

}

//6 7
//1 2
//2 3
//3 4
//1 3
//4 5
//4 6
//5 6

//5 5
//1 2
//2 3
//3 1
//1 4
//4 5

//4 3
//1 2
//2 3
//3 4

//7 7
//1 2
//2 3
//2 7
//2 4
//4 6
//5 6
//2 5