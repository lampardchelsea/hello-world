/**
Refer to
https://leetcode.com/problems/count-number-of-teams/
There are n soldiers standing in a line. Each soldier is assigned a unique rating value.

You have to form a team of 3 soldiers amongst them under the following rules:

Choose 3 soldiers with index (i, j, k) with rating (rating[i], rating[j], rating[k]).
A team is valid if: (rating[i] < rating[j] < rating[k]) or (rating[i] > rating[j] > rating[k]) where (0 <= i < j < k < n).
Return the number of teams you can form given the conditions. (soldiers can be part of multiple teams).

Example 1:
Input: rating = [2,5,3,4,1]
Output: 3
Explanation: We can form three teams given the conditions. (2,3,4), (5,4,1), (5,3,1). 

Example 2:
Input: rating = [2,1,3]
Output: 0
Explanation: We can't form any team given the conditions.

Example 3:
Input: rating = [1,2,3,4]
Output: 4

Constraints:
n == rating.length
3 <= n <= 1000
1 <= rating[i] <= 105
All the integers in rating are unique.
*/

// Solution 1: Native O(N^3) TLE
class Solution {
    public int numTeams(int[] rating) {
        int count = 0;
        int len = rating.length;
        for(int i = 0; i < len - 2; i++) {
            for(int j = i + 1; j < len - 1; j++) {
                for(int k = j + 1; k < len; k++) {
                    if(rating[i] < rating[j] && rating[j] < rating[k] || 
                      rating[i] > rating[j] && rating[j] > rating[k]) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}

// Solution 2: O(N^2)
// Refer to
// https://leetcode.com/problems/count-number-of-teams/discuss/555441/JavaC%2B%2B-100-O(N2)-Easy-To-Understand-With-Explanation
/**
Since N = 200, brute force is pretty fine for this problem.
But not sure if interviewer is expecting the same. So, optimizing to O(N^2) is pretty easy once we can see the tripplets:

We need to count tripplets {arr[i] < arr[j] < arr[k]} and {arr[i] > arr[j] > arr[k]} where i<j<k.
So, let's find for every j count of all i and k, so that it will follow either of above 2 conditons and summarize the counts:

Example: [13, 3, 4, 10, 7, 8]
13:

for {arr[i] < arr[j] < arr[k]} tripplets, Nothing smaller from left side.
for {arr[i] > arr[j] > arr[k]} tripplets, Nothing larger from left side.
3:

for {arr[i] < arr[j] < arr[k]} tripplets, Nothing smaller from left side.
for {arr[i] > arr[j] > arr[k]} tripplets, Nothing smaller from right side.
4:

for {arr[i] < arr[j] < arr[k]} tripplets,1 number is smaller and 3 are larger, total = 1*3 = 3 tripplets => {3, 4, 10}, {3, 4, 7}, {3, 4, 8}
for {arr[i] > arr[j] > arr[k]} tripplets, Nothing smaller from right side.
10:

for {arr[i] < arr[j] < arr[k]} tripplets,Nothing larger from right side.
for {arr[i] > arr[j] > arr[k]} tripplets, 1 number is bigger and 2 numbers are smaller, total = 1*2 = 2 tripplets => {13, 10, 7}, {13, 10, 8}
7:

for {arr[i] < arr[j] < arr[k]} tripplets, 2 numbers are smaller and 1 is larger, total = 2*1 = 2 => {3, 7, 8}, {4, 7, 8}
for {arr[i] > arr[j] > arr[k]} tripplets, Nothing smaller from right side.
8:

for {arr[i] < arr[j] < arr[k]} tripplets, Nothing larger from right side.
for {arr[i] > arr[j] > arr[k]} tripplets, Nothing smaller from right side.
Total = 3 + 2 + 2 = 7 tripplets.

Time complexity: O(N^2)
Space: O(1)
*/
class Solution {
    public int numTeams(int[] rating) {
        int count = 0;
        int len = rating.length;
        for(int i = 0; i < len; i++) {
            int leftsmaller = 0;
            int leftlarger = 0;
            int rightsmaller = 0;
            int rightlarger = 0;
            for(int j = 0; j < i; j++) {
                if(rating[j] < rating[i]) {
                    leftsmaller++;
                } else {
                    leftlarger++;
                }
            }
            for(int j = i + 1; j < len; j++) {
                if(rating[j] < rating[i]) {
                    rightsmaller++;
                } else {
                    rightlarger++;
                }
            }
            count += leftsmaller * rightlarger + leftlarger * rightsmaller;
        }
        return count;
    }
}
