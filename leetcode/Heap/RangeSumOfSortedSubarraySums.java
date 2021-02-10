/**
Refer to
https://leetcode.com/problems/range-sum-of-sorted-subarray-sums/
Given the array nums consisting of n positive integers. You computed the sum of all non-empty continous 
subarrays from the array and then sort them in non-decreasing order, creating a new array of n * (n + 1) / 2 numbers.

Return the sum of the numbers from index left to index right (indexed from 1), inclusive, in the new array. 
Since the answer can be a huge number return it modulo 10^9 + 7.

Example 1:
Input: nums = [1,2,3,4], n = 4, left = 1, right = 5
Output: 13 
Explanation: All subarray sums are 1, 3, 6, 10, 2, 5, 9, 3, 7, 4. After sorting them in non-decreasing order 
we have the new array [1, 2, 3, 3, 4, 5, 6, 7, 9, 10]. The sum of the numbers from index le = 1 to ri = 5 is 1 + 2 + 3 + 3 + 4 = 13. 

Example 2:
Input: nums = [1,2,3,4], n = 4, left = 3, right = 4
Output: 6
Explanation: The given array is the same as example 1. We have the new array [1, 2, 3, 3, 4, 5, 6, 7, 9, 10]. 
The sum of the numbers from index le = 3 to ri = 4 is 3 + 3 = 6.

Example 3:
Input: nums = [1,2,3,4], n = 4, left = 1, right = 10
Output: 50

Constraints:
1 <= nums.length <= 10^3
nums.length == n
1 <= nums[i] <= 100
1 <= left <= right <= n * (n + 1) / 2
*/

// Solution 1: Brute Force
class Solution {
    public int rangeSum(int[] nums, int n, int left, int right) {
        int[] temp = new int[n * (n + 1) / 2];
        int k = 0;
        int mod = 1000000007;
        for(int i = 0; i < n; i++) {
            int sum = 0;
            for(int j = i; j < n; j++) {
                sum += nums[j];
                temp[k++] = sum;
            }
        }
        Arrays.sort(temp);
        int result = 0;
        for(int i = left; i <= right; i++) {
            result = (result + temp[i - 1]) % mod;
        }
        return result;
    }
}

// Solution 2: Use a min-heap to store the sums for subarrays
// Refer to
// https://shawnlyu.com/leetcode/leetcode1508-range-sum-of-sorted-subarray-sums/
/**
This question first appeared in Biweekly Leetcode Contest 30. Its brute force approach is quite straightforward and 
also is able to pass the test cases. However, there are another two efficient solutions and, especially, 
the third one improves the time complexity from O(n^2logn) to O(nlog(sum(nums))).

Brute force – O(n^2logn)
Follow the context and ‘translate’ it into Python: use a nested for-loop to find the summation of all subarrays and 
return [left:right]. Computing sums for all subarrays takes O(n^2) and sorting them takes O((n^2)log((n^2))), 
which gives O(n^2logn)

class Solution:
    def rangeSum(self, nums: List[int], n: int, left: int, right: int) -> int:
        sums = []
        n = len(nums)
        for i in range(n):
            s = 0
            for j in range(i,n):
                s += nums[j]
                sums.append(s)
        return sum(sorted(sums)[left-1:right]) % (10**9 + 7)

Optimisation 1 – Priority queue – O(n^2logn), we don’t have to compute sums for all subarrays, and this approach saves time by 
computing sums for only top right subarrays. Although this approach still has O(n^2logn) as the worst cases time complexity.

To find the sum for one subarray starting with nums[a], we could keep adding the next element to it so sums for the subarrays 
starting with nums[a] would be: nums[a],nums[a]+nums[a+1],....With that being said, we could use a min-heap to store (cur_sum,next) 
where cur_sum is the sum for nums[a]+...+nums[cur] and next is the index of the next element to be added. Therefore we could 
generate the sums for subarrays in increasing order, and we would stop until we have right sums.

Use a min-heap to store the sums for subarrays:
Given array: (1,2,3,4) and create initial Pair(nums[i],i+1) 
then offer to minPQ: (1,1), (2,2), (3,3), (4,4)
minPQ sort by first element as sum

Step 1 -> pop: (1,1) ==> (1 + nums[1],2) = (3,2)
       -> push: (2,2), (3,2), (3,3), (4,4)

Step 2 -> pop: (2,2) ==> (2 + nums[2],3) = (5,3)
       -> push: (3,2), (3,3), (4,4), (5,3)

...etc.

class Solution:
    def rangeSum(self, nums: List[int], n: int, left: int, right: int) -> int:
        pq = []
        ret = 0
        for i in range(n):
            heapq.heappush(pq,(nums[i],i+1))
        for i in range(1,right+1):
            cur_sum,_next = heapq.heappop(pq)
            if i >= left:
                ret += cur_sum
            if _next<n:
                heapq.heappush(pq,(cur_sum+nums[_next],_next+1))
        return ret % (10**9 + 7)
*/

// https://leetcode.com/problems/range-sum-of-sorted-subarray-sums/discuss/730511/C++-priority_queue-solution/612996
