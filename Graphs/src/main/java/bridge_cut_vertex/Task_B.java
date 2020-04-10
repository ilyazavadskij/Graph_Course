package bridge_cut_vertex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Task_B {

    static class Graph {
        int n;
        int timer;

        boolean[] visited;
        int[] inTimer;
        int[] outTimer;

        ArrayList<Integer>[] connections;
        Set<Integer> cutVertices;

        Graph(int n) {
            this.n = n;
            this.visited = new boolean[n];
            this.inTimer = new int[n];
            this.outTimer = new int[n];

            this.cutVertices = new HashSet<>();
            this.connections = new ArrayList[n];
            for (int i = 0; i < n; i++)
                connections[i] = new ArrayList<>();
        }

        void addEdge(int vertex, int neighbor) {
            this.connections[vertex].add(neighbor);
        }

        void findCutVertices() {
            timer = 0;

            for (int i = 0; i < n; i++) {
                visited[i] = false;
            }

            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    DFS(i, -1);
                }
            }

            printCutVertices();
        }

        void DFS(int v, int p) {
            visited[v] = true;
            inTimer[v] = outTimer[v] = timer++;

            int children = 0;

            for (Integer to : this.connections[v]) {
                if (to == p) {
                    continue;
                }

                if (visited[to]) {
                    outTimer[v] = Math.min(outTimer[v], inTimer[to]);
                } else {
                    DFS(to, v);
                    outTimer[v] = Math.min(outTimer[v], outTimer[to]);
                    if (outTimer[to] >= inTimer[v] && p != -1) {
                        cutVertices.add(v);
                    }
                    children++;
                }
            }
            if (p == -1 && children > 1) {
                cutVertices.add(v);
            }
        }

        void printCutVertices() {
            System.out.println(cutVertices.size());
            cutVertices.stream().sorted().forEach(v -> System.out.println(v + 1));
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
            g.addEdge(second, first);
        }

        g.findCutVertices();
    }

}

//9 12
//1 2
//2 3
//4 5
//2 6
//2 7
//8 9
//1 3
//1 4
//1 5
//6 7
//3 8
//3 9