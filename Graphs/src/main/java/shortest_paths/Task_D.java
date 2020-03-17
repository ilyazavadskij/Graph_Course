package shortest_paths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Task_D {
    static class Graph {

        int n;
        int[][] distance;
        final int MAX_VALUE;

        Graph(int n) {
            this.n = n;
            this.distance = new int[n][n];
            MAX_VALUE = n * 1000;
        }

        void addLine(int i, String[] line) {
            for (int j = 0; j < n; j++) {
                int value = Integer.parseInt(line[j]);
                if (value != -1) {
                    distance[i][j] = value;
                } else {
                    distance[i][j] = MAX_VALUE;
                }
            }
        }

        void floydWarshall() {
            for (int k = 0; k < n; k++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (distance[i][k] + distance[k][j] < distance[i][j])
                            distance[i][j] = distance[i][k] + distance[k][j];

                    }
                }
            }
            printDiameter();
            printRadius();
        }

        private void printRadius() {
            int radius = MAX_VALUE;
            for (int i = 0; i < n; ++i) {
                int eccentricity = 0;
                for (int j = 0; j < n; ++j) {
                    if (eccentricity < distance[i][j] && distance[i][j] != MAX_VALUE) {
                        eccentricity = distance[i][j];
                    }
                }
                if (eccentricity < radius) {
                    radius = eccentricity;
                }
            }
            System.out.println(radius);
        }

        private void printDiameter() {
            int diameter = 0;
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (diameter < distance[i][j] && distance[i][j] != MAX_VALUE) {
                        diameter = distance[i][j];
                    }
                }
            }
            System.out.println(diameter);
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

        g.floydWarshall();
    }

}