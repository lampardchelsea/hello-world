/**
Refer to
https://leetcode.com/problems/element-appearing-more-than-25-in-sorted-array/
Given an integer array sorted in non-decreasing order, there is exactly one integer in the array that occurs more than 25% of the time.
Return that integer.
Example 1:
Input: arr = [1,2,2,6,6,6,6,7,10]
Output: 6
Constraints:
1 <= arr.length <= 10^4
0 <= arr[i] <= 10^5
*/

// Solution 1: Native freq map
class Solution {
    public int findSpecialInteger(int[] arr) {
        Map<Integer, Integer> freq = new HashMap<Integer, Integer>();
        for(int a : arr) {
            freq.put(a, freq.getOrDefault(a, 0) + 1);
        }
        int result = -1;
        for(int k : freq.keySet()) {
            if(freq.get(k) > arr.length / 4) {
                result = k;
            }
        }
        return result;
    }
}

// Solution 2: Binary Search
// Refer to
// https://leetcode.com/problems/element-appearing-more-than-25-in-sorted-array/discuss/451286/Java-Binary-Search/509023
// https://leetcode.com/problems/element-appearing-more-than-25-in-sorted-array/discuss/451286/Java-Binary-Search/406296
// No need to search for the end, directly check if start + n/4 is the same element.
class Solution {
    public int findSpecialInteger(int[] arr) {
        int len = arr.length;
        int a1 = arr[len / 4];
        int a2 = arr[len / 2];
        int a3 = arr[len * 3 / 4];
        List<Integer> list = new ArrayList<Integer>();
        list.add(a1);
        list.add(a2);
        list.add(a3);
        for(int e : list) {
            int first_idx = binarySearch(e, arr);
            if(arr[first_idx + len / 4] == e) {
                return e;
            }
        }
        return -1;
    }
    
    private int binarySearch(int e, int[] arr) {
        int lo = 0;
        int hi = arr.length - 1;
        while(lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if(arr[mid] < e) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return hi;
    }
}
