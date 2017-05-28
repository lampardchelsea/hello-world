/**
 * Refer to
 * https://leetcode.com/problems/next-permutation/#/description
 *  Implement next permutation, which rearranges numbers into the lexicographically 
 *  next greater permutation of numbers.
 *  If such arrangement is not possible, it must rearrange it as the lowest possible order 
 *  (ie, sorted in ascending order).
 *  The replacement must be in-place, do not allocate extra memory.
 *  Here are some examples. Inputs are in the left-hand column and its corresponding outputs 
 *  are in the right-hand column.
	1,2,3 → 1,3,2
	3,2,1 → 1,2,3
	1,1,5 → 1,5,1
 *
 * Solution
 * https://segmentfault.com/a/1190000003766260
 * 升序倒置法
 * 复杂度
 * 时间 O(N) 空间 O(1)
 * 思路
 * 首先我们来思考下，什么是next permutation
 * 比如124651这个序列，我们如果只想它变大一点点，应该尽可能的不去增加高位。因为增加高位会带来更大的增益。
 * 所以对于一个长为n的序列，我们增加第n位的前提是，前n-1位已经达到了最大排列方法。所以我们从后往前看：
   1
   51
   651
 * 
 * 前面三位已经是各自最大的情况，不可能再变大，而到万位的时候4651，可以将4移到后面来来增大。但是问题在于，
 * 把谁移到4的位置。因为是找下一个数，所以我们要找一个比4小却尽可能大的数，所以找到5。把5换到4的位置后，
 * 后三位仍然是个降序的排列。然而这时候，因为已经将万位增大了，我们要将前三位尽可能的变小，做成一个以5开头
 * 最小的序列，而这个序列应该是升序的，所以我们直接把后三位倒置一下，就从降序变成升序了。
 * 
 * 注意
 * 找第一个下降点的循环和着第一个比下降点稍大的数的循环，其判断条件都要包含=
 * 
 * https://discuss.leetcode.com/topic/2542/share-my-o-n-time-solution
 * 
 */
public class NextPermutation {
	public void nextPermutation(int[] nums) {
		if(nums.length <= 1) {
			return;
		}		
		// Find the first drop point i, condition is if we can increase
		// position i, then sequence of (i - 1) behind positions already 
		// sort into biggest value, also, in case of number like '511',
		// we need to skip both '1'
		// E.g For 124651, i = 2, nums[i] = 4 is our drop point
		int i = nums.length - 2;
		while(i >= 0 && nums[i] >= nums[i + 1]) {
			i--;
		}
		// If this drop point i still in array range (final value of i >= 0)
		// then we can find a slightly larger number to replace, if out of
		// range (final value of i < 0), which means current array is already
		// largest permutation
		// E.g For 124651 -> 125641
		if(i >= 0) {
			int j = nums.length - 1;
			// For case as '151', we need to skip last '1', must including equal
			while(j > i && nums[j] <= nums[i]) {
				j--;
			}
			swap(nums, i, j);
		}
		// Reverse sub-array in front of drop point into minimal sequence
		// E.g For 125641 -> 125146
		reverse(nums, i + 1, nums.length - 1);
	}
	
    private void swap(int[] nums, int i, int j){
        int tmp = nums[j];
        nums[j] = nums[i];
        nums[i] = tmp;
    }
    
    private void reverse(int[] nums, int left, int right){
        while(left < right){
            swap(nums, left, right);
            left++;
            right--;
        }
    }
    
    public static void main(String[] args) {
    	//int[] nums = {5, 1, 1};
    	int[] nums = {1, 2, 4, 6, 5, 1};
    	NextPermutation n = new NextPermutation();
    	n.nextPermutation(nums);
    	for(int i = 0; i < nums.length; i++) {
        	System.out.println(nums[i]);
    	}
    }
}

