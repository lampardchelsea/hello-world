https://leetcode.ca/all/1740.html
Given the root of a binary tree and two integers p and q, return the distance between the nodes of value p and value q in the tree.
The distance between two nodes is the number of edges on the path from one to the other.
 
Example 1:


Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 0
Output: 3
Explanation: There are 3 edges between 5 and 0: 5-3-1-0.

Example 2:


Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 7
Output: 2
Explanation: There are 2 edges between 5 and 7: 5-2-7.

Example 3:


Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 5
Output: 0
Explanation: The distance between a node and itself is 0.
 
Constraints:
- The number of nodes in the tree is in the range [1, 10^4].
- 0 <= Node.val <= 10^9
- All Node.val are unique.
- p and q are values in the tree.
--------------------------------------------------------------------------------
Attempt 1: 2024-07-16
Solution 1: LCA + Find Depth (30 min)
Style 1
class Solution {
    // Finds the distance between two nodes in a binary tree.
    public int findDistance(TreeNode root, int p, int q) {
        // Find the lowest common ancestor (LCA) of nodes p and q.
        TreeNode LCA = findLCA(root, p, q);
        // Calculate the distance from LCA to p and add it to the distance from LCA to q.
        return findDepth(lcaNode, p) + findDepth(lcaNode, q);
    }

    // Helper method to find the depth of a given value from the root of the tree.
    private int findDepth(TreeNode node, int value) {
        // If node is null, return -1 indicating the value is not present in this subtree.
        if (node == null) {
            return -1;
        }
        // If the node's value matches, return 0 indicating the depth is zero at this node.
        if (node.val == value) {
            return 0;
        }
        // Search in the left subtree.
        int leftDepth = findDepth(node.left, value);
        // Search in the right subtree.
        int rightDepth = findDepth(node.right, value);      
        // If the value is not found in either subtree, return -1.
        if (leftDepth == -1 && rightDepth == -1) {
            return -1;
        }
        // Return 1 plus the maximum depth found in either subtree.
        // The maximum is used since a non-existing path returns -1 and should be ignored.
        return 1 + Math.max(leftDepth, rightDepth);
    }

    // Helper method to find the lowest common ancestor (LCA) of two given values.
    private TreeNode findLCA(TreeNode node, int p, int q) {
        // If reached the end or found one of the values, return the current node.
        if (node == null || node.val == p || node.val == q) {
            return node;
        }
        // Search for LCA in the left subtree.
        TreeNode leftLCA = findLCA(node.left, p, q);
        // Search for LCA in the right subtree.
        TreeNode rightLCA = findLCA(node.right, p, q);     
        // If one of the values is on the left and the other is on the right, this node is their LCA.
        if (leftLCA != null && rightLCA != null) {
            return node;
        }
        // If only left subtree has one of the values, return the leftLCA.
        if (leftLCA != null) {
            return leftLCA;
        }
        // If only right subtree has one of the values, return the rightLCA.
        return rightLCA;
    }
}

// Definition for a binary tree node provided by the problem statement.
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;  
    // Constructor without children.
    TreeNode() {}
    // Constructor with the node's value.
    TreeNode(int val) { this.val = val; }
    // Constructor with the node's value and links to left and right children.
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Refer to
https://algo.monster/liteproblems/1740
Problem Description
Given a binary tree, we are tasked with finding the distance between two nodes, where the distance is defined as the number of edges on the shortest path between the two nodes. The binary tree is defined by its root node, and the two nodes whose distance we want to find are provided as integers p and q which represent their values. We are required to determine how many edges lie between the nodes containing these two values.
Intuition
To solve this problem, we employ a two-step strategy:
1.Find the Lowest Common Ancestor (LCA) of the two given nodes. The LCA is the deepest node in the tree that has both p and q as descendants (where a node can be a descendant of itself). By identifying the LCA, we can then find the lengths of the paths from the LCA to each of the two nodes.
2.Calculate the distance from the LCA to each of the nodes p and q. The distance between p and q is then the sum of these two distances.
The lca function, a recursive helper function, serves to find the lowest common ancestor of two nodes. This recursive approach checks the following at each node:
- If the node is null (base case), it means we've reached a leaf node and have not found either of the two values; we return null to represent that neither p nor q pass through this branch.
- If the current node’s value is p or q, the current node is part of the path to one of the target nodes, so we return it.
- Otherwise, we recursively call lca on the left and right children and observe:
- If the node has one child that returns null and another child that returns a non-null node, it means one of the target nodes has been found in one branch only, and we return the non-null child.
- If both children return non-null nodes, it means the current node is the lowest common ancestor, and so it is returned.
- If both children return null, it means none of the nodes p or q are found below this node, and hence null is returned.
Once the LCA is found with the lca function, the dfs (Depth-First Search) function computes the distance from the LCA to a given node v. dfs is another recursive function that traverses the subtree rooted at the LCA and searches for v. If v is found, dfs returns the number of edges between the LCA and the node v. If v is not found in a branch, -1 is returned.
Finally, the solution aggregates the result by calling dfs(g, p) and dfs(g, q), which finds the distance from the LCA to nodes p and q, respectively, and the sum of these distances yield the total distance between nodes p and q.
Solution Approach
The solution approach for finding the distance between two nodes with values p and q in a binary tree involves two main components: locating the lowest common ancestor (LCA) and performing two depth-first searches (DFS) to calculate the distance from the LCA to each node.
Here's how the implementation works, step by step:
Locating the Lowest Common Ancestor (LCA)
- The lca helper function uses recursion to traverse the binary tree.
- Starting at the root, the function checks whether the current node is null or if its value matches p or q.
- If the current node is null or if it matches p or q, the function returns the current node.
- If the current node is not what we are looking for, the function recursively calls itself on the left and right children.
- The key idea is that if both children return a non-null value, it means we've found nodes p and q in different branches of this node, making this node the LCA.
- If one child returns null and the other returns a non-null value, it indicates that both p and q are in the direction of the non-null returning child, and we propagate this value up.
- If both children return null, we continue searching along the tree.
Depth-First Search (DFS) for Distance Calculation
- Having identified the LCA, we now need to calculate the distances from this node to both p and q. We use the dfs helper function for this purpose.
- The dfs function takes a node and a value v and returns the distance from the given node to a descendant node containing v, or -1 if v is not found in the subtree.
- Similar to lca, dfs uses recursion to traverse the tree. It compares the current node's value with v.
- If a match is found, it returns 0 as the distance from a node to itself is zero.
- Otherwise, the function recursively calls dfs for the left and right children of the current node.
- If v is found in either subtree, dfs returns the distance to v plus one (to account for the edge between the current node and the child).
- If neither subtree contains v, indicated by -1 returns from both sides, dfs returns -1.
- By calling dfs(g, p) and dfs(g, q) separately where g is the LCA, the distance from LCA to p and to q is obtained.
The final distance between p and q is then the sum of the two distances obtained from dfs. By using the LCA as a starting point, we effectively find the shortest path between p and q, as any path between them must pass through the LCA.
All these functionalities are tied together in the findDistance method of the Solution class, which uses the defined helper functions to compute and return the total distance between the nodes p and q.
Example Walkthrough
Let's consider a simple binary tree for our example:
        3
       / \
      5   1
     / \
    6   2
       / \
      7   4
Assume we want to find the distance between nodes with values 7 and 4.
The steps outlined in the solution approach would occur as follows:
- Locating the Lowest Common Ancestor (LCA):
- We start at the root of the tree which is 3 and call the lca function.
- Since 3 is not 7 or 4, we call lca for its left child (5) and its right child (1).
- The right child (1) does not lead to nodes 7 or 4, so it returns null.
- However, the left child (5) is the parent node of both 7 and 4, so the recursive calls on its children (6 and 2) will eventually return non-null for paths leading to 7 and 4.
- Since both the recursive calls from node 2 (left child returning 7 and right child returning 4) will return non-null, it means we have found the LCA, which is the node with value 2.
- Depth-First Search (DFS) for Distance Calculation:
- Now, with node 2 determined as the LCA, we proceed to use the dfs function to calculate the distance from 2 to both 7 and 4.
- To find the distance to 7, we discover that 7 is the left child of 2. So the dfs will return 0 + 1 (as the distance between a parent and its child is 1).
- To find the distance to 4, we see that 4 is the right child of 2. Similarly, dfs will return 0 + 1.
- Having obtained both distances (from LCA to 7 and from LCA to 4), which are 1 and 1 respectively, we sum them to find the total distance.
- The final distance between nodes with values 7 and 4 is the sum of the distances from their LCA, which in this case is 1 + 1 = 2. So, the distance between nodes 7 and 4 is 2.
The final distance between nodes with values 7 and 4 is the sum of the distances from their LCA, which in this case is 1 + 1 = 2. So, the distance between nodes 7 and 4 is 2.
Solution Implementation
class Solution {

    // Finds the distance between two nodes in a binary tree.
    public int findDistance(TreeNode root, int p, int q) {
        // Find the lowest common ancestor (LCA) of nodes p and q.
        TreeNode lcaNode = findLowestCommonAncestor(root, p, q);
        // Calculate the distance from LCA to p and add it to the distance from LCA to q.
        return findDepth(lcaNode, p) + findDepth(lcaNode, q);
    }

    // Helper method to find the depth of a given value from the root of the tree.
    private int findDepth(TreeNode node, int value) {
        // If node is null, return -1 indicating the value is not present in this subtree.
        if (node == null) {
            return -1;
        }
        // If the node's value matches, return 0 indicating the depth is zero at this node.
        if (node.val == value) {
            return 0;
        }
        // Search in the left subtree.
        int leftDepth = findDepth(node.left, value);
        // Search in the right subtree.
        int rightDepth = findDepth(node.right, value);
      
        // If the value is not found in either subtree, return -1.
        if (leftDepth == -1 && rightDepth == -1) {
            return -1;
        }
        // Return 1 plus the maximum depth found in either subtree.
        // The maximum is used since a non-existing path returns -1 and should be ignored.
        return 1 + Math.max(leftDepth, rightDepth);
    }

    // Helper method to find the lowest common ancestor (LCA) of two given values.
    private TreeNode findLowestCommonAncestor(TreeNode node, int p, int q) {
        // If reached the end or found one of the values, return the current node.
        if (node == null || node.val == p || node.val == q) {
            return node;
        }
        // Search for LCA in the left subtree.
        TreeNode leftLca = findLowestCommonAncestor(node.left, p, q);
        // Search for LCA in the right subtree.
        TreeNode rightLca = findLowestCommonAncestor(node.right, p, q);
      
        // If one of the values is on the left and the other is on the right, this node is their LCA.
        if (leftLca != null && rightLca != null) {
            return node;
        }
        // If only left subtree has one of the values, return the leftLca.
        if (leftLca != null) {
            return leftLca;
        }
        // If only right subtree has one of the values, return the rightLca.
        return rightLca;
    }
}

// Definition for a binary tree node provided by the problem statement.
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
  
    // Constructor without children.
    TreeNode() {}
  
    // Constructor with the node's value.
    TreeNode(int val) { this.val = val; }
  
    // Constructor with the node's value and links to left and right children.
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
Time and Space Complexity
The given Python code defines a Solution class with a function findDistance to find the distance between two nodes in a binary tree, given their values p and q. The distance is the number of edges in the path that connects the two nodes.
Time Complexity:
The lca function computes the Lowest Common Ancestor (LCA) of the two nodes. It traverses the entire binary tree in a worst-case scenario with a time complexity of O(N), where N is the number of nodes in the tree.
The dfs function searches for the depth of a given node value from the root node (or given node in second call). The worst-case time complexity is also O(N) for a skewed binary tree. Since the dfs function is called twice, the time to execute both calls is 2 * O(N).
Thus, the overall time complexity of finding the distance would involve the sum of the complexities of computing the LCA and making two DFS searches. Hence, the total time complexity is O(N) + 2 * O(N) which simplifies to O(N).
Space Complexity:
Space complexity due to the recursive calls in the lca function depends on the height of the tree (which would be the maximum number of elements in the call stack at any time). For a balanced tree, this would be O(log N), but for a skewed tree, it could be O(N).
The dfs function also uses space due to the recursive calls made to traverse the tree. Just as with the lca function, this could require up to O(N) space in the worst case.
Taking into account the space needed for the system call stack during recursive calls, the total space complexity of the algorithm is O(N) in the worst case (assuming the tree is skewed). If the tree is balanced, space complexity would be O(log N) due to the reduced height of the tree.
--------------------------------------------------------------------------------
Style 2
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
    public int findDistance(TreeNode root, int p, int q) {
        TreeNode lca = findLCA(root, p, q);
        int distanceP = findDepth(lca, p, 0);
        int distanceQ = findDepth(lca, q, 0);
        return distanceP + distanceQ;
    }

    // Find the Lowest Common Ancestor (LCA) of two nodes
    private TreeNode findLCA(TreeNode root, int p, int q) {
        if (root == null) {
            return null;
        }
        if (root.val == p || root.val == q) {
            return root;
        }
        TreeNode left = findLCA(root.left, p, q);
        TreeNode right = findLCA(root.right, p, q);
        if (left != null && right != null) {
            return root;
        }
        return left != null ? left : right;
    }

    // Find the depth of a node from a given root
    private int findDepth(TreeNode root, int val, int depth) {
        if (root == null) {
            return -1; // Target node not found in this subtree
        }
        if (root.val == val) {
            return depth; // Target node found, return the current depth
        }
        int left = findDepth(root.left, val, depth + 1); // Search in the left subtree
        if (left != -1) {
            return left; // Target node found in the left subtree
        }
        return findDepth(root.right, val, depth + 1); // Search in the right subtree
    }
}

Time Complexity: O(n)
Space Complexity: O(n)
Refer to chatGPT
Problem Explanation
1.Binary Tree Distance: The distance between two nodes in a binary tree is defined as the number of edges in the shortest path between them.
2.Steps to Solve:
- Find the Lowest Common Ancestor (LCA) of the two nodes. The LCA is the deepest node that is an ancestor of both nodes.
- Compute the distance from the LCA to each node.
- Sum these distances to get the total distance between the two nodes.
Java Implementation
Here's a solution in Java:
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
    public int findDistance(TreeNode root, int p, int q) {
        TreeNode lca = findLCA(root, p, q);
        int distanceP = findDepth(lca, p, 0);
        int distanceQ = findDepth(lca, q, 0);
        return distanceP + distanceQ;
    }

    // Find the Lowest Common Ancestor (LCA) of two nodes
    private TreeNode findLCA(TreeNode root, int p, int q) {
        if (root == null) {
            return null;
        }
        if (root.val == p || root.val == q) {
            return root;
        }
        TreeNode left = findLCA(root.left, p, q);
        TreeNode right = findLCA(root.right, p, q);

        if (left != null && right != null) {
            return root;
        }
        return left != null ? left : right;
    }

    // Find the depth of a node from a given root
    private int findDepth(TreeNode root, int val, int depth) {
        if (root == null) {
            return -1;
        }
        if (root.val == val) {
            return depth;
        }
        int left = findDepth(root.left, val, depth + 1);
        if (left != -1) {
            return left;
        }
        return findDepth(root.right, val, depth + 1);
    }
}
Explanation
1.Finding the LCA:
- The findLCA function is a standard algorithm to find the lowest common ancestor in a binary tree.
- If the current node is one of the target nodes (p or q), it returns the current node.
- It recursively searches for the nodes in both the left and right subtrees.
- If both left and right recursive calls return non-null values, it means the current node is the LCA.
- If only one of the recursive calls returns a non-null value, it propagates that non-null value up the call stack.
2.Finding the Depth:
- The findDepth function calculates the depth of a node from a given root node.
- It uses a depth-first search (DFS) approach.
- If the current node matches the target value (val), it returns the current depth.
- Otherwise, it recursively searches the left and right subtrees, increasing the depth by 1.
- If the target node is found in either subtree, it returns the depth. Otherwise, it returns -1.
3.Calculating the Distance:
- The findDistance function combines the results from findLCA and findDepth to compute the distance between the two nodes.
- It first finds the LCA of nodes p and q.
- It then calculates the depth (distance) from the LCA to each of the nodes.
- Finally, it returns the sum of these two distances as the total distance between the two nodes.
Complexity Analysis
- Time Complexity: O(n), where n is the number of nodes in the binary tree. Both findLCA and findDepth functions traverse the tree, resulting in a linear time complexity.
- Space Complexity: O(h), where h is the height of the tree. This is the space used by the recursion stack, which in the worst case (a completely unbalanced tree) is O(n), but on average for a balanced tree is O(log n).
This solution effectively and efficiently finds the distance between two nodes in a binary tree by leveraging the properties of the Lowest Common Ancestor and depth calculation.

Why if the target node not found we return -1 ?
Returning -1 when the target node is not found in the findDepth function is a way to indicate that the target node does not exist in the subtree being examined. This approach is crucial for the recursive logic to correctly propagate the absence of the target node up the call stack. Here’s a more detailed explanation:
private int findDepth(TreeNode root, int val, int depth) {
    if (root == null) {
        return -1; // Target node not found in this subtree
    }
    if (root.val == val) {
        return depth; // Target node found, return the current depth
    }
    int left = findDepth(root.left, val, depth + 1); // Search in the left subtree
    if (left != -1) {
        return left; // Target node found in the left subtree
    }
    return findDepth(root.right, val, depth + 1); // Search in the right subtree
}
Why Return -1?
- Indicating Absence: Returning -1 is a signal that the target node is not present in the current subtree. This is essential for the function to correctly backtrack and continue the search in other parts of the tree.
- Recursive Propagation: When a subtree does not contain the target node, returning -1 ensures that this information is propagated back to the parent node, prompting the search in the other subtree.
Detailed Walkthrough with Example
Consider the binary tree:
       1
      / \
     2   3
    / \   \
   4   5   6

Finding Depth of Node 5
1.Start at Root (1):
- findDepth(root=1, val=5, depth=0)
- Node 1 is not 5. Search left subtree.
2.Left Subtree (2):
- findDepth(root=2, val=5, depth=1)
- Node 2 is not 5. Search left subtree.
3.Left Subtree of 2 (4):
- findDepth(root=4, val=5, depth=2)
- Node 4 is not 5. Search left subtree.
4.Left Subtree of 4 (null):
- findDepth(root=null, val=5, depth=3)
- Node is null, return -1.
- Back to node 4, search right subtree.
5.Right Subtree of 4 (null):
- findDepth(root=null, val=5, depth=3)
- Node is null, return -1.
- Node 5 not found in subtree rooted at 4, return -1 to node 2.
6.Back to Node 2, Search Right Subtree (5):
- findDepth(root=5, val=5, depth=2)
- Node 5 is found, return depth 2.
If node 5 were not found in the left subtree of node 2, the algorithm would proceed to the right subtree of node 1 (node 3) and continue similarly.
Propagation Example
- If node 5 was not in the tree at all, all recursive calls would return -1 up the call stack, ultimately returning -1 to the initial call, indicating node 5 does not exist in the tree.
Conclusion
Returning -1 ensures that the absence of the target node is correctly propagated up the call stack, allowing the search to continue in other subtrees. It ensures that the function does not mistakenly assume the target node exists where it does not.


Refer to
L2096.Step-By-Step Directions From a Binary Tree Node to Another(Ref.L1740,L236)
L236.Lowest Common Ancestor of a Binary Tree (Ref.L865,L235)
