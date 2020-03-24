package DFS_BFS;

import java.util.Scanner;

//Задача A. От матрицы смежности к списку ребер [1 sec, 256 mb]

public class Task_A {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int edge = input.nextInt();
                if (edge != 0) {
                    if (i <= j) {
                        System.out.printf("%d %d\n", i + 1, j + 1);
                    }
                }
            }
        }
    }

}
