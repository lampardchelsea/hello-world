https://leetcode.com/problems/destroying-asteroids/description/
You are given an integer mass, which represents the original mass of a planet. You are further given an integer array asteroids, where asteroids[i] is the mass of the ith asteroid.
You can arrange for the planet to collide with the asteroids in any arbitrary order. If the mass of the planet is greater than or equal to the mass of the asteroid, the asteroid is destroyed and the planet gains the mass of the asteroid. Otherwise, the planet is destroyed.
Return true if all asteroids can be destroyed. Otherwise, return false.

Example 1:
Input: mass = 10, asteroids = [3,9,19,5,21]
Output: true
Explanation: One way to order the asteroids is [9,19,5,3,21]:
- The planet collides with the asteroid with a mass of 9. New planet mass: 10 + 9 = 19
- The planet collides with the asteroid with a mass of 19. New planet mass: 19 + 19 = 38
- The planet collides with the asteroid with a mass of 5. New planet mass: 38 + 5 = 43
- The planet collides with the asteroid with a mass of 3. New planet mass: 43 + 3 = 46
- The planet collides with the asteroid with a mass of 21. New planet mass: 46 + 21 = 67
All asteroids are destroyed.

Example 2:
Input: mass = 5, asteroids = [4,9,23,4]
Output: false
Explanation: The planet cannot ever gain enough mass to destroy the asteroid with a mass of 23.
After the planet destroys the other asteroids, it will have a mass of 5 + 4 + 9 + 4 = 22.
This is less than 23, so a collision would not destroy the last asteroid.
 
Constraints:
- 1 <= mass <= 10^5
- 1 <= asteroids.length <= 10^5
- 1 <= asteroids[i] <= 10^5
--------------------------------------------------------------------------------
Attempt 1: 2024-10-21
Solution 1: Sorting + Greedy (10 min)
Wrong Solution (65/74)
class Solution {
    public boolean asteroidsDestroyed(int mass, int[] asteroids) {
        Arrays.sort(asteroids);
        for(int a : asteroids) {
            if(mass < a) {
                return false;
            }
            mass += a;
        }
        return true;
    }
}
Correct Solution
class Solution {
    public boolean asteroidsDestroyed(int mass, int[] asteroids) {
        Arrays.sort(asteroids);
        for(int a : asteroids) {
            if(mass < a) {
                return false;
            // Now we can conclude all can be destroyed
            } else if(mass > 100000) {
                return true;
            }
            mass += a;
        }
        return true;
    }
}


Sort then apply greedy algorithm.
Refer to
https://leetcode.com/problems/destroying-asteroids/solutions/1661044/java-python-3-sort-then-apply-greedy-algorithm/
    public boolean asteroidsDestroyed(int mass, int[] asteroids) {
        long m = mass;
        Arrays.sort(asteroids);
        for (int ast : asteroids) {
            if (m < ast) {
                return false;
            }
            m += ast;
        }
        return true;
    }
In case you do NOT like using long, we can use the constraints: 1 <= asteroids[i] <= 10^5: once mass is greater than all asteroids, we can conclude all can be destroyed.
    public boolean asteroidsDestroyed(int mass, int[] asteroids) {
        Arrays.sort(asteroids);
        for (int ast : asteroids) {
            if (mass < ast) {
                return false;
            }else if (mass > 100_000) { // now we can conclude all can be destroyed.
                return true;
            }
            mass += ast;
        }
        return true;
    }
Analysis:
Time: O(n * log(n)), space: O(n) - including sorting space.
