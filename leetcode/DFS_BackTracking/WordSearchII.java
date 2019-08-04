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
// http://algs4.cs.princeton.edu/52trie/TrieST.java.html
// http://stackoverflow.com/questions/12231453/syntax-for-creating-a-two-dimensional-array
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
	//private static final int lower_case_no = 26;
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
// Refer to
// https://www.cis.upenn.edu/~matuszek/cit594-2012/Pages/backtracking.html
// https://segmentfault.com/a/1190000003697153#articleHeader10
import java.util.ArrayList;
import java.util.List;

/** 
 * 复杂度
 * 时间 O(N^2logN) 空间 O(N)
 * 思路
 * 如果还像一中那样，对每个词进行一遍Word Search I，那复杂度就太高了。我们可以先用待查单词建立一个字典树，
 * 这样我们在从矩阵中某个点开始深度优先搜索时，可以直接用字典树判断当前组成的字符串是否是某个单词的前缀。
 * 如果是某个单词的前缀，再继续搜索下去。字典树同样也提供search接口，所以同样可以用于判断是否已经搜索到这个词了。
 * 注意
 * 因为结果中不能有重复，我们可以在加入结果前先判断是否已经加过该结果，也可以稍微修改一下字典树的search方法，
 * 使得每次我们搜索到一个单词时，将其isEnd的标记置为false，这样下次就不会搜索到这个词了。
 * 
 *  If input as
 *     char[][] board = {{'o','a','a','n'}, {'e','t','a','e'}, {'i','h','k','r'}, {'i','f','l','v'}};
       String[] words = {"oath", "pea", "eat", "rain"};
 *  
 *  Debugging message to show how dfs works with trie
 *  Insert 'oath' on Trie
    Insert 'pea' on Trie
    Insert 'eat' on Trie
    Insert 'rain' on Trie
    -----------------Finish Build Trie--------------------
    ------------------------------------------------------
	dfs(, row_index:0, column_index:0)
	|  add 'o' to prefix
	|  dfs(o, row_index:1, column_index:0)
	|  |  add 'e' to prefix
	|  search miss:'oe' prefix not exist in trie, return
	|  dfs(o, row_index:-1, column_index:0)
	|  index out of board range, return
	|  dfs(o, row_index:0, column_index:1)
	|  |  add 'a' to prefix
	|  |  dfs(oa, row_index:1, column_index:1)
	|  |  |  add 't' to prefix
	|  |  |  dfs(oat, row_index:2, column_index:1)
	|  |  |  |  add 'h' to prefix
	|  |  |  Search Hit --------> prefix (oath) recognized as whole word and find in trie ,as no duplicate will add into result, continue
	|  |  |  dfs(oath, row_index:3, column_index:1)
	|  |  |  |  add 'f' to prefix
	|  |  |  search miss:'oathf' prefix not exist in trie, return
	|  |  |  dfs(oath, row_index:1, column_index:1)
	|  |  |  board[1][1] already visited, return
	|  |  |  dfs(oath, row_index:2, column_index:2)
	|  |  |  |  add 'k' to prefix
	|  |  |  search miss:'oathk' prefix not exist in trie, return
	|  |  |  dfs(oath, row_index:2, column_index:0)
	|  |  |  |  add 'i' to prefix
	|  |  |  search miss:'oathi' prefix not exist in trie, return
	|  |  |  dfs(oat, row_index:0, column_index:1)
	|  |  |  board[0][1] already visited, return
	|  |  |  dfs(oat, row_index:1, column_index:2)
	|  |  |  |  add 'a' to prefix
	|  |  |  search miss:'oata' prefix not exist in trie, return
	|  |  |  dfs(oat, row_index:1, column_index:0)
	|  |  |  |  add 'e' to prefix
	|  |  |  search miss:'oate' prefix not exist in trie, return
	|  |  |  dfs(oa, row_index:-1, column_index:1)
	|  |  |  index out of board range, return
	|  |  |  dfs(oa, row_index:0, column_index:2)
	|  |  |  |  add 'a' to prefix
	|  |  |  search miss:'oaa' prefix not exist in trie, return
	|  |  |  dfs(oa, row_index:0, column_index:0)
	|  |  |  board[0][0] already visited, return
	|  |  |  dfs(o, row_index:0, column_index:-1)
	|  |  |  index out of board range, return
	|  |  |  dfs(, row_index:0, column_index:1)
	|  |  |  |  add 'a' to prefix
	|  |  |  search miss:'a' prefix not exist in trie, return
	|  |  |  dfs(, row_index:0, column_index:2)
	|  |  |  |  add 'a' to prefix
	|  |  |  search miss:'a' prefix not exist in trie, return
	|  |  |  dfs(, row_index:0, column_index:3)
	|  |  |  |  add 'n' to prefix
	|  |  |  search miss:'n' prefix not exist in trie, return
	|  |  |  dfs(, row_index:1, column_index:0)
	|  |  |  |  add 'e' to prefix
	|  |  |  |  dfs(e, row_index:2, column_index:0)
	|  |  |  |  |  add 'i' to prefix
	|  |  |  |  search miss:'ei' prefix not exist in trie, return
	|  |  |  |  dfs(e, row_index:0, column_index:0)
	|  |  |  |  |  add 'o' to prefix
	|  |  |  |  search miss:'eo' prefix not exist in trie, return
	|  |  |  |  dfs(e, row_index:1, column_index:1)
	|  |  |  |  |  add 't' to prefix
	|  |  |  |  search miss:'et' prefix not exist in trie, return
	|  |  |  |  dfs(e, row_index:1, column_index:-1)
	|  |  |  |  index out of board range, return
	|  |  |  |  dfs(, row_index:1, column_index:1)
	|  |  |  |  |  add 't' to prefix
	|  |  |  |  search miss:'t' prefix not exist in trie, return
	|  |  |  |  dfs(, row_index:1, column_index:2)
	|  |  |  |  |  add 'a' to prefix
	|  |  |  |  search miss:'a' prefix not exist in trie, return
	|  |  |  |  dfs(, row_index:1, column_index:3)
	|  |  |  |  |  add 'e' to prefix
	|  |  |  |  |  dfs(e, row_index:2, column_index:3)
	|  |  |  |  |  |  add 'r' to prefix
	|  |  |  |  |  search miss:'er' prefix not exist in trie, return
	|  |  |  |  |  dfs(e, row_index:0, column_index:3)
	|  |  |  |  |  |  add 'n' to prefix
	|  |  |  |  |  search miss:'en' prefix not exist in trie, return
	|  |  |  |  |  dfs(e, row_index:1, column_index:4)
	|  |  |  |  |  index out of board range, return
	|  |  |  |  |  dfs(e, row_index:1, column_index:2)
	|  |  |  |  |  |  add 'a' to prefix
	|  |  |  |  |  |  dfs(ea, row_index:2, column_index:2)
	|  |  |  |  |  |  |  add 'k' to prefix
	|  |  |  |  |  |  search miss:'eak' prefix not exist in trie, return
	|  |  |  |  |  |  dfs(ea, row_index:0, column_index:2)
	|  |  |  |  |  |  |  add 'a' to prefix
	|  |  |  |  |  |  search miss:'eaa' prefix not exist in trie, return
	|  |  |  |  |  |  dfs(ea, row_index:1, column_index:3)
	|  |  |  |  |  |  board[1][3] already visited, return
	|  |  |  |  |  |  dfs(ea, row_index:1, column_index:1)
	|  |  |  |  |  |  |  add 't' to prefix
	|  |  |  |  |  |  Search Hit --------> prefix (eat) recognized as whole word and find in trie ,as no duplicate will add into result, continue
	|  |  |  |  |  |  dfs(eat, row_index:2, column_index:1)
	|  |  |  |  |  |  |  add 'h' to prefix
	|  |  |  |  |  |  search miss:'eath' prefix not exist in trie, return
	|  |  |  |  |  |  dfs(eat, row_index:0, column_index:1)
	|  |  |  |  |  |  |  add 'a' to prefix
	|  |  |  |  |  |  search miss:'eata' prefix not exist in trie, return
	|  |  |  |  |  |  dfs(eat, row_index:1, column_index:2)
	|  |  |  |  |  |  board[1][2] already visited, return
	|  |  |  |  |  |  dfs(eat, row_index:1, column_index:0)
	|  |  |  |  |  |  |  add 'e' to prefix
	|  |  |  |  |  |  search miss:'eate' prefix not exist in trie, return
	|  |  |  |  |  |  dfs(, row_index:2, column_index:0)
	|  |  |  |  |  |  |  add 'i' to prefix
	|  |  |  |  |  |  search miss:'i' prefix not exist in trie, return
	|  |  |  |  |  |  dfs(, row_index:2, column_index:1)
	|  |  |  |  |  |  |  add 'h' to prefix
	|  |  |  |  |  |  search miss:'h' prefix not exist in trie, return
	|  |  |  |  |  |  dfs(, row_index:2, column_index:2)
	|  |  |  |  |  |  |  add 'k' to prefix
	|  |  |  |  |  |  search miss:'k' prefix not exist in trie, return
	|  |  |  |  |  |  dfs(, row_index:2, column_index:3)
	|  |  |  |  |  |  |  add 'r' to prefix
	|  |  |  |  |  |  |  dfs(r, row_index:3, column_index:3)
	|  |  |  |  |  |  |  |  add 'v' to prefix
	|  |  |  |  |  |  |  search miss:'rv' prefix not exist in trie, return
	|  |  |  |  |  |  |  dfs(r, row_index:1, column_index:3)
	|  |  |  |  |  |  |  |  add 'e' to prefix
	|  |  |  |  |  |  |  search miss:'re' prefix not exist in trie, return
	|  |  |  |  |  |  |  dfs(r, row_index:2, column_index:4)
	|  |  |  |  |  |  |  index out of board range, return
	|  |  |  |  |  |  |  dfs(r, row_index:2, column_index:2)
	|  |  |  |  |  |  |  |  add 'k' to prefix
	|  |  |  |  |  |  |  search miss:'rk' prefix not exist in trie, return
	|  |  |  |  |  |  |  dfs(, row_index:3, column_index:0)
	|  |  |  |  |  |  |  |  add 'i' to prefix
	|  |  |  |  |  |  |  search miss:'i' prefix not exist in trie, return
	|  |  |  |  |  |  |  dfs(, row_index:3, column_index:1)
	|  |  |  |  |  |  |  |  add 'f' to prefix
	|  |  |  |  |  |  |  search miss:'f' prefix not exist in trie, return
	|  |  |  |  |  |  |  dfs(, row_index:3, column_index:2)
	|  |  |  |  |  |  |  |  add 'l' to prefix
	|  |  |  |  |  |  |  search miss:'l' prefix not exist in trie, return
	|  |  |  |  |  |  |  dfs(, row_index:3, column_index:3)
	|  |  |  |  |  |  |  |  add 'v' to prefix
	|  |  |  |  |  |  |  search miss:'v' prefix not exist in trie, return
	oath
	eat
 * 
 */
public class WordPatternTrie {
	private static final boolean debugging = true;
	
    public List<String> findWords(char[][] board, String[] words) {
    	int rows = board.length;
    	int columns = board[0].length;
    	
    	boolean[][] visited = new boolean[rows][columns];
    	
    	// Use words need to search on board to build a trie
        Trie trie = new Trie();
        for(int i = 0; i < words.length; i++) {
        	trie.insert(words[i]);
        	System.out.println("Insert '" + words[i] + "' on Trie");
        }
        System.out.println("-----------------Finish Build Trie--------------------");
        System.out.println("------------------------------------------------------");
        
        List<String> result = new ArrayList<String>();
        for(int i = 0; i < rows; i++) {
        	for(int j = 0; j < columns; j++) {
        		dfs(board, visited, "", trie, i, j, result);
        	}
        }

        return result;
    }
    
	public void dfs(char[][] board, boolean[][] visited, String prefix, Trie trie, int rowIndex, int columnIndex, List<String> result) {
		enter(prefix, rowIndex, columnIndex);
		
		if(rowIndex < 0 || rowIndex >= board.length || columnIndex < 0 || columnIndex >= board[0].length) {
			// For debug
			String reason = "index out of board range, return";
			no(reason);
			return;
		}
		
		if(visited[rowIndex][columnIndex]) {
			// For debug
			String reason = "board[" + rowIndex + "][" + columnIndex + "] already visited, return";
			no(reason);
			return;
		}
		
		// Expend prefix string with current board item and used for next round dfs search
		prefix += board[rowIndex][columnIndex];
		
		// For debug
		add(board, rowIndex, columnIndex);
		
		if(!trie.startsWith(prefix)) {
			String reason = "search miss:'" + prefix + "' prefix not exist in trie, return";
			no(reason);
			return;
		} 
		
		if(trie.search(prefix)) {
			// For debug
			StringBuilder sb = new StringBuilder();
			sb.append("prefix (" + prefix + ") recognized as whole word and find in trie");
			if(!result.contains(prefix)) {
			   sb.append(" ,as no duplicate will add into result, continue");
			   result.add(prefix);
			}
			String reason = sb.toString();
			yes(reason);
		}
		
		visited[rowIndex][columnIndex] = true;
		dfs(board, visited, prefix, trie, rowIndex + 1, columnIndex, result);
		dfs(board, visited, prefix, trie, rowIndex - 1, columnIndex, result);
		dfs(board, visited, prefix, trie, rowIndex, columnIndex + 1, result);
		dfs(board, visited, prefix, trie, rowIndex, columnIndex - 1, result);
		visited[rowIndex][columnIndex] = false;
	}
	
	static String indent = "";
	public void enter(String prefix, int i, int j) {
		if(debugging) {
			System.out.println(indent + "dfs(" + prefix + ", row_index:" + i + ", column_index:" + j + ")");
			indent = indent + "|  ";
		}
	}
	
	public void add(char[][] board, int i, int j) {
		if(debugging) {
			System.out.println(indent + "add '" + board[i][j] + "' to prefix");
		}
	}
	
	public void yes(String reason) {
		if(debugging) {
			indent = indent.substring(3);
			System.out.println(indent + "Search Hit --------> " + reason);
		}
	}
	
	public void no(String reason) {
		if(debugging) {
			indent = indent.substring(3);
			System.out.println(indent + reason);
		}
	}
	
    public static void main(String[] args) {
    	char[][] board = {{'o','a','a','n'}, {'e','t','a','e'}, {'i','h','k','r'}, {'i','f','l','v'}};
    	String[] words = {"oath", "pea", "eat", "rain"};
    	
    	// For debug on duplicate string in final result
//    	char[][] board = {{'a','a'}};
//    	String[] words = {"a"};
    	
    	// For debug on time limit exceeded
//    	char[][] board = {{'b','a','a','b','a','b'},{'a','b','a','a','a','a'},{'a','b','a','a','a','b'},{'a','b','a','b','b','a'},
//    			{'a','a','b','b','a','b'},{'a','a','b','b','b','a'},{'a','a','b','a','a','b'}};
//    	String[] words = {"bbaabaabaaaaabaababaaaaababb","aabbaaabaaabaabaaaaaabbaaaba","babaababbbbbbbaabaababaabaaa","bbbaaabaabbaaababababbbbbaaa",
//    			"babbabbbbaabbabaaaaaabbbaaab","bbbababbbbbbbababbabbbbbabaa","babababbababaabbbbabbbbabbba","abbbbbbaabaaabaaababaabbabba",
//    			"aabaabababbbbbbababbbababbaa","aabbbbabbaababaaaabababbaaba","ababaababaaabbabbaabbaabbaba","abaabbbaaaaababbbaaaaabbbaab",
//    			"aabbabaabaabbabababaaabbbaab","baaabaaaabbabaaabaabababaaaa","aaabbabaaaababbabbaabbaabbaa","aaabaaaaabaabbabaabbbbaabaaa",
//    			"abbaabbaaaabbaababababbaabbb","baabaababbbbaaaabaaabbababbb","aabaababbaababbaaabaabababab","abbaaabbaabaabaabbbbaabbbbbb",
//    			"aaababaabbaaabbbaaabbabbabab","bbababbbabbbbabbbbabbbbbabaa","abbbaabbbaaababbbababbababba","bbbbbbbabbbababbabaabababaab",
//    			"aaaababaabbbbabaaaaabaaaaabb","bbaaabbbbabbaaabbaabbabbaaba","aabaabbbbaabaabbabaabababaaa","abbababbbaababaabbababababbb",
//    			"aabbbabbaaaababbbbabbababbbb","babbbaabababbbbbbbbbaabbabaa"};
    	
    	WordPatternTrie wpt = new WordPatternTrie();
    	for(String s : wpt.findWords(board, words)) {
    		System.out.println(s);
    	}
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
	//private static final int lower_case_no = 26;
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
	
	// Easy way to find out whether prefix exist in trie, simple
	// as directly call get(TrieNode x, String prefix, int d)
	// to detect weather TrieNode x exist or not, if exist,
	// then words start with prefix exist
	public boolean startsWith(String prefix) {
		TrieNode x = get(root, prefix, 0);
		if(x != null) {
			return true;
		}
		return false;
	}
	
	// If need to print out startsWith, then use same way as TrieST.java
//	public boolean startsWith(String prefix) {
//		Queue<String> results = new LinkedList<String>();
//		TrieNode x = get(root, prefix, 0);
//		collect(x, new StringBuilder(prefix), results);
//		if(results.size() != 0) {
//			return true;
//		}
//		return false;
//	}
//	
//	public void collect(TrieNode x, StringBuilder prefix, Queue<String> results) {
//		if(x == null) {
//			return;
//		}
//		if(x.isLeaf) {
//			results.add(prefix.toString());
//		}
//		for(int c = 0; c < lower_case_no; c++) {
//			prefix.append((char)(c + 'a'));
//			collect(x.next[c], prefix, results);
//			prefix.deleteCharAt(prefix.length() - 1);
//		}
//	}
}


// Native DFS + backtracking solution
class Solution {
    int[] dx = new int[]{0,0,1,-1};
    int[] dy = new int[]{1,-1,0,0};
    public List<String> findWords(char[][] board, String[] words) {
        Set<String> result = new HashSet<String>();
        for(String word : words) {
            boolean[][] visited = new boolean[board.length][board[0].length];
            for(int i = 0; i < board.length; i++) {
                for(int j = 0; j < board[0].length; j++) {
                    if(helper(word, board, i, j, 0, visited)) {
                        result.add(word);
                    }
                }
            }            
        }
        return new ArrayList<String>(result);
    }
    
    private boolean helper(String word, char[][] board, int i, int j, int index, boolean[][] visited) {
        if(index == word.length()) {
            return true;
        }
        if(i < 0 || i >= board.length || j < 0 || j >= board[0].length || index > word.length() || visited[i][j] || word.charAt(index) != board[i][j]) {
            return false;
        }
        visited[i][j] = true;
        for(int k = 0; k < 4; k++) {
            if(helper(word, board, i + dx[k], j + dy[k], index + 1, visited)) {
                return true;
            }
        }
        visited[i][j] = false;
        return false;
    }
}
