/**
 * Refer to
 * https://leetcode.com/problems/partition-list/#/description
 * Given a linked list and a value x, partition it such that all nodes less 
 * than x come before nodes greater than or equal to x.
 * You should preserve the original relative order of the nodes in each of the two partitions.
 * For example,
 * Given 1->4->3->2->5->2 and x = 3,
 * return 1->2->2->4->3->5.
 * 
 */
public class PartitionList {
	private class ListNode {
		int val;
		ListNode next;
		ListNode(int x) { 
			val = x; 
		}
	}
	
    public ListNode partition(ListNode head, int x) {
        if(head == null || head.next == null) {
            return head;
        }
        // Create dummy1 and dummy2 separately for smaller section
        // than x and larger section than x, after that combine
        // two section together(skip the dummy2 node)
        ListNode dummy1 = new ListNode(-1);
        // Don't assgin head to dummy1.next directly as normal case
        //dummy1.next = head;
        ListNode dummy2 = new ListNode(-1);
        
        ListNode itr = head;
        ListNode itr1 = dummy1;
        ListNode itr2 = dummy2;
        while(itr != null) {
        	// Important Point: Cut off a node as candidate,
        	// but have to store its connection for next loop,
        	// that's why we need to save a nextTemp here.
        	ListNode nextTemp = itr.next;
        	// Cut off its connection on original link, otherwise
        	// will encounter infinite loop issue
        	itr.next = null;
            if(itr.val < x) {
                itr1.next = itr;
                itr1 = itr1.next;
            } else {
                itr2.next = itr;
                itr2 = itr2.next;
            }
            // Assign stored original next node(information)
            // to itr for next loop
            itr = nextTemp;
        }
        
        // This check is not necessary
        //if(dummy2.next != null) {
        	// Skip dummy2 node to concatenate two sections
            // as itr1 already at the last node, point its
        	// next to dummy2's next node
            itr1.next = dummy2.next;
        //}
        
        return dummy1.next;
    }
    
    public static void main(String[] args) {
    	PartitionList p = new PartitionList();
    	//ListNode two = p.new ListNode(2);
    	ListNode one = p.new ListNode(1);
    	ListNode oneAgain = p.new ListNode(1);
    	//two.next = one;
    	one.next = oneAgain;
    	int x = 2;
    	//ListNode result = p.partition(two, x);
    	ListNode result = p.partition(one, x);
    	System.out.println(result.val);
    }
}
