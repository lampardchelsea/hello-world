https://leetcode.com/problems/distribute-coins-in-binary-tree/description/
You are given the root of a binary tree with n nodes where each node in the tree has node.val coins. There are n coins in total throughout the whole tree.
In one move, we may choose two adjacent nodes and move one coin from one node to another. A move may be from parent to child, or from child to parent.
Return the minimum number of moves required to make every node have exactly one coin.
 
Example 1:

Input: root = [3,0,0]
Output: 2
Explanation: From the root of the tree, we move one coin to its left child, and one coin to its right child.

Example 2:

Input: root = [0,3,0]
Output: 3
Explanation: From the left child of the root, we move two coins to the root [taking two moves]. Then, we move one coin from the root of the tree to the right child.
 
Constraints:
- The number of nodes in the tree is n.
- 1 <= n <= 100
- 0 <= Node.val <= n
- The sum of all Node.val is n.
--------------------------------------------------------------------------------
Attempt 1: 2025-12-05
Solution 1: DFS (60 min)
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
    private int moves = 0;
    
    public int distributeCoins(TreeNode root) {
        helper(root);
        return moves;
    }
    
    // Returns the balance of coins for this subtree
    // Positive = excess coins to send up
    // Negative = needs coins from parent
    // Zero = balanced (exactly 1 coin per node)
    private int helper(TreeNode node) {
        if (node == null) {
            return 0;
        }
        
        // Get balance from left and right subtrees
        int leftBalance = helper(node.left);
        int rightBalance = helper(node.right);
        
        // Current node's balance: (node's coins - 1) + children's balances
        int balance = node.val - 1 + leftBalance + rightBalance;
        
        // Add the absolute movements needed for children
        moves += Math.abs(leftBalance) + Math.abs(rightBalance);
        
        return balance;
    }
}

Refer to Deepseek
For LeetCode 979 (Distribute Coins in Binary Tree), the key is to calculate balance = coins - 1 for each subtree and sum the absolute movements needed. Here's the optimal Java solution:
class Solution {
    private int moves = 0;
    
    public int distributeCoins(TreeNode root) {
        dfs(root);
        return moves;
    }
    
    // Returns the balance of coins for this subtree
    // Positive = excess coins to send up
    // Negative = needs coins from parent
    // Zero = balanced (exactly 1 coin per node)
    private int dfs(TreeNode node) {
        if (node == null) {
            return 0;
        }
        
        // Get balance from left and right subtrees
        int leftBalance = dfs(node.left);
        int rightBalance = dfs(node.right);
        
        // Current node's balance: (node's coins - 1) + children's balances
        int balance = node.val - 1 + leftBalance + rightBalance;
        
        // Add the absolute movements needed for children
        moves += Math.abs(leftBalance) + Math.abs(rightBalance);
        
        return balance;
    }
}
🧮 How It Works: Step-by-Step
1. The Balance Concept
Each node needs exactly 1 coin. For a node with node.val coins:
- If node.val > 1: Has excess coins to distribute
- If node.val < 1: Needs coins (actually means 0 coins, needs 1)
- If node.val = 1: Perfectly balanced
Balance formula: node.val - 1
2. Why Sum Absolute Values?
The moves counter tracks physical coin movements:
     Example:
         0 (needs 1 coin)
        / \
       3  0
     (has 2 excess) (needs 1)

1.Left child balance = 3 - 1 = +2 (2 excess coins)
2.Right child balance = 0 - 1 = -1 (needs 1 coin)
3.Root balance = 0 - 1 + 2 + (-1) = 0 (eventually balanced)
Moves calculation:
- Left → Root: Math.abs(2) = 2 moves
- Right → Root: Math.abs(-1) = 1 move
- Total moves = 2 + 1 = 3
3. Visual Example
Initial:           After Distribution:
     2                  1
    / \                / \
   0   0    →→→→      1   1
  / \                / \
 3   0              1   1
DFS Calculation:
1. Node 3 (leaf): excess = 3 - 1 = +2, moves += 0 = 0
2. Node 0 (leaf sibling): excess = 0 - 1 = -1, moves += 0 = 0
3. Left parent (0): excess = 0 + 2 + (-1) - 1 = 0, moves += |2| + |-1| = 3
4. Right child (0): excess = 0 - 1 = -1, moves += 0 = 0
5. Root (2): excess = 2 + 0 + (-1) - 1 = 0, moves += |0| + |-1| = 1

Total moves = 0 + 0 + 3 + 0 + 1 = 4 ✅
🧠 Key Insight
The algorithm counts coin movements across edges:
1.Each edge is counted once: When a child returns its excess to parent (positive or negative), Math.abs(excess) represents coins moving across that child-parent edge.
2.Why it works:
- If a child has excess (+), those coins must move up through the edge
- If a child has deficit (-), coins must move down through the edge
- Either way, |excess| movements occur across that edge
3.Post-order is essential: We need to know children's excess before deciding parent's needs.
📊 Complexity Analysis
MetricValueExplanationTimeO(N)Each node visited onceSpaceO(H)Recursion depth = tree height
Key Insight: We don't need to simulate actual coin transfers. Just track net flow between nodes using post-order traversal.
🔄 Alternative: Return Array for Clarity
class Solution {
    public int distributeCoins(TreeNode root) {
        int[] result = new int[1]; // moves count
        dfs(root, result);
        return result[0];
    }
    
    private int dfs(TreeNode node, int[] moves) {
        if (node == null) return 0;
        
        int left = dfs(node.left, moves);
        int right = dfs(node.right, moves);
        
        moves[0] += Math.abs(left) + Math.abs(right);
        
        return node.val - 1 + left + right;
    }
}
🎯 Why This Approach is Optimal
1.Single Pass: Calculates moves while computing balance
2.No Extra Simulation: Math.abs() captures bidirectional flow
3.Post-order Necessity: Need children's balances before parent
The solution elegantly solves the problem by recognizing that total moves = sum of absolute imbalances at each edge!

Refer to
L968.Binary Tree Cameras (Ref.L979)
