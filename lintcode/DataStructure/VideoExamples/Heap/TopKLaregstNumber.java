/**
 * Refer to
 * https://aaronice.gitbooks.io/lintcode/content/data_structure/top_k_largest_numbers.html
 * Question

  Given an integer array, find the top k largest numbers in it.
  Example
  Given [3,10,1000,-99,4,100] and k = 3. Return [1000, 100, 10].
 *
 *
 * Solution
 * https://aaronice.gitbooks.io/lintcode/content/data_structure/top_k_largest_numbers.html
 * 此题运用Priority Queue，也就是Max Heap来求解十分简洁。重点在于对该数据结构的理解和应用。 使用Max Heap，
 * 也就是保证了堆顶的元素是整个堆最大的那一个。第一步，先构建一个capacity为k的Max Heap，这需要O(n)时间；
 * 第二步，对这个Max Heap进行k次poll()操作，丛中取出k个maximum的元素，这一步操作需要O(k logn)。因此最终
 * 整个操作的时间复杂度是 O(n + k logn)，空间复杂度是O(k)
*/
// Solution 1: Max Heap
import java.util.Comparator;
import java.util.PriorityQueue;


public class TopKLargestNumber {
	public int[] topK(int[] nums, int k) {
		Comparator<Integer> comparator = new Comparator<Integer>(){
			@Override
			public int compare(Integer a, Integer b) {
				if(a > b) {
					return -1;
				} else if(a < b) {
					return 1;
				} else {
					return 0;
				}
			}
			
		};
		
		PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(k, comparator);
		for(int i = 0; i < k; i++) {
			maxHeap.add(nums[i]);
		}
		int[] result = new int[k];
		for(int i = 0; i < k; i++) {
			result[i] = maxHeap.poll();
		}
		return result;
	}
	
	public static void main(String[] args) {
		TopKLargestNumber t = new TopKLargestNumber();
		int[] nums = {13,4,14,-4,1};
		int k = 3;
		int[] result = t.topK(nums, k);
		for(int i = 0; i < k; i++) {
			System.out.print(result[i] + " ");
		}
	}
}	



// Solution 2: Min Heap
public class TopKLargestNumber {
	public int[] topK(int[] nums, int k) {
		Comparator<Integer> comparator = new Comparator<Integer>(){
			@Override
			public int compare(Integer a, Integer b) {
				if(a > b) {
					return -1;
				} else if(a < b) {
					return 1;
				} else {
					return 0;
				}
			}
			
		};
		
		PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(k, comparator);
		for(int i = 0; i < k; i++) {
			maxHeap.add(nums[i]);
		}
		int[] result = new int[k];
		for(int i = 0; i < k; i++) {
			result[i] = maxHeap.poll();
		}
		return result;
	}
	
	
	public int[] topK1(int[] nums, int k) {
		PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
		for(int i = 0; i < k; i++) {
			minHeap.add(nums[i]);
		}
		int[] result = new int[k];
		for(int i = k - 1; i >= 0; i--) {
			result[i] = minHeap.poll();
		}
		return result;
	}
	
	public static void main(String[] args) {
		TopKLargestNumber t = new TopKLargestNumber();
		int[] nums = {13,4,14,-4,1};
		int k = 3;
		int[] result = t.topK1(nums, k);
		for(int i = 0; i < k; i++) {
			System.out.print(result[i] + " ");
		}
	}
}	
