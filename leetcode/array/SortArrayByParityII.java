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
// https://leetcode.com/problems/sort-array-by-parity-ii/solution/
// https://leetcode.com/problems/sort-array-by-parity-ii/discuss/181160/Java-two-pointer-one-pass-inplace
// Time Complexity: O(n)
// Space Complexity: O(1)
/**
 Approach 2: Read / Write Heads
 Intuition
 We are motivated (perhaps by the interviewer) to pursue a solution where we modify the original array A in place.
 First, it is enough to put all even elements in the correct place, since all odd elements will be in the 
 correct place too. So let's only focus on A[0], A[2], A[4], ...
 Ideally, we would like to have some partition where everything to the left is already correct, and everything 
 to the right is undecided.
 Indeed, this idea works if we separate it into two slices even = A[0], A[2], A[4], ... and odd = A[1], A[3], 
 A[5], .... Our invariant will be that everything less than i in the even slice is correct, and everything 
 less than j in the odd slice is correct.
 
 Algorithm
 For each even i, let's make A[i] even. To do it, we will draft an element from the odd slice. We pass j through 
 the odd slice until we find an even element, then swap. Our invariant is maintained, so the algorithm is correct.
*/
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
