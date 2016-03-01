package Prim;

import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

class Vertex implements Comparable<Vertex> {
    PriorityQueue<Edge> list;

    public int key, index;

    public Vertex() {
        list = new PriorityQueue<>();
        this.key = -1;
        this.index = -1;
    }

    public String toString() {
        return list.toString();
    }

    public int compareTo(Vertex v) {
        return (this.key - v.key);
    }
}

class Edge implements Comparable<Edge> {
    public int end, len;

    public Edge(int end, int len) {
        this.end = end;
        this.len = len;
    }

    public int compareTo(Edge e) {
        return (this.len - e.len);
    }

    public String toString() {
        return "->" + String.valueOf(end) + "(" + String.valueOf(len) + ")";
    }
}

class ListGraph {
    Vertex[] data;

    public ListGraph(int n) {
        data = new Vertex[n];

        for (int i = 0; i < n; i++)
            data[i] = new Vertex();
    }

    public void addEdge(int start, int end, int len) {
        data[start].list.add(new Edge(end, len));
        data[end].list.add(new Edge(start, len));
    }

    public void print() {
        for (Vertex v : this.data) {
            PriorityQueue<Edge> clone = new PriorityQueue<>(v.list);

            while (!clone.isEmpty()) {
                System.out.print(clone.poll() + " ");
            }

            System.out.println();
        }
    }
}


public class Prim {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int vNum = in.nextInt(), eNum = in.nextInt(), res = 0;

        ListGraph G = new ListGraph(vNum);

        for (int i = 0; i < eNum; i++) {
            G.addEdge(in.nextInt(), in.nextInt(), in.nextInt());
        }

        //G.print();

        PriorityQueue<Vertex> q = new PriorityQueue<>();
        Random rand = new Random();
        Vertex v = G.data[rand.nextInt(vNum)];
        while (true) {
            v.index = -2;
            for (Edge e : v.list) {
                Vertex u = G.data[e.end];
                if (u.index == -1) {
                    u.key = e.len;
                    u.index = 1;
                    q.add(u);
                } else if (u.index != -2 && e.len < u.key) {
                    q.remove(u);
                    u.key = e.len;
                    q.add(u);
                }
            }

            if (q.isEmpty()) {
                break;
            }

            v = q.poll();

            res += v.key;

        }

        System.out.println(res);
    }
}
