/**
 Refer to
 https://www.geeksforgeeks.org/partition-a-set-into-two-subsets-such-that-the-difference-of-subset-sums-is-minimum/
 Partition a set into two subsets such that the difference of subset sums is minimum
 Given a set of integers, the task is to divide it into two sets S1 and S2 such that 
 the absolute difference between their sums is minimum.
 If there is a set S with n elements, then if we assume Subset1 has m elements, Subset2 
 must have n-m elements and the value of abs(sum(Subset1) – sum(Subset2)) should be minimum.

 Example:
 Input:  arr[] = {1, 6, 11, 5} 
 Output: 1
 Explanation:
 Subset1 = {1, 5, 6}, sum of Subset1 = 12 
 Subset2 = {11}, sum of Subset2 = 11
*/

// Solution 1: Native DFS
// Refer to
// https://www.geeksforgeeks.org/partition-a-set-into-two-subsets-such-that-the-difference-of-subset-sums-is-minimum/
class Solution {
    // Function to find the minimum sum 
    public int helper(int arr[], int i, int sumCalculated, int sumTotal) {
        // If we have reached last element. 
        // Sum of one subset is sumCalculated, sum of other subset is sumTotal- 
        // sumCalculated. Return absolute difference of two sums. 
        if (i == arr.length - 1) {
            return Math.abs((sumTotal - sumCalculated) - sumCalculated);
        }
        // For every item arr[i], we have two choices 
        // (1) We do not include it first set 
        // (2) We include it in first set 
        // We return minimum of two choices 
        return Math.min(helper(arr, i + 1, sumCalculated + arr[i + 1], sumTotal), 
                        helper(arr, i + 1, sumCalculated, sumTotal));
    }

    // Returns minimum possible difference between sums of two subsets 
    public int findMin(int arr[], int n) {
        // Compute total sum of elements 
        int sumTotal = 0;
        for (int i = 0; i < n; i++) {
            sumTotal += arr[i];
        }
        // Compute result using recursive function 
        return findMinRec(arr, 0, 0, sumTotal);
    }
}

// Solution 2: Native DFS (Another style)
// Refer to
// https://www.techiedelight.com/minimum-sum-partition-problem/
class MinPartition {
    public int findMin(int arr[]) {
        return helper(arr, arr.length - 1, 0, 0);
    }
 
    // Partition the set S into two subsets S1, S2 such that the
    // difference between the sum of elements in S1 and the sum
    // of elements in S2 is minimized
    public static int helper(int[] S, int n, int S1, int S2) {
        // base case: if list becomes empty, return the absolute
        // difference between two sets
        if (n < 0) {
            return Math.abs(S1 - S2);
        }
        // Case 1. include current item in the subset S1 and recur
        // for remaining items (n - 1)
        int include = helper(S, n - 1, S1 + S[n], S2);
        // Case 2. exclude current item from subset S1 and recur for
        // remaining items (n - 1)
        int exclude = helper(S, n - 1, S1, S2 + S[n]);
        return Math.min(include, exclude);
    }
}

// Solution 3: Top-Down DP (DFS + Memoization)
// Refer to
// https://www.techiedelight.com/minimum-sum-partition-problem/
class MinPartition {
    public int findMin(int arr[]) {
        Map<String, Integer> memo = new HashMap<String, Integer>();
        return helper(arr, arr.length - 1, 0, 0, memo);
    }    
 
    // Partition the set S into two subsets S1, S2 such that the
    // difference between the sum of elements in S1 and the sum
    // of elements in S2 is minimized
    public static int minPartition(int[] S, int n, int S1, int S2, Map<String, Integer> memo) {
        // base case: if list becomes empty, return the absolute
        // difference between two sets
        if(n < 0) {
            return Math.abs(S1 - S2);
        }
        // construct a unique map key from dynamic elements of the input
        // Note that can uniquely identify the subproblem with n & S1 only,
        // as S2 is nothing but S - S1 where S is sum of all elements
        String key = n + "|" + S1;
        // if sub-problem is seen for the first time, solve it and
        // store its result in a map
        if(!memo.containsKey(key)) {
            // Case 1. include current item in the subset S1 and recur
            // for remaining items (n - 1)
            int include = minPartition(S, n - 1, S1 + S[n], S2, memo);
            // Case 2. exclude current item from subset S1 and recur for
            // remaining items (n - 1)
            int exclude = minPartition(S, n - 1, S1, S2 + S[n], memo);
            memo.put(key, Math.min(include, exclude));
        }
        return memo.get(key);
    }
}
