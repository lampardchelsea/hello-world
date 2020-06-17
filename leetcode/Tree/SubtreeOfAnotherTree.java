/**
 Refer to
 https://leetcode.com/problems/subtree-of-another-tree/
 Given two non-empty binary trees s and t, check whether tree t has exactly the same 
 structure and node values with a subtree of s. A subtree of s is a tree consists of 
 a node in s and all of this node's descendants. The tree s could also be considered 
 as a subtree of itself.

Example 1:
Given tree s:

     3
    / \
   4   5
  / \
 1   2
Given tree t:
   4 
  / \
 1   2
Return true, because t has the same structure and node values with a subtree of s.

Example 2:
Given tree s:

     3
    / \
   4   5
  / \
 1   2
    /
   0
Given tree t:
   4
  / \
 1   2
Return false.
*/
// Wrong solution: looks like should not find the same value node in S ?
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
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if(s == null) {
            return false;
        }
        TreeNode node = findTRootInS(s, t.val); // -> logic is wrong here
        if(node != null) {
            return compare(node, t);
        }
        return false;
    }
    
    private TreeNode findTRootInS(TreeNode sRoot, int tRootVal) {
        if(sRoot == null) {
            return null;
        }
        if(sRoot.val == tRootVal) {
            return sRoot;
        } else {
            if(sRoot.val > tRootVal) {
                sRoot = findTRootInS(sRoot.left, tRootVal);
            } else {
                sRoot = findTRootInS(sRoot.right, tRootVal);
            }
        }
        return sRoot;
    }
    
    private boolean compare(TreeNode a, TreeNode b) {
        if(a == null || b == null) {
            return a == b;
        }
        return a.val == b.val 
            && compare(a.left, b.left) 
            && compare(a.right, b.right);
    }
}

// Correct Solution:
// Refer to
// https://leetcode.com/problems/subtree-of-another-tree/discuss/102724/Java-Solution-tree-traversal
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
// Refer to
// 700.Search in a Binary Search Tree and 100. Same Tree two problems
class Solution {
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if(s == null && t == null) {
            return true;
        }
        if(s == null || t == null) {
            return false;
        }
        if(s.val == t.val && compare(s.left, t.left) && compare(s.right, t.right)) {
            return true;
        }
        return isSubtree(s.left, t) || isSubtree(s.right, t);
    }
    
    private boolean compare(TreeNode a, TreeNode b) {
        if(a == null || b == null) {
            return a == b;
        }
        return a.val == b.val 
            && compare(a.left, b.left) 
            && compare(a.right, b.right);
    }
}

// Re-work
// Solution 1: Recursive
// Refer to
// https://leetcode.com/problems/subtree-of-another-tree/discuss/102724/Java-Solution-tree-traversal
// https://leetcode.com/problems/subtree-of-another-tree/discuss/102724/Java-Solution-tree-traversal/153500
/**
The question is exactly similar to the Leetcode 100 Same Tree
Solution for Leetcode 100: https://leetcode.com/problems/same-tree/discuss/148340/CPP-Easy-to-Understand
Also Check Leetcode 101 [Symmetric Tree]https://leetcode.com/problems/symmetric-tree/description/)
Leetcode 101 eh? :P
Okay so now you will be absolutely comfortable with this question. It just requires you to 
1.Start with a node of tree s (lets call this s-node)
2.Compare the trees forming with root s-node and root t
3.If the trees match(leetcode 100 logic) then return true
4.Else go to step one and check for s->left || s->right
class Solution {
public:
    bool isSubtree(TreeNode* s, TreeNode* t) {
        if(!s) return false;
        return isSameTree(s,t) || isSubtree(s->left,t) || isSubtree(s->right,t);
    }
    
    //Leetcode 100
    bool isSameTree(TreeNode* p, TreeNode* q) {
        if(p==NULL && q==NULL)
            return true;
        if(p==NULL || q==NULL)
            return false;
        if(p->val == q->val)
            return isSameTree(p->left,q->left) && isSameTree(p->right,q->right);
        else
            return false;
    }
    
};
*/
// https://leetcode.com/problems/subtree-of-another-tree/discuss/102724/Java-Solution-tree-traversal/247836
/**
 If assum m is the number of nodes in the 1st tree and n is the number of nodes in the 2nd tree, then
 Time complexity: O(m*n), worst case: for each node in the 1st tree, we need to check if isSame(Node s, Node t). 
 Total m nodes, isSame(...) takes O(n) worst case
 Space complexity: O(height of 1str tree)(Or you can say: O(m) for worst case, O(logm) for average case)
*/
class Solution {
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if(s == null) {
            return false;
        }
        if(isSame(s, t)) {
            return true;
        }
        return isSubtree(s.left, t) || isSubtree(s.right, t);
    }
    
    private boolean isSame(TreeNode s, TreeNode t) {
        if(s == null && t == null) {
            return true;
        }
        if(s == null || t == null) {
            return false;
        }
        if(s.val != t.val) {
            return false;
        }
        return isSame(s.left, t.left) && isSame(s.right, t.right);
    }
}


// Solution 2: Iterative
// https://leetcode.com/problems/subtree-of-another-tree/discuss/102724/Java-Solution-tree-traversal/106046
class Solution {
    public boolean isSubtree(TreeNode s, TreeNode t) {
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.offer(s);
        while(!q.isEmpty()) {
            TreeNode node = q.poll();
            if(isSameTree(node, t)) {
                return true;
            }
            if(node.left != null) {
                q.offer(node.left);
            }
            if(node.right != null) {
                q.offer(node.right);
            }
        }
        return false;
    }
    
    private boolean isSameTree(TreeNode node, TreeNode t) {
        if(node == null && t == null) {
            return true;
        }
        if(node == null || t == null) {
            return false;
        }
        if(node.val != t.val) {
            return false;
        }
        return isSameTree(node.left, t.left) && isSameTree(node.right, t.right);
    }
}



