https://leetcode.ca/all/314.html
Given a binary tree, return the vertical order traversal of its nodes' values. (ie, from top to bottom, column by column).
If two nodes are in the same row and column, the order should be from left to right.
Examples 1:
Input: [3,9,20,null,null,15,7]

   3
  /\
 /  \
 9  20
    /\
   /  \
  15   7

Output:

[
  [9],
  [3,15],
  [20],
  [7]
]

Examples 2:
Input: [3,9,8,4,0,1,7]

     3
    /\
   /  \
   9   8
  /\  /\
 /  \/  \
 4  01   7

Output:

[
  [4],
  [9],
  [3,0,1],
  [8],
  [7]
]

Examples 3:
Input: [3,9,8,4,0,1,7,null,null,null,2,5] (0's right child is 2 and 1's left child is 5)

     3
    /\
   /  \
   9   8
  /\  /\
 /  \/  \
 4  01   7
    /\
   /  \
   5   2

Output:

[
  [4],
  [9,5],
  [3,0,1],
  [8,2],
  [7]
]

--------------------------------------------------------------------------------
Attempt 1: 2024-06-16
Solution 1: BFS + Hash Table (10 min)
import java.util.*;

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
    // Definition of Pair class to hold a TreeNode and an Integer value together
    private static class Pair {
        TreeNode node;
        Integer value;

        public Pair(TreeNode node, Integer value) {
            this.node = node;
            this.value = value;
        }

        public TreeNode getKey() {
            return node;
        }

        public Integer getValue() {
            return value;
        }
    }
  
    public List<List<Integer>> verticalOrder(TreeNode root) {
        // Initialize result list
        List<List<Integer>> result = new ArrayList<>();
        // Early return if root is null
        if (root == null) {
            return result;
        }

        // Create a deque to perform a level-order traversal
        Deque<Pair> queue = new ArrayDeque<>();
        // Offering the root node with column value 0
        queue.offer(new Pair(root, 0));
        // TreeMap to hold nodes values grouped by their column number
        TreeMap<Integer, List<Integer>> columnMap = new TreeMap<>();
      
        // While there are nodes in the queue, process each level
        while (!queue.isEmpty()) {
            Pair currentPair = queue.pollFirst();
            TreeNode currentNode = currentPair.getKey();
            int column = currentPair.getValue();
            // Add the current node's value to the corresponding column list
            columnMap.computeIfAbsent(column, k -> new ArrayList<>()).add(currentNode.val);
            // Offer the left child with column - 1 if it exists
            if (currentNode.left != null) {
                queue.offer(new Pair(currentNode.left, column - 1));
            }
            // Offer the right child with column + 1 if it exists
            if (currentNode.right != null) {
                queue.offer(new Pair(currentNode.right, column + 1));
            }
        }
        // Add each column's list of nodes to result list and return it
        result.addAll(columnMap.values());
        return result;
    }
}

Time Complexity: O(NlogN), sicne the order should be from left to right, we have to sort, so require NlogN
Space Complexity: O(N)

Refer to
https://www.cnblogs.com/grandyang/p/5278930.html
这道题让我们竖直遍历二叉树，并把每一列存入一个二维数组，看题目中给的第一个例子，3和 15 属于同一列，3在前，第二个例子中，3,5,2 在同一列，3在前，5和2紧随其后，那么隐约的可以感觉到好像是一种层序遍历的前后顺序，如何来确定列的顺序呢，这里可以把根节点给个序号0，然后开始层序遍历，凡是左子节点则序号减1，右子节点序号加1，这样可以通过序号来把相同列的节点值放到一起，用一个 TreeMap 来建立序号和其对应的节点值的映射，用 TreeMap 的另一个好处是其自动排序功能可以让列从左到右，由于层序遍历需要用到 queue，此时 queue 里不能只存节点，而是要存序号和节点组成的 pair 对儿，这样每次取出就可以操作序号，而且排入队中的节点也赋上其正确的序号，代码如下：
class Solution {
public:
    vector<vector<int>> verticalOrder(TreeNode* root) {
        vector<vector<int>> res;
        if (!root) return res;
        map<int, vector<int>> m;
        queue<pair<int, TreeNode*>> q;
        q.push({0, root});
        while (!q.empty()) {
            auto a = q.front(); q.pop();
            m[a.first].push_back(a.second->val);
            if (a.second->left) q.push({a.first - 1, a.second->left});
            if (a.second->right) q.push({a.first + 1, a.second->right});
        }
        for (auto a : m) {
            res.push_back(a.second);
        }
        return res;
    }
};

Refer to Deepseek
For LeetCode 314 "Binary Tree Vertical Order Traversal", this is the simpler version of the vertical traversal problem without the value sorting requirement. Here's the Java solution:
🎯 BFS Solution (Recommended)
class Solution {
    public List<List<Integer>> verticalOrder(TreeNode root) {
        if (root == null) return new ArrayList<>();
        
        // Map: column -> list of values in that column
        Map<Integer, List<Integer>> map = new HashMap<>();
        
        // We also track min/max columns to avoid sorting at the end
        int minCol = 0, maxCol = 0;
        
        // Queue stores (node, column)
        Queue<Pair<TreeNode, Integer>> queue = new LinkedList<>();
        queue.offer(new Pair<>(root, 0));
        
        while (!queue.isEmpty()) {
            Pair<TreeNode, Integer> pair = queue.poll();
            TreeNode node = pair.getKey();
            int col = pair.getValue();
            
            // Add node value to the appropriate column
            map.computeIfAbsent(col, k -> new ArrayList<>()).add(node.val);
            
            // Update min/max columns
            minCol = Math.min(minCol, col);
            maxCol = Math.max(maxCol, col);
            
            // Process children
            if (node.left != null) {
                queue.offer(new Pair<>(node.left, col - 1));
            }
            if (node.right != null) {
                queue.offer(new Pair<>(node.right, col + 1));
            }
        }
        
        // Build result from minCol to maxCol (left to right)
        List<List<Integer>> result = new ArrayList<>();
        for (int col = minCol; col <= maxCol; col++) {
            result.add(map.get(col));
        }
        
        return result;
    }
}
🔍 Key Differences from LeetCode 987
AspectLeetCode 314LeetCode 987SortingNo sorting neededSort by value for same positionOrderMaintain BFS orderSort by row then valueComplexityO(n)O(n log n)ApproachBFS onlyBFS/DFS with sorting
💡 Alternative with TreeMap
class Solution {
    public List<List<Integer>> verticalOrder(TreeNode root) {
        if (root == null) return new ArrayList<>();
        
        // TreeMap automatically sorts columns
        Map<Integer, List<Integer>> map = new TreeMap<>();
        
        Queue<Pair<TreeNode, Integer>> queue = new LinkedList<>();
        queue.offer(new Pair<>(root, 0));
        
        while (!queue.isEmpty()) {
            Pair<TreeNode, Integer> pair = queue.poll();
            TreeNode node = pair.getKey();
            int col = pair.getValue();
            
            map.computeIfAbsent(col, k -> new ArrayList<>()).add(node.val);
            
            if (node.left != null) {
                queue.offer(new Pair<>(node.left, col - 1));
            }
            if (node.right != null) {
                queue.offer(new Pair<>(node.right, col + 1));
            }
        }
        
        return new ArrayList<>(map.values());
    }
}
🧠 Step-by-Step Example
Input: [3,9,8,4,0,1,7]
Tree:
       3
      / \
     9   8
    / \ / \
   4  0 1  7

BFS Order: [3, 9, 8, 4, 0, 1, 7]
Column Assignment:
3: col=0
9: col=-1, 8: col=1
4: col=-2, 0: col=0, 1: col=0, 7: col=2
Result:
Column -2: [4]
Column -1: [9]  
Column  0: [3, 0, 1]  // Maintains BFS order!
Column  1: [8]
Column  2: [7]
🚀 Optimized with Min/Max Tracking
The first solution with min/max tracking is more efficient:
class Solution {
    public List<List<Integer>> verticalOrder(TreeNode root) {
        if (root == null) return new ArrayList<>();
        
        Map<Integer, List<Integer>> map = new HashMap<>();
        int minCol = 0, maxCol = 0;
        
        Queue<Pair<TreeNode, Integer>> queue = new LinkedList<>();
        queue.offer(new Pair<>(root, 0));
        
        while (!queue.isEmpty()) {
            Pair<TreeNode, Integer> pair = queue.poll();
            TreeNode node = pair.getKey();
            int col = pair.getValue();
            
            map.computeIfAbsent(col, k -> new ArrayList<>()).add(node.val);
            minCol = Math.min(minCol, col);
            maxCol = Math.max(maxCol, col);
            
            if (node.left != null) queue.offer(new Pair<>(node.left, col - 1));
            if (node.right != null) queue.offer(new Pair<>(node.right, col + 1));
        }
        
        // Build result efficiently without sorting
        List<List<Integer>> result = new ArrayList<>();
        for (int col = minCol; col <= maxCol; col++) {
            result.add(map.get(col));
        }
        return result;
    }
}
💡 Why BFS is Required for LeetCode 314
Problem Requirement:
"If two nodes are in the same row and column, the order should be from left to right."
This means we need to maintain the level order (BFS) sequence, not sort by value!
DFS Would Be Wrong:
// DFS might produce: [3, 1, 0] instead of [3, 0, 1]
// Because traversal order affects the sequence
📊 Complexity Analysis
Time Complexity: O(n)
- BFS Traversal: O(n) - each node processed once
- HashMap Operations: O(1) average case
- Result Construction: O(n) - building the output list
Space Complexity: O(n)
- Queue: O(n) in worst case
- HashMap: O(n) storing all values
- Result: O(n) output storage
🎯 Key Insights
1.BFS Essential: Maintains the left-to-right order within columns
2.No Sorting Needed: Unlike LeetCode 987, we preserve traversal order
3.Column Tracking: Use HashMap + min/max for O(n) solution
4.Efficient Construction: Build result by iterating from min to max column
💡 Edge Cases
// Empty tree
verticalOrder(null) → []

// Single node
verticalOrder([1]) → [[1]]

// Left-skewed tree
verticalOrder([1,2,null,3,null,4,null]) → [[4],[3],[2],[1]]

// Right-skewed tree  
verticalOrder([1,null,2,null,3,null,4]) → [[1],[2],[3],[4]]
The BFS solution with column tracking is optimal for LeetCode 314 - it achieves O(n) time and O(n) space while maintaining the required left-to-right order!



Refer to
L987.Vertical Order Traversal of a Binary Tree (Ref.L103,L199,L314)
