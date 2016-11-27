/**
 * Implement a trie with insert, search, and startsWith methods.
 * Note:
 * You may assume that all inputs are consist of lowercase letters a-z.
*/
// Solution 1: Refine Princeton TrieST.java
// Refer to 
// http://algs4.cs.princeton.edu/52trie/TrieST.java.html
import java.util.LinkedList;
import java.util.Queue;

/**
 * Instead of Princeton TrieST.java declare an nested class,
 * leetcode define another class for TrieNode
 */
class TrieNode {
    // For 'a' to 'z', we only need each node contain array 
    // to store 26 letters
    static int R = 26;
	public boolean isLeaf;
	public TrieNode[] next;
    
    // Initialize your data structure here.
    public TrieNode() {
        this.isLeaf = false;
        this.next = new TrieNode[R];
    }
}

public class Trie {
    private TrieNode root;
    
    // For 'a' to 'z', we only need each node contain array 
    // to store 26 letters
    private static final int lower_case_no = 26;
    
    public Trie() {
        root = new TrieNode();
    }

    // Inserts a word into the trie.
    public void insert(String word) {
    	// Call same TrieST.java put() method, but
    	// no need to get its return value
        put(root, word, 0);
    }

    public TrieNode put(TrieNode x, String word, int d) {
        if(x == null) {
            x = new TrieNode();
        }
        if(d == word.length()) {
            x.isLeaf = true;
            return x;
        }
        
        // Same way as TrieST.java, but as not create array
        // directly based on extended ASCII table(256), need
        // to convert character index into 0 initialization
        // array, which implement by (- 'a')
        int c = (int) (word.charAt(d) - 'a');
        x.next[c] = put(x.next[c], word, d + 1);
        return x;
    }


    // Returns if the word is in the trie.
    public boolean search(String word) {
    	// Tricky part, as get(String word) may return null,
    	// if no handling on null case will throw out
    	// NullPointerException
    	if(get(word) == null) {
    		return false;
    	} else {
    		// The final decision for search is not like TrieST.java
    		// which make judgment based on leaf node's value exist
    		// or not, its directly based on whether it is leaf node,
    		// especially when we initial this trie we haven't assign
    		// value to word's last character node, instead we define
    		// "isLeaf" to tag whether the word ends
            return get(word).isLeaf;
    	}
    }
    
    public TrieNode get(String word) {
    	TrieNode x = get(root, word, 0);
    	if(x == null) {
    		return null;
    	}
    	return x;
    }

    // Same way as TrieST.java get(Node x, String key, int d) method
    public TrieNode get(TrieNode x, String word, int d) {
        if(x == null) {
            return null;
        }
        if(d == word.length()) {
            return x;
        }
        int c = (int) (word.charAt(d) - 'a');
        return get(x.next[c], word, d + 1);
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        Queue<String> results = new LinkedList<String>();
        // Get the node represent prefix and use it as basement to find words
        // e.g if trie contains "ab", now search word prefix as "a", then
        // x will be the node represent "a"(isLeaf = false, next[1])
        TrieNode x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), results);
        if(results.size() != 0) {
            return true;
        }
        return false;
    }
    
    public void collect(TrieNode x, StringBuilder prefix, Queue<String> results) {
        if(x == null) {
            return;
        }
        // When we can find a node is leaf node of current word(word ends), 
        // which means we find the word, add word into results
        if(x.isLeaf) {
            results.add(prefix.toString());
        }
        // If not reach the end of word, continuously append the word based on prefix
        for(int c = 0; c < lower_case_no; c++) {
            prefix.append((char)(c + 'a'));
            collect(x.next[c], prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }
    
    // Additional testing case provide by leetcode,
    // insert "ab" onto trie, and make judgement on 
    // search "a" and startsWith "a"
    public static void main(String[] args) {
        Trie st = new Trie();
        st.insert("ab");
        boolean search = st.search("a");
        boolean startsWith = st.startsWith("a");
        System.out.println(search + "," + startsWith);
    }
}

// Your Trie object will be instantiated and called as such:
// Trie trie = new Trie();
// trie.insert("somestring");
// trie.search("key");



// Solution 2: 
// http://www.programcreek.com/2014/05/leetcode-implement-trie-prefix-tree-java/
