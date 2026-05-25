/**
 Refer to
 https://www.lintcode.com/problem/convert-binary-search-tree-to-sorted-doubly-linked-list/description
 Convert a BST to a sorted circular doubly-linked list in-place. Think of the left and right pointers as synonymous 
 to the previous and next pointers in a doubly-linked list.

 Let's take the following BST as an example, it may help you understand the problem better:
 
                  4
           2            5
        1     3

 We want to transform this BST into a circular doubly linked list. Each node in a doubly linked list has a predecessor 
 and successor. For a circular doubly linked list, the predecessor of the first element is the last element, and the 
 successor of the last element is the first element.

 The figure below shows the circular doubly linked list for the BST above. The "head" symbol means the node it points 
 to is the smallest element of the linked list.
 
              head  
          <------  <--   <--   <--   <--  <------
          |  --> 1 --> 2 --> 3 --> 4 --> 5 -->  |
          |  |                               |  |
          |  |-------------------------------|  |
          |-------------------------------------|
 
 Specifically, we want to do the transformation in place. After the transformation, the left pointer of the tree node 
 should point to its predecessor, and the right pointer should point to its successor. We should return the pointer 
 to the first element of the linked list.
 
 The figure below shows the transformed BST. The solid line indicates the successor relationship, while the dashed line 
 means the predecessor relationship.
              
  head
    1  --> successor --> 2   --> successor --> 3   --> successor -->  4   --> successor --> 5  --> successor --> 1 
    1  <-- predecessor <-- 2 <-- predecessor <-- 3 <-- predecessor <-- 4 <-- predecessor <-- 5 <-- predecessor <-- 1
*/

// Solution 1:
// Refer to
// https://www.cnblogs.com/grandyang/p/9615871.html
/**
这道题给了一个二叉搜索树，让我们将其转化为双向链表。并且题目中给了一个带图的例子，帮助理解。题目本身并不难理解，仔细观察下给的示例图。
首先，转化成双向链表的每个结点都有 left 和 right 指针指向左右两个结点，不管其原来是否是叶结点还是根结点，转换后统统没有区别。其次，
这是个循环双向链表，即首尾结点是相连的，原先的二叉搜索树中的最左结点和最右结点，现在也互相连接起来了。最后，返回的结点不再是原二叉
搜索树的根结点 root 了，而是最左结点，即最小值结点。

好，发现了上述规律后，来考虑如何破题。根据博主多年经验，跟二叉搜索树有关的题，肯定要利用其性质，即左<根<右，即左子结点值小于根结点值
小于右子结点值。而且十有八九都得用中序遍历来解，因为中序遍历的顺序就是左根右啊，跟性质吻合。观察到原二叉搜索树中结点4连接着结点2和结点5，
而在双向链表中，连接的是结点3和结点5，这就是为啥要用中序遍历了，因为只有中序遍历，结点3之后才会遍历到结点4，这时候可以将结点3和结点4
串起来。决定了用中序遍历之后，就要考虑是迭代还是递归的写法，博主建议写递归的，一般写起来都比较简洁，而且递归是解树类问题的神器啊，十有
八九都是用递归，一定要熟练掌握。再写中序遍历之前，其实还有难点，因为需要把相邻的结点连接起来，所以需要知道上一个遍历到的结点是什么，
所以用一个变量 pre，来记录上一个遍历到的结点。还需要一个变量 head，来记录最左结点，这样的话，在递归函数中，先判空，之后对左子结点
调用递归，这样会先一直递归到最左结点，此时如果 head 为空的话，说明当前就是最左结点，赋值给 head 和 pre，对于之后的遍历到的结点，
那么可以和 pre 相互连接上，然后 pre 赋值为当前结点 node，再对右子结点调用递归即可
class Solution {
public:
    Node* treeToDoublyList(Node* root) {
        if (!root) return NULL;
        Node *head = NULL, *pre = NULL;
        inorder(root, pre, head);
        pre->right = head;
        head->left = pre;
        return head;
    }
    void inorder(Node* node, Node*& pre, Node*& head) {
        if (!node) return;
        inorder(node->left, pre, head);
        if (!head) {
            head = node;
            pre = node;
        } else {
            pre->right = node;
            node->left = pre;
            pre = node;
        }
        inorder(node->right, pre, head);
    }
};
*/

// https://blog.csdn.net/zhangpeterx/article/details/89567460

/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 */

public class Solution {
    /**
     * @param root: root of a tree
     * @return: head node of a doubly linked list
     */
    // the smallest (first) and the largest (last) nodes
    TreeNode first = null;
    TreeNode last = null;
    public TreeNode treeToDoublyList(TreeNode root) {
        if(root == null) {
            return null;
        }
        helper(root);
        last.right = first;
        first.left = last;
        return first;
    }
    
    private void helper(TreeNode node) {
        if(node == null) {
            return;
        }
        helper(node.left);
        if(last != null) {
            // link the previous node (last)
            // with the current one (node)
            last.right = node;
            node.left = last;
            last = node;
        } else {
            // keep the smallest node to close 
            // doubly linked list later on
            first = node;
            last = node;
        }
        //last = node;
        helper(node.right);
    }
}















https://leetcode.ca/2017-01-29-426-Convert-Binary-Search-Tree-to-Sorted-Doubly-Linked-List/
Convert a Binary Search Tree to a sorted Circular Doubly-Linked List in place.
You can think of the left and right pointers as synonymous to the predecessor and successor pointers in a doubly-linked list. For a circular doubly linked list, the predecessor of the first element is the last element, and the successor of the last element is the first element.
We want to do the transformation in place. After the transformation, the left pointer of the tree node should point to its predecessor, and the right pointer should point to its successor. You should return the pointer to the smallest element of the linked list.
 
Example 1:
Let's take the following BST as an example, it may help you understand the problem better: 


 
We want to transform this BST into a circular doubly linked list. Each node in a doubly linked list has a predecessor and successor. For a circular doubly linked list, the predecessor of the first element is the last element, and the successor of the last element is the first element.
The figure below shows the circular doubly linked list for the BST above. The "head" symbol means the node it points to is the smallest element of the linked list. 


 
Specifically, we want to do the transformation in place. After the transformation, the left pointer of the tree node should point to its predecessor, and the right pointer should point to its successor. We should return the pointer to the first element of the linked list.
The figure below shows the transformed BST. The solid line indicates the successor relationship, while the dashed line means the predecessor relationship.



Example 2:
Input: root = [2,1,3]
Output: [1,2,3]
 
Constraints:
- The number of nodes in the tree is in the range [0, 2000].
- -1000 <= Node.val <= 1000
- All the values of the tree are unique.
--------------------------------------------------------------------------------
Attempt 1: 2026-05-25
Solution 1: In-order Traversal (30min)
Refer to Deepseek
Here's a Java solution for LeetCode 426 "Convert Binary Search Tree to Sorted Doubly Linked List" using in‑order traversal.
/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right) {
        val = _val;
        left = _left;
        right = _right;
    }
};
*/

class Solution {
    private Node first = null;  // head of the sorted list
    private Node last = null;   // tail of the sorted list

    public Node treeToDoublyList(Node root) {
        if (root == null) return null;
        
        inorder(root);
        
        // Make it circular: connect first and last
        first.left = last;
        last.right = first;
        
        return first;
    }
    
    private void inorder(Node node) {
        if (node == null) return;
        
        // Left subtree
        inorder(node.left);
        
        // Process current node
        if (last == null) {
            // This is the smallest node (first in list)
            first = node;
        } else {
            // Link previous node (last) with current node
            last.right = node;
            node.left = last;
        }
        last = node; // move last to current
        
        // Right subtree
        inorder(node.right);
    }
}
Key Points:
- Use an in‑order traversal (left → node → right) to visit nodes in increasing order.
- Maintain first (head) and last (tail) pointers.
- When visiting a node, link last.right to current node and current.left to last, then update last.
- After traversal, connect the first and last nodes to make the list circular.
- This solution runs in O(n) time and O(h) recursion stack space (where h is the tree height).

Can you show me how 'first' and 'last' node working process during the traversal ?
Sure! Let's walk through an example to see how first and last work during the in‑order traversal.
Example BST
Consider the following BST:
        4
       / \
      2   5
     / \
    1   3

In‑order traversal: left → node → right → yields sorted order: 1, 2, 3, 4, 5.
--------------------------------------------------------------------------------
Initial State
- first = null
- last = null
--------------------------------------------------------------------------------
Step-by-step Traversal
1. Start at root (4)
- Go left to node 2
- Go left to node 1
- Node 1 has no left child → process node 1
Process node 1:
- last == null → set first = node1
- last = node1
State:
first = 1
last = 1
List so far: 1

--------------------------------------------------------------------------------
2. Backtrack to node 2 (after finishing left subtree of 2)
- Process node 2:
- last != null → link last.right = node2 → 1.right = 2
- Link node2.left = last → 2.left = 1
- Update last = node2
State:
first = 1
last = 2
List: 1 <-> 2

--------------------------------------------------------------------------------
3. Go to right subtree of node 2 → node 3
- Node 3 has no left child → process node 3:
- last != null → link 2.right = 3, 3.left = 2
- Update last = 3
State:
first = 1
last = 3
List: 1 <-> 2 <-> 3

--------------------------------------------------------------------------------
4. Backtrack to node 4 (after finishing left subtree)
- Process node 4:
- Link 3.right = 4, 4.left = 3
- Update last = 4
State:
first = 1
last = 4
List: 1 <-> 2 <-> 3 <-> 4

--------------------------------------------------------------------------------
5. Go to right subtree of node 4 → node 5
- Node 5 has no left child → process node 5:
- Link 4.right = 5, 5.left = 4
- Update last = 5
Final state:
first = 1
last = 5
List: 1 <-> 2 <-> 3 <-> 4 <-> 5

--------------------------------------------------------------------------------
After traversal
We have a doubly linked list from first (1) to last (5).
To make it circular, we then do:
first.left = last;   // 1.left = 5
last.right = first;  // 5.right = 1

--------------------------------------------------------------------------------
Summary visual
StepNode processedfirstlastList state1111122121 ↔ 233131 ↔ 2 ↔ 344141 ↔ 2 ↔ 3 ↔ 455151 ↔ 2 ↔ 3 ↔ 4 ↔ 5
- first always points to the smallest node (first visited) and never changes after assignment.
- last moves forward each time we process a new node, always pointing to the most recently added node (the largest so far).
- Each new node is linked to the previous last, making the list grow in sorted order.

Refer to QWEN
Here's a clean, optimal Java solution for LeetCode 426. Convert Binary Search Tree to Sorted Doubly Linked List using Inorder Traversal:
class Solution {
    private Node prev = null;
    private Node head = null;

    public Node treeToDoublyList(Node root) {
        if (root == null) return null;
        
        // Reset for safety across multiple test cases
        prev = null;
        head = null;
        
        inorder(root);
        
        // Connect head and tail to make it circular
        head.left = prev;
        prev.right = head;
        
        return head;
    }

    private void inorder(Node curr) {
        if (curr == null) return;
        
        // 1. Traverse left subtree
        inorder(curr.left);
        
        // 2. Visit current node
        if (prev == null) {
            head = curr; // First visited node is the smallest (head)
        } else {
            prev.right = curr;
            curr.left = prev;
        }
        prev = curr; // Update previous pointer
        
        // 3. Traverse right subtree
        inorder(curr.right);
    }
}
🔍 How it Works:
1.Inorder Property: BST inorder traversal (left → root → right) naturally visits nodes in ascending order.
2.Pointer Manipulation: As we visit each node, we link it to prev (the node visited just before it). prev.right = curr and curr.left = prev build the DLL incrementally.
3.Head Tracking: The first node visited (leftmost) becomes head.
4.Circular Closure: After traversal, prev points to the last visited node (rightmost/tail). We connect head.left = tail and tail.right = head to satisfy the circular requirement.
⏱️ Complexity:
- Time: O(N) where N is the number of nodes. Each node is visited exactly once.
- Space: O(H) auxiliary space for the recursion stack, where H is tree height. O(log N) average, O(N) worst-case (skewed tree).
💡 Key Interview Insights:
ConceptWhy It MattersInorder = SortedBST + sorted output → always consider inorder traversal first.In-Place ModificationWe're only rewiring existing pointers. No new nodes created → O(1) extra space (excluding call stack).Circular LinkageDon't forget the final head ↔ tail connection. Many candidates lose points here.State ResetAlways reset class-level trackers (prev, head) in the public method. LeetCode sometimes reuses Solution instances across test cases.
🔄 Iterative Alternative (Zero Recursion Stack)
If you want to avoid recursion depth limits, here's the stack-based version:
public Node treeToDoublyList(Node root) {
    if (root == null) return null;
    Node prev = null, head = null;
    Stack<Node> stack = new Stack<>();
    Node curr = root;
    
    while (curr != null || !stack.isEmpty()) {
        while (curr != null) {
            stack.push(curr);
            curr = curr.left;
        }
        curr = stack.pop();
        
        if (prev == null) head = curr;
        else { prev.right = curr; curr.left = prev; }
        prev = curr;
        
        curr = curr.right;
    }
    
    head.left = prev;
    prev.right = head;
    return head;
}
Use iterative if explicitly asked to optimize space or handle deeply skewed trees.
Let me know if you'd like a visual walkthrough of the pointer rewiring process or a comparison with Morris Traversal (O(1) space)!

Refer to Kimi
Here is a clean recursive Java solution for LeetCode 426. Convert Binary Search Tree to Sorted Doubly Linked List.
Approach
An in-order traversal of a BST naturally visits nodes in ascending sorted order. During the traversal, we rewire the left and right pointers to act as prev and next pointers of a doubly linked list.
- first — tracks the head of the list (smallest node, first visited).
- last — tracks the tail of the list so far (most recently visited node).
After the traversal, we connect first and last to make the list circular.
Time Complexity: O(n)
Space Complexity: O(h) — recursion stack, where h is the height of the tree
/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right) {
        val = _val;
        left = _left;
        right = _right;
    }
};
*/

class Solution {
    private Node first = null;
    private Node last = null;
    
    public Node treeToDoublyList(Node root) {
        if (root == null) return null;
        
        inorder(root);
        
        // Close the circular DLL
        last.right = first;
        first.left = last;
        
        return first;
    }
    
    private void inorder(Node node) {
        if (node == null) return;
        
        // 1. Traverse left subtree
        inorder(node.left);
        
        // 2. Process current node
        if (first == null) {
            // This is the leftmost (smallest) node — the head of the list
            first = node;
        } else {
            // Link the previous node (last) with current
            last.right = node;
            node.left = last;
        }
        last = node;  // Update tail to current node
        
        // 3. Traverse right subtree
        inorder(node.right);
    }
}
How it works
StepActionIn-order traversalVisits nodes in ascending order: 1 → 2 → 3 → 4 → 5firstSet once at the very first node visited (1)lastUpdated at every step; before moving to the next node, links last.right = curr and curr.left = lastFinal wiringlast.right = first and first.left = last closes the circle
If you prefer an iterative solution (using a stack to avoid recursion), let me know and I can provide that version too.
