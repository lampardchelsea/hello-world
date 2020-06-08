/**
 Refer to
 https://www.lintcode.com/problem/binary-tree-path-sum-iv/description
 If the depth of a tree is smaller than 5, this tree can be represented by a list of three-digits integers.

For each integer in this list:

The hundreds digit represents the depth D of this node, 1 <= D <= 4.
The tens digit represents the position P of this node in the level it belongs to, 1 <= P <= 8. 
The position is the same as that in a full binary tree.
The units digit represents the value V of this node, 0 <= V <= 9.
Given a list of ascending three-digits integers representing a binary with the depth smaller than 5. 
You need to return the sum of all paths from the root towards the leaves.

Example
Example 1:
Input: [113, 215, 221]
Output: 12
Explanation:
  The tree that the list represents is:
    3
   / \
  5   1
  The path sum is (3 + 5) + (3 + 1) = 12.

Example 2:
Input: [113, 221]
Output: 4
Explanation:
  The tree that the list represents is:
    3
     \
      1
  The path sum is 3 + 1 = 4.
*/

// Solution 1: HashMap + Pre-order Traverse
// Refer to
// https://massivealgorithms.blogspot.com/2017/08/leetcode-666-path-sum-iv.html
/**
 How do we solve problem like this if we were given a normal tree? Yes, traverse it, keep a root to 
 leaf running sum. If we see a leaf node (node.left == null && node.right == null), we add the running 
 sum to the final result.
 Now each tree node is represented by a number. 1st digits is the level, 2nd is the position in that level 
 (note that it starts from 1 instead of 0). 3rd digit is the value. We need to find a way to traverse this 
 tree and get the sum.
 The idea is, we can form a tree using a HashMap. The key is first two digits which marks the position of 
 a node in the tree. The value is value of that node. Thus, we can easily find a node's left and right 
 children using math.
 Formula: For node xy? its left child is (x+1)(y*2-1)? and right child is (x+1)(y*2)?
 Given above HashMap and formula, we can traverse the tree. 
*/

// https://www.cnblogs.com/grandyang/p/7570954.html
/**
 这道题还是让我们求二叉树的路径之和，但是跟之前不同的是，树的存储方式比较特别，并没有专门的数结点，而是使用一个三位
 数字来存的，百位数是该结点的深度，十位上是该结点在某一层中的位置，个位数是该结点的结点值。为了求路径之和，我们肯定
 还是需要遍历树，但是由于没有树结点，所以我们可以用其他的数据结构代替。比如我们可以将每个结点的位置信息和结点值分离开，
 然后建立两者之间的映射。比如我们可以将百位数和十位数当作key，将个位数当作value，建立映射。由于题目中说了数组是有序的，
 所以首元素就是根结点，然后我们进行先序遍历即可。在递归函数中，我们先将深度和位置拆分出来，然后算出左右子结点的深度
 和位置的两位数，我们还要维护一个变量cur，用来保存当前路径之和。如果当前结点的左右子结点不存在，说明此时cur已经是一条
 完整的路径之和了，加到结果res中，直接返回。否则就是对存在的左右子结点调用递归函数即可
*/

// https://leetcode.com/articles/path-sum-iv/
/**
 Complexity Analysis
 Time Complexity: O(N) where NN is the length of nums. We construct the graph and traverse it in this time.
 Space Complexity: O(N), the size of the implicit call stack in our depth-first search. 
*/

public class Solution {
    /**
     * @param nums: a list of integers
     * @return: return an integer
     */
    // 'totalSum' used to record all paths sum
    int totalSum;
    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    public int pathSum(int[] nums) {
        totalSum = 0;
        for(int num : nums) {
            int key = num / 10;
            int val = num % 10;
            map.put(key, val);
        }
        helper(nums[0] / 10, 0);
        return totalSum;
    }
    
    // Pre-order Traverse
    private void helper(int root, int preSum) {
        int level = root / 10;
        int pos = root % 10;
        int left = (level + 1) * 10 + pos * 2 - 1;
        int right = (level + 1) * 10 + pos * 2;
        // Pre-order traverse, 'curSum' used to record current path sum
        int curSum = preSum + map.get(root);
        // If a leaf node means find a full path which record in 'curSum'
        // add it to 'totalSum'
        if(!map.containsKey(left) && !map.containsKey(right)) {
            totalSum += curSum;
            return;
        }
        if(map.containsKey(left)) {
            helper(left, curSum);
        }
        if(map.containsKey(right)) {
            helper(right, curSum);
        }
    }
}

// Solution 2: Iterative + Level-order Traverse
// Refer to
// https://www.cnblogs.com/grandyang/p/7570954.html
/**
 下面这种方法是迭代的形式，我们使用的层序遍历，与先序遍历不同的是，我们不能维护一个当前路径之和的变量，
 这样会重复计算结点值，而是在遍历每一层的结点时，加上其父结点的值，如果某一个结点没有子结点了，
 才将累加起来的结点值加到结果res中
*/
public class Solution {
    /**
     * @param nums: a list of integers
     * @return: return an integer
     */
    public int pathSum(int[] nums) {
        if(nums == null) {
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int num : nums) {
            int key = num / 10;
            int val = num % 10;
            map.put(key, val);
        }
        int result = 0;
        Queue<Integer> q = new LinkedList<Integer>();
        q.offer(nums[0] / 10);
        // Level-order traverse
        while(!q.isEmpty()) {
            int root = q.poll();
            int level = root / 10;
            int pos = root % 10;
            int left = (level + 1) * 10 + pos * 2 - 1;
            int right = left + 1;
            // Find a leaf node, a valid path get, add to the result
            if(!map.containsKey(left) && !map.containsKey(right)) {
                result += map.get(root);
            }
            // Update left node's value by adding its parent root value
            if(map.containsKey(left)) {
                map.put(left, map.get(left) + map.get(root));
                q.offer(left);
            }
            // Update right node's value by adding its parent root value
            if(map.containsKey(right)) {
                map.put(right, map.get(right) + map.get(root));
                q.offer(right);
            }
        }
        return result;
    }
}




