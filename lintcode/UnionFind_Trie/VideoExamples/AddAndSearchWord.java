/**
 * Refer to
 * https://leetcode.com/problems/add-and-search-word-data-structure-design/description/
 * http://www.lintcode.com/en/problem/add-and-search-word/
 * 
 * 
 * Solution
 * http://www.jiuzhang.com/solutions/add-and-search-word/
 * http://www.cnblogs.com/grandyang/p/4507286.html
 * 这道题如果做过之前的那道 Implement Trie (Prefix Tree) 实现字典树(前缀树)的话就没有太大的难度了，
 * 还是要用到字典树的结构，唯一不同的地方就是search的函数需要重新写一下，因为这道题里面'.'可以代替任意字符，
 * 所以一旦有了'.'，就需要查找所有的子树，只要有一个返回true，整个search函数就返回true，典型的DFS的问题，
 * 其他部分跟上一道实现字典树没有太大区别
 * 
 * 
 * https://yeqiuquan.blogspot.com/2017/03/lintcode-473-add-and-search-word.html
 * 这题一看就应该想到用Trie来解决。
 * 主要是implement trie要细心。
 * find里面如果碰到"."，就把root下所有的都找一遍。只要里面能return true，就说明能找到。
 * 
 * https://discuss.leetcode.com/topic/40275/trie-tree-java-solution-very-easy-to-understand
 */

// Solution 1: Array + DFS
class TrieNode {
    TrieNode[] children;
    boolean isEnd;
    public TrieNode() {
        this.children = new TrieNode[26];
        this.isEnd = false;
    }
}

public class WordDictionary {
    private TrieNode root;
    
    /** Initialize your data structure here. */
    public WordDictionary() {
        root = new TrieNode();
    }
    
    /** Adds a word into the data structure. */
    public void addWord(String word) {
        TrieNode node = root;
        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int index = c - 'a';
            if(node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.isEnd = true;
    }
    
    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        return find(word, 0, root);
    }
    
    // Using DFS to check if word existing or not
    private boolean find(String word, int index, TrieNode node) {
        if(index == word.length()) {
            return node.isEnd;
        }
        char c = word.charAt(index);
        if(c == '.') {
            for(int i = 0; i < 26; i++) {
                if(node.children[i] != null) {
                    if(find(word, index + 1, node.children[i])) {
                        return true;
                    }
                }
            }
            return false;
        } else if(node.children[c - 'a'] != null) {
            return find(word, index + 1, node.children[c - 'a']);
        } else {
            return false;
        }
    }
}



// Solution 2: HashMap + DFS (Then no limitation on characters between 'a' to 'z')
class TrieNode {
    Map<Character, TrieNode> map;
    boolean isEnd;
    public TrieNode() {
        this.map = new HashMap<Character, TrieNode>();
        this.isEnd = false;
    }
}

public class WordDictionary {
    private TrieNode root;

    /** Initialize your data structure here. */
    public WordDictionary() {
        root = new TrieNode();
    }
    
    /** Adds a word into the data structure. */
    public void addWord(String word) {
        TrieNode node = root;
        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if(!node.map.containsKey(c)) {
                node.map.put(c, new TrieNode());
            }
            node = node.map.get(c);
        }
        node.isEnd = true;
    }
    
    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        return find(word, 0, root);
    }
    
    private boolean find(String word, int index, TrieNode node) {
        if(index == word.length()) {
            return node.isEnd;
        }
        char c = word.charAt(index);
        // If we use HashMap, there is no limitation on characters only 
        // between 'a' - 'z'
        if(node.map.containsKey(c)) {
            return find(word, index + 1, node.map.get(c));
        } else if(c == '.') {
            for(Map.Entry<Character, TrieNode> entry : node.map.entrySet()) {
                if(entry.getValue() != null) {
                    if(find(word, index + 1, entry.getValue())) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            return false;
        }
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */
