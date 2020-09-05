/**
Refer to
https://blog.csdn.net/qq_46105170/article/details/108270779
https://leetcode.com/discuss/interview-question/417039/Amazon-or-Onsite-or-Clone-N-ary-Tree
https://leetcode.jp/leetcode-1490-clone-n-ary-tree-%e8%a7%a3%e9%a2%98%e6%80%9d%e8%b7%af%e5%88%86%e6%9e%90/
Given a root of an N-ary tree, return a deep copy (clone) of the tree.
Each node in the n-ary tree contains a val (int) and a list (List[Node]) of its children.

class Node {
    public int val;
    public List<Node> children;
}

Nary-Tree input serialization is represented in their level order traversal, 
each group of children is separated by the null value (See examples).
Follow up: Can your solution work for the graph problem?

Example 1:
            1
         3  2  4
       5   6

Input: root = [1,null,3,2,4,null,5,6]
Output: [1,null,3,2,4,null,5,6]

Example 2:
            1
      2   3   4   5
         6 7  8  9 10
           11 12 13
           14

Input: root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
Output: [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]

Constraints:
The depth of the n-ary tree is less than or equal to 1000.
The total number of nodes is between [0, 10^4].
*/

// Relation between clone binary tree and clone N-ary tree
// Refer to
// https://leetcode.com/discuss/interview-question/417039/Amazon-or-Onsite-or-Clone-N-ary-Tree/376167
/**
 A deep copy of an N-ary tree should be similar to a deep copy of a binary tree. The only difference would be, 
 instead of traversing left and right children, we traverse a list of children like below. 
 Time Complexity O(N) and Space Complexity O(N) for recursive call
*/
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) {
        this.val = val;
    }
}

class TreeNode {
    int val;
    List < TreeNode > children;
    TreeNode(int val) {
        this.val = val;
    }
}

Deep - Copy of a Binary Tree

/*  called as  TreeNode dup = obj.deepCopy(root, null);  */

private TreeNode deepCopy(TreeNode root, TreeNode dup) {
    if (root == null) return null;
    if (dup == null) dup = new TreeNode(root.val);
    return deepCopyRecursive(root, dup);
}

private TreeNode deepCopyRecursive(TreeNode node1, TreeNode node2) {
    if (node1 == null) return node2;
    if (node2 == null) {
        node2 = new TreeNode(node1.val);
        //return node2; //Do not return as we want to go deep in the depth
    }

    node2.left = deepCopyRecursive(node1.left, node2.left);
    node2.right = deepCopyRecursive(node1.right, node2.right);

    return node2;
}

Now instead of a left and a right child, we loop through a list of children
Deep - Copy of an N - arry tree

private TreeNode deepCopy(TreeNode root, TreeNode dup) {
    if (root == null) return null;
    if (dup == null) dup = new TreeNode(root.val);
    return deepCopyRecursive(root, dup);
}

private TreeNode deepCopyRecursive(TreeNode node1, TreeNode node2) {
    if (node1 == null) return null;
    if (node2 == null) node2 = new TreeNode(node1.val);
    if (node1.children != null) {
        node2.children = new ArrayList < > ();
        for (int i = 0; i < node1.children.size(); i++) {
            node2.children.add(null); //this step is required to make sure, when a recursive call is made, we are able to pass node2.children.get(i)
        }
        for (int i = 0; i < node1.children.size(); i++) {
            node2.children.add(i, deepCopyRecursive(node1.children.get(i), node2.children.get(i)));
        }
    }
    return node2;
}

// Solution 1: DFS with helper
// Refer to
// https://blog.csdn.net/qq_46105170/article/details/108270779
/**
 给定一棵NN叉树，要求对其深拷贝。思路是DFS，如果树空则返回null，否则先拷贝树根，然后对其每个孩子进行深拷贝，加入拷贝出来的树根的孩子中去
 时间复杂度O(n)，空间O(h)（递归栈深度）
*/
class Node {
    public int val;
    public List < Node > children;
    public Node() {
        children = new ArrayList < > ();
    }
    public Node(int _val) {
        val = _val;
        children = new ArrayList < > ();
    }
    public Node(int _val, List < Node > _children) {
        val = _val;
        children = _children;
    }
}

public class Solution {
    public Node cloneTree(Node root) {
        return dfs(root);
    }

    private Node dfs(Node root) {
        // 树空则返回null
        if (root == null) {
            return null;
        }
        // 拷贝树根
        Node res = new Node(root.val);
        // 遍历原树的孩子节点，分别拷贝之后加入新拷贝出来的树根的孩子里去
        for (Node child: root.children) {
            res.children.add(dfs(child));
        }
        // 返回新拷贝的树根
        return res;
    }
}

// Solution 2: DFS without helper
// Refer to
// https://leetcode.jp/leetcode-1490-clone-n-ary-tree-%e8%a7%a3%e9%a2%98%e6%80%9d%e8%b7%af%e5%88%86%e6%9e%90/
/**
这道题考查的是深度拷贝，在java语言中，使用等号赋值实际上只是地址（指针）拷贝，并不能实现深度拷贝的效果。
因此对于每个节点对象我们都必须重新new一个新的对象，然后将原节点的每个参数赋值到新的节点中。
解题时，我们使用dfs递归方式遍历树中每一个节点，首先new一个新的节点对象用于拷贝当前节点，将新的节点值设置为原节点值。
另外新建一个子节点列表，递归当前节点的每一个子节点，递归返回值（拷贝后的子节点）加入到新建的子节点列表中。最后将该
列表赋值到新建节点的列表对象。当前递归的返回值为该新建节点
Runtime: 1 ms, faster than 100.00% of Java online submissions for Clone N-ary Tree.
Memory Usage: 41.2 MB, less than 100.00% of Java online submissions for Clone N-ary Tree.
*/
public Node cloneTree(Node root) {
    if(root == null) return null; // 如果节点为空，返回空
    Node node = new Node(); // 新建一个节点对象
    node.val = root.val; // 设置新的节点值为元节点值
    List<Node> children = new ArrayList<>(); // 新建子节点列表
    for(Node c : root.children){ // 循环每一个子节点
        // 递归拷贝每一个子节点，并将其加入新建子节点列表
        children.add(cloneTree(c));
    }
    // 设置拷贝后的子节点列表
    node.children = children;
    // 返回当前新建的节点对象
    return node;
}
