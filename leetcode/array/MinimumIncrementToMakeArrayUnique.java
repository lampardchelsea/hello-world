/**
Refer to
https://leetcode.com/problems/minimum-increment-to-make-array-unique/
Given an array of integers A, a move consists of choosing any A[i], and incrementing it by 1.

Return the least number of moves to make every value in A unique.

Example 1:
Input: [1,2,2]
Output: 1
Explanation:  After 1 move, the array could be [1, 2, 3].

Example 2:
Input: [3,2,1,2,1,7]
Output: 6
Explanation:  After 6 moves, the array could be [3, 4, 1, 2, 5, 7].
It can be shown with 5 or less moves that it is impossible for the array to have all unique values.

Note:
0 <= A.length <= 40000
0 <= A[i] < 40000
*/

// Solution 1: Just Sort
// Refer to
// https://leetcode.com/problems/minimum-increment-to-make-array-unique/discuss/197687/JavaC%2B%2BPython-Straight-Forward
/**
Solution 1: Just Sort, O(NlogN)
Sort the input array.
Compared with previous number,
the current number need to be at least prev + 1.

Time Complexity: O(NlogN) for sorting
Space: O(1) for in-space sort

Note that you can apply "O(N)" sort in sacrifice of space.
Here we don't talk further about sort complexity.

Java:
    public int minIncrementForUnique(int[] A) {
        Arrays.sort(A);
        int res = 0, need = 0;
        for (int a : A) {
            res += Math.max(need - a, 0);
            need = Math.max(a, need) + 1;
        }
        return res;
    }
    
public int minIncrementForUnique(int[] A) {
        if(A == null || A.length == 0){
            return 0;
        }
        Arrays.sort(A);
        int res = 0, prev = A[0];
        for(int i = 1; i < A.length; i++){
            int expect = prev + 1;
            res += A[i] > expect ? 0 : expect - A[i];
            prev = Math.max(expect, A[i]);
        }
        return res;
    }
*/
class Solution {
    public int minIncrementForUnique(int[] A) {
        if(A.length == 0) {
            return 0;
        }
        int result = 0;
        Arrays.sort(A);
        int prev = A[0];
        for(int i = 1; i < A.length; i++) {
            int expect = prev + 1;
            if(A[i] <= expect) {
                result += expect - A[i];
            }
            prev = Math.max(expect, A[i]);
        }
        return result;
    }
}

