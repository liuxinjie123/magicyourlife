package com.miracle;

import java.util.Scanner;

public class Calphabet {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();
        int[] dp = new int[str.length()];
        dp[0] = 1;
        int ans = 1;
        for (int i=1; i<str.length(); i++) {
            dp[i] = 1;
            for (int j=0; j<i; j++) {
                if (str.charAt(i) > str.charAt(j)) {
                    dp[i] = Math.max(dp[i], dp[j]+1);
                }
            }
            ans = Math.max(ans, dp[i]);
        }
        System.out.println(26 - ans);
        return;
    }
}
