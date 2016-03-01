package Dividers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Dividers {
    static void findDividers(ArrayList<Long> divs, long n, long d, long g) {
        divs.add(d);
        if (d != g) {
            d++;
            while (d * d <= n && n % d != 0)
                d++;

            if (d * d <= n)
                findDividers(divs, n, d, n / d);
            divs.add(g);
        }
    }

    static boolean isPrime(long n) {
        for (int i = 2; i * i <= n; i++)
            if (n % i == 0)
                return false;
        return true;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        long num = in.nextLong();

        ArrayList<Long> divs = new ArrayList<>();
        findDividers(divs, num, 1, num);
        Collections.reverse(divs);

        System.out.println("graph {");
        for (long div : divs) {
            System.out.println("\t" + div);
        }

        for (long n : divs) {
            for (long x : divs) {
                if ((n != x) && (n % x == 0) && isPrime(n / x))
                    System.out.println("\t" + n + " -- " + x);
            }
        }

        System.out.println("}");
    }
}
