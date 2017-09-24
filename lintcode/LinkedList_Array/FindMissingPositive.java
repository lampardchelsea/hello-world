
/**
 * Refer to
 * http://www.lintcode.com/en/problem/first-missing-positive/
 * https://leetcode.com/problems/first-missing-positive/description/
 * Given an unsorted integer array, find the first missing positive integer.

	For example,
	Given [1,2,0] return 3,
	and [3,4,-1,1] return 2.
	
	Your algorithm should run in O(n) time and uses constant space.
 * 
 * 
 * Solution
 * http://blog.hyoung.me/cn/2017/02/find-the-missing-number/
 * 其实这道题目才算是第一题的 follow-up。最主要的区别就在于数组内数性质的变化，不再保证是
 * 基本连续的正整数了，其中可能会有负数和重复出现的数。但是利用桶排序的思路并没有变化，只是我们
 * 在做交换时需要更多的检查而已。类似于第一题中我们移动在数组中有位置的数，把N当做一个「锚」，
 * 在这里，我们把所有的负数、重复出现的数，以及在数组范围之外的数当做「锚」。

	具体代码如下：
	int firstMissingPositive(vector<int> &A) {
	    int N = A.size();
	    
	    for (int i = 0; i < N; ++i) {
	        while (A[i] > 0 && A[i] <= N && A[i] - 1 != i && A[i] != A[A[i] - 1])
	            swap(A[i], A[A[i] - 1]);
	    }
	    
	    for (int i = 0; i < N; ++i)
	        if (A[i] != i + 1)
	            return i + 1;
	    
	    return N + 1;
	}

           在这里，我们做了一点小处理，那就是把所有正整数在数组的位置都提前一位了，比如把5放在A[4]，
           这样可以让整体的逻辑更清晰一点儿，减少出错的机会。
           此外，最容易出错的地方就是没有考虑重复数字出现的可能了。如果没有考虑到这一点，内层循环就会陷入到
           一个死循环当中。这一点也很好想，假设有一个数出现了两次，其中一个正好在它对应的位置上，当遍历到
           另外一个时，由于肯定与当前位置不符，就需要交换，而换回来的数就是还是它，这就导致了死循环。
           桶排序还是一个比较小众的技巧，在这里也算是最大程度地彰显了它的独特之处。不过除此之外，
           其他的应用就并不那么常见了。
 * 
 * 
 * 
 * http://www.cnblogs.com/yuzhangcmu/p/4200096.html
 * https://segmentfault.com/a/1190000003488849
 */
public class FirstMissingPositive {
	// Refer to
	// http://www.cnblogs.com/yuzhangcmu/p/4200096.html
	public int firstMissingPositive_With_Bugs(int[] A) {
        // bug 3: when length is 0, return 1;
        if (A == null) {
            return 0;
        }
        
        for (int i = 0; i < A.length; i++) {
            // bug 1: TLE , should judge when A[i] - 1 == i;
            while (A[i] - 1 != i && A[i] > 0) {
                // bug 2: can't exchange a same node: A[A[i] - 1] != A[i]
                if (A[i] - 1 < A.length && A[A[i] - 1] != A[i]) {
                    swap(A, i, A[i] - 1);    
                } else {
                    // when the number is out of range, delete it.
                    A[i] = 0;
                }
            }
        }
        
        for (int i = 0; i < A.length; i++) {
            if (A[i] <= 0) {
                return i + 1;
            }
        }
        
        return A.length + 1;
    }
    
    /**
     * 简化后
     * 其实交换的条件就是3个：
		1: A[i] is in the range;
		2: A[i] > 0.
		3: The target is different; （如果不判断这个，会造成死循环，因为你交换过来一个一样的值）
     */
    public int firstMissingPositive(int[] A) {
        if (A == null) {
            return 0;
        }
        
        for(int i = 0; i < A.length; i++) {
            // 1: A[i] is in the range;
            // 2: A[i] > 0.
            // 3: The target is different;
        	//    此外，最容易出错的地方就是没有考虑重复数字出现的可能了。如果没有考虑到这一点，内层循环
        	//    就会陷入到一个死循环当中。这一点也很好想，假设有一个数出现了两次，其中一个正好在它对应
        	//    的位置上，当遍历到另外一个时，由于肯定与当前位置不符，就需要交换，而换回来的数就是还是它，
        	//    这就导致了死循环, e,g input = {1}
            while (A[i] <= A.length && A[i] > 0 && A[A[i] - 1] != A[i]) {
                swap(A, i, A[i] - 1);    
            }
        }
        
        for(int i = 0; i < A.length; i++) {
            if(A[i] != i + 1) {
                return i + 1;
            }
        }
        
        return A.length + 1;
    }
    
    private void swap(int[] A, int l, int r) {
        int tmp = A[l];
        A[l] = A[r];
        A[r] = tmp;
    }
    
    public static void main(String[] args) {
    	
    }
}
