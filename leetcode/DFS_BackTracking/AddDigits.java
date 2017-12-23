/**
 * Refer to
 * https://leetcode.com/problems/add-digits/description/
 * Given a non-negative integer num, repeatedly add all its digits until the result has only one digit.

    For example:

    Given num = 38, the process is like: 3 + 8 = 11, 1 + 1 = 2. Since 2 has only one digit, return it.

    Follow up:
    Could you do it without any loop/recursion in O(1) runtime?
 * 
 * Solution
 * https://stackoverflow.com/questions/4968323/java-parse-int-value-from-a-char
 * http://www.cnblogs.com/grandyang/p/4741028.html
*/
// Solution 1: DFS
class Solution {
    int result = 0;
    public int addDigits(int num) {
        helper(num);
        return result;
    }
    
    private void helper(int num) {
        String tmp = String.valueOf(num);
        if(tmp.length() == 1) {
            result = Integer.valueOf(tmp);
        } else {
            char[] chars = tmp.toCharArray();
            int val = 0;
            for(char c : chars) {
                val += (c - '0');
            }
            helper(val);   
        }
    }
}

// Solution 2: 
// Refer to
// http://www.cnblogs.com/grandyang/p/4741028.html
/** 
  但是这个解法在出题人看来又trivial又naive，需要想点高逼格的解法，一行搞定碉堡了，
  那么我们先来观察1到20的所有的树根：
  
    1    1
    2    2
    3    3
    4    4
    5    5
    6    6
    7    7
    8    8    
    9    9    
    10    1
    11    2
    12    3    
    13    4
    14    5
    15    6
    16    7
    17    8
    18    9
    19    1
    20    2

    根据上面的列举，我们可以得出规律，每9个一循环，所有大于9的数的树根都是对9取余，
    那么对于等于9的数对9取余就是0了，为了得到其本身，而且同样也要对大于9的数适用，
    我们就用(n-1)%9+1这个表达式来包括所有的情况
*/
class Solution {
    public int addDigits(int num) {
        return (num - 1) % 9 + 1;
    }
}












