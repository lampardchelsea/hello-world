public class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = 0;
        int j = 0;
        int k = 0;
        
        // Create a new result array to merge two arrays
        int[] result = new int[m + n];
        while(i < m && j < n) {
          if(nums1[i] <= nums2[j]) {
            result[k++] = nums1[i++];
          } else {
            result[k++] = nums2[j++];
          }
        }
        
        // If after merge all nums2 array, nums1 array remain items
        while(i < m) {
          result[k++] = nums1[i++];
        }
        
        // If after merge all nums1 array, nums2 array remain items
        while(j < n) {
          result[k++] = nums2[j++];
        }
    }
}
