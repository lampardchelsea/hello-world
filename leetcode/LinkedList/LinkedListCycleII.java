/**
 * Refer to
 * https://discuss.leetcode.com/topic/2975/o-n-solution-by-using-two-pointers-without-change-anything/17
 * My solution is like this: using two pointers, one of them one step at a time. 
 * another pointer each take two steps. Suppose the first meet at step k,the length of the Cycle is r. so..2k-k=nr,k=nr
 * Now, the distance between the start node of list and the start node of cycle is s. 
 * the distance between the start of list and the first meeting node is k(the pointer 
 * which wake one step at a time waked k steps).the distance between the start node of cycle 
 * and the first meeting node is m, so...s=k-m, s=nr-m, the 1st pointer starts from beginning of the list while 
 * the 2nd pointer starts from meet point, they will meet in the cycle point but the 2nd pointer walked n times of the cycle
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
public class Solution {
    public ListNode detectCycle(ListNode head) {
        // Detect if exist cycle, if not return null
        if(head == null) {
            return null;
        }
        ListNode walker = head;
        ListNode runner = head;
        boolean isCycle = false;
        while(runner.next != null && runner.next.next != null) {
            walker = walker.next;
            runner = runner.next.next;
            if(walker == runner) {
                isCycle = true;
                // Break out when find loop, otherwise will
                // fall in infinite loop
                break;
            }
        }
        // If there exist cycle, then handle as one pointer at
        // linklist start(head), one start at meeting place.
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
        }
        return walker;
    }
}
