/**
 * Refer to
 * http://www.lintcode.com/en/problem/intersection-of-two-arrays/#
 *
 * Solution
 * https://discuss.leetcode.com/topic/45685/three-java-solutions
 * http://www.jiuzhang.com/solution/intersection-of-two-arrays/
*/
// Use two hash sets
// Time complexity: O(n)
public class Solution {
    /**
     * @param nums1 an integer array
     * @param nums2 an integer array
     * @return an integer array
     */
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set = new HashSet<Integer>();
        Set<Integer> intersection = new HashSet<Integer>();
        for(int a : nums1) {
            set.add(a);
        }
        for(int b : nums2) {
            if(set.contains(b)) {
                intersection.add(b);
            }
        }
        int[] result = new int[intersection.size()];
        int i = 0;
        for(int c : intersection) {
            result[i++] = c; 
        }
        return result;
    }
}


// Sort both arrays, use two pointers
// Time complexity: O(nlogn)
public class Solution {
    /**
     * @param nums1 an integer array
     * @param nums2 an integer array
     * @return an integer array
     */
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set = new HashSet<Integer>();
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int i = 0, j = 0;
        while(i < nums1.length && j < nums2.length) {
            if(nums1[i] < nums2[j]) {
                i++;
            } else if(nums1[i] > nums2[j]) {
                j++;
            } else {
                set.add(nums1[i]);
                i++;
                j++;
            }
        }
        int[] result = new int[set.size()];
        int k = 0;
        for(int a : set) {
            result[k++] = a;
        }
        return result;
    }
}


// Binary search
// Time complexity: O(nlogn)
public class Solution {
    /**
     * @param nums1 an integer array
     * @param nums2 an integer array
     * @return an integer array
     */
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set = new HashSet<Integer>();
        // nums2 must be sorted to fit Binary Search require
        Arrays.sort(nums2);
        for(int a : nums1) {
            if(binarySearch(nums2, a)) {
                set.add(a);
            }
        }
        int i = 0;
        int[] result = new int[set.size()];
        for(int b : set) {
            result[i++] = b;
        }
        return result;
    }
    
    private boolean binarySearch(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        int low = 0;
        int high = nums.length - 1;
        while(low + 1 < high) {
            int mid = low + (high - low) / 2;
            if(nums[mid] < target) {
                low = mid;  
            } else if(nums[mid] > target) {
                high = mid;
            } else {
                return true;
            }
        }
        if(nums[low] == target) {
            return true;
        }
        if(nums[high] == target) {
            return true;
        }
        return false;
    }
}
