/**
 * Write a function to find the longest common prefix string amongst an array of strings.
*/
// Solution 1: Binary Search
// Refer to
// http://www.geeksforgeeks.org/longest-common-prefix-set-4-binary-search/
/**
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
/**
 * We start with an example. Suppose there are two strings- “geeksforgeeks” and “geeks”. 
 * What is the longest common prefix in both of them? It is “geeks”.
 * Now let us introduce another word “geek”. So now what is the longest common prefix in 
 * these three words ? It is “geek”
 * We can see that the longest common prefix holds the associative property, i.e-
 *    LCP(string1, string2, string3) 
         = LCP (LCP (string1, string2), string3)
      
      Like here
      LCP (“geeksforgeeks”, “geeks”, “geek”) 
         =  LCP (LCP (“geeksforgeeks”, “geeks”), “geek”)
         =  LCP (“geeks”, “geek”) = “geek”
 * So we can make use of the above associative property to find the LCP of the given strings. 
 * We one by one calculate the LCP of each of the given string with the LCP so far. The final 
 * result will be our longest common prefix of all the strings.
 * Note that it is possible that the given strings have no common prefix. This happens when 
 * the first character of all the strings are not same.
 * We show the algorithm with the input strings- “geeksforgeeks”, “geeks”, “geek”, “geezer” 
 * by the below figure.
 * 
 *            geek   geezer  geeksforgeeks  geeks
 *                \    /      /             /
 *                  gee      /             /
 *                    \     /             /   
 *                      gee              /
 *                        \             /
 *                         ---- gee ----
 * 
 */
public class LongestCommonPrefixWordToWord {
	public String longestCommonPrefix(String[] strs) {
        int length = strs.length;
        String result = "";
        if(length == 1) {
            result = strs[0];
        } else if(length > 1) {
        	// This process is elegant, the two points:
        	// (1) The base case is individually store strs[0]
        	//     as initial status
        	// (2) When we finish a common prefix retrieve
        	//     between two strings, need to use its result
        	//     into next loop, like iterative update temporary
        	//     string. Also, in each loop need to update
        	//     comparing object as next string in string array
        	//     by increase index as 1
            String temp = strs[0];
            for(int i = 1; i < length; i++) {
                temp = commonPrefixBetweenTwoStrings(temp, strs[i]);
            }
            result = temp;
        }
        return result;
    }
    
    public String commonPrefixBetweenTwoStrings(String str1, String str2) {
		String result = "";
		int str1Len = str1.length();
		int str2Len = str2.length();
		int minLen = str1Len <= str2Len ? str1Len : str2Len;
		int i;
		for(i = 0; i < minLen; i++) {
			if(str1.charAt(i) != str2.charAt(i)) {
				break;
			}
		}
		result = str1.substring(0, i);
		return result;
	}
    
    public static void main(String[] args) {
    	String[] strings = {"geek", "geezer", "geeksforgeeks", "geeks"};
    	LongestCommonPrefixCharToChar lcp = new LongestCommonPrefixCharToChar();
		String result = lcp.longestCommonPrefix(strings);
		System.out.println(result);
    }
}



// Solution 4: Character by Character Matching
// Refer to
// http://www.geeksforgeeks.org/longest-common-prefix-set-2-character-by-character-matching/
public class LongestCommonPrefixCharToChar {
	public String longestCommonPrefix(String[] strs) {
	    String result = "";
	    if(strs.length == 0) {
	        return result;
	    }
        int minLen = minLen(strs);
        for(int i = 0; i < minLen; i++) {
            boolean same = true;
            char c = strs[0].charAt(i);
            for(int j = 1; j < strs.length; j++) {
                if(strs[j].charAt(i) != c) {
                    same = false;
                    break;
                }
            }
            if(same) {
               result += c; 
            } else {
               // Must also break in outside for loop,
               // otherwise result may continue add same
               // characters after current different
               // character (e.g aca, cba if not break
               // here after first character not match,
               // it will continue add match character
               // as 'a' on position 2)
               break;
            }
        }
        return result;
    }
    
    public int minLen(String[] strs) {
        int minLen = Integer.MAX_VALUE;
        for(int i = 0; i < strs.length; i++) {
            if(strs[i].length() <= minLen) {
                minLen = strs[i].length();
            }
        }
        return minLen;
    }
    
    public static void main(String[] args) {
    	String[] strings = {"aca", "cba"};
    	LongestCommonPrefixCharToChar lcp = new LongestCommonPrefixCharToChar();
		String result = lcp.longestCommonPrefix(strings);
		System.out.println(result);
    }
}



// Solution 5: Divide and Conquer
// Refer to
// http://www.geeksforgeeks.org/longest-common-prefix-set-3-divide-and-conquer/
/**
 * What is the difference between recursion and divide and conquer?
 * Refer to
 * https://www.quora.com/What-is-the-difference-between-recursion-and-divide-and-conquer
 * Recursion is a programming method where you define a function in terms of itself. 
 * The function generally calls itself with slightly modified parameters (in order to converge).
 * Divide and conquer is when you split a problem into non-overlapping sub-problems. 
 * For example, in merge sort, you split the array into two halves and sort them and then merge 
 * them back. You divided the problem into two sub-problems, solved them and got a solution to 
 * the original problem. Note that we use recursion to solve the sub-problems.
 * 
 * Recursion is a "Programming Paradigm" which is generally used to implement 
 * the "Algorithmic Paradigm" Divide and Conquer.
 * 
 * Divide and conquer algorithms
 * Refer to
 * https://www.khanacademy.org/computing/computer-science/algorithms/merge-sort/a/divide-and-conquer-algorithms
 * 
 * Merge Sort
 * Refer to
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Sort_Search_DataStructure/MergeSort.java
 * 
 * How to print log with indent ?
 * Refer to
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/BackTracking/WordPatternII.java
 * e.g "geek", "geezer", "geeksforgeeks", "geeks"
 * Enter divideAndConquer() method
	|  Enter divideAndConquer() method
	|  |  Enter divideAndConquer() method
	|  |  Into branch --> index lo == hi(0,0) return string on current index position = 'geek'
	|  |  Enter divideAndConquer() method
	|  |  Into branch --> index lo == hi(1,1) return string on current index position = 'geezer'
	|  Enter commonPrefixBetweenTwoStrings() method
	|  Into branch --> index lo < hi(0,1) return common prefix between two strings = 'gee'
	|  Enter divideAndConquer() method
	|  |  Enter divideAndConquer() method
	|  |  Into branch --> index lo == hi(2,2) return string on current index position = 'geeksforgeeks'
	|  |  Enter divideAndConquer() method
	|  |  Into branch --> index lo == hi(3,3) return string on current index position = 'geeks'
	|  Enter commonPrefixBetweenTwoStrings() method
	|  Into branch --> index lo < hi(2,3) return common prefix between two strings = 'geeks'
	Enter commonPrefixBetweenTwoStrings() method
	Into branch --> index lo < hi(0,3) return common prefix between two strings = 'gee'
	gee
 * 
 * 
 * Refer to
 * http://www.geeksforgeeks.org/longest-common-prefix-set-3-divide-and-conquer/
 * In this algorithm, a divide and conquer approach is discussed. We first divide the arrays of string into two parts. 
 * Then we do the same for left part and after that for the right part. We will do it until and unless all the strings 
 * becomes of length 1. Now after that we will start conquering by returning the common prefix of the left and the 
 * right strings.
 * The algorithm will be clear using the below illustration. We consider our strings as 
 * – "geek", "geezer", "geeksforgeeks", "geeks"
 * 
 *                                 geek   geezer  geeksforgeeks  geeks
 *                                /                                   \
 *                          geek    geezer              geeksforgeeks    geeks
 *                            |       |                       |            |
 *                          geek    geezer              geeksforgeeks    geeks         
 *                             \     /                            \      /
 *       Longest Common Prefix = gee        Longest Common Prefix = geeks
 *                                 \                                 /
 *                                  ---------------    --------------
 *                          Longest Common Prefix = gee                          
 */
public class LongestCommonPrefixDivideAndConquer {
	public String indent = "";
	public boolean debug = true;
	
	public String longestCommonPrefix(String[] strs) {
	    int lo = 0;
	    int hi = strs.length - 1;
	    return divideAndConquer(strs, lo, hi);
    }
	
	public String commonPrefixBetweenTwoStrings(String str1, String str2) {
		// For debug
		enterCommonPrefixBetweenTwoStrings();
		String result = "";
		int str1Len = str1.length();
		int str2Len = str2.length();
		int minLen = str1Len <= str2Len ? str1Len : str2Len;
		int i;
		for(i = 0; i < minLen; i++) {
			if(str1.charAt(i) != str2.charAt(i)) {
				break;
			}
		}
		result = str1.substring(0, i);
		return result;
	}
	
	public String divideAndConquer(String[] strs, int lo, int hi) {
		// For debug
		enterDivideAndConquer();
		String result = "";
		// Important: base case --> index (lo == hi) will return
		// the string on current index position in string array
		if(lo == hi) {
		   result = strs[lo];
		   // For debug
		   enter1(result, lo, hi);
		}
		// We first divide the arrays of string into two parts. 
		// Then we do the same for left part and after that for the 
		// right part. We will do it until and unless all the strings 
		// becomes of length 1. Now after that we will start conquering 
		// by returning the common prefix of the left and the right strings.
		if(lo < hi) {
			int mid = (lo + hi) / 2;
			String str1 = divideAndConquer(strs, lo, mid);
			String str2 = divideAndConquer(strs, mid + 1, hi);
			result = commonPrefixBetweenTwoStrings(str1, str2);
			// For debug
			enter2(result, lo, hi);
		}		
		return result;
	}	
	
	public void enterDivideAndConquer() {
		if(debug) {
			System.out.println(indent + "Enter divideAndConquer() method");
			indent += "|  ";
		}
	}
	
	public void enterCommonPrefixBetweenTwoStrings() {
		if(debug) {
			indent = indent.substring(3);
			System.out.println(indent + "Enter commonPrefixBetweenTwoStrings() method");
			indent += "|  ";
		}
	}
	
	public void enter1(String result, int lo, int hi) {
		if(debug) {
			indent = indent.substring(3);
			System.out.println(indent + "Into branch --> index lo == hi(" + lo + "," + hi + ") return string on current index position = '" + result + "'");			
		}
	}
	
	public void enter2(String result, int lo, int hi) {
		if(debug) {
			indent = indent.substring(3);
			System.out.println(indent + "Into branch --> index lo < hi(" + lo + "," + hi + ") return common prefix between two strings = '" + result + "'");			
		}
	}
	
	public static void main(String[] args) {
		String[] strings = {"geek", "geezer", "geeksforgeeks", "geeks"};
		LongestCommonPrefixDivideAndConquer lcp = new LongestCommonPrefixDivideAndConquer();
		String result = lcp.longestCommonPrefix(strings);
		System.out.println(result);
	}
}



// Solution 6: Simple Solution
// Refer to
// https://discuss.leetcode.com/topic/6987/java-code-with-13-lines/6























































































































































https://leetcode.com/problems/longest-common-prefix/

Write a function to find the longest common prefix string amongst an array of strings.

If there is no common prefix, return an empty string "".

Example 1:
```
Input: strs = ["flower","flow","flight"]
Output: "fl"
```

Example 2:
```
Input: strs = ["dog","racecar","car"]
Output: ""
Explanation: There is no common prefix among the input strings.
```

Constraints:
- 1 <= strs.length <= 200
- 0 <= strs[i].length <= 200
- strs[i] consists of only lowercase English letters.
---
Attempt 1: 2023-08-13

Solution 1: Trie (30 min)
```
class Solution {
    TrieNode root = new TrieNode();
    public String longestCommonPrefix(String[] strs) {
        for(String str : strs) {
            addStr(str);
        }
        TrieNode node = root;
        // To find longest common prefix, randomly pick any string
        // among strs array is fine, because the longest common prefix
        // won't exceed any string
        int len = strs.length;
        return search(strs[0], len);
    }
 
    public void addStr(String str) {
        TrieNode node = root;
        for(char c : str.toCharArray()) {
            if(node.children[c - 'a'] == null) {
                node.children[c - 'a'] = new TrieNode();                
            }
            node = node.children[c - 'a'];
            node.count++;
        }
        //node.isEnd = true;
    }
 
    public String search(String str, int len) {
        StringBuilder sb = new StringBuilder();
        TrieNode node = root;
        for(char c : str.toCharArray()) {
            // Only when current character's corresponding TrieNode' count 
            // match total string number in the array(len), we will add 
            // current character into final result, because it means this 
            // character shared between all strings
            if(node.children[c - 'a'].count == len) {
                sb.append(c);
                node = node.children[c - 'a'];
            } else {
                // If count not match total string number
                break;
            }
        }
        return sb.toString();
    }
}

class TrieNode {
    TrieNode[] children;
    // No need 'isEnd' since to find longest common prefix
    // for any string in the given string array we at most
    // can go through all characters in that string, 'isEnd'
    // will have no usage during go through process 
    //boolean isEnd;
    // Used to judge if the character shared between all
    // strings in the array
    int count;
    public TrieNode() {
        this.children = new TrieNode[26];
        //this.isEnd = false;
        this.count = 0;
    }
}

Time complexity : preprocessing O(S), where S is the number of all characters in the array, LCP query O(m). 
Trie build has O(S) time complexity. To find the common prefix of q in the Trie takes in the worst case O(m). 
Space complexity : O(S). We only used additional S extra space for the Trie.
```

Refer to
https://leetcode.com/problems/longest-common-prefix/editorial/

Further Thoughts / Follow up

Let's take a look at a slightly different problem:

Given a set of keys S = [S1,S2…Sn], find the longest common prefix among a string q and S. This LCP query will be called frequently.
We could optimize LCP queries by storing the set of keys S in a Trie. For more information about Trie, please see this article Implement a trie (Prefix trie). In a Trie, each node descending from the root represents a common prefix of some keys. But we need to find the longest common prefix of a string q and all key strings. This means that we have to find the deepest path from the root, which satisfies the following conditions:
- it is prefix of query string q
- each node along the path must contain only one child element. Otherwise the found path will not be a common prefix among all strings.
- the path doesn't comprise of nodes which are marked as end of key. Otherwise the path couldn't be a prefix a of key which is shorter than itself.

Algorithm
The only question left, is how to find the deepest path in the Trie, that fulfills the requirements above. The most effective way is to build a trie from [S1…Sn] strings. Then find the prefix of query string qin the Trie. We traverse the Trie from the root, till it is impossible to continue the path in the Trie because one of the conditions above is not satisfied.

Figure 4. Finding the longest common prefix of strings using Trie

```
public String longestCommonPrefix(String q, String[] strs) {
    if (strs == null || strs.length == 0)
         return "";  
    if (strs.length == 1)
         return strs[0];
    Trie trie = new Trie();      
    for (int i = 1; i < strs.length ; i++) {
        trie.insert(strs[i]);
    }
    return trie.searchLongestPrefix(q);
}
class TrieNode {
    // R links to node children
    private TrieNode[] links;
    private final int R = 26;
    private boolean isEnd;
    // number of children non null links
    private int size;    
    public void put(char ch, TrieNode node) {
        links[ch -'a'] = node;
        size++;
    }
    public int getLinks() {
        return size;
    }
    //assume methods containsKey, isEnd, get, put are implemented as it is described
   //in  https://leetcode.com/articles/implement-trie-prefix-tree/)
}
public class Trie {
    private TrieNode root;
    public Trie() {
        root = new TrieNode();
    }
//assume methods insert, search, searchPrefix are implemented as it is described
//in  https://leetcode.com/articles/implement-trie-prefix-tree/)
    private String searchLongestPrefix(String word) {
        TrieNode node = root;
        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            char curLetter = word.charAt(i);
            if (node.containsKey(curLetter) && (node.getLinks() == 1) && (!node.isEnd())) {
                prefix.append(curLetter);
                node = node.get(curLetter);
            }
            else
                return prefix.toString();
         }
         return prefix.toString();
    }
}
```
Complexity Analysis
In the worst case query q has length m and it is equal to all n strings of the array.
- Time complexity : preprocessing O(S), where S is the number of all characters in the array, LCP query O(m).
  Trie build has O(S) time complexity. To find the common prefix of q in the Trie takes in the worst case O(m).
- Space complexity : O(S). We only used additional S extra space for the Trie.
---
Solution 2: Vertical Scanning (10 min)
```
class Solution {
    public String longestCommonPrefix(String[] strs) {
        for(int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            for(int j = 1; j < strs.length; j++) {
                // Either strs[j] reach the end or same position character
                // in strs[j] not equal to strs[0]'s one, break out
                if(strs[j].length() == i || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }
        return strs[0];
    }
}

Time complexity : O(S), where S is the sum of all characters in all strings. 
In the worst case there will be n equal strings with length m and the algorithm performs S=m⋅n character comparisons. 
Even though the worst case is still the same as Approach 1, in the best case there are at most n⋅minLen comparisons where minLen is the length of the shortest string in the array. 
Space complexity : O(1). We only used constant extra space.
```

Refer to
https://leetcode.com/problems/longest-common-prefix/editorial/

Approach 2: Vertical scanning

Algorithm
Imagine a very short string is the common prefix at the end of the array. The above approach will still do SSScomparisons. One way to optimize this case is to do vertical scanning. We compare characters from top to bottom on the same column (same character index of the strings) before moving on to the next column.
```
public String longestCommonPrefix(String[] strs) {
    if (strs == null || strs.length == 0) return "";
    for (int i = 0; i < strs[0].length() ; i++){
        char c = strs[0].charAt(i);
        for (int j = 1; j < strs.length; j ++) {
            if (i == strs[j].length() || strs[j].charAt(i) != c)
                return strs[0].substring(0, i);             
        }
    }
    return strs[0];
}
```
Complexity Analysis
- Time complexity : O(S), where S is the sum of all characters in all strings.
  In the worst case there will be n equal strings with length m and the algorithm performs S=m⋅n character comparisons.
  Even though the worst case is still the same as Approach 1, in the best case there are at most n⋅minLen comparisons where minLen is the length of the shortest string in the array.
- Space complexity : O(1). We only used constant extra space.

---
Solution 3: Horizontal  Scanning (10 min)
```
class Solution {
    public String longestCommonPrefix(String[] strs) {
        String prefix = strs[0];
        for(int i = 1; i < strs.length; i++) {
            while(strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if(prefix.isEmpty()) {
                    return "";
                }
            }
        }
        return prefix;
    }
}
```

Refer to
https://leetcode.com/problems/longest-common-prefix/editorial/

Approach 1: Horizontal scanning

Intuition

For a start we will describe a simple way of finding the longest prefix shared by a set of strings LCP(S1…Sn)
We will use the observation that :
LCP(S1…Sn)=LCP(LCP(LCP(S1,S2),S3),…Sn)

Algorithm
To employ this idea, the algorithm iterates through the strings [S1…Sn], finding at each iteration iiithe longest common prefix of strings LCP(S1…Si) 
When LCP(S1…Si) is an empty string, the algorithm ends. Otherwise after n iterations, the algorithm returns LCP(S1…Sn)

Figure 1. Finding the longest common prefix (Horizontal scanning)
```
 public String longestCommonPrefix(String[] strs) {
    if (strs.length == 0) return "";
    String prefix = strs[0];
    for (int i = 1; i < strs.length; i++)
        while (strs[i].indexOf(prefix) != 0) {
            prefix = prefix.substring(0, prefix.length() - 1);
            if (prefix.isEmpty()) return "";
        }        
    return prefix;
}
```
Complexity Analysis
- Time complexity : O(S), where S is the sum of all characters in all strings.
  In the worst case all n strings are the same. The algorithm compares the string S1 with the other strings [S2…Sn] There are S character comparisons, where S is the sum of all characters in the input array.
- Space complexity : O(1). We only used constant extra space.
---
Solution 4: Binary Search (10 min)
```
class Solution {
    public String longestCommonPrefix(String[] strs) {
        int minLen = Integer.MAX_VALUE;
        for(String str : strs) {
            minLen = Math.min(minLen, str.length());
        }
        int lo = 1;
        int hi = minLen;
        while(lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            // Use auxiliary helper method to determine if current
            // length of substring of any string (e.g strs[0]) is
            // a common prefix for all other strings
            if(isCommonPrefix(strs, mid)) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        // Use 'hi' is following pattern 'Find Upper Boundary'
        return strs[0].substring(0, hi);
    }
 

    private boolean isCommonPrefix(String[] strs, int len) {
        String str = strs[0].substring(0, len);
        for(int i = 1; i < strs.length; i++) {
            if(!strs[i].startsWith(str)) {
                return false;
            }
        }
        return true;
    }
}

Time complexity : O(S⋅log⁡m), where S is the sum of all characters in all strings. 
The algorithm makes log⁡m iterations, for each of them there are S=m⋅n comparisons, which gives in total O(S⋅log⁡m) time complexity. 
Space complexity : O(1). We only used constant extra space.
```

Refer to
https://leetcode.com/problems/longest-common-prefix/editorial/

Approach 4: Binary search

The idea is to apply binary search method to find the string with maximum value L, which is common prefix of all of the strings. The algorithm searches space is the interval (0…minLen), where minLen is minimum string length and the maximum possible common prefix. Each time search space is divided in two equal parts, one of them is discarded, because it is sure that it doesn't contain the solution. There are two possible cases:
- S[1...mid]is not a common string. This means that for each j > i S[1..j]is not a common string and we discard the second half of the search space.
- S[1...mid]is common string. This means that for for each i < j S[1..i]is a common string and we discard the first half of the search space, because we try to find longer common prefix.

Figure 3. Finding the longest common prefix of strings using binary search technique
```
public String longestCommonPrefix(String[] strs) {
    if (strs == null || strs.length == 0)
        return "";
    int minLen = Integer.MAX_VALUE;
    for (String str : strs)
        minLen = Math.min(minLen, str.length());
    int low = 1;
    int high = minLen;
    while (low <= high) {
        int middle = (low + high) / 2;
        if (isCommonPrefix(strs, middle))
            low = middle + 1;
        else
            high = middle - 1;
    }
    return strs[0].substring(0, (low + high) / 2);
}

private boolean isCommonPrefix(String[] strs, int len){
    String str1 = strs[0].substring(0,len);
    for (int i = 1; i < strs.length; i++)
        if (!strs[i].startsWith(str1))
            return false;
    return true;
}
```
Complexity Analysis
In the worst case we have n equal strings with length mmm
- Time complexity : O(S⋅log⁡m), where S is the sum of all characters in all strings.
  The algorithm makes log⁡m iterations, for each of them there are S=m⋅n comparisons, which gives in total O(S⋅log⁡m) time complexity.
- Space complexity : O(1). We only used constant extra space.
---
Solution 5: Divide and Conquer (10 min)
```
class Solution {
    public String longestCommonPrefix(String[] strs) {
        return helper(strs, 0, strs.length - 1);
    }

    private String helper(String[] strs, int left, int right) {
        // Base condition
        if(left == right) {
            return strs[left];
        }
        // Divide
        int mid = left + (right - left) / 2;
        String lcpleft = helper(strs, left, mid);
        String lcpright = helper(strs, mid + 1, right);
        // Conquer
        return commonPrefix(lcpleft, lcpright);
    }
  

    private String commonPrefix(String lcpleft, String lcpright) {
        int minLen = Math.min(lcpleft.length(), lcpright.length());
        for(int i = 0; i < minLen; i++) {
            if(lcpleft.charAt(i) != lcpright.charAt(i)) {
                return lcpleft.substring(0, i);
            }
        }
        return lcpleft.substring(0, minLen);
    }
}

Time complexity : O(S), where S is the number of all characters in the array, S=m⋅n 
Time complexity is 2⋅T(n/2)+O(m). Therefore time complexity is O(S). 
In the best case this algorithm performs O(minLen⋅n) comparisons, where minLen is the shortest string of the array 
Space complexity : O(m⋅log⁡n) 
There is a memory overhead since we store recursive calls in the execution stack. There are log⁡n recursive calls, each store need m space to store the result, so space complexity is O(m⋅log⁡n)
```

Refer to
https://leetcode.com/problems/longest-common-prefix/editorial/

Approach 3: Divide and conquer

Intuition
The idea of the algorithm comes from the associative property of LCP operation. We notice that :
LCP(S1…Sn)=LCP(LCP(S1…Sk),LCP(Sk+1…Sn)), where LCP(S1…Sn) is the longest common prefix in set of strings [S1…Sn], 1<k<n

Algorithm
To apply the observation above, we use divide and conquer technique, where we split the LCP(Si…Sj) problem into two subproblems LCP(Si…Smid) and LCP(Smid+1…Sj), where mid is (i+j)/2. We use their solutions lcpLeft and lcpRight to construct the solution of the main problem LCP(Si…Sj). To accomplish this we compare one by one the characters of lcpLeft and lcpRight till there is no character match. The found common prefix of lcpLeft and lcpRight is the solution of the LCP(Si…Sj).

Figure 2. Finding the longest common prefix of strings using divide and conquer technique
```
public String longestCommonPrefix(String[] strs) {
    if (strs == null || strs.length == 0) return "";    
        return longestCommonPrefix(strs, 0 , strs.length - 1);
}

private String longestCommonPrefix(String[] strs, int l, int r) {
    if (l == r) {
        return strs[l];
    }
    else {
        int mid = (l + r)/2;
        String lcpLeft =   longestCommonPrefix(strs, l , mid);
        String lcpRight =  longestCommonPrefix(strs, mid + 1,r);
        return commonPrefix(lcpLeft, lcpRight);
   }
}

String commonPrefix(String left,String right) {
    int min = Math.min(left.length(), right.length());       
    for (int i = 0; i < min; i++) {
        if ( left.charAt(i) != right.charAt(i) )
            return left.substring(0, i);
    }
    return left.substring(0, min);
}
```
Complexity Analysis
In the worst case we have n equal strings with length m
- Time complexity : O(S), where S is the number of all characters in the array, S=m⋅n
  Time complexity is 2⋅T(n/2)+O(m). Therefore time complexity is O(S).
  In the best case this algorithm performs O(minLen⋅n) comparisons, where minLen is the shortest string of the array
- Space complexity : O(m⋅log⁡n)
  There is a memory overhead since we store recursive calls in the execution stack. There are log⁡n recursive calls, each store need m space to store the result, so space complexity is O(m⋅log⁡n)
