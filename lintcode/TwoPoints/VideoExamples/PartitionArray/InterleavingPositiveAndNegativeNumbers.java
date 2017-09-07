/**
 * Refer to
 * http://www.lintcode.com/en/problem/interleaving-positive-and-negative-numbers/
 * Given an array with positive and negative integers. 
   Re-range it to interleaving with positive and negative integers.
   Notice
   You are not necessary to keep the original order of positive integers or negative integers.
    Have you met this question in a real interview? Yes
    Example
    Given [-1, -2, -3, 4, 5, 6], after re-range, it will be [-1, 5, -2, 4, -3, 6] or any other reasonable answer.
 *
 *
 * Solution
 * http://www.cnblogs.com/EdwardLiu/p/4314781.html
*/
class Solution {
    /**
     * @param A: An integer array.
     * @return: void
     */
    public void rerange(int[] A) {
        if(A == null || A.length == 0) {
            return;
        }
        int i = 0;
        int negNum = 0;
        int posNum = 0;
        while(i < A.length) {
            if(A[i] < 0) {
                negNum++;
            } else {
                posNum++;
            }
            i++;
        }
        int negInd = 0;
        int posInd = 1;
        // 这道题没有给出正数、负数谁多谁少，所以需要先统计数量，
        // 数量多的要包着数量少的，然后数组尾部全是数量多的数
        // E.g
        // Input
        // [28,2,-22,-27,2,9,-33,-4,-18,26,25,34,-35,-17,2,-2,32,35,-8]
        // Output
        // [-27,2,-22,28,-4,9,-33,2,-18,26,-17,34,-35,25,-2,2,32,35,-8]
        // Expected
        // [2,-35,2,-33,2,-27,9,-22,25,-18,26,-17,28,-8,32,-4,34,-2,35]
        if(posNum > negNum) {
            negInd = 1;
            posInd = 0;
        }
        while(negInd < A.length && posInd < A.length) {
            while(negInd < A.length && A[negInd] < 0) {
                negInd += 2;
            }
            while(posInd < A.length && A[posInd] > 0) {
                posInd += 2;
            }
            if(negInd < A.length && posInd < A.length) {
                swap(A, negInd, posInd);
            }
        }
   }
   
   private void swap(int[] A, int i, int j) {
       int temp = A[i];
       A[i] = A[j];
       A[j] = temp;
   }
}
