/**
Refer to
https://leetcode.com/problems/previous-permutation-with-one-swap/
Given an array of positive integers arr (not necessarily distinct), return the lexicographically largest permutation 
that is smaller than arr, that can be made with exactly one swap (A swap exchanges the positions of two numbers 
arr[i] and arr[j]). If it cannot be done, then return the same array.

Example 1:
Input: arr = [3,2,1]
Output: [3,1,2]
Explanation: Swapping 2 and 1.

Example 2:
Input: arr = [1,1,5]
Output: [1,1,5]
Explanation: This is already the smallest permutation.

Example 3:
Input: arr = [1,9,4,6,7]
Output: [1,7,4,6,9]
Explanation: Swapping 9 and 7.

Example 4:
Input: arr = [3,1,1,3]
Output: [1,3,1,3]
Explanation: Swapping 1 and 3.

Constraints:
1 <= arr.length <= 104
1 <= arr[i] <= 104
*/

// Solution 1: Scan from backwards and skip duplicates
// Refer to
// https://leetcode.com/problems/previous-permutation-with-one-swap/discuss/303188/C%2B%2B-O(N)-solution-with-example-with-explanation
/**
If the array is already sorted the solution is the input array.
If not, move form right side of array toward left side until the point where the left element is larger than right element.
At this point the left element (let's call it candid) is one of the elements that should be swapped.
But to swap with what element?
We actually need to swap candid with the largest value on its right side that is less than candid (let's call it candid_2).
Since the elements on the right side of candid are all sorted we can find the largest smaller number than candid in O(logN) by binary search

For example :
[1, 9 , 5, 7, 9]
Candid is index 1 : [1, 9, 5,7,9]
Candid should be swapp by index 3 [1, 9, 5, 7, 9]

Note: in case of having repeated digits we prefer to swap it with the left most one to reach a larger number.
for example in [3,1,1,3]
Candid is index 0 : [3,1,1,3]
Candid should be swapp by index 1 not index 2 [3,1,1,3]
This is because we want the candid elements which is larger than candid_2 be on left most side.
In other words "[1,3,1,3]" > "[1,1,3,3] "

Following code is composed two helper functions.
First we check whether the algorithm is already sorted (O(N))
Then we try to find the candid_2 in O (logN) complexity if the array was not already sorted.

    vector<int> prevPermOpt1(vector<int>& A) {
        int len = A.size();
        if (len<2 || is_sorted(A))
            return A;
        
        int cand = len-2;
        while (cand>=0 && A[cand+1]>=A[cand]){
            cand--;
        }
        
        int sw =swap_with (A, cand+1);
        swap (A[cand], A[sw]);
        return A;
        
    }
    
    int swap_with(vector<int> &A, int left){
        int right= A.size()-1;
        int val = A[left-1];
        int res= left;  
        while (left<=right){
            int mid = left+ (right-left)/2;           
            if (A[mid]<val){           
               if (A[res]<A[mid]) /*the preference for candid_2 is to be on left side as discussed by second example*/
                 res= mid;
               left=mid+1;
            } else
                right = mid-1;
        }
        
        return res;
    }
    bool is_sorted(vector<int> &A){
        for (int i= 1; i<A.size()-1;++i){
            if (A[i-1]>A[i])
                return false;
        }
        return true;
    }
*/

// https://leetcode.com/problems/previous-permutation-with-one-swap/discuss/299244/Similar-to-find-previous-permutation-written-in-Java
/**
This problem can be solved in a similar way (yet simpler) as finding the previous permutation. But here, we do not have to reverse the numbers between i and j.

class Solution {
    public int[] prevPermOpt1(int[] A) {
        if (A.length <= 1) return A;
        int idx = -1;
		// find the largest i such that A[i] > A[i + 1]
        for (int i = A.length - 1; i >= 1; i--) {
            if (A[i] < A[i - 1]) {
                idx = i - 1;
                break;
            }
        }
		// the array already sorted, not smaller permutation
        if (idx == -1) return A;
		// find the largest j such that A[j] > A[i], then swap them
        for (int i = A.length - 1; i > idx; i--) {
			// the second check to skip duplicate numbers
            if (A[i] < A[idx] && A[i] != A[i - 1]) {
                int tmp = A[i];
                A[i] = A[idx];
                A[idx] = tmp;
                break;
            }
        }
        return A;
    }
}
*/

class Solution {
    public int[] prevPermOpt1(int[] arr) {
        if(arr.length <= 1) {
            return arr;
        }
        // Find the largetest index i such that A[i] > A[i + 1]
        int index = -1;
        for(int i = arr.length - 1; i >= 1; i--) {
            if(arr[i - 1] > arr[i]) {
                index = i - 1; 
                break;
            }
        }
        // If array already sorted in ascending order no smaller one
        if(index == -1) {
            return arr;
        }
        // Find the largest index j such that A[index] > A[j], then swap
        for(int j = arr.length - 1; j > index; j--) {
            // Skip the duplicate value and must find the most close one smaller than
            // current number
            // E.g [3,1,1,3], the most close one should be 1313 not 1133
            // so the swap should between 3 at index 0 and 1 at index 1,
            // not 1 at index 2, the check as arr[j] != arr[j - 1] will
            // make sure index j not stop at index 2 but continue move
            // backwards to 1 which will give real closest permutation
            if(arr[j] < arr[index] && arr[j] != arr[j - 1]) {
                int tmp = arr[j];
                arr[j] = arr[index];
                arr[index] = tmp;
                break;
            }
        }
        return arr;
    }
}
