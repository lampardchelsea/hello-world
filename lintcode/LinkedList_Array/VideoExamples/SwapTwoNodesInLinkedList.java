
/**
 * Refer to
 * http://www.lintcode.com/en/problem/swap-two-nodes-in-linked-list/
 * Given a linked list and two values v1 and v2. Swap the two nodes in the linked list with values v1 and v2. 
 * It's guaranteed there is no duplicate values in the linked list. If v1 or v2 does not exist in the 
 * given linked list, do nothing.
 	Notice
 	You should swap the two nodes with values v1 and v2. Do not directly swap the values of the two nodes.
	Have you met this question in a real interview? Yes
	Example
	Given 1->2->3->4->null and v1 = 2, v2 = 4.
	Return 1->4->3->2->null.
 * 
 * Solution
 * http://www.jiuzhang.com/solutions/swap-two-nodes-in-linked-list
 *
 */
public class SwapTwoNodesInLinkedList {
   private class ListNode {
       int val;
	   ListNode next;
	   ListNode(int x) { val = x; }
   }
   
   public ListNode swapNodes(ListNode head, int v1, int v2) {
       ListNode dummy = new ListNode(0);
       dummy.next = head;
       
       ListNode node1Prev = null, node2Prev = null;
       ListNode cur = dummy;
       while (cur.next != null) {
           if (cur.next.val == v1) {
               node1Prev = cur;
           } else if (cur.next.val == v2) {
               node2Prev = cur;
           }
           cur = cur.next;
       }
       
       // If one of v1 or v2 value not exist, then no change will happen
       if (node1Prev == null || node2Prev == null) {
           return head;
       }
       
       // Will throw Memory Limit Exceeded exception without this check
	   /**
       Input
		10->8->7->6->4->3
		8
		10
		Expected
		8->10->7->6->4->3
	    */
       if (node2Prev.next == node1Prev) {
           // make sure node2Prev.next is not node1Prev
           ListNode t = node1Prev;
           node1Prev = node2Prev;
           node2Prev = t;
       }
       
       ListNode node1 = node1Prev.next;
       ListNode node2 = node2Prev.next;
       ListNode node2Next = node2.next;
       
       // If branch specifically used for handling given v1, v2 adjacency case,
       // with previous code section if (node2Prev.next == node1Prev) {...} help
       // on reverse the reference of 'node1Prev' and 'node2Prev'
       // e.g v1 = 8, v2 = 10 with given input 10->8->... (10, 8 adjacency)
       if (node1Prev.next == node2Prev) {
           node1Prev.next = node2;
           node2.next = node1;
           node1.next = node2Next;
       } else {
       // The else branch for v1, v2 not adjacency case, 
       // e.g v1 = 8, v2 = 6 with given input 10->8->7->6->... (8, 6 not adjacency)
           node1Prev.next = node2;
           node2.next = node1.next;
           
           node2Prev.next = node1;
           node1.next = node2Next;
       }
       
       return dummy.next;
   }
   
   public static void main(String[] args) {
	   SwapTwoNodesInLinkedList s = new SwapTwoNodesInLinkedList();	   
	   /**
        Input
		10->8->7->6->4->3->null
		8
		10
		Expected
		8->10->7->6->4->3->null
	    */
	   ListNode one = s.new ListNode(10);
	   ListNode two = s.new ListNode(8);
	   ListNode three = s.new ListNode(7);
	   ListNode four = s.new ListNode(6);
	   ListNode five = s.new ListNode(4);
	   ListNode six = s.new ListNode(3);
	   one.next = two;
	   two.next = three;
	   three.next = four;
	   four.next = five;
	   five.next = six;
	   int v1 = 8;
	   int v2 = 10;
	   ListNode result = s.swapNodes(one, v1, v2);
	   System.out.println(result.val);
   }
}
