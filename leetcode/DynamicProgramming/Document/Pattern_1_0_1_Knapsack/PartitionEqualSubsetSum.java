
https://leetcode.com/problems/partition-equal-subset-sum/description/
Given a non-empty array nums containing only positive integers, find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.

Example 1:
Input: nums = [1,5,11,5]
Output: true
Explanation: The array can be partitioned as [1, 5, 5] and [11].

Example 2:
Input: nums = [1,2,3,5]
Output: false
Explanation: The array cannot be partitioned into equal sum subsets.
 
Constraints:
- 1 <= nums.length <= 200
- 1 <= nums[i] <= 100
--------------------------------------------------------------------------------
Attempt 1: 2022-12-19
Solution 1:  Native DFS (TLE, 10 min)
Basic Solution
This problem follows the 0/1 Knapsack pattern. A basic brute-force solution could be to try all combinations of partitioning the given numbers into two sets to see if any pair of sets has an equal sum. Assume if S represents the total sum of all the given numbers, then the two equal subsets must have a sum equal to S/2. This essentially transforms our problem to: "Find a subset of the given numbers that has a total sum of S/2".
class Solution { 
    public boolean canPartition(int[] nums) { 
        int sum = 0; 
        for(int num : nums) { 
            sum += num; 
        } 
        if(sum % 2 == 1) { 
            return false; 
        } 
        int target = sum / 2; 
        return helper(nums, target, 0); 
    }

    private boolean helper(int[] nums, int target, int index) { 
        if(index >= nums.length) { 
            return false; 
        } 
        if(target == 0) { 
            return true; 
        } 
        // Pick current element 
        if(target >= nums[index]) { 
            if(helper(nums, target - nums[index], index + 1)) { 
                return true; 
            } 
        } 
        // Not pick current element 
        if(helper(nums, target, index + 1)) { 
            return true; 
        } 
        return false; 
    } 
}

Solution 2:  DFS with Memoization (Top down DP) (10 min)
Since we need to store the results for every subset and for every possible sum, therefore we will be using a two-dimensional array to store the results of the solved sub-problems. The first dimension of the array will represent different subsets and the second dimension will represent different ‘target’ that we can calculate from each subset. These two dimensions of the array can also be inferred from the two changing values (target and index) in our recursive function helper(). The above algorithm has time and space complexity of O(N*S), where ‘N’ represents total numbers and ‘S’ is the total sum of all the numbers.
class Solution { 
    public boolean canPartition(int[] nums) { 
        int sum = 0; 
        for(int num : nums) { 
            sum += num; 
        } 
        if(sum % 2 == 1) { 
            return false; 
        } 
        int target = sum / 2; 
        // No need store for index >= nums.length case, it means 'memo' will 
        // only store index < nums.length case, 1-dimension only need nums.length 
        // instead of 1 + nums.length and promote both TC and SC 
        //Boolean[][] memo = new Boolean[1 + nums.length][1 + target]; 
        Boolean[][] memo = new Boolean[nums.length][1 + target]; 
        return helper(nums, target, 0, memo); 
    } 

    private boolean helper(int[] nums, int target, int index, Boolean[][] memo) { 
        if(index >= nums.length) { 
            // No need store for index >= nums.length case, it means 'memo' will 
            // only store index < nums.length case, 1-dimension only need nums.length 
            // instead of 1 + nums.length and promote both TC and SC 
            //memo[index][target] = false; 
            return false; 
        } 
        if(memo[index][target] != null) { 
            return memo[index][target]; 
        } 
        if(target == 0) { 
            memo[index][target] = true; 
            return true; 
        } 
        // Pick current element 
        if(target >= nums[index]) { 
            if(helper(nums, target - nums[index], index + 1, memo)) { 
                memo[index][target] = true; 
                return true; 
            } 
        } 
        // Not pick current element 
        if(helper(nums, target, index + 1, memo)) { 
            memo[index][target] = true; 
            return true; 
        } 
        memo[index][target] = false; 
        return false; 
    } 
}

Solution 3: 2D bottom up DP (10 min)
Let’s try to populate our dp[][] array from the above solution, working in a bottom-up fashion. Essentially, we want to find if we can make all possible sums with every subset. This means, dp[i][s] will be ‘true’ if we can make sum ‘s’ from the first ‘i’ numbers. So, for each number at index ‘i’ (0 <= i < num.length) and sum ‘s’ (0 <= s <= S/2), we have two options: 
1. Exclude the number. In this case, we will see if we can get ‘s’ from the subset excluding this number: dp[i-1][s] 
2. Include the number if its value is not more than ‘s’. In this case, we will see if we can find a subset to get the remaining sum: dp[i-1][s-num[i]]. If either of the two above scenarios is true, we can find a subset of numbers with a sum equal to ‘s’.
The above algorithm has time and space complexity of O(N*S), where ‘N’ represents total numbers and ‘S’ is the total sum of all the numbers.
The big difference is happen on initialize first row, style 3's first row actually depends on index = 0 element on given array, style 1 & 2's first row is dummy row which has no element pick up from given array

Style 1: Initialize boolean[][] dp = new boolean[1 + nums.length][1 + target]
class Solution { 
    public boolean canPartition(int[] nums) { 
        int sum = 0; 
        for(int num : nums) { 
            sum += num; 
        } 
        if(sum % 2 == 1) { 
            return false; 
        } 
        int target = sum / 2; 
        boolean[][] dp = new boolean[1 + nums.length][1 + target]; 
        // populate the sum=0 columns, as we can always for '0' sum with an empty set 
        for(int i = 0; i <= nums.length; i++) { 
            dp[i][0] = true; 
        } 
        // For len as 0(no number in nums array pick up),  
        // given any target, we can not make it 
        for(int i = 1; i <= target; i++) { 
            dp[0][i] = false; 
        } 
        // process all subsets for all sums 
        for(int i = 1; i <= nums.length; i++) { 
            for(int j = 1; j <= target; j++) { 
                // if we can get the sum 'j' without the number at index 'i - 1' 
                if(dp[i - 1][j]) { 
                    dp[i][j] = dp[i - 1][j]; 
                // else if we can find a subset to get the remaining sum 
                } else if(j >= nums[i - 1]) { 
                    dp[i][j] = dp[i - 1][j - nums[i - 1]]; 
                } 
            } 
        } 
        return dp[nums.length][target]; 
    } 
}

Style 2: Initialize boolean[][] dp = new boolean[1 + nums.length][1 + target]
1. Directly inherit boolean result get the sum 'j' without the number at index 'i - 1' first, no matter true or false
2. if we can find a subset to get the remaining sum, '|=' or relational operator means besides directly inherit, we find another choice
class Solution { 
    public boolean canPartition(int[] nums) { 
        int sum = 0; 
        for(int num : nums) { 
            sum += num; 
        } 
        if(sum % 2 == 1) { 
            return false; 
        } 
        int target = sum / 2; 
        boolean[][] dp = new boolean[1 + nums.length][1 + target]; 
        // populate the sum=0 columns, as we can always for '0' sum with an empty set 
        for(int i = 0; i <= nums.length; i++) { 
            dp[i][0] = true; 
        } 
        // For len as 0(no number in nums array pick up),  
        // given any target, we can not make it 
        for(int i = 1; i <= target; i++) { 
            dp[0][i] = false; 
        } 
        // process all subsets for all sums 
        for(int i = 1; i <= nums.length; i++) { 
            for(int j = 1; j <= target; j++) { 
                // Directly inherit boolean result get the sum 'j' without the  
                // number at index 'i - 1' first, no matter true or false 
                dp[i][j] = dp[i - 1][j]; 
                // if we can find a subset to get the remaining sum, '|=' or 
                // relational operator means besides directly inherit, we find 
                // another choice 
                if(j >= nums[i - 1]) { 
                    dp[i][j] |= dp[i - 1][j - nums[i - 1]]; 
                } 
            } 
        } 
        return dp[nums.length][target]; 
    } 
}

Style 3: Initialize boolean[][] dp = new boolean[nums.length][1 + target]
1. Directly inherit boolean result get the sum 'j' without the number at index 'i ' first, no matter true or false
2. Different than Style 2 since no offset on 'i', since no dummy row added, exclude nums[i] is fine, not nums[i - 1],  if we can find a subset to get the remaining sum, '|=' or relational operator means besides directly inherit, we find another choice
class Solution { 
    public boolean canPartition(int[] nums) { 
        int sum = 0; 
        for(int num : nums) { 
            sum += num; 
        } 
        if(sum % 2 == 1) { 
            return false; 
        } 
        int target = sum / 2; 
        boolean[][] dp = new boolean[nums.length][1 + target]; 
        // populate the sum=0 columns, as we can always for '0' sum with an empty set 
        for(int i = 0; i < nums.length; i++) { 
            dp[i][0] = true; 
        } 
        // Different than Style 2 since the 1st row not dummy 
        // with only one number, we can form a subset only when the required sum  
        // is equal to its value 
        for(int i = 1; i <= target; i++) { 
            dp[0][i] = (nums[0] == i ? true : false); 
        } 
        // process all subsets for all sums 
        for(int i = 1; i < nums.length; i++) { 
            for(int j = 1; j <= target; j++) { 
                // Direclty inherit boolean result get the sum 'j' without the  
                // number at index 'i' first, no matter true or false 
                dp[i][j] = dp[i - 1][j]; 
                // Different than Style 2 since no offset on 'i', since no 
                // dummy row added, exclude nums[i] is fine, not nums[i - 1] 
                // if we can find a subset to get the remaining sum, '|=' or 
                // relational operator means besides directly inherit, we find 
                // another choice 
                if(j >= nums[i]) { 
                    dp[i][j] |= dp[i - 1][j - nums[i]]; 
                } 
            } 
        } 
        return dp[nums.length - 1][target]; 
    } 
}

Solution 4: 1D bottom up DP (60 min)
此时，外层是对数组的循环，内层通过一个 i=num～sum 的循环，求出所有加上当前位置的 num 能到得到 i 的解。外层对数组的循环，每到一个数字 num ，就是指对于子数组[0,...,num]来说，dp[i] 存放着数组[0,...,num]中和为 i 的子数组的个数，那么外层对数组的循环完了之后，dp[i] 存放的就是数组 nums 的和为 i 的子数组的个数，所以，此时 dp[sum] 就是本题的解。
其中，起始位置的dp[0] = true，是整个计算的开端，然后以此类推。
class Solution { 
    public boolean canPartition(int[] nums) { 
        int sum = 0; 
        for(int num : nums) { 
            sum += num; 
        } 
        if(sum % 2 == 1) { 
            return false; 
        } 
        int target = sum / 2; 
        // dp[j]存放着数组[0, ... , num]中和为j的子数组是否存在 
        boolean[] dp = new boolean[1 + target]; 
        dp[0] = true; 
        for(int i = 0; i < nums.length; i++) { 
            // Must loop backwards 
            for(int j = target; j >= 0; j--) { 
                if(j >= nums[i]) { 
                    //dp[j] = dp[j] || dp[j - nums[i]]; 
                    dp[j] |= dp[j - nums[i]]; 
                }                
            } 
        } 
        return dp[target]; 
    } 
}

Explanation of why we can degrade 2D to 1D array and why we need loop backwards
Refer to 
https://leetcode.com/problems/partition-equal-subset-sum/discuss/90592/01-knapsack-detailed-explanation 
https://leetcode.com/problems/partition-equal-subset-sum/solutions/90592/01-knapsack-detailed-explanation/comments/241664
Yes, the magic is observation from the induction rule/recurrence relation! 
For this problem, the induction rule: 
If not picking nums[i - 1], then dp[i][j] = dp[i-1][j] 
if picking nums[i - 1], then dp[i][j] = dp[i - 1][j - nums[i - 1]] 
You can see that if you point them out in the matrix, it will be like: 
              j 
    . . . . . . . . . . . .  
    . . . . . . . . . . . .   
    . . ? . . ? . . . . . .  ?(left): dp[i - 1][j - nums[i], ?(right): dp[i - 1][j] 
i    . . . . . # . . . . . .  # dp[i][j] 
    . . . . . . . . . . . .  
    . . . . . . . . . . . .  
    . . . . . . . . . . . .  
    . . . . . . . . . . . .  
    . . . . . . . . . . . .  
Optimize to O(2*n): you can see that dp[i][j] only depends on previous row, so you can  
optimize the space by only using 2 rows instead of the matrix. Let's say array1 and array2.  
Every time you finish updating array2, array1 have no value, you can copy array2 to array1  
as the previous row of the next new row. 
Optimize to O(n): you can also see that, the column indices of dp[i - 1][j - nums[i]] and  
dp[i - 1][j] are <= j. The conclusion you can get is: the elements of previous row whose  
column index is > j(i.e. dp[i - 1][j + 1 : n - 1]) will not affect the update of dp[i][j]  
since we will not touch them: 
              j 
    . . . . . . . . . . . .  
    . . . . . . . . . . . .   
    . . ? . . ? x x x x x x  you will not touch x for dp[i][j] 
i    . . . . . # . . . . . .  # dp[i][j] 
    . . . . . . . . . . . .  
    . . . . . . . . . . . .  
    . . . . . . . . . . . .  
    . . . . . . . . . . . .  
    . . . . . . . . . . . .  
Thus if you merge array1 and array2 to a single array array, if you update array backwards,  
all dependencies are not touched! 
    (n represents new value, i.e. updated) 
    . . ? . . ? n n n n n n n 
              #   
However if you update forwards, dp[j - nums[i - 1]] is updated already, you cannot use it: 
    (n represents new value, i.e. updated) 
    n n n n n ? . . . . . .  where another ? goes? Oops, it is overriden, we lost it :( 
              #   
Conclusion: 
So the rule is that observe the positions of current element and its dependencies in the matrix.  
Mostly if current elements depends on the elements in previous row(most frequent case)/columns,  
you can optimize the space.

Refer to
https://leetcode.com/problems/target-sum/solutions/245073/Java-solution-in-Chinese/
S1:直接递归
题目中指出，将数组中的每个数前面加上一个符号（+ 或 -），使得它们构成一个运算式，并且其计算结果等于给定的 S ，求符号的添加种类。
那么也就是说，对于每一个数字，我们可以选择加上它或者减去它，于是，可以直接使用递归，进行2^n次运算，求出所有的符号添加方式，然后再求其中结果为 S 的即可：
public static int findTargetSumWays(int[] nums, int S) {
    return ways(nums, S, 0, 0);
}

private static int ways(int[] nums, int S, int pos, int cur) {
    if (pos == nums.length) {
        return cur == S ? 1 : 0;
    } else {
        return ways(nums, S, pos+1, cur+nums[pos])
            + ways(nums, S, pos+1, cur-nums[pos]);
    }
}

S2:缓存递归
上述递归很有可能会增加很多不必要的计算，如对于一个数组[1,1,1,1,1,1]，如果前两个数字的符号分别是[-,+]和[+,-]，那么计算到第三个数字的时候，这两种符号添加方式指向了同一种情况，如果不加处理，肯定要造成后面的[1,1,1,1]的重复计算，此时可以考虑给递归函数加一个缓存。

每个递归函数有两个变量：
1.当前位置
2.当前的运算结果

所以初步考虑缓存应该是一个二维数组，此时就要判断这两个变量各自的取值范围：
1.对于当前的位置，肯定是要在 nums 里面，也就是说，它的范围是 0～n-1
2.由于数组中所有的数字都是正数，那么必然所有符号取 + 结果最大，所有符号取 - 结果最小，题目里面也指出，所有的数字和是不大于 1000 的，所以，范围取上下 1000 即可，也就是 2001

那么，添加了缓存之后就是：
public static int findTargetSumWays(int[] nums, int S) {
    int[][] saved = new int[nums.length][2001];
    for (int[] row : saved) {
        Arrays.fill(row, -1);
    }
    return ways2(nums, S, 0, 0, saved);
}

private static int ways2(int[] nums, int S, int pos, int cur, int[][] saved) {
    if (pos == nums.length) {
        return cur == S ? 1 : 0;
    } else {
        if (saved[pos][cur+1000] >= 0) return saved[pos][cur+1000];
        int ways = ways2(nums, S, pos+1, cur+nums[pos], saved)
                + ways2(nums, S, pos+1, cur-nums[pos], saved);
        saved[pos][cur+1000] = ways;
        return ways;
    }
}
1000 是数组中数字和的上限，如果想要缩减一下内存使用量，也可以直接计算出数字和。


S3: 动态规划
动态规划也需要一个数组保存记录值，与递归中使用的缓存数组类似。对于一个数组[1,...,n]来说，如果当前待判断位置是 i ，也就是说已经计算出了前面[1,...,i-1]这部分子数组的结果，每个数字有两种符号添加，也就是说总的应该有2^{i-1}种结果（可能会有重合的），那么在这2^{i-1}种结果加上（或者减去）nums[i] 之后，就会得到2^i种新的结果（当然，有可能在前面的2^{i-1}种结果中，存在 x y ，使得 x-nums[i] == y+nums[i] == cur），此时 cur 对应的解应该是 x y 两个数对应的解的和。那么当整个数组计算完成之后，就可以求出数字 S 对应的解。
public static int findTargetSumWays(int[] nums, int S) {
    int sum = 0;
    for (int num : nums) sum += num;
    if (sum < S || ((S + sum) & 1) == 1) return 0;
    int[] dp = new int[(sum<<1) + 1];
    dp[nums[0] + sum] = 1;
    dp[-nums[0] + sum] += 1;
    for (int i = 1; i < nums.length; i++) {
        int[] next = new int[(sum<<1) + 1];
        for (int j = -sum; j <= sum; j++) {
            if (dp[j + sum] > 0) {
                next[j + sum + nums[i]] += dp[j + sum];
                next[j + sum - nums[i]] += dp[j + sum];
            }
        }
        dp = next;
    }
    return dp[S + sum];
}
如上，第一个循环是遍历数组，第二个循环则是遍历以求出的2^{i-1}种结果，并将其分别加上（减去）nums[i] 以求出下一个位置的2^i种结果，当然，上述结果肯定是存在重合的，因为所有的运算结果都是在 -sum～sum 的范围内，所以当我们需要遍历已求出的2^{i-1}种结果时，由于会有重合，我们并不知道到底有多少种结果，这时的处理办法就是遍历整个可能的结果（从 -sum 到 sum），当这个结果对应的解不为 0 时，就意味着这是一个2^{i-1}个结果中的一个。
所以，本题中首先给执行了dp[nums[0] + sum] = 1;和dp[-nums[0] + sum] += 1;这两个代码，就是为了先求出2^1的结果，然后之后求第二个位置的2^2的结果的时候，才能利用已求出的结果。
另外，计算下一个位置的结果的时候的代码next[j + sum + nums[i]] += dp[j + sum];中使用的是+=符号，就是将所有导向统一结果的解相加
还有，S 与 sum 是否有什么关系？为什么要判断((S + sum) & 1) == 1？
本题中每个数字前都有一个符号，+ 或 - ，如果将整个数组按照数字之前的符号分成两部分，一部分的符号全是 + ，另一部分的符号全是 - ，然后让两部分的和分别是 x y ，那么有关系式：
x + y = sum
x - y = S
于是有x + x = S + sum = 2 * x，于是得出结论：S + sum 必然是偶数，所以才有了本题开始的判断。

S4:划分数组 & 动态规划
上面的这个关系式S + sum = 2 * x也可以加以利用，得出(S + sum) / 2 = x，也就是说本题可以变为，使 nums 数组的子数组的和等于 S+sum 的一半，求出符合条件的子数组的个数。
于是有：
public static int findTargetSumWays(int[] nums, int S) {
    int sum = 0;
    for (int num : nums) sum += num;
    if (sum < S || ((sum + S) & 1) == 1) return 0;
    sum = (sum + S) >> 1;
    int[] dp = new int[sum+1];
    dp[0] = 1;
    for (int num : nums) {
        for (int i = sum; i >= num; i--) {
            dp[i] += dp[i-num];
        }
    }
    return dp[sum];
}
此时，外层是对数组的循环，内层通过一个 i=num～sum 的循环，求出所有加上当前位置的 num 能到得到 i 的解。外层对数组的循环，每到一个数字 num ，就是指对于子数组[0,...,num]来说，dp[i] 存放着数组[0,...,num]中和为 i 的子数组的个数，那么外层对数组的循环完了之后，dp[i] 存放的就是数组 nums 的和为 i 的子数组的个数，所以，此时 dp[sum] 就是本题的解。
其中，起始位置的dp[0] = 1，是整个计算的开端，由 0+num=num ，得到dp[num] = 1，然后以此类推。
--------------------------------------------------------------------------------
Re-work
The idea is similar to start from each element with all elements afterwards to build one group, we can build array element count's groups, and inside each group to find a potential combination (a path) to sum up elements to approach a target, which is quite similar to find a path to approach a target but with different beginning.
The same thought refer to 
L64.Minimum Path Sum (Ref.L62,L63)
L112.P9.1.Path Sum (Ref.L257,L113)
L931.Minimum Falling Path Sum (Ref.L1937)
L1937.Maximum Number of Points with Cost (Ref.L64,L1981)
L1981.Minimize the Difference Between Target and Chosen Elements (Ref.L64,L112)

Attempt 1: 2026-04-11
Solution 1:  Native DFS (TLE 39/147, 10 min)
Style 1: For loop
Note: The solution fixed after Memoization solution passed since the 'target < nums[index]' issue identified only when test against Memoization way
class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for(int num : nums) {
            sum += num;
        }
        if(sum % 2 != 0) {
            return false;
        }
        int target = sum / 2;
        // For loop has the nature to exclude a element
        // since we only consider elements at or after
        // current index, same effect as take or not take
        for(int i = 0; i < nums.length; i++) {
            if(helper(nums, i, target)) {
                return true;
            }
        }
        return false;
    }

    private boolean helper(int[] nums, int index, int target) {
        if(index >= nums.length) {
            return false;
        }
        // Note: no need if(index == nums.length - 1 && target == 0), because
        // that we are not required to recursive till end of the array to find 
        // a solution, even in middle of the recursion if target minus to 0,
        // we consider that as combination as a solution.
        if(target == 0) {
            return true;
        }
        // 关键修复：当前元素正好等于剩余目标，直接成功
        // 如果没有这一行的逻辑bug：
        // 原始代码的递归逻辑是：每次进入 helper(index, target)，如果 target == 0 
        // 返回 true。然后它遍历 i 从 index+1 到末尾，尝试 helper(i, target - nums[index])。
        // 注意，它并没有直接检查 target == nums[index]，但可以通过以下方式间接实现：
        // 假设 target == nums[index]，那么 target - nums[index] == 0。在循环中，
        // 如果存在某个 i 使得 helper(i, 0) 返回 true，那么就会成功。而 helper(i, 0) 
        // 在函数开头有 if(target == 0) return true;，所以只要 i 在范围内（即 index+1 
        // 到 n-1 存在），它就会返回 true。但如果 index 是最后一个元素（index == n-1），
        // 则循环不会执行（因为 i 从 n 开始，不满足 i < n），此时函数会直接返回 false。
        // 所以问题仅出现在当 index 是最后一个元素且 target == nums[index] 时，无法成功。
        // 但测试用例中是否存在这样的场景？可能 LeetCode 的测试数据中，当需要选最后一个元素时，
        // 总会有一个更早的元素也可以选？或者由于我们是从外层循环遍历每个起始索引，即使
        // 最后一个元素单独等于 target，也会被外层循环以 i = n-1 调用 helper(nums, n-1, target)，
        // 而在这个调用中，由于 index = n-1，循环为空，且 target == nums[n-1]，但缺少直接判断，
        // 所以会返回 false。为何没有返回 false 呢？
        // 因为如果最后一个元素 nums[n-1] 恰好等于 target，那么前 n-1 个元素的和也必然
        // 等于 target（因为总和为 2*target）。因此，前 n-1 个元素的全体本身就是一个
        // 合法的子集，而这个子集可以通过外层循环从索引 0 开始，递归地选取所有前面的
        // 元素得到。所以即便递归函数无法处理“只选最后一个元素”的情况，另一个解（选所
        // 有前面的元素）依然存在，并且会被外层循环的某个起始索引成功找到。因此，你的
        // 原始代码在实际测试中不会失败。添加 if (target == nums[index]) return true; 
        // 是为了逻辑完整性，处理这种边界情况，但并非必要，因为总有其他解存在。不过，
        // 从代码健壮性角度，建议加上这一行，避免依赖数学巧合。而且该条件也保证了我们
        // 在 for 循环进入下一个元素检查之前 (因为 i 的起点是 index + 1 而非当前值 index)
        // 优先完成对当前元素和 target 值是否匹配的检测。
        // 如果用测试数组 nums = {1,2,3,6} 来测试，在没有 if(target == nums[index])
        // 该条件的时候，递归要进入到第四层，然后在 if(target == 0) 的条件下返回 true,
        // 而在有了该条件后，递归进入到第三层，然后在 if(target == nums[index]) 的条件
        // 下返回 true，而后者才是正确的思路，而非依赖前面 1 + 2 + 3 = target 6 的巧合
        // 如果用测试数组 nums = {1,5,11,5} 来测试则更加明显，在没有 if(target == nums[index])
        // 该条件的时候，需要在外面的 for 循环走到 i = 2, 也就是从 11 开始的时候才能在
        // 进入递归后因为满足 if(target == 0) 返回 true, 而在有了 if(target == nums[index])
        // 后，则只需要在外面的 for 循环从 i = 0 的时候，也就是从 1 开始的时候就能通过
        // if(target == nums[index]) 的条件找到 1 + 5 + 5 = target 11 这个组合，逻辑上
        // 产生了巨大的行为差异，并且有效提升了寻找效率。
        if(target == nums[index]) {
           return true;         
        }
        for(int i = index + 1; i < nums.length; i++) {
            // The condition added to fix the target < nums[index] issue
            // during recursion, when it happen we actually skip that number
            if(target >= nums[index]) {
                if(helper(nums, i, target - nums[index])) {
                    return true;
                }
            }
        }
        return false;
    }
}

该解法**没有使用记忆化**，因此复杂度如下：

- **时间复杂度**：**O(2ⁿ)**  
  递归枚举了所有可能的子集（每个元素选或不选），但通过起始索引限制顺序，实际上每个子集只被考虑一次。
  子集总数为 2ⁿ，故最坏情况下为指数级。

- **空间复杂度**：**O(n)**  
  递归调用栈的最大深度为数组长度 n，每层常数空间，因此为 O(n)。

### 说明
- 虽然代码中使用了 `for` 循环来跳过已考虑的元素，本质上仍是枚举所有子集，未利用记忆化避免重复子问题。
- 对于 LeetCode 416 的数据范围（n ≤ 200，target ≤ 10000），该解法会超时，通常需要使用动态规划
（O(n × target)）或带记忆化的 DFS（O(n × target)）才能通过。

Refer to Deepseek
Q1：You mention "这说明你的原生DFS 代码本身就有 bug！它没有处理当当前元素直接等于 target 且没有后续元素的情况。正确的做法应该是在循环之前检查 if (target == nums[index]) return true; 或者在循环中允许 i=index（但 i 从 index+1 开始，所以不行）。因此，你的原生 DFS 需要修复：在循环之前加一个判断：if (target == nums[index]) return true; 这样最后一个元素就能成功。"？
在原生 DFS 中，确实存在一个 bug：当当前元素 nums[index] 恰好等于 target 时，它应该被直接选中并返回 true，但原代码只在循环中尝试 target - nums[index]，而循环从 index+1 开始，因此无法处理这种情况（特别是当 index 是最后一个元素且 target == nums[index] 时，循环不会执行，函数最终返回 false）。
修复方法：在进入循环之前，先检查 if (target == nums[index]) return true;。
修正后的代码如下：
class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % 2 != 0) return false;
        int target = sum / 2;
        // 尝试以每个元素作为起始
        for (int i = 0; i < nums.length; i++) {
            if (helper(nums, i, target)) return true;
        }
        return false;
    }

    private boolean helper(int[] nums, int index, int target) {
        if (index >= nums.length) return false;
        if (target == 0) return true;
        // 关键修复：当前元素正好等于剩余目标，直接成功
        if (target == nums[index]) return true;
        // 尝试从后续元素中继续选取
        for (int i = index + 1; i < nums.length; i++) {
            if (target >= nums[index]) {
                if (helper(nums, i, target - nums[index])) return true;
            }
        }
        return false;
    }
}
这样，当 nums = [1,5,11,5] 时，递归到 index=2（元素 11），target 从初始 11 减少到 11（因为之前未选任何元素），target == nums[2] 触发 return true，从而正确返回 true。

Q2：But why without your new added bug fix line:
// 关键修复：当前元素正好等于剩余目标，直接成功
if (target == nums[index]) return true;
the original Native DFS solution pass all 147 test cases from leetcode platform ? this bug not cause an issue ?
我们注意到用户指出：原始 DFS 代码（没有 if (target == nums[index]) return true;）仍然通过了 LeetCode 所有 147 个测试用例。这意味着该 bug 实际上并未触发失败，因为测试数据中不存在需要这种场景的情况？或者实际上原始代码的逻辑已经隐含处理了？让我们分析一下。
原始代码的递归逻辑是：每次进入 helper(index, target)，如果 target == 0 返回 true。然后它遍历 i 从 index+1 到末尾，尝试 helper(i, target - nums[index])。注意，它并没有直接检查 target == nums[index]，但可以通过以下方式间接实现：假设 target == nums[index]，那么 target - nums[index] == 0。在循环中，如果存在某个 i 使得 helper(i, 0) 返回 true，那么就会成功。而 helper(i, 0) 在函数开头有 if(target == 0) return true;，所以只要 i 在范围内（即 index+1 到 n-1 存在），它就会返回 true。但如果 index 是最后一个元素（index == n-1），则循环不会执行（因为 i 从 n 开始，不满足 i < n），此时函数会直接返回 false。所以问题仅出现在当 index 是最后一个元素且 target == nums[index] 时，无法成功。但测试用例中是否存在这样的场景？可能 LeetCode 的测试数据中，当需要选最后一个元素时，总会有一个更早的元素也可以选？或者由于我们是从外层循环遍历每个起始索引，即使最后一个元素单独等于 target，也会被外层循环以 i = n-1 调用 helper(nums, n-1, target)，而在这个调用中，由于 index = n-1，循环为空，且 target == nums[n-1]，但缺少直接判断，所以会返回 false。所以如果存在这样的测试用例，原始代码就会失败。但实际通过所有测试，说明没有这样的用例，或者可能因为题目保证数组非空且至少有两个元素？但逻辑上 bug 确实存在。
用户想知道为什么没有这个修复也能通过。答案是：LeetCode 的测试用例可能没有覆盖到“最后一个元素单独构成目标”的情况，或者因为当 target 等于最后一个元素时，前面一定有其他组合可以选？实际上，例如 nums = [1,2,3,6]，总和 12，target=6。最后一个元素 6 本身就可以，但原始代码会失败吗？外层循环 i=3 调用 helper(3,6)，index=3，target=6，target !=0，循环从 i=4 开始，不执行，返回 false。所以会错过。但 LeetCode 测试用例可能没有这种？或者可能有但被其他起始索引找到了？比如 i=2 时，nums[2]=3，递归会尝试找和为 3 的后续元素？不，3+? 需要另一个 3，但只有 1,2,3,6，没有另一个 3。所以会失败。因此实际上这个 bug 会导致某些正确情况被误判。但用户说通过了所有测试，这可能是因为 LeetCode 的测试用例没有覆盖到这种边界，或者我记错了？实际上 416 的典型测试用例包括 [1,2,3,6]，预期 true，但原始代码会返回 false。如果用户代码通过了所有 147 个测试，说明他可能用的不是这个输入，或者 LeetCode 的测试集里没有这个？但我知道 416 的官方测试用例包括 [1,2,3,6]。所以可能存在误解：也许用户的原始代码中，外层循环是从 0 到 n-1，但递归中如果 target == nums[index]，那么它会在循环中尝试 target - nums[index] = 0，然后调用 helper(i, 0)，而 helper(i, 0) 会立即返回 true（因为 target==0）。但前提是存在一个 i 使得 i 在范围内。对于 index 不是最后一个元素的情况，确实存在后续索引，所以可以成功。对于 index 是最后一个元素，没有后续索引，就会失败。所以只有当最后一个元素单独构成 target 时才会失败。而 LeetCode 的测试用例中，这样的 case 可能存在也可能不存在。用户说通过了所有测试，可能他的代码实际上有隐含的修复？或者他用的记忆化版本？但这里讨论的是无记忆化的原始 DFS。我需给出合理解释。
最终回答：该 bug 在特定情况下（最后一个元素恰好等于 target）会导致错误，但 LeetCode 的测试用例可能没有覆盖这一场景，或者由于其他起始索引也能找到解（例如，如果最后一个元素等于 target，那么前面的元素中可能有组合也能达到 target？不一定）。实际上，对于 [1,2,3,6]，target=6，最后一个元素 6 是唯一解，但前面元素无法组成 6（1+2+3=6，哦，1+2+3=6，所以也有解！所以不会触发 bug）。因为 1+2+3=6，所以外层循环 i=0 就能找到解。因此，只有当唯一解是最后一个元素单独构成，且前面元素无法组成 target 时，才会失败。例如 [2,2,4]，总和 8，target=4。最后一个元素 4 是唯一解吗？前面 2+2=4，也有解。所以不容易构造。实际上，一个反例是 [1,1,1,1,4]，总和 8，target=4。最后一个元素 4 是唯一解？前面 1+1+1+1=4，也有解。所以很难构造只有最后一个元素是唯一解的情况。因为如果最后一个元素等于 target，那么前面所有元素的和也是 target（因为总和为 2*target），所以前面元素的和也等于 target，因此前面元素必然也能组成 target（通过选择所有前面元素）。所以实际上，当最后一个元素等于 target 时，前面所有元素的和也等于 target，因此前面元素的全集就是一个解。所以外层循环从 0 开始就能找到解。因此这个 bug 永远不会影响正确性！因为如果 nums[n-1] == target，那么前 n-1 个元素的和也是 target，所以必然存在一个子集（即全部前 n-1 个元素）和为 target，这个子集可以通过外层循环从某个起始索引（比如 0）递归得到。所以原始代码不会出错。因此用户无需添加那行代码也能通过所有测试。这是一个有趣的数学性质。
因此，回答用户：因为当最后一个元素等于 target 时，前 n-1 个元素的和也等于 target，所以必然存在另一个解，从而 bug 不会被触发。

你的观察非常敏锐。实际上，你原始的无记忆化 DFS 代码（没有 if (target == nums[index]) return true;）之所以能通过 LeetCode 所有 147 个测试用例，是因为一个数学上的必然性：
如果最后一个元素 nums[n-1] 恰好等于 target，那么前 n-1 个元素的和也必然等于 target（因为总和为 2*target）。因此，前 n-1 个元素的全体本身就是一个合法的子集，而这个子集可以通过外层循环从索引 0 开始，递归地选取所有前面的元素得到。所以即便递归函数无法处理“只选最后一个元素”的情况，另一个解（选所有前面的元素）依然存在，并且会被外层循环的某个起始索引成功找到。
因此，你的原始代码在实际测试中不会失败。添加 if (target == nums[index]) return true; 是为了逻辑完整性，处理这种边界情况，但并非必要，因为总有其他解存在。不过，从代码健壮性角度，建议加上这一行，避免依赖数学巧合。
所以，你原始的 DFS 代码实际上没有 bug，只是逻辑上不够直观。它依然正确。
而在加上 if(target == nums[index]) return true; 这一行后，修复了以下问题并改进了行为：
1. 修复了原生 DFS 的逻辑漏洞
- 原代码问题：当 target 恰好等于 nums[index] 时，代码只能通过后续元素递归来凑出 target - nums[index] = 0，但递归从 index+1 开始，无法利用当前元素本身。如果 index 是最后一个元素且没有后续元素，循环不会执行，函数会返回 false，导致漏掉合法解。
- 修复后：直接检测到 target == nums[index]，立即返回 true，正确处理了这种边界情况。
2. 提高了代码的健壮性和可读性
- 不再依赖“数组中必然存在另一个互补子集”的数学巧合（例如，当某个元素等于半和时，剩余元素之和也等于半和，因此其他起始索引也能找到解）。修复后，算法对任何子集和问题都通用，逻辑更清晰。
3. 略微提升了性能
- 避免了不必要的递归调用和循环遍历，当遇到 target == nums[index] 时直接返回，减少了计算开销。
总之，这一行添加使递归函数自洽地处理了“当前元素直接命中目标”的情况，完善了递归基例，是正确且推荐的改进。


Style 2: Take or not take
class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for(int num : nums) {
            sum += num;
        }
        if(sum % 2 != 0) {
            return false;
        }
        int target = sum / 2;
        if(helper(nums, 0, target)) {
            return true;
        }
        return false;
    }

    private boolean helper(int[] nums, int index, int target) {
        if(index >= nums.length) {
            return false;
        }
        // Note: no need if(index == nums.length - 1 && target == 0), because
        // that we are not required to recursive till end of the array to find 
        // a solution, even in middle of the recursion if target minus to 0,
        // we consider that as combination as a solution.
        if(target == 0) {
            return true;
        }
        // Still need to add below condition to robust the logic
        if(target == nums[index]) {
           return true;
        }
        // Not take
        if(helper(nums, index + 1, target)) {
            return true;
        }
        // Take
        if(target >= nums[index]) {
            if(helper(nums, index + 1, target - nums[index])) {
                return true;
            }
        }
        return false;
    }
}

该解法是典型的**不带记忆化的 DFS**，通过递归对每个元素做“选”或“不选”的决策。其复杂度分析如下：

- **时间复杂度**：**O(2ⁿ)**  
  每个元素有选与不选两种可能，递归树共有 2ⁿ 个叶子节点，每个节点执行常数时间操作，因此总时间复杂度为指数级。

- **空间复杂度**：**O(n)**  
  递归调用栈的最大深度为数组长度 n（最坏情况下一直递归到末尾），每层占用常数空间，故空间复杂度为 O(n)。

### 说明
- 该解法没有使用记忆化，因此无法避免重复子问题的计算，对于 LeetCode 416 的约束（n ≤ 200，target ≤ 10000）会严重超时。
- 实际解题时，推荐使用动态规划（O(n × target)）或带记忆化的 DFS（O(n × target)）来保证通过。

Refer to Deepseek
Q1：Is 'if(index >= nums.length)' condition also required in take and not take Native DFS solution ?
注意：if(index >= nums.length) 在 if(target == 0) 之前。那么当 target == nums[index] 且 index 是最后一个元素时，index+1 == n，递归调用 helper(nums, n, 0)。此时，首先检查 index >= nums.length 为 true，直接返回 false，而不会检查 target == 0。因此，按这个顺序，该情况会返回 false，导致错误。
用户问“are you sure? 因为在我的 solution 中，我有 if(index >= nums.length) 检查在 if(target == 0) 之前，但看起来仍然工作？” 这需要分析是否真的工作。实际上，如果顺序是 index 越界先判断，那么当递归到 index == n 且 target == 0 时，会返回 false，从而丢失正确解。但是用户说“看起来仍然工作”，可能是因为其他分支找到了解，或者测试用例没有覆盖到这种边界。理论上，这个顺序是有问题的。正确的做法应该先判断 target == 0，再判断越界，或者把越界条件改为 if(index == nums.length) return target == 0;。
因此，我的先前断言有误。我需要纠正：在用户给出的代码中，由于越界检查在前，当 target == nums[index] 且 index 是最后一个元素时，递归到下一层会因越界返回 false，从而导致无法正确返回 true。所以实际上需要调整顺序或添加额外判断。
用户问的是“是否还需要添加 if(target == nums[index]) return true;”，答案是：由于顺序问题，该情况会被漏掉，因此需要添加这一行，或者调整顺序（将 target == 0 检查移到越界之前）。所以我的回答应该是：需要添加，因为当前顺序下，递归调用 helper(index+1, 0) 会先触发越界返回 false，无法正确处理。
让我给出正确的解释。
在你的代码顺序中（先检查 index >= nums.length 返回 false，再检查 target == 0），确实存在一个边界问题：
当 target == nums[index] 且 index 是最后一个元素时：
- 进入 take 分支，调用 helper(nums, index+1, 0)。
- 此时 index+1 == n，首先检查 if(index >= nums.length)，条件成立，直接返回 false，不会进入 if(target == 0) 分支。
- 因此，这个合法解会被漏掉，函数返回 false。
为什么你觉得“看起来仍然工作”？
可能是因为：
1.测试用例中，当最后一个元素等于 target 时，前面的元素之和也等于 target（因为总和为 2*target），所以通过“不选”最后一个元素、选择前面所有元素也能成功。因此实际运行中并未触发这个边界错误。
2.但这不是代码逻辑的正确性保证，而是数学巧合。
所以，你的代码确实需要添加 if(target == nums[index]) return true;，或者调整顺序（将 if(target == 0) 移到越界检查之前）。正确的顺序应该是：
if (target == 0) return true;
if (index >= nums.length) return false;
这样，当递归到 (index == n, target == 0) 时，先命中 target == 0 返回 true，避免越界误判。
因此，对于你给出的这个“选或不选”结构，由于顺序问题，仍然需要添加 if(target == nums[index]) return true; 作为补救，或者更推荐调整判断顺序。感谢你的细致观察！

Q2：Can you use nums = {1,2,3,6} verify your above observation ?
我们使用 nums = {1,2,3,6}，总和=12，target=6。数组元素：1,2,3,6。最后一个元素是6，等于target。按照你的代码顺序（先检查index越界，再检查target==0），我们来模拟递归：
helper(0,6):
- 不选1: helper(1,6)
- 选1: helper(1,5)
我们关心的是是否存在直接选6的情况。即从索引3开始，target=6，选6的路径：helper(3,6) -> take分支：helper(4,0)。此时index=4>=n，返回false，然后因为take分支返回false，整体返回false。所以helper(3,6)会返回false。但我们可以通过选前面所有元素1+2+3=6来得到解：helper(0,6)不选1？不，需要选1,2,3。实际上，从索引0开始，通过一系列选和不选，可以凑出6。所以最终答案为true，但不是因为最后一个元素单独被选，而是因为前面的子集。因此代码仍然能通过测试，因为存在其他解。但是，如果数组是{6,1,2,3}，最后一个元素是3，target=6，那么单独选第一个6也能成功，但第一个元素不是最后一个。所以边界情况可能不触发。为了测试你的代码是否依赖于顺序，可以构造一个数组，使得只有最后一个元素等于target，且前面的元素无法凑出target（例如总和为2*target，但前面元素和小于target？但总和固定，如果最后一个等于target，前面和必为target，所以前面总能凑出target，因为前面所有元素的和正好是target。所以实际上，当最后一个元素等于target时，前面所有元素的和也等于target，因此选择前面所有元素就是一个解。所以你的代码总能通过，不是因为逻辑正确，而是因为数学性质保证了另一个解存在。因此，你不需要添加那行代码也能通过所有测试。但是，为了逻辑严谨，建议调整顺序或添加判断。
我们使用 nums = {1,2,3,6} 来验证。
- 总和 = 1+2+3+6 = 12，target = 6。
- 最后一个元素 nums[3] = 6，恰好等于 target。
你的代码（先检查越界，再检查 target==0）
递归调用 helper(3, 6)：
if (index >= nums.length) // index=3 <4, false
if (target == 0) // false
// 不选分支：helper(4,6) -> index>=4 -> false
// 选分支：target >= nums[3]=6 成立，调用 helper(4, 0)
// 在 helper(4,0) 中：
//   if (index >= nums.length) -> true，返回 false
// 所以选分支返回 false
// 最终 helper(3,6) 返回 false
因此，直接选最后一个元素 6 这条路会失败。
是否存在其他解？
前面的元素 {1,2,3} 的和也是 6，所以可以通过 helper(0,6) 选择 1,2,3 来成功。例如：
- 从索引0开始，不选1？不行，需要选1。递归路径：选1 -> helper(1,5) -> 选2 -> helper(2,3) -> 选3 -> helper(3,0) -> 此时 index=3, target=0，但注意：在 helper(3,0) 中，先检查 index>=4? false，然后检查 target==0? true，返回 true。所以最终成功。
因此，即使最后一个元素单独选失败，整个函数仍能返回 true。
结论
你的代码在数学上总能通过，因为当最后一个元素等于 target 时，前面所有元素之和也等于 target，所以必然存在另一个解（选择前面所有元素）。因此，你不需要添加 if(target == nums[index]) return true; 也能通过所有测试。但为了逻辑的完整性和可读性，建议调整判断顺序（将 target == 0 放在越界检查之前）或添加该行，使其不依赖数学巧合。


Solution 2: DFS + Memoization (30 min)
Style 1: For loop
Wrong Solution
class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for(int num : nums) {
            sum += num;
        }
        if(sum % 2 != 0) {
            return false;
        }
        int target = sum / 2;
        int n = nums.length;
        Boolean[][] memo = new Boolean[n][target + 1];
        // For loop has the nature to exclude a element
        // since we only consider elements at or after
        // current index, same effect as take or not take
        for(int i = 0; i < nums.length; i++) {
            if(helper(nums, i, target, memo)) {
                return true;
            }
        }
        return false;
    }

    private boolean helper(int[] nums, int index, int target, Boolean[][] memo) {
        if(index >= nums.length) {
            return false;
        }
        // Note: no need if(index == nums.length - 1 && target == 0), because
        // the we are not required to recursive till end of the array to find 
        // a solution, even in middle of the recursion if target minus to 0,
        // we consider that as combination as a solution.
        if(target == 0) {
            return true;
        }
        if(memo[index][target] != null) {
            return memo[index][target];
        }
        for(int i = index + 1; i < nums.length; i++) {
            if(helper(nums, i, target - nums[index], memo)) {
                return memo[index][target] = true;
            }
        }
        return memo[index][target] = false;
    }
}
but when input as nums = [1,5,11,5], it error out as below:
java.lang.ArrayIndexOutOfBoundsException: Index -6 out of bounds for length 12
  at line 28, Solution.helper
  at line 32, Solution.helper
  at line 32, Solution.helper
  at line 32, Solution.helper
  at line 14, Solution.canPartition
  at line 56, __DriverSolution__.__helper__
  at line 86, __Driver__.main
Correct Solution
class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for(int num : nums) {
            sum += num;
        }
        if(sum % 2 != 0) {
            return false;
        }
        int target = sum / 2;
        int n = nums.length;
        // First dimension only requires 'n' since index range won't exceed nums length
        // Second dimension will require 'target + 1' since target itself need to be approached
        // which means the boundary condition will include 'target' itself
        Boolean[][] memo = new Boolean[n][target + 1];
        // For loop has the nature to exclude a element
        // since we only consider elements at or after
        // current index, same effect as take or not take
        for(int i = 0; i < nums.length; i++) {
            if(helper(nums, i, target, memo)) {
                return true;
            }
        }
        return false;
    }

    private boolean helper(int[] nums, int index, int target, Boolean[][] memo) {
        if(index >= nums.length) {
            return false;
        }
        // Note: no need if(index == nums.length - 1 && target == 0), because
        // the we are not required to recursive till end of the array to find 
        // a solution, even in middle of the recursion if target minus to 0,
        // we consider that as combination as a solution.
        if(target == 0) {
            return true;
        }
        if(target == nums[index]) {
           return true;
        }
        if(memo[index][target] != null) {
            return memo[index][target];
        }
        for(int i = index + 1; i < nums.length; i++) {
            // The condition added to fix the target < nums[index] issue
            // during recursion, when it happen we actually skip that number
            if(target >= nums[index]) {
                if(helper(nums, i, target - nums[index], memo)) {
                    return memo[index][target] = true;
                }
            }
        }
        return memo[index][target] = false;
    }
}

该带记忆化的 DFS 解法，其时间复杂度和空间复杂度如下：

- **时间复杂度**：**O(n² × target)**  
  状态总数为 `n × (target+1)`，每个状态 `(index, target)` 需要遍历所有 `i > index`（最多 `n` 次）进行递归调用。
  由于记忆化，每个状态只计算一次，因此总操作次数约为 `n × target × n` = `n² × target`。在 `n ≤ 200`，`target ≤ 10000` 时，
  最坏约 `4×10⁸` 次操作，可能接近时间极限，但实际测试中许多状态不可达，通常能通过。

- **空间复杂度**：**O(n × target)**  
  记忆化数组 `memo` 的大小为 `(n+1) × (target+1)`，存储布尔值，约 `200 × 10000 = 2×10⁶` 个元素，内存可接受。

### 说明
- 该解法相比于无记忆化的指数级 DFS（`O(2ⁿ)`）有巨大改进，但相比标准“选或不选”的 DFS + 记忆化（`O(n × target)`）
仍多了一个 `n` 因子，因为每个状态需要循环遍历后续索引。
- 实际推荐使用“选或不选”的递归形式（`helper(index+1, target) || helper(index+1, target - nums[index])`），
配合记忆化，时间复杂度为 `O(n × target)`。
Refer to Deepseek
For input as nums = [1,5,11,5], when target < nums[index] will happen ? the concern is target is 11 as 1/2 of total sum 22 = 1 + 5 + 11 + 5, target as half of the sum of the array and at least will contains 1 element of the array, it has no chance to become less than a single element in the array, how target < nums[index] happened ?
你问得非常细致。在输入 nums = [1,5,11,5] 中，总和为 22，target = 11。你的递归逻辑是：外层循环遍历每个可能的起始索引，然后在内层循环中，对于每个后续索引 i，递归地尝试用当前元素 nums[index] 去凑剩余的 target。
关键点：在递归调用时，你使用的是 target - nums[index]，其中 index 是当前递归层的参数（即外层循环传入的那个索引），而不是内层循环的 i。这意味着，无论你递归到哪一层，减去的始终是当前递归层的那个固定元素，而不是新选择的元素。这会导致 target 迅速变小，甚至变成负数。
我们来模拟一下递归过程（以外层循环 index = 0，即元素 1 为例）：
1.初始调用：helper(nums, index=0, target=11)
内层循环 i 从 1 到 4：
- 当 i=1（元素5），target >= nums[0]=1 成立，递归调用 helper(nums, i=1, target=10)
- 在 helper(nums, 1, 10) 中，index=1，nums[1]=5。
内层循环 i 从 2 到 4：

- 当 i=2（元素11），target=10 >= nums[1]=5 成立，递归调用 helper(nums, 2, target=5)
- 在 helper(nums, 2, 5) 中，index=2，nums[2]=11。
此时 target=5 而 nums[2]=11，5 < 11，所以 if(target >= nums[index]) 条件不满足，跳过该分支。
继续内层循环 i=3（元素5），target=5 >= 11? 不成立，跳过。i=4 不存在，循环结束，返回 false。
- 当 i=3（元素5），target=10 >= 5 成立，递归调用 helper(nums, 3, target=5)
- 在 helper(nums, 3, 5) 中，index=3，nums[3]=5。
内层循环 i 从 4 开始，无元素，返回 false。
- 继续 i=2、i=3... 最终无法凑出 10。
由此可见，在 helper(nums, 2, 5) 这一层，确实出现了 target=5 < nums[2]=11 的情况。这是因为递归过程中，target 已经从初始的 11 逐渐减少到了 5，而当前层的元素是 11，自然就小于了。
所以你的疑问“target 怎么会小于单个元素”的答案是：递归过程中的 target 是剩余需要凑的和，它会随着你选取元素而不断减小，当减小到比当前要尝试的元素还小时，就会发生 target < nums[index]。这正是你需要加 if(target >= nums[index]) 判断的原因，否则会传入负数下标导致数组越界。
你的修复是正确的。虽然你的递归风格（外层循环起始点 + 内层循环选择下一个索引）与传统“选或不选”风格不同，但它依然能够正确枚举所有子集组合，只是需要小心处理 target 越界问题。

Style 2: Take or not take
class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for(int num : nums) {
            sum += num;
        }
        if(sum % 2 != 0) {
            return false;
        }
        int target = sum / 2;
        int n = nums.length;
        // First dimension only requires 'n' since index range won't exceed nums length
        // Second dimension will require 'target + 1' since target itself need to be approached
        // which means the boundary condition will include 'target' itself
        Boolean[][] memo = new Boolean[n][target + 1];
        if(helper(nums, 0, target, memo)) {
            return true;
        }
        return false;
    }

    private boolean helper(int[] nums, int index, int target, Boolean[][] memo) {
        if(index >= nums.length) {
            return false;
        }
        // Note: no need if(index == nums.length - 1 && target == 0), because
        // the we are not required to recursive till end of the array to find 
        // a solution, even in middle of the recursion if target minus to 0,
        // we consider that as combination as a solution.
        if(target == 0) {
            return true;
        }
        if(target == nums[index]) {
           return true;
        }
        if(memo[index][target] != null) {
            return memo[index][target];
        }
        // Not take
        if(helper(nums, index + 1, target, memo)) {
            return memo[index][target] = true;
        }
        // Take
        if(target >= nums[index]) {
            if(helper(nums, index + 1, target - nums[index], memo)) {
                return memo[index][target] = true;
            }
        }
        return memo[index][target] = false;
    }
}

该解法采用带记忆化的 DFS（选或不选），其复杂度分析如下：

- **时间复杂度**：**O(n × target)**  
  状态总数为 `n × (target+1)`，每个状态只计算一次，每次计算仅执行常数次操作（判断“不选”和“选”两个分支）。
  因此总时间复杂度为 O(n × target)。

- **空间复杂度**：**O(n × target)**  
  记忆化数组 `memo` 的大小为 `n × (target+1)`，存储 `Boolean` 对象，加上递归调用栈深度 O(n)，总体空间为 O(n × target)。

该解法是 LeetCode 416 的标准 DFS + 记忆化实现，能够在给定约束（n ≤ 200，target ≤ 10000）下高效运行。

Solution 3: 2D DP (30 min)
Style 1: For loop Native DFS converted
Before improvement (2118 ms)
class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for(int num : nums) {
            sum += num;
        }
        if(sum % 2 != 0) {
            return false;
        }
        int target = sum / 2;
        int n = nums.length;
        boolean[][] dp = new boolean[n][target + 1];
        // For index == n no need to initialize as dp array default as 'false'
        //for(int j = 0; j <= target; j++) {
        //    dp[n][j] = false;
        //}
        // For target == 0 need to initialize as 'true'
        for(int i = 0; i < n; i++) {
            dp[i][0] = true;
        }
        // Loop 'index' from n - 1 to 0 will map to bottom condition 'index >= n',
        // as in Native DFS 'index' keep increasing, then in bottom up DP reversely
        // keep decreasing
        // Loop 'cur' from 0 to target will map to bottom condition 'target == 0',
        // as in Native DFS 'target' keep decreasing, then in bottom up DP reversely
        // keep increasing 
        for(int index = n - 1; index >= 0; index--) {
            for(int cur = 0; cur <= target; cur++) {
                for(int i = index + 1; i < n; i++) {
                    // The condition added to fix the cur < nums[index] issue
                    // during recursion, when it happen we actually skip that number
                    if(cur >= nums[index]) {
                        if(dp[i][cur - nums[index]]) {
                            dp[index][cur] = true;
                        }
                    }
                }
            }
        }
        // Find the final status in Native DFS's top condition
        // where we start with 'i' and 'target' in a for loop,
        // if any of them is true, then we get the solution
        for(int i = 0; i < n; i++) {
            if(dp[i][target]) {
                return true;
            }
        }
        return false;
    }
}

After improvement (1052 ms)
class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for(int num : nums) {
            sum += num;
        }
        if(sum % 2 != 0) {
            return false;
        }
        int target = sum / 2;
        int n = nums.length;
        boolean[][] dp = new boolean[n][target + 1];
        // For index == n no need to initialize as dp array default as 'false'
        //for(int j = 0; j <= target; j++) {
        //    dp[n][j] = false;
        //}
        // For target == 0 need to initialize as 'true'
        for(int i = 0; i < n; i++) {
            dp[i][0] = true;
        }
        // Loop 'index' from n - 1 to 0 will map to bottom condition 'index >= n',
        // as in Native DFS 'index' keep increasing, then in bottom up DP reversely
        // keep decreasing
        // Loop 'cur' from 0 to target will map to bottom condition 'target == 0',
        // as in Native DFS 'target' keep decreasing, then in bottom up DP reversely
        // keep increasing 
        for(int index = n - 1; index >= 0; index--) {
            for(int cur = 0; cur <= target; cur++) {
                // Enhance the same condition added into Native DFS solution
                if(cur == nums[index]) {
                    dp[index][cur] = true;
                    continue;
                }
                // The condition added to fix the cur < nums[index] issue
                // during recursion, when it happen we actually skip that number
                if(cur > nums[index]) {
                    for(int i = index + 1; i < n; i++) {
                        if(dp[i][cur - nums[index]]) {
                            dp[index][cur] = true;
                            break;
                        }
                    }
                }
            }
        }
        // Find the final status in Native DFS's top condition
        // where we start with 'i' and 'target' in a for loop,
        // if any of them is true, then we get the solution
        for(int i = 0; i < n; i++) {
            if(dp[i][target]) {
                return true;
            }
        }
        return false;
    }
}

该解法的时间复杂度为 **O(n² × target)**，空间复杂度为 **O(n × target)**，其中 `n` 是数组长度，`target` 是总和的一半。

### 详细分析
- **时间**：三层循环。最外层 `index` 从 `n-1` 到 `0`，共 `n` 次；中间层 `cur` 从 `0` 到 `target`，
共 `target+1` 次；内层 `i` 从 `index+1` 到 `n-1`，最坏情况下每次遍历 `O(n)`。因此总操作数约
为 `n × target × n = n² × target`。实际运行中，由于 `cur > nums[index]` 条件以及找到 `true` 
后 `break` 提前退出，平均情况可能稍好，但最坏上界仍为 `O(n² × target)`。
- **空间**：二维布尔数组 `dp` 大小为 `n × (target+1)`，故空间复杂度为 `O(n × target)`。

在 LeetCode 416 的约束下（`n ≤ 200`, `target ≤ 10000`），最坏情况 `200² × 10000 = 4×10⁸` 次操作，
可能接近时间极限，但通常能通过；空间约 `200 × 10001 ≈ 2×10⁶` 布尔值，内存可接受。如需更优性能，
建议使用经典的一维 DP（`O(n × target)` 时间，`O(target)` 空间）。

Refer to Deepseek
你的 DP 实现基本正确，但遗漏了一个关键情况：当当前元素 nums[index] 恰好等于剩余和 cur 时，应该直接成功，无需依赖后续元素。另外，原生 DFS 本身也存在这个遗漏，需要补充该条件。
以下是修正后的 2D DP 代码，完全映射了修复后的 DFS 逻辑：
class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % 2 != 0) return false;
        int target = sum / 2;
        int n = nums.length;
        boolean[][] dp = new boolean[n][target + 1];
        // 初始化：当剩余和为0时，已经成功（不需要再选元素）
        for (int i = 0; i < n; i++) dp[i][0] = true;
        // 自底向上计算
        for (int index = n - 1; index >= 0; index--) {
            for (int cur = 0; cur <= target; cur++) {
                // 情况1：当前元素正好等于剩余和，直接成功
                if (cur == nums[index]) {
                    dp[index][cur] = true;
                    continue;
                }
                // 情况2：剩余和大于当前元素，尝试选取后从后续元素中继续
                if (cur > nums[index]) {
                    for (int i = index + 1; i < n; i++) {
                        if (dp[i][cur - nums[index]]) {
                            dp[index][cur] = true;
                            break;
                        }
                    }
                }
            }
        }
        // 检查是否存在起始索引使得能凑出 target
        for (int i = 0; i < n; i++) {
            if (dp[i][target]) return true;
        }
        return false;
    }
}
逻辑映射说明
- 状态定义：dp[index][cur] 对应原生 DFS 中 helper(index, cur) 的返回值，表示从索引 index 开始（且必须选择 nums[index] 作为子集的第一个元素）能否凑出剩余和 cur。
- 基本情况：
- cur == 0：已经凑满，无需再选任何元素 → dp[i][0] = true。
- cur == nums[index]：直接选择当前元素即凑满 → dp[index][cur] = true。
- 递推关系：若 cur > nums[index]，则选择当前元素后，剩余和为 cur - nums[index]，需要从后续索引 i > index 中找到某个 i 使得 dp[i][cur - nums[index]] 为真。
- 计算顺序：index 从大到小，确保后续索引的状态已计算。
- 最终答案：任意 dp[i][target] == true 即存在子集和为 target。
该解法时间复杂度 O(n² × target)，空间 O(n × target)，适合理解映射关系。实际提交时建议使用经典的一维 DP（O(n × target)）以获得更好性能。

追问：是否能解释一下 "break" 在该改进中的意义？
在代码中，break 的作用是一旦找到满足条件的后续索引，就立即终止内层循环，避免不必要的继续遍历。
详细解释
- 内层循环 for (int i = index + 1; i < n; i++) 遍历所有可能的后续索引 i，检查是否存在某个 i 使得 dp[i][cur - nums[index]] 为 true。
- 如果找到这样的 i，说明从当前 index 开始选择 nums[index] 后，剩余和可以由后续元素凑出，因此 dp[index][cur] 应该设为 true。
- 由于我们只需要知道是否存在至少一个 i，一旦找到第一个满足条件的 i，就没有必要继续检查剩余的 i 了，因此使用 break 跳出循环，提高执行效率。
如果不加 break
- 循环会继续检查后面的索引，虽然最终结果仍然是 true，但会浪费额外的时间。在数据规模较大时，这种优化很有意义。
总结
break 在这里是一个剪枝优化，它保证当已经确定状态为 true 后，立即停止搜索，避免冗余计算。

Style 2: Take or not take Native DFS converted
class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % 2 != 0) return false;
        int target = sum / 2;
        int n = nums.length;
        // dp[i][t] 表示从索引 i 开始（包括 i）能否凑出剩余和 t
        boolean[][] dp = new boolean[n + 1][target + 1];
        
        // 1. 边界条件：当剩余和为 0 时，成功（无论索引是否越界）
        for (int i = 0; i <= n; i++) dp[i][0] = true;
        
        // 2. 针对每个索引，如果当前元素恰好等于目标值，直接成功
        for (int i = 0; i < n; i++) {
            if (nums[i] == target) dp[i][nums[i]] = true;
        }
        
        // 3. 自底向上递推（从后往前，因为依赖下一行的状态）
        for (int i = n - 1; i >= 0; i--) {
            for (int t = 1; t <= target; t++) {
                // 不选当前元素
                if (dp[i + 1][t]) {
                    dp[i][t] = true;
                    // 含义：如果“不选当前元素”能够成功（即 dp[i+1][t] 为 true），
                    // 那么当前状态 dp[i][t] 直接设为 true，并跳过后续的“选”分支检查。
                    // 为什么需要：因为“不选”已经成功，我们不再需要检查“选”分支
                    // （即使“选”也可能成功，但结果相同）。这样可以避免一次不必要的 
                    // if 判断和可能的数组访问。
                    continue;
                }
                // 选当前元素（如果 t >= nums[i]）
                if (t >= nums[i] && dp[i + 1][t - nums[i]]) {
                    dp[i][t] = true;
                }
            }
        }
        return dp[0][target];
    }
}


Refer to
L64.Minimum Path Sum (Ref.L62,L63)
L112.P9.1.Path Sum (Ref.L257,L113)
L698.Partition to K Equal Sum Subsets (Ref.L473,L2397)
L931.Minimum Falling Path Sum (Ref.L1937)
L1937.Maximum Number of Points with Cost (Ref.L64,L1981)
L1981.Minimize the Difference Between Target and Chosen Elements (Ref.L64,L112)
