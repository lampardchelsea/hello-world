/**
 * Given a 2D board and a list of words from the dictionary, find all words in the board.
 * Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells 
 * are those horizontally or vertically neighboring. The same letter cell may not be used more than 
 * once in a word.
 * For example,
 * Given words = ["oath","pea","eat","rain"] and board =
  [
    ['o','a','a','n'],
    ['e','t','a','e'],
    ['i','h','k','r'],
    ['i','f','l','v']
  ]
 * Return ["eat","oath"].
 * Note:
 * You may assume that all inputs are consist of lowercase letters a-z.
 * click to show hint.
 * You would need to optimize your backtracking to pass the larger test. Could you stop backtracking earlier?
 * If the current candidate does not exist in all words' prefix, you could stop backtracking immediately. 
 * What kind of data structure could answer such query efficiently? Does a hash table work? Why or why not? 
 * How about a Trie? If you would like to learn how to implement a basic trie, please work on this problem: 
 * Implement Trie (Prefix Tree) first.
*/
// Solution 1:
// Refer to
// http://www.programcreek.com/2014/06/leetcode-word-search-ii-java/
public class Solution {
    public List<String> findWords(char[][] board, String[] words) {
    	int rows = board.length;
    	int columns = board[0].length;
    	
    	boolean[][] visited = new boolean[rows][columns];
    	
        Trie trie = new Trie();
        for(int i = 0; i < words.length; i++) {
        	trie.insert(words[i]);
        }
        
        List<String> result = new ArrayList<String>();
        for(int i = 0; i < rows; i++) {
        	for(int j = 0; j < columns; j++) {
        		dfs(board, visited, "", trie, i, j, result);
        	}
        }

        return result;
    }
    
	public void dfs(char[][] board, boolean[][] visited, String temp, Trie trie, int rowIndex, int columnIndex, List<String> result) {
		if(rowIndex < 0 || rowIndex >= board.length || columnIndex < 0 || columnIndex >= board[0].length) {
			return;
		}
		
		if(visited[rowIndex][columnIndex]) {
			return;
		}
		
		temp += board[rowIndex][columnIndex];
		
		if(!trie.startsWith(temp)) {
			return;
		} 
		
		if(trie.search(temp)) {
		    if(!result.contains(temp)) {
		        result.add(temp);   
		    }
		}
		
		visited[rowIndex][columnIndex] = true;
		dfs(board, visited, temp, trie, rowIndex + 1, columnIndex, result);
		dfs(board, visited, temp, trie, rowIndex - 1, columnIndex, result);
		dfs(board, visited, temp, trie, rowIndex, columnIndex + 1, result);
		dfs(board, visited, temp, trie, rowIndex, columnIndex - 1, result);
		visited[rowIndex][columnIndex] = false;
	}
}

class TrieNode {
	static final int R = 26;
	boolean isLeaf;
	TrieNode[] next;
	
	public TrieNode() {
		this.isLeaf = false;
		this.next = new TrieNode[R];
	}
}

class Trie {
	private static final int lower_case_no = 26;
	private TrieNode root;
	
	public Trie() {
		root = new TrieNode();
	}
	
	public void insert(String word) {
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
		int c = (int)(word.charAt(d) - 'a');
		x.next[c] = put(x.next[c], word, d + 1);
		return x;
	}
	
	public boolean search(String word) {
		if(get(word) == null) {
			return false;
		}
		return get(word).isLeaf;
	}
	
	public TrieNode get(String word) {
		TrieNode x = get(root, word, 0);
		if(x == null) {
			return null;
		}
		return x;
	}
	
	public TrieNode get(TrieNode x, String word, int d) {
		if(x == null) {
			return null;
		}
		if(d == word.length()) {
			return x;
		}
		int c = (int)(word.charAt(d) - 'a');
		return get(x.next[c], word, d + 1);
	}
	
	public boolean startsWith(String prefix) {
		TrieNode x = get(root, prefix, 0);
		if(x != null) {
			return true;
		}
		return false;
	}
}

// Solution With Test Case And Debugging

