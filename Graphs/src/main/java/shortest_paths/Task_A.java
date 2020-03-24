package shortest_paths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//Задача A. Флойд [2 sec, 256 mb]

public class Task_A {

    static class Graph {
        int n;
        int[][] distance;

        Graph(int n) {
            this.n = n;
            this.distance = new int[n][n];
        }

        void addLine(int i, String[] line) {
            for (int j = 0; j < n; j++) {
                distance[i][j] = Integer.parseInt(line[j]);
            }
        }

        void FloydWarshall() {
            for (int k = 0; k < n; k++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (distance[i][k] + distance[k][j] < distance[i][j])
                            distance[i][j] = distance[i][k] + distance[k][j];
                    }
                }
            }
            printDistance();
        }

        void printDistance() {
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    System.out.print(distance[i][j] + " ");
                }
                System.out.println();
            }
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        Graph g = new Graph(n);
        for (int i = 0; i < n; i++) {
            g.addLine(i, br.readLine().split(" "));
        }

        g.FloydWarshall();
    }

}
