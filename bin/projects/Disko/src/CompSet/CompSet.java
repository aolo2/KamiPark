package CompSet;

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
    private int vertexNum;
    private Vertex[] data;

    public ListGraph(int n) {
        this.vertexNum = n;

        data = new Vertex[n];

        for (int i = 0; i < n; i++)
            data[i] = new Vertex();
    }

    public int getVertexNum() {
        return vertexNum;
    }

    public void addEdge(int start, int end) {
        data[start].list.add(end);
        data[end].list.add(start);
    }

    public boolean dfs(int start) {

        boolean stop = true;

        if (!data[start].visited) {
            data[start].visited = true;
            stop = false;
        }

        for (int i : data[start].list) {
            if (!data[i].visited) {
                data[i].visited = true;
                stop = false;
                dfs(i);
            }
        }

        return stop;
    }

    public void print() {
        for (Vertex v : this.data)
            System.out.println(v);
    }
}

public class CompSet {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int a, b, vertexN = in.nextInt(), edgeN = in.nextInt(), compset = 0;

        ListGraph G = new ListGraph(vertexN);
        for (int i = 0; i < edgeN; i++) {
            a = in.nextInt();
            b = in.nextInt();

            G.addEdge(a, b);
        }

        for (int i = 0; i < G.getVertexNum(); i++) {
            if (!G.dfs(i))
                compset++;
        }

        System.out.println(compset);

    }
}
