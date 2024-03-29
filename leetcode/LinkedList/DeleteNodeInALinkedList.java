https://leetcode.com/problems/delete-node-in-a-linked-list/description/
There is a singly-linked list head and we want to delete a node node in it.
You are given the node to be deleted node. You will not be given access to the first node of head.
All the values of the linked list are unique, and it is guaranteed that the given node node is not the last node in the linked list.
Delete the given node. Note that by deleting the node, we do not mean removing it from memory. We mean:
- The value of the given node should not exist in the linked list.
- The number of nodes in the linked list should decrease by one.
- All the values before node should be in the same order.
- All the values after node should be in the same order.
Custom testing:
- For the input, you should provide the entire linked list head and the node to be given node. node should not be the last node of the list and should be an actual node in the list.
- We will build the linked list and pass the node to your function.
- The output will be the entire list after calling your function.

Example 1:

Input: head = [4,5,1,9], node = 5
Output: [4,1,9]
Explanation: You are given the second node with value 5, the linked list should become 4 -> 1 -> 9 after calling your function.

Example 2:

Input: head = [4,5,1,9], node = 1
Output: [4,5,9]
Explanation: You are given the third node with value 1, the linked list should become 4 -> 5 -> 9 after calling your function.
 
Constraints:
- The number of the nodes in the given list is in the range [2, 1000].
- -1000 <= Node.val <= 1000
- The value of each node in the list is unique.
- The node to be deleted is in the list and is not a tail node.
--------------------------------------------------------------------------------
Attempt 1: 2024-03-28
Solution 1: Trick to remove the node without access to header (30 min)
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }
}

Time Complexity: O(1)
Space Complexity: O(1)

Refer to
https://leetcode.com/problems/delete-node-in-a-linked-list/solutions/65464/easy-solution-in-java/
Since we couldn't enter the preceding node, we can not delete the given node. We can just copy the next node to the given node and delete the next one.
public void deleteNode(ListNode node) {
    node.val=node.next.val;
    node.next=node.next.next;
}

Refer to
https://algo.monster/liteproblems/237
Problem Description
In this problem, we're tasked with deleting a specific node from a singly-linked list, but with a twist: we're only given access to that particular node, not the head of the list. Moreover, the given node will not be the list's tail, and all node values in the list are unique. What makes this task unique is that typically, to remove a node from a linked list, we would need access to the node before the one we want to delete to adjust the next pointer. However, since we're only given the target node itself (and guaranteed it's not the last one), we need a different strategy. Our goal is to remove the node from the list while preserving the order of remaining nodes. It's important to understand that "deleting the node" here means ensuring the value of the said node doesn't appear in the linked list sequence, and the length of the linked list is decreased by one.
Intuition
Understanding the constraints and the standard operations on a linked list is key to solving this problem. Given that we cannot directly remove the target node by changing its previous node's next pointer (because we don't have access to it), we can use a clever trick: copy the value from the next node into the target node, and then remove the next node instead. This effectively overwrites the target node's value with its successor's value, and then deleting the successor (which we have access to) achieves the goal of removing the target node's value from the list. The key realization here is that we're not actually deleting the given node, but rather, we're making it a clone of the next node in terms of value, then skipping over the next node, effectively removing its presence from the list.
Solution Approach
The solution takes advantage of the properties of a singly-linked list and uses the constraint that is provided: We are given access to the node that must be deleted, and it is not the last node of the list.
Here's the step-by-step approach that is used in the solution:
- Copy the value from the next node into the current node. This is done by using the statement node.val = node.next.val. After this step, the current node (node) and the next node (node.next) have the same value, the one that was originally in node.next.
- Delete the next node from the linked list. This doesn't mean removing it from memory, since Python's garbage collector will take care of that eventually. We simply need to change the reference of the current node's next to skip the next node and point to the node following it. We accomplish this with node.next = node.next.next.
By performing these two steps, we've essentially shifted the value from the node.next to node, and then we unlink node.next from the chain. Visually, if our list was A -> B -> C -> D and we wanted to delete B, we make B take on the value and position of C, resulting in A -> C -> D. Then B (with the value of C) is no longer part of the list. This fulfills all the requirements for the deletion operation as per the problem statement.
Example Walkthrough
Let's assume we have a singly-linked list that looks as follows:
4 -> 5 -> 1 -> 9
And we're asked to delete the node with value 5 from it. We do not have access to the head of the list, only to the target node. Here's how we would solve it using the solution approach:
1.We start with the node we want to delete, which has the value 5. The list looks like this:
4 -> 5 -> 1 -> 9
2.According to our 1st step in the solution approach, we copy the value of the next node (1) into the current node (5). Our list will then look like this:
4 -> 1 -> 1 -> 9
3.Now, the node we wanted to delete (5) has been overwritten with the value of 1.
Next, we perform the 2nd step in our solution where we unlink the node after the current node (the second 1). This is done by setting the next pointer of our current node to the next of the next node. After this step, the list now looks like this:
4 -> 1 -> 9
4.The node originally containing 5 has now been removed (or rather its value copied over and its successor unlinked), and our linked list preserves the original order with the target node no longer present.
The deletion is successful and has been performed using the given access constraints. The linked list now correctly contains the sequence 4 -> 1 -> 9.
Java Solution
// Class definition for a singly-linked list node.
class ListNode {
    int val; // The value of the node.
    ListNode next; // Reference to the next node in the list.

    // Constructor to initialize the node with a value.
    ListNode(int x) {
        val = x;
    }
}

// Class containing the solution method.
class Solution {
    /**
     * Deletes a node (except the tail) from a singly-linked list, given only access to that node.
     * The input node should not be the tail, and the list should have at least two elements.
     * @param node the node to be deleted
     */
    public void deleteNode(ListNode node) {
        // Copy the value of the next node into the current node.
        node.val = node.next.val;
        // Set the current node's next pointer to skip the next node, effectively deleting it.
        node.next = node.next.next;
    }
}
Time and Space Complexity
The time complexity of the code is O(1). This is because it takes a constant amount of time to copy the value from the next node to the given node and to update the next pointer of the given node. No loops or recursive calls are involved.
The space complexity of the code is O(1). No extra space is used that is dependent on the size of the input. Only pointers are reassigned which does not require additional space.
