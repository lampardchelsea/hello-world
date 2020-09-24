/**
 * Refer to
 * https://leetcode.com/problems/intersection-of-two-linked-lists/?tab=Description
 * Write a program to find the node at which the intersection of two singly linked lists begins.
	For example, the following two linked lists:
	
	A:          a1 → a2
	                   ↘
	                     c1 → c2 → c3
	                   ↗            
	B:     b1 → b2 → b3
	begin to intersect at node c1.
	
 * Notes:
 * If the two linked lists have no intersection at all, return null.
 * The linked lists must retain their original structure after the function returns.
 * You may assume there are no cycles anywhere in the entire linked structure.
 * Your code should preferably run in O(n) time and use only O(1) memory.
 */
public class IntersectionOfTwoLinkedLists {
	 // Definition for singly-linked list.
	 private class ListNode {
	     int val;
	     ListNode next;
	     ListNode(int x) {
	         val = x;
	         next = null;
	     }
	 }
	
	 // Method to calculate length on both linkedlists
	 // Refer to
	 // https://leetcode.com/problems/intersection-of-two-linked-lists/discuss/49792/concise-java-solution-o1-memory-on-time
	 public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
		if(headA == null || headB == null) {
		    return null;
		}
		int lenA = 1;
		int lenB = 1;
		ListNode itrA = headA;
		ListNode itrB = headB;
		while(itrA.next != null) {
		    lenA++;
		    itrA = itrA.next;
		}
		while(itrB.next != null) {
		    lenB++;
		    itrB = itrB.next;
		}
		int maxLen = lenA > lenB ? lenA : lenB;
		int steps = 0;
		if(maxLen == lenA) {
		    steps = lenA - lenB;
		    while(steps > 0) {
		        headA = headA.next;
		        steps--;
		    }
		} else {
		    steps = lenB - lenA;
		    while(steps > 0) {
		        headB = headB.next;
		        steps--;
		    }
		}
		//        while(headA != headB && maxLen - steps > 0) {
		//            headA = headA.next;
		//            headB = headB.next;
		//            if(headA == headB) {
		//                return headA;
		//            }
		//        }
		//        // Important: Cannot return null as default, because it assumes
		//        // while(headA != headB) as previous condition, if headA == headB
		//        // initially but return as null, that is wrong
		//        return null;
		// It can be concise as below:
		while(headA != headB) {
		    headA = headA.next;
		    headB = headB.next;
		}
		return headA;
     }
	
	 // Method without calculate length
	 // Refer to
	 // https://discuss.leetcode.com/topic/5527/my-accepted-simple-and-shortest-c-code-with-comments-explaining-the-algorithm-any-comments-or-improvements/2
	 // https://discuss.leetcode.com/topic/28067/java-solution-without-knowing-the-difference-in-len/2
	 /**
	  * I found most solutions here pre-process linkedlists to get the difference in len.
	  * Actually we don't care about the "value" of difference, we just want to make 
	  * sure two pointers reach the intersection node at the same time.
	  * We can use two iterations to do that. In the first iteration, we will reset the pointer of one linkedlist 
	  * to the head of another linkedlist after it reaches the tail node. In the second iteration, we will move 
	  * two pointers until they points to the same node. Our operations in first iteration will help us counteract 
	  * the difference. So if two linkedlist intersects, the meeting point in second iteration must be the 
	  * intersection point. If the two linked lists have no intersection at all, then the meeting pointer in 
	  * second iteration must be the tail node of both lists, which is null
	  */
	 public ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        if(headA == null || headB == null) {
            return null;
        }
        ListNode itrA = headA;
        ListNode itrB = headB;
        while(itrA != null && itrB != null && itrA != itrB) {
            itrA = itrA.next;
            itrB = itrB.next;
            if(itrA == itrB) {
                return itrA;
            }
            if(itrA == null) {
                itrA = headB;
            }
            if(itrB == null) {
                itrB = headA;
            }
        }
        return itrA;
	 }
	
	 
	public static void main(String[] args) {
		IntersectionOfTwoLinkedLists m = new IntersectionOfTwoLinkedLists();
		// Test 1:
		// headA = [1,3,5,7,9,11,13,15,17,19,21]
		// headB = [2]
//		ListNode headA = m.new ListNode(1);
//		ListNode a = m.new ListNode(3);
//		ListNode b = m.new ListNode(5);
//		ListNode c = m.new ListNode(7);
//		ListNode d = m.new ListNode(9);
//		ListNode e = m.new ListNode(11);
//		ListNode f = m.new ListNode(13);
//		ListNode g = m.new ListNode(15);
//		ListNode h = m.new ListNode(17);
//		ListNode i = m.new ListNode(19);
//		ListNode j = m.new ListNode(21);
//		headA.next = a;
//		headA.next.next = b;
//		headA.next.next.next = c;
//		headA.next.next.next.next = d;
//		headA.next.next.next.next.next = e;
//		headA.next.next.next.next.next.next = f;
//		headA.next.next.next.next.next.next.next = g;
//		headA.next.next.next.next.next.next.next.next = h;
//		headA.next.next.next.next.next.next.next.next.next = i;
//		headA.next.next.next.next.next.next.next.next.next.next = j;
//		ListNode headB = m.new ListNode(2);
		
		// Test 2:
		// headA = [1]
		// headB = [1]
		ListNode headA = m.new ListNode(1);
		ListNode headB = m.new ListNode(1);
		ListNode result = m.getIntersectionNode(headA, headB);
		System.out.println(result);
		
		ListNode result1 = m.getIntersectionNode2(headA, headB);
		System.out.println(result1);
	}
}

// Re-work
// Solution 1: Calculate length
/**
 * Definition for singly-linked list.
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
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA == null || headB == null) {
            return null;
        }
        ListNode iter1 = headA;
        ListNode iter2 = headB;
        int len1 = 0;
        int len2 = 0;
        while(iter1 != null) {
            iter1 = iter1.next;
            len1++;
        }
        while(iter2 != null) {
            iter2 = iter2.next;
            len2++;
        }
        if(len1 > len2) {
            for(int i = 0; i < len1 - len2; i++) {
                headA = headA.next;
            }
        } else {
            for(int i = 0; i < len2 - len1; i++) {
                headB = headB.next;
            }
        }
        while(headA != headB) {
            headA = headA.next;
            headB = headB.next;
        }
        return headA;
    }
}

// Solution 2: No calculate length
// Refer to
// https://leetcode.com/problems/intersection-of-two-linked-lists/discuss/49785/Java-solution-without-knowing-the-difference-in-len!
// https://leetcode.com/problems/intersection-of-two-linked-lists/discuss/49785/Java-solution-without-knowing-the-difference-in-len!/165648
/**
Visualization of this solution:
Case 1 (Have Intersection & Same Len):

       a
A:     a1 → a2 → a3
                   ↘
                     c1 → c2 → c3 → null
                   ↗            
B:     b1 → b2 → b3
       b
            a
A:     a1 → a2 → a3
                   ↘
                     c1 → c2 → c3 → null
                   ↗            
B:     b1 → b2 → b3
            b
                 a
A:     a1 → a2 → a3
                   ↘
                     c1 → c2 → c3 → null
                   ↗            
B:     b1 → b2 → b3
                 b
A:     a1 → a2 → a3
                   ↘ a
                     c1 → c2 → c3 → null
                   ↗ b            
B:     b1 → b2 → b3
Since a == b is true, end loop while(a != b), return the intersection node a = c1.

Case 2 (Have Intersection & Different Len):

            a
A:          a1 → a2
                   ↘
                     c1 → c2 → c3 → null
                   ↗            
B:     b1 → b2 → b3
       b
204
            headB = headB.next;
205
        }
206
        return headA;
207
    }
208
}
209
​
210
// Solution 2: No calculate length
211
// Refer to
212
// https://leetcode.com/problems/intersection-of-two-linked-lists/discuss/49785/Java-solution-without-knowing-the-difference-in-len!
213
// https://leetcode.com/problems/intersection-of-two-linked-lists/discuss/49785/Java-solution-without-knowing-the-difference-in-len!/165648
214
/**
215
Visualization of this solution:
216
Case 1 (Have Intersection & Same Len):
217
​
218
       a
219
A:     a1 → a2 → a3
220
                   ↘
221
                     c1 → c2 → c3 → null
222
                   ↗            
223
B:     b1 → b2 → b3
224
       b
225
            a
226
A:     a1 → a2 → a3
227
                   ↘
228
                     c1 → c2 → c3 → null
229
                   ↗            
230
B:     b1 → b2 → b3
231
            b
232
                 a
233
A:     a1 → a2 → a3
234
                   ↘
235
                     c1 → c2 → c3 → null
236
                   ↗            
237
B:     b1 → b2 → b3
238
                 b
239
A:     a1 → a2 → a3
240
                   ↘ a
241
                     c1 → c2 → c3 → null
242
                   ↗ b            
243
B:     b1 → b2 → b3
244
Since a == b is true, end loop while(a != b), return the intersection node a = c1.
245
​
246
Case 2 (Have Intersection & Different Len):
247
​
248
            a
249
A:          a1 → a2
250
                   ↘
251
                     c1 → c2 → c3 → null
252
                   ↗            
253
B:     b1 → b2 → b3
254
       b
255
                 a
256
A:          a1 → a2
257
                   ↘
258
                     c1 → c2 → c3 → null

                 a
A:          a1 → a2
                   ↘
                     c1 → c2 → c3 → null
                   ↗            
B:     b1 → b2 → b3
            b
A:          a1 → a2
                   ↘ a
                     c1 → c2 → c3 → null
                   ↗            
B:     b1 → b2 → b3
                 b
A:          a1 → a2
                   ↘      a
                     c1 → c2 → c3 → null
                   ↗ b           
B:     b1 → b2 → b3
A:          a1 → a2
                   ↘           a
                     c1 → c2 → c3 → null
                   ↗      b           
B:     b1 → b2 → b3
A:          a1 → a2
                   ↘                a = null, then a = b1
                     c1 → c2 → c3 → null
                   ↗           b           
B:     b1 → b2 → b3
A:          a1 → a2
                   ↘ 
                     c1 → c2 → c3 → null
                   ↗                b = null, then b = a1 
B:     b1 → b2 → b3
       a
            b         
A:          a1 → a2
                   ↘ 
                     c1 → c2 → c3 → null
                   ↗
B:     b1 → b2 → b3
            a
                 b         
A:          a1 → a2
                   ↘ 
                     c1 → c2 → c3 → null
                   ↗ 
B:     b1 → b2 → b3
                 a
A:          a1 → a2
                   ↘ b
                     c1 → c2 → c3 → null
                   ↗ a
B:     b1 → b2 → b3
Since a == b is true, end loop while(a != b), return the intersection node a = c1.

Case 3 (Have No Intersection & Same Len):

       a
A:     a1 → a2 → a3 → null
B:     b1 → b2 → b3 → null
       b
            a
A:     a1 → a2 → a3 → null
B:     b1 → b2 → b3 → null
            b
                 a
A:     a1 → a2 → a3 → null
B:     b1 → b2 → b3 → null
                 b
                      a = null
A:     a1 → a2 → a3 → null
B:     b1 → b2 → b3 → null
                      b = null
Since a == b is true (both refer to null), end loop while(a != b), return a = null.

Case 4 (Have No Intersection & Different Len):

       a
A:     a1 → a2 → a3 → a4 → null
B:     b1 → b2 → b3 → null
       b
            a
A:     a1 → a2 → a3 → a4 → null
B:     b1 → b2 → b3 → null
            b
                 a
A:     a1 → a2 → a3 → a4 → null
B:     b1 → b2 → b3 → null
                 b
                      a
A:     a1 → a2 → a3 → a4 → null
B:     b1 → b2 → b3 → null
                      b = null, then b = a1
       b                   a = null, then a = b1
A:     a1 → a2 → a3 → a4 → null
B:     b1 → b2 → b3 → null
            b                   
A:     a1 → a2 → a3 → a4 → null
B:     b1 → b2 → b3 → null
       a
                 b
A:     a1 → a2 → a3 → a4 → null
B:     b1 → b2 → b3 → null
            a
                      b
A:     a1 → a2 → a3 → a4 → null
B:     b1 → b2 → b3 → null
                 a
                           b = null
A:     a1 → a2 → a3 → a4 → null
B:     b1 → b2 → b3 → null
                      a = null
Since a == b is true (both refer to null), end loop while(a != b), return a = null.

Notice that if list A and list B have the same length, this solution will terminate in no more than 1 traversal; 
if both lists have different lengths, this solution will terminate in no more than 2 traversals -- in the second 
traversal, swapping a and b synchronizes a and b before the end of the second traversal. By synchronizing a and b 
I mean both have the same remaining steps in the second traversal so that it's guaranteed for them to reach the 
first intersection node, or reach null at the same time (technically speaking, in the same iteration) -- see 
Case 2 (Have Intersection & Different Len) and Case 4 (Have No Intersection & Different Len).

PS: There are many great explanations of this solution for various cases, I believe to visualize it can resolve 
most of the doubts posted previously.
*/

// https://leetcode.wang/leetcode-160-Intersection-of-Two-Linked-Lists.html
/**
 它没有去分别求两个链表的长度，而是把所有的情况都合并了起来。
 如果没有重合部分，那么 a 和 b 在某一时间点 一定会同时走到 null，从而结束循环。
 如果有重合部分，分两种情况。
 长度相同的话， a 和 b 一定是同时到达相遇点，然后返回。
 长度不同的话，较短的链表先到达结尾，然后指针转向较长的链表。此刻，较长的链表继续向末尾走，多走的距离刚好就是最开始介绍的解法，
 链表的长度差，走完之后指针转向较短的链表。然后继续走的话，相遇的位置就刚好是相遇点了。
*/
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA == null || headB == null) {
            return null;
        }
        ListNode iter1 = headA;
        ListNode iter2 = headB;
        while(iter1 != iter2) {
            if(iter1 == null) {
                iter1 = headB;
            } else {
                iter1 = iter1.next;
            }
            if(iter2 == null) {
                iter2 = headA;
            } else {
                iter2 = iter2.next;
            }
        }
        return iter1;
    }
}
