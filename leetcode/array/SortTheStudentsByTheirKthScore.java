https://leetcode.com/problems/sort-the-students-by-their-kth-score/description/
There is a class with m students and n exams. You are given a 0-indexed m x n integer matrix score, where each row represents one student and score[i][j] denotes the score the ith student got in the jth exam. The matrix score contains distinct integers only.
You are also given an integer k. Sort the students (i.e., the rows of the matrix) by their scores in the kth (0-indexed) exam from the highest to the lowest.
Return the matrix after sorting it.
 
Example 1:

Input: score = [[10,6,9,1],[7,5,11,2],[4,8,3,15]], k = 2
Output: [[7,5,11,2],[10,6,9,1],[4,8,3,15]]
Explanation: In the above diagram, S denotes the student, while E denotes the exam.
- The student with index 1 scored 11 in exam 2, which is the highest score, so they got first place.
- The student with index 0 scored 9 in exam 2, which is the second highest score, so they got second place.
- The student with index 2 scored 3 in exam 2, which is the lowest score, so they got third place.

Example 2:

Input: score = [[3,4],[5,6]], k = 0
Output: [[5,6],[3,4]]
Explanation: In the above diagram, S denotes the student, while E denotes the exam.
- The student with index 1 scored 5 in exam 0, which is the highest score, so they got first place.
- The student with index 0 scored 3 in exam 0, which is the lowest score, so they got second place.
 
Constraints:
- m == score.length
- n == score[i].length
- 1 <= m, n <= 250
- 1 <= score[i][j] <= 105
- score consists of distinct integers.
- 0 <= k < n
--------------------------------------------------------------------------------
Attempt 1: 2026-05-18
Solution 1: Matrix + Sorting (10 min)
class Solution {
    public int[][] sortTheStudents(int[][] score, int k) {
        Arrays.sort(score, (a, b) -> (b[k] - a[k]));
        return score;
    }
}

Time Complexity: O(m log m) where m is the number of rows (score.length). 
Java's Arrays.sort() for objects uses Timsort, which runs in O(m log m) time. 
Each comparison is O(1) since we only access index k.
Space Complexity: O(m) auxiliary space in the worst case for Timsort's internal buffer. 
No extra data structures are created.

Refer to QWEN
Here's a clean and optimal Java solution for LeetCode 2545. Sort the Students by Their Kth Score:
import java.util.Arrays;

class Solution {
    public int[][] sortTheStudents(int[][] score, int k) {
        // Sort the 2D array rows in descending order based on the k-th column
        Arrays.sort(score, (a, b) -> b[k] - a[k]);
        return score;
    }
}
🔍 How it Works:
1.In Java, a 2D array int[][] is actually an array of objects (int[] objects representing each row).
2.Arrays.sort() accepts a custom Comparator for object arrays. We pass a lambda (a, b) -> b[k] - a[k] which tells Java to compare two rows a and b based on their value at index k.
3.b[k] - a[k] ensures descending order. (If you wanted ascending, it would be a[k] - b[k]).
4.The sorting happens in-place, so we simply return the modified score matrix.
⏱️ Complexity:
- Time Complexity: O(m log m) where m is the number of rows (score.length). Java's Arrays.sort() for objects uses Timsort, which runs in O(m log m) time. Each comparison is O(1) since we only access index k.
- Space Complexity: O(m) auxiliary space in the worst case for Timsort's internal buffer. No extra data structures are created.
💡 Pro Tips:
- Overflow Safety: While b[k] - a[k] is perfectly safe here (scores are between 1 and 10^5), a more robust practice for production code is:
Arrays.sort(score, (a, b) -> Integer.compare(b[k], a[k]));
This prevents integer overflow if values could be negative or extremely large.
- Why not Stream API? Arrays.stream(score).sorted(...).toArray(int[][]::new) works but creates unnecessary object overhead and is slower than the in-place Arrays.sort().
Let me know if you need a version that sorts in ascending order or handles additional constraints!

Refer to
L791.Custom Sort String (Ref.L2545)
