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
 */
public class KthSmallestElementInASortedMatrix {
	/**
	 * Solution 1: Heap Sort 
	 * Refer to
	 * http://www.cnblogs.com/grandyang/p/5727892.html
	 * 这道题让我们求有序矩阵中第K小的元素，这道题的难点在于数组并不是蛇形有序的，意思是当前行的最后一个元素并不一定会
	 * 小于下一行的首元素，所以我们并不能直接定位第K小的元素，所以只能另辟蹊径。先来看一种利用堆的方法，我们使用一个
	 * 最大堆，然后遍历数组每一个元素，将其加入堆，根据最大堆的性质，大的元素会排到最前面，然后我们看当前堆中的元素个数
	 * 是否大于k，大于的话就将首元素去掉，循环结束后我们返回堆中的首元素即为所求
	 */
    public int kthSmallest(int[][] matrix, int k) {
    	int rows = matrix.length;
    	int columns = matrix[0].length;
        MaxPQ maxPQ = new MaxPQ(rows * columns);
        for(int i = 0; i < rows; i++) {
        	for(int j = 0; j < columns; j++) {
        		maxPQ.insert(matrix[i][j]);
        	}
        }
        int remove = rows * columns - k;
        if(remove > 0) {
        	for(int i = 0; i < remove; i++) {
        		maxPQ.delMax();
        	}
        }
        return maxPQ.pq[1];
    }
    
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
    		// The wrong format here can be check with below input
    		// int[][] matrix = {{-2147483648, 5, 9}, {10, 11, 13}, {12, 13, 15}};
    		// Refer to
    		// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/array/ThirdMaximumNumber.java
    		//return pq[m] - pq[n] < 0;
    	}
    	
    	public void exch(int m, int n) {
    		int swap = pq[m];
    		pq[m] = pq[n];
    		pq[n] = swap;
    	}
    }
    
    /**
     * Solution 2: Binary Search
     * Refer to
     * https://discuss.leetcode.com/topic/52948/share-my-thoughts-and-clean-java-code/2
     * We are done here, but let's think about this problem in another way:
     * The key point for any binary search is to figure out the "Search Space". For me, I think there are two kind of "Search Space" 
     * -- index and range(the range from the smallest number to the biggest number). Most usually, when the array is sorted in one 
     * direction, we can use index as "search space", when the array is unsorted and we are going to find a specific number, 
     * we can use "range".
     * Let me give you two examples of these two "search space"
     * index -- A bunch of examples -- https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/ ( the array is sorted)
     * range -- https://leetcode.com/problems/find-the-duplicate-number/ (Unsorted Array)
     * The reason why we did not use index as "search space" for this problem is the matrix is sorted in two directions, 
     * we can not find a linear way to map the number and its index.
     * 
     * http://www.cnblogs.com/grandyang/p/5727892.html
     * 这题我们也可以用二分查找法来做，我们由于是有序矩阵，那么左上角的数字一定是最小的，而右下角的数字一定是最大的，所以这个是我们搜索的范围，
     * 然后我们算出中间数字mid，由于矩阵中不同行之间的元素并不是严格有序的，所以我们要在每一行都查找一下mid，我们使用upper_bound，这个函数
     * 是查找第一个大于目标数的元素，如果目标数在比该行的尾元素大，则upper_bound返回该行元素的个数，如果目标数比该行首元素小，
     * 则upper_bound返回0, 我们遍历完所有的行可以找出中间数是第几小的数，然后k比较，进行二分查找，本解法的整体时间复杂度为O(nlgn*lgX)，
     * 其中X为最大值和最小值的差值
     */
    public int kthSmallest2(int[][] matrix, int k) {
    	int rows = matrix.length;
    	int columns = matrix[0].length;
    	int lo = matrix[0][0];
    	int hi = matrix[rows - 1][columns - 1];
    	while(lo < hi) {
    		// mid_rank used for record current mid value position rank
    		// in matrix
        	int mid_rank = 0;
        	int mid = (lo + hi) / 2;
        	int j = columns - 1;
        	for(int i = 0; i < rows; i++) {
        		// Use while loop calculate each row contains how many
        		// items smaller than mid value
        		while(j >= 0 && matrix[i][j] > mid) {
        			j--;
        		}
        		// Add each row calculated value into mid_rank,
        		// do not miss "+1", e.g 1st row contain {1, 5} these
        		// two items smaller than mid = (1 + 15) / 2 = 8, so mid
        		// should rank as (1(j) + 1 = 2) after 1st row calculate(
        		// position start from 0, so 0(1), 1(5), 2(8) --> mid 
        		// value 8 position at 2)
        		mid_rank += j + 1;
        	}
        	// Compare mid_rank with target kth largest value, if current
        	// mid value smaller than kth largest value, it means lower
        	// bound need to increase for find the kth largest value, 
        	// otherwise means upper bound need to decrease for find the
        	// kth largest value.
        	if(mid_rank < k) {
        		lo = mid + 1;
        	} else {
        		hi = mid;
        	}
    	}
    	return lo;
    }
    
    
    public static void main(String[] args) {
    	//int[][] matrix = {{1, 5, 9}, {10, 11, 13}, {12, 13, 15}};
    	int[][] matrix = {{-2147483648, 1, 9}, {10, 11, 13}, {12, 13, 15}};
    	int k = 8;
    	KthSmallestElementInASortedMatrix a = new KthSmallestElementInASortedMatrix();
    	int result = a.kthSmallest(matrix, k);
    	//int result = a.kthSmallest2(matrix, k);
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
