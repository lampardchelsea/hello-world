/**
Refer to
https://leetcode.com/problems/generate-a-string-with-characters-that-have-odd-counts/
Given an integer n, return a string with n characters such that each character in such string occurs an odd number of times.

The returned string must contain only lowercase English letters. If there are multiples valid strings, return any of them.  

Example 1:
Input: n = 4
Output: "pppz"
Explanation: "pppz" is a valid string since the character 'p' occurs three times and the character 'z' occurs once. 
Note that there are many other valid strings such as "ohhh" and "love".

Example 2:
Input: n = 2
Output: "xy"
Explanation: "xy" is a valid string since the characters 'x' and 'y' occur once. Note that there are many other valid strings such as "ag" and "ur".

Example 3:
Input: n = 7
Output: "holasss"

Constraints:
1 <= n <= 500
*/

// Solution 1: One Lines
// Refer to
// https://leetcode.com/problems/generate-a-string-with-characters-that-have-odd-counts/discuss/532520/JavaC%2B%2BPython-One-Lines
/**
Explanation
If n is odd, we return "bbbb....b".
If n is even, we return "baaa...a".

Complexity
Time O(N)
Space O(N)

Java:
    public String generateTheString(int n) {
        return "b" + "ab".substring(n % 2, 1 + n % 2).repeat(n - 1);
*/
class Solution {
    public String generateTheString(int n) {
        return "b" + "ab".substring(n % 2, 1 + n % 2).repeat(n - 1);
    }
}
