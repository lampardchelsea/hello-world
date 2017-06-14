import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Solution 2
 * https://discuss.leetcode.com/topic/39585/o-n-k-2-java-solution-with-trie-structure-n-total-number-of-words-k-average-length-of-each-word
 * Apparently there is a O(n^2*k) naive solution for this problem, with n the total number of words in the "words" 
 * array and k the average length of each word:
 * For each word, we simply go through the words array and check whether the concatenated string is a palindrome or not.
 * Of course this will result in TLE as expected. To improve the algorithm, we need to reduce the number of words that 
 * is needed to check for each word, instead of iterating through the whole array. This prompted me to think if I can 
 * extract any useful information out of the process of checking whether the concatenated string is a palindrome, 
 * so that it can help eliminate as many words as possible for the rest of the words array.
 * 
 * To begin, here is the technique I employed to check for palindromes: maintain two pointers i and j, with i pointing to 
 * the start of the string and j to the end of the string. Characters pointed by i and j are compared. If at any time the 
 * characters pointed by them are not the same, we conclude the string is not a palindrome. Otherwise we move the two 
 * pointers towards each other until they meet in the middle and the string is a palindrome.
 * 
 * By examining the process above, I do find something that we may take advantage of to get rid of words that need to be 
 * checked otherwise. For example, let's say we want to append words to w0, which starts with character 'a'. Then we only 
 * need to consider words ending with character 'a', i.e., this will single out all words ending with character 'a'. 
 * If the second character of w0 is 'b' for instance, we can further reduce our candidate set to words ending with 
 * string "ba", etc. Our naive solution throws away all this "useful" information and repeats the comparison, which 
 * leads to the undesired O(n^2*k) time complexity.
 * 
 * In order to exploit the information gathered so far, we obviously need to restructure all the words in the words array. 
 * If you are familiar with Trie structure (I believe you are, since LeetCode has problems for it. In case you are not, 
 * see Trie), it will come to mind as we need to deal with words with common suffixes. The next step is to design the 
 * structure for each Trie node. There are at least two fields that should be covered for each TrieNode: a TrieNode 
 * array denoting the next layer of nodes and a boolean (or integer) to signify the end of a word. So our tentative 
 * TrieNode will look like this:
	
	class TrieNode {
	    TrieNode[] next;
	    boolean isWord;
	}
	
 * One point here is that we assume all the words contain lowercase letters only. This is not specified in the problem 
 * statement so you probably need to confirm with the interviewer (here I assume it is the case)
 * 
 * Now we will rearrange each word into this Trie structure: for each word, simply starting from its last character 
 * and identify the node at the next layer by indexing into root's next array with index given by the difference 
 * between the ending character and character 'a'. If the indexed node is null, create a new node. Continue to 
 * the next layer and towards the beginning of the word in this manner until we are done with the word, at which 
 * point we will label the isWord field of the final node as true.
 * 
 * After building up the Trie structure, we can proceed to search for pairs of palindromes for each word in the words 
 * array. I will use the following example to explain how it works and make possible modifications of the TrieNode we 
 * proposed above.
 * 
 * Let's say we have these words: ["ba", "a", "aaa"], the Trie structure will be as follows:
	
	        root (f)
	           | 'a'
	          n1 (t)
	     ------------
	 'b' |          | 'a'
	    n2 (t)    n3 (f)
	                | 'a'
	              n4 (t)
	
 * The letter in parentheses indicates the value of isWord for each node: f ==> false and t ==> true. The letter beside 
 * each vertical line denotes the index into the next array of the corresponding node. For example, for the first vertical 
 * line, 'a' means root.next[0] is not null. Similarly 'b' means n1.next[1] is not null, and so on.
 * 
 * Here is the searching process:
 * For word "ba", starting from the first character 'b', index into the root.next array with index given by 'b' - 'a' = 1. 
 * The corresponding node is null, then we know there are no words ending at this character, so the searching process is 
 * terminated;
 * For word "a", again indexing into array root.next at index given by 'a' - 'a' = 0 will yield node n1, which is not null. 
 * We then check the value of n1.isWord. If it is true, then it is possible to obtain a palindrome by appending this word 
 * to the one currently being examined (a.k.a word "a"). Also note that the two words should be distinct, but the n1.isWord 
 * field provides no information about the word itself, which makes it impossible to distinguish the two words. So it is 
 * necessary to modify the structure of the TrieNode so that we can identify the word it represents. One easy way is to 
 * have an integer field to remember the index of the word in the words array. For non-word nodes, this integer will take 
 * negative values (-1 for example) while for those representing a word, it will be non-negative values. Suppose we have 
 * made this modification, then the two words will be identified to be the same, so we discard this pair combination. 
 * Since the word "a" has only one letter, it seems we are done with it. Or do we? Not really. What if there are words 
 * with suffix "a" ("aaa" in this case)? We need to continue to check the rest part of these words (such as "aa" for the 
 * word "aaa") and see if the rest forms a palindrome. If it is, then appending this word ("aaa" in this case) to the 
 * original word ("a") will also form a palindrome ("aaaa"). Here I take another strategy: add an integer list to each 
 * TrieNode; the list will record the indices of all words satisfying the following two conditions: each word has a suffix 
 * represented by the current Trie node; the rest of the word forms a palindrome.
 * 
 * Before I get to the third word "aaa", let me spell out the new TrieNode and the corresponding Trie structure for the 
 * above array.
 * 
 * TrieNode:
	
	class TrieNode {
	    TrieNode[] next;
	    int index;
	    List<Integer> list;
	            
	    TrieNode() {
	        next = new TrieNode[26];
	        index = -1;
	        list = new ArrayList<>();
	    }
	}
	
	Trie structure:
	
	          root (-1,[1,2])
	            | 'a'
	          n1 (1,[0,1,2])
	    ---------------------
	'b' |                 | 'a'
	  n2 (0,[0])    n3 (-1,[2])
	                      | 'a'
	                 n4 (2,[2])
	
  * The first integer in the parentheses is the index of the word in the words" array (defaulted to -1). The integers 
  * in the square bracket are the indices of words satisfying the two conditions mentioned above.
  * 
  * Let's continue with the third word "aaa" with this new structure. Indexing into array root.next at index given by 
  * 'a' - 'a' = 0 will yield node n1 and n1.index = 1 >= 0, which means we have a valid word now. The index of this 
  * word (which is 1) is also different from the index of the word currently being visited, a.k.a "aaa" (which is 2). 
  * So pair (2,1) is a possible concatenation to form a palindrome. But still we need to check the rest of "aaa" 
  * (excluding the substring represented by current node n1 which is "a" from the beginning of "aaa") to see if 
  * it is a palindrome. If so, (2,1) will be a valid combination. We continue in this fashion until we reach the end 
  * of "aaa". Lastly we will check n4.list to see if there are any words satisfying the two conditions specified 
  * in step 2 which are different from current word, and add the corresponding valid pairs.
  * 
  * Both building and searching the Trie structure take O(n*k^2), which set the total time complexity of the solution. 
  * See the complete Java program in the answer.
 */
public class PalindromePairsTrie {
	class TrieNode {
		TrieNode[] next;
		int index;
		// Since the word "a" has only one letter, it seems we are done with it. Or do we? Not really. What if there are words 
		// with suffix "a" ("aaa" in this case)? We need to continue to check the rest part of these words (such as "aa" for the 
		// word "aaa") and see if the rest forms a palindrome. If it is, then appending this word ("aaa" in this case) to the 
		// original word ("a") will also form a palindrome ("aaaa"). 
		// Here I take another strategy: add an integer list to each TrieNode; 
		// the list will record the indices of all words satisfying the following two conditions: 
		// (1) each word has a suffix represented by the current Trie node; 
		// (2) the rest of the word forms a palindrome.
		List<Integer> list;
		public TrieNode() {
			next = new TrieNode[26];
			index = -1;
			list = new ArrayList<Integer>();
		}
	}
	
	public void addWord(TrieNode root, String word, int index) {
		// Important tips: The corner stone here is loop a word in backward
		// e.g given word = "dcba", adding sequence on TrieNode root is
		// 'a' -> 'b' -> 'c' -> 'd'
		for(int i = word.length() - 1; i >= 0; i--) {
			int j = word.charAt(i) - 'a';
			if(root.next[j] == null) {
				root.next[j] = new TrieNode();
			}
			if(isPalindrome(word, 0, i)) {
				root.list.add(index);
			}
			// Move on from current level to next level
			root = root.next[j];
		}
		// This line is for handling empty string case ?
		// As already reach the first character of string,
		// if recording its index means left substring is
		// "" empty string, and actually "" is palindrome
		// which satisfy two conditions set up for this list:
		// (1) each word has a suffix represented by the current Trie node; 
		//     --> current full string, e.g "dcba" has suffix Trie node "dcba"
		// (2) the rest of the word forms a palindrome.
		//     --> rest of the word of "abcd" is "" forms a palindrome
		// E.g if comment out this line, will error out as
		// 51/134 passed, and fail at
		// Input:["abcd","dcba","lls","s","sssll"]
		// Output:[[2,4],[3,2]]
		// Expected:[[0,1],[1,0],[2,4],[3,2]]
		root.list.add(index);
		// Update the index when a word scan finished
		root.index = index;
	}
	
	public void search(String[] words, int i, TrieNode root, List<List<Integer>> result) {
		for(int j = 0; j < words[i].length(); j++) {
			// (1) root.index >= 0 --> finished construct of current word
			// (2) root.index != i --> not the word itself(The index of this word is also different from the index of the word currently being visited)
			// (3) isPalindrome(...) 
			// --> E.g But still we need to check the rest of "aaa" (excluding the substring 
			//         represented by current node n1 which is "a" from the beginning of "aaa") 
			//         to see if it is a palindrome. If so, (2,1) will be a valid combination. 
			//         We continue in this fashion until we reach the end of "aaa"
			//         We start with index 'j' to 'words[i].length() - 1', no need to reverse the
			//         index checking order (e.g 'words[i].length() - 1' to 'j'), because we re-construct
			//         the word such as "aaa" from top root node to leaves node. The in order checking
			//         sequence here exactly implement check palindrome from top root node to leaves 
			//         node (e.g first check palindrome from 3rd 'a' to 1st 'a' in "aaa", then check 
			//         palindrome from 2nd 'a' to 1st 'a' in "aaa", then check 1st 'a' in "aaa")
			if(root.index >= 0 && root.index != i && isPalindrome(words[i], j, words[i].length() - 1)) {
				// Based on word at position i of words array and find a matching palindrome word
				// which at position root.index of words array
				result.add(Arrays.asList(i, root.index));
			}
			// Update to next level and try to find if that match conditions in next loop,
			// end with no more characters
			root = root.next[words[i].charAt(j) - 'a'];
			if(root == null) {
				return;
			}
		}
		
		// Lastly we will check current word's last node's list to see if there are any words 
		// satisfying the two conditions specified which are different from current word, 
		// and add the corresponding valid pairs.
		// E.g If miss this part, will cause 16 / 134 test cases passed.
		// Input: ["abcd","dcba","lls","s","sssll"]
		// Output: []
		// Expected: [[0,1],[1,0],[2,4],[3,2]] 
		/**
		 * There is a special analysis for this, initially, don't understand why we
		 * already loop the full word, still need to check one more node 'root'? And
		 * how is this one more node 'root' come from ?
		 * Explaination:
		 *  words:     abcd    dcba  lls  s  sssll
			position:	 0		1	  2   3    4
			The digital number after n means in when we construct this Trie, which
			step we reach which character, e.g (n5 means at step 5 we add last
			character of "dcba" as 'a' onto Trie, n6 means at step 6 we add second last
			character of "dcba" as 'b' onto Trie after step 5)
			abcd -> end at n8
			dcba -> end at n4 -> when construct Trie, "abcd" added onto Trie as "dcba" first
			lls  -> end at n11
			s    -> end at n9
			sssll-> end at n16
			
			   			           root (-1, [3])
					 / 'a'         | 'd'         | 'l'            \ 's'
				n5 (-1, [])	    n1 (-1, [])     n12 (-1, [])    n9 (3, [2,3])        
			        | 'b'          | 'c'         | 'l'            | 'l'
			    n6 (-1, [])     n2 (-1, [])		n13 (-1, [4])   n10 (-1, [2])
					| 'c'	       | 'b'         | 's'            | 'l'
				n7 (-1, [1])	n3 (-1, [0])    n14 (-1, [4])   n11 (2, [2])
					| 'd'		   | 'a'         | 's'
				n8 (1, [1])	    n4 (0, [0])     n15 (-1, [4])
				                                 | 's'
												n16 (4, [4]) 

	     * In this example, we first will search for "abcd" which locate at
	     * position 0 of words array, the special thing is we not directly
	     * reach the first character 'a' of "abcd", we have an additional
	     * "root" at the beginning before "abcd", so, we will loop 4 times
	     * to change the "root" (by root = root.next[words[i].charAt(j) - 'a'];)
	     * to reach the last character of current word (e.g when j increase
	     * from 0 to 3, "root" varies as "root" -> "a" -> "b" -> "c" -> "d"),
	     * and stay on the last character constructed node (e.g n8).
	     * Since we already pass the for loop because of 'j' already = 3, and
	     * increase to 4 which over length of "abcd" to terminate the for loop,
	     * but we only check lists from "root" -> "a" -> "b" -> "c", not yet
	     * checking list of node constructed by "d"(e.g here as n8), now we check
	     * its list as [1], which point out the word at position of 1 of words
	     * array as "dcba" satisfy a counter part of current word "abcd" to
	     * make up a palindrome, finally add this pair (i = 0, j = 1) to result.
	     * 
	     * Remind: 
	     * How is n8 (1, [1]) generated ?
	     * Two conditions:
	     * (1) Word "dcba" has a suffix represented by the current Trie node as "dcba"
	     * (2) Current word "abcd", left part empty string "" forms a palindrome
		 */
         for (int j : root.list) {
         	if(i == j) {
         		continue;
         	}
         	result.add(Arrays.asList(i, j));
         }
	}
	
	private boolean isPalindrome(String word, int i, int j) {
	    while (i < j) {
	    	if (word.charAt(i++) != word.charAt(j--)) {
	    		return false;
	    	}
	    }	    	
	    return true;
	}
	
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        TrieNode root = new TrieNode();
        for(int i = 0; i < words.length; i++) {
        	addWord(root, words[i], i);
        }
        for(int i = 0; i < words.length; i++) {
        	search(words, i, root, result);
        }
        return result;
    }
    
    public static void main(String[] args) {
    	PalindromePairsTrie p = new PalindromePairsTrie();
    	//String[] words = {"ba", "a", "aaa"};
    	String[] words = {"abcd","dcba","lls","s","sssll"};
    	List<List<Integer>> result = p.palindromePairs(words);
		for(List<Integer> a : result) {
			System.out.println("------------");
			for(Integer b : a) {
				System.out.println(b);
			}
		}
    }
}

