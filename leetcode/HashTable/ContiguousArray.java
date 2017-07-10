/**
 * Refer to
 * https://leetcode.com/problems/contiguous-array/#/description
 * Given a binary array, find the maximum length of a contiguous subarray with equal number of 0 and 1.
    Example 1:
    Input: [0,1]
    Output: 2
    Explanation: [0, 1] is the longest contiguous subarray with equal number of 0 and 1.

    Example 2:
    Input: [0,1,0]
    Output: 2
    Explanation: [0, 1] (or [1, 0]) is a longest contiguous subarray with equal number of 0 and 1.
 * Note: The length of the given binary array will not exceed 50,000. 
 * 
 * Solution
 * https://discuss.leetcode.com/topic/79906/easy-java-o-n-solution-presum-hashmap
 * The idea is to change 0 in the original array to -1. Thus, if we find SUM[i, j] == 0 then 
 * we know there are even number of -1 and 1 between index i and j. Also put the sum to index 
 * mapping to a HashMap to make search faster.
 * 
 * http://www.cnblogs.com/grandyang/p/6529857.html
 * 这道题给了我们一个二进制的数组，让我们找邻近的子数组使其0和1的个数相等。对于求子数组的问题，我们需要时刻记着求累积
 * 和是一种很犀利的工具，但是这里怎么将子数组的和跟0和1的个数之间产生联系呢？我们需要用到一个trick，遇到1就加1，遇到0，
 * 就减1，这样如果某个子数组和为0，就说明0和1的个数相等，这个想法真是太叼了，不过博主木有想出来。知道了这一点，我们用
 * 一个哈希表建立子数组之和跟结尾位置的坐标之间的映射。如果某个子数组之和在哈希表里存在了，说明当前子数组减去哈希表中存
 * 的那个子数字，得到的结果是中间一段子数组之和，必然为0，说明0和1的个数相等，我们更新结果res
*/
public class Solution {
    public int findMaxLength(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(0, -1);
        int max = 0;
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] == 0) {
                nums[i] = -1;
            }
        }
        int sum = 0;
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
            // Our target is 0 (Definition similar as LC325 as k)
            if(map.containsKey(sum - 0)) {
                max = Math.max(max, i - map.get(sum - 0));
            }
            if(!map.containsKey(sum)) {
                map.put(sum, i);
            }
        }
        return max;
    }
}
