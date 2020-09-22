/**
 Refer to
 https://leetcode.com/problems/design-linked-list/
 Design your implementation of the linked list. You can choose to use a singly or doubly linked list.
A node in a singly linked list should have two attributes: val and next. val is the value of the current node, 
and next is a pointer/reference to the next node.
If you want to use the doubly linked list, you will need one more attribute prev to indicate the previous node 
in the linked list. Assume all nodes in the linked list are 0-indexed.

Implement the MyLinkedList class:
1.MyLinkedList() Initializes the MyLinkedList object.
2.int get(int index) Get the value of the indexth node in the linked list. If the index is invalid, return -1.
3.void addAtHead(int val) Add a node of value val before the first element of the linked list. After the insertion, 
  the new node will be the first node of the linked list.
4.void addAtTail(int val) Append a node of value val as the last element of the linked list.
5.void addAtIndex(int index, int val) Add a node of value val before the indexth node in the linked list. 
6.If index equals the length of the linked list, the node will be appended to the end of the linked list. 
7.If index is greater than the length, the node will not be inserted.
8.void deleteAtIndex(int index) Delete the indexth node in the linked list, if the index is valid.
 
Example 1:
Input
["MyLinkedList", "addAtHead", "addAtTail", "addAtIndex", "get", "deleteAtIndex", "get"]
[[], [1], [3], [1, 2], [1], [1], [1]]
Output
[null, null, null, null, 2, null, 3]

Explanation
MyLinkedList myLinkedList = new MyLinkedList();
myLinkedList.addAtHead(1);
myLinkedList.addAtTail(3);
myLinkedList.addAtIndex(1, 2);    // linked list becomes 1->2->3
myLinkedList.get(1);              // return 2
myLinkedList.deleteAtIndex(1);    // now the linked list is 1->3
myLinkedList.get(1);              // return 3
 

Constraints:
0 <= index, val <= 1000
Please do not use the built-in LinkedList library.
At most 2000 calls will be made to get, addAtHead, addAtTail,  addAtIndex and deleteAtIndex.
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/design-linked-list/discuss/186459/Java-Doubly-Linked-List-solution-73ms
/**
class MyLinkedList {
    class ListNode {
        int val;
        ListNode next;
        ListNode pre;
        ListNode(int value) {
            this.val = value;
        }
    }
    
    ListNode head, tail;
    int size;
    /** Initialize your data structure here. */
    public MyLinkedList() {
        head = new ListNode(0);
        tail = new ListNode(0);
        head.next = tail;
        tail.pre = head;
    }
    
    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        if (index < 0 || index >= size) return -1;
        ListNode curr = head;
        for (int i = 0; i <= index; i++) {
            curr = curr.next;
        }
        return curr.val;
    }
    
    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        addAtIndex(0, val);
    }
    
    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        addAtIndex(size, val);
    }
    
    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        if (index < 0 || index > size) return;
        ListNode curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        ListNode newNode = new ListNode(val);
        newNode.next = curr.next;
        newNode.next.pre = newNode;
        curr.next = newNode;
        newNode.pre = curr;
        size++;
    }
    
    
    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        if (index < 0 || index >= size) return;
        ListNode curr = head;
        for (int i = 0; i <= index; i++) {
            curr = curr.next;
        }
        // delete curr;
        curr.next.pre = curr.pre;
        curr.pre.next = curr.next;
        size--;
    }
}

class MyLinkedList {
    class ListNode {
        int val;
        ListNode next;
        ListNode prev;
        public ListNode(int val) {
            this.val = val;
        }
    }
    
    ListNode head;
    ListNode tail;
    int size = 0;
    
    /** Initialize your data structure here. */
    public MyLinkedList() {
        head = new ListNode(0);
        tail = new ListNode(0);
        head.next = tail;
        tail.prev = head;
    }
    
    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        if(index < 0 || index >= size) {
            return -1;
        }
        ListNode iter = head;
        while(index >= 0) {
            iter = iter.next;
            index--;
        }
        return iter.val;
    }
    
    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        addAtIndex(0, val);
    }
    
    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        addAtIndex(size, val);
    }
    
    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        if(index < 0 || index > size) {
            return;
        }
        ListNode iter = head;
        while(index > 0) {
            iter = iter.next;
            index--;
        }
        ListNode newNode = new ListNode(val);
        newNode.next = iter.next;
        iter.next.prev = newNode;
        iter.next = newNode;
        newNode.prev = iter;
        size++;
    }
    
    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        if(index < 0 || index >= size) {
            return;
        }
        ListNode iter = head;
        while(index >= 0) {
            iter = iter.next;
            index--;
        }
        iter.next.prev = iter.prev;
        iter.prev.next = iter.next;
        size--;
    }
}

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */
*/
