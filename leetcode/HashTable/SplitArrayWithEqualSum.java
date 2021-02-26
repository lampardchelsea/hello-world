/**
Refer to
https://www.lintcode.com/problem/877/
Given an array with n integers, you need to find if there are triplets (i, j, k) which satisfies following conditions:

0 < i, i + 1 < j, j + 1 < k < n - 1
Sum of subarrays (0, i - 1), (i + 1, j - 1), (j + 1, k - 1) and (k + 1, n - 1) should be equal.
We define that subarray (L, R) represents a slice of the original array starting from the element indexed L to the element indexed R.

If it exists, return true, otherwise return false.

Example
Example 1:
Input: [1,3,2,1,3,2,1,3,2,1,3]
Output: true
Explanation: (2,5,8) => Four subarrays are all [1, 3].

Example 2:
Input: [1,2,1,2,1,2,1]
Output: true
Explanation: (1,3,5)

Notice
1.1 <= n <= 2000.
2. Elements in the given array will be in range [-1,000,000, 1,000,000].
*/

// Solution 1 to 4 Brute Force to PreSum
// Refer to
// https://xiaoguan.gitbooks.io/leetcode/content/LeetCode/548-split-array-with-equal-sum-medium.html
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/HashTable/Document/Split_Array_with_Equal_Sum.docx

// Solution 5: PreSum + HashSet
// Refer to
// https://xiaoguan.gitbooks.io/leetcode/content/LeetCode/548-split-array-with-equal-sum-medium.html
/**
Approach #5 Using Cumulative Sum and HashSet [Accepted]
Algorithm
In this approach, firstly we form a cumulative sum array sum, where sum[i] stores the cumulative sum of the array 
nums upto the i^{th} index. Then, we start by traversing over the possible positions for the middle cut formed by j. 
For every j, firstly, we find all the left cut's positions,i, that lead to equalizing the sum of the first and the 
second quarter (i.e. sum[i−1]=sum[j−1]−sum[i]) and store such sums in the set(a new HashSet is formed for every j chosen). 
Thus, the presence of a sum in set implies that such a sum is possible for having equal sum of the first and second 
quarter for the current position of the middle cut (j).
Then, we go for the right cut and find the position of the right cut that leads to equal sum of the third and the fourth 
quarter(sum[n-1]-sum[k]=sum[k-1] - sum[j]), for the same middle cut as chosen earlier. We also, look if the same sum 
exists in the set. If so, such a triplet (i, j, k) exists which satisfies the required criteria, otherwise not.
Look at the animation below for a visual representation of the process:

1≤i≤n−6
i+2≤j≤n−4
j+2≤k≤n−2

i=0         1               2                        3
----------------------------------------------------------------------------------
sum[0]   sum[1]           sum[2]                   sum[3] ...
nums[0]  nums[0]+nums[1]  nums[0]+nums[1]+nums[2]  nums[0]+nums[1]+nums[2]+nums[3]
----------------------------------------------------------------------------------
e.g 
explain 'int sum1 = sum[i - 1]'
for i = 1, sum1 means sum of all numbers before index = 1, 
should be nums[0] which mapping to sum[i - 1] as sum[0]

for i = 2, sum1 means sum of all numbers before index = 2, 
should be nums[0] + nums[1] which mapping to sum[i - 1] as sum[1]

for i = 3, sum1 means sum of all numbers before index = 3, 
should be nums[0]+nums[1]+nums[2] which mapping to sum[i - 1] as sum[2]

explain 'int sum2 = sum[j - 1] - sum[i]'
for i = 1, j = 3, sum2 means sum of all numbers after index = 1 and before 3,
should be nums[2] which mapping to sum[j - 1] - sum[i] as sum[2] - sum[1]
...etc.

c++ 189ms
class Solution {
public:
    bool splitArray(vector<int>& nums) {
        if (nums.size() < 7) return false;
        int n = nums.size();
        vector<int> sum(n); sum[0] = nums[0];
        for (int i = 1; i < n; ++i) sum[i] = sum[i-1]+nums[i];
        for (int j = 3; j < n-3; ++j) {
            unordered_set<int> s;
            for (int i = 1; i < j-1; ++i) {
                if (sum[i-1] == sum[j-1]-sum[i]) s.insert(sum[i-1]);
            }
            for (int k = j+2; k < n-1; ++k) {
                if (sum[n-1]-sum[k] == sum[k-1]-sum[j] && s.count(sum[n-1]-sum[k])) return true;
            }
        }
        return false;
    }
};

Complexity Analysis
•	Time complexity: O(n^2). One outer loop and two inner loops are used.
•	Space complexity: O(n). HashSet size can go upto n
*/
public class Solution {
    /**
     * @param nums: a list of integer
     * @return: return a boolean
     */
    public boolean splitArray(List<Integer> nums) {
        // write your code here
        int n = nums.size();
        if(n < 7) {
            return false;
        }
        int[] sum = new int[n];
        sum[0] = nums.get(0);
        for(int i = 1; i < n; i++) {
            sum[i] = sum[i - 1] + nums.get(i);
        }
        for(int j = 3; j < n - 3; j++) {
            Set<Integer> set = new HashSet<Integer>();
            for(int i = 1; i < j - 1; i++) {
                if(sum[i - 1] == sum[j - 1] - sum[i]) {
                    set.add(sum[i - 1]);
                }
            }
            for(int k = j + 2; k < n - 1; k++) {
                if(sum[n - 1] - sum[k] == sum[k - 1] - sum[j] && set.contains(sum[n - 1] - sum[k])) {
                    return true;
                }
            }
        }
        return false;
    }
}
