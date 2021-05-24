/**
Refer to
https://leetcode.com/problems/reorganize-string/
Given a string s, check if the letters can be rearranged so that two characters that are adjacent to each other are not the same.

If possible, output any possible result.  If not possible, return the empty string.

Example 1:
Input: s = "aab"
Output: "aba"

Example 2:
Input: s = "aaab"
Output: ""

Note:
s will consist of lowercase letters and have length in range [1, 500].
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/reorganize-string/discuss/232469/Java-No-Sort-O(N)-0ms-beat-100
/**
No Sort O(N):

count letter appearance and store in hash[i]
find the letter with largest occurence.
put the letter into even index numbe (0, 2, 4 ...) char array
put the rest into the array
    public String reorganizeString(String S) {
        int[] hash = new int[26];
        for (int i = 0; i < S.length(); i++) {
            hash[S.charAt(i) - 'a']++;
        } 
        int max = 0, letter = 0;
        for (int i = 0; i < hash.length; i++) {
            if (hash[i] > max) {
                max = hash[i];
                letter = i;
            }
        }
        if (max > (S.length() + 1) / 2) {
            return ""; 
        }
        char[] res = new char[S.length()];
        int idx = 0;
        while (hash[letter] > 0) {
            res[idx] = (char) (letter + 'a');
            idx += 2;
            hash[letter]--;
        }
        for (int i = 0; i < hash.length; i++) {
            while (hash[i] > 0) {
                if (idx >= res.length) {
                    idx = 1;
                }
                res[idx] = (char) (i + 'a');
                idx += 2;
                hash[i]--;
            }
        }
        return String.valueOf(res);
    }
Time O(N): fill hash[] + find the letter + write results into char array
Space O(N + 26): result + hash[]
*/

// Refer to
// https://leetcode.com/problems/reorganize-string/discuss/232469/Java-No-Sort-O(N)-0ms-beat-100/396190
/**
We construct the resulting string in sequence: at position 0, 2, 4, ... and then 1, 3, 5, ...
In this way, we can make sure there is always a gap between the same characters

Consider this example: "aaabbbcdd", we will construct the string in this way:

a _ a _ a _ _ _ _ // fill in "a" at position 0, 2, 4
a b a _ a _ b _ b // fill in "b" at position 6, 8, 1
a b a c a _ b _ b // fill in "c" at position 3
a b a c a d b d b // fill in "d" at position 5, 7
*/
class Solution {
    public String reorganizeString(String s) {
        int n = s.length();
        int[] freq = new int[26];
        for(char c : s.toCharArray()) {
            freq[c - 'a']++;
        }
        int maxFreq = 0;
        int maxFreqIndex = 0;
        for(int i = 0; i < 26; i++) {
            if(freq[i] > maxFreq) {
                maxFreq = freq[i];
                maxFreqIndex = i;
            }
        }
        // If maximum characater frequency over half, not possible
        if(maxFreq > (n + 1) / 2) {
            return "";
        }
        /**
        Consider this example: "aaabbbcdd", we will construct the string in this way:
        a _ a _ a _ _ _ _ // fill in "a" at position 0, 2, 4
        a b a _ a _ b _ b // fill in "b" at position 6, 8, 1
        a b a c a _ b _ b // fill in "c" at position 3
        a b a c a d b d b // fill in "d" at position 5, 7
        */
        char[] result = new char[n];
        int index = 0;
        while(freq[maxFreqIndex] > 0) {
            result[index] = (char)(maxFreqIndex + 'a');
            index += 2;
            freq[maxFreqIndex]--;
        }
        for(int i = 0; i < 26; i++) {
            while(freq[i] > 0) {
                // Very smart part to continue assign from start positions
                if(index >= n) {
                    index = 1;
                }
                result[index] = (char)(i + 'a');
                index += 2;
                freq[i]--;
            }
        }
        return new String(result);
    }
}
