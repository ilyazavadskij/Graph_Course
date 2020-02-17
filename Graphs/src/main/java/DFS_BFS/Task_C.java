package DFS_BFS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Task_C {
    static class Graph {

        int w;
        int h;
        int n;
        List<Set<Integer>> connections;

        Graph(int w, int h) {
            this.w = w;
            this.h = h;
            this.n = w * h;
            this.connections = new ArrayList();
            for (int i = 0; i < n; i++)
                connections.add(new HashSet<>());
        }

        void addEdge(int vertex, int neighbor) {
            this.connections.get(vertex).add(neighbor);
        }

        void findPath(int start, int finish) {
            LinkedList<Integer> visited = new LinkedList<>();
            findPathUtil(start, finish, visited);
            if (visited.getLast().equals(finish)) {
                System.out.println("YES");
                visited.forEach(v -> System.out.print((v % this.w + 1) + " " + (v / this.w + 1) + " "));
//                System.out.println();
//                visited.forEach(v -> System.out.print(v + " "));
            } else {
                System.out.println("NO");
            }
        }

        void findPathUtil(int v, int finish, LinkedList<Integer> visited) {
            visited.add(v);

            if (connections.get(v).contains(finish)) {
                visited.add(finish);
                return;
            }

            for (int c : connections.get(v)) {
                if (!visited.contains(c)) {
                    findPathUtil(c, finish, visited);
                    if (visited.getLast() == finish) {
                        return;
                    } else {
                        visited.removeLast();
                    }
                }
            }

        }
    }

    private static void checkConnections(char[][] cells, int x, int y, Graph g) {
        if (cells[y][x] == '.') {
            for (int i = x - 1; i <= x + 1; i++) {
                if (i < 0 || cells.length == i) {
                    continue;
                }
                if (i != x) {
                    if (cells[y][i] == '.') {
                        g.addEdge(y * cells.length + x, y * cells.length + i);
                    }
                }
            }

            for (int j = y - 1; j <= y + 1; j++) {
                if (j < 0 || cells[x].length == j) {
                    continue;
                }
                if (j != y) {
                    if (cells[j][x] == '.') {
                        g.addEdge(y * cells.length + x, j * cells.length + x);
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String temp[] = br.readLine().split(" ");
        int w = Integer.parseInt(temp[0]);
        int h = Integer.parseInt(temp[1]);
        int startX = Integer.parseInt(temp[2]) - 1;
        int startY = Integer.parseInt(temp[3]) - 1;
        int finishX = Integer.parseInt(temp[4]) - 1;
        int finishY = Integer.parseInt(temp[5]) - 1;

        Graph g = new Graph(w, h);
        char[][] cells = new char[w][h];
        for (int i = 0; i < h; i++) {
            cells[i] = br.readLine().toCharArray();
        }

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                checkConnections(cells, i, j, g);
            }
        }

//        for (int v = 0; v < g.connections.size(); v++) {
//            System.out.println(v + ": " + g.connections.get(v));
//        }

        g.findPath(startY * w + startX, finishY * w + finishX);
    }
}

//4 2 1 1 4 2
//....
//....

//4 2 1 1 4 2
//..*.
//.*..

//4 2 1 1 4 2
//..*.
//*...

//4 2 4 2 1 1
//....
//....