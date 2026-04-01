// ============================================================
//  PROBLEM: House Robber (LeetCode #198)
//  DIFFICULTY: Medium
//  TOPIC: Dynamic Programming — Linear DP / Kadane-style
// ============================================================
//
//  PROBLEM STATEMENT:
//  ------------------
//  You are a robber planning to rob houses along a street.
//  Each house has some amount of money stored.
//  The only constraint: you CANNOT rob two adjacent houses.
//  Given an array nums[], return the maximum money you can rob.
//
//  EXAMPLE:
//  Input : [2, 7, 9, 3, 1]
//  Output: 12  (rob index 0→2→4 : 2+9+1=12)
//
// ============================================================
//  CORE INTUITION:
//  ---------------
//  At every house i, you have exactly TWO choices:
//    1. ROB house i   → nums[i] + best up to house i-2
//    2. SKIP house i  → best up to house i-1
//
//  RECURRENCE:
//    dp[i] = max(dp[i-1], dp[i-2] + nums[i])
//
//  PATTERN: "Cannot pick adjacent elements"
//  → Always think dp[i-1] vs dp[i-2] + current
//  Similar Problems: House Robber II, Max Sum Non-Adjacent Elements
// ============================================================


// ============================================================
//  APPROACH 1 — MEMOIZATION (Top-Down)
//  Time  : O(n) — each index solved once, cached after
//  Space : O(n) + O(n) — memo array + recursion call stack
// ============================================================
//
// import java.util.Arrays;
//
// public class HouseRobberMemoization {
//
//     public static int rob(int[] nums) {
//         int[] memo = new int[nums.length];
//         Arrays.fill(memo, -1);               // -1 = not yet computed
//         return solve(nums, nums.length - 1, memo);
//     }
//
//     // solve(i) = max money robbing from house 0..i
//     private static int solve(int[] nums, int i, int[] memo) {
//
//         if (i < 0)  return 0;                // no houses left
//         if (i == 0) return nums[0];          // only one house
//
//         if (memo[i] != -1) return memo[i];   // already computed
//
//         // CHOICE 1: skip house i → carry best from i-1
//         int skip = solve(nums, i - 1, memo);
//
//         // CHOICE 2: rob house i → must skip i-1, go back to i-2
//         int rob  = solve(nums, i - 2, memo) + nums[i];
//
//         memo[i] = Math.max(skip, rob);
//         return memo[i];
//     }
// }


// ============================================================
//  APPROACH 2 — TABULATION (Bottom-Up)
//  Time  : O(n) — single pass through the array
//  Space : O(n) — dp array of size n
// ============================================================
//
// public class HouseRobberTabulation {
//
//     public static int rob(int[] nums) {
//         int n = nums.length;
//         if (n == 1) return nums[0];
//
//         int[] dp = new int[n];
//
//         dp[0] = nums[0];                          // only house 0 → rob it
//         dp[1] = Math.max(nums[0], nums[1]);       // two houses → rob bigger
//
//         for (int i = 2; i < n; i++) {
//             int skip = dp[i - 1];                 // skip house i
//             int rob  = dp[i - 2] + nums[i];       // rob house i
//             dp[i]    = Math.max(skip, rob);
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
//  dp[i] only ever looks at dp[i-1] and dp[i-2].
//  So we replace the whole array with two rolling variables.
//
//    prev2 = dp[i-2]   prev1 = dp[i-1]
//    current = max(prev1, prev2 + nums[i])
//    then slide: prev2 = prev1, prev1 = current
// ============================================================

public class HouseRobber {

    public static int rob(int[] nums) {
        int n = nums.length;

        if (n == 1) return nums[0];

        int prev2 = nums[0];                    // best answer two houses behind
        int prev1 = Math.max(nums[0], nums[1]); // best answer one house behind

        for (int i = 2; i < n; i++) {
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
        int[] nums1 = {2, 7, 9, 3, 1};
        System.out.println("Example 1: " + rob(nums1)); // Expected: 12

        int[] nums2 = {1, 2, 3, 1};
        System.out.println("Example 2: " + rob(nums2)); // Expected: 4

        int[] nums3 = {2, 1};
        System.out.println("Example 3: " + rob(nums3)); // Expected: 2

        int[] nums4 = {5};
        System.out.println("Example 4: " + rob(nums4)); // Expected: 5

        int[] nums5 = {2, 7, 9, 3, 1, 8, 2};
        System.out.println("Example 5: " + rob(nums5)); // Expected: 19
    }
}