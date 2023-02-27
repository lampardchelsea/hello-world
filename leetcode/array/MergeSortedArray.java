/**
  Refer to
  https://leetcode.com/problems/merge-sorted-array/
  Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.

Note:

The number of elements initialized in nums1 and nums2 are m and n respectively.
You may assume that nums1 has enough space (size that is greater or equal to m + n) to hold additional elements from nums2.
Example:

Input:
nums1 = [1,2,3,0,0,0], m = 3
nums2 = [2,5,6],       n = 3

Output: [1,2,2,3,5,6]
*/
// Solution 1: Two points
// Refer to
// https://leetcode.com/problems/merge-sorted-array/discuss/29578/Share-my-accepted-Java-solution!/172441
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i1 = m - 1;  //last index of nums1that has valid number
        int i2 = n - 1; // last index of nums2
        int lastIndex = m + n - 1; //last index of nums array
        while(i1 >= 0 && i2 >= 0){
            if(nums1[i1] > nums2[i2]){ //compare two numbers
                //if nums1[i1] is bigger, then place it in the last index in nums1
                nums1[lastIndex] = nums1[i1];
                i1 --;
            } else {
                nums1[lastIndex] = nums2[i2];
                i2 --;
            }
            lastIndex --;
        }
        // if i1 is greater than 0 but i2 is not, we don't need to do anything becuase it's a sorted array.
        // However, if i2 is greater than 0, this means the rest of spot is only n2.
        while( i2 >= 0){
            nums1[lastIndex] = nums2[i2];
            lastIndex --;
            i2 --;
        }
    }
}



/**
 * Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.
 * Note:
 * You may assume that nums1 has enough space (size that is greater or equal to m + n) to hold 
 * additional elements from nums2. The number of elements initialized in nums1 and nums2 are m 
 * and n respectively.
 * 
 * Analysis
 * The key to solve this problem is moving element of A and B backwards. If B has some elements 
 * left after A is done, also need to handle that case.
 * The takeaway message from this problem is that the loop condition. This kind of condition is 
 * also used for merging two sorted linked list.
*/
public class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        while(m > 0 && n > 0) {
            if(nums1[m - 1] <= nums2[n - 1]) {
                nums1[m + n - 1] = nums2[n - 1];
                n--;
            } else {
                nums1[m + n - 1] = nums1[m - 1];
                m--;
            }
        }
        
        // Handle when scan and allocate all items of nums1 array, 
        // but items of nums2 array still remain
        while(n > 0) {
            nums1[m + n - 1] = nums2[n - 1];
            n--;
        }
    }
}












































https://leetcode.com/problems/merge-sorted-array/

You are given two integer arrays nums1 and nums2, sorted in non-decreasing order, and two integers m and n, representing the number of elements in nums1 and nums2 respectively.

Merge nums1 and nums2 into a single array sorted in non-decreasing order.

The final sorted array should not be returned by the function, but instead be stored inside the array nums1. To accommodate this, nums1 has a length of m + n, where the first m elements denote the elements that should be merged, and the last n elements are set to 0 and should be ignored. nums2 has a length of n.

Example 1:
```
Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
Output: [1,2,2,3,5,6]
Explanation: The arrays we are merging are [1,2,3] and [2,5,6].
The result of the merge is [1,2,2,3,5,6] with the underlined elements coming from nums1.
```

Example 2:
```
Input: nums1 = [1], m = 1, nums2 = [], n = 0
Output: [1]
Explanation: The arrays we are merging are [1] and [].
The result of the merge is [1].
```

Example 3:
```
Input: nums1 = [0], m = 0, nums2 = [1], n = 1
Output: [1]
Explanation: The arrays we are merging are [] and [1].
The result of the merge is [1].
Note that because m = 0, there are no elements in nums1. The 0 is only there to ensure the merge result can fit in nums1.
```

Constraints:
- nums1.length == m + n
- nums2.length == n
- 0 <= m, n <= 200
- 1 <= m + n <= 200
- -109 <= nums1[i], nums2[j] <= 109
 
Follow up: Can you come up with an algorithm that runs in O(m + n) time?
---
Attempt 1: 2023-02-27

Solution 1:  Three Pointers backwards scan (30 min)

Why we have to scan backwards ?
Because it requires to return final result on nums1 in-place, and its default length as (m + n) equal to the sum of nums1 length = m and nums2 length = n, and filled with tailing 0s, so we have to replace those 0s by scanning backwards 
```
class Solution { 
    public void merge(int[] nums1, int m, int[] nums2, int n) { 
        int k = m + n - 1; 
        int i = m - 1; 
        int j = n - 1; 
        while(i >= 0 && j >= 0) { 
            if(nums1[i] > nums2[j]) { 
                nums1[k--] = nums1[i--]; 
            } else { 
                nums1[k--] = nums2[j--]; 
            } 
        } 
        while(j >= 0) { 
            nums1[k--] = nums2[j--]; 
        } 
    } 
}
```

Refer to
https://leetcode.com/problems/merge-sorted-array/solutions/29704/my-clean-java-solution/
```
public void merge(int[] nums1, int m, int[] nums2, int n) { 
    int tail1 = m - 1, tail2 = n - 1, finished = m + n - 1; 
    while (tail1 >= 0 && tail2 >= 0) { 
        nums1[finished--] = (nums1[tail1] > nums2[tail2]) ?  
                             nums1[tail1--] : nums2[tail2--]; 
    } 
    while (tail2 >= 0) { //only need to combine with remaining nums2, if any 
        nums1[finished--] = nums2[tail2--]; 
    } 
}
```

Refer to
https://leetcode.com/problems/merge-sorted-array/solutions/2120234/visual-explanation-o-1-space-java

Logic

We'd like to attempt this question in-place because all the space we need is already provided by our first array; there's no need to create extra space. We'd like to use a two-pointer approach but it can get quite messy dealing with certain cases. Since our extra space starts at the end of the first array, let's see if we can do this by iterating backwards!

Three Pointers: Let's use three pointers for this operation:
- The main pointer, i, will be starting from the end of the first array and will work its way to index 0.
- The pointer for the first array, a, and;
- The pointer for the second array, b, will be used for comparing the two sorted arrays and deciding what value to place at i next.
We'll decide which value at index a or b is larger, place that value at index i, and decrement either a or b depending on which was larger. This sounds a bit confusing so I've illustrated below how this would work with two example arrays:


I hope the above illustration sufficiently details the three pointer approach. In summary, we compare two values, pick the larger one, and move to the next comparison. The reason we move the pointer of the larger value is because we already know that that number is larger than (or equal to) ANY number that comes before it. So we can confidently place it at i and move on.


But won't some values at array A be overridden by the main pointer?

Good question! Yes you're right; our i pointer will eventually land at values of A that are non-zero. Is this a problem? Turns out it isn't. This is because we're guaranteed to have already used those values at A before they become overridden. Think about it like this:

A is of length m+n. All our meaningful values are the first m values. This means that the last n values are empty. Therefore, our i pointer needs to process the nth largest numbers in both arrays before overriding values at A. It's not possible for i to override any unused values of A by the time it reaches A.

Let's consider the two extremes:
- If the nth largest values are all at A, our pointer a pointer will be moving with i so there's no issues there.
- If the nth largest values are all at B, then our b pointer will trigger our exit condition "b >= 0" since our b pointer would be negative eventually. Therefore, i won't get a chance to override values at A.

If this all sounds a bit confusing, don't worry about it! Took me a while to get this explanation in order and if your interviewer expects you to spit this out in an interview, they don't want to hire you haha.


Code

Just for clarity, the for loop initializes all three pointers and it maintains the b >= 0 condition that's necessary for our algorithm as aforementioned. If you have any questions, suggestions or improvements, feel free to let me know. Thanks for reading!
```
public void merge(int[] A, int m, int[] B, int n) { 
	for (int i = m+n-1, a = m-1, b = n-1; b>=0; i--) { 
		if (a >= 0 && A[a] > B[b]) A[i] = A[a--];  
		else A[i] = B[b--]; 
	}         
}
```
Time Complexity: O(n+m)
Space Complexity: O(1)
