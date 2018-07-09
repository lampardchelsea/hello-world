/**
 * Refer to
 * https://leetcode.com/problems/set-mismatch/description/
 * The set S originally contains numbers from 1 to n. But unfortunately, due to the data error, 
   one of the numbers in the set got duplicated to another number in the set, which results in 
   repetition of one number and loss of another number.

    Given an array nums representing the data status of this set after the error. Your task is 
    to firstly find the number occurs twice and then find the number that is missing. Return 
    them in the form of an array.

    Example 1:
    Input: nums = [1,2,2,4]
    Output: [2,3]
    Note:
    The given array size will in the range [2, 10000].
    The given array's numbers won't have any order.

 * 
 * Solution
 * https://leetcode.com/problems/set-mismatch/discuss/112425/Java-HashMap-Solution-O(n)-time-O(1)-space
 * https://leetcode.com/problems/set-mismatch/discuss/105578/Java-Two-methods-using-sign-and-swap
 * https://leetcode.com/problems/set-mismatch/discuss/105528/Simple-Java-O(n)-solution-HashSet
*/
// Solution 1: HashMap
class Solution {
    public int[] findErrorNums(int[] nums) {
        int[] result = new int[2];
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }
        for(int key = 1; key <= nums.length; key++) {
            if(!map.containsKey(key)) {
                result[1] = key;
            } else if(map.get(key) > 1) {
                result[0] = key;
            }
        }
        return result;
    }
}


// Solution 2: Swap
class Solution {
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    public int[] findErrorNums(int[] nums) {
        int[] result = new int[2];
        for(int i = 0; i < nums.length; i++) {
            while(nums[nums[i] - 1] != nums[i]) {
                swap(nums, i, nums[i] - 1);
            }
        }
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] != i + 1) {
                result[0] = nums[i];
                result[1] = i + 1;
            }
        }
        return result;
    }
}


// Solution 3: Math and HashSet
class Solution {
    public int[] findErrorNums(int[] nums) {
        int expect = 0;
        int real = 0;
        for(int i = 1; i <= nums.length; i++) {
            expect += i;
        }
        for(int i = 0; i < nums.length; i++) {
            real += nums[i];
        }
        // Keep the sign bit as you don't know the duplicate
        // number is larger or smaller than the original index
        int diff = expect - real;
        Set<Integer> set = new HashSet<Integer>();
        int duplicate = 0;
        for(int num : nums) {
            if(!set.add(num)) {
                duplicate = num;
            }
        }
        return new int[] {duplicate, duplicate + diff};
    }
}
