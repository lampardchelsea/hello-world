/**
Refer to
https://leetcode.com/problems/number-of-different-integers-in-a-string/
You are given a string word that consists of digits and lowercase English letters.

You will replace every non-digit character with a space. For example, "a123bc34d8ef34" will become " 123  34 8  34". 
Notice that you are left with some integers that are separated by at least one space: "123", "34", "8", and "34".

Return the number of different integers after performing the replacement operations on word.

Two integers are considered different if their decimal representations without any leading zeros are different.

Example 1:
Input: word = "a123bc34d8ef34"
Output: 3
Explanation: The three different integers are "123", "34", and "8". Notice that "34" is only counted once.

Example 2:
Input: word = "leet1234code234"
Output: 2

Example 3:
Input: word = "a1b01c001"
Output: 1
Explanation: The three integers "1", "01", and "001" all represent the same integer because
the leading zeros are ignored when comparing their decimal values.

Constraints:
1 <= word.length <= 1000
word consists of digits and lowercase English letters.
*/

// Solution 1: Remove leading zeros but consider all digits zero must return as zero
// Refer to
// https://stackoverflow.com/a/7089841/6706875
class Solution {
    public int numDifferentIntegers(String word) {
        StringBuilder sb = new StringBuilder();
        for(char c : word.toCharArray()) {
            if(Character.isLetter(c)) {
                sb.append(" ");
            } else {
                sb.append(c);
            }
        }
        String[] strs = sb.toString().trim().split("\\s+");
        Set<String> set = new HashSet<String>();
        for(String s : strs) {
            // Check if empty string, test case: "u"
            if(!s.equals("")) {
                // Remove leading zeros, test case: "a1b01c001"
                set.add(removeLeadingZeros(s));
            }
        }
        return set.size();
    }

    private String removeLeadingZeros(String s) {
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) != '0') {
                return s.substring(i);
            }
        }
        // In case all digits are 0, must consider as 0
        return "0";
    }
}
