// ============================================================
//  PROBLEM: Frog Jump (GFG / Striver DP Series #3)
//  DIFFICULTY: Medium
//  TOPIC: Dynamic Programming — Minimum Cost Linear DP
// ============================================================
//
//  PROBLEM STATEMENT:
//  ------------------
//  A frog starts at stair 0 and wants to reach stair n-1.
//  The frog can jump 1 step or 2 steps at a time.
//  Each stair has a height. The energy cost of a jump is the
//  absolute difference in heights between the two stairs.
//  Find the MINIMUM total energy to reach the last stair.
//
//  EXAMPLE:
//  Input : height = [10, 20, 30, 10]
//  Output: 20
//  Best path: 0→1→3 = |10-20| + |20-10| = 10+10 = 20
//
// ============================================================
//  CORE INTUITION:
//  ---------------
//  At every stair i, the frog could have arrived from:
//    → i-1 (one step back)  cost = dp[i-1] + |height[i] - height[i-1]|
//    → i-2 (two steps back) cost = dp[i-2] + |height[i] - height[i-2]|
//  We pick the MINIMUM of the two.
//
//  RECURRENCE:
//    dp[i] = min(
//              dp[i-1] + |height[i] - height[i-1]|,
//              dp[i-2] + |height[i] - height[i-2]|
//            )
//
//  KEY DIFFERENCE vs Climbing Stairs:
//    Climbing Stairs → COUNT paths  → ADD subproblems
//    Frog Jump       → MINIMIZE cost → MIN of subproblems
//
//  PATTERN: "Minimum cost to reach destination, 1 or 2 step jumps"
//  Similar Problems: Min Cost Climbing Stairs, Frog Jump K Steps
// ============================================================


// ============================================================
//  APPROACH 1 — MEMOIZATION (Top-Down)
//  Time  : O(n) — each stair solved once, memoized after
//  Space : O(n) + O(n) — memo array + recursion call stack
// ============================================================
//
// import java.util.Arrays;
//
// public class FrogJumpMemoization {
//
//     public static int frogJump(int[] height) {
//         int n = height.length;
//         int[] memo = new int[n];
//         Arrays.fill(memo, -1);             // -1 = not yet computed
//         return solve(height, n - 1, memo);
//     }
//
//     // Returns minimum energy to reach stair i from stair 0
//     private static int solve(int[] height, int i, int[] memo) {
//         if (i == 0) return 0;              // at start, zero cost
//         if (i == 1) return Math.abs(height[1] - height[0]);
//
//         if (memo[i] != -1) return memo[i]; // already computed
//
//         // OPTION 1: came from one step back
//         int oneStep = solve(height, i - 1, memo)
//                       + Math.abs(height[i] - height[i - 1]);
//
//         // OPTION 2: came from two steps back
//         int twoStep = solve(height, i - 2, memo)
//                       + Math.abs(height[i] - height[i - 2]);
//
//         memo[i] = Math.min(oneStep, twoStep);
//         return memo[i];
//     }
// }


// ============================================================
//  APPROACH 2 — TABULATION (Bottom-Up)
//  Time  : O(n) — single pass through all stairs
//  Space : O(n) — dp array of size n
// ============================================================
//
// public class FrogJumpTabulation {
//
//     public static int frogJump(int[] height) {
//         int n = height.length;
//         if (n == 1) return 0;
//
//         int[] dp = new int[n];
//         dp[0] = 0;                                    // start, zero cost
//         dp[1] = Math.abs(height[1] - height[0]);     // only one way to reach stair 1
//
//         for (int i = 2; i < n; i++) {
//             int oneStep = dp[i - 1] + Math.abs(height[i] - height[i - 1]);
//             int twoStep = dp[i - 2] + Math.abs(height[i] - height[i - 2]);
//             dp[i] = Math.min(oneStep, twoStep);
//         }
//
//         return dp[n - 1];
//     }
// }


// ============================================================
//  APPROACH 3 — SPACE OPTIMIZED (Best)
//  Time  : O(n) — single pass
//  Space : O(1) — only two variables, no array needed
//
//  KEY INSIGHT:
//  dp[i] only needs dp[i-1] and dp[i-2].
//  Replace the array with two rolling variables prev1 and prev2.
// ============================================================

public class FrogJump {

    public static int frogJump(int[] height) {
        int n = height.length;

        if (n == 1) return 0;

        int prev2 = 0;                                // dp[i-2] → stair 0, zero cost
        int prev1 = Math.abs(height[1] - height[0]); // dp[i-1] → stair 1

        for (int i = 2; i < n; i++) {
            int oneStep = prev1 + Math.abs(height[i] - height[i - 1]);
            int twoStep = prev2 + Math.abs(height[i] - height[i - 2]);
            int current = Math.min(oneStep, twoStep);
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }

    // ----------------------------------------------------------
    //  MAIN — Example Usage
    // ----------------------------------------------------------
    public static void main(String[] args) {
        int[] height1 = {10, 20, 30, 10};
        System.out.println("Example 1: " + frogJump(height1)); // Expected: 20

        int[] height2 = {10, 50, 10};
        System.out.println("Example 2: " + frogJump(height2)); // Expected: 0

        int[] height3 = {10, 20, 10, 30, 20};
        System.out.println("Example 3: " + frogJump(height3)); // Expected: 20

        int[] height4 = {10};
        System.out.println("Example 4: " + frogJump(height4)); // Expected: 0

        int[] height5 = {30, 10, 60, 10, 60, 50};
        System.out.println("Example 5: " + frogJump(height5)); // Expected: 40
    }
}