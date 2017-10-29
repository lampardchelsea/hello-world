import java.util.HashMap;
import java.util.Map;

/**
 * Refer to
 * https://leetcode.com/problems/longest-substring-without-repeating-characters/#/description
 * 
 * Solution
 * https://discuss.leetcode.com/topic/8232/11-line-simple-java-solution-o-n-with-explanation
 * https://leetcode.com/articles/longest-substring-without-repeating-characters/
 */

// Solution 1:
class Solution {
    /**
     Approach #1 Brute Force [Time Limit Exceeded]
     Intuition
     Check all the substring one by one to see if it has no duplicate character.
     
     Algorithm
     Suppose we have a function boolean allUnique(String substring) which will return 
     true if the characters in the substring are all unique, otherwise false. We can 
     iterate through all the possible substrings of the given string s and call the 
     function allUnique. If it turns out to be true, then we update our answer of the 
     maximum length of substring without duplicate characters.
     
     Now let's fill the missing parts:
     To enumerate all substrings of a given string, we enumerate the start and end 
     indices of them. Suppose the start and end indices are i and j, respectively. 
     Then we have n0 ≤ i < j ≤ n (here end index j is exclusive by convention). 
     Thus, using two nested loops with i from 0 to n−1 and j from i+1 to n, we can 
     enumerate all the substrings of s.
     
     To check if one string has duplicate characters, we can use a set. We iterate 
     through all the characters in the string and put them into the set one by one. 
     Before putting one character, we check if the set already contains it. If so, 
     we return false. After the loop, we return true.
     
     Complexity Analysis
     Time complexity : O(n^3)
     Space complexity : O(min(n,m)). We need O(k) space for checking a substring 
                        has no duplicate characters, where k is the size of the Set. 
                        The size of the Set is upper bounded by the size of the 
                        string n and the size of the charset/alphabet m.
    */
    public int lengthOfLongestSubstring(String s) {
        if(s == null || s.length() == 0) {
            return 0;
        }
        int result = 0;
        int n = s.length();
        for(int i = 0; i < n; i++) {
            // Note: The char at index 'j' exclusive in substring
            // based on String.substring() method, and substring length
            // is (j - i), not (j - i + 1) as char at 'j' not include
            for(int j = 1; j <= n; j++) {
                if(allUnique(s, i, j)) {
                    result = Math.max(result, j - i);
                }
            }
        }
        return result;
    }
    
    private boolean allUnique(String s, int start, int end) {
        Set<Character> set = new HashSet<Character>();
        for(int i = start; i < end; i++) {
            Character c = s.charAt(i);
            if(set.contains(c)) {
                return false;
            }
            set.add(c);
        }
        return true;
    }
}


// Solution 2:
class Solution {
    /**
     Approach #2 Sliding Window [Accepted]
     Algorithm
     The naive approach is very straightforward. But it is too slow. So how can we optimize it?
     In the naive approaches, we repeatedly check a substring to see if it has duplicate 
     character. But it is unnecessary. If a substring sij from index i to j−1 is already 
     checked to have no duplicate characters. We only need to check if s[j] is already 
     in the substring sij.
     To check if a character is already in the substring, we can scan the substring, which 
     leads to an O(n^2) algorithm. But we can do better.
     By using HashSet as a sliding window, checking if a character in the current can be done in O(1).
     A sliding window is an abstract concept commonly used in array/string problems. A window is 
     a range of elements in the array/string which usually defined by the start and end indices, 
     i.e. [i, j) (left-closed, right-open). A sliding window is a window "slides" its two boundaries 
     to the certain direction. For example, if we slide [i, j) to the right by 11 element, then it 
     becomes [i+1, j+1) (left-closed, right-open).
     Back to our problem. We use HashSet to store the characters in current window [i, j) (j=i initially). 
     Then we slide the index j to the right. If it is not in the HashSet, we slide j further. 
     Doing so until s[j] is already in the HashSet. At this point, we found the maximum size of 
     substrings without duplicate characters start with index i. If we do this for all i, 
     we get our answer.
     
     Complexity Analysis
     Time complexity : O(2n) = O(n). In the worst case each character will be visited twice by i and j.
     Space complexity : O(min(m,n)). Same as the previous approach. We need O(k) space for the sliding 
                        window, where k is the size of the Set. The size of the Set is upper bounded 
                        by the size of the string n and the size of the charset/alphabet mm.
    */
    public int lengthOfLongestSubstring(String s) {
        if(s == null || s.length() == 0) {
            return 0;
        }
        int result = 0;
        int n = s.length();
        Set<Character> set = new HashSet<Character>();
        int j = 0;
        for(int i = 0; i < n; i++) {
            while(j < n) {
                Character c = s.charAt(j);
                if(!set.contains(c)) {
                    set.add(c);
                    result = Math.max(result, j - i + 1);
                    j++;
                    // result = Math.max(result, j - i);
                } else {
                    set.remove(s.charAt(i));
                    i++;
                }
            }
        }
        return result;
    }
}



// Solution 3: 
// Refer to
// https://discuss.leetcode.com/topic/8232/11-line-simple-java-solution-o-n-with-explanation
/**
 * The basic idea is, keep a hashmap which stores the characters 
 * in string as keys and their positions as values, and keep two 
 * pointers which define the max substring. move the right pointer 
 * to scan through the string , and meanwhile update the hashmap. 
 * If the character is already in the hashmap, then move the left 
 * pointer to the right of the same character last found. 
 * Note that the two pointers can only move forward.
*/
public class LongestSubstringWithoutRepeatingCharacters {
    public int lengthOfLongestSubstring(String s) {
        if(s.length() == 0) {
            return 0;
        }
        int maxLen = 0;
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        int newStart = 0;
        for(int i = 0; i < s.length(); i++) {
            if(map.containsKey(s.charAt(i))) {
//                newStart = map.get(s.charAt(i)) + 1;
            	// Very important !!!
            	// Must compare the current newStart with updated next position of
            	// char which encounter again, and only keep the larger one
            	// e.g given "abba", the newStart first because of encounter
            	// 'b' again will assign as 2, but next time, when encounter 
            	// 'a' again, if not select the larger value between that two
            	// values, it will drop back to 1, which based only on next
            	// position of char 'a' which encounter again, but actually
            	// the larger newStart value should come from previous newStart
            	// value which encounter 'b' again.
            	newStart = Math.max(newStart, map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i), i);
            maxLen = Math.max(maxLen, i - newStart + 1);
        }
        return maxLen;
    }
    
    public static void main(String[] args) {
    	LongestSubstringWithoutRepeatingCharacters x = new LongestSubstringWithoutRepeatingCharacters();
    	String s = "abba";
    	int result = x.lengthOfLongestSubstring(s);
    	System.out.println(result);
    }
}

