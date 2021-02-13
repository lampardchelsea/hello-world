/**
Refer to
https://leetcode.com/problems/max-chunks-to-make-sorted/
Given an array arr that is a permutation of [0, 1, ..., arr.length - 1], we split the array into some number of "chunks" (partitions), 
and individually sort each chunk.  After concatenating them, the result equals the sorted array.

What is the most number of chunks we could have made?

Example 1:
Input: arr = [4,3,2,1,0]
Output: 1
Explanation:
Splitting into two or more chunks will not return the required result.
For example, splitting into [4, 3], [2, 1, 0] will result in [3, 4, 0, 1, 2], which isn't sorted.

Example 2:
Input: arr = [1,0,2,3,4]
Output: 4
Explanation:
We can split into two chunks, such as [1, 0], [2, 3, 4].
However, splitting into [1, 0], [2], [3], [4] is the highest number of chunks possible.

Note:
arr will have length in range [1, 10].
arr[i] will be a permutation of [0, 1, ..., arr.length - 1].
*/

// Solution 1: Find index value equal to current subarray's max value
// Refer to
// https://leetcode.com/problems/max-chunks-to-make-sorted/discuss/113528/Simple-Java-O(n)-Solution-with-detailed-explanation/162249
/**
For this question, what this algorithm wants us to do is finding some splitting line so that numbers being left of this line are all 
small than numbers right of this line. The idea is very similar to quick sort. What the answer of this question is how many lines you 
can find. So that is why we want the max array, or we can only need a number for storing the maximum number up to now(because original 
array is 0 to len-1), as long as the max number equals the index, we know that all numbers left to this point are smaller than this 
point and numbers right to this point are larger than this point, then we count add one.
*/

// https://leetcode.com/problems/max-chunks-to-make-sorted/discuss/113528/Simple-Java-O(n)-Solution-with-detailed-explanation/181573
/**
A more explicit explanation to this algorithm, or another perspective to solve the problem is to cut the array into a serious of continuous 
sequences, so that each chunk, after being sorted, could be directly used as a brick to construct the sorted array.

# Example
array: [1, 0, 2, 4, 3]
cut :   [1, 0, 2 | 4, 3]
# [1, 0, 2] and [4, 3] are both continuous sequences.

The key to understand this algorithms lies in the fact that when max[index] == index, all the numbers before index must be smaller than 
max[index] (also index), so they make up of a continuous unordered sequence, i.e {0,1,..., index}. This is because numbers in array only 
vary in range [0, 1, ..., arr.length - 1], so the most numbers you can find that are smaller than a certain number, say arr[k], would 
be arr[k] - 1, i.e [0, 1, ..., arr[k] - 1]. So when arr[k] is the max number in [arr[0], arr[1], ..., arr[k]], all the k - 1 numbers before 
it can only lies in [0, 1, ..., arr[k] - 1], so they made up of a continuous sequence. (You can also prove it using contradiction, 
which may be easier to understand)

array: [1, 0, 2, 4, 3]
cut :   [1, 0, 2 | 4, 3]
max:  [1, 1, 2 | 4, 4]
index:[0, 1, 2, 3, 4]
# max[2] == 2, and for the first three numbers, all the numbers in [0, 1, 2, 3, 4] that are smaller than 2 lie in [0, 1], 
# so [1, 0, 2] makes a continuous unordered sequence, which can be used to construct [0, 1, 2, 3, 4] as a brick after been sorted into [0, 1, 2]
*/
class Solution {
    public int maxChunksToSorted(int[] arr) {
        int max = 0;
        int count = 0;
        for(int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
            if(max == i) {
                count++;
            }
        }
        return count;
    }
}

