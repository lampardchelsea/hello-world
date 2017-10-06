/**
 * Refer to
   https://leetcode.com/problems/word-search-ii/description/
	Given a 2D board and a list of words from the dictionary, find all words in the board.
	
	Each word must be constructed from letters of sequentially adjacent cell, where 
	"adjacent" cells are those horizontally or vertically neighboring. The same 
	letter cell may not be used more than once in a word.
	
	For example,
	Given words = ["oath","pea","eat","rain"] and board =
	
	[
	  ['o','a','a','n'],
	  ['e','t','a','e'],
	  ['i','h','k','r'],
	  ['i','f','l','v']
	]
	Return ["eat","oath"].
	Note:
	You may assume that all inputs are consist of lowercase letters a-z.
	
	click to show hint.
	
	You would need to optimize your backtracking to pass the larger test. 
	Could you stop backtracking earlier?
	
	If the current candidate does not exist in all words' prefix, you could stop 
	backtracking immediately. What kind of data structure could answer such query 
	efficiently? Does a hash table work? Why or why not? How about a Trie? If you 
	would like to learn how to implement a basic trie, please work on this problem: 
	Implement Trie (Prefix Tree) first.
 * 
 * 
 * Solution
 * Basic Array + DFS
 * https://discuss.leetcode.com/topic/14256/my-simple-and-clean-java-code-using-dfs-and-trie
 * 
 * Basic HashMap + DFS
 * https://aaronice.gitbooks.io/lintcode/content/trie/word_search_ii.html
 * 
 * Optimized Array + DFS
 * https://discuss.leetcode.com/topic/33246/java-15ms-easiest-solution-100-00
 * 
 * 
 */


// Solution 1: Array + DFS (No optimization)
class TrieNode {
    TrieNode[] children;
    boolean isEnd;
    public TrieNode() {
        this.children = new TrieNode[26];
        this.isEnd = false;
    }
}

class Trie {
    private TrieNode root;
    
    public Trie() {
        this.root = new TrieNode();
    }
    
    public void insert(String word) {
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
    
    public boolean search(String word) {
        TrieNode node = find(word);
        if(node != null && node.isEnd) {
            return true;
        }
        return false;
    }
    
    public boolean startWith(String word) {
        if(find(word) != null) {
            return true;
        }
        return false;
    }
    
    public TrieNode find(String word) {
        TrieNode node = root;
        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int index = c - 'a';
            if(node.children[index] == null) {
                return null;
            }
            node = node.children[index];
        }
        return node;
    }
}


public class Solution {
    // Caution: Use Hashset to filter out duplicate strings
    // use input = {"a", "a"} to test out
    // List<String> result = new ArrayList<String>();
    Set<String> result = new HashSet<String>();
    int[] dx = {0,0,-1,1};
    int[] dy = {1,-1,0,0};
    
    public List<String> findWords(char[][] board, String[] words) {    
        Trie trie = new Trie();
        for(String word : words) {
            trie.insert(word);
        }
        
        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                dfs(board, i, j, "", visited, trie);
            }
        }
        return new ArrayList<String>(result);
    }
    
    private void dfs(char[][] board, int i, int j, String str, boolean[][] visited, Trie trie) {
        if(i < 0 || i >= board.length || j < 0 || j >= board[0].length || visited[i][j]) {
            return;
        }
        char c = board[i][j];      
        str += c;
        if(!trie.startWith(str)) {
            return;
        }
        if(trie.search(str)) {
           result.add(str); 
        }
        visited[i][j] = true;
        for(int k = 0; k < 4; k++) {
            int next_i = i + dx[k];
            int next_j = j + dy[k];
            dfs(board, next_i, next_j, str, visited, trie);
        }
        visited[i][j] = false;
    }
}


// Solution 2: HashMap + DFS
class TrieNode {
    Map<Character, TrieNode> children;
    boolean isEnd;
    public TrieNode() {
        this.children = new HashMap<Character, TrieNode>();
        this.isEnd = false;
    }
}

class Trie {
    private TrieNode root;
    
    public Trie() {
        this.root = new TrieNode();
    }
    
    public void insert(String word) {
        TrieNode node = root;
        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if(node.children.get(c) == null) {
                node.children.put(c, new TrieNode());
            }
            node = node.children.get(c);
        }
        node.isEnd = true;
    }
    
    public boolean search(String word) {
        TrieNode node = find(word);
        if(node != null && node.isEnd) {
            return true;
        }
        return false;
    }
    
    public boolean startWith(String word) {
        if(find(word) != null) {
            return true;
        }
        return false;
    }
    
    public TrieNode find(String word) {
        TrieNode node = root;
        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if(node.children.get(c) == null) {
                return null;
            }
            node = node.children.get(c);
        }
        return node;
    }
}


public class Solution {
    // Caution: Use Hashset to filter out duplicate strings
    // use input = {"a", "a"} to test out
    // List<String> result = new ArrayList<String>();
    Set<String> result = new HashSet<String>();
    int[] dx = {0,0,-1,1};
    int[] dy = {1,-1,0,0};
    
    public List<String> findWords(char[][] board, String[] words) {    
        Trie trie = new Trie();
        for(String word : words) {
            trie.insert(word);
        }
        
        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                dfs(board, i, j, "", visited, trie);
            }
        }
        return new ArrayList<String>(result);
    }
    
    private void dfs(char[][] board, int i, int j, String str, boolean[][] visited, Trie trie) {
        if(i < 0 || i >= board.length || j < 0 || j >= board[0].length || visited[i][j]) {
            return;
        }
        char c = board[i][j];      
        str += c;
        if(!trie.startWith(str)) {
            return;
        }
        if(trie.search(str)) {
           result.add(str); 
        }
        visited[i][j] = true;
        for(int k = 0; k < 4; k++) {
            int next_i = i + dx[k];
            int next_j = j + dy[k];
            dfs(board, next_i, next_j, str, visited, trie);
        }
        visited[i][j] = false;
    }
}

// Solution 3: Opitimized + DFS
/**
 Refer to
 https://discuss.leetcode.com/topic/33246/java-15ms-easiest-solution-100-00
 Intuitively, start from every cell and try to build a word in the dictionary. 
 Backtracking (dfs) is the powerful way to exhaust every possible ways. Apparently, 
 we need to do pruning when current character is not in any word.

How do we instantly know the current character is invalid? HashMap?
How do we instantly know what's the next valid character? LinkedList?
But the next character can be chosen from a list of characters. "Mutil-LinkedList"?
Combing them, Trie is the natural choice. Notice that:

TrieNode is all we need. search and startsWith are useless.
No need to store character at TrieNode. c.next[i] != null is enough.
Never use c1 + c2 + c3. Use StringBuilder.
No need to use O(n^2) extra space visited[m][n].
No need to use StringBuilder. Storing word itself at leaf node is enough.
No need to use HashSet to de-duplicate. Use "one time search" trie.
*/
