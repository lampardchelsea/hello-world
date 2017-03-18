import java.util.HashSet;
import java.util.Set;

/**
 * Refer to
 * https://leetcode.com/problems/third-maximum-number/#/description
 * Given a non-empty array of integers, return the third maximum number in this array. 
 * If it does not exist, return the maximum number. The time complexity must be in O(n).
	
	Example 1:
	Input: [3, 2, 1]
	
	Output: 1
	
	Explanation: The third maximum is 1.
	Example 2:
	Input: [1, 2]
	
	Output: 2
	
	Explanation: The third maximum does not exist, so the maximum (2) is returned instead.
	Example 3:
	Input: [2, 2, 3, 1]
	
	Output: 1
	
  * Explanation: Note that the third maximum here means the third maximum distinct number.
  * Both numbers with value 2 are both considered as second maximum.
 * 
 */
public class ThirdMaximumNumber {
	public int thirdMax(int[] nums) {
        Set<Integer> set = new HashSet<Integer>();
        for(int i = 0; i < nums.length; i++) {
            set.add(nums[i]);
        }
        MaxPQ pq = new MaxPQ(set.size());
        for(Integer a : set) {
            pq.insert(a);
        }
        int result = 0;
        int size = pq.size();
        if(size < 3) {
            result = pq.delMax();
        } else {
            int k = 2;
            while(k-- > 0) {
                pq.delMax();
            }
            result = pq.delMax();
        }
        return result;
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
        
//        public boolean isEmpty() {
//            return n == 0;
//        }
        
        public int size() {
            return n;
        }
        
        /**
         * Refer to
         * http://stackoverflow.com/questions/12535095/java-integers-min-value-negative-then-compare
         * The change on compare format is really tricky, the exception can be test by
         * {-2147483648, 1, 1}, the first number -2147483648 actually the Integer.MIN_VALUE,
         * if we use pq[v] - pq[w] = -2147483648 - 1, the result is 2147483647, which is
         * Integer.MAX_VALUE, not satisfy < 0, which we expected. We need to change compare
         * format to pq[v] < pq[w] without 'minus' operation
         */
        public boolean less(int v, int w) {
//            return pq[v] - pq[w] < 0;
        	return pq[v] < pq[w];
        }
        
        public void exch(int v, int w) {
            int temp = pq[v];
            pq[v] = pq[w];
            pq[w] = temp;
        }
    }
    
    public static void main(String[] args) {
    	int[] nums = {-2147483648, 1, 1};
    	ThirdMaximumNumber t = new ThirdMaximumNumber();
    	int result = t.thirdMax(nums);
    	System.out.println(result);
    }
}

