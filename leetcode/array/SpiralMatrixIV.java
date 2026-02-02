https://leetcode.com/problems/spiral-matrix-iv/description/
You are given two integers m and n, which represent the dimensions of a matrix.
You are also given the head of a linked list of integers.
Generate an m x n matrix that contains the integers in the linked list presented in spiral order (clockwise), starting from the top-left of the matrix. If there are remaining empty spaces, fill them with -1.
Return the generated matrix.
 
Example 1:

Input: m = 3, n = 5, head = [3,0,2,6,8,1,7,9,4,2,5,5,0]
Output: [[3,0,2,6,8],[5,0,-1,-1,1],[5,2,4,9,7]]
Explanation: The diagram above shows how the values are printed in the matrix.Note that the remaining spaces in the matrix are filled with -1.

Example 2:

Input: m = 1, n = 4, head = [0,1,2]
Output: [[0,1,2,-1]]
Explanation: The diagram above shows how the values are printed from left to right in the matrix.The last space in the matrix is set to -1.
 
Constraints:
- 1 <= m, n <= 105
- 1 <= m * n <= 105
- The number of nodes in the list is in the range [1, m * n].
- 0 <= Node.val <= 1000
--------------------------------------------------------------------------------
Attempt 1: 2026-01-31
Solution 1: Array + Matrix (10 min)
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public int[][] spiralMatrix(int m, int n, ListNode head) {
        int[][] result = new int[m][n];
        for(int i = 0; i < m; i++) {
            Arrays.fill(result[i], -1);
        }
        int left = 0;
        int top = 0;
        int right = n - 1;
        int bottom = m - 1;
        ListNode curr = head;
        while(left <= right && top <= bottom && curr != null) {
            for(int i = left; i <= right && curr != null; i++) {
                result[top][i] = curr.val;
                curr = curr.next;
            }
            top++;
            for(int j = top; j <= bottom && curr != null; j++) {
                result[j][right] = curr.val;
                curr = curr.next;
            }
            right--;
            if(top <= bottom) {
                for(int i = right; i >= left && curr != null; i--) {
                    result[bottom][i] = curr.val;
                    curr = curr.next;
                }
                bottom--;
            }
            if(left <= right) {
                for(int j = bottom; j >= top && curr != null; j--) {
                    result[j][left] = curr.val;
                    curr = curr.next;
                }
                left++;
            }
        }
        return result;
    }
}

Time Complexity: O(m * n)
Space Complexity: O(1)

Refer to Deepseek
Here's the Java solution for LeetCode 2326 - Spiral Matrix IV:
Problem Summary
Given a linked list and dimensions m and n, create an m x n matrix and fill it in spiral order with the linked list values. If there are more cells than nodes, fill the remaining cells with -1.
Solution
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public int[][] spiralMatrix(int m, int n, ListNode head) {
        int[][] matrix = new int[m][n];
        
        // Initialize matrix with -1
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = -1;
            }
        }
        
        ListNode current = head;
        int top = 0, bottom = m - 1;
        int left = 0, right = n - 1;
        
        while (current != null && top <= bottom && left <= right) {
            // Fill top row from left to right
            for (int i = left; i <= right && current != null; i++) {
                matrix[top][i] = current.val;
                current = current.next;
            }
            top++;
            
            // Fill right column from top to bottom
            for (int i = top; i <= bottom && current != null; i++) {
                matrix[i][right] = current.val;
                current = current.next;
            }
            right--;
            
            // Fill bottom row from right to left
            if (top <= bottom) {
                for (int i = right; i >= left && current != null; i--) {
                    matrix[bottom][i] = current.val;
                    current = current.next;
                }
                bottom--;
            }
            
            // Fill left column from bottom to top
            if (left <= right) {
                for (int i = bottom; i >= top && current != null; i--) {
                    matrix[i][left] = current.val;
                    current = current.next;
                }
                left++;
            }
        }
        
        return matrix;
    }
}
Alternative Solution: Direction-Based Approach
class Solution {
    public int[][] spiralMatrix(int m, int n, ListNode head) {
        int[][] matrix = new int[m][n];
        
        // Initialize with -1
        for (int[] row : matrix) {
            Arrays.fill(row, -1);
        }
        
        ListNode current = head;
        
        // Directions: right, down, left, up
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int dir = 0; // start moving right
        
        int row = 0, col = 0;
        int top = 0, bottom = m - 1, left = 0, right = n - 1;
        
        while (current != null) {
            // Fill current cell
            matrix[row][col] = current.val;
            current = current.next;
            
            // Calculate next position
            int nextRow = row + dirs[dir][0];
            int nextCol = col + dirs[dir][1];
            
            // If next position is out of current bounds, change direction
            if (nextRow < top || nextRow > bottom || nextCol < left || nextCol > right) {
                // Update boundaries based on direction
                if (dir == 0) { // was moving right
                    top++;
                } else if (dir == 1) { // was moving down
                    right--;
                } else if (dir == 2) { // was moving left
                    bottom--;
                } else { // was moving up
                    left++;
                }
                
                // Change direction
                dir = (dir + 1) % 4;
                
                // Recalculate next position
                nextRow = row + dirs[dir][0];
                nextCol = col + dirs[dir][1];
            }
            
            row = nextRow;
            col = nextCol;
            
            // Check if we've completed all cells
            if (top > bottom || left > right) {
                break;
            }
        }
        
        return matrix;
    }
}
Optimized Solution with Early Termination
class Solution {
    public int[][] spiralMatrix(int m, int n, ListNode head) {
        int[][] matrix = new int[m][n];
        
        // Fill entire matrix with -1 first
        for (int[] row : matrix) {
            Arrays.fill(row, -1);
        }
        
        if (head == null) return matrix;
        
        int top = 0, bottom = m - 1;
        int left = 0, right = n - 1;
        ListNode current = head;
        
        while (top <= bottom && left <= right && current != null) {
            // Left to right
            for (int i = left; i <= right && current != null; i++) {
                matrix[top][i] = current.val;
                current = current.next;
            }
            top++;
            
            // Top to bottom
            for (int i = top; i <= bottom && current != null; i++) {
                matrix[i][right] = current.val;
                current = current.next;
            }
            right--;
            
            // Right to left (if there are rows left)
            if (top <= bottom) {
                for (int i = right; i >= left && current != null; i--) {
                    matrix[bottom][i] = current.val;
                    current = current.next;
                }
                bottom--;
            }
            
            // Bottom to top (if there are columns left)
            if (left <= right) {
                for (int i = bottom; i >= top && current != null; i--) {
                    matrix[i][left] = current.val;
                    current = current.next;
                }
                left++;
            }
        }
        
        return matrix;
    }
}
Explanation
Time Complexity: O(m × n)
- We visit each cell at most once
- Each cell operation is O(1)
Space Complexity: O(1)
- We only use constant extra space (excluding the output matrix)
Key Points:
1.Matrix Initialization: We fill the entire matrix with -1 first, then overwrite with linked list values
2.Spiral Traversal: Same pattern as Spiral Matrix I (LeetCode 54)
3.Linked List Integration: Traverse the linked list while filling the matrix
4.Early Termination: Stop when linked list is exhausted or matrix is filled
Comparison with Other Spiral Matrix Problems
Aspect2326 (Spiral Matrix IV)54 (Spiral Matrix)59 (Spiral Matrix II)InputLinked List + dimensionsExisting matrixInteger nOutputNew matrixList of elementsNew matrixFill ValueLinked list values, then -1Extract existing valuesNumbers 1 to n²ChallengeVariable length inputReading patternWriting pattern
Example Walkthrough
Input: m = 3, n = 5, head = [3,0,2,6,8,1,7,9,4,2,5,5,0]

Step-by-step filling:
1. Initialize 3x5 matrix with -1
2. Fill in spiral order until linked list is exhausted:

   - Top row: [3,0,2,6,8]
   - Right column: [1,7]
   - Bottom row: [9,4,2,5]
   - Left column: [5,0]
3. Remaining cells stay -1


Output:
[
  [3, 0, 2, 6, 8],
  [5, 0,-1,-1, 1],
  [5, 2, 4, 9, 7]
]
Edge Cases
// Test Cases:
1. Empty linked list → All -1
2. Single node → First cell = node value, rest -1
3. Exactly m×n nodes → No -1 in output
4. More nodes than cells → Only fill m×n cells, ignore extra nodes
5. m=1 or n=1 → Handle single row/column cases
The first solution (boundary simulation) is the most straightforward and handles all cases correctly. It clearly shows the spiral pattern while integrating linked list traversal.


Refer to
L54.Spiral Matrix (Ref.L59,L885,L2326)
L59.Spiral Matrix II (Ref.L54,L885,L2326)
L885.Spiral Matrix III (Ref.L54,L59,L2326)
