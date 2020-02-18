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
        boolean[][] connections;
        int[] parent;

        LinkedList<Integer> path;

        Graph(int w, int h) {
            this.w = w;
            this.h = h;
            this.n = w * h;
            this.connections = new boolean[w][h];

            this.parent = new int[n];
            Arrays.fill(this.parent, -1);

            this.path = new LinkedList<>();
        }


        void addLine(int i, char[] line) {
            for (int j = 0; j < this.w; j++) {
                connections[j][i] = line[j] == '.';
            }
        }

        void findPath(int start, int finish) {
            BFS(start, finish);

            System.out.println(Arrays.toString(parent));

            if (this.parent[finish] == -1) {
                System.out.println("NO");
            } else {
                System.out.println("YES");
                int current = finish;
                this.path.addFirst(current);
                while (current != start) {
                    current = this.parent[current];
                    this.path.addFirst(current);
                }
                path.forEach(v -> System.out.print((v % w + 1) + " " + (v / w + 1) + " "));
            }
        }

        void BFS(int start, int finish) {
            LinkedList<Integer> queue = new LinkedList<>();
            boolean[] visited = new boolean[n];

            visited[start] = true;
            queue.add(start);

            while (queue.size() != 0) {
                int u = queue.poll();
                int uX = u % this.w;
                int uY = u / this.w;

                int[] neighbors;
                if (this.w == 1) {
                    neighbors = new int[]{u - this.w, u + this.w};
                } else {
                    if (uX == 0) {
                        neighbors = new int[]{u - this.w, u + this.w, u + 1};
                    } else if (uX == this.w - 1) {
                        neighbors = new int[]{u - this.w, u + this.w, u - 1};
                    } else {
                        neighbors = new int[]{u - this.w, u + this.w, u - 1, u + 1};
                    }
                }
                System.out.println("  " + u + ": " + Arrays.toString(neighbors));


                for (int c : neighbors) {
                    if (c >= 0 && c < this.n) {
                        if (connections[c % this.w][c / this.w]) {
                            if (!visited[c]) {
                                this.parent[c] = u;
                                visited[c] = true;
                                queue.add(c);
                                if (c == finish) {
                                    return;
                                }
                            }
                        }
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
        for (int i = 0; i < h; i++) {
            g.addLine(i, br.readLine().toCharArray());
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