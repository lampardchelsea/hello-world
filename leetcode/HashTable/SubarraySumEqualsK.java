/**
 * Refer to
 * https://leetcode.com/problems/subarray-sum-equals-k/description/
 * Given an array of integers and an integer k, you need to find the total number of continuous subarrays whose sum equals to k.
    Example 1:
    Input:nums = [1,1,1], k = 2
    Output: 2
    Note:
    The length of the array is in range [1, 20,000].
    The range of numbers in the array is [-1000, 1000] and the range of the integer k is [-1e7, 1e7].
 * 
 * Solution
 * https://leetcode.com/problems/subarray-sum-equals-k/solution/#approach-1-brute-force-time-limit-exceeded
 *
 * Complexity Analysis
 * Time complexity : O(n^2)
   Considering every possible subarray takes O(n^2)time. 
   Finding out the sum of any subarray takes O(1) time after the initial processing of O(n) 
   for creating the cumulative sum array.
   Space complexity : O(n). Cumulative sum array sumsum of size n+1 is used.
*/

// Solution 1: preSum way
/**
 Approach #2 Using Cummulative sum [Accepted]
 Algorithm
 Instead of determining the sum of elements everytime for every new subarray considered, we can make use of a 
 cumulative sum array , sum. Then, in order to calculate the sum of elements lying between two indices, we 
 can subtract the cumulative sum corresponding to the two indices to obtain the sum directly, instead of 
 iterating over the subarray to obtain the sum.
 In this implementation, we make use of a cumulative sum array, sum, such that sum[i] is used to 
 store the cumulative sum of nums array upto the element corresponding to the (i-1)th index. Thus, to 
 determine the sum of elements for the subarray nums[i:j], we can directly use sum[j+1] - sum[i].
*/
class Solution {
    public int subarraySum(int[] nums, int k) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        // Create preSum array
        int[] sum = new int[nums.length + 1];
        for(int i = 1; i <= nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i - 1];
        }
        int count = 0;
        for(int start = 0; start < nums.length; start++) {
            for(int end = start + 1; end <= nums.length; end++) {
                if(sum[end] - sum[start] == k) {
                    count++;
                }
            }
        }
        return count;
    }
}


// Solution 2: 
/**
 Approach #3 Without space [Accepted]
 Algorithm
 Instead of considering all the startstart and endend points and then finding the sum for each subarray 
 corresponding to those points, we can directly find the sum on the go while considering different end
 points. i.e. We can choose a particular start point and while iterating over the end points, 
 we can add the element corresponding to the end point to the sum formed till now. Whenver the sum
 equals the required kk value, we can update the count value. We do so while iterating over all 
 the end indices possible for every start index. Whenver, we update the start index, we need to 
 reset the sum value to 0.
 Complexity Analysis

Time complexity : O(n^2). We need to consider every subarray possible
Space complexity : O(1) Constant space is used.
*/
class Solution {
    public int subarraySum(int[] nums, int k) {
        int count = 0;
        for(int start = 0; start < nums.length; start++) {
            int sum = 0;
            for(int end = start; end < nums.length; end++) {
                sum += nums[end];
                if(sum == k) {
                    count++;
                }                
            }
        }
        return count;
    }
}

// Solution 3: HashMap
/**
 * Approach #4 Using hashmap [Accepted]
   Algorithm
   The idea behind this approach is as follows: If the cumulative sum(represented by sum[i] for sum upto ith index) 
   upto two indices is the same, the sum of the elements lying in between those indices is zero. Extending the same 
   thought further, if the cumulative sum upto two indices, say i and j is at a difference of k 
   i.e. if sum[i] - sum[j] = k, the sum of elements lying between indices i and j is k.
   Based on these thoughts, we make use of a hashmap which is used to store the cumulative sum upto all the 
   indices possible along with the number of times the same sum occurs. We store the data in the form: 
   (sum_i, no. of occurences of sum_i) We traverse over the array nums and keep on finding the cumulative sum. 
   Every time we encounter a new sum, we make a new entry in the hashmap corresponding to that sum. If the same 
   sum occurs again, we increment the count corresponding to that sum in the hashmap. 
   Further, for every sum encountered, we also determine the number of times the sum k has occured already, 
   since it will determine the number of times a subarray with sum k has occured upto the current index. 
   We increment the count by the same amount.
   After the complete array has been traversed, the count gives the required result.
   Complexity Analysis
   Time complexity : O(n) The entire nums array is traversed only once.
   Space complexity : O(n) Hashmap map can contain upto nn distinct entries in the worst case.
*/
class Solution {
    public int subarraySum(int[] nums, int k) {
        int count = 0;
        int sum = 0;
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        // Why it is necessary to put (0,1) in the map before the loop. { map.put(0,1)} ?
        // consider the case k = 3 and nums = [3]
        map.put(0, 1);
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if(map.containsKey(sum - k)) {
                count += map.get(sum - k);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }
}


// Why does hashmap + presum solution work ?
// Refer to
// https://leetcode.com/problems/subarray-sum-equals-k/discuss/102106/Java-Solution-PreSum-+-HashMap/172014
// Some comments on "Why does this work?"
class Solution {
    public int subarraySum(int[] nums, int k) {
        // Edge cases
        if(nums.length == 0)    return 0;
        
        // Sliding window -- No, contains negative number
        // hashmap + preSum
        /*
            1. Hashmap<sum[0,i - 1], frequency>
            2. sum[i, j] = sum[0, j] - sum[0, i - 1]    --> sum[0, i - 1] = sum[0, j] - sum[i, j]
                   k           sum      hashmap-key     -->  hashmap-key  =  sum - k
            3. now, we have k and sum.  
                  As long as we can find a sum[0, i - 1], we then get a valid subarray
                 which is as long as we have the hashmap-key,  we then get a valid subarray
            4. Why don't map.put(sum[0, i - 1], 1) every time ?
                  if all numbers are positive, this is fine
                  if there exists negative number, there could be preSum frequency > 1
        */
        HashMap<Integer, Integer> map = new HashMap<>();
        int sum = 0;
        int result = 0;
        map.put(0, 1);
        for(int cur : nums) {
            sum += cur;
            if(map.containsKey(sum - k))  // there exist a key, that [hashmap-key  =  sum - k]
                result += map.get(sum - k);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return result; 
    }
}



































https://leetcode.com/problems/subarray-sum-equals-k/
Given an array of integers nums and an integer k, return the total number of subarrays whose sum equals to k.
A subarray is a contiguous non-empty sequence of elements within an array.

Example 1:
Input: nums = [1,1,1], k = 2
Output: 2

Example 2:
Input: nums = [1,2,3], k = 3
Output: 2

Constraints:
- 1 <= nums.length <= 2 * 10^4
- -1000 <= nums[i] <= 1000
- -10^7 <= k <= 10^7
--------------------------------------------------------------------------------
Attempt 1: 2023-02-04
Solution 1:  Native for loop with 2 passes (30 min, first pass is create preSum array, second pass is calculate interval with nested for loop)
class Solution {
    public int subarraySum(int[] nums, int k) {
        int[] preSum = new int[nums.length + 1];
        preSum[0] = 0;
        for(int i = 1; i < preSum.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }
        int count = 0;
        for(int start = 0; start < preSum.length; start++) {
            for(int end = start + 1; end < preSum.length; end++) {
                if(preSum[end] - preSum[start] == k) {
                    count++;
                }
            }
        }
        return count;
    }
}

Time Complexity: O(n^2)  
Space Complexity: O(n)

Solution 2:  Hash Table + Auxiliary array (30 min)
class Solution { 
    public int subarraySum(int[] nums, int k) { 
        Map<Integer, Integer> map = new HashMap<Integer, Integer>(); 
        //map.put(0, 0); 
        map.put(0, 1); 
        int[] preSum = new int[nums.length + 1]; 
        int count = 0; 
        for(int i = 1; i <= nums.length; i++) { 
            preSum[i] = preSum[i - 1] + nums[i - 1]; 
            if(map.containsKey(preSum[i] - k)) { 
                count += map.get(preSum[i] - k); 
            } 
            map.put(preSum[i], map.getOrDefault(preSum[i], 0) + 1); 
        } 
        return count; 
    } 
}

Time Complexity: O(n) 
Space Complexity: O(n)

Refer to
https://leetcode.com/problems/subarray-sum-equals-k/solutions/803317/java-solution-with-detailed-explanation/
Thinking
1. Use an array to store the sum accumulated from the beginning to a certain position.
Example:
nums = [1,   2,   3  ]
sum  = [1, 1+2, 1+2+3]

2. How to create array "sum" ?
sum[i] = sum[i - 1] + nums[i]
Q : If i == 0, the index is out of range. How to solve this problem ?
A : Set the first element of the array "sum" to 0, and initialize the array "sum" from index 1 rather than 0.
nums = [1,   2,   3  ]
sum  = [0,   1,   1+2, 1+2+3] // Also, the length of "sum" is one more than "nums"  
sum[i] = sum[i - 1] + nums[i - 1]

// Java Version
int[] sum = new int[nums.length + 1];
sum[0] = 0;
for (int i = 1; i < (nums.length + 1); i++)
  sum[i] = sum[i - 1] + nums[i - 1];

3. Using array "sum" to calculate the sum of a subarray
sumOfSubarray = sum[end] - sum[start];
For example : Calculate the sum of "nums" means using the last element of "sum" minus the first element of "sum" which is 0.
nums[0] + nums[1] + nums[2] = sum[3] - sum[0] = 6 - 0

4.Using array "sum"to caculate all possibilities .
Code
public class Solution {
    public int subarraySum(int[] nums, int k) {
        int count = 0;
      
        int[] sum = new int[nums.length + 1];
        sum[0] = 0;
        for (int i = 1; i <= nums.length; i++)
            sum[i] = sum[i - 1] + nums[i - 1];
      
        for (int start = 0; start < sum.length; start++) {
            for (int end = start + 1; end < sum.length; end++) {
                if (sum[end] - sum[start] == k)
                    count++;
            }
        }
      
        return count;
    }
}
Complexity Analysis
- Time complexity : O(n^2).
- Space complexity : O(n).
Optimization by Hashmap
Thinking
1.In the previous method
Step 1. The "nums" array is traversed to calculate all the elements of the sum array
Step 2. Use the nested loop to judge.
key : Can we judge when the array is traversed(Step 1) ?
Transposition
int[] sum = new int[nums.length + 1];
sum[0] = 0;
for (int end = 1; end < (nums.length + 1); end++)
  sum[end] = sum[end - 1] + nums[end - 1];
a. Put each element of "sum" array into hashmap according to this format : (sumi, number of occurence)
b. When constructing the "sum" array, we take the currently constructed element as sum[end], then all the elements before "end" which have been calculated can be regarded as all sum[start] for this "end".
Transform the judgment condition
Obviously, when sum[end] is calculated, all its possible sum[start] are already in the map.
sum[end] - sum[start] == k
sum[end] - k == sum[start]
c. When sumend is calculated, we only need to determine whether there is key == sumend - k in the hashmap and add the number of occurrence to the answer.
Attention : In the previous method, we set the first element of sum to 0. Similarly, we put it in the hashmap, which is (0, 1).
Code
public class Solution {
    public int subarraySum(int[] nums, int k) {
        int count = 0, sum = 0;
        HashMap < Integer, Integer > map = new HashMap < > ();
        map.put(0, 1);
      
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k))
                count += map.get(sum - k);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }
}
Complexity Anaysis
- Time complexity : O(n).
- Space complexity : O(n).
--------------------------------------------------------------------------------
Solution 3:  Hash Table + Single Varaiable (30 min)
Actually no need preSum array, we can replace its functionality with a single variable 'presSum'
class Solution {
    public int subarraySum(int[] nums, int k) {
        int count = 0;
        int preSum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        for(int num : nums) {
            preSum += num;
            count += map.getOrDefault(preSum - k, 0);
            map.put(preSum, map.getOrDefault(preSum, 0) + 1);
        }
        return count;
    }
}

Time Complexity: O(n) 
Space Complexity: O(1)

Refer to
https://leetcode.com/problems/subarray-sum-equals-k/solutions/102106/java-solution-presum-hashmap/
Solution 1. Brute force. We just need two loops (i, j) and test if SUM[i, j] = k. Time complexity O(n^2), Space complexity O(1). I bet this solution will TLE.
Solution 2. From solution 1, we know the key to solve this problem is SUM[i, j]. So if we know SUM[0, i - 1] and SUM[0, j], then we can easily get SUM[i, j]. To achieve this, we just need to go through the array, calculate the current sum and save number of all seen PreSum to a HashMap. Time complexity O(n), Space complexity O(n).
public class Solution {
    public int subarraySum(int[] nums, int k) {
        int sum = 0, result = 0;
        Map<Integer, Integer> preSum = new HashMap<>();
        preSum.put(0, 1);
        
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (preSum.containsKey(sum - k)) {
                result += preSum.get(sum - k);
            }
            preSum.put(sum, preSum.getOrDefault(sum, 0) + 1);
        }
        
        return result;
    }
}
A harder problem
https://leetcode.com/problems/number-of-submatrices-that-sum-to-target/
https://leetcode.com/problems/number-of-submatrices-that-sum-to-target/discuss/803353/java-solution-with-detailed-explanation

Refer to
L713.Subarray Product Less Than K
L724.Find Pivot Index
L1074.Number of Submatrices That Sum to Target (Ref.L560)
L1658.Minimum Operations to Reduce X to Zero (Ref.L1423)
L2090.K Radius Subarray Averages (Ref.L560)
L2219.Maximum Sum Score of Array (Ref.L560)
