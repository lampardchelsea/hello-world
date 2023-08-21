/**
 * Refer to
 * https://leetcode.com/problems/populating-next-right-pointers-in-each-node/description/
 * Given a binary tree

    struct TreeLinkNode {
      TreeLinkNode *left;
      TreeLinkNode *right;
      TreeLinkNode *next;
    }

    Populate each next pointer to point to its next right node. If there is no next right node, 
    the next pointer should be set to NULL.

    Initially, all next pointers are set to NULL.
    Note:
    You may only use constant extra space.
    You may assume that it is a perfect binary tree (ie, all leaves are at the same level, and 
    every parent has two children).
    
    For example,
    Given the following perfect binary tree,
             1
           /  \
          2    3
         / \  / \
        4  5  6  7
    
    After calling your function, the tree should look like:
             1 -> NULL
           /  \
          2 -> 3 -> NULL
         / \  / \
        4->5->6->7 -> NULL
 *
 * Solution
 * https://leetcode.com/problems/populating-next-right-pointers-in-each-node/discuss/37461/Java-solution-with-O(1)-memory+-O(n)-time
 * https://leetcode.com/problems/populating-next-right-pointers-in-each-node/discuss/37603/Java-solution-traversing-by-level-without-extra-space
 * https://www.youtube.com/watch?v=3MFL7L8HnUc&t=233s
*/
/**
 * Definition for binary tree with next pointer.
 * public class TreeLinkNode {
 *     int val;
 *     TreeLinkNode left, right, next;
 *     TreeLinkNode(int x) { val = x; }
 * }
 */
// Solution 1: Recursive
/**
   Strategy as pre-order traverse
             1
           /  \
          2    3
         / \  / \
        4  5  6  7
   If 4 has left child, then after connect 5,6 will not connect 
   6,7 directly after that because it will go as pre-order traverse
*/
public class Solution {
    public void connect(TreeLinkNode root) {
        if(root == null) {
            return;
        }
        // No need to add condition as root.right != null
        // since given perfect binary tree
        // if(root.left != null && root.right != null) {
        // To handle connection between 2->3, 4->5, 6->7
        if(root.left != null) {
            root.left.next = root.right;
        }
        // To handle connection between 5->6
        // current root = 2, 2.next = 3, 3.left = 6,
        // 2.right = 5, to connect 5 and 6 use 5.next = 6
        if(root.next != null && root.right != null) {
            root.right.next = root.next.left;
        }
        connect(root.left);
        connect(root.right);
    }
}

// Solution 2: Iterative
/**
   Strategy as level order traverse
*/
/**
 * Definition for binary tree with next pointer.
 * public class TreeLinkNode {
 *     int val;
 *     TreeLinkNode left, right, next;
 *     TreeLinkNode(int x) { val = x; }
 * }
 */
public class Solution {
    public void connect(TreeLinkNode root) {
        if(root == null) {
            return;
        }
        // 'start' node will record each level's left most node
        TreeLinkNode start = root;
        // Condition based on level exist or not
        while(start != null) {
            // 'cur' node used for traverse current level,
            // start with 'start'
            TreeLinkNode cur = start;
            // Traverse on each level
            while(cur != null) {
                if(cur.left != null) {
                    cur.left.next = cur.right;
                }
                if(cur.next != null && cur.right != null) {
                    cur.right.next = cur.next.left;
                }
                // Move forward on current level
                cur = cur.next;
            }
            // Go to next level
            start = start.left;
        }
    }
}

// More concise way and eliminate 'start' node as redundant
/**
 * Definition for binary tree with next pointer.
 * public class TreeLinkNode {
 *     int val;
 *     TreeLinkNode left, right, next;
 *     TreeLinkNode(int x) { val = x; }
 * }
 */
public class Solution {
    public void connect(TreeLinkNode root) {
        if(root == null) {
            return;
        }
        while(root != null) {
            // Traverse start as 'cur' on current level
            TreeLinkNode cur = root;
            while(cur != null) {
                if(cur.left != null) {
                    cur.left.next = cur.right;
                }
                if(cur.next != null && cur.right != null) {
                    cur.right.next = cur.next.left;
                }
                cur = cur.next;
            }
            // Move to next level
            root = root.left;
        }
    }
}































































































https://leetcode.com/problems/populating-next-right-pointers-in-each-node/description/

You are given a perfect binary tree where all leaves are on the same level, and every parent has two children. The binary tree has the following definition:
```
struct Node {
  int val;
  Node *left;
  Node *right;
  Node *next;
}
```

Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.

Initially, all next pointers are set to NULL.

Example 1:


```
Input: root = [1,2,3,4,5,6,7]
Output: [1,#,2,3,#,4,5,6,7,#]
Explanation: Given the above perfect binary tree (Figure A), your function should populate each next pointer to point to its next right node, just like in Figure B. The serialized output is in level order as connected by the next pointers, with '#' signifying the end of each level.
```

Example 2:
```
Input: root = []
Output: []
```

Constraints:
- The number of nodes in the tree is in the range [0, 212 - 1].
- -1000 <= Node.val <= 1000
 
Follow-up:
- You may only use constant extra space.
- The recursive approach is fine. You may assume implicit stack space does not count as extra space for this problem.
---
Attempt 1: 2023-08-19

Solution 1: BFS (30min)

Style 1: Left to right
```
/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;
    public Node() {}
    
    public Node(int _val) {
        val = _val;
    }
    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};
*/
class Solution {
    public Node connect(Node root) {
        if(root == null) {
            return null;
        }
        Queue<Node> q = new LinkedList<Node>();
        q.offer(root);
        while(!q.isEmpty()) {
            int size = q.size();
            Node prev = null;
            for(int i = 0; i < size; i++) {
                Node cur = q.poll();
                // From the second node, previous node next
                // node connect to current node
                if(i > 0) {
                    prev.next = cur;
                }
                prev = cur;
                // No need to check cur.right != null since 
                // L116 is perfect binary tree, if we have
                // seen left node will always have right node
                if(cur.left != null) {
                    q.offer(cur.left);
                    q.offer(cur.right);
                }
            }
        }
        return root;
    }
}

Time Complexity : O(N), where N is the number of nodes in the given tree. We only traverse the tree once using BFS which requires O(N).
Space Complexity : O(W) = O(N), where W is the width of given tree. This is required to store the nodes in queue. Since the given tree is a perfect binary tree, its width is given as W = (N+1)/2 ≈ O(N)
```

Refer to
https://leetcode.wang/leetcode-116-Populating-Next-Right-Pointers-in-Each-Node.html

解法一 BFS

如果没有要求空间复杂度这道题就简单多了，我们只需要用一个队列做BFS，BFS参见 102 题。然后按顺序将每个节点连起来就可以了。
```
public Node connect(Node root) {
    if (root == null) {
        return root;
    }
    Queue<Node> queue = new LinkedList<Node>();
    queue.offer(root);
    while (!queue.isEmpty()) {
        int size = queue.size();
        Node pre = null;
        for (int i = 0; i < size; i++) {
            Node cur = queue.poll();
            //从第二个节点开始，将前一个节点的 pre 指向当前节点
            if (i > 0) {
                pre.next = cur;
            }
            pre = cur;
            if (cur.left != null) {
                queue.offer(cur.left);
            }
            if (cur.right != null) {
                queue.offer(cur.right);
            }
        }
    }
    return root;
}
```

Style 2: Right to left
```
/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;
    public Node() {}
    
    public Node(int _val) {
        val = _val;
    }
    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};
*/
class Solution {
    public Node connect(Node root) {
        if(root == null) {
            return null;
        }
        Queue<Node> q = new LinkedList<Node>();
        q.offer(root);
        while(!q.isEmpty()) {
            int size = q.size();
            Node rightNode = null;
            // The for loop direction doesn't matter, the insertion
            // of right child first only matters, which means we
            // always poll out right child first than left child,
            // right child's next pointer connect to 'rightNode' first
            //for(int i = size - 1; i >= 0; i--) {
            for(int i = 0; i < size; i++) {
                Node cur = q.poll();
                cur.next = rightNode;
                rightNode = cur;
                // Make sure insert from right child node to 
                // left child node into queue
                if(cur.right != null) {
                    q.offer(cur.right);
                    q.offer(cur.left);
                }
            }
        }
        return root;
    }
}

Time Complexity : O(N), where N is the number of nodes in the given tree. We only traverse the tree once using BFS which requires O(N).
Space Complexity : O(W) = O(N), where W is the width of given tree. This is required to store the nodes in queue. Since the given tree is a perfect binary tree, its width is given as W = (N+1)/2 ≈ O(N)
```

Refer to
https://leetcode.com/problems/populating-next-right-pointers-in-each-node/solutions/1654181/c-python-java-simple-solution-w-images-explanation-bfs-dfs-o-1-optimized-bfs/
✔️ Solution - I (BFS - Right to Left)
It's important to see that the given tree is a perfect binary tree. This means that each node will always have both children and only the last level of nodes will have no children.


Now, we need to populate next pointers of each node with nodes that occur to its immediate right on the same level. This can easily be done with BFS. Since for each node, we require the right node on the same level, we will perform a right-to-left BFS instead of the standard left-to-right BFS.

Before starting the traversal of each level, we would initialize a rightNode variable set to NULL. Then, since we are performing right-to-left BFS, we would be starting at rightmost node of each level. We set the next node of cur as rightNode and update rightNode = cur. This would ensure that each node would be assigned its rightNode properly while traversing from right to left.Also, if cur has a child, we would first push its right child and only then its left child (since we are doing right-to-left BFS). Once BFS is completed (after queue becomes empty), all next node would be populated and we can finally return root.

The process is illustrated below -








C++ / Java
```
class Solution {
public:
    Node* connect(Node* root) {
        if(!root) return nullptr;
        queue<Node*> q;
        q.push(root);        
        while(size(q)) {
            Node* rightNode = nullptr;                    // set rightNode to null initially
            for(int i = size(q); i; i--) {                // traversing each level
                auto cur = q.front(); q.pop();            // pop a node from current level and,
                cur -> next = rightNode;                  // set its next pointer to rightNode
                rightNode = cur;                          // update rightNode as cur for next iteration
                if(cur -> right)                          // if a child exists
                    q.push(cur -> right),                 // IMP: push right first to do right-to-left BFS
                    q.push(cur -> left);                  // then push left
            }
        }
        return root;
    }
};
===========================================================================================================
class Solution {
    public Node connect(Node root) {
        if(root == null) return null;
        Queue<Node> q = new LinkedList<>();
        q.offer(root);
        while(!q.isEmpty()) {
            Node rightNode = null;
            for(int i = q.size(); i > 0; i--) {
                Node cur = q.poll();
                cur.next = rightNode;
                rightNode = cur;
                if(cur.right != null) {
                    q.offer(cur.right);
                    q.offer(cur.left);
                }
            }
        }
        return root;        
    }
}
```
Time Complexity : O(N), where N is the number of nodes in the given tree. We only traverse the tree once using BFS which requires O(N).
Space Complexity : O(W) = O(N), where W is the width of given tree. This is required to store the nodes in queue. Since the given tree is a perfect binary tree, its width is given as W = (N+1)/2 ≈ O(N)
---
Solution 2: DFS (30min)
```
/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;
    public Node() {}
    
    public Node(int _val) {
        val = _val;
    }
    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};
*/
class Solution {
    public Node connect(Node root) {
        return helper(root);
    }
    private Node helper(Node node) {
        // Base condition
        if(node == null) {
            return null;
        }
        //          node -------------------> node.next
        //        /      \                  /          \
        // node.left --> node.right --> node.next.left ...
        //           (1)            (2)  
        if(node.left != null) {
            node.left.next = node.right;  // (1) connect
            if(node.next != null) {
                node.right.next = node.next.left; // (2) connect
            }
        }
        helper(node.left);
        helper(node.right);
        return node;
    }
}

Time Complexity : O(N), each node is only traversed once
Space Complexity : O(logN), required for recursive stack. The maximum depth of recursion is equal to the height of tree which in this case of perfect binary tree is equal to O(logN)
```

Refer to
https://leetcode.com/problems/populating-next-right-pointers-in-each-node/solutions/1654181/c-python-java-simple-solution-w-images-explanation-bfs-dfs-o-1-optimized-bfs/
✔️ Solution - II (DFS)
We can also populate the next pointers recursively using DFS. This is slightly different logic than above but relies on the fact that the given tree is a perfect binary tree.

In the above solution, we had access to right nodes since we traversed in level-order. But in DFS, once we go to the next level, we cant get access to right node. So, we must update next pointers of the child of each node from the its parent's level itself. 

Thus at each recursive call -
- If child node exists:
	- assign next of left child node as right child node: root -> left -> next = root -> right. Note that, if once child exists, the other exists as well.
	- assign next of right child node as left child of root's next (if root's next exists): root -> right -> next = root -> next -> left.
	  How? We need right immediate node of right child. This wont exist if current root's next node doesn't exists. If next node of current root is present (the next pointer of root would already be populated in above level) , the right immediate node of root's right child must be root's next's left child because if child of root exists, then the child of root's next must also exist.
- If child node doesn't exist, we have reached the last level, we can directly return since there's no child nodes to populate their next pointers


The process is very similar to the one illustrated in the image below with just the difference that we are traversing with DFS instead of BFS shown below.

C++ / Java
```
class Solution {
public:
    Node* connect(Node* root) {
        if(!root) return nullptr;
        auto L = root -> left, R = root -> right, N = root -> next;
        if(L) {
            L -> next = R;                                // next of root's left is assigned as root's right
            if(N) R -> next = N -> left;                  // next of root's right is assigned as root's next's left (if root's next exist)
            connect(L);                                   // recurse left  - simple DFS 
            connect(R);                                   // recurse right
        }
        return root;
    }
};
===========================================================================================================
class Solution {
    public Node connect(Node root) {
        if(root == null) return null;
        Node L = root.left, R = root.right, N = root.next;
        if(L != null) {
            L.next = R;
            if(N != null) R.next = N.left;
            connect(L);
            connect(R);
        }
        return root;
    }
}
```
Time Complexity : O(N), each node is only traversed once
Space Complexity : O(logN), required for recursive stack. The maximum depth of recursion is equal to the height of tree which in this case of perfect binary tree is equal to O(logN)
---
Solution 3: BFS - Space-Optimized (60min)
```
/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}
    
    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};
*/

class Solution {
    public Node connect(Node root) {
        Node iter = root;
        for(; iter != null; iter = iter.left) {
            for(Node cur = iter; cur != null; cur = cur.next) {
                // 提前把下一层的next构造完成，到了下一层的时候就可以遍历了
                if(cur.left != null) {
                    cur.left.next = cur.right;
                    if(cur.next != null) {
                        cur.right.next = cur.next.left;
                    }
                } else {
                    break;
                }
            }
        }
        return root;
    }
}

Time Complexity : O(N), we only traverse each node once, basically doing a standard BFS.
Space Complexity : O(1), only constant extra space is being used
```

Refer to
https://leetcode.com/problems/populating-next-right-pointers-in-each-node/solutions/1654181/c-python-java-simple-solution-w-images-explanation-bfs-dfs-o-1-optimized-bfs/
✔️ Solution - III (BFS - Space-Optimized Appraoch)
This is a combination of logic of above logics- we will traverse in BFS manner but populate the next pointers of bottom level just as we did in the DFS solution.

Usually standard DFS/BFS takes O(N) space, but since we are given the next pointers in each node, we can use them to space-optimize our traversal to O(1).
- We first populate the next pointers of child nodes of current level. This makes it possible to traverse the next level without using a queue. To populate next pointers of child, the exact same logic as above is used
- We simply traverse to root's left child and repeat the process - traverse current level, fill next pointers of child nodes and then again update root = root -> left. So, we are basically performing standard BFS traversal in O(1) space by using next pointers to our advantage
- The process continues till we reach the last level of tree

The process is illustrated in images below -
1.We start with a perfect binary tree with all next pointers initially NULL

2.We start traversal level-by-level, from left to right on each level
```
cur = root
```
Every iteration, the next pointers of a node's child will be updated
```
if(cur -> left) {
    cur -> left -> next = cur -> right;
    if(cur -> next) cur -> right -> next = cur -> next -> left;
}
```


3.Move to next level
```
root = root -> left
// next iteration
cur = root
```
& repeat:
```
if(cur -> left) {
    cur -> left -> next = cur -> right;
    if(cur -> next) cur -> right -> next = cur -> next -> left;
}
```


4.Continue the same process with all nodes on current level
```
for(; cur; cur = cur -> next)
    // ...
```


5.No child node exists
```
if(cur -> left)
    // ...
else break
```
So, we break here. On the next iteration, root becomes NULL as well and we stop the process.


C++ / Java
```
class Solution {
public:
    Node* connect(Node* root) {
        auto head = root;
        for(; root; root = root -> left) 
            for(auto cur = root; cur; cur = cur -> next)   // traverse each level - it's just BFS taking advantage of next pointers          
                if(cur -> left) {                          // update next pointers of children if they exist               
                    cur -> left -> next = cur -> right;
                    if(cur -> next) cur -> right -> next = cur -> next -> left;
                }
                else break;                                // if no children exist, stop iteration                                                  
        
        return head;
    }
};
===========================================================================================================
class Solution {
    public Node connect(Node root) {
        Node head = root;
        for(; root != null; root = root.left) 
            for(Node cur = root; cur != null; cur = cur.next) 
                if(cur.left != null) {
                    cur.left.next = cur.right;
                    if(cur.next != null) cur.right.next = cur.next.left;
                } else break;
        
        return head;
    }
}
```
Time Complexity : O(N), we only traverse each node once, basically doing a standard BFS.
Space Complexity : O(1), only constant extra space is being used
---
https://leetcode.wang/leetcode-116-Populating-Next-Right-Pointers-in-Each-Node.html

解法二 迭代

当然既然题目要求了空间复杂度，那么我们来考虑下不用队列该怎么处理。只需要解决三个问题就够了。
- 每一层怎么遍历？
  之前是用队列将下一层的节点保存了起来。
  这里的话，其实只需要提前把下一层的next构造完成，到了下一层的时候就可以遍历了。
- 什么时候进入下一层？
  之前是得到当前队列的元素个数，然后遍历那么多次。
  这里的话，注意到最右边的节点的next为null，所以可以判断当前遍历的节点是不是null。
- 怎么得到每层开头节点？
  之前队列把当前层的所以节点存了起来，得到开头节点当然很容易。
  这里的话，我们额外需要一个变量把它存起来。

三个问题都解决了，就可以写代码了。利用三个指针，start 指向每层的开始节点，cur指向当前遍历的节点，pre指向当前遍历的节点的前一个节点。

如上图，我们需要把 pre 的左孩子的 next 指向右孩子，pre 的右孩子的next指向cur的左孩子。

如上图，当 cur 指向 null 以后，我们只需要把 pre 的左孩子的 next 指向右孩子。
```
public Node connect(Node root) {
    if (root == null) {
        return root;
    }
    Node pre = root;
    Node cur = null;
    Node start = pre;
    while (pre.left != null) {
        //遍历到了最右边的节点，要将 pre 和 cur 更新到下一层，并且用 start 记录
        if (cur == null) {
            //我们只需要把 pre 的左孩子的 next 指向右孩子。
            pre.left.next = pre.right;

            pre = start.left;
            cur = start.right;
            start = pre;
        //将下一层的 next 连起来，同时 pre、next 后移
        } else {
            //把 pre 的左孩子的 next 指向右孩子
            pre.left.next = pre.right;
            //pre 的右孩子的 next 指向 cur 的左孩子。
            pre.right.next = cur.left;

            pre = pre.next;
            cur = cur.next;
        }
    }
    return root;
}
```
分享下 leetcode 的高票回答的代码，看起来更简洁一些，C++ 写的。
```
void connect(TreeLinkNode *root) {
    if (root == NULL) return;
    TreeLinkNode *pre = root;
    TreeLinkNode *cur = NULL;
    while(pre->left) {
        cur = pre;
        while(cur) {
            cur->left->next = cur->right;
            if(cur->next) cur->right->next = cur->next->left;
            cur = cur->next;
        }
        pre = pre->left;
    }
}
```
我的代码里的变量和他的变量对应关系如下。
```
我的 start    pre    cur
      |       |      |
他的  pre     cur    cur.next
```
除了变量名不一样，算法本质还是一样的。

总

题目让我们初始化 next 指针，初始化过程中我们又利用到了next指针，很巧妙了。
