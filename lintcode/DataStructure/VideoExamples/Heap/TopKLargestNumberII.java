
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Refer to
 * https://aaronice.gitbooks.io/lintcode/content/data_structure/top_k_largest_numbers_ii.html
 * Implement a data structure, provide two interfaces:
	add(number). Add a new number in the data structure.
	topk(). Return the top k largest numbers in this data structure. k is given when we create the data structure.
	Example
	>
	
	s = new Solution(3);
	>> create a new data structure.
	s.add(3)
	s.add(10)
	s.topk()
	>> return [10, 3]
	s.add(1000)
	s.add(-99)
	s.topk()
	>> return [1000, 10, 3]
	s.add(4)
	s.topk()
	>> return [1000, 10, 4]
	s.add(100)
	s.topk()
	>> return [1000, 100, 10]
 * 
 * Solution
 * https://aaronice.gitbooks.io/lintcode/content/data_structure/top_k_largest_numbers_ii.html
 * 与top k问题1类似，不太一样的一点在于动态添加，使用min heap来实现，能够比较好地通过更新min heap来记录top k。
 * 当添加的元素数目在1 ~ k时，直接插入这一元素到min heap中； 当添加的元素数目超出k时，对于新添加的元素，需要与
 * min heap的根进行比较，如果比minheap.peek()大，那么便删除根，添加该新元素。
 * 或者改进后用max heap做也行
 * For heap operation time complexcity
 * Refer to
 * https://github.com/lampardchelsea/hello-world/blob/master/lintcode/DataStructure/VideoExamples/Heap/Document/heaps.pdf
 * Summary page
 */
public class TopKLargestNumberII {
	private PriorityQueue<Integer> maxHeap;
	private int maxSize;
	private Comparator<Integer> comparator;
	
	public TopKLargestNumberII(int k) {
		maxSize = k;
		// Create max heap comparator (a > b return -1)
		comparator = new Comparator<Integer>(){
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
		// maxSize = initialCapacity - the initial capacity for this priority queue
		// we can set any value larger than given maxSize
		maxHeap = new PriorityQueue<Integer>(maxSize, comparator);
		
	}
	// Time Complexity (logk) --> insert operation on heap
	public void add(int num) {
		if(maxHeap.size() < maxSize) {
			maxHeap.offer(num);
		} else {
			if(num > maxHeap.peek()) {
				maxHeap.poll();
				maxHeap.offer(num);
			}
		}
	}
	// Time Complexity (klogk) --> repeat k times deleteMin operation on heap
	public List<Integer> topk() {
		List<Integer> result = new ArrayList<Integer>();
		while(maxSize > 0) {
			Integer i = maxHeap.poll();
			if(i != null) {
				result.add(i);
			}
			maxSize--;
		}
		return result;
		// We can use iterator to do this also
//		Iterator iter = minHeap.iterator();
//        List<Integer> result = new ArrayList<Integer>();
//        while (iter.hasNext()) {
//            result.add((Integer) iter.next());
//        }
//        Collections.sort(result, Collections.reverseOrder());
//        return result;
	}
	
	public static void main(String[] args) {
		TopKLargestNumberII t = new TopKLargestNumberII(3);
		t.add(3);
		t.add(10);
		List<Integer> result = t.topk();
		for(int i : result) {
			System.out.print(i + " ");
		}		
	}
}
