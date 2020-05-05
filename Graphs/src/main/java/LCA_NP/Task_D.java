package LCA_NP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

//Задача D. Хроматическое число [1 sec, 256 mb]

public class Task_D {
    static class Graph {
        int n;

        int ALL;

        boolean[] dependent;
        int[] degree;
        int[] fill;

        boolean[][] connections;

        Graph(int n) {
            this.n = n;
            this.ALL = 1 << n;

            this.dependent = new boolean[ALL];
            this.fill = new int[ALL];

            this.degree = new int[n];
            this.connections = new boolean[n][n];
        }

        void addEdge(int vertex, int neighbor) {
            this.connections[vertex][neighbor] = true;
            this.degree[vertex]++;
            this.connections[neighbor][vertex] = true;
            this.degree[neighbor]++;
        }

        void colouring() {
            Arrays.fill(fill, Integer.MAX_VALUE);

            fill[0] = 0;
            int l = 0;
            while ((1 << l) < ALL) {
                fill[1 << l++] = 1;
            }


            for (int i = 0; i < ALL; i++) {
                dependent[i] = isIndependent(i);
            }

            for (int a = 0; a < ALL; a++) {
                for (int b = 0; b < ALL; b++) {
                    if ((a & b) == a) {
                        if (dependent[b & (~a)]) {
                            fill[b] = Math.min(fill[b], fill[a] + 1);
                        }
                    }
                }
            }

            System.out.println(fill[ALL - 1]);
        }

        boolean isIndependent(int lot) {
            for (int i = 0; i < n; i++) {
                if ((lot & (1 << i)) == (1 << i)) {
                    for (int j = 0; j < n; j++) {
                        if ((lot & (1 << j)) == (1 << j)) {
                            if (connections[i][j]) {
                                return false;
                            }
                        }
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
            int v = Integer.parseInt(temp[0]) - 1;
            int u = Integer.parseInt(temp[1]) - 1;
            g.addEdge(u, v);
        }

        g.colouring();
    }

}


//3 3
//1 2
//2 3
//3 1