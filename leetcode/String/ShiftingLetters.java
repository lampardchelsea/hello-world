/**
 Refer to
 https://leetcode.com/problems/shifting-letters/
 We have a string S of lowercase letters, and an integer array shifts.

Call the shift of a letter, the next letter in the alphabet, (wrapping around so that 'z' becomes 'a'). 

For example, shift('a') = 'b', shift('t') = 'u', and shift('z') = 'a'.

Now for each shifts[i] = x, we want to shift the first i+1 letters of S, x times.

Return the final string after all such shifts to S are applied.

Example 1:

Input: S = "abc", shifts = [3,5,9]
Output: "rpl"
Explanation: 
We start with "abc".
After shifting the first 1 letters of S by 3, we have "dbc".
After shifting the first 2 letters of S by 5, we have "igc".
After shifting the first 3 letters of S by 9, we have "rpl", the answer.

Note:
1 <= S.length = shifts.length <= 20000
0 <= shifts[i] <= 10 ^ 9
*/
// Native solution, caution on must use long for 'times' in case of large input
class Solution {
    public String shiftingLetters(String S, int[] shifts) {
        StringBuilder sb = new StringBuilder();
        int j = 0;
        for(int i = 0; i < S.length(); i++) {
            char cur = S.charAt(i);
            // Caution: Could not only use int, must change to long in
            // case of below test case:
            // e.g
            // "mkgfzkkuxownxvfvxasy" 
            //[505870226,437526072,266740649,224336793,532917782,311122363,567754492,595798950,81520022,684110326,
            //137742843,275267355,856903962,148291585,919054234,467541837,622939912,116899933,983296461,536563513]
            //int times = 0;
            long times = 0;
            for(int k = j; k < shifts.length; k++) {
                times += shifts[k];
            }
            char next = (char)((cur - 'a' + times % 26) % 26 + 'a');
            sb.append(next);
            j++;
        }
        return sb.toString();
    }
}
