/**
 * Refer to
 * https://leetcode.com/problems/copy-list-with-random-pointer/?tab=Description
 * A linked list is given such that each node contains an additional random 
 * pointer which could point to any node in the list or null.
 * Return a deep copy of the list.
 * 
 * Solution
 * Refer to
 * https://discuss.leetcode.com/topic/7594/a-solution-with-constant-space-complexity-o-1-and-linear-time-complexity-o-n
 * An intuitive solution is to keep a hash table for each node in the list, via which we 
 * just need to iterate the list in 2 rounds respectively to create nodes and assign the 
 * values for their random pointers. As a result, the space complexity of this solution 
 * is O(N), although with a linear time complexity.
 * 
 * As an optimised solution, we could reduce the space complexity into constant. The idea 
 * is to associate the original node with its copy node in a single linked list. In this way, 
 * we don't need extra space to keep track of the new nodes.
 * 
 * The algorithm is composed of the follow three steps which are also 3 iteration rounds.
 * (1) Iterate the original list and duplicate each node. The duplicate of each node follows its original immediately.
 * (2) Iterate the new list and assign the random pointer for each duplicated node.
 * (3) Restore the original list and extract the duplicated nodes.
 * 
 * http://www.cnblogs.com/springfor/p/3864457.html
 * 第一遍，对每个node进行复制，并插入其原始node的后面，新旧交替，变成重复链表。如：原始：1->2->3->null，复制后：1->1->2->2->3->3->null
 * 第二遍，遍历每个旧node，把旧node的random的复制给新node的random，因为链表已经是新旧交替的。所以复制方法为：
 * node.next.random = node.random.next
 * 前面是说旧node的next的random，就是新node的random，后面是旧node的random的next，正好是新node，是从旧random复制来的。
 *  第三遍，则是把新旧两个表拆开，返回新的表即可。
 */
public class CopyListWithRandomPointer {
    private class RandomListNode {
        int label;
        RandomListNode next, random;
        RandomListNode(int x) {
            this.label = x;
        }
    }

    public RandomListNode copyRandomList(RandomListNode head) {
        // Be careful, for this question, we should not use this check
        // if given input as one node, it will directly return itself
        // as original not the copy
        // E.g
        // Input: {-1,#}
        // Output: Node with label -1 was not copied but a reference to the original one.
        //        if(head == null || head.next == null) {
        //            return head;
        //        }

        // First round: make copy of each node,
        // and link them together side-by-side in a single list.
        RandomListNode itr = head;
        RandomListNode nextTemp;
        while (itr != null) {
            // Create a copy node based on 'label'
            RandomListNode copy = new RandomListNode(itr.label);
            // Reserve original next node of 'itr' into 'nextTemp'
            nextTemp = itr.next;
            // Insert copy node between 'itr' and 'itr.next'
            copy.next = nextTemp;
            itr.next = copy;
            // Advance 'itr' to original next node of 'itr' as 'nextTemp'
            itr = nextTemp;
        }

        // Second round: assign random pointers for the copy nodes.
        itr = head;
        while (itr != null) {
            if (itr.random != null) {
                // Current node's next node(itr.next) is a copy node, 
                // its 'random' value will be assigned by current node's
                // 'random' value(a node point)'s next node
                // E.g 1 -> 1' -> 3 -> 3'
                // if 1 has random value as 3, then 1' has random value as 3'
                itr.next.random = itr.random.next;
            }
            // Skip the copy node
            // E.g 1 -> 1' -> 3 -> 3', if itr = 1 now, it will skip 1'
            itr = itr.next.next;
        }

        // Third round: restore the original list, and extract the copy list.
        itr = head;
        RandomListNode dummy = new RandomListNode(-1);
        RandomListNode copy;
        RandomListNode copyItr = dummy;
        while (itr != null) {
            // Reuse 'nextTemp' to reserve original next node of 'itr'
            // E.g 1 -> 1' -> 3 -> 3', if itr = 1 now, we reserve 3 in nextTemp
            nextTemp = itr.next.next;
            // E.g 1 -> 1' -> 3 -> 3', if itr = 1 now, copy is 1'
            copy = itr.next;
            // Point copyItr to copy, e.g if copyItr = dummy now, 
            // it will concatenate dummy to 1' to construct new list
            copyItr.next = copy;
            // Move copyItr to copy for next loop, e.g if copyItr = dummy now, move to 1'
            copyItr = copy;
            // Point itr to original next node which stored in 'nextTemp',
            // e.g if itr = 1 now, it will concatenate 3 to reconstruct
            // original list
            itr.next = nextTemp;
            // Move itr to nextTemp for next loop, e.g if itr = 1 now, move to 3
            itr = nextTemp;
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        CopyListWithRandomPointer c = new CopyListWithRandomPointer();
        RandomListNode one = c.new RandomListNode(1);
        RandomListNode two = c.new RandomListNode(2);
        RandomListNode three = c.new RandomListNode(3);
        RandomListNode four = c.new RandomListNode(4);
        one.next = two;
        two.next = three;
        three.next = four;
        one.random = three;
        two.random = four;
        RandomListNode result = c.copyRandomList(one);
        System.out.println(result.label);
    }
}











































































https://leetcode.com/problems/copy-list-with-random-pointer/
A linked list of length n is given such that each node contains an additional random pointer, which could point to any node in the list, or null.
Construct a deep copy of the list. The deep copy should consist of exactly n brand new nodes, where each new node has its value set to the value of its corresponding original node. Both the next and random pointer of the new nodes should point to new nodes in the copied list such that the pointers in the original list and copied list represent the same list state. None of the pointers in the new list should point to nodes in the original list.
For example, if there are two nodes X and Y in the original list, where X.random --> Y, then for the corresponding two nodes x and y in the copied list, x.random --> y.
Return the head of the copied linked list.
The linked list is represented in the input/output as a list of n nodes. Each node is represented as a pair of [val, random_index] where:
- val: an integer representing Node.val
- random_index: the index of the node (range from 0 to n-1) that the random pointer points to, or null if it does not point to any node.
Your code will only be given the head of the original linked list.

Example 1:


Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]

Example 2:


Input: head = [[1,1],[2,1]]
Output: [[1,1],[2,1]]

Example 3:


Input: head = [[3,null],[3,0],[3,null]]
Output: [[3,null],[3,0],[3,null]]

Constraints:
- 0 <= n <= 1000
- -10^4 <= Node.val <= 10^4
- Node.random is null or is pointing to some node in the linked list.
--------------------------------------------------------------------------------
Attempt 1: 2023-02-18
Solution 1: Copy list with three rounds (30 min)
/* 
// Definition for a Node. 
class Node { 
    int val; 
    Node next; 
    Node random; 
    public Node(int val) { 
        this.val = val; 
        this.next = null; 
        this.random = null; 
    } 
} 
*/ 
class Solution { 
    public Node copyRandomList(Node head) { 
        if(head == null) { 
            return null; 
        } 
        // First round: make copy of each node, and link them together side-by-side in a single list. 
        Node iter = head; 
        while(iter != null) { 
            Node next = iter.next; 
            iter.next = new Node(iter.val); 
            iter.next.next = next; 
            iter = next; 
        } 
        // Second round: assign random pointers for the copy nodes. 
        iter = head; 
        while(iter != null) { 
            if(iter.random != null) { 
                iter.next.random = iter.random.next; 
            } 
            iter = iter.next.next; 
        } 
        // Third round: restore the original list, and extract the copy list. 
        iter = head; 
        Node copyHead = head.next; 
        Node copyIter = copyHead; 
        while(copyIter.next != null) { 
            iter.next = iter.next.next; 
            iter = iter.next; 
            copyIter.next = copyIter.next.next; 
            copyIter = copyIter.next; 
        } 
        // And actually I have a question for last "node.next = node.next.next;",  
        // why we need this? Sine copy node is the last one in list.  
        // (e.g 3 in 1 - 1' - 2 - 2' - 3 - 3'). 
        // Because after the last loop, for the original list: 3->3'->null,  
        // so you need to remove 3'. Just make the last node.next = null 
        //iter.next = iter.next.next; 
        iter.next = null; 
        return copyHead; 
    } 
}

Time Complexity: O(n), where n is the number of nodes in the Linked List  
Space Complexity: O(1)

Refer to
https://leetcode.com/problems/copy-list-with-random-pointer/solutions/43491/a-solution-with-constant-space-complexity-o-1-and-linear-time-complexity-o-n/
An intuitive solution is to keep a hash table for each node in the list, via which we just need to iterate the list in 2 rounds respectively to create nodes and assign the values for their random pointers. As a result, the space complexity of this solution is O(N), although with a linear time complexity.
Note: if we do not consider the space reversed for the output, then we could say that the algorithm does not consume any additional memory during the processing, i.e. O(1) space complexity
As an optimizer solution, we could reduce the space complexity into constant. The idea is to associate the original node with its copy node in a single linked list. In this way, we don't need extra space to keep track of the new nodes.
The algorithm is composed of the follow three steps which are also 3 iteration rounds.
1.Iterate the original list and duplicate each node. The duplicate of each node follows its original immediately.
2.Iterate the new list and assign the random pointer for each duplicated node.
3.Restore the original list and extract the duplicated nodes.

The algorithm is implemented as follows:
public RandomListNode copyRandomList(RandomListNode head) { 
  RandomListNode iter = head, next; 
  // First round: make copy of each node, 
  // and link them together side-by-side in a single list. 
  while (iter != null) { 
    next = iter.next; 
    RandomListNode copy = new RandomListNode(iter.label); 
    iter.next = copy; 
    copy.next = next; 
    iter = next; 
  }

  // Second round: assign random pointers for the copy nodes. 
  iter = head; 
  while (iter != null) { 
    if (iter.random != null) { 
      iter.next.random = iter.random.next; 
    } 
    iter = iter.next.next; 
  }

  // Third round: restore the original list, and extract the copy list. 
  iter = head; 
  RandomListNode pseudoHead = new RandomListNode(0); 
  RandomListNode copy, copyIter = pseudoHead; 
  while (iter != null) { 
    next = iter.next.next; 
    // extract the copy 
    copy = iter.next; 
    copyIter.next = copy; 
    copyIter = copy; 
    // restore the original list 
    iter.next = next; 
    iter = next; 
  } 
  return pseudoHead.next; 
}





A solution with constant space complexity O(1) and linear time complexity O(N)      


Refer to
L328.Odd Even Linked List (Ref.L138,L725)
