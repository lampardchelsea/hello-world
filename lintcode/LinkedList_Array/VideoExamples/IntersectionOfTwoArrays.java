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



