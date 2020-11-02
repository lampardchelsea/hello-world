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
			// For case as '151', we need to skip last '1', must including equal,
			// E.g For 124651 we need to find 5 to exchange with 4
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

// Re-work
// Refer to
// https://leetcode.com/problems/next-permutation/discuss/13866/Share-my-O(n)-time-solution/14234
/**
I don't know how many guys come up this solution only by himself. At least I cannot, I even don't know what is the next permutation. 
I stole this solution from somewhere to here because I don't see a clear explanation in LeetCode discussion. I think the biggest 
problem of this community is people here are too smart. They like writing "short code", they like using annotations like 
nums[k] < nums[k + 1], they don't like to give example. Even when a problem as abstract as this one.

I don't think any one can understand this solution without seeing an example, here is an example:
2,3,6,5,4,1

Solution:
Step1, from right to left, find the first number which not increase in a ascending order. In this case which is 3.
Step2, here we can have two situations:

We cannot find the number, all the numbers increasing in a ascending order. This means this permutation is the last permutation, 
we need to rotate back to the first permutation. So we reverse the whole array, for example, 6,5,4,3,2,1 we turn it to 1,2,3,4,5,6.

We can find the number, then the next step, we will try to find the closest number which is larger than 3 on its right side. In this case it is 4.
Then we swap 3 and 4, the list turn to 2,4,6,5,3,1.
Last, we sort the numbers at the right of 4, we finally get 2,4,1,3,5,6.

Time complexity is: O(n + n + nlogn)=O(n logn).

class Solution {
  public void nextPermutation(int[] nums) {
    if(nums.length == 0) {
      return;
    }
    
    int i = nums.length - 2;
    for(; i >= 0; i--) {
      if(nums[i] < nums[i + 1]) {
        break;
      }
    }
    
    if(i == -1) {
     reverse(nums);
     return;
    }

    int b = i + 1, a = i;
    for(; i < nums.length; i++) {
      if(nums[i] > nums[a] && nums[i] < nums[b]) {
        b = i;
      }
    }
    
    swap(nums,a,b);
    
    Arrays.sort(nums, a + 1, nums.length);
  }
  
  private void reverse(int[] nums) {
    int s = 0, e = nums.length - 1;
    while(s < e) {
      int t = nums[s];
      nums[s] = nums[e];
      nums[e] = t;
      s++; e--;
    }
  }
  
  private void swap(int[] nums, int a, int b) {
      int t = nums[a];
      nums[a] = nums[b];
      nums[b] = t;    
  }
}
*/
class Solution {
    public void nextPermutation(int[] nums) {
        // Step1, from right to left, find the first number which not increase in a ascending order
        int i = nums.length - 2;
        for(; i >= 0; i--) {
            if(nums[i] < nums[i + 1]) {
                break;
            }
        }
        // We cannot find the number, all the numbers increasing in a ascending order.
        if(i == -1) {
            reverse(nums);
            return;
        }
        // We can find the number, then the next step, we will try to find the closest number 
        // which is larger than it on its right side.
        int a = i;
        int b = i + 1;
        for(; i < nums.length; i++) {
            if(nums[i] > nums[a] && nums[i] < nums[b]) {
                b = i;
            }
        }
        // Then we swap these 2 digits
        swap(nums, a, b);
        // Last, we sort the numbers at the right the current index
        Arrays.sort(nums, a + 1, nums.length);
    }
    
    private void reverse(int[] nums) {
        int i = 0;
        int j = nums.length - 1;
        while(i < j) {
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
            i++;
            j--;
        }
    }
    
    private void swap(int[] nums, int a, int b) {
        int tmp = nums[a];
        nums[a] = nums[b];
        nums[b] = tmp;
    }
}


