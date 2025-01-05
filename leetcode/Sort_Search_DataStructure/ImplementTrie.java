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



// Solution 2: Not Use Recursive
// http://www.programcreek.com/2014/05/leetcode-implement-trie-prefix-tree-java/
// Java Solution 2 - Improve Performance by Using an Array
// Each trie node can only contains 'a'-'z' characters. So we can use a small array to store the character.
class TrieNode {
    TrieNode[] arr;
    boolean isEnd;
    // Initialize your data structure here.
    public TrieNode() {
        this.arr = new TrieNode[26];
    }
 
}
 
public class Trie {
    private TrieNode root;
 
    public Trie() {
        root = new TrieNode();
    }
 
    // Inserts a word into the trie.
    public void insert(String word) {
        TrieNode p = root;
        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);
            int index = c-'a';
            if(p.arr[index]==null){
                TrieNode temp = new TrieNode();
                p.arr[index]=temp;
                p = temp;
            }else{
                p=p.arr[index];
            }
        }
        p.isEnd=true;
    }
 
    // Returns if the word is in the trie.
    public boolean search(String word) {
        TrieNode p = searchNode(word);
        if(p==null){
            return false;
        }else{
            if(p.isEnd)
                return true;
        }
 
        return false;
    }
 
    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        TrieNode p = searchNode(prefix);
        if(p==null){
            return false;
        }else{
            return true;
        }
    }
 
    public TrieNode searchNode(String s){
        TrieNode p = root;
        for(int i=0; i<s.length(); i++){
            char c= s.charAt(i);
            int index = c-'a';
            if(p.arr[index]!=null){
                p = p.arr[index];
            }else{
                return null;
            }
        }
 
        if(p==root)
            return null;
 
        return p;
    }
}

// Solution 3: Best Explaination Of Different Case Need To Handle
// Refer to
// http://www.ideserve.co.in/learn/trie-insert-and-search
// http://www.ideserve.co.in/learn/trie-delete
/******************************************************************************
 *  Compilation:  javac TrieST.java
 *  Execution:    java TrieST < words.txt
 *  Dependencies: StdIn.java
 *  Data files:   http://algs4.cs.princeton.edu/52trie/shellsST.txt
 *
 *  A string symbol table for extended ASCII strings, implemented
 *  using a 256-way trie.
 *
 *  % java TrieST < shellsST.txt 
 *  by 4
 *  sea 6
 *  sells 1
 *  she 0
 *  shells 3
 *  shore 7
 *  the 5
 *
 ******************************************************************************/

/**
 *  The {@code TrieST} class represents an symbol table of key-value
 *  pairs, with string keys and generic values.
 *  It supports the usual <em>put</em>, <em>get</em>, <em>contains</em>,
 *  <em>delete</em>, <em>size</em>, and <em>is-empty</em> methods.
 *  It also provides character-based methods for finding the string
 *  in the symbol table that is the <em>longest prefix</em> of a given prefix,
 *  finding all strings in the symbol table that <em>start with</em> a given prefix,
 *  and finding all strings in the symbol table that <em>match</em> a given pattern.
 *  A symbol table implements the <em>associative array</em> abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike {@link java.util.Map}, this class uses the convention that
 *  values cannot be {@code null}—setting the
 *  value associated with a key to {@code null} is equivalent to deleting the key
 *  from the symbol table.
 *  <p>
 *  This implementation uses a 256-way trie.
 *  The <em>put</em>, <em>contains</em>, <em>delete</em>, and
 *  <em>longest prefix</em> operations take time proportional to the length
 *  of the key (in the worst case). Construction takes constant time.
 *  The <em>size</em>, and <em>is-empty</em> operations take constant time.
 *  Construction takes constant time.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/52trie">Section 5.2</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class TrieST<Value> {
    private static final int R = 256;        // extended ASCII


    private Node root;      // root of trie
    private int n;          // number of keys in trie

    // R-way trie node
    private static class Node {
        private Object val;
        private Node[] next = new Node[R];
    }

   /**
     * Initializes an empty string symbol table.
     */
    public TrieST() {
    }


    /**
     * Returns the value associated with the given key.
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     *     and {@code null} if the key is not in the symbol table
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }

    /**
     * Does this symbol table contain the given key?
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key} and
     *     {@code false} otherwise
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public boolean contains(String key) {
        return get(key) != null;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c], key, d+1);
    }

    /**
     * Inserts the key-value pair into the symbol table, overwriting the old value
     * with the new value if the key is already in the symbol table.
     * If the value is {@code null}, this effectively deletes the key from the symbol table.
     * @param key the key
     * @param val the value
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public void put(String key, Value val) {
        if (val == null) delete(key);
        else root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            if (x.val == null) n++;
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, val, d+1);
        return x;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return n;
    }

    /**
     * Is this symbol table empty?
     * @return {@code true} if this symbol table is empty and {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns all keys in the symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     * @return all keys in the symbol table as an {@code Iterable}
     */
    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    /**
     * Returns all of the keys in the set that start with {@code prefix}.
     * @param prefix the prefix
     * @return all of the keys in the set that start with {@code prefix},
     *     as an iterable
     */
    public Iterable<String> keysWithPrefix(String prefix) {
        PrincetonQueue<String> results = new PrincetonQueue<String>();
        Node x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), results);
        return results;
    }

    private void collect(Node x, StringBuilder prefix, PrincetonQueue<String> results) {
        if (x == null) return;
        if (x.val != null) results.enqueue(prefix.toString());
        for (char c = 0; c < R; c++) {
            prefix.append(c);
            collect(x.next[c], prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    /**
     * Returns all of the keys in the symbol table that match {@code pattern},
     * where . symbol is treated as a wildcard character.
     * @param pattern the pattern
     * @return all of the keys in the symbol table that match {@code pattern},
     *     as an iterable, where . is treated as a wildcard character.
     */
    public Iterable<String> keysThatMatch(String pattern) {
    	PrincetonQueue<String> results = new PrincetonQueue<String>();
        collect(root, new StringBuilder(), pattern, results);
        return results;
    }

    private void collect(Node x, StringBuilder prefix, String pattern, PrincetonQueue<String> results) {
        if (x == null) return;
        int d = prefix.length();
        if (d == pattern.length() && x.val != null)
            results.enqueue(prefix.toString());
        if (d == pattern.length())
            return;
        char c = pattern.charAt(d);
        if (c == '.') {
            for (char ch = 0; ch < R; ch++) {
                prefix.append(ch);
                collect(x.next[ch], prefix, pattern, results);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
        else {
            prefix.append(c);
            collect(x.next[c], prefix, pattern, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    /**
     * Returns the string in the symbol table that is the longest prefix of {@code query},
     * or {@code null}, if no such string.
     * @param query the query string
     * @return the string in the symbol table that is the longest prefix of {@code query},
     *     or {@code null} if no such string
     * @throws NullPointerException if {@code query} is {@code null}
     */
    public String longestPrefixOf(String query) {
        int length = longestPrefixOf(root, query, 0, -1);
        if (length == -1) return null;
        else return query.substring(0, length);
    }

    // returns the length of the longest string key in the subtrie
    // rooted at x that is a prefix of the query string,
    // assuming the first d character match and we have already
    // found a prefix match of given length (-1 if no such match)
    private int longestPrefixOf(Node x, String query, int d, int length) {
        if (x == null) return length;
        if (x.val != null) length = d;
        if (d == query.length()) return length;
        char c = query.charAt(d);
        return longestPrefixOf(x.next[c], query, d+1, length);
    }

    /**
     * Removes the key from the set if the key is present.
     * @param key the key
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public void delete(String key) {
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) {
            if (x.val != null) n--;
            x.val = null;
        }
        else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d+1);
        }

        // remove subtrie rooted at x if it is completely empty
        if (x.val != null) return x;
        for (int c = 0; c < R; c++)
            if (x.next[c] != null)
                return x;
        return null;
    }

    /**
     * Unit tests the {@code TrieST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        // build symbol table from standard input
        TrieST<Integer> st = new TrieST<Integer>();
//        for (int i = 0; !StdIn.isEmpty(); i++) {
//            String key = StdIn.readString();
//            st.put(key, i);
//        }

        st.put("by", 4);
        st.put("sea", 6);
        st.put("sells", 1);
        st.put("she", 0);
        st.put("shells", 3);
        st.put("shore", 7);
        st.put("the", 5);
        st.put("seat", 8);
        st.put("and", 2);
        st.put("andy", 10);
        st.put("by", 11);
        st.put("real", 12);
        st.put("rear", 13);

        // print results
        if (st.size() < 100) {
            StdOut.println("keys(\"\"):");
            for (String key : st.keys()) {
                StdOut.println(key + " " + st.get(key));
            }
            StdOut.println();
        }
        
	// Different kind of delete can refer to below explanation
        //st.delete("sea");
        //st.delete("and");
        st.delete("andy");
        
        StdOut.println("-----------------");
        
        if (st.size() < 100) {
            StdOut.println("keys(\"\"):");
            for (String key : st.keys()) {
                StdOut.println(key + " " + st.get(key));
            }
            StdOut.println();
        }
        
        st.keysWithPrefix("rea");
        
        
        StdOut.println("longestPrefixOf(\"shellsort\"):");
        StdOut.println(st.longestPrefixOf("shellsort"));
        StdOut.println();

        StdOut.println("longestPrefixOf(\"quicksort\"):");
        StdOut.println(st.longestPrefixOf("quicksort"));
        StdOut.println();

        StdOut.println("keysWithPrefix(\"shor\"):");
        for (String s : st.keysWithPrefix("shor"))
            StdOut.println(s);
        StdOut.println();

        StdOut.println("keysThatMatch(\".he.l.\"):");
        for (String s : st.keysThatMatch(".he.l."))
            StdOut.println(s);
    }
}

/**
 http://www.ideserve.co.in/learn/trie-delete
 In the previous post, we have seen how can we insert and retrieve keys for trie data structure. 
 In this post, we will discuss how to delete keys from trie.
 
 Background: A trie is a data structure used for efficient retrieval of data associated with keys. 
 If key is of length n, then using trie, worst case time complexity for searching the record associated 
 with this key is O(n). Insertion and deletion of (key, record) pair also takes O(n) time in worst case.

 You can see in the below picture as to how a trie data structure looks like for key, value pairs ("abc",1),
 ("xy",2),("xyz",5),("abb",9)("xyzb",8), ("word",5).
                       
		       Root
		     /   |   \
                    a    w    x
                   /     |      \
		  b      o       y 2 
	        /   \    |        \
	      b 9   c 1  r         z 5
	                 |          \
			 d 5         b 8
	       
 You might want to visit previous post for more details about trie data structure,constructing a trie, 
 insertion and search algorithms, its comparison with other data structures.

 Algorithm requirements for deleting key 'k':
 1. If key 'k' is not present in trie, then we should not modify trie in any way.
 2. If key 'k' is not a prefix nor a suffix of any other key and nodes of key 'k' are not part of any other 
    key then all the nodes starting from root node(excluding root node) to leaf node of key 'k' should be 
    deleted. For example, in the above trie if we were asked to delete key - "word", then nodes 'w','o','r',
    'd' should be deleted.
 3. If key 'k' is a prefix of some other key, then leaf node corresponding to key 'k' should be marked as 
    'not a leaf node'. No node should be deleted in this case. For example, in the above trie if we have 
     to delete key - "xyz", then without deleting any node we have to simply mark node 'z' as 'not a l
     eaf node' and change its value to "NON_VALUE"
 4. If key 'k' is a suffix of some other key 'k1', then all nodes of key 'k' which are not part of key 'k1' 
    should be deleted.
    For example, in the above trie if we were to delete key - "xyzb", then we should only delete node "b" 
    of key "xyzb" since other nodes of this key are also part of key "xyz".
 5. If key 'k' is not a prefix nor a suffix of any other key but some nodes of key 'k' are shared with some 
    other key 'k1', then nodes of key 'k' which are not common to any other key should be deleted and shared 
    nodes should be kept intact. For example, in the above trie if we have to delete key "abc" which shares 
    node 'a', node 'b' with key "abb", then the algorithm should delete only node 'c' of key "abc" and should 
    not delete node 'a' and node 'b'. 
*/
















































































https://leetcode.com/problems/implement-trie-prefix-tree/
A trie (pronounced as "try") or prefix tree is a tree data structure used to efficiently store and retrieve keys in a dataset of strings. There are various applications of this data structure, such as autocomplete and spellchecker.
Implement the Trie class:
- Trie() Initializes the trie object.
- void insert(String word) Inserts the string word into the trie.
- boolean search(String word) Returns true if the string word is in the trie (i.e., was inserted before), and false otherwise.
- boolean startsWith(String prefix) Returns true if there is a previously inserted string word that has the prefix prefix, and false otherwise.
 
Example 1:
Input
["Trie", "insert", "search", "search", "startsWith", "insert", "search"][[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
Output
[null, null, true, false, true, null, true]
Explanation
Trie trie = new Trie();
trie.insert("apple");
trie.search("apple");   // return True
trie.search("app");     // return False
trie.startsWith("app"); // return True
trie.insert("app");
trie.search("app");     // return True
 
Constraints:
- 1 <= word.length, prefix.length <= 2000
- word and prefix consist only of lowercase English letters.
- At most 3 * 10^4 calls in total will be made to insert, search, and startsWith.
--------------------------------------------------------------------------------
Attempt 1: 2022-12-14
Solution 1:  Implement TrieNode with array (10 min)
class Trie { 
    private TrieNode root; 
    public Trie() { 
        root = new TrieNode(); 
    } 
     
    public void insert(String word) { 
        TrieNode cur = root; 
        for(int i = 0; i < word.length(); i++) { 
            int index = word.charAt(i) - 'a'; 
            if(cur.children[index] == null) { 
                TrieNode node = new TrieNode(); 
                cur.children[index] = node; 
                cur = node; 
            } else { 
                cur = cur.children[index]; 
            } 
        } 
        cur.isEnd = true; 
    } 
     
    private TrieNode searchPrefix(String word) { 
        TrieNode cur = root; 
        for(int i = 0; i < word.length(); i++) { 
            int index = word.charAt(i) - 'a'; 
            if(cur.children[index] == null) { 
                return null; 
            } else { 
                cur = cur.children[index]; 
            } 
        } 
        return cur; 
    } 

    public boolean search(String word) { 
        TrieNode node = searchPrefix(word); 
        if(node == null) { 
            return false; 
        } else { 
            if(node.isEnd) { 
                return true; 
            } 
        } 
        return false; 
    } 
     
    // The approach is very similar to the one we used for searching  
    // a word in a trie. We traverse the trie from the root, till there  
    // are no characters left in word prefix or it is impossible to  
    // continue the path in the trie with the current word character.  
    // The only difference with the mentioned above search for a word  
    // algorithm is that when we come to an end of the word prefix,  
    // we always return true. We don't need to consider the isEnd mark  
    // of the current trie node, because we are searching for a prefix  
    // of a word, not for a whole word. 
    public boolean startsWith(String prefix) { 
        return searchPrefix(prefix) != null; 
    } 
}

class TrieNode { 
    TrieNode[] children; 
    boolean isEnd; 
    public TrieNode() { 
        this.children = new TrieNode[26]; 
        this.isEnd = false; 
    } 
} 
/** 
 * Your Trie object will be instantiated and called as such: 
 * Trie obj = new Trie(); 
 * obj.insert(word); 
 * boolean param_2 = obj.search(word); 
 * boolean param_3 = obj.startsWith(prefix); 
 */

Solution 2:  Implement TrieNode with HashMap (10 min)
class Trie { 
    private TrieNode root; 
    public Trie() { 
        root = new TrieNode(); 
    } 
     
    public void insert(String word) { 
        TrieNode cur = root; 
        for(int i = 0; i < word.length(); i++) { 
            char c = word.charAt(i); 
            if(!cur.children.containsKey(c)) { 
                TrieNode node = new TrieNode(); 
                cur.children.put(c, node); 
                cur = node;                 
            } else { 
                cur = cur.children.get(c); 
            } 
        } 
        cur.isEnd = true; 
    } 
     
    private TrieNode searchPrefix(String word) { 
        TrieNode cur = root; 
        for(int i = 0; i < word.length(); i++) { 
            char c = word.charAt(i); 
            if(!cur.children.containsKey(c)) { 
                return null; 
            } else { 
                cur = cur.children.get(c); 
            } 
        } 
        return cur; 
    } 
    
    public boolean search(String word) { 
        TrieNode node = searchPrefix(word); 
        if(node == null) { 
            return false; 
        } else { 
            if(node.isEnd) { 
                return true; 
            } 
        } 
        return false; 
    } 
     
    // The approach is very similar to the one we used for searching  
    // a word in a trie. We traverse the trie from the root, till there  
    // are no characters left in word prefix or it is impossible to  
    // continue the path in the trie with the current word character.  
    // The only difference with the mentioned above search for a word  
    // algorithm is that when we come to an end of the word prefix,  
    // we always return true. We don't need to consider the isEnd mark  
    // of the current trie node, because we are searching for a prefix  
    // of a word, not for a whole word. 
    public boolean startsWith(String prefix) { 
        return searchPrefix(prefix) != null; 
    } 
} 

class TrieNode { 
    Map<Character, TrieNode> children; 
    boolean isEnd; 
    public TrieNode() { 
        this.children = new HashMap<Character, TrieNode>(); 
        this.isEnd = false; 
    } 
} 
/** 
 * Your Trie object will be instantiated and called as such: 
 * Trie obj = new Trie(); 
 * obj.insert(word); 
 * boolean param_2 = obj.search(word); 
 * boolean param_3 = obj.startsWith(prefix); 
 */

Refer to
https://leetcode.com/problems/implement-trie-prefix-tree/solutions/127843/implement-trie-prefix-tree/
Summary
This article is for intermediate level users. It introduces the following ideas: The data structure Trie (Prefix tree) and most common operations with it.
Solution
Applications
Trie (we pronounce "try") or prefix tree is a tree data structure, which is used for retrieval of a key in a dataset of strings. There are various applications of this very efficient data structure such as :

1. Autocomplete

Figure 1. Google Suggest in action.

2. Spell checker

Figure 2. A spell checker used in word processor.

3. IP routing (Longest prefix matching)

Figure 3. Longest prefix matching algorithm uses Tries in Internet Protocol (IP) routing to select an entry from a forwarding table.

4. T9 predictive text

Figure 4. T9 which stands for Text on 9 keys, was used on phones to input texts during the late 1990s.

5. Solving word games

Figure 5. Tries is used to solve Boggle efficiently by pruning the search space.

There are several other data structures, like balanced trees and hash tables, which give us the possibility to search for a word in a dataset of strings. Then why do we need Trie ? Although hash table has O(1) time complexity for looking for a key, it is not efficient in the following operations :
- Finding all keys with a common prefix.
- Enumerating a dataset of strings in lexicographical order.
Another reason why Trie outperforms hash table, is that as hash table increases in size, there are lots of hash collisions and the search time complexity could deteriorate to O(n), where n is the number of keys inserted. Trie could use less space compared to Hash Table when storing many keys with the same prefix. In this case using Trie has only O(m) time complexity, where m is the key length. Searching for a key in a balanced tree costs O(mlog⁡n) time complexity.

Trie node structure
Trie is a rooted tree. Its nodes have the following fields:
- Maximum of R links to its children, where each link corresponds to one of R character values from dataset alphabet. In this article we assume that R is 26, the number of lowercase latin letters.
- Boolean field which specifies whether the node corresponds to the end of the key, or is just a key prefix.

Figure 6. Representation of a key "leet" in Trie.

Java
class TrieNode { 
    // R links to node children 
    private TrieNode[] links; 
    private final int R = 26; 
    private boolean isEnd; 
    public TrieNode() { 
        links = new TrieNode[R]; 
    } 
    public boolean containsKey(char ch) { 
        return links[ch -'a'] != null; 
    } 
    public TrieNode get(char ch) { 
        return links[ch -'a']; 
    } 
    public void put(char ch, TrieNode node) { 
        links[ch -'a'] = node; 
    } 
    public void setEnd() { 
        isEnd = true; 
    } 
    public boolean isEnd() { 
        return isEnd; 
    } 
}
Two of the most common operations in a Trie are insertion of a key and search for a key.

Insertion of a key to a Trie
We insert a key by searching into the Trie. We start from the root and search a link, which corresponds to the first key character. There are two cases :
- A link exists. Then we move down the tree following the link to the next child level. The algorithm continues with searching for the next key character.
- A link does not exist. Then we create a new node and link it with the parent's link matching the current key character. We repeat this step until we encounter the last character of the key, then we mark the current node as an end node and the algorithm finishes.

Figure 7. Insertion of keys into a Trie.

Java
class Trie { 
    private TrieNode root; 
    public Trie() { 
        root = new TrieNode(); 
    } 
    // Inserts a word into the trie. 
    public void insert(String word) { 
        TrieNode node = root; 
        for (int i = 0; i < word.length(); i++) { 
            char currentChar = word.charAt(i); 
            if (!node.containsKey(currentChar)) { 
                node.put(currentChar, new TrieNode()); 
            } 
            node = node.get(currentChar); 
        } 
        node.setEnd(); 
    } 
}
Complexity Analysis
- Time complexity : O(m), where m is the key length.
In each iteration of the algorithm, we either examine or create a node in the Trie till we reach the end of the key. This takes only m operations.
- Space complexity : O(m).
In the worst case newly inserted key doesn't share a prefix with the the keys already inserted in the Trie. We have to add m new nodes, which takes us O(m) space.

Search for a key in a Trie
Each key is represented in the Trie as a path from the root to the internal node or leaf. We start from the root with the first key character. We examine the current node for a link corresponding to the key character. There are two cases :
- A link exist. We move to the next node in the path following this link, and proceed searching for the next key character.
- A link does not exist. If there are no available key characters and current node is marked as isEnd we return true. Otherwise there are possible two cases in each of them we return false :
- There are key characters left, but it is impossible to follow the key path in the Trie, and the key is missing.
- No key characters left, but current node is not marked as isEnd. Therefore the search key is only a prefix of another key in the Trie.


Figure 8. Search for a key in a Trie.

Java
class Trie { 
    ... 
    // search a prefix or whole key in trie and 
    // returns the node where search ends 
    private TrieNode searchPrefix(String word) { 
        TrieNode node = root; 
        for (int i = 0; i < word.length(); i++) { 
           char curLetter = word.charAt(i); 
           if (node.containsKey(curLetter)) { 
               node = node.get(curLetter); 
           } else { 
               return null; 
           } 
        } 
        return node; 
    } 
    // Returns if the word is in the trie. 
    public boolean search(String word) { 
       TrieNode node = searchPrefix(word); 
       return node != null && node.isEnd(); 
    } 
}
Complexity Analysis
- Time complexity : O(m) In each step of the algorithm we search for the next key character. In the worst case the algorithm performs m operations.
- Space complexity : O(1)

Search for a key prefix in a Trie
The approach is very similar to the one we used for searching a key in a Trie. We traverse the Trie from the root, till there are no characters left in key prefix or it is impossible to continue the path in the Trie with the current key character. The only difference with the mentioned above 
search for a key algorithm is that when we come to an end of the key prefix, we always return true. We don't need to consider the 
isEnd mark of the current Trie node, because we are searching for a prefix of a key, not for a whole key.


Figure 9. Search for a key prefix in a Trie.

Java
class Trie { 
    ... 
    // Returns if there is any word in the trie 
    // that starts with the given prefix. 
    public boolean startsWith(String prefix) { 
        TrieNode node = searchPrefix(prefix); 
        return node != null; 
    } 
}
Complexity Analysis
- Time complexity : O(m)
- Space complexity : O(1)

Practice Problems
Here are some wonderful problems for you to practice which uses the Trie data structure.
1.Add and Search Word - Data structure design
 - Pretty much a direct application of Trie.
2.Word Search II
 - Similar to Boggle.
    
Refer to
L14.Longest Common Prefix
L211.Design Add and Search Words Data Structure
L212.Word Search II
L336.Palindrome Pairs
L588.Design In-Memory File System
L745.Prefix and Suffix Search (Ref.L211)
L792.Number of Matching Subsequences (Ref.L208,L392,L1034,L2062)
L1032.Stream of Characters
