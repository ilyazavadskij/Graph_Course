package bridge_cut_vertex;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Task_C {

    static class Graph {
        int n;

        boolean[] visited;
        int[] color;

        ArrayList<Integer>[] connections;
        ArrayList<Integer>[] connectionsReversed;

        LinkedList<Integer> order;
        Set<Pair<Integer,Integer>> s;

        Graph(int n) {
            this.n = n;
            this.visited = new boolean[n];
            this.color = new int[n];

            this.order = new LinkedList<>();
            this.s = new HashSet<>();

            this.connections = new ArrayList[n];
            this.connectionsReversed = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                connections[i] = new ArrayList<>();
                connectionsReversed[i] = new ArrayList<>();
            }
        }

        void addEdge(int vertex, int neighbor) {
            this.connections[vertex].add(neighbor);
            this.connectionsReversed[neighbor].add(vertex);
        }

        void condensation() {
            for (int i = 0; i < n; i++) {
                visited[i] = false;
            }

            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    firstDFS(i);
                }
            }

            for (int i = 0; i < n; i++) {
                color[i] = -1;
            }
            int c = 0;
            for (int i = 0; i < n; i++) {
                int v = order.get(n - i - 1);
                if (color[v] == -1) {
                    secondDFS(v, c++);
//                    System.out.println(v + ": " + Arrays.toString(color));
                }
            }

            for (int from =0; from<n; from++) {
                for (Integer to : connections[from]) {
                    if (color[from] != color[to]) {
                        s.add(new Pair<>(from, to));
                    }
                }
            }
//            System.out.println(s);
            System.out.println(s.size());
        }

        void firstDFS(int v) {
            visited[v] = true;
            for (Integer d : this.connections[v]) {
                if (!visited[d]) {
                    firstDFS(d);
                }
            }
            order.addLast(v);
        }

        void secondDFS(int v, int c) {
            color[v] = c;
            for (Integer d : this.connectionsReversed[v]) {
                if (color[d] == -1) {
                    secondDFS(d, c);
                }
            }
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

        g.condensation();
    }

}

//4 4
//2 1
//3 2
//2 3
//4 3