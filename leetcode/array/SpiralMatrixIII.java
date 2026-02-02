https://leetcode.com/problems/spiral-matrix-iii/description/
You start at the cell (rStart, cStart) of an rows x cols grid facing east. The northwest corner is at the first row and column in the grid, and the southeast corner is at the last row and column.
You will walk in a clockwise spiral shape to visit every position in this grid. Whenever you move outside the grid's boundary, we continue our walk outside the grid (but may return to the grid boundary later.). Eventually, we reach all rows * cols spaces of the grid.
Return an array of coordinates representing the positions of the grid in the order you visited them.
 
Example 1:

Input: rows = 1, cols = 4, rStart = 0, cStart = 0
Output: [[0,0],[0,1],[0,2],[0,3]]

Example 2:

Input: rows = 5, cols = 6, rStart = 1, cStart = 4
Output: [[1,4],[1,5],[2,5],[2,4],[2,3],[1,3],[0,3],[0,4],[0,5],[3,5],[3,4],[3,3],[3,2],[2,2],[1,2],[0,2],[4,5],[4,4],[4,3],[4,2],[4,1],[3,1],[2,1],[1,1],[0,1],[4,0],[3,0],[2,0],[1,0],[0,0]]
 
Constraints:
- 1 <= rows, cols <= 100
- 0 <= rStart < rows
- 0 <= cStart < cols
--------------------------------------------------------------------------------
Attempt 1: 2026-01-31
Solution 1: Matrix (30 min)
Style 1: Step increase at the begin of while loop
class Solution {
    public int[][] spiralMatrixIII(int rows, int cols, int rStart, int cStart) {
        // dir: east -> south -> west -> north
        int dir = 0;
        // Wrong direction system:
        //int[] dx = new int[]{1, 0, -1, 0};
        //int[] dy = new int[]{0, -1, 0, 1};
        // For rows: 0, 1, 0, -1 (east: no row change, south: +1, west: no row change, north: -1)
        // For cols: 1, 0, -1, 0 (east: +1, south: no col change, west: -1, north: no col change)
        int[] dx = new int[]{0, 1, 0, -1};
        int[] dy = new int[]{1, 0, -1, 0};        
        int total = rows * cols;
        int[][] result = new int[total][2];
        int index = 0;
        result[index][0] = rStart;
        result[index][1] = cStart;
        index++;
        int stepLen = 0;
        while(index < total) {
            // For east and west directions, step increases
            if(dir == 0 || dir == 2) {
                stepLen++;
            }
            // Move 'step' steps in current direction
            for(int i = 0; i < stepLen; i++) {
                rStart += dx[dir];
                cStart += dy[dir];
                if(rStart >= 0 && rStart < rows && cStart >= 0 && cStart < cols) {
                    result[index][0] = rStart;
                    result[index][1] = cStart;
                    index++;
                    if(index == total) {
                        return result;
                    }
                }
            }
            // Change direction
            dir = (dir + 1) % 4;
        }
        return result;
    }
}

Time Complexity: O(m × n) — Each cell is visited at most once
Space Complexity: O(m × n) — For the output matrix (excluding input list)
Style 2: Step increase at the end of while loop
class Solution {
    public int[][] spiralMatrixIII(int rows, int cols, int rStart, int cStart) {
        // Direction vectors: East → South → West → North (clockwise)
        int dir = 0;
        // Wrong direction system:
        //int[] dx = new int[]{1, 0, -1, 0};
        //int[] dy = new int[]{0, -1, 0, 1};
        // For rows: 0, 1, 0, -1 (east: no row change, south: +1, west: no row change, north: -1)
        // For cols: 1, 0, -1, 0 (east: +1, south: no col change, west: -1, north: no col change)
        int[] dx = new int[]{0, 1, 0, -1};
        int[] dy = new int[]{1, 0, -1, 0};        
        int total = rows * cols;
        int[][] result = new int[total][2];
        // Add starting position
        int index = 0;
        result[index][0] = rStart;
        result[index][1] = cStart;
        index++;
        // Initial step length
        int stepLen = 1;
        while(index < total) {
            // For each step length, move in TWO directions (East+South, West+North, ...)
            for(int j = 0; j < 2; j++) {
                for(int i = 0; i < stepLen; i++) {
                    rStart += dx[dir];
                    cStart += dy[dir];
                    if(rStart >= 0 && rStart < rows && cStart >= 0 && cStart < cols) {
                        result[index][0] = rStart;
                        result[index][1] = cStart;
                        index++;
                        if(index == total) {
                            return result;
                        }
                    }
                }
                // Turn clockwise to next direction
                dir = (dir + 1) % 4;
            }
            // After completing two directions, increase step length
            stepLen++;
        }
        return result;
    }
}

Time Complexity: O(m × n) — Each cell is visited at most once
Space Complexity: O(m × n) — For the output matrix (excluding input list)

Refer to Deepseek
Here's a Java solution for LeetCode 885 - Spiral Matrix III:
Problem Summary
Given a matrix with R rows and C columns, start at position (r0, c0) and walk in a clockwise spiral shape that grows outward. Return a list of coordinates in the order they were visited.
Solution
Approach 1: Simulation with Pattern Recognition
class Solution {
    public int[][] spiralMatrixIII(int R, int C, int r0, int c0) {
        int totalCells = R * C;
        int[][] result = new int[totalCells][2];
        int index = 0;
        
        // Directions: east, south, west, north
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        
        // Step pattern: 1,1,2,2,3,3,4,4,...
        int step = 0;
        int dir = 0; // start moving east
        
        // Add starting point
        result[index][0] = r0;
        result[index][1] = c0;
        index++;
        
        // If we've already collected all cells (1x1 matrix)
        if (index == totalCells) return result;
        
        while (index < totalCells) {
            // For east and west directions, step increases
            if (dir == 0 || dir == 2) {
                step++;
            }
            
            // Move 'step' steps in current direction
            for (int i = 0; i < step; i++) {
                r0 += dirs[dir][0];
                c0 += dirs[dir][1];
                
                // Check if within bounds
                if (r0 >= 0 && r0 < R && c0 >= 0 && c0 < C) {
                    result[index][0] = r0;
                    result[index][1] = c0;
                    index++;
                    
                    if (index == totalCells) {
                        return result;
                    }
                }
            }
            
            // Change direction
            dir = (dir + 1) % 4;
        }
        
        return result;
    }
}
Approach 2: More Explicit Pattern
class Solution {
    public int[][] spiralMatrixIII(int R, int C, int r0, int c0) {
        int[][] result = new int[R * C][2];
        int count = 0;
        
        // Add starting position
        result[count][0] = r0;
        result[count][1] = c0;
        count++;
        
        // The spiral pattern: 1 east, 1 south, 2 west, 2 north, 3 east, 3 south, ...
        int len = 1; // Current step length
        int dir = 0; // 0: east, 1: south, 2: west, 3: north
        
        while (count < R * C) {
            // For each direction, we do it twice with the same length
            // except the first time (east and south share len=1)
            for (int i = 0; i < 2; i++) {
                int[] direction = getDirection(dir);
                
                for (int step = 0; step < len; step++) {
                    r0 += direction[0];
                    c0 += direction[1];
                    
                    if (r0 >= 0 && r0 < R && c0 >= 0 && c0 < C) {
                        result[count][0] = r0;
                        result[count][1] = c0;
                        count++;
                        
                        if (count == R * C) {
                            return result;
                        }
                    }
                }
                
                dir = (dir + 1) % 4; // Change direction
            }
            
            len++; // Increase step length after completing two directions
        }
        
        return result;
    }
    
    private int[] getDirection(int dir) {
        switch(dir) {
            case 0: return new int[]{0, 1};  // east
            case 1: return new int[]{1, 0};  // south
            case 2: return new int[]{0, -1}; // west
            case 3: return new int[]{-1, 0}; // north
            default: return new int[]{0, 0};
        }
    }
}
Approach 3: Cleaner Implementation
class Solution {
    public int[][] spiralMatrixIII(int R, int C, int r0, int c0) {
        int[][] result = new int[R * C][2];
        int idx = 0;
        
        // Directions: east, south, west, north
        int[] dr = {0, 1, 0, -1};
        int[] dc = {1, 0, -1, 0};
        
        // Add starting point
        result[idx++] = new int[]{r0, c0};
        
        // If we only have one cell
        if (R * C == 1) return result;
        
        // Spiral parameters
        for (int step = 1; idx < R * C; step++) {
            // Move step steps in east direction, then step steps in south direction
            // Then step+1 steps in west direction, then step+1 steps in north direction
            for (int dir = 0; dir < 4; dir++) {
                // The number of steps: for dir 0,1 use step; for dir 2,3 use step+1?
                // Actually pattern is: 1 east, 1 south, 2 west, 2 north, 3 east, 3 south, 4 west, 4 north...
                // So: even steps (0,2,4,...): for directions 0 and 1 use step, for 2 and 3 use step
                // But actually: after first step=1, we do: 1 east, 1 south
                // Then step=2: 2 west, 2 north
                // So: when step is odd: we're doing east/south, when even: west/north
                
                int currentSteps = (dir < 2) ? step : step + 1;
                
                for (int i = 0; i < currentSteps; i++) {
                    r0 += dr[dir];
                    c0 += dc[dir];
                    
                    if (r0 >= 0 && r0 < R && c0 >= 0 && c0 < C) {
                        result[idx++] = new int[]{r0, c0};
                        if (idx == R * C) return result;
                    }
                }
            }
        }
        
        return result;
    }
}
Approach 4: Simplified Pattern Recognition (Most Intuitive)
class Solution {
    public int[][] spiralMatrixIII(int R, int C, int r0, int c0) {
        int[][] result = new int[R * C][2];
        int index = 0;
        
        // Add starting point
        result[index][0] = r0;
        result[index][1] = c0;
        index++;
        
        // Movement pattern: move east 1, south 1, west 2, north 2, east 3, south 3, ...
        int step = 1;
        int dir = 0; // 0: east, 1: south, 2: west, 3: north
        
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        
        while (index < R * C) {
            // For each direction in current step
            for (int i = 0; i < 2; i++) { // Two directions per step size
                for (int j = 0; j < step; j++) {
                    r0 += directions[dir][0];
                    c0 += directions[dir][1];
                    
                    if (r0 >= 0 && r0 < R && c0 >= 0 && c0 < C) {
                        result[index][0] = r0;
                        result[index][1] = c0;
                        index++;
                        
                        if (index == R * C) {
                            return result;
                        }
                    }
                }
                
                // Change direction
                dir = (dir + 1) % 4;
            }
            
            // Increase step size after completing two directions
            step++;
        }
        
        return result;
    }
}
Explanation
Time Complexity: O(max(R, C)²)
- In worst case, we might walk outside the matrix many times
- The spiral expands until it covers the entire matrix
Space Complexity: O(R × C) for the output
Key Pattern:
The spiral grows with this step pattern:
Move 1 step east
Move 1 step south  
Move 2 steps west
Move 2 steps north
Move 3 steps east
Move 3 steps south
Move 4 steps west
Move 4 steps north
...
Key Points:
1.Starting Point: Always include (r0, c0) first
2.Boundary Check: Only add coordinates that are within the matrix bounds
3.Spiral Growth: Step size increases by 1 after every 2 direction changes
4.Direction Cycle: East → South → West → North → repeat
Visual Example
R = 5, C = 6, r0 = 1, c0 = 4

The spiral pattern:
Start: (1,4)
1 step east:  (1,5)
1 step south: (2,5)
2 steps west: (2,4), (2,3)
2 steps north: (1,3), (0,3)
3 steps east: (0,4), (0,5), (0,6) [out of bounds]
3 steps south: (1,6) [out], (2,6) [out], (3,6) [out]
4 steps west: (3,5), (3,4), (3,3), (3,2)
... and so on
Edge Cases
// Test Cases:
1. R=1, C=1, r0=0, c0=0 → [[0,0]]
2. R=1, C=4, r0=0, c0=0 → [[0,0],[0,1],[0,2],[0,3]]
3. R=5, C=6, r0=1, c0=4 → (as shown in problem example)
4. Starting at corner: R=3, C=3, r0=0, c0=0
5. Starting at center: R=3, C=3, r0=1, c0=1
Differences from Spiral Matrix I & II
1.Starting Point: Not necessarily at top-left corner
2.Outward Spiral: Expands outward instead of inward
3.Partial Matrix: We visit ALL cells in the matrix, not just a subset
4.Step Pattern: Fixed pattern of 1,1,2,2,3,3,... steps
The simplest approach is Approach 4, which clearly shows the pattern of taking step moves in two directions, then incrementing step and repeating with the next two directions.

Q1: i get below wrong solution:

Input
rows = 5
cols = 6
rStart = 1
cStart = 4
Output = [[1,4],[2,4],[2,3],[1,3],[0,3],[0,4],[0,5],[1,5],[2,5],[3,5],[3,4],[3,3],[3,2],[2,2],[1,2],[0,2],[4,5],[4,4],[4,3],[4,2],[4,1],[3,1],[2,1],[1,1],[0,1],[4,0],[3,0],[2,0],[1,0],[0,0]]
Expected = [[1,4],[1,5],[2,5],[2,4],[2,3],[1,3],[0,3],[0,4],[0,5],[3,5],[3,4],[3,3],[3,2],[2,2],[1,2],[0,2],[4,5],[4,4],[4,3],[4,2],[4,1],[3,1],[2,1],[1,1],[0,1],[4,0],[3,0],[2,0],[1,0],[0,0]]

The issue with your solution is in the direction arrays. You have swapped the row and column movements incorrectly. In your current code:
int[] dx = new int[]{1, 0, -1, 0};
int[] dy = new int[]{0, -1, 0, 1};
This corresponds to:
- dx[0] = 1 (move down in rows) - should be moving right in columns
- dy[0] = 0 (no column movement) - should be moving right in columns
The correct direction arrays for clockwise spiral (east → south → west → north) should be:
// For rows: 0, 1, 0, -1 (east: no row change, south: +1, west: no row change, north: -1)
// For cols: 1, 0, -1, 0 (east: +1, south: no col change, west: -1, north: no col change)
int[] dr = new int[]{0, 1, 0, -1};
int[] dc = new int[]{1, 0, -1, 0};
Here's the corrected solution:
class Solution {
    public int[][] spiralMatrixIII(int rows, int cols, int rStart, int cStart) {
        // Directions: east -> south -> west -> north
        int dir = 0;
        // Correct direction arrays:
        // dr: row changes, dc: column changes
        int[] dr = new int[]{0, 1, 0, -1};
        int[] dc = new int[]{1, 0, -1, 0};
        
        int total = rows * cols;
        int[][] result = new int[total][2];
        int index = 0;
        
        // Add starting point
        result[index][0] = rStart;
        result[index][1] = cStart;
        index++;
        
        int stepLen = 0;
        
        while(index < total) {
            // Increase step length when moving east or west
            if(dir == 0 || dir == 2) {
                stepLen++;
            }
            
            // Move stepLen steps in current direction
            for(int i = 0; i < stepLen; i++) {
                rStart += dr[dir];
                cStart += dc[dir];
                
                // Check if within bounds
                if(rStart >= 0 && rStart < rows && cStart >= 0 && cStart < cols) {
                    result[index][0] = rStart;
                    result[index][1] = cStart;
                    index++;
                    
                    if(index == total) {
                        return result;
                    }
                }
            }
            
            // Change direction clockwise
            dir = (dir + 1) % 4;
        }
        
        return result;
    }
}
Why Your Original Code Gave Wrong Output:
With your direction arrays:
int[] dx = new int[]{1, 0, -1, 0};  // Actually row movements
int[] dy = new int[]{0, -1, 0, 1};  // Actually column movements
This corresponds to:
- Direction 0: dx[0]=1, dy[0]=0 → Move down 1 row (should be move right)
- Direction 1: dx[1]=0, dy[1]=-1 → Move left 1 column (should be move down)
- Direction 2: dx[2]=-1, dy[2]=0 → Move up 1 row (should be move left)
- Direction 3: dx[3]=0, dy[3]=1 → Move right 1 column (should be move up)
So you're actually moving in a different pattern: down → left → up → right, which is counter-clockwise and starts in the wrong direction.
Key Insight:
- dr array controls row movement: 0 (no change), 1 (down), 0 (no change), -1 (up)
- dc array controls column movement: 1 (right), 0 (no change), -1 (left), 0 (no change)
This ensures the clockwise spiral pattern: east (right) → south (down) → west (left) → north (up).

Refer to
L54.Spiral Matrix (Ref.L59,L885,L2326)
L59.Spiral Matrix II (Ref.L54,L885,L2326)
L2326.Spiral Matrix IV (Ref.L54,L59,L2326)
