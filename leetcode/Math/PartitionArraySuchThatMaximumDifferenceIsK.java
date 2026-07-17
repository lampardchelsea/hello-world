https://leetcode.com/problems/partition-array-such-that-maximum-difference-is-k/description/
You are given an integer array nums and an integer k. You may partition nums into one or more subsequences such that each element in nums appears in exactly one of the subsequences.
Return the minimum number of subsequences needed such that the difference between the maximum and minimum values in each subsequence is at most k.
A subsequence is a sequence that can be derived from another sequence by deleting some or no elements without changing the order of the remaining elements.
 
Example 1:
Input: nums = [3,6,1,2,5], k = 2
Output: 2
Explanation:We can partition nums into the two subsequences [3,1,2] and [6,5].The difference between the maximum and minimum value in the first subsequence is 3 - 1 = 2.The difference between the maximum and minimum value in the second subsequence is 6 - 5 = 1.Since two subsequences were created, we return 2. It can be shown that 2 is the minimum number of subsequences needed.

Example 2:
Input: nums = [1,2,3], k = 1
Output: 2
Explanation:We can partition nums into the two subsequences [1,2] and [3].The difference between the maximum and minimum value in the first subsequence is 2 - 1 = 1.The difference between the maximum and minimum value in the second subsequence is 3 - 3 = 0.Since two subsequences were created, we return 2. Note that another optimal solution is to partition nums into the two subsequences [1] and [2,3].

Example 3:
Input: nums = [2,2,4,5], k = 0
Output: 3
Explanation:We can partition nums into the three subsequences [2,2], [4], and [5].The difference between the maximum and minimum value in the first subsequences is 2 - 2 = 0.The difference between the maximum and minimum value in the second subsequences is 4 - 4 = 0.The difference between the maximum and minimum value in the third subsequences is 5 - 5 = 0.Since three subsequences were created, we return 3. It can be shown that 3 is the minimum number of subsequences needed.
 
Constraints:
- 1 <= nums.length <= 10^5
- 0 <= nums[i] <= 10^5
- 0 <= k <= 10^5
--------------------------------------------------------------------------------
Attempt 1: 2026-07-16
Solution 1: Sorting + Greedy (10 min)
class Solution {
    public int partitionArray(int[] nums, int k) {
        // Sort to group close numbers together
        Arrays.sort(nums);
        int count = 0;
        int i = 0;
        int n = nums.length;
        while(i < n) {
            count++;
            // Start a new partition with the current smallest element
            int start = nums[i];
            // Greedily include as many elements as possible into this partition
            while(i < n && nums[i] - start <= k) {
                i++;
            }
            // When the while loop breaks, nums[i] is too large for this partition.
            // The next loop iteration will start a new partition from nums[i].
        }
        return count;
    }
}

Time Complexity: O(n*logn)
Space Complexity: O(logn)

Refer to Deepseek
Since you just asked about Leetcode 2444 (Count Subarrays With Fixed Bounds), let me compare 2294 directly with it to highlight why 2294 is much simpler.
The core difference is: 2444 requires maintaining order (subarrays) and uses a sliding window with lastBad/lastMin/lastMax. 2294 deals with subsequences (order doesn't matter), so we can just sort + greedy.
--------------------------------------------------------------------------------
Core Idea for Leetcode 2294 (Sorting + Greedy)
1.Since we are partitioning a subsequence (not necessarily contiguous), the original order is completely irrelevant.
2.We sort the array. In a sorted array, any valid group will simply be a contiguous slice.
3.To minimize the number of partitions, each partition must contain as many elements as possible.
4.Start from the smallest ungrouped number. This number will be the min of the current group. Greedily include every subsequent number that is <= min + k. Once we find a number larger than min + k, we must cut here and start a new group.
--------------------------------------------------------------------------------
Java Code
import java.util.Arrays;

class Solution {
    public int partitionArray(int[] nums, int k) {
        // 1. Sort to group close numbers together
        Arrays.sort(nums);
        
        int partitions = 0;
        int i = 0;
        int n = nums.length;
        
        while (i < n) {
            // Start a new partition with the current smallest element
            int start = nums[i];
            partitions++;
            
            // 2. Greedily include as many elements as possible into this partition
            while (i < n && nums[i] - start <= k) {
                i++;
            }
            // When the while loop breaks, nums[i] is too large for this partition.
            // The next loop iteration will start a new partition from nums[i].
        }
        
        return partitions;
    }
}
--------------------------------------------------------------------------------
Complexity Analysis
- Time Complexity: O(n log n) – dominated by sorting.
- Space Complexity: O(log n) to O(n) depending on the sorting algorithm (Java uses Dual-Pivot Quicksort for primitives, which is O(log n)).
--------------------------------------------------------------------------------
Quick Example
Input: nums = [3, 6, 1, 2, 5], k = 2
- Sorted: [1, 2, 3, 5, 6]
- Partition 1: Start at 1. Include 2 (diff 1), 3 (diff 2). Stop at 5 (diff 4 > 2). → [1, 2, 3]
- Partition 2: Start at 5. Include 6 (diff 1). → [5, 6]
- Result: 2
--------------------------------------------------------------------------------
Why does Greedy work here? (Proof)
If the smallest unassigned element is x, in any valid partition, x must belong to some group. Since x is the smallest in the entire remaining array, the maximum element that can be with x is x + k. Any element larger than x + k cannot be in the same group as x.
So, we might as well put all elements up to x + k into this group. Putting them here cannot hurt us, because it only reduces the load for the remaining partitions. Hence, greedy is optimal.
--------------------------------------------------------------------------------
Contrast with Leetcode 2444 (which you just asked)
Aspect22942444Problem TypePartition / GroupingCounting SubarraysOrder matters?No (Subsequence, can reorder)Yes (Subarray, fixed order)Main TechniqueSorting + GreedySingle Pass + Last Occurrence TrackerGoalMinimize number of partitionsCount all valid contiguous subarraysComplexityO(n log n)O(n)
This is why Leetcode 2294 is classified as Medium (mostly just sorting) while 2444 is Hard (requires careful index math while maintaining fixed order). Let me know if you want me to elaborate further!

Refer to Kimi
Here is the complete Java solution for Leetcode 2294. Partition Array Such That Maximum Difference Is K, with the greedy proof.
Key Insight
Sort the array. Once sorted, any valid partition must consist of consecutive elements — if you skip an element, it would fall between your min and max, so it belongs in the same partition. Therefore, we just greedily extend each partition as far as possible.
Java Solution
import java.util.Arrays;

class Solution {
    public int partitionArray(int[] nums, int k) {
        Arrays.sort(nums);
        
        int n = nums.length;
        int partitions = 0;
        int i = 0;
        
        while (i < n) {
            // Start a new partition at nums[i]
            int start = nums[i];
            partitions++;
            
            // Greedily extend: all elements <= start + k can stay in this partition
            while (i < n && nums[i] - start <= k) {
                i++;
            }
        }
        
        return partitions;
    }
}
Why Greedy is Optimal (Proof Sketch)
After sorting, consider any valid partition. Its elements must be contiguous in the sorted array — otherwise an intermediate value would violate the max-min bound if placed in a different partition.
Now, suppose we start a partition at nums[i]. If we can include nums[j] (where j > i) because nums[j] - nums[i] <= k, there is never a benefit to excluding it:
- Including it doesn't break the condition for this partition
- Excluding it forces nums[j] to start a new partition, increasing the total count
Therefore, making each partition as large as possible from left to right yields the minimum number of partitions.
Complexity
TimeO(n log n) — sorting dominatesSpaceO(1) extra (or O(\log n) for sort stack)
Connection to Your Previous Questions
This problem is the "partition" cousin of 1438 (Longest Continuous Subarray…). In 1438, you are given a limit and asked to find the longest valid subarray in an unsorted array. In 2294, you are allowed to reorder (sort) and must cover the entire array with the fewest valid groups. The sorting step is what makes the greedy approach possible.


Refer to
L1438.Longest Continuous Subarray With Absolute Diff Less Than or Equal to Limit
L2779.Maximum Beauty of an Array After Applying Operation
