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


Solution 1 (360min, too long because try to understand what is Divide and Conquer ? what is the difference between Divide and Conquer and Traversal ?)

class Solution {
    public int longestSubstring(String s, int k) {
        // Why we can use Divide and Conquer ?
        // Basic idea it's to use those char which counting is smaller 
        // than k as a 'wall' and divide the string into two parts and 
        // use recursion on the two parts
        return helper(s, 0, s.length(), k);       
    }
   
    private int helper(String s, int start, int end, int k) {
        // Base case 1:
        // If this substring is shorter than k, then no characters in it
        // can be repeated k times, therefore this substring and all substrings 
        // that could be formed from it are invalid, therefore return 0
        if(end - start < k) {
            return 0;
        }
        // Divide
        // Count the frequency of characters in this substring
        int[] freq = new int[26];
        for(int i = start; i < end; i++) {
            freq[s.charAt(i) - 'a']++;
        }
        // Find all pivot indexes if character occurs at least once, but fewer 
        // than k times in this substring, we know:
        // (1) this character cannot be part of the longest valid substring,
        // (2) the current substring is not valid.
        // Hence, we will "split" this substring on this character, wherever it 
        // occurs, and check the substrings formed by that split
        for(int i = start; i < end; i++) {
            if(freq[s.charAt(i) - 'a'] > 0 && freq[s.charAt(i) - 'a'] < k) {
                int left = helper(s, start, i, k);
                int right = helper(s, i + 1, end, k);
                // Conquer
                return Math.max(left, right);
            }
        }
        // Base case 2:
        // If every character in this substring occurs at least k times,
        // then this is a valid substring, so return this substring's length
        return end - start;
    }
}

Space Complexity: O(n)
This is the space used to store the recursive call stack. The maximum depth of recursive call stack would be O(n)
Time Complexity: O(n^2)
Where n is the length of string s, though the algorithm performs better in most cases, the worst case time complexity 
is still O(n^2), In cases where we perform split at every index, the maximum depth of recursive call could be O(n) 
rather than O(logn). For each recursive call it takes O(n) time to build the countMap resulting in O(n^2) time complexity

Solution 2 (180min, too long since sliding window not intuitive at all, hard to come up with outside for loop on maxUniqueChars)
class Solution {
    public int longestSubstring(String s, int k) {
        int len = s.length();
        // Find the number of unique characters in the string s and 
        // store the count in variable maxUniqueChars. For s = aabcbacad, 
        // the unique characters are a,b,c,d and maxUniqueChars = 4
        int maxUniqueChars = 0;
        boolean[] existChars = new boolean[26];
        for(int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if(!existChars[c - 'a']) {
                maxUniqueChars++;
                existChars[c - 'a'] = true;
            }
        }
        // Iterate over the string s with the value of n ranging from 1 to 
        // maxUniqueChars. In each iteration, n is the maximum number of 
        // unique characters that must be present in the sliding window
        int maxLen = 0;
        for(int n = 1; n <= maxUniqueChars; n++) {
            maxLen = Math.max(maxLen, longestSubstringWithGivenNUniqueChars(s, k, n));
        }
        return maxLen;
    }
    
    private int longestSubstringWithGivenNUniqueChars(String s, int k, int maxUniqueCharsLimit) {
        int maxLenWithUniqueCharsLimit = 0;
        int[] freq = new int[26];
        // 'uniqueChars' denotes count of unique character in current window
        int uniqueChars = 0;
        // 'charsFreqNoLessThanK' denotes count of unique character frequency no less than k
        int charsFreqNoLessThanK = 0;
        int len = s.length();
        // The sliding window starts at index 'i' and ends at index 'j' and 
        // slides over string s until 'j' reaches the end of string s. At 
        // any given point, we shrink or expand the window to ensure that 
        // the number of unique characters is not greater than maxUniqueCharsLimit
        int i = 0;
        // If the count of unique character in the sliding window is less 
        // than or equal to maxUniqueCharsLimit, expand the window from the 
        // right by adding a character to the end of the window given by 'j'
        for(int j = 0; j < len; j++) {
            char c = s.charAt(j);
            // Seen a new character increase the unique character count
            if(freq[c - 'a'] == 0) {
                uniqueChars++;
            }
            freq[c - 'a']++;
            // When character frequency equal to k increase no less than k count
            if(freq[c - 'a'] == k) {
                charsFreqNoLessThanK++;
            }
            // Shrink the window from the left by removing a character from the 
            // start of the window given by 'i'
            while(uniqueChars > maxUniqueCharsLimit) {
                char c1 = s.charAt(i);
                if(freq[c1 - 'a'] == k) {
                    charsFreqNoLessThanK--;
                }
                freq[c1 - 'a']--;
                if(freq[c1 - 'a'] == 0) {
                    uniqueChars--;
                }
                i++;
            }
            // Keep track of the number of unique characters in the current sliding 
            // window having at least k frequency given by charsFreqNoLessThanK. Update 
            // the result if all the characters in the window have at least k frequency
            if(uniqueChars == maxUniqueCharsLimit && uniqueChars == charsFreqNoLessThanK) {
                maxLenWithUniqueCharsLimit = Math.max(maxLenWithUniqueCharsLimit, j - i + 1);
            }
        }
        return maxLenWithUniqueCharsLimit;
    }
}

Space Complexity: O(1)
We use constant extra space of size 26 to store the existChars
Time Complexity: O(maxUnique * n)
We iterate over the string of length n, maxUnqiue times. Ideally, the number of unique characters 
in the string would not be more than 26 (a to z). Hence, the time complexity is approximately O(26⋅n) = O(n)
