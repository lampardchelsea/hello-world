/**
 * Refer to
 * https://leetcode.com/problems/reverse-nodes-in-k-group/#/description
 * Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.
 * k is a positive integer and is less than or equal to the length of the linked list. 
 * If the number of nodes is not a multiple of k then left-out nodes in the end should remain as it is.
 * You may not alter the values in the nodes, only nodes itself may be changed.
 * Only constant memory is allowed.
 * For example,
 * Given this linked list: 1->2->3->4->5
 * For k = 2, you should return: 2->1->4->3->5
 * For k = 3, you should return: 3->2->1->4->5
 * 
 * Solution
 * Refer to
 * https://discuss.leetcode.com/topic/12364/non-recursive-java-solution-and-idea/2
 * http://www.cnblogs.com/lichen782/p/leetcode_Reverse_Nodes_in_kGroup.html
 * Reverse a link list between begin and end exclusively
 * an example:
 * a linked list:
 * 0->1->2->3->4->5->6
 * |           |   
 * begin       end
 * after call begin = reverse(begin, end)
 * 
 * 0->3->2->1->4->5->6
 *          |  |
 *      begin end
 * @return the reversed list's 'begin' node, which is the precedence of node end
 */
public class ReverseNodesInKGroup {
    private class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
    	    val = x; 
        }
    }
    
    // Solution 1:
    // Refer to
    // https://discuss.leetcode.com/topic/12364/non-recursive-java-solution-and-idea/2
    // https://github.com/lampardchelsea/hello-world/blob/master/leetcode/LinkedList/ReverseLinkedListII.java
    public ListNode reverseKGroup(ListNode head, int k) {
        if(head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode begin = dummy;
        int i = 0;
        while(head != null) {
        	i++;
        	if(i % k == 0) {
        		begin = reverseBetween(begin, k);
        		head = begin.next;
        	} else {
        		head = head.next;
        	}
        }
        return dummy.next;
    }
    
    // Not like the given example(Solution 2)
    // https://discuss.leetcode.com/topic/12364/non-recursive-java-solution-and-idea/2
    // Modify its 2nd parameter into k, which used for loop control,
    // this change relate loop condition set as > 1, consider the situation
    // we encounter in ReverseLinkedListII
    // https://github.com/lampardchelsea/hello-world/blob/master/leetcode/LinkedList/ReverseLinkedListII.java
    // if we need to reverse 2 to 4(3 nodes), we need loop 4 - 2 = 2 times
    // same here, if k = 3, means reverse 1 to 3(3 nodes), still need loop
    // 2 times to get the same effect, if k = 2, means reverse 1 to 2(2 nodes),
    // need loop 1 time to get the effect, so loop terminate condition set up
    // as 'while(k-- > 1)'. Then we can use same switch way as before, and no
    // need to depends on pass in 'end' node.
    // One more thing is return 'start' node after switch rather than return
    // 'dummy.next' as normal. 
    public ListNode reverseBetween(ListNode begin, int k) {
        // Make a pointer 'pre' as a marker for the node before reversing
        ListNode pre = begin;
        // A pointer to the beginning of a sub-list that will be reversed
        ListNode start = pre.next;
        // A pointer to a node that will be reversed
        ListNode then = start.next;
        // E.g
	    // Given 1 - 2 - 3 - 4 - 5 ; m = 2; n = 4 ---> pre = 1, start = 2, then = 3
	    // dummy -> 1 -> 2 -> 3 -> 4 -> 5
	    // first reversing : dummy -> 1 -> 3 -> 2 -> 4 -> 5; pre = 1, start = 2, then = 4
	    // second reversing: dummy -> 1 -> 4 -> 3 -> 2 -> 5; pre = 1, start = 2, then = 5 (finish)
        while(k-- > 1) {
        	start.next = then.next; // Switch 'start' to the later position of 'then'
        	then.next = pre.next; // Switch 'then' to the later position of 'pre'(beginning)
        	pre.next = then; // Set 'then' as new beginning
        	then = start.next; // Put 'then' back to the next of 'start' and prepare for next loop
        }
        // Instead of return dummy.next, we return 'start' node for next loop,
        // which is the node previous of next section, such as
        // 'dummy' is the node previous of first section
        return start;
    }
    
    // Solution 2:
    // Refer to
    // http://www.cnblogs.com/lichen782/p/leetcode_Reverse_Nodes_in_kGroup.html
    public ListNode reverseKGroup2(ListNode head, int k) {
        if(head == null || k == 1) {
        	return head;
        }
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode begin = dummy;
        int i = 0;
        while(head != null){
            i++;
            if(i % k == 0){
                begin = reverseBetween2(begin, head.next);
                head = begin.next;
            }else {
                head = head.next;
            }
        }
        return dummy.next;
    }
    
    public ListNode reverseBetween2(ListNode begin, ListNode end){
    	// No need on set up a new ListNode 'pre', we can directly
    	// use 'begin' here, as 'begin' is already same thing as
    	// a marker for the node before reversing section
    	// ListNode pre = begin;
        ListNode start = begin.next;
        ListNode then = start.next;
        while(then != end){
            start.next = then.next;
            then.next = begin.next;
            begin.next = then;
            then = start.next;
        }
        return start;
    }
    
    public static void main(String[] args) {
    	ReverseNodesInKGroup r = new ReverseNodesInKGroup();
    	ListNode one = r.new ListNode(1);
    	ListNode two = r.new ListNode(2);
    	ListNode three = r.new ListNode(3);
    	ListNode four = r.new ListNode(4);
    	ListNode five = r.new ListNode(5);
    	one.next = two;
    	two.next = three;
    	three.next = four;
    	four.next = five;
//    	ListNode result = r.reverseKGroup(one, 3);
    	ListNode result = r.reverseKGroup(one, 2);
    	System.out.println(result.val);
    }
}

// Re-work
// Solution 1: Iterative
/**
  Interative solution actually follow as keep inserting at head
  Refer to
  https://leetcode.com/problems/reverse-nodes-in-k-group/discuss/11423/Short-but-recursive-Java-code-with-comments/225224
  https://leetcode.com/problems/reverse-nodes-in-k-group/discuss/11413/Share-my-Java-Solution-with-comments-in-line
  https://leetcode.com/problems/reverse-nodes-in-k-group/discuss/11413/Share-my-Java-Solution-with-comments-in-line/248864
  头插法:不停的把then插到prev后面
  initial: prev -> 1 -> 2 -> 3 -> 4 -> 5
  k = 3
  target:  prev -> 3 -> 2 -> 1 -> 4 -> 5
  ============================================================   
  round1:  prev -> 1 -> 2 -> 3 -> 4 -> 5
                 start
                       then
           ---------------------------------
           Step 1: start.next = then.next => 3
           Step 2: then.next = prev.next => 1
           Step 3: prev.next = then => 2
           Step 4: then = start.next => 3
           ---------------------------------
           prev -> 2 -> 1 -> 3 -> 4 -> 5
             2 inserted as new node but not lose connection 
             since it inherits connection of old 'then' ('then'
             update from 2 to 3) 
                      start
                            then
  ============================================================
  round 2: prev -> 2 -> 1 -> 3 -> 4 -> 5
                      start
                            then
           ---------------------------------
           Step 1: start.next = then.next => 4
           Step 2: then.next = prev.next => 2
           Step 3: prev.next = then => 3
           Step 4: then = start.next => 4
           ---------------------------------
           prev -> 3 -> 2 -> 1 -> 4 -> 5
             3 inserted as new node but not lose connection
             since it inherits connection of old 'then' ('then'
             update from 3 to 4)
                            start
                                 then
  ============================================================
  prev = start;
  Now after round 2 we finish reverse of the only section when k = 3
  and nodes in list as 5, so we update 'prev' from '-1' to '1',
  which actually is 'start' node and prepare for possible existing
  next reverse
*/
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        if(head == null || head.next == null || k == 1) {
            return head;
        }
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode iter = dummy;
        int size = 0;
        while(iter.next != null) {
            size++;
            iter = iter.next;
        }
        int groups = size / k;
        ListNode prev = dummy;
        for(int i = 0; i < groups; i++) {
            ListNode start = prev.next;
            ListNode then = start.next;
            /**
              Why j from 0 to k - 2 (loop k - 1 times)?
              if k = 3, means reverse 1 to 3(3 nodes), still need loop 2 times 
              to get the same effect, if k = 2, means reverse 1 to 2(2 nodes), 
              need loop 1 time to get the effect, so loop terminate condition 
              set up as 'while(k-- > 1)' same as loop k - 1 times
            */
            for(int j = 0; j < k - 1; j++) {
                /**
                  Refer to
                  https://github.com/lampardchelsea/hello-world/blob/master/leetcode/LinkedList/ReverseLinkedListII.java
                  node1.next = node2.next;//node1交换到node2的后面
                  node2.next = startpoint.next;//node2交换到最开始
                  startpoint.next = node2;//node2作为新的点
                  node2 = node1.next;//node2回归到node1的下一个，继续遍历
                */
                start.next = then.next; // switch 'start' and 'then', record next node of 'then', and assign to 'start.next' which will used in re-point later
                then.next = prev.next;  // 2 steps to insert 'then' behind 'prev' as new node
                prev.next = then;
                then = start.next; // re-point 'then' as next node of 'start' for next round
            }
            prev = start;
        }
        return dummy.next;
    }
}

// Solution 2: Recursive
// Refer to
// https://leetcode.com/problems/reverse-nodes-in-k-group/discuss/11423/Short-but-recursive-Java-code-with-comments
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode curr = head;
        int count = 0;
        while(curr != null && count != k) { // find the k+1 node
            curr = curr.next;
            count++;
        }
        if(count == k) { // if k+1 node is found
            curr = reverseKGroup(curr, k); // reverse list with k+1 node as head
            // head - head-pointer to direct part, 
            // curr - head-pointer to reversed part;
            while(count-- > 0) { // reverse current k-group: 
                ListNode tmp = head.next; // tmp - next head in direct part
                head.next = curr; // preappending "direct" head to the reversed list 
                curr = head; // move head of reversed part to a new node
                head = tmp; // move "direct" head to the next node in direct part
            }
            head = curr;
        }
        return head;
    }
}

// https://leetcode.com/problems/reverse-nodes-in-k-group/discuss/11423/Short-but-recursive-Java-code-with-comments/12134
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        // Test weather we have more then k node left, 
        // if less then k node left we just return head 
        ListNode node = head;
        int count = 0;
        while(count < k) {
            if(node == null) {
                return head;
            }
            node = node.next;
            count++;
        }
        // Reverse k node at current level
        // pre node point to the the answer of sub-problem
        ListNode pre = reverseKGroup(node, k);
        while(count > 0) {  
            ListNode next = head.next; 
            head.next = pre; 
            pre = head; 
            head = next;
            count--;
        }
        return pre;
    }
}


















































https://leetcode.com/problems/reverse-nodes-in-k-group/

Given the head of a linked list, reverse the nodes of the list k at a time, and return the modified list.

k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes, in the end, should remain as it is.

You may not alter the values in the list's nodes, only nodes themselves may be changed.

Example 1:


```
Input: head = [1,2,3,4,5], k = 2
Output: [2,1,4,3,5]
```

Example 2:


```
Input: head = [1,2,3,4,5], k = 3
Output: [3,2,1,4,5]
```

Constraints:
- The number of nodes in the list is n.
- 1 <= k <= n <= 5000
- 0 <= Node.val <= 1000

Follow-up: Can you solve the problem in O(1) extra memory space?
---
Attempt 1: 2023-02-12

Solution 1:  Iterative Solution (120 min, must remember the solution)
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
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode iter = dummy;
        while(iter != null) {
            ListNode node = iter;
            // First check whether there are k nodes to reverse in current iteration
            int i = 0;
            while(i < k && node != null) {
                node = node.next;
                i++;
            }
            if(node == null) {
                break;
            }
            // Now we know that we have k nodes, we will start from the first node
            ListNode prev = null;
            ListNode cur = iter.next;
            ListNode next = null;
            int j = 0;
            while(j < k) {
                next = cur.next;
                cur.next = prev;
                prev = cur;
                cur = next;
                j++;
            }
            ListNode tail = iter.next;
            tail.next = cur;
            iter.next = prev;
            iter = tail;
        }
        return dummy.next;
    }
}
```

Refer to
https://leetcode.com/problems/reverse-nodes-in-k-group/solutions/183356/java-o-n-solution-with-super-detailed-explanation-illustration/
This problem can be split into several steps:
1. Since we need to reverse the linked-list every k nodes, we need to check whether the number of list nodes are enough to reverse. Otherwise, there is no need to reverse.
2. If we need to reverse the k nodes, how to do that? Following is my idea:
   If the structure of the linkedlist is like this:
```
1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7
```

Then there will always be a pointer, which points to the node AHEAD of the first node to reverse. The pointer will help to link the linkedlist after.
At first, we will add a dummy node in front of the linked list to act as the first pointer. After we add the pointer, the linked list will look like this:
```
0 (pointer) -> 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7
```

Suppose that there are enough nodes to be reversed, we just use the "reverse linked list" trick to reverse the k nodes. Please refer to "https://leetcode.com/problems/reverse-linked-list/" if you don't know how to reverse a linked list.
if k = 3, we can reverse 1 to 3 first using the following code:
```
  ListNode prev = null, curr = pointer.next, next = null;
  for (int i = 0; i < k; i++) {
		next = curr.next;
		curr.next = prev;
		prev = curr;
		curr = next;
  }
```

This is the illustration of the first 3 steps:
```
step1: 0 (pointer) -> 1      2 -> 3 -> 4 -> 5 -> 6 -> 7 
step2: 0 (pointer) -> 1 <- 2      3 -> 4 -> 5 -> 6 -> 7
step3: 0 (pointer) -> 1 <- 2 <- 3      4 -> 5 -> 6 -> 7
```

This is an easy and general algorithm to reverse a linked list. However, if you are careful enough, you will find that after the for-loop, the link from 3 to 4 will be cut (as shown in step3).
Now we need to reconstruct the linked list and fix the issue. You will figure out that at step3, the 3 is the prev node, 4 is the curr node.
```
step3: 0 (pointer) -> 1 <- 2 <- 3 (prev)    4 (curr) -> 5 -> 6 -> 7
```

We can fix the sequence based on the following codes. The basic idea is to link the pointer to 3 and link 1 to 4:
```
ListNode tail = pointer.next;
tail.next = curr;  
pointer.next = prev; 
pointer = tail;
```

Then the result is:
```
after first line:   0 (pointer) -> 1 (tail) <- 2 <- 3 (prev)    4 (curr) -> 5 -> 6 -> 7
after second line:  0 (pointer) -> 1 (tail) <- 2 <- 3 (prev)    4 (curr) -> 5 -> 6 -> 7
                                   |____________________________↑ 
after third line:   
|-------------------------------↓ 
0 (pointer)    1 (tail) <- 2 <- 3 (prev)    4 (curr) -> 5 -> 6 -> 7
               |____________________________↑ 
									   
after forth line:  0 -> 3 -> 2 -> 1 (pointer) -> 4 -> 5 -> 6 -> 7
```

Now we get the new pointer, and we can repeat the process. Note that to retrieve the head, we need to record the first dummy node (0).
Here is the code:

```
    public ListNode reverseKGroup(ListNode head, int k) { 
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pointer = dummy;
        while (pointer != null) {
            ListNode node = pointer;
            // first check whether there are k nodes to reverse
            for (int i = 0; i < k && node != null; i++) {
                node = node.next;
            }
            if (node == null) break;
            
            // now we know that we have k nodes, we will start from the first node
            ListNode prev = null, curr = pointer.next, next = null;
            for (int i = 0; i < k; i++) {
                next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
            ListNode tail = pointer.next;
            tail.next = curr;
            pointer.next = prev;
            pointer = tail;
        }
        return dummy.next;
    }
```

Solution 2:  Recursive Solution (120 min, must remember the solution)
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
    public ListNode reverseKGroup(ListNode head, int k) {
        // Test weather we have more then k node left, if less then k node left we just return head
        ListNode iter = head;
        int count = 0;
        while(count < k) {
            if(iter == null) {
                return head;
            }
            iter = iter.next;
            count++;
        }
        // Reverse k node at current level
        // 'prev' node point to the answer of sub-problem, represents the new head after reverse on current section
        // e.g 1 -> 2 -> 3 -> 4 -> 5, k=2
        // In 1st recursion, return 'prev'=5, in 2nd recursion, return 'prev'=4,
        // in 3rd recursion, return 'prev'=2
        ListNode prev = reverseKGroup(iter, k);
        while(count > 0) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
            count--;
        }
        return prev;
    }
}
```

Refer to
https://leetcode.com/problems/reverse-nodes-in-k-group/solutions/11423/short-but-recursive-java-code-with-comments/comments/12134
This type of question can split into 2 steps to solve ( like reverse in pair , reverse in 3 node ... )
Here is my solution follow this 2 steps and with little more clear naming conventions.
```
 public ListNode reverseKGroup(ListNode head, int k) {
    //1. test weather we have more then k node left, if less then k node left we just return head 
    ListNode node = head;
    int count = 0;
    while (count < k) { 
        if(node == null)return head;
        node = node.next;
        count++;
    }
    // 2.reverse k node at current level 
       ListNode pre = reverseKGroup(node, k); //pre node point to the the answer of sub-problem 
        while (count > 0) {  
            ListNode next = head.next; 
            head.next = pre; 
            pre = head; 
            head = next;
            count = count - 1;
        }
        return pre;
}
```

