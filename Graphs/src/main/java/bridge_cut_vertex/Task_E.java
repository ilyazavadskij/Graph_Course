package bridge_cut_vertex;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Task_E {

    static class Graph {
        int n;

        int vCount;
        int hCount;

        final int R = 0;
        final int G = 1;
        final int B = 2;

        boolean[] visited;
        boolean[] visitedReversed;
        int[] colors;
        int[] components;

        ArrayList<Integer>[] edges;
        LinkedList<Integer>[] connections;
        LinkedList<Integer>[] connectionsReversed;

        LinkedList<Integer> order;
        LinkedList<Pair<Integer, Integer>> conditions;

        Graph(int n) {
            this.n = n;
            this.hCount = 3 * n;
            this.vCount = 2 * hCount;

            this.colors = new int[n];
            this.visited = new boolean[vCount];
            this.visitedReversed = new boolean[vCount];
            this.components = new int[vCount];

            this.order = new LinkedList<>();
            this.conditions  = new LinkedList<>();

            this.edges = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                edges[i] = new ArrayList<>();
            }
        }

        void addColor(String c) {
            String[] colors = c.split("");
            for (int i = 0; i < n; i++) {
                this.colors[i] = colors[i].equals("R") ? R : (colors[i].equals("G") ? G : B);
            }
        }

        void addEdge(int vertex, int neighbor) {
            this.edges[vertex].add(neighbor);
            this.edges[neighbor].add(vertex);
        }

        void makeConditions() {
            for (int v = 0; v < n; v++) {
                for (int c1 = 0; c1 < 3; c1++) {
                    for (int c2 = 0; c2 < 3; c2++) {
                        if (c1 != c2) {
                            conditions.addLast(new Pair<>(var(v, c1, false), var(v, c2, false)));
                        }
                    }
                }
            }

            for (int v = 0; v < n; v++) {
                if (colors[v] == R) {
                    conditions.addLast(new Pair<>(var(v, G, true), var(v, B, true)));
                } else if (colors[v] == G) {
                    conditions.addLast(new Pair<>(var(v, R, true), var(v, B, true)));
                } else {
                    conditions.addLast(new Pair<>(var(v, R, true), var(v, G, true)));
                }
            }

            for (int u = 0; u < n; u++) {
                for (Integer v : edges[u]) {
                    for (int c = 0; c < 3; c++) {
                        conditions.addLast(new Pair<>(var(u, c, false), var(v, c, false)));
                    }
                }
            }
        }

        void initialize() {
            connections = new LinkedList[vCount];
            connectionsReversed = new LinkedList[vCount];
            for (int i = 0; i < vCount; i++) {
                connections[i] = new LinkedList<>();
                connectionsReversed[i] = new LinkedList<>();
            }

            for (Pair<Integer, Integer> p : conditions) {
                int a = p.getKey();
                int b = p.getValue();
                int numberA = (hCount + a) % vCount;
                int numberB = (hCount + b) % vCount;

                connections[numberA].addLast(b);
                connections[numberB].addLast(a);
                connectionsReversed[a].addLast(numberB);
                connectionsReversed[b].addLast(numberA);
            }
        }

        void SAT_2() {
            makeConditions();

            initialize();

            Arrays.fill(visited, false);
            for (int i = 0; i < vCount; i++) {
                if (!visited[i]) {
                    firstDFS(i);
                }
            }

            Arrays.fill(visitedReversed, false);
            Arrays.fill(components, -1);

            int c = 0;

            for (int i = 0; i < vCount; i++) {
                int v = order.get(vCount - i - 1);
                if (!visitedReversed[v]) {
                    secondDFS(v, c);
                    c++;
                }
            }

            for (int i = 0; i < hCount; i++) {
                if (components[i] == components[hCount + i]) {
                    System.out.println("Impossible");
                    return;
                }
            }

            for (int i = 0; i < hCount; i++) {
                if (components[i] > components[hCount + i]) {
                    switch (i % 3) {
                        case 0:
                            System.out.print('R');
                            break;
                        case 1:
                            System.out.print('G');
                            break;
                        default:
                            System.out.print('B');
                            break;
                    }
                }
            }
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
            visitedReversed[v] = true;
            components[v] = c;
            for (Integer d : this.connectionsReversed[v]) {
                if (!visitedReversed[d]) {
                    secondDFS(d, c);
                }
            }
        }

        int var(int v, int color, boolean isIn) {
            if (isIn) {
                return v * 3 + color;
            } else {
                return (n + v) * 3 + color;
            }
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");
        int n = Integer.parseInt(temp[0]);
        int m = Integer.parseInt(temp[1]);

        Graph g = new Graph(n);

        g.addColor(br.readLine());

        for (int i = 0; i < m; i++) {
            temp = br.readLine().split(" ");
            int u = Integer.parseInt(temp[0]) - 1;
            int v = Integer.parseInt(temp[1]) - 1;
            g.addEdge(u, v);
        }

        g.SAT_2();
    }

}

//4 5
//RRRG
//1 3
//1 4
//3 4
//2 4
//2 3

//4 5
//RGRR
//1 3
//1 4
//3 4
//2 4
//2 3