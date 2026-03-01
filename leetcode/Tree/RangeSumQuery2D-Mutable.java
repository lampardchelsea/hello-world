https://leetcode.ca/2016-10-03-308-Range-Sum-Query-2D-Mutable/
Given a 2D matrix matrix, handle multiple queries of the following types:
1.Update the value of a cell in matrix.
2.Calculate the sum of the elements of matrix inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).
Implement the NumMatrix class:
- NumMatrix(int[][] matrix) Initializes the object with the integer matrix matrix.
- void update(int row, int col, int val) Updates the value of matrix[row][col] to be val.
- int sumRegion(int row1, int col1, int row2, int col2) Returns the sum of the elements of matrix inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).
 
Example 1:


Input
["NumMatrix", "sumRegion", "update", "sumRegion"]
[[[[3, 0, 1, 4, 2], [5, 6, 3, 2, 1], [1, 2, 0, 1, 5], [4, 1, 0, 1, 7], [1, 0, 3, 0, 5]]], [2, 1, 4, 3], [3, 2, 2], [2, 1, 4, 3]]

Output
[null, 8, null, 10]

Explanation
NumMatrix numMatrix = new NumMatrix([[3, 0, 1, 4, 2], [5, 6, 3, 2, 1], [1, 2, 0, 1, 5], [4, 1, 0, 1, 7], [1, 0, 3, 0, 5]]);
numMatrix.sumRegion(2, 1, 4, 3); // return 8 (i.e. sum of the left red rectangle)
numMatrix.update(3, 2, 2); // matrix changes from left image to right image
numMatrix.sumRegion(2, 1, 4, 3); // return 10 (i.e. sum of the right red rectangle)
 
Constraints:
- m == matrix.length
- n == matrix[i].length
- 1 <= m, n <= 200
- -1000 <= matrix[i][j] <= 1000
- 0 <= row < m
- 0 <= col < n
- -1000 <= val <= 1000
- 0 <= row1 <= row2 < m
- 0 <= col1 <= col2 < n
- At most 5000 calls will be made to sumRegion and update.
--------------------------------------------------------------------------------
Attempt 1: 2026-02-22
Solution 1: Prefix Sum (30 min)
Refer to Deepseek
Here's a Java solution for LeetCode 308 - Range Sum Query 2D - Mutable using a 2D Binary Indexed Tree (Fenwick Tree):
class NumMatrix {
    private int[][] matrix;  // stores the current matrix values (optional)
    private int[][] bit;     // 2D Fenwick tree (1‑based indexing)
    private int m, n;

    public NumMatrix(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) return;
        this.matrix = matrix;
        this.m = matrix.length;
        this.n = matrix[0].length;
        this.bit = new int[m + 1][n + 1];

        // Build BIT by adding each cell value
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                init(i, j, matrix[i][j]);
            }
        }
    }

    // Internal method to add a value at (row, col) into BIT
    private void init(int row, int col, int val) {
        int i = row + 1;
        while (i <= m) {
            int j = col + 1;
            while (j <= n) {
                bit[i][j] += val;
                j += j & -j;
            }
            i += i & -i;
        }
    }

    // Public update: set cell (row, col) to new value
    public void update(int row, int col, int val) {
        int delta = val - matrix[row][col];
        matrix[row][col] = val;   // keep the matrix in sync
        int i = row + 1;
        while (i <= m) {
            int j = col + 1;
            while (j <= n) {
                bit[i][j] += delta;
                j += j & -j;
            }
            i += i & -i;
        }
    }

    // Prefix sum from (0,0) to (row, col) inclusive
    private int prefixSum(int row, int col) {
        int sum = 0;
        int i = row + 1;
        while (i > 0) {
            int j = col + 1;
            while (j > 0) {
                sum += bit[i][j];
                j -= j & -j;
            }
            i -= i & -i;
        }
        return sum;
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        return prefixSum(row2, col2) 
             - prefixSum(row1 - 1, col2) 
             - prefixSum(row2, col1 - 1) 
             + prefixSum(row1 - 1, col1 - 1);
    }
}
Explanation
- 2D Fenwick Tree extends the 1D BIT by using nested loops. Each node bit[i][j] stores the sum of a submatrix that ends at (i, j) in BIT coordinates (1‑based).
- Initialization: In the constructor, we call init() for every cell to add its value to the tree. This is O(m * n * log m * log n) but acceptable because it's done once.
- Update: When the value at (row, col) changes, we compute delta and propagate it through all BIT nodes that cover that cell (both row and column loops).
- Prefix Sum: prefixSum(row, col) returns the sum of all cells from (0,0) to (row, col) inclusive. It walks up the tree in both dimensions.
- Region Sum: Using inclusion‑exclusion, we compute:
sumRegion = prefix(row2, col2) - prefix(row1-1, col2) - prefix(row2, col1-1) + prefix(row1-1, col1-1)
- Space: O(m * n) for both the stored matrix and the BIT.
Complexity
- Constructor: O(m * n * log m * log n)
- update: O(log m * log n)
- sumRegion: O(log m * log n)
- Space: O(m * n)
Alternative: 2D Segment Tree
A 2D segment tree is also possible but more complex to implement and usually heavier. The Fenwick tree is preferred for simplicity and efficiency when updates are point updates.

Refer to QWEN
Here's an efficient and well-commented Java solution for LeetCode 308: Range Sum Query 2D - Mutable using 2D Binary Indexed Tree (Fenwick Tree) — the optimal approach for mutable 2D range sum queries:
class NumMatrix {
    private int[][] matrix; // Store current values to compute deltas on update
    private int[][] bit;    // 2D Fenwick Tree (1-indexed internally)
    private int m, n;
    
    public NumMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            this.m = this.n = 0;
            this.matrix = new int[0][0];
            this.bit = new int[0][0];
            return;
        }
        
        this.m = matrix.length;
        this.n = matrix[0].length;
        this.matrix = new int[m][n];
        this.bit = new int[m + 1][n + 1]; // 1-indexed BIT
        
        // Initialize with original values
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                this.matrix[i][j] = matrix[i][j];
                updateBIT(i, j, matrix[i][j]);
            }
        }
    }
    
    // Update single cell: O(log m * log n)
    public void update(int row, int col, int val) {
        int delta = val - matrix[row][col];
        matrix[row][col] = val;
        updateBIT(row, col, delta);
    }
    
    // Range sum query: O(log m * log n)
    public int sumRegion(int row1, int col1, int row2, int col2) {
        return prefixSum(row2, col2) 
             - prefixSum(row1 - 1, col2) 
             - prefixSum(row2, col1 - 1) 
             + prefixSum(row1 - 1, col1 - 1);
    }
    
    // =============== PRIVATE HELPER METHODS ===============
    
    // Update BIT with delta at (row, col) - 0-indexed input
    private void updateBIT(int row, int col, int delta) {
        // Convert to 1-indexed for BIT internal storage
        for (int i = row + 1; i <= m; i += i & -i) {
            for (int j = col + 1; j <= n; j += j & -j) {
                bit[i][j] += delta;
            }
        }
    }
    
    // Get prefix sum [0..row][0..col] (0-indexed); handles negative indices
    private int prefixSum(int row, int col) {
        if (row < 0 || col < 0) return 0;
        int sum = 0;
        // Convert to 1-indexed traversal
        for (int i = row + 1; i > 0; i -= i & -i) {
            for (int j = col + 1; j > 0; j -= j & -j) {
                sum += bit[i][j];
            }
        }
        return sum;
    }
}
🔑 Why 2D BIT is Optimal for This Problem
ApproachUpdateQuerySpaceVerdictNaiveO(1)O(m·n)O(1)❌ Fails with many queries (10⁴ × 40k = 400M ops)Recompute Prefix SumO(m·n)O(1)O(m·n)❌ Update too slow2D Segment TreeO(log m·log n)O(log m·log n)O(m·n)✅ Works, but complex✅ 2D BIT (Fenwick)O(log m·log n)O(log m·log n)O(m·n)BEST
🌟 Key Implementation Insights
1.Delta Tracking:
- Store original matrix to compute delta = newVal - oldVal on updates
- Critical: Problem sets value (not increments), so delta calculation is mandatory
2.1-Indexed BIT Internally:
- BIT array sized (m+1) × (n+1) to simplify bit operations
- Public methods use 0-indexed coordinates; conversion happens internally
3.Inclusion-Exclusion with Safety:
- prefixSum() handles negative indices (for row1-1/col1-1 edge cases)
- Clean formula: sum = P(r2,c2) - P(r1-1,c2) - P(r2,c1-1) + P(r1-1,c1-1)
4.BIT Operations:
- Update: Propagate delta through all responsible nodes using i += i & -i
- Query: Accumulate values from all covering nodes using i -= i & -i
📊 Complexity Analysis
OperationTime ComplexitySpace ComplexityConstructorO(m·n·log m·log n)O(m·n)update()O(log m · log n)O(1)sumRegion()O(log m · log n)O(1)
- With constraints (m, n ≤ 200, operations ≤ 10⁴):
- Worst-case operations: ~10⁴ × (log₂200)² ≈ 10⁴ × 64 = 640k operations ✅
- Naive approach would require ~400M operations ❌
💡 Why Not Segment Tree?
While 2D Segment Tree also achieves O(log m·log n):
- ✅ BIT Advantages:
- Simpler implementation (no recursion, fewer lines)
- Smaller constant factors (faster in practice)
- Less memory overhead
- ❌ Segment Tree Drawbacks:
- Complex 2D implementation (node structures, recursion)
- Overkill for pure sum queries (BIT suffices)
🔄 Connection to Other Problems
ProblemMutabilityOptimal DSKey Difference#303Immutable (1D)Prefix SumO(1) queries, no updates#304Immutable (2D)2D Prefix SumO(1) queries, no updates#307Mutable (1D)BIT / SegTree1D version of this problem✅ #308Mutable (2D)2D BITThis solution#1146Versioned ArrayHistory Map + BSDifferent pattern (snapshots)
🧪 Example Walkthrough
NumMatrix nm = new NumMatrix(new int[][]{
    {3, 0, 1, 4, 2},
    {5, 6, 3, 2, 1},
    {1, 2, 0, 1, 5},
    {4, 1, 0, 1, 7},
    {1, 0, 3, 0, 5}
});

nm.sumRegion(2, 1, 4, 3); // Returns 8 (region sum)
nm.update(3, 2, 2);       // Update matrix[3][2] from 0 → 2
nm.sumRegion(2, 1, 4, 3); // Returns 10 (updated sum)
This solution efficiently handles all constraints while maintaining clean, production-ready code. The 2D BIT pattern is essential for mutable range query problems and frequently appears in system design interviews for analytics engines or real-time dashboards. 📈


Refer to
L304.Range Sum Query 2D - Immutable (Ref.L303,L307,L308,L3030)
L307.Range Sum Query - Mutable
