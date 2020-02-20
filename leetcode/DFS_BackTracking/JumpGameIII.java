/**
 Refer to
 https://leetcode.com/problems/jump-game-iii/
 Given an array of non-negative integers arr, you are initially positioned at start index of the array. 
 When you are at index i, you can jump to i + arr[i] or i - arr[i], check if you can reach to any index 
 with value 0.

Notice that you can not jump outside of the array at any time.

Example 1:
Input: arr = [4,2,3,0,3,1,2], start = 5
Output: true
Explanation: 
All possible ways to reach at index 3 with value 0 are: 
index 5 -> index 4 -> index 1 -> index 3 
index 5 -> index 6 -> index 4 -> index 1 -> index 3 

Example 2:
Input: arr = [4,2,3,0,3,1,2], start = 0
Output: true 
Explanation: 
One possible way to reach at index 3 with value 0 is: 
index 0 -> index 4 -> index 1 -> index 3

Example 3:
Input: arr = [3,0,2,1,2], start = 2
Output: false
Explanation: There is no way to reach at index 1 with value 0.
 
Constraints:
1 <= arr.length <= 5 * 10^4
0 <= arr[i] < arr.length
0 <= start < arr.length
*/

// Solution 1: DFS
// Refer to
// https://leetcode.com/problems/jump-game-iii/discuss/463872/Simple-one-using-queue-and-visited-paths-JAVA/417023
// Runtime: 0 ms, faster than 100.00% of Java online submissions for Jump Game III.
// Memory Usage: 42.7 MB, less than 100.00% of Java online submissions for Jump Game III.
class Solution {
    public boolean canReach(int[] arr, int start) {
        boolean[] visited = new boolean[arr.length];
        return helper(arr, start, visited);
    }
    
    private boolean helper(int[] arr, int cur, boolean[] visited) {
        if(visited[cur]) {
            return false;
        }
        if(arr[cur] == 0) {
            return true;
        }
        visited[cur] = true;
        boolean right = false;
        boolean left = false;
        if(cur + arr[cur] < arr.length) {
            right = helper(arr, cur + arr[cur], visited);
        }
        if(cur - arr[cur] >= 0) {
            left = helper(arr, cur - arr[cur], visited);
        }
        return right || left;
    }
}
