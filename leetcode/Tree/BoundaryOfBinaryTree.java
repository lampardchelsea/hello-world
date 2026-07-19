/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/6833459.html
 * Given a binary tree, return the values of its boundary in anti-clockwise direction starting from root. 
   Boundary includes left boundary, leaves, and right boundary in order without duplicate nodes.

    Left boundary is defined as the path from root to the left-most node. Right boundary is defined as 
    the path from root to the right-most node. If the root doesn't have left subtree or right subtree, 
    then the root itself is left boundary or right boundary. Note this definition only applies to the 
    input binary tree, and not applies to any subtrees.

    The left-most node is defined as a leaf node you could reach when you always firstly travel to 
    the left subtree if exists. If not, travel to the right subtree. Repeat until you reach a leaf node.

    The right-most node is also defined by the same way with left and right exchanged.

    Example 1

    Input:
      1
       \
        2
       / \
      3   4

    Ouput:
    [1, 3, 4, 2]

    Explanation:
    The root doesn't have left subtree, so the root itself is left boundary.
    The leaves are node 3 and 4.
    The right boundary are node 1,2,4. Note the anti-clockwise direction means you should output reversed right boundary.
    So order them in anti-clockwise without duplicates and we have [1,3,4,2].


    Example 2

    Input:
        ____1_____
       /          \
      2            3
     / \          / 
    4   5        6   
       / \      / \
      7   8    9  10  

    Ouput:
    [1,2,4,7,8,9,10,6,3]

    Explanation:
    The left boundary are node 1,2,4. (4 is the left-most node according to definition)
    The leaves are node 4,7,8,9,10.
    The right boundary are node 1,3,6,10. (10 is the right-most node).
    So order them in anti-clockwise without duplicate nodes we have [1,2,4,7,8,9,10,6,3].
 *
 *
 * Solution
 * http://www.cnblogs.com/grandyang/p/6833459.html
   这道题给了我们一棵二叉树，让我们以逆时针的顺序来输出树的边界，按顺序分别为左边界，叶结点和右边界。
   题目中给的例子也能让我们很清晰的明白哪些算是边界上的结点。那么最直接的方法就是分别按顺序求出左边界结点，
   叶结点，和右边界结点。那么如何求的，对于树的操作肯定是用递归最简洁啊，所以我们可以写分别三个递归函数来
   分别求左边界结点，叶结点，和右边界结点。首先我们先要处理根结点的情况，当根结点没有左右子结点时，其也是
   一个叶结点，那么我们一开始就将其加入结果res中，那么再计算叶结点的时候又会再加入一次，这样不对。所以我
   们判断如果根结点至少有一个子结点，我们才提前将其加入结果res中。然后再来看求左边界结点的函数，如果当前
   结点不存在，或者没有子结点，我们直接返回。否则就把当前结点值加入结果res中，然后看如果左子结点存在，
   就对其调用递归函数，反之如果左子结点不存在，那么对右子结点调用递归函数。而对于求右边界结点的函数就
   反过来了，如果右子结点存在，就对其调用递归函数，反之如果右子结点不存在，就对左子结点调用递归函数，
   注意在调用递归函数之后才将结点值加入结果res，因为我们是需要按逆时针的顺序输出。最后就来看求叶结点
   的函数，没什么可说的，就是看没有子结点存在了就加入结果res，然后对左右子结点分别调用递归即可
 
 
 * http://blog.csdn.net/sundawei2016/article/details/73649430
 * https://discuss.leetcode.com/topic/84275/java-12ms-left-boundary-left-leaves-right-leaves-right-boundary
 * http://www.geeksforgeeks.org/tree-traversals-inorder-preorder-and-postorder/
*/
public class BoundaryOfBinaryTree {
       private class TreeNode {
		TreeNode left;
		TreeNode right;
		int val;
		public TreeNode(int x) {
			this.val = x;
		}
	}
	
        // 这道题给了我们一棵二叉树，让我们以逆时针的顺序来输出树的边界，按顺序分别为左边界，
	// 叶结点和右边界。题目中给的例子也能让我们很清晰的明白哪些算是边界上的结点。那么最直接
	// 的方法就是分别按顺序求出左边界结点，叶结点，和右边界结点。那么如何求的，对于树的操作
	// 肯定是用递归最简洁啊，所以我们可以写分别三个递归函数来分别求左边界结点，叶结点，和右边界结点。
	public List<Integer> boundaryOfBinaryTree(TreeNode root) {  
		List<Integer> result = new ArrayList<Integer>();
		if(root == null) {
			return result;
		}
		// 首先我们先要处理根结点的情况，当根结点没有左右子结点时，其也是一个叶结点，
		// 那么我们一开始就将其加入结果res中，那么再计算叶结点的时候又会再加入一次，
		// 这样不对。所以我们判断如果根结点至少有一个子结点，我们才提前将其加入结果res中。
		if(root.left != null || root.right != null) {
			result.add(root.val);
		}
		left_bound(root.left, result);
		leaves(root, result);
		right_bound(root.right, result);
		return result;
	}  
	  
	// pre-order traverse
	// 然后看如果左子结点存在，就对其调用递归函数，反之如果左子结点不存在，那么对右子结点调用递归函数。
	private void left_bound(TreeNode root, List<Integer> result) {  
        if(root == null || (root.left == null && root.right == null)) {
        	return;
        }
        result.add(root.val);
        if(root.left == null) {
        	left_bound(root.right, result);
        } else {
        	left_bound(root.left, result);
        }
	}  
	  
	// post-order traverse
	// 那么对右子结点调用递归函数。而对于求右边界结点的函数就反过来了，如果右子结点存在，就对其调用递归函数，
	// 反之如果右子结点不存在，就对左子结点调用递归函数，注意在调用递归函数之后才将结点值加入结果res，
	// 因为我们是需要按逆时针的顺序输出。
	private void right_bound(TreeNode root, List<Integer> result) {  
        if(root == null || (root.left == null && root.right == null)) {
        	return;
        }
        if(root.right == null) {
        	right_bound(root.left, result);
        } else {
        	right_bound(root.right, result);
        }
        result.add(root.val);
	}  
	
	// 最后就来看求叶结点的函数，没什么可说的，就是看没有子结点存在了就加入结果res，然后对左右子结点分别调用递归即可
	private void leaves(TreeNode root, List<Integer> result) {  
        if(root == null) {
        	return;
        }
        if(root.left == null && root.right == null) {
        	result.add(root.val);
        }
        leaves(root.left, result);
        leaves(root.right, result);
	}
   
   public static void main(String[] args) {
		BoundaryOfBinaryTree b = new BoundaryOfBinaryTree();
		TreeNode one = b.new TreeNode(1);
		TreeNode two = b.new TreeNode(2);
		TreeNode three = b.new TreeNode(3);
		TreeNode four = b.new TreeNode(4);
		TreeNode five = b.new TreeNode(5);
		TreeNode six = b.new TreeNode(6);
		TreeNode seven = b.new TreeNode(7);
		TreeNode eight = b.new TreeNode(8);
		TreeNode nine = b.new TreeNode(9);
		TreeNode ten = b.new TreeNode(10);
		one.left = two;
		one.right = three;
		two.left = four;
		two.right = five;
		five.left = seven;
		five.right = eight;
		three.left = six;
		six.left = nine;
		six.right = ten;
		List<Integer> result = b.boundaryOfBinaryTree(one);
		for(int i : result) {
			System.out.print(i + " ");
		}


}























https://leetcode.ca/2017-05-28-545-Boundary-of-Binary-Tree/
The boundary of a binary tree is the concatenation of the root, the left boundary, the leaves ordered from left-to-right, and the reverse order of the right boundary.
The left boundary is the set of nodes defined by the following:
- The root node's left child is in the left boundary. If the root does not have a left child, then the left boundary is empty.
- If a node in the left boundary and has a left child, then the left child is in the left boundary.
- If a node is in the left boundary, has no left child, but has a right child, then the right child is in the left boundary.
- The leftmost leaf is not in the left boundary.
The right boundary is similar to the left boundary, except it is the right side of the root's right subtree. Again, the leaf is not part of the right boundary, and the right boundary is empty if the root does not have a right child.
The leaves are nodes that do not have any children. For this problem, the root is not a leaf.
Given the root of a binary tree, return the values of its boundary.
 
Example 1:


Input: root = [1,null,2,3,4]
Output: [1,3,4,2]
Explanation:- The left boundary is empty because the root does not have a left child.
- The right boundary follows the path starting from the root's right child 2 -> 4.  4 is a leaf, so the right boundary is [2].
- The leaves from left to right are [3,4].
Concatenating everything results in [1] + [] + [3,4] + [2] = [1,3,4,2].

Example 2:


Input: root = [1,2,3,4,5,6,null,null,null,7,8,9,10]
Output: [1,2,4,7,8,9,10,6,3]
Explanation:
- The left boundary follows the path starting from the root's left child 2 -> 4.  4 is a leaf, so the left boundary is [2].
- The right boundary follows the path starting from the root's right child 3 -> 6 -> 10.  10 is a leaf, so the right boundary is [3,6], and in reverse order is [6,3].
- The leaves from left to right are [4,7,8,9,10].
Concatenating everything results in [1] + [2] + [4,7,8,9,10] + [6,3] = [1,2,4,7,8,9,10,6,3].

Constraints:
- The number of nodes in the tree is in the range [1, 104].
- -1000 <= Node.val <= 1000
--------------------------------------------------------------------------------
Attempt 1: 2026-07-18
Solution 1: Traversal (30 min)
Refer to Deepseek
这道题 (LeetCode 545) 和之前那些“排序 + 滑动窗口”的题目完全不同，它回到了二叉树的领域。这道题难度为 Medium，主要考察的是树的遍历以及对“边界”定义的精准理解。
核心思路：分治 + 三次遍历
根据题目要求，我们按逆时针顺序输出边界，可以分为四个部分（根节点单独处理）：
1.根节点（如果存在的话，直接加入）。
2.左边界：从根节点的左孩子开始，自上而下，一直走到最左叶子节点的父节点为止（不包含叶子节点）。
3.叶子节点：按从左到右的顺序（即中序遍历），收集所有的叶子节点。
4.右边界：从根节点的右孩子开始，自下而上，一直走到最右叶子节点的父节点为止（不包含叶子节点）。
--------------------------------------------------------------------------------
Java 代码实现
import java.util.*;

class Solution {
    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        
        // 1. 加入根节点
        res.add(root.val);
        
        // 2. 收集左边界（不包含叶子节点）
        addLeftBoundary(root.left, res);
        
        // 3. 收集所有叶子节点（分别遍历左右子树，避免根节点重复）
        addLeaves(root.left, res);
        addLeaves(root.right, res);
        
        // 4. 收集右边界（不包含叶子节点，且由于递归后添加，天然是自下而上）
        addRightBoundary(root.right, res);
        
        return res;
    }
    
    // 收集左边界：自上而下。注意跳过叶子节点。
    private void addLeftBoundary(TreeNode node, List<Integer> res) {
        if (node == null) return;
        // 如果是叶子节点，跳过（叶子节点由 addLeaves 统一处理）
        if (node.left == null && node.right == null) return;
        
        res.add(node.val); // 先添加（自上而下）
        
        // 优先走左子树，如果左子树为空，则走右子树（这也是边界的一部分）
        if (node.left != null) {
            addLeftBoundary(node.left, res);
        } else {
            addLeftBoundary(node.right, res);
        }
    }
    
    // 收集叶子节点：中序遍历（先左后右），保证从左到右的顺序
    private void addLeaves(TreeNode node, List<Integer> res) {
        if (node == null) return;
        if (node.left == null && node.right == null) {
            res.add(node.val);
            return;
        }
        addLeaves(node.left, res);
        addLeaves(node.right, res);
    }
    
    // 收集右边界：自下而上。注意跳过叶子节点。
    private void addRightBoundary(TreeNode node, List<Integer> res) {
        if (node == null) return;
        // 如果是叶子节点，跳过
        if (node.left == null && node.right == null) return;
        
        // 优先走右子树，如果右子树为空，则走左子树
        if (node.right != null) {
            addRightBoundary(node.right, res);
        } else {
            addRightBoundary(node.left, res);
        }
        
        // 后添加（递归返回后再加），实现自下而上的顺序
        res.add(node.val);
    }
}

Time Complexity: O(n)
Space Complexity: O(n)
示例验证
树形结构：
        1
      /   \
     2     3
    / \   / \
   4   5 6   7
- addLeftBoundary(2)：添加 2，进入左孩子 4（叶子，跳过）。 -> 左边界：[2]
- addLeaves：左子树叶子 [4, 5]，右子树叶子 [6, 7]。 -> 叶子：[4, 5, 6, 7]
- addRightBoundary(3)：先递归到 7（叶子，跳过），回到 3，添加 3。 -> 右边界：[3]
- 最终：[1, 2, 4, 5, 6, 7, 3]。逆时针完美绕了一圈。
--------------------------------------------------------------------------------
关键细节（面试常问）
1.为什么要跳过叶子节点？
因为叶子节点有专门的 addLeaves 函数统一收集。如果不跳过，addLeftBoundary 和 addRightBoundary 会把叶子节点收集两次（例如左边界函数会把最左边的叶子也加进去，导致重复）。
2.为什么 addLeaves 要分成 root.left 和 root.right 两次调用？
因为我们的 res 里已经包含了 root。如果直接调用 addLeaves(root, res)，当树只有一个根节点时，根节点会被重复添加。分两次调用子节点，既保证了全树叶子被收集，又避免了根节点（如果它是叶子）的重复问题。
3.右边界为什么是“后添加”？
因为逆时针顺序要求右边界是从下到上。利用递归的回溯特性——先递归深入，等递归返回后再将当前节点值加入列表，天然就实现了倒序。
--------------------------------------------------------------------------------
复杂度分析
- 时间复杂度：O(N)。每个节点最多被访问两次（一次在找边界，一次在找叶子）。
- 空间复杂度：O(N)。最坏情况下（树退化成链表），递归栈深度为 N。

Refer to Kimi
Here is the clean Java solution for Leetcode 545. Boundary of Binary Tree.
Key Insight
The boundary consists of three parts in anti-clockwise order:
1.Left boundary: from root down to the leftmost leaf (excluding leaf)
2.Leaves: all leaf nodes from left to right
3.Right boundary: from the rightmost leaf up to root (excluding leaf, added in reverse)
Java Solution
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        // Root is always part of boundary (unless it's also a leaf, handled below)
        if (!isLeaf(root)) {
            result.add(root.val);
        }
        
        // 1. Left boundary (excluding root and leaves)
        leftBoundary(root.left, result);
        
        // 2. All leaves (left to right)
        leaves(root, result);
        
        // 3. Right boundary (excluding root and leaves, added in reverse)
        rightBoundary(root.right, result);
        
        return result;
    }
    
    // Check if node is a leaf
    private boolean isLeaf(TreeNode node) {
        return node != null && node.left == null && node.right == null;
    }
    
    // Collect left boundary: prefer left child, fallback to right
    private void leftBoundary(TreeNode node, List<Integer> result) {
        if (node == null || isLeaf(node)) return;
        
        result.add(node.val);
        
        // Prefer left child; if no left, take right (still on boundary)
        if (node.left != null) {
            leftBoundary(node.left, result);
        } else {
            leftBoundary(node.right, result);
        }
    }
    
    // Collect all leaves in left-to-right order
    private void leaves(TreeNode node, List<Integer> result) {
        if (node == null) return;
        
        if (isLeaf(node)) {
            result.add(node.val);
            return;
        }
        
        leaves(node.left, result);
        leaves(node.right, result);
    }
    
    // Collect right boundary in reverse: prefer right child, fallback to left
    // Use post-order to naturally get reverse order
    private void rightBoundary(TreeNode node, List<Integer> result) {
        if (node == null || isLeaf(node)) return;
        
        // Post-order: traverse first, add later (gives reverse)
        if (node.right != null) {
            rightBoundary(node.right, result);
        } else {
            rightBoundary(node.left, result);
        }
        
        result.add(node.val);
    }
}
How it works
PartTraversalRuleWhen to stopRootDirect addAlways first—Left boundaryPre-orderPrefer left, fallback rightStop at leafLeavesIn-order (full traversal)Add when left == null && right == null—Right boundaryPost-orderPrefer right, fallback leftStop at leaf, add after recursion for reverse
Example Walkthrough
        1
       / \
      2   7
     / \   \
    3   4   8
       / \
      5   6
- Root: 1
- Left boundary: 2 (left of 1), then 3 is leaf so stop. Result: [2]
- Leaves (LTR): 3, 5, 6, 8. Result: [3, 5, 6, 8]
- Right boundary (reverse): 7 (right of 1), then 8 is leaf so stop. Traversed 7 → 8, added in post-order: 7. Result: [7]
Final: [1, 2, 3, 5, 6, 8, 7]
Edge Cases
CaseHandlingSingle node (root is leaf)Just return [root.val] — root is also a leafNo left childLeft boundary is emptyNo right childRight boundary is emptySkewed treeFollows the single path, leaves are just the bottom node
Complexity
TimeO(n) — each node visited at most onceSpaceO(h) recursion stack, O(n) for result list

