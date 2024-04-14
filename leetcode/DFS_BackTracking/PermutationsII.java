/**
 * Refer to
 * https://leetcode.com/problems/permutations-ii/description/
 * Given a collection of numbers that might contain duplicates, return all possible unique permutations.

    For example,
    [1,1,2] have the following unique permutations:
    [
      [1,1,2],
      [1,2,1],
      [2,1,1]
    ]
 * 
 * 
 * Solution
 * https://leetcode.com/submissions/detail/114437976/
*/
// Time Complexity
// Refer to
// https://leetcode.com/problems/permutations-ii/discuss/18594/Really-easy-Java-solution-much-easier-than-the-solutions-with-very-high-vote/121098
/**
The worst-case time complexity is O(n! * n).
For any recursive function, the time complexity is O(branches^depth) * amount of work at each node in the recursive call tree. 
However, in this case, we have n*(n-1)*(n*2)*(n-3)*...*1 branches at each level = n!, so the total recursive calls is O(n!)
We do n-amount of work in each node of the recursive call tree, (a) the for-loop and (b) at each leaf when we add n elements 
to an ArrayList. So this is a total of O(n) additional work per node.
Therefore, the upper-bound time complexity is O(n! * n).
*/
public class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if(nums == null || nums.length == 0) {
            return result;
        }
        Arrays.sort(nums);
        List<Integer> combination = new ArrayList<Integer>();
        helper(nums, result, combination, new boolean[nums.length]);
        return result;
    }
    
    private void helper(int[] nums, List<List<Integer>> result, List<Integer> combination, boolean[] used) {
        if(combination.size() == nums.length) {
            result.add(new ArrayList<Integer>(combination));
        }
        for(int i = 0; i < nums.length; i++) {
            /*
            Why used[i] || (i > 0 && !used[i - 1] && nums[i] == nums[i - 1]) ?
            判断主要是为了去除重复元素影响。
            比如，给出一个排好序的数组，[1,2,2]，那么第一个2和第二2如果在结果中互换位置，
            我们也认为是同一种方案，所以我们强制要求相同的数字，原来排在前面的，在结果
            当中也应该排在前面，这样就保证了唯一性。所以当前面的2还没有使用的时候，就
            不应该让后面的2使用。
            Refer to
            https://leetcode.com/problems/permutations-ii/discuss/18594/Really-easy-Java-solution-much-easier-than-the-solutions-with-very-high-vote/324818
            The difficulty is to handle the duplicates.
            With inputs as [1a, 1b, 2a],
            If we don't handle the duplicates, the results would be: [1a, 1b, 2a], [1b, 1a, 2a]...,
            so we must make sure 1a goes before 1b to avoid duplicates
            By using nums[i-1]==nums[i] && !used[i-1], we can make sure that 1b cannot be choosed before 1a
            */
            if(used[i] || (i > 0 && !used[i - 1] && nums[i] == nums[i - 1])) {
                continue;
            }
            used[i] = true;
            combination.add(nums[i]);
            helper(nums, result, combination, used);
            combination.remove(combination.size() - 1);
            // Don't forget to reset 'used' boolean flag back to false
            used[i] = false;
        }        
    }
}

// Wrong Solution
// The solution for 40. Combination Sum II is the wrong way to solve this issue
// Because that's the way for computing Permutation, since change the order will be recognize as different solution
// e.g if target is 8, [2,6] and [6,2] is one combination but two permutation 
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/lintcode/DFS/VideoExamples/CombinationSumII.java
// https://leetcode.com/problems/combination-sum-ii/
/**
 Input
[1,1,2]

Output
[[1,1,2]]

Expected
[[1,1,2],[1,2,1],[2,1,1]]
*/
class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        Arrays.sort(nums);
        helper(result, new ArrayList<Integer>(), nums, 0);
        return result;
    }
    
    private void helper(List<List<Integer>> result, List<Integer> list, int[] nums, int index) {
        if(list.size() == nums.length) {
            result.add(new ArrayList<Integer>(list));
        }
        for(int i = index; i < nums.length; i++) {
            if(i > index && nums[i] == nums[i - 1]) {
                continue;
            }
            list.add(nums[i]);
            helper(result, list, nums, i + 1);
            list.remove(list.size() - 1);
        }
    }
}

// Why we don't need index for loop ? Can we also introduce 'index' to do same as Permutations or Combinations as level order traversal ?
// For 1st question --> Because every time we directly start from 0 for each recursive level, which generate duplicates automatically, but will handle by prune
// as visited boolean array plus "if(used[i] || (i > 0 && !used[i - 1] && nums[i] == nums[i - 1]))" logic
// For 2nd quesiton --> Yes, we can
// Refer to
// https://www.cnblogs.com/grandyang/p/4359825.html
// 这道题是之前那道 Permutations 的延伸，由于输入数组有可能出现重复数字，如果按照之前的算法运算，会有重复排列产生，我们要避免重复的产生，
// 在递归函数中要判断前面一个数和当前的数是否相等，如果相等，且其对应的 visited 中的值为1，当前的数字才能使用（下文中会解释这样做的原因），
// 否则需要跳过，这样就不会产生重复排列了，代码如下：
/**
class Solution {
public:
    vector<vector<int>> permuteUnique(vector<int>& nums) {
        vector<vector<int>> res;
        vector<int> out, visited(nums.size(), 0);
        sort(nums.begin(), nums.end());
        permuteUniqueDFS(nums, 0, visited, out, res);
        return res;
    }
    void permuteUniqueDFS(vector<int>& nums, int level, vector<int>& visited, vector<int>& out, vector<vector<int>>& res) {
        if (level >= nums.size()) {res.push_back(out); return;}
        for (int i = 0; i < nums.size(); ++i) {
            if (visited[i] == 1) continue;
            if (i > 0 && nums[i] == nums[i - 1] && visited[i - 1] == 0) continue;
            visited[i] = 1;
            out.push_back(nums[i]);
            permuteUniqueDFS(nums, level + 1, visited, out, res);
            out.pop_back();
            visited[i] = 0;
        }
    }
};
*/
// 在使用上面的方法的时候，一定要能弄清楚递归函数的 for 循环中两个 if 的剪枝的意思。在此之前，要弄清楚 level 的含义，这里用数组 out 
// 来拼排列结果，level就是当前已经拼成的个数，其实就是 out 数组的长度。我们看到，for 循环的起始是从0开始的，而本题的解法二，三，四都
// 是用了一个 start 变量，从而 for 循环都是从 start 开始，一定要分清楚 start 和本解法中的 level 的区别。由于递归的 for 都是从0开始，
// 难免会重复遍历到数字，而全排列不能重复使用数字，意思是每个 nums 中的数字在全排列中只能使用一次（当然这并不妨碍 nums 中存在重复数字）。
// 不能重复使用数字就靠 visited 数组来保证，这就是第一个 if 剪枝的意义所在。关键来看第二个 if 剪枝的意义，这里说当前数字和前一个数字相同，
// 且前一个数字的 visited 值为0的时候，必须跳过。这里的前一个数 visited 值为0，并不代表前一个数字没有被处理过，也可能是递归结束后恢复
// 状态时将 visited 值重置为0了，实际上就是这种情况，下面打印了一些中间过程的变量值，如下所示：
/**
level = 0, i = 0 => out: {}
level = 1, i = 0 => out: {1 } skipped 1
level = 1, i = 1 => out: {1 }
level = 2, i = 0 => out: {1 2 } skipped 1
level = 2, i = 1 => out: {1 2 } skipped 1
level = 2, i = 2 => out: {1 2 }
level = 3 => saved  {1 2 2}
level = 3, i = 0 => out: {1 2 2 } skipped 1
level = 3, i = 1 => out: {1 2 2 } skipped 1
level = 3, i = 2 => out: {1 2 2 } skipped 1
level = 2, i = 2 => out: {1 2 2 } -> {1 2 } recovered
level = 1, i = 1 => out: {1 2 } -> {1 } recovered
level = 1, i = 2 => out: {1 } skipped 2
level = 0, i = 0 => out: {1 } -> {} recovered
level = 0, i = 1 => out: {}
level = 1, i = 0 => out: {2 }
level = 2, i = 0 => out: {2 1 } skipped 1
level = 2, i = 1 => out: {2 1 } skipped 1
level = 2, i = 2 => out: {2 1 }
level = 3 => saved  {1 2 2}
level = 3, i = 0 => out: {2 1 2 } skipped 1
level = 3, i = 1 => out: {2 1 2 } skipped 1
level = 3, i = 2 => out: {2 1 2 } skipped 1
level = 2, i = 2 => out: {2 1 2 } -> {2 1 } recovered
level = 1, i = 0 => out: {2 1 } -> {2 } recovered
level = 1, i = 1 => out: {2 } skipped 1
level = 1, i = 2 => out: {2 }
level = 2, i = 0 => out: {2 2 }
level = 3 => saved  {1 2 2}
level = 3, i = 0 => out: {2 2 1 } skipped 1
level = 3, i = 1 => out: {2 2 1 } skipped 1
level = 3, i = 2 => out: {2 2 1 } skipped 1
level = 2, i = 0 => out: {2 2 1 } -> {2 2 } recovered
level = 2, i = 1 => out: {2 2 } skipped 1
level = 2, i = 2 => out: {2 2 } skipped 1
level = 1, i = 2 => out: {2 2 } -> {2 } recovered
level = 0, i = 1 => out: {2 } -> {} recovered
level = 0, i = 2 => out: {} skipped 2
*/
// 注意看这里面的 skipped 1 表示的是第一个 if 剪枝起作用的地方，skipped 2 表示的是第二个 if 剪枝起作用的地方。我们主要关心的
// 是第二个 if 剪枝，看上方第一个蓝色标记的那行，再上面的红色一行表示在 level = 1, i = 1 时递归调用结束后，恢复到起始状态，
// 那么此时 out 数组中只有一个1，后面的2已经被 pop_back() 了，当然对应的 visited 值也重置为0了，这种情况下需要剪枝，当然不能
// 再一次把2往里加，因为这种情况在递归中已经加入到结果 res 中了，所以到了 level = 1, i = 2 的状态时，
// nums[i] == nums[i-1] && visited[i-1] == 0 的条件满足了，剪枝就起作用了，这种重复的情况就被剪掉了。

// Re-work
// Solution 2: Using 'index'
// Following is another solution which introduce 'index'
// Refer to
// https://leetcode.com/problems/permutations-ii/discuss/18648/Share-my-Java-code-with-detailed-explanantion
/**
Since we only need permutations of the array, the actual "content" does not change, we could find each permutation by swapping the elements in the array.
The idea is for each recursion level, swap the current element at 1st index with each element that comes after it (including itself). For example, permute[1,2,3]:
At recursion level 0, current element at 1st index is 1, there are 3 possibilities: [1] + permute[2,3], [2] + permute[1,3], [3] + permute[2,1].
Take "2+permute[1,3]" as the example at recursion level 0. At recursion level 1, current elemenet at 1st index is 1, there are 2 possibilities: 
[2,1] + permute[3], [2,3] + permute[1].
... and so on.

Let's look at another example, permute[1,2,3,4,1].
At recursion level 0, we have [1] + permute[2,3,4,1], [2] + permute[1,3,4,1], [3] + permute[2,1,4,1], [4] + permute[2,3,1,1], [1] + permute[2,3,4,1].
1 has already been at the 1st index of current recursion level, so the last possibility is redundant. We can use a hash set to mark which elements 
have been at the 1st index of current recursion level, so that if we meet the element again, we can just skip it.
*/
public class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        if (nums==null || nums.length==0) { return ans; }
        permute(ans, nums, 0);
        return ans;
    }
    
    private void permute(List<List<Integer>> ans, int[] nums, int index) {
        if (index == nums.length) { 
            List<Integer> temp = new ArrayList<>();
            for (int num: nums) { temp.add(num); }
            ans.add(temp);
            return;
        }
        Set<Integer> appeared = new HashSet<>();
        for (int i=index; i<nums.length; ++i) {
            if (appeared.add(nums[i])) {
                swap(nums, index, i);
                permute(ans, nums, index+1);
                swap(nums, index, i);
            }
        }
    }
    
    private void swap(int[] nums, int i, int j) {
        int save = nums[i];
        nums[i] = nums[j];
        nums[j] = save;
    }
}

// Refer to
// https://www.cnblogs.com/grandyang/p/4359825.html
// 还有一种比较简便的方法，在 Permutations 的基础上稍加修改，用 TreeSet 来保存结果，利用其不会有重复项的特点，然后在递归函数中 swap 的地方，
// 判断如果i和 start 不相同，但是 nums[i] 和 nums[start] 相同的情况下跳过，继续下一个循环，参见代码如下：
/**
class Solution {
public:
    vector<vector<int>> permuteUnique(vector<int>& nums) {
        set<vector<int>> res;
        permute(nums, 0, res);
        return vector<vector<int>> (res.begin(), res.end());
    }
    void permute(vector<int>& nums, int start, set<vector<int>>& res) {
        if (start >= nums.size()) res.insert(nums);
        for (int i = start; i < nums.size(); ++i) {
            if (i != start && nums[i] == nums[start]) continue; // 剪枝
            swap(nums[i], nums[start]);
            permute(nums, start + 1, res);
            swap(nums[i], nums[start]);
        }
    }
};
*/
// 对于上面的解法，你可能会有疑问，我们不是在 swap 操作之前已经做了剪枝了么，为什么还是会有重复出现，以至于还要用 TreeSet 来取出重复呢。
// 总感觉使用 TreeSet 去重复有点耍赖，可能并没有探究到本题深层次的内容。这是很好的想法，首先尝试将上面的 TreeSet 还原为 vector，并且
// 在主函数调用递归之前给 nums 排个序（代码参见评论区三楼），然后测试一个最简单的例子：[1, 2, 2]，得到的结果为：
// [[1,2,2], [2,1,2], [2,2,1], [2,2,1],  [2,1,2]]
// 我们发现有重复项，那么剪枝究竟在做些什么，怎么还是没法防止重复项的产生！那个剪枝只是为了防止当 start = 1, i = 2 时，将两个2交换，
// 这样可以防止 {1, 2, 2} 被加入两次。但是没法防止其他的重复情况，要闹清楚为啥，需要仔细分析一些中间过程，下面打印了一些中间过程的变量
/**
start = 0, i = 0 => {1 2 2} 
start = 1, i = 1 => {1 2 2} 
start = 2, i = 2 => {1 2 2} 
start = 3 => saved  {1 2 2}
start = 1, i = 2 => {1 2 2} skipped
start = 0, i = 1 => {1 2 2} -> {2 1 2}
start = 1, i = 1 => {2 1 2} 
start = 2, i = 2 => {2 1 2} 
start = 3 => saved  {2 1 2}
start = 1, i = 2 => {2 1 2} -> {2 2 1}
start = 2, i = 2 => {2 2 1} 
start = 3 => saved  {2 2 1}
start = 1, i = 2 => {2 2 1} -> {2 1 2} recovered
start = 0, i = 1 => {2 1 2} -> {1 2 2} recovered
start = 0, i = 2 => {1 2 2} -> {2 2 1}
start = 1, i = 1 => {2 2 1} 
start = 2, i = 2 => {2 2 1} 
start = 3 => saved  {2 2 1}
start = 1, i = 2 => {2 2 1} -> {2 1 2}
start = 2, i = 2 => {2 1 2} 
start = 3 => saved  {2 1 2}
start = 1, i = 2 => {2 1 2} -> {2 2 1} recovered
start = 0, i = 2 => {2 2 1} -> {1 2 2} recovered
*/
// 问题出在了递归调用之后的还原状态，参见上面的红色的两行，当 start = 0, i = 2 时，nums 已经还原到了 {1, 2, 2} 的状态，此时 nums[start] 
// 不等于 nums[i]，剪枝在这已经失效了，那么交换后的 {2, 2, 1} 还会被存到结果 res 中，而这个状态在之前就已经存过了一次。


















































































































































https://leetcode.com/problems/permutations-ii/
Given a collection of numbers, nums, that might contain duplicates, return all possible unique permutations in any order.

Example 1:
Input: nums = [1,1,2]
Output:
[[1,1,2],
 [1,2,1],
 [2,1,1]]

Example 2:
Input: nums = [1,2,3]
Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]

Constraints:
- 1 <= nums.length <= 8
- -10 <= nums[i] <= 10
--------------------------------------------------------------------------------
Attempt 1: 2022-10-20
Solution 1: Backtracking style 1 (10min)
Style 1: With redundant 'index' parameter
class Solution { 
    public List<List<Integer>> permuteUnique(int[] nums) { 
        List<List<Integer>> result = new ArrayList<List<Integer>>(); 
        boolean[] visited = new boolean[nums.length]; 
        Arrays.sort(nums); 
        helper(nums, result, new ArrayList<Integer>(), visited, 0); 
        return result; 
    } 
     
    private void helper(int[] nums, List<List<Integer>> result, List<Integer> tmp, boolean[] visited, int index) { 
        if(tmp.size() == nums.length) { 
            result.add(new ArrayList<Integer>(tmp)); 
            return; 
        } 
        for(int i = index; i < nums.length; i++) { 
            if(visited[i] || (i > 0 && !visited[i - 1] && nums[i] == nums[i - 1])) { 
                continue; 
            } 
            tmp.add(nums[i]); 
            visited[i] = true; 
            helper(nums, result, tmp, visited, index); 
            tmp.remove(tmp.size() - 1); 
            visited[i] = false; 
        } 
    } 
}

Time Complexity: O(N * N!)
Space Complexity: O(N)
Style 2: Without redundant 'index' parameter
class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        boolean[] visited = new boolean[nums.length];
        Arrays.sort(nums);
        helper(nums, result, new ArrayList<Integer>(), visited);
        return result;
    }

    private void helper(int[] nums, List<List<Integer>> result, List<Integer> tmp, boolean[] visited) {
        if(tmp.size() == nums.length) {
            result.add(new ArrayList<Integer>(tmp));
            return;
        }
        for(int i = 0; i < nums.length; i++) {
            if(visited[i] || (i > 0 && !visited[i - 1] && nums[i] == nums[i - 1])) {
                continue;
            }
            tmp.add(nums[i]);
            visited[i] = true;
            helper(nums, result, tmp, visited);
            tmp.remove(tmp.size() - 1);
            visited[i] = false;
        }
    }
}

Time Complexity: O(N * N!)
Space Complexity: O(N)

Refer to 
https://leetcode.com/problems/permutations-ii/discuss/18594/Really-easy-Java-solution-much-easier-than-the-solutions-with-very-high-vote/121098 
The worst-case time complexity is O(n! * n). 
For any recursive function, the time complexity is O(branches^depth) * amount of work at each node in the recursive call tree. However, in this case, we have n*(n-1)*(n*2)*(n-3)*...*1 branches at each level = n!, so the total recursive calls is O(n!) 
We do n-amount of work in each node of the recursive call tree, (a) the for-loop and (b) at each leaf when we add n elements to an ArrayList. So this is a total of O(n) additional work per node. 
Therefore, the upper-bound time complexity is O(n! * n).

Refer to 
https://leetcode.com/problems/permutations/discuss/1527929/Java-or-TC%3A-O(N*N!)-or-SC%3A-O(N)-or-Recursive-Backtracking-and-Iterative-Solutions 
Time Complexity: O(N * N!). Number of permutations = P(N,N) = N!.  
Each permutation takes O(N) to construct  
T(n) = n*T(n-1) + O(n)  
T(n-1) = (n-1)*T(n-2) + O(n-1)  
...  
T(2) = (2)*T(1) + O(2)  
T(1) = O(N) -> To convert the nums array to ArrayList.  
Above equations can be added together to get:  
 T(n) = n + n*(n-1) + n*(n-1)*(n-2) + ... + (n....2) + (n....1) * n  
      = P(n,1) + P(n,2) + P(n,3) + ... + P(n,n-1) + n*P(n,n)  
      = (P(n,1) + ... + P(n,n)) + (n-1)*P(n,n)  
      = Floor(e*n! - 1) + (n-1)*n!  
      = O(N * N!) 
Space Complexity: O(N). Recursion stack.   
N = Length of input array.

Refer to
https://leetcode.com/problems/permutations-ii/discuss/18594/Really-easy-Java-solution-much-easier-than-the-solutions-with-very-high-vote/324818
The difficulty is to handle the duplicates. With inputs as [1a, 1b, 2a]. If we don't handle the duplicates, the results would be: [1a, 1b, 2a], [1b, 1a, 2a] ..., so we must make sure 1a goes before 1b to avoid duplicates by using nums[i - 1] == nums[i] && !used[i - 1], we can make sure that 1b cannot be chosen before 1a

http://www.jiuzhang.com/solutions/permutations-ii/
public class Solution { 
    public List<List<Integer>> permuteUnique(int[] nums) { 
        List<List<Integer>> result = new ArrayList<List<Integer>>(); 
        if(nums == null || nums.length == 0) { 
            return result; 
        } 
        Arrays.sort(nums); 
        List<Integer> combination = new ArrayList<Integer>(); 
        helper(nums, result, combination, new boolean[nums.length]); 
        return result; 
    } 
     
    private void helper(int[] nums, List<List<Integer>> result, List<Integer> combination, boolean[] used) { 
        if(combination.size() == nums.length) { 
            result.add(new ArrayList<Integer>(combination)); 
        } 
        for(int i = 0; i < nums.length; i++) { 
            /* 
            判断主要是为了去除重复元素影响。 
            比如，给出一个排好序的数组，[1,2,2]，那么第一个2和第二2如果在结果中互换位置， 
            我们也认为是同一种方案，所以我们强制要求相同的数字，原来排在前面的，在结果 
            当中也应该排在前面，这样就保证了唯一性。所以当前面的2还没有使用的时候，就 
            不应该让后面的2使用。 
            */ 
            if(used[i] || (i > 0 && !used[i - 1] && nums[i] == nums[i - 1])) { 
                continue; 
            } 
            used[i] = true; 
            combination.add(nums[i]); 
            helper(nums, result, combination, used); 
            combination.remove(combination.size() - 1); 
            // Don't forget to reset 'used' boolean flag back to false 
            used[i] = false; 
        }         
    } 
}

--------------------------------------------------------------------------------
Solution 2: Backtracking style 2 (10min, no Arrays.sort(), no boolean visited, but frequency Hash Map)
class Solution { 
    public List<List<Integer>> permuteUnique(int[] nums) { 
        List<List<Integer>> result = new ArrayList<List<Integer>>(); 
        //boolean[] visited = new boolean[nums.length]; 
        //Arrays.sort(nums); 
        Map<Integer, Integer> freq = new HashMap<Integer, Integer>(); 
        for(int num : nums) { 
            freq.put(num, freq.getOrDefault(num, 0) + 1); 
        } 
        helper(nums, result, new ArrayList<Integer>(), freq, 0); 
        return result; 
    } 
     
    private void helper(int[] nums, List<List<Integer>> result, List<Integer> tmp, Map<Integer, Integer> freq, int index) { 
        if(tmp.size() == nums.length) { 
            result.add(new ArrayList<Integer>(tmp)); 
            return; 
        }     
        // Instead of loop on 'nums' array, loop on key set of frequency map since 
        // we only iterate over the unique value to pick up
        for(int k : freq.keySet()) { 
            // Only when unique value's frequency > 0, we are allowed to choose
            if(freq.get(k) > 0) { 
                tmp.add(k); 
                freq.put(k, freq.get(k) - 1); 
                helper(nums, result, tmp, freq, index); 
                // Why backtrack? 
                // After DFS done and hit base case to store current combination, we  
                // have to restore the statistics, prepare for next for loop iteration  
                // which start from new unique value to build new combination
                tmp.remove(tmp.size() - 1); 
                freq.put(k, freq.get(k) + 1);                 
            } 
        } 
    } 
}

Refer to 
https://leetcode.com/problems/permutations-ii/discuss/18594/Really-easy-Java-solution-much-easier-than-the-solutions-with-very-high-vote/121098 
The worst-case time complexity is O(n! * n). 
For any recursive function, the time complexity is O(branches^depth) * amount of work at each node in the recursive call tree. However, in this case, we have n*(n-1)*(n*2)*(n-3)*...*1 branches at each level = n!, so the total recursive calls is O(n!) 
We do n-amount of work in each node of the recursive call tree, (a) the for-loop and (b) at each leaf when we add n elements to an ArrayList. So this is a total of O(n) additional work per node. 
Therefore, the upper-bound time complexity is O(n! * n).

Refer to 
https://leetcode.com/problems/permutations/discuss/1527929/Java-or-TC%3A-O(N*N!)-or-SC%3A-O(N)-or-Recursive-Backtracking-and-Iterative-Solutions 
Time Complexity: O(N * N!). Number of permutations = P(N,N) = N!.  
Each permutation takes O(N) to construct  
T(n) = n*T(n-1) + O(n)  
T(n-1) = (n-1)*T(n-2) + O(n-1)  
...  
T(2) = (2)*T(1) + O(2)  
T(1) = O(N) -> To convert the nums array to ArrayList.  
Above equations can be added together to get:  
 T(n) = n + n*(n-1) + n*(n-1)*(n-2) + ... + (n....2) + (n....1) * n  
      = P(n,1) + P(n,2) + P(n,3) + ... + P(n,n-1) + n*P(n,n)  
      = (P(n,1) + ... + P(n,n)) + (n-1)*P(n,n)  
      = Floor(e*n! - 1) + (n-1)*n!  
      = O(N * N!) 
Space Complexity: O(N). Recursion stack.   
N = Length of input array.

Video explain for Solution 2: Backtracking style 2 how to work with HashMap
Permutations II - Backtracking - Leetcode 47
https://www.youtube.com/watch?v=qhBVWf0YafA
We cannot use duplicate elements in the same position which will result into same permutation


Create frequency table based on given input, when use an element decrease the frequency of that element
e.g initially element 1 frequency is 2, after using one of them, decrease 1 from frequency as 2 - 1 = 1

After using two 1 and one 2 to build one permutation {1,1,2}, both element 1 and 2's frequency drop to 0


Refer to
https://leetcode.com/problems/permutations-ii/discuss/1768113/Java-Backtracking-HashMap
class Solution { 
    public List<List<Integer>> permuteUnique(int[] nums) { 
        List<List<Integer>> result = new ArrayList(); 
        Map<Integer, Integer> map = new HashMap(); 
        for (int num:nums) 
            map.put(num, map.getOrDefault(num,0)+1); 
        backtracking(nums, result, map, new ArrayList<Integer>()); 
        return result; 
    } 

    private void backtracking(int[] nums, List<List<Integer>> result, Map<Integer, Integer> map, List<Integer> list){ 
        if (list.size() == nums.length){ 
            result.add(new ArrayList<Integer>(list)); 
                return; 
        } 
        for (Integer key: map.keySet()){ 
            if (map.get(key)>0){ 
                list.add(key); 
                map.put(key, map.get(key) -1); 
                backtracking(nums, result, map, list);
                map.put(key, map.get(key) +1); 
                list.remove(list.size() -1); 
            } 
        } 
    } 
}

--------------------------------------------------------------------------------
No "Not pick" and "Pick" branch available for this problem yet (normal decision tree not gonna work)
Because in L47. we have to use all numbers in the given array, not like L77. we just pick k out of n numbers, so there is no chance for a number in L47 to 'Not pick', hence no "Not pick" and "Pick" strategy here      
    
Refer to
L46.P11.3.Permutations (Ref.L77)
L90.P11.2.Subsets II (Ref.L491,L78)
