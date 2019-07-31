/**
 Refer to
 https://leetcode.com/problems/distribute-candies-to-people/
 We distribute some number of candies, to a row of n = num_people people in the following way:

We then give 1 candy to the first person, 2 candies to the second person, and so on until we give n 
candies to the last person.

Then, we go back to the start of the row, giving n + 1 candies to the first person, n + 2 candies to 
the second person, and so on until we give 2 * n candies to the last person.

This process repeats (with us giving one more candy each time, and moving to the start of the row after 
we reach the end) until we run out of candies.  The last person will receive all of our remaining candies 
(not necessarily one more than the previous gift).

Return an array (of length num_people and sum candies) that represents the final distribution of candies.

Example 1:
Input: candies = 7, num_people = 4
Output: [1,2,3,1]
Explanation:
On the first turn, ans[0] += 1, and the array is [1,0,0,0].
On the second turn, ans[1] += 2, and the array is [1,2,0,0].
On the third turn, ans[2] += 3, and the array is [1,2,3,0].
On the fourth turn, ans[3] += 1 (because there is only one candy left), and the final array is [1,2,3,1].

Example 2:
Input: candies = 10, num_people = 3
Output: [5,2,3]
Explanation: 
On the first turn, ans[0] += 1, and the array is [1,0,0].
On the second turn, ans[1] += 2, and the array is [1,2,0].
On the third turn, ans[2] += 3, and the array is [1,2,3].
On the fourth turn, ans[0] += 4, and the final array is [5,2,3].

Constraints:
1 <= candies <= 10^9
1 <= num_people <= 1000
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/distribute-candies-to-people/discuss/347852/Java-straightforward-solution-based-on-build-formula-to-calculate-rolling-row
class Solution {
    public int[] distributeCandies(int candies, int num_people) {
        int[] result = new int[num_people];
        int row = 0;
        for(int i = 0; i < num_people; i++) {
            // Relation for each index based on level is
            // i + 1 + num_people * row
            // e.g [60, 4] ->
            // i = 0   1   2   3
            //     1   2   3   4 -> row = 0
            //     5   6   7   8 -> row = 1
            //     9  10   5     -> row = 2
            if(candies - (i + 1 + num_people * row) >= 0) {
                result[i] += (i + 1 + num_people * row);
                candies -= (i + 1 + num_people * row);
                if(i == num_people - 1) {
                    row++;
                    // Don't forget to reset i to -1,
                    // not as 0 since i++ should be 0 to
                    // make sure turn into new row and
                    // start as index = 0
                    i = -1;
                }
            } else {
                // Remained value (current candies value)
                // set to current index
                result[i] += candies;
                // Don't forget to break out in case of
                // current i < num_people - 1, if not
                // break out, for loop will continue,
                // e.g [60, 4] expected [15, 18, 15, 12]
                // if not break out [15, 18, 15, 17]
                break;
            }
        }
        return result;
    }
}
