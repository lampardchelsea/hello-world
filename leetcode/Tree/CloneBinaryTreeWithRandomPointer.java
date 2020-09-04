/**
 Refer to
 https://www.cnblogs.com/cnoodle/p/13557573.html
 https://www.acwing.com/file_system/file/content/whole/index/content/592764/
 A binary tree is given such that each node contains an additional random pointer which could point to any node in the tree or null.
 Return a deep copy of the tree.
 
 The tree is represented in the same input/output way as normal binary trees where each node is represented as a pair of 
 [val, random_index] where:
 val: an integer representing Node.val
 random_index: the index of the node (in the input) where the random pointer points to, or null if it does not point to any node.
 
 You will be given the tree in class Node and you should return the cloned tree in class NodeCopy. NodeCopy class is just a clone 
 of Node class with the same attributes and constructors.
 
 Example 1:
     ------------------->
     ^                  |
     |          index:0 |
     |                  1
     |               random -> null
     |             left  right 
     |     index:1 /          \ index:2
     |           null          4
     |                       random
     |                     left  right
     |              index:3 /         \ 
     <------------------   7          null
                         random
                       left  right
 Input: root = [[1,null],null,[4,3],[7,0]]
 Output: [[1,null],null,[4,3],[7,0]]
 Explanation: The original binary tree is [1,null,4,7].
 The random pointer of node one is null, so it is represented as [1, null].
 The random pointer of node 4 is node 7, so it is represented as [4, 3] where 3 is the index of node 7 in the array representing the tree.
 The random pointer of node 7 is node 1, so it is represented as [7, 0] where 0 is the index of node 1 in the array representing the tree.
 
 Example 2:
 Input: root = [[1,4],null,[1,0],null,[1,5],[1,5]]
 Output: [[1,4],null,[1,0],null,[1,5],[1,5]]
 Explanation: The random pointer of a node can be the node itself.
 
 Example 3:
 Input: root = [[1,6],[2,5],[3,4],[4,3],[5,2],[6,1],[7,0]]
 Output: [[1,6],[2,5],[3,4],[4,3],[5,2],[6,1],[7,0]]
 
 Example 4:
 Input: root = []
 Output: []
 
 Example 5:
 Input: root = [[1,null],null,[2,null],null,[1,null]]

 Constraints:
 The number of nodes in the tree is in the range [0, 1000].
 Each node’s value is between [1, 10^6].
*/

// Solution 1:
// Refer to
// https://www.techiedelight.com/clone-a-binary-tree-with-random-pointers/
/**
 To clone a binary tree with reandom pointers, the intuitive solution is to maintain a hash table
 for each node in the binary tree, the idea is to traverse the binary tree in preorder fashion
 and for each encountered node, we create a new node with same data and create a mapping from the
 orignial node to the duplicate node in the hash table. After creating the mapping, we recursively
 set its left and right pointers. Finally, we traverse the original binary tree again and update
 random pointers of the duplicate nodes using the hash table
*/
class Node {
    int data;
    Node left, right, random;
    Node(int data) {
        this.data = data;
    }
}

class Solution {
    // Function to print the preorder traversal of a binary tree
    public void preorder(Node root) {
        if (root == null) {
            return;
        }
        // print data
        System.out.print(root.data + " -> (");
        // print left child's data
        System.out.print((root.left != null ? root.left.data : "X") + ", ");
        // print right child's data
        System.out.print((root.right != null ? root.right.data : "X") + ", ");
        // print random child's data
        System.out.println((root.random != null ? root.random.data : "X") + ")");
        // recur for the left and right subtree
        preorder(root.left);
        preorder(root.right);
    }

    public Node copyRandomBinaryTree(Node root) {
        // Create a map to store mappings from a node to its clone
        Map < Node, Node > map = new HashMap < Node, Node > ();
        // Clone data, left and right pointers for each node of
        // original binary tree and put references into the map
        cloneLeftRightPointers(root, map);
        // Update random pointers from the original binary tree
        // into the map
        updateRandomPointers(root, map);
        // Return the cloned root node
        return map.get(root);
    }

    private Node cloneLeftRightPointers(Node root, Map < Node, Node > map) {
        // Base case
        if (root == null) {
            return null;
        }
        // Clone all fields of the root node except random pointer
        // Create a new node with same data as root node
        Node newRoot = new Node(root.data);
        map.put(root, newRoot);
        // Clone the left and right subtree
        newRoot.left = cloneLeftRightPointers(root.left, map);
        newRoot.right = cloneLeftRightPointers(root.right, map);
        // Return cloned root node
        return newRoot;
    }

    private void updateRandomPointers(Node root, Map < Node, Node > map) {
        // Base case
        if (map.get(root) == null) {
            return;
        }
        // Update the random pointer of cloned node
        map.get(root).random = map.get(root.random);
        // Update for left and right subtree
        updateRandomPointers(root.left, map);
        updateRandomPointers(root.right, map);
    }

    public static void main(String[] args) {
        Node root = q.new Node(1);
        root.left = q.new Node(2);
        root.right = q.new Node(3);
        root.left.left = q.new Node(4);
        root.left.right = q.new Node(5);
        root.right.left = q.new Node(6);
        root.right.right = q.new Node(7);

        root.random = root.right.left.random;
        root.left.left.random = root.right;
        root.left.right.random = root;
        root.right.left.random = root.left.left;
        root.random = root.left;

        System.out.println("Preorder traversal of the original tree:");
        q.preorder(root);

        Node clone = q.copyRandomBinaryTree(root);

        System.out.println("\nPreorder traversal of the cloned tree:");
        q.preorder(clone);
    }
}
