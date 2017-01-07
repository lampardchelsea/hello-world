/**
 * Write a function to find the longest common prefix string amongst an array of strings.
*/
// Solution 1: Binary Search
// Refer to
// http://www.geeksforgeeks.org/longest-common-prefix-set-4-binary-search/
/**
 * Refer to
 * http://www.geeksforgeeks.org/longest-common-prefix-set-4-binary-search/
 * 
 * In this article, an approach using Binary Search is discussed.
 * Steps:
 * (1) Find the string having the minimum length. Let this length be L.
 * (2) Perform a binary search on any one string (from the input array of strings). 
 *     Let us take the first string and do a binary search on the characters from 
 *     the index – 0 to L-1.
 * (3) Initially, take low = 0 and high = L-1 and divide the string into two 
 *     halves – left (low to mid) and right (mid+1 to high).Check whether all the 
 *     characters in the left half is present at the corresponding indices (low to 
 *     mid) of all the strings or not. If it is present then we append this half to 
 *     our prefix string and we look in the right half in a hope to find a longer 
 *     prefix.(It is guaranteed that a common prefix string is there.)
 * (4) Otherwise, if all the characters in the left half is not present at the 
 *     corresponding indices (low to mid) in all the strings, then we need not look 
 *     at the right half as there is some character(s) in the left half itself which 
 *     is not a part of the longest prefix string. So we indeed look at the left half 
 *     in a hope to find a common prefix string. (It may be possible that we don’t 
 *     find any common prefix string)
 *     
 * E.g Strings: geeksforgeeks   geeks   geek   geezer
 *     Length:       13           5       4      6
 *     The string with minimum length is "geek" (length = 4)
 *     So, we will do a binary search on any of the strings with the low as 0 and high
 *     as 3 (4 -1)
 *     For convenience we take the first string of the above array - "geeksforgeeks"
 *     In the string "geeksforgeeks" we do a binary search on its substring from index
 *     0 to index 3, i.e - "geek"
 *     
 *     We will do a binary search later
 *                                  
 *                                  geek
 *                       -----------    -----------            
 *                      /                          \
 *                     ge                           ek
 *     since "ge" is present                  ------   ------          
 *     in all the strings, so                /               \
 *     append this to our                   e                 k    
 *     longest common prefix           since "e" is        "k" is not present in 
 *     and go to the right side        present in all       all strings at its
 *                                     the strings at       correct index(it is not)
 *                                     its correct index,   present in "geezer" as
 *                                     so append it to      at the place of "k", "z"
 *                                     our longest common   is there, so we don't
 *                                     substring and go     append "k" to our longest
 *                                     to the right         common prefix
 *    
 *    Hence our longest common prefix is "gee"     
 */           
public class LongestCommonPrefixBinarySearch {
	public String longestCommonPrefix(String[] strs) {
		String result = "";
	    String strForBS = findShortestString(strs);
	    int lo = 0;
	    int hi = strForBS.length() - 1;
	    while(lo <= hi) {
		    int mid = (lo + hi) / 2;
		    // To get left part of string for binary search, index 
		    // range end at (mid + 1), as java substring method
		    // will naturally exclusive last position, e.g if
		    // strForBS is "geek", lo = 0, hi = 3, mid = 1, if not
		    // using (mid + 1), leftSubstring is "g", not what we
		    // expect as "ge"
		    String leftSubstring = strForBS.substring(lo, mid + 1);
	    	if(!existInAllStrs(strs, leftSubstring, lo, mid + 1)) {
	    		// If not exist in all strings, keep search in 
	    		// current left part of string for binary search
	    		hi = mid - 1;
	    	} else {
	    		// If exist, add current left part to result
	    		// and move on to search right part of string
	    		// for binary search
	    		lo = mid + 1;
	    		result += leftSubstring;
	    	}
	    }
	    return result;
    }
	
	// Find shorest string in all strings in given array
	public String findShortestString(String[] strs) {
		int minimum = Integer.MAX_VALUE;
		String result = "";
		for(int i = 0; i < strs.length; i++) {
			if(strs[i].length() <= minimum) {
				result = strs[i];
				minimum = strs[i].length();
			}
		}
		return result;
	}
	
	// Identify whether current left part of string for binary search exist or not
	// in all strings in given array
	public boolean existInAllStrs(String[] strs, String leftSubstring, int lo, int mid) {
		for(int i = 0; i < strs.length; i++) {
			String s = strs[i].substring(lo, mid);
			if(!s.equals(leftSubstring)) {
				return false;
			} 
		}
		return true;
	}
	
	public static void main(String[] args) {
		String[] strings = {"geek", "geezer", "geeksforgeeks", "geeks"};
		LongestCommonPrefixBinarySearch lcp = new LongestCommonPrefixBinarySearch();
		String result = lcp.longestCommonPrefix(strings);
		System.out.println(result);
	}
}


// Solution 2: Trie
// Refer to
// http://www.geeksforgeeks.org/longest-common-prefix-set-5-using-trie/
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Sort_Search_DataStructure/ImplementTrie.java
import java.util.ArrayList;
import java.util.List;

/**
 * Refer to
 * http://www.geeksforgeeks.org/longest-common-prefix-set-5-using-trie/
 * 
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
public class LongestCommonPrefixTrie {
	static final int ascii = 256;
	public String longestCommonPrefix(String[] strs) {
		Trie trie = new LongestCommonPrefixTrie().new Trie();
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
		LongestCommonPrefixTrie lcp = new LongestCommonPrefixTrie();
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
