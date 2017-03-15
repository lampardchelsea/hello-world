/**
 * Refer to
 * https://leetcode.com/problems/add-two-numbers-ii/#/description
 * You are given two non-empty linked lists representing two non-negative integers. 
 * The most significant digit comes first and each of their nodes contain a single digit. 
 * Add the two numbers and return it as a linked list.
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 * Follow up:
 * What if you cannot modify the input lists? In other words, reversing the lists is not allowed.
 * Example:
 * Input: (7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 8 -> 0 -> 7
 */
// Solution 1: Reverse list
public class AddTwoNumbersII {
	private class ListNode {
		int val;
		ListNode next;
		ListNode(int x) { 
			val = x; 
		}
	}
	
	// Solution 1: Reverse original list which change format into
	//             AddTwoNumbers.java
	// Refer to
	// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/LinkedList/AddTwoNumbers.java
	// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/LinkedList/ReverseLinkedList.java
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode revL1 = reverseList(l1);
        ListNode revL2 = reverseList(l2);
        ListNode dummy = new ListNode(-1);
        ListNode itr = dummy;
        int temp = 0;
        while(revL1 != null || revL2 != null) {
            temp = temp / 10;
            if(revL1 != null) {
                temp += revL1.val;
                revL1 = revL1.next;
            }
            if(revL2 != null) {
                temp += revL2.val;
                revL2 = revL2.next;
            }
            itr.next = new ListNode(temp % 10);
            itr = itr.next;
        }
        if(temp / 10 == 1) {
            itr.next = new ListNode(1);
        }
        return reverseList(dummy.next); 
    }
    
    public ListNode reverseList(ListNode head) {
        if(head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode start = prev.next;
        ListNode then = start.next;
        while(start.next != null) {
            start.next = then.next;
            then.next = prev.next;
            prev.next = then;
            then = start.next;
        }
        return dummy.next;
    }
    
    public static void main(String[] args) {
    	AddTwoNumbersII a = new AddTwoNumbersII();
    	ListNode seven = a.new ListNode(7);
    	ListNode two = a.new ListNode(2);
    	ListNode four = a.new ListNode(4);
    	ListNode three = a.new ListNode(3);
    	seven.next = two;
    	two.next = four;
    	four.next = three;
    	
    	ListNode five = a.new ListNode(5);
    	ListNode six = a.new ListNode(6);
    	ListNode fourAgain = a.new ListNode(4);
    	five.next = six;
    	six.next = fourAgain;
    	
    	ListNode result = a.addTwoNumbers(seven, five);
    	System.out.println(result.val);
    }
}

// Solution 2: Use 3 stacks
// Refer to
// https://discuss.leetcode.com/topic/65279/easy-o-n-java-solution-using-stack
// http://www.cnblogs.com/grandyang/p/6216480.html
/**
 * 这道题是之前那道Add Two Numbers的拓展，我们可以看到这道题的最高位在链表首位置，如果我们给链表翻转一下的话就跟之前
 * 的题目一样了，这里我们来看一些不修改链表顺序的方法。由于加法需要从最低位开始运算，而最低位在链表末尾，链表只能从前
 * 往后遍历，没法取到前面的元素，那怎么办呢？我们可以利用栈来保存所有的元素，然后利用栈的后进先出的特点就可以从后往前
 * 取数字了，我们首先遍历两个链表，将所有数字分别压入两个栈s1和s2中，我们建立一个值为0的res节点，然后开始循环，如果
 * 栈不为空，则将栈顶数字加入sum中，然后将res节点值赋为sum%10，然后新建一个进位节点head，赋值为sum/10，如果没有进位，
 * 那么就是0，然后我们head后面连上res，将res指向head，这样循环退出后，我们只要看res的值是否为0，为0返回res->next，
 * 不为0则返回res即可
*/
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Stack<Integer> s1 = new Stack<Integer>();
        Stack<Integer> s2 = new Stack<Integer>();
        Stack<Integer> s = new Stack<Integer>();
        while(l1 != null) {
            s1.push(l1.val);
            l1 = l1.next;
        }
        while(l2 != null) {
            s2.push(l2.val);
            l2 = l2.next;
        }
        ListNode dummy = new ListNode(-1);
        ListNode itr = dummy;
        int temp = 0;
        while(!s1.empty() || !s2.empty()) {
            temp = temp / 10;
            if(!s1.empty()) {
                temp += s1.pop();
            }
            if(!s2.empty()) {
                temp += s2.pop();
            }
            // Important, as using two stacks, we are adding
            // two least significant numbers first, but for
            // result must come with most significant digit
            // first, we have to either use stack or trickily
            // appending result list from dummy as end(not as
            // head as normal)
            //itr.next = new ListNode(temp % 10);
            //itr = itr.next;
            s.push(temp % 10);
        }
        // if(temp / 10 == 1) {
        //     itr.next = new ListNode(1);
        // }
        if(temp / 10 == 1) {
            s.push(1);
        }
        while(!s.empty()) {
            itr.next = new ListNode(s.pop());
            itr = itr.next;
        }
        return dummy.next;
    }
}

// Solution 3: No need to use 3 stacks, just use 2 stacks
// and continously insert new head before old head to
// construct the final linked list
// Refer to
// https://discuss.leetcode.com/topic/65279/easy-o-n-java-solution-using-stack
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Stack<Integer> s1 = new Stack<Integer>();
        Stack<Integer> s2 = new Stack<Integer>();

        while(l1 != null) {
            s1.push(l1.val);
            l1 = l1.next;
        }
        while(l2 != null) {
            s2.push(l2.val);
            l2 = l2.next;
        }

        int temp = 0;
        // Create a dummy old head as null for this case 
        // (as repeatly adding new head before the old head)
        ListNode oldHead = null;
        ListNode itr = oldHead;
        ListNode newHead;
        while(!s1.empty() || !s2.empty()) {
            temp = temp / 10;
            if(!s1.empty()) {
                temp += s1.pop();
            }
            if(!s2.empty()) {
                temp += s2.pop();
            }
            // Important, as using two stacks, we are adding
            // two least significant numbers first, but for
            // result must come with most significant digit
            // first, we have to either use stack or trickily
            // appending result list from dummy as end(not as
            // head as normal)
            //itr.next = new ListNode(temp % 10);
            //itr = itr.next;
            newHead = new ListNode(temp % 10);
            // Insert before old tail
            newHead.next = itr;
            // Update pointer from old tail to new tail
            itr = newHead;
        }
        if(temp / 10 == 1) {
            newHead = new ListNode(1);
            newHead.next = itr;
            itr = newHead;
        }
        return itr;
    }

}

