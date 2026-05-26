https://leetcode.ca/2016-08-05-249-Group-Shifted-Strings/
We can shift a string by shifting each of its letters to its successive letter.
- For example, "abc" can be shifted to be "bcd".
We can keep shifting the string to form a sequence.
- For example, we can keep shifting "abc" to form the sequence: "abc" -> "bcd" -> ... -> "xyz".
Given an array of strings strings, group all strings[i] that belong to the same shifting sequence. You may return the answer in any order.
 
Example 1:
Input: strings = ["abc","bcd","acef","xyz","az","ba","a","z"]
Output: [["acef"],["a","z"],["abc","bcd","xyz"],["az","ba"]]

Example 2:
Input: strings = ["a"]
Output: [["a"]]
 
Constraints:
- 1 <= strings.length <= 200
- 1 <= strings[i].length <= 50
- strings[i] consists of lowercase English letters.
--------------------------------------------------------------------------------
Attempt 1: 2026-05-25
Solution 1: Hash Table (10 min)
Refer to Deepseek

--------------------------------------------------------------------------------
🎯 解法剖析：核心思路与原理
这道题的核心是找到一个能唯一标识一个“移位序列”的签名（signature）。
无论我们将一个字符串进行多少次移位，它内部字母之间的相对间距模式（interval pattern） 是保持不变的。这就像一首歌的旋律，不管你把它的调性（key）从C大调升到D大调，音符之间的音程关系是不变的，我们听起来还是同一首歌。
所以，问题就转换成了：如何为每个字符串生成一个能代表其“旋律”的签名，然后以签名为键进行分组。
主要有两种生成签名的方法：
1.差值法 (Difference Sequence): 计算字符串中相邻字母之间的差值模26的结果，将这些差值组合起来作为签名。比如 "abc" 的差值是 [1, 1]。
2.规范化法 (Normalization): 将整个字符串“平移”，直到它的第一个字母变成 'a'，然后将这个“归一化”后的字符串作为签名。比如 "bcd" 会变成 "abc"。
--------------------------------------------------------------------------------
🧩 方法一：差值法（最常用、最直观）
算法流程图
flowchart TD
    A[开始] --> B(输入字符串数组 strings)
    B --> C[创建 HashMap 用于分组]
    C --> D{遍历 strings 中的每个字符串 str}
    D --> E[生成 str 的差值签名 key]
    E --> F[将 str 添加到以 key 为键的列表中]
    F --> G{还有字符串吗？}
    G -- Yes --> D
    G -- No --> H[收集 HashMap 中所有的值列表]
    H --> I[返回分组结果]
    I --> J[结束]
Java代码（使用差值法）
import java.util.*;

class Solution {
    public List<List<String>> groupStrings(String[] strings) {
        Map<String, List<String>> groups = new HashMap<>();
        
        for (String s : strings) {
            StringBuilder keyBuilder = new StringBuilder();
            // 遍历字符串，计算相邻字符的差值
            for (int i = 1; i < s.length(); i++) {
                // 核心：计算差值并处理循环
                int diff = (s.charAt(i) - s.charAt(i - 1) + 26) % 26;
                // 将差值转换为字符存入 key，例如 diff=1 -> 'b'
                keyBuilder.append((char)('a' + diff));
            }
            // 处理长度为1的字符串，使其 key 为空字符串
            String key = keyBuilder.toString();
            
            // 使用 computeIfAbsent 简化代码，如果 key 不存在则创建新列表
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        
        return new ArrayList<>(groups.values());
    }
}
关键点解读：
1.(s.charAt(i) - s.charAt(i - 1) + 26) % 26: 这是整个算法的精髓。
- s.charAt(i) - s.charAt(i - 1) 计算的是相邻字母的ASCII码差值。比如 'b' - 'a' = 1。如果遇到 'a' - 'z'，结果是 -25。
- + 26 是为了将负数变成正数，例如 -25 + 26 = 1。
- % 26 确保结果在 0 到 25 的范围内。这个结果精确地代表了从一个字母到下一个字母需要向前移动多少步（循环）。
2.(char)('a' + diff): 这段代码将数值差值 diff 映射回一个小写字母。这样做成的 key 是紧凑的字符串，而不是 [1,1] 这种数组。'a' 加上 0 还是 'a'，加上 1 变成 'b'，以此类推。
3.空字符串 "" 作为 key: 如果一个字符串长度为 1，for 循环不会执行，keyBuilder 是空的，其 toString() 会返回空字符串 ""。这意味着所有单字符的字符串，如 "a", "b", "z"，都会被放入同一个 key = "" 的分组中。这是完全正确的，因为所有单字符的字符串都可以通过移位互相转换。
--------------------------------------------------------------------------------
💡 方法二：规范化法（另一种巧妙思路）
这种方法更接近“平移旋律”的比喻：我们主动将所有字符串“翻译”成同一个“调性”下的版本。
算法流程
flowchart LR
    subgraph A[原始字符串]
        direction LR
        S1["bcd"]
    end

    subgraph B[步骤1]
        direction LR
        D[计算偏移量]
        O["offset = str[0] - 'a'"]
    end

    S1 --> B
    O --> C{步骤2: 对每个字符平移}

    subgraph C[计算过程]
        direction TB
        N1["'b' - 1 = 'a'"]
        N2["'c' - 1 = 'b'"]
        N3["'d' - 1 = 'c'"]
    end

    subgraph D[规范化结果]
        K["abc"]
    end

    C -.-> K
    K --> G[作为分组key]
Java代码（使用规范化法）
import java.util.*;

class Solution {
    public List<List<String>> groupStrings(String[] strings) {
        Map<String, List<String>> groups = new HashMap<>();
        
        for (String s : strings) {
            char[] chars = s.toCharArray();
            if (chars.length > 0) {
                // 计算偏移量：将第一个字符变成 'a'
                int offset = chars[0] - 'a';
                for (int i = 0; i < chars.length; i++) {
                    // 对每个字符应用偏移量，并处理循环
                    char originalChar = chars[i];
                    char shiftedChar = (char)(originalChar - offset);
                    if (shiftedChar < 'a') {
                        shiftedChar += 26;
                    }
                    chars[i] = shiftedChar;
                }
            }
            // 使用规范化后的字符串作为 key
            String key = new String(chars);
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(groups.values());
    }
}
（代码及解释参考了LeetCode讨论区和相关解法资料）
--------------------------------------------------------------------------------
🔄 两种方法的对比与选择
这两种方法本质上是殊途同归的，只是在实现上有些不同。
方法生成的Key示例 ("abc" 和 "bcd")优点缺点差值法"bb" 和 "bb"key 非常简洁，长度是 N-1。处理循环简洁优雅，直接在计算差值时用 % 26 解决。对于新手来说，需要多一步转换来理解 key 的含义。规范化法"abc" 和 "abc"更符合直觉：“平移整个字符串”。生成的 key 本身也是一个字符串，更方便调试和查看。key 长度和原始字符串相同，稍长一些。处理循环时需要单独判断 shiftedChar < 'a'。
总的来说，两种方法的时间复杂度和空间复杂度完全相同：
- 时间复杂度: O(N * K)，其中 N 是字符串的数量，K 是字符串的平均长度。我们需要遍历每个字符串的每个字符来生成签名。
- 空间复杂度: O(N * K)，主要用于存储哈希表和最终的返回结果。
在实际编写代码时，我更推荐方法一（差值法），因为它的代码更短、更优雅，且巧妙地利用了循环特性。但其普适性使得规范化法也值得掌握，两种都是面试中的常见解法。
--------------------------------------------------------------------------------
🔥 面试前必看的特殊场景
如果面试官追问一些边界情况，可以准备这样回答：
1.处理循环
- 差值法通过 (diff + 26) % 26 处理，确保所有差值都在 0-25 之间。例如"az"（'z'→'a'）的差值是 (0-25+26)%26=1，而"ba"（'a'→'b'）的差值是 (1-0+26)%26=1，两者相同，实现正确分组。
- 规范化法通过检查结果是否小于 'a'，若是则加 26 处理。
2.处理长度为1的字符串
- 单字符字符串无相邻字符，生成的差值为空字符串；规范化法转化后仍为单字符字符串，所以所有单字符字符串都应被归为一组。因为理论上，它们可以通过26次移位相互转换。
3.一个值得注意的潜在陷阱
- 如果面试官问及“原地修改字符串是否会带来额外复杂性”，可补充说明：两种方法的时间复杂度均为 O(N×K)，空间复杂度 O(N×K)，N 为字符串数量，K 为最大长度。规范化法额外 O(K) 数组用于转化，但规模相当，不影响整体上界。
--------------------------------------------------------------------------------
💎 总结
LeetCode 249 “Group Shifted Strings” 这道题的精髓在于识别不变性，即一个“移位序列”中字符串的字符间相对间距模式是恒定不变的。我们通过两种方法将这个模式提取出来：
- 差值法：将间距模式编码成一个字符串（如"abc"->"bb"）。
- 规范化法：将整个字符串平移成一个统一的基准格式（如"abc"->"abc"）。
掌握“寻找不变性”和“设计独特签名”的思路，你就掌握了高效解决分组问题的利器。
--------------------------------------------------------------------------------
本题还有另一种“静态映射”的解法，但它需要额外的数据结构，通常面试中不推荐。如果你想了解其实现细节，或者想巩固一下代码的完整性，随时告诉我，我来为你进一步讲解～


Refer to Kimi
Here is a clean Java solution for LeetCode 249. Group Shifted Strings.
Approach: Normalization Key
Two strings belong to the same shifting sequence if you can shift every character by the same amount to make them identical.
For each string, we compute a signature by shifting it so that its first character becomes 'a'. All strings that share the same signature belong to the same group.
Example:
- "bcd" → shift back by 1 → "abc"
- "xyz" → shift back by 23 → "abc"
- "az" stays "az"; "ba" → shift back by 1 → "az"
Time Complexity: O(n * L) where n = number of strings, L = max length
Space Complexity: O(n * L)
import java.util.*;

class Solution {
    public List<List<String>> groupStrings(String[] strings) {
        Map<String, List<String>> groups = new HashMap<>();
        
        for (String s : strings) {
            String key = normalize(s);
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        
        return new ArrayList<>(groups.values());
    }
    
    // Shift every char so that the first character becomes 'a'
    private String normalize(String s) {
        int shift = s.charAt(0) - 'a';
        StringBuilder sb = new StringBuilder();
        
        for (char c : s.toCharArray()) {
            // Shift back by 'shift' positions, wrapping around with modulo 26
            char normalized = (char) ((c - 'a' - shift + 26) % 26 + 'a');
            sb.append(normalized);
        }
        
        return sb.toString();
    }
}
Why the +26?
c - 'a' - shift can be negative (e.g., 'a' with shift 1 gives -1). Adding 26 ensures the value is positive before applying % 26, correctly handling the wrap-around from 'a' back to 'z'.
Alternative Signature
You can also use the sequence of adjacent differences as the key:
private String getDiffKey(String s) {
    StringBuilder sb = new StringBuilder();
    for (int i = 1; i < s.length(); i++) {
        int diff = (s.charAt(i) - s.charAt(i - 1) + 26) % 26;
        sb.append(diff).append(',');
    }
    return sb.toString(); // empty for length-1 strings, so they all group together
}
Both approaches are correct; the normalization approach above is slightly more concise.
