/**
 Refer to
 https://leetcode.com/problems/ones-and-zeroes/
 In the computer world, use restricted resource you have to generate maximum benefit is 
 what we always want to pursue.
For now, suppose you are a dominator of m 0s and n 1s respectively. On the other hand, 
there is an array with strings consisting of only 0s and 1s.
Now your task is to find the maximum number of strings that you can form with given m 0s 
and n 1s. Each 0 and 1 can be used at most once.

Note:
The given numbers of 0s and 1s will both not exceed 100
The size of given string array won't exceed 600.

Example 1:
Input: Array = {"10", "0001", "111001", "1", "0"}, m = 5, n = 3
Output: 4
Explanation: This are totally 4 strings can be formed by the using of 5 0s and 3 1s, which are “10,”0001”,”1”,”0”

Example 2:
Input: Array = {"10", "0", "1"}, m = 1, n = 1
Output: 2
Explanation: You could form "10", but then you'd have nothing left. Better form "0" and "1".
*/

// Solution 1: Native DFS -> Style same as SolveKnapsack.java -> TLE
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DynamicProgramming/Document/Pattern_1_0_1_Knapsack/SolveKnapsack.java
// https://leetcode.com/problems/ones-and-zeroes/discuss/297910/Java-Brute-Force-With-Memoization
class Solution {
    public int findMaxForm(String[] strs, int m, int n) {
        return helper(strs, m, n, 0);
    }
    
    private int helper(String[] strs, int m, int n, int index) {
        if(index == strs.length) {
            return 0;
        }        
        String str = strs[index];
        int ones = 0;
        int zeros = 0;
        for(int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == '1') {
                ones++;
            } else {
                zeros++;
            }
        }
        int choose = 0;
        if(m >= zeros && n >= ones) {
            choose = 1 + helper(strs, m - zeros, n - ones, index + 1);
        }
        int notChoose = helper(strs, m, n, index + 1);
        return Math.max(choose, notChoose);
    }
}

// Solution 2: DFS + Memoization (Use 3D array or Map<String, Integer> to store index, m, n together as key)
// Refer to
// https://leetcode.com/problems/ones-and-zeroes/discuss/297910/Java-Brute-Force-With-Memoization
// https://leetcode.com/problems/ones-and-zeroes/discuss/376956/Very-Very-Easy-CPP-TOP-DOWN-with-Memoization
class Solution {
    public int findMaxForm(String[] strs, int m, int n) {
        Map<String, Integer> memo = new HashMap<String, Integer>();
        return helper(strs, m, n, 0, memo);
    }
    
    private int helper(String[] strs, int m, int n, int index, Map<String, Integer> memo) {
        String key = index + "_" + m + "_" + n;
        if(memo.containsKey(key)) {
            return memo.get(key);
        }
        if(index == strs.length) {
            return 0;
        }        
        String str = strs[index];
        int ones = 0;
        int zeros = 0;
        for(int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == '1') {
                ones++;
            } else {
                zeros++;
            }
        }
        int choose = 0;
        if(m >= zeros && n >= ones) {
            choose = 1 + helper(strs, m - zeros, n - ones, index + 1, memo);
        }
        int notChoose = helper(strs, m, n, index + 1, memo);
        int result = Math.max(choose, notChoose);
        memo.put(key, result);
        return result;
    }
}

// Solution 3: 


