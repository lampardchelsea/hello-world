/**
Refer to
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

// Solution 1: One Pointer (Two Pass)
// Refer to
// https://leetcode.com/problems/remove-nth-node-from-end-of-list/solution/
/**
Approach 1: Two pass algorithm
Intuition
We notice that the problem could be simply reduced to another one : Remove the (L - n + 1) th node from the beginning in the list , 
where L is the list length. This problem is easy to solve once we found list length L.

Algorithm
First we will add an auxiliary "dummy" node, which points to the list head. The "dummy" node is used to simplify some corner cases 
such as a list with only one node, or removing the head of the list. On the first pass, we find the list length L. Then we set a 
pointer to the dummy node and start to move it through the list till it comes to the (L - n) th node. We relink next pointer 
of the (L - n) th node to the (L - n + 2) th node and we are done.

Complexity Analysis
Time complexity : O(L). The algorithm makes two traversal of the list, first to calculate list length L and second to find the (L - n) th node. 
There are 2L-n operations and time complexity is O(L).
Space complexity : O(1). We only used constant extra space.
*/
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
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode iter = dummy;
        int len = 0;
        while(iter.next != null) {
            iter = iter.next;
            len++;
        }
        len -= n;
        ListNode iter1 = dummy;
        while(len > 0) {
            iter1 = iter1.next;
            len--;
        }
        iter1.next = iter1.next.next;
        return dummy.next;
    }
}


// Solution 2: Two Pointers (One Pass)
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/LinkedList/RemoveNthNodeFromEndOfList.java
/**
 * The above algorithm could be optimized to one pass. Instead of one pointer, we could use two pointers. 
 * The first pointer advances the list by n+1 steps from the beginning, while the second pointer starts 
 * from the beginning of the list. Now, both pointers are exactly separated by n nodes apart. We maintain 
 * this constant gap by advancing both pointers together until the first pointer arrives past the last node. 
 * The second pointer will be pointing at the nnth node counting from the last. We relink the next pointer 
 * of the node referenced by the second pointer to point to the node's next next node.
*/
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
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode walker = dummy;
        ListNode runner = dummy;
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
