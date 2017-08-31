/**
 * Refer to
 * http://www.lintcode.com/en/problem/intersection-of-two-arrays-ii/
 * 
 * Solution
 * http://www.jiuzhang.com/solutions/intersection-of-two-arrays-ii/
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
