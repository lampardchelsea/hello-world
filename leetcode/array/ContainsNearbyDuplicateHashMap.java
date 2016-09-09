/**
 * Given an array of integers and an integer k, find out whether there are two distinct indices i 
 * and j in the array such that nums[i] = nums[j] and the difference between i and j is at most k.
*/
public boolean containsNearbyDuplicate(int[] nums, int k) {
    int length = nums.length;
    boolean result = false;
    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    for(int i = 0; i < length; i++) {
        Integer preIndex = map.put(nums[i], i);
        
        if(preIndex != null && i - preIndex.intValue() <= k) {
            result = true;
            break;
        }
    }
    
    return result;
}
