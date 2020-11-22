/**
Refer to
https://www.cnblogs.com/Dylan-Java-NYC/p/12047372.html
Given three integer arrays arr1, arr2 and arr3 sorted in strictly increasing order, return a sorted array of only 
the integers that appeared in all three arrays.

Example 1:
Input: arr1 = [1,2,3,4,5], arr2 = [1,2,5,7,9], arr3 = [1,3,4,5,8]
Output: [1,5]
Explanation: Only 1 and 5 appeared in the three arrays.
Constraints:
1 <= arr1.length, arr2.length, arr3.length <= 1000
1 <= arr1[i], arr2[i], arr3[i] <= 2000
*/

// Solution 1: Three Pointers
// Refer to
// https://www.cnblogs.com/Dylan-Java-NYC/p/12047372.html
/**
Solution
Have 3 pointers pointing to 3 arrays.
If 3 pointed values are the same, add it to res and move all 3 pointers.
Else if first value < second value, move 1st pointer.
Else if second value < third value, now first value must be >= second value, need to move 2nd pointer.
Else move the 3rd pointer, since noew first value >= second value and second value >= third value.
Time Complexity: O(n). n = sum of array lengths.
Space: O(1).
*/
class Solution {
    public List<Integer> arraysIntersection(int[] arr1, int[] arr2, int[] arr3) {
        List<Integer> res = new ArrayList<>();
        if(arr1 == null || arr2 == null || arr3 == null) {
            return res;
        }
        int i = 0;
        int j = 0;
        int k = 0;
        while(i < arr1.length && j < arr2.length && k < arr3.length) {
            if(arr1[i] == arr2[j] && arr1[i] == arr3[k]){
                res.add(arr1[i]);
                i++;
                j++;
                k++;
            }else if(arr1[i] < arr2[j]) {
                i++;
            }else if(arr2[j] < arr3[k]) {
                j++;
            }else {
                k++;
            }
        }
        return res;
    }
}
