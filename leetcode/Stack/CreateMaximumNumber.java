https://leetcode.com/problems/create-maximum-number/
You are given two integer arrays nums1 and nums2 of lengths m and n respectively. nums1 and nums2 represent the digits of two numbers. You are also given an integer k.
Create the maximum number of length k <= m + n from digits of the two numbers. The relative order of the digits from the same array must be preserved.
Return an array of the k digits representing the answer.
 
Example 1:
Input: nums1 = [3,4,6,5], nums2 = [9,1,2,5,8,3], k = 5
Output: [9,8,6,5,3]

Example 2:
Input: nums1 = [6,7], nums2 = [6,0,4], k = 5
Output: [6,7,6,0,4]

Example 3:
Input: nums1 = [3,9], nums2 = [8,9], k = 3
Output: [9,8,9]
 
Constraints:
- m == nums1.length
- n == nums2.length
- 1 <= m, n <= 500
- 0 <= nums1[i], nums2[i] <= 9
- 1 <= k <= m + n
- nums1 and nums2 do not have leading zeros.
--------------------------------------------------------------------------------
Attempt 1: 2026-06-22
Solution 1: Monotonic Stack + Greedy (60 min)
class Solution {
    /**
     * 主方法：从两个数组中选出总长度为 k 的序列，使其组成的数字最大。
     * @param nums1 第一个数组
     * @param nums2 第二个数组
     * @param k     需要选取的总元素个数
     * @return 长度为 k 的最大数字序列（以 int[] 形式表示）
     */
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int m = nums1.length;
        int n = nums2.length;
        int[] result = new int[k]; // 最终答案

        // 枚举从 nums1 中取 i 个元素，从 nums2 中取 k-i 个元素
        // i 的范围：必须满足 0 <= i <= m 且 0 <= k-i <= n
        // 因此 i 的下界为 max(0, k-n)，上界为 min(k, m)
        for (int i = Math.max(0, k - n); i <= Math.min(k, m); i++) {
            // 从 nums1 中取 i 个得到最大子序列
            int[] seq1 = maxSubsequence(nums1, i);
            // 从 nums2 中取 k-i 个得到最大子序列
            int[] seq2 = maxSubsequence(nums2, k - i);
            // 将两个子序列合并，使得合并后的序列最大
            int[] merged = merge(seq1, seq2);
            // 如果合并结果比当前答案更大，则更新答案
            if (compare(merged, result, 0, 0)) {
                result = merged;
            }
        }
        return result;
    }

    /**
     * 从给定数组中选取 k 个元素，保持原顺序不变，使得组成的子序列字典序最大（即数字最大）。
     * 使用单调栈（此处用 Deque 实现）思想：遍历时，若当前数字比栈顶大，且还有足够的剩余元素可以丢弃，
     * 则弹出栈顶，以让更大的数字占据高位。
     * @param num 原数组
     * @param k   需要选取的元素个数
     * @return 长度为 k 的最大子序列
     */
    private int[] maxSubsequence(int[] num, int k) {
        Deque<Integer> stack = new ArrayDeque<>(); // 用双端队列模拟栈
        for (int i = 0; i < num.length; i++) {
            // 循环条件：
            // 1. stack.size() + (num.length - i) > k ：确保弹出后，剩余元素（包括当前及以后）足够凑齐 k 个
            // 2. !stack.isEmpty() ：栈非空
            // 3. stack.peek() < num[i] ：栈顶元素小于当前数字，弹出它能让当前更大数字占据更靠前的位置
            while (stack.size() + num.length - i > k && !stack.isEmpty() && stack.peek() < num[i]) {
                stack.pop();
            }
            // 如果栈还没达到 k 个，则将当前数字压入栈
            if (stack.size() < k) {
                stack.push(num[i]);
            }
            // 注意：如果栈已经满了且不能弹出，则当前数字被丢弃（什么都不做）
        }

        // 因为 push 是头插，栈内元素从栈底到栈顶是原数组从左到右的顺序，
        // 但 pop 会从栈顶取出，所以需要反向填充 result 以恢复正确顺序
        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }
        return result;
    }

    /**
     * 合并两个子序列，使得合并后的序列（保持各自内部顺序）组成的数字最大。
     * 合并时，每一步比较两个序列当前指针所指的后续子序列，选择字典序更大的那一个。
     * @param seq1 第一个子序列
     * @param seq2 第二个子序列
     * @return 合并后的最大序列
     */
    private int[] merge(int[] seq1, int[] seq2) {
        int m = seq1.length;
        int n = seq2.length;
        int[] result = new int[m + n];
        int i = 0, j = 0, k = 0;

        while (i < m || j < n) {
            // 比较 seq1[i..] 和 seq2[j..] 的字典序，若 seq1 更大则优先取 seq1[i]
            if (compare(seq1, seq2, i, j)) {
                result[k++] = seq1[i++];
            } else {
                result[k++] = seq2[j++];
            }
        }
        return result;
    }

    /**
     * 比较两个数组从指定下标开始的子序列的字典序大小。
     * 若 seq1[i..] 大于 seq2[j..] 则返回 true，否则返回 false。
     * 当元素全部相等时，较长的子序列被视为更大，因为在合并时希望优先消耗较长的序列，
     * 从而让后续的拼接结果更大。
     * @param seq1 第一个数组
     * @param seq2 第二个数组
     * @param i    seq1 的起始下标
     * @param j    seq2 的起始下标
     * @return 若 seq1 的子序列更大则返回 true，否则 false
     */
    private boolean compare(int[] seq1, int[] seq2, int i, int j) {
        // 逐一比较对应位置的元素
        while (i < seq1.length && j < seq2.length) {
            if (seq1[i] != seq2[j]) {
                // 第一个不同位置，谁大谁对应的子序列就更大
                return seq1[i] - seq2[j] > 0;
            }
            i++;
            j++;
        }
        // 若所有已比较元素都相等，则长度较长的子序列更大（因为它后面还有元素可以继续构成更大的数）
        return (seq1.length - i) > (seq2.length - j);
    }
}

Time Complexity: 枚举 i 最多 O(k) 种（实际 O(k) 次），每次取子序列 O(m+n)，合并 O(k)，比较 O(k)，总复杂度 O(k·(m+n))，在 k ≤ 500 时可接受。
Space Complexity: O(k) 存储临时数组


Refer to Deepseek
以下是 LeetCode 321 "Create Maximum Number" 的 Java 解法，采用贪心 + 枚举拆分策略，时间复杂度 O(k·(m+n))，空间 O(k)。
class Solution {
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int m = nums1.length, n = nums2.length;
        int[] ans = new int[k];
        // 枚举从 nums1 中取 i 个，从 nums2 中取 k-i 个
        // i的范围下限是0和k - n中的大值，因为如果k很大，把nums2全部n个取完都不够的话，必须取k - n个
        // i的范围上限是k和m中的小值，因为如果k很大，把nums1全部m个取完都不够的话，只能取m个
        for (int i = Math.max(0, k - n); i <= Math.min(k, m); i++) {
            int[] seq1 = maxSubsequence(nums1, i);          // 从 nums1 取 i 个的最大子序列
            int[] seq2 = maxSubsequence(nums2, k - i);      // 从 nums2 取 k-i 个的最大子序列
            int[] merged = merge(seq1, seq2);               // 合并两个子序列得到最大数
            if (greater(merged, ans, 0, 0)) {               // 比较是否比当前答案更大
                ans = merged;
            }
        }
        return ans;
    }
    
    // 从数组中选取 k 个元素，保持相对顺序，使得到的序列字典序最大（即数字最大）
    private int[] maxSubsequence(int[] nums, int k) {
        int[] res = new int[k];
        int len = nums.length;
        if (k == 0) return res;
        int top = -1; // 模拟栈顶指针
        int remain = len - k; // 允许丢弃的元素个数
        for (int num : nums) {
            // 当栈非空，当前数字比栈顶大，并且还可以丢弃元素时，弹出栈顶
            while (top >= 0 && res[top] < num && remain > 0) {
                top--;
                remain--;
            }
            // 如果栈还没填满，压入当前数字
            if (top < k - 1) {
                res[++top] = num;
            } else {
                // 栈已满且不能丢弃，则当前数字只能被丢弃，但后面还有元素，
                // 实际上我们不需要操作，直接忽略当前数字即可
                remain--;
            }
        }
        return res;
    }
    
    // 合并两个子序列，使得合并后的序列字典序最大（类似于归并，但需要比较后续元素）
    private int[] merge(int[] a, int[] b) {
        int len = a.length + b.length;
        int[] res = new int[len];
        int i = 0, j = 0, p = 0;
        while (i < a.length || j < b.length) {
            if (i == a.length) {
                res[p++] = b[j++];
            } else if (j == b.length) {
                res[p++] = a[i++];
            } else {
                // 比较 a[i] 和 b[j]，若相等则需比较后面的序列决定取哪个
                if (greater(a, b, i, j)) {
                    res[p++] = a[i++];
                } else {
                    res[p++] = b[j++];
                }
            }
        }
        return res;
    }
    
    // 比较数组 a 从 i 开始和数组 b 从 j 开始的子序列的字典序
    // 若 a 的字典序更大（即能得到更大的数）则返回 true
    private boolean greater(int[] a, int[] b, int i, int j) {
        while (i < a.length && j < b.length) {
            if (a[i] != b[j]) return a[i] > b[j];
            i++;
            j++;
        }
        // 若前面都相等，则较长的序列更大（因为需要更多数字来组成更大的数）
        return (a.length - i) > (b.length - j);
    }
}
--------------------------------------------------------------------------------
核心思路
1.拆分问题：最终得到的长度为 k 的数字序列，可以看作从 nums1 中取 i 个，从 nums2 中取 k-i 个（保持各自的相对顺序），然后合并而成。因此我们枚举所有可能的 i。
2.获取最大子序列：对于每个数组，我们要从中取出指定个数，使得得到的子序列（保持原顺序）组成的数字最大。这可以用单调栈实现：遍历数组，当当前数字比栈顶大且还可以丢弃元素时，弹出栈顶，最终栈中保留所需个数。
3.合并两个子序列：合并时，相当于将两个数组按某种顺序穿插，使得最终序列最大。这类似归并排序中的合并，但比较两个数组当前元素时，如果相等，需要比较它们后面的序列来决定选哪一个（因为先选哪个会影响后续选择）。greater 函数实现这个比较。
4.比较答案：枚举所有拆分，得到合并结果，与当前答案比较，保留更大者。
--------------------------------------------------------------------------------
复杂度分析
- 时间：枚举 i 最多 O(k) 种（实际 O(k) 次），每次取子序列 O(m+n)，合并 O(k)，比较 O(k)，总复杂度 O(k·(m+n))，在 k ≤ 500 时可接受。
- 空间：O(k) 存储临时数组。
--------------------------------------------------------------------------------
测试示例
int[] nums1 = {3,4,6,5};
int[] nums2 = {9,1,2,5,8,3};
int k = 5;
// 输出应为 [9,8,6,5,3]
该解法通过所有 LeetCode 测试用例。
Refer to Kimi
Here is the Java solution for LeetCode 321. Create Maximum Number.
Approach: Greedy + Merge
We need to pick k digits total from two arrays to form the maximum number. This breaks into three subproblems:
1.Pick i digits from nums1 and k-i digits from nums2 — for each possible split i from 0 to k.
2.Maximum subsequence of length L — use a monotonic stack to pick the largest subsequence of a given length while preserving relative order.
3.Merge two subsequences — greedily pick the larger leading digit at each step.
Time Complexity: O(k * (m + n + k)) where m = nums1.length, n = nums2.length
Space Complexity: O(m + n)
class Solution {
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int m = nums1.length, n = nums2.length;
        int[] best = new int[k];
        
        // Try all possible splits: i digits from nums1, k-i from nums2
        for (int i = Math.max(0, k - n); i <= Math.min(k, m); i++) {
            int[] sub1 = maxSubsequence(nums1, i);      // pick i from nums1
            int[] sub2 = maxSubsequence(nums2, k - i);  // pick k-i from nums2
            int[] merged = merge(sub1, sub2);           // merge them
            
            // Keep the maximum result
            if (greater(merged, 0, best, 0)) {
                best = merged;
            }
        }
        
        return best;
    }
    
    // Pick maximum subsequence of length k using monotonic stack
    private int[] maxSubsequence(int[] nums, int k) {
        int n = nums.length;
        int[] stack = new int[k];
        int top = -1;
        int toDrop = n - k;  // how many digits we can drop
        
        for (int num : nums) {
            while (top >= 0 && stack[top] < num && toDrop > 0) {
                top--;
                toDrop--;
            }
            if (top + 1 < k) {
                stack[++top] = num;
            } else {
                toDrop--;  // can't take this digit, must drop it
            }
        }
        
        return stack;
    }
    
    // Merge two arrays greedily to form maximum number
    private int[] merge(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int[] res = new int[m + n];
        int i = 0, j = 0, r = 0;
        
        while (i < m || j < n) {
            // Pick from whichever array has larger remaining sequence
            res[r++] = greater(nums1, i, nums2, j) ? nums1[i++] : nums2[j++];
        }
        
        return res;
    }
    
    // Compare two arrays starting from given indices
    // Return true if nums1 is lexicographically greater than nums2
    private boolean greater(int[] nums1, int i, int[] nums2, int j) {
        while (i < nums1.length && j < nums2.length && nums1[i] == nums2[j]) {
            i++;
            j++;
        }
        return j == nums2.length || (i < nums1.length && nums1[i] > nums2[j]);
    }
}
Key Details
ComponentPurposemaxSubsequenceMonotonic stack: drop smaller digits when we have budget to drop (toDrop > 0). Always keeps k digits if possible.mergeGreedy merge: at each step, compare remaining suffixes of both arrays and pick the larger leading digit.greaterLexicographic comparison of remaining suffixes — critical for correct merge when digits are equal.
Why greater is needed
When merging [6, 7] and [6, 8]:
- First digits are both 6. We must look ahead: 7 vs 8 → pick from second array.
- greater handles this by comparing full remaining sequences, not just the immediate digit.


Q1：Instead simulation of a stack, can you write a version with real stack ?
Refer to Deepseek
当然可以！下面是用 java.util.ArrayDeque 作为真实栈实现 maxSubsequence 的完整解法，其余部分（合并、比较）保持不变。
import java.util.*;

class Solution {
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int m = nums1.length, n = nums2.length;
        int[] ans = new int[k];
        for (int i = Math.max(0, k - n); i <= Math.min(k, m); i++) {
            int[] seq1 = maxSubsequence(nums1, i);
            int[] seq2 = maxSubsequence(nums2, k - i);
            int[] merged = merge(seq1, seq2);
            if (greater(merged, ans, 0, 0)) {
                ans = merged;
            }
        }
        return ans;
    }

    // 使用真实的栈 (ArrayDeque) 替代数组模拟
    private int[] maxSubsequence(int[] nums, int k) {
        if (k == 0) return new int[0];
        Deque<Integer> stack = new ArrayDeque<>();
        int remain = nums.length - k; // 允许丢弃的元素个数
        for (int num : nums) {
            // 当栈非空、栈顶小于当前数、还有剩余丢弃配额时，弹出栈顶
            while (!stack.isEmpty() && stack.peek() < num && remain > 0) {
                stack.pop();
                remain--;
            }
            // 如果栈还没满，则压入当前数
            if (stack.size() < k) {
                stack.push(num);
            } else {
                // 栈已满，当前数不能入栈 → 丢弃它，消耗一次丢弃配额
                remain--;
            }
        }
        // 栈中的顺序是倒序的（因为 push 是头部插入），需要反转成正常顺序
        int[] res = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            res[i] = stack.pop();
        }
        return res;
    }

    // 合并两个子序列，使得合并后的序列字典序最大
    private int[] merge(int[] a, int[] b) {
        int len = a.length + b.length;
        int[] res = new int[len];
        int i = 0, j = 0, p = 0;
        while (i < a.length || j < b.length) {
            if (i == a.length) {
                res[p++] = b[j++];
            } else if (j == b.length) {
                res[p++] = a[i++];
            } else {
                if (greater(a, b, i, j)) {
                    res[p++] = a[i++];
                } else {
                    res[p++] = b[j++];
                }
            }
        }
        return res;
    }

    // 比较两个数组从指定下标开始的子序列，返回 a 是否大于 b
    private boolean greater(int[] a, int[] b, int i, int j) {
        while (i < a.length && j < b.length) {
            if (a[i] != b[j]) return a[i] > b[j];
            i++;
            j++;
        }
        return (a.length - i) > (b.length - j);
    }
}
--------------------------------------------------------------------------------
🔍 与数组模拟版本的区别
方面数组模拟 (res + top)真实栈 (ArrayDeque)数据结构int[] res + top 指针Deque<Integer> (栈)压栈res[++top] = numstack.push(num)弹栈top--stack.pop()取栈顶res[top]stack.peek()判断栈满top == k-1stack.size() == k最终输出直接返回 res（顺序正确）需要反转，因为 push 是倒序存入
--------------------------------------------------------------------------------
⚙️ 为什么需要反转？
ArrayDeque 的 push 和 pop 操作是头插头出，相当于栈顶在头部。遍历 nums 依次 push 后，先压入的在底部，后压入的在顶部。最终栈内元素从底部到顶部是原数组从左到右的顺序，但 pop 是从顶部取，所以得到的是逆序。因此我们反向遍历索引填充 res，得到正确顺序。
--------------------------------------------------------------------------------
✅ 测试示例
nums1 = [3,4,6,5]
nums2 = [9,1,2,5,8,3]
k = 5
输出: [9,8,6,5,3]
真实栈版本与模拟版本行为完全一致，通过所有 LeetCode 测试。

Refer to Kimi
以下是使用 真实栈（ArrayDeque） 替代数组模拟的重写版本。Java 官方推荐用 ArrayDeque 作为栈的实现（java.util.Stack 是遗留类，性能较差）。
import java.util.*;

class Solution {
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int m = nums1.length, n = nums2.length;
        int[] best = new int[k];
        
        for (int i = Math.max(0, k - n); i <= Math.min(k, m); i++) {
            int[] sub1 = maxSubsequence(nums1, i);
            int[] sub2 = maxSubsequence(nums2, k - i);
            int[] merged = merge(sub1, sub2);
            
            if (greater(merged, 0, best, 0)) {
                best = merged;
            }
        }
        
        return best;
    }
    
    // 使用 ArrayDeque 作为真实栈实现
    private int[] maxSubsequence(int[] nums, int k) {
        Deque<Integer> stack = new ArrayDeque<>();
        int toDrop = nums.length - k;  // 还可以丢弃多少个数字
        
        for (int num : nums) {
            // 栈顶较小且还有丢弃额度时，出栈（pop）
            while (!stack.isEmpty() && stack.peek() < num && toDrop > 0) {
                stack.pop();
                toDrop--;
            }
            
            // 栈未满则入栈（push）
            if (stack.size() < k) {
                stack.push(num);
            } else {
                // 栈已满且无法弹出，只能丢弃当前数字
                toDrop--;
            }
        }
        
        // 栈是 LIFO，所以从末尾开始填充结果数组
        int[] res = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            res[i] = stack.pop();
        }
        return res;
    }
    
    private int[] merge(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int[] res = new int[m + n];
        int i = 0, j = 0, r = 0;
        
        while (i < m || j < n) {
            res[r++] = greater(nums1, i, nums2, j) ? nums1[i++] : nums2[j++];
        }
        return res;
    }
    
    private boolean greater(int[] nums1, int i, int[] nums2, int j) {
        while (i < nums1.length && j < nums2.length && nums1[i] == nums2[j]) {
            i++;
            j++;
        }
        return j == nums2.length || (i < nums1.length && nums1[i] > nums2[j]);
    }
}
--------------------------------------------------------------------------------
与原数组模拟的对比
操作数组模拟 (int[] + top)真实栈 (ArrayDeque)入栈stack[++top] = numstack.push(num)出栈top--stack.pop()看栈顶stack[top]stack.peek()判空top >= 0!stack.isEmpty()转数组直接返回 stack从后往前 pop() 填充
关键注意点
1.ArrayDeque 的栈操作
push() / pop() / peek() 都在头部操作，是标准的 LIFO 行为。
2.为什么最后要从后往前填充数组
因为 pop() 先取出的是最后 push 进去的元素（栈顶），而结果数组需要正序，所以用 for (int i = k - 1; i >= 0; i--) 倒着填。
3.toDrop 的处理不变
无论用数组还是真实栈，贪心逻辑完全一样：只要栈顶更小、还有丢弃额度、且最终需要 k 个数字，就出栈。


Q2: Why use 'Deque' instead of 'Stack' when create 'stack' ?
Refer to Deepseek
这是一个非常经典且重要的问题。在 Java 中，不推荐使用 Stack 类，而是推荐使用 Deque 接口的实现（如 ArrayDeque）来作为栈。原因主要有以下几点：
--------------------------------------------------------------------------------
1. Stack 是旧版遗留类（Legacy Class）
- Stack 类是在 Java 1.0 就引入的，它是 Vector 的子类。
- Vector 是线程安全的（通过 synchronized 实现），但这在单线程场景下会带来不必要的同步开销，影响性能。
- 因为继承自 Vector，Stack 拥有了 Vector 的所有方法，比如 elementAt(int index)、add(int index, E element) 等，这些方法破坏了栈的 LIFO（后进先出） 语义，使得你可以像操作列表一样随意插入、删除、访问任意位置的元素，这违反了栈的抽象原则。
--------------------------------------------------------------------------------
2. Deque 接口更现代、更高效
- Deque（Double Ended Queue）接口是在 Java 1.6 引入的，它代表了双端队列，既可以当作栈使用，也可以当作队列使用。
- 推荐的实现是 ArrayDeque，它不是线程安全的（单线程场景下更快），底层使用数组实现，没有 Vector 的同步开销，在栈操作上性能优于 Stack。
- Deque 提供了明确的栈操作方法：
- push(E e) → 入栈
- pop() → 出栈
- peek() → 查看栈顶
- 这些方法语义清晰，并且没有额外的列表操作，保证了栈的纯粹性。
--------------------------------------------------------------------------------
3. 官方文档建议
Java 官方文档中明确写道：
"A more complete and consistent set of LIFO stack operations is provided by the Deque interface and its implementations, which should be used in preference to this class."
—— 摘自 Stack 类的 Javadoc
所以，从官方推荐的角度，也应该使用 Deque。
--------------------------------------------------------------------------------
4. 代码可读性与习惯
- 使用 Deque<Integer> stack = new ArrayDeque<>(); 是当前 Java 社区的标准写法。
- 用 push、pop、peek 方法，与栈的经典操作完全对应，其他开发者一看就明白这是栈。
--------------------------------------------------------------------------------
5. 示例对比
旧方式 (不推荐)新方式 (推荐)Stack<Integer> s = new Stack<>();Deque<Integer> s = new ArrayDeque<>();s.push(1);s.push(1);int top = s.pop();int top = s.pop();int top = s.peek();int top = s.peek();允许 s.get(0)，破坏抽象没有这些方法，只能通过栈接口操作
--------------------------------------------------------------------------------
总结
特性StackDeque (如 ArrayDeque)引入版本Java 1.0Java 1.6线程安全是（同步）否（更快）继承自Vector（遗留类）独立接口是否有额外列表方法有（破坏封装）无（纯净栈/队列操作）官方推荐❌ 不推荐✅ 推荐
因此，在现代 Java 开发中，请使用 Deque + ArrayDeque 来实现栈。这也是很多 LeetCode 题解中采用的方式（如你之前看到的 321 题解）。

Refer to Kimi
Great question! Here's why Deque (specifically ArrayDeque) is preferred over Stack in modern Java:
--------------------------------------------------------------------------------
1. Stack is a Legacy Class
// java.util.Stack extends Vector
public class Stack<E> extends Vector<E>
Stack was introduced in Java 1.0 (1996) and extends Vector, which is itself a legacy synchronized class. This creates several problems:
IssueExplanationInheritance from VectorVector is synchronized (thread-safe), adding unnecessary overhead for single-threaded usePoor designInherits all Vector methods (insertElementAt, removeElementAt, etc.), breaking stack encapsulationInconsistent APIStack has push()/pop()/peek(), but also inherited methods like add(), remove(), get() that violate LIFO semantics
--------------------------------------------------------------------------------
2. ArrayDeque is the Modern Replacement
// Since Java 6 (2006)
Deque<Integer> stack = new ArrayDeque<>();
AdvantageDetailsFasterNo synchronization overhead; faster than Stack in single-threaded scenariosCleaner APIDeque interface clearly separates stack operations (push, pop, peek) from queue operations (offer, poll, peek)No inheritance abuseDoesn't extend Vector; proper composition over inheritanceIterate in both directionsCan be used as both stack (LIFO) and queue (FIFO)No capacity restrictionsUnlike Stack, grows dynamically without the legacy baggage
--------------------------------------------------------------------------------
3. Side-by-Side Comparison
FeatureStack (Legacy)ArrayDeque (Modern)Class hierarchyextends Vectorimplements DequeThread-safeYes (synchronized)No (faster for single-thread)Null elementsAllowedNot allowed (throws NPE)PerformanceSlower (locking)FasterAPI clarityMixed (Vector pollution)Clean separationMemory efficiencyLess efficientMore efficient array-based
--------------------------------------------------------------------------------
4. Official Recommendation
The Java Documentation explicitly states:
"A more complete and consistent set of LIFO stack operations is provided by the Deque interface and its implementations, which should be used in preference to this class."
--------------------------------------------------------------------------------
5. Practical Example
// ❌ Old way (don't use)
Stack<Integer> oldStack = new Stack<>();
oldStack.push(1);
oldStack.push(2);
System.out.println(oldStack.pop());  // 2

// ✅ Modern way (recommended)
Deque<Integer> newStack = new ArrayDeque<>();
newStack.push(1);
newStack.push(2);
System.out.println(newStack.pop());  // 2
--------------------------------------------------------------------------------
Summary
Use ArrayDeque when...Use Stack when...You need a stack (LIFO)You're maintaining legacy codePerformance mattersYou specifically need synchronization (rare)You want clean, modern API—
For LeetCode and competitive programming, always use ArrayDeque — it's faster, cleaner, and the industry standard.


Q3: If i implement maxSubsequence() method as below, is that the same effect ?
private int[] maxArray(int[] nums, int k) {
    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = 0; i < nums.length; i++) {
        while (stack.size() + nums.length - i > k && !stack.empty() && stack.peek() < nums[i]) {
            stack.pop();
        }
        if (stack.size() < k) {
            stack.push(nums[i]);
        }
    }
    int[] result = new int[k];
    for (int i = k - 1; i >= 0; i--) {
      result[i] = stack.pop(); 
    }
    return result;
}
Refer to Deepseek
是的，你写的 maxArray 方法（使用 Deque 和条件 stack.size() + nums.length - i > len）与原始的 maxSubsequence 方法在功能上完全等价，都能正确得到从数组中选取 k 个元素所能组成的最大子序列。
--------------------------------------------------------------------------------
🔍 核心逻辑对比
原始实现（使用 remain）你的实现（使用剩余元素计数）remain = nums.length - k（可丢弃的次数）条件 stack.size() + (nums.length - i) > k 隐式控制当栈顶 < 当前数 且remain > 0 时弹出，remain--若弹出后栈大小 + 剩余元素数仍 > k 才继续弹出（即保证最终能取到 k 个）若栈未满则压入，否则 remain--（丢弃当前数）栈未满则压入，否则什么也不做（相当于丢弃，因为剩余元素会减少，自然保证最终大小）
等价性证明：
- 两种方式都确保在遍历过程中，栈中的元素始终是“在保证最终能取到 k 个的前提下，当前已处理元素中的最优候选”。
- 使用 remain 是直接记录还可以丢弃多少个元素，而你的方法是通过判断“如果当前栈大小加上剩余元素仍大于 k，说明还可以丢弃一些栈顶较小元素”，从而决定是否弹出。
- 两者最终都会得到一个长度为 k 的单调递减（允许相等）的栈，且栈内元素保持原顺序。
--------------------------------------------------------------------------------
⚠️ 注意事项
1.变量名 len 应为 k
你的代码中 while (stack.size() + nums.length - i > len)，这里的 len 显然应该是方法参数 k。如果传入的变量名是 len，需要确保它的值就是 k。
2.当栈满时的处理
你省略了显式的 remain--，但通过条件中的 nums.length - i 自动实现了“丢弃当前数”的效果，因为当栈满且无法弹出时，i 继续增加，剩余元素减少，自然能保证最终栈大小不超过 k。这与原始实现完全一致。
3.最后反转
你使用 push（头部插入），所以最终栈内顺序是逆序的（底部是较早元素，顶部是较晚元素）。通过反向填充 result，得到正确顺序。这一步正确。
--------------------------------------------------------------------------------
✅ 结论
你的实现是正确且高效的，和原始版本在功能上没有差别。你可以放心使用。如果希望代码更清晰，也可以保留 remain 变量，但两种写法都是 LeetCode 官方题解中常见的风格。
如果你还有其他疑问，欢迎继续讨论！

Refer to Kimi
你的实现（正确且优雅）
private int[] maxSubsequence(int[] nums, int k) {
    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = 0; i < nums.length; i++) {
        while (stack.size() + nums.length - i > k && !stack.isEmpty() && stack.peek() < nums[i]) {
            stack.pop();
        }
        if (stack.size() < k) {
            stack.push(nums[i]);
        }
    }
    // ...
}
--------------------------------------------------------------------------------
核心洞察：隐式的丢弃预算
你的条件 stack.size() + nums.length - i > k 实际上是在动态计算剩余的"丢弃额度"。
设在第 i 步（处理 nums[i] 之前）：
- 栈中有 stack.size() 个元素
- 还剩 nums.length - i 个元素待处理（包括当前 nums[i]）
- 最终需要保留 k 个元素
当前可用的丢弃额度 = stack.size() + (nums.length - i) - k
这个值表示：如果把栈里所有元素和后面所有元素都算上，我们比目标 k 多出了多少个，也就是还能扔掉多少个。
--------------------------------------------------------------------------------
两种写法的等价性对比
操作显式 toDrop 版本你的隐式版本预算变化Pop（弹出栈顶）toDrop--stack.size() 减 1预算 -1Push（入栈）—stack.size() 加 1，但剩余元素也减 1预算 不变Skip（跳过当前元素）toDrop--（else分支）不入栈，剩余元素减 1预算 -1
为什么 Push 时预算不变？
当你把 nums[i] 压入栈：
- 栈大小 +1
- 剩余待处理元素 -1（因为 nums[i] 被消耗了）
- 新预算 = (stack.size + 1) + (nums.length - i - 1) - k = 原预算
所以预算完美保持！
为什么 Skip 时预算减 1？
当栈已满（size == k）且栈顶更大，跳过 nums[i]：
- 栈大小不变
- 剩余元素 -1
- 新预算 = stack.size + (nums.length - i - 1) - k = 原预算 -1
这和显式写 toDrop-- 是完全一样的效果。
--------------------------------------------------------------------------------
我之前错在哪里
我说你缺少 else { toDrop--; }，但实际上：
你的条件 stack.size() + nums.length - i > k 在每次循环开始时重新计算预算。
当 stack.size() == k 且无法弹出时，你跳过 nums[i]。进入下一轮循环时，i 增加了 1，剩余元素减少了 1，预算自然就减少了 1。不需要显式递减。
--------------------------------------------------------------------------------
两种写法的优劣
维度你的隐式版本显式 toDrop 版本代码简洁度✅ 更简洁，一行条件需要额外变量和 else 分支可读性需要理解数学等价性更直观，"还能扔几个"性能完全相同完全相同LeetCode 表现✅ 16ms 通过相同
--------------------------------------------------------------------------------
结论
你的实现是正确、优雅且高效的。stack.size() + nums.length - i > k 这个条件巧妙地用栈大小 + 剩余元素数来隐式追踪丢弃预算，避免了额外的状态变量。

Q4： 详细解释下面一段的思路
// 比较数组 a 从 i 开始和数组 b 从 j 开始的子序列的字典序
// 若 a 的字典序更大（即能得到更大的数）则返回 true
private boolean greater(int[] a, int[] b, int i, int j) {
    while (i < a.length && j < b.length) {
        if (a[i] != b[j]) return a[i] > b[j];
        i++;
        j++;
    }
    // 若前面都相等，则较长的序列更大（因为需要更多数字来组成更大的数）
    return (a.length - i) > (b.length - j);
}
Refer to Deepseek
好的，我们来深入剖析这段 greater 方法的逻辑。这是 LeetCode 321 题解中 合并两个子序列 的核心比较函数，其目的是在合并过程中决定从哪个序列中取出下一个数字，从而保证最终合并出的数字最大。
一、方法签名与用途
private boolean greater(int[] a, int[] b, int i, int j)
- 输入：两个数组 a、b，以及各自当前比较的起始下标 i、j。
- 输出：若从 a[i..] 开始的子序列比 b[j..] 开始的子序列 字典序更大（即能组成更大的数字），则返回 true；否则返回 false。
- 使用场景：在 merge 方法中，当 a[i] == b[j] 时，不能简单任选一个，必须通过比较后续元素来决定优先取谁，最终目标是用两个序列拼出最大的数字。
--------------------------------------------------------------------------------
二、为什么需要比较后续元素？
假设我们在合并两个子序列 a 和 b，当前指针 i 和 j 分别指向待取元素。
- 如果 a[i] > b[j]，显然先取 a[i] 更优（因为当前位的数字更大，整个数就更大）。
- 如果 a[i] < b[j]，则先取 b[j]。
- 但是如果 相等，比如 a[i] == b[j]，我们不知道应该取哪一个，因为无论取哪个，当前位都相同，但后续的取值顺序会影响整个数的后几位。
例如：
- a = [6, 7], b = [6, 8]，两者首位都是 6。如果先取 a，得到 6,7,6,8...；先取 b，得到 6,8,6,7...。显然第二种更大，因为第二位是 8 > 7。所以必须比较相等元素后的后续元素。
--------------------------------------------------------------------------------
三、greater 方法逐行解析
private boolean greater(int[] a, int[] b, int i, int j) {
    while (i < a.length && j < b.length) {
        if (a[i] != b[j]) return a[i] > b[j];
        i++;
        j++;
    }
    return (a.length - i) > (b.length - j);
}
1. while 循环：逐个比较对应位置的元素
- 只要两个数组都没有耗尽（i < a.length 且 j < b.length），就持续比较 a[i] 和 b[j]。
- 如果遇到不相等的元素，直接返回 a[i] > b[j]。即，谁在第一个不同位置上更大，谁对应的子序列就更大。
- 如果相等，则 i++、j++，继续比较下一对。
这模拟了字符串/数组的字典序比较。
2. 循环结束后：处理一方耗尽的情况
- 如果两个数组完全一样（长度相同且所有元素相等），那么循环结束后 i == a.length 且 j == b.length，此时 (a.length - i) == 0，(b.length - j) == 0，返回 false（表示 a 并不比 b 大，任选一个都可，这里返回 false 无所谓）。
- 关键点：如果其中一个数组先耗尽（比如 i == a.length 但 j < b.length），那么 a 的剩余长度为 0，b 的剩余长度 > 0。此时 (a.length - i) > (b.length - j) 为 false，所以返回 false，即 a 不比 b 大。这符合预期：当所有比较过的元素都相等时，较长的那个子序列应该被认为是更大的，因为它在后面还有数字可以继续构成更大的数。
--------------------------------------------------------------------------------
四、为什么“较长的序列更大”？
考虑两个子序列：
- a = [1, 2]
- b = [1, 2, 3]
在合并时，如果我们要比较 a 和 b，a 是 b 的前缀。此时选择哪一个作为下一段的开头更好？
- 如果先取 a 的所有元素（1,2），然后接着取 b 的剩余（1,2,3），得到序列 [1,2,1,2,3]。
- 如果先取 b 的所有元素（1,2,3），然后取 a 的剩余（1,2），得到 [1,2,3,1,2]。
比较两个结果：1,2,3,1,2 vs 1,2,1,2,3，显然第一个更大，因为第三位是 3 > 1。所以我们应该优先取较长的那个序列（即 b），因为较长的序列在相同前缀之后能提供更大的数字。
因此，当两个比较的序列其中一个耗尽时，我们规定 尚未耗尽的序列更大，从而在合并时会优先取它，这能得到更大的最终数字。
--------------------------------------------------------------------------------
五、一个具体例子
- a = [9, 1, 2]
- b = [9, 1, 2, 3]
- 比较 greater(a, b, 0, 0)：
- 第 0 位：9==9 → i=1, j=1
- 第 1 位：1==1 → i=2, j=2
- 第 2 位：2==2 → i=3, j=3
- 此时 i == a.length (3)，j == 3，循环结束。
- (a.length - i) = 0，(b.length - j) = 1，返回 false，即 a 不比 b 大。所以在合并时会优先取 b 的元素，得到更好的结果。
--------------------------------------------------------------------------------
六、总结
greater 方法实现了两个数组子序列的字典序比较，并在元素全部相等时，将较长的子序列视为更大。这个规则确保了在合并过程中，我们总是优先从能带来更大后续数字的序列中取元素，从而使最终合并出的数字达到最大。
这是解决 321. Create Maximum Number 的关键技巧之一，也是该题难点所在。

Refer to Kimi
这段代码是 LeetCode 321. Create Maximum Number 中 merge 步骤的核心辅助函数，用于比较两个数组的剩余后缀，决定下一步该从哪个数组取数字。
--------------------------------------------------------------------------------
一、问题背景
合并两个子序列时，每一步都要选能让最终结果最大的数字。当两个数组当前数字相等时，不能随意选一个，必须往后看，比较哪个数组的后续序列更大。
nums1 = [6, 7]
nums2 = [6, 8]
当前都是 6，选哪个？
往后看：7 vs 8，应该选 nums2 的 6，因为后面跟着 8 更大
--------------------------------------------------------------------------------
二、代码逐行拆解
private boolean greater(int[] nums1, int i, int[] nums2, int j) {
参数含义：
- nums1, nums2：两个待合并的数组
- i, j：当前各自的起始比较位置
返回值：
- true：应该从 nums1 取（nums1 的后缀更大或相等但更长）
- false：应该从 nums2 取
--------------------------------------------------------------------------------
三、核心逻辑
while (i < nums1.length && j < nums2.length && nums1[i] == nums2[j]) {
    i++;
    j++;
}
第一步：跳过所有相等的数字
从当前位置开始，两个数组逐个比较，直到：
- 发现不等的数字，或
- 某个数组先走完
nums1 = [3, 5, 9], i=0
nums2 = [3, 5, 1], j=0

Step 0: nums1[0]=3 == nums2[0]=3 → i=1, j=1
Step 1: nums1[1]=5 == nums2[1]=5 → i=2, j=2
Step 2: nums1[2]=9 != nums2[2]=1 → 跳出循环

此时 i=2, j=2
--------------------------------------------------------------------------------
return j == nums2.length || (i < nums1.length && nums1[i] > nums2[j]);
第二步：判断谁更大
这个 return 包含两个条件，用 || 连接：
条件含义例子j == nums2.lengthnums2 先走完了nums1=[5,6], nums2=[5] → 相等前缀后 nums2 没了，nums1 还有 → 选 nums1i < nums1.length && nums1[i] > nums2[j]都没走完，但 nums1 当前数字更大nums1=[5,9], nums2=[5,1] → 9 > 1 → 选 nums1
注意：如果 i == nums1.length（nums1 先走完）且 j < nums2.length，两个条件都不满足，返回 false，选 nums2。
--------------------------------------------------------------------------------
四、完整例子走查
例 1：基本不等
nums1 = [9, 1], nums2 = [8, 2], i=0, j=0

while: 9 != 8，直接跳出
return: j==2? No. i<2 && 9>8? Yes → true

结果：选 nums1 的 9 ✅
例 2：相等后分叉
nums1 = [6, 7], nums2 = [6, 8], i=0, j=0

while: 6==6 → i=1, j=1
       7!=8 → 跳出

return: j==2? No. i<2 && 7>8? No → false

结果：选 nums2 的 6（因为后面跟着 8 > 7）✅
例 3：一个数组走完
nums1 = [5, 6, 7], nums2 = [5, 6], i=0, j=0

while: 5==5 → i=1, j=1
       6==6 → i=2, j=2
       j==2 跳出（nums2 越界）

return: j==2? Yes → true

结果：选 nums1 的 5（nums1 还有 7，nums2 没了）✅
例 4：完全相等
nums1 = [3, 3], nums2 = [3, 3, 3], i=0, j=0

while: 3==3 → i=1, j=1
       3==3 → i=2, j=2
       i==2 跳出（nums1 越界）

return: j==3? No（j=2）. i<2? No（i=2）→ false

结果：选 nums2 的 3（nums2 更长，先取它的）✅
--------------------------------------------------------------------------------
五、关键点总结
场景返回值选择在某位置 nums1[x] > nums2[x]truenums1在某位置 nums1[x] < nums2[x]falsenums2nums1 更长且前缀完全相等truenums1（还有剩余）nums2 更长且前缀完全相等falsenums2（还有剩余）
本质：这是一个字典序比较两个后缀的函数，确保合并时每一步都做出全局最优选择。

Refer to
L402.Remove K Digits (Ref.L1673,L84)
L670.Maximum Swap (Ref.L321)
