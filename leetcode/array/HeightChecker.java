/**
Refer to
https://leetcode.com/problems/height-checker/
Students are asked to stand in non-decreasing order of heights for an annual photo.

Return the minimum number of students that must move in order for all students to be standing in non-decreasing order of height.

Notice that when a group of students is selected they can reorder in any possible way between themselves and the non selected 
students remain on their seats.

Example 1:
Input: heights = [1,1,4,2,1,3]
Output: 3
Explanation: 
Current array : [1,1,4,2,1,3]
Target array  : [1,1,1,2,3,4]
On index 2 (0-based) we have 4 vs 1 so we have to move this student.
On index 4 (0-based) we have 1 vs 3 so we have to move this student.
On index 5 (0-based) we have 3 vs 4 so we have to move this student.

Example 2:
Input: heights = [5,1,2,3,4]
Output: 5

Example 3:
Input: heights = [1,2,3,4,5]
Output: 0

Constraints:
1 <= heights.length <= 100
1 <= heights[i] <= 100
*/

// Solution 1: Sort array O(NlogN)
// Refer to
// https://leetcode.com/problems/height-checker/discuss/299211/JavaPython-3-O(nlogn)-and-O(n)-codes.
/**
Time: O(nlogn), space: O(n), where n = heights.length, space includes the returning array h.
*/
class Solution {
    public int heightChecker(int[] heights) {
        int[] h = heights.clone();
        Arrays.sort(h);
        int n = heights.length;
        int count = 0;
        for(int i = 0; i < n; i++) {
            if(h[i] != heights[i]) {
                count++;
            }
        }
        return count;
    }
}

// Solution 2: Counting Sort
// Refer to
// https://leetcode.com/problems/height-checker/discuss/300472/Java-0ms-O(n)-solution-no-need-to-sort
/**
Just count the frequency of each height (using HashMap or int[] as the height is promised to be within range[1, 100]) and use 2 pointers to make comparison:

class Solution {
    public int heightChecker(int[] heights) {
        int[] heightToFreq = new int[101];
        
        for (int height : heights) {
            heightToFreq[height]++;
        }
        
        int result = 0;
        int curHeight = 0;
        
        for (int i = 0; i < heights.length; i++) {
            while (heightToFreq[curHeight] == 0) {
                curHeight++;
            }
            
            if (curHeight != heights[i]) {
                result++;
            }
            heightToFreq[curHeight]--;
        }
        
        return result;
    }
}
*/

// https://leetcode.com/problems/height-checker/discuss/299211/JavaPython-3-O(nlogn)-and-O(n)-codes.
/**
Count the heights to get the occurrence of each height;
From 1 to 100, accumulate to get the occurrences of shorter than and equal of each height ( number of heights <= h);
From the highest height to the shortest, compare one by one to get the number of mismatches.
    public int heightChecker(int[] heights) {
        int[] cnt = new int[101];
        for (int h : heights) { 
            ++cnt[h]; 
        }
        for (int i = 1; i <= 100; ++i) { 
            cnt[i] += cnt[i - 1]; 
        }
        int ans = 0;
        for (int i = heights.length - 1; i >= 0; --i) {
            if (heights[--cnt[heights[i]]] != heights[i]) 
                ++ans;
        }   
        return ans;
    }
Time & space: O(n), where n = heights.length.
*/
class Solution {
    public int heightChecker(int[] heights) {
        int[] count = new int[101];
        for(int h : heights) {
            count[h]++;
        }
        for(int i = 1; i <= 100; i++) {
            count[i] += count[i - 1];
        }
        int result = 0;
        for(int i = heights.length - 1; i >= 0; i--) {
            // Count Sorting:
            // Be careful, not 'count[heights[i]] - 1', since we also
            // need to decrease count[heights[i]] itself by 1, not only
            // calculate the index by -1
            int index = --count[heights[i]];
            if(heights[index] != heights[i]) {
                result++;
            }
        }
        return result;
    }
}
