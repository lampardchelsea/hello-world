// Time limit exceeded solution
import java.util.ArrayList;
import java.util.List;

/**
 * Refer to
 * https://leetcode.com/problems/find-median-from-data-stream/?tab=Description
 * 
 * Median is the middle value in an ordered integer list. If the size of the list is even, 
 * there is no middle value. So the median is the mean of the two middle value.
   Examples: 
	[2,3,4] , the median is 3
	
	[2,3], the median is (2 + 3) / 2 = 2.5
	
 * Design a data structure that supports the following two operations:
 * void addNum(int num) - Add a integer number from the data stream to the data structure.
 * double findMedian() - Return the median of all elements so far.
   For example:
	addNum(1)
	addNum(2)
	findMedian() -> 1.5
	addNum(3) 
	findMedian() -> 2
 *
 * Solution
 * MinPQ
 * http://algs4.cs.princeton.edu/24pq/MinPQ.java.html
 * How to calculate the median of an array ?
 * http://stackoverflow.com/questions/11955728/how-to-calculate-the-median-of-an-array
 */
public class MedianFinder {
    List<Integer> list;
    MinPQ minPQ;
    
    /** initialize your data structure here. */
    public MedianFinder() {
    	// The list used for store each time loop result from
    	// changed minPQ, note: need to refresh(clear) before
    	// newly insertion
        list = new ArrayList<Integer>();
        minPQ = new MinPQ();
    }
    
    public void addNum(int num) {
    	// Every time insert onto minPQ 
        minPQ.insert(num);
    }
    
    public double findMedian() {
    	  // Cannot use a for loop against a varying size object "minPQ"
    	  // need to use while loop with isEmpty() checking
//        for(int i = 0; i < minPQ.size(); i++) {
//            int temp = minPQ.delMin();
//            list.add(temp);
//            // Important: Re-insert again to maintain the original data on MinPQ, 
//            // but here is wrong place, as if directly insert back, next loop
//            // will still delete this value as it will automatically swim to 
//            // first place of minPQ.
//            //minPQ.insert(temp);
//        }
    	
    	// If repeatedly calling findMedian() need to remove
    	// previous result in list before new round insert
    	if(list.size() != 0) {
    		list.clear();
    	}
    	
    	while(!minPQ.isEmpty()) {
    		int temp = minPQ.delMin();
    		list.add(temp);
    	}
    	
        // Insert back to minPQ to prepare possible next addNum()
        for(int i = 0; i < list.size(); i++) {
        	minPQ.insert(list.get(i));
        }
        
        if(list.size() % 2 == 0) {
            int index = list.size() / 2;
            // To get real double value require (double) cast on each section
            return ((double)list.get(index) + (double)list.get(index - 1)) / 2;
        } else {
            int index = list.size() / 2;
            return (double)list.get(index);
        }
    }
    
    private class MinPQ {
        int[] pq;
        int n;
        public MinPQ(int initialCapacity) {
            pq = new int[initialCapacity + 1];
            n = 0;
        }
        
        public MinPQ() {
            this(1);
        }
        
        public void insert(int x) {
            if(n == pq.length - 1) {
                resize(2 * pq.length);
            }
            pq[++n] = x;
            swim(n);
        }
        
        public int delMin() {
            int min = pq[1];
            exch(1, n--);
            sink(1);
            pq[n + 1] = 0;
            if((n > 0) && (n == (pq.length - 1) / 4)) {
                resize(pq.length / 2);
            }
            return min;
        }
        
        // Introduce resize() method is necessary for this case, 
        // as we cannot assume how many times addNum() will be
        // called, so the size of minPQ will be varying
        public void resize(int len) {
            int[] temp = new int[len];
            for(int i = 1; i <= n; i++) {
                temp[i] = pq[i];
            }
            pq = temp;
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
	/**
         * Refer to
         * http://stackoverflow.com/questions/12535095/java-integers-min-value-negative-then-compare
         * The change on compare format is really tricky, the exception can be test by
         * {-2147483648, 1, 1}, the first number -2147483648 actually the Integer.MIN_VALUE,
         * if we use pq[v] - pq[w] = -2147483648 - 1, the result is 2147483647, which is
         * Integer.MAX_VALUE, not satisfy < 0, which we expected. We need to change compare
         * format to pq[v] < pq[w] without 'minus' operation
         */	
//             return pq[v] - pq[w] > 0;
	     return pq[v] > pq[w];
        }
        
        public void exch(int v, int w) {
            int swap = pq[v];
            pq[v] = pq[w];
            pq[w] = swap;
        }
        
        public boolean isEmpty() {
        	return n == 0;
        }
    }
    
    public static void main(String[] args) {
    	// ["MedianFinder","addNum","addNum","findMedian","addNum","findMedian"]
    	// [[],[1],[2],[],[3],[]]
    	MedianFinder mf = new MedianFinder();
    	mf.addNum(1);
    	mf.addNum(2);
    	double result1 = mf.findMedian();
    	System.out.println(result1);
    	mf.addNum(3);
    	double result2 = mf.findMedian();
    	System.out.println(result2);
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */


// Solution: MaxPQ and MinPQ
/**
 * Refer to
 * https://leetcode.com/problems/find-median-from-data-stream/?tab=Description
 * 
 * Median is the middle value in an ordered integer list. If the size of the list is even, 
 * there is no middle value. So the median is the mean of the two middle value.
   Examples: 
	[2,3,4] , the median is 3
	
	[2,3], the median is (2 + 3) / 2 = 2.5
	
 * Design a data structure that supports the following two operations:
 * void addNum(int num) - Add a integer number from the data stream to the data structure.
 * double findMedian() - Return the median of all elements so far.
   For example:
	addNum(1)
	addNum(2)
	findMedian() -> 1.5
	addNum(3) 
	findMedian() -> 2
 *
 * Solution:
 * Refer to
 * https://segmentfault.com/a/1190000003709954
 * 最大最小堆
 * 复杂度
 * 时间 O(NlogN) 空间 O(N)
 * 思路
 * 维护一个最大堆，一个最小堆。最大堆存的是到目前为止较小的那一半数，最小堆存的是到目前为止较大的那一半数，这样中位数
 * 只有可能是堆顶或者堆顶两个数的均值。而维护两个堆的技巧在于判断堆顶数和新来的数的大小关系，还有两个堆的大小关系。
 * 我们将新数加入堆后，要保证两个堆的大小之差不超过1。先判断堆顶数和新数的大小关系，有如下三种情况：最小堆堆顶小于新数时，
 * 说明新数在所有数的上半部分。最小堆堆顶大于新数，但最大堆堆顶小于新数时，说明新数将处在最小堆堆顶或最大堆堆顶，也就是
 * 一半的位置。最大堆堆顶大于新数时，说明新数将处在所有数的下半部分。再判断两个堆的大小关系，如果新数不在中间，那目标堆
 * 不大于另一个堆时，将新数加入目标堆，否则将目标堆的堆顶加入另一个堆，再把新数加入目标堆。如果新数在中间，那加到大小较
 * 小的那个堆就行了（一样大的话随便，代码中是加入最大堆）。这样，每次新加进来一个数以后，如果两个堆一样大，则中位数是两个
 * 堆顶的均值，否则中位数是较大的那个堆的堆顶。
 */
public class MedianFinder {
    MaxPQ maxPQ;
    MinPQ minPQ;
    
    /** initialize your data structure here. */
    public MedianFinder() {
        maxPQ = new MaxPQ();
        minPQ = new MinPQ();
    }
    
    /**
     * The smaller half of data stream will add onto MaxPQ, 
     * the larger half will add onto MinPQ, basic idea is 
     * the median will generate from peek of both PQ or from 
     * newly added num which will locate on either MaxPQ or MinPQ
     * Example 1: 
     * Given 1, 2, 3, 4, 5, 6
     * MaxPQ(store smaller half)       MinPQ(store larger half)
     *   3                               4
     *   1                               5
     *   2                               6
     * The peek of MaxPQ is 3, peek of MinPQ is 4, as equal size = 3,
     * the median is between (double)(3 + 4) / 2 = 3.5
     *   
     * Example 2:
     * Given 1, 2, 3, 4, 5, 6, 7
     * MaxPQ(store smaller half)       MinPQ(store larger half)
     *                                   4
     *   3                               5
     *   1                               6
     *   2                               7
     * The peek of MaxPQ is 3, peek of MinPQ is 4, MinPQ size > MaxPQ size,
     * the median is from MinPQ peek = 4.0
     */
    public void addNum(int num) {
    	// If MaxPQ is empty or the number is smaller than peek,
    	// add number into MaxPQ, the first time comparison
    	// between number and peek depends on Integer.MAX_VALUE
    	// as MaxPQ is empty
        if(maxPQ.isEmpty() || num <= maxPQ.peek()) {
        	// If MaxPQ size is larger than MinPQ size, need to
        	// make a balance by moving the peek of MaxPQ into
        	// MinPQ, which make sure the size difference <= 1
            if(maxPQ.size() > minPQ.size()) {
                minPQ.insert(maxPQ.delMax());
            }
            // Then add the new number to MaxPQ
            maxPQ.insert(num);
        } 
    	// If MinPQ is empty or the number is larger than peek,
    	// add number into MinPQ, the first time comparison
    	// between number and peek depends on Integer.MIN_VALUE
    	// as MinPQ is empty
        else if(minPQ.isEmpty() || num > minPQ.peek()) {
        	// If MinPQ size is larger than MaxPQ size, need to
        	// make a balance by moving the peek of MinPQ into
        	// MaxPQ, which make sure the size difference <= 1
            if(minPQ.size() > maxPQ.size()) {
                maxPQ.insert(minPQ.delMin());
            }
            // Then add the new number to MinPQ
            minPQ.insert(num);
        } 
        // If the number is smaller than MinPQ peek
        // and larger than MaxPQ peek, then return
        // size larger PQ's peek
        else {
            if(maxPQ.size() <= minPQ.size()) {
                maxPQ.insert(num);
            } else {
                minPQ.insert(num);
            }
        }
    }
    
    public double findMedian() {
        if(maxPQ.size() > minPQ.size()) {
            return (double)maxPQ.peek();
        } else if(minPQ.size() > maxPQ.size()) {
            return (double)minPQ.peek();
        } else if(minPQ.isEmpty() && maxPQ.isEmpty()) {
            return 0;
        } else {
            return ((double)maxPQ.peek() + (double)minPQ.peek()) / 2;
        }
    }
    
    private class MaxPQ {
        int[] pq;
        int n;
        public MaxPQ(int initialCapacity) {
            pq = new int[initialCapacity + 1];
            n = 0;
        }
        
        public MaxPQ() {
            this(1);
        }
        
        public void insert(int x) {
            if(n == pq.length - 1) {
                resize(2 * pq.length);
            }
            pq[++n] = x;
            swim(n);
        }
        
        public int delMax() {
            int max = pq[1];
            exch(1, n--);
            sink(1);
            pq[n + 1] = 0;
            if((n > 0) && (n == (pq.length - 1) / 4)) {
                resize(pq.length / 2);
            }
            return max;
        }
        
        public void resize(int len) {
            int[] temp = new int[len];
            for(int i = 1; i <= n; i++) {
                temp[i] = pq[i];
            }
            pq = temp;
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
        
        public boolean less(int v, int w) {
         /**
         * Refer to
         * http://stackoverflow.com/questions/12535095/java-integers-min-value-negative-then-compare
         * The change on compare format is really tricky, the exception can be test by
         * {-2147483648, 1, 1}, the first number -2147483648 actually the Integer.MIN_VALUE,
         * if we use pq[v] - pq[w] = -2147483648 - 1, the result is 2147483647, which is
         * Integer.MAX_VALUE, not satisfy < 0, which we expected. We need to change compare
         * format to pq[v] < pq[w] without 'minus' operation
         */   
//             return pq[v] - pq[w] < 0;
	     return pq[v] < pq[w];
        }
        
        public void exch(int v, int w) {
            int swap = pq[v];
            pq[v] = pq[w];
            pq[w] = swap;
        }
        
        public boolean isEmpty() {
            return n == 0;
        }
        
        public int size() {
            return n;
        }
        // Adding peek method, if empty for MaxPQ should
        // return Integer.MAX_VALUE as peek value,
        // otherwise return peek value as pq[1]
        public int peek() {
            if(isEmpty()) {
               return Integer.MAX_VALUE; 
            }
            return pq[1];
        }
    }
    
    private class MinPQ {
        int[] pq;
        int n;
        public MinPQ(int initialCapacity) {
            pq = new int[initialCapacity + 1];
            n = 0;
        }
        
        public MinPQ() {
            this(1);
        }
        
        public void insert(int x) {
            if(n == pq.length - 1) {
                resize(2 * pq.length);
            }
            pq[++n] = x;
            swim(n);
        }
        
        public int delMin() {
            int min = pq[1];
            exch(1, n--);
            sink(1);
            pq[n + 1] = 0;
            if((n > 0) && (n == (pq.length - 1) / 4)) {
                resize(pq.length / 2);
            }
            return min;
        }
        
        public void resize(int len) {
            int[] temp = new int[len];
            for(int i = 1; i <= n; i++) {
                temp[i] = pq[i];
            }
            pq = temp;
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
//             return pq[v] - pq[w] > 0;
	     return pq[v] > pq[w];
        }
        
        public void exch(int v, int w) {
            int swap = pq[v];
            pq[v] = pq[w];
            pq[w] = swap;
        }
        
        public boolean isEmpty() {
        	return n == 0;
        }
        
        public int size() {
            return n;
        }
        // Adding peek method, if empty for MinPQ should
        // return Integer.MIN_VALUE as peek value, 
        // otherwise return peek value as pq[1]
        public int peek() {
            if(isEmpty()) {
               return Integer.MIN_VALUE; 
            }
            return pq[1];
        }
    }
    
    public static void main(String[] args) {
    	// ["MedianFinder","addNum","addNum","findMedian","addNum","findMedian"]
    	// [[],[1],[2],[],[3],[]]
    	MedianFinder mf = new MedianFinder();
    	mf.addNum(1);
    	mf.addNum(2);
    	double result1 = mf.findMedian();
    	System.out.println(result1);
    	mf.addNum(3);
    	mf.addNum(4);
    	mf.addNum(5);
    	mf.addNum(6);
    	mf.addNum(7);
    	double result2 = mf.findMedian();
    	System.out.println(result2);
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */


