/**
 Refer to
 https://leetcode.com/problems/relative-sort-array/
 Given two arrays arr1 and arr2, the elements of arr2 are distinct, 
 and all elements in arr2 are also in arr1.

Sort the elements of arr1 such that the relative ordering of items in arr1 are the same as in arr2.  
Elements that don't appear in arr2 should be placed at the end of arr1 in ascending order.

Example 1:
Input: arr1 = [2,3,1,3,2,4,6,7,9,2,19], arr2 = [2,1,4,3,9,6]
Output: [2,2,2,1,4,3,3,9,6,7,19]

Constraints:
arr1.length, arr2.length <= 1000
0 <= arr1[i], arr2[i] <= 1000
Each arr2[i] is distinct.
Each arr2[i] is in arr1.
*/
// Solution:
// Refer to
// https://leetcode.com/problems/relative-sort-array/discuss/335056/Java-in-place-solution-using-counting-sort
class Solution {
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int[] index = new int[1001];
        for(int i = 0; i < arr1.length; i++) {
            index[arr1[i]]++;
        }        
        int k = 0;
        for(int i = 0; i < arr2.length; i++) {
            int num = arr2[i];
            while(index[num] > 0) {
                arr1[k] = num;
                index[num]--;
                k++;
            }
        }
        for(int i = 0; i < index.length; i++) {
            while(index[i] > 0) {
                arr1[k] = i;
                k++;
                index[i]--;
            }
        }
        return arr1;
    }
}
