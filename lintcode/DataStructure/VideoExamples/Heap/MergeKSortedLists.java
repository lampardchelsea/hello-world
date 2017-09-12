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


// Solution 2: Merge two by two
// Use O(n) time to half problem size
// T(k) = T(k/2) + O(n)
// 归并k个已排序链表，可以分解问题拆解成为，重复k次，归并2个已排序链表。
// 不过这种方法会在LeetCode中TLE（超出时间限制）。总共需要k次merge two sorted lists的合并过程。+
// 改进方法：
// 使用merge sort中的二分的思维，将包含k个链表的列表逐次分成两个部分，再逐次对两个链表合并，
// 这样就有 log(k)次合并过程，每次均使用merge two sorted lists的算法。时间复杂度O(nlog(k))。
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
    public ListNode mergeKLists(List<ListNode> lists) {  
        if(lists == null || lists.size() == 0) {
            return null;
        }
        while(lists.size() > 1) {
            List<ListNode> new_lists = new ArrayList<ListNode>(); 
            for(int i = 0; i < lists.size() - 1; i += 2) {
                ListNode merged_list = merge(lists.get(i), lists.get(i + 1));
                new_lists.add(merged_list);
            }
            if(lists.size() % 2 == 1) {
                new_lists.add(lists.get(lists.size() - 1));
            }
            lists = new_lists;
        }
        return lists.get(0);
    }
    
    private ListNode merge(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0);
        ListNode itr = dummy;
        while(a != null && b != null) {
            if(a.val < b.val) {
                itr.next = a;
                a = a.next;
            } else {
                itr.next = b;
                b = b.next;
            }
            itr = itr.next;
        }
        if(a != null) {
            itr.next = a;
        }
        if(b != null) {
            itr.next = b;
        }
        return dummy.next;
    }
}




