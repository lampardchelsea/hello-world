/**
 * Refer to
 * https://leetcode.com/problems/kth-largest-element-in-an-array/
 * Find the kth largest element in an unsorted array. Note that it is the kth largest 
 * element in the sorted order, not the kth distinct element.
   For example,
   Given [3,2,1,5,6,4] and k = 2, return 5.
 * Note: 
 * You may assume k is always valid, 1 ≤ k ≤ array's length
*/

// Soluton 1: Max Heap
public class Solution {
    public int findKthLargest(int[] nums, int k) {
        int length = nums.length;
        MaxPQ maxPQ = new MaxPQ(length);
        for(int i = 0; i < length; i++) {
            maxPQ.insert(nums[i]);
        }
        for(int i = 0; i < k - 1; i++) {
            maxPQ.delMax();
        }
        return maxPQ.delMax();
    }
    
    private class MaxPQ {
        int[] pq;
        int n;
        public MaxPQ(int initialCapacity) {
            pq = new int[initialCapacity + 1];
            n = 0;
        }
        
        public void insert(int x) {
            pq[++n] = x;
            swim(n);
        }
        
        public int delMax() {
            int max = pq[1];
            exch(1, n--);
            sink(1);
            return max;
        }
        
        public void swim(int k) {
            while(k > 1 && less(k/2, k)) {
                exch(k/2, k);
                k = k/2;
            } 
        }
        
        public void sink(int k) {
            while(k <= n/2) {
                int j = 2 * k;
                if(j < n && less(j, j + 1)) {
                    j++;
                }
                if(!less(k, j)) {
                    break;
                }
                exch(k, j);
                k = j;
            }
        }
        
        public void exch(int v, int w) {
            int swap = pq[v];
            pq[v] = pq[w];
            pq[w] = swap;
        }
        
        public boolean less(int v, int w) {
            // The error prone format here
            // return pq[v] - pq[w] < 0;
            // but as this question will find the kth largest number, and -2147483648 is surely
            // sit at the last position, so will not reflect problem here
    		   // Refer to
    		   // https://github.com/lampardchelsea/hello-world/blob/master/leetcode/array/ThirdMaximumNumber.java
            
            return pq[v] < pq[w];
        }
    }
}

// Solution 2:
// Refer to
// http://www.geeksforgeeks.org/kth-smallestlargest-element-unsorted-array/
public int findKthLargest(int[] nums, int k) {
        final int N = nums.length;
        Arrays.sort(nums);
        return nums[N - k];
}




