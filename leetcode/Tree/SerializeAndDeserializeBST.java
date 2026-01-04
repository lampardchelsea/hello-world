
https://leetcode.com/problems/serialize-and-deserialize-bst/
Serialization is converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.
Design an algorithm to serialize and deserialize a binary search tree. There is no restriction on how your serialization/deserialization algorithm should work. You need to ensure that a binary search tree can be serialized to a string, and this string can be deserialized to the original tree structure.
The encoded string should be as compact as possible.

Example 1:
Input: root = [2,1,3]
Output: [2,1,3]

Example 2:
Input: root = []
Output: []

Constraints:
- The number of nodes in the tree is in the range [0, 10^4].
- 0 <= Node.val <= 10^4
- The input tree is guaranteed to be a binary search tree.
--------------------------------------------------------------------------------
Attempt 1: 2022-10-29
Solution 1: Same as L297.Serialize and Deserialize Binary Tree, not use BST property,  preorder traversal for Serialize using DFS and for Deserialize using DFS (based on Queue) (10min)
/** 
 * Definition for a binary tree node. 
 * public class TreeNode { 
 *     int val; 
 *     TreeNode left; 
 *     TreeNode right; 
 *     TreeNode(int x) { val = x; } 
 * } 
 */ 
public class Codec { 
    private String NA = "X"; 
    private String spliter = ","; 
     
    // Encodes a tree to a single string. 
    public String serialize(TreeNode root) { 
        StringBuilder sb = new StringBuilder(); 
        serializeHelper(root, sb); 
        return sb.toString(); 
    } 
     
    private void serializeHelper(TreeNode root, StringBuilder sb) { 
        if(root == null) { 
            sb.append(NA).append(spliter); 
            return; 
        } 
        sb.append(root.val).append(spliter); 
        serializeHelper(root.left, sb); 
        serializeHelper(root.right, sb); 
    } 
    // Decodes your encoded data to tree. 
    public TreeNode deserialize(String data) { 
        Queue<String> q = new LinkedList<String>(); 
        q.addAll(Arrays.asList(data.split(spliter))); 
        return deserializeHelper(q); 
    } 
     
    private TreeNode deserializeHelper(Queue<String> q) { 
        String rootVal = q.poll(); 
        if(rootVal.equals(NA)) { 
            return null; 
        } 
        TreeNode root = new TreeNode(Integer.parseInt(rootVal)); 
        root.left = deserializeHelper(q); 
        root.right = deserializeHelper(q); 
        return root; 
    } 
} 
// Your Codec object will be instantiated and called as such: 
// Codec ser = new Codec(); 
// Codec deser = new Codec(); 
// String tree = ser.serialize(root); 
// TreeNode ans = deser.deserialize(tree); 
// return ans;

Time Complexity: O(N), where N <= 10^4 is number of nodes in the BST   
Space Complexity: O(N)

Solution 2: Same as L297.Serialize and Deserialize Binary Tree, not use BST property, level order traversal for Serialize using BFS (Queue) and for Deserialize using BFS (Queue) (10min)
/** 
 * Definition for a binary tree node. 
 * public class TreeNode { 
 *     int val; 
 *     TreeNode left; 
 *     TreeNode right; 
 *     TreeNode(int x) { val = x; } 
 * } 
 */ 
public class Codec { 
    private String NA = "X"; 
    private String spliter = ","; 
     
    // Encodes a tree to a single string. 
    public String serialize(TreeNode root) { 
        if(root == null) { 
            return ""; 
        } 
        StringBuilder sb = new StringBuilder(); 
        Queue<TreeNode> q = new LinkedList<TreeNode>(); 
        q.offer(root); 
        while(!q.isEmpty()) { 
            TreeNode node = q.poll(); 
            if(node == null) { 
                sb.append(NA).append(spliter); 
                continue; 
            } 
            sb.append(node.val).append(spliter); 
            q.offer(node.left); 
            q.offer(node.right); 
        } 
        return sb.toString(); 
    } 
    // Decodes your encoded data to tree. 
    public TreeNode deserialize(String data) { 
        if(data == "") { 
            return null; 
        } 
        String[] values = data.split(spliter); 
        Queue<TreeNode> q = new LinkedList<TreeNode>(); 
        TreeNode root = new TreeNode(Integer.parseInt(values[0])); 
        q.offer(root); 
        for(int i = 1; i < values.length; i++) { 
            TreeNode node = q.poll(); 
            if(!values[i].equals(NA)) { 
                TreeNode leftNode = new TreeNode(Integer.parseInt(values[i])); 
                node.left = leftNode; 
                q.offer(leftNode); 
            } 
            i++; 
            if(!values[i].equals(NA)) { 
                TreeNode rightNode = new TreeNode(Integer.parseInt(values[i])); 
                node.right = rightNode; 
                q.offer(rightNode); 
            } 
        } 
        return root; 
    } 
} 
// Your Codec object will be instantiated and called as such: 
// Codec ser = new Codec(); 
// Codec deser = new Codec(); 
// String tree = ser.serialize(root); 
// TreeNode ans = deser.deserialize(tree); 
// return ans;

Time Complexity: O(N), where N <= 10^4 is number of nodes in the BST   
Space Complexity: O(N)

Solution 3:  Use BST property (60min, too long to understand how to use BST attribute in deserialize function)
/** 
 * Definition for a binary tree node. 
 * public class TreeNode { 
 *     int val; 
 *     TreeNode left; 
 *     TreeNode right; 
 *     TreeNode(int x) { val = x; } 
 * } 
 */ 
public class Codec { 
    // No need handling NULL with String NA = "X" like L297.Serialize and Deserialize Binary Tree, 
    // it will match the requirement 'The encoded string should be as compact as possible' 
    private String spliter = ","; 
     
    // Encodes a tree to a single string. 
    public String serialize(TreeNode root) { 
        if(root == null) { 
            return ""; 
        } 
        StringBuilder sb = new StringBuilder(); 
        serializeHelper(root, sb); 
        return sb.toString(); 
    } 
     
    private void serializeHelper(TreeNode root, StringBuilder sb) { 
        // No need handling NULL with sb.append(NA).append(spliter) like  
        // L297.Serialize and Deserialize Binary Tree, just return, it will match  
        // the requirement 'The encoded string should be as compact as possible' 
        if(root == null) { 
            return; 
        } 
        sb.append(root.val).append(spliter); 
        serializeHelper(root.left, sb); 
        serializeHelper(root.right, sb); 
    } 
    // Decodes your encoded data to tree. 
    public TreeNode deserialize(String data) { 
        if(data == "") { 
            return null; 
        } 
        List<String> values = Arrays.asList(data.split(spliter)); 
        Queue<String> q = new LinkedList<String>(); 
        q.addAll(values); 
        return deserializeHelper(q, Integer.MIN_VALUE, Integer.MAX_VALUE); 
    } 
     
    private TreeNode deserializeHelper(Queue<String> q, int lower, int upper) { 
        if(q.isEmpty()) { 
            return null; 
        } 
        int currentRootVal = Integer.parseInt(q.peek()); 
        // No need check 'currentRootVal < lower' 
        //if(currentRootVal < lower || currentRootVal > upper) { 
        if(currentRootVal > upper) { 
            return null; 
        } 
        q.poll(); 
        TreeNode root = new TreeNode(currentRootVal); 
        root.left = deserializeHelper(q, lower, currentRootVal); 
        root.right = deserializeHelper(q, currentRootVal, upper); 
        return root; 
    } 
} 
// Your Codec object will be instantiated and called as such: 
// Codec ser = new Codec(); 
// Codec deser = new Codec(); 
// String tree = ser.serialize(root); 
// TreeNode ans = deser.deserialize(tree); 
// return ans;

Time Complexity: O(N), where N <= 10^4 is number of nodes in the BST   
Space Complexity: O(N)

What's the difference between L449.Serialize and Deserialize BST with L297.Serialize and Deserialize Binary Tree ?

Refer to
https://leetcode.com/problems/serialize-and-deserialize-bst/discuss/177617/the-General-Solution-for-Serialize-and-Deserialize-BST-and-Serialize-and-Deserialize-BT/524978
Q: Since BST is special case of Binary Tree, should not we use Binary Tree approach for Serializing and De-serializing both ?
A:  Because the problem says.. "The encoded string should be as compact as possible" . we could save the space used for saving "X" while encountering null in BST.

Refer to
https://leetcode.com/problems/serialize-and-deserialize-bst/discuss/177617/the-General-Solution-for-Serialize-and-Deserialize-BST-and-Serialize-and-Deserialize-BT/214024
Q: Can someone please explain why if (val < lower || val > upper) return null;is used?
A: That way you do not have to store null values, return null, when the range bound is out of lower or upper. It makes the string compact. Smart solution indeed. According to the Binary search rule, only the lower values than the root can be added to the left child of the tree. This is done by creating a boundary such that if the value > higher bound, do not add it to left child i.e. Val > upper=> return null condition
Do we need to check val < lower?
Refer to
https://leetcode.com/problems/serialize-and-deserialize-bst/discuss/177617/the-General-Solution-for-Serialize-and-Deserialize-BST-and-Serialize-and-Deserialize-BT/733933
I think if (val < lower || val > upper) return null; can be simplified into if (val > upper) return null;Because we are traversing the tree in preorder, each condition violation will only happen in upper bound.(You can think that in this algorithm, we will first fill all elements on the left. When the first violation happens, this value must satisfy val > upper.Then we will go back to the parent node. In the parent node's right child, the bound becomes [parent's val, upper_bound].However, we know that we are in a BST tree, so all the remaining values in the queue must > parent's val.Therefore, because all the remaining elements > parent's val and so on so forth. There is no need to maintain the lower bound.      


Refer to QWEN
LeetCode 449 is similar to LeetCode 297 but specifically for Binary Search Trees (BSTs). The key advantage is that BSTs have ordering properties that allow for more efficient serialization without needing to store null markers.
Key Insight
Since it's a BST, we can use preorder traversal and reconstruct the tree using the BST property (left < root < right). We don't need to store null values because we can determine subtree boundaries using value ranges.
Approach 1: Preorder Traversal with StringBuilder (Most Efficient)
import java.util.*;

public class Codec {
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }
    
    private void serializeHelper(TreeNode node, StringBuilder sb) {
        if (node == null) return;
        sb.append(node.val).append(",");
        serializeHelper(node.left, sb);
        serializeHelper(node.right, sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data.isEmpty()) return null;
        
        String[] values = data.split(",");
        Queue<Integer> queue = new LinkedList<>();
        for (String val : values) {
            queue.offer(Integer.parseInt(val));
        }
        return deserializeHelper(queue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    private TreeNode deserializeHelper(Queue<Integer> queue, int min, int max) {
        if (queue.isEmpty()) return null;
        
        int val = queue.peek();
        if (val < min || val > max) return null;
        
        queue.poll();
        TreeNode node = new TreeNode(val);
        node.left = deserializeHelper(queue, min, val);
        node.right = deserializeHelper(queue, val, max);
        return node;
    }
}
Approach 2: Using ArrayList Instead of Queue
import java.util.*;

public class Codec {
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }
    
    private void serializeHelper(TreeNode node, StringBuilder sb) {
        if (node == null) return;
        sb.append(node.val).append(",");
        serializeHelper(node.left, sb);
        serializeHelper(node.right, sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data.isEmpty()) return null;
        
        String[] values = data.split(",");
        List<Integer> list = new ArrayList<>();
        for (String val : values) {
            list.add(Integer.parseInt(val));
        }
        int[] index = {0}; // Using array to pass by reference
        return deserializeHelper(list, index, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    private TreeNode deserializeHelper(List<Integer> list, int[] index, int min, int max) {
        if (index[0] >= list.size()) return null;
        
        int val = list.get(index[0]);
        if (val < min || val > max) return null;
        
        index[0]++;
        TreeNode node = new TreeNode(val);
        node.left = deserializeHelper(list, index, min, val);
        node.right = deserializeHelper(list, index, val, max);
        return node;
    }
}
How It Works
Serialization
- Simply perform preorder traversal (root, left, right)
- Only store actual node values (no null markers needed)
- Result: "5,3,2,4,7,6" for a typical BST
Deserialization
- Use the BST property: for any subtree with root value val,
- Left subtree contains values in range (min, val)
- Right subtree contains values in range (val, max)
- Process values in preorder sequence
- If current value is outside the valid range, it doesn't belong to current subtree
Example Walkthrough
For BST:
    5
   / \
  3   7
 / \ / \
2  4 6  8
Serialization: "5,3,2,4,7,6,8"
Deserialization process:
1.Start with range (-∞, +∞), take 5 as root
2.Left subtree range (-∞, 5):
- Take 3, then 2 (range (-∞, 3)), then 4 (range (3, 5))
3.Right subtree range (5, +∞):
- Take 7, then 6 (range (5, 7)), then 8 (range (7, +∞))
Key Advantages Over General Binary Tree Serialization
1.No null markers needed - saves space
2.More compact serialization - only store actual values
3.Leverages BST property for reconstruction
4.Same time complexity O(N) but better space efficiency
Time & Space Complexity
- Time: O(N) for both serialize and deserialize
- Space: O(N) for the serialized string and recursion stack
- Serialization length: Exactly N values (vs 2N+1 for general binary tree with null markers)
This solution is optimal for BSTs and demonstrates how data structure properties can be leveraged for more efficient algorithms.

Can you explain what's the situation about below ? any example ? is the situation handle by statement 'if (val < min || val > max) return null;' ? If current value is outside the valid range, it doesn't belong to current subtree
Yes, exactly! The statement if (val < min || val > max) return null; handles the situation where we encounter a value that belongs to a different part of the tree (specifically, an ancestor's right subtree or a completely different branch).
Let me walk through a concrete example to illustrate this:
Example Tree
        10
       /  \
      5    15
     / \   / \
    2   7 12  20
         /
        11
Preorder serialization: "10,5,2,7,15,12,11,20"
Deserialization Walkthrough
We start with deserializeHelper(queue, -∞, +∞)
Step 1: Current range (-∞, +∞)
- Queue: [10,5,2,7,15,12,11,20]
- val = 10 → within range → create root(10)
- Recurse left with range (-∞, 10)
- Recurse right with range (10, +∞)
Step 2: Left subtree, range (-∞, 10)
- Queue: [5,2,7,15,12,11,20]
- val = 5 → within range → create node(5)
- Recurse left with range (-∞, 5)
- Recurse right with range (5, 10)
Step 3: Left of 5, range (-∞, 5)
- Queue: [2,7,15,12,11,20]
- val = 2 → within range → create node(2)
- Recurse left with range (-∞, 2) → next val is 7, but 7 > 2 → returns null
- Recurse right with range (2, 5) → next val is 7, but 7 > 5 → returns null
- Node 2 is complete, return to node 5
Step 4: Right of 5, range (5, 10)
- Queue: [7,15,12,11,20]
- val = 7 → within range (5 < 7 < 10) → create node(7)
- Recurse left with range (5, 7) → next val is 15, but 15 > 7 → returns null
- Recurse right with range (7, 10) → next val is 15, but 15 > 10 → returns null
- Node 7 is complete, return to node 5, then to root(10)
Step 5: Right subtree of root, range (10, +∞)
- Queue: [15,12,11,20]
- val = 15 → within range → create node(15)
- Recurse left with range (10, 15)
- Recurse right with range (15, +∞)
Step 6: Left of 15, range (10, 15)
- Queue: [12,11,20]
- val = 12 → within range (10 < 12 < 15) → create node(12)
- Recurse left with range (10, 12)
- Recurse right with range (12, 15)
Step 7: Left of 12, range (10, 12)
- Queue: [11,20]
- val = 11 → within range (10 < 11 < 12) → create node(11)
- Recurse left with range (10, 11) → next val is 20, but 20 > 11 → returns null
- Recurse right with range (11, 12) → next val is 20, but 20 > 12 → returns null
Step 8: Right of 12, range (12, 15)
- Queue: [20]
- val = 20 → OUTSIDE RANGE! (20 > 15)  ← HERE'S THE KEY SITUATION
- This is handled by: if (val < min || val > max) return null;
- Returns null, so node 12's right child is null
Step 9: Right of 15, range (15, +∞)
- Queue: [20]
- val = 20 → within range → create node(20)
The Critical Situation
The key moment is in Step 8, where we have:
- Current range: (12, 15) (looking for right child of node 12)
- Current value in queue: 20
- Since 20 > 15, it doesn't belong in this subtree
What does this mean?
- Value 20 actually belongs to the right subtree of node 15, not the right subtree of node 12
- The condition if (val < min || val > max) return null; correctly identifies that 20 is not part of the current subtree
- It returns null for node 12's right child, and the recursion unwinds back to node 15
- Then node 15 processes its right subtree with range (15, +∞), where 20 DOES belong
Why This Works
The preorder traversal ensures that all nodes in a subtree appear consecutively in the serialized string. When we encounter a value outside the current valid range, it means:
1.We've finished processing the current subtree
2.The current value belongs to an ancestor's right subtree (or higher up in the tree)
3.We should return null to indicate "no more nodes in this subtree" and let the parent/ancestor handle this value
This is the elegant insight that makes BST deserialization possible without null markers: the BST ordering property combined with preorder traversal naturally delineates subtree boundaries through valid ranges.

Why change 'int val = queue.peek();' to 'int val = queue.poll();' won't work ?
Great question! Changing queue.peek() to queue.poll() will break the algorithm because it removes the element from the queue before we've verified that it actually belongs to the current subtree.
Let me show you exactly why this fails with an example.
The Problem
When we use queue.poll() immediately:
- We remove the value from the queue
- Then we check if it's within the valid range
- If it's not in range, we return null
- But the value is already gone from the queue!
This means we lose that value permanently, and it won't be available for the correct parent/ancestor that should process it.
Concrete Example
Consider this BST:
    5
     \
      10
     /
    7
Preorder serialization: "5,10,7"
Deserialization process with queue.poll() (WRONG):
Initial queue: [5, 10, 7]
1.Root call: deserializeHelper(queue, -∞, +∞)
- val = queue.poll() → val = 5, queue becomes [10, 7]
- 5 is in range → create root(5)
- Process left: deserializeHelper(queue, -∞, 5)
2.Left subtree of 5: deserializeHelper(queue, -∞, 5)
- val = queue.poll() → val = 10, queue becomes [7]
- Check: 10 < -∞ || 10 > 5 → TRUE! (10 > 5)
- Return null ← PROBLEM! Value 10 is lost!
3.Process right of root: deserializeHelper(queue, 5, +∞)
- val = queue.poll() → val = 7, queue becomes []
- 7 is in range (5 < 7 < +∞) → create node(7)
- Process left: deserializeHelper(queue, 5, 7) → queue empty, returns null
- Process right: deserializeHelper(queue, 7, +∞) → queue empty, returns null
Result: We get tree 5 -> 7 (missing node 10!)
Correct Behavior with queue.peek()
Deserialization process with queue.peek() (CORRECT):
Initial queue: [5, 10, 7]
1.Root call: deserializeHelper(queue, -∞, +∞)
- val = queue.peek() → val = 5, queue stays [5, 10, 7]
- 5 is in range → queue.poll(), queue becomes [10, 7]
- Create root(5)
- Process left: deserializeHelper(queue, -∞, 5)
2.Left subtree of 5: deserializeHelper(queue, -∞, 5)
- val = queue.peek() → val = 10, queue stays [10, 7]
- Check: 10 > 5 → OUT OF RANGE
- Return null ← Value 10 is still in queue!
3.Process right of root: deserializeHelper(queue, 5, +∞)
- val = queue.peek() → val = 10, queue stays [10, 7]
- 10 is in range (5 < 10 < +∞) → queue.poll(), queue becomes [7]
- Create node(10)
- Process left: deserializeHelper(queue, 5, 10)
- val = queue.peek() → val = 7, in range (5 < 7 < 10) → create node(7)
- Process right: deserializeHelper(queue, 10, +∞) → empty, returns null
Result: Correct tree 5 -> 10 -> 7 ✓
The Key Insight
- peek() lets us look at the next value without consuming it
- We only poll() when we're sure the value belongs to the current subtree
- If the value doesn't belong here, we leave it in the queue for the correct ancestor to process
This is crucial because in preorder traversal, values that don't belong to the current subtree must remain available for processing by the appropriate parent or ancestor node.
Your modified code with queue.poll() immediately would cause values to be lost whenever they don't belong to the current subtree, leading to incorrect tree reconstruction.


Refer to
L297.Serialize and Deserialize Binary Tree (Ref.L449)
