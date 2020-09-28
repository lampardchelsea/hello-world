/**
 Refer to
 https://leetcode.com/problems/numbers-with-same-consecutive-differences/
 Return all non-negative integers of length n such that the absolute difference between every two consecutive digits is k.
 Note that every number in the answer must not have leading zeros except for the number 0 itself. 
 For example, 01 has one leading zero and is invalid, but 0 is valid.
 
 You may return the answer in any order.

Example 1:
Input: n = 3, k = 7
Output: [181,292,707,818,929]
Explanation: Note that 070 is not a valid number, because it has leading zeroes.

Example 2:
Input: n = 2, k = 1
Output: [10,12,21,23,32,34,43,45,54,56,65,67,76,78,87,89,98]

Example 3:
Input: n = 2, k = 0
Output: [11,22,33,44,55,66,77,88,99]

Example 4:
Input: n = 2, k = 1
Output: [10,12,21,23,32,34,43,45,54,56,65,67,76,78,87,89,98]

Example 5:
Input: n = 2, k = 2
Output: [13,20,24,31,35,42,46,53,57,64,68,75,79,86,97]

Constraints:
2 <= n <= 9
0 <= k <= 9
*/

// Solution 1: 
// Refer to
// https://leetcode.com/problems/numbers-with-same-consecutive-differences/discuss/798716/Java-Idea-Explained-or-DFS-Easy-to-Understand
/**
 We have to build all the numbers which satisfy the property - absolute difference between any two consecutive digits is K.
So, we will start from each digits 1-9 as leading 0 digits of N > 1 is not valid. Let it call num.
How to choose next digits ?
For getting previousDigits from any number we just need to do modulo with 10. ==> previousDigits = num % 10
Now we have 2 choices

if previousDigits + K < 10 then nextDigits = previousDigits + K and newNum = num * 10 + nextDigits
if previousDigits - K >= 0 then nextDigits = previousDigits - K and newNum = num * 10 + nextDigits
Also we need to take care the case if K = 0, in that case previousDigits + K == previousDigits - K so we only need one case from 
above two case. we will avoid this by adding extra condition in case 2: K > 0 like K > 0 && previousDigits - K >= 0
*/
class Solution {
    public int[] numsSameConsecDiff(int n, int k) {
        List<Integer> list = new ArrayList<Integer>();
        for(int i = 1; i <= 9; i++) {
            helper(i, n - 1, k, list);
        }
        int[] result = new int[list.size()];
        for(int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }
    
    private void helper(int currNum, int n, int k, List<Integer> list) {
        if(n == 0) {
            list.add(currNum);
            return;
        }
        // Most important step to get last digit
        int lastDigit = currNum % 10;
        if(lastDigit + k < 10) {
            helper(currNum * 10 + (lastDigit + k), n - 1, k, list);
        }
        // k > 0 added for n = 2, k = 0 and generate duplicate cases
        // Output
        // [11,11,22,22,33,33,44,44,55,55,66,66,77,77,88,88,99,99]
        // Expected
        // [11,22,33,44,55,66,77,88,99]
        if(lastDigit - k >= 0 && k > 0) {
            helper(currNum * 10 + (lastDigit - k), n - 1, k, list);
        }
    }
}
