/**
Refer to
https://leetcode.com/problems/find-k-th-smallest-pair-distance/
Given an integer array, return the k-th smallest distance among all the pairs. The distance of a pair (A, B) 
is defined as the absolute difference between A and B.

Example 1:
Input:
nums = [1,3,1]
k = 1
Output: 0 
Explanation:
Here are all the pairs:
(1,3) -> 2
(1,1) -> 0
(3,1) -> 2
Then the 1st smallest distance pair is (1,1), and its distance is 0.
Note:
2 <= len(nums) <= 10000.
0 <= nums[i] < 1000000.
1 <= k <= len(nums) * (len(nums) - 1) / 2.
*/

// Solution 1:
// Refer to
// https://leetcode.com/discuss/general-discussion/786126/python-powerful-ultimate-binary-search-template-solved-many-problems
/**
719. Find K-th Smallest Pair Distance [Hard]
Given an integer array, return the k-th smallest distance among all the pairs. The distance of a pair (A, B) is defined as the absolute difference between A and B.

Example :

Input:
nums = [1,3,1]
k = 1
Output: 0 
Explanation:
Following are all the pairs. The 1st smallest distance pair is (1,1), and its distance is 0.
(1,3) -> 2
(1,1) -> 0
(3,1) -> 2
Very similar to LC 668 above, both are about finding Kth-Smallest. Just like LC 668, We can design an enough function, 
given an input distance, determine whether there're at least k pairs whose distances are less than or equal to distance. 
We can sort the input array and use two pointers (fast pointer and slow pointer, pointed at a pair) to scan it. Both 
pointers go from leftmost end. If the current pair pointed at has a distance less than or equal to distance, all pairs 
between these pointers are valid (since the array is already sorted), we move forward the fast pointer. Otherwise, 
we move forward the slow pointer. By the time both pointers reach the rightmost end, we finish our scan and see if total 
counts exceed k. Here is the implementation:

def enough(distance) -> bool:  # two pointers
    count, i, j = 0, 0, 0
    while i < n or j < n:
        while j < n and nums[j] - nums[i] <= distance:  # move fast pointer
            j += 1
        count += j - i - 1  # count pairs
        i += 1  # move slow pointer
    return count >= k
Obviously, our search space should be [0, max(nums) - min(nums)]. Now we are ready to copy-paste our template:

def smallestDistancePair(nums: List[int], k: int) -> int:
    nums.sort()
    n = len(nums)
    left, right = 0, nums[-1] - nums[0]
    while left < right:
        mid = left + (right - left) // 2
        if enough(mid):
            right = mid
        else:
            left = mid + 1
    return left
*/
class Solution {
    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);
        int lo = 0;
        int hi = nums[nums.length - 1] - nums[0];
        while(lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if(countPairs(nums, mid) >= k) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }
    
    private int countPairs(int[] nums, int limit) {
        int count = 0; 
        int len = nums.length;
        // two pointers
        int i = 0;
        int j = 0;
        while(i < len || j < len) {
            // move fast pointer
            while(j < len && nums[j] - nums[i] <= limit) {
                j++;
            }
            // count pairs
            count += j - i - 1;
            // move slow pointer
            i++;
        }
        return count;
    }
}
