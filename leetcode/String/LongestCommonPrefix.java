/**
 * Write a function to find the longest common prefix string amongst an array of strings.
*/
// Solution 1: Binary Search
// Refer to
// http://www.geeksforgeeks.org/longest-common-prefix-set-4-binary-search/


// Solution 2: Trie
// Refer to
// http://www.geeksforgeeks.org/longest-common-prefix-set-5-using-trie/
import java.util.ArrayList;
import java.util.List;
/**
 * (1)Insert all the words one by one in the trie. After inserting we perform a walk on the trie.
 * (2)In this walk, go deeper until we find a node having more than 1 children(branching occurs) or 
 *    0 children (one of the string gets exhausted).This is because the characters (nodes in trie) 
 *    which are present in the longest common prefix must be the single child of its parent, 
 *    i.e- there should not be a branching in any of these nodes
 *  
 *                   root                   A trie for the words:
 *                     |                    "geek", "geezer", "geeksforgeeks", "geeks"
 *                     g
 *                     |
 *                     e
 *                     |
 *                     e   --> First node where branching occurs, hence all the characters
 *                    / \      above this node is in our longest prefix string, as "gee"   
 *                   k   z  
 *                  /     \
 */
public class LongestCommonPrefix {
	static final int ascii = 256;
	public String longestCommonPrefix(String[] strs) {
		Trie trie = new LongestCommonPrefix().new Trie();
		for(int i = 0; i < strs.length; i++) {
			trie.insert(strs[i]);
		}
		return walkTrie(trie.root);
    }
	
	public String walkTrie(TrieNode node) {
		String result = "";
		while(!node.isLeaf) {
			// Exactly "== 1" (not <= 1) has two meanings:
			// 1. If current node has next[] array only contain 1 NOT null item, 
			//    which means only one child, this is necessary for common 
			//    string, if over 1 item in next[] array equals branch happen
			// 2. If current node has next[] array all are null item, which means
			//    current node is already leaf, combine with while loop condition
			//    it will throw java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
			//    when given input Strings as empty array [], because list.get(0)
			//    cannot run with this empty list, so not "<= 1", exactly "== 1"
			if(countChildren(node).size() == 1) {
				int index = countChildren(node).get(0);
				String c = indexToString(index);
				result += c;
				// Recursive find child node
				node = node.next[index];
			} else {
				// If not match condition, break out, e.g has 2 children
				break;
			}
		}
		return result;
	}
	
	// Convert index back to next[index] ascii character
	public String indexToString(int index) {
		return Character.toString((char)index);
	}
	
	// Find how many items in current node next[] array not NULL
	// and record their indexes into list
	public List<Integer> countChildren(TrieNode node) {
		List<Integer> result = new ArrayList<Integer>();
		for(int i = 0; i < ascii; i++) {
			if(node.next[i] != null) {
				result.add(i);
			}
		}
		return result;
	}
	
	private class Trie {
		private TrieNode root;
		
		public Trie() {
			this.root = new TrieNode();
		}
		
		// In lcp(longest common prefix) only need insert method
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
			char c = word.charAt(d);
			x.next[c] = put(x.next[c], word, d + 1);
			return x;
		}
	}
	
	private class TrieNode {
		boolean isLeaf;
		public TrieNode[] next;
		
		public TrieNode() {
			this.isLeaf = false;
			this.next = new TrieNode[ascii];
		}
	}
	
	public static void main(String[] args) {
		String[] strings = {"geek", "geezer", "geeksforgeeks", "geeks"};
		LongestCommonPrefix lcp = new LongestCommonPrefix();
		String result = lcp.longestCommonPrefix(strings);
		System.out.println(result);
	}
}

 
// Solution 3: Word by Word Matching
// Refer to
// http://www.geeksforgeeks.org/longest-common-prefix-set-1-word-by-word-matching/


// Solution 4: Character by Character Matching
// Refer to
// http://www.geeksforgeeks.org/longest-common-prefix-set-2-character-by-character-matching/



// Solution 5: Divide and Conquer
// Refer to
// http://www.geeksforgeeks.org/longest-common-prefix-set-3-divide-and-conquer/



// Solution 6: Simple Solution
// Refer to
// https://discuss.leetcode.com/topic/6987/java-code-with-13-lines/6
