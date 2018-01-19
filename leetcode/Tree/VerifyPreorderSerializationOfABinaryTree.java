/**
 * Refer to
 * https://leetcode.com/problems/verify-preorder-serialization-of-a-binary-tree/description/
 * One way to serialize a binary tree is to use pre-order traversal. When we encounter a non-null node, 
   we record the node's value. If it is a null node, we record using a sentinel value such as #.

         _9_
        /   \
       3     2
      / \   / \
     4   1  #  6
    / \ / \   / \
    # # # #   # #
    For example, the above binary tree can be serialized to the string "9,3,4,#,#,1,#,#,2,#,6,#,#", 
    where # represents a null node.

    Given a string of comma separated values, verify whether it is a correct preorder traversal 
    serialization of a binary tree. Find an algorithm without reconstructing the tree.

    Each comma separated value in the string must be either an integer or a character '#' representing null pointer.

    You may assume that the input format is always valid, for example it could never contain two 
    consecutive commas such as "1,,3".

    Example 1:
    "9,3,4,#,#,1,#,#,2,#,6,#,#"
    Return true

    Example 2:
    "1,#"
    Return false

    Example 3:
    "9,#,#,1"
    Return false
 *
 *
 * Solution
 * https://www.youtube.com/watch?v=_mbnPPHJmTQ
 * https://discuss.leetcode.com/topic/35976/7-lines-easy-java-solution
 * https://discuss.leetcode.com/topic/35976/7-lines-easy-java-solution/7
*/

// Solution 1: Indgree Outdegree
// Refer to
// https://discuss.leetcode.com/topic/35976/7-lines-easy-java-solution
/**
 Some used stack. Some used the depth of a stack. Here I use a different perspective. 
 In a binary tree, if we consider null as leaves, then

 all non-null node provides 2 outdegree and 1 indegree (2 children and 1 parent), except root 
 all null node provides 0 outdegree and 1 indegree (0 child and 1 parent).

 Suppose we try to build this tree. During building, we record the difference between 
 out degree and in degree diff = outdegree - indegree. When the next node comes, we then 
 decrease diff by 1, because the node provides an in degree. If the node is not null, 
 we increase diff by 2, because it provides two out degrees. If a serialization is correct, 
 diff should never be negative and diff will be zero when finished.
*/
class Solution {
    public boolean isValidSerialization(String preorder) {
        if(preorder == null || preorder.length() == 0) {
            return false;
        }
        String[] nodes = preorder.split(",");
        // To make sure it will serialize successfully, the final
        // count should be 0
        // Start with 1 free arrow which point to root node
        // as indegree
        int count = 1;
        for(int i = 0; i < nodes.length; i++) {
            // Should not put here, wrong case: e.g "#,7,6,9,#,#,#" as root already null
            // if(count < 0) {
            //     return false;
            // }
            // Each node will consume 1 count as indegree
            count--;
            // If count < 0 means there will be one node have no where to place into
            // as it already set as consume 1 count as indegree
            if(count < 0) {
                return false;
            }
            if(!nodes[i].equals("#")) {
                // For each point not "#", outdegree increase 2
                // and contribute to count, but for all "#" we
                // treat as leaves and should not contribute on
                // outdegree as no childrens (left, right)
                count += 2;
            }
            // Should not put here, wrong case: e.g "#,7,6,9,#,#,#" as root already null
            // if(count < 0) {
            //     return false;
            // }
        }
        return count == 0;
    }
}


// Solution 2: Leaves Nonleaves
// Refer to
/**
 Nice solution. My solution is quite similar to yours.
 If we treat null's as leaves, then the binary tree will always be full. A full binary tree has 
 a good property that # of leaves = # of nonleaves + 1. Since we are given a pre-order serialization, 
 we just need to find the shortest prefix of the serialization sequence satisfying the property above. 
 If such prefix does not exist, then the serialization is definitely invalid; otherwise, the serialization 
 is valid if and only if the prefix is the entire sequence.
*/
class Solution {
    public boolean isValidSerialization(String preorder) {
        if(preorder == null || preorder.length() == 0) {
            return false;
        }
        String[] nodes = preorder.split(",");
        int leaves = 0;
        int nonleaves = 0;
        int i;
        for(i = 0; i < nodes.length; i++) {
            if(leaves - nonleaves != 1) {
                if(nodes[i].equals("#")) {
                    leaves++;
                } else {
                    nonleaves++;
                }    
            } else {
                return false;
            }
        }
        return i == nodes.length && leaves - nonleaves == 1;
    }
}


