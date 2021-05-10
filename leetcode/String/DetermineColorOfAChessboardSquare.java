/**
Refer to
https://leetcode.com/problems/determine-color-of-a-chessboard-square/
You are given coordinates, a string that represents the coordinates of a square of the chessboard. Below is a chessboard for your reference.

    


Return true if the square is white, and false if the square is black.

The coordinate will always represent a valid chessboard square. The coordinate will always have the letter first, and the number second.

Example 1:
Input: coordinates = "a1"
Output: false
Explanation: From the chessboard above, the square with coordinates "a1" is black, so return false.

Example 2:
Input: coordinates = "h3"
Output: true
Explanation: From the chessboard above, the square with coordinates "h3" is white, so return true.

Example 3:
Input: coordinates = "c7"
Output: false

Constraints:
coordinates.length == 2
'a' <= coordinates[0] <= 'h'
'1' <= coordinates[1] <= '8'
*/

class Solution {
    public boolean squareIsWhite(String coordinates) {
        int x = coordinates.charAt(0) - 'a';
        int y = coordinates.charAt(1) - '1';
        boolean[][] map = new boolean[][] {{false, true}, {true, false}};
        return map[x % 2][y % 2];
    }
}

// Better Solution
// Refer to
// https://leetcode.com/problems/determine-color-of-a-chessboard-square/discuss/1140578/JavaC%2B%2BPython-1-lines
class Solution {
    public boolean squareIsWhite(String a) {
        return (int)a.charAt(0) % 2 != (int)a.charAt(1) % 2;
    }
}
