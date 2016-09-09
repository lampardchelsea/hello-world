/**
 * Given an array of integers and an integer k, find out whether there are two distinct indices i 
 * and j in the array such that nums[i] = nums[j] and the difference between i and j is at most k.
*/
public boolean containsNearbyDuplicate(int[] nums, int k) {
    int length = nums.length;
    boolean result = false;
    
    // Use HashMap to store nums[i] as key and index i as value
    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    for(int i = 0; i < length; i++) {
        // The return of HashMap put method is described as:
        // the previous value associated with key, or null if there was no mapping for key. 
        // (A null return can also indicate that the map previously associated null with key.)
        // Note: As description, if current key(nums[i]) is first time put into map, it will
        // return as null because no previous index as value(i) stored, to handle this case
        // must set variable preIndex as Object type(Integer).
        Integer preIndex = map.put(nums[i], i);
        // The check for preIndex not null is required, otherwise will break as NullPointException
        // Use (i - preIndex.intValue()) to calculate the gap between one key's two nearest
        // distance, as HashMap put method will return latest previous same key(nums[i])'s index
        // (i) in array. If the gap not larger than k, then we find their is a pair nums[i],
        // and nums[j] have same value and distance no larger than k.
        if(preIndex != null && i - preIndex.intValue() <= k) {
            result = true;
            break;
        }
    }
    
    return result;
}
