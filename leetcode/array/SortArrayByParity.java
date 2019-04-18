/**
 Given an array A of non-negative integers, return an array consisting of all 
 the even elements of A, followed by all the odd elements of A.

You may return any answer array that satisfies this condition.

Example 1:
Input: [3,1,2,4]
Output: [2,4,3,1]
The outputs [4,2,3,1], [2,4,1,3], and [4,2,1,3] would also be accepted.

Note:
1 <= A.length <= 5000
0 <= A[i] <= 5000
*/

// Solution 1: Not efficient swap
/**
 https://leetcode.com/problems/sort-array-by-parity/discuss/170734/C++Java-In-Place-Swap/208664
 IMHO, your java solution is not much efficient, it does lot of swaps in memory.
 Lets say all elements are even, then your solution does unnecessary swaps 
 till the end of array, which are not required.
*/
class Solution {
    public int[] sortArrayByParity(int[] A) {
        for(int i = 0, j = 0; j < A.length; j++) {
            if(A[j] % 2 == 0) {
                int temp = A[i];
                A[i] = A[j];
                A[j] = temp;
                i++;
            }
        }
        return A;
    }
}

// Solution 2: Efficient swap
