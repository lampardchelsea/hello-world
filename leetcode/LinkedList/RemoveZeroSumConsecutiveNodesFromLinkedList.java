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
        // sum[i] is used to store the cumulative sum of nums array upto the element corresponding 
	// to the (i - 1)th index. Thus, to determine the sum of elements for the subarray nums[i:j], 
	// we can directly use sum[j + 1] - sum[i]. --> sum[end] - sum[start] means sum of elements 
	// for the sub-list[start:end - 1]
	// e.g input list as [1, 2, -3, 3, 1] --> sum as [0, 1, 3, 0, 3, 4]
        // the first start / end pair will be start = 0, end = 3 since sum[3] - sum[0] = 0 - 0 = 0
	// so end = j + 1 --> j = 2, start = i --> i = 0, which means sum of sub-list[0:2] as [1, 2, -3]
	// is 0, which is correct, to no-overlap for next possible range, the next start should no
	// less than index 3, which is the last end index --> start >= pre_end
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

// Solution 2:
// Refer to
// https://leetcode.com/problems/remove-zero-sum-consecutive-nodes-from-linked-list/discuss/366319/JavaC%2B%2BPython-Greedily-Skip-with-HashMap
/**
 Intuition
Assume the input is an array.
Do you know how to solve it?
Scan from the left, and calculate the prefix sum.
Whenever meet the seen prefix,
remove all elements of the subarray between them.

Solution 1
Because the head ListNode can be removed in the end,
I create a dummy ListNode and set it as a previous node of head.
prefix calculates the prefix sum from the first node to the current cur node.

Next step, we need an important hashmap m (no good name for it),
It takes a prefix sum as key, and the related node as the value.

Then we scan the linked list, accumulate the node's value as prefix sum.

If it's a prefix that we've never seen, we set m[prefix] = cur.
If we have seen this prefix, m[prefix] is the node we achieve this prefix sum.
We want to skip all nodes between m[prefix] and cur.next (exclusive).
So we simplely do m[prefix].next = cur.next.
We keep doing these and it's done.

Complexity
Time O(N), one pass
SpaceO(N), for hashmap
*/

// https://leetcode.com/problems/remove-zero-sum-consecutive-nodes-from-linked-list/discuss/366337/Java-Iterative-and-Recursive-solution
/**
 Method 1: Iterative:
Use a hashmap to keep track the prefix sum.
If current sum is zero, it means the sum from first node to current node is zero. So the new head is the next node.
If the current sum already exists the hashmap (say node-i), it means the sum from node-(i+1) to current node is zero. So, we remove those nodes.
For example,
input = [1,2,3,-3,1]
i = 0, prefixSum = 1
i = 1, prefixSum = 3
i = 2, prefixSum = 6
i = 3, prefixSum = 3  // prefixsum = 3 is already in the hashmap. It means the sum of subarray [2,3] is zero.
i = 4, prefixSum = 4
Use a dummy head to simplify code.
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
    public ListNode removeZeroSumSublists(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode curr = dummy;
        int preSum = 0;
        Map<Integer, ListNode> map = new HashMap<Integer, ListNode>();
        while(curr != null) {
            preSum += curr.val;
            if(map.containsKey(preSum)) {
                // We have this preSum before then relocate cursor back to
                // that previous recorded start node's next node, which suppose
                // to be the first node of a chain (till 2nd preSum happen)
                // to remove, also we need to remove the corresponding 'key'
                // in the map, since when we build map follow the rule of
                // preSum calcualted by cumulative sum of all previous node,
                // we can find these preSum(s) based on (preSum + curr.val),
                // each loop till find the 2nd preSum (check by keyToRemove !=
                // preSum) will find a new preSum as key need to remove from map
                // e.g 1,2,-3,3,1
                // cumulative sum as 0,1,3,0,3,4
                // First time we see 0 and it map to dummy node
                // Second time we see 0 it map to node -3
                // the supposed section to remove is 1->2->-3
                // and then dummy node should go with next node of -3 as 3
                // during remove nodes, clean up key-value pair as
                // {1, node 1}, {3, node 2}, here 1 calculated based on
                // (preSum + curr.val) -> (0 + 1), 3 calculated based on
                // (preSum + curr.val) -> (1 + 2)
                curr = map.get(preSum).next;
                int keyToRemove = preSum + curr.val;
                while(keyToRemove != preSum) {
                    map.remove(keyToRemove);
                    curr = curr.next;
                    keyToRemove += curr.val;
                }
                // Now we cut off the section
                map.get(preSum).next = curr.next;
            } else {
                map.put(preSum, curr);
            }
            curr = curr.next;
        }
        return dummy.next;
    }
}




