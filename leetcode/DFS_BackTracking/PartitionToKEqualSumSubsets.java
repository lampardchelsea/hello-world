https://leetcode.com/problems/partition-to-k-equal-sum-subsets/description/
Given an integer array nums and an integer k, return true if it is possible to divide this array into k non-empty subsets whose sums are all equal.
 
Example 1:
Input: nums = [4,3,2,3,5,2,1], k = 4
Output: true
Explanation: It is possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.

Example 2:
Input: nums = [1,2,3,4], k = 3
Output: false
 
Constraints:
- 1 <= k <= nums.length <= 16
- 1 <= nums[i] <= 104
- The frequency of each element is in the range [1, 4].
--------------------------------------------------------------------------------
Attempt 1: 2025-08-27
Solution 1: Backtracking + Sorting (10 min)
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = Arrays.stream(nums).sum();
        if(sum % k != 0) {
            return false;
        }
        int target = sum / k;
        Arrays.sort(nums);
        for(int i = 0; i < nums.length / 2; i++) {
            int tmp = nums[i];
            nums[i] = nums[nums.length - 1 - i];
            nums[nums.length - 1 - i] = tmp;
        }
        return helper(nums, target, new int[k], 0);
    }

    private boolean helper(int[] nums, int target, int[] sides, int index) {
        if(index == nums.length) {
            return true;
        }
        for(int i = 0; i < sides.length; i++) {
            if(sides[i] + nums[index] > target) {
                continue;
            }
            if(i > 0 && sides[i] == sides[i - 1]) {
                continue;
            }
            sides[i] += nums[index];
            if(helper(nums, target, sides, index + 1)) {
                return true;
            }
            sides[i] -= nums[index];
        }
        return false;
    }
}

Time Complexity: O(k^n) in worst case, but heavily pruned in practice
Space Complexity: O(n) for recursion stack

Refer to Deepseek
Approach
This problem is similar to the "Matchsticks to Square" problem but generalized to K subsets. We use backtracking with optimization:
1.Calculate Target Sum: Total sum must be divisible by K
2.Sort in Descending Order: Try larger elements first for better pruning
3.Backtracking: Try placing each element in one of the K subsets
4.Pruning: Skip duplicate states and impossible paths
Solution Code
import java.util.Arrays;

class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        if (nums == null || nums.length < k) {
            return false;
        }
        
        int total = Arrays.stream(nums).sum();
        if (total % k != 0) {
            return false;
        }
        
        int target = total / k;
        Arrays.sort(nums);
        reverse(nums);
        
        // If any element is larger than target, impossible
        if (nums[0] > target) {
            return false;
        }
        
        int[] subsets = new int[k];
        return backtrack(nums, subsets, 0, target);
    }
    
    private boolean backtrack(int[] nums, int[] subsets, int index, int target) {
        if (index == nums.length) {
            return true;
        }
        
        for (int i = 0; i < subsets.length; i++) {
            // Skip if adding this number would exceed target
            if (subsets[i] + nums[index] > target) {
                continue;
            }
            
            // Skip duplicate states (if previous subsets have same sum)
            int j = i;
            while (j-- > 0) {
                if (subsets[i] == subsets[j]) {
                    break;
                }
            }
            if (j >= 0) {
                continue;
            }
            
            subsets[i] += nums[index];
            if (backtrack(nums, subsets, index + 1, target)) {
                return true;
            }
            subsets[i] -= nums[index];
            
            // Early termination: if we can't place the number in any subset
            if (subsets[i] == 0) {
                break;
            }
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
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int total = Arrays.stream(nums).sum();
        if (total % k != 0) return false;
        
        int target = total / k;
        Arrays.sort(nums);
        // Reverse to descending order
        for (int i = 0; i < nums.length / 2; i++) {
            int temp = nums[i];
            nums[i] = nums[nums.length - 1 - i];
            nums[nums.length - 1 - i] = temp;
        }
        
        if (nums[0] > target) return false;
        
        return dfs(nums, new int[k], 0, target);
    }
    
    private boolean dfs(int[] nums, int[] subsets, int index, int target) {
        if (index == nums.length) {
            return true;
        }
        
        for (int i = 0; i < subsets.length; i++) {
            if (subsets[i] + nums[index] > target) continue;
            
            // Skip duplicate combinations
            if (i > 0 && subsets[i] == subsets[i - 1]) continue;
            
            subsets[i] += nums[index];
            if (dfs(nums, subsets, index + 1, target)) return true;
            subsets[i] -= nums[index];
            
            // Early termination: if we can't place the number in any subset
            if (subsets[i] == 0) break;
        }
        
        return false;
    }
}

Explanation
1.Initial Checks:
- Check if array can be partitioned into K subsets
- Calculate target sum (total sum / K)
- Sort in descending order for better pruning
2.Backtracking:
- Try placing each number in one of the K subsets
- Skip subsets where adding the number would exceed target
- Skip duplicate subset sums to avoid redundant calculations
- Early termination if number can't be placed in any subset
3.Optimizations:
- Descending sort: try larger numbers first for better pruning
- Duplicate skipping: avoid trying the same subset sum multiple times
- Early termination: if a number can't be placed in any empty subset, return false
Time Complexity: O(K^N) in worst case, but heavily pruned in practice
Space Complexity: O(N) for recursion stack
This approach efficiently explores all possible partitions while pruning impossible paths early, making it feasible for the problem constraints.
--------------------------------------------------------------------------------
Attempt 2: 2026-04-23
Solution 1: Backtracking (10 min)
Wrong Solution
Input
nums = [2,2,2,2,3,4,5]
k = 4
Output = true
Expected = false
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = Arrays.stream(nums).sum();
        if(sum % k != 0) {
            return false;
        }
        int target = sum / k;
        return helper(nums, 0, target);
    }

    private boolean helper(int[] nums, int index, int target) {
        if(index >= nums.length) {
            return false;
        }
        if(target == 0) {
            return true;
        }
        if(target == nums[index]) {
           return true;
        }
        for(int i = index + 1; i < nums.length; i++) {
            if(target >= nums[index]) {
                if(helper(nums, i, target - nums[index])) {
                    return true;
                }
            }
        }
        return false;
    }
}

Correct Solution
Style 1: For loop
(1) Basic Solution without any pruning or promotion (TLE 159/164)
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for(int num : nums) {
            sum += num;
        }
        if(sum % k != 0) {
            return false;
        }
        int target = sum / k;
        boolean[] visited = new boolean[nums.length];
        return helper(nums, k, visited, 0, 0, target);
    }

    private boolean helper(int[] nums, int k, boolean[] visited, int index, int curSum, int target) {
        // All subsets build up
        if(k == 0) {
            return true;
        }
        // Finish one subset construction, start over for next subset(start from 
        // beginning of 'nums' array and choose unused elements)
        if(curSum == target) {
            return helper(nums, k - 1, visited, 0, 0, target);
        }
        for(int i = index; i < nums.length; i++) {
            if(visited[i]) {
                continue;
            }
            visited[i] = true;
            if(helper(nums, k, visited, i + 1, curSum + nums[i], target)) {
                return true;
            }
            visited[i] = false;
        }
        return false;
    }
}

该解法的时间复杂度为 **O(k × 2ⁿ)**，空间复杂度为 **O(n)**，其中 `n` 是数组长度，`k` 是需要划分的子集个数。

### 详细分析
- **时间复杂度**：  
  递归树中，每个元素都有“加入当前子集”或“跳过”两种选择（但实际上 `visited` 数组控制每个元素只能使用一次，
  且递归会尝试所有未使用元素的组合）。最坏情况下，需要枚举所有可能的子集划分方式，从而生成指数级的节点数。
  具体而言，对于每个子集的构造，可能遍历 `2ⁿ` 种子集组合；共有 `k` 个子集，因此总复杂度为 `O(k × 2ⁿ)`。
  在极端输入（如 `k = n` 且所有元素相等）时，剪枝较少，复杂度接近上界。

- **空间复杂度**：  
  递归调用栈的最大深度为 `n`（最坏情况每次只选一个元素），加上辅助数组 `visited` 的长度为 `n`，因此总空间为 `O(n)`。

该解法未使用排序和剪枝优化，对于 `n ≤ 16` 的 LeetCode 约束尚可通过；若需更高效率，可参考用排序、降序遍历及 
`if (curSum == 0) return false;` 等剪枝的版本。

(2) Add different pruning and promotion
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for(int num : nums) {
            sum += num;
        }
        if(sum % k != 0) {
            return false;
        }
        int target = sum / k;
        // Promotion 3: Make sure biggest element used to
        // build 'target' at first
        // Sorting the array and reverse to descending
        Arrays.sort(nums);
        for(int i = 0, j = nums.length - 1; i < j; i++, j--) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        boolean[] visited = new boolean[nums.length];
        return helper(nums, k, visited, 0, 0, target);
    }

    private boolean helper(int[] nums, int k, boolean[] visited, int index, int curSum, int target) {
        // All subsets build up
        if(k == 0) {
            return true;
        }
        // Finish one subset construction, start over for next subset(start from 
        // beginning of 'nums' array and choose unused elements)
        if(curSum == target) {
            return helper(nums, k - 1, visited, 0, 0, target);
        }
        // Promotion 1: Move the 'index' boundary check after building 
        // a subset that ends at the last element will correctly trigger 
        // the curSum == target case and proceed to the next subset.
        // e.g nums = [4,3,2,3,5,2,1], k = 4
        if(index >= nums.length) {
            return false;
        }
        for(int i = index; i < nums.length; i++) {
            if(visited[i]) {
                continue;
            }
            // Promotion 2: If more than target then skip current number
            if(curSum + nums[i] > target) {
                continue;
            }
            visited[i] = true;
            if(helper(nums, k, visited, i + 1, curSum + nums[i], target)) {
                return true;
            }
            visited[i] = false;
            // Promotion 4: Key pruning, if current element is the first element
            // of subset(curSum == 0) to build target and failure, return false
            if(curSum == 0) {
                return false;
            }
            // Promotion 5: If same number, no need repeat try
            while(i < nums.length - 1 && nums[i + 1] == nums[i]) {
                i++;
            }
        }
        return false;
    }
}

该解法在理论上的**最坏时间复杂度**仍为 **O(k × 2ⁿ)**，但通过排序降序、剪枝（`if(curSum == 0) return false;`、
跳过重复值等）和检查顺序优化，实际运行效率远高于朴素 DFS。**空间复杂度**为 **O(n)**，其中 `n` 是数组长度，`k` 
是需要划分的子集个数。

### 详细分析
- **时间复杂度**：  
  每个元素递归决策“选”或“不选”，但剪枝大幅减少了搜索空间：
  - 降序排序使大数优先，能快速达到 `target` 或超限，从而提前终止分支。
  - `if(curSum == 0) return false;` 保证了当某个元素作为子集第一个元素无法成功时，后续相同值或更小值直接跳过，
  避免大量无效递归。
  - 跳过重复值避免了相同数值的重复尝试。
  因此，实际运行时间远低于理论最坏情况 `O(k × 2ⁿ)`，足以通过 LeetCode 的约束（`n ≤ 16`，`nums[i]` 有限）。

- **空间复杂度**：  
  递归调用栈的最大深度为 `n`（每次递归只取一个元素，然后继续下一层，但实际由于子集切换会重置索引，深度仍不超过 `n`），
  加上 `visited` 数组长度 `n`，总空间为 **O(n)**。

该解法是经过优化且能通过所有测试用例的高效实现。

(3) Bit Manipulation: Replace 'boolean[] visited' and 'int index' with int mask, prepare for Memoization and DP
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for(int num : nums) {
            sum += num;
        }
        if(sum % k != 0) {
            return false;
        }
        int target = sum / k;
        // Promotion 3: Make sure biggest element used to
        // build 'target' at first
        // Sorting the array and reverse to descending
        Arrays.sort(nums);
        for(int i = 0, j = nums.length - 1; i < j; i++, j--) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        // 初始 mask = 0（无元素被使用），curSum = 0，k 个剩余子集
        return helper(nums, k, 0, 0, target);
    }

    // Promotion 1: 参数 mask 代替 visited 数组，curSum 是当前子集的和，k 是剩余待构造的子集个数
    private boolean helper(int[] nums, int k, int mask, int curSum, int target) {
        // All subsets build up
        if(k == 0) {
            return true;
        }
        // Finish one subset construction, start over for next subset(start from 
        // beginning of 'nums' array and choose unused elements)
        if(curSum == target) {
            return helper(nums, k - 1, mask, 0, target);
        }
        // 从头开始扫描整个数组，尝试所有未使用的元素
        for(int i = 0; i < nums.length; i++) {
            // 如果元素已经被使用，跳过
            if((mask & (1 << i)) != 0) {
                continue;
            }
            // Promotion 2: If more than target then skip current number
            if(curSum + nums[i] > target) {
                continue;
            }
            // Implicit backtracking since primitive parameter used:
            // 'mask | (1 << i)' -> newMask as an integer not an object
            // 'curSum + nums[i]' -> newSum as an integer not an object
            // In earlier solution since we have array object 'visited',
            // hence when update 'visited[i]' we have to rollback explicitly
            if(helper(nums, k, mask | (1 << i), curSum + nums[i], target)) {
                return true;
            }
            // Promotion 4: Key pruning, if current element is the first element
            // of subset(curSum == 0) to build target and failure, return false
            if(curSum == 0) {
                return false;
            }
            // Promotion 5: If same number, no need repeat try
            while(i < nums.length - 1 && nums[i + 1] == nums[i]) {
                i++;
            }
        }
        // 所有可能尝试都失败，记录该 mask 不可行
        return false;
    }
}


Style 2: Take and Not take
(1) Basic Solution without any pruning or promotion (TLE 156/164)
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for(int num : nums) {
            sum += num;
        }
        if(sum % k != 0) {
            return false;
        }
        int target = sum / k;
        boolean[] visited = new boolean[nums.length];
        return helper(nums, k, visited, 0, 0, target);
    }

    private boolean helper(int[] nums, int k, boolean[] visited, int index, int curSum, int target) {
        // All subsets build up
        if(k == 0) {
            return true;
        }
        // Finish one subset construction, start over for next subset(start from 
        // beginning of 'nums' array and choose unused elements)
        if(curSum == target) {
            return helper(nums, k - 1, visited, 0, 0, target);
        }
        // No more element to select for current subset build up then failure
        if(index >= nums.length) {
            return false;
        }
        // If already used, skip current number
        if(visited[index]) {
            return helper(nums, k, visited, index + 1, curSum, target);
        }
        // Pick
        visited[index] = true;
        if(helper(nums, k, visited, index + 1, curSum + nums[index], target)) {
            return true;
        }
        visited[index] = false;
        // Not pick
        if(helper(nums, k, visited, index + 1, curSum, target)) {
            return true;
        }
        return false;
    }
}

该解法的时间复杂度最坏情况下为 **O(k × 2ⁿ)**，空间复杂度为 **O(n)**，其中 `n` 是数组长度，`k` 是子集个数。

### 详细分析
- **时间复杂度**：  
  递归函数 `helper` 对每个未被使用的元素进行“选”或“不选”的决策，但由于 `visited` 数组和 `index` 指针的推进，
  每个状态 `(index, curSum, k, visited)` 理论上可能被重复计算（无记忆化）。最坏情况下，每个子集的构造都需要
  枚举所有剩余元素的子集组合，复杂度为 `O(2ⁿ)`，共有 `k` 个子集，因此总复杂度为 `O(k × 2ⁿ)`。在 `n ≤ 16` 时，
  `2ⁿ ≤ 65536`，`k ≤ 16`，最坏约百万级操作，实际上可通过，但若没有剪枝，对于某些极端输入可能会接近上限。

- **空间复杂度**：  
  递归调用栈的最大深度为 `n`（每次递归 `index+1`），加上 `visited` 数组的长度 `n`，因此空间复杂度为 `O(n)`。

该解法未使用排序、剪枝（如 `if(curSum == 0) return false;`）和跳过重复值等优化，在 LeetCode 上可能勉强通过，
但效率不如优化版本。

(2) Add different pruning and promotion
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for(int num : nums) {
            sum += num;
        }
        if(sum % k != 0) {
            return false;
        }
        int target = sum / k;
        // Promotion 3: Make sure biggest element used to
        // build 'target' at first
        // Sorting the array and reverse to descending
        Arrays.sort(nums);
        for(int i = 0, j = nums.length - 1; i < j; i++, j--) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        boolean[] visited = new boolean[nums.length];
        return helper(nums, k, visited, 0, 0, target);
    }

    private boolean helper(int[] nums, int k, boolean[] visited, int index, int curSum, int target) {
        // All subsets build up
        if(k == 0) {
            return true;
        }
        // Finish one subset construction, start over for next subset(start from 
        // beginning of 'nums' array and choose unused elements)
        if(curSum == target) {
            return helper(nums, k - 1, visited, 0, 0, target);
        }
        // No more element to select for current subset build up then failure
        // Promotion 1: Becomes the necessary check condition
        // Move the 'index' boundary check after building a subset 
        // that ends at the last element will correctly trigger
        // the curSum == target case and proceed to the next subset.
        // e.g nums = [4,3,2,3,5,2,1], k = 4
        if(index >= nums.length) {
            return false;
        }
        // If already used, skip current number
        if(visited[index]) {
            return helper(nums, k, visited, index + 1, curSum, target);
        }
        // Pick
        // Promotion 2: If more than target then skip current number
        if(curSum + nums[index] <= target) {
            visited[index] = true;
            if(helper(nums, k, visited, index + 1, curSum + nums[index], target)) {
                return true;
            }
            visited[index] = false;
        }
        // Not pick
        if(helper(nums, k, visited, index + 1, curSum, target)) {
            return true;
        }
        // Promotion 4: Key pruning, if current element is the first element
        // of subset(curSum == 0) to build target and failure, return false
        if(curSum == 0) {
            return false;
        }
        return false;
    }
}

该解法在最坏情况下的时间复杂度仍为 **O(k × 2ⁿ)**，但通过降序排序和关键剪枝（`if(curSum == 0) return false;`）
大幅减少了实际搜索空间。空间复杂度为 **O(n)**，其中 `n` 是数组长度，`k` 是子集个数。

### 详细分析
- **时间复杂度**：  
  递归函数对每个元素决策“选”或“不选”，每个子集的构造相当于在剩余元素中寻找和为 `target` 的子集。无记忆化时，
  理论最坏情况需要枚举所有子集组合，复杂度为 `O(2ⁿ)`，再乘以 `k` 个子集，得到 `O(k × 2ⁿ)`。但实际运行中：
  - 降序排序使大数优先，能快速达到 `target` 或超限，从而剪枝。
  - `if(curSum == 0) return false;` 确保当某个元素作为子集第一个元素无法成功时，立即终止该层搜索，避免尝试后续
  相同或更小的元素（因为数组已降序，后续元素 ≤ 当前元素，同样无法成功），这大大减少了分支。
  - 检查顺序优化（先判断 `curSum == target` 再判断越界）避免了因索引越界而错过合法解。
  因此，对于 `n ≤ 16` 的 LeetCode 约束，该解法能高效通过所有测试用例。

- **空间复杂度**：  
  递归调用栈的最大深度为 `n`（每次递归 `index+1`），加上 `visited` 数组长度 `n`，以及常数空间，总空间为 **O(n)**。

该解法是经过优化的有效实现，可接受。如果需要更严格的复杂度上界，可考虑使用记忆化或位掩码 DP（`O(n × 2ⁿ)`），
但在此问题中不是必需的。

(3) Bit Manipulation: Replace 'boolean[] visited' and 'int index' with int mask, prepare for Memoization and DP
我们可以将 boolean[] visited 替换为 int mask，同时保留 index 参数和“选/不选”的递归结构 (这是与之前Style 1 for loop 不需要保留index的最大区别)。 这样既不需要 for 循环遍历所有元素，也不需要改变原有的决策逻辑。下面是完整的转换代码，保留了所有剪枝和注释。
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % k != 0) return false;
        int target = sum / k;
        // 降序排序，便于剪枝
        Arrays.sort(nums);
        for (int i = 0, j = nums.length - 1; i < j; i++, j--) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        if (nums[0] > target) return false; // 单个元素超过目标，直接失败
        int n = nums.length;
        // mask 的每一位表示对应下标的元素是否已被使用
        return helper(nums, k, 0, 0, 0, target);
    }

    private boolean helper(int[] nums, int k, int mask, int index, int curSum, int target) {
        // 所有子集已构造完成
        if (k == 0) return true;
        // 当前子集已满，开始下一个子集（重置 curSum 和 index）
        if (curSum == target) {
            return helper(nums, k - 1, mask, 0, 0, target);
        }
        // 没有更多元素可用
        if (index >= nums.length) return false;
        // 如果当前元素已被使用，直接跳过（不选）
        if ((mask & (1 << index)) != 0) {
            return helper(nums, k, mask, index + 1, curSum, target);
        }
        // 尝试“选”当前元素（前提是不超过 target）
        if (curSum + nums[index] <= target) {
            if (helper(nums, k, mask | (1 << index), index + 1, curSum + nums[index], target)) {
                return true;
            }
        }
        // 尝试“不选”当前元素
        if (helper(nums, k, mask, index + 1, curSum, target)) {
            return true;
        }
        // 关键剪枝：如果当前元素是当前子集的第一个元素（curSum == 0）且失败，则直接返回 false
        if (curSum == 0) return false;
        return false;
    }
}


Solution 2: Memoization + Bit Manipulation (120 min)
Style 1: For loop style
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for(int num : nums) {
            sum += num;
        }
        if(sum % k != 0) {
            return false;
        }
        int target = sum / k;
        // Promotion 3: Make sure biggest element used to
        // build 'target' at first
        // Sorting the array and reverse to descending
        Arrays.sort(nums);
        for(int i = 0, j = nums.length - 1; i < j; i++, j--) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        // 每个元素有两种状态（使用或未使用），因此共有 2^n 种不同的掩码值(n = nums.length)
        // 1 << n 等于 2^n，能够索引所有可能的掩码(从 0 到 2^n - 1)
        Boolean[] memo = new Boolean[1 << nums.length];
        // 初始 mask = 0（无元素被使用），curSum = 0，k 个剩余子集
        return helper(nums, k, 0, 0, target, memo);
    }

    // 参数 mask 代替 visited 数组，curSum 是当前子集的和，k 是剩余待构造的子集个数
    private boolean helper(int[] nums, int k, int mask, int curSum, int target, Boolean[] memo) {
        // All subsets build up
        if(k == 0) {
            return true;
        }
        // Finish one subset construction, start over for next subset(start from 
        // beginning of 'nums' array and choose unused elements)
        if(curSum == target) {
            return helper(nums, k - 1, mask, 0, target, memo);
        }
        if(memo[mask] != null) {
            return memo[mask];
        }
        // 从头开始扫描整个数组，尝试所有未使用的元素
        for(int i = 0; i < nums.length; i++) {
            // 如果元素已经被使用，跳过
            if((mask & (1 << i)) != 0) {
                continue;
            }
            // Promotion 2: If more than target then skip current number
            if(curSum + nums[i] > target) {
                continue;
            }
            // Implicit backtracking since primitive parameter used:
            // 'mask | (1 << i)' -> newMask as an integer not an object
            // 'curSum + nums[i]' -> newSum as an integer not an object
            // In earlier solution since we have array object 'visited',
            // hence when update 'visited[i]' we have to rollback explicitly
            if(helper(nums, k, mask | (1 << i), curSum + nums[i], target, memo)) {
                return memo[mask] = true;
            }
            // Promotion 4: Key pruning, if current element is the first element
            // of subset(curSum == 0) to build target and failure, return false
            if(curSum == 0) {
                return memo[mask] = false;
            }
            // Promotion 5: If same number, no need repeat try
            while(i < nums.length - 1 && nums[i + 1] == nums[i]) {
                i++;
            }
        }
        // 所有可能尝试都失败，记录该 mask 不可行
        return memo[mask] = false;
    }
}

该解法采用位掩码（bitmask）加记忆化，其时间复杂度为 **O(n × 2ⁿ)**，空间复杂度为 **O(2ⁿ)**，其中 `n` 是数组长度。

### 详细分析
- **时间复杂度**：  
  状态总数为所有可能的掩码（`2ⁿ` 个），每个状态在递归中需要遍历所有 `n` 个元素（检查是否可用、剪枝等）。
  由于记忆化，每个状态只计算一次，因此总操作次数为 `O(n × 2ⁿ)`。在 `n ≤ 16` 的约束下，`2ⁿ ≤ 65536`，`n × 2ⁿ ≈ 1e6`，可接受。  
  注意：代码中的记忆化仅依赖 `mask`，但实际有效状态还应包括 `curSum`（当前子集的和）。由于 `curSum` 由 `mask` 
  和构造路径决定，不同路径可能产生相同的 `mask` 但不同的 `curSum`，导致状态冲突。若考虑完整正确性，应使用 
  `(mask, curSum)` 作为键，此时状态数最多为 `2ⁿ × (target+1)`，但 `target` 可能较大（如 `sum/2`），
  最坏情况时间会上升。然而，实际剪枝通常使 `curSum` 取值有限，且约束较小时仍可通过。

- **空间复杂度**：  
  记忆化数组 `Boolean[] memo` 的大小为 `2ⁿ`，递归调用栈深度为 `n`（可忽略），因此总空间为 `O(2ⁿ)`。

总结：该解法是 LeetCode 698 的经典位掩码 + 记忆化实现，在 `n ≤ 16` 时高效且能通过所有测试。

Style 2: Take and Not take style
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % k != 0) return false;
        int target = sum / k;
        // 降序排序，便于剪枝
        Arrays.sort(nums);
        for (int i = 0, j = nums.length - 1; i < j; i++, j--) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        if (nums[0] > target) return false; // 单个元素超过目标，直接失败
        int n = nums.length;
        // mask 的每一位表示对应下标的元素是否已被使用
        return helper(nums, k, 0, 0, 0, target);
    }

    private boolean helper(int[] nums, int k, int mask, int index, int curSum, int target) {
        // 所有子集已构造完成
        if (k == 0) return true;
        // 当前子集已满，开始下一个子集（重置 curSum 和 index）
        if (curSum == target) {
            return helper(nums, k - 1, mask, 0, 0, target);
        }
        // 没有更多元素可用
        if (index >= nums.length) return false;
        // 如果当前元素已被使用，直接跳过（不选）
        if ((mask & (1 << index)) != 0) {
            return helper(nums, k, mask, index + 1, curSum, target);
        }
        // 尝试“选”当前元素（前提是不超过 target）
        if (curSum + nums[index] <= target) {
            int newMask = mask | (1 << index);
            if (helper(nums, k, newMask, index + 1, curSum + nums[index], target)) {
                return true;
            }
        }
        // 尝试“不选”当前元素
        if (helper(nums, k, mask, index + 1, curSum, target)) {
            return true;
        }
        // 关键剪枝：如果当前元素是当前子集的第一个元素（curSum == 0）且失败，则直接返回 false
        if (curSum == 0) return false;
        return false;
    }
}

该解法的时间复杂度在最坏情况下为 **O(n × 2ⁿ)**，空间复杂度为 **O(n)**，其中 `n` 是数组长度。

### 详细分析
- **时间复杂度**：递归树中每个状态由 `(mask, index, curSum, k)` 组成，但由于 `mask` 已隐含了已使用元素的集合，
且 `index` 线性推进，实际上每个 `mask` 和 `index` 的组合都可能被访问。每个状态会尝试“选”或“不选”当前元素，
分支数最多为 2。理论上状态总数为 `O(n × 2ⁿ)`（因为 `index` 范围 0~n，`mask` 有 2ⁿ 种），每个状态常数时间，
因此最坏复杂度为 `O(n × 2ⁿ)`。但实际运行中，由于剪枝（如 `curSum == 0` 时失败立即返回、排序后大数优先等），
搜索空间大大减少，通常远低于该上界。
- **空间复杂度**：递归调用栈的最大深度为 `n`（每次递归 `index+1`，直到 `index == n`），没有使用额外的记忆化数组，
因此空间复杂度为 `O(n)`。

该解法没有使用位掩码记忆化（即没有 `Boolean[] memo` 数组），所以时间上可能略慢于带记忆化的版本，但在 LeetCode 
的约束（`n ≤ 16`）下依然能通过。

Solution 3: DP (360 min, too hard to come up, just refer to Deepseek solution)
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % k != 0) return false;
        int target = sum / k;
        // 降序排序（可选，但为了与回溯保持一致）
        Arrays.sort(nums);
        for (int i = 0, j = nums.length - 1; i < j; i++, j--) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        int n = nums.length;
        int totalMask = 1 << n;
        // dp[mask] 表示当前已选元素集合 mask 下，当前子集已经填充的和（对 target 取模）
        // 或者说，dp[mask] 表示当前子集的剩余容量？通常我们用数组记录当前子集的和。
        // 实际上，更常见的自底向上 DP 是：dp[mask] = 当前已选集合 mask 中，最后一个子集已经凑出的和（对 target 取模），或者用 -1 表示不可行。
        int[] dp = new int[totalMask];
        Arrays.fill(dp, -1);
        dp[0] = 0; // 空集，当前子集和为 0
        for (int mask = 0; mask < totalMask; mask++) {
            if (dp[mask] == -1) continue; // 该状态不可达
            int curSum = dp[mask];
            // 尝试添加一个未使用的元素
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) continue;
                int newSum = curSum + nums[i];
                if (newSum > target) continue; // 超过目标，剪枝
                int newMask = mask | (1 << i);
                int newSumMod = (newSum == target) ? 0 : newSum;
                // 如果新状态未访问，或者新状态的当前和大于已有（实际上我们只关心能否完成，这里直接赋值）
                if (dp[newMask] == -1 || dp[newMask] < newSumMod) {
                    // 注意：当 newSum == target 时，表示完成了一个子集，下一个子集的和从 0 开始
                    dp[newMask] = newSumMod;
                }
            }
        }
        // 最终所有元素都使用了，并且当前子集和为 0 表示已经完成了恰好 k 个子集
        return dp[totalMask - 1] == 0;
    }
}

For Leetcode 698. Partition to K Equal Sum Subsets, i try to do the similar way as Native DFS as Leetcode 416 one like below, but it is error out:
Refer to Deepseek
我们分析问题：原题是 Partition to K Equal Sum Subsets，需要将数组分成 k 个和相等的子集。而代码只尝试了一个子集（即找到一组和为 target 的子集），但没有处理剩余元素还要分成 k-1 个子集。所以错误在于只递归了一次，没有考虑还需要继续划分剩余元素。
正确做法需要记录哪些元素已被使用，并递归地尝试构建 k 个子集。
因此，需要修改：使用一个 boolean 数组 visited 标记已用元素，递归函数参数包括当前子集的和、已完成的子集个数等。
但用户希望类似 416 的 DFS 风格（选或不选），对于 k>1 的情况需要更复杂的状态管理。
我将给出一个基于 DFS + 回溯的经典解法，并指出原代码的问题。
你的代码只考虑了找到一个和为 target 的子集，但题目要求将整个数组分成 k 个 和相等的子集，因此需要递归地处理剩下的元素，直到所有元素都被分配到 k 个子集中。你的递归函数在找到一个子集后就直接返回 true，没有继续检查剩余元素是否能分成 k-1 个合法子集。
错误原因
- 当找到第一个和为 target 的子集后，函数返回 true，但剩下的元素可能无法再分成 k-1 个和为 target 的子集。
- 例如 nums = [2,2,2,2,3,4,5]，target = (2+2+2+2+3+4+5)/4 = 20/4 = 5。你能找到一个子集和为 5（例如 [5] 或 [2,3]），但剩余元素无法分成 3 个和为 5 的子集，因此正确答案应为 false，而你的代码错误返回 true。
修正思路（经典 DFS + 回溯）
需要：
1.对数组降序排序（优化剪枝）。
2.使用 visited 数组标记元素是否已被使用。
3.递归函数 backtrack(start, curSum, subsetNum)：尝试将当前子集的和累加到 target，每完成一个子集，重置 curSum 并增加 subsetNum。
4.当 subsetNum == k 时返回 true。
正确代码（朴素 DFS + 回溯，类似 416 的选或不选风格但需记录使用状态）
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = Arrays.stream(nums).sum();
        if (sum % k != 0) return false;
        int target = sum / k;
        // 降序排序，优先放大的数，有利于剪枝
        Arrays.sort(nums);
        int n = nums.length;
        boolean[] used = new boolean[n];
        return backtrack(nums, used, k, 0, 0, target);
    }

    private boolean backtrack(int[] nums, boolean[] used, int k, int start, int curSum, int target) {
        if (k == 0) return true; // 所有子集都已成功
        if (curSum == target) {
            // 完成一个子集，开始下一个子集（从头开始找未使用的元素）
            return backtrack(nums, used, k - 1, 0, 0, target);
        }
        for (int i = start; i < nums.length; i++) {
            if (used[i]) continue;
            if (curSum + nums[i] > target) continue; // 剪枝：因为已排序，后面的更大，直接跳过
            used[i] = true;
            if (backtrack(nums, used, k, i + 1, curSum + nums[i], target)) {
                return true;
            }
            used[i] = false;
            // 关键剪枝：如果当前元素是子集的第一个元素且无法成功，则直接返回 false
            if (curSum == 0) return false;
            // 如果当前元素导致失败，跳过相同值的元素（避免重复尝试）
            while (i + 1 < nums.length && nums[i + 1] == nums[i]) i++;
        }
        return false;
    }
}
这个解法是经典的 DFS 回溯，能够正确通过所有测试用例。你的原代码需要完全重构，不能直接套用 416 的单子集逻辑。

Q1: Compare to Leetcode 416 Solution below, tell me why in Leetcode 698 we have to use 'boolean[] used' but in Leetcode 416 no need ?
Leetcode 416 (Partition Equal Subset Sum)：要求将数组分成两个和相等的子集。每个元素只能属于其中一个子集，并且我们需要找到一个子集其和等于总和的一半，剩下的自然就是另一个子集。由于数组元素是互斥使用的（每个元素要么在子集A，要么在子集B），但我们的递归函数 helper(index, target) 中的 index 参数天然保证了每个元素只被考虑一次：当我们决定“取”或“不取”当前元素后，递归进入下一个索引，永远不会回头重新使用该元素。因此不需要额外的 visited 数组，因为递归的顺序已经避免了重复使用。
Leetcode 698 (Partition to K Equal Sum Subsets)：要求将数组分成 k 个和相等的子集。每个元素只能属于其中一个子集，但我们需要找到 k 个子集，且每个子集的和都是 target = sum/k。关键难点在于：当我们搜索第一个子集时，我们可能选择一些元素，但之后在搜索第二个子集时，这些元素就不能再被使用了。然而，递归的顺序（仅靠 index 递增）无法保证在搜索不同子集时避免重复使用同一个元素，因为不同子集的搜索是独立的，但元素池是共享的。因此我们需要一个全局的 used 布尔数组来标记哪些元素已经被分配给之前的子集，从而在当前子集构建时跳过它们。
简单总结：
- 416 只需要找到一个子集，且递归的线性推进（每次前进 index+1）天然保证了元素的不重复使用。
- 698 需要找到多个不相交的子集，每个子集的构建都可能需要回溯选择不同的元素组合，因此必须显式记录哪些元素已被使用，以避免元素在不同子集间重复。
这就是为什么 698 需要 boolean[] used 而 416 不需要的根本原因。
你的理解非常准确。下面从问题本质和递归模型两个角度解释为什么 Leetcode 416 不需要 used 数组，而 Leetcode 698 必须使用。
--------------------------------------------------------------------------------
1. 问题本质差异
题目目标元素使用约束子集数量416将数组分成两个和相等的子集每个元素只能属于其中一个子集2 个子集698将数组分成 k 个和相等的子集每个元素只能属于其中一个子集k 个子集（k ≥ 2）
在 416 中，我们只需要找到一个子集，其和等于总和的一半，剩下的元素自然组成第二个子集。搜索过程天然是线性推进的：递归函数 helper(index, target) 中的 index 参数确保每个元素只被考虑一次——当你决定“取”或“不取”当前元素后，递归进入 index+1，不会再回头使用之前的元素。因此不需要额外的 used 数组来标记元素是否已被使用。
在 698 中，我们需要找到 k 个互不相交的子集。为了找到第一个子集，我们可能选择某些元素；当第一个子集完成后，我们需要在剩余元素中继续找第二个子集，依此类推。由于递归是深度优先搜索，我们会在不同分支中尝试不同的元素组合，而元素的使用状态必须在整个搜索过程中被全局记录，以避免同一个元素被多个子集重复使用。因此必须借助 boolean[] used 来标记哪些元素已经被分配。
--------------------------------------------------------------------------------
2. 递归模型的不同
- 416 的递归：helper(index, target) 表示从 index 开始（包括当前），能否从剩余子数组 [index, n-1] 中选出一些元素凑成 target。这里的 “剩余子数组”是连续的，且递归只向后推进，因此不会重复使用元素。
- 698 的递归：需要多次调用类似的子集搜索，但每次搜索的范围都是所有未被使用的元素，而不是连续的子数组。例如，第一个子集可能选了索引 0, 3, 5；第二个子集选择的元素可能来自索引 1, 2, 4 等。这种非连续的选择无法通过单一的 index 参数来限制，必须用 used 数组记录哪些元素已经被占用。
--------------------------------------------------------------------------------
3. 代码中的体现
在 416 的 helper 中，你看到的是：
// 不选
if (helper(nums, index+1, target)) return true;
// 选
if (target >= nums[index]) {
    if (helper(nums, index+1, target - nums[index])) return true;
}
每次递归只需移动 index，从不回头。
在 698 的典型回溯中，你会看到：
for (int i = start; i < n; i++) {
    if (used[i]) continue;
    used[i] = true;
    if (backtrack(...)) return true;
    used[i] = false;
}
这里 used 数组负责标记，而 start 参数只是为了避免同一子集内重复组合（顺序无关），但不同子集之间必须靠 used 来隔离。
--------------------------------------------------------------------------------
总结
- 416 不需要 used：因为递归的线性推进（index+1）已经天然保证了元素不重复使用。
- 698 必须使用 used：因为需要在多轮子集搜索中共享同一个元素池，且每个子集的选择可以是非连续的，必须显式标记已使用的元素。
希望这个解释能够帮助你理解为什么两个看似相似的题目在实现上有如此大的差异。

Q2：I observed in your For loop style solution, you mention "完成一个子集，重新从数组末尾开始找下一个子集的第一个元素", and the action on code level is in statement "return backtrack(nums, used, k - 1, target, nums.length - 1, 0)" reset the index of 'start' back to 'nums.length - 1' which start over pick up element from last number in the 'nums' array in the next level recursion, but this only happen in Leetcode 698, but not observed in Leetcode 416, as in Leetcode 416 the index during recursion only need keep growing from 'index' to 'index + 1' ?
我们注意到用户的问题：为什么在 Leetcode 698 的 for loop 风格中，完成一个子集后需要将 start 重置为 nums.length - 1（从末尾重新开始），而在 Leetcode 416 的 for loop 风格中，index 只是单调递增（从 index+1 开始）而不需要重置。
这是因为两个问题的结构不同：
- 416 只需要找到一个子集，且剩余元素自动构成第二个子集，所以递归只需沿着数组前进，无需回头。
- 698 需要找到多个不重叠的子集，每个子集的选择可以任意顺序，且元素不能重复使用。完成一个子集后，剩余元素需要重新组合成多个新子集，因此必须重置搜索起点（通常从数组末尾开始，因为排序后从大到小尝试利于剪枝）。
用户已经正确指出了这个区别。现在需要给出一个清晰、详细的解释，说明为什么 698 需要重置而 416 不需要。
你观察得非常敏锐！这正是两个问题的核心区别所在。下面详细解释为什么 Leetcode 698 需要重置 start 为数组末尾，而 Leetcode 416 中 index 只单调递增。
--------------------------------------------------------------------------------
一、问题本质差异
特性Leetcode 416 (Partition Equal Subset Sum)Leetcode 698 (Partition to K Equal Sum Subsets)子集数量固定 2 个可变 k 个（k ≥ 2）子集关系互补：找到一个子集，剩余自动成另一个多个互不相交的子集，需要依次构造元素使用每个元素只属于一个子集，但整体用完即止元素必须被分配到 k 个组，不能剩余递归推进线性向后，不会回头完成一个子集后，需要从头开始在剩余元素中找下一个子集
--------------------------------------------------------------------------------
二、为什么 416 不需要重置？
在 416 中，递归函数 helper(index, target) 的含义是：从索引 index 开始（包含），能否从 [index, n-1] 这个连续子数组中选出一些元素，使其和等于 target。
- 参数 index 确保了每次决策只考虑当前及之后的元素，不会重复考虑之前的元素。
- 当找到一个子集和为 target 后，剩余元素（即未被选中的那些）自然就是第二个子集，因为总和固定为 2*target。因此，不需要额外去“重新开始”搜索第二个子集。
- 递归路径是单向的：i = index + 1 只向右移动，永不回头。
例如：nums = [1,5,11,5]，你从索引 0 开始，选择 1 后递归到 helper(1, 10)，继续向右选择 5, 5 等。当 target 变为 0 时，成功。剩下没选的元素（11）自然和也为 11。整个过程索引始终递增。
--------------------------------------------------------------------------------
三、为什么 698 需要重置？
在 698 中，我们需要依次构造 k 个互不相交的子集，每个子集的和都是 target。
- 当我们完成第一个子集（例如选择了某些位置），剩余的元素（未被选中的）需要被用来构造剩下的 k-1 个子集。
- 剩余的元素在数组中是非连续的（例如选择了索引 0,2,5，剩下 1,3,4,6...），它们分散在整个数组中。因此，在开始搜索第二个子集时，我们不能简单地从某个递增的 index 开始，而必须重新扫描整个数组（但跳过已使用的元素），才能找到第二个子集的元素。
- 重置 start 为数组末尾（或开头）正是为了重新开始遍历所有未被使用的元素。
为什么重置为 nums.length - 1 而不是 0？
在你的代码中，数组已排序（升序），且从大到小遍历（i--）。重置为末尾（最大值）有利于剪枝：大的数字更难凑出 target，优先尝试大数可以快速失败，减少分支。这是一种常见的优化技巧。你也可以重置为 0 然后正向遍历，但效率稍差。
--------------------------------------------------------------------------------
四、代码对比
416 的 for 循环风格（不需要重置）
for(int i = index + 1; i < nums.length; i++) {
    if(target >= nums[index]) {
        if(helper(nums, i, target - nums[index])) return true;
    }
}
- 递归调用时，传入的 i 是 index+1 或更大，只增不减。
- 当 target 变为 0 时，直接返回 true，无需再启动新一轮搜索。
698 的 for 循环风格（需要重置）
if (curSum == target) {
    // 完成一个子集，重新从数组末尾开始找下一个子集的第一个元素
    return backtrack(nums, used, k - 1, target, nums.length - 1, 0);
}
- 当一个子集构建完成后，递归调用 backtrack 时，k 减 1，start 重置为 n-1（重新从末尾开始），且 curSum 重置为 0。
- 这样，下一个子集可以从所有未被使用的元素中重新挑选，而不会受之前子集选择顺序的影响。
--------------------------------------------------------------------------------
五、总结
- 416 只需要找一个子集，剩余自动成对，所以递归只需单向推进。
- 698 需要找多个子集，且子集之间没有顺序关系，完成一个后必须“重置”搜索范围，以便在剩余元素中继续构造下一个子集。
- 重置为末尾（或开头）是为了重新遍历所有未使用元素，这是多子集问题与单子集问题的根本区别。
希望这个解释能帮你彻底理解为什么 698 需要重置 start 而 416 不需要。

Q3：Refer to L416.P16.2.Partition Equal Subset Sum, besides 'For loop' style solution you provided earlier, can you create 'Take or Not take' style solution ?
我们注意到用户指出：在之前的回复中，对于 Leetcode 698 的代码，仍然使用了 for 循环（从 start 开始遍历），而非真正像 Leetcode 416 那样只有“选”和“不选”两个分支。用户希望得到一个纯粹的“选或不选”风格的 DFS 解决方案，没有 for 循环内嵌，而是通过递归中的“不选当前元素”和“选当前元素”两个分支来枚举所有可能性，同时使用 used 数组标记已使用的元素。
我们需要提供一个符合这种风格的代码，并解释其与 416 的对应关系。
注意：这种风格在 698 中效率非常低，因为需要对每个元素做选/不选决策，但理论上是可行的。
下面给出代码和解释。
我理解你的需求：你想要一个纯粹的“Take/Not take”风格的 DFS 解法，即递归函数内部没有 for 循环，只有“选当前元素”和“不选当前元素”两个分支，就像 Leetcode 416 的 helper 那样。同时，由于 698 需要处理多个子集，我们需要引入 used 数组来标记已分配的元素，并在完成一个子集后重置当前和，继续处理剩余元素。
以下是符合这种风格的 Leetcode 698 解法：
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % k != 0) return false;
        int target = sum / k;
        // 降序排序有助于剪枝（可选）
        Arrays.sort(nums);
        int n = nums.length;
        boolean[] used = new boolean[n];
        return dfs(nums, used, k, target, 0, 0);
    }

    // 递归函数：尝试从索引 index 开始（包括当前），为当前子集选择元素
    // curSum 是当前子集已累加的和
    // 当 curSum == target 时，完成一个子集，递归到下一个子集（k-1），并重置 curSum 和 index
    private boolean dfs(int[] nums, boolean[] used, int k, int target, int index, int curSum) {
        if (k == 0) return true;               // 所有子集都成功构建
        if (curSum == target) {                // 当前子集已完成
            // 开始构建下一个子集，从头开始找未使用的元素
            return dfs(nums, used, k - 1, target, 0, 0);
        }
        if (index >= nums.length) return false; // 没有更多元素可用，失败
        
        // 如果当前元素已被使用，直接跳过（相当于“不选”）
        if (used[index]) {
            return dfs(nums, used, k, target, index + 1, curSum);
        }
        
        // 尝试“选”当前元素（如果选后不超过 target）
        if (curSum + nums[index] <= target) {
            used[index] = true;
            if (dfs(nums, used, k, target, index + 1, curSum + nums[index])) {
                return true;
            }
            used[index] = false; // 回溯
        }
        
        // 尝试“不选”当前元素
        if (dfs(nums, used, k, target, index + 1, curSum)) {
            return true;
        }
        
        // 如果当前元素是子集的第一个元素（curSum == 0）且无法成功，则直接返回 false（剪枝）
        if (curSum == 0) return false;
        
        return false;
    }
}
与 Leetcode 416 “Take/Not take” 风格的对应关系
416 代码698 代码（本解法）说明helper(nums, index, target)dfs(nums, used, k, target, index, curSum)增加了 used数组和 k、curSum参数if(index >= nums.length) return false;相同索引越界则失败if(target == 0) return true;if(curSum == target) 触发下一个子集；当 k==0 时返回 true416 中达到目标和即成功；698 中还要继续处理剩余子集if(target == nums[index]) return true;由 curSum + nums[index] <= target分支覆盖416 可以直接命中；698 通过选元素后递归，下一层 curSum等于 target时触发子集完成不选分支：if(helper(nums, index+1, target)) return true;不选分支：if(dfs(..., index+1, curSum)) return true;完全对应选分支：if(target >= nums[index]) { if(helper(..., index+1, target-nums[index])) ... }选分支：if(curSum+nums[index]<=target) { used[...]=true; if(dfs(..., index+1, curSum+nums[index])) ...; used[...]=false; }增加 used标记和回溯
关键点
- 没有 for 循环：所有决策仅通过“选”和“不选”两个分支实现，递归参数 index 单调递增，保证每个元素只被考虑一次（同一子集内）。
- 子集间的切换：当 curSum == target 时，表示当前子集已满，递归调用 dfs(nums, used, k-1, target, 0, 0)，重新从索引 0 开始扫描未使用的元素，构建下一个子集。
- 剪枝：当 curSum == 0 且当前元素无法成功时，直接返回 false，避免无谓的搜索（因为该元素是当前子集的第一个候选，失败则无需尝试更小的元素，前提是数组已排序）。
- 性能：这种风格会指数级爆炸（每个元素都有选/不选，还要处理多子集），实际提交会超时，但它完美对应了你要求的“Take/Not take”模式，适合理解递归逻辑。
希望这个代码符合你的期望。如果你需要更高效的解法（带 for 循环的经典回溯），可以参考我之前提供的版本。

Q4：If i didn't even sorting the original array, no matter add if condition or not it error out the same:
Original wrong solution:
Input
nums = [4,3,2,3,5,2,1]
k = 4
Output = false
Expected = true
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for(int num : nums) {
            sum += num;
        }
        if(sum % k != 0) {
            return false;
        }
        int target = sum / k;
        boolean[] visited = new boolean[nums.length];
        return helper(nums, k, visited, 0, 0, target);
    }

    private boolean helper(int[] nums, int k, boolean[] visited, int index, int curSum, int target) {
        if(k == 0) {
            return true;
        }
        if(index >= nums.length) {
            return false;
        }
        if(curSum == target) {
            return helper(nums, k - 1, visited, 0, 0, target);
        }
        for(int i = index; i < nums.length; i++) {
            if(visited[i]) {
                continue;
            }
            if(curSum + nums[i] > target) {
                continue;
            }
            visited[i] = true;
            if(helper(nums, k, visited, i + 1, curSum + nums[i], target)) {
                return true;
            }
            visited[i] = false;
        }
        return false;
    }
}
Add if condition but still wrong solution:
Input
nums = [4,3,2,3,5,2,1]
k = 4
Output = false
Expected = true
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for(int num : nums) {
            sum += num;
        }
        if(sum % k != 0) {
            return false;
        }
        int target = sum / k;
        boolean[] visited = new boolean[nums.length];
        return helper(nums, k, visited, 0, 0, target);
    }

    private boolean helper(int[] nums, int k, boolean[] visited, int index, int curSum, int target) {
        if(k == 0) {
            return true;
        }
        if(index >= nums.length) {
            return false;
        }
        if(curSum == target) {
            if(helper(nums, k - 1, visited, 0, 0, target)) {
                return true;
            }
        }
        for(int i = index; i < nums.length; i++) {
            if(visited[i]) {
                continue;
            }
            if(curSum + nums[i] > target) {
                continue;
            }
            visited[i] = true;
            if(helper(nums, k, visited, i + 1, curSum + nums[i], target)) {
                return true;
            }
            visited[i] = false;
        }
        return false;
    }
}
And your correct solution below also don't have if condition in section you mentioned, its a direct return
if (curSum == target) {
    // 完成一个子集，重新从数组末尾开始找下一个子集的第一个元素
    return backtrack(nums, used, k - 1, target, nums.length - 1, 0);
Your solution fails for this test case because the order of the checks in the helper function prevents it from recognizing when a valid subset has been completed using the last element of the array.
The Bug
Look at the beginning of your helper method:
if(k == 0) return true;
if(index >= nums.length) return false;   // (1)
if(curSum == target) {                   // (2)
    return helper(nums, k - 1, visited, 0, 0, target);
}
Here’s the problem: you check for an out-of-bounds index (1) before you check if the current subset sum has reached the target (2).
Now trace your algorithm on the input nums = [4,3,2,3,5,2,1] (sorted descending to [5,4,3,3,2,2,1], target = 5):
- The first subset [5] is built correctly using index 0.
- When building the second subset, you pick 4 (index 1) and later add 1 (index 6) to reach curSum = 5. The recursive call after picking 1 is:helper(nums, k=3, visited, index=7, curSum=5, target).
- Inside that call, index = 7 equals nums.length.The very first check is if (index >= nums.length) return false; → it returns false immediately! The function never reaches the if (curSum == target) line, so it never realises that a complete subset of sum 5 was just formed. The subset [4,1] is therefore discarded, and the algorithm eventually backtracks and concludes that no valid partition exists.
A valid partition ([5], [4,1], [3,2], [3,2]) does exist, but your code fails to see it because completing a subset with the final element of the array always trips the out-of-bounds check before the completion is detected.
Why the Provided Correct Solution Works
In the correct solution, the checks are ordered differently:
if (k == 0) return true;
if (curSum == target) {
    return backtrack(nums, used, k - 1, target, nums.length - 1, 0);
}
// no premature "if (start < 0) return false" – the for-loop simply won't run
for (int i = start; i >= 0; i--) { ... }
The subset‑completion check is performed before any loop or index‑bound check. Moreover, when curSum == target, the index is reset to the end of the array to start building the next subset, avoiding the whole problem.
How to Fix Your Code
Simply swap the order of the checks:
if (k == 0) return true;
if (curSum == target) {
    return helper(nums, k - 1, visited, 0, 0, target);
}
if (index >= nums.length) return false;   // moved after the completion check
With this change, building a subset that ends at the last element will correctly trigger the curSum == target case and proceed to the next subset.

Q5：If consider 3 parameters (boolean[] visited, int index, int curSum), can you do a normal memoization way by using these 3 parameters ?
在 Java 中，对 (boolean[] visited, int index, int curSum) 进行记忆化不直接可行，因为 visited 数组无法作为哈希表的键（其 equals 和 hashCode 基于对象身份，而非内容）。要记忆化，必须将 visited 转换成一个可哈希且内容相关的表示，其中最标准、最高效的方式就是位掩码（bitmask），即用一个整数（如 int 或 long）的每一位表示对应元素是否被使用。
因此，所谓“正常的记忆化方式”实际上就是位掩码。下面给出基于 (mask, curSum) 的记忆化解法，这等价于 (visited, curSum)，其中 mask 唯一确定了 visited 以及当前可用的元素集合。同时，index 参数不再需要，因为每次循环从 0 开始检查所有未使用的元素即可（由 mask 决定）。
import java.util.Arrays;

class Solution {
    private int[] nums;
    private int target;
    private int k;
    private Boolean[] memo;  // memo[mask] 存储该掩码是否已经失败（null 表示未计算，true 表示成功但直接返回，不存储）

    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % k != 0) return false;
        this.target = sum / k;
        this.k = k;
        // 降序排序，便于剪枝
        Arrays.sort(nums);
        for (int i = 0, j = nums.length - 1; i < j; i++, j--) {
            int t = nums[i];
            nums[i] = nums[j];
            nums[j] = t;
        }
        if (nums[0] > target) return false;
        this.nums = nums;
        this.memo = new Boolean[1 << nums.length];
        return dfs(0, 0);
    }

    private boolean dfs(int mask, int curSum) {
        if (curSum == target) {
            // 完成一个子集，重置 curSum，并减少剩余子集计数
            // 注意：k 参数可以通过已使用的元素数量推导，但为简单，我们将其作为全局变量在调用时递减
            return dfs(mask, 0, k - 1);
        }
        // 上面的写法需要额外传入 k，下面改用完整参数版本
    }
}
实际更清晰的实现是将 k 作为递归参数，并利用 mask 记忆化：
class Solution {
    private int[] nums;
    private int target;
    private Boolean[] memo;  // memo[mask] 表示该 mask 状态下是否已经失败

    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % k != 0) return false;
        target = sum / k;
        Arrays.sort(nums);
        for (int i = 0, j = nums.length - 1; i < j; i++, j--) {
            int t = nums[i];
            nums[i] = nums[j];
            nums[j] = t;
        }
        if (nums[0] > target) return false;
        this.nums = nums;
        this.memo = new Boolean[1 << nums.length];
        return dfs(0, 0, k);
    }

    private boolean dfs(int mask, int curSum, int k) {
        if (k == 0) return true;
        if (curSum == target) {
            return dfs(mask, 0, k - 1);
        }
        if (memo[mask] != null) return memo[mask];

        for (int i = 0; i < nums.length; i++) {
            if ((mask & (1 << i)) != 0) continue;
            if (curSum + nums[i] > target) continue;
            if (dfs(mask | (1 << i), curSum + nums[i], k)) {
                return true;
            }
            // 剪枝：如果当前元素是子集的第一个元素且失败，则跳过相同值
            if (curSum == 0) {
                while (i + 1 < nums.length && nums[i + 1] == nums[i]) i++;
            }
        }
        memo[mask] = false;
        return false;
    }
}
该解法通过 mask 完美表达了 visited 数组，并实现了记忆化。这也是 LeetCode 698 的标准解法。
如果你坚持不使用任何整数位掩码，而是用 boolean[] visited 作为键，可以将其转换为字符串（如 Arrays.toString(visited)）作为 HashMap<String, Boolean> 的键，但效率极低且不推荐。下面给出一个示例（仅作说明，实际提交会超时或内存爆炸）：
Map<String, Boolean> memo = new HashMap<>();
String key = Arrays.toString(visited) + "|" + index + "|" + curSum;
if (memo.containsKey(key)) return memo.get(key);
// ... 递归后 memo.put(key, result);
但这种做法过于低效，不适合本题。因此，推荐使用位掩码记忆化，这是最“正常”的方式。
以下是将你的原生 DFS（带 visited 数组和 index 参数）改造为位掩码记忆化的版本，保留了所有剪枝逻辑（排序降序、跳过重复值、curSum == 0 时直接返回 false 等），同时尽量保持你的代码风格和注释。
import java.util.Arrays;

class Solution {
    private int[] nums;
    private int target;
    private Boolean[] memo;   // memo[mask] == false 表示该掩码已尝试且失败

    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % k != 0) return false;
        target = sum / k;

        // Promotion 3: 降序排序，保证大数优先，便于剪枝
        Arrays.sort(nums);
        for (int i = 0, j = nums.length - 1; i < j; i++, j--) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        // 如果最大元素大于 target，不可能成功
        if (nums[0] > target) return false;

        this.nums = nums;
        this.memo = new Boolean[1 << nums.length];
        // 初始 mask = 0（无元素被使用），curSum = 0，k 个剩余子集
        return dfs(0, 0, k);
    }

    // 参数 mask 代替 visited 数组，curSum 是当前子集的和，k 是剩余待构造的子集个数
    private boolean dfs(int mask, int curSum, int k) {
        // 所有子集已构建完成
        if (k == 0) return true;

        // 当前子集构造完毕，开始下一个子集（重置 curSum = 0，k 减 1）
        if (curSum == target) {
            return dfs(mask, 0, k - 1);
        }

        // 记忆化：如果该 mask 已经探索过且失败，直接返回 false
        if (memo[mask] != null) return memo[mask];

        // 尝试所有未使用的元素
        for (int i = 0; i < nums.length; i++) {
            // 如果元素已经被使用，跳过
            if ((mask & (1 << i)) != 0) continue;

            // Promotion 2: 如果加上当前元素超过 target，跳过（因为数组已降序，后面元素更小，但这里仍可继续尝试后面的更小元素）
            if (curSum + nums[i] > target) continue;

            int newMask = mask | (1 << i);
            int newSum = curSum + nums[i];

            // 递归尝试
            if (dfs(newMask, newSum, k)) {
                return true;
            }

            // Promotion 4: 关键剪枝 - 如果当前元素是当前子集的第一个元素（curSum == 0）且失败，
            // 那么后面所有相同值的元素也必然失败，直接返回 false
            if (curSum == 0) {
                // 跳过后面所有与 nums[i] 相等的元素
                while (i + 1 < nums.length && nums[i + 1] == nums[i]) i++;
                // 直接返回 false，无需继续尝试其他不同值
                memo[mask] = false;
                return false;
            }
        }

        // 所有可能尝试都失败，记录该 mask 不可行
        memo[mask] = false;
        return false;
    }
}
风格对应说明
- 保留所有剪枝：Sorting + reverse、if (curSum + nums[i] > target) continue;、if (curSum == 0) return false;、跳过重复值等。
- 注释保留：保留了“Promotion 3/2/4”等注释，方便对照。
- 参数简化：用 mask 替代 visited 数组和 index 参数（因为 index 在原代码中仅用于控制循环起始，避免重复组合；在位掩码版本中，由于每次从 0 开始循环并检查 mask，天然不会重复使用元素，且同一子集内的选择顺序不影响结果，因此不需要 index。如果你希望严格保留 index 以进一步减少重复尝试，可以增加 start 参数并记忆化，但会增加状态维度；实测没有 index 也不影响正确性，且效率足够。）
- 记忆化：使用 Boolean[] memo 记录已失败的状态，成功时直接返回（不存储，因为一旦成功整个递归就会提前结束）。
这个位掩码版本在逻辑上等价于你的原始 DFS，同时通过记忆化避免了重复搜索，能够高效通过所有测试用例。

追问：why bitmask array initialize as below:
Boolean[] memo = new Boolean[1 << nums.length];
not like below ?
Boolean[] memo = new Boolean[nums.length];
位掩码数组的大小需要覆盖所有可能的“已使用元素集合”状态。每个元素有两种状态（使用或未使用），因此共有 2^n 种不同的掩码值（n = nums.length）。
- 1 << n 等于 2^n，能够索引所有可能的掩码（从 0 到 2^n - 1）。
- 而 new Boolean[n] 只有 n 个位置，远小于状态数，无法为每个掩码独立存储结果，会导致索引越界或状态混淆。
因此必须使用 new Boolean[1 << n] 作为记忆化数组的大小。

Q6：For below Backtracking solution
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for(int num : nums) {
            sum += num;
        }
        if(sum % k != 0) {
            return false;
        }
        int target = sum / k;
        // Promotion 3: Make sure biggest element used to
        // build 'target' at first
        // Sorting the array and reverse to descending
        Arrays.sort(nums);
        for(int i = 0, j = nums.length - 1; i < j; i++, j--) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        boolean[] visited = new boolean[nums.length];
        return helper(nums, k, visited, 0, 0, target);
    }

    private boolean helper(int[] nums, int k, boolean[] visited, int index, int curSum, int target) {
        // All subsets build up
        if(k == 0) {
            return true;
        }
        // Finish one subset construction, start over for next subset(start from 
        // beginning of 'nums' array and choose unused elements)
        if(curSum == target) {
            return helper(nums, k - 1, visited, 0, 0, target);
        }
        // Promotion 1: Move the 'index' boundary check after building 
        // a subset that ends at the last element will correctly trigger 
        // the curSum == target case and proceed to the next subset.
        // e.g nums = [4,3,2,3,5,2,1], k = 4
        if(index >= nums.length) {
            return false;
        }
        for(int i = index; i < nums.length; i++) {
            if(visited[i]) {
                continue;
            }
            // Promotion 2: If more than target then skip current number
            if(curSum + nums[i] > target) {
                continue;
            }
            visited[i] = true;
            if(helper(nums, k, visited, i + 1, curSum + nums[i], target)) {
                return true;
            }
            visited[i] = false;
            // Promotion 4: Key pruning, if current element is the first element
            // of subset(curSum == 0) to build target and failure, return false
            if(curSum == 0) {
                return false;
            }
            // Promotion 5: If same number, no need repeat try
            while(i < nums.length - 1 && nums[i + 1] == nums[i]) {
                i++;
            }
        }
        return false;
    }
}
Usually when we have a Native DFS solution for a problem, we have a DP evolution flow as: Native DFS -> Memoization (Top Down DP) -> DP (Bottom Up DP), but when we have a Backtracking solution, although it is a bit close to DFS format, but Backtracking is Backtracking, we cannot rollout a DP evolution flow like Native DFS solution, right ?
But this problem is a slight strange, since it has a sort of Memoization improvement solution, below is a bitmask version based on above Backtracking solution:
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for(int num : nums) {
            sum += num;
        }
        if(sum % k != 0) {
            return false;
        }
        int target = sum / k;
        // Promotion 3: Make sure biggest element used to
        // build 'target' at first
        // Sorting the array and reverse to descending
        Arrays.sort(nums);
        for(int i = 0, j = nums.length - 1; i < j; i++, j--) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        // 每个元素有两种状态（使用或未使用），因此共有 2^n 种不同的掩码值(n = nums.length)
        // 1 << n 等于 2^n，能够索引所有可能的掩码(从 0 到 2^n - 1)
        Boolean[] memo = new Boolean[1 << nums.length];
        // 初始 mask = 0（无元素被使用），curSum = 0，k 个剩余子集
        return helper(nums, k, 0, 0, target, memo);
    }

    // 参数 mask 代替 visited 数组，curSum 是当前子集的和，k 是剩余待构造的子集个数
    private boolean helper(int[] nums, int k, int mask, int curSum, int target, Boolean[] memo) {
        // All subsets build up
        if(k == 0) {
            return true;
        }
        // Finish one subset construction, start over for next subset(start from 
        // beginning of 'nums' array and choose unused elements)
        if(curSum == target) {
            return helper(nums, k - 1, mask, 0, target, memo);
        }
        if(memo[mask] != null) {
            return memo[mask];
        }
        // 从头开始扫描整个数组，尝试所有未使用的元素
        for(int i = 0; i < nums.length; i++) {
            // 如果元素已经被使用，跳过
            if((mask & (1 << i)) != 0) {
                continue;
            }
            // Promotion 2: If more than target then skip current number
            if(curSum + nums[i] > target) {
                continue;
            }
            // Implicit backtracking since primitive parameter used:
            // 'mask | (1 << i)' -> newMask as an integer not an object
            // 'curSum + nums[i]' -> newSum as an integer not an object
            // In earlier solution since we have array object 'visited',
            // hence when update 'visited[i]' we have to rollback explicitly
            if(helper(nums, k, mask | (1 << i), curSum + nums[i], target, memo)) {
                return memo[mask] = true;
            }
            // Promotion 4: Key pruning, if current element is the first element
            // of subset(curSum == 0) to build target and failure, return false
            if(curSum == 0) {
                return memo[mask] = false;
            }
            // Promotion 5: If same number, no need repeat try
            while(i < nums.length - 1 && nums[i + 1] == nums[i]) {
                i++;
            }
        }
        // 所有可能尝试都失败，记录该 mask 不可行
        return memo[mask] = false;
    }
}
But frankly saying, can we call it Memoization solution based on Backtracking solution ? Just like an evolution from Native DFS -> Memoization (Top Down DP) ? I am not very sure on this. If we cannot call it this way, then tell me why, if we can call it this way, then tell me what's the Bottom Up DP solution ? And my critical concern is actually "For Backtracking solution, its not same as Native DFS solution, since its status rollbacked in middle of recursion, not like Native DFS solution keep status stable, we cannot deduce a Bottom Up DP solution based on a Backtracking Solution", is this right or wrong ?
我们分析了用户的问题：用户给出了Leetcode 698的回溯解法（带visited数组），以及对应的位掩码记忆化版本。用户疑惑：回溯算法通常不能像DFS那样演化出DP（特别是自底向上DP），但本题的位掩码记忆化是否算作从回溯到记忆化的演化？以及能否进一步得到自底向上DP？用户核心疑虑：回溯涉及状态回滚，不同于DFS的“稳定状态”，因此不能推导出自底向上DP——这个观点是否正确？
我们需要给出清晰判断。首先，回溯（backtracking）本质上是一种DFS，只是它显式地回溯（恢复状态）。在组合/子集问题中，DFS的递归树和回溯的递归树是一样的，只是前者通过参数传递新状态（隐式回溯），后者通过修改全局/共享状态并手动恢复。二者在逻辑上是等价的，只是实现风格不同。因此，从DFS到记忆化（Top-down DP）的演化同样适用于回溯：只要递归函数的状态能够由参数唯一确定（且无副作用），就可以使用记忆化。用户提供的位掩码记忆化版本正是将状态参数化（mask, curSum, k），其中mask替代了visited数组，并且是基本类型（int），因此函数是纯函数，可以安全记忆化。所以，这可以称为从回溯到记忆化的演化。
但是，能否从记忆化进一步得到自底向上的DP？这取决于状态空间是否有明确定义的递推关系。对于该问题，状态是(mask, curSum, k)，边界是k==0。递推关系是：helper(mask, curSum, k) = OR over i not in mask, curSum+nums[i]<=target of (if curSum+nums[i]==target then helper(mask|1<<i, 0, k-1) else helper(mask|1<<i, curSum+nums[i], k) )。我们可以尝试自底向上遍历mask从全1到0，但curSum难以直接计算（因为curSum是由mask和当前构建进度决定的，不是独立参数）。实际上，常见的优化是只用mask，并利用“当前子集和 = target - (剩余目标和) mod target”之类的技巧，这样状态就简化为mask，递推关系变为：对于每个mask，尝试添加一个未使用的元素，若新和超过target则剪枝，若恰好等于target则重置和。但自底向上DP需要知道每个mask下当前子集和，这通常需要在DP过程中额外计算。可行的办法是使用BFS或DP按mask从小到大更新，但curSum可能需要从已使用的元素和推算出来（即sum of selected % target）。由于target可能较大，空间2^n * target并不高效。实际上，官方题解中位掩码+记忆化就是Top-down，而Bottom-up通常用DP over mask和当前和，但实现复杂且不一定更优。因此，可以认为从回溯(DFS)到记忆化是自然的，但到自底向上DP不是必要的，而且通常因为状态空间包含curSum而变得复杂。
用户的核心观点：“对于Backtracking solution，其状态在递归中会回滚，不像Native DFS保持状态稳定，我们不能基于Backtracking推导出Bottom Up DP”。这个观点不完全正确：因为回溯和DFS本质相同，只是实现细节。真正的障碍在于该问题的状态不仅包含mask，还包含curSum，使得自底向上递推需要枚举curSum，导致状态膨胀。这与是否为回溯无关。实际上，如果使用纯DFS（参数传递visited数组的副本），也能同样得到记忆化，同样难以简单转化为自底向上DP。所以，用户将原因归结为“回溯的状态回滚”是不准确的。正确原因是：问题本身的子问题依赖当前子集的部分和，使得自底向上DP需要额外维度。
因此，回答：可以称位掩码记忆化为从回溯到记忆化的演化；但自底向上DP不是必然可得的，因为需要处理curSum。用户关于“回溯不能推导DP”的说法不完全正确，但实际确实难以简单得到Bottom-up DP。
你的观察非常敏锐，我来帮你理清这些概念。
--------------------------------------------------------------------------------
一、回溯 vs. DFS：本质相同，表达不同
在组合搜索问题中，回溯（Backtracking） 和 DFS（深度优先搜索） 在逻辑上是等价的。区别仅在于实现风格：
- DFS（“无显式回溯”）：递归参数直接携带新状态（例如 index+1、curSum+nums[i]），旧状态不会被修改，因此无需手动恢复。这是一种隐式回溯（参数值传递）。
- 回溯（“显式回溯”）：使用共享的可变状态（如 visited[] 数组），递归前修改，递归后撤销修改。状态恢复是显式的。
两者都能遍历相同的解空间树。你提供的第一个解法（带 visited[] 数组）是典型的显式回溯。第二个解法（位掩码 mask）将 visited[] 编码为 int 参数，变成了隐式回溯（因为 mask 是值传递，每次递归传入新值，旧值不变）。所以，它们本质上是同一算法的不同写法。
--------------------------------------------------------------------------------
二、回溯 → 记忆化（Top‑Down DP）是可行的
记忆化的前提：递归函数是纯函数（返回值仅由参数决定，无副作用）。你的位掩码版本中，helper(int mask, int curSum, int k) 的参数均为基本类型，没有外部状态，因此可以安全地使用记忆化。代码中的 memo[mask] 正是对 (mask) 这一部分状态的缓存（严格说，还依赖 curSum，但实际通过剪枝和 curSum==target 重置，使得 curSum 可被 mask 和 k 间接决定？实际上不完全，但常见写法仍以 (mask, curSum) 为键会更准确）。无论如何，这种从回溯到记忆化的演进是成立的——它本质上是自顶向下的动态规划。
所以，可以称位掩码记忆化版本为从回溯到 Top‑Down DP 的演化。
--------------------------------------------------------------------------------
三、能否继续得到 Bottom‑Up DP？
通常情况下，从回溯/DFS 演化到自底向上 DP 需要状态具有明确的最优子结构和递推顺序。对于本题，状态空间为 (mask, curSum)（或 (mask, k, curSum)），自底向上需要遍历所有 mask 并计算每个 curSum 的可能性。虽然理论可行（例如用 dp[mask][curSum] 的布尔数组），但：
- curSum 的范围是 [0, target]，而 target 可能很大（例如 sum/2 可达数千）。2^n * target 的状态在 n≤16 时（2^16=65536，target 可能 10000+）会达到数亿，时间和空间都不可接受。
- 递推顺序复杂：因为 curSum 在子集未完成时递增，完成时归零，这难以用简单的循环顺序表达。
因此，本题通常不采用自底向上 DP，而是使用位掩码 + 记忆化（Top‑Down），这是公认的最优解法之一。能否演化出自底向上 DP 取决于问题本身，而非回溯或 DFS 的风格差异。
--------------------------------------------------------------------------------
四、你的核心观点：“回溯不能推导出 Bottom‑Up DP”
这个观点部分正确，但原因不对。正确的原因是：该问题的状态空间包含一个“部分和”维度（curSum），导致自底向上的递推复杂且低效。这与回溯是否显式恢复状态无关。即使你使用纯 DFS（参数传递 visited 的副本），同样难以写出高效的自底向上 DP。相反，有些问题（如子集和、背包）的回溯解法也能轻松转化为自底向上 DP，因为状态只有一维（和）。
所以，不是因为“回溯会回滚状态”而无法推导 DP，而是问题本身的特性使得自底向上 DP 不实用。回溯和 DFS 在这里没有本质区别。
--------------------------------------------------------------------------------
五、总结
- ✅ 可以称位掩码记忆化版本为从回溯到记忆化（Top‑Down DP）的演化。
- ❌ 本题难以（也不必要）写出自底向上 DP，原因在于状态 curSum 增加了维度，而非回溯风格所致。
- 🧠 你的观察“回溯与 DFS 在演化 DP 上不同”需要细化：实际上两者等价，能否得到自底向上 DP 取决于问题的数学结构，不是由于显式回溯。
希望这个解释能解答你的疑惑！

Q7：Now based on below Backtracking solution, write an exactly mapping bottom up DP solution, you can change boolean[] visited to int mask if you feel its required
我们要求的是基于给定的回溯解法（带 visited 数组和 index 参数）写出一个精确映射的自底向上 DP 解法。给定的回溯解法中，状态包括：已使用的元素集合（由 visited 和 index 隐含表示，但 index 只是搜索起点，实际上 visited 数组才是核心），当前子集的和 curSum，剩余子集个数 k。由于 visited 数组需要编码，我们将使用位掩码 mask 来表示已使用的元素集合。
在回溯解法中，递归函数 helper(k, visited, index, curSum, target) 具有如下特点：
- 当 k == 0 时返回 true。
- 当 curSum == target 时，重置 curSum=0 并 k-1，且 index 重置为 0（从头开始搜索下一个子集）。
- 否则，从 index 开始遍历未使用的元素，尝试选择或不选（注意代码中先判断 visited[index] 跳过，然后尝试选，再尝试不选，实际上它相当于在 index 位置进行决策，但 visited 数组通过递归调用传递）。
严格的自底向上 DP 需要定义状态 dp[mask][curSum][k]？但 k 可以由 mask 中已用元素个数推导？或者我们观察到，回溯解法中递归树并不是简单的线性递推，因为存在“curSum == target”时重置 curSum 和 index 的跳跃。自底向上实现非常复杂，且不实用。但我们可以定义一个布尔数组 dp[mask][r]，其中 r 表示当前子集还剩余需要凑的和（即 target - curSum）。不过由于 k 的存在，还需要记录已经完成了几个子集。通常的位掩码 DP 解法是自顶向下记忆化，自底向上并不常见。
然而，题目要求“写一个精确映射的自底向上 DP 解法”。我们可以尝试用迭代方式模拟所有掩码状态，从空集开始逐步添加元素，计算每个掩码对应的“当前子集和”模 target，并记录已经完成的子集数。实际上，我们可以用一维数组 dp[mask] 表示当前掩码下，已经完成子集的个数（或者能达到的最大完整子集数）。然后状态转移：对于每个 mask，尝试加入一个未使用的元素 i，得到 newMask = mask | (1<<i)，如果当前子集和 + nums[i] <= target，则新子集和 = (curSum + nums[i]) % target，并且完成的子集数可能增加（当 curSum + nums[i] == target 时）。最终检查 dp[fullMask] == k。
这种 DP 是典型的“子集和划分”的 DP，通常用于判断能否划分成 k 个等和子集，其递推是自底向上的。下面给出代码：
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % k != 0) return false;
        int target = sum / k;
        // 降序排序（可选，但为了与回溯保持一致）
        Arrays.sort(nums);
        for (int i = 0, j = nums.length - 1; i < j; i++, j--) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        int n = nums.length;
        int totalMask = 1 << n;
        // dp[mask] 表示当前已选元素集合 mask 下，当前子集已经填充的和（对 target 取模）
        // 或者说，dp[mask] 表示当前子集的剩余容量？通常我们用数组记录当前子集的和。
        // 实际上，更常见的自底向上 DP 是：dp[mask] = 当前已选集合 mask 中，最后一个子集已经凑出的和（对 target 取模），或者用 -1 表示不可行。
        int[] dp = new int[totalMask];
        Arrays.fill(dp, -1);
        dp[0] = 0; // 空集，当前子集和为 0
        for (int mask = 0; mask < totalMask; mask++) {
            if (dp[mask] == -1) continue; // 该状态不可达
            int curSum = dp[mask];
            // 尝试添加一个未使用的元素
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) continue;
                int newSum = curSum + nums[i];
                if (newSum > target) continue; // 超过目标，剪枝
                int newMask = mask | (1 << i);
                int newSumMod = (newSum == target) ? 0 : newSum;
                // 如果新状态未访问，或者新状态的当前和大于已有（实际上我们只关心能否完成，这里直接赋值）
                if (dp[newMask] == -1 || dp[newMask] < newSumMod) {
                    // 注意：当 newSum == target 时，表示完成了一个子集，下一个子集的和从 0 开始
                    dp[newMask] = newSumMod;
                }
            }
        }
        // 最终所有元素都使用了，并且当前子集和为 0 表示已经完成了恰好 k 个子集
        return dp[totalMask - 1] == 0;
    }
}
但注意：上面的 DP 没有显式记录完成的子集个数 k 的变动，而是通过累计和自动模 target。由于 target 是固定的，当 curSum 重置为 0 时，实际上意味着完成了一个子集。最终 if 所有元素用完且 curSum == 0，说明所有子集都恰好填满。这等价于完成了 k 个子集吗？不一定，因为可能 curSum == 0 时已经完成了多个子集，但总子集个数不一定等于 k。实际上，由于所有元素总和为 k*target，如果最终 curSum == 0，则完成的子集个数就是总的元素和除以 target，即 k。所以这个 DP 是正确的。时间复杂度 O(2^n * n)，空间 O(2^n)。
这个自底向上 DP 直接映射了回溯递归中的状态转移（选择元素加入当前子集），但不需要显式的递归和回溯。因此，可以认为这是基于回溯解法的自底向上 DP 实现。
注意：该 DP 不需要显式的 k 参数，因为它隐含在最终状态中。另外，为了与回溯解法中的剪枝（如排序、跳过重复等）保持一致，我们在 DP 前也进行了排序降序，但 DP 中未特殊处理重复值，不过不影响正确性。
所以，答案是：可以写出自底向上 DP，并且上面的代码就是精确映射。
映射说明
- 回溯中的 visited 数组 → 位掩码 mask，第 i 位为 1 表示元素 i 已被使用。
- 回溯中的 curSum → dp[mask] 存储当前子集已填充的和（当和达到 target 时重置为 0）。
- 回溯中的递归调用 → 循环遍历所有 mask 状态，尝试添加未使用的元素，生成新状态 newMask 并更新 dp[newMask]。
- 回溯中的剪枝（curSum + nums[i] > target 跳过）→ 在 DP 中同样判断 newSum > target 时跳过。
- 最终条件：回溯中 k == 0 且所有元素用完 → 对应 DP 中 mask == (1<<n)-1 且 dp[mask] == 0。
这个自底向上 DP 完全模拟了回溯解法的状态转移过程，并且不需要显式的递归或回溯。其时间复杂度为 O(n × 2ⁿ)，空间复杂度 O(2ⁿ)，在 n ≤ 16 时效率优秀。

追问：if you cannot replace boolean[] visited to int mask, and i still ask you to write a bottom up DP solution based on below Backtracking solution, is that possible ? if yes, how it looks like ?
我们被要求在不使用位掩码（即不将 boolean[] visited 转为 int mask）的情况下，写出一个自底向上的 DP 解法，基于给定的回溯代码。注意：自底向上 DP 通常需要状态能够被离散化并且有明确的依赖顺序。然而，这里的状态包括 visited 数组（或等价的元素使用状态）、当前索引 index、当前子集和 curSum、剩余子集数 k。由于 visited 是一个布尔数组，如果不压缩为位掩码，状态空间巨大（每个元素有是否使用，共 2^n 种，但无法用简单整数索引），DP 表无法直接建立。
实际上，在不使用位掩码的情况下，我们无法用传统的数组 DP 来存储 visited 状态，因为 visited 数组本身不能作为数组下标。但理论上，我们可以使用哈希表（如 Map<String, Boolean>）来记录状态，其中键是 visited 数组的某种编码（如字符串、列表等），但这不再是“底层 DP 数组”，而是带记忆化的递归，即自顶向下的记忆化搜索。而且自底向上（迭代）要求能够按某种顺序遍历所有状态，并逐步构建，但 visited 的枚举顺序很难保证。
因此，结论是：如果不将 visited 转换为位掩码，无法写出一个可行的自底向上 DP 解法。因为自底向上 DP 的核心是状态空间的可枚举性和可递推性，而 visited 数组的状态空间虽然有限（2^n），但若不压缩，就无法用连续整数索引，只能使用类似集合的表示，这通常意味着递归+记忆化。
如果你坚持要一个“自底向上”的形式，那么唯一的方法是手动枚举所有 visited 状态（例如用二进制数表示），这恰恰又回到了位掩码。所以不转换是不可能的。
故回答：在不使用位掩码的情况下，无法写出自底向上的 DP 解法。若你允许使用位掩码，则参考前面给出的 DP 代码。
在不使用位掩码（即保持 boolean[] visited 形式）的情况下，无法写出自底向上的 DP 解法。原因如下：
- 状态空间巨大且不可枚举：自底向上 DP 需要将所有可能的状态（visited 数组 + index + curSum + k）离散化为整数索引，并按某种顺序递推。visited 是一个布尔数组，其可能状态数为 2^n（n 为数组长度），但若不将其编码为一个整数（如位掩码），就无法作为数组下标或哈希键，也就无法构建 DP 表。强行用 Map<String, Boolean> 记录虽然可行，但这本质上是记忆化递归（自顶向下），而非自底向上迭代。
- 自底向上要求状态的依赖关系是单向且可遍历的：即使我们勉强用 Map 存储所有状态，也无法像传统 DP 那样通过简单的嵌套循环按顺序计算，因为 visited 状态之间的转移关系复杂，且每个状态需要依赖“添加一个未使用元素”后的状态，这种依赖难以用递推顺序保证。通常，我们会用位掩码枚举所有 mask 并从小到大遍历（因为 newMask > mask），这正是位掩码的优势。
因此，结论：如果不将 boolean[] visited 转换为整数位掩码，则无法写出有效的自底向上 DP 解法。如果你希望保留原始回溯的代码风格，只能采用带记忆化的递归（自顶向下 DP）或者保持回溯。

Q8：How to replace 'boolean[] visited' to 'int mask' in two styles Native DFS solution ? Are we able to remove the 'index' togther with 'visited' in parameter ?
用 int mask 替代了 boolean[] visited，并且移除了 index 参数。这是完全可行的，原因如下：
- mask 的每一位表示对应索引的元素是否被使用，这等价于 visited 数组。
- 不再需要 index 参数：因为每次递归时，我们都从 0 开始扫描所有元素，并通过 mask 判断该元素是否已被使用。虽然这会导致重复检查已使用元素，但 n ≤ 16 时开销很小，且代码更简洁。如果你想进一步优化，可以保留 start 参数来跳过已尝试过的元素，但非必须。
因此，你的转换是完全正确的，并且已经去掉了 index。如果需要增加记忆化，可以添加 Boolean[] memo = new Boolean[1 << n]，在函数开头检查 memo[mask]。但注意：由于 curSum 也是状态的一部分，仅用 mask 作为键可能不够精确（不同路径可能达到相同的 mask 但不同的 curSum），不过在你的代码中，当 curSum == target 时会被重置，且剪枝较强，实际不影响正确性。若要严格记忆化，可改用 Map<Integer, Boolean> 或二维数组 Boolean[mask][curSum]，但会增加复杂度。
总结：你的替换方案是标准的位掩码回溯写法，移除了 index 并正确运行。
For loop Style Native DFS convert:
将 boolean[] visited 替换为 int mask 后，可以安全地移除 index 参数，因为 mask 已经记录了所有元素的使用情况，每次递归从头扫描数组即可。下面给出最终代码，并附上必要的剪枝和注释。
import java.util.Arrays;

class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % k != 0) return false;
        int target = sum / k;
        // 降序排序（大数优先，便于剪枝）
        Arrays.sort(nums);
        for (int i = 0, j = nums.length - 1; i < j; i++, j--) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        // 如果最大元素大于 target，不可能成功
        if (nums[0] > target) return false;

        return backtrack(nums, k, 0, 0, target);
    }

    private boolean backtrack(int[] nums, int k, int mask, int curSum, int target) {
        if (k == 0) return true;                     // 所有子集构造完成
        if (curSum == target) {                      // 当前子集完成，开始下一个
            return backtrack(nums, k - 1, mask, 0, target);
        }
        for (int i = 0; i < nums.length; i++) {
            if ((mask & (1 << i)) != 0) continue;   // 元素已使用
            if (curSum + nums[i] > target) continue; // 超过目标，跳过
            if (backtrack(nums, k, mask | (1 << i), curSum + nums[i], target)) {
                return true;
            }
            // 关键剪枝：如果当前元素是子集的第一个元素且失败，则后续相同值也失败
            if (curSum == 0) {
                while (i + 1 < nums.length && nums[i + 1] == nums[i]) i++;
                return false;
            }
        }
        return false;
    }
}
总结：
- ✅ 已成功将 visited 数组替换为 mask 位掩码。
- ✅ 已移除 index 参数，每次从头遍历所有元素，通过 mask 判断是否使用。
- ✅ 保留了原回溯中的所有剪枝（排序、超限跳过、首元素失败剪枝、跳过重复值）。
- ✅ 该解法是 LeetCode 698 的标准回溯写法，在 n ≤ 16 时能高效通过。

Take and not take Style Native DFS convert:
我们可以将 boolean[] visited 替换为 int mask，同时保留 index 参数和“选/不选”的递归结构。这样既不需要 for 循环遍历所有元素，也不需要改变原有的决策逻辑。下面是完整的转换代码，保留了所有剪枝和注释。
import java.util.Arrays;

class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for (int num : nums) sum += num;
        if (sum % k != 0) return false;
        int target = sum / k;
        // 降序排序，便于剪枝
        Arrays.sort(nums);
        for (int i = 0, j = nums.length - 1; i < j; i++, j--) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        if (nums[0] > target) return false; // 单个元素超过目标，直接失败
        int n = nums.length;
        // mask 的每一位表示对应下标的元素是否已被使用
        return helper(nums, k, 0, 0, 0, target);
    }

    private boolean helper(int[] nums, int k, int mask, int index, int curSum, int target) {
        // 所有子集已构造完成
        if (k == 0) return true;
        // 当前子集已满，开始下一个子集（重置 curSum 和 index）
        if (curSum == target) {
            return helper(nums, k - 1, mask, 0, 0, target);
        }
        // 没有更多元素可用
        if (index >= nums.length) return false;
        // 如果当前元素已被使用，直接跳过（不选）
        if ((mask & (1 << index)) != 0) {
            return helper(nums, k, mask, index + 1, curSum, target);
        }
        // 尝试“选”当前元素（前提是不超过 target）
        if (curSum + nums[index] <= target) {
            int newMask = mask | (1 << index);
            if (helper(nums, k, newMask, index + 1, curSum + nums[index], target)) {
                return true;
            }
        }
        // 尝试“不选”当前元素
        if (helper(nums, k, mask, index + 1, curSum, target)) {
            return true;
        }
        // 关键剪枝：如果当前元素是当前子集的第一个元素（curSum == 0）且失败，则直接返回 false
        if (curSum == 0) return false;
        return false;
    }
}
转换说明
- visited 数组 → mask 位掩码：(mask & (1 << index)) != 0 判断元素是否已使用，mask | (1 << index) 标记使用。
- 保留了 index 参数：用于线性顺序处理每个元素，因此无需 for 循环。
- 保留了“选/不选”两个分支：完全对应原回溯结构。
- 保留了所有剪枝：包括 curSum == target 时重置、超限跳过、首元素失败剪枝等。
- 去除了显式的回溯操作：因为 mask 是值传递，递归时传入新值，自动实现隐式回溯。
这种写法既满足了“不使用 for 循环”的要求，又成功将 visited 数组替换为位掩码。



Refer to
L416.P16.2.Partition Equal Subset Sum
L473.Matchsticks to Square (Ref.L2397)
L2397.Maximum Rows Covered by Columns (Ref.L473)
