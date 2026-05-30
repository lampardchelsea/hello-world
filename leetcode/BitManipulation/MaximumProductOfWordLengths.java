https://leetcode.com/problems/maximum-product-of-word-lengths/description/
Given a string array words, return the maximum value of length(word[i]) * length(word[j]) where the two words do not share common letters. If no such two words exist, return 0.
 
Example 1:
Input: words = ["abcw","baz","foo","bar","xtfn","abcdef"]
Output: 16
Explanation: The two words can be "abcw", "xtfn".

Example 2:
Input: words = ["a","ab","abc","d","cd","bcd","abcd"]
Output: 4
Explanation: The two words can be "ab", "cd".

Example 3:
Input: words = ["a","aa","aaa","aaaa"]
Output: 0
Explanation: No such pair of words.
 
Constraints:
- 2 <= words.length <= 1000
- 1 <= words[i].length <= 1000
- words[i] consists only of lowercase English letters.
--------------------------------------------------------------------------------
Attempt 1: 2026-05-25
Solution 1: Bit Manipulation (10 min)
class Solution {
    public int maxProduct(String[] words) {
        int n = words.length;
        int[] masks = new int[n];
        int[] lens = new int[n];
        for(int i = 0; i < n; i++) {
            String s = words[i];
            int mask = 0;
            for(char c : s.toCharArray()) {
                mask |= 1 << (c - 'a');
            }
            masks[i] = mask;
            lens[i] = s.length();
        }
        int maxProd = 0;
        for(int i = 0; i < n; i++) {
            for(int j = i + 1; j < n; j++) {
                if((masks[i] & masks[j]) == 0) {
                    maxProd = Math.max(maxProd, lens[i] * lens[j]);
                }
            }
        }
        return maxProd;
    }
}

Time: O(n^2 + L) where L is total characters across all words.
Space: O(n) for masks and lengths.


Refer to Deepseek
Here's a Java solution for LeetCode 318 "Maximum Product of Word Lengths" using bitmasking to efficiently check if two words share any common letters.
class Solution {
    public int maxProduct(String[] words) {
        int n = words.length;
        int[] masks = new int[n];
        int[] lens = new int[n];
        
        // Precompute bitmask for each word
        for (int i = 0; i < n; i++) {
            String word = words[i];
            int mask = 0;
            for (char c : word.toCharArray()) {
                mask |= 1 << (c - 'a');
            }
            masks[i] = mask;
            lens[i] = word.length();
        }
        
        int maxProd = 0;
        // Compare all pairs
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if ((masks[i] & masks[j]) == 0) {
                    maxProd = Math.max(maxProd, lens[i] * lens[j]);
                }
            }
        }
        return maxProd;
    }
}
🧠 How it works
1.Bitmask representation
Each word is converted to a 26‑bit integer:
'a' → bit 0, 'b' → bit 1, …, 'z' → bit 25.
Example: "abc" → 111 (binary) = 7.
2.Check no common letters
(mask[i] & mask[j]) == 0 means the two masks have no overlapping bits → no shared letters.
3.Compute product
If condition holds, update maxProd with len[i] * len[j].
⏱ Complexity
- Time: O(n^2 + L) where L is total characters across all words.
- Space: O(n) for masks and lengths.
💡 Optimization (optional)
If words have the same mask, keep only the longest length, because a shorter word with the same mask can never produce a larger product. This reduces n in practice.
Implementation:
Map<Integer, Integer> bestLen = new HashMap<>();
for (int i = 0; i < n; i++) {
    int m = masks[i];
    bestLen.put(m, Math.max(bestLen.getOrDefault(m, 0), lens[i]));
}
// Then iterate over keys of bestLen
But for n ≤ 1000, the simple double loop is sufficient.
--------------------------------------------------------------------------------
Example
words = ["abcw","baz","foo","bar","xtfn","abcdef"]
Masks:
- "abcw" → bits for a,b,c,w
- "baz" → a,b,z (overlap with previous)
- "xtfn" → x,t,f,n → no overlap with "abcw" → product 4*4=16 → answer.
This solution passes all LeetCode test cases.




Refer to



