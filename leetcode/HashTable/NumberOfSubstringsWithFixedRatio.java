https://leetcode.ca/2023-01-16-2489-Number-of-Substrings-With-Fixed-Ratio/
You are given a binary string s, and two integers num1 and num2. num1 and num2 are coprime numbers.
A ratio substring is a substring of s where the ratio between the number of 0's and the number of 1's in the substring is exactly num1 : num2.
- For example, if num1 = 2 and num2 = 3, then "01011" and "1110000111" are ratio substrings, while "11000" is not.
Return the number of non-empty ratio substrings of s.
Note that:
- A substring is a contiguous sequence of characters within a string.
- Two values x and y are coprime if gcd(x, y) == 1 where gcd(x, y) is the greatest common divisor of x and y.

Example 1:
Input: s = "0110011", num1 = 1, num2 = 2
Output: 4
Explanation: There exist 4 non-empty ratio substrings.
- The substring s[0..2]: "0110011". It contains one 0 and two 1's. The ratio is 1 : 2.
- The substring s[1..4]: "0110011". It contains one 0 and two 1's. The ratio is 1 : 2.
- The substring s[4..6]: "0110011". It contains one 0 and two 1's. The ratio is 1 : 2.
- The substring s[1..6]: "0110011". It contains two 0's and four 1's. The ratio is 2 : 4 == 1 : 2.
It can be shown that there are no more ratio substrings.

Example 2:
Input: s = "10101", num1 = 3, num2 = 1
Output: 0
Explanation: There is no ratio substrings of s. We return 0.
 
Constraints:
- 1 <= s.length <= 105
- 1 <= num1, num2 <= s.length
- num1 and num2 are coprime integers.
--------------------------------------------------------------------------------
Attempt 1: 2026-05-31
Solution 1: Hash Table + Math + Prefix Sum (90 min)
class Solution {
    public long fixedRatio(String s, int num1, int num2) {
        long result = 0;
        long zeros = 0;
        long ones = 0;
        // s[i + 1... j] have a ratio num1 : num2 between 0 and 1
        // (zerosj - zerosi) / (onesj - onesi) = num1 / num2
        Map<Long, Long> map = new HashMap<>();
        // Initial state: before processing any characters, key = 0 has occurred once
        map.put(0L, 1L);
        for(char c : s.toCharArray()) {
            if(c == '0') {
                zeros++;                            
            } else {
                ones++;                            
            }
            long key = ones * num1 - zeros * num2;
            result += map.getOrDefault(key, 0L);
            map.put(key, map.getOrDefault(key, 0L) + 1);
        }
        return result;
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Refer to Kimi
Here is the Java solution for LeetCode 2489. Number of Substrings With Fixed Ratio.
Approach: Prefix Sum + Hash Map
For a substring s[i+1..j] to have a ratio num1 : num2 between 0s and 1s:


Cross-multiplying (since num1 and num2 are coprime):


So we compute the key ones * num1 - zeros * num2 at each position. If the same key appeared at index ibefore,  then every substring from i+1 to current index j satisfies the ratio.
We use a HashMap to count how many times each key has appeared.
Time Complexity: O(n)
Space Complexity: O(n)
import java.util.HashMap;
import java.util.Map;

class Solution {
    public long fixedRatio(String s, int num1, int num2) {
        long zeros = 0, ones = 0;
        long ans = 0;
        
        // Map from key value to its frequency count
        Map<Long, Long> count = new HashMap<>();
        // Initial state: before processing any characters, key = 0 has occurred once
        count.put(0L, 1L);
        
        for (char c : s.toCharArray()) {
            if (c == '0') {
                zeros++;
            } else {
                ones++;
            }
            
            // Compute the transformed key: ones * num1 - zeros * num2
            long key = ones * num1 - zeros * num2;
            
            // Add the number of previous occurrences of this same key
            ans += count.getOrDefault(key, 0L);
            
            // Increment the count for this key
            count.put(key, count.getOrDefault(key, 0L) + 1);
        }
        
        return ans;
    }
}
Walkthrough with s = "0110011", num1 = 1, num2 = 2
IndexCharzerosonesKey = ones*1 - zeros*2count beforeans addedcount afterinit—000{0:1}—{0:1}0010-2{0:1}0{0:1, -2:1}1111-1{0:1, -2:1}0{0:1, -2:1, -1:1}21120{0:1, -2:1, -1:1}1{0:2, -2:1, -1:1}3022-2{0:2, -2:1, -1:1}1{0:2, -2:2, -1:1}4032-4{0:2, -2:2, -1:1}0{0:2, -2:2, -1:1, -4:1}5133-3{0:2, -2:2, -1:1, -4:1}0{0:2, -2:2, -1:1, -4:1, -3:1}6134-2{0:2, -2:2, -1:1, -4:1, -3:1}2{0:2, -2:3, -1:1, -4:1, -3:1}
Total ans = 1 + 1 + 2 = 4 ✓

追问：能否用中文详细解释一遍这个解法和每一步的含义
好的，下面用中文详细解释 LeetCode 2489. Number of Substrings With Fixed Ratio 的完整解法。
--------------------------------------------------------------------------------
一、问题本质
给定一个只包含 0 和 1 的字符串 s，以及两个正整数 num1 和 num2（互质）。
要求找出有多少个子串，满足：
子串中 0 的个数 : 1 的个数 = num1 : num2
注意：子串中可能只有 0 或只有 1，此时另一边个数为 0，比例仍然可以成立（例如 num1=0 的情况，但本题 num1, num2 >= 1）。
--------------------------------------------------------------------------------
二、核心数学推导
1. 前缀和思路
假设我们遍历到字符串的第 j 个位置（包含），记：
- zeros[j] = 前 j+1 个字符中 0 的累计个数
- ones[j] = 前 j+1 个字符中 1 的累计个数
对于任意一个子串 s[i+1 ... j]（即从第 i+1 位到第 j 位）：
- 子串中 0 的个数 = zeros[j] - zeros[i]
- 子串中 1 的个数 = ones[j] - ones[i]
2. 比例条件转化
题目要求这个子串满足比例：

即：
(zeros[j] - zeros[i]) * num2 = (ones[j] - ones[i]) * num1
展开：
zeros[j] * num2 - zeros[i] * num2 = ones[j] * num1 - ones[i] * num1
把含 j 的项移到左边，含 i 的项移到右边：
ones[j] * num1 - zeros[j] * num2 = ones[i] * num1 - zeros[i] * num2
3. 关键发现
等式两边形式完全一样！
这意味着：如果我们定义一个"特征值"（key）：
{key}(k) = ones[k] * num1 - zeros[k] * num2
那么 子串 s[i+1 ... j] 满足固定比例 的充要条件就是：
{key}(j) = {key}(i)
换句话说，如果在位置 j 算出的特征值，和之前某个位置 i 算出的特征值相同，那么从 i+1 到 j 的这段子串就一定满足 0 和 1 的固定比例。
--------------------------------------------------------------------------------
三、算法设计
基于上面的发现，我们不需要两重循环枚举所有子串，只需要：
1.从左到右遍历字符串，一边统计 zeros 和 ones。
2.计算当前的 key = ones * num1 - zeros * num2。
3.用一个 HashMap 记录每个 key 之前出现过多少次。
4.如果当前 key 之前出现过 k 次，就说明有 k 个不同的起始位置 i，使得子串 s[i+1 ... 当前位置] 满足条件，直接把 k 加入答案。
5.然后把当前 key 的计数 +1，继续往后走。
为什么初始时 HashMap 里要放 (0, 1)？
在还没开始遍历（一个字符都没读）时，我们处于"空前缀"状态：
- zeros = 0, ones = 0
- key = 0 * num1 - 0 * num2 = 0
这个空前缀对应 i = -1（起始位置之前）。如果后面某个位置 j 的 key 也是 0，说明子串 s[0 ... j]（从开头到 j）本身就满足比例。所以初始时 key=0 的计数是 1。
--------------------------------------------------------------------------------
四、代码逐行解释
class Solution {
    public long fixedRatio(String s, int num1, int num2) {
        long zeros = 0, ones = 0;   // 累计的 0 和 1 的个数
        long ans = 0;               // 最终答案
        
        // HashMap: key -> 这个 key 之前出现过多少次
        Map<Long, Long> count = new HashMap<>();
        
        // 初始状态：空前缀，zeros=0, ones=0, key=0，出现过 1 次
        count.put(0L, 1L);
        
        // 遍历字符串中的每个字符
        for (char c : s.toCharArray()) {
            if (c == '0') {
                zeros++;            // 遇到 0，zeros 加 1
            } else {
                ones++;             // 遇到 1，ones 加 1
            }
            
            // 计算当前位置的特征值 key
            long key = ones * num1 - zeros * num2;
            
            // 如果之前有过相同的 key，每个都对应一个合法子串
            ans += count.getOrDefault(key, 0L);
            
            // 把当前 key 的计数加 1，供后面的位置使用
            count.put(key, count.getOrDefault(key, 0L) + 1);
        }
        
        return ans;
    }
}
--------------------------------------------------------------------------------
五、完整例子走一遍
输入：s = "0110011", num1 = 1, num2 = 2
目标比例：0 的个数 : 1 的个数 = 1 : 2，即 0 的个数 * 2 = 1 的个数 * 1
我们逐个字符走一遍：
步骤字符zerosoneskey = ones×1 - zeros×2之前相同 key 次数ans 增加累计 ansHashMap 状态初始—000——0{0: 1}00100 - 2 = -2000{0:1, -2:1}11111 - 2 = -1000{0:1, -2:1, -1:1}21122 - 2 = 01（初始的 0）11{0:2, -2:1, -1:1}30222 - 4 = -21（步骤 0 的 -2）12{0:2, -2:2, -1:1}40322 - 6 = -4002{0:2, -2:2, -1:1, -4:1}51333 - 6 = -3002{0:2, -2:2, -1:1, -4:1, -3:1}61344 - 6 = -22（步骤 0 和 3）24{0:2, -2:3, -1:1, -4:1, -3:1}
最终答案：4
这 4 个子串分别是：
1.步骤 2（key=0 匹配初始状态）：子串 s[0..2] = "011"
- 0 的个数 = 1，1 的个数 = 2 → 比例 1:2 ✅
2.步骤 3（key=-2 匹配步骤 0）：子串 s[1..3] = "110"
- 0 的个数 = 1，1 的个数 = 2 → 比例 1:2 ✅
3.步骤 6（key=-2 匹配步骤 0）：子串 s[1..6] = "110011"
- 0 的个数 = 2，1 的个数 = 4 → 比例 2:4 = 1:2 ✅
4.步骤 6（key=-2 匹配步骤 3）：子串 s[4..6] = "011"
- 0 的个数 = 1，1 的个数 = 2 → 比例 1:2 ✅
--------------------------------------------------------------------------------
六、复杂度分析
项目复杂度说明时间O(n)只遍历字符串一次，HashMap 操作 O(1)空间O(n)HashMap 最多存储 n+1 个不同的 key
--------------------------------------------------------------------------------
七、总结
这个解法的精妙之处在于把比例条件通过代数变形，转化为了"两个位置的前缀特征值相等"的问题。一旦转化成功，就可以用 "前缀和 + 哈希表" 的经典 O(n) 技巧来解决，避免了 O(n^2) 的暴力枚举。

Refer to
L696.Count Binary Substrings (Ref.L271,L2489,L3234)
