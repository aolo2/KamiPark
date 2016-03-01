package EqDist;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

class Vertex {
    boolean visited;
    ArrayList<Integer> list;

    public Vertex() {
        list = new ArrayList<>();
        visited = false;
    }

    @Override
    public String toString() {
        return visited + ", " + list;
    }
}

class ListGraph {
    private int vertexNum;
    private Vertex[] data;

    public ListGraph(int n) {
        this.vertexNum = n;

        data = new Vertex[n];

        for (int i = 0; i < n; i++)
            data[i] = new Vertex();
    }

    public void addEdge(int start, int end) {
        data[start].list.add(end);
        data[end].list.add(start);
    }

    public void bfs(ArrayList<Integer> bearingList) {
        ArrayList<int[]> lengths = new ArrayList<>(bearingList.size());

        for (int start : bearingList) {
            ArrayDeque<Integer> q1, q2;
            q1 = new ArrayDeque<>();
            q2 = new ArrayDeque<>();

            ArrayList<Integer> p1, p2;
            p1 = new ArrayList<>();
            p2 = new ArrayList<>();

            q1.add(start);
            q2.add(start);

            while (!q1.isEmpty()) {
                int k1 = q1.remove(), k2 = q2.remove();
                if (!data[k1].visited) {
                    p1.add(k1);
                    p2.add(k2);
                    data[k1].visited = true;

                    for (int n : data[k1].list)
                        if (!data[n].visited) {
                            q1.add(n);
                            q2.add(k1);
                        }
                }
            }

            int[] len = new int[vertexNum];
            for (int i = 0; i < vertexNum; i++) {
                int lvl = -1, from, index;

                if (data[i].visited) {
                    lvl = 0;
                    for (from = i; from != start; ) {
                        index = p1.indexOf(from);

                        if (index != -1) {
                            from = p2.get(index);
                            lvl++;
                        }
                    }
                }

                len[i] = lvl;

            }

            lengths.add(len);
            reset();
        }

        int count = 0;
        for (int i = 0; i < vertexNum; i++) {
            boolean same = true;
            for (int k = 1; k < lengths.size(); k++)
                if (lengths.get(k)[i] != lengths.get(k - 1)[i] ||
                        lengths.get(k)[i] == -1 || lengths.get(k - 1)[i] == -1)
                    same = false;

            if (same) {
                System.out.print(i + " ");
                count++;
            }
        }

        if (count == 0)
            System.out.println("-");
    }

    public void reset() {
        for (Vertex v : data)
            v.visited = false;
    }
}

public class EqDist {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int a, b, vertexN, edgeN, bearingN;

        vertexN = in.nextInt();
        edgeN = in.nextInt();

        ListGraph G = new ListGraph(vertexN);
        for (int i = 0; i < edgeN; i++) {
            a = in.nextInt();
            b = in.nextInt();

            G.addEdge(a, b);
        }

        bearingN = in.nextInt();

        ArrayList<Integer> bearingList = new ArrayList<>();
        for (int i = 0; i < bearingN; i++)
            bearingList.add(in.nextInt());

        G.bfs(bearingList);
    }
}
