https://leetcode.com/problems/number-of-subarrays-with-gcd-equal-to-k/description/
Given an integer array nums and an integer k, return the number of subarrays of nums where the greatest common divisor of the subarray's elements is k.
A subarray is a contiguous non-empty sequence of elements within an array.
The greatest common divisor of an array is the largest integer that evenly divides all the array elements.
 
Example 1:
Input: nums = [9,3,1,2,6,3], k = 3
Output: 4
Explanation: The subarrays of nums where 3 is the greatest common divisor of all the subarray's elements are:
- [9,3,1,2,6,3]
- [9,3,1,2,6,3]
- [9,3,1,2,6,3]
- [9,3,1,2,6,3]

Example 2:
Input: nums = [4], k = 7
Output: 0
Explanation: There are no subarrays of nums where 7 is the greatest common divisor of all the subarray's elements.
 
Constraints:
- 1 <= nums.length <= 1000
- 1 <= nums[i], k <= 10^9
--------------------------------------------------------------------------------
Attempt 1: 2024-01-27
Solution 1: Brute Force (30 min)
class Solution {
    public int subarrayGCD(int[] nums, int k) {
        int count = 0;
        int n = nums.length;
        // Outer loop to iterate over the start of the subarray
        for(int i = 0; i < n; i++) {
            int gcdVal = 0;
            // Inner loop to iterate over the end of the subarray
            for(int j = i; j < n; j++) {
                // Update gcdValue with the current num
                gcdVal = gcd(gcdVal, nums[j]);
                if(gcdVal == k) {
                    count++;
                }
            }
        }
        return count;
    }

    private int gcd(int a, int b) {
        if(b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
}

Time Complexity: O(N^2)
Space Complexity: O(N)

Why we cannot use sliding window to resolve LeetCode 2447. Number of Subarrays With GCD Equal to K ?
Answer from chatGPT
The problem of finding the number of subarrays with a specific greatest common divisor (GCD) can be challenging, and using a sliding window approach may not be straightforward or efficient for this particular problem.
The reason is that the GCD is a non-trivial mathematical operation, and it is not easy to compute the GCD of a subarray efficiently within the sliding window framework. Additionally, the GCD operation is not additive or subtractive, which means that combining the results of GCDs from different subarrays is not straightforward.
To solve the "Number of Subarrays With GCD Equal to K" problem efficiently, you may need to use a different approach, such as dynamic programming or number theory. You could explore techniques like prefix sums or precomputing GCDs for certain subarrays to speed up the computations. Understanding the properties of the GCD and how it behaves in the context of subarrays is crucial for devising an efficient algorithm for this specific problem.
If you have a specific code or approach you'd like me to review or discuss further, please provide more details, and I'll do my best to assist you.
To understand why using a sliding window approach might not be straightforward for solving "LeetCode 2447. Number of Subarrays With GCD Equal to K" with the given input [9,3,1,2,6,3] and k = 3, let's break down the problem and identify the challenges:
The problem involves finding the number of subarrays with a specific greatest common divisor (GCD), in this case, k = 3.
1.GCD Computation Complexity:
- Computing the GCD of a subarray efficiently within a sliding window can be challenging. GCD is not a simple operation like addition or subtraction. It involves finding the greatest common divisor of multiple numbers, and the sliding window might not naturally lend itself to efficient GCD computations.
2.Dynamic Nature of GCD:
- The GCD changes dynamically as the elements in the window slide. Unlike problems where a simple calculation can be performed on each window to update the result, GCD is more complex and requires a deeper understanding of the changing factors.
3.Non-Monotonic Property:
- The GCD of a subarray is not necessarily monotonic. This means that the GCD of a subarray may not always increase or decrease as we move the window. This non-monotonic behavior makes it challenging to apply a sliding window strategy straightforwardly.
4.Complex Dependencies:
- The GCD of a subarray depends not only on the elements within the current window but also on the previous state of the GCD. This introduces additional complexity and makes it difficult to devise a sliding window strategy that efficiently updates and maintains the GCD information.
To solve this problem efficiently, you may need to explore alternative approaches, such as dynamic programming or number theory, and take advantage of properties specific to GCD calculations. This problem might benefit from a more specialized algorithm rather than a generic sliding window approach due to the nature of the GCD operation and its dynamic behavior across subarrays.

Refer to
https://algo.monster/liteproblems/2447
Problem Description
This problem asks us to determine the count of subarrays within a given integer array nums such that the greatest common divisor (GCD) of all the numbers in each subarray equals a specified integer k. A subarray is defined as a contiguous, non-empty sequence of elements from the array. The GCD of an array is the largest integer that divides every element of the array without leaving a remainder.
The task is to explore all possible subarrays within nums and determine which of them have a GCD that matches k. The final output should be the total number of these qualifying subarrays.
Intuition
The intuition behind the solution is based on incrementally verifying every possible subarray. The traditional brute-force approach starts with an index i and iteratively expands to include each subsequent element, recalculating the GCD each time with the new element included. This brute force method works by calculating the GCD of each subarray starting with a single element and expanding the subarray one element at a time, keeping track of the GCD as it grows. If the GCD matches k at any point, that configuration is counted as a valid subarray.
By starting from every possible index i in the array, we ensure that we do not miss any potential subarrays. For each fixed starting index i, we expand the subarray by adding one element at a time to the end, which we denote as x coming from the slice nums[i:] in the code. We keep updating the GCD with the help of the gcd function, which computes the GCD of two numbers.
Whenever the GCD of the current subarray configuration (g) equates to k, we increment our answer (ans) indicating that we have found a subarray that satisfies the given condition. The process then repeats for all start and end index combinations. Although this method has a higher time complexity as it employs nested loops to check all subarrays, it is straightforward and makes use of the mathematical properties of GCD to identify qualifying subarrays.
Solution Approach
The implemented solution approach relies on a nested loop construct to evaluate each subarray's GCD within the input nums array. The outer loop determines the starting position of the subarray, and the inner loop expands the subarray to include additional elements one by one. Here's a breakdown of how the algorithm operates:
1.We iterate over the array nums using an outer loop with the index i which defines the start of the subarray. This loop goes from the first element to the last in nums.
2.For each starting index i, we initialize a variable g which will hold the GCD of the current subarray. Initially, g is set to 0, representing an "empty" subarray state.
3.We then enter an inner loop starting at i and going to the end of nums. Within this loop, we use the gcd function to calculate the new GCD each time an additional element (x) is included in the subarray. The gcd function is called with the current value of g and the new element x from nums.
4.The result of the gcd function is then used to update g, which now holds the GCD of the subarray starting at index i and ending at the current position of the inner loop.
5.If at any point the value of g (the GCD of the current subarray) equals the target value k, we increment a counter variable ans since we've found a qualifying subarray.
6.After all elements have been considered for the subarray starting with index i, we repeat the process for the next start index.
This approach does not use any sophisticated data structures; it simply depends on two variables—g for tracking the GCD and ans for tracking the count of valid subarrays. The computation leans heavily on the mathematical properties of GCD, particularly that the GCD of any two numbers remains unchanged or becomes smaller when more elements are introduced into the calculation.
The complexity of the solution lies in the nested loops, making the time complexity O(n^2) in the worst case, where n is the length of nums. This occurs because for each starting index, the inner loop could, in the worst case, iterate over the entire array size to form the subarray.
Despite the relatively simple brute-force nature of the approach, it is effective for solving this specific problem. However, for larger arrays or constraints requiring better performance, a more optimized solution would be necessary to reduce the time complexity of the problem.
Example Walkthrough
Let's walk through a small example to illustrate the solution approach. Suppose we have the following input:
nums = [2, 4, 6, 3, 9]
k = 3
We want to find all subarrays whose GCD is equal to 3.
1.Start with i = 0 and g = 0.
- The subarray starting at index 0 is [2]. The GCD (g) of [2] is 2, which is not equal to k (3).
2.Increment i to 1 and reset g to 0 (Not monopoly increase).
- The subarray starting at index 1 is [4]. The GCD (g) of [4] is 4, which is not equal to k (3).
3.Increment i to 2 and reset g to 0 (Not monopoly increase).
- The subarray [6] has a GCD (g) of 6, which is not equal to k (3).
- Expand the subarray to [6, 3]. The GCD (g) of [6, 3] is 3, which is equal to k. We increment ans to 1.
4.Increment i to 3 and reset g to 0 (Not monopoly increase).
- The subarray [3] has a GCD (g) of 3, which is equal to k. Increment ans to 2.
- Expand the subarray to [3, 9]. The GCD (g) of [3, 9] is 3, which is equal to k. Increment ans to 3.
5.Increment i to 4 and reset g to 0 (Not monopoly increase).
- The subarray [9] has a GCD (g) of 9, which is not equal to k (3).
After checking all possible subarrays, we end up with a total count of 3 subarrays for which the GCD equals k. Hence, ans is 3, and that is our solution.
In this example, the process of expanding subarrays and finding their GCDs demonstrates the straightforward but computationally intensive nature of the algorithm, exemplifying how it operates in practice.
Java Solution
class Solution {

    /**
     * Counts the number of subarrays that have a GCD equal to k.
     *
     * @param nums The array of integers to be checked.
     * @param k    The GCD value that subarrays need to match.
     * @return The count of subarrays with GCD equal to k.
     */
    public int subarrayGCD(int[] nums, int k) {
        int length = nums.length; // Use a clearer variable name for array length
        int count = 0; // Use 'count' to represent the number of valid subarrays

        // Outer loop to iterate over the start of the subarray
        for (int i = 0; i < length; ++i) {
            int gcdValue = 0; // This will hold the GCD of the subarray starting at 'i'

            // Inner loop to iterate over the end of the subarray
            for (int j = i; j < length; ++j) {
                // Update gcdValue with the current num
                gcdValue = gcd(gcdValue, nums[j]);

                // Check if the current GCD equals to k; if so, increment the count
                if (gcdValue == k) {
                    ++count;
                }
            }
        }

        // Return the total count of subarrays with GCD equal to k
        return count;
    }

    /**
     * Calculates the greatest common divisor (GCD) of two numbers.
     *
     * @param a The first number.
     * @param b The second number.
     * @return The GCD of a and b.
     */
    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b); // Recursive implementation of the Euclidean algorithm
    }
}
Time and Space Complexity
Time Complexity
The given function calculates the number of subarrays with a GCD equal to k. It uses two nested loops. The outer loop runs from the start of the nums array to its end. For each iteration of the outer loop, denoted by index i, the inner loop runs from the current index i to the end of the array. In the worst case, where i starts at 0, this is n iterations for the inner loop. As i increases, the inner loop's range decreases, but in a big-O notation sense, this still leads to an overall time complexity of about (1/2)n(n+1), which is O(n^2) (quadratic time complexity), where n is the length of nums.
At each step of the inner loop, the greatest common divisor (GCD) is updated and compared against k. The gcd function itself has a time complexity which is effectively O(log(min(a, b))) where a and b are the numbers for which the GCD is being computed. However, in the worst case, since these are elements of the array which can vary widely, we often consider this part constant because it doesn't scale with n substantially.
Thus, the time complexity is dominated by the nested loops and is typically expressed as O(n^2).
Space Complexity
The space complexity of the function is O(1) which means constant space. This is because the space used does not grow with the input size n. The only extra variables used are g and ans which are used for calculating the GCD and for keeping the running count of subarrays whose GCD equals k, respectively.
--------------------------------------------------------------------------------
Solution 2: Count GCDs O(n log m)
仅做记录，不做研究
Refer to
https://leetcode.com/problems/number-of-subarrays-with-gcd-equal-to-k/solutions/2734442/brute-force-vs-count-gcds/
For an element num[i], we count GCDs produced by subarrays:
- nums[i - 1]
- nums[i - 2], nums[i - 1]
- nums[i - 3], nums[i - 2], nums[i - 1]
- ...and so on.
The number of distinct GCDs would not exceed log(m), where m is the maximum value in the array.
Note that those disticnt GCDs should share a factor k, and come from subarrays ending at some position.To produce a different GCD, gcd(n[i], gcd(n[i - 2], n[i - 1]) should be bigger than gcd(n[i - 2], n[i - 1]).Therefore n[i - 1] < n[i], and n[i - 1] * f == n[i].The best we can do is something like [2, 4, 8, 16, 32, ...], and the number of unique GCDs will therefore be log m.
int subarrayGCD(vector<int>& nums, int k) {
    int res = 0;
    unordered_map<int, int> gcds;
    for (int i = 0; i < nums.size(); ++i) {
        unordered_map<int, int> gcds1;
        if (nums[i] % k == 0) {
            ++gcds[nums[i]];
            for (auto [prev_gcd, cnt] : gcds)
                gcds1[gcd(prev_gcd, nums[i])] += cnt;
        }
        res += gcds1[k];
        swap(gcds, gcds1);
    }
    return res;
}

https://leetcode.com/problems/number-of-subarrays-with-gcd-equal-to-k/solutions/2734442/brute-force-vs-count-gcds/comments/1657826
class Solution {
    public int subarrayGCD(int[] nums, int k) {
        // since smallest prime number is 2, so the min gap between the neighbor numbers in the gcd list is at least 2 times. 
        // so the gcd list's length is less than 32
        // since we should store the previous gcd list and the current gcd list, 64 is enough. 
        int result = 0, list[][] = new int[64][2], prevIndex = 0, currentIndex = 0;
        for (int gcd : nums) {
            if (gcd < k || gcd % k != 0) {
                prevIndex = prevIndex >= 32 ? 32 : 0; // clear the previous gcd list
                continue;
            }
            list[currentIndex++] = new int[] {gcd, 1};
            for (int i = prevIndex >= 32 ? 32 : 0; i < prevIndex; i++)
                if (list[currentIndex - 1][0] == (gcd = getGcd(gcd, list[i][0]))) list[currentIndex - 1][1] += list[i][1];
                else list[currentIndex++] = new int[] {gcd,list[i][1]};
            if (list[currentIndex - 1][0] == k) result += list[currentIndex - 1][1];
            currentIndex = (prevIndex = currentIndex) >= 32 ? 0 : 32; // swap the previous gcd list and the current gcd list
        }
        return result;
    }

    int getGcd(int a, int b) {return a == 0 ? b : getGcd(b % a, a);}
}
