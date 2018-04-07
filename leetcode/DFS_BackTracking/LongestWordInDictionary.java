/**
 * Refer to
 * https://leetcode.com/problems/longest-word-in-dictionary/description/
 * Given a list of strings words representing an English Dictionary, find the longest word in words 
   that can be built one character at a time by other words in words. If there is more than one 
   possible answer, return the longest word with the smallest lexicographical order.

    If there is no answer, return the empty string.
    Example 1:
    Input: 
    words = ["w","wo","wor","worl", "world"]
    Output: "world"
    Explanation: 
    The word "world" can be built one character at a time by "w", "wo", "wor", and "worl".
    Example 2:
    Input: 
    words = ["a", "banana", "app", "appl", "ap", "apply", "apple"]
    Output: "apple"
    Explanation: 
    Both "apply" and "apple" can be built from other words in the dictionary. However, "apple" is lexicographically smaller than "apply".
    Note:

    All the strings in the input will only contain lowercase letters.
    The length of words will be in the range [1, 1000].
    The length of words[i] will be in the range [1, 30].
 * 
 * Solution
 * https://leetcode.com/problems/longest-word-in-dictionary/discuss/109118/Java-solution-with-Trie/113861
*/ 

class Solution {
    public String longestWord(String[] words) {
        if(words == null || words.length == 0) {
            return "";
        }
        Trie trie = new Trie();
        for(int i = 0; i < words.length; i++) {
            trie.insert(words[i]);
        }
        return dfs(trie.root);
    }
    
    // The only special method need to focus on, DFS to find longest word
    private String dfs(TrieNode root) {
        String longest = "";
        for(int i = 0; i < root.array.length; i++) {
            if(root.array[i] != null && root.array[i].isEnd) {
                String cur = (char)('a' + i) + dfs(root.array[i]);
                if(cur.length() > longest.length()) {
                    longest = cur;
                }
            }
        }
        return longest;
    }
    
}

class Trie {
    public TrieNode root;
    public Trie() {
        root = new TrieNode();
    }
    public void insert(String s) {
        TrieNode p = root;
        for(int i = 0; i < s.length(); i++) {
            int index = s.charAt(i) - 'a';
            if(p.array[index] == null) {
                p.array[index] = new TrieNode();
            }
            p = p.array[index];
        }
        p.isEnd = true;
    }
    public boolean startWith(String s) {
        TrieNode node = searchNode(s);
        if(node == null) {
            return false;
        }
        return true;
    }
    public boolean search(String s) {
        TrieNode node = searchNode(s);
        if(node != null && node.isEnd) {
            return true;
        }
        return false;
    }
    public TrieNode searchNode(String s) {
        TrieNode p = root;
        for(int i = 0; i < s.length(); i++) {
            int index = s.charAt(i) - 'a';
            if(p.array[index] == null) {
                return null;
            } else {
                p = p.array[index];
            }
        }
        return p;
    }
}

class TrieNode {
    TrieNode[] array;
    boolean isEnd;
    public TrieNode() {
        this.isEnd = false;
        this.array = new TrieNode[26];
    }
}
