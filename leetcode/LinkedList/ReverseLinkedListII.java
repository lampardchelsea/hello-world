/**
 * Refer to
 * https://leetcode.com/problems/reverse-linked-list-ii/?tab=Description
 * Reverse a linked list from position m to n. Do it in-place and in one-pass.
 * For example:
	Given 1->2->3->4->5->NULL, m = 2 and n = 4,	
	return 1->4->3->2->5->NULL.
 * Note:
 * Given m, n satisfy the following condition:
 * 1 ≤ m ≤ n ≤ length of list.
 */
public class ReverseLinkedListII {
    private class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
    	    val = x; 
        }
    }
    
    // Wrong solution: cause infinite loop
    // This can be detect by given [3, 5] and (m = 1, n = 2)
//    public ListNode reverseBetween(ListNode head, int m, int n) {
//        if(head == null || head.next == null) {
//            return head;
//        } 
//        ListNode itr = head;
//        int count = m;
//        while(itr != null && count > 1) {
//            itr = itr.next;
//            count--;
//        }
//        ListNode pre = itr;
//        ListNode curr = itr.next;
//        for(int i = 0; i < n - m; i++) {
//            ListNode nextTemp = curr.next;
//            curr.next = pre;
//            pre = curr;
//            curr = nextTemp;
//        }
//        return head;
//    }
    
    // Right Solution: 
    /**
     * Refer to
     * http://www.cnblogs.com/springfor/p/3864303.html
     * https://discuss.leetcode.com/topic/8976/simple-java-solution-with-clear-explanation/2
     * 经典的题目就是链表逆序啦，一般的链表逆序是让把链表从前到后都逆序，这个是给定了起始位置和结束位置，方法是一样的。
     * 就是维护3个指针，startpoint，node1和node2。
     * startpoint永远指向需要开始reverse的点的前一个位置。
     * node1指向正序中第一个需要rever的node，node2指向正序中第二个需要reverse的node。 
     * 交换后，node1 在后，node2在前。这样整个链表就逆序好了。
	     public ListNode reverseBetween(ListNode head, int m, int n) {
	        ListNode newhead = new ListNode(-1);
	        newhead.next = head;
	        
	        if(head==null||head.next==null)
	            return newhead.next;
	            
	        ListNode startpoint = newhead;//startpoint指向需要开始reverse的前一个
	        ListNode node1 = null;//需要reverse到后面去的节点
	        ListNode node2 = null;//需要reverse到前面去的节点
	        
	        for (int i = 0; i < n; i++) {
	            if (i < m-1){
	                startpoint = startpoint.next;//找真正的startpoint
	            } else if (i == m-1) {//开始第一轮
	                node1 = startpoint.next;
	                node2 = node1.next;
	            }else {
	                node1.next = node2.next;//node1交换到node2的后面
	                node2.next = startpoint.next;//node2交换到最开始
	                startpoint.next = node2;//node2作为新的点
	                node2 = node1.next;//node2回归到node1的下一个，继续遍历
	            }
	        }
	        return newhead.next;
	    }
     * 
     */
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if(head == null || head.next == null) {
            return head;
        }
        // Create a 'dummy' node to mark the head of this list
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode itr = dummy;
        // Use (m - 1) to guarantee get the 'pre'
        for(int i = 0; i < m - 1; i++) {
        	itr = itr.next;
        }
        // Make a pointer 'pre' as a marker for the node before reversing section
	// Actually, we can direclty use 'itr' either, without set up a 'pre' to
	// separately declare the same thing again, refer to
	// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/LinkedList/ReverseNodesInKGroup.java
        // http://www.cnblogs.com/lichen782/p/leetcode_Reverse_Nodes_in_kGroup.html
        ListNode pre = itr;
        // A pointer to the beginning of a sub-list that will be reversed
        ListNode start = pre.next;
        // A pointer to a node that will be reversed
        ListNode then = start.next;
        // E.g
	    // Given 1 - 2 - 3 - 4 - 5 ; m = 2; n = 4 ---> pre = 1, start = 2, then = 3
	    // dummy -> 1 -> 2 -> 3 -> 4 -> 5
	    // first reversing : dummy -> 1 -> 3 -> 2 -> 4 -> 5; pre = 1, start = 2, then = 4
	    // second reversing: dummy -> 1 -> 4 -> 3 -> 2 -> 5; pre = 1, start = 2, then = 5 (finish)
        for(int i = 0; i < n - m; i++) {
        	start.next = then.next; // Switch 'start' to the later position of 'then'
        	then.next = pre.next; // Switch 'then' to the later position of 'pre'(beginning)
        	pre.next = then; // Set 'then' as new beginning
        	then = start.next; // Put 'then' back to the next of 'start' and prepare for next loop
        }
        return dummy.next;
    }
    
    
    public static void main(String[] args) {
    	ReverseLinkedListII r = new ReverseLinkedListII();
    	// Test 1:
//    	ListNode three = r.new ListNode(3);
//    	ListNode five = r.new ListNode(5);
//    	three.next = five;
//    	ListNode result = r.reverseBetween(three, 1, 2);
    	// Test 2:
    	ListNode one = r.new ListNode(1);
    	ListNode two = r.new ListNode(2);
    	ListNode three = r.new ListNode(3);
    	ListNode four = r.new ListNode(4);
    	ListNode five = r.new ListNode(5);
    	one.next = two;
    	two.next = three;
    	three.next = four;
    	four.next = five;
    	ListNode result = r.reverseBetween(one, 2, 4);    	
    	System.out.println(result.val);
    }
}

// Re-work
class Solution {
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if(head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        for(int i = 0; i < m - 1; i++) {
            prev = prev.next;
        }
        ListNode start = prev.next;
        ListNode then = start.next;
        for(int i = 0; i < n - m; i++) {
            start.next = then.next;
            then.next = prev.next;
            prev.next = then;
            then = start.next;
        }
        return dummy.next;
    }
}
