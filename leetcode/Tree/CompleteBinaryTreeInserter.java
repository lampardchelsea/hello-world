/**
 Refer to
 https://leetcode.com/problems/complete-binary-tree-inserter/
 A complete binary tree is a binary tree in which every level, except possibly the last, is completely filled, 
 and all nodes are as far left as possible.
 Write a data structure CBTInserter that is initialized with a complete binary tree and supports the following operations:
 CBTInserter(TreeNode root) initializes the data structure on a given tree with head node root;
 CBTInserter.insert(int v) will insert a TreeNode into the tree with value node.val = v so that the 
 tree remains complete, and returns the value of the parent of the inserted TreeNode;
 CBTInserter.get_root() will return the head node of the tree.
 
 Example 1:
 Input: inputs = ["CBTInserter","insert","get_root"], inputs = [[[1]],[2],[]]
 Output: [null,1,[1,2]]
 
 Example 2:
 Input: inputs = ["CBTInserter","insert","insert","get_root"], inputs = [[[1,2,3,4,5,6]],[7],[8],[]]
 Output: [null,3,4,[1,2,3,4,5,6,7,8]]
 
 Note:
 The initial given tree is complete and contains between 1 and 1000 nodes.
 CBTInserter.insert is called at most 10000 times per test case.
 Every value of a given or inserted node is between 0 and 5000.
*/

// Solution 1: Queue + List + parent / child index relationship
// Refer to
// https://leetcode.com/problems/complete-binary-tree-inserter/discuss/178424/C%2B%2BJavaPython-O(1)-Insert
/**
 Store tree nodes to a list self.tree in bfs order.
 Node tree[i] has left child tree[2 * i + 1] and tree[2 * i + 2]
 So when insert the Nth node (0-indexed), we push it into the list.
 we can find its parent tree[(N - 1) / 2] directly.
 
    List<TreeNode> tree;
    public CBTInserter(TreeNode root) {
        tree = new ArrayList<>();
        tree.add(root);
        for (int i = 0; i < tree.size(); ++i) {
            if (tree.get(i).left != null) tree.add(tree.get(i).left);
            if (tree.get(i).right != null) tree.add(tree.get(i).right);
        }
    }

    public int insert(int v) {
        int N = tree.size();
        TreeNode node = new TreeNode(v);
        tree.add(node);
        if (N % 2 == 1)
            tree.get((N - 1) / 2).left = node;
        else
            tree.get((N - 1) / 2).right = node;
        return tree.get((N - 1) / 2).val;
    }

    public TreeNode get_root() {
        return tree.get(0);
    }
    
 Time Complexity: CBTInserter, O(n). insert, O(1). get_root, O(1).
 Space: O(n). queue space.
*/

// https://leetcode.com/problems/complete-binary-tree-inserter/discuss/611953/Java-Simple-solution-(with-FULL-EXPLANATION)
/**
 Idea:
Since the binary tree is initialized with "completeness" and the insertion is such that the binary tree should remain 
"complete", we can imagine the nodes stored in an array.

For example,
[1,2,3,4,5,6]
Assume that these are the nodes stored in an array. So, the root will be the first element, and other elements are stored 
in the level order traversal in an array.
Node at the nth index will have their children stored at 2n + 1th and 2n + 2th index in the array.
Initialize the array with the given binary tree using level order traversal
when the new value is inserted, add it to the end of the list, get its parent, form the connection between parent and new node.
0th element will always be the node.
*/
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
class CBTInserter {
    List<TreeNode> list; 
    public CBTInserter(TreeNode root) {
        list = new ArrayList<TreeNode>();
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.offer(root);
        while(!q.isEmpty()) {
            TreeNode node = q.poll();
            list.add(node);
            if(node.left != null) {
                q.offer(node.left);
            }
            if(node.right != null) {
                q.offer(node.right);
            }
        }
    }
    
    public int insert(int v) {
        TreeNode newNode = new TreeNode(v);
        list.add(newNode);
        TreeNode parent = list.get((list.size() - 2) / 2);
        if(parent.left == null) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        return parent.val;
    }
    
    public TreeNode get_root() {
        return list.get(0);
    }
}

/**
 * Your CBTInserter object will be instantiated and called as such:
 * CBTInserter obj = new CBTInserter(root);
 * int param_1 = obj.insert(v);
 * TreeNode param_2 = obj.get_root();
 */

// Solution 2: If not introduce parent child index relationship then use normal strategy to find first node not have both left/right child
// Refer to
// https://www.cnblogs.com/Dylan-Java-NYC/p/11145099.html
/**
 题解：
 Do level order traversal on the tree. Keep the nodes that doesn't have both left and right child in the queue.
 Pop the first node in the queue and mark it as current.
 When inserting, check current node's left, if null, insert it as left child. Or check current node's right, if null, 
 insert it as right child. If both not null, then pop another node from queue and insert it as left child.
 Time Complexity: CBTInserter, O(n). insert, O(1). get_root, O(1).
 Space: O(n). queue space.
 
 Note: After my test, the thing not very clear is about "If both not null, then pop another node from queue and insert it as left child."
 Below 4 cases not include above case
           1                      1                     1                     1
        2   3                  2   3                 2   3                 2   3
                             4                     4  5                  4  5 6
        queue -> 1           queue -> 1            queue -> 1            queue -> 1
        top -> 1             top -> 1              top -> 1              top -> 1
        queue -> 2,3         queue -> 2,3          queue -> 2,3          queue -> 2,3
        cur -> 2             cur -> 2              top -> 2              top -> 2
        queue -> 3           queue -> 3,4          queue -> 3,4,5        queue -> 3,4,5
        insert 4             insert 5              cur -> 3              cur -> 3
        queue -> 3,4         queue -> 3,4,5        queue -> 4,5          queue -> 4,5,6
        return 2             return 2              insert 6              insert 7
                                                   queue -> 4,5,6        queue -> 4,5,6,7
                                                   return 3              return 3
*/
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class CBTInserter {
    TreeNode root;
    TreeNode cur;
    LinkedList<TreeNode> que;
    public CBTInserter(TreeNode root) {
        this.root = root;
        que = new LinkedList<TreeNode>();
        que.add(root);
        while(que.peek().left != null && que.peek().right != null){
            TreeNode top = que.poll();
            que.add(top.left);
            que.add(top.right);
        }

        cur = que.poll();
        if(cur.left != null){
            que.add(cur.left);
        }
    }

    public int insert(int v) {
        TreeNode newNode = new TreeNode(v);
        que.add(newNode);
        if(cur.left == null){
            cur.left = newNode;
        }else if(cur.right == null){
            cur.right = newNode;
        }else{
            cur = que.poll();
            cur.left = newNode;
        }
        return cur.val;
    }

    public TreeNode get_root() {
        return this.root;
    }
}

/**
 * Your CBTInserter object will be instantiated and called as such:
 * CBTInserter obj = new CBTInserter(root);
 * int param_1 = obj.insert(v);
 * TreeNode param_2 = obj.get_root();
 */
