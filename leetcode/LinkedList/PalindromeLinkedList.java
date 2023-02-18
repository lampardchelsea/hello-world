// Solution 1:
/**
 * Refer to
 * https://leetcode.com/problems/palindrome-linked-list/?tab=Description
 * Given a singly linked list, determine if it is a palindrome.
 * Follow up:
 * Could you do it in O(n) time and O(1) space ?
 * 
 * Refer to
 * https://discuss.leetcode.com/topic/33376/java-easy-to-understand
 * http://www.geeksforgeeks.org/function-to-check-if-a-singly-linked-list-is-palindrome/
 * This method takes O(n) time and O(1) extra space.
 * 1) Get the middle of the linked list.
 * 2) Reverse the second half of the linked list.
 * 3) Check if the first half and second half are identical.
 * 4) Construct the original linked list by reversing the second half again and attaching it back to the first half
 * 
 * To divide the list in two halves, method 2 of this post is used.
 * When number of nodes are even, the first and second half contain exactly half nodes. 
 * The challenging thing in this method is to handle the case when number of nodes are odd. 
 * We don’t want the middle node as part of any of the lists as we are going to compare them 
 * for equality. For odd case, we use a separate variable ‘midnode’.
 */
public class PalindromeLinkedList {
    private class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
    	    val = x; 
        }
    }
    public boolean isPalindrome(ListNode head) {
        ListNode secondHalfStart = findSecondHalfStart(head);
        ListNode temp = reverseSecondHalf(secondHalfStart);
        boolean result = checkIdentical(head, temp);
        return result;
    }

    public ListNode findSecondHalfStart(ListNode head) {
        if(head == null) {
            return null;
        }
        int len = 1;
        ListNode itr = head;
        while(itr.next != null) {
            len++;
            itr = itr.next;
        }
        ListNode preMidNode;
        ListNode midNode;
        ListNode secondHalfStart;
        // Length equal to 1 is a special case, use [1, 2] to detect
        // just need to return head itself as itr.next is null
        if(len == 1) {
            return head;
        } else if(len % 2 == 1) {
            itr = head;
            int x = len/2;
            // Modify x > 0 to x > 1 in case of looking for preMidNode
            // of midNode and then cut off connection between
            // preMidNode --> midNode in odd item case 
            while(x > 1) {
            	itr = itr.next;
            	x--;
            }
            // Store itr as preMidNode 
            preMidNode = itr;
            // Store midNode in odd item case
            midNode = itr.next;
            // Cut off preMidNode --> midNode 
            preMidNode.next = null;
            // Store midNode.next as secondHalfStart
            secondHalfStart = midNode.next;
            // Cut off midNode --> secondHalfStart
            midNode.next = null;
            return secondHalfStart;
        } else {
            itr = head;
            int x = len/2;
            while(x > 1) {
            	itr = itr.next;
            	x--;
            }
            preMidNode = itr;
            midNode = itr.next;
            // Cut off preMidNode --> secondHalfStart(midNode)
            //preMidNode.next = null;
            secondHalfStart = midNode;
            return secondHalfStart;
        }
    }
    
    public ListNode reverseSecondHalf(ListNode head) {
        if(head == null) {
            return null;
        }
        if(head.next == null) {
            return head;
        }
        ListNode p = reverseSecondHalf(head.next);
        head.next.next = head;
        head.next = null;
        return p;
    }
    
    public boolean checkIdentical(ListNode x, ListNode y) {
        if(x == null && y == null) {
            return true;
        }
        if(x == null || y == null) {
            return false;
        }
        ListNode itr1 = x;
        ListNode itr2 = y;
        // Need to start compare including current head on
        // both half, if not include itr1 and itr2 by skipping
        // as itr1.next != null && itr2.next != null will miss
        // they are not equal case, e.g [1, 2]
        while(itr1 != null && itr2 != null) {
            if(itr1.val != itr2.val) {
                return false;
            }
            itr1 = itr1.next;
            itr2 = itr2.next;
        }
        return true;
    }
	
	public static void main(String[] args) {
		PalindromeLinkedList p = new PalindromeLinkedList();
		ListNode one = p.new ListNode(1);
		ListNode two = p.new ListNode(2);
//		ListNode three = p.new ListNode(3);
//		ListNode four = p.new ListNode(4);
		ListNode three = p.new ListNode(2);
		ListNode four = p.new ListNode(1);
		one.next = two;
		two.next = three;
		three.next = four;
		boolean result = p.isPalindrome(one);
		System.out.println(result);
	}
}




// Improvement Solution 1:
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public boolean isPalindrome(ListNode head) {
        ListNode secondHalfStart = findSecondHalfStart(head);
        ListNode temp = reverseSecondHalf(secondHalfStart);
        return checkIdentical(head, temp);
    }

    public ListNode findSecondHalfStart(ListNode head) {
        if(head == null) {
            return null;
        }
        int len = 1;
        ListNode itr = head;
        while(itr.next != null) {
            len++;
            itr = itr.next;
        }
        // Improvement 1: No need to cut off relationship as
        // preMidNode --> midNode --> secondHalfStart, because
        // in checkIdentical() method we will only loop same
        // length on both section (itr1 != null && itr2 != null)
        // even we don't cut off relation on original list, we
        // will not loop additional length
        if(len == 1) {
            return head;
        } else if(len % 2 == 1) {
            itr = head;
            int x = len/2;
            while(x > 0) {
            	itr = itr.next;
            	x--;
            }
            return itr.next;
        } else {
            itr = head;
            int x = len/2;
            while(x > 0) {
            	itr = itr.next;
            	x--;
            }
            return itr;
        }
    }
    
    // Improvement 2: Use iterative way to save more time
    public ListNode reverseSecondHalf(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while(curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }
    
    public boolean checkIdentical(ListNode x, ListNode y) {
        if(x == null && y == null) {
            return true;
        }
        if(x == null || y == null) {
            return false;
        }
        ListNode itr1 = x;
        ListNode itr2 = y;
        while(itr1 != null && itr2 != null) {
            if(itr1.val != itr2.val) {
                return false;
            }
            itr1 = itr1.next;
            itr2 = itr2.next;
        }
        return true;
    }
}

// Re-work
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/LinkedList/ReverseLinkedList.java
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
    public boolean isPalindrome(ListNode head) {
        ListNode iter = head;
        int count = 0;
        while(iter != null) {
            iter = iter.next;
            count++;
        }
        int half_count = 0;
        ListNode prev = new ListNode(0);
        prev.next = head;
        ListNode curr = prev;
        if(count % 2 == 0) {
            half_count = count / 2;
            while(half_count > 0) {
                curr = curr.next;
                half_count--;
            } 
        } else {
            half_count = (count - 1) / 2;
            half_count += 1;
            while(half_count > 0) {
                curr = curr.next;
                half_count--;
            }
        }
        ListNode prev_second_half = curr;
        // Very important !!! 
        // Store the second half start node and cut off previous node with it,
        // then original list split into 2 sublists, then reverse the 2nd sublist
        ListNode second_half_start = curr.next;
        curr.next = null;
        while(second_half_start != null) {
            ListNode nextTemp = second_half_start.next;
            second_half_start.next = prev_second_half;
            prev_second_half = second_half_start;
            second_half_start = nextTemp;
        }
        while(head != null && prev_second_half != null) {
            if(head.val != prev_second_half.val) {
                return false;
            }
            head = head.next;
            prev_second_half = prev_second_half.next;
        }
        return true;
    }
}

// Solution 2: Two pointers
// Refer to
// https://leetcode.com/problems/palindrome-linked-list/discuss/64501/Java-easy-to-understand
/**
This can be solved by reversing the 2nd half and compare the two halves. Let's start with an example [1, 1, 2, 1].

In the beginning, set two pointers fast and slow starting at the head.

1 -> 1 -> 2 -> 1 -> null 
sf
(1) Move: fast pointer goes to the end, and slow goes to the middle.

1 -> 1 -> 2 -> 1 -> null 
          s          f
(2) Reverse: the right half is reversed, and slow pointer becomes the 2nd head.

1 -> 1    null <- 2 <- 1           
h                      s
(3) Compare: run the two pointers head and slow together and compare.

1 -> 1    null <- 2 <- 1             
     h            s
     
==============================================================================================
odd number of nodes
  1 -> 2 -> 3 -> 4 -> 5 -> null
fast
           fast
                     fast

slow 
      slow
           slow
           ---> slow = slow.next

even number of nodes
  1 -> 2 -> 3 -> 4 -> null
fast
           fast
                      fast

slow
      slow
           slow
*/
class Solution {
    public boolean isPalindrome(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while(fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        if(fast != null) {
            slow = slow.next;
        }
        slow = reverse(slow);
        fast = head;
        while(slow != null) {
            if(slow.val != fast.val) {
                return false;
            }
            slow = slow.next;
            fast = fast.next;
        }
        return true;
    }
    
    private ListNode reverse(ListNode head) {
        ListNode prev = null;
        while(head != null) {
            ListNode nextTemp = head.next;
            head.next = prev;
            prev = head;
            head = nextTemp;
        }
        return prev;
    } 
}








































https://leetcode.com/problems/palindrome-linked-list/

Given the head of a singly linked list, return true if it is a palindrome or falseotherwise.

Example 1:


```
Input: head = [1,2,2,1]
Output: true
```

Example 2:


```
Input: head = [1,2]
Output: false
```

Constraints:
- The number of nodes in the list is in the range [1, 105].
- 0 <= Node.val <= 9
 
Follow up: Could you do it inO(n)time andO(1)space?
---
Attempt 1: 2023-02-16

Solution 1: Clone list and reverse list then compare (30 min)

Why we have to clone original list ? 
If only reverse the original list, the mutation will happen on original list, the 'head' of original list will point to NULL after reverse as a 'tail', so before reversing original list, we have to clone it to reserve the original list for later 'reverse'
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
    public boolean isPalindrome(ListNode head) { 
        if(head == null || head.next == null) { 
            return true; 
        } 
        ListNode clone = cloneList(head); 
        ListNode rev = reverseList(head); 
        ListNode iter1 = clone; 
        ListNode iter2 = rev; 
        while(iter1 != null) { 
            if(iter1.val != iter2.val) { 
                return false; 
            } 
            iter1 = iter1.next; 
            iter2 = iter2.next; 
        } 
        return true; 
    }

    // Why we have to clone original list? 
    // If only reverse the original list, the mutation will happen on  
    // original list, the 'head' of original list will point to NULL  
    // after reverse as a 'tail', so before reversing original list,  
    // we have to clone it to reserve the original list for later 'reverse' 
     
    // Function takes a linked list and returns its complete copy 
    private ListNode cloneList(ListNode head) { 
        // 'iter' used to iterate over the original list 
        ListNode iter = head; 
        // 'newHead' as head of the new list 
        ListNode newHead = null; 
        // 'tail' point to the last node in a new list 
        ListNode tail = null; 
        while(iter != null) { 
            // Special case for the first node of new list 
            if(newHead == null) { 
                newHead = new ListNode(iter.val); 
                tail = newHead; 
            } else { 
                tail.next = new ListNode(iter.val); 
                tail = tail.next; 
                // Don't forget to remove old connection after 'tail' updated 
                tail.next = null; 
            } 
            iter = iter.next; 
        } 
        return newHead; 
    }

    private ListNode reverseList(ListNode head) { 
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

Time Complexity: O(n), where n is the number of nodes in the Linked List 
Space Complexity: O(n), where n is the number of nodes in the Linked List
```

How to clone a linked list ?
Refer to
https://www.techiedelight.com/clone-given-linked-list/
The idea is to iterate over the original list in the usual way and maintain two pointers to keep track of the new list: one head pointer and one tail pointer, which always points to the last node of the new list. The first node is done as a special case, and then the tail pointer is used in the standard way for the others.
```
// A Linked List Node 
class Node 
{ 
    int data; 
    Node next; 
    Node(int data, Node next) 
    { 
        this.data = data; 
        this.next = next; 
    } 
    Node() {} 
} 
class Main 
{ 
    // Helper function to print a given linked list 
    public static void printList(Node head) 
    { 
        Node ptr = head; 
        while (ptr != null) 
        { 
            System.out.print(ptr.data + " —> "); 
            ptr = ptr.next; 
        } 
        System.out.println("null"); 
    } 
    // Function takes a linked list and returns its complete copy 
    public static Node copyList(Node head) 
    { 
        Node current = head;    // used to iterate over the original list 
        Node newList = null;    // head of the new list 
        Node tail = null;       // point to the last node in a new list 
        while (current != null) 
        { 
            // special case for the first new node 
            if (newList == null) 
            { 
                newList = new Node(current.data, null); 
                tail = newList; 
            } 
            else { 
                tail.next = new Node(); 
                tail = tail.next; 
                tail.data = current.data; 
                tail.next = null; 
            } 
            current = current.next; 
        } 
        return newList; 
    } 
    public static void main(String[] args) 
    { 
        // input keys 
        int[] keys = {1, 2, 3, 4}; 
        // points to the head node of the linked list 
        Node head = null; 
        // construct a linked list 
        for (int i = keys.length - 1; i >= 0; i--) { 
            head = new Node(keys[i], head); 
        } 
        // copy linked list 
        Node copy = copyList(head); 
        // print duplicate linked list 
        printList(copy); 
    } 
}
```

---
Solution 2: Recursion and naturally get tail node recursively for comparison (30 min)
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
    ListNode left; 
    public boolean isPalindrome(ListNode head) { 
        left = head; 
        return helper(head); 
    }

    private boolean helper(ListNode right) { 
        // Return true if you've reached the end of the list 
        if(right == null) { 
            return true; 
        } 
        // Keep pointing towards right and call function recursively 
        // so that, when each recursive call is popped from the stack, 
        // the nodes on the right will be compared in reverse order 
        boolean result = helper(right.next); 
        // If sub-list is not a palindrome then return false 
        if(!result) { 
            return false; 
        } 
        // Compare current left and right node value 
        if(left.val != right.val) { 
            return false; 
        } 
        // Move left node to next node, prepare for next recursion comparison 
        left = left.next; 
        return true; 
    } 
}

Time Complexity: O(n), where n is the number of nodes in the Linked List
The recursive function is run once for each of the nnn nodes, and the body of the recursive function is O(1). Therefore, this gives a total of O(n).
Space Complexity: O(n), where n is the number of nodes in the Linked List

I hinted at the start that this is not using O(1) space. This might seem strange, after all we aren't creating any new data structures. So, where is the O(n) extra memory we're using? Understanding what is happening here requires understanding how the computer runs a recursive function. 
Each time a function is called within a function, the computer needs to keep track of where it is up to (and the values of any local variables) in the current function before it goes into the called function. It does this by putting an entry on something called the runtime stack, called a stack frame. Once it has created a stack frame for the current function, it can then go into the called function. Then once it is finished with the called function, it pops the top stack frame to resume the function it had been in before the function call was made. 
Before doing any palindrome checking, the above recursive function creates n of these stack frames because the first step of processing a node is to process the nodes after it, which is done with a recursive call. Then once it has the nnn stack frames, it pops them off one-by-one to process them. 
So, the space usage is on the runtime stack because we are creating nnn stack frames. Always make sure to consider what's going on the runtime stack when analyzing a recursive function. It's a common mistake to forget to. 
Not only is this approach still using O(n) space, it is worse than the first approach because in many languages (such as Python), stack frames are large, and there's a maximum runtime stack depth of 1000 (you can increase it, but you risk causing memory errors with the underlying interpreter). With every node creating a stack frame, this will greatly limit the maximum Linked List size the algorithm can handle.
```

Refer to
https://leetcode.com/problems/palindrome-linked-list/solutions/346730/java-1-ms-recursive-solution-easy-to-understand/
1. Keep left & right pointer at head.
2. Call the function recursively with 'right.next' until you reach the end of the list.
3. When recursive calls end and are popped of the stack, we compare left and right node values and return the result.
```
/** 
 * Definition for singly-linked list. 
 * public class ListNode { 
 *     int val; 
 *     ListNode next; 
 *     ListNode(int x) { val = x; } 
 * } 
 */ 
class Solution { 
    ListNode left; 
     
    public boolean isPalindrome(ListNode head) { 
        left = head; 
        return isListPalindrome(head); 
    } 
     
    boolean isListPalindrome(ListNode right) { 
         
        // return true if you've reached the end of the list 
        if (right == null) { 
            return true; 
        } 
         
        // keep pointing towards right and call function recursively 
        // so that, when each recursive call is popped from the stack, 
        // the nodes on the right will be compared in reverse order 
        boolean isPalin = isListPalindrome(right.next); 
         
        // if sub-list is not a palindrome then return false 
        if (!isPalin) 
            return false; 
         
        // compare current left and right node value 
        boolean isEqual = left.val == right.val; 
        left = left.next; 
         
        return isEqual; 
    } 
}
```

Refer to
https://leetcode.com/problems/palindrome-linked-list/solutions/433547/palindrome-linked-list/

Approach 2: Recursive (Advanced)

Intuition
In an attempt to come up with a way of using O(1) space, you might have thought of using recursion. However, this is still O(n)space. Let's have a look at it and understand why it is not O(1) space.

Recursion gives us an elegant way to iterate through the nodes in reverse. For example, this algorithm will print out the values of the nodes in reverse. Given a node, the algorithm checks if it is null. If it is null, nothing happens. Otherwise, all nodes after it are processed, and then the value for the current node is printed.
```
function print_values_in_reverse(ListNode head) 
    if head is NOT null 
        print_values_in_reverse(head.next) 
        print head.val
```
If we iterate the nodes in reverse using recursion, and iterate forward at the same time using a variable outside the recursive function, then we can check whether or not we have a palindrome.

Algorithm
When given the head node (or any other node), referred to as currentNode, the algorithm firstly checks the rest of the Linked List. If it discovers that further down that the Linked List is not a palindrome, then it returns false. Otherwise, it checks that currentNode.val == frontPointer.val. If not, then it returns false. Otherwise, it moves frontPointer forward by 1 node and returns true to say that from this point forward, the Linked List is a valid palindrome.

It might initially seem surprisingly that frontPointer is always pointing where we want it. The reason it works is because the order in which nodes are processed by the recursion is in reverse (remember our "printing" algorithm above). Each node compares itself against frontPointer and then moves frontPointer down by 1, ready for the next node in the recursion. In essence, we are iterating both backwards and forwards at the same time.

Here is an animation that shows how the algorithm works. The nodes have each been given a unique identifier (e.g. $1 and $4) so that they can more easily be referred to in the explanations. The computer needs to use its runtime stack for recursive functions.

Step by step simulation recursion stack

Step 1



Step 2


Step 3

Step 4

Step 5

Step 6

Step 7

Step 8

Step 9

Step 10

Step 11

Step 12


Step 13

Step 14

Step 15

Step 16

Step 17

Step 18

Step 19

Step 20

Step 21

Step 22

Step 23

Step 24

Step 25

Step 26

Step 27

Step 28

Step 29

Step 30

Step 31

Step 32

Step 33

Step 34


```
class Solution { 
    private ListNode frontPointer; 
    private boolean recursivelyCheck(ListNode currentNode) { 
        if (currentNode != null) { 
            if (!recursivelyCheck(currentNode.next)) return false; 
            if (currentNode.val != frontPointer.val) return false; 
            frontPointer = frontPointer.next; 
        } 
        return true; 
    } 
    public boolean isPalindrome(ListNode head) { 
        frontPointer = head; 
        return recursivelyCheck(head); 
    } 
}
```
Complexity Analysis
- Time complexity : O(n) , where n is the number of nodes in the Linked List.
  The recursive function is run once for each of the n nodes, and the body of the recursive function is O(1). Therefore, this gives a total of O(n).
- Space complexity : O(n), where n is the number of nodes in the Linked List.
  I hinted at the start that this is not using O(1) space. This might seem strange, after all we aren't creating any new data structures. So, where is the O(n) extra memory we're using? Understanding what is happening here requires understanding how the computer runs a recursive function.

Each time a function is called within a function, the computer needs to keep track of where it is up to (and the values of any local variables) in the current function before it goes into the called function. It does this by putting an entry on something called the runtime stack, called a stack frame. Once it has created a stack frame for the current function, it can then go into the called function. Then once it is finished with the called function, it pops the top stack frame to resume the function it had been in before the function call was made.

Before doing any palindrome checking, the above recursive function creates n of these stack frames because the first step of processing a node is to process the nodes after it, which is done with a recursive call. Then once it has the n stack frames, it pops them off one-by-one to process them.

So, the space usage is on the runtime stack because we are creating n stack frames. Always make sure to consider what's going on the runtime stack when analyzing a recursive function. It's a common mistake to forget to.

Not only is this approach still using O(n) space, it is worse than the first approach because in many languages (such as Python), stack frames are large, and there's a maximum runtime stack depth of 1000 (you can increase it, but you risk causing memory errors with the underlying interpreter). With every node creating a stack frame, this will greatly limit the maximum Linked List size the algorithm can handle.

---
Solution 3: Reverse second half in-place (30 min)
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
    public boolean isPalindrome(ListNode head) { 
        ListNode secondHalfHead = findSecondHalfHead(head); 
        ListNode revSecondHalfHead = reverse(secondHalfHead); 
        ListNode iter1 = head; 
        ListNode iter2 = revSecondHalfHead; 
        while(iter1 != null && iter2 != null) { 
            if(iter1.val != iter2.val) { 
                return false; 
            } 
            iter1 = iter1.next; 
            iter2 = iter2.next; 
        } 
        return true; 
    }

    private ListNode findSecondHalfHead(ListNode head) { 
        ListNode slow = head; 
        ListNode fast = head; 
        while(fast.next != null && fast.next.next != null) { 
            slow = slow.next; 
            fast = fast.next.next; 
        } 
        return slow.next; 
    }

    private ListNode reverse(ListNode secondHalfHead) { 
        ListNode prev = null; 
        ListNode cur = secondHalfHead; 
        while(cur != null) { 
            ListNode next = cur.next; 
            cur.next = prev; 
            prev = cur; 
            cur = next; 
        } 
        return prev; 
    } 
}

Time complexity : O(n), where n is the number of nodes in the Linked List. 
Similar to the above approaches. Finding the middle is O(n), reversing a list in place is O(n), and then comparing the 2 resulting Linked Lists is also O(n). 
Space complexity : O(1)
We are changing the next pointers for half of the nodes. This was all memory that had already been allocated, so we are not using any extra memory and therefore it is O(1).
```

Refer to
https://leetcode.com/problems/palindrome-linked-list/solutions/433547/palindrome-linked-list/

Approach 3: Reverse Second Half In-place

Intuition
The only way we can avoid using O(n) extra space is by modifying the input in-place.
The strategy we can use is to reverse the second half of the Linked List in-place (modifying the Linked List structure), and then comparing it with the first half. Afterwards, we should re-reverse the second half and put the list back together. While you don't need to restore the list to pass the test cases, it is still good programming practice because the function could be a part of a bigger program that doesn't want the Linked List broken.

Algorithm
Specifically, the steps we need to do are:
1. Find the end of the first half.
2. Reverse the second half.
3. Determine whether or not there is a palindrome.
4. Restore the list.
5. Return the result.
To do step 1, we could count the number of nodes, calculate how many nodes are in the first half, and then iterate back down the list to find the end of the first half. Or, we could do it in a single parse using the two runners pointer technique. Either is acceptable, however we'll have a look at the two runners pointer technique here.

Imagine we have 2 runners one fast and one slow, running down the nodes of the Linked List. In each second, the fast runner moves down 2 nodes, and the slow runner just 1 node. By the time the fast runner gets to the end of the list, the slow runner will be half way. By representing the runners as pointers, and moving them down the list at the corresponding speeds, we can use this trick to find the middle of the list, and then split the list into two halves.

If there is an odd-number of nodes, then the "middle" node should remain attached to the first half.

Step 2 uses the algorithm that can be found in the solution article for the Reverse Linked List problem to reverse the second half of the list.

Step 3 is fairly straightforward. Remember that we have the first half, which might also contain a "middle" node at the end, and the second half, which is reversed. We can step down the lists simultaneously ensuring the node values are equal. When the node we're up to in the second list is null, we know we're done. If there was a middle value attached to the end of the first list, it is correctly ignored by the algorithm. The result should be saved, but not returned, as we still need to restore the list.

Step 4 requires using the same function you used for step 2, and then for step 5the saved result should be returned.

```
class Solution { 
    public boolean isPalindrome(ListNode head) { 
        if (head == null) return true; 
        // Find the end of first half and reverse second half. 
        ListNode firstHalfEnd = endOfFirstHalf(head); 
        ListNode secondHalfStart = reverseList(firstHalfEnd.next); 
        // Check whether or not there is a palindrome. 
        ListNode p1 = head; 
        ListNode p2 = secondHalfStart; 
        boolean result = true; 
        while (result && p2 != null) { 
            if (p1.val != p2.val) result = false; 
            p1 = p1.next; 
            p2 = p2.next; 
        }         
        // Restore the list and return the result. 
        firstHalfEnd.next = reverseList(secondHalfStart); 
        return result; 
    } 
    // Taken from https://leetcode.com/problems/reverse-linked-list/solution/ 
    private ListNode reverseList(ListNode head) { 
        ListNode prev = null; 
        ListNode curr = head; 
        while (curr != null) { 
            ListNode nextTemp = curr.next; 
            curr.next = prev; 
            prev = curr; 
            curr = nextTemp; 
        } 
        return prev; 
    } 
    private ListNode endOfFirstHalf(ListNode head) { 
        ListNode fast = head; 
        ListNode slow = head; 
        while (fast.next != null && fast.next.next != null) { 
            fast = fast.next.next; 
            slow = slow.next; 
        } 
        return slow; 
    } 
}
```
Complexity Analysis
- Time complexity : O(n), where n is the number of nodes in the Linked List.
  Similar to the above approaches. Finding the middle is O(n), reversing a list in place is O(n), and then comparing the 2 resulting Linked Lists is also O(n).
- Space complexity : O(1).
  We are changing the next pointers for half of the nodes. This was all memory that had already been allocated, so we are not using any extra memory and therefore it is O(1).

I have seen some people on the discussion forum saying it has to be O(n) because we're creating a new list. This is incorrect, because we are changing each of the pointers one-by-one, in-place. We are not needing to allocate more than O(1) extra memory to do this work, and there is O(1) stack frames going on the stack. It is the same as reversing the values in an Array in place (using the two-pointer technique).

The downside of this approach is that in a concurrent environment (multiple threads and processes accessing the same data), access to the Linked List by other threads or processes would have to be locked while this function is running, because the Linked List is temporarily broken. This is a limitation of many in-place algorithms though.
