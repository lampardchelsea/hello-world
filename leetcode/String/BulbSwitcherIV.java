/**
Refer to
https://leetcode.com/problems/bulb-switcher-iv/
There is a room with n bulbs, numbered from 0 to n - 1, arranged in a row from left to right. Initially, all the bulbs are turned off.

Your task is to obtain the configuration represented by target where target[i] is '1' if the ith bulb is turned on and is '0' if it is turned off.

You have a switch to flip the state of the bulb, a flip operation is defined as follows:

Choose any bulb (index i) of your current configuration.
Flip each bulb from index i to index n - 1.
When any bulb is flipped it means that if it is '0' it changes to '1' and if it is '1' it changes to '0'.

Return the minimum number of flips required to form target.

Example 1:
Input: target = "10111"
Output: 3
Explanation: Initial configuration "00000".
flip from the third bulb:  "00000" -> "00111"
flip from the first bulb:  "00111" -> "11000"
flip from the second bulb:  "11000" -> "10111"
We need at least 3 flip operations to form target.

Example 2:
Input: target = "101"
Output: 3
Explanation: "000" -> "111" -> "100" -> "101".

Example 3:
Input: target = "00000"
Output: 0

Example 4:
Input: target = "001011101"
Output: 5

Constraints:
1 <= target.length <= 105
target[i] is either '0' or '1'.
*/

// Solution 1: Flip is a must when current status of the bulbs and target status are not same.
// Refer to
// https://leetcode.com/problems/bulb-switcher-iv/discuss/755939/C%2B%2B-Python-Java%3A-Readable-easy-code-with-explanation-and-code-comments
/**
Key idea
Flip is a must when current status of the bulbs and target status are not same.

Initially all bulbs are at 0 and when a bulb at index i is flipped, everything that comes after that gets flipped. 
So it only makes sense to keep getting the status of the bulbs correct from left to right.

We will keep track of the status of the bulbs as we make flips.

Initially all bulbs are 0, so say status = 0.
Bulb 0 is 0 initially.

If we want it to be 0, we don't have to make a flip.
If we want it to be 1, we must make a flip. This will change the status of remaining bulbs and they will be ON i.e. 1, so we will also make status as 1.
Whenever we see the status to be different from what the target is, we must make a flip and this will also change the status of remaining bulbs.

class Solution {
public:
    int minFlips(string target) {
        int n = target.size();                          // Total bulbs.
        int flips = 0;                                  // Final answer.
        char status = '0';                              // This stores the status of bulbs that are
                                                        // ahead of current index `i`.
        for (int i=0; i<n; i++) {                       // For each bulb =
            if (status != target[i]) {                  // If what we want is different from what
                                                        // it is at this moment, make a flip.
                flips++;                                // We made a flip.
                status = status == '0' ?  '1' : '0';    // Now status of remaining
                                                        // bulbs have changed.
            }
        }
        return flips; // Awesome, return the answer now.
    }
};

Java
class Solution {
    public int minFlips(String target) {
        int n = target.length();
        int flips = 0;
        char status = '0';
        for (int i = 0; i < n; i++) {
            if (status != target.charAt(i)) {
                flips++;
            }
            status = flips % 2 == 1 ? '1' : '0'; 
        }
        return flips;
    }
}
*/
// Test with "10111" is fine
class Solution {
    public int minFlips(String target) {
        int flip = 0;
        char status = '0';
        for(char c : target.toCharArray()) {
            if(c != status) {
                flip++;
                status = flip % 2 == 1 ? '1' : '0';
            }
        }
        return flip;
    }
}
