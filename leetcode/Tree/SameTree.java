/**
 * Given two binary trees, write a function to check if they are equal or not.
 * Two binary trees are considered equal if they are structurally identical and the nodes have the same value.
*/


/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p == null && q == null) {
            return true;
        } else if((p == null && q != null) || (p != null && q == null)) {
            return false;
        }
        
        if(p.val == q.val) {
            return (isSameTree(p.left, q.left) && isSameTree(p.right, q.right));
        } else {
            return false;
        }
    }
}

// Re-work
// Refer to
// https://www.cnblogs.com/grandyang/p/4053384.html
// https://leetcode.com/problems/same-tree/solution/
// Solution 1: Pre-order recursive (DFS)
// Refer to
// https://leetcode.com/problems/same-tree/discuss/32687/Five-line-Java-solution-with-recursion/31600
/**
 public boolean isSameTree(TreeNode p, TreeNode q) {
    
    // Equal nullity denotes that this branch is the same (local equality)
    // This is a base case, but also handles being given two empty trees
    if (p == null && q == null) return true;
    
    // Unequal nullity denotes that the trees aren't the same
    // Note that we've already ruled out equal nullity above
    else if (p == null || q == null) return false;
        
    // Both nodes have values; descend iff those values are equal
    // "&&" here allows for any difference to overrule a local equality
    if (p.val == q.val) return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    
    // If we're here, both nodes have values, and they're unequal, so the trees aren't the same
    return false;
}
*/
/**
 判断两棵树是否相同和之前的判断两棵树是否对称都是一样的原理，利用深度优先搜索 DFS 来递归
*/
class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        // p and q are both null
        if(p == null && q == null) {
            return true;
        }
        // one of p and q is null
        if(p == null || q == null) {
            return false;
        }
        if(p.val != q.val) {
            return false;
        }
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}

// Solution 2: Pre-order iterative traverse
// Refer to
// Two queue version
// https://leetcode.com/problems/same-tree/discuss/32684/My-non-recursive-method
// One queue version
// https://leetcode.com/problems/same-tree/discuss/32684/My-non-recursive-method/119184
/**
  这道题还有非递归的解法，因为二叉树的四种遍历(层序，先序，中序，后序)
  均有各自的迭代和递归的写法，这里我们先来看先序的迭代写法，相当于同时
  遍历两个数，然后每个节点都进行比较, 可参见之间那道 Binary Tree Preorder Traversal
*/
class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        Stack<TreeNode> stack_p = new Stack<TreeNode>();
        Stack<TreeNode> stack_q = new Stack<TreeNode>();
        if(p != null) {
            stack_p.push(p);            
        }
        if(q != null) {
            stack_q.push(q);            
        }
        while(!stack_p.isEmpty() && !stack_q.isEmpty()) {
            TreeNode pn = stack_p.pop();
            TreeNode qn = stack_q.pop();
            if(pn.val != qn.val) {
                return false;
            }
            if(pn.right != null) {
                stack_p.push(pn.right);
            }
            if(qn.right != null) {
                stack_q.push(qn.right);
            }
            if(stack_p.size() != stack_q.size()) {
                return false;
            }
            if(pn.left != null) {
                stack_p.push(pn.left);
            }
            if(qn.left != null) {
                stack_q.push(qn.left);
            }
            if(stack_p.size() != stack_q.size()) {
                return false;
            }
        }
        return stack_p.size() == stack_q.size();
    }
}

// Now we conver it from 2 Stack into 1 Stack (Pre-order iterative traverse)
// Refer to
// https://leetcode.com/problems/same-tree/discuss/32684/My-non-recursive-method/119184
class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(p);
        stack.push(q);
        while(!stack.isEmpty()) {
            TreeNode a = stack.pop();
            TreeNode b = stack.pop();
            if(a == null && b == null) {
                continue;
            } else if(a == null || b == null || a.val != b.val) {
                return false;
            }
            stack.push(a.left);
            stack.push(b.left);
            stack.push(a.right);
            stack.push(b.right);
        }
        return true;
    }
}

// Solution 3: In-order iterative traverse 
// Refer to
// https://www.cnblogs.com/grandyang/p/4053384.html
class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        while(p != null || q != null || !stack.isEmpty()) {
            while(p != null || q != null) {
                if((p == null && q != null) || (p != null && q == null) || (p.val != q.val)) {
                    return false;
                }
                stack.push(p);
                stack.push(q);
                p = p.left;
                q = q.left;
            }
            p = stack.pop();
            q = stack.pop();
            p = p.right;
            q = q.right;
        }
        return true;
    }
}

// Solution 4: Level-order iterative traverse
// Refer to
// https://www.cnblogs.com/grandyang/p/4053384.html
/**
 对于层序遍历的迭代写法，其实跟先序遍历的迭代写法非常的类似，只不过把栈换成了队列，
 对应之前那道 Binary Tree Level Order Traversal
*/
class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(p);
        queue.offer(q);
        while(!queue.isEmpty()) {
            TreeNode a = queue.poll();
            TreeNode b = queue.poll();
            if(a == null && b == null) {
                continue;
            } else if(a == null || b == null || a.val != b.val) {
                return false;
            }
            queue.offer(a.left);
            queue.offer(b.left);
            queue.offer(a.right);
            queue.offer(b.right);
        }
        return true;
    }
}













































https://leetcode.com/problems/same-tree/
Given the roots of two binary trees p and q, write a function to check if they are the same or not.
Two binary trees are considered the same if they are structurally identical, and the nodes have the same value.

Example 1:


Input: p = [1,2,3], q = [1,2,3]
Output: true

Example 2:


Input: p = [1,2], q = [1,null,2]
Output: false

Example 3:


Input: p = [1,2,1], q = [1,1,2]
Output: false
 
Constraints:
- The number of nodes in both trees is in the range [0, 100].
- -10^4 <= Node.val <= 10^4
--------------------------------------------------------------------------------
Attempt 1: 2022-11-08
Solution 1:  Divide and Conquer (10min)
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
    public boolean isSameTree(TreeNode p, TreeNode q) { 
        // Base case 
        // Equal nullity denotes that this branch is the same (local equality) 
        // This is a base case, but also handles being given two empty trees 
        if(p == null && q == null) { 
            return true; 
        } 
        // Unequal nullity denotes that the trees aren't the same 
        // Note that we've already ruled out equal nullity above 
        if(p == null || q == null) { 
            return false; 
        } 
        // Both nodes have values; descend iff those values are equal 
        if(p.val != q.val) { 
            return false; 
        } 
        // Divide 
        boolean left = isSameTree(p.left, q.left); 
        boolean right = isSameTree(p.right, q.right); 
        // Conquer 
        return left && right; 
    } 
}

Time Complexity: O(n), where n is number of nodes in the Binary Tree     
Space Complexity: O(n)

Refer to
https://leetcode.com/problems/same-tree/solution/
Approach 1: Recursion
Intuition
The simplest strategy here is to use recursion. Check if p and q nodes are not None, and their values are equal. If all checks are OK, do the same for the child nodes recursively.
/** 
 * Definition for a binary tree node. 
 * public class TreeNode { 
 *     int val; 
 *     TreeNode left; 
 *     TreeNode right; 
 *     TreeNode(int x) { val = x; } 
 * } 
 */ 
class Solution { 
  public boolean isSameTree(TreeNode p, TreeNode q) { 
    // p and q are both null 
    if (p == null && q == null) return true; 
    // one of p and q is null 
    if (q == null || p == null) return false; 
    if (p.val != q.val) return false; 
    return isSameTree(p.right, q.right) && 
            isSameTree(p.left, q.left); 
  } 
}
Complexity Analysis
- Time complexity : O(N), where N is a number of nodes in the tree, since one visits each node exactly once.
- Space complexity : O(N)in the worst case of completely unbalanced tree, to keep a recursion stack.

Solution 2: Iterative preorder traversal with stack (10min)
Note:  Iterative postorder traversal with stack will be similar, the only difference is switch the order between right and left subtree check in while loop, since we don't need list output like result.add(0, node.val) for postorder traversal, so the switch on order is the only change
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
    public boolean isSameTree(TreeNode p, TreeNode q) { 
        if(p == null && q == null) { 
            return true; 
        } 
        if(p == null || q == null) { 
            return false; 
        } 
        Stack<TreeNode> sp = new Stack<TreeNode>(); 
        sp.push(p); 
        Stack<TreeNode> sq = new Stack<TreeNode>(); 
        sq.push(q); 
        while(!sp.isEmpty() && !sq.isEmpty()) { 
            TreeNode np = sp.pop(); 
            TreeNode nq = sq.pop(); 
            if(np.val != nq.val) { 
                return false; 
            } 
            if(np.right != null && nq.right != null) { 
                sp.push(np.right); 
                sq.push(nq.right); 
            } else if(np.right != null || nq.right != null) { 
                return false; 
            } 
            if(np.left != null && nq.left != null) { 
                sp.push(np.left); 
                sq.push(nq.left); 
            } else if(np.left != null || nq.left != null) { 
                return false; 
            } 
        } 
        return true; 
    } 
}

Time Complexity: O(n), where n is number of nodes in the Binary Tree      
Space Complexity: O(n)

Refer to
https://leetcode.com/problems/same-tree/solution/
Approach 2: Iteration
Intuition
Start from the root and then at each iteration pop the current node out of the deque. Then do the same checks as in the approach 1 :
- p and p are not None,
- p.val is equal to q.val,
and if checks are OK, push the child nodes.

Implementation
class Solution { 
  public boolean check(TreeNode p, TreeNode q) { 
    // p and q are null 
    if (p == null && q == null) return true; 
    // one of p and q is null 
    if (q == null || p == null) return false; 
    if (p.val != q.val) return false; 
    return true; 
  } 
  public boolean isSameTree(TreeNode p, TreeNode q) { 
    if (p == null && q == null) return true; 
    if (!check(p, q)) return false; 
    // init deques 
    ArrayDeque<TreeNode> deqP = new ArrayDeque<TreeNode>(); 
    ArrayDeque<TreeNode> deqQ = new ArrayDeque<TreeNode>(); 
    deqP.addLast(p); 
    deqQ.addLast(q); 
    while (!deqP.isEmpty()) { 
      p = deqP.removeFirst(); 
      q = deqQ.removeFirst(); 
      if (!check(p, q)) return false; 
      if (p != null) { 
        // in Java nulls are not allowed in Deque 
        if (!check(p.left, q.left)) return false; 
        if (p.left != null) { 
          deqP.addLast(p.left); 
          deqQ.addLast(q.left); 
        } 
        if (!check(p.right, q.right)) return false; 
        if (p.right != null) { 
          deqP.addLast(p.right); 
          deqQ.addLast(q.right); 
        } 
      } 
    } 
    return true; 
  } 
}
Complexity Analysis
- Time complexity : O(N)since each node is visited exactly once.
- Space complexity : O(N)in the worst case, where the tree is a perfect fully balanced binary tree, since BFS will have to store at least an entire level of the tree in the queue, and the last level has O(N)nodes.

Solution 3: Iterative inorder traversal with stack (10min)
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
    public boolean isSameTree(TreeNode p, TreeNode q) { 
        if(p == null && q == null) { 
            return true; 
        } 
        if(p == null || q == null) { 
            return false; 
        } 
        Stack<TreeNode> sp = new Stack<TreeNode>(); 
        //sp.push(p); 
        Stack<TreeNode> sq = new Stack<TreeNode>(); 
        //sq.push(q); 
        while((p != null || !sp.isEmpty()) && (q != null || !sq.isEmpty())) { 
            while(p != null && q != null) { 
                sp.push(p); 
                p = p.left; 
                sq.push(q); 
                q = q.left; 
            } 
            // If while loop break out because of either p != null and q == null 
            // or p == null and q != null, then mismatch happen, we should return  
            // false before pop out previous stored nodes from stack 
            // Note: if p == null && q == null, we cannot judge easily by direct 
            // return true or false, because that only means two tree traversal 
            // sync up at same position with no more left leaves 
            if((p != null && q == null) || (p == null && q != null)) { 
                return false; 
            } 
            p = sp.pop(); 
            q = sq.pop(); 
            if(p.val != q.val) { 
                return false; 
            } 
            p = p.right; 
            q = q.right; 
            // Also need to check if p or q only one existing here, test out 
            // by input: [1] and [1,null,2] 
            if((p != null && q == null) || (p == null && q != null)) { 
                return false; 
            } 
        } 
        return true; 
    } 
}

Time Complexity: O(n), where n is number of nodes in the Binary Tree      
Space Complexity: O(n)      
    

Refer to
L94.Binary Tree Inorder Traversal (Ref.L98,L230,L144,L145)
