/**
Refer to
https://leetcode.com/problems/path-crossing/
Given a string path, where path[i] = 'N', 'S', 'E' or 'W', each representing moving one unit north, south, east, or west, 
respectively. You start at the origin (0, 0) on a 2D plane and walk on the path specified by path.

Return true if the path crosses itself at any point, that is, if at any time you are on a location you have previously visited. 
Return false otherwise.

Example 1:
Input: path = "NES"
   --------
   |      |
   |      |
 (0,0)   
Output: false 
Explanation: Notice that the path doesn't cross any point more than once.

Example 2:
Input: path = "NESWW"
        --------
        |      |
        |      |
  --------------
      (0,0)  
Output: true
Explanation: Notice that the path visits the origin twice.

Constraints:
1 <= path.length <= 104
path[i] is either 'N', 'S', 'E', or 'W'.
*/
class Solution {
    public boolean isPathCrossing(String path) {
        Set<String> pos = new HashSet<String>();
        int x = 0;
        int y = 0;
        pos.add("" + x + "_" + y);
        for(char c : path.toCharArray()) {
            if(c == 'N') {
                y++;
            } else if(c == 'S') {
                y--;
            } else if(c == 'E') {
                x++;
            } else if(c == 'W') {
                x--;
            }
            if(pos.contains("" + x + "_" + y)) {
                return true;
            }
            pos.add("" + x + "_" + y);
        }
        return false;
    }
}
