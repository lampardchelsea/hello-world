

// Improvement Solution:
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public boolean isPalindrome(ListNode head) {
        ListNode secondHalfStart = findSecondHalfStart(head);
        ListNode temp = reverseSecondHalf(secondHalfStart);
        return checkIdentical(head, temp);
    }

    public ListNode findSecondHalfStart(ListNode head) {
        if(head == null) {
            return null;
        }
        int len = 1;
        ListNode itr = head;
        while(itr.next != null) {
            len++;
            itr = itr.next;
        }
        // Improvement 1: No need to cut off relationship as
        // preMidNode --> midNode --> secondHalfStart, because
        // in checkIdentical() method we will only loop same
        // length on both section (itr1 != null && itr2 != null)
        // even we don't cut off relation on original list, we
        // will not loop additional length
        if(len == 1) {
            return head;
        } else if(len % 2 == 1) {
            itr = head;
            int x = len/2;
            while(x > 0) {
            	itr = itr.next;
            	x--;
            }
            return itr.next;
        } else {
            itr = head;
            int x = len/2;
            while(x > 0) {
            	itr = itr.next;
            	x--;
            }
            return itr;
        }
    }
    
    // Improvement 2: Use iterative way to save more time
    public ListNode reverseSecondHalf(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while(curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }
    
    public boolean checkIdentical(ListNode x, ListNode y) {
        if(x == null && y == null) {
            return true;
        }
        if(x == null || y == null) {
            return false;
        }
        ListNode itr1 = x;
        ListNode itr2 = y;
        while(itr1 != null && itr2 != null) {
            if(itr1.val != itr2.val) {
                return false;
            }
            itr1 = itr1.next;
            itr2 = itr2.next;
        }
        return true;
    }
}
