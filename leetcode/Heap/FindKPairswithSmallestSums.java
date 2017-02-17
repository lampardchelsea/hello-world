
// Solution 1: Brute Force
// Refer to
// https://discuss.leetcode.com/topic/50450/slow-1-liner-to-fast-solutions
// Need to fully scan length1 * length2 times of all pairs and add onto MinPQ,
// then print out required k pairs
public class Solution {
    public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        int length1 = nums1.length;
        int length2 = nums2.length;
        int pairs = length1 * length2;
        List<int[]> result = new ArrayList<int[]>();
        if(length1 == 0 || length2 == 0 || k == 0) {
            return result;
        }
        MinPQ minPQ = new MinPQ(pairs);
        // Create all pairs and insert onto MinPQ
        for(int i = 0; i < length1; i++) {
            for(int j = 0; j < length2; j++) {
                int sum = nums1[i] + nums2[j];
                Pair pair = new Pair(sum, i, j);
                minPQ.insert(pair);
            }
        }
        // Print out required k pairs, come with different cases as
        // k smaller than existing pairs or not
        if(k <= pairs) {
            for(int i = 0; i < k; i++) {
                Pair pair = minPQ.delMin();
                int[] temp = {nums1[pair.index1], nums2[pair.index2]};
                result.add(temp);
            }
        } else {
            for(int i = 0; i < pairs; i++) {
                Pair pair = minPQ.delMin();
                int[] temp = {nums1[pair.index1], nums2[pair.index2]};
                result.add(temp);
            }
        }
        return result;
    }
    
    // Store sum used for MinPQ and related indexes on both arrays
    private class Pair {
        int sum;
        int index1;
        int index2;
        public Pair(int sum, int index1, int index2) {
            this.sum = sum;
            this.index1 = index1;
            this.index2 = index2;
        }
    }
    
    private class MinPQ {
        Pair[] pq;
        int n;
        public MinPQ(int initialCapacity) {
            pq = new Pair[initialCapacity + 1];
            n = 0;
        }
        
        public void insert(Pair x) {
            pq[++n] = x;
            swim(n);
        }
        
        public Pair delMin() {
            Pair min = pq[1];
            exch(1, n--);
            sink(1);
            return min;
        }
        
        public void swim(int k) {
            while(k > 1 && greater(k/2, k)) {
                exch(k/2, k);
                k = k/2;
            }
        }
        
        public void sink(int k) {
            while(k <= n/2) {
                int j = 2 * k;
                if(j < n && greater(j, j + 1)) {
                    j++;
                }
                if(!greater(k, j)) {
                    break;
                }
                exch(k, j);
                k = j;
            }
        }
        
        public boolean greater(int v, int w) {
            return pq[v].sum - pq[w].sum > 0;
        }
        
        public void exch(int v, int w) {
            Pair swap = pq[v];
            pq[v] = pq[w];
            pq[w] = swap;
        }
    }
}

// Solution 2: simple Java O(KlogK) solution with explanation
// Refer to
// https://leetcode.com/problems/find-k-pairs-with-smallest-sums/?tab=Description

import java.util.ArrayList;
import java.util.List;

/**
 * You are given two integer arrays nums1 and nums2 sorted in ascending order and an integer k.
 * Define a pair (u,v) which consists of one element from the first array and one element from the second array.
 * Find the k pairs (u1,v1),(u2,v2) ...(uk,vk) with the smallest sums.
	Example 1:
	Given nums1 = [1,7,11], nums2 = [2,4,6],  k = 3
	Return: [1,2],[1,4],[1,6]
	
	The first 3 pairs are returned from the sequence:
	[1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
	
	Example 2:
	Given nums1 = [1,1,2], nums2 = [1,2,3],  k = 2
	Return: [1,1],[1,1]
	
	The first 2 pairs are returned from the sequence:
	[1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]
	
	Example 3:
	Given nums1 = [1,2], nums2 = [3],  k = 3 
	Return: [1,3],[2,3]
	
	All possible pairs are returned from the sequence:
	[1,3],[2,3]
 *
 *
 *  Solution
 *  Refer to
 *  https://discuss.leetcode.com/topic/50885/simple-java-o-klogk-solution-with-explanation/2
 *  Basic idea: Use min_heap to keep track on next minimum pair sum, and we only need to maintain K possible 
 *  candidates in the data structure.
 *  Some observations: For every numbers in nums1, its best partner(yields min sum) always starts from nums2[0] 
 *  since arrays are all sorted; And for a specific number in nums1, its next candidate should be 
 *  [this specific number] + nums2[current_associated_index + 1], unless out of boundary;)
 *
 *
  Example 1:
     nums1 nums2 k = 4
      16    15
      11    10
       7     9
       1     2
     Initial MinPQ = [] [1, 2] [7, 2] [11, 2] [16, 2]
     k = 4 --> delMin [1, 2] --> result = [1, 2] --> curNums1Val = 1(index1 = 0), nextNums2Val = 9(++index2 = 1) --> insert [1, 9] (1 + 9 = 10, swim to position 2)
             MinPQ = [] [7, 2] [1, 9] [11, 2] [16, 2]
     k = 3 --> delMin [7, 2] --> result = [1, 2] [7, 2] --> curNums1Val = 7(index1 = 1), nextNums2Val = 9(++index2 = 1) --> insert [7, 9] (7 + 9 = 16, swim to position 2)
             MinPQ = [] [1, 9] [7, 9] [11, 2] [16, 2]
     k = 2 --> delMin [1, 9] --> result = [1, 2] [7, 2] [1, 9] --> curNums1Val = 1(index = 0), nextNums2Val = 10(++index2 = 2) --> insert [1, 10] (1 + 10 = 11, swim to position 1)
             MinPQ = [] [1, 10] [11, 2] [16, 2] [7, 9]
     k = 1 --> delMin [1, 10] --> result = [1, 2] [7, 2] [1, 9] [1, 10] --> curNums1Val = 1(index = 0), nextNums2Val = 10(++index2 = 3) --> insert [1, 15] (1 + 15 = 16, swim to position 4)
             MinPQ = [] [11, 2] [7, 9] [16, 2] [1, 15]
     k = 0 terminate and return result
    
  Example 2: (This example detect the issue on additional limitation as "i < length1 && i < k" when initially insert on MinPQ)
     nums1 nums2 k = 2
       2     3       
       1     2
       1     1
     Initial MinPQ = [] [1, 1] [1, 1]
     k = 2 --> delMin [1, 1] --> result = [1, 1] --> curNums1Val = 1(index1 = 0), nextNums2Val = 2(++index2 = 1) --> insert [1, 2] (1 + 2 = 3, swim to position 2)
             MinPQ = [] [1, 1] [1, 2]
     k = 1 --> delMin [1, 1] --> result = [1, 1] [1, 1] --> curNums1Val = 1(index1 = 1), nextNums2Val = 2(++index2 = 1) --> insert [1, 2] (1 + 2 = 3, swim to position 2)
             MinPQ = [] [1, 2] [1, 2]
     k = 0 terminate and return result
 * 
 * Execution time = 6ms, O(k * logk) much faster than Solution 1 = 42ms, O(k*k)
 */
public class FindKPairswithSmallestSums {
    public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        int length1 = nums1.length;
        int length2 = nums2.length;
        int pairs = Math.min(k, length1 * length2);
        List<int[]> result = new ArrayList<int[]>();
        if(length1 == 0 || length2 == 0 || k == 0) {
            return result;
        }
        MinPQ minPQ = new MinPQ(pairs);
        for(int i = 0; i < length1 && i < k; i++) {
            int sum = nums1[i] + nums2[0];
            Pair pair = new Pair(sum, i, 0);
            minPQ.insert(pair);
        }
        // runs k times -> logk, logk, logk ...
        while(k-- > 0 && !minPQ.isEmpty()) {
            Pair minPair = minPQ.delMin();
            int[] temp = {nums1[minPair.index1], nums2[minPair.index2]};
            result.add(temp);
            if(minPair.index2 == length2 - 1) {
                continue;
            }
            // The next inserted pair(candidate) depends on current deleted minPair from MinPQ
            int curNums1Val = nums1[minPair.index1];
            int nextNums2Val = nums2[++minPair.index2];
            int sum = curNums1Val + nextNums2Val;
            Pair candidate = new Pair(sum, minPair.index1, minPair.index2);
            minPQ.insert(candidate);
        }
        return result;
    }
    
    private class Pair {
        int sum;
        int index1;
        int index2;
        public Pair(int sum, int index1, int index2) {
            this.sum = sum;
            this.index1 = index1;
            this.index2 = index2;
        }
    }
    
    private class MinPQ {
        Pair[] pq;
        int n;
        public MinPQ(int initialCapacity) {
            pq = new Pair[initialCapacity + 1];
            n = 0;
        }
        
        public void insert(Pair x) {
            pq[++n] = x;
            swim(n);
        }
        
        public Pair delMin() {
            Pair min = pq[1];
            exch(1, n--);
            // Avoid loitering and help with garbage collection
            pq[n+1] = null;
            sink(1);
            return min;
        }
        
        public void swim(int k) {
            while(k > 1 && greater(k/2, k)) {
                exch(k/2, k);
                k = k/2;
            }
        }
        
        public void sink(int k) {
            while(k <= n/2) {
                int j = 2 * k;
                if(j < n && greater(j, j + 1)) {
                    j++;
                }
                if(!greater(k, j)) {
                    break;
                }
                exch(k, j);
                k = j;
            }
        }
        
        public boolean greater(int v, int w) {
            return pq[v].sum - pq[w].sum > 0;
        }
        
        public void exch(int v, int w) {
            Pair swap = pq[v];
            pq[v] = pq[w];
            pq[w] = swap;
        }
        
        public boolean isEmpty() {
            return n == 0;
        }
    }
    
    public static void main(String[] args) {
    	FindKPairswithSmallestSums f = new FindKPairswithSmallestSums();
    	int[] nums1 = {1, 7, 11, 16};
    	int[] nums2 = {2, 9, 10, 15};
    	int k = 4;
//    	int[] nums1 = {1, 1, 2};
//    	int[] nums2 = {1, 2, 3};
//    	int k = 2;
    	List<int[]> result = f.kSmallestPairs(nums1, nums2, k);
    	for(int i = 0; i < result.size(); i++) {
    		int[] temp = result.get(i);
    		System.out.println("[" + temp[0] + ", " + temp[1] + "]");
    	}
    }
}


