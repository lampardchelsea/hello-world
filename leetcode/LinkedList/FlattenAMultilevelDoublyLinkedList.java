/**
 Refer to
 https://leetcode.com/problems/flatten-a-multilevel-doubly-linked-list/
 You are given a doubly linked list which in addition to the next and previous pointers, it could have a child pointer, 
 which may or may not point to a separate doubly linked list. These child lists may have one or more children of their 
 own, and so on, to produce a multilevel data structure, as shown in the example below.

 Flatten the list so that all the nodes appear in a single-level, doubly linked list. You are given the head of the 
 first level of the list.

 Example 1:
 Input: head = [1,2,3,4,5,6,null,null,null,7,8,9,10,null,null,11,12]
 Output: [1,2,3,7,8,11,12,9,10,4,5,6]
 Explanation:
 The multilevel linked list in the input is as follows:
 
 After flattening the multilevel linked list it becomes:

 Example 2:
 Input: head = [1,2,null,3]
 Output: [1,3,2]
 Explanation:

 The input multilevel linked list is as follows:

  1---2---NULL
  |
  3---NULL

 Example 3:
 Input: head = []
 Output: []
 
 How multilevel linked list is represented in test case:
 We use the multilevel linked list from Example 1 above:

 1---2---3---4---5---6--NULL
         |
         7---8---9---10--NULL
             |
             11--12--NULL
             
 The serialization of each level is as follows:
 [1,2,3,4,5,6,null]
 [7,8,9,10,null]
 [11,12,null]
 
 To serialize all levels together we will add nulls in each level to signify no node connects to the upper node of the previous level. The serialization becomes:
 [1,2,3,4,5,6,null]
 [null,null,7,8,9,10,null]
 [null,11,12,null]
 
 Merging the serialization of each level and removing trailing nulls we obtain:
 [1,2,3,4,5,6,null,null,null,7,8,9,10,null,null,11,12]
 
 Constraints:
 Number of Nodes will not exceed 1000.
 1 <= Node.val <= 10^5
*/

// Solution 1: Intuitive flatten node with child link
// Refer to
// https://leetcode.com/problems/flatten-a-multilevel-doubly-linked-list/discuss/150321/Easy-Understanding-Java-beat-95.7-with-Explanation
/**
 Basic idea is straight forward:
 1.Start form the head , move one step each time to the next node
 2.When meet with a node with child, say node p, follow its child chain to the end and connect the tail node with p.next, by doing this we 
 merged the child chain back to the main thread
 3.Return to p and proceed until find next node with child.
 4.Repeat until reach null
*/
/*
// Definition for a Node.
class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;
};
*/

class Solution {
    public Node flatten(Node head) {
        if(head == null) {
            return head;
        }
        Node iter = head;
        while(iter != null) {
            // If current node has child, manipulate it
            if(iter.child != null) {
                // Find child link tail
                Node temp = iter.child;
                while(temp.next != null) {
                    temp = temp.next;
                }
                // Connect tail node to current node's next node
                // and since double linked list, the next node
                // should connect back to tail node
                temp.next = iter.next;
                if(iter.next != null) {
                    iter.next.prev = temp;
                }
                // Flatten current node child link and remove child link
                iter.next = iter.child;
                iter.child.prev = iter;
                iter.child = null;
            }
            iter = iter.next;
        }
        return head;
    }
}
