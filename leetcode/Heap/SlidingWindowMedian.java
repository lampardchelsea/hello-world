import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Refer to
 * https://leetcode.com/problems/sliding-window-median/description/
 * http://www.lintcode.com/en/problem/sliding-window-median/
 * Median is the middle value in an ordered integer list. If the size of the list is even, 
 * there is no middle value. So the median is the mean of the two middle value.

	Examples: 
	[2,3,4] , the median is 3
	[2,3], the median is (2 + 3) / 2 = 2.5
	
	Given an array nums, there is a sliding window of size k which is moving from the very 
	left of the array to the very right. You can only see the k numbers in the window. 
	Each time the sliding window moves right by one position. Your job is to output the 
	median array for each window in the original array.
	
	For example,
	Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.
	
	Window position                Median
	---------------               -----
	[1  3  -1] -3  5  3  6  7       1
	 1 [3  -1  -3] 5  3  6  7       -1
	 1  3 [-1  -3  5] 3  6  7       -1
	 1  3  -1 [-3  5  3] 6  7       3
	 1  3  -1  -3 [5  3  6] 7       5
	 1  3  -1  -3  5 [3  6  7]      6
	Therefore, return the median sliding window as [1,-1,-1,3,5,6].

    Note: 
    You may assume k is always valid, ie: k is always smaller than input array's size for non-empty array.
 * 
 * 
 * 
 * Solution
 * https://discuss.leetcode.com/topic/74724/java-solution-using-two-priorityqueues
 * Almost the same idea of Find Median from Data Stream https://leetcode.com/problems/find-median-from-data-stream/
 * Use two Heaps to store numbers. maxHeap for numbers smaller than current median, minHeap 
 * for numbers bigger than and equal to current median. A small trick I used is always make 
 * size of minHeap equal (when there are even numbers) or 1 element more (when there are odd numbers) 
 * than the size of maxHeap. Then it will become very easy to calculate current median.
 * Keep adding number from the right side of the sliding window and remove number from left side of 
 * the sliding window. And keep adding current median to the result.
 * 
 * https://stackoverflow.com/questions/12535095/java-integers-min-value-negative-then-compare  
 * https://stackoverflow.com/questions/12719066/priority-queue-remove-complexity-time
 */
public class SlidingWindowMedian {
	// minPQ store larger half of input (>= median)
    // maxPQ store smaller half of input (strictly < median)
	PriorityQueue<Integer> minPQ;
	PriorityQueue<Integer> maxPQ;
	public SlidingWindowMedian(int k) {
	    this.minPQ = new PriorityQueue<Integer>();
	    this.maxPQ = new PriorityQueue<Integer>(k, new Comparator<Integer>() {
	        /**
	         * Why must use compareTo ?
	           Input: nums = [-2147483648,-2147483648,2147483647,-2147483648,-2147483648,-2147483648,2147483647,2147483647,2147483647,2147483647,-2147483648,2147483647,-2147483648] and k = 3
	           Output: [-2147483648.00000,-2147483648.00000,-2147483648.00000,-2147483648.00000,-2147483648.00000,2147483647.00000,2147483647.00000,2147483647.00000,-2147483648.00000,-2147483648.00000,-2147483648.00000]
	           Expected: [-2147483648.00000,-2147483648.00000,-2147483648.00000,-2147483648.00000,-2147483648.00000,2147483647.00000,2147483647.00000,2147483647.00000,2147483647.00000,2147483647.00000,-2147483648.00000]
	           
	           Use below print log to check why:
	           -2147483648 minus -2147483648 equals 0
               -2147483648 compareTo -2147483648 equals 0
               ----------------
               -2147483648 minus -2147483648 equals 0
               -2147483648 compareTo -2147483648 equals 0
               ----------------
               2147483647 minus -2147483648 equals -1 --> This is wrong
               2147483647 compareTo -2147483648 equals 1
               ----------------
               2147483647 minus -2147483648 equals -1 --> This is wrong
               2147483647 compareTo -2147483648 equals 1
               ----------------
               Refer to
               Java Integers Min_Value negative then compare ?
               https://stackoverflow.com/questions/12535095/java-integers-min-value-negative-then-compare              
               Because of silent integer overflow: Integer.MIN_VALUE is -2^31 and Integer.MAX_VALUE is 2^31-1, 
               so -Integer.MIN_VALUE is 2^31, which is Integer.MAX_VALUE + 1, which by definition is too large 
               for an integer. So it overflows and becomes Integer.MIN_VALUE...
               You can also check that:
               System.out.println(Integer.MAX_VALUE + 1);
	        */
	        public int compare(Integer a, Integer b) {
	        	System.out.println(b + " minus " + a + " equals " + (b - a));
	        	System.out.println(b + " compareTo " + a + " equals " + b.compareTo(a));
	        	System.out.println("----------------");
	            //return b - a;
	            return b.compareTo(a);
	        } 
	    });
	}
    
    public double[] medianSlidingWindow(int[] nums, int k) {
        int n = nums.length - k + 1;
        if(n <= 0) {
            return new double[0];
        }
        double[] result = new double[n];
	// Caution: The condition here is i <= nums.length, not normal i < nums.length,
	// E.g as given example in problem, for 8 numbers and k = 3, we need 6 rounds
	// to go through, result build up since i = 3 to i = nums.length = 8 (6 rounds)
        for(int i = 0; i <= nums.length; i++) {
            if(i >= k) {
                result[i - k] = getMedian();
                // Refer to
                // Priority Queue remove complexity time ?
                // https://stackoverflow.com/questions/12719066/priority-queue-remove-complexity-time
                // The confusion is actually caused by your "remove" function. In java, there are two remove functions.
                // remove() -> This is to remove the head/root, it takes O(logN) time.
                // remove(Object o) -> This is to remove an arbitrary object. Finding this object takes 
                //                     O(N) time, and removing it takes O(logN) time.
                remove(nums[i - k]);
            }
            if(i < nums.length) {
                add(nums[i]);
            }
        }
        return result;
    }
    
    private void add(int num) {
	// Tricky Point: Not like Data Stream Median,
	// Refer to
	// https://github.com/lampardchelsea/hello-world/blob/master/lintcode/DataStructure/VideoExamples/Heap/DataStreamMedian.java
	// we compare new 'num' with maxPQ.peek(), we compare 'num' with 'median',
	// which also require an initialized status of 'median' value, check 'getMedian()' method
        if(num < getMedian()) {
            maxPQ.add(num);
        } else {
            minPQ.add(num);
        }
        // Re-balance
        if(minPQ.size() > maxPQ.size() + 1) {
            maxPQ.add(minPQ.poll());
        } else if(maxPQ.size() >= minPQ.size() + 1) {
            minPQ.add(maxPQ.poll());
        }
    }
    
    private void remove(int num) {
        if(num < getMedian()) {
            maxPQ.remove(num);
        } else {
            minPQ.remove(num);
        }
        // Re-balance
        if(minPQ.size() > maxPQ.size() + 1) {
            maxPQ.add(minPQ.poll());
        } else if(maxPQ.size() >= minPQ.size() + 1) {
            minPQ.add(maxPQ.poll());
        }
    }
    
    private double getMedian() {
        // Initial base case when both pq empty, return 0
        if(minPQ.size() == 0 && maxPQ.size() == 0) {
            return 0;
        }
        if(minPQ.size() == maxPQ.size()) {
            return ((double)minPQ.peek() + (double)maxPQ.peek()) / 2.0;
        } else {
            // Based on set up, minPQ store ones smaller or equal to median
            // maxPQ store ones larger than median, it means minPQ always
            // has chance size larger than maxPQ, and always only larger
            // as 1 with help of re-balance strategy in add() method
            return (double)minPQ.peek();
        }
    }
    
    public static void main(String[] args) {
    	int k = 3;
    	SlidingWindowMedian s = new SlidingWindowMedian(k);
    	int[] nums = {-2147483648,-2147483648,2147483647,-2147483648,-2147483648,-2147483648,2147483647,2147483647,2147483647,2147483647,-2147483648,2147483647,-2147483648};
    	double[] result = s.medianSlidingWindow(nums, k);
    	for(double d : result) {
    		System.out.print(d + " ");
    	}
    }
}
