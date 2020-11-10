/**
 Refer to
 https://www.cnblogs.com/Dylan-Java-NYC/p/11980644.html
 原题链接在这里：https://leetcode.com/problems/find-k-length-substrings-with-no-repeated-characters/

题目：
Given a string S, return the number of substrings of length K with no repeated characters.

Example 1:
Input: S = "havefunonleetcode", K = 5
Output: 6
Explanation: 
There are 6 substrings they are : 'havef','avefu','vefun','efuno','etcod','tcode'.

Example 2:
Input: S = "home", K = 5
Output: 0
Explanation: 
Notice K can be larger than the length of S. In this case is not possible to find any substring.

Note:
1 <= S.length <= 10^4
All characters of S are lowercase English letters.
1 <= K <= 10^4
*/

// Solution 1: Sliding Window
// Refer to
// https://www.cnblogs.com/Dylan-Java-NYC/p/11980644.html
