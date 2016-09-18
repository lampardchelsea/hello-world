/**
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
 * You may assume that each input would have exactly one solution.
 * Example:
 * Given nums = [2, 7, 11, 15], target = 9,
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 * UPDATE (2016/2/13):
 * The return format had been changed to zero-based indices. 
 * Please read the above updated description carefully.
 * 
 * If write like below, input as nums = [0,3,4,0] and target as 0, the problem is when put 0 on index 3,
 * will replace the original 0 on index 0 which stored on map previously as using map's put method,
 * then the result will be [0,0] not as right as [0,3].
 *     public int[] twoSum(int[] nums, int target) {
        int length = nums.length;
        int[] result = new int[2];
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        
        for(int i = 0; i < length; i++) {
            map.put(nums[i], i);
            for(Integer a : map.keySet()) {
                if(a + nums[i] == target && (map.get(a) != i)) {
                    result[0] = map.get(a);
                    result[1] = i;
                } 
            }
        }
        
        return result;
    }
*/
