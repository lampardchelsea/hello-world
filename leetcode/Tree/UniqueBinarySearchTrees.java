https://leetcode.com/problems/unique-binary-search-trees/description/
Given an integer n, return the number of structurally unique BST's (binary search trees) which has exactly n nodes of unique values from 1 to n.

Example 1:

Input: n = 3
Output: 5

Example 2:
Input: n = 1
Output: 1
 
Constraints:
- 1 <= n <= 19
--------------------------------------------------------------------------------
Attempt 1: 2024-07-10
Solution 1: Follow same style as L95 (30 min, Memory Limit Exceeded)
class Solution {
    public int numTrees(int n) {
        List<String> result = helper(1, n);
        return result.size();
    }

    private List<String> helper(int start, int end) {
        List<String> result = new ArrayList<>();
        if(start > end) {
            result.add("null");
            return result;
        }
        for(int i = start; i <= end; i++) {
            List<String> left = helper(start, i - 1);
            List<String> right = helper(i + 1, end);
            for(String l : left) {
                for(String r : right) {
                    String tree = "(" + i + " " + l + " " + r + ")";
                    result.add(tree);
                }
            }
        }
        return result;
    }
}

Refer to chatGPT
Q: I know Leetcode 96 only focus on count the number, but how can we calculate number by printing out all actual trees first then find its count, which is similar like what Leetcode 95 doing ? 
If you want to solve Leetcode 96 by actually generating all the possible unique BSTs (like in Leetcode 95) and then counting them, you can do so by adapting the solution from Leetcode 95. Here's how you can modify the code to generate all BSTs and then count them:
1.Generate All BSTs: Use a recursive function similar to the one used in Leetcode 95 to generate all unique BSTs.
2.Count the Trees: Simply count the number of trees generated.
Here's the complete solution in Java:
import java.util.ArrayList;
import java.util.List;

public class Solution {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val) { 
            this.val = val; 
        }
    }

    public int numTrees(int n) {
        List<TreeNode> trees = generateTrees(1, n);
        return trees.size();
    }

    private List<TreeNode> generateTrees(int start, int end) {
        List<TreeNode> ans = new ArrayList<>();
        
        // Base case: if start > end, there are no nodes, so return a list with null
        if (start > end) {
            ans.add(null);
            return ans;
        }
        
        // Try each number as a root
        for (int i = start; i <= end; i++) {
            // Generate all possible left subtrees
            List<TreeNode> leftTrees = generateTrees(start, i - 1);
            // Generate all possible right subtrees
            List<TreeNode> rightTrees = generateTrees(i + 1, end);
            
            // Combine each left and right subtree with the current root `i`
            for (TreeNode left : leftTrees) {
                for (TreeNode right : rightTrees) {
                    TreeNode root = new TreeNode(i);
                    root.left = left;
                    root.right = right;
                    ans.add(root);
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int n = 3; // You can change this value to test other cases
        int count = solution.numTrees(n);
        System.out.println("Number of unique BSTs for n = " + n + " is: " + count);
    }
}
Q: For Leetcode 96, is there a way we can print all unique BSTs but no need define TreeNode class ?
For Leetcode 96, we need to generate and count all unique Binary Search Trees (BSTs). However, if you want to print all unique BSTs without defining a TreeNode class, you can represent the trees in a different format, such as nested lists or strings.
Here is how you can achieve this using a string representation of trees:
Approach
1.Generate All BSTs: Use a recursive function to generate all unique BSTs, but represent each tree as a string.
2.Print and Count the Trees: Store and print each tree representation and then count them.
Here's how you can do it in Java:
import java.util.ArrayList;
import java.util.List;

public class Solution {
    public int numTrees(int n) {
        List<String> trees = generateTrees(1, n);

        return trees.size();
    }

    private List<String> generateTrees(int start, int end) {
        List<String> ans = new ArrayList<>();
        
        // Base case: if start > end, there are no nodes, so return a list with "null"
        if (start > end) {
            ans.add("null");
            return ans;
        }
        
        // Try each number as a root
        for (int i = start; i <= end; i++) {
            // Generate all possible left subtrees
            List<String> leftTrees = generateTrees(start, i - 1);
            // Generate all possible right subtrees
            List<String> rightTrees = generateTrees(i + 1, end);
            
            // Combine each left and right subtree with the current root `i`
            for (String left : leftTrees) {
                for (String right : rightTrees) {
                    String tree = "(" + i + " " + left + " " + right + ")";
                    ans.add(tree);
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int n = 3; // You can change this value to test other cases
        int count = solution.numTrees(n);
        System.out.println("Number of unique BSTs for n = " + n + " is: " + count);
    }
}
Explanation
1.numTrees Method: This is the main method that generates all unique BSTs and prints their string representations, then returns their count.
2.generateTrees Method: Recursively generates all unique BSTs for a given range [start, end], but represents each tree as a string:
- If start > end, it returns a list containing "null", representing an empty subtree.
- For each possible root i from start to end, it generates all possible left subtrees and right subtrees.
- It combines each left subtree with each right subtree and constructs a string representation of the tree with root i.
3.Tree Representation: Each tree is represented as a string in the format "(root left right)", where root is the root value, and left and right are the string representations of the left and right subtrees, respectively.
Complexity
- Time Complexity: The time complexity is high due to the combinatorial nature of generating all unique BSTs. It is exponential in the number of nodes.
- Space Complexity: The space complexity is also high due to the storage of all generated tree strings. The number of unique BSTs for n nodes is given by the nth Catalan number, which grows exponentially.
Conclusion
This approach solves Leetcode 96 by generating all unique BSTs using string representations instead of defining a TreeNode class. It is an educational method to visualize the problem and understand the generation of unique BSTs. However, it is not as efficient as the dynamic programming approach for counting BSTs in terms of time and space complexity.

--------------------------------------------------------------------------------
Solution 2: Native DFS (30 min)
Style 1: helper(1, n), the style which inherent from L95.P11.8.Unique Binary Search Trees II (Ref.L241,L96) DFS solution
class Solution {
    public int numTrees(int n) {
        return helper(1, n);
    }

    private int helper(int start, int end) {
        if (start >= end) return 1;  // 空区间或单节点，只有一种结构
        int count = 0;
        for (int root = start; root <= end; root++) {
            int left = helper(start, root - 1);
            int right = helper(root + 1, end);
            count += left * right;
        }
        return count;
    }
}

Time Complexity : O(N!), where N is the given number of nodes in BST. Read here for proof.
Space Complexity : O(N), the maximum recursive stack depth.

Style 2: helper(n), BST numbers generated by n nodes only depends on node numbers
class Solution {
    public int numTrees(int n) {
        return helper(n);
    }

    private int helper(int n) {
        // There is one unique BST for a tree with zero nodes, which is an empty tree
        // so n = 0 create empty tree and return 1
        if(n <= 1) {
            return 1;
        }
        int result = 0;
        for(int i = 1; i <= n; i++) {
            // For each root i, left subtree has i - 1 nodes(i from 1 to i - 1, total i - 1), 
            // right subtree has n - i nodes(i from i + 1 to n, total n - i)
            result += helper(i - 1) * helper(n - i);
        }
        return result;
    }
}

Time Complexity : O(3^N), where N is the given number of nodes in BST. Read here for proof.
Space Complexity : O(N), the maximum recursive stack depth.
The base condition could also change from n <= 1 to n < 1, since we only need empty tree = 1 as base condition
class Solution {
    public int numTrees(int n) {
        return helper(n);
    }

    private int helper(int n) {
        // There is one unique BST for a tree with zero nodes, which is an empty tree
        // so n = 0 create empty tree and return 1
        if(n < 1) {
            return 1;
        }
        int result = 0;
        for(int i = 1; i <= n; i++) {
            // For each root i, left subtree has i - 1 nodes(i from 1 to i - 1, total i - 1), 
            // right subtree has n - i nodes(i from i + 1 to n, total n - i)
            result += helper(i - 1) * helper(n - i);
        }
        return result;
    }
}

Time Complexity : O(3^N), where N is the given number of nodes in BST. Read here for proof.
Space Complexity : O(N), the maximum recursive stack depth.

Solution 3: DFS + Memoization (10 min)
Style 1: Based on helper(1, n), the style which inherent from L95.P11.8.Unique Binary Search Trees II (Ref.L241,L96) DFS solution
class Solution {
    public int numTrees(int n) {
        // memo[start][end] 存储区间 [start, end] 可形成的 BST 数量
        Integer[][] memo = new Integer[n + 2][n + 2];
        return helper(1, n, memo);
    }

    private int helper(int start, int end, Integer[][] memo) {
        // 空区间或单节点，只有一种结构（空树或单节点树）
        if (start >= end) return 1;
        // 如果已经计算过该区间，直接返回
        if (memo[start][end] != null) return memo[start][end];

        int count = 0;
        for (int root = start; root <= end; root++) {
            int left = helper(start, root - 1, memo);
            int right = helper(root + 1, end, memo);
            count += left * right;
        }
        // 缓存结果
        memo[start][end] = count;
        return count;
    }
}

时间复杂度：每个区间 [start, end] 最多被计算一次，区间总数 O(n²)，每个区间内部枚举根节点 O(n)，总时间复杂度 O(n³)（实际上由于区间长度不同，总和约为 O(n³)）。
若进一步优化为只按区间长度记忆（一维），可将时间复杂度降至 O(n²)，但二维版本仍是可接受的（n 较小，通常 ≤ 19）。

空间复杂度：O(n²) 用于缓存数组。

Style 2: Based on helper(n), BST numbers generated by n nodes only depends on node numbers
class Solution {
    public int numTrees(int n) {
        Integer[] memo = new Integer[n + 1];
        return helper(n, memo);
    }

    private int helper(int n, Integer[] memo) {
        // There is one unique BST for a tree with zero nodes, which is an empty tree
        // so n = 0 create empty tree and return 1
        if(n <= 1) {
            return 1;
        }
        if(memo[n] != null) {
            return memo[n];
        }
        int result = 0;
        for(int i = 1; i <= n; i++) {
            // For each root i, left subtree has i - 1 nodes(i from 1 to i - 1, total i - 1), 
            // right subtree has n - i nodes(i from i + 1 to n, total n - i)
            result += helper(i - 1, memo) * helper(n - i, memo);
        }
        return memo[n] = result;
    }
}

Time Complexity : O(N^2)
Here we calculate numTrees(i) (for 1<=i<=N) only once and memoize it which will take O(N). 
For calculating each of numTrees(i), we need N iterations to calculate numTrees(0) * numTrees(i) 
+ numTrees(1) * numTrees(i-1) + numTrees(2) * numTrees(i-2)+ ... + numTrees(i) * numTrees(0). 
Thus, the overall time complexity becomes O(N * N).
Space Complexity : O(N), required for recursion and memoization
The base condition could also change from n <= 1 to n < 1, since we only need empty tree = 1 as base condition
class Solution {
    public int numTrees(int n) {
        Integer[] memo = new Integer[n + 1];
        return helper(n, memo);
    }

    private int helper(int n, Integer[] memo) {
        // There is one unique BST for a tree with zero nodes, which is an empty tree
        // so n = 0 create empty tree and return 1
        if(n < 1) {
            return 1;
        }
        if(memo[n] != null) {
            return memo[n];
        }
        int result = 0;
        for(int i = 1; i <= n; i++) {
            // For each root i, left subtree has i - 1 nodes(i from 1 to i - 1, total i - 1), 
            // right subtree has n - i nodes(i from i + 1 to n, total n - i)
            result += helper(i - 1, memo) * helper(n - i, memo);
        }
        return memo[n] = result;
    }
}

Time Complexity : O(N^2)
Here we calculate numTrees(i) (for 1<=i<=N) only once and memoize it which will take O(N). 
For calculating each of numTrees(i), we need N iterations to calculate numTrees(0) * numTrees(i) 
+ numTrees(1) * numTrees(i-1) + numTrees(2) * numTrees(i-2)+ ... + numTrees(i) * numTrees(0). 
Thus, the overall time complexity becomes O(N * N).
Space Complexity : O(N), required for recursion and memoization

Solution 4: DP (60 min)
Based on helper(n), BST numbers generated by n nodes only depends on node numbers
Style 1: Set dp[0] = dp[1] = 1
class Solution {
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = dp[1] = 1;
        for(int i = 2; i <= n; i++) {
            for(int j = 1; j <= i; j++) {
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        return dp[n];
    }
}

Time Complexity : O(N^2), we iterate over the range i=[2, n] and iteratively calculate dp[i]. 
The total number of operations performed equals 2+3+4+5..n = (n*(n+1)/2)-1 ≈ O(N^2)
Space Complexity : O(N), required to store the results in dp
- Actually if only define dp[0] = 1, NO need dp[1] = 1 is also fine, which exactly only require mapping to empty tree condition
- Then we calculate result for each number of nodes i from 1...n one after another instead from 2 ...n.
Style 2: Set dp[0] = 1
class Solution {
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= i; j++) {
                dp[i] += dp[j - 1] * dp[i - j];
            }            
        }
        return dp[n];
    }
}

Time Complexity : O(N^2), we iterate over the range i=[2, n] and iteratively calculate dp[i]. 
The total number of operations performed equals 2+3+4+5..n = (n*(n+1)/2)-1 ≈ O(N^2)
Space Complexity : O(N), required to store the results in dp

--------------------------------------------------------------------------------
[C++/Python] 5 Easy Solutions w/ Explanation | Optimization from Brute-Force to DP to Catalan O(N)
Refer to
https://leetcode.com/problems/unique-binary-search-trees/solutions/1565543/c-python-5-easy-solutions-w-explanation-optimization-from-brute-force-to-dp-to-catalan-o-n/
❌ Solution - I (Brute-Force) [TLE]
Let's start by trying to solve the problem in Brute-Force manner. To form structurally unique BST consisting of n nodes, we can start by taking any of the node 1...n as the root node. Let the chosen root node be i. Then, we have a BST where the root node is i, the left child consist of all nodes from 1...i-1 (since left sub-tree must have only less than root's value) and right child consist of all nodes from i+1...n.
    
              1            1                   2                    3               3
           \            \                 / \                  /               /
                3             2              1   3                2               1
               /               \                                 /                 \
              2                 3                              1                    2
                     i = 1                   i = 2                       i = 3           
(i = root node)
Now, we need to realize that the number of structurally unique BST formable with nodes having value i+1...n is equal to the number of structurally unique BST formable with nodes having value i+1-i...n-i = 1...n-i. Why? Because we only need to find BST which are structurally unique irrespective of their values and we can form an equal number of them with nodes from 1...n or 2...n+1 or n...2n-1 and so on. So, the number only depends on number of nodes using which BST is to be formed.
Now, when we choose i as root node, we will have nodes from 1...i-1 (i-1 nodes in total) in left sub-tree and nodes from i+1...n (n-i nodes in total) in the right side. We can then form numTrees(i-1) BSTs for left sub-tree and numTrees(n-i) BSTs for the right sub-tree. The total number of structurally unique BSTs formed having root i will be equal to product of these two, i.e, numTrees(i-1) * numTrees(n-i). The same can be followed recursively till we reach base case - numTrees(0) = numTrees(1) = 1 because we can form only a single empty BST and single node BST in these cases respectively.
The final answer will be summation of answers considering all 1...n as root nodes.
           3                          2                         1               
          / \                        / \                      /   \      
numTrees(2) numTrees(0)    numTrees(1) numTrees(1)   numTrees(0) numTrees(2)              
         i = 3                      i = 2                     i = 1           
         
                            i
    =>              /       \ 
            numTrees(i-1)    numTrees(n-i)
With that in mind, we have the following -


class Solution {
public:
    int numTrees(int n) {
        if(n <= 1) return 1;
        int ans = 0;
        for(int i = 1; i <= n; i++) 
            ans += numTrees(i-1) * numTrees(n-i);
        return ans;
    }
};
The base condition could also change from n <= 1 to n < 1, since we only need empty tree = 1 as base condition
class Solution {
public:
    int numTrees(int n) {
        if(n < 1) return 1;
        int ans = 0;
        for(int i = 1; i <= n; i++) 
            ans += numTrees(i-1) * numTrees(n-i);
        return ans;
    }
};
Time Complexity : O(3^N), where N is the given number of nodes in BST. Read here for proof.
Space Complexity : O(N), the maximum recursive stack depth.

✔️ Solution - II (Dynamic Programming - Memoization)
The above approach times out due to lots of unnecessary repeated calculation.
f(i) = numTrees(i)
                                                                                                 f(5)
                                __________________________________|____________________________________________                                 
                              ↙                            ↓                ↓                ↓                 ↘
                       (f(0)*           f(4))                 f(1)*f(3)        f(2)*f(2)        f(3)*f(1)          f(4)*f(0)
                     _____________|_____________           ⬆️          ⬆️  ⬆️         ⬆️                 ⬆️
                    ↙        ↓        ↓         ↘         
            f(0)f(3)     f(1)f(2)   f(2)f(1)   f(3)f(0)      
        ______|_____       ⬆️     ⬆️        ⬆️
          ↙      ↓     ↘
      f(0)f(2) f(1)f(1) f(2)f(1)
          __|__             ⬆️ 
         ↙       ↘
     f(0)f(1)  f(1)f(0)
In the above diagram, drawing out even the partial recursion tree for the above approach, we can find that there are many redundant repeated calculations. We can instead store or memoize these result and later avoid repeated calculations over and over again.
The approach and code will be very similar. The only change is every time we calculate the result for numTrees(i), we store the result in dp[i] and only then return it. After that, each time we encounter dp[i] that's already calculated, we can directly return the result. This way, we won't solve for the same numTrees(i) multiple times.
class Solution {
public:
    int dp[20]{};
    int numTrees(int n) {
        if(n <= 1) return 1;
        if(dp[n]) return dp[n];
        for(int i = 1; i <= n; i++) 
            dp[n] += numTrees(i-1) * numTrees(n-i);
        return dp[n];
    }
};
The base condition could also change from n <= 1 to n < 1, since we only need empty tree = 1 as base condition
class Solution {
public:
    int dp[20]{};
    int numTrees(int n) {
        if(n < 1) return 1;
        if(dp[n]) return dp[n];
        for(int i = 1; i <= n; i++) 
            dp[n] += numTrees(i-1) * numTrees(n-i);
        return dp[n];
    }
};
Time Complexity : O(N^2)
Here we calculate numTrees(i) (for 1<=i<=N) only once and memoize it which will take O(N). For calculating each of numTrees(i), we need N iterations to calculate numTrees(0) * numTrees(i) + numTrees(1) * numTrees(i-1) + numTrees(2) * numTrees(i-2)+ ... + numTrees(i) * numTrees(0). Thus, the overall time complexity becomes O(N * N).
Space Complexity : O(N), required for recursion and memoization

✔️ Solution - III (Dynamic Programming - Tabulation)
We can also solve it using iterative dynamic programming. Again, the logic is similar to above with slight change in approach that we start from base conditions instead of other way around.
- We have base conditions of dp[0] = dp[1] = 1.
- Then we calculate result for each number of nodes i from 2...n one after another.
- For i nodes. we can consider each of the node j from 1...i as the root node of BST.
- Considering the jth node as the root node in BST having total of i nodes, the final result is summation of dp[j-1] * dp[i-j], for all j from 1...i. (Comparing to above solution dp[j-1] = numTrees(j-1) and dp[i-j]=numTrees(i-j))
class Solution {
public:
    int numTrees(int n) {
        vector<int> dp(n+1);
        dp[0] = dp[1] = 1;
        for(int i = 2; i <= n; i++) 
            for(int j = 1; j <= i; j++)
                dp[i] += dp[j-1] * dp[i-j];
        return dp[n];
    }
};
- Actually if only define dp[0] = 1, NO need dp[1] = 1 is also fine, which exactly only require mapping to empty tree condition
- Then we calculate result for each number of nodes i from 1...n one after another instead from 2 ...n.
class Solution {
public:
    int numTrees(int n) {
        vector<int> dp(n+1);
        dp[0] = 1;
        for(int i = 1; i <= n; i++) 
            for(int j = 1; j <= i; j++)
                dp[i] += dp[j-1] * dp[i-j];
        return dp[n];
    }
};
Time Complexity : O(N^2), we iterate over the range i=[2, n] and iteratively calculate dp[i]. The total number of operations performed equals 2+3+4+5..n = (n*(n+1)/2)-1 ≈ O(N^2)
Space Complexity : O(N), required to store the results in dp

✔️ Solution - IV (Catalan Numbers)
Observing the series we get above for various numTrees(n), we see that it is infact a series of popular numbers known as Catalan Numbers. This approach is hard to get unless you are already familiar with catalan numbers and probably wont be expected in interview either. But I am mentioning this approach as another possible and more efficient solution to this question.
We can use the following formula for calculating catalan numbers Cn to calculate the result in O(N) time complexity -

We will use 1st equation of above image with binomial coefficient function - ncr in C++ to avoid overflow. In python, we can directly calculate factorial as it can handle long numbers
class Solution {
public:
    long ncr(int n, int r) {
        long ans = 1;
        for(int i = 0; i < r; i++) {
            ans *= n-i;
            ans /= i+1;
        }
        return ans;   
    }
    int numTrees(int n) {
        return ncr(2*n, n) / (n + 1);
    }
};
Time Complexity : O(N) The ncr function runs in O(N) time. In python, the factorial function takes O(N) time as well.
Space Complexity : O(1)

✔️ Solution - V (Catalan Numbers - 2)
The Catalan Numbers also follow the below recurrence relation -

This can be said to be a kind of dynamic programming approach where our next result depends only on previous one. This is slightly easier to implement in code than the 1st formula for catalan numbers.
class Solution {
public:
    int numTrees(int n) {
        long ans = 1;
        for(int i = 0; i < n; i++) 
            ans *= (4*i+2) / (i+2.);
        return ans;
    }
};
Time Complexity : O(N)
Space Complexity : O(1)
--------------------------------------------------------------------------------
Refer to
https://algo.monster/liteproblems/96
Problem Description
The problem asks for the number of structurally unique Binary Search Trees (BSTs) that can be formed using exactly n nodes, each node having a unique value from 1 to n. A Binary Search Tree is a tree where each node satisfies the following conditions:
- The left subtree of a node contains only nodes with keys less than the node's key.
- The right subtree of a node contains only nodes with keys greater than the node's key.
- Both the left and right subtrees must also be binary search trees.
Understanding this, we need to calculate the count of all possible unique tree structures without considering the actual node values, as the unique values will be distributed in the same structure in exactly one way due to BST property.
Intuition
To solve this problem, we can use a method known as dynamic programming. Dynamic programming breaks down the problem into smaller subproblems and uses the results of these subproblems to solve larger ones.
The intuition behind the solution can be understood in terms of the following points:
1.Consider that the number of unique BSTs with 0 nodes (an empty tree) is 1. This serves as our base case.
2.The key to solving the problem is realizing that when we choose a number i from 1 to n to be the root of a BST, then 1 to i-1 will necessarily form the left subtree and i+1 to n will form the right subtree.
3.The number of unique BSTs that can be formed with i as the root is the product of the number of unique BSTs that can be formed with 1 to i-1 and the number of unique BSTs that can be formed with i+1 to n.
4.Thus, we iterate through all numbers from 1 to n, treating each as the root, and multiply the number of ways to form left and right subtrees, summing these products up to get the total number of unique BSTs for n nodes.
The dynamic programming array dp will hold the solution to our subproblems where dp[i] represents the number of unique BSTs that can be formed with i distinct nodes. We iteratively fill up this array by using the results of smaller subproblems to build the solutions to larger subproblems.
Solution Approach
The solution uses dynamic programming to build up the number of unique BSTs that can be formed for each number of nodes from 0 to n. The following steps are executed in the solution:
1.A list dp is initialized with n + 1 elements, all set to 0. This list holds the solutions to our subproblems where dp[i] gives the number of unique BSTs that can be formed with i nodes. Since we start counting nodes from 1, we make our list one element larger than n to make it easier to refer to dp[n].
2.The first element dp[0] is set to 1. This represents the fact that there is exactly one BST that can be formed with 0 nodes, the empty tree.
3.We then use a nested loop where we consider every number i from 1 to n to represent the number of nodes we are currently interested in. For each i, we consider every possible root j from 1 to i. Hence, we calculate the number of unique trees with j as the root by multiplying the number of unique trees that can be formed with j-1 nodes (left subtree) and i-j nodes (right subtree).
4.For each i and j, we update the dp array by adding the product dp[j] * dp[i - j - 1] to dp[i]. This is done because when j is the root, there are dp[j] ways to construct the left subtree from 0 to j-1 (dp[j]'s coming from j-1 - 0 + 1 = j) and dp[i - j - 1] ways to construct the right subtree from j+1 to i - 1(dp[i - j - 1]'s i - j - 1 coming from (i - 1) - (j+1) + 1 = i - j - 1, and be careful, its not i but i - 1, the i - 1 is coming from the 'root' defined from 0 to '< i', the maximum 'root' is 'i - 1'). Since the root is fixed, the left and right subtrees form independently of each other; thus the total number is the product of possibilities for each side.
5.After the loops complete, dp[n] contains the final result, which is the number of unique BSTs that can be made with n distinct nodes.
In our case, dp[-1] is equivalent to dp[n] since Python supports negative indexing and refers to the last element of the list. This value is then returned by the function.
This implementation uses a bottom-up approach, and each subproblem is solved only once and then reused, leading to an efficient algorithm with a time complexity of O(n^2). The space complexity is O(n), as we need to store the number of unique BSTs for each number from 0 to n.
Example Walkthrough
To illustrate the solution approach, let's walk through a small example where n = 3. We are seeking to find the number of structurally unique BSTs that can be formed with nodes valued from 1 to 3.
According to the solution approach, these are the steps we would follow:
1.Initialize a dynamic programming list dp of size n+1 and set all its elements to 0. This list will store the number of unique BSTs for each number of nodes, so in our case, dp = [0, 0, 0, 0].
2.Set the base case dp[0] to 1, which represents the count of unique BSTs with 0 nodes (empty tree). It means that our dp list now looks like this: dp = [1, 0, 0, 0].
3.Now, we need to compute dp[i] for each i from 1 to n. Let's start by finding dp[1].
- For i = 1 (with only one node), regardless of the value, there's only one way to create a BST. So, dp[1] = 1.
4.Let's move on to compute dp[2] (two nodes). We have two scenarios here:
- We pick 1 as the root. There is dp[0] ways to arrange the left subtree since there are zero nodes to the left of 1, and dp[1] ways to arrange the right subtree, with just one node 2.
- We pick 2 as the root. There is dp[1] ways to arrange the left subtree with just one node 1, and dp[0] ways for the right subtree since there are no nodes to the right of 2.
- The total, combining both scenarios, gives us dp[2] = dp[0] * dp[1] + dp[1] * dp[0] = 1 * 1 + 1 * 1 = 2.
5.Finally, we have to compute dp[3].
- Picking 1 as the root: No nodes on the left, so we have dp[0] ways; and two nodes on the right (2 and 3), giving us dp[2] ways.
- Picking 2 as the root: One node on the left (1), so dp[1] ways; and one node on the right (3), also dp[1] ways.
- Picking 3 as the root: Two nodes on the left (1 and 2), so dp[2] ways; and no nodes on the right, giving dp[0] ways.
- We sum up all these products to get: dp[3] = dp[0] * dp[2] + dp[1] * dp[1] + dp[2] * dp[0] = 1 * 2 + 1 * 1 + 2 * 1 = 5.
So, the array dp at the end of our computation is [1, 1, 2, 5]. The number of unique BSTs that can be formed with 3 nodes is dp[3], which is 5. Hence, the function would return 5 for n = 3. This method can be extended for any larger n, using the same dynamic programming approach.
Solution Implementation
class Solution {
    // Function to compute number of unique BSTs with n nodes
    public int numTrees(int n) {
        // Initialise the dp array to store the number of unique BSTs for each count of nodes
        // dp[i] gives the number of unique BSTs that can be formed with i nodes
        int[] dp = new int[n + 1];
      
        // There is one unique BST for a tree with zero nodes, which is an empty tree
        dp[0] = 1;
      
        // Iterate over each count of nodes from 1 to n
        for (int nodes = 1; nodes <= n; ++nodes) {
            // Calculate the number of unique BSTs for 'nodes' number of nodes
            for (int root = 0; root < nodes; ++root) {
                // For each position 'root', the number of trees is the product of the
                // number of unique trees in the left subtree (dp[root])
                // and the number of unique trees in the right subtree (dp[nodes - root - 1]).
                dp[nodes] += dp[root] * dp[nodes - root - 1];
            }
        }
      
        // Return the result for n nodes
        return dp[n];
    }
}
Time and Space Complexity
The given code is designed to calculate the number of structurally unique BSTs (binary search trees) that store values 1 through n using dynamic programming. To analyze the time and space complexity, we will consider the main operations and how they scale with respect to the input n.
Time Complexity
We initialize a list dp of size n+1 with zero, which takes O(n) time.
There is a nested loop where the outer loop runs from 1 to n and the inner loop runs i times (which is at most n times when i=n).
Within the inner loop, the corresponding element in dp is updated, and this operation is constant time, O(1).
Thus, the time complexity for the nested loops can be approximated as the sum of the first n natural numbers, which is O(n^2).
Therefore, the overall time complexity of the code is O(n^2).
Space Complexity
We have a list dp that contains n+1 elements, which represents the space complexity of O(n).
No other data structures are used that grow with n, so O(n) represents the total space complexity.
In conclusion, the time complexity of the code is O(n^2) and the space complexity is O(n).
--------------------------------------------------------------------------------
Q1: Develop from Native DFS to DP
Refer to Deepseek
当然可以。我们也可以用 带记忆化的 DFS（递归 + 备忘录） 来求解，本质上和动态规划是相同的状态转移，只是把计算顺序改为“自顶向下”的递归。
纯 DFS（无记忆化，会超时，但展现思路）
class Solution {
    public int numTrees(int n) {
        return dfs(1, n);
    }

    private int dfs(int start, int end) {
        if (start >= end) return 1;  // 空区间或单节点，只有一种结构
        int count = 0;
        for (int root = start; root <= end; root++) {
            int left = dfs(start, root - 1);
            int right = dfs(root + 1, end);
            count += left * right;
        }
        return count;
    }
}
这种写法虽然简洁，但存在大量重复计算（如 dfs(1, 2) 会被多次调用），在 n = 19 时就会超时。

这个不带记忆化的 DFS 解法，其时间复杂度是指数级的，具体为 O(n × Cₙ)，其中 Cₙ 是第 n 个卡特兰数（Catalan number）。由于卡特兰数增长速度约为 O(4ⁿ / n^{3/2})，所以总时间复杂度可视为 O(4ⁿ / n^{3/2})。
原因分析
- 对于每个区间 [start, end]，函数会枚举所有可能的根，并递归计算左右子区间。
- 没有记忆化，相同的子区间（例如 [1,2]）会在不同的递归路径中重复出现多次。
- 递归调用次数与所有可能的 二叉搜索树结构的总数 成正比，因为每棵树都对应一条从根到叶子的递归路径。而所有 BST 的数量就是第 n 个卡特兰数 Cₙ。
- 每个递归调用内部会循环遍历区间长度次，因此额外乘以一个 O(n) 因子。
空间复杂度则是 O(n)，因为递归调用栈的最大深度取决于树的高度，最坏情况下为 n（例如当根始终取最小值或最大值时）。
因此，该解法虽然正确，但在 n 较大（例如 n ≥ 19）时会非常慢，甚至超时。实际应用中应当使用记忆化 DFS 或动态规划来达到 O(n²) 的时间复杂度。

优化：DFS + 记忆化（Memoization）
使用一个二维数组 memo[start][end] 来缓存已经计算过的区间结果。
class Solution {
    public int numTrees(int n) {
        // memo[i][j] 表示区间 [i, j] 可构成的 BST 数量
        Integer[][] memo = new Integer[n + 2][n + 2];
        return dfs(1, n, memo);
    }

    private int dfs(int start, int end, Integer[][] memo) {
        if (start >= end) return 1;
        if (memo[start][end] != null) return memo[start][end];

        int count = 0;
        for (int root = start; root <= end; root++) {
            int left = dfs(start, root - 1, memo);
            int right = dfs(root + 1, end, memo);
            count += left * right;
        }
        memo[start][end] = count;
        return count;
    }
}
复杂度分析
- 时间：每个区间 [start, end] 只会被计算一次，共有 O(n²) 个区间，每个区间遍历根节点，总复杂度 O(n²)。
- 空间：O(n²) 用于存储备忘录。

进一步简化（因为只关心数量，不关心区间具体值）
因为 BST 的结构只与节点数量有关，与具体数值无关。比如 [1, 2] 和 [4, 5] 的 BST 数量是相同的。所以我们可以只用一维数组，用节点个数作为状态：
这个性质是理解二叉搜索树（BST）计数问题（LeetCode 96 和 95）的核心基础。让我详细解释。
1. 性质表述
BST 的结构只与节点数量有关，与节点具体数值无关。
也就是说，对于任意一组互不相同且按升序排列的节点值，能够构成的 BST 的形态（即树的结构）数量，只取决于节点个数 n，而不依赖于这些具体值是 {1,2,3} 还是 {10,20,30}，或是 {5,7,12}。
例如，用 [1,2,3] 能生成 5 种不同形态的 BST，用 [10,20,30] 也同样生成 5 种形态。虽然节点值不同，但树的形状（左、右子树的连接方式）是相同的。
2. 为什么成立？
BST 的性质是：
- 左子树中所有节点值 < 根节点值；
- 右子树中所有节点值 > 根节点值。
这个性质只依赖于节点之间的相对大小顺序，而不是绝对值。当我们讨论一个区间 [L, R]（连续整数）时，选择某个值 k 作为根，其左子树区间为 [L, k-1]，右子树区间为 [k+1, R]。左子树和右子树的节点数量分别是 (k-1 - L + 1) = k-L 和 (R - (k+1) + 1) = R-k。
如果我们把区间整体平移（例如 [1,3] 变为 [4,6]），相应根也平移，左右子区间的节点数量完全不变，只是具体数值改变了。而树的形态（即递归结构）仅由左右子树的节点数量决定，与它们的实际值无关。
因此，只要我们知道当前区间的大小 len = R-L+1，而不需要知道具体的 L 和 R，就能确定该区间能生成的 BST 数量。这正是动态规划状态压缩的关键。
3. 在 LeetCode 96（计数）中的应用
LeetCode 96 要求计算 n 个节点（值从 1 到 n）能构成多少种 BST。
由于性质成立，我们可以定义 dp[i] 为“i 个节点能构成的 BST 数量”。这里不需要知道具体的节点值，因为每个区间的大小为 i 时，数量相同。
状态转移方程：
dp[0] = 1  // 空树
dp[i] = sum_{j=0}^{i-1} dp[j] * dp[i-1-j]
其中 j 表示左子树的节点数，i-1-j 表示右子树的节点数。因为根节点占用一个，所以左右子树节点数之和为 i-1。这里完全不需要知道根节点的具体值，只需要知道左子树和右子树的节点个数即可。
这正体现了“只与节点数量有关”的思想。
4. 在 LeetCode 95（构造所有树）中的应用
LeetCode 95 要求实际生成所有 BST，每个节点都要有具体值。但递归构造时，我们依然利用区间 [start, end] 来表示当前可用的节点值范围。
递归函数 build(start, end) 生成区间内所有可能的树。在循环中，选择 i 作为根，递归生成 [start, i-1] 和 [i+1, end]。
这里虽然具体数值不同，但子区间 [start, i-1] 与 [1, i-start] 在结构上是同构的。如果我们用记忆化，可以缓存相同区间大小的结果（例如用 Map<Integer, List<TreeNode>> 按长度缓存），因为区间 [1,3] 和 [5,7] 生成的树形态是完全一样的，只是节点值不同。这在实际优化中很有用（但 LeetCode 95 通常不用缓存，因为 n 很小）。
5. 扩展到其他变体
这个性质不仅适用于数字 1..n，也适用于任何有序且无重复的节点集合。比如，给定一组键值，构建 BST 的数量也只与节点数有关。
它背后的数学本质是：BST 的形态对应于中序遍历序列的顺序，只要序列元素有全序关系，形态就只依赖于元素个数。
6. 为什么没有影响？
- 在计数时，我们只关心树形，不关心节点值，所以用数量作为状态足够。
- 在构造时，我们需要填入具体值，所以用区间表示，但形态复制依然是基于数量。
总结：这个性质是二叉搜索树组合计数的基石，它让我们能够从具体数值中抽象出来，用节点个数作为唯一参数进行递归或动态规划。
class Solution {
    public int numTrees(int n) {
        int[] memo = new int[n + 1];
        return dfs(n, memo);
    }

    private int dfs(int n, int[] memo) {
        if (n <= 1) return 1;
        if (memo[n] != 0) return memo[n];

        int count = 0;
        for (int root = 1; root <= n; root++) {
            int left = dfs(root - 1, memo);
            int right = dfs(n - root, memo);
            count += left * right;
        }
        memo[n] = count;
        return count;
    }
}
这个版本只依赖节点个数，更加简洁，与 DP 版本本质相同，只是递归方向不同。

总结
- 原生 DFS 不加记忆 直观但会超时。
- DFS + 记忆化 是典型的“自顶向下” DP，和“自底向上” DP 效率一致，都是 O(n²)。
- 如果面试要求写递归，带记忆化的 DFS 是最佳选择，既体现了分治思想，又避免了重复计算。
--------------------------------------------------------------------------------
Refer to Kimi
Here is the native DFS + Memoization solution for LeetCode 96. Unique Binary Search Trees.
Approach
The pure recursive idea is the same as LeetCode 95: for every possible root i in [start, end], count left trees × right trees.
However, without memoization, this is exponential (Catalan-level recursion). The key insight is:
The number of unique BSTs depends only on the length of the interval, not the actual values.
So we memoize by interval (start, end) to avoid recomputing identical subproblems.
Time Complexity: O(n^2) with memoization
Space Complexity: O(n^2) for memo table + $O(n)$ recursion stack
class Solution {
    private int[][] memo;
    
    public int numTrees(int n) {
        memo = new int[n + 1][n + 1];
        // Initialize with -1 to indicate "not computed yet"
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                memo[i][j] = -1;
            }
        }
        return dfs(1, n);
    }
    
    private int dfs(int start, int end) {
        // Base case: empty interval
        if (start > end) {
            return 1;
        }
        
        // Return cached result
        if (memo[start][end] != -1) {
            return memo[start][end];
        }
        
        int count = 0;
        for (int i = start; i <= end; i++) {
            int leftTrees = dfs(start, i - 1);
            int rightTrees = dfs(i + 1, end);
            count += leftTrees * rightTrees;
        }
        
        memo[start][end] = count;
        return count;
    }
}
--------------------------------------------------------------------------------
Why Memoization is Essential
Without MemoWith MemoSubproblemdfs(1, 3) computed multiple times via different pathsEach (start, end) computed onceTimeO(C_n) — exponentialO(n^2) — polynomialSpaceO(n) stackO(n^2) memo + O(n) stack
Example of reuse: dfs(1, 2) and dfs(2, 3) are different intervals, but dfs(1, 1) (single node) is reused as a left/right child across many parent calls.
--------------------------------------------------------------------------------
Alternative: Memo by Length Only
Since BST count depends only on how many nodes exist (not their values), you can simplify the memo to 1D:
class Solution {
    private int[] memo;
    
    public int numTrees(int n) {
        memo = new int[n + 1];
        Arrays.fill(memo, -1);
        return dfs(n);
    }
    
    private int dfs(int n) {
        if (n <= 1) return 1;
        if (memo[n] != -1) return memo[n];
        
        int count = 0;
        for (int i = 1; i <= n; i++) {
            // i-1 nodes on left, n-i nodes on right
            count += dfs(i - 1) * dfs(n - i);
        }
        
        memo[n] = count;
        return count;
    }
}
This is cleaner and more directly reveals the Catalan number structure. Both versions are correct; the 1D version is preferred for interviews.

Q2: In DFS solution it recursively going from n to 1, i am talking about "helper(n)" start from n to 1, so logically, its mapping DP solution outside loop should loop from 1 to n, and that's the exactly mapping ?
你的理解完全正确！让我来详细解释这个映射关系：
Native DFS
class Solution {
    public int numTrees(int n) {
        return helper(n);
    }

    private int helper(int n) {
        // There is one unique BST for a tree with zero nodes, which is an empty tree
        // so n = 0 create empty tree and return 1
        if(n < 1) {
            return 1;
        }
        int result = 0;
        for(int i = 1; i <= n; i++) {
            // For each root i, left subtree has i - 1 nodes(i from 1 to i - 1, total i - 1), 
            // right subtree has n - i nodes(i from i + 1 to n, total n - i)
            result += helper(i - 1) * helper(n - i);
        }
        return result;
    }
}
Exactly mapping DP
class Solution {
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= i; j++) {
                dp[i] += dp[j - 1] * dp[i - j];
            }            
        }
        return dp[n];
    }
}
递归（DFS）自顶向下的调用路径
在递归解法中：
- 我们调用 helper(n)，然后它内部调用 helper(i-1) 和 helper(n-i)，其中 i 从 1 到 n。
- 这些子问题的参数都 小于 n（因为 i-1 < n 且 n-i < n）。
- 所以递归实际上是 从大问题下降到小问题，直到 n <= 1 返回。
DP 自底向上的填充顺序
在 DP 中：
- 我们想要计算 dp[n]，但必须先知道所有 dp[k]（k < n）的值。
- 所以我们 从小规模开始：先算 dp[1]，再算 dp[2]，以此类推，直到 dp[n]。
- 外循环 for (int i = 1; i <= n; i++) 正好对应 “先算小规模，再算大规模” 的顺序。
映射关系
递归中的 helper(k)（k 为任意小于等于 n 的正整数）对应 DP 中的 dp[k]。
递归调用DP 对应值说明helper(i-1)dp[i-1]左子树规模，已算好helper(n-i)dp[n-i]右子树规模，已算好
在递归中，当我们计算 helper(n) 时，它需要先执行 helper(i-1) 和 helper(n-i) 并等待返回，这在调用栈上是 “先深入，再返回” 的过程。但在 DP 中，我们把这种 “先求子问题” 的顺序体现在了外循环的顺序上——先求规模小的，再求规模大的。
所以，虽然递归是从 n 开始向下，DP 是从 1 开始向上，但两者在 依赖关系 上是完全一致的：dp[i] 只依赖于 dp[小于 i] 的值，因此从 1 到 n 递增是最自然且准确的映射。
为什么不是从 n 到 1？
如之前所述，如果外循环从 n 递减到 1，那么当我们计算 dp[n] 时，dp[n-1]、dp[n-2] 等还没有被初始化（均为 0），结果会错误。因为递推是 dp[i] 依赖 dp[小于 i]，所以必须保证子问题先于父问题计算。
结论
- DFS 递归：helper(n) → 内部调用 helper(更小) → 直到基例。
- DP 迭代：先计算所有小规模 dp[1..n]，然后用它们组合出大规模 dp[n]。
这两种方式的 逻辑完全等价，只是计算方向相反（自顶向下 vs. 自底向上）。因此，外循环从 1 到 n 是唯一正确且与递归逻辑匹配的写法。你的理解完全正确！

Q3: I have observed 2 kind of DP solution:
Style 1:
class Solution {
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= i; j++) {
                dp[i] += dp[j - 1] * dp[i - j];
            }            
        }
        return dp[n];
    }
}
Style 2:
class Solution {
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = dp[1] = 1;
        for(int i = 2; i <= n; i++) {
            for(int j = 1; j <= i; j++) {
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        return dp[n];
    }
}
what's their difference ?  and what's their mapping Native DFS solution ? i think it also be different, right ?
两种写法逻辑完全等价，只是 base case 的显式程度不同。下面详细解释差异，以及它们与 Native DFS 的精确映射。
--------------------------------------------------------------------------------
一、Style 1 vs Style 2 的差异
Style 1
dp[0] = 1;
for (int i = 1; i <= n; i++) {
    for (int j = 1; j <= i; j++) {
        dp[i] += dp[j - 1] * dp[i - j];
    }
}
Style 2
dp[0] = dp[1] = 1;
for (int i = 2; i <= n; i++) {
    for (int j = 1; j <= i; j++) {
        dp[i] += dp[j - 1] * dp[i - j];
    }
}
区别分析
维度Style 1Style 2Base Case 显式化只显式 dp[0] = 1显式 dp[0] = 1 和 dp[1] = 1循环起点i = 1i = 2dp[1] 怎么来的由 i = 1 的循环算出：dp[1] += dp[0] * dp[0] = 1直接赋值结果完全相同完全相同
Style 1 更简洁（少写一行），Style 2 更直观（把 n <= 1 的边界都显式列出）。
--------------------------------------------------------------------------------
二、与 Native DFS 的映射关系
Native DFS（1D memo by length）：
private int dfs(int n) {
    if (n <= 1) return 1;           // base case: 空树或单节点
    if (memo[n] != -1) return memo[n];
    
    int count = 0;
    for (int i = 1; i <= n; i++) {  // i 作为根，把 n 个节点分成左右
        count += dfs(i - 1) * dfs(n - i);
    }
    
    memo[n] = count;
    return count;
}
精确映射表
DFS 元素DP 对应dfs(n)dp[n]if (n <= 1) return 1dp[0] = 1（空树）<br>+ dp[1] = 1（单节点）for (int i = 1; i <= n; i++)for (int j = 1; j <= i; j++)dfs(i - 1)dp[j - 1]（左子树节点数）dfs(n - i)dp[i - j]（右子树节点数）count += ...dp[i] += ...
关键洞察
DP 是 DFS 的"自底向上"填表版本。
- DFS 从 n 开始，递归到 n-1, n-2, ... 直到 0, 1
- DP 从 0, 1 开始，从小到大填表，直到 n
--------------------------------------------------------------------------------
三、Style 1 和 Style 2 分别对应哪种 DFS 风格？
Style 1 对应 "最小 Base Case" DFS
// DFS: 只把 n == 0 当作最底层 base case，n == 1 也走递归
if (n == 0) return 1;  // 唯一显式 base case
// n == 1 时进入循环：i=1, count += dfs(0)*dfs(0) = 1
DP 里就是：
dp[0] = 1;  // 只初始化这一个
// dp[1] 靠 i=1 的循环自然得到
Style 2 对应 "完整 Base Case" DFS
// DFS: 把 n <= 1 都当作 base case，直接返回
if (n <= 1) return 1;  // 两个 base case 都显式
DP 里就是：
dp[0] = dp[1] = 1;  // 两个都显式初始化
// 循环从 i=2 开始，因为 0 和 1 已经知道了
--------------------------------------------------------------------------------
四、一句话总结
Style 1 和 Style 2 没有本质区别，只是 dp[1] = 1 是"算出来"还是"写出来"的区别。两者都与 Native DFS 完全等价，只是 DFS 是"自顶向下递归"，DP 是"自底向上填表"。

Then why below solution is wrong ?
Input
n = 3
Use Testcase
Output = 12
Expected = 5
class Solution {
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = dp[1] = 1;
        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= i; j++) {
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        return dp[n];
    }
}
The bug is in the outer loop starting from i = 1 while also pre-initializing dp[1] = 1.
When i = 1, the inner loop executes j = 1:
dp[1] += dp[0] * dp[0];  // dp[1] = 1 + 1*1 = 2
This overwrites the correct value dp[1] = 1 with 2, and the error cascades:
StepCalculationWrong dpCorrect dpi=1, j=1dp[1] += dp[0]*dp[0]dp[1]=2dp[1]=1i=2, j=1dp[2] += dp[0]*dp[1]+2+1i=2, j=2dp[2] += dp[1]*dp[0]+2+1i=2 totaldp[2]=4dp[2]=2i=3, j=1dp[3] += dp[0]*dp[2]+4+2i=3, j=2dp[3] += dp[1]*dp[1]+4+1i=3, j=3dp[3] += dp[2]*dp[0]+4+2i=3 totaldp[3]=12dp[3]=5
The Fix
Option 1: Pre-init dp[1], start loop from i = 2 (Style 2)
dp[0] = dp[1] = 1;
for (int i = 2; i <= n; i++) {  // start from 2, not 1
    for (int j = 1; j <= i; j++) {
        dp[i] += dp[j - 1] * dp[i - j];
    }
}
Option 2: Only init dp[0], let dp[1] be computed naturally (Style 1)
dp[0] = 1;
for (int i = 1; i <= n; i++) {  // start from 1 is fine
    for (int j = 1; j <= i; j++) {
        dp[i] += dp[j - 1] * dp[i - j];
    }
}
In this version, when i = 1, dp[1] starts at 0 and becomes dp[0]*dp[0] = 1, which is correct.
Rule of Thumb
If you init...Then loop starts at...dp[0] = 1 onlyi = 1dp[0] = dp[1] = 1i = 2
Mixing both (dp[1] = 1 + i = 1) causes double-counting because dp[1] is initialized as a base case but then treated as a value to be computed inside the loop.


Refer to
L95.P11.8.Unique Binary Search Trees II (Ref.L241,L96)





