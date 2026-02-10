package com.bajaj.bfhl.bajajqualifier1.util;

import java.util.ArrayList;
import java.util.List;

public class MathUtil {



    public static List<Integer> fibonacci(int n) {
        List<Integer> ans = new ArrayList<>();
        if (n == 0) return ans;
        int a = 0, b = 1;
        for (int i = 0; i < n; i++) {
            ans.add(a);
            int c = a + b;
            a = b;
            b = c;
        }
        return ans;
    }


    public static boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }


    public static int gcd(int a, int b) {
        if (b == 0) return Math.abs(a);
        return gcd(b, a % b);
    }


    public static int hcf(List<Integer> list) {
        int result = list.get(0);
        for (int n : list) {
            result = gcd(result, n);
        }
        return result;
    }


    public static int lcm(List<Integer> list) {
        int result = list.get(0);
        for (int n : list) {
            result = Math.abs(result * n) / gcd(result, n);
        }
        return result;
    }
}
