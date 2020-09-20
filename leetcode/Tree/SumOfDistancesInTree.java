/**
 Refer to
 https://leetcode.com/problems/sum-of-distances-in-tree/
 An undirected, connected tree with N nodes labelled 0...N-1 and N-1 edges are given.
 The ith edge connects nodes edges[i][0] and edges[i][1] together.
 Return a list ans, where ans[i] is the sum of the distances between node i and all other nodes.
 Example 1:
 Input: N = 6, edges = [[0,1],[0,2],[2,3],[2,4],[2,5]]
 Output: [8,12,6,10,10,10]
 Explanation: 
 Here is a diagram of the given tree:
   0
  / \
 1   2
    /|\
   3 4 5
 We can see that dist(0,1) + dist(0,2) + dist(0,3) + dist(0,4) + dist(0,5)
 equals 1 + 1 + 2 + 2 + 2 = 8.  Hence, answer[0] = 8, and so on.
 Note: 1 <= N <= 10000
*/

// Solution 1: Brute force 1 pass DFS (TLE) --> Time Complexity: O(n^2)
// Refer to
// https://leetcode.com/problems/sum-of-distances-in-tree/discuss/466998/TLE-C%2B%2B-DFS-%2B-undirected-graph..Need-some-idea-to-fix-this-problem.-6469-accepted.
class Solution {
    public int[] sumOfDistancesInTree(int N, int[][] edges) {
        int[] result = new int[N];
        Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
        for(int i = 0; i < N; i++) {
            map.put(i, new ArrayList<Integer>());
        }
        for(int[] edge : edges) {
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }
        for(int i = 0; i < N; i++) {
            result[i] = helper(i, map, 0, new boolean[N]);
        }
        return result;
    }
    
    private int helper(int i, Map<Integer, List<Integer>> map, int depth, boolean[] visited) {
        if(visited[i]) {
            return 0;
        }
        visited[i] = true;
        int sum = 0;
        sum += depth;
        for(int j : map.get(i)) {
            if(!visited[j]) {
                sum += helper(j, map, depth + 1, visited);
            }
        }
        return sum;
    }
}

// Solution 2: 2 PASS DFS --> Time Complexity O(N)
// Refer to
// https://leetcode.com/problems/sum-of-distances-in-tree/solution/
/**
Preword
Well, another long solution.
what I am always trying is to:

let you understand my solution (with my poor explanation)
prevent from reading my codes

Intuition
What if given a tree, with a certain root 0?
In O(N) we can find sum of distances in tree from root and all other nodes.
Now for all N nodes?
Of course, we can do it N times and solve it in O(N^2).
C++ and Java may get accepted luckily, but it's not what we want.

When we move our root from one node to its connected node,
one part of nodes get closer, one the other part get further.

If we know exactly how many nodes in both parts, we can solve this problem.

With one single traversal in tree, we should get enough information for it and
don't need to do it again and again.


Explanation
Let's solve it with node 0 as root.

Initial an array of hashset tree, tree[i] contains all connected nodes to i.
Initial an array count, count[i] counts all nodes in the subtree i.
Initial an array of res, res[i] counts sum of distance in subtree i.

Post order dfs traversal, update count and res:
count[root] = sum(count[i]) + 1
res[root] = sum(res[i]) + sum(count[i])

Pre order dfs traversal, update res:
When we move our root from parent to its child i, count[i] points get 1 closer to root, n - count[i] nodes get 1 futhur to root.

res[i] = res[root] - count[i] + N - count[i]

return res, done.
Time Complexity:
dfs: O(N) time
dfs2: O(N) time
*/

// https://leetcode.com/problems/sum-of-distances-in-tree/discuss/130583/C%2B%2BJavaPython-Pre-order-and-Post-order-DFS-O(N)

// https://www.cnblogs.com/grandyang/p/11520804.html
/**
Example 1:
Input: N = 6, edges = [[0,1],[0,2],[2,3],[2,4],[2,5]]
Output: [8,12,6,10,10,10]
Explanation:
Here is a diagram of the given tree:
  0
 / \
1   2
   /|\
  3 4 5
We can see that dist(0,1) + dist(0,2) + dist(0,3) + dist(0,4) + dist(0,5)
equals 1 + 1 + 2 + 2 + 2 = 8.  Hence, answer[0] = 8, and so on.

这道题给了一棵树，实际上是无向图，让求每个结点到其他所有结点的距离之和。这里并没有定义树结构，而是给了每条边的两端结点，
那么还是先建立邻接链表吧，然后当作无向图来处理吧。由于结点的个数为N，所以直接用二维数组建立邻接链表，注意无向图是双向的。
好，现在表示树的数据结构有了，该如何求距离之和呢？先从最简单的例子还是看吧，假如只有一个结点的话，由于不存在其他结点，
则也没有距离之说，所以是0。若有连两个结点，比如下面所示：
  0
 / 
1   

对于结点0来说，距离之和为1，因为只有结点1距离其为1，此时结点0只有1个子结点。若有三个结点的话，比如：

  0
 / \
1   2

则所有结点到结点0的距离之和为2，而结点0也正好有两个子结点，是不是有某种联系呢，还是说我们想多了？再来看一个稍稍复杂些的例子吧：

    0
   / \
  1   2
 / \
3   4

这里的话所有结点到结点0的距离之和为6，显然不是子结点的个数，整个树也就5个结点。对于左子树，这个正好是上一个讨论的例子，左子树
中到结点1的距离之和为2，而左子树总共有3个结点，加起来是5。而右子树只有一个结点2，在右子树中的距离之和为0，右子树总共有1个结点，
5加上1，正好是6？恭喜，这就是算每个子树中的结点到子树根结的距离之和的方法，即所有子结点的距离之和加上以子结点为根的子树结点个数。
说的好晕啊，用代码来表示吧，需要两个数组 count 和 res，其中 count[i] 表示以结点i为根结点的子树中结点的个数，res[i] 表示其他
所有结点到结点i的距离之和。根据上面的规律，可以总结出下面两个式子：

count[root] = sum(count[i]) + 1
res[root] = sum(res[i]) + sum(count[i])

这里的 root 表示所有的子树的根结点，i表示的是 root 的相连子结点，注意必须是相连的，这里不一定是二叉树，所有可能会有多个子结点。
另外需要注意的是这里的 res[root] 表示的是以 root 为根结点的子树中所有的结点到 root 的距离之和，其他非子树中结点的距离之和还没
有统计。可以发现这两个式子中当前结点的值都是由其子结点决定的，这种由下而上的特点天然适合用后序遍历来做，可以参见这道题 
Binary Tree Postorder Traversal，还好这里不用写迭代形式的后序遍历，用递归写就简单的多了。同时还要注意的是用邻接链表表示的无向
图遍历时，为了避免死循环，一般是要记录访问过的结点的，这里由于是树的结构，不会存在环，所以可以简单化，直接记录上一个结点 pre 就
行了，只有当前结点i和 pre 不同才继续处理。

好，更新完了所有的 count[root] 和 res[root] 之后，就要来更新所有的 res[i] 了，因为上面的讲解提到了 res[root] 表示的是以 root 
为根结点的子树中所有的结点到 root 的距离，那么子树之外的结点到 root 的距离也得加上，才是最终要求的 res[i]。虽然现在还没有更新
所有的 res[i]，但是有一个结点的 res 值是正确的，就是整个树的根结点，这个真正的 res[root] 值是正确的！现在假设要计算 root 结点
的一个子结点i的 res 值，即要计算所有结点到结点i的距离，此时知道以结点i为根结点的子树的总结点个数为 count[i]，而这 count[i] 个
结点之前在算 res[root] 时是到根结点 root 的距离，但是现在只要计算到结点i的距离，所以这 count[i] 个结点的距离都少了1，而其他
所有的结点，共 N - count[i] 个，离结点i的距离比离 root 结点的距离都增加了1，所以 res[i] 的更新方法如下：

res[i] = res[root] - count[i] + N - count[i]

这里是从上而下的更新，可以使用最常用的先序遍历，可以参见这道题 Binary Tree Preorder Traversal，这样更新下来，所有的 res[i] 就都是题目中要求的值了
*/
class Solution {
    // Initial an array count, count[i] counts all nodes in the subtree i.
    // Initial an array of result, result[i] counts sum of distance in subtree i.
    int[] result; // res[i] 表示其他所有结点到结点i的距离之和
    int[] count;  // 其中 count[i] 表示以结点i为根结点的子树中结点的个数
    Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
    public int[] sumOfDistancesInTree(int N, int[][] edges) {
        result = new int[N];
        count = new int[N];
        //Arrays.fill(count, 1);
        for(int i = 0; i < N; i++) {
            map.put(i, new ArrayList<Integer>());
        }
        for(int[] edge : edges) {
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }
        helper(0, -1);
        helper2(0, -1);
        return result;
    }
    
    // Post-order traversal
    // 可以发现这两个式子中当前结点(node)的值都是由其子结点(child)决定的，
    // 这种由下而上的特点天然适合用后序遍历来做
    private void helper(int node, int parent) {
        for(int child : map.get(node)) {
            if(child != parent) {
                helper(child, node);
                count[node] += count[child];
                // 算每个子树中的结点到子树根结的距离之和的方法，
                // 即所有子结点的距离之和加上以子结点为根的子树结点个数
                result[node] += result[child] + count[child];
            }
        }
        count[node]++; // count[i] 表示以结点i为根结点的子树中结点的个数
    }
    
    // Pre-order traversal
    // we need to use res[node] first to deduce root[i], that's why need pre-order
    // 现在假设要计算 root 结点的一个子结点i的 res 值，即要计算所有结点到结点i的距离，
    // 此时知道以结点i为根结点的子树的总结点个数为 count[i]，而这 count[i] 个结点
    // 之前在算 res[root] 时是到根结点 root 的距离，但是现在只要计算到结点i的距离，
    // 所以这 count[i] 个结点的距离都少了1，而其他所有的结点，共 N - count[i] 个，
    // 离结点i的距离比离 root 结点的距离都增加了1，所以 res[i] 的更新方法如下
    private void helper2(int node, int parent) {
        for(int child : map.get(node)) {
            if(child != parent) {
                result[child] = result[node] - count[child] + count.length - count[child];
                helper2(child, node);
            }
        }
    }
}
