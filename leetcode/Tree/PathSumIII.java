/**
 * Refer to
 * https://leetcode.com/problems/path-sum-iii/description/
 * You are given a binary tree in which each node contains an integer value.

    Find the number of paths that sum to a given value.

    The path does not need to start or end at the root or a leaf, but it must go downwards 
    (traveling only from parent nodes to child nodes).

    The tree has no more than 1,000 nodes and the values are in the range -1,000,000 to 1,000,000.

    Example:

    root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8

          10
         /  \
        5   -3
       / \    \
      3   2   11
     / \   \
    3  -2   1

    Return 3. The paths that sum to 8 are:

    1.  5 -> 3
    2.  5 -> 2 -> 1
    3. -3 -> 11
 *
 * Solution
 * https://leetcode.com/problems/path-sum-iii/discuss/91884/Simple-AC-Java-Solution-DFS
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
class Solution {
    /**
     * Refer to
     * https://leetcode.com/problems/path-sum-iii/discuss/91884/Simple-AC-Java-Solution-DFS
     * Each time find all the path start from current node
       Then move start node to the child and repeat.
       Time Complexity should be O(N^2) for the worst case and O(NlogN) for balanced binary Tree.
    */
    public int pathSum(TreeNode root, int sum) {
        if(root == null) {
            return 0;
        }
        return helper(root, sum) + pathSum(root.left, sum) + pathSum(root.right, sum);
    }
    
    private int helper(TreeNode root, int remain) {
        int result = 0;
        if(root == null) {
            return result;
        }
        if(root.val == remain) {
            result++;
        }
        result += helper(root.left, remain - root.val);
        result += helper(root.right, remain - root.val);
        return result;
    }
}



/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    /**
     * Refer to
     * leetcode.com/problems/path-sum-iii/discuss/91878/17-ms-O(n)-java-Prefix-sum-method/96424
     1. The prefix stores the sum from the root to the current node in the recursion
     2. The map stores <prefix sum, frequency> pairs before getting to the current node. We can imagine a path from the root to the current node. The sum from any node in the middle of the path to the current node = the difference between the sum from the root to the current node and the prefix sum of the node in the middle.
     3. We are looking for some consecutive nodes that sum up to the given target value, which means the difference discussed in 2. should equal to the target value. In addition, we need to know how many differences are equal to the target value.
     4. Here comes the map. The map stores the frequency of all possible sum in the path to the current node. If the difference between the current sum and the target value exists in the map, there must exist a node in the middle of the path, such that from this node to the current node, the sum is equal to the target value.
     5. Note that there might be multiple nodes in the middle that satisfy what is discussed in 4. The frequency in the map is used to help with this.
     6. Therefore, in each recursion, the map stores all information we need to calculate the number of ranges that sum up to target. Note that each range starts from a middle node, ended by the current node.
     7. To get the total number of path count, we add up the number of valid paths ended by EACH node in the tree.
     8. Each recursion returns the total count of valid paths in the subtree rooted at the current node. And this sum can be divided into three parts:
     - the total number of valid paths in the subtree rooted at the current node's left child
     - the total number of valid paths in the subtree rooted at the current node's right child
     - the number of valid paths ended by the current node
        The interesting part of this solution is that the prefix is counted from the top(root) to the bottom(leaves), and the result of total count is calculated from the bottom to the top :D
    */
    public int pathSum(TreeNode root, int sum) {
        if(root == null) {
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(0, 1);
        return helper(root, 0, sum, map);
    }
    
    private int helper(TreeNode curr, int sum, int target, Map<Integer, Integer> map) {
        if(curr == null) {
            return 0;
        }
        // update the prefix sum by adding the current val
        sum += curr.val;
        // get the number of valid path, ended by the current node
        int numPathToCurr = map.getOrDefault(sum - target, 0);
        // update the map with the current sum, so the map is good to be passed to the next recursion
        map.put(sum, map.getOrDefault(sum, 0) + 1);
        // add the 3 parts discussed in 8. together
        int result = numPathToCurr + helper(curr.left, sum, target, map) + helper(curr.right, sum, target, map);
        // restore the map, as the recursion goes from the bottom to the top
        map.put(sum, map.get(sum) - 1);
        return result;
    }
}

// Re-work
// Solution 1: Brute Force: O(nlogn) ~ O(n^2)
// Refer to
// https://leetcode.com/problems/path-sum-iii/discuss/141424/Python-step-by-step-walk-through.-Easy-to-understand.-Two-solutions-comparison.-%3A-)
/**
 1.1 High level walk-through:
(Define return) Define a global var: self.numOfPaths in the main function.
(1st layer DFS) Use recursive traverse to go through each node (can be any order: pre, in, post all fine).
(2nd layer DFS) For each node, walk all paths. If a path sum equals to the target: self.numOfPaths += 1
Return result: return self.numOfPaths
1.2 Complexity analysis
1.2.1 Space
Space complexity is O(1), due to there is no extra cache. However, for any recursive question, we need to 
think about stack overflow, namely the recursion should not go too deep.
Assume we have n TreeNodes in total, the tree height will vary from O(n) (single sided tree) to O(logn)(balanced tree).
The two DFS will go as deep as the tree height.
1.2.2 Time
Time complexity depends on the two DFS.
1st layer DFS will always take O(n), due to here we will take each node out, there are in total n TreeNodes
2nd layer DFS will take range from O(n) (single sided tree) to O(logn)(balanced tree). This is due to here we 
are get all the paths from a given node. The length of path is proportional to the tree height.
Therefore, the total time complexity is O(nlogn) to O(n^2).
*/
class Solution {
    // define global variable
    int count;
    public int pathSum(TreeNode root, int sum) {
        count = 0;
        helper(root, sum);
        return count;
    }
    
    // 1st layer DFS to go through each node
    // traverse through the tree, at each treenode, call another DFS to test if a path sum include the answer
    private void helper(TreeNode node, int sum) {
        if(node == null) {
            return;
        }
        dfs(node, sum); // we can move the line to any order, here is pre-order
        helper(node.left, sum);
        helper(node.right, sum);
    }
    
    // for a given node, DFS to find any path that sum == target, if find count += 1
    private void dfs(TreeNode node, int sum) {
        if(node == null) {
            return;
        }
        if(node.val == sum) {
            count++;
        }
        dfs(node.left, sum - node.val);
        dfs(node.right, sum - node.val);
    }
}

// Solution 2: DFS + backtracking
// Refer to
// https://leetcode.com/problems/path-sum-iii/discuss/91878/17-ms-O(n)-java-Prefix-sum-method/96424
/**
 his is an excellent idea and took me some time to figure out the logic behind.
 Hope my comment below could help some folks better understand this solution.

 1.The prefix stores the sum from the root to the current node in the recursion
 2.The map stores <prefix sum, frequency> pairs before getting to the current node. 
   We can imagine a path from the root to the current node. The sum from any node in 
   the middle of the path to the current node = the difference between the sum from 
   the root to the current node and the prefix sum of the node in the middle.
 3.We are looking for some consecutive nodes that sum up to the given target value, 
   which means the difference discussed in 2. should equal to the target value. 
   In addition, we need to know how many differences are equal to the target value.
 4.Here comes the map. The map stores the frequency of all possible sum in the path 
   to the current node. If the difference between the current sum and the target value 
   exists in the map, there must exist a node in the middle of the path, such that from 
   this node to the current node, the sum is equal to the target value.
 5.Note that there might be multiple nodes in the middle that satisfy what is discussed 
   in 4. The frequency in the map is used to help with this.
 6.Therefore, in each recursion, the map stores all information we need to calculate the 
   number of ranges that sum up to target. Note that each range starts from a middle node, 
   ended by the current node.
 7.To get the total number of path count, we add up the number of valid paths ended by 
   EACH node in the tree.
 8.Each recursion returns the total count of valid paths in the subtree rooted at the current node. 
   And this sum can be divided into three parts:
 - the total number of valid paths in the subtree rooted at the current node's left child
 - the total number of valid paths in the subtree rooted at the current node's right child
 - the number of valid paths ended by the current node
 The interesting part of this solution is that the prefix is counted from the top(root) to the bottom(leaves), 
 and the result of total count is calculated from the bottom to the top :D
*/

// https://leetcode.com/problems/path-sum-iii/discuss/91878/17-ms-O(n)-java-Prefix-sum-method/96427
/**
 This solution makes me confused at first and seems others are having the same problem.
 The idea is based on path.
 Suppose now the hash table preSum stores the prefix sum of the whole path. Then after adding current 
 node's val to the pathsum, if (pathsum-target) is in the preSum, then we know that at some node of 
 path we have a (pathsum-target) preSum, hence we have a path of target. Actually, it is the path 
 starting from that node.
 
 Now the problem is how to maintain this preSum table? Since one path's preSum is different from others, 
 we have to change it. However, we should notice that we can reuse the most part of the preSum table. 
 If we are done with current node, we just need to delete the current pathsum in the preSum, and leave 
 all other prefix sum in it. Then, in higher layers, we can forget everything about this node (and its descendants).
 That's why we have
 preSum.put(sum, preSum.get(sum) - 1);
 // this deletes current pathsum and leave all previous sums
 After running the algorithm, the preSum table should contain keys of all possible path sum starting from 
 root, but all values of them are 0, except key 0. For instance in the example we should have:
 {0: 1, 7: 0, 10: 0, 15: 0, 16: 0, 17: 0, 18: 0, 21: 0}
 
 Hope it helps.
 @jeffery said in 17 ms O(n) java Prefix sum method:
 Could we know what's the two sum problem and its solution we are talking here?
 I a curious how to solve it using prefix sum.
 Thanks
 
 @marcusgao94 said in 17 ms O(n) java Prefix sum method:
 Could you explain what exactly your hashmap saves? what does the key mean in the hashmap? Why can you save 
 the number of paths to a Integer? I think you must associate the number of paths to a certain node, instead 
 of the prefix sum value. Because if two nodes on two different branches have same prefix value, the number 
 of paths to them may not be the same, and if you calls preSum.get(prefix) then the res will be wrong.

 And why do you need to put preSum(sum, 1) first and at last let preSum(sum) - 1?
*/
class Solution {
    public int pathSum(TreeNode root, int sum) {
        if(root == null) {
            return 0;
        }
        Map<Integer, Integer> preSum = new HashMap<Integer, Integer>();
        // [key, value] -> <prefix sum, frequency>
        // The prefix stores the sum from the root to the current node in the recursion
        preSum.put(0, 1);
        return helper(root, 0, sum, preSum);
    }
    
    private int helper(TreeNode curr, int currSum, int target, Map<Integer, Integer> preSum) {
        if(curr == null) {
            return 0;
        }
        // update the prefix sum by adding the current val
        currSum += curr.val;
        // get the number of valid path, ended by the current node
        int numPathToCurr = preSum.getOrDefault(currSum - target, 0);
        // update the map with the current sum, so the map is good to be passed to the next recursion
        preSum.put(currSum, preSum.getOrDefault(currSum, 0) + 1);
        // add the 3 parts discussed in 8. together
        int result = numPathToCurr + helper(curr.left, currSum, target, preSum) + helper(curr.right, currSum, target, preSum);
        // restore the map, as the recursion goes from the bottom to the top
        preSum.put(currSum, preSum.get(currSum) - 1);
        return result;
    }
}


