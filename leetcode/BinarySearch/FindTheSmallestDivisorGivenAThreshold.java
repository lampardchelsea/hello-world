/**
Refer to
https://leetcode.com/problems/find-the-smallest-divisor-given-a-threshold/
Given an array of integers nums and an integer threshold, we will choose a positive integer divisor, 
divide all the array by it, and sum the division's result. Find the smallest divisor such that the 
result mentioned above is less than or equal to threshold.

Each result of the division is rounded to the nearest integer greater than or equal to that element. 
(For example: 7/3 = 3 and 10/2 = 5).

It is guaranteed that there will be an answer.

Example 1:
Input: nums = [1,2,5,9], threshold = 6
Output: 5
Explanation: We can get a sum to 17 (1+2+5+9) if the divisor is 1. 
If the divisor is 4 we can get a sum of 7 (1+1+2+3) and if the divisor is 5 the sum will be 5 (1+1+1+2). 

Example 2:
Input: nums = [44,22,33,11,1], threshold = 5
Output: 44

Example 3:
Input: nums = [21212,10101,12121], threshold = 1000000
Output: 1

Example 4:
Input: nums = [2,3,5,7,11], threshold = 11
Output: 3

Constraints:
1 <= nums.length <= 5 * 104
1 <= nums[i] <= 106
nums.length <= threshold <= 106
*/

// Solution 1:
// Refer to
// https://leetcode.com/discuss/general-discussion/786126/python-powerful-ultimate-binary-search-template-solved-many-problems
/**
Given an array of integers nums and an integer threshold, we will choose a positive integer divisor and divide all the 
array by it and sum the result of the division. Find the smallest divisor such that the result mentioned above is less 
than or equal to threshold.

Each result of division is rounded to the nearest integer greater than or equal to that element. (For example: 7/3 = 3 
and 10/2 = 5). It is guaranteed that there will be an answer.

Example :
Input: nums = [1,2,5,9], threshold = 6
Output: 5
Explanation: We can get a sum to 17 (1+2+5+9) if the divisor is 1. 
If the divisor is 4 we can get a sum to 7 (1+1+2+3) and if the divisor is 5 the sum will be 5 (1+1+1+2). 
After so many problems introduced above, this one should be a piece of cake. We don't even need to bother to design a condition 
function, because the problem has already told us explicitly what condition we need to satisfy.

def smallestDivisor(nums: List[int], threshold: int) -> int:
    def condition(divisor) -> bool:
        return sum((num - 1) // divisor + 1 for num in nums) <= threshold

    left, right = 1, max(nums)
    while left < right:
        mid = left + (right - left) // 2
        if condition(mid):
            right = mid
        else:
            left = mid + 1
    return left
*/
class Solution {
    public int smallestDivisor(int[] nums, int threshold) {
        int lo = 1;
        int hi = 0;
        for(int num : nums) {
            hi = Math.max(hi, num);
        }
        while(lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if(countNum(nums, mid) <= threshold) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }
    
    private int countNum(int[] nums, int x) {
        int count = 0;
        for(int num : nums) {
            count += (num - 1) / x + 1;
        }
        return count;
    }
}



Attempt 1: 2022-09-07 (10min, problem convert to find the first 'lo' match the requirement, means find the lower boundary of 'lo', use the template directly)

```
class Solution { 
    public int smallestDivisor(int[] nums, int threshold) { 
        int hi = 0; 
        for(int num : nums) { 
            hi = Math.max(hi, num); 
        } 
        int lo = 1; 
        while(lo <= hi) { 
            int mid = lo + (hi - lo) / 2; 
            // If sum of division's result larger than threashold means  
            // 'mid' (divisor) still smaller than requirement, need to 
            // increase 'lo', the problem convert to find the first 'lo' 
            // match the requirement, means find the lower boundary of 'lo' 
            if(sumOfDivision(nums, mid) > threshold) { 
                lo = mid + 1; 
            } else { 
                hi = mid - 1; 
            } 
        } 
        return lo; 
    } 
     
    private int sumOfDivision(int[] nums, int divisor) { 
        int count = 0; 
        for(int num : nums) { 
            count += (num - 1) / divisor + 1; 
        } 
        return count; 
    } 
}

Space Complexity: O(1) 
Time Complexity: O(nlogn)
```

Refer to
https://leetcode.com/problems/find-the-smallest-divisor-given-a-threshold/discuss/1001055/JAVA-oror-Binary-Search
```
class Solution {
    public int smallestDivisor(int[] nums, int threshold) {
        int left = 1, right = 1000000;
        
        while(left <= right){
            int mid = left+(right-left)/2;
            long sum = getDivSum(nums, mid);
            if(sum > threshold){
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return left;
    }
    
    private long getDivSum(int[] nums, int divisor){
        long sum = 0;
        for(int num: nums){
            sum += (num-1)/divisor+1; //ceil of (num/divisor)
        }
        
        return sum;
    }
}
```

