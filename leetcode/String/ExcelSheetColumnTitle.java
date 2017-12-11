/**
 * Refer to
 * https://leetcode.com/problems/excel-sheet-column-title/description/
 * Given a positive integer, return its corresponding column title as appear in an Excel sheet.

    For example:

        1 -> A
        2 -> B
        3 -> C
        ...
        26 -> Z
        27 -> AA
        28 -> AB 
 *
 * Solution
 * https://www.youtube.com/watch?v=zURc9uRxV_o
 * https://discuss.leetcode.com/topic/6212/share-my-simple-solution-just-a-little-trick-to-handle-corner-case-26
*/
class Solution {
    public String convertToTitle(int n) {
        StringBuilder sb = new StringBuilder();
        while(n > 0) {
            n--;  // Important to decrease 1 first, prepare for 'A' plus position
            sb.append((char)('A' + n % 26));
            n /= 26;
        }
        // Revert the order
        return sb.reverse().toString();
    }
}
