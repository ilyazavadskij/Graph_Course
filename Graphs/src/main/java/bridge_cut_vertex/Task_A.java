package bridge_cut_vertex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

//Задача A. Bridges. Мосты [2 sec, 256 mb]

public class Task_A {

    static class Edge {
        int d;
        int number;

        Edge(int d, int number) {
            this.d = d;
            this.number = number;
        }
    }

    static class Graph {
        int n;
        int timer;

        boolean[] visited;
        int[] inTimer;
        int[] outTimer;

        ArrayList<Edge>[] connections;
        ArrayList<Integer> bridges;

        Graph(int n) {
            this.n = n;
            this.visited = new boolean[n];
            this.inTimer = new int[n];
            this.outTimer = new int[n];

            this.bridges = new ArrayList<>();
            this.connections = new ArrayList[n];
            for (int i = 0; i < n; i++)
                connections[i] = new ArrayList<>();
        }

        void addEdge(int vertex, int neighbor, int i) {
            this.connections[vertex].add(new Edge(neighbor, i));
            this.connections[neighbor].add(new Edge(vertex, i));
        }

        void findBridges() {
            timer = 0;

            for (int i = 0; i < n; i++) {
                visited[i] = false;
            }

            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    DFS(i, -1);
                }
            }

            printBridges();
        }

        void DFS(int v, int p) {
            timer++;
            visited[v] = true;
            inTimer[v] = timer;
            outTimer[v] = timer;

            for (Edge edge : this.connections[v]) {
                int d = edge.d;
                if (d == p) {
                    continue;
                }
                if (visited[d]) {
                    outTimer[v] = Math.min(outTimer[v], inTimer[d]);
                } else {
                    DFS(d, v);
                    outTimer[v] = Math.min(outTimer[v], outTimer[d]);
                    if (outTimer[d] > inTimer[v]) {
                        bridges.add(edge.number);
                    }
                }
            }
        }

        void printBridges() {
            System.out.println(bridges.size());
            bridges.stream().sorted().forEach(System.out::println);
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
            g.addEdge(first, second, i + 1);
        }

        g.findBridges();
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

//7 8
//1 2
//2 3
//3 1
//2 7
//2 4
//4 6
//5 6
//2 5