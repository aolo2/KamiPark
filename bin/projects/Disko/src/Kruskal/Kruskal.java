package Kruskal;

import java.util.*;

class Vertex {
    Vertex parent = this;
    int x, y, depth;

    public Vertex(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vertex Find() {
        if (this != this.parent) {
            this.parent = this.parent.Find();
        }

        return this.parent;
    }

    public void Union(Vertex v) {
        Vertex a = this.Find(), b = v.Find();

        if (a.depth < b.depth) {
            a.parent = b;
        } else {
            b.parent = a;
            if (a.parent == b.parent && a != b) {
                a.depth++;
            }
        }
    }

}

class Edge {
    float weight;
    Vertex start, end;

    public Edge(Vertex start, Vertex end) {
        this.start = start;
        this.end = end;
        weight = (float) (Math.sqrt((end.x - start.x) * (end.x - start.x) + (end.y - start.y) * (end.y - start.y)));
    }
}

public class Kruskal {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int pNum = in.nextInt();

        double min_length = 0;

        ArrayList<Vertex> points = new ArrayList<>(pNum);
        ArrayList<Edge> ws = new ArrayList<>((pNum * (pNum - 1)) / 2);

        for (int i = 0; i < pNum; i++) {
            points.add(new Vertex(in.nextInt(), in.nextInt()));
        }

        for (int i = 0; i < pNum; i++) {
            for (int j = i + 1; j < pNum; j++) {
                ws.add(new Edge(points.get(i), points.get(j)));
            }
        }



        for (Edge e : ws) {
            if (e.start.Find() != e.end.Find()) {
                min_length += e.weight;
                e.start.Union(e.end);
            }
        }

        System.out.format("%.2f\n", min_length);

    }
}
