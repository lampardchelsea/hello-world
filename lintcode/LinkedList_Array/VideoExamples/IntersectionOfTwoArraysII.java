/**
 * Refer to
 * https://leetcode.com/problems/intersection-of-two-arrays-ii/description/
 * Given two arrays, write a function to compute their intersection.
    Example:
    Given nums1 = [1, 2, 2, 1], nums2 = [2, 2], return [2, 2].

    Note:
    Each element in the result should appear as many times as it shows in both arrays.
    The result can be in any order.
    Follow up:
    What if the given array is already sorted? How would you optimize your algorithm?
    What if nums1's size is small compared to nums2's size? Which algorithm is better?
    What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements into the memory at once? 
 *
 *
 *
 * Solution
 * http://www.jiuzhang.com/solutions/intersection-of-two-arrays-ii/
 * https://discuss.leetcode.com/topic/45992/solution-to-3rd-follow-up-question
 * 
 * 
*/
public class Solution {
    /**
     * @param nums1 an integer array
     * @param nums2 an integer array
     * @return an integer array
     */
    public int[] intersection(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int a : nums1) {
            if(!map.containsKey(a)) {
                map.put(a, 1);
            } else {
                map.put(a, map.get(a) + 1);
            }
        }
        
        List<Integer> list = new ArrayList<Integer>();
        for(int b : nums2) {
            if(map.containsKey(b) && map.get(b) > 0) {
                list.add(b);
                map.put(b, map.get(b) - 1);
            }
        }
        
        int[] result = new int[list.size()];
        int i = 0;
        for(int c : list) {
            result[i++] = c;
        }
        return result;
    }
}
