/**
Refer to
https://leetcode.com/problems/the-k-weakest-rows-in-a-matrix/
Given a m * n matrix mat of ones (representing soldiers) and zeros (representing civilians), return the indexes 
of the k weakest rows in the matrix ordered from the weakest to the strongest.

A row i is weaker than row j, if the number of soldiers in row i is less than the number of soldiers in row j, 
or they have the same number of soldiers but i is less than j. Soldiers are always stand in the frontier of a row, 
that is, always ones may appear first and then zeros.

Example 1:
Input: mat = 
[[1,1,0,0,0],
 [1,1,1,1,0],
 [1,0,0,0,0],
 [1,1,0,0,0],
 [1,1,1,1,1]], 
k = 3
Output: [2,0,3]
Explanation: 
The number of soldiers for each row is: 
row 0 -> 2 
row 1 -> 4 
row 2 -> 1 
row 3 -> 2 
row 4 -> 5 
Rows ordered from the weakest to the strongest are [2,0,3,1,4]

Example 2:
Input: mat = 
[[1,0,0,0],
 [1,1,1,1],
 [1,0,0,0],
 [1,0,0,0]], 
k = 2
Output: [0,2]
Explanation: 
The number of soldiers for each row is: 
row 0 -> 1 
row 1 -> 4 
row 2 -> 1 
row 3 -> 1 
Rows ordered from the weakest to the strongest are [0,2,3,1]

Constraints:
m == mat.length
n == mat[i].length
2 <= n, m <= 100
1 <= k <= m
matrix[i][j] is either 0 or 1.
*/

// Solution 1: Heap + Binary Search
// Refer to
// https://leetcode.com/problems/the-k-weakest-rows-in-a-matrix/discuss/496555/Java-Best-Solution-100-TimeSpace-Binary-Search-%2B-Heap
/**
class Solution {
    public int[] kWeakestRows(int[][] mat, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] != b[0] ? b[0] - a[0] : b[1] - a[1]);
        int[] ans = new int[k];
        
        for (int i = 0; i < mat.length; i++) {
            pq.offer(new int[] {numOnes(mat[i]), i});
            if (pq.size() > k)
                pq.poll();
        }
        
        while (k > 0)
            ans[--k] = pq.poll()[1];
        
        return ans;
    }
    
    private int numOnes(int[] row) {
        int lo = 0;
        int hi = row.length;
        
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            
            if (row[mid] == 1)
                lo = mid + 1;
            else
                hi = mid;
        }
        
        return lo;
    }
}
*/

// https://leetcode.com/problems/the-k-weakest-rows-in-a-matrix/discuss/496555/Java-Best-Solution-100-TimeSpace-Binary-Search-+-Heap/504907
/**
private int count(int[] row){
        // find last 1
        int lo = 0;
        int hi = row.length - 1;
        while (lo + 1 < hi){
            int mid = lo + (hi - lo) / 2;
            if (row[mid] == 0){
                hi = mid - 1;
            }else{
                lo = mid;
            }
        }
        if (row[hi] == 1){
            return hi + 1;
        }else if (row[lo] == 1){
            return lo + 1;
        }else{
            return 0;
        }
    }
*/
class Solution {
    public int[] kWeakestRows(int[][] mat, int k) {
        // int[] element -> element[0] = number of 1 in row, element[1] = row index
        PriorityQueue<int[]> minPQ = new PriorityQueue<int[]>((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        int rows = mat.length;
        for(int i = 0; i < rows; i++) {
            minPQ.offer(new int[] {findOneCountInRow(mat[i]), i});
        }
        int[] result = new int[k];
        for(int i = 0; i < k; i++) {
            result[i] = minPQ.poll()[1];
        }
        return result;
    }
    
    // Since already sort as all 1 in front, we can do binary search
    // to find last position of 1
    private int findOneCountInRow(int[] row) {
        int lo = 0;
        int hi = row.length - 1;
        // Find last 1 position
        while(lo + 1 < hi) {
            int mid = lo + (hi - lo) / 2;
            if(row[mid] == 0) {
                hi = mid - 1;
            } else {
                lo = mid;
            }
        }
        if(row[hi] == 1) {
            return hi + 1;
        } else if(row[lo] == 1) {
            return lo + 1;
        } else {
            return 0;
        }
    }
}
