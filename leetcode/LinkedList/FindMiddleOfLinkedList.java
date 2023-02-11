/**
 * Refer to
 * http://www.geeksforgeeks.org/write-a-c-function-to-print-the-middle-of-the-linked-list/
 * Given a singly linked list, find middle of the linked list. 
 * For example, if given linked list is 1->2->3->4->5 then output should be 3.
 * If there are even nodes, then there would be two middle nodes, we need to print second middle element. 
 * For example, if given linked list is 1->2->3->4->5->6 then output should be 4.
 * 
 * Solution:
 * Method 1:
 * Traverse the whole linked list and count the no. of nodes. 
 * Now traverse the list again till count/2 and return the node at count/2.
 * Method 2:
 * Traverse linked list using two pointers. Move one pointer by one and other pointer by two. 
 * When the fast pointer reaches end slow pointer will reach middle of the linked list.
 */
public class FindMiddleOfLinkedList {
    private class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
    	    val = x; 
        }
    }
	
    // Method 1: Traverse with counter
	public ListNode findMiddle(ListNode head) {
		if(head == null) {
			return head;
		}
		int count = 1;
		ListNode itr = head;
		while(itr.next != null) {
			count++;
			itr = itr.next;
		}
		int x = count/2;
		itr = head;
		while(x > 0) {
			itr = itr.next;
			x--;
		} 
		return itr;
	}
	
	// Method 2: Two pointers traverse
	public ListNode findMiddle2(ListNode head) {
		if(head == null) {
			return head;
		}
		ListNode walker = head;
		ListNode runner = head;
		while(runner != null && runner.next != null) {
			walker = walker.next;
			runner = runner.next.next;
		}
		return walker;
	}
	
	// Method 3: Two pointers traverse with tail condition check(not null)
	public ListNode findMiddle3(ListNode head) {
		if(head == null) {
			return head;
		}
		ListNode walker = head;
		ListNode runner = head;
		ListNode tail = head;
		// Find the tail of linked list 
		// (condition is node.next point to null)
		while(tail.next != null) {
			tail = tail.next;
		}
		// This is the same way as 
		// while(runner != null && runner.next != null)
		// and used with first get tail as a combination will
		// help on more general cases, e.g Convert Sorted List to Binary Search Tree
		// if use preorder way to convert, we need to recursively find mid node
		// with given start node and end node
		// https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree/?tab=Description
		while(runner != tail.next && runner.next != tail.next) {
			walker = walker.next;
			runner = runner.next.next;
		}
		return walker;
	}
	
	public static void main(String[] args) {
		FindMiddleOfLinkedList f = new FindMiddleOfLinkedList();
		ListNode one = f.new ListNode(1);
		ListNode two = f.new ListNode(2);
		ListNode three = f.new ListNode(3);
		ListNode four = f.new ListNode(4);
		ListNode five = f.new ListNode(5);
		ListNode six = f.new ListNode(6);
		//ListNode seven = f.new ListNode(7);
		one.next = two;
		two.next = three;
		three.next = four;
		four.next = five;
		five.next = six;
		//six.next = seven;
//		int result = f.findMiddle(one).val;
//		int result = f.findMiddle2(one).val;
		int result = f.findMiddle3(one).val;
		System.out.println(result);
	}
}
















































https://leetcode.com/problems/middle-of-the-linked-list/

Given the head of a singly linked list, return the middle node of the linked list.

If there are two middle nodes, return the second middle node.

Example 1:


```
Input: head = [1,2,3,4,5]
Output: [3,4,5]
Explanation: The middle node of the list is node 3.
```

Example 2:


```
Input: head = [1,2,3,4,5,6]
Output: [4,5,6]
Explanation: Since the list has two middle nodes with values 3 and 4, we return the second one.
```
 
Constraints:
- The number of nodes in the list is in the range [1, 100].
- 1 <= Node.val <= 100
---
Attempt 1: 2023-02-10

Solution 1: Count the length and store the node by the way (10 min)
```
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
    public ListNode middleNode(ListNode head) { 
        ListNode[] list = new ListNode[100]; 
        ListNode dummy = new ListNode(); 
        dummy.next = head; 
        ListNode iter = dummy; 
        int count = 0; 
        while(iter.next != null) { 
            iter = iter.next; 
            list[count++] = iter; 
        } 
        return list[count / 2]; 
    } 
}

=========================================================================
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
    public ListNode middleNode(ListNode head) { 
        ListNode[] list = new ListNode[100];
        int count = 0; 
        while(head != null) { 
            list[count++] = head; 
            head = head.next;
        } 
        return list[count / 2]; 
    } 
}

Time Complexity: O(n)    
Space Complexity: O(n)
```

Refer to
https://leetcode.com/problems/middle-of-the-linked-list/solutions/154715/middle-of-the-linked-list/

Approach 1: Output to Array

Intuition and Algorithm
Put every node into an array A in order. Then the middle node is just A[A.length // 2], since we can retrieve each node by index.
We can initialize the array to be of length 100, as we're told in the problem description that the input contains between 1 and 100 nodes.
```
class Solution { 
    public ListNode middleNode(ListNode head) { 
        ListNode[] A = new ListNode[100]; 
        int t = 0; 
        while (head != null) { 
            A[t++] = head; 
            head = head.next; 
        } 
        return A[t / 2]; 
    } 
}
```
Complexity Analysis
- Time Complexity: O(N), where N is the number of nodes in the given list.
- Space Complexity: O(N), the space used by A.
---
Solution 2: Fast and Slow pointer (10 min)
```
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
    public ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while(fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
}

Time Complexity: O(n)    
Space Complexity: O(1)
```

Refer to
https://leetcode.com/problems/middle-of-the-linked-list/solutions/154715/middle-of-the-linked-list/

Approach 2: Fast and Slow Pointer

Intuition and Algorithm
When traversing the list with a pointer slow, make another pointer fast that traverses twice as fast. When fast reaches the end of the list, slow must be in the middle.
```
class Solution { 
    public ListNode middleNode(ListNode head) { 
        ListNode slow = head, fast = head; 
        while (fast != null && fast.next != null) { 
            slow = slow.next; 
            fast = fast.next.next; 
        } 
        return slow; 
    } 
}
```
Complexity Analysis
- Time Complexity: O(N), where N is the number of nodes in the given list.
- Space Complexity: O(1), the space used by slow and fast.

