package DFS_BFS;

import java.util.Scanner;

//Задача A. От матрицы смежности к списку ребер [1 sec, 256 mb]
//        Простой неориентированный граф задан матрицей смежности, выведите его представление в виде списка ребер.
//
//      Формат входных данных
//        Входной файл содержит число N (1 <= N <= 100) — число вершин в графе, и затем N строк по N чисел,
//        каждое из которых равно 0 или 1 — его матрицу смежности.
//
//      Формат выходных данных
//        Выведите в выходной файл список ребер заданного графа. Ребра можно выводить в произвольном порядке.

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