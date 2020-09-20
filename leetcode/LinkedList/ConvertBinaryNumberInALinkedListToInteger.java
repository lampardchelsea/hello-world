/**
 Refer to
 https://leetcode.com/problems/convert-binary-number-in-a-linked-list-to-integer/
 Given head which is a reference node to a singly-linked list. The value of each node in the linked list is 
 either 0 or 1. The linked list holds the binary representation of a number.
 
 Return the decimal value of the number in the linked list.
 
 Example 1:
 1 -> 0 -> 1
 Input: head = [1,0,1]
 Output: 5
 Explanation: (101) in base 2 = (5) in base 10

 Example 2:
 Input: head = [0]
 Output: 0
 
 Example 3:
 Input: head = [1]
 Output: 1
 
 Example 4:
 Input: head = [1,0,0,1,0,0,1,1,1,0,0,0,0,0,0]
 Output: 18880
 
 Example 5:
 Input: head = [0,0]
 Output: 0
 
 Constraints:
 The Linked List is not empty.
 Number of nodes will not exceed 30.
 Each node's value is either 0 or 1.
*/

// Solution 1: Reverse linked list first
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/LinkedList/ReverseLinkedList.java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public int getDecimalValue(ListNode head) {
        int result = 0;
        ListNode prev = null;
        ListNode curr = head;
        while(curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        int count = 0;
        while(prev != null) {
            if(prev.val == 1) {
                result += 1 << count;
            }
            count++;
            prev = prev.next;
        }
        return result;
    }
}

// Solution 2: Binary Representation
// Refer to
// https://leetcode.com/problems/convert-binary-number-in-a-linked-list-to-integer/solution/
// Style 1: Check head != null
class Solution {
    public int getDecimalValue(ListNode head) {
        int result = 0;
        while(head != null) {
            result = result * 2 + head.val;
            head = head.next;
        }
        return result;
    }
}
// Style 2: Check head.next != null
class Solution {
    public int getDecimalValue(ListNode head) {
        int result = head.val;
        while(head.next != null) {
            result = result * 2 + head.next.val;
            head = head.next;
        }
        return result;
    }
}

// Solution 3: Bit Manipulation
// Refer to
// https://leetcode.com/problems/convert-binary-number-in-a-linked-list-to-integer/solution/
// Style 1: Check head != null
class Solution {
    public int getDecimalValue(ListNode head) {
        int result = 0;
        while(head != null) {
            result = (result << 1) | head.val;
            head = head.next;
        }
        return result;
    }
}

// Style 2: Check head.next != null
class Solution {
    public int getDecimalValue(ListNode head) {
        int result = head.val;
        while(head.next != null) {
            result = (result << 1) | head.next.val;
            head = head.next;
        }
        return result;
    }
}
