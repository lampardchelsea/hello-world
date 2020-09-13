/**
 Refer to
 https://leetcode.com/problems/number-of-good-leaf-nodes-pairs/
 Given the root of a binary tree and an integer distance. A pair of two different leaf nodes of a binary tree is said 
 to be good if the length of the shortest path between them is less than or equal to distance.

 Return the number of good leaf node pairs in the tree.

 Example 1:
           1
        2     3
          4
          
 Input: root = [1,2,3,null,4], distance = 3
 Output: 1
 Explanation: The leaf nodes of the tree are 3 and 4 and the length of the shortest path between 
 them is 3. This is the only good pair.

 Example 2:
           1
        2     3
      4   5 6   7  
        
 Input: root = [1,2,3,4,5,6,7], distance = 3
 Output: 2
 Explanation: The good pairs are [4,5] and [6,7] with shortest path = 2. The pair [4,6] is not good 
 because the length of ther shortest path between them is 4.

 Example 3:
 Input: root = [7,1,4,6,null,5,3,null,null,null,null,null,2], distance = 3
 Output: 1
 Explanation: The only good pair is [2,5].

 Example 4:
 Input: root = [100], distance = 1
 Output: 0
 
 Example 5:
 Input: root = [1,1,1], distance = 2
 Output: 1
  
 Constraints:
 The number of nodes in the tree is in the range [1, 2^10].
 Each node's value is between [1, 100].
 1 <= distance <= 10
*/

// Solution 1: DFS + similar to diameter
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Tree/DiameterOfBinaryTree.java
// https://leetcode.com/problems/number-of-good-leaf-nodes-pairs/discuss/755784/Java-Detailed-Explanation-Post-Order-Cache-in-Array
// https://leetcode.com/problems/number-of-good-leaf-nodes-pairs/discuss/755784/Java-Detailed-Explanation-Post-Order-Cache-in-Array/630624
/**
 Key Notes:
 Store distances of all leaf nodes, at each non-leaf node, find those leaf nodes satisfying the condition, and accumulate into res.
 Distance is from 0 - 10. We could use a size 11 array to count frequency, and ignore the leaf node with distance larger than 10.
 What helper function returns: the number of leaf nodes for each distance. Let's name the return array is arr for the current node: 
 arr[i]: the number of leaf nodes, i: distance from leaf node to current node is i.
 Note:
 When the current node is a leaf node, shouldn't set A[0] = 1 instead of A[1] = 1? A[0] = 1 means there is 1 leaf node and its distance 
 to the current node(which is the leaf node itself) is 0.
 But A[0] will not used in DFS calculation of this problem.
*/

// https://leetcode.com/problems/number-of-good-leaf-nodes-pairs/discuss/756198/Java-DFS-Solution-with-a-Twist-100-Faster-Explained
/**
 Steps -
 We have to do normal post order tree traversal.
 The trick is to keep track of number of leaf nodes with a particular distance. The arrays are used for this purpose.
 For this we maintain an array of size = max distance.
 
 Example -
            1
         2     3
           4
 In above example , assume maximum distance = 4. So we maintain an array of size 4.
 For root node 1,
 left = [ 0,0,1,0,0]
 right = [0,1,0,0,0]
 Here, left[2] = 1, which denotes that there is one leaf node with distance 2 in left subtree of root node 1.
 right[1] = 1, which denotes that there is one leaf node with distance 1 in right subtree of root node 1.
 
 In this way, we have to recursively, calculate the left and right subtree of every root node.
 Once we have both left and right arrays for a particular root, we have to just calculate total number of good 
 node pairs formed using result += left[l]*right[r];
 Before we bactrack to parent, we have to return the distance for parents by adding up left and right subtrees of 
 current node. Note that we are doing - res[i+1] = left[i]+right[i];
 The intution is that, if a leaf node is at distance i from current node, it would be at distance i+1 from its parent. 
 Hence, will building the res array, we are adding sum in i+1 th position and return to parent.
*/




