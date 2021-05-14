/**
Refer to
https://leetcode.com/problems/find-and-replace-pattern/
You have a list of words and a pattern, and you want to know which words in words matches the pattern.

A word matches the pattern if there exists a permutation of letters p so that after replacing every letter x 
in the pattern with p(x), we get the desired word.

(Recall that a permutation of letters is a bijection from letters to letters: every letter maps to another letter, 
and no two letters map to the same letter.)

Return a list of the words in words that match the given pattern. 

You may return the answer in any order.

Example 1:
Input: words = ["abc","deq","mee","aqq","dkd","ccc"], pattern = "abb"
Output: ["mee","aqq"]
Explanation: "mee" matches the pattern because there is a permutation {a -> m, b -> e, ...}. 
"ccc" does not match the pattern because {a -> c, b -> c, ...} is not a permutation,
since a and b map to the same letter.

Note:
1 <= words.length <= 50
1 <= pattern.length = words[i].length <= 20
*/

// Solution 1: Two map one on one
// Refer to
// https://leetcode.com/problems/find-and-replace-pattern/solution/
/**
Approach 1: Two Maps
Intuition and Algorithm

If say, the first letter of the pattern is "a", and the first letter of the word is "x", then in the permutation, "a" must map to "x".

We can write this bijection using two maps: a forward map m1 and a backwards map m2.

m1:"a"→"x" 
m2:"x"→"a"

Then, if there is a contradiction later, we can catch it via one of the two maps. For example, if the (word, pattern) is ("aa", "xy"), 
we will catch the mistake in m1("a")="x"="y". Similarly, with (word, pattern) = ("ab", "xx"), we will catch the mistake in m2.
*/
class Solution {
    public List<String> findAndReplacePattern(String[] words, String pattern) {
        List<String> result = new ArrayList<String>();
        for(String word : words) {
            if(match(word, pattern)) {
                result.add(word);
            }
        }
        return result;
    }
    
    private boolean match(String word, String pattern) {
        Map<Character, Character> m1 = new HashMap<Character, Character>();
        Map<Character, Character> m2 = new HashMap<Character, Character>();
        for(int i = 0; i < word.length(); i++) {
            char w = word.charAt(i);
            char p = pattern.charAt(i);
            // Because of one on one mapping, should only update
            // map for the 1st time encounter the mapping, if
            // encounter again don't update, because one character
            // should not map to two different characters
            if(!m1.containsKey(w)) {
                m1.put(w, p);
            }
            if(!m2.containsKey(p)) {
                m2.put(p, w);
            }
            if(m1.get(w) != p || m2.get(p) != w) {
                return false;
            }
        }
        return true;
    }
}
