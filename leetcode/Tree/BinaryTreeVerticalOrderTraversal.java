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

Do we have DFS solution ?
Yes, LeetCode 314 can be solved with a DFS approach. The key is to record both the column and row (depth) of each node during traversal, then sort them to achieve the required order.
Here is a Java solution using DFS:
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
    public List<List<Integer>> verticalOrder(TreeNode root) {
        // Map to store column index as key, and a list of (row, value) pairs as value
        Map<Integer, List<int[]>> columnMap = new HashMap<>();
        
        // Perform DFS to traverse the tree and record node positions
        dfs(root, 0, 0, columnMap);
        
        // Prepare the result list
        List<List<Integer>> result = new ArrayList<>();
        
        // Sort the columns from leftmost to rightmost
        List<Integer> sortedColumns = new ArrayList<>(columnMap.keySet());
        Collections.sort(sortedColumns);
        
        // For each column, sort the nodes by row (depth), then by natural visitation order for same row
        for (int col : sortedColumns) {
            List<int[]> nodesInColumn = columnMap.get(col);
            // Sort by row (depth)
            Collections.sort(nodesInColumn, (a, b) -> a[0] - b[0]);
            
            // Extract the values in the sorted order
            List<Integer> values = new ArrayList<>();
            for (int[] node : nodesInColumn) {
                values.add(node[1]);
            }
            result.add(values);
        }
        
        return result;
    }
    
    private void dfs(TreeNode node, int row, int col, Map<Integer, List<int[]>> columnMap) {
        if (node == null) {
            return;
        }
        
        // If the column doesn't exist in the map, create a new list for it
        columnMap.computeIfAbsent(col, k -> new ArrayList<>());
        // Add the current node's row and value to its column list
        columnMap.get(col).add(new int[]{row, node.val});
        
        // Traverse left: column decreases, row increases
        dfs(node.left, row + 1, col - 1, columnMap);
        // Traverse right: column increases, row increases
        dfs(node.right, row + 1, col + 1, columnMap);
    }
}

🔍 How the DFS Solution Works
The core idea is to track both the column (horizontal distance from root) and row (depth) for each node. The root starts at (0, 0). Moving to a left child changes the position to (row+1, col-1), and to a right child to (row+1, col+1).
After traversal, the solution processes the collected data in two sorting steps:
1.Sort columns from leftmost to rightmost.
2.Within each column, sort nodes by their row (depth) to get top-to-bottom order.
⚖️ DFS vs. BFS for This Problem
While a DFS solution works, a BFS (level-order) approach is generally more efficient and straightforward for this specific problem.
FeatureDFS ApproachBFS ApproachSorting Needed✅ Sort by column, then by row✅ Sort only by columnNatural OrderDoes not naturally visit nodes top-to-bottom, left-to-rightNaturally visits nodes in the required orderTime ComplexityO(n log n) due to sortingO(n) with efficient range tracking
The BFS approach is more efficient because it uses the natural level-order traversal to maintain the top-to-bottom, left-to-right order, eliminating the need to sort nodes by their depth.
I hope this clarifies the DFS approach for LeetCode 314. Would you like me to explain the BFS solution as well?

If using TreeMap for DFS solution, how it looks like ?
Here's the LeetCode 314 DFS solution using TreeMap to automatically handle column sorting:
🎯 DFS Solution with TreeMap
class Solution {
    public List<List<Integer>> verticalOrder(TreeNode root) {
        if (root == null) return new ArrayList<>();
        
        // TreeMap: column -> List of [row, value] pairs
        // Automatically sorts columns from left to right
        Map<Integer, List<int[]>> map = new TreeMap<>();
        
        // DFS traversal
        dfs(root, 0, 0, map);
        
        // Build result
        List<List<Integer>> result = new ArrayList<>();
        
        // Process columns in sorted order (thanks to TreeMap)
        for (List<int[]> columnNodes : map.values()) {
            // Sort nodes within this column by row (depth)
            Collections.sort(columnNodes, (a, b) -> a[0] - b[0]);
            
            // Extract just the values in sorted order
            List<Integer> values = new ArrayList<>();
            for (int[] node : columnNodes) {
                values.add(node[1]);
            }
            result.add(values);
        }
        
        return result;
    }
    
    private void dfs(TreeNode node, int row, int col, Map<Integer, List<int[]>> map) {
        if (node == null) return;
        
        // Add node to the appropriate column with its row information
        map.computeIfAbsent(col, k -> new ArrayList<>())
           .add(new int[]{row, node.val});
        
        // Traverse children
        dfs(node.left, row + 1, col - 1, map);
        dfs(node.right, row + 1, col + 1, map);
    }
}
💡 Alternative: Nested TreeMap Approach (The only difference of this solution between L987.Vertical Order Traversal of a Binary Tree (Ref.L103,L199,L314) DFS solution is NO Collections.sort(values))
class Solution {
    public List<List<Integer>> verticalOrder(TreeNode root) {
        if (root == null) return new ArrayList<>();
        
        // TreeMap: column -> TreeMap: row -> List of values
        // Both levels automatically sorted!
        Map<Integer, TreeMap<Integer, List<Integer>>> map = new TreeMap<>();
        
        dfs(root, 0, 0, map);
        
        // Build result - much cleaner!
        List<List<Integer>> result = new ArrayList<>();
        for (TreeMap<Integer, List<Integer>> column : map.values()) {
            List<Integer> columnValues = new ArrayList<>();
            for (List<Integer> values : column.values()) {
                columnValues.addAll(values);
            }
            result.add(columnValues);
        }
        
        return result;
    }
    
    private void dfs(TreeNode node, int row, int col, 
                    Map<Integer, TreeMap<Integer, List<Integer>>> map) {
        if (node == null) return;
        
        map.computeIfAbsent(col, k -> new TreeMap<>())
           .computeIfAbsent(row, k -> new ArrayList<>())
           .add(node.val);
        
        dfs(node.left, row + 1, col - 1, map);
        dfs(node.right, row + 1, col + 1, map);
    }
}
🔍 Key Differences from LeetCode 987
LeetCode 314 (This Problem):
// No value sorting needed within same (row, col)
// Just maintain DFS visitation order
map.get(col).get(row).add(node.val);  // Order matters!
LeetCode 987 (Different Problem):
// Need to sort values within same (row, col)
Collections.sort(values);  // Extra sorting step
🧠 Why DFS Works for 314 (Unlike Common Belief)
Many think DFS doesn't work for 314, but it does work with proper row tracking:
The Trick:
- Track both column AND row during DFS
- Sort columns left→right (TreeMap handles this)
- Sort nodes within each column by row top→bottom
- No value sorting - maintain DFS order for same row
Example: [3,9,8,4,0,1,7]
Tree:
       3
      / \
     9   8
    / \ / \
   4  0 1  7

DFS Order (pre-order): [3, 9, 4, 0, 8, 1, 7]
But with row tracking, we reconstruct correct vertical order!
🚀 Most Elegant TreeMap Solution
class Solution {
    public List<List<Integer>> verticalOrder(TreeNode root) {
        if (root == null) return new ArrayList<>();
        
        // column -> row -> values (all auto-sorted)
        Map<Integer, TreeMap<Integer, List<Integer>>> map = new TreeMap<>();
        
        dfs(root, 0, 0, map);
        
        // Build result - very clean!
        List<List<Integer>> result = new ArrayList<>();
        for (TreeMap<Integer, List<Integer>> column : map.values()) {
            List<Integer> columnValues = new ArrayList<>();
            // Rows are automatically sorted by TreeMap!
            for (List<Integer> values : column.values()) {
                columnValues.addAll(values);
            }
            result.add(columnValues);
        }
        return result;
    }
    
    private void dfs(TreeNode node, int row, int col, 
                    Map<Integer, TreeMap<Integer, List<Integer>>> map) {
        if (node == null) return;
        
        map.computeIfAbsent(col, k -> new TreeMap<>())
           .computeIfAbsent(row, k -> new ArrayList<>())
           .add(node.val);
        
        dfs(node.left, row + 1, col - 1, map);
        dfs(node.right, row + 1, col + 1, map);
    }
}
📊 Complexity Analysis
Time Complexity: O(n log n)
- DFS Traversal: O(n)
- TreeMap Operations: O(n log C) where C = number of columns
- Result Construction: O(n) - just flattening
Space Complexity: O(n)
- Recursion Stack: O(h) where h = tree height
- TreeMap Structures: O(n) total storage
- Result: O(n)
🎯 When to Use DFS vs BFS for 314
Use BFS When:
- You want optimal O(n) time
- Simpler code
- Natural level-order preservation
Use DFS When:
- You're practicing DFS techniques
- Tree is very deep but narrow (better stack usage)
- You want to understand the coordinate tracking concept
💡 Key Insight
The TreeMap DFS solution works because:
1.TreeMap sorts columns automatically
2.Inner TreeMap sorts rows automatically
3.We preserve DFS order for nodes at same (row,col) - which is acceptable for 314!
4.No value sorting needed - unlike problem 987
While BFS is generally preferred for LeetCode 314, this DFS + TreeMap solution demonstrates an elegant approach that automatically handles the sorting requirements!


Refer to
L987.Vertical Order Traversal of a Binary Tree (Ref.L103,L199,L314)
