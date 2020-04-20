package LCA_NP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;


public class Task_C {

    static class Edge {
        int d;

        Edge(int d) {
            this.d = d;
        }
    }

    static class Graph {
        int n;

        boolean[] used;
        int[] height;
        int[] first;
        int[] tree;

        ArrayList<Integer> list;
        ArrayList<Edge>[] connections;

        Graph(int n) {
            this.n = n;
            this.used = new boolean[n];
            this.height = new int[n];
            this.first = new int[n];

            this.list = new ArrayList<>();
            this.connections = new ArrayList[n];
            for (int i = 0; i < n; i++)
                connections[i] = new ArrayList<>();
        }

        void addEdge(int vertex, int neighbor) {
            this.connections[vertex].add(new Edge(neighbor));
            this.connections[neighbor].add(new Edge(vertex));
        }


        void preProcess() {
            Arrays.fill(used, false);
            Arrays.fill(first, -1);

            DFS(0, 1);

            int m = list.size();
            tree = new int[4 * m + 1];
            Arrays.fill(tree, -1);

            buildTree(1, 0, m - 1);

            for (int i = 0; i < m; ++i) {
                int v = list.get(i);
                if (first[v] == -1) {
                    first[v] = i;
                }
            }
        }

        void DFS(int v, int h) {
            used[v] = true;
            height[v] = h;
            list.add(v);

            for (Edge edge : connections[v]) {
                if (!used[edge.d]) {
                    DFS(edge.d, h + 1);
                    list.add(v);
                }
            }
        }

        void buildTree(int i, int l, int r) {
            if (l == r) {
                tree[i] = list.get(l);
            } else {
                int m = (l + r) >> 1;
                buildTree(2 * i, l, m);
                buildTree(2 * i + 1, m + 1, r);
                if (height[tree[2 * i]] < height[tree[2 * i + 1]]) {
                    tree[i] = tree[2 * i];
                } else {
                    tree[i] = tree[2 * i + 1];
                }
            }
        }

        void LCA(int a, int b) {
            int left = first[a];
            int right = first[b];

            if (left > right) {
                System.out.println(getMinTree(1, 0, list.size() - 1, right, left) + 1);
            } else {
                System.out.println(getMinTree(1, 0, list.size() - 1, left, right) + 1);
            }
        }

        int getMinTree(int i, int sl, int sr, int l, int r) {
            if (sl == l && sr == r) {
                return tree[i];
            }

            int sm = (sl + sr) >> 1;

            if (r <= sm) {
                return getMinTree(2 * i, sl, sm, l, r);
            }
            if (l > sm) {
                return getMinTree(2 * i + 1, sm + 1, sr, l, r);
            }

            int a1 = getMinTree(2 * i, sl, sm, l, sm);
            int a2 = getMinTree(2 * i + 1, sm + 1, sr, sm + 1, r);
            return height[a1] < height[a2] ? a1 : a2;
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        Graph g = new Graph(n);

        for (int i = 0; i < n - 1; i++) {
            int vertex = Integer.parseInt(br.readLine()) - 1;
            g.addEdge(i + 1, vertex);
        }

        g.preProcess();

        int m = Integer.parseInt(br.readLine());

        for (int i = 0; i < m; i++) {
            String[] temp = br.readLine().split(" ");
            int first = Integer.parseInt(temp[0]) - 1;
            int second = Integer.parseInt(temp[1]) - 1;
            g.LCA(first, second);
        }
    }

}

//5
//1
//1
//2
//3
//2
//2 3
//4 5