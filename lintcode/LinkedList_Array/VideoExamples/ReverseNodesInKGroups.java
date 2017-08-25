// Very Important: Please refer uniform ways on how to solve both Reverse Linked List II and Reverse Nodes In K Group under leetcode folder
// https://github.com/lampardchelsea/hello-world/blob/6f1916b90009115b28a930ad3f6920a0bf3d84e0/leetcode/LinkedList/ReverseLinkedListII.java
// https://github.com/lampardchelsea/hello-world/blob/6f1916b90009115b28a930ad3f6920a0bf3d84e0/leetcode/LinkedList/ReverseNodesInKGroup.java


/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */


public class Solution {
    /*
     * @param head: a ListNode
     * @param k: An integer
     * @return: a ListNode
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        if(head == null) {
            return null;
        }
        // Create dummy for reconstruct list
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        // Create new reference 'itr' point to 'dummy' node
        // used for traverse and build list
        ListNode itr = dummy;
        while(true) {
            itr = reverseBetween(itr, k);
            if(itr == null) {
                break;
            }
        }
        return dummy.next;
    }
    
    // head -> n1 -> n2 ... nk -> nk+1
    // =>
    // head -> nk -> nk-1 .. n1 -> nk+1
    // return n1
    // If continue use the same way as Reverse Linked List II, it will piss off !
    // E.g given 1->2->3->4->5 will return 1->4->5
    // private ListNode reverseBetween(ListNode itr, int k) {
    //     ListNode prevmNode = itr;
    //     ListNode mNode = itr.next;
    //     ListNode nNode = mNode;
    //     ListNode postnNode = mNode.next;
    //     for(int i = 0; i < k; i++) {
    //         ListNode then = postnNode.next;
    //         postnNode.next = nNode;
    //         nNode = postnNode;
    //         postnNode = then;
    //     }
    //     mNode.next = postnNode;
    //     prevmNode.next = nNode;
    //     return mNode;
    // }
    
    private ListNode reverseBetween(ListNode prev, int k) {
		ListNode nodek = prev;
		ListNode node1 = prev.next;
		for(int i = 0; i < k; i++) {
		    // This check is necessary
		    if(nodek == null) {
			    return null;
			}
		    nodek = nodek.next;
		}
	        // This check is also necessary
		if(nodek == null) {
		    return null;
		}
		
		ListNode nodekplus = nodek.next;
		
		ListNode preCurt = prev;
		ListNode curt = prev.next;
		for(int i = 0; i < k; i++) {
		    ListNode temp = curt.next;
			curt.next = preCurt;
			preCurt = curt;
			curt = temp;
		}
		
		node1.next = nodekplus;
		prev.next = nodek;
		
		return node1;
    }
}


