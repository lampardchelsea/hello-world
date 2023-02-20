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








































https://leetcode.com/problems/linked-list-cycle/

Given head, the head of a linked list, determine if the linked list has a cycle in it.

There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the next pointer. Internally, pos is used to denote the index of the node that tail's next pointer is connected to. Note that pos is not passed as a parameter.

Return true if there is a cycle in the linked list. Otherwise, return false.

Example 1:


```
Input: head = [3,2,0,-4], pos = 1
Output: true
Explanation: There is a cycle in the linked list, where the tail connects to the 1st node (0-indexed).
```

Example 2:


```
Input: head = [1,2], pos = 0
Output: true
Explanation: There is a cycle in the linked list, where the tail connects to the 0th node.
```

Example 3:


```
Input: head = [1], pos = -1
Output: false
Explanation: There is no cycle in the linked list.
```

Constraints:
- The number of the nodes in the list is in the range [0, 104].
- -105 <= Node.val <= 105
- pos is -1 or a valid index in the linked-list.
 
Follow up: Can you solve it using O(1) (i.e. constant) memory?
---
Attempt 1: 2023-02-19

Solution 1:  (30 min)
```
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
        ListNode slow = head; 
        ListNode fast = head; 
        while(fast != null && fast.next != null) { 
            slow = slow.next; 
            fast = fast.next.next; 
            if(slow == fast) { 
                return true; 
            } 
        } 
        return false; 
    } 
}

Time Complexity: O(n) 
Space Complexity: O(1)
```

Refer to
https://leetcode.com/problems/linked-list-cycle/solutions/1829641/c-my-first-c-code-explained/
It's a classic algo for detecting cycles in a linked list. We use two pointers to traverse the list: The first one is moving one node at the time and the second two nodes at the time. If there is a cycle, sooner or later pointers will meet and we return true. If the fast pointer reached the end of the list, that means there is no cycle and we can return false.
For reference: Floyd's Cycle Detection Algorithm
Time: O(n) - for traversing
Space: O(1) - nothing stored
```
class Solution { 
public: 
    bool hasCycle(ListNode *head) { 
        ListNode *slow = head, *fast = head; 
        while (fast && fast->next) { 
            slow = slow->next; 
            fast = fast->next->next; 
            if (slow == fast) return true; 
        } 
         
        return false; 
    } 
};
```
