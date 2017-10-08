import java.util.ArrayList;
import java.util.List;

/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/6006000.html
 * Given a set of words (without duplicates), find all word squares you can build from them.

	A sequence of words forms a valid word square if the kth row and column read the exact same string, 
	where 0 ≤ k < max(numRows, numColumns).
	
	For example, the word sequence ["ball","area","lead","lady"] forms a word square because each word 
	reads the same both horizontally and vertically.
	
	b a l l
	a r e a
	l e a d
	l a d y
	Note:
	
	There are at least 1 and at most 1000 words.
	All words will have the exact same length.
	Word length is at least 1 and at most 5.
	Each word contains only lowercase English alphabet a-z.
	 
	
	Example 1:
	
	Input:
	["area","lead","wall","lady","ball"]
	
	Output:
	[
	  [ "wall",
	    "area",
	    "lead",
	    "lady"
	  ],
	  [ "ball",
	    "area",
	    "lead",
	    "lady"
	  ]
	]
	
	Explanation:
	The output consists of two word squares. The order of output does not matter 
	(just the order of words in each word square matters).
	 
	
	Example 2:
	
	Input:
	["abat","baba","atan","atal"]
	
	Output:
	[
	  [ "baba",
	    "abat",
	    "baba",
	    "atan"
	  ],
	  [ "baba",
	    "abat",
	    "baba",
	    "atal"
	  ]
	]
	
	Explanation:
	The output consists of two word squares. The order of output does not matter 
	(just the order of words in each word square matters).
 * 
 * 
 * Solution
 * https://discuss.leetcode.com/topic/63516/explained-my-java-solution-using-trie-126ms-16-16
 * http://www.cnblogs.com/grandyang/p/6006000.html
 * 这道题是之前那道Valid Word Square的延伸，由于要求出所有满足要求的单词平方，所以难度大大的增加了，不要幻想着可以利用之前那题的解法来
 * 暴力破解，OJ不会答应的。那么根据以往的经验，对于这种要打印出所有情况的题的解法大多都是用递归来解，那么这题的关键是根据前缀来找单词，我们如果
 * 能利用合适的数据结构来建立前缀跟单词之间的映射，使得我们能快速的通过前缀来判断某个单词是否存在，这是解题的关键。对于建立这种映射，这里主要有
 * 两种方法，一种是利用哈希表来建立前缀和所有包含此前缀单词的集合之前的映射，第二种方法是建立前缀树Trie，顾名思义，前缀树专门就是为这种问题设计的。
 */
public class WordSquares {
	private class TrieNode {
		TrieNode[] children;
		List<String> startWith;
		public TrieNode() {
			this.children = new TrieNode[26];
			this.startWith = new ArrayList<String>();
		}
	}
	
	private class Trie {
		private TrieNode root;
		public Trie() {
			root = new TrieNode();
		}
		
		public void insert(String[] words) {
			// Caution: For insert multiple words (not 1 word),
			// we have to make sure for each word insert start
			// with fresh 'root', not share with one 'root',
			// so, move 'TrieNode node = root' into for loop
			// Compare with
			// https://github.com/lampardchelsea/hello-world/blob/master/lintcode/UnionFind_Trie/VideoExamples/ImplementTrie.java
//			TrieNode node = root;
			for(String word : words) {
				TrieNode node = root;
				for(int i = 0; i < word.length(); i++) {
					char c = word.charAt(i);
					int index = c - 'a';
					if(node.children[index] == null) {
						node.children[index] = new TrieNode();
					}
					// Binding current 'word' into this prefix
					node.children[index].startWith.add(word);
					node = node.children[index];				
				}
			}
		}
		
		public List<String> findByPrefix(String prefix) {
			List<String> result = new ArrayList<String>();
			TrieNode node = root;
			for(int i = 0; i < prefix.length(); i++) {
				char c = prefix.charAt(i);
				int index = c - 'a';
				if(node.children[index] == null) {
					return result;
				}
				node = node.children[index];
			}
			// Collect all strings start with 'prefix' of
			// current node
			result.addAll(node.startWith);
			return result;
		}
	}
	
	public List<List<String>> wordSquares(String[] words) {
		List<List<String>> result = new ArrayList<List<String>>();
		if(words == null || words.length == 0) {
			return result;
		}
		// Flag to control DFS
		int len = words[0].length();
		// Build trie with all given words
		Trie trie = new Trie();
		trie.insert(words);
		// Initial temporary answer builder for DFS
		List<String> ansBuilder = new ArrayList<String>();
		for(String word : words) {
			ansBuilder.add(word);
			dfs(len, trie, result, ansBuilder);
			// Backtracking to remove current combination
			ansBuilder.remove(ansBuilder.size() - 1);
		}
		return result;
	}
	
	public void dfs(int len, Trie trie, List<List<String>> result, List<String> ansBuilder) {
		if(ansBuilder.size() == len) {
			result.add(new ArrayList<>(ansBuilder));
			return;
		}
		int index = ansBuilder.size();
		StringBuilder prefixBuilder = new StringBuilder();
		for(String s : ansBuilder) {
			// Caution: Critical part --> s.charAt(index) with for loop
			// will create prefix for current dfs
			// E.g
			// Every word can be the first word of the sequence, let's take "wall" for example.
			// Which word could be the second word? Must be a word start with "a" (therefore "area"), 
			// because it has to match the second letter of word "wall".
			// Which word could be the third word? Must be a word start with "le" (therefore "lead"), 
			// because it has to match the third letter of word "wall" and the third letter of word "area".
			// What about the last word? Must be a word start with "lad" (therefore "lady"). 
			// For the same reason above.
			prefixBuilder.append(s.charAt(index));
		}
		List<String> startWith = trie.findByPrefix(prefixBuilder.toString());
		for(String sw : startWith) {
			ansBuilder.add(sw);
			dfs(len, trie, result, ansBuilder);
			// Backtracking to remove current string in combination
			ansBuilder.remove(ansBuilder.size() - 1);
		}
	}
	
	public static void main(String[] args) {
		WordSquares w = new WordSquares();
		String[] words = {"area","lead","wall","lady","ball"};
		List<List<String>> result = w.wordSquares(words);
		for(List<String> list : result) {
			System.out.println("--------------------");
			for(String s : list) {
				System.out.println(s);
			}
		}
	}
}
