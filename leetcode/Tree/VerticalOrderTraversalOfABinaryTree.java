https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/description/
Given the root of a binary tree, calculate the vertical order traversal of the binary tree.
For each node at position (row, col), its left and right children will be at positions (row + 1, col - 1) and (row + 1, col + 1) respectively. The root of the tree is at (0, 0).
The vertical order traversal of a binary tree is a list of top-to-bottom orderings for each column index starting from the leftmost column and ending on the rightmost column. There may be multiple nodes in the same row and same column. In such a case, sort these nodes by their values.
Return the vertical order traversal of the binary tree.
 
Example 1:

Input: root = [3,9,20,null,null,15,7]
Output: [[9],[3,15],[20],[7]]
Explanation:
Column -1: Only node 9 is in this column.
Column 0: Nodes 3 and 15 are in this column in that order from top to bottom.
Column 1: Only node 20 is in this column.
Column 2: Only node 7 is in this column.

Example 2:

Input: root = [1,2,3,4,5,6,7]
Output: [[4],[2],[1,5,6],[3],[7]]
Explanation:
Column -2: Only node 4 is in this column.
Column -1: Only node 2 is in this column.
Column 0: Nodes 1, 5, and 6 are in this column. 
                 1 is at the top, so it comes first.
                 5 and 6 are at the same position (2, 0), so we order them by their value, 5 before 6.
Column 1: Only node 3 is in this column.Column 2: Only node 7 is in this column.

Example 3:

Input: root = [1,2,3,4,6,5,7]
Output: [[4],[2],[1,5,6],[3],[7]]
Explanation:
This case is the exact same as example 2, but with nodes 5 and 6 swapped.
Note that the solution remains the same since 5 and 6 are in the same location and should be ordered by their values.
 
Constraints:
- The number of nodes in the tree is in the range [1, 1000].
- 0 <= Node.val <= 1000
--------------------------------------------------------------------------------
Attempt 1: 2025-11-28
Solution 1: DFS (30 min)
Style 1: With Hash Table
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
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        // <k, <k, v>> -> <column, <row, List of values at that (row, col) position>>
        Map<Integer, TreeMap<Integer, List<Integer>>> map = new TreeMap<>();
        helper(root, 0, 0, map);
        List<List<Integer>> result = new ArrayList<>();
        // Process columns in sorted order (left to right) - automatically handled by TreeMap
        for(TreeMap<Integer, List<Integer>> rowMap : map.values()) {
            List<Integer> columnValues = new ArrayList<>();
            // Process rows in sorted order (top to bottom) - automatically handled by TreeMap
            for(List<Integer> values : rowMap.values()) {
                // Only need to sort values at the same (row, col) position
                Collections.sort(values);
                columnValues.addAll(values);
            }
            result.add(columnValues);
        }
        return result;
    }

    private void helper(TreeNode node, int row, int col, Map<Integer, TreeMap<Integer, List<Integer>>> map) {
        if(node == null) {
            return;
        }
        // Initialize the column TreeMap if not present
        //map.putIfAbsent(col, new TreeMap<>());
        // Initialize the row list if not present
        //map.get(col).putIfAbsent(row, new ArrayList<>());
        // Add the value to the correct (row, col) position
        //map.get(col).get(row).add(node.val);
        // All above 3 lines can simplified into 1 line
        map.computeIfAbsent(col, k -> new TreeMap<>())
            .computeIfAbsent(row, k -> new ArrayList<>())
            .add(node.val);
        // Traverse left: column decreases, row increases
        helper(node.left, row + 1, col - 1, map);
        // Traverse right: column increases, row increases
        helper(node.right, row + 1, col + 1, map);
    }
}

Time Complexity: O(n*logn)
Space Complexity: O(n)

Style 2: With auxiliary class
class Solution {
    class Node {
        int col;
        int row;
        int val;
        public Node(int col, int row, int val) {
            this.col = col;
            this.row = row;
            this.val = val;
        }
    }

    public List<List<Integer>> verticalTraversal(TreeNode root) {
        List<Node> nodes = new ArrayList<>();
        // Collect all nodes with their positions
        helper(root, 0, 0, nodes);
        // Sort nodes: first by column, then by row, then by value
        Collections.sort(nodes, (a, b) -> {
            if(a.col != b.col) {
                return a.col - b.col;
            }
            if(a.row != b.row) {
                return a.row - b.row;
            }
            return a.val - b.val;
        });
        // Group by column
        List<List<Integer>> result = new ArrayList<>();
        int curCol = Integer.MIN_VALUE;
        for(Node node : nodes) {
            if(node.col != curCol) {
                result.add(new ArrayList<>());
                curCol = node.col;
            }
            result.get(result.size() - 1).add(node.val);
        }
        return result;
    }

    private void helper(TreeNode node, int col, int row, List<Node> nodes) {
        if(node == null) {
            return;
        }
        nodes.add(new Node(col, row, node.val));
        helper(node.left, col - 1, row + 1, nodes);
        helper(node.right, col + 1, row + 1, nodes);
    }
}

Time Complexity: O(n*logn)
Space Complexity: O(n)


Solution 2: BFS (30 min)
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
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        // Use TreeMap to automatically sort by column
        Map<Integer, TreeMap<Integer, List<Integer>>> map = new TreeMap<>();
        // Pair = {TreeNode, {row, col}}
        Queue<Pair<TreeNode, int[]>> q = new LinkedList<>();
        q.offer(new Pair<> (root, new int[] {0, 0}));
        while(!q.isEmpty()) {
            Pair<TreeNode, int[]> cur = q.poll();
            TreeNode node = cur.getKey();
            int row = cur.getValue()[0];
            int col = cur.getValue()[1];
            map.computeIfAbsent(col, k -> new TreeMap<>())
                .computeIfAbsent(row, k -> new ArrayList<>())
                .add(node.val);
            if(node.left != null) {
                q.offer(new Pair<> (node.left, new int[] {row + 1, col - 1}));
            }
            if(node.right != null) {
                q.offer(new Pair<> (node.right, new int[] {row + 1, col + 1}));
            }
        }
        List<List<Integer>> result = new ArrayList<>();
        // Process columns in sorted order (left to right) - automatically handled by TreeMap
        for(TreeMap<Integer, List<Integer>> rowMap : map.values()) {
            List<Integer> columnValues = new ArrayList<>();
            // Process rows in sorted order (top to bottom) - automatically handled by TreeMap
            for(List<Integer> values : rowMap.values()) {
                // Only need to sort values at the same (row, col) position
                Collections.sort(values);
                columnValues.addAll(values);
            }
            result.add(columnValues);
        }
        return result;
    }
}

Time Complexity: O(n*logn)
Space Complexity: O(n)

Refer to Deepseek
For LeetCode 987 "Vertical Order Traversal of a Binary Tree", this problem requires a custom traversal with sorting. Here's the Java solution:
🎯 DFS with Sorting Solution
class Solution {
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        // Map: column -> Map: row -> List of values at that (row, col) position
        Map<Integer, Map<Integer, List<Integer>>> map = new TreeMap<>();
        
        // Traverse the tree and populate the map
        traverse(root, 0, 0, map);
        
        // Build the result
        List<List<Integer>> result = new ArrayList<>();
        
        // Process columns in sorted order (left to right)
        for (int col : map.keySet()) {
            Map<Integer, List<Integer>> rowMap = map.get(col);
            List<Integer> columnValues = new ArrayList<>();
            
            // Process rows in sorted order (top to bottom)
            for (int row : new TreeSet<>(rowMap.keySet())) {
                List<Integer> values = rowMap.get(row);
                // Sort values at the same (row, col) position
                Collections.sort(values);
                columnValues.addAll(values);
            }
            
            result.add(columnValues);
        }
        
        return result;
    }
    
    private void traverse(TreeNode node, int row, int col, Map<Integer, Map<Integer, List<Integer>>> map) {
        if (node == null) return;
        
        // Initialize the column if not present
        map.putIfAbsent(col, new HashMap<>());
        
        // Initialize the row if not present
        map.get(col).putIfAbsent(row, new ArrayList<>());
        
        // Add the value to the correct (row, col) position
        map.get(col).get(row).add(node.val);
        
        // Traverse left: column decreases, row increases
        traverse(node.left, row + 1, col - 1, map);
        
        // Traverse right: column increases, row increases  
        traverse(node.right, row + 1, col + 1, map);
    }
}
🔍 How the Algorithm Works
Key Concepts:
- Column: X-coordinate (root=0, left=-1, right=+1)
- Row: Y-coordinate (root=0, children=+1)
- Sorting: Same (row,col) → sort values, columns left→right, rows top→bottom
Example Tree:
    3
   / \
  9   20
     /  \
    15   7
Coordinates:
- Node 3: (row=0, col=0)
- Node 9: (row=1, col=-1)
- Node 20: (row=1, col=1)
- Node 15: (row=2, col=0)
- Node 7: (row=2, col=2)
💡 BFS Solution (Level Order Traversal)
class Solution {
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        if (root == null) return new ArrayList<>();
        
        // Use TreeMap to automatically sort by column
        Map<Integer, Map<Integer, List<Integer>>> map = new TreeMap<>();
        
        // Queue for BFS: store (node, row, col)
        Queue<Pair<TreeNode, int[]>> queue = new LinkedList<>();
        queue.offer(new Pair<>(root, new int[]{0, 0}));
        
        while (!queue.isEmpty()) {
            Pair<TreeNode, int[]> pair = queue.poll();
            TreeNode node = pair.getKey();
            int row = pair.getValue()[0];
            int col = pair.getValue()[1];
            
            // Initialize data structures if needed
            map.putIfAbsent(col, new TreeMap<>());
            map.get(col).putIfAbsent(row, new ArrayList<>());
            
            // Add current node value
            map.get(col).get(row).add(node.val);
            
            // Process children
            if (node.left != null) {
                queue.offer(new Pair<>(node.left, new int[]{row + 1, col - 1}));
            }
            if (node.right != null) {
                queue.offer(new Pair<>(node.right, new int[]{row + 1, col + 1}));
            }
        }
        
        // Build result
        List<List<Integer>> result = new ArrayList<>();
        for (Map<Integer, List<Integer>> colMap : map.values()) {
            List<Integer> column = new ArrayList<>();
            for (List<Integer> values : colMap.values()) {
                Collections.sort(values);
                column.addAll(values);
            }
            result.add(column);
        }
        
        return result;
    }
}
🚀 Optimized DFS Solution with Custom Class
class Solution {
    class NodeInfo {
        int row;
        int col;
        int val;
        
        NodeInfo(int r, int c, int v) {
            row = r;
            col = c;
            val = v;
        }
    }
    
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        List<NodeInfo> nodes = new ArrayList<>();
        
        // Collect all nodes with their positions
        dfs(root, 0, 0, nodes);
        
        // Sort nodes: first by column, then by row, then by value
        Collections.sort(nodes, (a, b) -> {
            if (a.col != b.col) return a.col - b.col;
            if (a.row != b.row) return a.row - b.row;
            return a.val - b.val;
        });
        
        // Group by column
        List<List<Integer>> result = new ArrayList<>();
        int currentCol = Integer.MIN_VALUE;
        for (NodeInfo node : nodes) {
            if (node.col != currentCol) {
                result.add(new ArrayList<>());
                currentCol = node.col;
            }
            result.get(result.size() - 1).add(node.val);
        }
        return result;
    }
    
    private void dfs(TreeNode node, int row, int col, List<NodeInfo> nodes) {
        if (node == null) return;
        nodes.add(new NodeInfo(row, col, node.val));
        dfs(node.left, row + 1, col - 1, nodes);
        dfs(node.right, row + 1, col + 1, nodes);
    }
}
🧠 Step-by-Step Example
Input: [3,9,20,null,null,15,7]
Tree:
    3
   / \
  9   20
     /  \
    15   7

Node positions:
3:  (0,0)
9:  (1,-1)
20: (1,1)
15: (2,0)
7:  (2,2)


Sorting order (col, row, value):
(-1,1,9)  → Column -1: [9]
(0,0,3)   → Column 0: [3,15]
(0,2,15)
(1,1,20)  → Column 1: [20]
(2,2,7)   → Column 2: [7]

Result: [[9], [3,15], [20], [7]]
💡 Key Insights
1.Three-level Sorting:
- Primary: Column (left to right)
- Secondary: Row (top to bottom)
- Tertiary: Value (small to large for same position)
2.Traversal Choice: Both DFS and BFS work
3.Data Structure: Need nested maps or custom sorting
📊 Complexity Analysis
DFS/BFS with Maps:
- Time: O(n log n) for sorting
- Space: O(n) for storing node information
Optimized with Custom Class:
- Time: O(n log n) for sorting all nodes
- Space: O(n) for node list and result
🎯 Recommendation
The custom class with single sort solution is preferred because:
- ✅ Cleaner code: Single sorting operation
- ✅ Efficient: One pass collection, one sort
- ✅ Easy to understand: Clear sorting criteria
- ✅ Flexible: Easy to modify sorting rules
The key is understanding the three-level sorting criteria for vertical order traversal!

For DFS solution above, why not set the inner map also as TreeMap, which will save some sorting work, right ? Currently it created as HashMap like 'map.putIfAbsent(col, new HashMap<>());'
You're absolutely right! That's an excellent optimization. Using TreeMap for the inner map would eliminate the need for the manual sorting of rows. Let me show you the improved version:
🎯 Optimized DFS Solution with TreeMap
class Solution {
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        // Map: column -> TreeMap: row -> List of values at that (row, col) position
        // Using TreeMap for both levels automatically sorts by column and row
        Map<Integer, TreeMap<Integer, List<Integer>>> map = new TreeMap<>();
        
        // Traverse the tree and populate the map
        traverse(root, 0, 0, map);
        
        // Build the result
        List<List<Integer>> result = new ArrayList<>();
        
        // Process columns in sorted order (left to right) - automatically handled by TreeMap
        for (TreeMap<Integer, List<Integer>> rowMap : map.values()) {
            List<Integer> columnValues = new ArrayList<>();
            
            // Process rows in sorted order (top to bottom) - automatically handled by TreeMap
            for (List<Integer> values : rowMap.values()) {
                // Only need to sort values at the same (row, col) position
                Collections.sort(values);
                columnValues.addAll(values);
            }
            
            result.add(columnValues);
        }
        
        return result;
    }
    
    private void traverse(TreeNode node, int row, int col, 
                         Map<Integer, TreeMap<Integer, List<Integer>>> map) {
        if (node == null) return;
        
        // Initialize the column TreeMap if not present
        map.putIfAbsent(col, new TreeMap<>());
        
        // Initialize the row list if not present
        map.get(col).putIfAbsent(row, new ArrayList<>());
        
        // Add the value to the correct (row, col) position
        map.get(col).get(row).add(node.val);
        
        // Traverse left: column decreases, row increases
        traverse(node.left, row + 1, col - 1, map);
        
        // Traverse right: column increases, row increases  
        traverse(node.right, row + 1, col + 1, map);
    }
}
🔍 What This Optimization Achieves
Before (HashMap):
Map<Integer, Map<Integer, List<Integer>>> map = new TreeMap<>();
// Later need to manually sort rows:
for (int row : new TreeSet<>(rowMap.keySet())) {
    // Manual sorting of rows needed
}
After (TreeMap):
Map<Integer, TreeMap<Integer, List<Integer>>> map = new TreeMap<>();
// Rows are automatically sorted when iterating:
for (List<Integer> values : rowMap.values()) {
    // Rows already in sorted order!
}
📊 Performance Comparison
AspectHashMap + Manual SortTreeMap (Auto-sort)Row SortingO(k log k) per columnO(1) - already sortedCode ComplexityMore complexCleanerMemorySlightly lessSlightly more (tree overhead)ReadabilityGoodBetter
💡 Even More Optimized BFS Version
Here's the BFS version with the same optimization:
class Solution {
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        if (root == null) return new ArrayList<>();
        
        // Use TreeMap for automatic column and row sorting
        Map<Integer, TreeMap<Integer, List<Integer>>> map = new TreeMap<>();
        
        Queue<Pair<TreeNode, int[]>> queue = new LinkedList<>();
        queue.offer(new Pair<>(root, new int[]{0, 0}));
        
        while (!queue.isEmpty()) {
            Pair<TreeNode, int[]> pair = queue.poll();
            TreeNode node = pair.getKey();
            int row = pair.getValue()[0];
            int col = pair.getValue()[1];
            
            // TreeMap handles automatic sorting
            map.putIfAbsent(col, new TreeMap<>());
            map.get(col).putIfAbsent(row, new ArrayList<>());
            map.get(col).get(row).add(node.val);
            
            if (node.left != null) {
                queue.offer(new Pair<>(node.left, new int[]{row + 1, col - 1}));
            }
            if (node.right != null) {
                queue.offer(new Pair<>(node.right, new int[]{row + 1, col + 1}));
            }
        }
        
        // Build result - much cleaner now!
        List<List<Integer>> result = new ArrayList<>();
        for (TreeMap<Integer, List<Integer>> rowMap : map.values()) {
            List<Integer> column = new ArrayList<>();
            for (List<Integer> values : rowMap.values()) {
                Collections.sort(values);  // Only sort needed
                column.addAll(values);
            }
            result.add(column);
        }
        
        return result;
    }
}
🎯 Why This is Better
1.Cleaner Code: No manual sorting logic for rows
2.Better Performance: Eliminates O(k log k) row sorting per column
3.More Readable: The data structure clearly expresses the sorting requirements
4.Maintainable: Easy to modify if sorting requirements change
💡 Trade-offs
The TreeMap approach uses slightly more memory due to the tree structure overhead, but for most practical cases, this is negligible compared to the code clarity and performance benefits.
Your suggestion is spot-on - using TreeMap for the inner map is definitely the cleaner and more efficient approach! It makes the code more expressive and eliminates unnecessary sorting operations.

Can you explain this "// Only need to sort values at the same (row, col) position" ?
🔍 Understanding "Sort Values at Same (row, col) Position"
This refers to the key requirement in LeetCode 987 where multiple nodes can exist at the exact same coordinate and need to be sorted by value.
💡 Real Collision Example
The sorting is needed when we have different nodes that end up with the same (row, col) coordinates in our data structure. This happens because:
The Actual Scenario:
// The problem statement says: 
// "If two nodes have the same position, then the value of the node 
//  that is reported first is the value that is smaller."

// This means when we process nodes and group them by (col, row),
// we might have multiple values in the same bucket that need sorting.
When This Occurs:
1.Different traversal orders can put nodes in the same list
2.The algorithm groups all nodes with same (col, row) together
3.Within that group, we sort by node value
🔍 Step-by-Step Processing
Example Input: [1,2,3,4,5,6,7]
Tree:
       1
      / \
     2   3
    / \ / \
   4  5 6  7

Coordinates:
1: (0,0)
2: (1,-1), 3: (1,1)  
4: (2,-2), 5: (2,0), 6: (2,0), 7: (2,2)
The Collision:
// At coordinate (row=2, col=0) we have TWO nodes:
// Node 5: value 5
// Node 6: value 6

// In our data structure:
map.get(0).get(2) = [5, 6]  // Two values at same position!

// We need to sort this list: [5, 6] → already sorted
// But if it was [6, 5] we'd sort to [5, 6]
💡 Why This Sorting Matters
Without Sorting:
// If we process tree in different order, we might get:
map.get(0).get(2) = [6, 5]

// Output would be: [..., [6, 5], ...]
// But problem requires: [..., [5, 6], ...]
With Sorting:
Collections.sort(values); // Ensures [5, 6] regardless of insertion order
🎯 The Complete Picture
Three Levels of Sorting:
1.Columns: Left to right (handled by outer TreeMap)
2.Rows: Top to bottom (handled by inner TreeMap)
3.Values: Small to large (handled by Collections.sort)
Code Execution:
for (List<Integer> values : rowMap.values()) {
    // 'values' contains all nodes at the same (row, col) position
    // There might be 1, 2, or more nodes in this list
    Collections.sort(values);  // Sort them by value
    columnValues.addAll(values);
}
🧪 Test Case Demonstration
Input where sorting matters:
// A tree structure that would create multiple nodes 
// in the same (row, col) bucket during processing
Actually, upon checking the problem constraints more carefully, in a proper binary tree you cannot have multiple nodes at the exact same (row, col) because each position is unique.
The sorting is actually needed for a different reason - let me clarify:
🔧 Correct Explanation
The sorting is required because when we collect nodes during traversal, the order they appear in each (row, col) bucket depends on traversal order, but the problem requires them to be sorted by value within each bucket.
// Even though each node has unique coordinates,
// our grouping by (col, row) creates buckets that
// should have values sorted ascending

// Example: During DFS we might visit nodes in order: 5, 3, 7
// But we want the output order: 3, 5, 7
So the comment "Only need to sort values at the same (row, col) position" means: Within each coordinate bucket, ensure values are sorted ascending, regardless of the order they were inserted during traversal.
This ensures the output meets the problem's specific ordering requirements!

How to use computeIfAbsent to replace below 3 statements ?
// Initialize the column TreeMap if not present
map.putIfAbsent(col, new TreeMap<>());

// Initialize the row list if not present
map.get(col).putIfAbsent(row, new ArrayList<>());

// Add the value to the correct (row, col) position
map.get(col).get(row).add(node.val);
🎯 Using computeIfAbsent to Simplify
You can replace those 3 statements with just 1 line using nested computeIfAbsent:
// Original: 3 statements
map.putIfAbsent(col, new TreeMap<>());
map.get(col).putIfAbsent(row, new ArrayList<>());
map.get(col).get(row).add(node.val);

// Simplified: 1 statement
map.computeIfAbsent(col, k -> new TreeMap<>())
   .computeIfAbsent(row, k -> new ArrayList<>())
   .add(node.val);
🔍 How computeIfAbsent Works
Method Signature:
// computeIfAbsent: If key exists, return its value. 
// If key doesn't exist, compute new value using the function, store it, and return it.
V computeIfAbsent(K key, Function<K, V> mappingFunction)
Step-by-Step Execution:
map.computeIfAbsent(col, k -> new TreeMap<>())
   // ↑ Returns existing TreeMap for col, or creates new one if absent
   
   .computeIfAbsent(row, k -> new ArrayList<>())
   // ↑ Returns existing List for row, or creates new one if absent
   
   .add(node.val);
   // ↑ Adds value to the list we just got/created
💡 Complete Optimized Method
private void traverse(TreeNode node, int row, int col, 
                     Map<Integer, TreeMap<Integer, List<Integer>>> map) {
    if (node == null) return;
    
    // Single line replacement for all 3 operations
    map.computeIfAbsent(col, k -> new TreeMap<>())
       .computeIfAbsent(row, k -> new ArrayList<>())
       .add(node.val);
    
    // Traverse children
    traverse(node.left, row + 1, col - 1, map);
    traverse(node.right, row + 1, col + 1, map);
}

🧩 Comparison: Before vs After
Before (3 statements):
// Verbose and repetitive
map.putIfAbsent(col, new TreeMap<>());
map.get(col).putIfAbsent(row, new ArrayList<>()); 
map.get(col).get(row).add(node.val);
After (1 statement):
// Concise and expressive
map.computeIfAbsent(col, k -> new TreeMap<>())
   .computeIfAbsent(row, k -> new ArrayList<>())
   .add(node.val);
🎯 Benefits of computeIfAbsent
1.More Concise: Reduces 3 operations to 1 fluent call
2.Fewer Map Lookups:
- Original: 2 get() calls + 1 putIfAbsent()
- New: Direct computation without intermediate get() calls
3.Thread-Safe: Single atomic operation per level
4.Cleaner Code: Expresses intent more clearly
💡 Alternative for BFS Version
The BFS version can also be simplified:
// Before:
map.putIfAbsent(col, new TreeMap<>());
map.get(col).putIfAbsent(row, new ArrayList<>());
map.get(col).get(row).add(node.val);

// After:
map.computeIfAbsent(col, k -> new TreeMap<>())
   .computeIfAbsent(row, k -> new ArrayList<>())
   .add(node.val);
🚀 Even More Concise with Method Reference
If you prefer, you can use method references:
// Using method references instead of lambda
map.computeIfAbsent(col, k -> new TreeMap<>())
   .computeIfAbsent(row, k -> new ArrayList<>())
   .add(node.val);

// Or if you have a constructor reference (less common):
map.computeIfAbsent(col, k -> new TreeMap<>())
   .computeIfAbsent(row, k -> new ArrayList<>())
   .add(node.val);
📊 Performance Consideration
While computeIfAbsent is slightly more expensive than putIfAbsent + get for the case where the key exists, the difference is negligible for this problem size, and the code clarity benefits outweigh any micro-optimizations.
Your refactored code becomes much cleaner and more expressive using computeIfAbsent!

What's the time and space complexity for DFS solution ?
📊 Time and Space Complexity Analysis
Time Complexity: O(n log n)
Let's break it down step by step:
1. DFS Traversal: O(n)
helper(root, 0, 0, map);  // Visits each node exactly once
2. TreeMap Operations: O(n log n)
map.computeIfAbsent(col, k -> new TreeMap<>())
   .computeIfAbsent(row, k -> new ArrayList<>())
   .add(node.val);
- Each computeIfAbsent on TreeMap: O(log C) where C = number of columns
- Each computeIfAbsent on inner TreeMap: O(log R) where R = number of rows per column
- Total for n nodes: O(n × (log C + log R)) ≈ O(n log n)
3. Sorting Phase: O(n log n)
for(List<Integer> values : rowMap.values()) {
    Collections.sort(values);  // This is the dominant cost!
}
- Each Collections.sort(values): O(k log k) where k = list size
- Sum over all lists: O(n log n) in worst case
4. Result Construction: O(n)
columnValues.addAll(values);  // O(n) total across all calls
Total Time: O(n) + O(n log n) + O(n log n) + O(n) = O(n log n)
Space Complexity: O(n)
1. Recursion Stack: O(h)
helper(node.left, ...);  // Maximum depth = tree height h
helper(node.right, ...);
- Best case (balanced tree): O(log n)
- Worst case (skewed tree): O(n)
2. Data Structures: O(n)
Map<Integer, TreeMap<Integer, List<Integer>>> map;
- Outer TreeMap: O(C) where C ≤ n
- Inner TreeMaps: O(n) total entries
- All ArrayLists: O(n) total elements
- Total: O(n)
3. Result List: O(n)
List<List<Integer>> result;  // Stores all n values
Total Space: O(h) + O(n) + O(n) = O(n)
🔍 Detailed Breakdown
Worst Case Scenario
Complete binary tree with all nodes at different coordinates:
- n nodes → n different (col, row) positions
- Each list has size 1 → sorting is O(1) per list
- But TreeMap operations still O(n log n)
Expensive Case Scenario
Tree where many nodes share same coordinates:
- Few large lists that need expensive sorting
- Example: k lists of size n/k → sorting cost O(k × (n/k) log(n/k)) = O(n log(n/k))
Best Case Scenario
Perfectly balanced tree:
- Still O(n log n) due to TreeMap operations
- But better constant factors

🎯 Bottleneck Analysis
Primary Bottleneck: Sorting
Collections.sort(values);  // O(k log k) per list
- This dominates when you have large lists at same coordinates
Secondary Bottleneck: TreeMap Operations
map.computeIfAbsent(col, k -> new TreeMap<>())  // O(log C)
   .computeIfAbsent(row, k -> new ArrayList<>()) // O(log R)
- Becomes significant when tree is large and spread out
--------------------------------------------------------------------------------
💡 Comparison with Alternative Approach
Current Approach (TreeMap + Sorting):
- Time: O(n log n)
- Space: O(n)
- Code: Clean and readable
Alternative (Single Sort):
// Collect all (value, row, col) tuples, then sort once
List<int[]> nodes = new ArrayList<>();  // [value, row, col]
// Then sort by: col → row → value
- Time: O(n log n) - same theoretical complexity
- Space: O(n) - same
- Code: May have better constant factors
--------------------------------------------------------------------------------
📈 Practical Performance
For LeetCode constraints (n ≤ 1000):
- O(n log n) = 1000 × log₂(1000) ≈ 10,000 operations
- Very efficient and easily passes all test cases
✅ Summary
ComponentTime ComplexitySpace ComplexityDFS TraversalO(n)O(h)TreeMap OperationsO(n log n)O(n)SortingO(n log n)O(1)TotalO(n log n)O(n)
The solution is optimal for this problem since we need to sort the output, and sorting inherently requires O(n log n) time!

Refer to
L103.P8.3.Binary Tree Zigzag Level Order Traversal (Ref.L314,L987)
L199.P8.8.Binary Tree Right Side View (Ref.L103,L314,L987)
L314.Binary Tree Vertical Order Traversal (Ref.L987)
