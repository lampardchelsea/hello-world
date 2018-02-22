/**
 * Refer to
 * https://leetcode.com/problems/first-unique-character-in-a-string/description/
 * Given a string, find the first non-repeating character in it and return it's index. If it doesn't exist, return -1.
 
    Examples:

    s = "leetcode"
    return 0.

    s = "loveleetcode",
    return 2.
    Note: You may assume the string contain only lowercase letters.
 * 
 * Solution
 * https://www.youtube.com/watch?v=_vPDoOVNlKQ
 * https://leetcode.com/problems/first-unique-character-in-a-string/discuss/86348/java-7-lines-solution-29ms/91418?page=1
   Hey guys. My solution is pretty straightforward. It takes O(n) and goes through the string twice:
   Get the frequency of each character.
   Get the first character that has a frequency of one.
*/
// Solution 1: With HashMap
class Solution {
    public int firstUniqChar(String s) {
        if(s == null || s.length() == 0) {
            return -1;
        }
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        int len = s.length();
        for(int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if(!map.containsKey(c)) {
                map.put(c, 1);
            } else {
                map.put(c, map.get(c) + 1);
            }
        }
        for(int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if(map.get(c) == 1) {
                return i;
            }
        }
        return -1;
    }
}

// Solution 2: With limitation from 'a' to 'z' provide just use array as dictionary
class Solution {
    public int firstUniqChar(String s) {
        if(s == null || s.length() == 0) {
            return -1;
        }
        // Since condition as you may assume the string contain only lowercase letters.
        // which means only 'a' to 'z'
        int[] dict = new int[26];
        int len = s.length();
        for(int i = 0; i < len; i++) {
            dict[s.charAt(i) - 'a']++;
        }
        for(int i = 0; i < len; i++) {
            if(dict[s.charAt(i) - 'a'] == 1) {
                return i;
            }
        }
        return -1;
    }
}
