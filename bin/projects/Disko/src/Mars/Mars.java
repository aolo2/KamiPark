package Mars;
// I'm so bad

import java.util.*;

class Vertex {
    boolean visited;
    int val;
    ArrayList<Integer> list;

    public Vertex(int val) {
        list = new ArrayList<>();
        this.val = val;
        visited = false;
    }
}

class Node {
    int here, from;

    public Node(int here, int from) {
        this.here = here;
        this.from = from;
    }
}

class ListGraph {
    private Vertex[] data;

    public ListGraph(int n) {
        data = new Vertex[n];

        for (int i = 0; i < n; i++)
            data[i] = new Vertex(i);
    }

    public boolean isEdge(int to, int from) {
        return (data[from].list.indexOf(to) != -1);
    }

    public void addEdge(int start, int end) {
        if (data[start].list.indexOf(end) == -1) {
            data[start].list.add(end);
        }

        if (data[end].list.indexOf(start) == -1) {
            data[end].list.add(start);
        }
    }

    public ArrayList<Node> bfs(int start) {
        ArrayDeque<Node> q = new ArrayDeque<>();
        ArrayList<Node> path = new ArrayList<>();

        q.add(new Node(start, start));

        while (!q.isEmpty()) {
            Node k = q.remove();

            if (!data[k.here].visited) {
                path.add(k);
                data[k.here].visited = true;

                for (int n : data[k.here].list) {
                    if (!data[n].visited) {
                        q.add(new Node(n, k.here));

                    }
                }
            }
        }

        return path;
    }

    boolean allVisited() {
        for (Vertex v : data) {
            if (!v.visited) {
                return false;
            }
        }

        return true;
    }
}

class Rule {
    ArrayList<Integer> team1, team2;

    public Rule(ArrayList<Node> src) {
        team1 = new ArrayList<>();
        team2 = new ArrayList<>();

        int len, start;
        team1.add(start = src.get(0).here);

        for (int i = 1; i < src.size(); i++) {
            len = 0;
            int tmp = src.get(i).from;
            while (tmp != start) {
                for (int j = i - 1; j > 0; j--) {
                    if (src.get(j).here == tmp) {
                        tmp = src.get(j).from;
                        len++;
                        break;
                    }
                }
            }

            if (len % 2 != 0) {
                team1.add(src.get(i).here);
            } else {
                team2.add(src.get(i).here);
            }
        }
    }

    public void check(ListGraph G) {
        int k = 1;
        for (ArrayList<Integer> t = team1; k < 3; k++, t = team2) {
            for (int i = 0; i < t.size(); i++) {
                for (int j = 0; j < t.size(); j++) {
                    if (G.isEdge(t.get(i), t.get(j))) {
                        System.out.println("No solution");
                        System.exit(1);
                    }
                }
            }
        }
    }
}


public class Mars {

    static boolean Greater(ArrayList<Integer> a, ArrayList<Integer> b) {
        for (int i = 0; i < a.size(); i++) { //Using a.size() cause a.size() alsways =
            if (a.get(i) < b.get(i)) {
                return false;
            } else if (a.get(i) > b.get(i)) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();

        char[][] matrix = new char[N][N];
        ListGraph plus = new ListGraph(N);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                char c = in.next().toCharArray()[0];
                matrix[i][j] = c;

                if (c == '+') {
                    plus.addEdge(i, j);
                }
            }
        }

        ArrayList<Rule> rules = new ArrayList<>();
        ArrayList<Integer> neutral = new ArrayList<>();
        int start = 0;

        while (!plus.allVisited()) {
            ArrayList<Node> path = plus.bfs(start++);
            if (path.size() == 1) {
                neutral.add(path.get(0).here);
            } else if (path.size() > 1) {
                rules.add(new Rule(path));
            }
        }


        ArrayList<ArrayList<Integer>> branch;
        ArrayList<Integer> vars = new ArrayList<>();
        for (int i = 0; i < rules.size(); i++) {
            vars.add(i);
        }

        ArrayList<Integer> fixed;
        fixed = new ArrayList<>();
        int comb = (int) Math.pow(2, rules.size());
        for (int i = 0; i < rules.size(); i++) {
            Rule r = rules.get(i);
            r.check(plus);
            if (r.team1.size() <= r.team2.size()) {
                comb /= 2;
                fixed.add(i);
                vars.remove(new Integer(i));
            }
        }

        branch = new ArrayList<>();
        int maxLen = rules.size() - fixed.size();

        ArrayList<Integer> finalExp = new ArrayList<>();
        if (maxLen == 0) {
            for (Rule r : rules) {
                finalExp.addAll(r.team1);
            }

            int size = finalExp.size();
            for (int i = 0; i < (N / 2) - size; i++) {
                finalExp.add(neutral.get(i));
            }

            Collections.sort(finalExp);
        } else {

            for (int i = 0; i < comb; i++) {
                ArrayList<Integer> temp = new ArrayList<>();
                String tmp = Integer.toBinaryString(i);
                while (tmp.length() < maxLen) {
                    tmp = "0" + tmp;
                }

                char[] combArr = tmp.toCharArray();
                for (int j = 0; j < combArr.length; j++) {
                    char c = combArr[j];
                    if (c == '0') {
                        temp.addAll(rules.get(vars.get(j)).team1);
                    } else {
                        temp.addAll(rules.get(vars.get(j)).team2);
                    }
                }

                for (int f = 0; f < rules.size(); f++) {
                    if (fixed.indexOf(f) != -1) {
                        temp.addAll(rules.get(f).team1);
                    }
                }


                int rem = temp.size();
                for (int k = 0; k < (N / 2) - rem; k++) {
                    temp.add(neutral.get(k));
                }

                Collections.sort(temp);
                branch.add(temp);
            }



        /* HERE COMES THE BUBBLE SORT!!! */
            boolean flag = true;
            ArrayList<Integer> temp;

            while (flag) {
                flag = false;
                for (int j = 0; j < branch.size() - 1; j++) {
                    if (Greater(branch.get(j), branch.get(j + 1))) {
                        temp = branch.get(j);
                        branch.set(j, branch.get(j + 1));
                        branch.set(j + 1, temp);
                        flag = true;
                    }
                }
            }
        /* It's over, you can look now */

            finalExp = branch.get(0);
        }

        for (int n : finalExp) {
            System.out.print((n + 1) + " ");
        }
    }
}
