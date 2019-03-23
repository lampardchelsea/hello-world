/**
 Given an n-ary tree, return the postorder traversal of its nodes' values.

For example, given a 3-ary tree:
                 1
             3   2   4
           5   6
Return its postorder traversal as: [5,6,3,2,4,1].

Note:
Recursive solution is trivial, could you do it iteratively?
*/
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val,List<Node> _children) {
        val = _val;
        children = _children;
    }
};
*/
// Refer to
// https://leetcode.com/articles/introduction-to-n-ary-trees/
// In a N-ary tree, postorder means traverse the subtree rooted 
// at its children first and then visit the root node itself
class Solution {
    public List<Integer> postorder(Node root) {
        List<Integer> result = new ArrayList<Integer>();
        postorder(root, result);
        return result;
    }
    
    public void postorder(Node root, List<Integer> result) {
        if(root == null) {
            return;
        }
        List<Node> children = root.children;
        for(Node child : children) {
            postorder(child, result);
        }
        result.add(root.val);
    }
}
