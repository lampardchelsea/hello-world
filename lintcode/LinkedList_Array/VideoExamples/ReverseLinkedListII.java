/** 
 * Refer to
 * http://www.lintcode.com/en/problem/reverse-linked-list-ii/
 * Reverse a linked list from position m to n.
   Notice
  Given m, n satisfy the following condition: 1 ≤ m ≤ n ≤ length of list.

  Have you met this question in a real interview? Yes
  Example
  Given 1->2->3->4->5->NULL, m = 2 and n = 4, return 1->4->3->2->5->NULL.
 *
 * Solution
 * http://www.jiuzhang.com/solutions/reverse-linked-list-ii/
 * https://github.com/lampardchelsea/hello-world/blob/108b14f402d6d323029bcec9db8ee67cc755b23b/leetcode/LinkedList/ReverseLinkedListII.java
*/

/**
 * Definition for ListNode
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */


public class Solution {
    /*
     * @param head: ListNode head is the head of the linked list 
     * @param m: An integer
     * @param n: An integer
     * @return: The head of the reversed ListNode
     */
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if(head == null) {
            return null;
        }
        // To reconsturct the list need create dummy node
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        // Because dummy relate to final return(dummy.next),
        // need to create new reference point to this dummy node
        ListNode itr = dummy;
        // Start from 1 because 'm' is not indexed value, it represents
        // which node in list, so start from 1
        for(int i = 1; i < m; i++) {
            itr = itr.next;
        }
        
        // Below process exactly similar thing as
        // https://github.com/lampardchelsea/hello-world/blob/108b14f402d6d323029bcec9db8ee67cc755b23b/leetcode/LinkedList/ReverseLinkedListII.java
        
        // Mark node before position 'm' as 'prevmNode',
        // used for looply reverse
        ListNode prevmNode = itr;
        // A pointer to the beginning of a sub-list that will be reversed
        ListNode mNode = itr.next;
        // Additional to the original Reverse Linked List, we also have
        // to consider ListNode 'nNode' and its next node 'postnNode'
        // as only part of list will reversed
        ListNode nNode = mNode;
        ListNode postnNode = mNode.next;
        // Use 'nNode' and 'postnNode' to iterate instead of 'prevmNode' and
        // 'mNode' in while loop, because after iterate still need
        // original 'prevmNode' and 'mNode' reference to concatenate
        for(int i = m; i < n; i++) {
            ListNode then = postnNode.next;
            postnNode.next = nNode;
            nNode = postnNode;
            postnNode = then;
        }
        // Concatenate two ends with original 'prevmNode' and 'mNode'
        mNode.next = postnNode;
        prevmNode.next = nNode;
        
        return dummy.next;
    }
}


// 更加深入的理解和对比，基于Reverse Nodes In K Group

/**
 * Refer to
 * https://leetcode.com/problems/reverse-linked-list-ii/?tab=Description
 */
public class ReverseLinkedListII {
    private class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
    	    val = x; 
        }
    }

    public ListNode reverseBetween(ListNode head, int m, int n) {
        if(head == null || head.next == null) {
            return head;
        }
        // Create a 'dummy' node to mark the head of this list
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode itr = dummy;
        // Use (m - 1) to guarantee get the 'pre'
        for(int i = 0; i < m - 1; i++) {
        	itr = itr.next;
        }
        // Make a pointer 'pre' as a marker for the node before reversing
        ListNode pre = itr;
        // A pointer to the beginning of a sub-list that will be reversed
        ListNode start = pre.next;
        // A pointer to a node that will be reversed
        ListNode then = start.next;
        // E.g
	    // Given 1 - 2 - 3 - 4 - 5 ; m = 2; n = 4 ---> pre = 1, start = 2, then = 3
	    // dummy -> 1 -> 2 -> 3 -> 4 -> 5
	    // first reversing : dummy -> 1 -> 3 -> 2 -> 4 -> 5; pre = 1, start = 2, then = 4
	    // second reversing: dummy -> 1 -> 4 -> 3 -> 2 -> 5; pre = 1, start = 2, then = 5 (finish)
        for(int i = 0; i < n - m; i++) {
        	start.next = then.next; // Switch 'start' to the later position of 'then'
        	then.next = pre.next; // Switch 'then' to the later position of 'pre'(beginning)
        	pre.next = then; // Set 'then' as new beginning
        	then = start.next; // Put 'then' back to the next of 'start' and prepare for next loop
        }
        return dummy.next;
    }
    
    /**
         之所以尝试方法2是因为九章的讲解，但是不是这道题，是来自Reverse Nodes In K Group那题，两题很相似，
	 所以我想试试能否先找到最后的那个需要reverse的节点，相当于对原链接分3段处理，m node之前的不变顺序，
	 n node 之后的不变顺序，m到n之间的用reverse方法，但是发现有问题，我把两题一并发上来，其中九章那题
	 是我这个思路的来由，有一句话我加了注释： Try to locate the last node need to reverse，九章就是
	 用node k先标记了reverse前的最后一个节点
         
	 关键问题：两种方法中循环部分的区别：
	 在第一种方法的循环里，一共有三个指针：pre, start, then, 
	 其中，start 和 then 两个指针指向的目标是在 m 到 n 这个区间里的。
	 也就是说，每次循环，他都能改变 m 到 n 这个区间里两个节点。
	 虽然 start 这个指针没有动窝，只有最后一次循环之后更新留下的值，在退出循环以后被保留了，
	 其他值都被遗弃了。但是，这种结构，还是能一次更新两个点。
	 所以如果对于给出的实例1->2->3->4->5，m = 2, n = 4，只需要2次循环就能更新2,3,4三个
	 点的关系
	 
	 在第二种方法里，只有 curt 一个指针在 m 到 n 这个区间里。
	 每次循环只能更新一个节点。如果对于给出的实例1->2->3->4->5，m = 2, n = 4，则需要3次
	 循环才能更新2,3,4三个点的关系，如果仅仅使用i < n - m = 4 - 2 = 2, 即和方法一一样
	 之循环2次，则循环完后，刚好还差一步。
	*/
    public ListNode reverseBetween2(ListNode head, int m, int n) {
        if(head == null) {
            return null;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        // Find previous node of m and node m
        ListNode itr = dummy;
        for(int i = 0; i < m - 1; i++) {
            itr = itr.next;
        }
        ListNode prevmNode = itr;
        ListNode mNode = itr.next;
        
        // Find post node of n and node n
		ListNode itr1 = dummy;
		for(int j = 0; j < n; j++) {
			itr1 = itr1.next;
		}
		ListNode nNode = itr1;
		ListNode postnNode = itr1.next;
		
		// Mark with new reference as 'preCurt' and 'curt' used
		// for loop
		ListNode preCurt = prevmNode;
		ListNode curt = mNode;
 		for(int k = 0; k <= n - m; k++) {
			ListNode temp = curt.next;
			curt.next = preCurt;
			preCurt = curt;
			curt = temp;
		}
 		
 		// Concatenate three parts together
		mNode.next = postnNode;
		prevmNode.next = nNode;

        return dummy.next;
    }
    
    
    public static void main(String[] args) {
    	ReverseLinkedListII r = new ReverseLinkedListII();
    	ListNode one = r.new ListNode(1);
    	ListNode two = r.new ListNode(2);
    	ListNode three = r.new ListNode(3);
    	ListNode four = r.new ListNode(4);
    	ListNode five = r.new ListNode(5);
    	one.next = two;
    	two.next = three;
    	three.next = four;
    	four.next = five;
    	ListNode result = r.reverseBetween2(one, 2, 4);
    	System.out.println(result.val);
    }
}










