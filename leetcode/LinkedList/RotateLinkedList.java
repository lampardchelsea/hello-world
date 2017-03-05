/**
 * Refer to
 * https://leetcode.com/problems/rotate-list/?tab=Description
 *
 */
public class RotateLinkedList {
    private class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
    	    val = x; 
        }
    }
	
    public ListNode rotateRight(ListNode head, int k) {
        if(head == null) {
            return head;
        }
        int len = 1;
        ListNode itr = head;

        // Find original tail and length of linkedlist
        while(itr.next != null) {
            len++;
            itr = itr.next;
        }
        ListNode tail = itr;

        // Calculate how many items need to rotate
        int needToMove = k % len;
        // If no need to rotate, directly return current list
        if(needToMove == 0) {
            return head;
        }
        // Calculate how many items need to keep
        int needToKeep = len - needToMove;
        ListNode itr1 = head;
        // Use '> 1' to find newTail
        while(needToKeep > 1) {
            itr1 = itr1.next;
            needToKeep--;
        }
        // Find new tail
        ListNode newTail = itr1;
        // Find new head
        ListNode newHead = itr1.next;
        // Cut off connection between newTail and newHead
        newTail.next = null;
        // Concatenate original tail to original head
        tail.next = head;
        // Return newHead
        return newHead;
    }
    
    public static void main(String[] args) {
    	RotateLinkedList r = new RotateLinkedList();
    	ListNode one = r.new ListNode(1);
    	ListNode two = r.new ListNode(2);
    	one.next = two;
    	ListNode result = r.rotateRight(one, 1);
    	System.out.println(result.val);
    }
    
}
