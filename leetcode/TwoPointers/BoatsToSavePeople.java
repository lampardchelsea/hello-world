/**
Refer to
https://leetcode.com/problems/boats-to-save-people/
The i-th person has weight people[i], and each boat can carry a maximum weight of limit.

Each boat carries at most 2 people at the same time, provided the sum of the weight of those people is at most limit.

Return the minimum number of boats to carry every given person.  (It is guaranteed each person can be carried by a boat.)

Example 1:
Input: people = [1,2], limit = 3
Output: 1
Explanation: 1 boat (1, 2)

Example 2:
Input: people = [3,2,2,1], limit = 3
Output: 3
Explanation: 3 boats (1, 2), (2) and (3)

Example 3:
Input: people = [3,5,3,4], limit = 5
Output: 4
Explanation: 4 boats (3), (3), (4), (5)

Note:
1 <= people.length <= 50000
1 <= people[i] <= limit <= 30000
*/

// Solution 1: Two pointers + Greedy
// Refer to
// https://leetcode.com/problems/boats-to-save-people/solution/
/**
Approach 1: Greedy (Two Pointer)
Intuition
If the heaviest person can share a boat with the lightest person, then do so. Otherwise, the heaviest person can't pair with anyone, 
so they get their own boat.
The reason this works is because if the lightest person can pair with anyone, they might as well pair with the heaviest person.

Algorithm
Let people[i] to the currently lightest person, and people[j] to the heaviest.
Then, as described above, if the heaviest person can share a boat with the lightest person (if people[j] + people[i] <= limit) 
then do so; otherwise, the heaviest person sits in their own boat.
class Solution {
    public int numRescueBoats(int[] people, int limit) {
        Arrays.sort(people);
        int i = 0, j = people.length - 1;
        int ans = 0;

        while (i <= j) {
            ans++;
            if (people[i] + people[j] <= limit)
                i++;
            j--;
        }

        return ans;
    }
}
*/

class Solution {
    public int numRescueBoats(int[] people, int limit) {
        Arrays.sort(people);
        int result = 0;
        int i = 0;
        int j = people.length - 1;
        while(i <= j) {
            result++;
            if(people[i] + people[j] <= limit) {
                i++;
            }
            j--;
        }
        return result;
    }
}


