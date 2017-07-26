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
	
	
	public int[] searchRange2(int[] A, int target) {
        // Check null and empty case
        if(A == null || A.length == 0) {
            return new int[] {-1, -1};
        }
        int index_1 = -1;
        int index_2 = -1;
        
        // Find first position of target
        int start = 0;
        int end = A.length - 1;
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            if(A[mid] == target) {
                end = mid;
            } else if(A[mid] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }
        // Important: We must use 'if-else' in this problem,
        // since we don't return 'start' or 'end'
        // directly as template, but we can only
        // assign 1 possible value to result, so
        // 'if-else' will enable atomic value assign
        if(A[start] == target) {
            index_1 = start;
        }
        if(A[end] == target) {
            index_1 = end;
        } 
        
        // Find last position of target
        // Don't forget to re-assign values as
        // we start a new search
        start = 0;
        end = A.length - 1;
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            if(A[mid] == target) {
                start = mid;
            } else if(A[mid] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }
        
        if(A[end] == target) {
            index_2 = end;
        } 
        if(A[start] == target) {
            index_2 = start;
        }
        
        return new int[] {index_1, index_2};
	}
	
	
	
	public static void main(String[] args) {
		SearchForARange s = new SearchForARange();
		int[] nums = {5,5,5,5,5,5,5,5,5,5};
		int target = 5;
		int[] result = s.searchRange2(nums, target);
		for(int i : result) {
			System.out.print(i + ",");
		}
	}
}

