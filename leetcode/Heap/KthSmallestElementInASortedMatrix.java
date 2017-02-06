/**
 * Given a n x n matrix where each of the rows and columns are sorted in ascending order, 
 * find the kth smallest element in the matrix.
 * Note that it is the kth smallest element in the sorted order, not the kth distinct element.
 * 
 * Example:
	matrix = [
	   [ 1,  5,  9],
	   [10, 11, 13],
	   [12, 13, 15]
	],
	k = 8,
	
	return 13.
 *	
 * Note: You may assume k is always valid, 1 ≤ k ≤ n2.
 *
 */

/**
 * Solution 1: Heap Sort
 * Refer to
 * http://www.cnblogs.com/grandyang/p/5727892.html
 * 这道题让我们求有序矩阵中第K小的元素，这道题的难点在于数组并不是蛇形有序的，意思是当前行的最后一个元素并不一定会
 * 小于下一行的首元素，所以我们并不能直接定位第K小的元素，所以只能另辟蹊径。先来看一种利用堆的方法，我们使用一个
 * 最大堆，然后遍历数组每一个元素，将其加入堆，根据最大堆的性质，大的元素会排到最前面，然后我们看当前堆中的元素个数
 * 是否大于k，大于的话就将首元素去掉，循环结束后我们返回堆中的首元素即为所求
 */
public class KthSmallestElementInASortedMatrix {
    public int kthSmallest(int[][] matrix, int k) {
    	int rows = matrix.length;
    	int columns = matrix[0].length;
      // Initial max priority queue based on helper class
      MaxPQ maxPQ = new MaxPQ(rows * columns);
      // Insert all matrix data onto heap
      for(int i = 0; i < rows; i++) {
        for(int j = 0; j < columns; j++) {
          maxPQ.insert(matrix[i][j]);
        }
      }
      // Remove redundant elements if larger than required size k
      int remove = rows * columns - k;
      if(remove > 0) {
        for(int i = 0; i < remove; i++) {
          maxPQ.delMax();
        }
      }
      // Return the max value on current heap
      return maxPQ.pq[1];
    }
    
    // Create helper class for building up heap
    private class MaxPQ {
    	private int[] pq;
    	private int n;
    	
    	public MaxPQ(int initCapacity) {
    		pq = new int[initCapacity + 1];
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
    	
    	public void swim(int k) {
    		// This is wrong way: The limitation on while loop is only k > 1,
    		// only adding condition as less(k/2, k) can prevent infinite loop
    		// e.g in current example, when process to k = 7, it stucks here
//    		while(k > 1) {
//    			if(less(k/2, k)) {
//        			exch(k/2, k);
//        			k = k/2;    				
//    			}
//    		}
    		while(k > 1 && less(k/2, k)) {
    			exch(k/2, k);
    			k = k/2;
    		}
    	}
    	
    	public boolean less(int m, int n) {
    		return pq[m] < pq[n];
    	}
    	
    	public void exch(int m, int n) {
    		int swap = pq[m];
    		pq[m] = pq[n];
    		pq[n] = swap;
    	}
    }
    
    
    public static void main(String[] args) {
    	int[][] matrix = {{1, 5, 9}, {10, 11, 13}, {12, 13, 15}};
    	int k = 8;
    	KthSmallestElementInASortedMatrix a = new KthSmallestElementInASortedMatrix();
    	int result = a.kthSmallest(matrix, k);
    	System.out.println(result);
    	
    	// For testing on MaxPQ
//    	KthSmallestElementInASortedMatrix v = new KthSmallestElementInASortedMatrix();    	
//		int[] a = {1, 5, 9, 0, 10, 11, 13, 0, 12, 13, 15, 0};
//		MaxPQ pq = v.new MaxPQ(a.length);
//		for(int i = 0; i < a.length; i++) {
//			if(a[i] != 0) {
//				pq.insert(a[i]);
//			} else {
//				StdOut.print(pq.delMax() + " ");
//			}
//		}
//		StdOut.println("(" + pq.size() + " left on pq)");  	
    }
}

