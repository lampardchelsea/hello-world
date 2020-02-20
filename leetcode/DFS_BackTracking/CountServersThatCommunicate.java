/**
 Refer to
 https://leetcode.com/problems/count-servers-that-communicate/
 You are given a map of a server center, represented as a m * n integer matrix grid, where 1 
 means that on that cell there is a server and 0 means that it is no server. Two servers are 
 said to communicate if they are on the same row or on the same column.
 Return the number of servers that communicate with any other server.
 
 Example 1:
 Input: grid = [[1,0],[0,1]]
 Output: 0
 Explanation: No servers can communicate with others.
 
 Example 2:
 Input: grid = [[1,0],[1,1]]
 Output: 3
 Explanation: All three servers can communicate with at least one other server.
 
 Example 3:
 Input: grid = [[1,1,0,0],[0,0,1,0],[0,0,1,0],[0,0,0,1]]
 Output: 4
 Explanation: The two servers in the first row can communicate with each other. 
 The two servers in the third column can communicate with each other. The server at 
 right bottom corner can't communicate with any other server.
 
 Constraints:
 m == grid.length
 n == grid[i].length
 1 <= m <= 250
 1 <= n <= 250
 grid[i][j] == 0 or 1
*/

// Solution 1: DFS
// Refer to
// https://leetcode.com/problems/count-servers-that-communicate/discuss/436167/Simple-java-DFS-solution-similar-to-200.-number-of-islands



// Solution 2: Concise solution without DFS
// Refer to
// https://leetcode.com/problems/count-servers-that-communicate/discuss/436188/Java-or-Clean-And-Simple-or-Beats-100
// https://leetcode.com/problems/count-servers-that-communicate/discuss/436188/Java-or-Clean-And-Simple-or-Beats-100/392655
// https://leetcode.com/problems/count-servers-that-communicate/discuss/438394/Javascript-and-C%2B%2B-solutions
/**
 Synopsis:
 Count the amount of servers per row i and column j. And simultaneously track each server's row i and column j.
 Count and return the answer as the amount of servers which communicate to other servers 
 (ie. each server communicates to another server if there exists more than one server in row i or there exists 
 more than one server in column j)
 Runtime: O(M * N)
*/
class Solution {
    public int countServers(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int totalServers = 0;
        int[] rowCount = new int[m];
        int[] colCount = new int[n];
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] == 1) {
                    rowCount[i]++;
                    colCount[j]++;
                    totalServers++;
                }
            }
        }
        int result = 0;
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] == 1) {
                    if(rowCount[i] > 1|| colCount[j] > 1) {
                        result++;
                    }
                }
            }
        }
        return result;
    }
}
