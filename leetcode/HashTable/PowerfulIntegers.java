/**
 Refer to
 https://leetcode.com/problems/powerful-integers/
 Given two positive integers x and y, an integer is powerful if it is equal to 
 x^i + y^j for some integers i >= 0 and j >= 0.

Return a list of all powerful integers that have value less than or equal to bound.
You may return the answer in any order.  In your answer, each value should occur at most once.

Example 1:
Input: x = 2, y = 3, bound = 10
Output: [2,3,4,5,7,9,10]
Explanation: 
2 = 2^0 + 3^0
3 = 2^1 + 3^0
4 = 2^0 + 3^1
5 = 2^1 + 3^1
7 = 2^2 + 3^1
9 = 2^3 + 3^0
10 = 2^0 + 3^2

Example 2:
Input: x = 3, y = 5, bound = 15
Output: [2,4,6,8,10,14]
 
Note:
1 <= x <= 100
1 <= y <= 100
0 <= bound <= 10^6
*/
// Solution 1: Native handle different cases
// Refer to
// https://leetcode.com/problems/powerful-integers/discuss/214212/JavaC++Python-Brute-Force/217857
class Solution {
    public List<Integer> powerfulIntegers(int x, int y, int bound) {
        Set<Integer> set = new HashSet<Integer>();
        if(x == 1 && y == 1) {
            if(bound >= 2) {
                set.add(2);
            }  
        } else if(x == 1) {
            for(int i = 1; i + 1 <= bound; i *= y) {
                set.add(i + 1);
            }
        } else if(y == 1) {
            for(int i = 1; i + 1 <= bound; i *= x) {
                set.add(i + 1);
            }
        } else {
            for(int i = 1; i < bound; i *= x) {
                for(int j = 1; i + j <= bound; j *= y) {
                    set.add(i + j);
                }
            }
        }
        return new ArrayList<Integer>(set);
    }
}

// Solution 2: Elegant Solution
// Refer to
// https://leetcode.com/problems/powerful-integers/discuss/214197/Java-straightforward-try-all-combinations
class Solution {
    public List<Integer> powerfulIntegers(int x, int y, int bound) {
        Set<Integer> set = new HashSet<Integer>();
        for(int i = 1; i < bound; i *= x) {
            for(int j = 1; i + j <= bound; j *= y) {
                set.add(i + j);
                if(y == 1) {
                    break;
                }
            }
            if(x == 1) {
                break;
            }
        }
        return new ArrayList<Integer>(set);
    }
}
