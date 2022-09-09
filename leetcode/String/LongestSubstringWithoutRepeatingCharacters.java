class Solution {
    public int lengthOfLongestSubstring(String s) {
        if(s == null || s.length() == 0) {
            return 0;
        }
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        // Caution: not create frequency map, just create bucket and set as 0 as
        // template did
        // Refer to
        // https://leetcode.com/problems/minimum-window-substring/discuss/26808/Here-is-a-10-line-template-that-can-solve-most-'substring'-problems/25816
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            map.put(c, 0);
        }
        int start = 0;
        int end = 0;
        int maxLen = Integer.MIN_VALUE;
        int count = 0; // count represent repeated character number
        while(end < s.length()) {
            // if this character already exist in map, meet again will
            // increase number as repeated
            if(map.get(s.charAt(end)) > 0) {
                count++;
            }
            map.put(s.charAt(end), map.get(s.charAt(end)) + 1);
            end++;
            // if found repeated character, need to move start indicator forward
            // to remove start characters on substring to make it without
            // repeated characters again
            while(count > 0) {
                if(map.get(s.charAt(start)) > 1) {
                    count--;
                }
                map.put(s.charAt(start), map.get(s.charAt(start)) - 1);
                start++;
            }
            maxLen = Math.max(maxLen, end - start);
        }
        return maxLen;
    }
}






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

Attempt 1: 2022-09-09

Solution 1 (10min)

class Solution { 
    public int lengthOfLongestSubstring(String s) { 
        int len = s.length(); 
        Map<Character, Integer> freq = new HashMap<Character, Integer>(); 
        int maxLen = Integer.MIN_VALUE; 
        // 'i' means left end pointer, 'j' means right end pointer 
        int i = 0; 
        for(int j = 0; j < len; j++) { 
            char c = s.charAt(j); 
            freq.put(c, freq.getOrDefault(c, 0) + 1); 
            int size = freq.size(); 
            // When map size less than substring length means repeating characters exist 
            // To resolve repeating character requires shrink subtring by moving left end 
            // pointer to right 
            while(size < j - i + 1) { 
                // Remove 'c1' and update its frequency on map 
                char c1 = s.charAt(i); 
                freq.put(c1, freq.get(c1) - 1); 
                // If 'c1' frequency drop to 0 means not exist on current substring, remove 
                // 'c1' from map key, update map size 
                if(freq.get(c1) == 0) { 
                    freq.remove(c1); 
                    size--; 
                } 
                // Shrink left end pointer one position 
                i++; 
            } 
            maxLen = Math.max(maxLen, j - i + 1); 
        } 
        return maxLen == Integer.MIN_VALUE ? 0 : maxLen; 
    } 
}

Space Complexity: O(n)
Time Complexity: O(n)

https://leetcode.com/problems/minimum-size-subarray-sum/discuss/59078/Accepted-clean-Java-O(n)-solution-(two-pointers)/924580 
For those trying to figure out how is it O(n): 
Here we have defined 2 index i & j, 
In case of O(n^2) for each outer loop, inner loop runs some n or m number of times to make it O(nm), that means, as soon as the 
outer loop finishes one iteration, inner loop resets itself.  
In case of O(n2), as in this case, we are not resetting the inner inner variable i, it's just incrementing each time. It is like 
2 loops one after another and both runs n number of time.

Solution 2 (180min, too long for recollecting how to truncate duplicate characters by tracking left end pointer without removing 
actual map key character like Solution 1)

class Solution {
    public int lengthOfLongestSubstring(String s) {
        int len = s.length();
        // Map store different content -> {key, value} => {character, position} not {character, frequency}
        Map<Character, Integer> char_index_map = new HashMap<Character, Integer>();
        int maxLen = Integer.MIN_VALUE;
        // 'i' means left end pointer, 'j' means right end pointer
        int i = 0;
        for(int j = 0; j < len; j++) {
            char c = s.charAt(j);
            // Once we've landed on a character we've seen before, 
            // we want to move the left pointer of our window to the 
            // index after the "LAST" occurrence of that character.
            // e.g "abba" 
            // Initially left end pointer i = 0, right end pointer j keep
            // moving to right, when j = 2, find second 'b', since 
            // we have seen 'b' when j = 1, 'b' is a duplicate character,
            // to remove duplication, we can move the left pointer of our 
            // window as i = 0 to the index after the "LAST" occurrence 
            // of 'b', which means update i from 0 to 2 ('LAST' occurrence 
            // index = 1, plus 1 to skip it to next index, then 1 + 1 = 2), 
            // when j = 3, find second 'a', since we have seen 'a' when 
            // j = 0, 'a' is another duplicate character, we can move 
            // the left pointer i from 2 back to 1 (0 + 1 = 1) ?? 
            // That's the issue, when current left end pointer i's index
            // is larger than what it suppose to be, that means current 
            // duplicate character (e.g 'a' here) already truncated during 
            // previous index update for other duplicate character 
            // (e.g 'b' here), we just need to keep current position to 
            // avoid left end pointer drop back.
            // In simple, we don't need to move back left end pointer i
            // index from 2 to 1 for duplicate 'a' because the first 'a'
            // already truncated when handling duplicate 'b' by updating
            // i from 0 to 2 (The index = 0 and index = 1 two characters
            // "ab" has been removed, remain substring as "ba", actually 
            // no duplicate 'a' when we see the second 'a')
            if(char_index_map.containsKey(c)) {
                i = Math.max(i, char_index_map.get(c) + 1);
            }
            char_index_map.put(c, j);
            maxLen = Math.max(maxLen, j - i + 1);
        }
        return maxLen == Integer.MIN_VALUE ? 0 : maxLen;
    }
}

Space Complexity: O(n)
Time Complexity: O(n)
