/**
 * Refer to
 * https://leetcode.com/problems/reorder-list/?tab=Description
 * Given a singly linked list L: L0→L1→…→Ln-1→Ln,
 * reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→…
 * You must do this in-place without altering the nodes' values.
 * For example,
 * Given {1,2,3,4}, reorder it to {1,4,2,3}.
 * 
 * Solution
 * Refer to
 * https://discuss.leetcode.com/topic/13869/java-solution-with-3-steps
 */
public class ReorderList {
	private class ListNode {
	    int val;
	    ListNode next;
	    ListNode(int x) { 
	    	val = x;
	    }
	}
	
	public void reorderList(ListNode head) {
        if(head == null || head.next == null) {
            return;
        }
        // Find the middle
        ListNode walker = head;
        ListNode runner = head;
        while(runner.next != null && runner.next.next != null) {
            walker = walker.next;
            runner = runner.next.next;
        }

        // Reverse second half
        // Same way as ReverserLinkedListII
        ListNode pre = walker;
        ListNode start = pre.next;
        ListNode then = start.next;
        while(start.next != null) {
        	start.next = then.next;
        	then.next = pre.next;
        	pre.next = then;
        	then = start.next;
        }
        
        // Start reorder one by one 1 -> 2 -> 3 -> 6 -> 5 -> 4 
        // to 1 -> 6 -> 2 -> 5 -> 3 -> 4
        // Create two pointers, p1 for first half, 
        // p2 for already reversed second half
        ListNode p1 = head;
        ListNode p2 = walker.next;
        // If p1 not reach the end(= walker, as last node before second half start)
        while(p1 != walker) {
        	// Store p2.next into walker.next(e.g in first loop, walker.next = 5)
        	walker.next = p2.next;
        	// Move p2(= 6) between p1(= 1) and p1.next(= 3), also store p1.next 
        	// into p2.next(e.g in first loop, p2.next = 2)
        	p2.next = p1.next;
        	p1.next = p2;
        	// Update p1 to its original next value as p2.next(which store it previously)
        	// (e.g in first loop, p2.next = 2)
        	p1 = p2.next;
        	// Update p2 to its original next value as walker.next(which store it previously)
        	// (e.g in first loop, walker.next = 5, p2 update from 6 to 5)
        	p2 = walker.next;
        }
    }
	
	public static void main(String[] args) {
		ReorderList r = new ReorderList();
		ListNode one = r.new ListNode(1);
		ListNode two = r.new ListNode(2);
		ListNode three = r.new ListNode(3);
		ListNode four = r.new ListNode(4);
		ListNode five = r.new ListNode(5);
		ListNode six = r.new ListNode(6);
		one.next = two;
		two.next = three;
		three.next = four;
		four.next = five;
		five.next = six;
		r.reorderList(one);
	}
	
}

// Re-work
// Solution 1: Merge two half without cutting in middle, not very intuitive
// Refer to
// https://leetcode.com/problems/reorder-list/discuss/44992/Java-solution-with-3-steps
class Solution {
    public void reorderList(ListNode head) {
        if(head == null || head.next == null) {
            return;
        }
        ListNode iter = head;
        int count = 0;
        while(iter != null) {
            iter = iter.next;
            count++;
        }
        int first_half_size = (count % 2 == 0 ? count / 2 : (count + 1) / 2);
        int second_half_size = count - first_half_size;
        // Find prev node of second half
        ListNode second_half_prev = new ListNode(-1);
        second_half_prev.next = head;
        for(int i = 0; i < first_half_size; i++) {
            second_half_prev = second_half_prev.next;
        }
        // Reverse second half
        ListNode second_half_start = second_half_prev.next;
        ListNode second_half_then = second_half_start.next;
        for(int i = 0; i < second_half_size - 1; i++) {
            second_half_start.next = second_half_then.next;
            second_half_then.next = second_half_prev.next;
            second_half_prev.next = second_half_then;
            second_half_then = second_half_start.next;
        }
        // Merge two half without cut off in middle, not very intuitive
        // Initial: 1 -> 2 -> 4 -> 3
        //          p1   |
        //        second_half_prev
        //                    p2
        // 
        // Target:  1 -> 4 -> 2 -> 3
        ListNode p1 = head;
        ListNode p2 = second_half_prev.next;
        while(p1 != second_half_prev){
        	second_half_prev.next = p2.next;
            p2.next = p1.next;
            p1.next = p2;
            p1 = p2.next;
            p2 = second_half_prev.next;
        }
    }
}

// Solution 2: Merge two half with cut off in middle, very intuitive
// Refer to
// https://leetcode.com/problems/reorder-list/discuss/44992/Java-solution-with-3-steps/155674
class Solution {
    public void reorderList(ListNode head) {
        if(head == null || head.next == null) {
            return;
        }
        ListNode iter = head;
        int count = 0;
        while(iter != null) {
            iter = iter.next;
            count++;
        }
        int first_half_size = (count % 2 == 0 ? count / 2 : (count + 1) / 2);
        int second_half_size = count - first_half_size;
        // Find prev node of second half
        ListNode second_half_prev = new ListNode(-1);
        second_half_prev.next = head;
        for(int i = 0; i < first_half_size; i++) {
            second_half_prev = second_half_prev.next;
        }
        // Reverse second half
        ListNode second_half_start = second_half_prev.next;
        ListNode second_half_then = second_half_start.next;
        for(int i = 0; i < second_half_size - 1; i++) {
            second_half_start.next = second_half_then.next;
            second_half_then.next = second_half_prev.next;
            second_half_prev.next = second_half_then;
            second_half_then = second_half_start.next;
        }
        /**
           Merge two half with cut off in middle, very intuitive
           Initial: 1 -> 2 -> 4 -> 3
                    p1   |
                  second_half_prev
                              p2
           
           Target:  1 -> 4 -> 2 -> 3
          -------------------------------------------------------------------
           second_half_prev.next = null; --> Don't cut off on original pointer
           We have to make a copy as new head for second half as 'head2', after 
           split it will be:
           First half: 1 -> 2 -> null
                       |
                      head
           Second half: 4 -> 3 -> null
                        |
                      head2
          -------------------------------------------------------------------
        */    
        ListNode head2 = second_half_prev.next;
        second_half_prev.next = null;
        while(head != null && head2 != null) {
        	ListNode tmp1 = head.next;
        	ListNode tmp2 = head2.next;
        	head2.next = head.next;
        	head.next = head2;
        	head = tmp1;
        	head2 = tmp2;
        }
    }
}
