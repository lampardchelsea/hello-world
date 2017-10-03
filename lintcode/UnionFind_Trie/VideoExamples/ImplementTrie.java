/**
 * Refer to
 * http://www.lintcode.com/en/problem/implement-trie/
 * https://leetcode.com/problems/implement-trie-prefix-tree/description/
 * 
 *
 * Solution
 * https://aaronice.gitbooks.io/lintcode/content/trie/implement_trie.html
 * https://www.jiuzhang.com/solution/implement-trie/ 
 *
 * Java Implementation 1
 * Each trie node can only contains 'a'-'z' characters. So we can use a 
 * small array to store the character.+
 * 
 * Java Implementation 2
 * A trie node should contains the character, its children and the flag that 
 * marks if it is a leaf node. You can use this diagram to walk though the 
 * Java solution.
 */

// Solution 1: Array store characters version
class TrieNode {
    // Initialize your data structure here.
    TrieNode[] array;
    boolean isEnd;
    public TrieNode() {
        this.array = new TrieNode[26];
        this.isEnd = false;
    }
}

public class Trie {
    private TrieNode root;

    public Trie() {
        // do intialization if necessary
        root = new TrieNode();
    }

    /*
     * @param word: a word
     * @return: nothing
     */
    public void insert(String word) {
        TrieNode p = root;
        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int index = c - 'a';
            if(p.array[index] == null) {
                TrieNode node = new TrieNode();
                p.array[index] = node;
                p = node;
            } else {
                p = p.array[index];
            }
        }
        p.isEnd = true;
    }

    /*
     * @param word: A string
     * @return: if the word is in the trie.
     */
    public boolean search(String word) {
        TrieNode node = searchNode(word);
        if(node == null) {
            return false;
        } else {
            if(node.isEnd) {
                return true;
            }
        }
        return false;
    }

    /*
     * @param prefix: A string
     * @return: if there is any word in the trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
        TrieNode node = searchNode(prefix);
        if(node == null) {
            return false;
        } else {
            return true;
        }
    }
    
    private TrieNode searchNode(String s) {
        TrieNode p = root;
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int index = c - 'a';
            if(p.array[index] == null) {
                return null;
            } else {
                p = p.array[index];
            }
        }
        return p;
    }
}


// Solution 2: 







