/**
 * Refer to
 * https://leetcode.com/problems/swap-nodes-in-pairs/#/description
 * Given a linked list, swap every two adjacent nodes and return its head.
 * For example,
 * Given 1->2->3->4, you should return the list as 2->1->4->3.
 * Your algorithm should use only constant space. You may not modify the 
 * values in the list, only nodes itself can be changed.
 */
public class SwapNodesInPairs {
	private class ListNode {
		int val;
		ListNode next;
		ListNode(int x) { 
			val = x; 
		}
	}
	
	// Solution 1: Iterative and use constant space
	// Refer to
	// https://discuss.leetcode.com/topic/10649/my-simple-java-solution-for-share/5
    public ListNode swapPairs(ListNode head) {
        if(head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode curr = null;
        ListNode then = null;
        // E.g
        // dummy ->  1   ->   2   -> 3
        //  prev -> curr -> then
        while(prev.next != null && prev.next.next != null) {
            // Store 1
            curr = prev.next;
            // Store 2
            then = prev.next.next;
            // 1 -> 3
            curr.next = then.next;
            // 2 -> 1
            then.next = curr;
            // dummy -> 2
            prev.next = then;
            // Move forward 1 step of 'prev', which will also update
            // 'curr' and 'then' in next loop
            prev = curr;
            // After one loop
            // dummy -> 2  ->  1   ->  3  ->  4
            //                prev -> curr -> then
        }
        return dummy.next;   
    }
    
    public static void main(String[] args) {
    	SwapNodesInPairs s = new SwapNodesInPairs();
    	ListNode one = s.new ListNode(1);
    	ListNode two = s.new ListNode(2);
    	ListNode three = s.new ListNode(3);
    	ListNode four = s.new ListNode(4);
    	one.next = two;
    	two.next = three;
    	three.next = four;
    	ListNode result = s.swapPairs(one);
    	System.out.println(result.val);
    }
}
