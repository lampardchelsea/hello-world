/**
 Refer to
 https://leetcode.com/problems/jewels-and-stones/
 You're given strings J representing the types of stones that are jewels, and S representing 
 the stones you have.  Each character in S is a type of stone you have.  You want to know how 
 many of the stones you have are also jewels.

The letters in J are guaranteed distinct, and all characters in J and S are letters. Letters 
are case sensitive, so "a" is considered a different type of stone from "A".

Example 1:
Input: J = "aA", S = "aAAbbbb"
Output: 3

Example 2:
Input: J = "z", S = "ZZ"
Output: 0

Note:
S and J will consist of letters and have length at most 50.
The characters in J are distinct.
*/
// Solution 1: 
// Time complexity: O(m * n)
// Space complexity: O(1)
// Runtime: 1 ms, faster than 98.77% of Java online submissions for Jewels and Stones.
// Memory Usage: 36 MB, less than 88.72% of Java online submissions for Jewels and Stones.
class Solution {
    public int numJewelsInStones(String J, String S) {
        int count = 0;
        for(int i = 0; i < J.length(); i++) {
            char c = J.charAt(i);
            for(int j = 0; j < S.length(); j++) {
                if(c == S.charAt(j)) {
                    count++;
                }
            }
        }
        return count;
    }
}

// Solution 2:
// Refer to
// https://leetcode.com/problems/jewels-and-stones/discuss/113553/C%2B%2BJavaPython-Set-Solution-O(M%2BN)
// Time Complexity: O(m + n)
// Splace Complexity: O(n)
// Runtime: 2 ms, faster than 82.19% of Java online submissions for Jewels and Stones.
// Memory Usage: 36.9 MB, less than 87.95% of Java online submissions for Jewels and Stones.
class Solution {
    public int numJewelsInStones(String J, String S) {
        int count = 0;
        Set<Character> set = new HashSet<Character>();
        for(char c : J.toCharArray()) {
            set.add(c);
        }
        for(char c : S.toCharArray()) {
            if(set.contains(c)) {
                count++;
            }
        }
        return count;
    }
}
