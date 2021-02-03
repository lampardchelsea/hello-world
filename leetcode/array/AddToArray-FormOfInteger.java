/**
Refer to
https://leetcode.com/problems/add-to-array-form-of-integer/
For a non-negative integer X, the array-form of X is an array of its digits in left to right order.  
For example, if X = 1231, then the array form is [1,2,3,1].

Given the array-form A of a non-negative integer X, return the array-form of the integer X+K.

Example 1:
Input: A = [1,2,0,0], K = 34
Output: [1,2,3,4]
Explanation: 1200 + 34 = 1234

Example 2:
Input: A = [2,7,4], K = 181
Output: [4,5,5]
Explanation: 274 + 181 = 455

Example 3:
Input: A = [2,1,5], K = 806
Output: [1,0,2,1]
Explanation: 215 + 806 = 1021

Example 4:
Input: A = [9,9,9,9,9,9,9,9,9,9], K = 1
Output: [1,0,0,0,0,0,0,0,0,0,0]
Explanation: 9999999999 + 1 = 10000000000

Note：
1 <= A.length <= 10000
0 <= A[i] <= 9
0 <= K <= 10000
If A.length > 1, then A[0] != 0
*/

// Solution 1: Schoolbook Addition (Since A.length <= 10000, we have to use this way, otherwise overflow)
// Refer to
// https://leetcode.com/problems/add-to-array-form-of-integer/solution/
class Solution {
    public List<Integer> addToArrayForm(int[] A, int K) {
        List<Integer> B = new ArrayList<Integer>();
        while(K > 0) {
            B.add(K % 10);
            K /= 10;
        }
        List<Integer> list = new ArrayList<Integer>();
        int carry = 0;
        int len = Math.min(B.size(), A.length);
        for(int i = 0; i < len; i++) {
            int cur = A[A.length - 1 - i] + B.get(i) + carry;
            carry = cur / 10;
            list.add(cur % 10);
        }
        if(A.length > len) {
            for(int i = A.length - len - 1; i >= 0; i--) {
                int cur = A[i] + carry;
                carry = cur / 10;
                list.add(cur % 10);
            }
        }
        if(B.size() > len) {
            for(int i = len; i < B.size(); i++) {
                int cur = B.get(i) + carry;
                carry = cur / 10;
                list.add(cur % 10);
            }
        }
        if(carry > 0) {
            list.add(carry);
        }
        Collections.reverse(list);
        return list;
    }
}

// Solution 2: Take K itself as a Carry
// Refer to
// https://leetcode.com/problems/add-to-array-form-of-integer/discuss/234488/JavaC%2B%2BPython-Take-K-itself-as-a-Carry
/**
Explanation
Take K as a carry.
Add it to the lowest digit,
Update carry K,
and keep going to higher digit.


Complexity
Insert will take O(1) time or O(N) time on shifting, depending on the data stucture.
But in this problem K is at most 5 digit so this is restricted.
So this part doesn't matter.

The overall time complexity is O(N).
For space I'll say O(1)

Java

    public List<Integer> addToArrayForm(int[] A, int K) {
        List<Integer> res = new LinkedList<>();
        for (int i = A.length - 1; i >= 0; --i) {
            res.add(0, (A[i] + K) % 10);
            K = (A[i] + K) / 10;
        }
        while (K > 0) {
            res.add(0, K % 10);
            K /= 10;
        }
        return res;
    }
*/
class Solution {
    public List<Integer> addToArrayForm(int[] A, int K) {
        List<Integer> res = new LinkedList<>();
        for (int i = A.length - 1; i >= 0; --i) {
            res.add(0, (A[i] + K) % 10);
            K = (A[i] + K) / 10;
        }
        while (K > 0) {
            res.add(0, K % 10);
            K /= 10;
        }
        return res;
    }
}

// Solution 3: Don't use LinkedList, since its very slow, just reverse ArrayList
// Refer to
// https://leetcode.com/problems/add-to-array-form-of-integer/discuss/234488/JavaC++Python-Take-K-itself-as-a-Carry/236242
/**
LinkedList is very slow due to cache-inefficiency and takes more memory (for every element it creates a new node object) 
so it would be better to just append at the end and then reverse the list.
*/
class Solution {
    public List<Integer> addToArrayForm(int[] nums, int k) {
        List<Integer> result = new ArrayList<>(nums.length + 1);
        for (int i = nums.length - 1, carry = 0; i >= 0 || k > 0 || carry > 0; i--, k /= 10) {
            int d1 = i >= 0 ? nums[i] : 0;
            int d2 = k % 10;
            int sum = d1 + d2 + carry;
            result.add(sum % 10);
            carry = sum / 10;
        }
        Collections.reverse(result);
        return result;
    }
}
