/**
 Refer to
 https://leetcode.com/problems/sort-array-by-parity-ii/
 Given an array A of non-negative integers, half of the integers in A are odd, and half of the integers are even.
 Sort the array so that whenever A[i] is odd, i is odd; and whenever A[i] is even, i is even.
 You may return any answer array that satisfies this condition.
 
 Example 1:
 Input: [4,2,5,7]
 Output: [4,5,2,7]
 Explanation: [4,7,2,5], [2,5,4,7], [2,7,4,5] would also have been accepted.
 
 Note:
 2 <= A.length <= 20000
 A.length % 2 == 0
 0 <= A[i] <= 1000
*/
// Solution 1: Two pointers split even and odd
// Refer to
// https://leetcode.com/problems/sort-array-by-parity-ii/solution/
// Time Complexity: O(n)
// Space Complexity: O(n)
class Solution {
    public int[] sortArrayByParityII(int[] A) {
        int i = 0;
        int j = 1;
        int[] result = new int[A.length];
        for(int k = 0; k < A.length; k++) {
            if(A[k] % 2 == 0) {
                result[i] = A[k];
                i += 2;
            } else {
                result[j] = A[k];
                j += 2;
            }
        }
        return result;
    }
}

// Solution 2:
// Refer to
// https://leetcode.com/problems/sort-array-by-parity-ii/discuss/181160/Java-two-pointer-one-pass-inplace
// Time Complexity: O(n)
// Space Complexity: O(1)
class Solution {
    public int[] sortArrayByParityII(int[] A) {
        int i = 0;
        int j = 1;
        int len = A.length;
        while(i < len && j < len) {
            while(i < len && A[i] % 2 == 0) {
                i += 2;
            }
            while(j < len && A[j] % 2 == 1) {
                j += 2;
            }
            // Add i, j condition again to preserve final
            // value of i, j out of bound of A.length after
            // while loop but still go into swap section
            if(i < len && j < len) {
                int temp = A[i];
                A[i] = A[j];
                A[j] = temp;
            }
        }
        return A;
    }
}
