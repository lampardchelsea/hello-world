
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
	 // https://discuss.leetcode.com/topic/5492/concise-java-solution-o-1-memory-o-n-time
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

