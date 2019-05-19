/**
  Refer to
  https://leetcode.com/problems/merge-sorted-array/
  Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.

Note:

The number of elements initialized in nums1 and nums2 are m and n respectively.
You may assume that nums1 has enough space (size that is greater or equal to m + n) to hold additional elements from nums2.
Example:

Input:
nums1 = [1,2,3,0,0,0], m = 3
nums2 = [2,5,6],       n = 3

Output: [1,2,2,3,5,6]
*/
// Solution 1: Two points
// Refer to
// https://leetcode.com/problems/merge-sorted-array/discuss/29578/Share-my-accepted-Java-solution!/172441
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i1 = m - 1;  //last index of nums1that has valid number
        int i2 = n - 1; // last index of nums2
        int lastIndex = m + n - 1; //last index of nums array
        while(i1 >= 0 && i2 >= 0){
            if(nums1[i1] > nums2[i2]){ //compare two numbers
                //if nums1[i1] is bigger, then place it in the last index in nums1
                nums1[lastIndex] = nums1[i1];
                i1 --;
            } else {
                nums1[lastIndex] = nums2[i2];
                i2 --;
            }
            lastIndex --;
        }
        // if i1 is greater than 0 but i2 is not, we don't need to do anything becuase it's a sorted array.
        // However, if i2 is greater than 0, this means the rest of spot is only n2.
        while( i2 >= 0){
            nums1[lastIndex] = nums2[i2];
            lastIndex --;
            i2 --;
        }
    }
}



/**
 * Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.
 * Note:
 * You may assume that nums1 has enough space (size that is greater or equal to m + n) to hold 
 * additional elements from nums2. The number of elements initialized in nums1 and nums2 are m 
 * and n respectively.
 * 
 * Analysis
 * The key to solve this problem is moving element of A and B backwards. If B has some elements 
 * left after A is done, also need to handle that case.
 * The takeaway message from this problem is that the loop condition. This kind of condition is 
 * also used for merging two sorted linked list.
*/
public class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        while(m > 0 && n > 0) {
            if(nums1[m - 1] <= nums2[n - 1]) {
                nums1[m + n - 1] = nums2[n - 1];
                n--;
            } else {
                nums1[m + n - 1] = nums1[m - 1];
                m--;
            }
        }
        
        // Handle when scan and allocate all items of nums1 array, 
        // but items of nums2 array still remain
        while(n > 0) {
            nums1[m + n - 1] = nums2[n - 1];
            n--;
        }
    }
}
