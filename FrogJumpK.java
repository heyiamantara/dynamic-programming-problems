// ============================================================
//  PROBLEM: Frog Jump with K Distances (GFG / Striver DP #4)
//  DIFFICULTY: Medium
//  TOPIC: Dynamic Programming — Generalized Minimum Cost DP
// ============================================================
//
//  PROBLEM STATEMENT:
//  ------------------
//  A frog starts at stair 0 and wants to reach stair n-1.
//  The frog can jump anywhere from 1 step to K steps at a time.
//  Each stair has a height. The energy cost of a jump from
//  stair i to stair j is |height[i] - height[j]|.
//  Find the MINIMUM total energy to reach the last stair.
//
//  EXAMPLE:
//  Input : height = [10, 40, 30, 10, 20], K = 3
//  Output: 10
//  Best path: 0→3→4 = |10-10| + |10-20| = 0+10 = 10
//
// ============================================================
//  CORE INTUITION:
//  ---------------
//  Frog Jump (2 steps) extended to K steps.
//  At every stair i, the frog could have arrived from
//  ANY of the K stairs before it: i-1, i-2, ..., i-k
//  We try all valid jumps and pick the MINIMUM cost.
//
//  RECURRENCE:
//    dp[i] = min over j=1..K of:
//            dp[i-j] + |height[i] - height[i-j]|
//            (only when i-j >= 0)
//
//  HOW IT EXTENDS FROG JUMP (K=2):
//    K=2 → check only j=1 and j=2  (two fixed choices)
//    K=N → check j=1,2,...,K       (loop over all choices)
//
//  WHY CAN'T WE DO O(1) SPACE?
//  dp[i] depends on the last K values (not just last 2),
//  so we need at minimum O(K) space (circular window).
//
//  PATTERN: "Min cost, can jump up to K steps"
//  Similar Problems: Frog Jump (K=2), Jump Game I & II
// ============================================================


// ============================================================
//  APPROACH 1 — MEMOIZATION (Top-Down)
//  Time  : O(n × k) — n stairs, each tries k jumps
//  Space : O(n) + O(n) — memo array + recursion call stack
// ============================================================
//
// import java.util.Arrays;
//
// public class FrogJumpKMemoization {
//
//     public static int frogJump(int[] height, int k) {
//         int n = height.length;
//         int[] memo = new int[n];
//         Arrays.fill(memo, -1);
//         return solve(height, n - 1, k, memo);
//     }
//
//     private static int solve(int[] height, int i, int k, int[] memo) {
//         if (i == 0) return 0;              // at start, no cost
//         if (memo[i] != -1) return memo[i]; // already computed
//
//         int minCost = Integer.MAX_VALUE;
//
//         // Try all jumps of size 1 to k that land on stair i
//         for (int j = 1; j <= k; j++) {
//             if (i - j >= 0) {
//                 int jumpCost = solve(height, i - j, k, memo)
//                                + Math.abs(height[i] - height[i - j]);
//                 minCost = Math.min(minCost, jumpCost);
//             }
//         }
//
//         memo[i] = minCost;
//         return memo[i];
//     }
// }


// ============================================================
//  APPROACH 2 — TABULATION (Bottom-Up)
//  Time  : O(n × k) — outer loop n, inner loop k
//  Space : O(n) — dp array of size n
// ============================================================
//
// public class FrogJumpKTabulation {
//
//     public static int frogJump(int[] height, int k) {
//         int n = height.length;
//         int[] dp = new int[n];
//         dp[0] = 0;                                    // start, zero cost
//
//         for (int i = 1; i < n; i++) {
//             int minCost = Integer.MAX_VALUE;
//
//             // Try all jumps of size j=1..k landing on stair i
//             for (int j = 1; j <= k; j++) {
//                 if (i - j >= 0) {
//                     int jumpCost = dp[i - j]
//                                    + Math.abs(height[i] - height[i - j]);
//                     minCost = Math.min(minCost, jumpCost);
//                 }
//             }
//
//             dp[i] = minCost;
//         }
//
//         return dp[n - 1];
//     }
// }


// ============================================================
//  APPROACH 3 — SPACE OPTIMIZED (Best)
//  Time  : O(n × k) — same as tabulation
//  Space : O(k) — circular window of size k+1
//
//  KEY INSIGHT:
//  dp[i] depends on last K values, not just last 2.
//  So O(1) is impossible. Best we can do is O(k) using
//  a circular window: window[i % (k+1)] stores dp[i].
//  This avoids keeping the full O(n) dp array.
// ============================================================

public class FrogJumpK {

    public static int frogJump(int[] height, int k) {
        int n = height.length;

        // Circular window of size k+1 to store last k+1 dp values
        // window[i % (k+1)] = dp[i]
        int[] window = new int[k + 1];

        window[0] = 0; // dp[0] = 0, start at stair 0 with zero cost

        for (int i = 1; i < n; i++) {
            int minCost = Integer.MAX_VALUE;

            for (int j = 1; j <= k; j++) {
                if (i - j >= 0) {
                    // Retrieve dp[i-j] from its circular position
                    int prev     = window[(i - j) % (k + 1)];
                    int jumpCost = prev + Math.abs(height[i] - height[i - j]);
                    minCost      = Math.min(minCost, jumpCost);
                }
            }

            // Store dp[i] at its circular position
            window[i % (k + 1)] = minCost;
        }

        // dp[n-1] lives at position (n-1) % (k+1) in the window
        return window[(n - 1) % (k + 1)];
    }

    // ----------------------------------------------------------
    //  MAIN — Example Usage
    // ----------------------------------------------------------
    public static void main(String[] args) {
        int[] height1 = {10, 40, 30, 10, 20};
        System.out.println("Example 1 (K=3): " + frogJump(height1, 3)); // Expected: 10

        int[] height2 = {10, 20, 30, 10};
        System.out.println("Example 2 (K=2): " + frogJump(height2, 2)); // Expected: 20

        int[] height3 = {10, 50, 10, 50, 10};
        System.out.println("Example 3 (K=2): " + frogJump(height3, 2)); // Expected: 0

        int[] height4 = {10, 50, 10, 50, 10};
        System.out.println("Example 4 (K=4): " + frogJump(height4, 4)); // Expected: 0

        int[] height5 = {40, 10, 20, 70, 80, 10, 20, 70, 80, 60};
        System.out.println("Example 5 (K=4): " + frogJump(height5, 4)); // Expected: 40
    }
}