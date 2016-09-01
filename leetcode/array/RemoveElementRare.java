/**
 * Given an array and a value, remove all instances of that value in place and return the new length.
 * Do not allocate extra space for another array, you must do this in place with constant memory.
 * The order of elements can be changed. It doesn't matter what you leave beyond the new length.
 * Example:
 * Given input array nums = [3,2,2,3], val = 3
 * Your function should return length = 2, with the first two elements of nums being 2.
 * Show Hint 
 * Hint:
 * 1. Try two pointers.
 * 2. Did you use the property of "the order of elements can be changed"?
 * 3. What happens when the elements to remove are rare?
 * 
 * Intuition
 * Now consider cases where the array contains few elements to remove. For example, nums = [1,2,3,5,4], 
 * val = 4nums=[1,2,3,5,4],val=4. The previous algorithm will do unnecessary copy operation of the first 
 * four elements. Another example is nums = [4,1,2,3,5], val = 4nums=[4,1,2,3,5],val=4. It seems 
 * unnecessary to move elements [1,2,3,5][1,2,3,5] one step left as the problem description mentions 
 * that the order of elements could be changed.
 * 
 * Algorithm
 * When we encounter nums[i] = valnums[i]=val, we can swap the current element out with the last element 
 * and dispose the last one. This essentially reduces the array's size by 1.
 * 
 * Note that the last element that was swapped in could be the value you want to remove itself. 
 * But don't worry, in the next iteration we will still check this element.
*/
public class Solution {
    public int removeElement(int[] nums, int val) {
        int length = nums.length;
        int i = 0;
        while(i < length) {
            if(nums[i] == val) {
                // If match given val, replace current item with last item
                nums[i] = nums[length - 1];
                // New last item will be discard with decrease loop length,
                // note: the array length can not change, but last items can
                // manually treat as redundant and keep as is.
                length--;
            } else {
                // The i++ should only happen in else block, otherwise when the swapped last
                // element also match the given val will not discard, e.g if input [3,2,2,3]
                // and given val = 3, after replace 1st item 3 with last item 3, if i++ works
                // for while block, we will keep the 1st item with replacement item 3, and
                // this violate the rule, the output will be [3,2,2]. So, only when we make
                // sure current element always not match given val, we can increase i.
                i++;
            }
        }
        return length;
    }
}
