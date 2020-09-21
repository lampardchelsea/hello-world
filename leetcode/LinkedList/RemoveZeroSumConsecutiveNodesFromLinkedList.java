/**
 Refer to
 https://leetcode.com/problems/remove-zero-sum-consecutive-nodes-from-linked-list/
 Given the head of a linked list, we repeatedly delete consecutive sequences of nodes that sum to 0 until there are no such sequences.
 
 After doing so, return the head of the final linked list.  You may return any such answer.
 (Note that in the examples below, all sequences are serializations of ListNode objects.)

 Example 1:
 Input: head = [1,2,-3,3,1]
 Output: [3,1]
 Note: The answer [1,2,1] would also be accepted.

 Example 2:
 Input: head = [1,2,3,-3,4]
 Output: [1,2,4]

 Example 3:
 Input: head = [1,2,3,-3,-2]
 Output: [1]

 Constraints:
 The given linked list will contain between 1 and 1000 nodes.
 Each node in the linked list has -1000 <= node.val <= 1000.
*/

// Solution 1: Subarray sum equal K but without overlap indexes
/**
 1. Convert the linked list into an array.
 2. While you can find a non-empty subarray with sum = 0, erase it.
 3. Convert the array into a linked list.
*/
// Refer to
// How to calculate subarray sum equal K ?
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/array/SubarraySumEqualsK.java
// But in this problem we have to add a contrain as no overlap for subarray
/**
 E.g if no limitation, then with below function will generate few overlapped indexes result:
 	private List<int[]> noOverlapSubarraySumEqualZeroIndexes(List<Integer> list) {
		List<int[]> result = new ArrayList<int[]>();
        int[] sum = new int[list.size() + 1];
        for(int i = 1; i <= list.size(); i++) {
            sum[i] = sum[i - 1] + list.get(i - 1);
        }
        for(int start = 0; start < list.size(); start++) {
            for(int end = start + 1; end <= list.size(); end++) {
                if(sum[end] - sum[start] == 0) {
                    result.add(new int[] {start, end - 1});
                }
            }
        }
        return result;
	}
  
  Test out by:
  [1,3,2,-3,-2,5,5,-5,1]
  Result as below, we can see pair [3, 5] and [3, 7] both have conflict with [1, 4], since 3 is between 1 to 4,
  which must be avoided by add new condition as newer pair 'start' index must larger than previous pair 'end',
  introduce 'pre_end' to make it happen
  [1, 4] -> 3,2,-3,-2
  [3, 5] -> -3,-2,5
  [3, 7] -> -3,-2,5,5,-5
  [6, 7] -> 5,-5
*/

// Style 1:
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
    public ListNode removeZeroSumSublists(ListNode head) {
        ListNode iter = head;
        List < Integer > list = new ArrayList < Integer > ();
        while (iter != null) {
            list.add(iter.val);
            iter = iter.next;
        }
        List < int[] > all_indexes_pair = noOverlapSubarraySumEqualZeroIndexes(list);
        List < Integer > all_indexes = new ArrayList < Integer > ();
        for (int[] indexes: all_indexes_pair) {
            for (int i = 0; i < list.size(); i++) {
                if (i >= indexes[0] && i <= indexes[1]) {
                    all_indexes.add(i);
                }
            }
        }
        List < Integer > newList = new ArrayList < Integer > ();
        for (int i = 0; i < list.size(); i++) {
            if (!all_indexes.contains(i)) {
                newList.add(list.get(i));
            }
        }
        ListNode prev = new ListNode(-1);
        ListNode newIter = prev;
        for (int i = 0; i < newList.size(); i++) {
            ListNode node = new ListNode(newList.get(i));
            newIter.next = node;
            newIter = node;
        }
        return prev.next;
    }

    private List < int[] > noOverlapSubarraySumEqualZeroIndexes(List < Integer > list) {
        List < int[] > result = new ArrayList < int[] > ();
        int[] sum = new int[list.size() + 1];
        for (int i = 1; i <= list.size(); i++) {
            sum[i] = sum[i - 1] + list.get(i - 1);
        }
        int pre_end = 0;
        for (int start = 0; start < list.size(); start++) {
            for (int end = start + 1; end <= list.size(); end++) {
                if (sum[end] - sum[start] == 0 && start >= pre_end) {
                    pre_end = end;
                    result.add(new int[] {
                        start,
                        end - 1
                    });
                }
            }
        }
        return result;
    }
}

// Style 2:
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
    public ListNode removeZeroSumSublists(ListNode head) {
        ListNode iter = head;
        List < Integer > list = new ArrayList < Integer > ();
        while (iter != null) {
            list.add(iter.val);
            iter = iter.next;
        }
        List < Integer > all_indexes = noOverlapSubarraySumEqualZeroIndexes(list);
        List < Integer > newList = new ArrayList < Integer > ();
        for (int i = 0; i < list.size(); i++) {
            if (!all_indexes.contains(i)) {
                newList.add(list.get(i));
            }
        }
        ListNode prev = new ListNode(-1);
        ListNode newIter = prev;
        for (int i = 0; i < newList.size(); i++) {
            ListNode node = new ListNode(newList.get(i));
            newIter.next = node;
            newIter = node;
        }
        return prev.next;
    }

    private List < Integer > noOverlapSubarraySumEqualZeroIndexes(List < Integer > list) {
        List < Integer > result = new ArrayList < Integer > ();
        int[] sum = new int[list.size() + 1];
        for (int i = 1; i <= list.size(); i++) {
            sum[i] = sum[i - 1] + list.get(i - 1);
        }
        int pre_end = 0;
        for (int start = 0; start < list.size(); start++) {
            for (int end = start + 1; end <= list.size(); end++) {
                if (sum[end] - sum[start] == 0 && start >= pre_end) {
                    pre_end = end;
                    for (int i = start; i < end; i++) {
                        result.add(i);
                    }
                }
            }
        }
        return result;
    }
}






