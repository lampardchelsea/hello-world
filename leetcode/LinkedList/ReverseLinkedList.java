/**
 * Refer to
 * https://leetcode.com/problems/reverse-linked-list/?tab=Description
 * 
 * Refer to
 * https://leetcode.com/articles/reverse-linked-list/
 * http://algorithms.tutorialhorizon.com/reverse-a-linked-list/
 * http://algorithms.tutorialhorizon.com/reverse-a-linked-list-part-2/
 * http://stackoverflow.com/questions/354875/reversing-a-linked-list-in-java-recursively
 */
public class ReverseLinkedList {
	private class ListNode {
	    int val;
	    ListNode next;
	    ListNode(int x) { 
	    	val = x;
	    }
	}
	
	/**
	 * Solution 1: Iterative
	 * Assume that we have linked list 1 → 2 → 3 → Ø, we would like to change it to Ø ← 1 ← 2 ← 3.
	 * While you are traversing the list, change the current node's next pointer to point to 
	 * its previous element. Since a node does not have reference to its previous node, you must 
	 * store its previous element beforehand. You also need another pointer to store the next 
	 * node before changing the reference. Do not forget to return the new head reference at the end!
	 */
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while(curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            // Important: Should not reverse the setting order here,
            // first assign 'curr' to 'prev'
            //curr = nextTemp;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }
    
    /**
     * Solution 2: Recursive
     * The recursive version is slightly trickier and the key is to work backwards. 
     * Assume that the rest of the list had already been reversed, now how do I reverse 
     * the front part? Let's assume the list is: n1 → … → nk-1 → nk → nk+1 → … → nm → Ø(null)
     * Assume from node nk+1 to nm had been reversed and you are at node nk.
     * n1 → … → nk-1 → nk → nk+1 ← … ← nm
     * We want nk+1’s next node to point to nk.
     * So, nk.next.next = nk;
     * Be very careful that n1's next must point to Ø(null). If you forget about this, your 
     * linked list has a cycle in it. This bug could be caught if you test your code 
     * with a linked list of size 2, e.g [1, 2], [1, 2, 3]...
     */
    public ListNode reverseList2(ListNode head) {
    	// Reverse of null (the empty list)
    	if(head == null) {
    		return null;
    	}
    	// Reverse of a one element list
    	if(head.next == null) {
    		return head;
    	}
    	ListNode p = reverseList2(head.next);
    	head.next.next = head;
    	// Bug fix - need to unlink head from the rest or you will get a cycle,
    	// (head.next = null) breaks the connection between the element and its 
    	// subsequent element, without it you will later have a cycle between 
    	// this element and the subsequent element
    	/**
    	 * Also refer to
    	 * http://www.java2blog.com/2014/07/how-to-reverse-linked-list-in-java.html
    	 * Now lets understand logic for above recursive program.
    	 * E.g 5->6->7->1->2
    	 * Above function will terminate when last node(2) 's next will be null. so while returning when 
    	 * you reach at node with value 1,If you closely observe node.next.next=node is actually setting 2->1
    	 * (i.e. reversing the link between node with value 1 and 2) and node.next=null is removing link 1->2. 
    	 * So in each iteration, you are reversing link between two nodes.
    	 */
    	head.next = null;
    	return p;
    }
    
    /**
     * Solution 3: Iterative with dummy node
     * Refer to
     * https://leetcode.com/problems/reverse-linked-list-ii/?tab=Description
     * This solution compare to Solution 1 is more generic, for detail check on
     * http://www.cnblogs.com/springfor/p/3864303.html
     * https://discuss.leetcode.com/topic/8976/simple-java-solution-with-clear-explanation/2
     */
    public ListNode reverseList3(ListNode head) {
        if(head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode pre = dummy;
        ListNode start = pre.next;
        ListNode then = start.next;
        while(start.next != null) {
            start.next = then.next;
            then.next = pre.next;
            pre.next = then;
            then = start.next;
        }
        return dummy.next;
    }
    
    public static void main(String[] args) {
    	ReverseLinkedList r = new ReverseLinkedList();
    	ListNode one = r.new ListNode(1);
    	ListNode two = r.new ListNode(2);
    	ListNode three = r.new ListNode(3);
    	one.next = two;
    	two.next = three;
    	ListNode result = r.reverseList2(one);
    	System.out.println(result.val);
    }
    
}















































https://leetcode.com/problems/reverse-linked-list/

Given the head of a singly linked list, reverse the list, and return the reversed list.

Example 1:


```
Input: head = [1,2,3,4,5]
Output: [5,4,3,2,1]
```

Example 2:


```
Input: head = [1,2]
Output: [2,1]
```

Example 3:
```
Input: head = []
Output: []
```
 
Constraints:
- The number of nodes in the list is the range [0, 5000].
- -5000 <= Node.val <= 5000
 
Follow up: A linked list can be reversed either iteratively or recursively. Could you implement both?
---
Attempt 1: 2023-02-12

Solution 1:  Iterative Solution (10 min)
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
    public ListNode reverseList(ListNode head) { 
        if(head == null || head.next == null) { 
            return head; 
        } 
        ListNode prev = null; 
        ListNode cur = head; 
        while(cur != null) { 
            ListNode next = cur.next; 
            cur.next = prev; 
            prev = cur; 
            cur = next;             
        } 
        return prev; 
    } 
}
```

Solution 2:  Recursive Solution (10 min)
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
    public ListNode reverseList(ListNode head) { 
        if(head == null || head.next == null) { 
            return head; 
        } 
        return helper(head, null); 
    }

    private ListNode helper(ListNode cur, ListNode prev) { 
        if(cur == null) { 
            return prev; 
        } 
        ListNode next = cur.next; 
        cur.next = prev; 
        return helper(next, cur); 
    } 
}
```

Refer to
https://leetcode.com/problems/reverse-linked-list/solutions/803955/c-iterative-vs-recursive-solutions-compared-and-explained-99-time-85-space/
Not sure how this problem is expecting me to use less memory than this, but here is the deal:
- we are going to use 3 variables: prevNode, head and nextNode, that you can easily guess what are meant to represent as we go;
- we will initialise prevNode to NULL, while nextNode can stay empty;
- we are then going to loop until our current main iterator (head) is truthy (ie: not NULL), which would imply we reached the end of the list;
- during the iteration, we first of all update nextNode so that it acquires its namesake value, the one of the next node indeed: head->next;
- we then proceeding "reversing" head->next and assigning it the value of prevNode, while prevNode will become take the current value of head;
- finally, we update head with the value we stored in nextNode and go on with the loop until we can. After the loop, we return prevNode.

I know it is complex, but I find this gif from another platform to make the whole logic much easier to understand (bear in mind we do not need curr and will just use head in its place):


The code:
```
class Solution { 
public: 
    ListNode* reverseList(ListNode* head) { 
        ListNode *nextNode, *prevNode = NULL; 
        while (head) { 
            nextNode = head->next; 
            head->next = prevNode; 
            prevNode = head; 
            head = nextNode; 
        } 
        return prevNode; 
    } 
};
```

Relatively trivial refactor (the function does basically the same) with recursion and comma operator to make it one-line:
```
class Solution { 
public: 
    ListNode* reverseList(ListNode *head, ListNode *nextNode = NULL, ListNode *prevNode = NULL) { 
        return head ? reverseList(head->next, (head->next = prevNode, nextNode), head) : prevNode; 
    } 
};
```

Refer to
https://leetcode.com/problems/reverse-linked-list/solutions/58125/in-place-iterative-and-recursive-java-solution/
We always put a node's previous node as one's next
```
Take 1 -> 2 -> 3 -> N for example, we reverse the list by
put 1's previous node null as 1's next,
put 2's previous node 1 as 2's next,
put 3's previous node 2 as 3's next,
return 3 // put null's previous node 3 as null's next
```
The code is as follows:
```
public ListNode reverseList(ListNode head) { 
    /* iterative solution */ 
    ListNode newHead = null; 
    while (head != null) { 
        ListNode next = head.next; 
        head.next = newHead; 
        newHead = head; 
        head = next; 
    } 
    return newHead; 
}

public ListNode reverseList(ListNode head) { 
    /* recursive solution */ 
    return reverseListInt(head, null); 
}

private ListNode reverseListInt(ListNode head, ListNode newHead) { 
    if (head == null) 
        return newHead; 
    ListNode next = head.next; 
    head.next = newHead; 
    return reverseListInt(next, head); 
}
```
