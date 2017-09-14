/**
 * Refer to
 * http://www.lintcode.com/en/problem/sliding-window-median/
 * Given an array of n integer, and a moving window(size k), move the window at each iteration from 
    the start of the array, find the median of the element inside the window at each moving. 
    (If there are even numbers in the array, return the N/2-th number after sorting the element in the window. )

    Have you met this question in a real interview? Yes
    Example
    For array [1,2,7,8,5], moving window size k = 3. return [2,7,7]

    At first the window is at the start of the array like this

    [ | 1,2,7 | ,8,5] , return the median 2;

    then the window move one step forward.

    [1, | 2,7,8 | ,5], return the median 7;

    then the window move one step forward again.

    [1,2, | 7,8,5 | ], return the median 7;
 *
 * Solution
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Heap/SlidingWindowMedian.java
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Heap/SlidingWindowMedian_ExchangeMaxPQMinPQCondition_Version.java
 * Note: Only the SlidingWindowMedian_ExchangeMaxPQMinPQCondition_Version works for Lintcode because the definition
 *       of median between Lintcode and Leetcode
*/
public class Solution {
    /*
     * @param nums: A list of integers
     * @param k: An integer
     * @return: The median of the element inside the window at each moving
     */
    private PriorityQueue<Integer> minPQ;
    private PriorityQueue<Integer> maxPQ;
    
    public List<Integer> medianSlidingWindow(int[] nums, int k) {
        int n = nums.length - k + 1;
        List<Integer> result = new ArrayList<Integer>();
        if(nums == null || n <= 0 || k == 0) {
            return result;
        }
        minPQ = new PriorityQueue<Integer>();
        maxPQ = new PriorityQueue<Integer>(k, new Comparator<Integer>() {
             public int compare(Integer a, Integer b) {
                 return b.compareTo(a);
             } 
        });
        for(int i = 0; i <= nums.length; i++) {
            if(i >= k) {
                result.add(getMedian());
                remove(nums[i - k]);
            }
            if(i < nums.length) {
                add(nums[i]);
            }
        }
        return result;
    }
    
    private void add(int num) {
        if(num <= getMedian()) {
            maxPQ.add(num);
        } else {
            minPQ.add(num);
        }
        if(minPQ.size() >= maxPQ.size() + 1) {
            maxPQ.add(minPQ.poll());
        } else if(maxPQ.size() > minPQ.size() + 1) {
            minPQ.add(maxPQ.poll());
        }
    }
    
    private void remove(int num) {
        if(num <= getMedian()) {
            maxPQ.remove(num);
        } else {
            minPQ.remove(num);
        }
        if(minPQ.size() >= maxPQ.size() + 1) {
            maxPQ.add(minPQ.poll());
        } else if(maxPQ.size() > minPQ.size() + 1) {
            minPQ.add(maxPQ.poll());
        }
    }
    
    private Integer getMedian() {
        if(minPQ.size() == 0 && maxPQ.size() == 0) {
            return 0;
        }
        // if(minPQ.size() == maxPQ.size()) {
        //     return (minPQ.peek() + maxPQ.peek()) / 2;
        // } else {
        //     return maxPQ.peek();   
        // }
        // This change because median definition difference betweeen Lintcode and Leetcode
        return maxPQ.peek();
    }
}
