/**
 * Refer to
 * http://www.geeksforgeeks.org/write-a-c-function-to-print-the-middle-of-the-linked-list/
 * Given a singly linked list, find middle of the linked list. 
 * For example, if given linked list is 1->2->3->4->5 then output should be 3.
 * If there are even nodes, then there would be two middle nodes, we need to print second middle element. 
 * For example, if given linked list is 1->2->3->4->5->6 then output should be 4.
 * 
 * Solution:
 * Method 1:
 * Traverse the whole linked list and count the no. of nodes. 
 * Now traverse the list again till count/2 and return the node at count/2.
 * Method 2:
 * Traverse linked list using two pointers. Move one pointer by one and other pointer by two. 
 * When the fast pointer reaches end slow pointer will reach middle of the linked list.
 */
public class FindMiddleOfLinkedList {
    private class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
    	    val = x; 
        }
    }
	
    // Method 1: Traverse with counter
	public ListNode findMiddle(ListNode head) {
		if(head == null) {
			return head;
		}
		int count = 1;
		ListNode itr = head;
		while(itr.next != null) {
			count++;
			itr = itr.next;
		}
		int x = count/2;
		itr = head;
		while(x > 0) {
			itr = itr.next;
			x--;
		} 
		return itr;
	}
	
	// Method 2: Two pointers traverse
	// Important: This method not handle one case, if the original list
	// length is only 2 (head itself and its next), we cannot pass while 
	// condition check and will not return what we need as walker.next
	// e.g [1, 2], we expect 2, but return as 1
	public ListNode findMiddle2(ListNode head) {
		//if(head == null) {
		//	return head;
		//}
		// Solve above issue
		if(head == null || head.next == null) {
			return head;
		}
		ListNode walker = head;
		ListNode runner = head;
		while(runner.next != null && runner.next.next != null) {
			walker = walker.next;
			runner = runner.next.next;
		}
		return walker;
	}
	
	public static void main(String[] args) {
		FindMiddleOfLinkedList f = new FindMiddleOfLinkedList();
		ListNode one = f.new ListNode(1);
		ListNode two = f.new ListNode(2);
		ListNode three = f.new ListNode(3);
		ListNode four = f.new ListNode(4);
		ListNode five = f.new ListNode(5);
		ListNode six = f.new ListNode(6);
		ListNode seven = f.new ListNode(7);
		one.next = two;
		two.next = three;
		three.next = four;
		four.next = five;
		five.next = six;
		six.next = seven;
//		int result = f.findMiddle(one).val;
		int result = f.findMiddle2(one).val;
		System.out.println(result);
	}
}

