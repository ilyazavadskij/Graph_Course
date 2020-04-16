package bridge_cut_vertex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

//Задача G. Разрезание графа [2 sec, 256 mb]

public class Task_G {

    static class Graph {
        int n;
        int k;
        final int MAX_OPERATIONS;

        ArrayList<Integer>[] connections;
        Operation[] operations;

        int[] dsu;
        boolean[] result;

        Graph(int n, int k) {
            this.n = n;
            this.k = k;
            this.MAX_OPERATIONS = 150001;

            this.dsu = new int[MAX_OPERATIONS];
            this.result = new boolean[MAX_OPERATIONS];

            this.connections = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                this.connections[i] = new ArrayList<>();
            }
            this.operations = new Operation[MAX_OPERATIONS];
        }

        void addEdge(int vertex, int neighbor) {
            this.connections[vertex].add(neighbor);
        }

        void addOperation(int i, String op, int vertex, int neighbor) {
            this.operations[i] = new Operation(op, vertex, neighbor);
        }

        void cutGraph() {
            for (int i = 0; i < n; i++) {
                dsu[i] = i;
            }

            for (int i = k - 1; i >= 0; i--) {
                if (operations[i].op == 1) {
                    result[i] = (root(operations[i].u) == root(operations[i].v));
                } else {
                    union(operations[i].u, operations[i].v);
                }
            }

            printResult();
        }

        int root(int n) {
            if (n == dsu[n]) {
                return n;
            }
            return dsu[n] = root(dsu[n]);
        }

        void union(int x, int y) {
            int x1 = root(x);
            int y1 = root(y);
            dsu[x1] = y1;
        }

        private void printResult() {
            for (int i = 0; i < k; i++) {
                if (operations[i].op == 1) {
                    if (result[i]) {
                        System.out.println("YES");
                    } else {
                        System.out.println("NO");
                    }
                }
            }
        }
    }

    static class Operation {
        int op;
        int u;
        int v;

        Operation(String operation, int u, int v) {
            this.op = operation.equals("ask") ? 1 : 0;
            this.u = u;
            this.v = v;
        }

        public String toString() {
            return "(" + op + "=" + u + "-" + v + ")";
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");
        int n = Integer.parseInt(temp[0]);
        int m = Integer.parseInt(temp[1]);
        int k = Integer.parseInt(temp[2]);

        Graph g = new Graph(n, k);

        for (int i = 0; i < m; i++) {
            temp = br.readLine().split(" ");
            int u = Integer.parseInt(temp[0]) - 1;
            int v = Integer.parseInt(temp[1]) - 1;
            g.addEdge(u, v);
            g.addEdge(v, u);
        }

        for (int i = 0; i < k; i++) {
            temp = br.readLine().split(" ");
            String operation = temp[0];
            int u = Integer.parseInt(temp[1]) - 1;
            int v = Integer.parseInt(temp[2]) - 1;
            g.addOperation(i, operation, u, v);
        }

        g.cutGraph();
    }

}

//3 3 7
//1 2
//2 3
//3 1
//ask 3 3
//cut 1 2
//ask 1 2
//cut 1 3
//ask 2 1
//cut 2 3
//ask 3 1