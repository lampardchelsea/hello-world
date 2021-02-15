/**
Refer to
https://leetcode.com/problems/flip-string-to-monotone-increasing/
A string of '0's and '1's is monotone increasing if it consists of some number of '0's (possibly 0), followed by some number of '1's (also possibly 0.)

We are given a string S of '0's and '1's, and we may flip any '0' to a '1' or a '1' to a '0'.

Return the minimum number of flips to make S monotone increasing.

Example 1:
Input: "00110"
Output: 1
Explanation: We flip the last digit to get 00111.

Example 2:
Input: "010110"
Output: 2
Explanation: We flip to get 011111, or alternatively 000111.

Example 3:
Input: "00011000"
Output: 2
Explanation: We flip to get 00000000.

Note:
1 <= S.length <= 20000
S only consists of '0' and '1' characters.
*/

// Solution 1: DP intuitive find the relation between previous flip to current flip in different situations
// Refer to
// https://leetcode.com/problems/flip-string-to-monotone-increasing/discuss/186797/Java-DP-solution
/**
DP Solution
Mantain two variables flipToZero and flipToOne
flipToZero: how many flips we need so that we can flip current digit to zero.
flipToOne: how many flips we need so that we can flip current digit to one.
=========================================================================================================
DP recursion:
if current digit is zero
	flipToZero[i] = 0 + flipToZero[i-1]  //no need to flip the current digit and the previous digit must be zero
	flipToOne[i] = 1 + min(flipToZero[i-1], flipToOne[i-1]) // need flip zero to one and the previous digit can be either zero or one
if current digit is one
	flipToZero[i] = 1 + flipToZero[i-1]  //need to flip one to zero and the previous digit must be zero
	flipToOne[i] = 0 + min(flipToZero[i-1], flipToOne[i-1])  // no need to flip the current digit and the previous digit can be either zero or one

So if we use current digit = n, we can re-write above 4 situations into 2 formulas:
flipToZero[i] = n + flipToZero[i-1]  -->  flip_to_zero = n + prev_flip_to_zero
flipToOne[i] = (1-n) + min(flipToZero[i-1], flipToOne[i-1])  -->  flip_to_one = (1-n) + min(prev_flip_to_zero, prev_flip_to_one)
=========================================================================================================
Here is an example:
---------------------------------------------------------------------------------------------------------
  S             0   0   1   1   0  index = 0 (initial status)
0(flipToZero)   0                  prev_flip_to_zero = S.charAt(0) - '0' = 0
1(flipToOne)    1                  prev_flip_to_one = 1 - prev_flip_to_zero = 1 - 0 = 1
---------------------------------------------------------------------------------------------------------
  S             0   0   1   1   0  index = 1 (n = S.charAt(1) = 0)
0(flipToZero)   0   0              flip_to_zero = 0 + prev_flip_to_zero = 0 + 0 = 0
1(flipToOne)    1   1              flip_to_one = (1 - 0) + Math.min(prev_flip_to_zero, prev_flip_to_one) = 1 + 0 = 1
                                   prev_flip_to_zero = flip_to_zero = 0
                                   prev_flip_to_one = flip_to_one = 1
---------------------------------------------------------------------------------------------------------
  S             0   0   1   1   0  index = 2 (n = S.charAt(2) = 1)
0(flipToZero)   0   0   1          flip_to_zero = 1 + prev_flip_to_zero = 1 + 0 = 1
1(flipToOne)    1   1   0          flip_to_one = (1 - 1) + Math.min(prev_flip_to_zero, prev_flip_to_one) = 0 + 0 = 0
                                   prev_flip_to_zero = flip_to_zero = 1
                                   prev_flip_to_one = flip_to_one = 0
---------------------------------------------------------------------------------------------------------
  S             0   0   1   1   0  index = 3 (n = S.charAt(3) = 1)
0(flipToZero)   0   0   1   2      flip_to_zero = 1 + prev_flip_to_zero = 1 + 1 = 2
1(flipToOne)    1   1   0   0      flip_to_one = (1 - 1) + Math.min(prev_flip_to_zero, prev_flip_to_one) = 0 + 0 = 0
                                   prev_flip_to_zero = flip_to_zero = 2
                                   prev_flip_to_one = flip_to_one = 0
---------------------------------------------------------------------------------------------------------
  S             0   0   1   1   0  index = 4 (n = S.charAt(4) = 0)
0(flipToZero)   0   0   1   2   2  flip_to_zero = 1 + prev_flip_to_zero = 0 + 2 = 2
1(flipToOne)    1   1   0   0   1  flip_to_one = (1 - 0) + Math.min(prev_flip_to_zero, prev_flip_to_one) = 1 + 0 = 1
                                   prev_flip_to_zero = flip_to_zero = 2
                                   prev_flip_to_one = flip_to_one = 1
---------------------------------------------------------------------------------------------------------
Final result = Math.min(2, 1) = 1
*/
class Solution {
    public int minFlipsMonoIncr(String S) {
        int prev_flip_to_zero = S.charAt(0) - '0';
        int prev_flip_to_one = 1 - prev_flip_to_zero;
        int flip_to_zero = 0;
        int flip_to_one = 0;
        for(int i = 1; i < S.length(); i++) {
            int n = S.charAt(i) - '0';
            flip_to_zero = prev_flip_to_zero + n;
            flip_to_one = (1 - n) + Math.min(prev_flip_to_zero, prev_flip_to_one);
            prev_flip_to_zero = flip_to_zero;
            prev_flip_to_one = flip_to_one;
        }
        return Math.min(flip_to_zero, flip_to_one);
    }
}

// Solution 2: Another DP style
// Refer to
// https://leetcode.com/problems/flip-string-to-monotone-increasing/discuss/189751/C%2B%2B-one-pass-DP-solution-0ms-O(n)-or-O(1)-one-line-with-explaination.
/**
This is a typical case of DP.

Let's see the sub-question of DP first.

Suppose that you have a string s, and the solution to the mono increase question is already solved. That is, for string s, counter_flip 
flips are required for the string, and there were counter_one '1's in the original string s.

Let's see the next step of DP.

Within the string s, a new incoming character, say ch, is appended to the original string. The question is that, how should counter_flip 
be updated, based on the sub-question? We should discuss it case by case.

When '1' comes, no more flip should be applied, since '1' is appended to the tail of the original string.
When '0' comes, things become a little bit complicated. There are two options for us: flip the newly appended '0' to '1', after counter_flip 
flips for the original string; or flip counter_one '1' in the original string to '0'. Hence, the result of the next step of DP, in the '0' 
case, is std::min(counter_flip + 1, counter_one);.
Based on these analysis, the solution comes.

class Solution {
public:
    int minFlipsMonoIncr(const std::string& S, int counter_one  = 0, int counter_flip = 0) {
        for (auto ch : S) counter_flip = std::min(counter_one += ch - '0', counter_flip + '1' - ch);
        return counter_flip;
    }
};

If you find the above snippet of code is somewhat difficult to understand, try the below one.
class Solution {
public:
    int minFlipsMonoIncr(const std::string& S, int counter_one  = 0, int counter_flip = 0) {
        for (auto ch : S) {
            if (ch == '1') {
                ++counter_one;
            } else {
                ++counter_flip;
            }
            counter_flip = std::min(counter_one, counter_flip);
        }
        return counter_flip;
    }
};
*/
class Solution {
    public int minFlipsMonoIncr(String S) {
        int count_one = 0;
        int count_flip = 0;
        for(char c : S.toCharArray()) {
            if(c == '1') {
                count_one++;
            } else {
                count_flip++;
            }
            count_flip = Math.min(count_one, count_flip);
        }
        return count_flip;
    }
}

