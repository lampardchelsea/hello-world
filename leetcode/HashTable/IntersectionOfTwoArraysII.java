/**
 * Given two arrays, write a function to compute their intersection.
 * Example:
 * Given nums1 = [1, 2, 2, 1], nums2 = [2, 2], return [2, 2].
 * Note:
 * Each element in the result should appear as many times as it shows in both arrays.
 * The result can be in any order.
 * Follow up:
 * What if the given array is already sorted? How would you optimize your algorithm?
 * What if nums1's size is small compared to nums2's size? Which algorithm is better?
 * What if elements of nums2 are stored on disk, and the memory is limited such that 
 * you cannot load all elements into the memory at once?
*/
// Solution 1: Use HashMap as a filter, build the HashMap first depends on nums1,
// then use this map to measure nums2, find the intersection between two array.
// Refer to 
// http://www.jiuzhang.com/solutions/intersection-of-two-arrays-ii/
// https://aaronice.gitbooks.io/lintcode/content/array/intersection_of_two_arrays_ii.html
public class Solution {
    public int[] intersect(int[] nums1, int[] nums2) {
        // Construct the HashMap based on nums1
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0; i < nums1.length; i++) {
            if(!map.containsKey(nums1[i])) {
                map.put(nums1[i], 1);
            } else {
                map.put(nums1[i], map.get(nums1[i]) + 1);
            }
        }
        
        // Measure nums2 with HashMap and find the intersection part
        List<Integer> tmp = new ArrayList<Integer>();
        for(int j = 0; j < nums2.length; j++) {
            if(map.containsKey(nums2[j]) && map.get(nums2[j]) > 0) {
                tmp.add(nums2[j]);
                map.put(nums2[j], map.get(nums2[j]) - 1);
            }
        }
        
        // Print out the result
        int[] result = new int[tmp.size()];
        int k = 0;
        for(Integer i : tmp) {
            result[k++] = i;
        }
        
        return result;
    }
}

// Solution 2:

