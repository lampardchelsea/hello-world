/**
 Refer to
 https://leetcode.com/problems/matchsticks-to-square/
 Remember the story of Little Match Girl? By now, you know exactly what matchsticks the little match girl has, 
 please find out a way you can make one square by using up all those matchsticks. You should not break any stick, 
 but you can link them up, and each matchstick must be used exactly one time.

Your input will be several matchsticks the girl has, represented with their stick length. Your output will either 
be true or false, to represent whether you could make one square using all the matchsticks the little match girl has.

Example 1:
Input: [1,1,2,2,2]
Output: true

Explanation: You can form a square with length 2, one side of the square came two sticks with length 1.
Example 2:
Input: [3,3,3,3,4]
Output: false

Explanation: You cannot find a way to form a square with all the matchsticks.
Note:
The length sum of the given matchsticks is in the range of 0 to 10^9.
The length of the given matchstick array will not exceed 15.
*/

// This question is a typical problem of 698. Partition to K Equal Sum Subsets
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DFS_BackTracking/PartitionToKEqualSumSubsets.java

// Solution 1: DFS + backtracking
// Refer to
// https://leetcode.com/problems/matchsticks-to-square/discuss/95729/Java-DFS-Solution-with-Explanation
/**
 According to https://en.wikipedia.org/wiki/Partition_problem, the partition problem (or number partitioning) 
 is the task of deciding whether a given multiset S of positive integers can be partitioned into two subsets 
 S1 and S2 such that the sum of the numbers in S1 equals the sum of the numbers in S2. The partition problem 
 is NP-complete.

When I trying to think how to apply dynamic programming solution of above problem to this one (difference is 
divid S into 4 subsets), I took another look at the constraints of the problem:
The length sum of the given matchsticks is in the range of 0 to 10^9.
The length of the given matchstick array will not exceed 15.

Sounds like the input will not be very large... Then why not just do DFS? In fact, DFS solution passed judges.
Anyone solved this problem by using DP? Please let me know :)
*/
// Runtime: 1095 ms, faster than 13.53% of Java online submissions for Matchsticks to Square.
// Memory Usage: 36.5 MB, less than 10.47% of Java online submissions for Matchsticks to Square.
class Solution {
    public boolean makesquare(int[] nums) {
        if(nums == null || nums.length == 0) {
            return false;
        }
        int sum = 0;
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if(sum % 4 != 0) {
            return false;
        }
        return helper(0, nums, sum / 4, new int[4]);
    }
    
    private boolean helper(int index, int[] nums, int target, int[] sums) {
        if(index == nums.length) {
            if(sums[0] == target && sums[1] == target && sums[2] == target) {
                return true;
            }
            return false;
        }
        for(int i = 0; i < 4; i++) {
            if(sums[i] + nums[index] <= target) {
                sums[i] += nums[index];
                if(helper(index + 1, nums, target, sums)) {
                    return true;
                }
                sums[i] -= nums[index];
            }
        }
        return false;
    }
}

// Solution 2: Optimization by sorting the array first
// Refer to
// https://leetcode.com/problems/matchsticks-to-square/discuss/95729/Java-DFS-Solution-with-Explanation
/**
 Sorting the input array DESC will make the DFS process run much faster. Reason behind this is we always try 
 to put the next matchstick in the first subset. If there is no solution, trying a longer matchstick first 
 will get to negative conclusion earlier. Following is the updated code. Runtime is improved from more than 
 1000ms to around 40ms. A big improvement.
 We can either reverse array as one more function or we can traverse indexes from end to start index
 Here we choose the 2nd way since it will take even less time and boost the time from 1095ms to 21ms
*/
// 中文解释
// https://www.cnblogs.com/grandyang/p/7733098.html
/**
 下面这种方法也挺巧妙的，思路是建立长度为k的数组v，只有当v里面所有的数字都是target的时候，才能返回true。我们还需要给数组排个序，
 由于题目中限制了全是正数，所以数字累加只会增大不会减小，一旦累加超过了target，这个子集合是无法再变小的，所以就不能加入这个数。
 实际上相当于贪婪算法，由于题目中数组数字为正的限制，有解的话就可以用贪婪算法得到。我们用一个变量idx表示当前遍历的数字，排序后，
 我们从末尾大的数字开始累加，我们遍历数组v，当前位置加上nums[idx]，如果超过了target，我们掉过继续到下一个位置，否则就调用递归，
 此时的idx为idx-1，表示之前那个数字已经成功加入数组v了，我们尝试着加下一个数字。如果递归返回false了，我们就将nums[idx]从数组v
 中对应的位置减去，还原状态，然后继续下一个位置。如果某个递归中idx等于-1了，表明所有的数字已经遍历完了，此时我们检查数组v中k个
 数字是否都为target，是的话返回true，否则返回false
*/
// Runtime: 21 ms, faster than 68.12% of Java online submissions for Matchsticks to Square.
// Memory Usage: 36.4 MB, less than 10.47% of Java online submissions for Matchsticks to Square.
class Solution {
    public boolean makesquare(int[] nums) {
        if(nums == null || nums.length == 0) {
            return false;
        }
        int sum = 0;
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if(sum % 4 != 0) {
            return false;
        }
        // We are sorting the array first
        Arrays.sort(nums);
        // Traverse index from end to start
        return helper(nums.length - 1, nums, sum / 4, new int[4]);
    }
    
    private boolean helper(int index, int[] nums, int target, int[] sums) {
        // Must index == -1 instead of index == 0, test case: [4,3,2,3,5,2,1] and k = 4
        // because for sums[i] += nums[index] also need to be checked when index = 0,
        // then pass index = 0 to next level as helper(index - 1, ....), the terminate 
        // checking condition should be if(index == -1) which will include previous
        // level index = 0
        if(index == -1) {
            if(sums[0] == target && sums[1] == target && sums[2] == target) {
                return true;
            }
            return false;
        }
        for(int i = 0; i < 4; i++) {
            if(sums[i] + nums[index] <= target) {
                sums[i] += nums[index];
                if(helper(index - 1, nums, target, sums)) {
                    return true;
                }
                sums[i] -= nums[index];
            }
        }
        return false;
    }
}


















































https://leetcode.com/problems/matchsticks-to-square/description/
You are given an integer array matchsticks where matchsticks[i] is the length of the ith matchstick. You want to use all the matchsticks to make one square. You should not break any stick, but you can link them up, and each matchstick must be used exactly one time.
Return true if you can make this square and false otherwise.
 
Example 1:

Input: matchsticks = [1,1,2,2,2]
Output: true
Explanation: You can form a square with length 2, one side of the square came two sticks with length 1.

Example 2:
Input: matchsticks = [3,3,3,3,4]
Output: false
Explanation: You cannot find a way to form a square with all the matchsticks.
 
Constraints:
- 1 <= matchsticks.length <= 15
- 1 <= matchsticks[i] <= 10^8
--------------------------------------------------------------------------------
Attempt 1: 2025-08-25
Solution 1: Backtracking + Sorting (60 min)
class Solution {
    public boolean makesquare(int[] matchsticks) {
        if(matchsticks.length < 4) {
            return false;
        }
        int total = Arrays.stream(matchsticks).sum();
        if(total % 4 != 0) {
            return false;
        }
        int target = total / 4;
        Arrays.sort(matchsticks);
        // Reverse to descending order
        for(int i = 0; i < matchsticks.length / 2; i++) {
            int tmp = matchsticks[i];
            matchsticks[i] = matchsticks[matchsticks.length - 1 - i];
            matchsticks[matchsticks.length - 1 - i] = tmp;
        }
        if(matchsticks[0] > target) {
            return false;
        }
        return helper(matchsticks, new int[4], target, 0);
    }

    private boolean helper(int[] matchsticks, int[] sides, int target, int index) {
        if(index == matchsticks.length) {
            // Why no 'sides[0] == target && sides[1] == target && sides[2] == target && sides[3] == target' ?
            // Implicit Guarantee by Constraints:
            // - We've already verified that the total sum of matchsticks equals 4 * target
            // - We never allow any side to exceed the target (due to the pruning condition)
            // - Therefore, if we successfully place all matchsticks, each side must exactly equal the target
            return true;
        }
        for(int i = 0; i < 4; i++) {
            // Skip if adding this matchstick would exceed target
            if(sides[i] + matchsticks[index] > target) {
                continue;
            }
            // Skip duplicate combinations
            if(i > 0 && sides[i] == sides[i - 1]) {
                continue;
            }
            sides[i] += matchsticks[index];
            if(helper(matchsticks, sides, target, index + 1)) {
                return true;
            }
            sides[i] -= matchsticks[index];
        }
        return false;
    }
}

Time Complexity: O(4^n) in worst case, but heavily pruned in practice
Space Complexity: O(n) for recursion stack

Refer to Deepseek
Approach
This problem can be solved using backtracking with optimization. The key idea is to determine if we can partition the matchsticks into 4 subsets that each sum to the target side length (total sum / 4). We use:
1.Early pruning: Sort matchsticks in descending order to try larger pieces first
2.Backtracking: Try placing each matchstick in one of the 4 sides
3.Memoization: Skip duplicate states and impossible cases
Solution Code
import java.util.Arrays;

class Solution {
    public boolean makesquare(int[] matchsticks) {
        if (matchsticks == null || matchsticks.length < 4) {
            return false;
        }
        
        int total = 0;
        for (int stick : matchsticks) {
            total += stick;
        }
        
        // Check if total length can be divided into 4 equal sides
        if (total % 4 != 0) {
            return false;
        }
        
        int side = total / 4;
        // Sort in descending order to try larger matchsticks first (better pruning)
        Arrays.sort(matchsticks);
        reverse(matchsticks);
        
        // If any matchstick is longer than side length, impossible
        if (matchsticks[0] > side) {
            return false;
        }
        
        int[] sides = new int[4];
        return backtrack(matchsticks, sides, 0, side);
    }
    
    private boolean backtrack(int[] matchsticks, int[] sides, int index, int target) {
        if (index == matchsticks.length) {
            // All matchsticks used, check if all sides are equal to target
            return sides[0] == target && sides[1] == target && sides[2] == target && sides[3] == target;
        }
        
        for (int i = 0; i < 4; i++) {
            // Skip if adding this matchstick would exceed target
            if (sides[i] + matchsticks[index] > target) {
                continue;
            }
            
            // Skip duplicate states (if previous sides have same length)
            int j = i;
            while (j-- > 0) {
                if (sides[i] == sides[j]) {
                    break;
                }
            }
            if (j >= 0) {
                continue;
            }
            
            sides[i] += matchsticks[index];
            if (backtrack(matchsticks, sides, index + 1, target)) {
                return true;
            }
            sides[i] -= matchsticks[index];
        }
        
        return false;
    }
    
    private void reverse(int[] arr) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }
}
Optimized Solution with Better Pruning
import java.util.Arrays;

class Solution {
    public boolean makesquare(int[] matchsticks) {
        if (matchsticks == null || matchsticks.length < 4) return false;
        
        int total = Arrays.stream(matchsticks).sum();
        if (total % 4 != 0) return false;
        
        int side = total / 4;
        Arrays.sort(matchsticks);
        // Reverse to descending order
        for (int i = 0; i < matchsticks.length / 2; i++) {
            int temp = matchsticks[i];
            matchsticks[i] = matchsticks[matchsticks.length - 1 - i];
            matchsticks[matchsticks.length - 1 - i] = temp;
        }
        
        if (matchsticks[0] > side) return false;
        
        return dfs(matchsticks, new int[4], 0, side);
    }
    
    private boolean dfs(int[] matchsticks, int[] sides, int index, int target) {
        if (index == matchsticks.length) {
            return true;
        }
        
        for (int i = 0; i < 4; i++) {
            if (sides[i] + matchsticks[index] > target) continue;
            
            // Skip duplicate combinations
            if (i > 0 && sides[i] == sides[i - 1]) continue;
            
            sides[i] += matchsticks[index];
            if (dfs(matchsticks, sides, index + 1, target)) return true;
            sides[i] -= matchsticks[index];
            
            // Early termination: if we can't place the first matchstick anywhere
            if (sides[i] == 0) break;
        }
        
        return false;
    }
}
Explanation
1.Initial Checks:
- Check if there are at least 4 matchsticks
- Check if total length is divisible by 4
- Check if any matchstick is longer than the target side length
2.Sorting:
- Sort matchsticks in descending order to try larger pieces first
- This helps prune impossible paths early in the backtracking
3.Backtracking:
- Try placing each matchstick in one of the 4 sides
- Skip sides where adding the matchstick would exceed the target
- Skip duplicate states (sides with same current length)
- If a matchstick can't be placed in any side, backtrack
4.Optimizations:
- Early termination when first matchstick can't be placed
- Skipping duplicate side lengths to avoid redundant calculations
- Pruning when current side + matchstick > target
Time Complexity: O(4^n) in worst case, but heavily pruned in practice
Space Complexity: O(n) for recursion stack
This approach efficiently explores all possible partitions while pruning impossible paths early, making it feasible for the problem constraints.

Why in the 'Optimized Solution with Better Pruning' solution dfs method termination condition we don't need 'sides[0] == target && sides[1] == target && sides[2] == target && sides[3] == target' ?
Explanation of DFS Termination Condition in Optimized Solution
In the optimized solution, the DFS method doesn't explicitly check sides[0] == target && sides[1] == target && sides[2] == target && sides[3] == target because:
Key Reasons:
1.Implicit Guarantee by Constraints:
- We've already verified that the total sum of matchsticks equals 4 * target
- We never allow any side to exceed the target (due to the pruning condition)
- Therefore, if we successfully place all matchsticks, each side must exactly equal the target
2.Mathematical Certainty:
- Let S = total sum of all matchsticks = 4 * target
- Let s₁, s₂, s₃, s₄ be the current sums of the 4 sides
- We know s₁ + s₂ + s₃ + s₄ = S = 4 * target
- We also know s₁ ≤ target, s₂ ≤ target, s₃ ≤ target, s₄ ≤ target (due to pruning)
- The only possible solution is s₁ = s₂ = s₃ = s₄ = target
3.Algorithmic Efficiency:
- Removing the explicit check makes the code slightly faster
- The condition index == matchsticks.length is sufficient to indicate success
Why This Works:
private boolean dfs(int[] matchsticks, int[] sides, int index, int target) {
    if (index == matchsticks.length) {
        return true; // All matchsticks placed successfully
    }
    
    for (int i = 0; i < 4; i++) {
        if (sides[i] + matchsticks[index] > target) continue; // Pruning: never exceed target
        
        // ... rest of the backtracking logic
    }
    
    return false;
}
The algorithm ensures that:
1.We only proceed if adding a matchstick doesn't make any side exceed the target
2.We use all matchsticks (total sum = 4 * target)
3.Therefore, when we use all matchsticks, each side must equal the target
This optimization makes the code cleaner and slightly more efficient while maintaining correctness.


Refer to
L2397.Maximum Rows Covered by Columns (Ref.L473)
