/**
 Refer to
 https://www.geeksforgeeks.org/maximum-product-cutting-dp-36/
 https://algorithms.tutorialhorizon.com/dynamic-programming-maximum-product-cutting-problem/
 Given a rope of length n meters, cut the rope in different parts of integer lengths in a way 
 that maximizes product of lengths of all parts. You must make at least one cut. Assume that 
 the length of rope is more than 2 meters.
Examples:

Input: n = 2
Output: 1 (Maximum obtainable product is 1*1)

Input: n = 3
Output: 2 (Maximum obtainable product is 1*2)

Input: n = 4
Output: 4 (Maximum obtainable product is 2*2)

Input: n = 5
Output: 6 (Maximum obtainable product is 2*3)

Input: n = 10
Output: 36 (Maximum obtainable product is 3*3*4)
*/

// Solution 1: Native DFS
// Refer to
// https://algorithms.tutorialhorizon.com/dynamic-programming-maximum-product-cutting-problem/
/**
 Approach:
Using Recursion:
This problem is similar to “Rod Cutting Problem“.
Check the products of all possible cuts can be made in the rope and return the maximum product.
Since for every length there are two options, either a cut to be made or not. Solve the problem 
for both options and choose maximum.
After Making the cut the further options are , Either this cut will produce the max product OR 
we need to make further cuts
Recursive Equation:
Let MPC(n) is the maximum product for length n.
MPC(n) = max(i*(n-i), i*MPC(n-i)) for all (i=1,2…..n) (After Making the cut the further options are, 
Either this cut will produce the max product OR we need to make further cuts).
Time Complexity: O(2^n).
*/
class Solution {
    public int maxProdutRecursion(int n) {
        if (n == 0 || n == 1) {
            return 0;
        }
        int max = 0;
        for (int i = 1; i < n; i++) {
            // Since for every length there are two options, either a cut to be made or not. 
            // Solve the problem for both options and choose maximum
            // No more cut: i * (n - i)
            // Need further cut: i * maxProdutRecursion(n - i))
            max = Math.max(max, Math.max(i * (n - i), i * maxProdutRecursion(n - i)));
        }
        return max;
    }
}

// Solution 2: Bottom-up DP
class Solution {
    public int bottomUpMaxProdutRecursion(int n) {
        if (n == 0 || n == 1) {
            return 0;
        }
        int[] dp = new int[n + 1];
        dp[1] = 1;
        for (int i = 2; i < n + 1; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = 1; j <= i; j++) {
                max = Math.max(max, Math.max(j * (i - j), j * bottomUpMaxProdutRecursion(i - j)));
            }
            dp[i] = max;
        }
        return dp[n];
    }
}


