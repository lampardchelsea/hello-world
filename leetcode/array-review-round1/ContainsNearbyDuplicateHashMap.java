public class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        int length = nums.length;
        boolean result = false;
        
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0; i < length; i++) {
            Integer preIndex = map.put(nums[i], i);

            if(preIndex != null && i - preIndex <= k) {
                result = true;
            }
        }
        
        return result;
    }
}
