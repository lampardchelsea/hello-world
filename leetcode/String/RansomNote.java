/**
 * Given an arbitrary ransom note string and another string containing letters from 
 * all the magazines, write a function that will return true if the ransom note can 
 * be constructed from the magazines ; otherwise, it will return false.
 * Each letter in the magazine string can only be used once in your ransom note.
 * Note:
 * You may assume that both strings contain only lowercase letters.
 * canConstruct("a", "b") -> false
 * canConstruct("aa", "ab") -> false
 * canConstruct("aa", "aab") -> true
*/

// Refer to
// https://discuss.leetcode.com/topic/53864/java-o-n-solution-easy-to-understand
public class Solution {
    public boolean canConstruct(String ransomNote, String magazine) {
        int[] table = new int[26];
        for(int i = 0; i < 26; i++) {
            table[i] = 0;
        }
        int i = 0;
        while(i < magazine.length()) {
            char c = magazine.charAt(i++);
            table[c - 'a']++;
        }
        int j = 0;
        while(j < ransomNote.length()){
            char c = ransomNote.charAt(j++);
            table[c - 'a']--;
        }
        for(int k = 0; k < 26; k++) {
            if(table[k] < 0) {
                return false;
            }
        }
        return true;
    }
}
