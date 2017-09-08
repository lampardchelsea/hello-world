/**
 * Refer to
 * http://www.lintcode.com/en/problem/sort-colors-ii/
 * Given an array of n objects with k different colors (numbered from 1 to k), 
   sort them so that objects of the same color are adjacent, with the colors in the order 1, 2, ... k.
   Notice
    You are not suppose to use the library's sort function for this problem.
    k <= n
    Have you met this question in a real interview? Yes
    Example
    Given colors=[3, 2, 2, 1, 4], k=4, your code should sort colors in-place to [1, 2, 2, 3, 4].
 *
 * Solution
 * https://github.com/lampardchelsea/hello-world/blob/master/lintcode/TwoPoints/VideoExamples/PartitionArray/QuickSort_JiuZhang.java
*/
// Solution 1:
// 用quick sort的partition做就行了，不用递归，时间复杂度是O(n * k), 空间是O(1), 
// 不过，QuickSort应该不能称作O(1)空间的解法，因为它在递归中至少会用掉O(logN)内存
// Actually Solution 1 is totally same as Solution 2 efficient, as it still recursively calling QuickSort, 
// even its name is RainBowSort
class Solution {
    /**
     * @param colors: A list of integer
     * @param k: An integer
     * @return: nothing
     */
    public void sortColors2(int[] colors, int k) {
        if (colors == null || colors.length == 0) {
            return;
        }
        rainbowSort(colors, 0, colors.length - 1, 1, k);
    }
    
    public void rainbowSort(int[] colors,
                            int left,
                            int right,
                            int colorFrom,
                            int colorTo) {
        if (colorFrom == colorTo) {
            return;
        }
        
        if (left >= right) {
            return;
        }
        
        int colorMid = (colorFrom + colorTo) / 2;
        int l = left, r = right;
        while (l <= r) {
            while (l <= r && colors[l] <= colorMid) {
                l++;
            }
            while (l <= r && colors[r] > colorMid) {
                r--;
            }
            if (l <= r) {
                int temp = colors[l];
                colors[l] = colors[r];
                colors[r] = temp;
                
                l++;
                r--;
            }
        }
        
        rainbowSort(colors, left, r, colorFrom, colorMid);
        rainbowSort(colors, l, right, colorMid + 1, colorTo);
    }
}


// Solution 2: Purely Quick Sort, O(nlogn)
// With this solution you don't even need to use given parameter k
class Solution {
    /**
     * @param colors: A list of integer
     * @param k: An integer
     * @return: nothing
     */
    public void sortColors2(int[] colors, int k) {
        if(k <= 0 || colors == null || colors.length == 0) {
            return;
        }
        quickSort(colors, 0, colors.length - 1);
    }
    
    private void quickSort(int[] colors, int start, int end) {
        if(start >= end) {
            return;
        }
        int left = start;
        int right = end;
        int pivot = colors[start + (end - start) / 2];
        while(left <= right) {
            while(left <= right && colors[left] < pivot) {
                left++;
            }
            while(left <= right && colors[right] > pivot) {
                right--;
            }
            if(left <= right) {
                int temp = colors[left];
                colors[left] = colors[right];
                colors[right] = temp;
                left++;
                right--;
            }
        }
        quickSort(colors, start, right);
        quickSort(colors, left, end);
    }
}
