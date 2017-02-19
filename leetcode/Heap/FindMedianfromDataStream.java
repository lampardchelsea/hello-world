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
 * https://discuss.leetcode.com/topic/27521/short-simple-java-c-python-o-log-n-o-1
 * https://segmentfault.com/a/1190000003709954
 * http://wiki.jikexueyuan.com/project/for-offer/question-sixty-four.html
 * http://algs4.cs.princeton.edu/24pq/MinPQ.java.html
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
            return pq[v] - pq[w] > 0;
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
