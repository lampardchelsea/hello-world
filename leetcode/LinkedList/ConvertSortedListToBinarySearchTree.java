/**
 * Refer to
 * https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree/?tab=Description
 * Given a singly linked list where elements are sorted in ascending order, 
 * convert it to a height balanced BST.
 * 
 * Solution 1: Inorder traverse to construct BST
 * Refer to
 * http://www.geeksforgeeks.org/tree-traversals-inorder-preorder-and-postorder/
 * 
 * Below two links give best solution
 * https://discuss.leetcode.com/topic/8141/share-my-o-1-space-and-o-n-time-java-code
 * http://www.geeksforgeeks.org/sorted-linked-list-to-balanced-bst/ --> This link has detail explanation
 * Following is a simple algorithm where we first find the middle node of list and make it root 
   of the tree to be constructed.
   1) Get the Middle of the linked list and make it root.
   2) Recursively do same for left half and right half.
       a) Get the middle of left half and make it left child of the root
          created in step 1.
       b) Get the middle of right half and make it right child of the
          root created in step 1.
   Time complexity: O(nLogn) where n is the number of nodes in Linked List.
 *
 * Below link cannot directly implement (which the ListNode node must set as global variable
 * , the wrong version of code is set as local variable and pass into recursive method)
 * http://articles.leetcode.com/convert-sorted-list-to-balanced-binary/
 * Best Solution:
 * As usual, the best solution requires you to think from another perspective. In other words, 
 * we no longer create nodes in the tree using the top-down approach. We create nodes bottom-up, 
 * and assign them to its parents. The bottom-up approach enables us to access the list in its 
 * order while creating nodes.
 * Isn’t the bottom-up approach neat? Each time you are stucked with the top-down approach, 
 * give bottom-up a try. Although bottom-up approach is not the most natural way we think, 
 * it is extremely helpful in some cases. However, you should prefer top-down instead of bottom-up 
 * in general, since the latter is more difficult to verify in correctness.
 * Below is the code for converting a singly linked list to a balanced BST. Please note that 
 * the algorithm requires the list’s length to be passed in as the function’s parameters. 
 * The list’s length could be found in O(N) time by traversing the entire list’s once. 
 * The recursive calls traverse the list and create tree’s nodes by the list’s order, 
 * which also takes O(N) time. Therefore, the overall run time complexity is still O(N).
 * 
 * Solution 2: Preorder traverse to construct BST
 * https://discuss.leetcode.com/topic/35997/share-my-java-solution-1ms-very-short-and-concise
 * 
 * 
 */
public class ConvertSortedListToBinarySearchTree {
	private class ListNode {
		int val;
		ListNode next;
		ListNode(int x) { 
			val = x; 
		}
	}
	
	private class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x) { 
			val = x; 
		}
	}
	
	// Wrong Solution: Pass 'node' as local variable.
	// In right solution, 'node' should be global variable,
	// initial as 'head' and update to 'head.next' in
	// recursive method. Wrong situation can be test with
	// {3, 5, 8}, error out put as {3, 3, 5}
//    public TreeNode sortedListToBST(ListNode head) {
//        if(head == null) {
//            return null;
//        }
//        int size = countLen(head);
//        // Index start as 0, so end node mapping to (size - 1)
//        return inorderSortedListToBST(head, 0, size - 1);
//    }
//    
//    public int countLen(ListNode head) {
//        ListNode itr = head;
//        int count = 0;
//        while(itr != null) {
//            itr = itr.next;
//            count++;
//        }
//        return count;
//    }
//    
//    public TreeNode inorderSortedListToBST(ListNode node, int start, int end) {
//        if(start > end) {
//            return null;
//        }
//        // Same as (start+end)/2, avoids overflow
//        int mid = start + (end - start)/2;
//        TreeNode left = inorderSortedListToBST(node, start, mid - 1);
//        TreeNode parent = new TreeNode(node.val);
//        parent.left = left;
//        node = node.next;
//        parent.right = inorderSortedListToBST(node, mid + 1, end);
//        return parent;
//    } 
	
	// Right Solution 1: Inorder traverse to construct BST
    private ListNode node;
    public TreeNode sortedListToBST(ListNode head) {
        if(head == null) {
            return null;
        }
        int size = countLen(head);
        node = head;
        // Index start as 0, so end node mapping to (size - 1)
        return inorderSortedListToBST(0, size - 1);
    }
    
    public int countLen(ListNode head) {
        ListNode itr = head;
        int count = 0;
        while(itr != null) {
            itr = itr.next;
            count++;
        }
        return count;
    }
    
    // The main function that constructs BST and returns root of it
    public TreeNode inorderSortedListToBST(int start, int end) {
    	// Base case
        if(start > end) {
            return null;
        }
        // Same as (start+end)/2, avoids overflow
        int mid = start + (end - start)/2;
        // Recursively construct the left subtree
        TreeNode left = inorderSortedListToBST(start, mid - 1);
        // head_ref now refers to the middle node, make
        // middle node as root of BST
        TreeNode root = new TreeNode(node.val);
        // Set pointer to left subtree
        root.left = left;
        // Update node pointer of linked list for parent
        // recursive calls, The recursive calls traverse 
        // the list and create tree’s nodes by the list’s 
        // order, which also takes O(N) time. Therefore, 
        // the overall run time complexity is still O(N)
        node = node.next;
        // Recursively construct the right subtree and link it to root
        root.right = inorderSortedListToBST(mid + 1, end);
        return root;
    } 
	
    // Right Solution 2: Preorder traverse to construct BST
    public TreeNode sortedListToBST2(ListNode head) {
    	if(head == null) {
    		return null;
    	}
    	// Pass into end node as null
    	return preorderSortedListToBST(head, null);
    }
    
    public TreeNode preorderSortedListToBST(ListNode start, ListNode end) {
        ListNode walker = start;
        ListNode runner = start;
        // Base Case
        if(start == end) {
            return null;
        }
        // Find mid node
        // Be careful, the check condition should NOT be
        // while(runner.next != end && runner.next.next != end)
        // also, the initial ListNode 'end' passed into should be 'null'
        // Refer to FindMiddleOfLinkedList
        // https://github.com/lampardchelsea/hello-world/blob/master/leetcode/LinkedList/FindMiddleOfLinkedList.java
        while(runner != end && runner.next != end) {
            walker = walker.next;
            runner = runner.next.next;
        }
        // Set mid node as tree root
        TreeNode root = new TreeNode(walker.val);
        root.left = preorderSortedListToBST(start, walker);
        root.right = preorderSortedListToBST(walker.next, end);
        return root;
    } 
    
    
    public static void main(String[] args) {
    	ConvertSortedListToBinarySearchTree c = new ConvertSortedListToBinarySearchTree();
    	ListNode three = c.new ListNode(3);
    	ListNode five = c.new ListNode(5);
    	ListNode eight = c.new ListNode(8);
    	//ListNode ten = c.new ListNode(10);
    	three.next = five;
    	five.next = eight;
    	//eight.next = ten;
//    	TreeNode result = c.sortedListToBST(three);
    	TreeNode result = c.sortedListToBST2(three);
    	System.out.println(result.val);
    }
}
