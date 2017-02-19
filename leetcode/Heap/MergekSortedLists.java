import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Refer to
 * https://leetcode.com/problems/merge-k-sorted-lists/?tab=Description
 * 
 * Merge k sorted linked lists and return it as one sorted list. 
 * Analyze and describe its complexity.
 * 
 * Solution
 * https://discuss.leetcode.com/topic/2780/a-java-solution-based-on-priority-queue
 * 
 * Iterator
 * Refer to
 * http://algs4.cs.princeton.edu/13stacks/Queue.java.html
 */
public class MergekSortedLists {
    public ListNode mergeKLists(ListNode[] lists) {
        int len = lists.length;
        MinPQ minPQ = new MinPQ(len);
        for(int i = 0; i < len; i++) {
            // If current node is null, don't insert, otherwise
        	// will cause NullPointerException
            if(lists[i] != null) {
                minPQ.insert(lists[i]);
            }
        }
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while(!minPQ.isEmpty()) {
            tail.next = minPQ.delMin();
            // Important Step: Update tail pointer to its next node,
            // otherwise while loop will become infinite loop,
            // as delete current minimum node and insert it back
            tail = tail.next;
            if(tail.next != null) {
                minPQ.insert(tail.next);
            }
        }
        // Start from dummy's next node
        return dummy.next;
    }
    
    private class MinPQ {
        ListNode[] pq;
        int n;
        public MinPQ(int initialCapacity) {
            pq = new ListNode[initialCapacity + 1];
            n = 0;
        }
        
        public void insert(ListNode x) {
            pq[++n] = x;
            swim(n);
        }
        
        public ListNode delMin() {
            ListNode min = pq[1];
            exch(1, n--);
            sink(1);
            pq[n + 1] = null;
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
            return pq[v].val - pq[w].val > 0;
        }
        
        public void exch(int v, int w) {
            ListNode swap = pq[v];
            pq[v] = pq[w];
            pq[w] = swap;
        }
        
        public boolean isEmpty() {
            return n == 0;
        }
    }
	
	// Definition for singly-linked list
	private class ListNode {
		int val;
		ListNode next;
		public ListNode(int x) {
			val = x;
		}
	}
	
	// Define an iterator to loop the node list
	public ListIterator iterator(ListNode[] lists) {
		return new ListIterator(mergeKLists(lists));
	}
	
	@SuppressWarnings("rawtypes")
	private class ListIterator implements Iterator {
		private ListNode current;
		
		public ListIterator(ListNode first) {
			current = first;
		}
		
		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Integer next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			Integer val = current.val;
			current = current.next;
			return val;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}	
	}
	
	public static void main(String[] args) {
		MergekSortedLists m = new MergekSortedLists();
		// 1 --> 4 --> 5
		// 2 --> 3 --> 6
		ListNode one = m.new ListNode(1);
		ListNode two = m.new ListNode(2);
		ListNode three = m.new ListNode(3);
		ListNode four = m.new ListNode(4);
		ListNode five = m.new ListNode(5);
		ListNode six = m.new ListNode(6);
		one.next = four;
		two.next = three;
		four.next = five;
		three.next = six;
		ListNode[] lists = {one, two};		
		// For debug
		//ListNode result = m.mergeKLists(lists);
		ListIterator iterator = m.iterator(lists);
		while(iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}
}

