/**
 Refer to
 https://leetcode.com/problems/n-ary-tree-level-order-traversal/
 Given an n-ary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).

For example, given a 3-ary tree:
            1
        3   2   4
      5   6   
      
We should return its level order traversal:
[
     [1],
     [3,2,4],
     [5,6]
]
 
Note:
The depth of the tree is at most 1000.
The total number of nodes is at most 5000.
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
// Solution 1: BFS level order traversal
class Solution {
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if(root == null) {
            return result;
        }
        Queue<Node> queue = new LinkedList<Node>();
        queue.offer(root);
        while(!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<Integer>();
            for(int i = 0; i < size; i++) {
                Node curr = queue.poll();
                level.add(curr.val);
                for(Node node : curr.children) {
                    queue.offer(node);
                }
            }
            result.add(level);
        }
        return result;
    }
}
