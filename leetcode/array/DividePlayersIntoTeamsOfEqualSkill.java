https://leetcode.com/problems/divide-players-into-teams-of-equal-skill/
You are given a positive integer array skill of even length n where skill[i] denotes the skill of the ith player. Divide the players into n / 2 teams of size 2 such that the total skill of each team is equal.
The chemistry of a team is equal to the product of the skills of the players on that team.
Return the sum of the chemistry of all the teams, or return -1 if there is no way to divide the players into teams such that the total skill of each team is equal.
 
Example 1:
Input: skill = [3,2,5,1,3,4]
Output: 22
Explanation: 
Divide the players into the following teams: (1, 5), (2, 4), (3, 3), where each team has a total skill of 6.
The sum of the chemistry of all the teams is: 1 * 5 + 2 * 4 + 3 * 3 = 5 + 8 + 9 = 22.

Example 2:
Input: skill = [3,4]
Output: 12
Explanation: 
The two players form a team with a total skill of 7.
The chemistry of the team is 3 * 4 = 12.

Example 3:
Input: skill = [1,1,2,3]
Output: -1
Explanation: 
There is no way to divide the players into teams such that the total skill of each team is equal.
 
Constraints:
2 <= skill.length <= 10^5
skill.length is even.
1 <= skill[i] <= 1000
--------------------------------------------------------------------------------
Attempt 1: 2024-01-12
Solution 1: Sorting + Two Pointers (10min)
After sorting, we set a target total skill value, which is the sum of the first and last element in the sorted array
本题最关键的一步就是因为要严格分出所有2人一组的球队，那么对于每一只队伍的skill value总和是多少呢？先把整个输入排序，然后最小与最大值的和就一定是，也唯一是skill value的总和，因为如果skill value不是这个最小加上最大值说明至少最小或者最大值这一组里面有一个数不满足组成一只球队的要求，那么总体上就无法平均分出2人一组的多支球队
class Solution {
    public long dividePlayers(int[] skill) {
        Arrays.sort(skill);
        int i = 0;
        int j = skill.length - 1;
        int sum = skill[i] + skill[j];
        long result = 0;
        while(i < j) {
            if(skill[i] + skill[j] != sum) {
                return -1;
            } else {
                result += (long)skill[i] * skill[j];
                i++;
                j--;
            }
        }
        return result;
    }
}

Time Complexity: O(N*logN)
Space Complexity: O(1)

Refer to
https://algo.monster/liteproblems/2491
Problem Description
In this problem, you are tasked with dividing players with certain skills into teams. The players are represented by a positive integer array called skill, which has an even number of elements denoted by n. Each element in the array represents the skill level of a player, with skill[i] corresponding to the i-th player's skill.
The players must be divided into n / 2 teams, with each team consisting of two players. The key condition for forming these teams is that the total skill (sum of the skill levels of both team members) must be the same for every team.
The chemistry of a team is defined as the product of the skill levels of the two players in that team. The objective is to calculate and return the sum of the chemistry of all teams. However, if it is impossible to divide the players into teams that all have an equal total skill, you should return -1.
Intuition
The intuition behind the solution is to first sort the skill array in increasing order. Sorting is useful because it helps in easily pairing players in such a way that might meet the condition of equal total skill for each team.
After sorting, we set a target total skill value, which is the sum of the first and last element in the sorted array. Considering the array is sorted, this target value should be what we aim to match for each team. We set two pointers, one at the start and one at the end of the sorted array, and start pairing players by moving these pointers towards each other.
At each iteration, we check whether the sum of the skills of the two selected players (pointed by i and j) equals the target skill value (t). If it doesn't match the target, then we know immediately that it's impossible to form the teams with an equal total skill and we return -1.
If it matches, we compute the chemistry (product of the skills) for this pair of players and add it to a running total (ans). We then move our pointers inward (increment i and decrement j) and continue this process.
We repeat these steps until our pointers meet in the middle. If we are able to pair up all the players in such a way that each team's total skill matches the target, then at the end, we will have successfully found the sum of the chemistry of all teams and return it. If at any point the condition fails, we know that forming the teams as per the condition is not possible, hence the result will be -1.
Solution Approach
The solution uses a straightforward approach, leveraging sorting and the two-pointer technique to efficiently pair players into teams with equal total skills.
Here's a breakdown of the algorithm:
1.Sorting: The array skill is sorted in ascending order. This step ensures that the smallest and largest elements are at the beginning and end of the array, respectively, thus allowing us to easily find pairs that may equal our target total skill.
2.Setting a target total skill (t): Once sorted, we take the sum of the first (skill[0]) and last (skill[-1]) elements to set our target total skill. This forms our baseline which all other pairs must match to ensure all teams have the same total skill.
3.Two-Pointer Technique: We use a classic two-pointer approach where one pointer (i) starts from the beginning of the array, and the other pointer (j) starts from the end. We then iterate through the array, with the following steps in our loop:
- Check if the sum of the current pair of players (skill[i] + skill[j]) equals the target total skill (t). If not, we immediately return -1, indicating the task is impossible.
- If the pair matches the target, we calculate the chemistry (product) for this team (skill[i] * skill[j]) and add it to our running total (ans).
- Move the pointers closer to each other (i increments by 1 and j decrements by 1) to form the next team with the subsequent smallest and largest elements.
4.Loop until pointers meet: The loop continues until i is no longer less than j. This means we have checked and formed teams with all players in the sorted array.
5.Return the result: If we're able to go through the entire array without finding a mismatch in total skills, we then return the accumulated sum of chemistry (ans).
Through this method, we avoid the need for complex data structures and leverage a simple yet effective algorithmic pattern to find the solution. The key to our approach is the greedy strategy of pairing the smallest and largest remaining players to match the target total skill after sorting, ensuring that if there is a possible solution, we will find it.
Example Walkthrough
Let's consider an example to illustrate the solution approach. We have an array skill = [4, 7, 6, 5].
1.Sorting: The first step is to sort this array in ascending order. So, after sorting, the array becomes skill = [4, 5, 6, 7].
2.Setting a target total skill (t): The target total skill is determined by the sum of the first and last elements in the sorted array. Here, t = skill[0] + skill[-1] which is t = 4 + 7 = 11.
3.Two-Pointer Technique: We set two pointers, i at the beginning (i = 0) and j at the end (j = 3). Now, we iterate through the array using these pointers with the following steps:
- The sum of the current pair is skill[i] + skill[j], which is 4 + 7 = 11. This equals our target t. Since the condition is met, we calculate the chemistry for this team, which is 4 * 7 = 28.
- We add this chemistry to our running total (ans), so ans = 28.
- Move the pointers: we increment i to 1 and decrement j to 2, and then check the next pair.
4.Next Iteration: Now i = 1 and j = 2. The sum of this new pair of players is skill[1] + skill[2], which is 5 + 6 = 11. Again, this matches our target t.
- The chemistry for this team is 5 * 6 = 30.
- We add it to our running total, so now ans = 28 + 30 = 58.
- Since we have no more elements left to pair, we have reached the end of our iteration.
5.Return the result: As we have successfully formed teams such that each team's total skill equals the target total skill, and there were no mismatches, we return the sum of the chemistry of all teams, which is ans = 58.
Therefore, for the given example skill = [4, 7, 6, 5], the function would return 58.
Java Solution
class Solution {

    public long dividePlayers(int[] skillLevels) {
        // Sort the skill levels array to organize players by their skill
        Arrays.sort(skillLevels);
      
        // Get the number of players
        int numOfPlayers = skillLevels.length;
      
        // Calculate the target sum based on the lowest and highest skill levels
        int targetSum = skillLevels[0] + skillLevels[numOfPlayers - 1];
      
        // Initialize the answer variable to store the sum of products
        long answer = 0;
      
        // Use two pointers to iterate from the beginning and end towards the center
        for (int left = 0, right = numOfPlayers - 1; left < right; ++left, --right) {
            // Check if the current pair of players does not meet the target sum
            if (skillLevels[left] + skillLevels[right] != targetSum) {
                return -1; // Return -1 if condition is not met, indicating invalid pairing
            }
          
            // Calculate the product of the skill levels of the two players and add it to the answer
            answer += (long) skillLevels[left] * skillLevels[right];
        }
      
        // Return the final answer which is the sum of products of the pairs
        return answer;
    }
}
Time and Space Complexity
The time complexity of the dividePlayers function is primarily determined by the sort operation. In Python, the sort method typically uses Timsort, an algorithm with a time complexity of O(n log n), where n is the number of elements in the input list skill. Once the list is sorted, the function enters a while-loop, which iterates approximately n/2 times (since each iteration pairs up one element from the start of the list with one from the end). The operations inside the loop are constant time operations, so they do not add more than O(n) to the time complexity. As a result, the overall time complexity of the function is dominated by the sorting step: O(n log n).
For space complexity, the code does not create any additional data structures that grow with the size of the input list; it only uses a fixed number of variables. Therefore, the space complexity is O(1), implying constant space usage excluding the input list.
