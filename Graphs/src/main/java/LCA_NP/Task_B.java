package LCA_NP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

//Задача B. Коммивояжёр возвращается! [4 sec, 256 mb]

public class Task_B {

    static class Graph {
        int n;
        int result;
        int finalResult;

        final int ALL;

        int[][] connections;

        List<Integer> path;
        Integer[][] memo;
        Integer[][] parent;

        Graph(int n) {
            this.n = n;
            this.result = Integer.MAX_VALUE;
            this.finalResult = Integer.MAX_VALUE;

            this.ALL = (1 << n) - 1;

            this.connections = new int[n][n];
            this.memo = new Integer[n][1 << n];
            this.parent = new Integer[n][1 << n];
            path = new LinkedList<>();
        }

        void addLine(int i, String[] line) {
            for (int j = 0; j < n; j++) {
                connections[i][j] = Integer.parseInt(line[j]);
            }
        }

        void findLeast() {
            for (int i = 0; i < n; i++) {
                Arrays.fill(memo[i], Integer.MAX_VALUE);
            }

            memo[0][0] = 0;

            for (int mask = 0; mask < ALL; mask++) {
                for (int i = 0; i < n; i++) {
                    if (memo[i][mask] == Integer.MAX_VALUE) {
                        continue;
                    }

                    for (int j = 0; j < n; j++) {
                        if ((mask & (1 << j)) != 0) {
                            memo[j][mask ^ (1 << j)] = Math.min(memo[j][mask ^ (1 << j)], memo[i][mask] + connections[i][j]);
                        }
                    }
                }
            }

            System.out.println(memo[0][ALL - 1]);

            int mask = ALL - 2;
            int j = 0;

            for (int k = 0; k < n - 1; k++) {
                for (int i = 0; i < n; i++) {
                    if (j != i && (mask & (1 << i)) != 0) {
                        if (memo[j][mask ^ (1 << j)] == memo[i][mask] + connections[i][j]) {
                            j = i;
                            mask = mask ^ (1 << i);

                            path.add(j);
                            break;
                        }
                    }
                }
            }

            for (int i = path.size() - 1; i >= 0; i--) {
                System.out.print(path.get(i) + ' ');
            }
        }

        int TravellingSalesman(int i, int mask, int start) {
            if (mask == ALL) {
                return 0;
            }

            if (memo[i][mask] != null) {
                return memo[i][mask];
            }

            int minCost = Integer.MAX_VALUE;
            int index = -1;

            for (int j = 0; j < n; j++) {
                if ((mask & (1 << j)) == 0) {

                    int nextMask = mask | (1 << j);
                    int newCost = connections[i][j] + TravellingSalesman(j, nextMask, start);

                    if (newCost <= minCost) {
                        minCost = newCost;
                        index = j;
                    }
                }
            }

            parent[i][mask] = index;
            return memo[i][mask] = minCost;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");
        int n = Integer.parseInt(temp[0]);

        Graph g = new Graph(n);

        for (int i = 0; i < n; i++) {
            temp = br.readLine().split(" ");
            g.addLine(i, temp);
        }

        g.findLeast();
    }

}

//3
//8 1 6
//3 5 7
//4 9 2

//5
//1 5 2 3 8
//6 6 10 6 10
//1 2 6 2 5
//6 8 7 2 1
//1 10 1 10 3

