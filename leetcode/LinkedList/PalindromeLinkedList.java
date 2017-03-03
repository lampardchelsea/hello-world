// Solution 1:
/**
 * Refer to
 * https://leetcode.com/problems/palindrome-linked-list/?tab=Description
 * Given a singly linked list, determine if it is a palindrome.
 * Follow up:
 * Could you do it in O(n) time and O(1) space ?
 * 
 * Refer to
 * http://www.geeksforgeeks.org/function-to-check-if-a-singly-linked-list-is-palindrome/
 * This method takes O(n) time and O(1) extra space.
 * 1) Get the middle of the linked list.
 * 2) Reverse the second half of the linked list.
 * 3) Check if the first half and second half are identical.
 * 4) Construct the original linked list by reversing the second half again and attaching it back to the first half
 * 
 * To divide the list in two halves, method 2 of this post is used.
 * When number of nodes are even, the first and second half contain exactly half nodes. 
 * The challenging thing in this method is to handle the case when number of nodes are odd. 
 * We don’t want the middle node as part of any of the lists as we are going to compare them 
 * for equality. For odd case, we use a separate variable ‘midnode’.
 */
public class PalindromeLinkedList {
    private class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
    	    val = x; 
        }
    }
    public boolean isPalindrome(ListNode head) {
        ListNode secondHalfStart = findSecondHalfStart(head);
        ListNode temp = reverseSecondHalf(secondHalfStart);
        boolean result = checkIdentical(head, temp);
        return result;
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
        ListNode preMidNode;
        ListNode midNode;
        ListNode secondHalfStart;
        // Length equal to 1 is a special case, use [1, 2] to detect
        // just need to return head itself as itr.next is null
        if(len == 1) {
            return head;
        } else if(len % 2 == 1) {
            itr = head;
            int x = len/2;
            // Modify x > 0 to x > 1 in case of looking for preMidNode
            // of midNode and then cut off connection between
            // preMidNode --> midNode in odd item case 
            while(x > 1) {
            	itr = itr.next;
            	x--;
            }
            // Store itr as preMidNode 
            preMidNode = itr;
            // Store midNode in odd item case
            midNode = itr.next;
            // Cut off preMidNode --> midNode 
            preMidNode.next = null;
            // Store midNode.next as secondHalfStart
            secondHalfStart = midNode.next;
            // Cut off midNode --> secondHalfStart
            midNode.next = null;
            return secondHalfStart;
        } else {
            itr = head;
            int x = len/2;
            while(x > 1) {
            	itr = itr.next;
            	x--;
            }
            preMidNode = itr;
            midNode = itr.next;
            // Cut off preMidNode --> secondHalfStart(midNode)
            //preMidNode.next = null;
            secondHalfStart = midNode;
            return secondHalfStart;
        }
    }
    
    public ListNode reverseSecondHalf(ListNode head) {
        if(head == null) {
            return null;
        }
        if(head.next == null) {
            return head;
        }
        ListNode p = reverseSecondHalf(head.next);
        head.next.next = head;
        head.next = null;
        return p;
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
        // Need to start compare including current head on
        // both half, if not include itr1 and itr2 by skipping
        // as itr1.next != null && itr2.next != null will miss
        // they are not equal case, e.g [1, 2]
        while(itr1 != null && itr2 != null) {
            if(itr1.val != itr2.val) {
                return false;
            }
            itr1 = itr1.next;
            itr2 = itr2.next;
        }
        return true;
    }
	
	public static void main(String[] args) {
		PalindromeLinkedList p = new PalindromeLinkedList();
		ListNode one = p.new ListNode(1);
		ListNode two = p.new ListNode(2);
//		ListNode three = p.new ListNode(3);
//		ListNode four = p.new ListNode(4);
		ListNode three = p.new ListNode(2);
		ListNode four = p.new ListNode(1);
		one.next = two;
		two.next = three;
		three.next = four;
		boolean result = p.isPalindrome(one);
		System.out.println(result);
	}
}




// Improvement Solution 1:
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
