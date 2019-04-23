/**
 Refer to
 https://leetcode.com/problems/n-repeated-element-in-size-2n-array/
 In a array A of size 2N, there are N+1 unique elements, and exactly one of these elements is repeated N times.

Return the element repeated N times.
Example 1:
Input: [1,2,3,3]
Output: 3

Example 2:
Input: [2,1,2,5,3,2]
Output: 2

Example 3:
Input: [5,1,5,2,5,3,5,4]
Output: 5

Note:
4 <= A.length <= 10000
0 <= A[i] < 10000
A.length is even
*/
// Solution 1:
// Runtime: 14 ms, faster than 29.66% of Java online submissions for N-Repeated Element in Size 2N Array.
// Memory Usage: 40.4 MB, less than 82.35% of Java online submissions for N-Repeated Element in Size 2N Array.
class Solution {
    public int repeatedNTimes(int[] A) {
        int sum = 0;
        Set<Integer> set = new HashSet<Integer>();
        for(int i = 0; i < A.length; i++) {
            sum += A[i];
            set.add(A[i]);
        }
        int sumOfUniqueN = 0;
        for(Integer a : set) {
            sumOfUniqueN += a;
        }
        return (sum - sumOfUniqueN) / (A.length / 2 - 1);
    }
}
