/**
 Refer to
 https://www.geeksforgeeks.org/cutting-a-rod-dp-13/
 Given a rod of length n inches and an array of prices that contains prices of all pieces 
 of size smaller than n. Determine the maximum value obtainable by cutting up the rod and 
 selling the pieces. For example, if length of the rod is 8 and the values of different 
 pieces are given as following, then the maximum obtainable value is 22 (by cutting in 
 two pieces of lengths 2 and 6)

length   | 1   2   3   4   5   6   7   8  
--------------------------------------------
price    | 1   5   8   9  10  17  17  20

And if the prices are as following, then the maximum obtainable value is 24 (by cutting 
in eight pieces of length 1)

length   | 1   2   3   4   5   6   7   8  
--------------------------------------------
price    | 3   5   8   9  10  17  17  20

Output:
Maximum Obtainable Value is 22
*/


/**
 A naive solution for this problem is to generate all configurations of different pieces and 
 find the highest priced configuration. This solution is exponential in term of time complexity. 
 Let us see how this problem possesses both important properties of a Dynamic Programming (DP) 
 Problem and can efficiently solved using Dynamic Programming.

1) Optimal Substructure:
We can get the best price by making a cut at different positions and comparing the values 
obtained after a cut. We can recursively call the same function for a piece obtained after a cut.

Let cutRod(n) be the required (best possible price) value for a rod of length n. cutRod(n) can 
be written as following.

cutRod(n) = max(price[i] + cutRod(n-i-1)) for all i in {0, 1 .. n-1}

2) Overlapping Subproblems
Following is simple recursive implementation of the Rod Cutting problem. The implementation 
simply follows the recursive structure mentioned above.

Considering the above implementation, following is recursion tree for a Rod of length 4.

cR() ---> cutRod() 

                          cR(4)
                  /        /           
                 /        /              
             cR(3)       cR(2)     cR(1)   cR(0)
            /  |         /         |
           /   |        /          |  
      cR(2) cR(1) cR(0) cR(1) cR(0) cR(0)
     /        |          |
    /         |          |   
  cR(1) cR(0) cR(0)      cR(0)
   /
 /
CR(0)

In the above partial recursion tree, cR(2) is being solved twice. We can see that there are many 
subproblems which are solved again and again. Since same suproblems are called again, this problem 
has Overlapping Subprolems property. So the Rod Cutting problem has both properties (see this and this) 
of a dynamic programming problem. Like other typical Dynamic Programming(DP) problems, recomputations 
of same subproblems can be avoided by constructing a temporary array val[] in bottom up manner.
*/

// Solution 1: Native DFS
// Refer to
// https://web.stanford.edu/class/archive/cs/cs161/cs161.1168/lecture12.pdf
// https://www.geeksforgeeks.org/cutting-a-rod-dp-13/
class Solution {
    public int cutRod(int[] prices, int[] length, int n) {
        if (n <= 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            max = Math.max(max, prices[i] + cutRod(prices, length, n - length[i]));
        }
        return max;
    }
    
    // test with below:
    // int prices[] = {1, 5, 8, 9, 10, 17, 17, 20}; 
    // int length[] = {1, 2, 3, 4, 5, 6, 7, 8};
}

// Solution 2: Top down DP
// Refer to
// https://web.stanford.edu/class/archive/cs/cs161/cs161.1168/lecture12.pdf
// https://www.geeksforgeeks.org/cutting-a-rod-dp-13/
// https://www.codesdope.com/course/algorithms-rod-cutting/
// Style 1:
class Solution {
    public int topDownCutRod(int[] prices, int[] length, int n) {
        if (n <= 0) {
            return 0;
        }
        // memo store the maximum profit when current rod length as n
        Integer[] memo = new Integer[n + 1];
        return helper(prices, length, n, memo, n);
    }

    private int helper(int[] prices, int[] length, int n, Integer[] memo, int leftLen) {
        int result = 0;
        if (memo[n] != null) {
            return memo[n];
        }
        if (leftLen == 0) {
            return result;
        }
        for (int i = 0; i < n; i++) {
            result = Math.max(result, prices[i] + helper(prices, length, n - length[i], memo, n - length[i]));
        }
        memo[n] = result;
        return result;
    }
}

// Style 2: Compare to Style 1 remove one duplicate parameter, add limitation
// as 'if(leftLen >= length[i])' to make sure ArrayIndexOutOfBound issue not happen
class Solution {
    public int topDownCutRod(int[] prices, int[] length, int n) {
        if (n <= 0) {
            return 0;
        }
        // memo store the maximum profit when current rod length as n
        Integer[] memo = new Integer[n + 1];
        return helper(prices, length, n, memo);
    }

    private int helper(int[] prices, int[] length, int leftLen, Integer[] memo) {
        int result = 0;
        if (leftLen == 0) {
            return result;
        }
        if (memo[leftLen] != null) {
            return memo[leftLen];
        }
        for (int i = 0; i < prices.length; i++) {
            if (leftLen >= length[i]) {
                result = Math.max(result, prices[i] + helper(prices, length, leftLen - length[i], memo));
            }
        }
        memo[leftLen] = result;
        return result;
    }
}

// Solution 3: Bottom-up Solution
// Refer to
// 
