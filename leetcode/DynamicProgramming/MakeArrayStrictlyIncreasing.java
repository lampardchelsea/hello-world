/**
 Refer to
 https://leetcode.com/problems/make-array-strictly-increasing/
 Given two integer arrays arr1 and arr2, return the minimum number of operations (possibly zero) 
 needed to make arr1 strictly increasing.

In one operation, you can choose two indices 0 <= i < arr1.length and 0 <= j < arr2.length and do 
the assignment arr1[i] = arr2[j].

If there is no way to make arr1 strictly increasing, return -1.
Example 1:
Input: arr1 = [1,5,3,6,7], arr2 = [1,3,2,4]
Output: 1
Explanation: Replace 5 with 2, then arr1 = [1, 2, 3, 6, 7].

Example 2:
Input: arr1 = [1,5,3,6,7], arr2 = [4,3,1]
Output: 2
Explanation: Replace 5 with 3 and then replace 3 with 4. arr1 = [1, 3, 4, 6, 7].

Example 3:
Input: arr1 = [1,5,3,6,7], arr2 = [1,6,3,3]
Output: -1
Explanation: You can't make arr1 strictly increasing.

Constraints:
1 <= arr1.length, arr2.length <= 2000
0 <= arr1[i], arr2[i] <= 10^9
*/

// Solution 1: DP similar to LIS.300
// Refer to
// https://leetcode.com/problems/make-array-strictly-increasing/discuss/377495/Java-dp-solution-%3A-A-simple-change-from-Longest-Increasing-Subsequence
/**
 This problem is similiar to the Longest Increasing Subsequence (LIS) problem (LC300). The only difference is that, 
 while we are not only calculating the longest increasing subsequence, we also need to make sure that, base on this 
 sequence, we can form a new arr1 from swapping with arr2, to be a complete increasing array.

So to start, the dp relationship in LIS problem is:

//dp[i] -> longest increasing subsequence length ending at arr[i]
if (arr1[j] < arr1[i])
	dp[i] = Math.max(dp[i], dp[j] + 1)
We can start from this relationship, just add a simple check function: the function checks if arr1[j] to arr1[i] can 
be swapped from arr2, to make a complete increasing array, ending at arr1[i].
So now the relationship becomes:

//dp[i] -> the answer we need (nubmer of swapping operations), ending at arr1[i]
if (arr1[j] < arr1[i] && dp[j] != Integer.MAX_VALUE){
	int change = check(arr1, arr2, j, i);
	if (change >= 0){
		dp[i] = Math.min(dp[i], dp[j] + change);
    }
}
Now things are done! The check function is actually simple to implement after sorting arr2 and binary search.
Some tricky edge cases here are we have to add one min and one max to the both side of arr1. And we need to deal with 
the duplicates in arr2. Sorry I didn't came up with a more elegant way to do this. I just created two new arrays.

class Solution {
    public int makeArrayIncreasing(int[] arr1, int[] arr2) {
        int n = arr1.length;
		
        //sort and generate new arr2
        Arrays.sort(arr2);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < arr2.length; i++){
            if (i+1 < arr2.length && arr2[i] == arr2[i+1])
                continue;
            list.add(arr2[i]);
        }
        int[] newarr2 = new int[list.size()];
        for (int i = 0; i < list.size(); i++)
            newarr2[i] = list.get(i);
        arr2 = newarr2;
        
		//generate new arr1
        int[] newarr1 = new int[n+2];
        for (int i = 0; i < n; i++)
            newarr1[i+1] = arr1[i];
        newarr1[n+1] = Integer.MAX_VALUE;
        newarr1[0] = Integer.MIN_VALUE;
        arr1 = newarr1;
        
		//perform dp based on LIS
        int[] dp = new int[n+2];
        Arrays.fill(dp, Integer.MAX_VALUE);
        //dp[i] -> answer to change array 0 to i
        dp[0] = 0;
        for (int i = 1; i < n+2; i++){
            for (int j = 0; j < i; j++){
                if (arr1[j] < arr1[i] && dp[j] != Integer.MAX_VALUE){
                    int change = check(arr1, arr2, j, i);
                    if (change >= 0){
                        dp[i] = Math.min(dp[i], dp[j] + change);
                    }
                }
            }
        }
        return dp[n+1] == Integer.MAX_VALUE? -1:dp[n+1];
    }
    
    //change number from start+1 to end-1
    private int check(int[] arr1, int[] arr2, int start, int end){
        if (start+1 == end)
            return 0;
        int min = arr1[start];
        int max = arr1[end];
        int idx = Arrays.binarySearch(arr2, min);
        if (idx < 0)
            idx = -idx-1;
        else
            idx = idx+1;
        
        int maxcount = end-start-1;
        int endi = idx + maxcount-1;
        if (endi < arr2.length && arr2[endi] < max)
            return maxcount;
        else
            return -1;
    }
}
*/

// https://leetcode.com/problems/make-array-strictly-increasing/discuss/377495/Java-dp-solution-:-A-simple-change-from-Longest-Increasing-Subsequence/339264
/**
 I can help you understand what he is trying to do in the "check(...)" method. What he means is to check whether 
 for given start,end (where start<end<n and arr1[start]<arr1[end] and arr1[0:start] already can be made strictly 
 increasing i.e. dp[start] != INT_MAX), does arr2 have enough elements to replace all elements in 
 arr1[start+1:end-1] (worst case: all elements need to be replaced). If there are enough replacements in arr2, 
 just return (end-start-1) else -1. As you can see, this is done in (logm) time complexity where m = arr2.length 
 using binary search.

arr2 is sorted and has no duplicates
idx = index of smallest element in arr2 which is larger than arr1[start].
maxcount = max number of elements to be potentially replaced (this value will be returned if enough replacements 
are available in arr2 for worst case).
endi = index of the number in arr2 which can replace the element at arr1[end-1] in the worst case (assuming all 
elements in arr1[start+1] to arr1[end-1] need replacement, this way we are able to make sure that enough elements 
in arr2 are available to replace all elements in arr1[start1+1 : end-1]).
*/
class Solution {
    public int makeArrayIncreasing(int[] arr1, int[] arr2) {
        // Remove duplicates and sort arr2
        Arrays.sort(arr2);
        List<Integer> list = new ArrayList<Integer>();
        for(int i = 0; i < arr2.length; i++) {
            if(i + 1 < arr2.length && arr2[i] == arr2[i + 1]) {
                continue;
            }
            list.add(arr2[i]);
        }
        int[] new_arr2 = new int[list.size()];
        for(int i = 0; i < list.size(); i++) {
            new_arr2[i] = list.get(i);
        }
        // Adding floor and ceiling for arr1
        int[] new_arr1 = new int[arr1.length + 2];
        new_arr1[0] = Integer.MIN_VALUE;
        new_arr1[new_arr1.length - 1] = Integer.MAX_VALUE;
        for(int i = 0; i < arr1.length; i++) {
            new_arr1[i + 1] = arr1[i];
        }
        // Perform dp similar as LIS
        // dp[i] -> number of swapping operations ending at new_arr1[i]
        int[] dp = new int[new_arr1.length];
        for(int i = 0; i < dp.length; i++) {
            dp[i] = Integer.MAX_VALUE;
        }
        dp[0] = 0;
        for(int i = 1; i < new_arr1.length; i++) {
            for(int j = 0; j < i; j++) {
                if(new_arr1[j] < new_arr1[i] && dp[j] != Integer.MAX_VALUE) {
                    int change = helper(new_arr1, new_arr2, j, i);
                    if(change >= 0) {
                        dp[i] = Math.min(dp[i], dp[j] + change);
                    }
                }
            }
        }
        return dp[dp.length - 1] == Integer.MAX_VALUE ? -1 : dp[dp.length - 1];
    }
    
    // Change number from start + 1 to end - 1
    private int helper(int[] a, int[] b, int start, int end) {
        if(start + 1 == end) {
            return 0;
        }
        int min = a[start];
        int max = a[end];
        int startIndex = Arrays.binarySearch(b, min);
        if(startIndex < 0) {
            startIndex = -startIndex - 1;
        } else {
            startIndex = startIndex + 1;
        }
        int maxCount = end - start - 1;
        int endIndex = startIndex + maxCount - 1;
        if(endIndex < b.length && b[endIndex] < max) {
            return maxCount;
        } else {
            return -1;
        }
    }
}
