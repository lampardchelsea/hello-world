/**
 Refer to
 https://leetcode.com/problems/split-linked-list-in-parts/
 Given a (singly) linked list with head node root, write a function to split the linked list into k consecutive linked list "parts".
 
 The length of each part should be as equal as possible: no two parts should have a size differing by more than 1. 
 This may lead to some parts being null.The parts should be in order of occurrence in the input list, 
 and parts occurring earlier should always have a size greater than or equal parts occurring later.

 Return a List of ListNode's representing the linked list parts that are formed.

 Examples 1->2->3->4, k = 5 // 5 equal parts [ [1], [2], [3], [4], null ]
 
 Example 1:
 Input:
 root = [1, 2, 3], k = 5
 Output: [[1],[2],[3],[],[]]
 Explanation:
 The input and each element of the output are ListNodes, not arrays.
 For example, the input root has root.val = 1, root.next.val = 2, \root.next.next.val = 3, and root.next.next.next = null.
 The first element output[0] has output[0].val = 1, output[0].next = null.
 The last element output[4] is null, but it's string representation as a ListNode is [].

 Example 2:
 Input: 
 root = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], k = 3
 Output: [[1, 2, 3, 4], [5, 6, 7], [8, 9, 10]]
 Explanation:
 The input has been split into consecutive parts with size difference at most 1, and earlier parts are a larger size than the later parts.
 
 Note:
 The length of root will be in the range [0, 1000].
 Each value of a node in the input will be an integer in the range [0, 999].
 k will be an integer in the range [1, 50].
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/split-linked-list-in-parts/discuss/109296/JavaC%2B%2B-Clean-Code
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
    public ListNode[] splitListToParts(ListNode root, int k) {
        ListNode[] result = new ListNode[k];
        if(root == null) {
            return result;
        }
        int total_nodes = 0;
        // Find given list length
        ListNode iter = root;
        while(iter != null) {
            total_nodes++;
            iter = iter.next;
        }
        // If there are N nodes in the list, and k parts, then every part has 
        // N / k elements, except the first N % k parts have an extra one
        int base_num = total_nodes / k;
        int additional_sections = total_nodes % k;
        ListNode currStart = root;
        // 'prev' used for tracking as iterator and cut off tailing
        ListNode prev = new ListNode(-1);
        prev.next = currStart;
        for(int i = 0; i < k; i++) {
            result[i] = currStart;
            // For first N % k parts loop 1 more round
            if(i < additional_sections) {
                int count = base_num + 1;
                while(count > 0) {
                    prev = currStart;
                    currStart = currStart.next;
                    count--;
                }
            } else {
                int count = base_num;
                while(count > 0) {
                    prev = currStart;
                    currStart = currStart.next;
                    count--;
                }
            }
            // Cut off tailing
            prev.next = null;
        }
        return result;
    }
}

