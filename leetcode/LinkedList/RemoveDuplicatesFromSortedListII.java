/**
 * Refer to
 * https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/#/description
 * Given a sorted linked list, delete all nodes that have duplicate numbers, 
 * leaving only distinct numbers from the original list.
 * For example,
 * Given 1->2->3->3->4->4->5, return 1->2->5.
 * Given 1->1->1->2->3, return 2->3.
 */
public class RemoveDuplicatesFromSortedListII {
	private class ListNode {
		int val;
		ListNode next;
		ListNode(int x) { 
			val = x; 
		}
	}
	
	// If the linked list is not the sorted one, cannot use this three pointers solution
	/**
	 * Solution 1:
	 * http://www.cnblogs.com/springfor/p/3862077.html
	 * 这道题与I的区别就是要把所有重复的node删除。因此，还是利用I中去重的方法，引用双指针，并且增加一个新的指针，
	 * 指向当前的前一个node，使用3个指针（prev，current，post）来遍历链表。 
	 * 最开始还是需要建立一个fakehead，让fakehead的next指向head。然后，使用我刚才说过的3个指针方法来初始化3个指针，如下： 
		  ListNode ptr0 = fakehead; //prev
		  ListNode ptr1 = fakehead.next; //current
		  ListNode ptr2 = fakehead.next.next; //post
	 * 同时还需要引入一个布尔型的判断flag，来帮助判断当前是否遇到有重复，这个flag能帮助识别是否需要删除重复。
	 * 当没有遇到重复值（flag为false）时，3个指针同时往后移动：
	      ptr0 = ptr1;
	      ptr1 = ptr2;
	      ptr2 = ptr2.next; 
	 * 当遇到重复值时，设置flag为true，并让ptr2一直往后找找到第一个与ptr1值不等的位置时停止，这时，ptr1指向的node的值
	 * 是一个重复值，需要删除，所以这时就需要让ptr0的next连上当前的ptr2，这样就把所有重复值略过了(这样有效的前提是因为
	 * linkedlist已经是sorted，所有的同样字母已经被归结在一起，这一点很重要，否则无法实现用三指针跳过所有同样字符的模式)。
	 * 然后，让ptr1和ptr2往后挪动继续查找。
	 * 这里还需要注意的是，当ptr2一直往后找的过程中，是有可能ptr2==null（这种情况就是list的最后几个元素是重复的，
	 * 例如1->2->3->3->null)，这时ptr1指向的值肯定是需要被删除的，所以要特殊处理，令ptr0的next等于null，把重复值删掉。
	 * 其他情况说明最后几个元素不重复，不需要处理结尾，遍历就够了。
	 */
	public ListNode deleteDuplicates(ListNode head) {
        if(head == null || head.next == null)
            return head;
        
        ListNode fakehead = new ListNode(0);
        fakehead.next = head;
        
        ListNode ptr0 = fakehead;
        ListNode ptr1 = fakehead.next;
        ListNode ptr2 = fakehead.next.next;
        
        boolean flag = false;
        while(ptr2!=null){
            if(ptr1.val == ptr2.val){
                flag = true;
                ptr2 = ptr2.next;
                if(ptr2 == null)
                    ptr0.next = null;
            }else{
                if(flag){
                    ptr0.next = ptr2;
                    flag = false;
                }else{
                    ptr0 = ptr1;
                }
                ptr1 = ptr2;
                ptr2 = ptr2.next;
            }
        }
        return fakehead.next;
    }
	
	/**
	 * Solution 2:
	 * Refer to
	 * https://discuss.leetcode.com/topic/3890/my-accepted-java-code
	 * Just need 2 pointers
	 */
	public ListNode deleteDuplicates2(ListNode head) {
        if(head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode curr = head;
        ListNode prev = dummy;
        while(curr != null) {
            while(curr.next != null && curr.val == curr.next.val) {
                curr = curr.next;
            }
            if(prev.next == curr) {
                prev = prev.next;
            } else {
                prev.next = curr.next;
            }
            curr = curr.next;
        }
        return dummy.next;
	}
	
	public static void main(String[] args) {
		RemoveDuplicatesFromSortedListII r = new RemoveDuplicatesFromSortedListII();
		ListNode one = r.new ListNode(1);
		ListNode oneAgain = r.new ListNode(1);
		ListNode two = r.new ListNode(2);
		ListNode twoAgain = r.new ListNode(2);
		ListNode three = r.new ListNode(3);
		one.next = oneAgain;
		oneAgain.next = two;
		two.next = twoAgain;
		twoAgain.next = three;
		ListNode result = r.deleteDuplicates2(one);
		System.out.println(result.val);
	}
}

// Re-work
// Style 1:
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if(head == null) {
            return head;
        }
        // dummy -> head -> head.next
        //  prev    curr    iter
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode curr = head;
        ListNode iter = head.next;
        int currValCount = 1;
        while(iter != null) {
            if(curr.val == iter.val) {
                currValCount++;
                // Test case: No different value node than current node till end
                // [1,2,2] -> expected [1]
                // Since not able to use 'prev.next = iter' to remove all duplicates
                // node, have to clean up all nodes from 'prev.next' till end
                if(iter.next == null) {
                    prev.next = null;
                }
            } else {
                if(currValCount > 1) {
                    prev.next = iter; // Remove all duplicate nodes including first occurrence
                    curr = iter;      // Update curr to iter
                    currValCount = 1; // Reset count to 1
                } else {
                    prev = curr;
                    curr = iter;
                }
            }
            iter = iter.next;
        }
        // Test case: [1,2,2] -> expected [1] output []
        // Or we can put the case handling here
        //if(currValCount > 1) {
        //    prev.next = null;
        //}
        return dummy.next;
    }
}

// Style 2:
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
    public ListNode deleteDuplicates(ListNode head) {
        if(head == null) {
            return head;
        }
        // dummy -> head -> head.next
        //  prev    curr    iter
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode curr = head;
        ListNode iter = head.next;
        int currValCount = 1;
        while(iter != null) {
            if(curr.val == iter.val) {
                currValCount++;
                // Test case: No different value node than current node till end
                // [1,2,2] -> expected [1]
                // Since not able to use 'prev.next = iter' to remove all duplicates
                // node, have to clean up all nodes from 'prev.next' till end
                //if(iter.next == null) {
                //    prev.next = null;
                //}
            } else {
                if(currValCount > 1) {
                    prev.next = iter; // Remove all duplicate nodes including first occurrence
                    curr = iter;      // Update curr to iter
                    currValCount = 1; // Reset count to 1
                } else {
                    prev = curr;
                    curr = iter;
                }
            }
            iter = iter.next;
        }
        // Test case: [1,2,2] -> expected [1] output []
        // Or we can put the case handling here
        if(currValCount > 1) {
            prev.next = null;
        }
        return dummy.next;
    }
}
