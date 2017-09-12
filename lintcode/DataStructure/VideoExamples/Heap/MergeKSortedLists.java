/**
 * Refer to
 * http://www.lintcode.com/en/problem/merge-k-sorted-lists/
 * Merge k sorted linked lists and return it as one sorted list.

    Analyze and describe its complexity.

    Have you met this question in a real interview? Yes
    Example
    Given lists:

    [
      2->4->null,
      null,
      -1->null
    ],
    return -1->2->4->null.
 *
 * Solution
 * http://www.jiuzhang.com/solutions/merge-k-sorted-lists/
*/

// Solution 1: Heap
/**
 * Definition for ListNode.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int val) {
 *         this.val = val;
 *         this.next = null;
 *     }
 * }
 */ 
public class Solution {
    /**
     * @param lists: a list of ListNode
     * @return: The head of one sorted list.
     */
    private Comparator<ListNode> comparator = new Comparator<ListNode>() {
        public int compare(ListNode a, ListNode b) {
            return a.val - b.val;
        }  
    };
    
    public ListNode mergeKLists(List<ListNode> lists) {  
        if(lists == null || lists.size() == 0) {
            return null;
        }
        PriorityQueue<ListNode> queue = new PriorityQueue<ListNode>(lists.size(), comparator);
        ListNode dummy = new ListNode(0);
        ListNode itr = dummy;
        for(ListNode node : lists) {
            if(node != null) {
                queue.add(node);
            }
        }
        while(!queue.isEmpty()) {
            ListNode head = queue.poll();
            itr.next = head;
            itr = head;
            if(head.next != null) {
                queue.add(head.next);
            }
        }
        return dummy.next;
    }
    
}
