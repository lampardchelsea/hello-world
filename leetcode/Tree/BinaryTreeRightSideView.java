
https://leetcode.com/problems/binary-tree-right-side-view/
Given the root of a binary tree, imagine yourself standing on the right side of it, return the values of the nodes you can see ordered from top to bottom.

Example 1:


Input: root = [1,2,3,null,5,null,4]
Output: [1,3,4]

Example 2:
Input: root = [1,null,3]
Output: [1,3]

Example 3:
Input: root = []
Output: []

Constraints:
- The number of nodes in the tree is in the range [0, 100].
- -100 <= Node.val <= 100
--------------------------------------------------------------------------------
Attempt 1: 2022-12-04
Solution 1:  Recursive traversal as DFS (10 min)
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
    public List<Integer> rightSideView(TreeNode root) { 
        List<Integer> result = new ArrayList<Integer>(); 
        helper(result, root, 0); 
        return result; 
    } 
    private void helper(List<Integer> result, TreeNode root, int level) { 
        if(root == null) { 
            return; 
        } 
        if(result.size() == level) { 
            result.add(root.val); 
        } 
        helper(result, root.right, level + 1); 
        helper(result, root.left, level + 1); 
    } 
}

Time Complexity : O(N) 
Space Complexity: O(N)

Refer to
https://leetcode.com/problems/binary-tree-right-side-view/solutions/56012/my-simple-accepted-solution-java/comments/57608
Add 3 points:
(1) the traverse of the tree is NOT standard pre-order traverse. It checks the RIGHT node first and then the LEFT
(2) the line to check currDepth == result.size() makes sure the first element of that level will be added to the result list
(3) if reverse the visit order, that is first LEFT and then RIGHT, it will return the left view of the tree.

Solution 2:  Level order traversal as BFS (10 min)
Style 1: From right -> left
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
    public List<Integer> rightSideView(TreeNode root) { 
        List<Integer> result = new ArrayList<Integer>(); 
        if(root == null) { 
            return result; 
        } 
        Queue<TreeNode> q = new LinkedList<TreeNode>(); 
        q.offer(root); 
        while(!q.isEmpty()) { 
            // Level order traversal 
            int size = q.size(); 
            for(int i = 0; i < size; i++) { 
                TreeNode node = q.poll(); 
                // right -> left 
                if(i == 0) { 
                    result.add(node.val); 
                } 
                // First add right node of next level to queue, then left 
                if(node.right != null) { 
                    q.offer(node.right); 
                } 
                if(node.left != null) { 
                    q.offer(node.left); 
                } 
            } 
        } 
        return result; 
    } 
}

Time Complexity : O(N)  
Space Complexity: O(N)

Style 2: From left -> right
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
    public List<Integer> rightSideView(TreeNode root) { 
        List<Integer> result = new ArrayList<Integer>(); 
        if(root == null) { 
            return result; 
        } 
        Queue<TreeNode> q = new LinkedList<TreeNode>(); 
        q.offer(root); 
        while(!q.isEmpty()) { 
            // Level order traversal 
            int size = q.size(); 
            for(int i = 0; i < size; i++) { 
                TreeNode node = q.poll(); 
                // left -> right 
                if(i == size - 1) { 
                    result.add(node.val); 
                } 
                // First add left node of next level to queue, then right 
                if(node.left != null) { 
                    q.offer(node.left); 
                } 
                if(node.right != null) { 
                    q.offer(node.right); 
                } 
            } 
        } 
        return result; 
    } 
}

Time Complexity : O(N)  
Space Complexity: O(N)

Refer to
https://leetcode.com/problems/binary-tree-right-side-view/solutions/56012/my-simple-accepted-solution-java/comments/239974
But I think BFS way is much more intuitive. Do the level order traversal, and add the last node on every layer.
class Solution { 
    public List<Integer> rightSideView(TreeNode root) { 
        if (root == null) 
            return new ArrayList(); 
        Queue<TreeNode> queue = new LinkedList(); 
        queue.offer(root); 
        List<Integer> res = new ArrayList(); 
         
        while(!queue.isEmpty()){ 
            int size = queue.size(); 
             
            while (size -- > 0){ 
                TreeNode cur = queue.poll(); 
                if (size == 0) 
                    res.add(cur.val); 
                 
                if (cur.left != null) 
                    queue.offer(cur.left); 
                if (cur.right != null) 
                    queue.offer(cur.right); 
            } 
        } 
         
        return res; 
    } 
}


Refer to Deepseek
For LeetCode 199 "Binary Tree Right Side View", here's the Java solution using BFS level-order traversal:
🎯 BFS Solution (Level Order)
class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            
            // Process all nodes at current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                // If this is the last node in current level, add to result
                if (i == levelSize - 1) {
                    result.add(node.val);
                }
                
                // Add children to queue
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
        
        return result;
    }
}
🔍 How the Algorithm Works
Key Insight
At each level, the last node in the BFS queue represents the rightmost node visible from that level.
Example: [1,2,3,null,5,null,4]
Tree:
    1
   / \
  2   3
   \   \
    5   4

BFS Levels:
Level 0: [1]         → Rightmost: 1
Level 1: [2,3]       → Rightmost: 3  
Level 2: [5,4]       → Rightmost: 4

Result: [1, 3, 4]
💡 Alternative: DFS Solution (Right-First)
class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        dfs(root, 0, result);
        return result;
    }
    
    private void dfs(TreeNode node, int depth, List<Integer> result) {
        if (node == null) return;
        
        // If this is the first node we've seen at this depth, add it
        if (depth == result.size()) {
            result.add(node.val);
        }
        
        // Traverse right first, then left
        // This ensures we always get the rightmost node at each depth
        dfs(node.right, depth + 1, result);
        dfs(node.left, depth + 1, result);
    }
}
🧠 Step-by-Step Examples
Example 1: [1,2,3,null,5,null,4]
Tree:
    1
   / \
  2   3
   \   \
    5   4

BFS Execution:
Level 0: Process [1] → last node = 1 → result = [1]
Level 1: Process [2,3] → last node = 3 → result = [1,3]  
Level 2: Process [5,4] → last node = 4 → result = [1,3,4]
Example 2: [1,2,3,4]
Tree:
      1
     / \
    2   3
   /
  4

BFS Execution:  
Level 0: [1] → last = 1 → [1]
Level 1: [2,3] → last = 3 → [1,3]
Level 2: [4] → last = 4 → [1,3,4]

Note: Node 4 is visible even though it's on the left side
because there's no node blocking it from the right view.
🚀 Optimized BFS with Early Termination
class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            TreeNode rightmost = null;
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                rightmost = node;  // Track the current node
                
                // Add children left to right
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            
            // After processing level, the last node is the rightmost
            result.add(rightmost.val);
        }
        
        return result;
    }
}
💡 DFS Solution Explanation
The DFS approach uses right-first traversal:
dfs(node.right, depth + 1, result);  // Right first
dfs(node.left, depth + 1, result);   // Then left
Why this works:
- We visit right child before left child at each node
- The first node we encounter at each depth is the rightmost visible node
- depth == result.size() ensures we only take the first node at each depth
📊 Complexity Analysis
BFS Solution:
- Time Complexity: O(n) - each node processed once
- Space Complexity: O(w) where w = maximum width of tree
DFS Solution:
- Time Complexity: O(n) - each node processed once
- Space Complexity: O(h) where h = height of tree (recursion stack)
🎯 Comparison: BFS vs DFS
AspectBFSDFSIntuitionProcess level by levelRight-first traversalSpaceO(w) - queue sizeO(h) - recursion stackBest ForWide treesDeep treesCodeMore explicitMore concise
💡 Key Insights
1.Rightmost Node: At each level, the last node in BFS order is the rightmost visible node
2.No Blocking: A left node can be visible if no right node exists at that level
3.Level Tracking: Essential for both BFS and DFS approaches
4.Edge Cases: Empty tree, single node, left-skewed trees
🚀 Most Practical Solution
The BFS solution is generally preferred because:
- ✅ More intuitive: Clearly shows level-by-level processing
- ✅ Handles all cases: Works consistently for all tree shapes
- ✅ No recursion: Avoids stack overflow for very deep trees
- ✅ Easy to understand: Direct mapping to problem requirement
The right side view is essentially the last node at each level in a level-order traversal!

Refer to
L103.P8.3.Binary Tree Zigzag Level Order Traversal (Ref.L314,L987)
L314.Binary Tree Vertical Order Traversal (Ref.L987)
L987.Vertical Order Traversal of a Binary Tree (Ref.L103,L199,L314)
