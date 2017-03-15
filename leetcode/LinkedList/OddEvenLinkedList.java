/**
 * Refer to
 * https://leetcode.com/problems/odd-even-linked-list/#/description
 * Given a singly linked list, group all odd nodes together followed by the even nodes. 
 * Please note here we are talking about the node number and not the value in the nodes.
 * You should try to do it in place. The program should run in O(1) space complexity and O(nodes) time complexity.
 * Example:
 * Given 1->2->3->4->5->NULL,
 * return 1->3->5->2->4->NULL.
 * Note:
 * The relative order inside both the even and odd groups should remain as it was in the input. 
 * The first node is considered odd, the second node even and so on ...
 * 
 * Solution
 * Refer to
 * https://discuss.leetcode.com/topic/34292/simple-o-n-time-o-1-space-java-solution
 * http://www.cnblogs.com/grandyang/p/5138936.html
 */
public class OddEvenLinkedList {
	private class ListNode {
		int val;
		ListNode next;
		ListNode(int x) { 
			val = x; 
		}
	}
	
    public ListNode oddEvenList(ListNode head) {
        if(head == null || head.next == null) {
            return head;
        }
        ListNode oddDummy = new ListNode(-1);
        ListNode evenDummy = new ListNode(-1);
        int count = 1;
        ListNode itr1 = oddDummy;
        ListNode itr2 = evenDummy;
        ListNode itr = head;
        while(itr != null) {
        	ListNode nextTemp = itr.next;
        	// Don't forget to cut off the connection
        	// between current node and next node,
        	// otherwise will cause infinite loop issue
        	itr.next = null;
        	if(count % 2 == 1) {
        		itr1.next = itr;
        		itr1 = itr1.next;
        	} else {
        		itr2.next = itr;
        		itr2 = itr2.next;
        	}
        	itr = nextTemp;
        	count++;
        }
        itr1.next = evenDummy.next;
        return oddDummy.next;
    }
    
    public static void main(String[] args) {
    	OddEvenLinkedList o = new OddEvenLinkedList();
    	ListNode one = o.new ListNode(1);
    	ListNode two = o.new ListNode(2);
    	ListNode three = o.new ListNode(3);
    	one.next = two;
    	two.next = three;
    	ListNode result = o.oddEvenList(one);
    	System.out.println(result.val);
    }
}
