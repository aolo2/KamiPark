package GraphBase;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

class MatrixGraph {
    private int[][] data;
    private boolean[] visited;

    public MatrixGraph(int n) {
        data = new int[n][n];
        visited = new boolean[n];
    }

    public void addEdge(int start, int end) {
        data[start][end] = 1;
        data[end][start] = -1;
    }

    public void reverse() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] *= -1;
            }
        }
    }

    public void reset() {
        for (int i = 0; i < visited.length; i++) {
            visited[i] = false;
        }
    }

    public String toString() {
        String tmp = "";

        for (int[] col : data) {
            for (int n : col) {
                tmp += String.format("%3d", n);
            }

            tmp += '\n';
        }

        return tmp;
    }

    public boolean isVisited(int v) {
        return visited[v];
    }

    public boolean allVisited() {
        for (boolean b : visited) {
            if (!b) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<Integer> bfs(int start) {
        ArrayDeque<Integer> q = new ArrayDeque<>();
        ArrayList<Integer> path = new ArrayList<>();

        q.add(start);
        while (!q.isEmpty()) {
            int tmp = q.remove();
            if (!visited[tmp]) {
                path.add(tmp);
                visited[tmp] = true;
                for (int i = 0; i < data[tmp].length; i++) {
                    int near = data[tmp][i];
                    if (near == 1) {
                        q.add(i);
                    }
                }
            }
        }

        return path;
    }
}

public class GraphBase {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt(), M = in.nextInt();
        MatrixGraph G = new MatrixGraph(N);

        for (int i = 0; i < M; i++) {
            G.addEdge(in.nextInt(), in.nextInt());
        }

//        System.out.println(G);

        ArrayList<ArrayList<Integer>> strongComp = new ArrayList<>();

        for (int start = 0; start < N; start++) {
            ArrayList<Integer> union = G.bfs(start);
            G.reverse();
            G.reset();
            ArrayList<Integer> tmp = G.bfs(start);

            union.retainAll(tmp);
            strongComp.add(union);

            G.reset();
        }

        for (ArrayList<Integer> a : strongComp) {
            System.out.println(a);
        }
    }
}