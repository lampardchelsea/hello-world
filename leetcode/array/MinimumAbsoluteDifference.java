/**
Refer to
https://leetcode.com/problems/minimum-absolute-difference/
Given an array of distinct integers arr, find all pairs of elements with the minimum absolute difference of any two elements. 

Return a list of pairs in ascending order(with respect to pairs), each pair [a, b] follows

a, b are from arr
a < b
b - a equals to the minimum absolute difference of any two elements in arr

Example 1:
Input: arr = [4,2,1,3]
Output: [[1,2],[2,3],[3,4]]
Explanation: The minimum absolute difference is 1. List all pairs with difference equal to 1 in ascending order.

Example 2:
Input: arr = [1,3,6,10,15]
Output: [[1,3]]

Example 3:
Input: arr = [3,8,-10,23,19,-4,-14,27]
Output: [[-14,-10],[19,23],[23,27]]

Constraints:
2 <= arr.length <= 10^5
-10^6 <= arr[i] <= 10^6
*/

// Solution 1: Including sort() as 3 pass
class Solution {
    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        Arrays.sort(arr);
        int n = arr.length;
        int min_diff = Integer.MAX_VALUE;
        for(int i = 1; i < n; i++) {
            int diff = arr[i] - arr[i - 1];
            if(min_diff > diff) {
                min_diff = diff;
            }
        }
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        for(int i = 1; i < n; i++) {
            if(arr[i] - arr[i - 1] == min_diff) {
                List<Integer> list = new ArrayList<Integer>();
                list.add(arr[i - 1]);
                list.add(arr[i]);
                result.add(list);
            }
        }
        return result;
    }
}

// Solution 2: Including sort() as 2 pass
// Refer to
// https://leetcode.com/problems/minimum-absolute-difference/discuss/387784/JavaPython-3-2-pass-and-3-pass-codes-w-analysis.
/**
    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        Arrays.sort(arr);
        List<List<Integer>> ans = new ArrayList<>();
        int diff = Integer.MAX_VALUE;
        for (int i = 1; i < arr.length; ++i) {
            if (arr[i] - arr[i - 1] <= diff) {
                if (arr[i] - arr[i - 1] < diff) {
                    diff = arr[i] - arr[i - 1];
                    // ans.clear(); // modified to the follow to achieve O(1) time, credit to @vivek_23.
                    ans = new ArrayList<>();
                }
                ans.add(Arrays.asList(arr[i - 1], arr[i]));
            }
        }
        return ans;
    }
*/
class Solution {
    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        Arrays.sort(arr);
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        int n = arr.length;
        int min_diff = Integer.MAX_VALUE;
        for(int i = 1; i < n; i++) {
            int diff = arr[i] - arr[i - 1];
            if(min_diff >= diff) {
                if(min_diff > diff) {
                    min_diff = diff;
                    result = new ArrayList<List<Integer>>();
                }
                result.add(Arrays.asList(arr[i - 1], arr[i]));
            }
        }
        return result;
    }
}
