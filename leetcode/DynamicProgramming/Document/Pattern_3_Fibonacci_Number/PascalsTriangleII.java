/**
 Refer to
 https://leetcode.com/problems/pascals-triangle-ii/
 Given a non-negative index k where k ≤ 33, return the kth index row of the Pascal's triangle.

Note that the row index starts from 0
In Pascal's triangle, each number is the sum of the two numbers directly above it.

Example:
Input: 3
Output: [1,3,3,1]
Follow up:

Could you optimize your algorithm to use only O(k) extra space?
*/
// Solution 1:
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/array/PascalTriangleII.java
// Runtime: 1 ms, faster than 88.94% of Java online submissions for Pascal's Triangle II.
// Memory Usage: 33.6 MB, less than 6.17% of Java online submissions for Pascal's Triangle II.
class Solution {
    public List<Integer> getRow(int rowIndex) {
        List<Integer> result = new ArrayList<Integer>(1 + rowIndex);
        for(int i = 0; i <= rowIndex; i++) {
            result.add(0);
        }
        for(int i = 0; i <= rowIndex; i++) {
            result.set(i, 1);
            for(int j = i - 1; j > 0; j--) {
                result.set(j, result.get(j - 1) + result.get(j));
            }
        }
        return result;
    }
}

// Solution 2:
// Refer to
// https://leetcode.com/problems/pascals-triangle-ii/discuss/38584/Another-accepted-Java-solution
// Runtime: 1 ms, faster than 88.94% of Java online submissions for Pascal's Triangle II.
// Memory Usage: 33.8 MB, less than 6.17% of Java online submissions for Pascal's Triangle II.
// Styel 1:
class Solution {
    public List<Integer> getRow(int rowIndex) {
        Integer[] dp = new Integer[rowIndex + 1];
        Arrays.fill(dp, 0);
        dp[0] = 1;
        for(int i = 1; i <= rowIndex; i++) {
            for(int j = i; j > 0; j--) {
                dp[j] += dp[j - 1];
            }
        }
        return Arrays.asList(dp);
    }
}

// Style 2:
class Solution {
    public List<Integer> getRow(int rowIndex) {
        Integer[] dp = new Integer[rowIndex + 1];
        for(int i = 0; i <= rowIndex; i++) {
            dp[i] = 1;
            for(int j = i - 1; j > 0; j--) {
                dp[j] += dp[j -1];
            }
        }
        return Arrays.asList(dp);
    }
}
