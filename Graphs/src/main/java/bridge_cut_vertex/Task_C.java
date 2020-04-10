package bridge_cut_vertex;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Task_C {

    static class Graph {
        int n;

        boolean[] visited;
        boolean[] visitedReversed;
        int[] color;

        ArrayList<Integer>[] connections;
        ArrayList<Integer>[] connectionsReversed;

        LinkedList<Integer> order;

        Graph(int n) {
            this.n = n;
            this.visited = new boolean[n];
            this.visitedReversed = new boolean[n];
            this.color = new int[n];

            this.order = new LinkedList<>();

            this.connections = new ArrayList[n];
            this.connectionsReversed = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                connections[i] = new ArrayList<>();
                connectionsReversed[i] = new ArrayList<>();
            }
        }

        void addEdge(int vertex, int neighbor) {
            this.connections[vertex].add(neighbor);
            this.connectionsReversed[neighbor].add(vertex);
        }

        void condensation() {
            for (int i = 0; i < n; i++) {
                visited[i] = false;
            }

            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    firstDFS(i);
                }
            }

            for (int i = 0; i < n; i++) {
                visitedReversed[i] = false;
                color[i] = -1;
            }
            int c = 0;

            for (int i = 0; i < n; i++) {
                int v = order.get(n - i - 1);
                if (!visitedReversed[v]) {
                    secondDFS(v, c);
                    c++;
//                    System.out.println(v + ": " + Arrays.toString(color));
                }
            }


            Set<Pair<Integer, Integer>> s = new HashSet<>();
            for (int from = 0; from < n; from++) {
                for (Integer to : connections[from]) {
                    if (color[from] != color[to]) {
                        s.add(new Pair<>(color[from], color[to]));
                    }
                }
            }
//            System.out.println(s);
            System.out.println(s.size());
        }

        void firstDFS(int v) {
            visited[v] = true;
            for (Integer d : this.connections[v]) {
                if (!visited[d]) {
                    firstDFS(d);
                }
            }
            order.addLast(v);
        }

        void secondDFS(int v, int c) {
            visitedReversed[v] = true;
            color[v] = c;
            for (Integer d : this.connectionsReversed[v]) {
                if (!visitedReversed[d]) {
                    secondDFS(d, c);
                }
            }
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = br.readLine().split(" ");
        int n = Integer.parseInt(temp[0]);
        int m = Integer.parseInt(temp[1]);

        Graph g = new Graph(n);

        for (int i = 0; i < m; i++) {
            temp = br.readLine().split(" ");
            int first = Integer.parseInt(temp[0]) - 1;
            int second = Integer.parseInt(temp[1]) - 1;
            g.addEdge(first, second);
        }

        g.condensation();
    }

}

//4 4
//2 1
//3 2
//2 3
//4 3


//100 398
//58 56
//96 68
//13 81
//36 11
//11 57
//90 95
//61 12
//24 97
//50 12
//61 12
//70 91
//70 49
//70 27
//64 42
//70 25
//32 96
//73 12
//96 68
//46 10
//9 53
//93 2
//33 27
//70 32
//15 18
//14 45
//29 63
//47 1
//70 1
//88 94
//62 34
//19 53
//70 100
//94 62
//19 53
//9 26
//18 98
//70 76
//70 57
//33 64
//50 73
//70 40
//70 58
//75 64
//95 93
//67 23
//49 77
//18 98
//95 93
//70 46
//14 45
//82 44
//70 9
//98 52
//7 99
//70 41
//70 8
//70 87
//63 26
//51 19
//70 83
//12 72
//70 88
//13 57
//92 40
//81 36
//9 26
//5 4
//70 11
//70 65
//63 29
//24 97
//70 12
//73 50
//70 42
//97 2
//87 66
//70 73
//100 7
//3 96
//23 3
//71 5
//55 1
//70 95
//43 39
//66 17
//49 77
//32 96
//70 21
//40 92
//47 86
//70 23
//1 19
//94 62
//70 99
//26 46
//34 20
//44 43
//77 84
//6 71
//22 21
//25 28
//25 58
//48 92
//55 1
//88 78
//25 41
//100 7
//57 71
//28 79
//40 25
//70 6
//94 76
//33 66
//70 14
//70 75
//12 72
//71 5
//45 40
//22 18
//71 38
//15 18
//13 57
//62 76
//43 37
//17 49
//70 17
//70 13
//98 70
//36 6
//70 33
//88 94
//78 62
//70 7
//83 32
//70 82
//43 82
//99 22
//28 79
//70 96
//59 14
//70 31
//70 47
//69 80
//6 11
//26 53
//85 91
//57 38
//96 68
//30 69
//67 23
//68 27
//70 97
//62 20
//15 52
//72 85
//89 20
//89 78
//8 97
//70 85
//51 73
//70 35
//70 5
//2 8
//70 48
//73 50
//40 25
//69 91
//70 90
//30 85
//99 22
//70 92
//70 62
//3 83
//86 16
//31 88
//70 50
//70 61
//47 43
//78 88
//82 55
//87 66
//90 95
//70 38
//78 88
//42 74
//48 92
//97 74
//76 34
//75 64
//37 82
//56 60
//37 82
//70 43
//22 21
//33 87
//70 94
//63 9
//46 10
//70 78
//60 67
//70 67
//52 70
//8 42
//97 95
//10 51
//70 79
//49 49
//17 49
//70 15
//90 8
//26 10
//94 89
//15 7
//27 33
//70 68
//14 59
//16 44
//45 45
//41 59
//70 18
//9 19
//29 29
//3 83
//70 29
//20 31
//13 57
//23 3
//70 30
//70 69
//86 39
//64 27
//41 59
//88 34
//74 24
//70 98
//72 85
//70 55
//77 84
//42 74
//34 20
//82 55
//35 29
//70 84
//70 56
//53 35
//3 67
//70 93
//70 10
//47 86
//62 76
//44 82
//12 72
//79 48
//57 71
//43 47
//70 60
//33 87
//30 69
//70 24
//92 41
//39 37
//53 35
//29 63
//70 20
//48 28
//70 89
//97 65
//70 81
//43 43
//21 15
//45 40
//80 38
//70 16
//70 63
//2 8
//58 56
//72 69
//6 11
//91 30
//70 36
//44 43
//36 6
//21 15
//70 22
//19 51
//68 58
//85 91
//70 100
//87 77
//4 81
//93 8
//35 29
//81 36
//70 80
//74 24
//26 46
//70 53
//1 16
//70 44
//70 28
//20 31
//9 19
//2 65
//56 60
//60 67
//5 38
//70 100
//69 72
//70 19
//84 75
//28 79
//27 33
//52 28
//45 40
//54 89
//97 93
//77 84
//70 86
//65 90
//31 54
//41 41
//90 97
//65 90
//54 89
//18 70
//83 32
//70 26
//7 99
//99 15
//50 61
//69 80
//70 45
//59 14
//76 31
//84 75
//10 51
//50 91
//36 4
//23 67
//23 60
//96 58
//70 59
//93 2
//8 16
//70 71
//70 37
//70 39
//70 2
//100 18
//89 78
//3 58
//80 73
//11 13
//66 17
//11 13
//4 81
//38 4
//65 74
//70 74
//23 3
//70 72
//69 69
//70 3
//39 37
//70 66
//70 52
//15 99
//5 34
//70 54
//70 64
//98 99
//87 66
//51 19
//43 47
//31 54
//52 18
//86 39
//97 65
//58 3
//63 9
//92 41
//91 30
//36 5
//27 33
//70 34
//82 82
//70 51
//70 4
//38 4
//16 44
//98 52
//84 27
//56 96
//70 77
//48 48
//79 48
//77 87
//50 61