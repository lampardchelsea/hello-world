/**
 * Refer to
 * Suppose a sorted array is rotated at some pivot unknown to you beforehand.
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * You are given a target value to search. If found in the array return its index, otherwise return -1.
 * You may assume no duplicate exists in the array.
 * Have you met this question in a real interview?
 * Example
   For [4, 5, 1, 2, 3] and target=1, return 2.
   For [4, 5, 1, 2, 3] and target=0, return -1.
 *
 * Solution
 * Refer to
 * http://www.jiuzhang.com/solutions/search-in-rotated-sorted-array/
 * http://blog.leanote.com/post/westcode/77b6bf8c4b5b
 * 这题的思路是在 Find Minimum in roated Sorted Array 这题上延伸的，如果不懂先看看那题。
 * 这题同样也是用二分法，那么我们看看二分法的条件满足了哪几个
    二分要找的 target - 题目给出
    执行二分的数据 - 题目给出
    二分的区间
        这个可能需要一点思考，因为我们只做一次二分，所以区间就是整个 array
    二分指针的变化
        这个才是我们需要找的点。假设我们mid任意找到一个点， 那么我们应该往哪个方向移动搜索呢？这个就是这题的考点。
 * 要想清楚这一点，我们同样先用栗子4 5 6 7 0 1 2画图

              7
            6 
          5        
        4
        _______________
                    2
                  1
                0 
    idx 0 1 2 3 4 5 6

  * 我们看到，和 Find Minimum 一样。一个 rotated 过后的 array，可以分为这么两个部分，他们分别都是 sorted 的。而且他有这么一个性质
  * 后半部分的最大值（整个 rotated array的最后一个值）不会大于前半部分的最小值
  * 那么我们是不是就可以利用这个性质，通过判断 mid 和 target 是否在前半部分还是后半部分来分析 target 和 mid 的相对位置？
  * 举个栗子，假设我们要找1, 我们第一个 mid=6
  * 通过比较最后一个值来看处于哪个部分 mid=6>2前半部分 target=1 <= 2属于后半部分
  * 那么我们知道前半部分是无论符合都不会包含1的，所以我们要向右搜索。（这样是不是就就和我们普通的二分改变方向反过来？）
  * 那如果 mid 和 target 处于同一个部分呢? 假设 target 是 4 mid 是 6，那么我们发现他们都处于前半部分，那是不是按照普通二分的改变方向，
  * 即向前搜索就可以了？
  * 如果继续举例可以发现 如果 mid 在后半部分也是同样的性质。那么我们是不是就可以得出一个结论，也就是我们解题的关键？即
  * 每次二分搜索中，如果 mid 和 target 处于同一部分，我们使用普通二分搜索的方向。如果mid 和 target 处于不同部分，我们使用相反的搜索方向。
  * 在哪里部分的判断我们使用一个 if 语句来和最后一个值比较即可
*/

public class SearchInRotatedSortedArray {
    public int search(int[] A, int target) {
    	// Check null and empty case
        if(A == null || A.length == 0) {
            return -1;
        }
        int start = 0;
        int end = A.length - 1;
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            if(A[mid] == target) {
                return mid;
            }
            // We need to separate into 2 cases
            // 1. 'mid' item happen in first rise zone (e.g 4 -- 7)
            // 2. 'mid' item happen in second rise zone (e.g 0 -- 2)
            // First rise zone
            // Note 1: Both 'A[mid] > A[start]' or 'A[mid] > A[end]' works,
            // as both represent 'mid' item happen in first rise zone
            // Note 2: Use 'else if' or 'if' here both works
            else if(A[mid] > A[start]) {
                if(A[start] <= target && target <= A[mid]) {
                    end = mid;
                } else {
                    start = mid;
                }
            // Second rise zone
            } else {
                if(A[mid] <= target && target <= A[end]) {
                    start = mid;
                } else {
                    end = mid;
                }
            }
        }
        if(A[start] == target) {
            return start;
        }
        if(A[end] == target) {
            return end;
        }
        return -1;
    }
    
    public static void main(String[] args) {
    	//int[] A = {1001,10001,10007,1,10,101,201};
    	int[] A = {0,1,2,-10,-9,-8,-7,-6,-5,-4,-3,-2,-1};
    	//int target = 10001;
    	int target = -9;
    	SearchInRotatedSortedArray s = new SearchInRotatedSortedArray();
    	int result = s.search(A, target);
    	System.out.println(result);
    }
    
}

