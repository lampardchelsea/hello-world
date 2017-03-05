/**
 * Refer to
 * https://leetcode.com/problems/sort-list/?tab=Description
 *
 * Refer to
 * https://discuss.leetcode.com/topic/18100/java-merge-sort-solution
 * http://www.cnblogs.com/springfor/p/3869372.html
 * 考虑到要求用O(nlogn)的时间复杂度和constant space complexity来sort list，
 * 自然而然想到了merge sort方法。同时我们还已经做过了merge k sorted list和
 * merge 2 sorted list。这样这个问题就比较容易了。
 * 不过这道题要找linkedlist中点，那当然就要用最经典的faster和slower方法，faster
 * 速度是slower的两倍，当faster到链尾时，slower就是中点，slower的next是下一半的开始点
 */
public class SortList {
    private class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
    	    val = x; 
        }
    }
	
    public ListNode sortList(ListNode head) {
        if(head == null || head.next == null) {
            return head;
        }
        ListNode walker = head;
        ListNode runner = head;
        while(runner.next != null && runner.next.next != null) {
            walker = walker.next;
            runner = runner.next.next;
        }
        ListNode firstHalf = head;
        // walker is midNode, walker.next is start of second half
        ListNode secondHalf = walker.next;
        walker.next = null;
        ListNode l1 = sortList(firstHalf);
        ListNode l2 = sortList(secondHalf);
        return merge(l1, l2);
    }

    
//    public ListNode merge(ListNode l1, ListNode l2) {
//        ListNode dummy = new ListNode(-1);
//        ListNode itr = dummy;
//        ListNode itr1 = l1;
//        ListNode itr2 = l2;
//        while(itr1 != null && itr2 != null) {
//            if(itr1.val < itr2.val) {
//                itr.next = itr1;
//                itr1 = itr1.next;
//            } else {
//                itr.next = itr2;
//                itr2 = itr2.next;
//            }
//            itr = itr.next;
//        }
//        if(itr1 != null) {
//            itr.next = itr1;
//        }
//        if(itr2 != null) {
//            itr.next = itr2;
//        }
//        return dummy.next;
//    }
    
    // We don't need itr1 and itr2 for l1 and l2, because
    // don't need to store original l1 and l2 as result,
    // just use as temporary variable to get final result,
    // but for 'dummy' we need a copy of 'itr' to loop
    // through the list because we need to return dummy.next
    // as final result require reservation of dummy
    public ListNode merge(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1);
        ListNode itr = dummy;
        while(l1 != null && l2 != null) {
            if(l1.val < l2.val) {
                itr.next = l1;
                l1 = l1.next;
            } else {
                itr.next = l2;
                l2 = l2.next;
            }
            itr = itr.next;
        }
        if(l1 != null) {
            itr.next = l1;
        }
        if(l2 != null) {
            itr.next = l2;
        }
        return dummy.next;
    }
}

