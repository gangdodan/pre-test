package com.pretest.solution.solution02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[] coins, dp;
    static int amount;

    public static int countCoinSum() {
        dp[0] = 1;
        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] += dp[i - coin];
            }
        }
        return dp[amount];
    }

    public static void main(String[] args) throws IOException {
        amount = Integer.parseInt(br.readLine());
        String input = br.readLine();
        String[] arr = input.split(" ");

        dp = new int[amount + 1];
        coins = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            coins[i] = Integer.parseInt(arr[i]);
        }

        System.out.println("result = " + countCoinSum());
    }
}
