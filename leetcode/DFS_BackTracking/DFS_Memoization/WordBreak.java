/**
 Refer to
 https://leetcode.com/problems/word-break/
 Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine 
 if s can be segmented into a space-separated sequence of one or more dictionary words.

Note:
The same word in the dictionary may be reused multiple times in the segmentation.
You may assume the dictionary does not contain duplicate words.
Example 1:
Input: s = "leetcode", wordDict = ["leet", "code"]
Output: true
Explanation: Return true because "leetcode" can be segmented as "leet code".

Example 2:
Input: s = "applepenapple", wordDict = ["apple", "pen"]
Output: true
Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
             Note that you are allowed to reuse a dictionary word.
Example 3:
Input: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
Output: false
*/

// Native DFS (Style 1)
// Refer to
// https://algorithmstuff.wordpress.com/tag/memoization/
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        return helper(s, 0, wordDict);
    }
    
    private boolean helper(String s, int index, List<String> wordDict) {
        if(wordDict.contains(s.substring(index))) {
            return true;
        }
        for(int i = index; i < s.length(); i++) {
            if(wordDict.contains(s.substring(index, i + 1))) {
                if(helper(s, i + 1, wordDict)) {
                    return true;
                }
            }
        }
        return false;
    }
}

// Adding memoization DFS (Style 1)
// Refer to
// https://algorithmstuff.wordpress.com/tag/memoization/
public boolean stringBreak(String s, Set<String> dict) {
    HashSet<Integer> memo = new HashSet<Integer>(); // We just need to keep track of those indexes that we have already computed and the result is false
    return dfs(s, 0, dict, memo);
 }
 boolean dfs(String s, int i, Set<String> dict, HashSet<Integer> memo)
 {
     if(dict.contains(s.substring(i)))
         return true;
     if(memo.contains(i)) // if we have already computed the result for this substring we just return the answer
         return false;
     //otherwise, compute the answer for this substring
     for(int j = i; j < s.length(); ++j)
         if(dict.contains(s.substring(i, j + 1)))
             if(dfs(s, j + 1, dict, set)) return true;
 
     //we just store the results for substrings which result is false
     memo.add(i);
     return false;
 }



// Native DFS (Style 2) -> Better logic and save 1 more parameter
// Refer to
// https://leetcode.com/problems/word-break/discuss/169383/The-Time-Complexity-of-The-Brute-Force-Method-Should-Be-O(2n)-and-Prove-It-Below
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        return helper(s, wordDict);
    }
    
    private boolean helper(String s, List<String> wordDict) {
        if(s.length() == 0) {
            return true;
        }
        for(int i = 1; i <= s.length(); i++) {
            if(wordDict.contains(s.substring(0, i))) {
                if(helper(s.substring(i), wordDict)) {
                    return true;
                }
            }
        }
        return false;
    }
}

// Adding memoization (Style 2)
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> cache = new HashSet<String>();
        return helper(s, wordDict, cache);
    }
    
    private boolean helper(String s, List<String> wordDict, Set<String> cache) {
        if(s.length() == 0) {
            return true;
        }
        if(cache.contains(s)) {
            return false;
        }
        for(int i = 1; i <= s.length(); i++) {
            if(wordDict.contains(s.substring(0, i))) {
                if(helper(s.substring(i), wordDict, cache)) {
                    return true;
                }
            }
        }
        cache.add(s);
        return false;
    }
}

// Adding memoization and optimize with use index only (Style 2)
// Since substring only relate to index (an inteeger), no need to store the string
// in set, just need to store the index
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<Integer> cache = new HashSet<Integer>();
        return helper(s, 0, wordDict, cache);
    }
    
    private boolean helper(String s, int index, List<String> wordDict, Set<Integer> cache) {
        if(s.length() == 0) {
            return true;
        }
        if(cache.contains(index)) {
            return false;
        }
        for(int i = 1; i <= s.length(); i++) {
            if(wordDict.contains(s.substring(0, i))) {
                if(helper(s.substring(i), i, wordDict, cache)) {
                    return true;
                }
            }
        }
        cache.add(index);
        return false;
    }
}



// Adding memoization and store both result to true/false case (Style 2)
// Compare to Adding memoization Style 1
// Refer to
// https://algorithmstuff.wordpress.com/tag/memoization/
// https://leetcode.com/problems/word-break/discuss/43819/DFS-with-Path-Memorizing-Java-Solution/192447
// https://leetcode.com/problems/word-break/discuss/43819/DFS-with-Path-Memorizing-Java-Solution/273463
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        Map<Integer, Boolean> cache = new HashMap<Integer, Boolean>(); // we need to keep track of those indexes that we have already computed and either result is true or false
        return helper(s, 0, wordDict, cache);
    }
    
    private boolean helper(String s, int index, List<String> wordDict, Map<Integer, Boolean> cache) {
        if(s.length() == 0) {
            return true;
        }
        if(cache.containsKey(index)) { // if we have already computed the result for this substring we just return the answer
            return cache.get(index); 
        }
        for(int i = 1; i <= s.length(); i++) {
            if(wordDict.contains(s.substring(0, i))) {
                if(helper(s.substring(i), i, wordDict, cache)) {
                    cache.put(i, true); // we store the results for substrings which DFS result is true
                    return true;
                }
            }
        }
        cache.put(index, false); // we store the results for substrings which DFS result is false
        return false;
    }
}
