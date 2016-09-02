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

Analysis:
Input nums2 as empty array [], length n is 0, if(i > 0 && nums1[i] > nums2[j]) is false because i = 0 and j = -1,
will hit else block, but nums2[j--] is nums2[-1], index out of bound exception. Need to change i > 0 to i >= 0,
also add j < 0 case in if condition. Then come to Case 2.

Error Case 2:
Last executed input:
[1,2,3,0,0,0]  3  [2,5,6]  3

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

Analysis:
When i = 1, j = -1, k = 1, when judge on if condition, if put j < 0 after (i > 0 && nums1[i] > nums2[j]), nums2[j] is
actually nums2[-1], which should avoid by putting j < 0 at first place. Exchange two sections of if condition.
Then come to Case 3.

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

Analysis:
When i = 0, j = 0, k = 1, the right case is nums1[0] = 2 > nums2[0] = 1, and we should go into if block, not else block,
but as (i > 0 && nums1[i] > nums2[j]) also contain a i > 0 condition, which should be i >= 0, cause the error that go
into else block, then reverse to the right order. Must change i > 0 to i >= 0.
*/
public class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;
        while(k >= 0) {
            if(j < 0 || (i >= 0 && nums1[i] > nums2[j])) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }
    }
}

