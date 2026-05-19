https://leetcode.com/problems/custom-sort-string/description/
You are given two strings order and s. All the characters of order are unique and were sorted in some custom order previously.
Permute the characters of s so that they match the order that order was sorted. More specifically, if a character x occurs before a character y in order, then x should occur before y in the permuted string.
Return any permutation of s that satisfies this property.
 
Example 1:
Input: order = "cba", s = "abcd"
Output: "cbad"
Explanation: "a", "b", "c" appear in order, so the order of "a", "b", "c" should be "c", "b", and "a".
Since "d" does not appear in order, it can be at any position in the returned string. "dcba", "cdba", "cbda" are also valid outputs.

Example 2:
Input: order = "bcafg", s = "abcd"
Output: "bcad"
Explanation: The characters "b", "c", and "a" from order dictate the order for the characters in s. The character "d" in s does not appear in order, so its position is flexible.
Following the order of appearance in order, "b", "c", and "a" from s should be arranged as "b", "c", "a". "d" can be placed at any position since it's not in order. The output "bcad" correctly follows this rule. Other arrangements like "dbca" or "bcda" would also be valid, as long as "b", "c", "a" maintain their order.
 
Constraints:
- 1 <= order.length <= 26
- 1 <= s.length <= 200
- order and s consist of lowercase English letters.
- All the characters of order are unique.
--------------------------------------------------------------------------------
Attempt 1: 2026-05-18
Solution 1: Hash Table (10 min)
class Solution {
    public String customSortString(String order, String s) {
        // Step 1: Count frequency of each char in s
        int[] freq = new int[26];
        for(char c : s.toCharArray()) {
            freq[c - 'a']++;
        }
        // Step 2: Append chars in the custom order
        StringBuilder sb = new StringBuilder();
        for(char c : order.toCharArray()) {
            while(freq[c - 'a']-- > 0) {
                sb.append(c);
            }
        }
        // Step 3: Append remaining chars not present in order
        for(char c : s.toCharArray()) {
            while(freq[c - 'a']-- > 0) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}

Time Complexity: 
O(N + M) where N is the length of s and M is the length of order. 
We traverse s once to count, traverse order once, and then traverse the 26-length array. 
All operations are linear.
Space Complexity: 
O(1) auxiliary space. The count array is fixed at size 26, and StringBuilder 
only stores the output string.

Refer to Deepseek
我们要求解LeetCode 791. Custom Sort String。题目描述：给定两个字符串order和s，order中的字符是唯一的，且顺序是自定义的。我们需要根据order定义的顺序对s中的字符进行排序，但只针对出现在order中的字符，没有出现在order中的字符可以按任意顺序放在末尾（通常按原顺序或任意，题目不要求具体顺序，只要在最后即可）。要求返回排序后的字符串。
典型解法：统计s中每个字符的频率，然后按照order的顺序构建结果字符串，对于order中没有的字符，最后添加。
需要注意：order中出现的字符可能不全在s中，只需处理存在的。另外，s中可能有order中没有的字符，这些字符按任意顺序放在最后。通常我们按原顺序或按ASCII顺序，但题目要求不严格。
实现：先创建一个频率数组，统计s中每个字符（只考虑小写字母？题目中字符是小写字母，order长度<=26，s长度<=200）。然后遍历order，对每个字符，如果频率>0，则添加该字符相应次数。最后遍历所有字母，将剩余的（order中没有的）添加。
复杂度O(n+m)，n为order长度，m为s长度。
下面提供Java代码：
class Solution {
    public String customSortString(String order, String s) {
        // 统计 s 中每个字符的出现次数
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }
        
        StringBuilder result = new StringBuilder();
        // 按照 order 的顺序添加字符
        for (char c : order.toCharArray()) {
            while (freq[c - 'a']-- > 0) {
                result.append(c);
            }
        }
        // 添加 order 中没有出现的字符（剩余字符）
        for (char c = 'a'; c <= 'z'; c++) {
            while (freq[c - 'a']-- > 0) {
                result.append(c);
            }
        }
        return result.toString();
    }
}


Refer to
L2545.Sort the Students by Their Kth Score (Ref.L791)
