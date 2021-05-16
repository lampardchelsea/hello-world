/**
Refer to
https://leetcode.com/problems/custom-sort-string/
order and str are strings composed of lowercase letters. In order, no letter occurs more than once.

order was sorted in some custom order previously. We want to permute the characters of str so that they match the order that 
order was sorted. More specifically, if x occurs before y in order, then x should occur before y in the returned string.

Return any permutation of str (as a string) that satisfies this property.

Example:
Input: 
order = "cba"
str = "abcd"
Output: "cbad"
Explanation: 
"a", "b", "c" appear in order, so the order of "a", "b", "c" should be "c", "b", and "a". 
Since "d" does not appear in order, it can be at any position in the returned string. "dcba", "cdba", "cbda" are also valid outputs.

Note:
order has length at most 26, and no character is repeated in order.
str has length at most 200.
order and str consist of lowercase letters only.
*/
class Solution {
    public String customSortString(String order, String str) {
        // Build freq map for 'str' which pending on sort
        int[] freq = new int[26];
        for(char c : str.toCharArray()) {
            freq[c - 'a']++;
        }
        StringBuilder sb = new StringBuilder();
        // Select out character in same order from freq map based on 'order'
        for(char c : order.toCharArray()) {
            int count = freq[c - 'a'];
            for(int i = 0; i < count; i++) {
                sb.append(c);
            }
            // Since used up current character, remove it from freq map
            freq[c - 'a'] = 0;
        }
        // Left characters identified by frequency still > 0 on freq map
        // append on tail
        for(int i = 0; i < 26; i++) {
            if(freq[i] > 0) {
                for(int j = 0; j < freq[i]; j++) {
                    sb.append((char)(i + 'a'));
                }
            }
        }
        return sb.toString();
    }
}
