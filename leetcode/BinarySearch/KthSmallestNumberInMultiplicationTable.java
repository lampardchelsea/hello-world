/**
Refer to
https://leetcode.com/problems/kth-smallest-number-in-multiplication-table/
Nearly every one have used the Multiplication Table. But could you find out the k-th smallest 
number quickly from the multiplication table?

Given the height m and the length n of a m * n Multiplication Table, and a positive integer k, 
you need to return the k-th smallest number in this table.

Example 1:
Input: m = 3, n = 3, k = 5
Output: 
Explanation: 
The Multiplication Table:
1	2	3
2	4	6
3	6	9
The 5-th smallest number is 3 (1, 2, 2, 3, 3).

Example 2:
Input: m = 2, n = 3, k = 6
Output: 
Explanation: 
The Multiplication Table:
1	2	3
2	4	6
The 6-th smallest number is 6 (1, 2, 2, 3, 4, 6).

Note:
The m and n will be in the range [1, 30000].
The k will be in the range [1, m * n]
*/

// Solution 1: Approach #1: Brute Force [Memory Limit Exceeded]
// Refer to
// https://leetcode.com/problems/kth-smallest-number-in-multiplication-table/solution/
/**
Intuition and Algorithm
Create the multiplication table and sort it, then take the k th element.
Complexity Analysis
Time Complexity: O(m∗n) to create the table, and O(m∗nlog(m∗n)) to sort it.
Space Complexity: O(m∗n) to store the table.
*/
class Solution {
    public int findKthNumber(int m, int n, int k) {
        int[] table = new int[m * n];
        for(int i = 1; i <= m; i++) {
            for(int j = 1; j <= n; j++) {
                table[(i - 1) * n + j - 1] = i * j;
            }
        }
        Arrays.sort(table);
        return table[k - 1];
    }
}

// Solution 2: Binary Search
// Refer to
// https://leetcode.com/problems/kth-smallest-number-in-multiplication-table/solution/
/**
Approach #3: Binary Search [Accepted]
Intuition
As k and m*n are up to 9 * 10^8, linear solutions will not work. This motivates solutions with log complexity, 
such as binary search.

Algorithm
Let's do the binary search for the answer A.
Say enough(x) is true if and only if there are k or more values in the multiplication table that are less than or 
equal to x. Colloquially, enough describes whether x is large enough to be the k th value in the multiplication table.

Then (for our answer A), whenever x ≥ A, enough(x) is True; and whenever x < A, enough(x) is False.

In our binary search, our loop invariant is enough(hi) = True. At the beginning, enough(m*n) = True, and whenever hi is set, 
it is set to a value that is "enough" (enough(mi) = True). That means hi will be the lowest such value at the end of our binary search.

This leaves us with the task of counting how many values are less than or equal to x. For each of m rows, the ith row looks like 
[i, 2*i, 3*i, ..., n*i]. The largest possible k*i ≤ x that could appear is k = x/i. However, if x is really big, then perhaps k > n, 
so in total there are min(k, n) = min(x/i, n) values in that row that are less than or equal to x.

After we have the count of how many values in the table are less than or equal to x, by the definition of enough(x), 
we want to know if that count is greater than or equal to k.
*/

// https://leetcode.com/discuss/general-discussion/786126/python-powerful-ultimate-binary-search-template-solved-many-problems
/**
668. Kth Smallest Number in Multiplication Table [Hard]
Nearly every one have used the Multiplication Table. But could you find out the k-th smallest number quickly from the multiplication table? Given the height m and the length n of a m * n Multiplication Table, and a positive integer k, you need to return the k-th smallest number in this table.

Example :

Input: m = 3, n = 3, k = 5
Output: 3
Explanation: 
The Multiplication Table:
1	2	3
2	4	6
3	6	9

The 5-th smallest number is 3 (1, 2, 2, 3, 3).
For Kth-Smallest problems like this, what comes to our mind first is Heap. Usually we can maintain a Min-Heap and just pop the 
top of the Heap for k times. However, that doesn't work out in this problem. We don't have every single number in the entire 
Multiplication Table, instead, we only have the height and the length of the table. If we are to apply Heap method, we need to 
explicitly calculate these m * n values and save them to a heap. The time complexity and space complexity of this process are 
both O(mn), which is quite inefficient. This is when binary search comes in. Remember we say that designing condition function 
is the most difficult part? In order to find the k-th smallest value in the table, we can design an enough function, given an 
input num, determine whether there're at least k values less than or equal to num. The minimal num satisfying enough function 
is the answer we're looking for. Recall that the key to binary search is discovering monotonicity. In this problem, if num satisfies 
enough, then of course any value larger than num can satisfy. This monotonicity is the fundament of our binary search algorithm.

Let's consider search space. Obviously the lower bound should be 1, and the upper bound should be the largest value in the 
Multiplication Table, which is m * n, then we have search space [1, m * n]. The overwhelming advantage of binary search solution 
to heap solution is that it doesn't need to explicitly calculate all numbers in that table, all it needs is just picking up one 
value out of the search space and apply enough function to this value, to determine should we keep the left half or the right 
half of the search space. In this way, binary search solution only requires constant space complexity, much better than heap solution.

Next let's consider how to implement enough function. It can be observed that every row in the Multiplication Table is just multiples 
of its index. For example, all numbers in 3rd row [3,6,9,12,15...] are multiples of 3. Therefore, we can just go row by row to count 
the total number of entries less than or equal to input num. Following is the complete solution.

def findKthNumber(m: int, n: int, k: int) -> int:
    def enough(num) -> bool:
        count = 0
        for val in range(1, m + 1):  # count row by row
            add = min(num // val, n)
            if add == 0:  # early exit
                break
            count += add
        return count >= k                

    left, right = 1, n * m
    while left < right:
        mid = left + (right - left) // 2
        if enough(mid):
            right = mid
        else:
            left = mid + 1
    return left 
*/
class Solution {
    public int findKthNumber(int m, int n, int k) {
        int lo = 1;
        int hi = m * n;
        while(lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if(countNumber(m, n, mid) >= k) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }
    
    /**
    This leaves us with the task of counting how many values are less 
    than or equal to x. For each of m rows, the ith row looks like 
    [i, 2*i, 3*i, ..., n*i]. The largest possible k*i ≤ x that could 
    appear is k = x/i. However, if x is really big, then perhaps k > n, 
    so in total there are min(k, n) = min(x/i, n) values in that row 
    that are less than or equal to x.
    */
    private int countNumber(int m, int n, int x) {
        int count = 0;
        for(int i = 1; i <= m; i++) {
            count += Math.min(x / i, n);
        }
        return count;
    }
}
