/**
 * Refer to
 * http://www.lintcode.com/en/problem/kth-largest-element/
 * Find K-th largest element in an array.
    Notice
    You can swap elements in the array

    Have you met this question in a real interview? Yes
    Example
    In array [9,3,2,4,8], the 3rd largest element is 4.
    In array [1,2,3,4,5], the 1st largest element is 5, 2nd largest element is 4, 3rd largest element is 3 and etc.
 *
 * Solution
 * http://www.jiuzhang.com/solution/kth-largest-element
 * https://aaronice.gitbooks.io/lintcode/content/data_structure/kth_largest_element.html
*/
// Solution 1: O(nlogn)
class Solution {
    /*
     * @param k : description of k
     * @param nums : array of nums
     * @return: description of return
     */
    public int kthLargestElement(int k, int[] nums) {
        if(nums == null || nums.length == 0) {
            return -1;
        }
        Arrays.sort(nums);
        int index = nums.length - 1 - (k - 1);
        return nums[index];
    }
};


// Solution 2: Quick Select
// Refer to
// https://aaronice.gitbooks.io/lintcode/content/data_structure/kth_largest_element.html
// https://github.com/lampardchelsea/hello-world/blob/master/lintcode/TwoPoints/VideoExamples/PartitionArray/KthSmallestNumbersInUnsortedArray.java
class Solution {
    /*
     * @param k : description of k
     * @param nums : array of nums
     * @return: description of return
     */
    public int kthLargestElement(int k, int[] nums) {
        if(nums == null || nums.length == 0) {
            return -1;
        }
        return quickSelect(nums, 0, nums.length - 1, nums.length - 1 - (k - 1));
    }
    
    private int quickSelect(int[] nums, int start, int end, int k) {
        if(start == end) {
            return nums[start];
        }
        int left = start;
        int right = end;
        int mid = start + (end - start) / 2;
        int pivot = nums[mid];
        while(left <= right) {
            while(left <= right && nums[left] < pivot) {
                left++;
            }
            while(left <= right && nums[right] > pivot) {
                right--;
            }
            if(left <= right) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
                right--;
            }
        } 
        if(right >= k && start <= right) {
            return quickSelect(nums, start, right, k);
        } else if(left <= k && left <= end) {
            return quickSelect(nums, left, end, k);
        } else {
            return nums[k];
        }
    }
};
