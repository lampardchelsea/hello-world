/**
 * Refer to
 * https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/#/description
 * Suppose an array sorted in ascending order is rotated at some pivot unknown 
 * to you beforehand.
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * Find the minimum element.
 * You may assume no duplicate exists in the array.
 * 
 * Solution
 * https://segmentfault.com/a/1190000003488815
 * 二分递归法
 * 复杂度
 * 时间 O(N) 空间 O(N) 递归栈空间
 * 思路
 * 递归法实现起来更加简洁清晰。当min等于max时，我们锁定了那个最小值。那如何判断在哪一半呢，如果num[max] > num[mid]，
 * 说明右边都是有序的，所以那个旋转点必在左半边，也就是min到mid之间，如果num[max] < num[mid]，说明右边有问题，
 * 不过mid点本身肯定不是最小值，他已经大于num[max]了，所以在mid+1和max之间
 * 注意
 * min == max时随便返回一个
 * 
 * https://discuss.leetcode.com/topic/6112/a-concise-solution-with-proof-in-the-comment
 * class Solution {
	public:
	    int findMin(vector<int> &num) {
	        int low = 0, high = num.size() - 1;
	        // loop invariant: 1. low < high
	        //                 2. mid != high and thus A[mid] != A[high] (no duplicate exists)
	        //                 3. minimum is between [low, high]
	        // The proof that the loop will exit: after each iteration either the 'high' decreases
	        // or the 'low' increases, so the interval [low, high] will always shrink.
	        while (low < high) {
	            auto mid = low + (high - low) / 2;
	            if (num[mid] < num[high])
	                // the mininum is in the left part
	                high = mid;
	            else if (num[mid] > num[high])
	                // the mininum is in the right part
	                low = mid + 1;
	        }
	
	        return num[low];
	    }
	};
 */
public class FindMinimumInRotatedSortedArray {
    public int findMin(int[] nums) {
        return helper(nums, 0, nums.length - 1);
    }
    
    public int helper(int[] nums, int min, int max) {
        if(min == max) {
            return nums[min];
        }
        int mid = min + (max - min)/2;
    	// Important: 'mid' itself is NOT possible equal to 'max'
    	// value, since "mid = min + (max - min)/2" will always
    	// only make 'mid' possible equal to 'min', 'mid' has
    	// at least 1 difference than 'max', so the 'else' branch
        // here actually strictly means (nums[max] < nums[mid]),
        // at this situation, nums[mid] is absolutely NOT able
        // to be the minimum value since it already larger than
        // nums[max], so the 'min' is strictly exist in range
        // (mid + 1, max)
        if(nums[max] > nums[mid]) {
            return helper(nums, min, mid);
        } else {
            return helper(nums, mid + 1, max);
        }
    }
    
    /**
     * Better solution without recursion
     * Based on
     * https://discuss.leetcode.com/topic/6112/a-concise-solution-with-proof-in-the-comment
       public class Solution {
		    public int findMin(int[] nums) {
		        int min = 0;
		        int max = nums.length - 1;
		        while(min < max) {
		            int mid = min + (max - min)/2;
		            if(nums[mid] > nums[max]) {
		                min = mid + 1;
		            } else {
		                // https://discuss.leetcode.com/topic/6468/my-pretty-simple-code-to-solve-it/38
		                // Why hi = mid and not hi = mid - 1?
		                // in the case of nums[mid] < nums[hi], it means that nums[mid] is the smallest on right side, 
		                // it could be the smallest on the left side too, so we need to include it in the following search.
						// e.g. 4,5,6,7,0,1,2,3,4
						// mid is 0. If we use hi = mid - 1, 0 would be omitted.
		                max = mid;
		            }
		        }
		        return nums[min];
		    }
		}
     */
    
    public static void main(String[] args) {
    	int[] nums = {1, 2};
    	FindMinimumInRotatedSortedArray f = new FindMinimumInRotatedSortedArray();
    	int result = f.findMin(nums);
    	System.out.println(result);
    }
}
