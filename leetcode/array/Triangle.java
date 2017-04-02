/**
 * Refer to
 * https://leetcode.com/problems/triangle/#/description
 * Given a triangle, find the minimum path sum from top to bottom. Each step you may move to adjacent numbers on the row below.
    For example, given the following triangle
    [
         [2],
        [3,4],
       [6,5,7],
      [4,1,8,3]
    ]
    The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5 + 1 = 11).
 * Note:
 * Bonus point if you are able to do this using only O(n) extra space, where n is the total number of rows in the triangle.
 *  
 * Solution
 * Refer to
 * https://discuss.leetcode.com/topic/1669/dp-solution-for-triangle
 * This problem is quite well-formed in my opinion. The triangle has a tree-like structure, which would lead people to think 
 * about traversal algorithms such as DFS. However, if you look closely, you would notice that the adjacent nodes always 
 * share a 'branch'. In other word, there are overlapping subproblems. Also, suppose x and y are 'children' of k. 
 * Once minimum paths from x and y to the bottom are known, the minimum path starting from k can be decided in O(1), 
 * that is optimal substructure. Therefore, dynamic programming would be the best solution to this problem in terms 
 * of time complexity.
 * What I like about this problem even more is that the difference between 'top-down' and 'bottom-up' DP can be 'literally' 
 * pictured in the input triangle. For 'top-down' DP, starting from the node on the very top, we recursively find the minimum 
 * path sum of each node. When a path sum is calculated, we store it in an array (memoization); the next time we need to 
 * calculate the path sum of the same node, just retrieve it from the array. However, you will need a cache that is at least 
 * the same size as the input triangle itself to store the pathsum, which takes O(N^2) space. With some clever thinking, 
 * it might be possible to release some of the memory that will never be used after a particular point, but the order of 
 * the nodes being processed is not straightforwardly seen in a recursive solution, so deciding which part of the cache to 
 * discard can be a hard job.
 * 'Bottom-up' DP, on the other hand, is very straightforward: we start from the nodes on the bottom row; the min pathsums 
 * for these nodes are the values of the nodes themselves. From there, the min pathsum at the ith node on the kth row would 
 * be the lesser of the pathsums of its two children plus the value of itself, i.e.:
 * minpath[k][i] = min( minpath[k+1][i], minpath[k+1][i+1]) + triangle[k][i];
 * Or even better, since the row minpath[k+1] would be useless after minpath[k] is computed, we can simply set minpath as 
 * a 1D array, and iteratively update itself:
 * For the kth level:
 * minpath[i] = min( minpath[i], minpath[i+1]) + triangle[k][i]; 
 * Thus, we have the following solution
    int minimumTotal(vector<vector<int> > &triangle) {
        int n = triangle.size();
        vector<int> minlen(triangle.back());
        for (int layer = n-2; layer >= 0; layer--) // For each layer
        {
            for (int i = 0; i <= layer; i++) // Check its every 'node'
            {
                // Find the lesser of its two children, and sum the current value in the triangle with it.
                minlen[i] = min(minlen[i], minlen[i+1]) + triangle[layer][i]; 
            }
        }
        return minlen[0];
    }
 * 
 * http://www.cnblogs.com/springfor/p/3887908.html
 * 一道动态规划的经典题目。需要自底向上求解。
 * 递推公式是： dp[i][j] = dp[i+1][j] + dp[i+1][j+1] ，当前这个点的最小值，由他下面那一行临近的2个点的最小值与当前点的值相加得到。
 * 由于是三角形，且历史数据只在计算最小值时应用一次，所以无需建立二维数组，每次更新1维数组值，最后那个值里存的就是最终结果。
 *
 * https://segmentfault.com/a/1190000003502873
 * 这题我们可以从上往下依次计算每个节点的最短路径，也可以自下而上。自下而上要简单一些，因为我们只用在两个下方元素中选一个较小的，
 * 就能得到确定解。如果将上一层的累加和存在一个一维数组里，则可以只用O(n)空间，但实际上我们可以直接in place在输入list中修改值，
 * 就可以不用额外空间了。
*/
public class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        if(triangle.size()==1) {
            return triangle.get(0).get(0); 
        }
        int[] dp = new int[triangle.size()];
        // initial by last row 
        for (int i = 0; i < triangle.get(triangle.size() - 1).size(); i++) {
            dp[i] = triangle.get(triangle.size() - 1).get(i);
        }
        // iterate from last second row
        for (int i = triangle.size() - 2; i >= 0; i--) {
            for (int j = 0; j < triangle.get(i).size(); j++) {
                dp[j] = Math.min(dp[j], dp[j + 1]) + triangle.get(i).get(j);
            }
        }
        return dp[0];
    }
}
