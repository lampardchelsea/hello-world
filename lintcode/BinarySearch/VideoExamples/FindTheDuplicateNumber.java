/**
 * Refer to
 * http://www.lintcode.com/en/problem/find-the-duplicate-number/#
 * https://leetcode.com/problems/find-the-duplicate-number/#/description
 * Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive), 
 * prove that at least one duplicate number must exist. Assume that there is only one duplicate 
 * number, find the duplicate one.
 * 
 * Note:
 * You must not modify the array (assume the array is read only).
 * You must use only constant, O(1) extra space.
 * Your runtime complexity should be less than O(n2).
 * There is only one duplicate number in the array, but it could be repeated more than once.
 * 
 */
public class FindTheDuplicateNumber {
	// Solution 1: Binary Search
	// Refer to
	// https://segmentfault.com/a/1190000003817671
	// http://www.cnblogs.com/grandyang/p/4843654.html
	// https://discuss.leetcode.com/topic/25580/two-solutions-with-explanation-o-nlog-n-and-o-n-time-o-1-space-without-changing-the-input-array
	/**
	 * This solution is based on binary search.
	 * At first the search space is numbers between 1 to n. Each time I select a number mid (which is the one 
	 * in the middle) and count all the numbers equal to or less than mid. Then if the count is more than mid, 
	 * the search space will be [1 mid] otherwise [mid+1 n]. I do this until search space is only one number.
	 * Let's say n=10 and I select mid=5. Then I count all the numbers in the array which are less than equal mid. 
	 * If the there are more than 5 numbers that are less than 5, then by Pigeonhole Principle 
	 * (https://en.wikipedia.org/wiki/Pigeonhole_principle) one of them has occurred more than once. So I shrink 
	 * the search space from [1 10] to [1 5]. Otherwise the duplicate number is in the second half so for the next 
	 * step the search space would be [6 10].
	 */
	// https://discuss.leetcode.com/topic/25580/two-solutions-with-explanation-o-nlog-n-and-o-n-time-o-1-space-without-changing-the-input-array/11
	/**
	 * If the there are more than 5 numbers that are less than 5, then by Pigeonhole Principle one of them has occurred more than once. [...]. 
	 * Otherwise the duplicate number is in the second half
	 * While that's all correct, it's incomplete. The pigeonhole principle is only one way. It does tell you that there's a duplicate if 
	 * there are "too many" items. But it doesn't tell you that there isn't a duplicate if there aren't "too many" items. 
	 * So how do you know the duplicate is in the second half?
	 * I'd put it this way:
	 * Let count be the number of elements in the range 1 .. mid, as in your solution.
	 * If count > mid, then there are more than mid elements in the range 1 .. mid and thus that range contains a duplicate.
	 * If count <= mid, then there are n+1-count elements in the range mid+1 .. n. That is, at least n+1-mid elements in a 
	 * range of size n-mid. Thus this range must contain a duplicate.
	 * Or less formally:
	 * We know that the whole range is "too crowded" and thus that the first half or the second half of the range is too crowded 
	 * (if both weren't, then neither would be the whole range). So you check to know whether the first half is too crowded, 
	 * and if it isn't, you know that the second half is. 
	 * Also, better say "less than or equal to 5", not just "less than 5".
	 * Btw, you could also use count = sum(i <= mid for i in nums) or even directly if sum(i <= mid for i in nums) <= mid:.
	 */
    public int findDuplicate(int[] nums) {
//        int lo = 0;
//        int hi = nums.length - 1;
//        while(lo <= hi) {
//            int mid = lo + (hi - lo) / 2;
//            int count = 0;
//            for(int i = 0; i < nums.length; i++) {
//                if(nums[i] <= mid) {
//                    count++;
//                }
//            }
//            if(count > mid) {
//                hi = mid - 1;
//            } else {
//                lo = mid + 1;
//            }
//        }
//        return lo;
    	// Represent 1 to n, lo = 1, hi = n(as array length is n + 1)
        int lo = 1;
        int hi = nums.length - 1;
        // loop condition not contains lo = hi
        while(lo < hi) {
            int mid = lo + (hi - lo) / 2;
            int count = 0;
            for(int i = 0; i < nums.length; i++) {
                if(nums[i] <= mid) {
                    count++;
                }
            }
            // When current subarray nums size > mid value,
            // duplicate number happen in first half of
            // current subarray, else it happens on second
            // half of current subarray.
            if(count > mid) {
            	// Special as not set as hi = mid - 1
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }
    
    // Solution 2: Same as Find Circle In Linked List II
    // Refer to
    // https://segmentfault.com/a/1190000003817671
    /**
     * 复杂度
     * 时间 O(N) 空间 O(1)
     * 思路
     * 假设数组中没有重复，那我们可以做到这么一点，就是将数组的下标和1到n每一个数一对一的映射起来。比如数组是213,则映射关系
     * 为0->2, 1->1, 2->3。假设这个一对一映射关系是一个函数f(n)，其中n是下标，f(n)是映射到的数。如果我们从下标
     * 为0出发，根据这个函数计算出一个值，以这个值为新的下标，再用这个函数计算，以此类推，直到下标超界。实际上可以产生一个类似
     * 链表一样的序列。比如在这个例子中有两个下标的序列，0->2->3。
     * 但如果有重复的话，这中间就会产生多对一的映射，比如数组2131,则映射关系为0->2, {1，3}->1, 2->3。这样，我们
     * 推演的序列就一定会有环路了，这里下标的序列是0->2->3->1->1->1->1->...，而环的起点就是重复的数。
     * 所以该题实际上就是找环路起点的题，和Linked List Cycle II一样。我们先用快慢两个下标都从0开始，快下标每轮映射两次，
     * 慢下标每轮映射一次，直到两个下标再次相同。这时候保持慢下标位置不变，再用一个新的下标从0开始，这两个下标都继续每轮映射一次，
     * 当这两个下标相遇时，就是环的起点，也就是重复的数。对这个找环起点算法不懂的
     */
    // https://discuss.leetcode.com/topic/25913/my-easy-understood-solution-with-o-n-time-and-o-1-space-without-modifying-the-array-with-clear-explanation
    /**
     * The main idea is the same with problem Linked List Cycle II,https://leetcode.com/problems/linked-list-cycle-ii/. 
     * Use two pointers the fast and the slow. The fast one goes forward two steps each time, while the slow one goes 
     * only step each time. They must meet the same item when slow==fast. In fact, they meet in a circle, the duplicate 
     * number must be the entry point of the circle when visiting the array from nums[0]. Next we just need to find the entry 
     * point. We use a point(we can use the fast one before) to visit form beginning with one step each time, do the same job 
     * to slow. When fast==slow, they meet at the entry point of the circle. The easy understood code is as follows.
     */
    public int findDuplicate2(int[] nums) {
    	int walker = 0;
    	int runner = 0;
    	// We can use while(true) because as description from question there must be a duplicate
    	// value cause loop situation and break out of while loop will definitely happen
    	while(true) {
    		walker = nums[walker];
    		runner = nums[nums[runner]];
    		if(walker == runner) {
    			break;
    		}
    	}
    	// Reset either one pointer to 0(same as head node), then find out the meeting point
    	// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/LinkedList/LinkedListCycleII.java
    	walker = 0;
    	while(true) {
    		walker = nums[walker];
    		runner = nums[runner];
    		if(walker == runner) {
    			break;
    		}
    	}
    	return walker;
    }
    
    // JiuZhang Template
    public int findDuplicate3(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        // Caution: start is 1, not present the index, present the real integer
        //          start value, and end present real interger end value as
        //          nums.length - 1, '-1' because we know 1 more duplicate number
        //          e.g 1,2,3,4,5,5 --> length = 6, but integer range is 1 to 5
        //              start as 1, end as 5
        int start = 1;
        int end = nums.length - 1;
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            // Find how many numbers(count) no large(<=) than 'mid' in given array,
            // if count_mid <= mid --> duplicate number happen in [mid, end]
            // if count_mid > mid --> duplicate number happen in [start, mid]
            // e.g 1,2,2,3,4 -> mid = 2, count = 3, count > mid, 
            //     duplicate happen in first half, change end to mid
            //     1,2,3,3,4 -> mid = 2, count = 2, count = mid,
            //     first recognize duplicate happen in first half, change end to mid
            //               -> mid = 3, count = 4, count > mid,
            //     second recognize duplicate happen in first half, change end to mid
            int count = count_no_large_than(nums, mid);
            if(count <= mid) {
                start = mid;
            } else if(count > mid) {
                end = mid;
            }
        }
        // Still use 1,2,3,3,4 as example, finally narrow to start = 2, end = 3
        // Still following the same rule to judge the final two values: start & end
        // if count_mid <= mid --> duplicate number happen in [mid, end]
        // here, we have count_start <= start --> duplicate happen in [end]
        // if count_mid > mid --> duplicate number happen in [start, mid]
        // here, we have count_start > start --> duplicate happen in [start]
        if(count_no_large_than(nums, start) <= start) {
            return end;
        } else {
            return start;
        }
    }
    
    private int count_no_large_than(int[] nums, int mid) {
        int count = 0;
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] <= mid) {
                count++;
            }
        }
        return count;
    }
    
    
    
    public static void main(String[] args) {
    	int[] nums = {1, 2, 3, 3, 4};
    	FindTheDuplicateNumber f = new FindTheDuplicateNumber();
//    	int result = f.findDuplicate(nums);
    	int result = f.findDuplicate3(nums);
    	System.out.println(result);
    }
}
