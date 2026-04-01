// ============================================================
//  PROBLEM: Climbing Stairs (LeetCode #70)
//  DIFFICULTY: Easy
//  TOPIC: Dynamic Programming — Fibonacci / Counting DP
// ============================================================
//
//  PROBLEM STATEMENT:
//  ------------------
//  You are climbing a staircase with n steps.
//  Each time you can climb either 1 step or 2 steps.
//  In how many DISTINCT WAYS can you climb to the top?
//
//  EXAMPLE:
//  Input : n = 2  → Output: 2   ([1+1], [2])
//  Input : n = 3  → Output: 3   ([1+1+1], [1+2], [2+1])
//  Input : n = 5  → Output: 8
//
// ============================================================
//  CORE INTUITION:
//  ---------------
//  To reach step n, the last jump was either:
//    → 1 step from step n-1  → all ways to reach n-1
//    → 2 steps from step n-2 → all ways to reach n-2
//  Total ways = ways(n-1) + ways(n-2)
//
//  THIS IS FIBONACCI IN DISGUISE:
//  n:    1  2  3  4  5  6   7   8
//  ways: 1  2  3  5  8  13  21  34
//
//  RECURRENCE:
//    dp[i] = dp[i-1] + dp[i-2]
//
//  KEY DIFFERENCE vs Frog Jump:
//    Frog Jump       → MINIMIZE cost  → use min(...)
//    Climbing Stairs → COUNT paths    → use sum (+)
//    No height/cost involved here — all steps are equal.
//
//  PATTERN: "How many distinct ways, 1 or 2 steps"
//  → Pure counting DP = Fibonacci pattern
//  Similar Problems: Frog Jump (count variant),
//  Decode Ways, Tribonacci, Min Cost Climbing Stairs
// ============================================================


// ============================================================
//  APPROACH 1 — MEMOIZATION (Top-Down)
//  Time  : O(n) — each step solved once, cached after
//  Space : O(n) + O(n) — memo array + recursion call stack
// ============================================================
//
// import java.util.Arrays;
//
// public class ClimbingStairsMemoization {
//
//     public static int climbStairs(int n) {
//         int[] memo = new int[n + 1];
//         Arrays.fill(memo, -1);
//         return solve(n, memo);
//     }
//
//     // Returns number of distinct ways to reach step n
//     private static int solve(int n, int[] memo) {
//         if (n == 0) return 1;              // reached ground exactly → valid path
//         if (n == 1) return 1;              // only one way: take 1 step
//
//         if (memo[n] != -1) return memo[n]; // already computed
//
//         // Ways to reach n = ways from n-1 (1-step jump)
//         //                 + ways from n-2 (2-step jump)
//         memo[n] = solve(n - 1, memo) + solve(n - 2, memo);
//         return memo[n];
//     }
// }


// ============================================================
//  APPROACH 2 — TABULATION (Bottom-Up)
//  Time  : O(n) — single pass from step 2 to step n
//  Space : O(n) — dp array of size n+1
// ============================================================
//
// public class ClimbingStairsTabulation {
//
//     public static int climbStairs(int n) {
//         if (n == 1) return 1;
//
//         int[] dp = new int[n + 1];
//         dp[0] = 1;  // 1 way to "be at" ground (start position)
//         dp[1] = 1;  // only one way to reach step 1 → take a single 1-step jump
//
//         for (int i = 2; i <= n; i++) {
//             // From step i-1: take 1 step → adds dp[i-1] ways
//             // From step i-2: take 2 steps → adds dp[i-2] ways
//             dp[i] = dp[i - 1] + dp[i - 2];
//         }
//
//         return dp[n];
//     }
// }


// ============================================================
//  APPROACH 3 — SPACE OPTIMIZED (Best)
//  Time  : O(n) — single pass
//  Space : O(1) — only two variables, no array needed
//
//  KEY INSIGHT:
//  dp[i] only needs dp[i-1] and dp[i-2].
//  Replace the whole array with two rolling variables.
//  This is the classic Fibonacci space optimization trick.
// ============================================================

public class ClimbingStairs {

    public static int climbStairs(int n) {

        if (n == 1) return 1;

        int prev2 = 1; // dp[0] = 1 (ways to be at ground)
        int prev1 = 1; // dp[1] = 1 (ways to reach step 1)

        for (int i = 2; i <= n; i++) {
            int current = prev1 + prev2; // Fibonacci addition
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }

    // ----------------------------------------------------------
    //  MAIN — Example Usage
    // ----------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("n=1:  " + climbStairs(1));  // Expected: 1
        System.out.println("n=2:  " + climbStairs(2));  // Expected: 2
        System.out.println("n=3:  " + climbStairs(3));  // Expected: 3
        System.out.println("n=4:  " + climbStairs(4));  // Expected: 5
        System.out.println("n=5:  " + climbStairs(5));  // Expected: 8
        System.out.println("n=6:  " + climbStairs(6));  // Expected: 13
        System.out.println("n=10: " + climbStairs(10)); // Expected: 89
        System.out.println("n=45: " + climbStairs(45)); // Expected: 1836311903
    }
}