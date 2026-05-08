https://leetcode.com/problems/tiling-a-rectangle-with-the-fewest-squares/description/
Given a rectangle of size n x m, return the minimum number of integer-sided squares that tile the rectangle.
 
Example 1:

Input: n = 2, m = 3
Output: 3
Explanation: 3 squares are necessary to cover the rectangle.
2 (squares of 1x1)
1 (square of 2x2)

Example 2:

Input: n = 5, m = 8
Output: 5

Example 3:

Input: n = 11, m = 13
Output: 6
 
Constraints:
- 1 <= n, m <= 13
--------------------------------------------------------------------------------
Attempt 1: 2026-05-04
Solution 1: Native DFS (60 min, TLE 2/38)
class Solution {
    public int tilingRectangle(int n, int m) {
        // Make the height the larger dimension, width the smaller one
        int W = Math.min(n, m);
        int H = Math.max(n, m);
        int[] heights = new int[W];
        return helper(heights, W, H);
    }

    private int helper(int[] heights, int W, int H) {
        // Check if the rectangle is completely filled
        boolean full = true;
        for(int h : heights) {
            if(h != H) {
                full = false;
                break;
            }
        }
        if(full) {
            return 0;
        }
        // Find the leftmost column with minimum height
        // Find minimum height
        int minH = H + 1;
        for(int h : heights) {
            if(h < minH) {
                minH = h;
            }
        }
        // leftmost column with height minH
        int i = 0;
        while(heights[i] != minH) {
            i++;
        }
        // Determine how many consecutive columns have height minH starting at i
        int maxRun = 0;
        while(i + maxRun < W && heights[i + maxRun] == minH) {
            maxRun++;
        }
        // Try every possible square size L that fits in the current run and height
        int best = Integer.MAX_VALUE;
        for(int L = 1; L <= maxRun && L + minH <= H; L++) {
            // Create new heights after placing an L x L square
            int[] newHeights = heights.clone();
            for(int j = i; j < i + L; j++) {
                newHeights[j] = L + minH;
            }
            int tmp = helper(newHeights, W, H);
            if(tmp != Integer.MAX_VALUE) {
                best = Math.min(best, tmp + 1);
            }
        }
        return best;
    }
}
Time Complexity
This implementation uses DFS without memoization. The state is represented by an array heights of length W = min(n, m), where each height is between 0 and H = max(n, m).
- Number of possible states (without constraints) is (H+1)^W. For the worst allowed case (13×13), this is 14^13 ≈ 7.9 × 10^14, far too many to enumerate.
- The algorithm’s branching factor at each step is at most W (by trying square sizes L = 1..maxRun).
- The depth of recursion is bounded by the number of placed squares, which in the worst case (all 1×1 squares) is n × m ≤ 169.
Without memoization, the same intermediate state can be reached via different sequences of placements and will be recomputed each time. In the worst case, the recursion tree can be exponential in the total area, i.e., O(W^(n*m)). For n = m = 13, this is astronomically large and the algorithm will not finish within a reasonable time (it will likely time out on LeetCode).
Practical note: With memoization (adding a HashMap keyed by an encoded state), the time complexity drops to the number of distinct reachable states, which is much smaller (on the order of thousands for 13×13). However, the given code lacks memoization.
Space Complexity
Space usage is determined by:
1.Recursion stack – maximum depth ≤ n × m ≤ 169.
2.Array copies – each recursive call creates a new array clone of length W ≤ 13. At any point, the call stack holds at most depth clones simultaneously.
Thus, the space complexity is O(W × depth) = O(min(n, m) × n × m) = O(13 × 169) = O(2197), which is effectively O(1) in terms of input size due to the small constraints. In practice, it's about a few kilobytes.
However, note that if we consider the theoretical upper bound without considering the small constants, it is O(min(n, m) × n × m), which is still polynomial in the input dimensions.
--------------------------------------------------------------------------------
Summary for the Given Code
ComplexityValueTimeExponential, worst-case Ω(2^(n*m)) (without memoization)SpaceO(min(n, m) × n × m) ≈ O(13 × 169)
Because n, m ≤ 13, the space is small, but the time without memoization is prohibitive for many test cases. The intended solution for LeetCode 1240 uses memoization to make it feasible.

Solution 2: Memoization (60 min)
class Solution {
    public int tilingRectangle(int n, int m) {
        // Make the height the larger dimension, width the smaller one
        int W = Math.min(n, m);
        int H = Math.max(n, m);
        int[] heights = new int[W];
        // Memoization: encoded state -> minimum squares needed
        Map<Long, Integer> memo = new HashMap<>();
        return helper(heights, W, H, memo);
    }

    private int helper(int[] heights, int W, int H, Map<Long, Integer> memo) {
        long key = encode(heights, H);
        if(memo.containsKey(key)) {
            return memo.get(key);
        }
        // Check if the rectangle is completely filled
        boolean full = true;
        for(int h : heights) {
            if(h != H) {
                full = false;
                break;
            }
        }
        if(full) {
            return 0;
        }
        // Find the leftmost column with minimum height
        // Find minimum height
        int minH = H + 1;
        for(int h : heights) {
            if(h < minH) {
                minH = h;
            }
        }
        // leftmost column with height minH
        int i = 0;
        while(heights[i] != minH) {
            i++;
        }
        // Determine how many consecutive columns have height minH starting at i
        int maxRun = 0;
        while(i + maxRun < W && heights[i + maxRun] == minH) {
            maxRun++;
        }
        // Try every possible square size L that fits in the current run and height
        int best = Integer.MAX_VALUE;
        for(int L = 1; L <= maxRun && L + minH <= H; L++) {
            // Create new heights after placing an L x L square
            int[] newHeights = heights.clone();
            for(int j = i; j < i + L; j++) {
                newHeights[j] = L + minH;
            }
            int tmp = helper(newHeights, W, H, memo);
            if(tmp != Integer.MAX_VALUE) {
                best = Math.min(best, tmp + 1);
            }
        }
        memo.put(key, best);
        return best;
    }

    // Encode the current column heights into a single long integer.
    // Base = H + 1 because each height is in [0, H].
    private long encode(int[] heights, int H) {
        long key = 0;
        int base = H + 1;
        for(int h : heights) {
            key = key * base + h;
        }
        return key;
    }
}
Complexity Analysis (with Memoization)
This version adds a HashMap<Long, Integer> memo to cache results for previously visited states. The state is encoded as a base-(H+1) number from the heights array.
Time Complexity
Let W = min(n, m), H = max(n, m), and N = n * m (total area).
The number of possible height vectors (states) is at most (H+1)^W.
For the maximum allowed input n, m ≤ 13, we have W ≤ 13, H ≤ 13, so (14)^13 ≈ 7.9 × 10^14 in theory.
However, due to the problem’s constraints (heights must be achievable by placing squares in the rectangle), actually reachable states are far fewer. In practice, for 13×13, the number of distinct states is on the order of tens of thousands.
For each state, the work done is:
1.Encode the state (O(W))
2.Check if fully filled (O(W))
3.Find minimum height (O(W))
4.Find leftmost minimum column and the maximum run (O(W))
5.Loop over L up to maxRun ≤ W and for each, clone the array (O(W)) and recurse.
Thus, per state, the cost is O(W²) (since inside the loop, cloning is O(W), and loop has O(W) iterations).
Therefore, the total time complexity is:
\[
O(\text{\#distinct states} \times W^2)
\]
In the worst-case theoretical sense (ignoring problem-specific pruning), it is exponential:
\[
O\left((H+1)^W \cdot W^2\right)
\]
But given W ≤ 13 and the actual number of reachable states is modest, the algorithm runs quickly (typically < 100 ms on LeetCode).
Space Complexity
The space usage consists of:
- Memoization table: stores an entry for each distinct state. Each entry is a (Long, Integer) pair plus overhead.
Space = O({#distinct states}).
- Recursion stack: maximum depth ≤ number of squares placed, which in worst case (all 1×1 squares) is N = n × m ≤ 169.
- Temporary arrays: each recursive call creates a cloned heights array of length W. At any moment, the call stack holds at most depth copies, so O(W × depth).
Thus total space complexity is:
O({#distinct states} + W * N)
Since W ≤ 13 and N ≤ 169, the dominant term is the memoization table size. In practice, \#distinct states is manageable (thousands to tens of thousands), so the space is well within limits (a few MB).
Summary Table
MetricComplexityTimeO( (H+1)^W × W² ) worst‑case theoretical, but much lower in practiceSpaceO( (#states) + W × N ), where #states is number of visited states
For the constraints n, m ≤ 13, the solution is efficient and passes all test cases.

Refer to Deepseek
import java.util.*;

class Solution {
    public int tilingRectangle(int n, int m) {
        // If the rectangle is a square, a single square suffices
        if (n == m) return 1;
        
        // Make the height the larger dimension, width the smaller one
        int H = Math.max(n, m);
        int W = Math.min(n, m);
        
        // Initial heights: all columns start at 0
        int[] heights = new int[W];
        // Memoization: encoded state -> minimum squares needed
        Map<Long, Integer> memo = new HashMap<>();
        
        return dfs(heights, H, W, memo);
    }
    
    private int dfs(int[] heights, int H, int W, Map<Long, Integer> memo) {
        long key = encode(heights, H);
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        
        // Check if the rectangle is completely filled
        boolean full = true;
        for (int h : heights) {
            if (h != H) {
                full = false;
                break;
            }
        }
        if (full) return 0;
        
        // Find the leftmost column with minimum height
        int minH = H + 1;
        for (int h : heights) {
            if (h < minH) minH = h;
        }
        int i = 0;
        while (heights[i] != minH) i++; // leftmost column with height minH
        
        // Determine how many consecutive columns have height minH starting at i
        int maxRun = 0;
        while (i + maxRun < W && heights[i + maxRun] == minH) {
            maxRun++;
        }
        
        int best = Integer.MAX_VALUE;
        // Try every possible square size L that fits in the current run and height
        for (int L = 1; L <= maxRun && minH + L <= H; L++) {
            // Create new heights after placing an L x L square
            int[] newHeights = heights.clone();
            for (int j = i; j < i + L; j++) {
                newHeights[j] = minH + L;
            }
            int res = dfs(newHeights, H, W, memo);
            if (res != Integer.MAX_VALUE) {
                best = Math.min(best, res + 1);
            }
        }
        
        memo.put(key, best);
        return best;
    }
    
    // Encode the current column heights into a single long integer.
    // Base = H + 1 because each height is in [0, H].
    private long encode(int[] heights, int H) {
        long key = 0;
        int base = H + 1;
        for (int h : heights) {
            key = key * base + h;
        }
        return key;
    }
}
问题理解
我们要用若干个大小相等或不相等的正方形（边长均为整数）铺满一个 n × m 的矩形（长和宽都是整数），要求使用的正方形数量最少。
例如：
- n=2, m=3，最少用 3 个正方形（1×1 + 1×1 + 1×1 或 2×2 + 1×1）。
- n=11, m=13 这种则需要更巧妙的剪枝。
该题与“铺满地板”或“正方形分割”问题类似，但这里可以用不同大小的正方形，目标是总数最少。
核心思路 —— 自底向上的贪心 + 深度优先搜索 + 记忆化
- 将矩形看作 m 列，每列有一个当前已填充的高度（从底部向上）。
- 初始状态所有列高度为 0。
- 每次操作，在矩形中找一个尚未完全填满的区域，放置一个尽可能大的正方形，但需要保证不超出边界，并且要尽可能减少总数量。
- 因为最终要求最少，不能用纯粹的贪心（比如总是放最大可能的正方形），而需要深度优先搜索（DFS）所有可能的放置顺序，并利用记忆化避免重复计算。
状态表示
对于当前已填充的状态，我们用一个整数数组 heights[] 表示每一列已填充的高度，长度 = 矩形的宽度 W（W = min(n, m)，高度 H = max(n, m)）。
这样矩形是 H × W（高度 H ≥ 宽度 W），便于编码。
例如：如果矩形是 5 × 3，我们让 H = 5, W = 3，heights[0], heights[1], heights[2] 分别表示三列已经填到的高度（从底部开始）。
- 当某一列的高度 == H 时，这一列已填满。
- 当所有列高度 == H 时，矩形完全填满。
状态需要被唯一标识，以便记忆化。我们使用整数进制编码：
把 heights 数组看作一个 (H+1) 进制数，得到一个 long 类型的 key。
private long encode(int[] heights, int H) {
    long key = 0;
    int base = H + 1;
    for (int h : heights) {
        key = key * base + h;
    }
    return key;
}

这样，每个状态对应唯一 key，可以用 Map<Long, Integer> 记录从该状态到填满矩形还需要的最少正方形数量。
--------------------------------------------------------------------------------
算法步骤（DFS）
1.进入递归 dfs(heights, H, W, memo)：
- 如果当前状态已经填满（所有列高度 == H），返回 0。
- 如果该状态在 memo 中，直接返回。
2.找到最左边的“最低列”
- 检查所有列的最小高度 minH。
- 找到第一列（索引最小）其高度等于 minH，记作 i。
- 从 i 开始向右数，有多少列连续高度都是 minH，得到最长连续段长度 maxRun。
为什么这样找？
因为我们要在某个最低的“凹坑”里放正方形，而且这个凹坑必须连续才能放得下。从最左边的最低列开始放正方形，不会丢失最优解（可以证明，存在一种最优解，每次在最低列的最左位置放置正方形）。
3.尝试放置不同边长的正方形
- 可选的边长 L 从 1 到 maxRun，并且还要满足 minH + L <= H（不超出矩形顶部）。
- 对于每个可行的 L，将 i 到 i+L-1 这几列的高度全部增加 L（相当于放置了一个 L×L 的正方形）。
- 对新状态递归调用 dfs，得到的返回值 +1 就是本次放置后的总数。
- 取所有可能 L 中的最小结果。
4.记忆化并返回
--------------------------------------------------------------------------------
代码中细节解释
为什么要将 n,m 转成 H,W（H≥W）？
- 把高度设为大值，宽度设为小值，可以压缩状态空间。
- 因为矩形可以旋转，答案对称，所以我们可以约定 H = max(n, m), W = min(n, m) 来只处理一种方向。
为什么选择最左边的最低列？
- 因为所有状态是等价的，如果我们不限定最左边，可能产生对称状态，导致重复搜索。
- 用最左边的最低列作为放置起始点，能确保状态编码唯一，并且不会错过最优解。
放置正方形为什么只能从 i 开始连续放 L 列？
- 我们必须在一个完整的连续低洼区域放下正方体，否则会出现“悬空”问题（铺瓷砖不能重叠，必须填满底部）。
- 所以 L 不能超过 maxRun。
记忆化的必要性
- 状态数最多是 (H+1)^W，当 H=13, W=13 时大约是 14^13，但实际剪枝后有效状态很少。
- 使用 HashMap 存储中间结果，防止重复计算相同的状态。
--------------------------------------------------------------------------------
示例手动模拟
取 n = 2, m = 3，则 H=3, W=2（旋转后为 3×2）。
初始 heights = [0,0]，最小高度 minH = 0，最左边最低列 i=0，maxRun = 2。
尝试 L=1：放置 1×1 在 (0,0) 位置 → newHeights = [1,0]，继续递归。
尝试 L=2：放置 2×2 在 (0,0) 位置 → 需要高度 0+2=2 ≤ H=3，且连续两列可放下 → newHeights = [2,2]，剩下一列高度 2，还需要填满到 3。继续递归。
递归最终返回最小值，由于 memo 存在，很快算出答案是 3。
--------------------------------------------------------------------------------
复杂度分析
- 理论上状态数为 O((H+1)^W)，但经过剪枝（只在坑里放正方形）通常远小于这个值。
- 实际能通过的测试范围是 1 ≤ n, m ≤ 13。
--------------------------------------------------------------------------------
完整代码（已提供）
代码中有 dfs 递归函数、encode 哈希编码、主函数入口 tilingRectangle。
核心是每次找到最左边的最低列，尝试所有可能的正方形大小，递归求解。
--------------------------------------------------------------------------------
总结
该解法是经典的基于柱状图状态的 DFS + 记忆化方法，本质上是将二维铺砖问题转化为一维“高度数组”的状态搜索，通过固定放置规则（最左边最低列）减少对称状态，并利用进制编码实现状态 key，最终得到最优解。

追问：Is this a Greedy solution or Backtracking solution ?
You're absolutely right — the solution you just shared (with memoization) is essentially a backtracking approach, not a greedy one.
Let me clarify the distinction:
- Greedy would mean always picking the largest possible square at each step without reconsidering. That's not what happens here.
- Backtracking tries different square sizes (L = 1..maxRun) and explores each possibility recursively, then picks the best overall result. The memoization caches states to avoid re‑exploring identical subproblems.
The only "non‑standard" part is the rule "always place the next square at the leftmost column with the minimum current height".
This is not a greedy choice (it doesn’t decide the square size – that’s still tried exhaustively).
Instead, it's a symmetry‑breaking / canonical ordering rule:
- Any placement of a square can be shifted left until it touches the lowest column. This ensures every state is reached in only one way, drastically reducing the search space.
- Without this rule, the same arrangement of squares could be represented by many different sequences (e.g., placing the same square one column to the right). The algorithm would then compute the same substate repeatedly.
So yes, the core is backtracking + memoization; the "leftmost lowest" rule is just a heuristic to make backtracking feasible.
--------------------------------------------------------------------------------
Other backtracking variants (without that rule)
Theoretically, you could backtrack over any possible placement of a square (all possible top‑left corners and sizes).
However, the branching factor would be huge:
For an H × W rectangle with some holes, there could be O(H*W*min(H,W)) placements, and the number of distinct states would explode.
That version would be far too slow for n,m ≤ 13.
Some people implement a pure backtracking that always fills the top‑leftmost empty cell (instead of the lowest column). That works similarly to the “leftmost minimum” approach but from a different orientation. Both are valid backtracking strategies.
--------------------------------------------------------------------------------
Other known solutions for this problem
- DP on column heights – exactly what the memoized solution does. It's the most common accepted approach.
- DP on the shape of the remaining region using a bitmap of the grid (not practical for 13×13 → too many states).
- Integer linear programming or BFS on state space – also possible but overkill.
--------------------------------------------------------------------------------
Takeaway
- The solution you posted is a backtracking DFS with memoization.
- The “greedy” label in the original question was a slight misnomer – the fixed placement order is just a clever pruning rule, not a greedy decision.
- Without that canonical ordering, a naive backtracking would be exponential in the worst case and time out.
中文翻译如下：
当然，下面是上面英文回答的详细中文翻译：
--------------------------------------------------------------------------------
你说得完全正确——你刚分享的那个解法（带记忆化搜索）本质上是一种**回溯（backtracking）**方法，而不是贪心。
让我澄清一下两者的区别：
- 贪心（Greedy）：每一步都选择当前看起来最优的（比如总是放最大的正方形），并且绝不回头重新考虑。这个解法并不是贪心。
- 回溯（Backtracking）：它会尝试所有可能的正方形大小（L = 1..maxRun），对每一种可能性递归探索，然后从所有结果中选出最好的。同时利用记忆化缓存已访问过的状态，避免重复计算。
这个解法里唯一有点“特殊”的规则是：总是把下一个正方形放在“最左边且当前高度最低”的那一列上。
这不是一个贪心选择（因为它并没有直接决定正方形的大小——大小仍然通过循环穷举尝试）。
实际上，这是一个打破对称性 / 规范化顺序的规则：
- 任何正方形的放置都可以向左平移，直到碰到最低的那一列。这样可以确保每个状态只以唯一的方式被访问到，从而大幅减少搜索空间。
- 如果没有这个规则，同一个正方形的布局可能会被不同的放置顺序所表示（比如同一个正方形往右挪一列）。那么算法就会反复计算相同的子状态，效率大大降低。
所以，核心就是回溯 + 记忆化；“最左边最低列”这个规则只是一个启发式策略，让回溯变得可行。
--------------------------------------------------------------------------------
不带这个规则的其他回溯方式
理论上，你可以回溯尝试所有可能的正方形放置位置（所有可能的左上角坐标和边长）。
但是分支因子会非常巨大：
对于一个 H × W 的矩形（带有一些空洞），可能的放置方式大约有 O(H*W*min(H,W)) 种，状态数会爆炸。
那种版本对于 n,m ≤ 13 也会慢得无法接受。
有些人实现的纯回溯是每次填充 最左上角的空格（而不是最低列）。这种思路和“最左边最低列”本质上类似，只是方向不同。两者都是有效的回溯策略。
--------------------------------------------------------------------------------
本题已知的其他解法
- 基于列高度的 DP – 就是你刚才发的带记忆化的解法，也是 LeetCode 上最常见的通过解法。
- 基于剩余区域形状的 DP（例如用位图表示整个矩形）– 对于 13×13 来说状态数太多，不实际。
- 整数线性规划 或 状态空间 BFS – 也可以，但过于复杂。
--------------------------------------------------------------------------------
总结
- 你贴出来的解法就是带记忆化的回溯 DFS。
- 原描述中“贪心 + DFS”的说法其实不太准确——固定的放置顺序只是一个聪明的剪枝规则，而不是贪心决策。
- 如果没有这种规范化顺序，朴素的回溯算法会在最坏情况下指数爆炸，无法在规定时间内运行。



