/**
 * Given two arrays, write a function to compute their intersection.
 * Example:
 * Given nums1 = [1, 2, 2, 1], nums2 = [2, 2], return [2].
 * Note:
 * Each element in the result must be unique. 
 * The result can be in any order
*/
public class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        int length_1 = nums1.length;
        int length_2 = nums2.length;
        
        Set<Integer> set1 = new HashSet<Integer>();
        Set<Integer> set2 = new HashSet<Integer>();
        
        for(int i = 0; i < length_1; i++) {
            set1.add(nums1[i]);
        }
        
        for(int j = 0; j < length_2; j++) {
            if(set1.contains(nums2[j])) {
                set2.add(nums2[j]);
            }
        }
        
        int[] result = new int[set2.size()];
        int k = 0;
        for(Integer i : set2) {
            result[k++] = i;
        }
        
        return result;
    }
}
