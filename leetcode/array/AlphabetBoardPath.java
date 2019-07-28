/**
 Refer to
 https://leetcode.com/problems/alphabet-board-path/
 On an alphabet board, we start at position (0, 0), corresponding to character board[0][0].
 Here, board = ["abcde", "fghij", "klmno", "pqrst", "uvwxy", "z"], as shown in the diagram below.
      a  b  c  d  e
      f  g  h  i  j  
      k  l  m  n  o 
      p  q  r  s  t
      u  v  w  x  y
      z

 We may make the following moves:
'U' moves our position up one row, if the position exists on the board;
'D' moves our position down one row, if the position exists on the board;
'L' moves our position left one column, if the position exists on the board;
'R' moves our position right one column, if the position exists on the board;
'!' adds the character board[r][c] at our current position (r, c) to the answer.
(Here, the only positions that exist on the board are positions with letters on them.)

Return a sequence of moves that makes our answer equal to target in the minimum number of moves.  
You may return any path that does so.

Example 1:
Input: target = "leet"
Output: "DDR!UURRR!!DDD!"

Example 2:
Input: target = "code"
Output: "RR!DDRR!UUL!R!"

Constraints:
1 <= target.length <= 100
target consists only of English lowercase letters.
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/alphabet-board-path/discuss/345541/Java-Runtime-faster-than-100-memory-usage-less-than-100-with-detail-explanation
/**
 The straight forward solution with inline comments, mapping each character from board into 
 two 1-dimensional array as coordinate, then for loop once on target string to get each pair 
 (indexed - 1 and indexed) characters' distance on coordindate, draw the line directly and 
 special case as handle 'z', we can only move from 'u' to 'z' as down ('D') or 'z' to 'u' as up ('U')
*/
class Solution {
    public String alphabetBoardPath(String target) {
        if(target == null || target.length() == 0) {
            return "";
        }
        // Mapping alphabet to coordinate with two arrays dx and dy
        // ---------------->  a  b  c  d  e  f  g  h  i  j  k  l  m  n  o  p  q  r  s  t  u  v  w  x  y  z
        int[] dx = new int[] {0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0};
        int[] dy = new int[] {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5};
        String result = "";
        // Use 'prev' to record the previous character
        int prev = 0;
        for(int i = 0; i < target.length(); i++) {
            // Get current character distance to 'a'
            int cur = target.charAt(i) - 'a';
            // Get distance between current character and previous character
            // on two dimensions
            int diff_x = dx[cur] - dx[prev];
            int diff_y = dy[cur] - dy[prev];
            // Handle speical case as continuous 'z'
            // Pickup 'z' by '!' first, then skip current loop, no need to update 'prev'
            // since both 'prev' and 'cur' equal to 'z'
            if(cur == 25 && prev == 25) {
                result += '!';
                continue;
            }
            // Handle special case as go to 'z'
            // Go to 'u' (cur == 20) first, then move down from 'u' once
            if(cur == 25) {
                diff_x = dx[20] - dx[prev];
                diff_y = dy[20] - dy[prev];
            }
            // Handle special case go from 'z'
            // Go to 'u' (cur == 20) first, move up from 'z' once
            if(prev == 25) {
                diff_x = dx[cur] - dx[20];
                diff_y = dy[cur] - dy[20];
                // Move from 'z' to 'u' as the first step before all other steps
                result += 'U';
            }
            if(diff_x > 0) {
                while(diff_x > 0) {
                    result += 'R';
                    diff_x--;
                }
            } else if(diff_x < 0) {
                while(diff_x < 0) {
                    result += 'L';
                    diff_x++;
                }
            }
            if(diff_y > 0) {
                while(diff_y > 0) {
                    result += 'D';
                    diff_y--;
                }
            } else if(diff_y < 0) {
                while(diff_y < 0) {
                    result += 'U';
                    diff_y++;
                }
            }
            if(cur == 25) {
                // Move from 'u' to 'z' as the last step after all other steps
                result += 'D';
            }
            // After all steps find current character and pick up by '!'
            result += '!';
            // Update preivous character by current charater
            prev = cur;
        }
        return result;
    }
}
