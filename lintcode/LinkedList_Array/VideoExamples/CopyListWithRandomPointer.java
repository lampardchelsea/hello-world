/**
 * Refer to
 * http://www.lintcode.com/en/problem/copy-list-with-random-pointer/
 * A linked list is given such that each node contains an additional random pointer 
 * which could point to any node in the list or null.
 * Return a deep copy of the list.
 *
 * Solution
 * http://www.jiuzhang.com/solutions/copy-list-with-random-pointer/
 * https://discuss.leetcode.com/topic/7594/a-solution-with-constant-space-complexity-o-1-and-linear-time-complexity-o-n
*/

/**
 * Definition for singly-linked list with a random pointer.
 * class RandomListNode {
 *     int label;
 *     RandomListNode next, random;
 *     RandomListNode(int x) { this.label = x; }
 * };
 */
public class Solution {
    /**
     * @param head: The head of linked list with a random pointer.
     * @return: A new head of a deep copy of the list.
     */
    public RandomListNode copyRandomList(RandomListNode head) {
        RandomListNode iter = head;
        copyNext(iter);
        iter = head;
        copyRandom(iter);
        iter = head;
        return splitList(iter);
    }
    
    // First round: make copy of each node,
	// and link them together side-by-side in a single list.
    private void copyNext(RandomListNode iter) {
        while(iter != null) {
            RandomListNode next = iter.next;
            RandomListNode copy = new RandomListNode(iter.label);
            iter.next = copy;
            copy.next = next;
            iter = next;
        }
    }
    
    // Second round: assign random pointers for the copy nodes.
    private void copyRandom(RandomListNode iter) {
        while(iter != null) {
            if(iter.random != null) {
                iter.next.random = iter.random.next;
            }
            iter = iter.next.next;
        }
    }
    
    // Third round: restore the original list, and extract the copy list.
    private RandomListNode splitList(RandomListNode iter) {
        // When we change the linkedlist structure, we should
        // use dummy node to re-construct
        RandomListNode dummy = new RandomListNode(0);
        RandomListNode copy;
        RandomListNode next;
        RandomListNode copyIter = dummy;
        while(iter != null) {
            next = iter.next.next;
            // extract copy
            copy = iter.next;
            copyIter.next = copy;
            copyIter = copy;
            // restore the original list
            iter.next = next;
            iter = next;
        }
        return dummy.next;
    }
    
}
