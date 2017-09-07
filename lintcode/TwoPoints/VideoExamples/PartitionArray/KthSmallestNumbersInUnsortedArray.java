/**
 * Refer to
 * https://yeqiuquan.blogspot.com/2017/03/lintcode-461-kth-smallest-numbers-in.html
 * Description
    Find the kth smallest numbers in an unsorted integer array.

    Example
    Given [3, 4, 1, 2, 5], k = 3, the 3rd smallest numbers are [1, 2, 3].

    Challenge 
    An O(nlogn) algorithm is acceptable, if you can do it in O(n), that would be great.
 *
 * Solution
 * https://www.jiuzhang.com/solutions/kth-smallest-numbers-in-unsorted-array/
 * http://blog.csdn.net/sinat_32547403/article/details/55259566
 * http://www.geeksforgeeks.org/kth-smallestlargest-element-unsorted-array/
*/

public class KthSmallestNumbersInUnsortedArray {
	// Solution 1: O(nlogn) QuickSort
	public int kthSmallest(int k, int[] nums) {
		if(nums == null || nums.length == 0) {
			return 0;
		}
	    quickSort(nums, 0, nums.length - 1);
	    return nums[k - 1];
    }
	
	private void quickSort(int[] nums, int start, int end) {
		if(start >= end) {
			return;
		}
		int left = start;
		int right = end;
		int mid = left + (right - left) / 2;
		int pivot = nums[mid];
		while(left <= right) {
			while(left <= right && nums[left] < pivot) {
				left++;
			}
			while(left <= right && nums[right] > pivot) {
				right--;
			}
			if(left <= right) {
				int temp = nums[left];
				nums[left] = nums[right];
				nums[right] = temp;
				left++;
				right--;
			}
		}
		quickSort(nums, start, right);
		quickSort(nums, left, end);
	}
	
	// Solution 2: O(n) Quick Select
    public int kthSmallest2(int k, int[] nums) {
		if(nums == null || nums.length == 0) {
			return 0;
		}
		return quickSelect(nums, 0, nums.length - 1, k - 1);
    }

    private int quickSelect(int[] nums, int start, int end, int k) {
	// Little different than QuickSort template on base condition
	if(start == end) {
		return nums[start];
	}
	int left = start;
	int right = end;
	int mid = left + (right - left) / 2;
	int pivot = nums[mid];
	while(left <= right) {
		while(left <= right && nums[left] < pivot) {
			left++;
		}
		while(left <= right && nums[right] > pivot) {
			right--;
		}
		if(left <= right) {
			int temp = nums[left];
			nums[left] = nums[right];
			nums[right] = temp;
			left++;
			right--;
		}
	}
	/**
	 * Refer to
	 * http://www.geeksforgeeks.org/kth-smallestlargest-element-unsorted-array/
	 * Method 4 (QuickSelect) 
	 * This is an optimization over method 1 if QuickSort is used as a sorting algorithm 
	 * in first step. In QuickSort, we pick a pivot element, then move the pivot element 
	 * to its correct position and partition the array around it. The idea is, not to do 
	 * complete quicksort, but stop at the point where pivot itself is k’th smallest element. 
	 * Also, not to recur for both left and right sides of pivot, but recur for one of them 
	 * according to the position of pivot. The worst case time complexity of this method 
	 * is O(n2), but it works in O(n) on average.
	 */
        if (right >= k && start <= right)
            return quickSelect(nums, start, right, k);
        else if (left <= k && left <= end)
            return quickSelect(nums, left, end, k);
        else
            return nums[k];
    }
	
	public static void main(String[] args) {
		KthSmallestNumbersInUnsortedArray m = new KthSmallestNumbersInUnsortedArray();
		int[] nums = {3, 4, 1, 2, 5};
		int k = 3;
		int result = m.kthSmallest2(k, nums);
		System.out.println(result);
	}
}

