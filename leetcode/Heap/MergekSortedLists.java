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
            //return pq[v].val - pq[w].val > 0;
            /**
             * Refer to
             * http://stackoverflow.com/questions/12535095/java-integers-min-value-negative-then-compare
             * The change on compare format is really tricky, the exception can be test by
             * {-2147483648, 1, 1}, the first number -2147483648 actually the Integer.MIN_VALUE,
             * if we use pq[v] - pq[w] = -2147483648 - 1, the result is 2147483647, which is
             * Integer.MAX_VALUE, not satisfy < 0, which we expected. We need to change compare
             * format to pq[v] < pq[w] without 'minus' operation
             * 
             * Similar problem happen on
             * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Heap/KthSmallestElementInASortedMatrix.java
             * 
             * So here we have test case with below
             * If the full test case as , 
				[[1, 4, 5], [-2147483648, 2, 3, 6]]
				which suppose to return 
				[-2147483648, 1, 2, 3, 4, 5, 6]
				in error code above, it will return
				[1, 4, 5, -2147483648, 2, 3, 6]
             */
        	return pq[v].val > pq[w].val;
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
		// -2147483648 --> 2 --> 3 --> 6
		ListNode special = m.new ListNode(-2147483648);
		ListNode one = m.new ListNode(1);
		ListNode two = m.new ListNode(2);
		ListNode three = m.new ListNode(3);
		ListNode four = m.new ListNode(4);
		ListNode five = m.new ListNode(5);
		ListNode six = m.new ListNode(6);
		special.next = two;
		one.next = four;
		two.next = three;
		four.next = five;
		three.next = six;
//		ListNode[] lists = {one, two};
		ListNode[] lists = {one, special};
		// For debug
		//ListNode result = m.mergeKLists(lists);
		ListIterator iterator = m.iterator(lists);
		while(iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}
}













































































































https://leetcode.com/problems/merge-k-sorted-lists/description/
You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.
Merge all the linked-lists into one sorted linked-list and return it.

Example 1:
Input: lists = [[1,4,5],[1,3,4],[2,6]]
Output: [1,1,2,3,4,4,5,6]
Explanation: The linked-lists are:
[
  1->4->5,
  1->3->4,
  2->6
]
merging them into one sorted list:
1->1->2->3->4->4->5->6

Example 2:
Input: lists = []
Output: []

Example 3:
Input: lists = [[]]
Output: []

Constraints:
- k == lists.length
- 0 <= k <= 10^4
- 0 <= lists[i].length <= 500
- -10^4 <= lists[i][j] <= 10^4
- lists[i] is sorted in ascending order.
- The sum of lists[i].length will not exceed 10^4.
--------------------------------------------------------------------------------
Attempt 1: 2023-02-19
Solution 1:  Priority Queue Solution (30 min)
/** 
 * Definition for singly-linked list. 
 * public class ListNode { 
 *     int val; 
 *     ListNode next; 
 *     ListNode() {} 
 *     ListNode(int val) { this.val = val; } 
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; } 
 * } 
 */ 
class Solution { 
    public ListNode mergeKLists(ListNode[] lists) { 
        if(lists == null || lists.length == 0) { 
            return null; 
        } 
        ListNode dummy = new ListNode(); 
        ListNode iter = dummy; 
        PriorityQueue<ListNode> minPQ = new PriorityQueue<ListNode>(lists.length, (a, b) -> a.val - b.val); 
        for(ListNode listHead : lists) { 
            if(listHead != null) { 
                minPQ.offer(listHead); 
            } 
        } 
        while(!minPQ.isEmpty()) { 
            ListNode curPeek = minPQ.poll(); 
            iter.next = curPeek;
            iter = iter.next; 
            if(curPeek.next != null) { 
                minPQ.offer(curPeek.next); 
            } 
        } 
        return dummy.next; 
    } 
}

Time complexity:O(Nlog⁡k) where k is the number of linked lists. 
- The comparison cost will be reduced to O(log⁡k) for every pop and insertion to priority queue. But finding the node with the smallest value just costs O(1) time. 
- There are N nodes in the final linked list. 
Space complexity:O(n) Creating a new linked list costs O(n)space. 
- O(k) The code above present applies in-place method which cost O(1) space. And the priority queue (often implemented with heaps) costs O(k) space (it's far less than N in most situations).

Refer to
https://leetcode.com/problems/merge-k-sorted-lists/solutions/10528/a-java-solution-based-on-priority-queue/
public class Solution { 
    public ListNode mergeKLists(List<ListNode> lists) { 
        if (lists==null||lists.size()==0) return null; 
         
        PriorityQueue<ListNode> queue= new PriorityQueue<ListNode>(lists.size(),new Comparator<ListNode>(){ 
            @Override 
            public int compare(ListNode o1,ListNode o2){ 
                if (o1.val<o2.val) 
                    return -1; 
                else if (o1.val==o2.val) 
                    return 0; 
                else  
                    return 1; 
            } 
        }); 
         
        ListNode dummy = new ListNode(0); 
        ListNode tail=dummy; 
         
        for (ListNode node:lists) 
            if (node!=null) 
                queue.add(node); 
             
        while (!queue.isEmpty()){ 
            tail.next=queue.poll(); 
            tail=tail.next; 
             
            if (tail.next!=null) 
                queue.add(tail.next); 
        } 
        return dummy.next; 
    } 
}

-------------------------------------------------------------------------------------------------------
Using Java 8:-
class Solution {
    public ListNode mergeKLists(ListNode[] lists) { 
        if (lists==null || lists.length==0) return null; 
         
        PriorityQueue<ListNode> queue= new PriorityQueue<ListNode>(lists.length, (a,b)-> a.val-b.val); 
         
        ListNode dummy = new ListNode(0); 
        ListNode tail=dummy; 
         
        for (ListNode node:lists) 
            if (node!=null) 
                queue.add(node); 
             
        while (!queue.isEmpty()){ 
            tail.next=queue.poll(); 
            tail=tail.next; 
             
            if (tail.next!=null) 
                queue.add(tail.next); 
        } 
        return dummy.next; 
    } 
}

Refer to
https://leetcode.com/problems/merge-k-sorted-lists/solutions/127466/merge-k-sorted-list/
Approach 2: Compare one by one
Algorithm
- Compare every k nodes (head of every linked list) and get the node with the smallest value.
- Extend the final sorted linked list with the selected nodes.

Step 1

Step 2

Step 3

Step 4

Step 5

Step 6

Step 7

Step 8

Step 9

Step 10

Step 11


Approach 3: Optimize Approach 2 by Priority Queue
Algorithm
Almost the same as the one above but optimize the comparison process by priority queue. You can refer here for more information about it.
from Queue import PriorityQueue 
class Solution(object): 
    def mergeKLists(self, lists): 
        """ 
        :type lists: List[ListNode] 
        :rtype: ListNode 
        Note: this is Python2 code 
        """ 
        head = point = ListNode(0) 
        q = PriorityQueue() 
        for l in lists: 
            if l: 
                q.put((l.val, l)) 
        while not q.empty(): 
            val, node = q.get() 
            point.next = ListNode(val) 
            point = point.next 
            node = node.next 
            if node: 
                q.put((node.val, node)) 
        return head.next
Complexity Analysis
- Time complexity : O(Nlog⁡k) where k is the number of linked lists.
- The comparison cost will be reduced to O(log⁡k)for every pop and insertion to priority queue. But finding the node with the smallest value just costs O(1) time.
- There are N nodes in the final linked list.
- Space complexity :
- O(n) Creating a new linked list costs O(n)space.
- O(k) The code above present applies in-place method which cost O(1) space. And the priority queue (often implemented with heaps) costs O(k) space (it's far less than N in most situations).      
    
