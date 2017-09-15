
import java.util.Comparator;
import java.util.Deque;
import java.util.PriorityQueue;

/**
 * Refer to
 * https://leetcode.com/problems/sliding-window-maximum/description/
 * Given an array nums, there is a sliding window of size k which is moving from the 
 * very left of the array to the very right. You can only see the k numbers in the 
 * window. Each time the sliding window moves right by one position.

	For example,
	Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.
	
	Window position                Max
	---------------               -----
	[1  3  -1] -3  5  3  6  7       3
	 1 [3  -1  -3] 5  3  6  7       3
	 1  3 [-1  -3  5] 3  6  7       5
	 1  3  -1 [-3  5  3] 6  7       5
	 1  3  -1  -3 [5  3  6] 7       6
	 1  3  -1  -3  5 [3  6  7]      7
	Therefore, return the max sliding window as [3,3,5,5,6,7].
	
	Note: 
	You may assume k is always valid, ie: 1 ≤ k ≤ input array's size for non-empty array.
	
	Follow up:
	Could you solve it in linear time?
 * 
 * 
 * Solution 1: Priority Queue
 * http://wxx5433.github.io/sliding-window-maximum.html
 * http://articles.leetcode.com/sliding-window-maximum/
 * https://discuss.leetcode.com/topic/20165/my-java-solution-using-priorityqueue
 * 
 * Solution 2: Deque
 * http://articles.leetcode.com/sliding-window-maximum/
 * https://discuss.leetcode.com/topic/19055/java-o-n-solution-using-deque-with-explanation
 * https://segmentfault.com/a/1190000003903509
 */
public class SlidingWindowMaximum {
	/**
	 * Solution 1: Priority Queue (Time Complexity: O(nlogk))
	 * Not a linear solution, instead, it is of O(nlogk) complexity, since add, pop and 
	 * remove operation of PriorityQueue cost O(logk) time.
	 * What we need to do is just maintain a heap, that heap top gets the maximal value of the k elements.
	 */
	public int[] maxSlidingWindow(int[] nums, int k) {
		int n = nums.length - k + 1;
		if(nums == null || n <= 0 || k == 0) {
			return new int[0];
		}
		int[] result = new int[n];
        PriorityQueue<Integer> maxPQ = new PriorityQueue<Integer>(k, new Comparator<Integer>() {
        	public int compare(Integer a, Integer b) {
        		return b.compareTo(a);
        	}
        });
        // For Sliding Window issue we have two styles to mapping each section
        // calculating to final result
        // Style 1:
        // Refer to
        // http://wxx5433.github.io/sliding-window-maximum.html
       for(int i = 0; i < nums.length; i++) {
           maxPQ.add(nums[i]);
           if(i >= k - 1) {
           	result[i - (k - 1)] = maxPQ.peek();
           	maxPQ.remove(nums[i - (k - 1)]);
           }
       }
        // Style 2:
        // Refer to
        // https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Heap/SlidingWindowMedian.java
//         for(int i = 0; i <= nums.length; i++) {
//             if(i >= k) {
//             	result[i - k] = maxPQ.peek();
//             	maxPQ.remove(nums[i - k]);
//             }
//         	if(i < nums.length) {
//                 maxPQ.add(nums[i]);       		
//         	}
//         }
        return result;
    }
	
	
	/**
	 *  Solution 2: Deque (Time Complexity: O(n))
	 *  双向队列
	 *  复杂度
	 *  时间 O(N) 空间 O(K)
	 *  思路
	 *  我们用双向队列可以在O(N)时间内解决这题。当我们遇到新的数时，将新的数和双向队列的末尾比较，如果末尾比新数小，则把末尾扔掉，
	 *  直到该队列的末尾比新数大或者队列为空的时候才住手。这样，我们可以保证队列里的元素是从头到尾降序的，由于队列里只有窗口内的数，
	 *  所以他们其实就是窗口内第一大，第二大，第三大...的数。保持队列里只有窗口内数的方法和上个解法一样，也是每来一个新的把窗口
	 *  最左边的扔掉，然后把新的加进去。然而由于我们在加新数的时候，已经把很多没用的数给扔了，这样队列头部的数并不一定是窗口最左边的数。
	 *  这里的技巧是，我们队列中存的是那个数在原数组中的下标，这样我们既可以直到这个数的值，也可以知道该数是不是窗口最左边的数。
	 *  这里为什么时间复杂度是O(N)呢？因为每个数只可能被操作最多两次，一次是加入队列的时候，一次是因为有别的更大数在后面，
	 *  所以被扔掉，或者因为出了窗口而被扔掉。
	 *  
	 */	
	public int[] maxSlidingWindow2(int[] nums, int k) {
		int n = nums.length - k + 1;
	    if(nums == null || n <= 0 || k == 0) {
	    	return new int[0];
	    }
	    int[] result = new int[n];
	    // 然而由于我们在加新数的时候，已经把很多没用的数给扔了，这样队列头部的数并不一定是窗口最左边的数。这里的技巧是，
	    // 我们队列中存的是那个数在原数组中的下标，这样我们既可以直到这个数的值，也可以知道该数是不是窗口最左边的数。
	    Deque<Integer> deque = new LinkedList<Integer>();
	    for(int i = 0; i < nums.length; i++) {
	    	// 每当新数进来时，如果发现队列头部的数的下标，是窗口最左边数的下标，则扔掉
	    	// 扔掉的原因是基于： 保持队列里只有窗口内数的方法和上个解法一样，也是每来一个新的把窗口最左边的扔掉，然后把新的加进去。
	    	// 然而由于我们在加新数的时候，已经把很多没用的数给扔了，这样队列头部的数并不一定是窗口最左边的数。
	    	if(!deque.isEmpty() && deque.peekFirst() == i - k) {
	    		deque.poll();
	    	}
	    	// 把队列尾部所有比新数小的都扔掉，保证队列是降序的
	    	// 当我们遇到新的数时，将新的数和双向队列的末尾比较，如果末尾比新数小，则把末尾扔掉，直到该队列的末尾比新数大或者队列为空的时候才住手。
	    	// 这样，我们可以保证队列里的元素是从头到尾降序的，由于队列里只有窗口内的数，所以他们其实就是窗口内第一大，第二大，第三大...的数。
	    	while(!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
	    		deque.removeLast();
	    	}
	    	// 新数加入到队列尾部
	    	deque.offerLast(i);
	    	if(i >= k - 1) {
		    	result[i - k + 1] = nums[deque.peekFirst()];	    		
	    	}
	    }
	    return result;
	}
	
	
	
	
	
	public static void main(String[] args) {
		SlidingWindowMaximum s = new SlidingWindowMaximum();
		int[] nums = {1,3,-1,-3,5,3,6,7};
		int k = 3;
		int[] result = s.maxSlidingWindow2(nums, k);
		for(int i = 0; i < result.length; i++) {
			System.out.print(result[i] + " ");
		}
	}
}
