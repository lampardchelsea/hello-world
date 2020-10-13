/**
 Refer to
 https://leetcode.com/problems/partition-to-k-equal-sum-subsets/
 Given an array of integers nums and a positive integer k, find whether it's possible to divide this array into k non-empty subsets whose sums are all equal.

 Example 1:
 Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
 Output: True
 Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
 
 Note:
 1 <= k <= len(nums) <= 16.
 0 < nums[i] < 10000.
*/

// Solution 1: DFS + Backtracking (more like greedy)
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DFS_BackTracking/MatchsticksToSquare.java
// Runtime: 2173 ms, faster than 5.03% of Java online submissions for Partition to K Equal Sum Subsets.
// Memory Usage: 36.2 MB, less than 7.06% of Java online submissions for Partition to K Equal Sum Subsets.
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if(sum % k != 0) {
            return false;
        }
        return helper(0, nums, new int[k], sum / k);
    }
    
    private boolean helper(int index, int[] nums, int[] sums, int target) {
        if(index == nums.length) {
            int temp = sums[0];
            for(int i = 1; i < sums.length; i++) {
                if(sums[i] != temp) {
                    return false;
                }
            }
            return true;
        }
        for(int i = 0; i < sums.length; i++) {
            if(sums[i] + nums[index] <= target) {
                sums[i] += nums[index];
                if(helper(index + 1, nums, sums, target)) {
                    return true;
                }
                sums[i] -= nums[index];
            }
        }
        return false;
    }
}

// Solution 2: DFS + Backtracking (more like greedy) + Optimization by sorting the array first
// Refer to
// 1. 473. Matchsticks to Square is special case for 698. Partition to K Equal Sum Subsets
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DFS_BackTracking/MatchsticksToSquare.java
/**
 Sorting the input array DESC will make the DFS process run much faster. Reason behind this is we always try 
 to put the next matchstick in the first subset. If there is no solution, trying a longer matchstick first 
 will get to negative conclusion earlier. Following is the updated code. Runtime is improved from more than 
 1000ms to around 40ms. A big improvement.
 We can either reverse array as one more function or we can traverse indexes from end to start index
 Here we choose the 2nd way since it will take even less time and boost the time from 2173ms to 22ms
*/
// 2. Java beat 100%
// https://leetcode.com/problems/partition-to-k-equal-sum-subsets/discuss/108741/Solution-with-Reference
// 3. We need to use if(index == -1) instead of (index == 0) for the terminate case
// https://leetcode.com/problems/matchsticks-to-square/discuss/95729/Java-DFS-Solution-with-Explanation/100200
// 4. 中文解释
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
// Runtime: 22 ms, faster than 30.64% of Java online submissions for Partition to K Equal Sum Subsets.
// Memory Usage: 36.4 MB, less than 7.06% of Java online submissions for Partition to K Equal Sum Subsets.
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if(sum % k != 0) {
            return false;
        }
        Arrays.sort(nums);
        return helper(nums.length - 1, nums, new int[k], sum / k);
    }
    
    private boolean helper(int index, int[] nums, int[] sums, int target) {
        // Must index == -1 instead of index == 0, test case: [4,3,2,3,5,2,1] and k = 4
        // because for sums[i] += nums[index] also need to be checked when index = 0,
        // then pass index = 0 to next level as helper(index - 1, ....), the terminate 
        // checking condition should be if(index == -1) which will include previous
        // level index = 0
        if(index == -1) {
            int temp = sums[0];
            for(int i = 1; i < sums.length; i++) {
                if(sums[i] != temp) {
                    return false;
                }
            }
            return true;
        }
        for(int i = 0; i < sums.length; i++) {
            if(sums[i] + nums[index] <= target) {
                sums[i] += nums[index];
                if(helper(index - 1, nums, sums, target)) {
                    return true;
                }
                sums[i] -= nums[index];
            }
        }
        return false;
    }
}

// Solution 3: Classic DFS
// Refer to
// https://leetcode.com/problems/partition-to-k-equal-sum-subsets/discuss/108741/Solution-with-Reference
// https://www.cnblogs.com/grandyang/p/7733098.html
/**
 这道题给了我们一个数组nums和一个数字k，问我们该数字能不能分成k个非空子集合，使得每个子集合的和相同。给了k的范围是[1,16]，
 而且数组中的数字都是正数。这跟之前那道 Partition Equal Subset Sum 很类似，但是那道题只让分成两个子集合，所以问题可以转
 换为是否存在和为整个数组和的一半的子集合，可以用dp来做。但是这道题让求k个和相同的，感觉无法用dp来做，因为就算找出了一个，
 其余的也需要验证。这道题我们可以用递归来做，首先我们还是求出数组的所有数字之和sum，首先判断sum是否能整除k，不能整除的话
 直接返回false。然后需要一个visited数组来记录哪些数组已经被选中了，然后调用递归函数，我们的目标是组k个子集合，是的每个子
 集合之和为target = sum/k。我们还需要变量start，表示从数组的某个位置开始查找，curSum为当前子集合之和，在递归函数中，
 如果k=1，说明此时只需要组一个子集合，那么当前的就是了，直接返回true。如果curSum等于target了，那么我们再次调用递归，此时
 传入k-1，start和curSum都重置为0，因为我们当前又找到了一个和为target的子集合，要开始继续找下一个。否则的话就从start开始
 遍历数组，如果当前数字已经访问过了则直接跳过，否则标记为已访问。然后调用递归函数，k保持不变，因为还在累加当前的子集合，
 start传入i+1，curSum传入curSum+nums[i]，因为要累加当前的数字，如果递归函数返回true了，则直接返回true。否则就将当前数字
 重置为未访问的状态继续遍历
*/
// Runtime: 1 ms, faster than 95.86% of Java online submissions for Partition to K Equal Sum Subsets.
// Memory Usage: 36.3 MB, less than 7.06% of Java online submissions for Partition to K Equal Sum Subsets.
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if(sum % k != 0) {
            return false;
        }
        return helper(0, 0, nums, new boolean[nums.length], sum / k, k);
    }
    
    private boolean helper(int index, int curSum, int[] nums, boolean[] visited, int target, int round) {
        if(round == 0) {
            return true;
        }
        if(curSum == target) {
            return helper(0, 0, nums, visited, target, round - 1);
        }
        for(int i = index; i < nums.length; i++) {
            if(!visited[i] && curSum + nums[i] <= target) {
                visited[i] = true;
                if(helper(i + 1, curSum + nums[i], nums, visited, target, round)) {
                    return true;
                }
                visited[i] = false;
            }
        }
        return false;
    }
}

// Solution 4: Classic DFS + Optimization by sorting the array first
// Refer to
// https://leetcode.com/problems/partition-to-k-equal-sum-subsets/discuss/108741/Solution-with-Reference
/**
    public boolean canPartitionKSubsets(int[] A, int k) {
        if (k > A.length) return false;
        int sum = 0;
        for (int num : A) sum += num;
        if (sum % k != 0) return false;
        boolean[] visited = new boolean[A.length];
        Arrays.sort(A);
        return dfs(A, 0, A.length - 1, visited, sum / k, k);
    }

    public boolean dfs(int[] A, int sum, int st, boolean[] visited, int target, int round) {
        if (round == 0) return true;
        if (sum == target && dfs(A, 0, A.length - 1, visited, target, round - 1))
            return true;
        for (int i = st; i >= 0; --i) {
            if (!visited[i] && sum + A[i] <= target) {
                visited[i] = true;
                if (dfs(A, sum + A[i], i - 1, visited, target, round))
                    return true;
                visited[i] = false;
            }
        }
        return false;
    }
*/

// https://www.cnblogs.com/grandyang/p/7733098.html
/**
 我们也可以对上面的解法进行一些优化，比如先给数组按从大到小的顺序排个序，然后在递归函数中，我们可以直接判断，如果
 curSum大于target了，直接返回false，因为题目中限定了都是正数，并且我们也给数组排序了，后面的数字只能更大，这个剪枝
 操作大大的提高了运行速度
*/
// Runtime: 0 ms, faster than 100.00% of Java online submissions for Partition to K Equal Sum Subsets.
// Memory Usage: 36.5 MB, less than 7.06% of Java online submissions for Partition to K Equal Sum Subsets.
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if(sum % k != 0) {
            return false;
        }
        Arrays.sort(nums);
        return helper(nums.length - 1, 0, nums, new boolean[nums.length], sum / k, k);
    }
    
    private boolean helper(int index, int curSum, int[] nums, boolean[] visited, int target, int round) {
        if(round == 0) {
            return true;
        }
        if(curSum == target) {
            return helper(nums.length - 1, 0, nums, visited, target, round - 1);
        }
        for(int i = index; i >= 0; i--) {
            if(!visited[i] && curSum + nums[i] <= target) {
                visited[i] = true;
                if(helper(i - 1, curSum + nums[i], nums, visited, target, round)) {
                    return true;
                }
                visited[i] = false;
            }
        }
        return false;
    }
}
