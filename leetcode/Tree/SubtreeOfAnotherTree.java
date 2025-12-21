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








































































https://leetcode.com/problems/subtree-of-another-tree/description/
Given the roots of two binary trees root and subRoot, return true if there is a subtree of root with the same structure and node values of subRoot and false otherwise.
A subtree of a binary tree tree is a tree that consists of a node in tree and all of this node's descendants. The tree tree could also be considered as a subtree of itself.
 
Example 1:

Input: root = [3,4,5,1,2], subRoot = [4,1,2]
Output: true

Example 2:

Input: root = [3,4,5,1,2,null,null,null,null,0], subRoot = [4,1,2]
Output: false
 
Constraints:
- The number of nodes in the root tree is in the range [1, 2000].
- The number of nodes in the subRoot tree is in the range [1, 1000].
- -104 <= root.val <= 10^4
- -104 <= subRoot.val <= 10^4
--------------------------------------------------------------------------------
Attempt 1: 2025-12-16
Solution 1: DFS (10 min)
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
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if(root == null) {
            return false;
        }
        return isSameTree(root, subRoot) || isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    private boolean isSameTree(TreeNode a, TreeNode b) {
        if(a == null || b == null) {
            return a == b;
        }
        return a.val == b.val && isSameTree(a.left, b.left) && isSameTree(a.right, b.right);
    }
}

Time Complexity: O(m * n), In the worst case (e.g., a skewed tree), 
for each of the n nodes in root, we might check against all m nodes of subRoot.
Space Complexity: O(h), The space is determined by the recursion stack, 
which depends on the height h of the main tree root.

Refer to Deepseek
For LeetCode 572 (Subtree of Another Tree), the standard Java solution is a recursive Depth-First Search (DFS) approach. The core idea is to traverse the main tree and, at each node, check if the subtree rooted there is identical to the given subRoot.
📝 Java Solution (Recursive DFS)
Here is the complete, well-commented Java solution:
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
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        // Base case: if the main tree is null, it cannot contain a subtree 
        if (root == null) {
            return false;
        }
      
        // Check: 1. If trees match from the current root, OR
        //        2. If the subtree exists in the left child, OR
        //        3. If the subtree exists in the right child.
        return isSameTree(root, subRoot) 
            || isSubtree(root.left, subRoot)
            || isSubtree(root.right, subRoot);
    }

    // Helper function to check if two trees are identical
    private boolean isSameTree(TreeNode p, TreeNode q) {
        // If either node is null, both must be null for the trees to be identical
        if (p == null || q == null) {
            return p == q;
        }
        // Check if the current nodes have the same value
        // AND their left subtrees are identical
        // AND their right subtrees are identical
        return p.val == q.val 
            && isSameTree(p.left, q.left) 
            && isSameTree(p.right, q.right);
    }
}
🔍 How the Algorithm Works
The solution uses a two-part recursive strategy:
1.isSubtree: This is the main function. It recursively traverses every node in the root tree, treating each node as a potential starting point for the subRoot tree. It checks three possibilities at each step: if the trees match at the current node, or if the subRoot is a subtree of the left child, or if it's a subtree of the right child .
2.isSameTree: This helper function checks if two given trees are structurally identical and have matching node values at every corresponding position. It's called by isSubtree to verify a match at a specific node .
This is visualized in the flowchart below:
flowchart TD
    Start["isSubtree(root, subRoot)"] --> CheckRoot{"root == null?"}
    
    CheckRoot -->|Yes| ReturnFalse["Return false"]
    
    CheckRoot -->|No| CheckCurrent["Check: isSameTree(root, subRoot)"]
    CheckCurrent -->|Trees are identical| ReturnTrue["Return true"]
    
    CheckCurrent -->|Not identical| CheckLeft["Recurse: isSubtree(root.left, subRoot)"]
    CheckLeft -->|Found in left| ReturnTrue
    
    CheckLeft -->|Not in left| CheckRight["Recurse: isSubtree(root.right, subRoot)"]
    CheckRight -->|Found in right| ReturnTrue
    CheckRight -->|Not found anywhere| ReturnFalse
📊 Complexity Analysis
AspectComplexityExplanationTime ComplexityO(m × n)In the worst case (e.g., a skewed tree), for each of the n nodes in root, we might check against all m nodes of subRoot .Space ComplexityO(h)The space is determined by the recursion stack, which depends on the height h of the main tree root .
💡 Alternative Approach: Tree Serialization
For a more advanced solution with better worst-case time complexity, you can serialize both trees into strings and perform substring matching (e.g., using the KMP algorithm). This approach has an O(m + n) time and space complexity but is more complex to implement . The recursive DFS method above is typically preferred for interviews due to its clarity.
✅ Edge Cases to Consider
When testing your solution, make sure it handles these scenarios:
- Empty subRoot: An empty tree is always a subtree. The code above returns false for this case, but LeetCode's constraints state the number of nodes is at least 1 .
- Identical trees: The main tree is considered a subtree of itself .
- subRoot is a leaf node: The algorithm should correctly match a single node.
- Values match but structure differs: The isSameTree function catches this by checking structure.
I hope this detailed explanation helps you understand the solution. Would you like me to walk through a specific example or explain the alternative serialization approach in more detail?

Refer to
L1770.Maximum Score from Performing Multiplication Operations (Ref.L1423)
DFS return logic

