/** 
 * Refer to
 * http://www.lintcode.com/en/problem/reverse-linked-list-ii/
 * Reverse a linked list from position m to n.
   Notice
  Given m, n satisfy the following condition: 1 ≤ m ≤ n ≤ length of list.

  Have you met this question in a real interview? Yes
  Example
  Given 1->2->3->4->5->NULL, m = 2 and n = 4, return 1->4->3->2->5->NULL.
 *
 * Solution
 * http://www.jiuzhang.com/solutions/reverse-linked-list-ii/
 * https://github.com/lampardchelsea/hello-world/blob/108b14f402d6d323029bcec9db8ee67cc755b23b/leetcode/LinkedList/ReverseLinkedListII.java#L102
*/

/**
 * Definition for ListNode
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */


public class Solution {
    /*
     * @param head: ListNode head is the head of the linked list 
     * @param m: An integer
     * @param n: An integer
     * @return: The head of the reversed ListNode
     */
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if(head == null) {
            return null;
        }
        // To reconsturct the list need create dummy node
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        // Because dummy relate to final return(dummy.next),
        // need to create new reference point to this dummy node
        ListNode itr = dummy;
        // Start from 1 because 'm' is not indexed value, it represents
        // which node in list, so start from 1
        for(int i = 1; i < m; i++) {
            itr = itr.next;
        }
        
        // Below process exactly similar thing as
        // https://github.com/lampardchelsea/hello-world/blob/108b14f402d6d323029bcec9db8ee67cc755b23b/leetcode/LinkedList/ReverseLinkedListII.java
        
        // Mark node before position 'm' as 'prevmNode',
        // used for looply reverse
        ListNode prevmNode = itr;
        // A pointer to the beginning of a sub-list that will be reversed
        ListNode mNode = itr.next;
        // Additional to the original Reverse Linked List, we also have
        // to consider ListNode 'nNode' and its next node 'postnNode'
        // as only part of list will reversed
        ListNode nNode = mNode;
        ListNode postnNode = mNode.next;
        // Use 'nNode' and 'postnNode' to iterate instead of 'prevmNode' and
        // 'mNode' in while loop, because after iterate still need
        // original 'prevmNode' and 'mNode' reference to concatenate
        for(int i = m; i < n; i++) {
            ListNode then = postnNode.next;
            postnNode.next = nNode;
            nNode = postnNode;
            postnNode = then;
        }
        // Concatenate two ends with original 'prevmNode' and 'mNode'
        mNode.next = postnNode;
        prevmNode.next = nNode;
        
        return dummy.next;
    }
}
