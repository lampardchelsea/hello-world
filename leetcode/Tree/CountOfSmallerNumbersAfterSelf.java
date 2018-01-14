/**
 * Refer to
 * https://leetcode.com/problems/count-of-smaller-numbers-after-self/description/
 * You are given an integer array nums and you have to return a new counts array. 
   The counts array has the property where counts[i] is the number of smaller elements to the right of nums[i].

    Example:

    Given nums = [5, 2, 6, 1]

    To the right of 5 there are 2 smaller elements (2 and 1).
    To the right of 2 there is only 1 smaller element (1).
    To the right of 6 there is 1 smaller element (1).
    To the right of 1 there is 0 smaller element.
    Return the array [2, 1, 1, 0].
 *
 *
 * Solution
 * https://discuss.leetcode.com/topic/31405/9ms-short-java-bst-solution-get-answer-when-building-bst
 * https://stackoverflow.com/questions/8896758/initial-size-for-the-arraylist
*/
public class CountOfSmallerNumbersAfterSelf {
private class Node {
		Node left;
		Node right;
		int val;
		// Recording the total of number on it's left bottom side
		int sum;
		// Counts the duplication
		int dup = 1;
		public Node(int v, int s) {
			this.val = v;
			this.sum = s;
		}
	}
	
	/**
	 * Refer to
	 * https://discuss.leetcode.com/topic/31405/9ms-short-java-bst-solution-get-answer-when-building-bst
	 * Every node will maintain a val sum recording the total of number on it's left bottom side, dup 
	 * counts the duplication. For example, [3, 2, 2, 6, 1], from back to beginning,we would have:

                1(0, 1)
                     \
                     6(3, 1)
                     /
                   2(0, 2)
                       \
                        3(0, 1)
       
       When we try to insert a number, the total number of smaller number would be adding dup and sum 
       of the nodes where we turn right.for example, if we insert 5, it should be inserted on the way 
       down to the right of 3, the nodes where we turn right is 1(0,1), 2,(0,2), 3(0,1), so the answer 
       should be (0 + 1)+(0 + 2)+ (0 + 1) = 4
       if we insert 7, the right-turning nodes are 1(0,1), 6(3,1), so answer should be (0 + 1) + (3 + 1) = 5
	 */
	public List<Integer> countSmaller(int[] nums) {
        List<Integer> result = new ArrayList<Integer>(nums.length);
        if(nums == null || nums.length == 0) {
        	return result;
        }
        // Refer to
        // Initial size for the ArrayList
        // https://stackoverflow.com/questions/8896758/initial-size-for-the-arraylist
        for(int i = 0; i < nums.length; i++) {
        	result.add(i, 0);
        }
        Node root = null;
        // Build tree from backwards traverse
        for(int i = nums.length - 1; i >= 0; i--) {
        	root = insert(nums[i], root, result, i, 0);
        }
        return result;
    }
	
	private Node insert(int num, Node node, List<Integer> result, int i, int preSum) {
      // Don't deep copy as normal DFS, just make change on original result
      // since each time 'i' is different
		//List<Integer> result = new ArrayList<Integer>(tmp);
		// For new node
		if(node == null) {
			node = new Node(num, 0);
			result.set(i, preSum);
		// For duplicate node
		} else if(node.val == num) {
			node.dup++;
			result.set(i, preSum + node.sum);
		// For smaller node put on left
		// will be calculated into node's
		// total of number on it's left bottom
		} else if(node.val > num) {
			node.sum++;
			node.left = insert(num, node.left, result, i, preSum);
		} else {
			// When we try to insert a number, the total number of smaller 
			// number would be adding dup and sum of the nodes where we turn right.
			node.right = insert(num, node.right, result, i, preSum + node.dup + node.sum);
		}
		return node;
	} 
	
	public static void main(String[] args) {
		CountOfSmallerNumbersAfterSelf c = new CountOfSmallerNumbersAfterSelf();
		int[] nums = new int[]{3, 2, 2, 6, 1};
		List<Integer> result = c.countSmaller(nums);
		for(int i : result) {
			System.out.print(i + " ");
		}
	}

}
