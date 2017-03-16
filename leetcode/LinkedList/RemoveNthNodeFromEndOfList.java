/**
 * Refer to
 * https://leetcode.com/problems/remove-nth-node-from-end-of-list/#/description
 * Given a linked list, remove the nth node from the end of list and return its head.
 * For example,
 * Given linked list: 1->2->3->4->5, and n = 2.
 * After removing the second node from the end, the linked list becomes 1->2->3->5.
 * Note:
 * Given n will always be valid.
 * Try to do this in one pass.
 * 
 * Solution:
 * Refer to
 * https://leetcode.com/articles/remove-nth-node-end-list/
 * https://discuss.leetcode.com/topic/7031/simple-java-solution-in-one-pass
 * http://www.cnblogs.com/springfor/p/3862219.html
 */
public class RemoveNthNodeFromEndOfList {
	private class ListNode {
		int val;
		ListNode next;
		ListNode(int x) { 
			val = x; 
		}
	}
	
	// Solution 1: Two pass solution
    public ListNode removeNthFromEnd(ListNode head, int n) {
    	// Be careful, as may delete the only head node,
    	// no set for 'head.next == null' case
        if(head == null) {
            return null;
        }
        // Find total length
        int length = 1;
        ListNode itr = head;
        while(itr.next != null) {
            itr = itr.next;
            length++;
        }
        // Find which node need to remove from the head(not like
        // problem description from end, start with index = 1 as head)
        int target = length - n + 1;
        // Set up dummy head in case of may delete original head
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        // Find the previous node of targeted remove node
        ListNode itr1 = dummy;
        while(target > 1) {
            itr1 = itr1.next;
            target--;
        }
        // Skip the removed node
        itr1.next = itr1.next.next;
        return dummy.next;
    }
    
    public static void main(String[] args) {
    	RemoveNthNodeFromEndOfList r = new RemoveNthNodeFromEndOfList();
    	ListNode one = r.new ListNode(1);
    	//ListNode two = r.new ListNode(2);
    	//one.next = two;
    	ListNode result = r.removeNthFromEnd(one, 1);
    	// If only head exist and need to remove,
    	// should not print val as no node exist
    	//System.out.println(result.val);
    }
}



// Solution 2:
// Refer to
// https://leetcode.com/articles/remove-nth-node-end-list/
// https://discuss.leetcode.com/topic/7031/simple-java-solution-in-one-pass
/**
 * The above algorithm could be optimized to one pass. Instead of one pointer, we could use two pointers. 
 * The first pointer advances the list by n+1n+1 steps from the beginning, while the second pointer starts 
 * from the beginning of the list. Now, both pointers are exactly separated by nn nodes apart. We maintain 
 * this constant gap by advancing both pointers together until the first pointer arrives past the last node. 
 * The second pointer will be pointing at the nnth node counting from the last. We relink the next pointer 
 * of the node referenced by the second pointer to point to the node's next next node.
*/
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if(head == null) {
            return null;
        }
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode walker = dummy;
        ListNode runner = dummy;
        // If runner ahead (n + 1) positions of walker,
        // it will move forward about length + 1 - (n + 1)
        // = length - n steps to reach past of last node
        // as null, and we move forward walker and runner
        // pointers at the same pace, then walker will
        // also move length - n steps forward, which will
        // be n steps to away from end
        int steps = n + 1;
        while(steps > 0) {
            runner = runner.next;
            steps--;
        }
        while(runner != null) {
            walker = walker.next;
            runner = runner.next;
        }
        walker.next = walker.next.next;
        return dummy.next;
    }
    
}
