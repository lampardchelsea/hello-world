/**
 * Refer to
 * http://www.lintcode.com/en/problem/find-the-missing-number/
 * https://leetcode.com/problems/missing-number/description/
 * Given an array containing n distinct numbers taken from 0, 1, 2, ..., n, 
   find the one that is missing from the array.
   For example, Given nums = [0, 1, 3] return 2.
   Note:
   Your algorithm should run in linear runtime complexity. Could you implement 
   it using only constant extra space complexity?
 * 
 * 
 * Solution
 * http://www.jiuzhang.com/solution/find-the-missing-number/
 * http://blog.hyoung.me/cn/2017/02/find-the-missing-number/
*/
public class FindTheMissingNumber {
	// Solution 1:
	/**
	 * Refer to
	 * http://blog.hyoung.me/cn/2017/02/find-the-missing-number/
	 * 看到这个题目，最直接的想法往往就是新建一个包含所有数字的集合，然后遍历这个数组，依次把出现过的数从集合中删去，
	 * 最后集合中剩下的那个数就是没有出现的那个了。这里的时间复杂度是 O(N) 空间复杂度也是O(N)，看上去已经很好了。
	 * 不过如果是这样解的话，未免太过简单了。考虑到无论如何我们都要遍历一遍数组，所以时间复杂度已经是最优了，
	 * 那么我们能不能优化一下空间复杂度呢？
	 * 答案是肯定的。考虑到这里的数据基本上是连续的正整数，那么我们就可以利用桶排序的思想：遍历数组，把每一个数都
	 * 送到它应该去的地方。
	 */
	public int findMissing(int[] nums) {
		/**
		 * 外层循环用于确保每个数都待在它应该在的位置，即nums[i] == i，而内层循环用于把属于这个位置的数给「换」回来。
		 * 这里有一个特殊情况，那就是数N，它很显然无法被放进数组，于是我们就把它当做缺失数的初始值，并且当在数组中碰到它时，
		 * 我们就把它作为一个「锚」不去主动移动它。每次当我们在某个位置发现它时，那个位置应该存在的数就有可能是缺失数。
		 * 到最后遍历结束时，要么N未曾出现，要么N所在位置对应的那个数就是缺失数。虽然这里有两层循环，但我们很容易就能想到，
		 * 每一次交换我们都可以保证把一个数移到了它应该在的位置，而最多只需交换N次就可以了，因此时间复杂度依然是O(N) 
		 * 而空间复杂度降到了O(1)
		 */
		int n = nums.length;
		int i = 0;
		while(i < n) {
			while(nums[i] != i && nums[i] < n) {
				int t = nums[i];
				nums[i] = nums[t];
				nums[t] = t;
			}
			i++;
		}
		for(i = 0; i < n; i++) {
			if(nums[i] != i) {
				return i;
			}
		}
		return n;
	}
	
	
	
    // Solution 2:
    // Math
    // Caution: Use long instead of int
    /*
     * @param nums: An array of integers
     * @return: An integer
     */
    public int findMissing_2(int[] nums) {
        long n = nums.length;
        long sum = n * (n + 1) / 2;
        for(int i : nums) {
            sum -= i;
        }
        return (int)sum;
    }
}
