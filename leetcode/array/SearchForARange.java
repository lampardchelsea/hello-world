/**
 * Refer to
 * https://leetcode.com/problems/search-for-a-range/#/description
 * Given an array of integers sorted in ascending order, find the starting and 
 * ending position of a given target value.
 * Your algorithm's runtime complexity must be in the order of O(log n).
 * If the target is not found in the array, return [-1, -1].
 * For example,
 * Given [5, 7, 7, 8, 8, 10] and target value 8,
 * return [3, 4]. 
 *
 * Solution
 * https://discuss.leetcode.com/topic/5891/clean-iterative-solution-with-two-binary-searches-with-explanation
 * 
 * https://segmentfault.com/a/1190000003817863
 * 二分搜索
 * 复杂度
 * 时间 O(logN) 空间 O(1)
 * 思路
 * 其实就是执行两次二分搜索，一次专门找左边边界，一次找右边边界。特别的，如果找左边边界时，
 * 要看mid左边一位的的数是否和当前mid相同，如果相同要继续在左半边二分搜索。如果找右边边界，
 * 则要判断右边一位的数是否相同。
 */


/**
 * Refer to
 * Best template as LintCode similar way
 * leetcode.com/problems/search-for-a-range/discuss/14701/A-very-simple-Java-solution-with-only-one-binary-search-algorithm/119333
*/
class Solution {
    public int[] searchRange(int[] nums, int target) {
        if(nums == null || nums.length == 0) {
            return new int[]{-1, -1};
        }
        int start = firstGreatOrEqual(nums, target);
        if(start == nums.length || nums[start] != target) {
            return new int[]{-1, -1};
        }
        int end = firstGreatOrEqual(nums, target + 1);
        return new int[]{start, nums[end] == target ? end : end - 1};
    }
    
    private int firstGreatOrEqual(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        while(low + 1 < high) {
            int mid = low + (high - low) / 2;
            if(nums[mid] < target) {
                low = mid;
            } else if(nums[mid] > target) {
                high = mid;
            } else if(nums[mid] == target) {
                high = mid;
            }
        }
        if(nums[low] == target) {
            return low;   
        }
        return high;
    }
}












public class SearchForARange {
	public int[] searchRange(int[] nums, int target) {
		// 找到左边界
		int front = search(nums, target, "front");
		// 找到右边界
		int rear = search(nums, target, "rear");
		int[] result = {front, rear};
		return result;
    }
	
	public int search(int[] nums, int target, String type) {
		int min = 0;
		int max = nums.length - 1;
		while(min <= max) {
			int mid = min + (max - min) / 2;
			if(nums[mid] > target) {
				max = mid - 1;
			} else if(nums[mid] < target) {
				min = mid + 1;
			} else {
				if(type == "front") {
					// 对于找左边的情况，要判断左边的数是否重复
					if(mid == 0) {
						return 0;
					}
					if(nums[mid] != nums[mid - 1]) {
						return mid;
					}
					max = mid - 1;
				} else {
					// 对于找右边的情况，要判断右边的数是否重复
					if(mid == nums.length - 1) {
						return nums.length - 1;
					}
					if(nums[mid] != nums[mid + 1]) {
						return mid;
					}
					min = mid + 1;
				}
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		
	}
}

