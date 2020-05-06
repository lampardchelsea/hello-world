/**
 Refer to
 https://leetcode.com/problems/number-of-squareful-arrays/
 Given an array A of non-negative integers, the array is squareful if for every pair of adjacent elements, 
 their sum is a perfect square.
 Return the number of permutations of A that are squareful.  Two permutations A1 and A2 differ if and only 
 if there is some index i such that A1[i] != A2[i].
 
 Example 1:
 Input: [1,17,8]
 Output: 2
 Explanation: 
 [1,8,17] and [17,8,1] are the valid permutations.

 Example 2:
 Input: [2,2,2]
 Output: 1
 
 Note:
 1 <= A.length <= 12
 0 <= A[i] <= 1e9
*/

// Solution 1: Java Backtracking similar to Permutations II
// Refer to
// https://leetcode.com/problems/number-of-squareful-arrays/discuss/238658/Java-Backtracking-similar-to-Permutations-II
class Solution {
    int count = 0;
    public int numSquarefulPerms(int[] A) {
        if(A.length == 0) {
            return 0;
        }
        Arrays.sort(A);
        helper(A, new ArrayList<Integer>(), new boolean[A.length]);
        return count;
    }
    
    private void helper(int[] A, List<Integer> temp, boolean[] used) {
        if(temp.size() == A.length) {
            count++;
            return;
        }
        for(int i = 0; i < A.length; i++) {
            if(used[i] || i > 0 && !used[i - 1] && A[i - 1] == A[i]) {
                continue;
            }
            // Similar to Permutations II, only need to check if sum of 
            // adjancent number are perfect square (two conditions, when no
            // element in temp or when it has element in temp then each pair)
            if(temp.size() == 0 || (isSquare(temp.get(temp.size() - 1) + A[i]))) {
                used[i] = true;
                temp.add(A[i]);
                helper(A, temp, used);
                used[i] = false;
                temp.remove(temp.size() - 1);
            }
        }
    }
    
    private boolean isSquare(int n) {
        double m = Math.sqrt(n);
        return m == Math.floor(m);
    }
}
