/* Memoization (Top-Down DP)

Time Complexity: O(n)
Space Complexity: O(n) + O(n recursion stack)

import java.util.*;

class Solution {

    private static int solve(int n, int[] dp) {
        if (n <= 2) {
            return n;
        }
        if (dp[n] != -1) {
            return dp[n];
        }
        dp[n] = solve(n - 1, dp) + solve(n - 2, dp);
        return dp[n];
    }

    public static int climbStairs(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);
        return solve(n, dp);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        System.out.println(climbStairs(n));
        sc.close();
    }
}

*/


/* Tabulation (Bottom-Up DP)

Time Complexity: O(n)
Space Complexity: O(n)

import java.util.*;

class Solution {

    public static int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }

        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        System.out.println(climbStairs(n));
        sc.close();
    }
}

*/


/* Space Optimized Version

Time Complexity: O(n)
Space Complexity: O(1)

*/

import java.util.*;

class Solution {

    public static int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }

        int prev2 = 1;
        int prev1 = 2;

        for (int i = 3; i <= n; i++) {
            int current = prev1 + prev2;
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        System.out.println(climbStairs(n));
        sc.close();
    }
}