/**
Refer to
https://leetcode.com/problems/advantage-shuffle/
Given two arrays A and B of equal size, the advantage of A with respect to B is the number of indices i for which A[i] > B[i].

Return any permutation of A that maximizes its advantage with respect to B.

Example 1:
Input: A = [2,7,11,15], B = [1,10,4,11]
Output: [2,11,7,15]

Example 2:
Input: A = [12,24,8,32], B = [13,25,32,11]
Output: [24,32,8,12]

Note:
1 <= A.length = B.length <= 10000
0 <= A[i] <= 10^9
0 <= B[i] <= 10^9
*/

// Solution 1: Greedy
// Refer to
// https://leetcode.com/problems/advantage-shuffle/discuss/149822/JAVA-Greedy-6-lines-with-Explanation
/**
[idea]
2 rules:
1. We should always first satisfy the biggest element of B, because they are the hardest to satisfy.
2. If the biggest value of A can not satisfy current value of B, nothing can satisfy.

[code]
1. sort A
2. create res
3. create a priority queue which sorts with value B in decreasing order
4. initialize the pq with B
5. initialize lo = 0 and hi = n - 1
6. while pq is not empty, we still have work undone:
   (1) extract the value and index of the current biggest element on pq of B
   (2) if A[hi] > val, it means A can satisfy current value of B: res[idx] = A[hi--]
   (3) else B cannot be satisfied, we just put the smallest element in that slot: res[idx] = A[lo++], 
       which actually consume the most difficult to satisfy element with minimum expense
7. return res in the end
*/

class Solution {
    public int[] advantageCount(int[] A, int[] B) {
        int n = B.length;
        Arrays.sort(A);
        PriorityQueue<int[]> maxPQ = new PriorityQueue<int[]>(n, (a, b) -> b[0] - a[0]);
        for(int i = 0; i < n; i++) {
            maxPQ.offer(new int[] {B[i], i});
        }
        int lo = 0;
        int hi = n - 1;
        int[] result = new int[n];
        while(!maxPQ.isEmpty()) {
            int[] cur = maxPQ.poll();
            int val = cur[0];
            int idx = cur[1];
            if(A[hi] > val) {
                result[idx] = A[hi];
                hi--;
            } else {
                result[idx] = A[lo];
                lo++;
            }
        }
        return result;
    }
}
