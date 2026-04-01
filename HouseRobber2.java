// ============================================================
//  PROBLEM: House Robber II (LeetCode #213)
//  DIFFICULTY: Medium
//  TOPIC: Dynamic Programming — Circular Linear DP
// ============================================================
//
//  PROBLEM STATEMENT:
//  ------------------
//  Same as House Robber I, but houses are arranged in a CIRCLE.
//  This means house 0 and house n-1 are adjacent.
//  You cannot rob both the first and the last house.
//  Return the maximum money you can rob.
//
//  EXAMPLE:
//  Input : [2, 3, 2]
//  Output: 3  (can't rob index 0 and 2 together, best = rob index 1)
//
//  Input : [1, 2, 3, 1]
//  Output: 4  (rob index 0 + index 2 = 1+3 = 4)
//
// ============================================================
//  CORE INTUITION:
//  ---------------
//  Since house 0 and house n-1 cannot BOTH be robbed,
//  at least one of them is always skipped in any valid solution.
//  This gives us exactly TWO scenarios:
//    Scenario A: Rob from house 0 to house n-2 (exclude last)
//    Scenario B: Rob from house 1 to house n-1 (exclude first)
//
//  Answer = max(Scenario A, Scenario B)
//  Each scenario is just House Robber I on a subarray!
//
//  RECURRENCE (per scenario — same as HR1):
//    dp[i] = max(dp[i-1], dp[i-2] + nums[i])
//
//  PATTERN: "Circular array + cannot pick adjacent"
//  → Break the circle by solving two linear subproblems
//  Similar Problems: House Robber I, Max Sum Circular Subarray
// ============================================================


// ============================================================
//  APPROACH 1 — MEMOIZATION (Top-Down)
//  Time  : O(n) — two passes of HR1, each O(n)
//  Space : O(n) + O(n) — two memo arrays + recursion call stack
// ============================================================
//
// import java.util.Arrays;
//
// public class HouseRobber2Memoization {
//
//     public static int rob(int[] nums) {
//         int n = nums.length;
//         if (n == 1) return nums[0];
//         if (n == 2) return Math.max(nums[0], nums[1]);
//
//         // Scenario A: exclude last house (index 0 to n-2)
//         int[] memo1 = new int[n];
//         Arrays.fill(memo1, -1);
//         int scenarioA = solve(nums, 0, n - 2, memo1);
//
//         // Scenario B: exclude first house (index 1 to n-1)
//         int[] memo2 = new int[n];
//         Arrays.fill(memo2, -1);
//         int scenarioB = solve(nums, 1, n - 1, memo2);
//
//         return Math.max(scenarioA, scenarioB);
//     }
//
//     // HR1 memoization restricted to nums[start..end]
//     private static int solve(int[] nums, int start, int end, int[] memo) {
//         if (end < start)  return 0;
//         if (end == start) return nums[start];
//
//         if (memo[end] != -1) return memo[end];   // already computed
//
//         int skip = solve(nums, start, end - 1, memo);
//         int rob  = solve(nums, start, end - 2, memo) + nums[end];
//
//         memo[end] = Math.max(skip, rob);
//         return memo[end];
//     }
// }


// ============================================================
//  APPROACH 2 — TABULATION (Bottom-Up)
//  Time  : O(n) — two linear passes
//  Space : O(n) — dp array of size n for each range
// ============================================================
//
// public class HouseRobber2Tabulation {
//
//     public static int rob(int[] nums) {
//         int n = nums.length;
//         if (n == 1) return nums[0];
//         if (n == 2) return Math.max(nums[0], nums[1]);
//
//         return Math.max(
//             robRange(nums, 0, n - 2),   // Scenario A: skip last house
//             robRange(nums, 1, n - 1)    // Scenario B: skip first house
//         );
//     }
//
//     // HR1 tabulation restricted to nums[start..end]
//     private static int robRange(int[] nums, int start, int end) {
//         int rangeLen = end - start + 1;
//         if (rangeLen == 1) return nums[start];
//
//         int[] dp = new int[rangeLen];
//         dp[0] = nums[start];
//         dp[1] = Math.max(nums[start], nums[start + 1]);
//
//         for (int i = 2; i < rangeLen; i++) {
//             int skip = dp[i - 1];
//             int rob  = dp[i - 2] + nums[start + i]; // map dp index → nums index
//             dp[i]    = Math.max(skip, rob);
//         }
//
//         return dp[rangeLen - 1];
//     }
// }


// ============================================================
//  APPROACH 3 — SPACE OPTIMIZED (Best)
//  Time  : O(n) — two O(n) passes, no arrays
//  Space : O(1) — only two variables per pass
//
//  KEY INSIGHT:
//  Run HR1 space-optimized solution twice:
//    → Once on range [0, n-2]  (exclude last house)
//    → Once on range [1, n-1]  (exclude first house)
//  Return the max of both results.
// ============================================================

public class HouseRobber2 {

    public static int rob(int[] nums) {
        int n = nums.length;

        if (n == 1) return nums[0];
        if (n == 2) return Math.max(nums[0], nums[1]);

        return Math.max(
            robRange(nums, 0, n - 2),   // Scenario A: exclude last house
            robRange(nums, 1, n - 1)    // Scenario B: exclude first house
        );
    }

    // HR1 space-optimized solver restricted to nums[start..end]
    private static int robRange(int[] nums, int start, int end) {
        int prev2 = nums[start];                          // dp[i-2]
        int prev1 = Math.max(nums[start], nums[start + 1]); // dp[i-1]

        for (int i = start + 2; i <= end; i++) {
            int current = Math.max(prev1, prev2 + nums[i]);
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }

    // ----------------------------------------------------------
    //  MAIN — Example Usage
    // ----------------------------------------------------------
    public static void main(String[] args) {
        int[] nums1 = {2, 3, 2};
        System.out.println("Example 1: " + rob(nums1)); // Expected: 3

        int[] nums2 = {1, 2, 3, 1};
        System.out.println("Example 2: " + rob(nums2)); // Expected: 4

        int[] nums3 = {1, 2, 3};
        System.out.println("Example 3: " + rob(nums3)); // Expected: 3

        int[] nums4 = {5};
        System.out.println("Example 4: " + rob(nums4)); // Expected: 5

        int[] nums5 = {2, 7, 9, 3, 1};
        System.out.println("Example 5: " + rob(nums5)); // Expected: 11
    }
}