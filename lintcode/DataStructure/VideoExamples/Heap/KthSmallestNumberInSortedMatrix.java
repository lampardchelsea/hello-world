
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Refer to
 * http://www.lintcode.com/en/problem/kth-smallest-number-in-sorted-matrix/
 * Find the kth smallest number in at row and column sorted matrix.

	Have you met this question in a real interview? Yes
	Example
	Given k = 4 and a matrix:
	
	[
	  [1 ,5 ,7],
	  [3 ,7 ,8],
	  [4 ,8 ,9],
	]
	return 5
 * 
 * Solution 1: 30ms Leetcode
 * https://aaronice.gitbooks.io/lintcode/content/data_structure/kth_smallest_number_in_sorted_matrix.html
 * 寻找第k小的数，可以联想到转化为数组后排序，不过这样的时间复杂度较高：O(n^2 log n^2) + O(k).
          进一步，换种思路，考虑到堆（Heap）的特性，可以建立一个Min Heap，然后poll k次，得到第k个最小数字。不过这样的复杂度仍然较高。
	考虑到问题中矩阵本身的特点：排过序，那么可以进一步优化算法。
	[1 ,5 ,7],
	[3 ,7 ,8],
	[4 ,8 ,9],
         因为行row和列column都已排序，那么matrix中最小的数字无疑是左上角的那一个，坐标表示也就是(0, 0)。
         寻找第2小的数字，也就需要在(0, 1), (1, 0)中得出；以此类推第3小的数字，也就要在(0, 1), (1, 0), (2, 0), (1, 1), (0, 2)中寻找。
         在一个数字集合中寻找最大（Max）或者最小值（Min），很快可以联想到用Heap，在Java中的实现是Priority Queue，
         它的pop，push操作均为O(logn)，而top操作，得到堆顶仅需O(1)。
         从左上(0, 0)位置开始往右/下方遍历，使用一个Hashmap记录visit过的坐标，把候选的数字以及其坐标放入一个大小为k的heap中
         （只把未曾visit过的坐标放入heap），并且每次放入前弹出掉（poll）堆顶元素，这样最多会添加（push）2k个元素。时间复杂度
         是O(klog2k)，也就是说在矩阵自身特征的条件上优化，可以达到常数时间的复杂度，空间复杂度也为O(k)，即存储k个候选数字
         的Priority Queue （Heap）。
 *
 * Solution 2: 27ms Leetcode
 * https://discuss.leetcode.com/topic/52948/share-my-thoughts-and-clean-java-code
 * Here is the step of my solution:
 * Build a minHeap of elements from the first row.
 * Do the following operations k-1 times :
 * Every time when you poll out the root(Top Element in Heap), you need to know the row number 
 * and column number of that element(so we can create a tuple class here), replace that root 
 * with the next element from the same column.
 * For this question, you can also build a min Heap from the first column, and do the similar 
 * operations as above.(Replace the root with the next element from the same row)
 * 
 * Solution 1 and 2 difference is on Solution 1 when we supply new Pair onto queue, 
 * we consider both directions as x-axis (0,1) and y-axis (1,0), so we add two new
 * numbers onto queue, but in Solution 2, we just consider supply same column one as
 * next element, so add only one new number onto queue
 * 
 */
public class KthSmallestElementInASortedMatrix_Optimization {
    /*
     * @param matrix: a matrix of integers
     * @param k: An integer
     * @return: the kth smallest number in the matrix
     */
	// Solution 1
    public int kthSmallest(int[][] matrix, int k) {
        // Magic array for directions
    	int[] dx = {0, 1};
    	int[] dy = {1, 0};
    	PriorityQueue<Pair> queue = new PriorityQueue<Pair>(k, new Comparator<Pair>(){
    		public int compare(Pair a, Pair b) {
    			return a.val - b.val;
    		}
    	});
    	int n = matrix.length;
    	int m = matrix[0].length;
    	boolean[][] visited = new boolean[n][m];
    	Pair p = new Pair(0, 0, matrix[0][0]);
    	queue.add(p);
    	// Check visited as there will be duplicate adding possibility
    	// E.g {{1,5,7}, {3,7,8}, {4,8,9}} -> the 2nd '7' will be calculated
    	// by 3->7 on x-axis or 5->7 on y-axis
    	visited[0][0] = true;
    	// Caution: i < k - 1, because we already add matrix[0][0] as
    	// previous step, so only need (k - 1) step to reach the final position
    	for(int i = 0; i < k - 1; i++) {
    		Pair cur = queue.poll();
    		// Adding both options: x-axis/y-axis next value onto queue
    		for(int j = 0; j < 2; j++) {
    			int next_x = cur.x + dx[j];
    			int next_y = cur.y + dy[j];
    			if(next_x < n && next_y < m && !visited[next_x][next_y]) {
    				int next_val = matrix[next_x][next_y];
    				visited[next_x][next_y] = true;
    				Pair next = new Pair(next_x, next_y, next_val);
    				queue.add(next);
    			}
    		}
    	}
    	return queue.peek().val;
    }
    
    private class Pair {
    	int x;
    	int y;
    	int val;
    	public Pair(int x, int y, int val) {
    		this.x = x;
    		this.y = y;
    		this.val = val;
    	}
    }
    
    // Solution 2
    public int kthSmallest2(int[][] matrix, int k) {
        int n = matrix.length;
        int m = matrix[0].length;
        PriorityQueue<Tuple> pq = new PriorityQueue<Tuple>(k, new Comparator<Tuple>() {
            public int compare(Tuple a, Tuple b) {
                return a.val - b.val;
            }
        });
        // Initialize min heap with first row
        for(int i = 0; i < m; i++) {
            pq.add(new Tuple(0, i, matrix[0][i])); 
        }
        // Replace the items on min heap with same column ones
        for(int i = 0; i < k - 1; i++) {
            Tuple cur = pq.poll();
            // Already reach the last row, if not checking here will cause
            // ArrayIndexOutofBound issue as (cur.x + 1 == n) when generate
            // new Tuple
            if(cur.x == n - 1) {
                continue;
            }
            pq.add(new Tuple(cur.x + 1, cur.y, matrix[cur.x + 1][cur.y]));
        }
        return pq.poll().val;
    }
    
    private class Tuple {
    	int x;
    	int y;
    	int val;
    	public Tuple(int x, int y, int val) {
    		this.x = x;
    		this.y = y;
    		this.val = val;
    	}
    }
    
    public static void main(String[] args) {
    	KthSmallestElementInASortedMatrix_Optimization k = new KthSmallestElementInASortedMatrix_Optimization();
    	//int[][] matrix = {{1,5,7}, {3,7,8}, {4,8,9}};
    	// Test if not an n * n matrix
    	int[][] matrix = {{1},{2},{3},{100},{101},{1000},{9999}};
    	//int index = 4;
    	int index = 5;
    	int result = k.kthSmallest2(matrix, index);
    	System.out.print(result);
    }
}


