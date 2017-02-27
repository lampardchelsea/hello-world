/**
 * Refer to
 * https://discuss.leetcode.com/topic/12516/o-1-space-solution/7
 * You can make use of Floyd's cycle-finding algorithm, also know as tortoise and hare algorithm. 
 * The idea is to have two references to the list and move them at different speeds. Move one 
 * forward by 1 node and the other by 2 nodes. If the linked list has a loop they will definitely meet.
 * 
 * (1) Use two pointers, walker and runner .
 * (2) walker moves step by step. runner m oves two steps at time.
 * (3) if the Linked List has a cycle walk er and runner will meet at some point.
 * 
 * Explain how finding cycle start node in cycle linked list work ?
 * Refer to
 * http://stackoverflow.com/questions/2936213/explain-how-finding-cycle-start-node-in-cycle-linked-list-work
 * 
 * Why need runner.next != null && runner.next.next != null ?
 * there is a reason why we use runner.next != null && runner.next.next != null. 
 * We need to make sure that the runner can really move two steps. 
 * If runner can move two steps, walker can move one step
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
    public boolean hasCycle(ListNode head) {
        if(head == null) {
            return false;
        }
        ListNode walker = head;
        ListNode runner = head;
        while(runner.next != null && runner.next.next != null) {
            walker = walker.next;
            runner = runner.next.next;
            if(walker == runner) {
                return true;
            }
        }
        return false;
    }
}
