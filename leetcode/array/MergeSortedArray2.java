/**
Error Case 1:
Last executed input:
[1]  1  []  0

public class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;
        while(k >= 0) {
            if(i > 0 && nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }
        
    }
}

Error Case 2:
Last executed input:
[1,2,3,0,0,0]  3  [2,5,6] 3

public class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;
        while(k >= 0) {
            if((i > 0 && nums1[i] > nums2[j]) || j < 0) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }
        
    }
}

Error Case 3:
Input:
[2,0]  1  [1]  1
Output:
[2,1]
Expected:
[1,2]

public class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;
        while(k >= 0) {
            if(j < 0 || (i > 0 && nums1[i] > nums2[j])) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }
    }
}
*/

