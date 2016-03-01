package BridgeNum;

import java.util.ArrayList;
import java.util.Scanner;

class Vertex {
    boolean visited;
    ArrayList<Integer> list;

    public Vertex() {
        list = new ArrayList<>();
        visited = false;
    }

    public String toString() {
        return visited + ", " + list;
    }
}

class ListGraph {
    private int vertexNum, bridges;
    private Vertex[] data;
    private boolean[] used;
    private int timer, tin[], fup[];

    private int min(int a, int b) {
        return (a <= b) ? a : b;
    }

    public ListGraph(int n) {
        this.vertexNum = n;

        data = new Vertex[n];

        for (int i = 0; i < n; i++)
            data[i] = new Vertex();

        tin = new int[n];
        fup = new int[n];
        used = new boolean[n];
    }

    public void addEdge(int start, int end) {
        data[start].list.add(end);
        data[end].list.add(start);
    }

    private int dfs(int v, int p) {
        used[v] = true;
        tin[v] = fup[v] = timer++;
        for (int i = 0; i < data[v].list.size(); i++) {
            int to = data[v].list.get(i);

            if (to == p)
                continue;

            if (used[to])
                fup[v] = min(fup[v], tin[to]);
            else {
                dfs(to, v);
                fup[v] = min(fup[v], fup[to]);
                if (fup[to] > tin[v])
                    bridges++;
            }
        }

        return bridges;
    }

    public int find_bridges() {
        timer = 0;
        for (int i = 0; i < vertexNum; i++)
            if (!used[i])
                dfs(i, -1);

        return bridges;
    }
}


public class BridgeNum {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int a, b, vertexN, edgeN;
        vertexN = in.nextInt();
        edgeN = in.nextInt();

        ListGraph G = new ListGraph(vertexN);
        for (int i = 0; i < edgeN; i++) {
            a = in.nextInt();
            b = in.nextInt();

            G.addEdge(a, b);
        }

        System.out.println(G.find_bridges());
    }
}
