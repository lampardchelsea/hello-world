/**
 * Refer to
 * https://leetcode.com/problems/guess-number-higher-or-lower/description/
 * We are playing the Guess Game. The game is as follows:

    I pick a number from 1 to n. You have to guess which number I picked.

    Every time you guess wrong, I'll tell you whether the number is higher or lower.

    You call a pre-defined API guess(int num) which returns 3 possible results (-1, 1, or 0):

    -1 : My number is lower
     1 : My number is higher
     0 : Congrats! You got it!
    Example:
    n = 10, I pick 6.

    Return 6.
 *
 * Solution
 * https://leetcode.com/articles/guess-number-higher-or-lower/
 * https://discuss.leetcode.com/topic/51083/the-key-point-is-to-read-the-problem-carefully
*/


/* The guess API is defined in the parent class GuessGame.
   @param num, your guess
   @return -1 if my number is lower, 1 if my number is higher, otherwise return 0
      int guess(int num); */

public class Solution extends GuessGame {
    public int guessNumber(int n) {
        int lo = 0;
        int hi = n;
        while(lo + 1 < hi) {
            int mid = lo + (hi - lo) / 2;
            int res = guess(mid);
            if(res == 0) {
                return mid;
            } else if(res > 0) {
                // My number is higher -> 'mid' number is smaller
                lo = mid;
            } else if(res < 0) {
                // My number is lower -> 'mid' number is larger
                hi = mid;
            }
        }
        if(guess(lo) == 0) {
            return lo;
        } else {
            return hi;
        }
    }
}
