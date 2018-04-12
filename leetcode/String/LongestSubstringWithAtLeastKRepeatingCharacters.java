/**
 * Refer to
 * https://leetcode.com/problems/longest-substring-with-at-least-k-repeating-characters/description/
 * Find the length of the longest substring T of a given string (consists of lowercase letters only) 
   such that every character in T appears no less than k times.

    Example 1:

    Input:
    s = "aaabb", k = 3

    Output:
    3

    The longest substring is "aaa", as 'a' is repeated 3 times.
    Example 2:

    Input:
    s = "ababbc", k = 2

    Output:
    5

    The longest substring is "ababb", as 'a' is repeated 2 times and 'b' is repeated 3 times.
 *
 *
 * Solution
 * https://leetcode.com/problems/longest-substring-with-at-least-k-repeating-characters/discuss/87739/Java-Strict-O(N)-Two-Pointer-Solution
*/
class Solution {
    public int longestSubstring(String s, int k) {
        int maxLen = 0;
        int numUniqueTarget = 0;
        for(; numUniqueTarget < 26; numUniqueTarget++) {
            maxLen = Math.max(maxLen, helper(s, k, numUniqueTarget));
        }
        return maxLen;
    }
    
    private int helper(String s, int k, int numUniqueTarget) {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for(int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), 0);
        }
        int numUnique = 0;
        int numNoLessThanK = 0;
        int start = 0;
        int end = 0;
        int result = Integer.MIN_VALUE;
        while(end < s.length()) {
            if(map.get(s.charAt(end)) == 0) {
                numUnique++;
            }
            // increment map[c] after this statement
            map.put(s.charAt(end), map.get(s.charAt(end)) + 1);
            if(map.get(s.charAt(end)) == k) {
                numNoLessThanK++;
            }
            // increment end after this statement
            end++;
            while(numUnique > numUniqueTarget) {
                if(map.get(s.charAt(start)) == k) {
                    numNoLessThanK--;
                }
                // decrement map[c] after this statement
                map.put(s.charAt(start), map.get(s.charAt(start)) - 1);
                if(map.get(s.charAt(start)) == 0) {
                    numUnique--;
                }
                // increment start after this statement
                start++;
            }
            // if we found a string where the number of unique chars equals our target
            // and all those chars are repeated at least K times then update max
            if(numUnique == numUniqueTarget && numUnique == numNoLessThanK) {
                result = Math.max(end - start, result);
            }
        }
        return result;
    }
}
