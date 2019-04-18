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
/**
 Refer to
 https://leetcode.com/problems/sort-array-by-parity/discuss/170734/C++Java-In-Place-Swap/229675
 Swap just when we need to
 Target, even as header, odd as tail, i = 0, j = A.length - 1
 1. if A[i] is odd, A[j] is even, then swap, incease i and decrease j
 2. if A[i] is odd, A[j] is also odd, no need swap, keep A[i] there, 
    just decrease j try to find if next A[j] is even, then can swap
 3. if A[i] is even, no need to check A[j], increase i to check if next
    A[i] is odd, then can follow same logic as case 1 and 2
*/
class Solution {
    public int[] sortArrayByParity(int[] A) {
        int i = 0;
        int j = A.length - 1;
        while(i < j) {
            if(A[i] % 2 == 1) {
                if(A[j] % 2 == 0) {
                    int temp = A[i];
                    A[i] = A[j];
                    A[j] = temp;
                    i++;
                    j--;
                } else {
                    j--;
                }
            } else {
                i++;
            }
        }
        return A;
    }
}
