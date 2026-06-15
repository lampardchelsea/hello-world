https://leetcode.com/problems/count-the-number-of-substrings-with-dominant-ones/description/
You are given a binary string s.
Return the number of substrings with dominant ones.
A string has dominant ones if the number of ones in the string is greater than or equal to the square of the number of zeros in the string.
 
Example 1:
Input: s = "00011"
Output: 5
Explanation:
The substrings with dominant ones are shown in the table below.
ijs[i..j]Number of ZerosNumber of Ones33101441012301113411022401112
Example 2:
Input: s = "101101"
Output: 16
Explanation:
The substrings with non-dominant ones are shown in the table below.
Since there are 21 substrings total and 5 of them have non-dominant ones, it follows that there are 16 substrings with dominant ones.
ijs[i..j]Number of ZerosNumber of Ones110104401014011022041011023150110123
 
Constraints:
- 1 <= s.length <= 4 * 104
- s consists only of characters '0' and '1'.
--------------------------------------------------------------------------------
Attempt 1: 2026-06-14
Solution 1: Two Pointers + Math (90 min)
class Solution {
    public int numberOfSubstrings(String s) {
        int n = s.length();
        // 最大可能的零的个数：如果子串中零的个数超过 sqrt(n)，则零的平方会大于 n，
        // 而子串长度最多为 n，不可能满足 ones >= zeros²，因此只需考虑 zeros <= sqrt(n)
        int max_zero = (int) Math.sqrt(n);
        
        // 存储所有 '0' 字符的下标（按升序）
        List<Integer> zeros = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') {
                zeros.add(i);
            }
        }
        // 添加哨兵 n，方便处理最后一个零之后的区间（表示字符串末尾）
        zeros.add(n);
        
        int result = 0;
        // zeroIdx 指向 zeros 列表中第一个大于等于当前左端点 i 的零的下标
        int zeroIdx = 0;
        
        // 枚举每个左端点 i
        for (int i = 0; i < n; i++) {
            // ----- 情况1：子串中不含任何零（全为 '1'）-----
            if (s.charAt(i) == '1') {
                // 从 i 开始，直到下一个零之前（不包含该零），所有右端点构成的子串都不含零
                int nextZero = zeros.get(zeroIdx);
                // 右端点可取 i, i+1, ..., nextZero-1，共 nextZero - i 个
                result += nextZero - i;
            }
            
            // ----- 情况2：子串中包含至少一个零，枚举零的个数 count_0 -----
            // k 从当前 zeroIdx 开始，指向第 count_0 个零在 zeros 中的索引
            for (int k = zeroIdx; k < zeros.size() - 1; k++) {
                int count_0 = k - zeroIdx + 1;   // 当前考虑的零的个数
                if (count_0 > max_zero) {
                    // 零的个数超过上限，后面的情况不可能满足，直接跳出循环
                    break;
                }
                // j 是第 count_0 个零的位置（子串必须包含它）
                int j = zeros.get(k);
                // nextZeroIdx 是第 count_0+1 个零的位置，即子串能扩展的右边界（不包含该零）
                int nextZeroIdx = zeros.get(k + 1);
                
                // 以 i 为左端点，恰好包含 count_0 个零的最短子串是 [i, j]
                int totalLen = j - i + 1;
                int count_1 = totalLen - count_0;   // 该最短子串中 '1' 的个数
                
                if (count_1 >= count_0 * count_0) {
                    // 最短子串已经满足条件，那么向右延长到下一个零之前的所有子串（只增加 '1'）
                    // 都必然满足条件，因为 '1' 只会增加，不等式继续保持
                    // 可选的右端点范围：j, j+1, ..., nextZeroIdx-1，共 nextZeroIdx - j 个
                    result += nextZeroIdx - j;
                } else {
                    // 最短子串不满足，需要额外的 '1' 才能达到 count_0²
                    int need_1 = count_0 * count_0 - count_1;
                    // 右端点至少要到 j + need_1 才能补足需要的 '1'（注意 j 到 nextZeroIdx-1 之间都是 '1'）
                    int rightStart = j + need_1;
                    if (rightStart < nextZeroIdx) {
                        // 从 rightStart 到 nextZeroIdx-1 都是合法的右端点
                        result += nextZeroIdx - rightStart;
                    }
                    // 若 rightStart >= nextZeroIdx，则无法满足，贡献为 0
                }
            }
            
            // 当前左端点 i 处理完毕，准备移动到下一个左端点 i+1
            // 如果当前字符是 '0'，那么它不会再出现在后续子串中（因为左端点右移后它被排除）
            // 因此需要将 zeroIdx 指向下一个零的位置
            if (s.charAt(i) == '0') {
                zeroIdx++;
            }
        }
        return result;
    }
}

Time Complexity: O(n*sqrt(n))
Space Complexity: O(n)

Refer to Kimi
Here is the Java solution for LeetCode 3234. Count the Number of Substrings With Dominant Ones.
Approach: Enumerate by Zero Count (O(nsqrt{n}))
Key Insight:
If a substring has cnt0 zeros and cnt1 ones, the condition is cnt1 >= cnt0².
Since substring length L = cnt0 + cnt1 <= n, we have cnt0 + cnt0² <= n, which means cnt0 is at most about √n (≈ 200 when n = 4×10⁴).
So we can enumerate the number of zeros in the substring (from 0 to √n), and for each starting position i, find where the cnt0-th zero occurs. All valid right endpoints form a contiguous range, which can be counted in O(1).
Algorithm:
1.Pre-collect all positions of '0' into a list zeros, append a sentinel n at the end.
2.Use a pointer ptr to track the first zero at or after the current left endpoint i.
3.Case cnt0 = 0: If s[i] == '1', all substrings starting at i consisting of only 1s (up to the next zero) are valid.
4.Case cnt0 >= 1: For each possible cnt0 (up to √n):
- Let j = position of the cnt0-th zero, k = position right before the (cnt0+1)-th zero.
- The substring [i, j] contains exactly cnt0 zeros. If it already satisfies cnt1 >= cnt0², then extending the right endpoint from j to k only adds more 1s, so all k - j + 1 extensions are valid.
- If not, calculate how many extra 1s are needed (extra = cnt0² - cnt1). The right endpoint must be at least j + extra. If this is within [j, k], add k - (j + extra) + 1.
5.If s[i] is '0', advance ptr so the next iteration starts after it.
Time Complexity: O(nsqrt{n})
Space Complexity: O(n)
class Solution {
    public int numberOfSubstrings(String s) {
        int n = s.length();
        int mx = (int) Math.sqrt(n);  // max possible zeros in a valid substring
        
        // Collect all positions of '0', append sentinel n at the end
        List<Integer> zeros = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') {
                zeros.add(i);
            }
        }
        zeros.add(n);
        
        int ans = 0;
        int ptr = 0;  // points to the first zero at or after current left endpoint i
        
        for (int i = 0; i < n; i++) {
            // Case cnt0 = 0: substring contains only 1s
            if (s.charAt(i) == '1') {
                int nextZero = zeros.get(ptr);
                ans += nextZero - i;  // right endpoints: i, i+1, ..., nextZero-1
            }
            
            // Case cnt0 >= 1: enumerate number of zeros from 1 to mx
            for (int zidx = ptr; zidx < zeros.size() - 1; zidx++) {
                int cnt0 = zidx - ptr + 1;  // the cnt0-th zero from position i
                if (cnt0 > mx) break;       // cnt0 cannot exceed sqrt(n)
                
                int j = zeros.get(zidx);           // position of cnt0-th zero
                int k = zeros.get(zidx + 1) - 1;   // position before (cnt0+1)-th zero
                
                int sz = j - i + 1;       // length of substring [i, j]
                int cnt1 = sz - cnt0;     // number of 1s in [i, j]
                
                if (cnt1 >= cnt0 * cnt0) {
                    // [i, j] is already valid; extending to k only adds 1s
                    ans += k - j + 1;
                } else {
                    // need extra 1s to reach cnt0^2
                    int extra = cnt0 * cnt0 - cnt1;
                    int rightBound = j + extra;
                    if (rightBound <= k) {
                        ans += k - rightBound + 1;
                    }
                }
            }
            
            // If current char is '0', it will never be used as "cnt0-th zero"
            // for any future left endpoint, so advance ptr
            if (s.charAt(i) == '0') {
                ptr++;
            }
        }
        
        return ans;
    }
}

Walkthrough with s = "00011" (n = 5, mx = 2)
is[i]ptrzeros[ptr]ActionAdded to ans0000cnt0=1: j=0,k=0, need 1 extra, rightBound=1>0 ❌<br>cnt0=2: j=1,k=1, need 4 extra, rightBound=5>1 ❌01011cnt0=1: j=1,k=1, need 1 extra, rightBound=2>1 ❌<br>cnt0=2: j=2,k=4, need 4 extra, rightBound=6>4 ❌02022cnt0=1: j=2,k=4, sz=1,cnt1=0, need 1 extra, rightBound=3<=4 ✅2 ("01", "011")3135All-1s: 5-3=22 ("1", "11")4135All-1s: 5-4=11 ("1")
Total = 5 ✓
Refer to Deepseek
感谢您分享这个更高效的解法！Kimi 的代码充分利用了“0 的个数最多 √n”这一性质，通过预存储所有 0 的位置，并针对每个左端点枚举 0 的个数，直接计算符合条件的子串范围，避免了原解法中逐字符扫描的 O(√n) 内层循环（虽然也是 O(√n)，但常数更小）。下面详细解释这个解法的核心思想。
--------------------------------------------------------------------------------
核心思路（Kimi 解法）
1.预存所有 0 的位置
将字符串中所有 '0' 的下标存入列表 zeros，并在末尾添加一个哨兵 n（用于快速得到最后一个 0 之后的边界）。
2.枚举左端点 i 并分类讨论
- Case 1：子串中不含 0如果 s[i] == '1'，那么从 i 开始直到下一个 0 之前的所有位置作为右端点，构成的子串都没有 0，全部满足 ones >= 0。设 nextZero = zeros.get(ptr)（ptr 指向第一个 ≥ i 的 0 的位置），则长度为 nextZero - i，累加到答案。
- Case 2：子串中包含 k 个 0（k ≥ 1）由于 zeros 列表是递增的，对于当前左端点 i，ptr 指向第一个 ≥ i 的 0 的下标。令第 cnt0 个 0 的位置为 j = zeros.get(zidx)（其中 zidx = ptr + cnt0 - 1），第 cnt0+1 个 0 的位置为 nextZeroPos = zeros.get(zidx+1)。那么，以 i 为左端点，恰好包含 cnt0 个 0 的子串，其右端点可以取从 j 到 nextZeroPos - 1 的任意值（因为超过 nextZeroPos 就会多出一个 0）。其中：
- 右端点 j 对应子串 [i, j]，它包含 cnt0 个 0 和若干 1；
- 右端点右移时，只会增加 1（因为 j 和 nextZeroPos 之间都是 1）。
我们需要这些子串满足 ones >= cnt0²。
设子串 [i, j] 中的 1 的个数为 cnt1 = (j - i + 1) - cnt0。
- 如果 cnt1 >= cnt0²，那么 [i, j] 本身已经满足，并且向右延长到 nextZeroPos-1 的所有子串也都满足（因为只会增加 1，不等式保持）。因此贡献为 (nextZeroPos - 1) - j + 1 = nextZeroPos - j。
- 如果 cnt1 < cnt0²，则需要额外补充 extra = cnt0² - cnt1 个 1 才能满足。因此，右端点必须至少为 j + extra。只要 j + extra <= nextZeroPos - 1，就可以得到一段连续的有效右端点：j + extra 到 nextZeroPos - 1，个数为 (nextZeroPos - 1) - (j + extra) + 1 = nextZeroPos - j - extra。若 j + extra > nextZeroPos - 1，则无法满足。
3.移动左端点时调整 ptr
当 s[i] == '0' 时，这个 0 将不再作为任何后续左端点的“第一个 0”，所以 ptr++。
--------------------------------------------------------------------------------
时间复杂度分析
- 外层循环遍历每个左端点 i：O(n)
- 内层循环枚举 0 的个数 cnt0 从 1 到 mx（mx ≈ √n）：O(√n)
- 每次内层计算为 O(1)（只做算术和索引访问）
总复杂度 O(n√n)，但常数远小于逐字符扫描的版本（因为不需要内层循环中逐个字符判断 '0'/'1'，而是直接利用 zeros 列表跳转）。对于 n=10^5，√n≈316，总操作约 3×10⁷，加上高效的整数运算，可以在 300ms 内完成。
--------------------------------------------------------------------------------
代码逐段解读（附注释）
class Solution {
    public int numberOfSubstrings(String s) {
        int n = s.length();
        int mx = (int) Math.sqrt(n);  // 最大可能的0的个数

        // 记录所有0的位置，并在末尾加一个哨兵n
        List<Integer> zeros = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') zeros.add(i);
        }
        zeros.add(n);   // 用于计算最后一个0之后的区间

        int ans = 0;
        int ptr = 0;   // 指向第一个 >= 当前左端点 i 的0的下标

        for (int i = 0; i < n; i++) {
            // ----- 情况1：子串中不含0 (全1) -----
            if (s.charAt(i) == '1') {
                int nextZero = zeros.get(ptr);
                ans += nextZero - i;  // 右端点可以是i, i+1, ..., nextZero-1
            }

            // ----- 情况2：子串中包含cnt0个0 (cnt0从1到mx) -----
            for (int zidx = ptr; zidx < zeros.size() - 1; zidx++) {
                int cnt0 = zidx - ptr + 1;   // 当前考虑的第cnt0个0
                if (cnt0 > mx) break;        // 超过√n不可能满足条件

                int j = zeros.get(zidx);          // 第cnt0个0的位置
                int nextZeroPos = zeros.get(zidx + 1); // 第cnt0+1个0的位置

                int totalLen = j - i + 1;       // 子串[i..j]的长度
                int cnt1 = totalLen - cnt0;     // 子串[i..j]中1的个数

                if (cnt1 >= cnt0 * cnt0) {
                    // [i, j] 已经满足，向右延长直到下一个0之前都满足
                    ans += nextZeroPos - j;
                } else {
                    int need = cnt0 * cnt0 - cnt1;   // 还需要多少个1
                    int rightStart = j + need;       // 最少需要的右端点
                    if (rightStart < nextZeroPos) {
                        ans += nextZeroPos - rightStart;
                    }
                }
            }

            // 如果当前字符是0，则它不会再作为后续左端点的"第一个0"，ptr右移
            if (s.charAt(i) == '0') {
                ptr++;
            }
        }
        return ans;
    }
}
--------------------------------------------------------------------------------
对比原解法
方面原解法（逐字符扫描）Kimi 解法（基于0的位置）内层循环操作逐个字符判断并更新计数直接通过零的位置计算区间长度常数因子较高（每次循环需分支、条件判断）极低（仅算术运算和数组访问）适用性通过所有测试，但耗时 1807ms耗时 253ms，性能提升 7 倍
Kimi 的解法本质上是将“连续扫描”转化为“预计算零的索引 + 数学公式”，这是此类计数问题常见的优化技巧，非常值得学习。再次感谢您的分享！

追问：详细解释下面这一段代码
// 如果当前字符是0，则它不会再作为后续左端点的"第一个0"，ptr右移
if (s.charAt(i) == '0') {
    ptr++;
}
这段代码出现在枚举左端点 i 的循环末尾，它的作用是维护 ptr 指针，使其始终指向当前左端点 i 及其之后出现的第一个 '0' 在 zeros 列表中的索引。
--------------------------------------------------------------------------------
1. 回顾 ptr 的含义
- zeros 列表按升序存储了字符串中所有 '0' 的下标（例如 [0, 3, 5, ...]），并在末尾添加一个哨兵 n。
- ptr 是一个整数，它表示：对于当前的左端点 i，zeros.get(ptr) 是第一个 ≥ i 的 0 的位置。
- 换句话说，ptr 指向了从 i 开始往后看到的“第 0 个零”在 zeros 中的索引。
例如：
字符串 s = "01001"
zeros = [0, 2, 3] 加上 n=5 → [0,2,3,5]
当 i = 0 时，第一个 ≥0 的零在 zeros[0]=0，所以 ptr=0。
当 i = 1 时，第一个 ≥1 的零在 zeros[1]=2，所以 ptr=1。
当 i = 2 时，第一个 ≥2 的零在 zeros[1]=2，所以 ptr=1。
当 i = 3 时，第一个 ≥3 的零在 zeros[2]=3，所以 ptr=2。
--------------------------------------------------------------------------------
2. 为什么要在 s[i] == '0' 时增加 ptr？
在循环中，我们处理完当前左端点 i 的所有子串后，马上就会进入下一个左端点 i+1。
- 如果当前字符 s[i] 是 '1'，那么它不影响零的位置：从 i+1 开始，第一个 '0' 的位置与从 i 开始看到的第一个 '0' 是同一个（因为 i 本身不是零），所以 ptr 不需要改变。
- 如果当前字符 s[i] 是 '0'，那么 这个零已经作为当前左端点 i 的第一个零（或者第几个零）被使用过了。当左端点向右移动到 i+1 时，这个位于 i 的零已经不在新子串的范围内了，因此新子串（以 i+1 为左端点）所看到的“第一个零”应该是下一个零（即 zeros.get(ptr+1)）。所以必须将 ptr 加 1，让它指向下一个零的位置。
举例说明
字符串 s = "010"（下标 0~2），zeros = [0, 2, 3]。
- i=0，s[0]='0'，当前 ptr=0（指向 zeros[0]=0）。循环内会处理包含 0 的情况（cnt0=1,2...）。循环结束后，因为 s[0]=='0'，执行 ptr++，此时 ptr=1，指向 zeros[1]=2。
- 接下来 i=1，s[1]='1'。我们需要知道从下标 1 开始第一个零的位置——它应该是下标 2，正是 zeros[1]=2，符合 ptr=1。如果之前没加 ptr，ptr 仍然是 0，就会错误地认为第一个零在位置 0（但实际上位置 0 已经在左端点之前，不能包含在子串中），导致算法错误。
--------------------------------------------------------------------------------
3. 总结
- ptr 的维护保证了对于任何左端点 i，zeros.get(ptr) 都是第一个出现在 i 或 i 之后的 '0'。
- 当 s[i] == '0' 时，这个零被“丢弃”，所以 ptr 必须递增，以指向后续的零。
- 当 s[i] == '1' 时，零的位置不变，ptr 保持原值。
这种指针移动方式与双指针（滑动窗口）中维护“下一个元素”的思路一致，是算法能够高效枚举所有包含不同个数零的子串的关键。

Refer to
L696.Count Binary Substrings (Ref.L271,L2489,L3234)
