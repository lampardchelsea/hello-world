https://leetcode.com/problems/maximum-swap/description/
You are given an integer num. You can swap two digits at most once to get the maximum valued number.
Return the maximum valued number you can get.
 
Example 1:
Input: num = 2736
Output: 7236
Explanation: Swap the number 2 and the number 7.

Example 2:
Input: num = 9973
Output: 9973
Explanation: No swap.
 
Constraints:
- 0 <= num <= 10^8
--------------------------------------------------------------------------------
Attempt 1: 2026-06-21
Solution 1: Greedy + Math (10 min)
class Solution {
    public int maximumSwap(int num) {
        char[] chars = String.valueOf(num).toCharArray();
        int len = chars.length;
        // lastDigitIdx[d] = last index where digit d appears
        int[] lastDigitIdx = new int[10];
        for(int i = 0; i < len; i++) {
            lastDigitIdx[chars[i] - '0'] = i;
        }
        // Try to find the first digit that can be improved
        for(int i = 0; i < len; i++) {
            int curDigit = chars[i] - '0';
            // Look for a larger digit (9 down to current+1) that appears later
            for(int d = 9; d > curDigit; d--) {
                // Swap with the rightmost occurrence of the larger digit
                if(lastDigitIdx[d] > i) {
                    char tmp = chars[i];
                    chars[i] = chars[lastDigitIdx[d]];
                    chars[lastDigitIdx[d]] = tmp;
                    return Integer.valueOf(new String(chars));
                }
            }
        }
        // Already the maximum
        return num;
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Refer to Deepseek
以下是 LeetCode 670 "Maximum Swap" 的 Java 解法，采用贪心 + 哈希表记录每个数字最后出现的位置，时间复杂度 O(n)，空间复杂度 O(1)。
class Solution {
    public int maximumSwap(int num) {
        // 将数字转为字符数组以便交换
        char[] digits = Integer.toString(num).toCharArray();
        int n = digits.length;
        
        // 记录每个数字（0-9）最后出现的位置索引
        int[] last = new int[10];
        for (int i = 0; i < n; i++) {
            last[digits[i] - '0'] = i;
        }
        
        // 从左到右扫描，尝试找一个更大的数字放在当前位置
        for (int i = 0; i < n; i++) {
            int curDigit = digits[i] - '0';
            // 从 9 到 curDigit+1 寻找更大的数字
            for (int d = 9; d > curDigit; d--) {
                int j = last[d];
                if (j > i) {
                    // 交换 digits[i] 和 digits[j]
                    char temp = digits[i];
                    digits[i] = digits[j];
                    digits[j] = temp;
                    // 转为整数返回
                    return Integer.parseInt(new String(digits));
                }
            }
        }
        
        // 没有可交换的，返回原数
        return num;
    }
}
思路详解
1.目标：在最多一次交换下，使数字尽可能大。
2.贪心策略：
- 对于高位（左侧）的数字，我们希望用后面出现的更大数字来替换它，这样能最大化结果。
- 因此，从左到右扫描，对于位置 i，检查是否存在比 digits[i] 更大的数字（9 ~ digits[i]+1）出现在 i 的右侧。
- 为了确保交换后结果最大，当有多个相同的大数字时，应该选择最靠右的那个（即最后出现的位置），这样可以将该大数字放到尽可能高的位上，同时不影响更高位的数字。
3.实现技巧：
- 用数组 last 预先记录每个数字（0-9）最后一次出现的位置。
- 遍历时，从 9 到当前数字加 1，如果某个数字的最后位置大于当前下标，就进行交换并立即返回。
4.复杂度：遍历两次，外层 O(n)，内层最多循环 9 次（因为数字只有 0-9），所以总复杂度 O(10n) ≈ O(n)，空间 O(1)。
示例
- 输入 num = 2736
- digits = [2,7,3,6]
- 扫描 i=0，cur=2，从 9 到 3，第一个存在的且最后位置 >0 的数字是 7（位置1），交换 → [7,2,3,6] → 7236。
- 输入 num = 9973，已经是最大，没有可交换，返回原数。
边界情况
- 数字只有一位：直接返回原数。
- 数字已经降序（如 9876）：无更大数字在右侧，返回原数。

Refer to Kimi
Here is the Java solution for LeetCode 670. Maximum Swap.
Approach: Greedy with Last Occurrence Lookup
The key insight is that for each digit, we want to know if a larger digit appears later in the number. If so, swapping the leftmost such digit with the rightmost occurrence of the largest possible digit yields the maximum result.
1.Record last positions of each digit 0-9.
2.Scan left to right: for each digit, check if any larger digit (9 down to current+1) exists later.
3.First valid swap gives the maximum number.
4.If no swap found, return the original number.
Time Complexity: O(n) — single pass to record positions, single pass to find swap
Space Complexity: O(1) — fixed array of size 10
class Solution {
    public int maximumSwap(int num) {
        char[] digits = String.valueOf(num).toCharArray();
        int n = digits.length;
        
        // lastPos[d] = last index where digit d appears
        int[] lastPos = new int[10];
        for (int i = 0; i < n; i++) {
            lastPos[digits[i] - '0'] = i;
        }
        
        // Try to find the first digit that can be improved
        for (int i = 0; i < n; i++) {
            int currentDigit = digits[i] - '0';
            
            // Look for a larger digit (9 down to current+1) that appears later
            for (int d = 9; d > currentDigit; d--) {
                if (lastPos[d] > i) {
                    // Swap with the rightmost occurrence of the larger digit
                    char temp = digits[i];
                    digits[i] = digits[lastPos[d]];
                    digits[lastPos[d]] = temp;
                    return Integer.parseInt(new String(digits));
                }
            }
        }
        
        // Already the maximum
        return num;
    }
}
Walkthrough: num = 2736
StepActionlastPosRecord2 at 0, 7 at 1, 3 at 2, 6 at 3[_, _, 0, 2, _, _, 3, 1, _, _]i=0, digit=2Check 9→3: lastPos[7]=1 > 0 ✅Swap 2 and 7 → 7236
Result: 7236

Refer to
321
