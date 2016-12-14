/**
 * Given two strings s and t, write a function to determine if t is an anagram of s.
 * For example,
 * s = "anagram", t = "nagaram", return true.
 * s = "rat", t = "car", return false.
 * Note:
 * You may assume the string contains only lowercase alphabets.
 * Follow up:
 * What if the inputs contain unicode characters? How would you adapt your solution to such case?
*/
// Solution 1: 哈希表法
// 复杂度
// 时间 O(K) 空间 O(1)
// 思路
// 变形词的本质是两个单词中，每个字母出现的次数相同，所以我们可以用一个哈希表，记录第一个单词中每个字母的个数，
// 再遍历第二个单词，减去相应的字母出现次数，如果某个字母的计数器不为0了，则说明某个字母出现的次数不一样。
// 这里只用了一个大小为26的数组，是假设只会出现英文字母。
public class Solution {
    public boolean isAnagram(String s, String t) {
        if(s.length() != t.length()) {
            return false;
        }
        int[] table = new int[26];
        int length = s.length();
        for(int i = 0; i < length; i++) {
            table[s.charAt(i) - 'a']++;
            table[t.charAt(i) - 'a']--;
        }
        for(int i = 0; i < table.length; i++) {
            if(table[i] != 0) {
                return false;
            }
        }
        return true;
    }
}


// Solution 2:
