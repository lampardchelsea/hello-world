// Re-document
// Refer to
// https://leetcode.com/problems/sort-colors/discuss/26472/Share-my-at-most-two-pass-constant-space-10-line-solution
// The idea is to sweep all 0s to the left and all 2s to the right, then all 1s are left in the middle.
// It is hard to define what is a "one-pass" solution but this algorithm is bounded by O(2n), meaning 
// that at most each element will be seen and operated twice (in the case of all 0s). You may be able 
// to write an algorithm which goes through the list only once, but each step requires multiple operations, 
// leading the total operations larger than O(2n).
    class Solution {
    public:
        void sortColors(int A[], int n) {
            int second=n-1, zero=0;
            for (int i=0; i<=second; i++) {
                while (A[i]==2 && i<second) swap(A[i], A[second--]);
                while (A[i]==0 && i>zero) swap(A[i], A[zero++]);
            }
        }
    };


/**
 * Refer to
 * https://leetcode.com/problems/sort-colors/#/description
 *  Given an array with n objects colored red, white or blue, sort them so that objects of 
 *  the same color are adjacent, with the colors in the order red, white and blue.
 *  Here, we will use the integers 0, 1, and 2 to represent the color red, white, 
 *  and blue respectively.
 *  Note:
 *  You are not suppose to use the library's sort function for this problem.
 *  click to show follow up.
 *  Follow up:
 *  A rather straight forward solution is a two-pass algorithm using counting sort.
 *  First, iterate the array counting number of 0's, 1's, and 2's, then overwrite array 
 *  with total number of 0's, then 1's and followed by 2's.
 *  Could you come up with an one-pass algorithm using only constant space?
 * 
 * Solution
 * https://segmentfault.com/a/1190000003761919
 * 交换法
 * 复杂度
 * 时间 O(N) 空间 O(1)
 * 思路
 * 我们先用两个指针，一个指向已经排好序的0的序列的后一个点，一个指向已经排好序的2的序列的前一个点。这样在一开始，
 * 两个指针是指向头和尾的，因为我们还没有开始排序。然后我们遍历数组，当遇到0时，将其和0序列后面一个数交换，然后将
 * 0序列的指针向后移。当遇到2时，将其和2序列前面一个数交换，然后将2序列的指针向前移。遇到1时，不做处理。这样，当
 * 我们遍历到2序列开头时，实际上我们已经排好序了，因为所有0都被交换到了前面，所有2都被交换到了后面。
 * 
 * https://discuss.leetcode.com/topic/36832/sharing-c-solution-with-good-explanation
 * The solution requires the use of tracking 3 positions, the Low, Mid and High.
 * We assume that the mid is the "Unknown" area that we must evaluate.
 * If we encounter a 0, we know that it will be on the low end of the array, and if we encounter a 2, 
 * we know it will be on the high end of the array.
 * To achieve this in one pass without preprocessing (counting), we simply traverse the unknown will 
 * generating the low and high ends.
 * Take this example:
 * Assume our input is: 1 0 2 2 1 0 (short for simplicity).
 * Running the algorithm by hand would look something like:
    1 0 2 2 1 0
    ^         ^
    L         H
    M

    Mid != 0 || 2
    Mid++

    1 0 2 2 1 0
    ^ ^       ^
    L M       H

    Mid == 0
    Swap Low and Mid
    Mid++
    Low++

    0 1 2 2 1 0
      ^ ^     ^
      L M     H

    Mid == 2
    Swap High and Mid
    High--

    0 1 0 2 1 2
      ^ ^   ^
      L M   H

    Mid == 0
    Swap Low and Mid
    Mid++
    Low++

    0 0 1 2 1 2
        ^ ^ ^
        L M H

    Mid == 2
    Swap High and Mid
    High--

    0 0 1 1 2 2
        ^ ^
        L M
          H

    Mid <= High is our exit case
    
  * Implemented in C++, it looks like:
	class Solution {
	    public:
	    void sortColors(vector<int>& nums) 
	    {
	        int tmp = 0, low = 0, mid = 0, high = nums.size() - 1;
	    
	        while(mid <= high)
	        {
	            if(nums[mid] == 0)
	            {
	                tmp = nums[low];
	                nums[low] = nums[mid];
	                nums[mid] = tmp;
	                low++;
	                mid++;
	            }
	            else if(nums[mid] == 1)
	            {
	                mid++;
	            }
	            else if(nums[mid] == 2)
	            {
	                tmp = nums[high];
	                nums[high] = nums[mid];
	                nums[mid] = tmp;
	                high--;
	            }
	        }
	    }
	};
 * 
 * https://discuss.leetcode.com/topic/36832/sharing-c-solution-with-good-explanation/8
 * Why when mid==2 satisfy, we dont need mid++?
	See the follow. Thks.
	Mid == 2
	Swap High and Mid
	High--
 * https://discuss.leetcode.com/topic/36832/sharing-c-solution-with-good-explanation/10
 * The reason why we don't need mid++ is because on the right side of mid, it could be a 0 swapped back, 
 * that will need be further swapped to left to mid. This is a problem when doing scanning from left 
 * to right. 
 * E.g
 * 0 1 2 2 1 0
      ^ ^     ^
      L M     H

    Mid == 2
    Swap High and Mid
    High--

    0 1 0 2 1 2
      ^ ^   ^
      L M   H
 * 
 * If doing scanning from right to left, we have following
    public void sortColors(int[] nums) {
        if (nums == null || nums.length == 0)
            return;
        int n = nums.length;
        int i = n - 1, j = 0, k = n - 1;
        while (j <= i) {
            if (nums[i] == 0) {
                int t = nums[i];
                nums[i] = nums[j];
                nums[j] = t;
                j++;
            } else if (nums[i] == 1) {
                i--;
            } else {
                int t = nums[i];
                nums[i] = nums[k];
                nums[k] = t;
                k--;
                i--;
            }
        }
    }
 */
public class SortColors {
	// Solution 1: Scan from left to right with 3 pointers
    public void sortColors(int[] nums) {
        // The position pointer for red, start at head,
        // going towards end position of red
        int left = 0;
        // The position pointer for blue, start at end,
        // going towards start position of blue
        int right = nums.length - 1;
        // Scanner pointer
        int i = 0;
        while(i <= right) {
            // When encounter red(0), exchange to head
            if(nums[i] == 0) {
                swap(nums, i, left);
                // Move forward one step for next exchange
                left++;
                // Increase scan pointer
                i++;
            } else if(nums[i] == 2) {
                // When encounter blue(2), exchange to end
                swap(nums, i, right);
                // Move backward one step for next exchange
                right--;
                // Here not contain i++ as when scan from
                // left to right, if we exchange nums[i] and
                // nums[right], the newly nums[i](original
                // nums[right]) might be a red(0), which need
                // further shift down to left of current
                // position i, so we should not increase i
                // at current loop, need a further judgement
                // on next loop.
            } else {
                // When encounter white(1), do nothing,
                // directly increase scan pointer
                i++;   
            }
        }
    }
    
    // Solution 2: Scan from right to left with 3 pointers
    public void sortColors2(int[] nums) {
    	int left = 0;
    	int right = nums.length - 1;
    	int i = nums.length - 1;
    	while(i >= left) {
    		if(nums[i] == 0) {
                // Here not contain i-- as when scan from
                // right to left, if we exchange nums[i] and
                // nums[left], the newly nums[i](original
                // nums[left]) might be a blue(2), which need
                // further shift up to right of current
                // position i, so we should not increase i
                // at current loop, need a further judgment
                // on next loop.
    			swap(nums, left, i);
    			left++;
    		} else if(nums[i] == 2) {
    			swap(nums, right, i);
    			right--;
    			i--;
    		} else {
    			i--;
    		}
    	}
    }
    
    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    public static void main(String[] args) {
    	SortColors s = new SortColors();
    	int[] nums = {1, 0, 2, 2, 1, 0};
    	s.sortColors2(nums);
    	for(int i = 0; i < nums.length; i++) {
    		System.out.println(nums[i]);
    	}
    }
}
