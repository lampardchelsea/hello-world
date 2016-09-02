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
