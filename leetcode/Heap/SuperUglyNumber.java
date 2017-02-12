/**
 * https://leetcode.com/problems/super-ugly-number/
 * 
 * Write a program to find the nth super ugly number.
 * Super ugly numbers are positive numbers whose all prime factors are in the 
 * given prime list primes of size k. For example, [1, 2, 4, 7, 8, 13, 14, 16, 19, 26, 28, 32] 
 * is the sequence of the first 12 super ugly numbers given primes = [2, 7, 13, 19] of size 4.
 * Note:
 * (1) 1 is a super ugly number for any given primes.
 * (2) The given numbers in primes are in ascending order.
 * (3) 0 < k ≤ 100, 0 < n ≤ 106, 0 < primes[i] < 1000.
 * (4) The nth super ugly number is guaranteed to fit in a 32-bit signed integer.
 */

/**
 * Solution 1: Heap
 * Refer to
 * https://discuss.leetcode.com/topic/34841/java-three-methods-23ms-36-ms-58ms-with-heap-performance-explained
 * http://www.cnblogs.com/grandyang/p/5144918.html
 * http://algs4.cs.princeton.edu/24pq/MinPQ.java.html
 * 
 * 这道题让我们求超级丑陋数，是之前那两道Ugly Number 丑陋数和Ugly Number II 丑陋数之二的延伸，质数集合可以任意给定，
 * 这就增加了难度。但是本质上和Ugly Number II 丑陋数之二没有什么区别，由于我们不知道质数的个数，我们可以用一个idx数组
 * 来保存当前的位置，然后我们从每个子链中取出一个数，找出其中最小值，然后更新idx数组对应位置，注意有可能最小值不止一个，
 * 要更新所有最小值的位置
*/
public class Solution {
    public int nthSuperUglyNumber(int n, int[] primes) {
        int[] ugly = new int[n];
        ugly[0] = 1;
        // Each prime number obtains an ugly array, which
        // constructed by previous ugly number multiple
        // this prime number, the index_array record the 
        // changed index on each array
        int[] index_array = new int[primes.length];
        // Initialize factor_array
        int[] factor_array = new int[primes.length];
        for(int i = 0; i < primes.length; i++) {
            factor_array[i] = primes[i] * ugly[index_array[i]];
        }
        // Start loop to fill the ugly array
        for(int i = 1; i < n; i++) {
            int min = min(factor_array);
            ugly[i] = min;
            for(int j = 0; j < factor_array.length; j++) {
                // Update index and factor in corresponding prime 
                // number ugly array, as matched maybe not only
                // one, need to update all matched result
                if(min == factor_array[j]) {
                    index_array[j] = index_array[j] + 1;
                    factor_array[j] = primes[j] * ugly[index_array[j]];
                }
            }
        }
        return ugly[n - 1];
    }
    
    public int min(int[] array) {
        // Generate a minimum priority queue to sort and find
        // the minimum item in current array
        MinPQ minPQ = new MinPQ(array.length);
        for(int i = 0; i < array.length; i++) {
            minPQ.insert(array[i]);
        }
        return minPQ.delMin();
    }
    
    // Minimum priority queue
    // Refer to
    // http://algs4.cs.princeton.edu/24pq/MinPQ.java.html
    private class MinPQ {
        int[] pq;
        int n;
        public MinPQ(int initialCapacity) {
            pq = new int[initialCapacity + 1];
            n = 0;
        }
        
        public void insert(int x) {
            pq[++n] = x;
            swim(n);
        }
        
        public int delMin() {
            int min = pq[1];
            exch(1, n--);
            sink(1);
            return min;
        }
        
        public boolean greater(int v, int w) {
            return (pq[v] - pq[w]) > 0;
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
        
        public void swim(int k) {
            while(k > 1 && greater(k/2, k)) {
                exch(k/2, k);
                k = k/2;
            }
        }
        
        public void exch(int v, int w) {
            int swap = pq[v];
            pq[v] = pq[w];
            pq[w] = swap;
        }
    }
}

// Solution 2:
// Refer to
// https://discuss.leetcode.com/topic/34841/java-three-methods-23ms-36-ms-58ms-with-heap-performance-explained
public class Solution {
    public int nthSuperUglyNumber(int n, int[] primes) {
        int[] ugly = new int[n];
        int[] idx = new int[primes.length];
    
        ugly[0] = 1;
        for (int i = 1; i < n; i++) {
            //find next
            ugly[i] = Integer.MAX_VALUE;
            for (int j = 0; j < primes.length; j++)
                ugly[i] = Math.min(ugly[i], primes[j] * ugly[idx[j]]);
            
            //slip duplicate
            for (int j = 0; j < primes.length; j++) {
                while (primes[j] * ugly[idx[j]] <= ugly[i]) idx[j]++;
            }
        }
    
        return ugly[n - 1];
    }
}
