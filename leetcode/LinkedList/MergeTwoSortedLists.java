/**
 * Refer to
 * https://leetcode.com/problems/merge-two-sorted-lists/?tab=Description
 * Merge two sorted linked lists and return it as a new list. 
 * The new list should be made by splicing together the nodes of the first two lists.
 * 
 * Solution
 * Refer to
 * http://www.geeksforgeeks.org/merge-two-sorted-linked-lists/
 * https://discuss.leetcode.com/topic/5513/my-recursive-way-to-solve-this-problem-java-easy-understanding/2
 */
public class MergeTwoLists {
    private class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
    	    val = x; 
        }
    }
    
    // Solution 1: Use dummy header
    /**
     * The strategy here uses a temporary dummy node as the start of the result list. 
     * The pointer Tail always points to the last node in the result list, so appending new nodes is easy.
     * The dummy node gives tail something to point to initially when the result list is empty. 
     * This dummy node is efficient, since it is only temporary, and it is allocated in the stack. 
     * The loop proceeds, removing one node from either ‘a’ or ‘b’, and adding it to tail. When
     * we are done, the result is in dummy.next.
     */
	public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1 == null && l2 == null) {
            return null;
        }
        // These 2 conditions include in while loop
//        if(l1 == null) {
//            return l2;
//        }
//        if(l2 == null) {
//            return l1;
//        }
        ListNode dummy = new ListNode(-1);
        ListNode itr = dummy;
        ListNode itr1 = l1;
        ListNode itr2 = l2;
        while(itr1 != null || itr2 != null) {
            if(itr1 != null && itr2 != null) {
                if(itr1.val < itr2.val) {
                    itr.next = itr1;
                    itr1 = itr1.next;
                } else {
                    itr.next = itr2;
                    itr2 = itr2.next;
                } 
            } else if(itr1 == null) {
                itr.next = itr2;
                itr2 = itr2.next;
            } else if(itr2 == null) {
                itr.next = itr1;
                itr1 = itr1.next;
            }
            itr = itr.next;
        }
        return dummy.next;
    }
	
	// Solution 2: Use Recursive
	// Refer to
	// https://discuss.leetcode.com/topic/5513/my-recursive-way-to-solve-this-problem-java-easy-understanding/2
	/**
	 * Merge is one of those nice recursive problems where the recursive solution code is much 
	 * cleaner than the iterative code. You probably wouldn’t want to use the recursive version 
	 * for production code however, because it will use stack space which is proportional to 
	 * the length of the lists.
	 */
	public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
		if(l1 == null){
            return l2;
        }
        if(l2 == null){
            return l1;
        }
        
        ListNode mergeHead;
        if(l1.val < l2.val){
            mergeHead = l1;
            mergeHead.next = mergeTwoLists(l1.next, l2);
        }
        else{
            mergeHead = l2;
            mergeHead.next = mergeTwoLists(l1, l2.next);
        }
        return mergeHead;
	}
	
	
	public static void main(String[] args) {
		MergeTwoLists m = new MergeTwoLists();
		ListNode one = m.new ListNode(1);
		ListNode two = m.new ListNode(2);
		ListNode result = m.mergeTwoLists(one, two);
		ListNode result2 = m.mergeTwoLists2(one, two);
		System.out.println(result.val);
	}
}

