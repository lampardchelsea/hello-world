/**
Refer to
https://leetcode.com/problems/determine-if-string-halves-are-alike/
You are given a string s of even length. Split this string into two halves of equal lengths, and let a be the first half and b be the second half.

Two strings are alike if they have the same number of vowels ('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'). 
Notice that s contains uppercase and lowercase letters.

Return true if a and b are alike. Otherwise, return false.

Example 1:
Input: s = "book"
Output: true
Explanation: a = "bo" and b = "ok". a has 1 vowel and b has 1 vowel. Therefore, they are alike.

Example 2:
Input: s = "textbook"
Output: false
Explanation: a = "text" and b = "book". a has 1 vowel whereas b has 2. Therefore, they are not alike.
Notice that the vowel o is counted twice.

Example 3:
Input: s = "MerryChristmas"
Output: false

Example 4:
Input: s = "AbCdEfGh"
Output: true

Constraints:
2 <= s.length <= 1000
s.length is even.
s consists of uppercase and lowercase letters.
*/

// Solution 1:
// Style 1: Two Pass
class Solution {
    public boolean halvesAreAlike(String s) {
        int len = s.length() / 2;
        String s1 = s.substring(0, len);
        String s2 = s.substring(len);
        Character[] vowels = new Character[] {'a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'};
        Set<Character> set = new HashSet<Character>(Arrays.asList(vowels));
        int c1 = 0;
        int c2 = 0;
        for(char c : s1.toCharArray()) {
            if(set.contains(c)) {
                c1++;
            }
        }
        for(char c : s2.toCharArray()) {
            if(set.contains(c)) {
                c2++;
            }
        }
        return c1 == c2;
    }
}

// Style 2: One Pass
// Refer to
// https://leetcode.com/problems/determine-if-string-halves-are-alike/discuss/988203/JavaPython-3-Use-Set-to-check-equality./800277
class Solution {
    public boolean halvesAreAlike(String s) {
        Set<Character> set = new HashSet<Character>(Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));
        int i = 0;
        int j = s.length() - 1;
        int count = 0;
        while(i < j) {
            if(set.contains(s.charAt(i++))) {
                count++;
            }
            if(set.contains(s.charAt(j--))) {
                count--;
            }
        }
        return count == 0;
    }
}
