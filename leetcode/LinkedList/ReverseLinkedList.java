/**
 * Refer to
 * https://leetcode.com/problems/reverse-linked-list/?tab=Description
 * 
 * Refer to
 * https://leetcode.com/articles/reverse-linked-list/
 * http://algorithms.tutorialhorizon.com/reverse-a-linked-list/
 * http://algorithms.tutorialhorizon.com/reverse-a-linked-list-part-2/
 * http://stackoverflow.com/questions/354875/reversing-a-linked-list-in-java-recursively
 */
public class ReverseLinkedList {
	private class ListNode {
	    int val;
	    ListNode next;
	    ListNode(int x) { 
	    	val = x;
	    }
	}
	
	/**
	 * Solution 1: Iterative
	 * Assume that we have linked list 1 → 2 → 3 → Ø, we would like to change it to Ø ← 1 ← 2 ← 3.
	 * While you are traversing the list, change the current node's next pointer to point to 
	 * its previous element. Since a node does not have reference to its previous node, you must 
	 * store its previous element beforehand. You also need another pointer to store the next 
	 * node before changing the reference. Do not forget to return the new head reference at the end!
	 */
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while(curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            // Important: Should not reverse the setting order here,
            // first assign 'curr' to 'prev'
            //curr = nextTemp;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }
    
    /**
     * Solution 2: Recursive
     * The recursive version is slightly trickier and the key is to work backwards. 
     * Assume that the rest of the list had already been reversed, now how do I reverse 
     * the front part? Let's assume the list is: n1 → … → nk-1 → nk → nk+1 → … → nm → Ø(null)
     * Assume from node nk+1 to nm had been reversed and you are at node nk.
     * n1 → … → nk-1 → nk → nk+1 ← … ← nm
     * We want nk+1’s next node to point to nk.
     * So, nk.next.next = nk;
     * Be very careful that n1's next must point to Ø(null). If you forget about this, your 
     * linked list has a cycle in it. This bug could be caught if you test your code 
     * with a linked list of size 2, e.g [1, 2], [1, 2, 3]...
     */
    public ListNode reverseList2(ListNode head) {
    	// Reverse of null (the empty list)
    	if(head == null) {
    		return null;
    	}
    	// Reverse of a one element list
    	if(head.next == null) {
    		return head;
    	}
    	ListNode p = reverseList2(head.next);
    	head.next.next = head;
    	// Bug fix - need to unlink head from the rest or you will get a cycle,
    	// (head.next = null) breaks the connection between the element and its 
    	// subsequent element, without it you will later have a cycle between 
    	// this element and the subsequent element
    	/**
    	 * Also refer to
    	 * http://www.java2blog.com/2014/07/how-to-reverse-linked-list-in-java.html
    	 * Now lets understand logic for above recursive program.
    	 * E.g 5->6->7->1->2
    	 * Above function will terminate when last node(2) 's next will be null. so while returning when 
    	 * you reach at node with value 1,If you closely observe node.next.next=node is actually setting 2->1
    	 * (i.e. reversing the link between node with value 1 and 2) and node.next=null is removing link 1->2. 
    	 * So in each iteration, you are reversing link between two nodes.
    	 */
    	head.next = null;
    	return p;
    }
    
    /**
     * Solution 3: Iterative with dummy node
     * Refer to
     * https://leetcode.com/problems/reverse-linked-list-ii/?tab=Description
     * This solution compare to Solution 1 is more generic, for detail check on
     * http://www.cnblogs.com/springfor/p/3864303.html
     * https://discuss.leetcode.com/topic/8976/simple-java-solution-with-clear-explanation/2
     */
    public ListNode reverseList3(ListNode head) {
        if(head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode pre = dummy;
        ListNode start = pre.next;
        ListNode then = start.next;
        while(start.next != null) {
            start.next = then.next;
            then.next = pre.next;
            pre.next = then;
            then = start.next;
        }
        return dummy.next;
    }
    
    public static void main(String[] args) {
    	ReverseLinkedList r = new ReverseLinkedList();
    	ListNode one = r.new ListNode(1);
    	ListNode two = r.new ListNode(2);
    	ListNode three = r.new ListNode(3);
    	one.next = two;
    	two.next = three;
    	ListNode result = r.reverseList2(one);
    	System.out.println(result.val);
    }
    
}
