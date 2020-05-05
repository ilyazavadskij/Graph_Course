package matching_centroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//Задача D. Минимальное контролирующее множество [2 sec, 256 mb]

public class Task_D {

    static class Edge {
        int d;

        Edge(int d) {
            this.d = d;
        }

        public String toString() {
            return "{"+ d + "}";
        }
    }

    static class Graph {
        int n;
        int m;

        boolean[] match;
        boolean[] visited;

        int maxPairsSize = 0;
        int[] maxPairs;

        ArrayList<Edge>[] leftEdges;
        ArrayList<Edge>[] orientedEdges;


        Graph(int m, int n) {
            this.m = m;
            this.n = n;

            this.match = new boolean[m];
            this.maxPairs = new int[m];
            this.visited = new boolean[m + n];

            this.leftEdges = new ArrayList[m];
            for (int i = 0; i < m; i++) {
                leftEdges[i] = new ArrayList<>();
            }

            this.orientedEdges = new ArrayList[m + n];
            for (int i = 0; i < m + n; i++)
                orientedEdges[i] = new ArrayList<>();
        }

        void addEdge(int vertex, int neighbor) {
            this.leftEdges[vertex].add(new Edge(neighbor));
        }

        void addMaxPair(int vertex, int neighbor) {
            this.maxPairsSize++;
            this.maxPairs[vertex] = neighbor;
            this.match[vertex] = true;
        }

        void vertexCover() {
            for (int i = 0; i < m; i++) {
                for (Edge edge : leftEdges[i]) {
                    if (maxPairs[i] == edge.d) {
                        orientedEdges[edge.d + m].add(new Edge(i));
                    } else {
                        orientedEdges[i].add(new Edge(edge.d + m));
                    }
                }
            }

//            for (int i = 0; i < m + n; i++) {
//                System.out.println(i + ": " + orientedEdges[i]);
//            }

            for (int i = 0; i < m; i++) {
                if (!match[i]) {
                    DFS(i);
                }
            }

            minVertexCover();
        }

        void DFS(int v) {
            visited[v] = true;

            for (Edge edge : orientedEdges[v]) {
                if (!visited[edge.d]) {
                    DFS(edge.d);
                }
            }
        }

        void minVertexCover() {
            System.out.println(maxPairsSize);
            List<Integer> cover = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                if (!visited[i]) {
                    cover.add(i + 1);
                }
            }
            printCover(cover);
            cover.clear();

            for (int i = m; i < m + n; i++) {
                if (visited[i]) {
                    cover.add(i + 1 - m);
                }
            }
            printCover(cover);
        }

        void printCover(List<Integer> cover) {
            System.out.print(cover.size() + " ");
            for (Integer v : cover) {
                System.out.print(v + " ");
            }
            System.out.println();
        }

        void print() {
            for (int i = 0; i < m; i++) {
                System.out.println(i + ": " + leftEdges[i]);
            }
            System.out.println();
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");
        int m = Integer.parseInt(temp[0]);
        int n = Integer.parseInt(temp[1]);

        Graph g = new Graph(m, n);

        for (int v = 0; v < m; v++) {
            temp = br.readLine().split(" ");
            int l = Integer.parseInt(temp[0]);
            for (int j = 0; j < l; j++) {
                int u = Integer.parseInt(temp[j + 1]);
                g.addEdge(v, u - 1);
            }
        }
//        g.print();

        temp = br.readLine().split(" ");
        for (int v = 0; v < m; v++) {
            if (!temp[v].equals("0")) {
                int u = Integer.parseInt(temp[v]) - 1;
                g.addMaxPair(v, u);
            }
        }

        g.vertexCover();
    }
}

//3 2
//2 1 2
//1 2
//1 2
//1 2 0