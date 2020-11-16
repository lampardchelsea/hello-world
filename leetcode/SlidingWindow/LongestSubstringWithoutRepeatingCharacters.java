/**
Refer to
https://leetcode.com/problems/longest-substring-without-repeating-characters/
Given a string s, find the length of the longest substring without repeating characters.

Example 1:
Input: s = "abcabcbb"
Output: 3
Explanation: The answer is "abc", with the length of 3.

Example 2:
Input: s = "bbbbb"
Output: 1
Explanation: The answer is "b", with the length of 1.

Example 3:
Input: s = "pwwkew"
Output: 3
Explanation: The answer is "wke", with the length of 3.
Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.

Example 4:
Input: s = ""
Output: 0

Constraints:
0 <= s.length <= 5 * 104
s consists of English letters, digits, symbols and spaces.
*/

// Solution 1: Not fixed length sliding window
// Template from
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/SlidingWindow/MaxConsecutiveOnesIII.java
// Runtime: 59 ms, faster than 16.45% of Java online submissions for Longest Substring Without Repeating Characters.
// Memory Usage: 39.6 MB, less than 34.67% of Java online submissions for Longest Substring Without Repeating Characters.
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int maxLen = 0;
        Map<Character, Integer> freq = new HashMap<Character, Integer>();
        int i = 0;
        for(int j = 0; j < s.length(); j++) {
            char c = s.charAt(j);
            freq.put(c, freq.getOrDefault(c, 0) + 1);
            while(anyOverOne(freq)) {
                freq.put(s.charAt(i), freq.get(s.charAt(i)) - 1);
                i++;
            }
            maxLen = Math.max(maxLen, j - i + 1);
        }
        return maxLen;
    }
    
    private boolean anyOverOne(Map<Character, Integer> map) {
        for(int v : map.values()) {
            if(v > 1) {
                return true;
            }
        }
        return false;
    }
} 

// Solution 2: A great promotion on how to avoid check all map values for over 1 exist or not ?
// This map store different content -> {key, value} => {character, position} not {character, frequency}
// Refer to
// https://leetcode.com/problems/longest-substring-without-repeating-characters/discuss/1729/11-line-simple-Java-solution-O(n)-with-explanation
/**
The basic idea is, keep a hashmap which stores the characters in string as keys and their positions as values, and keep two 
pointers which define the max substring. move the right pointer to scan through the string , and meanwhile update the hashmap. 
If the character is already in the hashmap, then move the left pointer to the right of the same character last found. 
Note that the two pointers can only move forward.

   public int lengthOfLongestSubstring(String s) {
        if (s.length()==0) return 0;
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        int max=0;
        for (int i=0, j=0; i<s.length(); ++i){
            if (map.containsKey(s.charAt(i))){
                j = Math.max(j,map.get(s.charAt(i))+1);
            }
            map.put(s.charAt(i),i);
            max = Math.max(max,i-j+1);
        }
        return max;
    }
*/
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int maxLen = 0;
        Map<Character, Integer> freq = new HashMap<Character, Integer>();
        int i = 0;
        for(int j = 0; j < s.length(); j++) {
            char c = s.charAt(j);
            if(freq.containsKey(c)) {
                i = Math.max(i, freq.get(c) + 1);
            }
            freq.put(c, j); // map store {character, position} not {character, frequency}
            maxLen = Math.max(maxLen, j - i + 1);
        }
        return maxLen;
    }
}

