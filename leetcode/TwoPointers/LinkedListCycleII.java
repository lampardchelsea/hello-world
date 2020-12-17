/**
 * Refer to
 * http://www.lintcode.com/en/problem/linked-list-cycle-ii/
 * Given a linked list, return the node where the cycle begins.
 * If there is no cycle, return null.
 * Have you met this question in a real interview? Yes
 * Example
 * Given -21->10->4->5, tail connects to node index 1，return 10
 * 
 * Solution
 * https://discuss.leetcode.com/topic/2975/o-n-solution-by-using-two-pointers-without-change-anything?page=1
 * https://discuss.leetcode.com/topic/5284/concise-o-n-solution-by-using-c-with-detailed-alogrithm-description
 * public class Solution {
    public ListNode detectCycle(ListNode head) {  
        if(head == null) {
            return null;
        }
        ListNode walker = head;
        ListNode runner = head;
        boolean isCycle = false;
        // 这里的while判断上两种方式等效，区别在于后一种需要先判断runner != null也就是
        // head != null这个条件，正好，我们已经预先用(head == null)判断过了，而关于
        // 为何只能用runner而不是walker做判断是因为这种写法隐藏了一个现实就是walker
        // 走过的路runner已经走过，runner就像在前面趟地雷的，如果把runner改成了walker
        // 则意味着不考虑runner的死活只满足walker就行，但是这明显是不可以的
        while(runner != null && runner.next != null) {
        //while(runner.next != null && runner.next.next != null) {
            walker = walker.next;
            runner = runner.next.next;
            if(walker == runner) {
                isCycle = true;
                // Break out when find loop, otherwise will
                // fall in infinite loop
                break;
            }
        }
        if(!isCycle) {
            return null;
        } else {
            // As cycle already find, which means now 'walker' and
            // 'runner' are meeting together, as calculate, reset
            // 'walker'(either one is fine) back to head, and
            // keep 'runner' at meeting position, then move forward
            // 1 step further for both at a time, after they meet
            // again, the position is the start of cycle
            walker = head;
            while(walker != runner) {
                walker = walker.next;
                runner = runner.next;
            }
            return walker;
        }
    }
}
*/
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */

// Solution 1: Two Pointers
// Refer to
// https://leetcode.com/problems/linked-list-cycle-ii/discuss/44774/Java-O(1)-space-solution-with-detailed-explanation.
/**
Define two pointers slow and fast. Both start at head node, fast is twice as fast as slow. If it reaches the end it means there is no cycle, 
otherwise eventually it will eventually catch up to slow pointer somewhere in the cycle.

Let the distance from the first node to the the node where cycle begins be A, and let say the slow pointer travels travels A+B. The fast 
pointer must travel 2A+2B to catch up. The cycle size is N. Full cycle is also how much more fast pointer has traveled than slow pointer 
at meeting point.

A+B+N = 2A+2B
N=A+B
From our calculation slow pointer traveled exactly full cycle when it meets fast pointer, and since originally it travled A before starting 
on a cycle, it must travel A to reach the point where cycle begins! We can start another slow pointer at head node, and move both pointers 
until they meet at the beginning of a cycle.

public class Solution {
            public ListNode detectCycle(ListNode head) {
                ListNode slow = head;
                ListNode fast = head;
        
                while (fast!=null && fast.next!=null){
                    fast = fast.next.next;
                    slow = slow.next;
                    
                    if (fast == slow){
                        ListNode slow2 = head; 
                        while (slow2 != slow){
                            slow = slow.next;
                            slow2 = slow2.next;
                        }
                        return slow;
                    }
                }
                return null;
            }
        }
*/
public class Solution {
    public ListNode detectCycle(ListNode head) {
        ListNode walker = head;
        ListNode runner = head;
        boolean isCycle = false;
        while(runner != null && runner.next != null) {
            walker = walker.next;
            runner = runner.next.next;
            if(walker == runner) {
                isCycle = true;
                break;
            }
        }
        if(!isCycle) {
            return null;
        } else {
            walker = head;
            while(walker != runner) {
                walker = walker.next;
                runner = runner.next;
            }
            return walker;
        }
    }
}
