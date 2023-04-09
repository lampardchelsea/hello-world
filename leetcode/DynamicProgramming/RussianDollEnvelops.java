/**
 * Refer to
 * https://leetcode.com/problems/russian-doll-envelopes/description/
 * You have a number of envelopes with widths and heights given as a pair of 
   integers (w, h). One envelope can fit into another if and only if both the 
   width and height of one envelope is greater than the width and height of 
   the other envelope.
   What is the maximum number of envelopes can you Russian doll? (put one inside other)
   Example:
   Given envelopes = [[5,4],[6,4],[6,7],[2,3]], the maximum number of envelopes 
   you can Russian doll is 3 ([2,3] => [5,4] => [6,7]).
 *
 *
 * Solution
 * http://www.cnblogs.com/grandyang/p/5568818.html
 * https://segmentfault.com/a/1190000003819886
 * https://discuss.leetcode.com/topic/47404/simple-dp-solution
 * https://discuss.leetcode.com/topic/47469/java-nlogn-solution-with-explanation
*/

// Solution 1:
// Refer to
// http://www.cnblogs.com/grandyang/p/5568818.html
// https://discuss.leetcode.com/topic/47404/simple-dp-solution
// 这道题给了我们一堆大小不一的信封，让我们像套俄罗斯娃娃那样把这些信封都给套起来，这道题实际上
// 是之前那道Longest Increasing Subsequence的具体应用，而且难度增加了，从一维变成了两维，
// 但是万变不离其宗，解法还是一样的，首先来看DP的解法，这是一种brute force的解法，首先要给所有
// 的信封按从小到大排序，首先根据宽度从小到大排，如果宽度相同，那么高度小的再前面，这是STL里面
// sort的默认排法，所以我们不用写其他的comparator，直接排就可以了，然后我们开始遍历，对于每一个
// 信封，我们都遍历其前面所有的信封，如果当前信封的长和宽都比前面那个信封的大，那么我们更新dp数组，
// 通过dp[i] = max(dp[i], dp[j] + 1)。然后我们每遍历完一个信封，都更新一下结果res
class Solution {
    public int maxEnvelopes(int[][] envelopes) {
        if(envelopes == null || envelopes.length == 0 || envelopes[0] == null || envelopes[0].length == 0) {
            return 0;
        }
        // sort
        Arrays.sort(envelopes, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                if(a[0] != b[0]) {
                    return a[0] - b[0];
                } else {
                    return a[1] - b[1];
                }
            }; 
        });
        // state
        int m = envelopes.length;
        int[] dp = new int[m];
        // initialize
        for(int i = 0; i < m; i++) {
            dp[i] = 1;
        }
        int max = 0;
        // function
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < i; j++) {
                if(envelopes[i][0] > envelopes[j][0] && envelopes[i][1] > envelopes[j][1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(dp[i], max);
        }
        // answer
        return max;
    }
}

// Solution 2:
// Refer to
// http://www.cnblogs.com/grandyang/p/5568818.html
// https://discuss.leetcode.com/topic/47469/java-nlogn-solution-with-explanation
// http://www.cnblogs.com/grandyang/p/4938187.html
// https://discuss.leetcode.com/topic/47469/java-nlogn-solution-with-explanation/31?page=2
/**
 我们来看一种思路更清晰的二分查找法，跟上面那种方法很类似，思路是先建立一个空的dp数组，然后开始遍历原数组，
 对于每一个遍历到的数字，我们用二分查找法在dp数组找第一个不小于它的数字，如果这个数字不存在，那么直接在dp
 数组后面加上遍历到的数字，如果存在，则将这个数字更新为当前遍历到的数字，最后返回dp数字的长度即可，注意的是，
 跟上面的方法一样，特别注意的是dp数组的值可能不是一个真实的LIS
*/

// https://leetcode.com/problems/longest-increasing-subsequence/solution/
/**
 Approach #4 Dynamic Programming with Binary Search[Accepted]:
 Algorithm
 In this approach, we scan the array from left to right. We also make use of a dp array initialized 
 with all 0's. This dp array is meant to store the increasing subsequence formed by including the 
 currently encountered element. While traversing the nums array, we keep on filling the dp array 
 with the elements encountered so far. For the element corresponding to the jth index (nums[j]), we 
 determine its correct position in the dp array(say ith index) by making use of Binary Search(which 
 can be used since the dp array is storing increasing subsequence) and also insert it at the correct 
 position. An important point to be noted is that for Binary Search, we consider only that portion of 
 the dp array in which we have made the updations by inserting some elements at their correct 
 positions(which remains always sorted). Thus, only the elements upto the ith index in the dp array 
 can determine the position of the current element in it. Since, the element enters its correct 
 position(i) in an ascending order in the dp array, the subsequence formed so far in it is surely 
 an increasing subsequence. Whenever this position index ii becomes equal to the length of the LIS 
 formed so far(len), it means, we need to update the len as len = len + 1.
 Note: dp array does not result in longest increasing subsequence, but length of dpdp array will 
 give you length of LIS.
 
 Consider the example:
 input: [0, 8, 4, 12, 2]
 dp: [0]
 dp: [0, 8]
 dp: [0, 4]
 dp: [0, 4, 12]
 dp: [0 , 2, 12] which is not the longest increasing subsequence, but length of dp array results in 
                 length of Longest Increasing Subsequence.
 
 Note: Arrays.binarySearch() method returns index of the search key, if it is contained in the array, 
 else it returns (-(insertion point) - 1). The insertion point is the point at which the key would be 
 inserted into the array: the index of the first element greater than the key, or a.length if all 
 elements in the array are less than the specified key.
 
 Complexity Analysis
 Time complexity : O(nlog(n)). Binary search takes log(n) time and it is called n times.
 Space complexity : O(n). dp array of size nn is used.
*/

class Solution {
    public int maxEnvelopes(int[][] envelopes) {
        if(envelopes == null || envelopes.length == 0 || envelopes[0] == null || envelopes[0].length == 0) {
            return 0;
        }
        // sort
        /**
         binary search solution: O(nlogn) width is increasing, but if two widths are the same, 
         the height is decreasing after sorting, all envolopes are valid 'based on width', 
         so we just binary search based on 'heights' to choose 'some of them' to meet the 
         requirement
         Ex. after sorting: (1,3), (3,5), (6,8), (6,7), (8,4), (9,5)
         transform to question find LIS: [3,5,8,7,4,5] => like '300. Longest Increasing Subsequence'
        */
        Arrays.sort(envelopes, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                if(a[0] != b[0]) {
                    return a[0] - b[0];
                } else {
                    return b[1] - a[1];
                }
            }; 
        });
        // state
        int m = envelopes.length;
        int[] dp = new int[m];
        // intialize
        int size = 0;
        // function
        // Refer to
        // https://discuss.leetcode.com/topic/47469/java-nlogn-solution-with-explanation/31?page=2
        for(int[] envelop : envelopes) {
            /**
            我们来看一种思路更清晰的二分查找法，跟上面那种方法很类似，思路是先建立一个空的dp数组，
            然后开始遍历原数组，对于每一个遍历到的数字，我们用二分查找法在dp数组找第一个不小于它
            的数字，如果这个数字不存在，那么直接在dp数组后面加上遍历到的数字，如果存在，则将这个
            数字更新为当前遍历到的数字，最后返回dp数字的长度即可，注意的是，跟上面的方法一样，
            特别注意的是dp数组的值可能不是一个真实的LIS
            */
            int index = findIndex(dp, 0, size, envelop[1]);
            dp[index] = envelop[1];
            if(index == size) {
                size++;
            }
        }
        return size;
    }
    
    // 我们用二分查找法在dp数组找第一个不小于它的数字
    private int findIndex(int[] dp, int start, int end, int target) {
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            if(dp[mid] == target) {
                return mid;
            } else if(dp[mid] > target) {
                end = mid;
            } else {
                start = mid;
            }
        }
        if(dp[start] >= target) {
            return start;
        } else {
            return end;
        }
    }
}






























































https://leetcode.com/problems/russian-doll-envelopes/

You are given a 2D array of integers envelopes where envelopes[i] = [wi, hi] represents the width and the height of an envelope.

One envelope can fit into another if and only if both the width and height of one envelope are greater than the other envelope's width and height.

Return the maximum number of envelopes you can Russian doll (i.e., put one inside the other).

Note: You cannot rotate an envelope.

Example 1:
```
Input: envelopes = [[5,4],[6,4],[6,7],[2,3]]
Output: 3
Explanation: The maximum number of envelopes you can Russian doll is 3 ([2,3] => [5,4] => [6,7]).
```

Example 2:
```
Input: envelopes = [[1,1],[1,1],[1,1]]
Output: 1
```

Constraints:
- 1 <= envelopes.length <= 105
- envelopes[i].length == 2
- 1 <= wi, hi <= 105
---
Attempt 1: 2023-04-08

Solution 1: DP (30 min, global 'max' update more strict than L300.Longest Increasing Subsequence, TLE)
```
class Solution { 
    public int maxEnvelopes(int[][] envelopes) { 
        Arrays.sort(envelopes, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]); 
        int len = envelopes.length; 
        int[] dp = new int[len]; 
        Arrays.fill(dp, 1); 
        int max = Integer.MIN_VALUE; 
        for(int i = 0; i < len; i++) { 
            for(int j = 0; j < i; j++) { 
                if(envelopes[i][0] > envelopes[j][0] && envelopes[i][1] > envelopes[j][1]) { 
                    dp[i] = Math.max(dp[i], dp[j] + 1); 
                    // Not same as L300.Longest Increasing Subsequence, 
                    // we cannot update global 'max' in inside for loop, 
                    // have to update in ourside for loop, because have 
                    // to handle test case like: 
                    // int[][] envelopes = {{1,1},{1,1},{1,1}} when 
                    // no envelopes[i] strictly surround envelopes[j] exist 
                    //max = Math.max(max, dp[i]); 
                } 
            } 
            max = Math.max(max, dp[i]); 
        } 
        return max; 
    } 
}

Time Complexity : O(N^2)  
Space Complexity : O(N)
```

Refer to
https://leetcode.com/problems/russian-doll-envelopes/solutions/82778/two-solutions-in-c-well-explained/

DP

It's quite intuitive to adopt DP to solve this problem:
- sorting the envelopes first via its first value (width)
- allocating an array to record the maximal amount for each envelope (the maximal amount we can get ending with the current envelope)
Directly the time cost here will be o(nlogn+n^2) which is o(n^2) and meantime taking up o(n) extra space.
```
int maxenvelopes(vector<pair<int, int>>& envelopes)  
{ 
	int size = envelopes.size(); 
	if(!size) return 0; 
	sort(envelopes.begin(), envelopes.end()); 
	int maxrolls[size]{0}, maxroll = 1; 
	maxrolls[0] = 1; 
	for(int i = 1; i < size; ++i) 
	{ 
		maxrolls[i] = 1; 
		for(int j = i-1; j >= 0; --j) 
			if(envelopes[i].first>envelopes[j].first && envelopes[i].second>envelopes[j].second) 
				maxrolls[i] = max(maxrolls[i], maxrolls[j]+1); 
		maxroll = max(maxroll, maxrolls[i]); 
	} 
	return maxroll; 
}
```

---
Solution 2: Binary Search (60 min, width sort ascending, height sort descending, then problem convert into L300.Longest Increasing Subsequence by binary search on second dimension to find longest increasing subsequence)
```
class Solution { 
    public int maxEnvelopes(int[][] envelopes) { 
        Arrays.sort(envelopes, (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]); 
        List<Integer> list = new ArrayList<Integer>(); 
        for(int[] cur : envelopes) { 
            if(list.size() == 0 || list.get(list.size() - 1) < cur[1]) { 
                list.add(cur[1]); 
            } else { 
                int index = binarySearch(list, cur[1]); 
                list.set(index, cur[1]); 
            } 
        } 
        return list.size(); 
    } 
    private int binarySearch(List<Integer> list, int target) { 
        int start = 0; 
        int end = list.size() - 1; 
        while(start <= end) { 
            int mid = start + (end - start) / 2; 
            if(list.get(mid) >= target) { 
                end = mid - 1; 
            } else { 
                start = mid + 1; 
            } 
        } 
        return start; 
    } 
}

Time Complexity : O(NlogN)   
Space Complexity : O(N)
```

Why sort width as ascending and height as descending ?
Refer to
https://leetcode.com/problems/russian-doll-envelopes/solutions/2071477/c-java-python-best-explanation-with-pictures/

Why we need to sort?

- In these types of problem when we are dealing with two dimensions, we need to reduce the problem from two-dimensional array into a one-dimensional array in order to improve time complexity.
- "Sort first when things are undecided", sorting can make the data orderly, reduce the degree of confusion, and often help us to sort out our thinking. the same is true with this question. Now, after doing the correct sorting, we just need to find Longest Increasing Subsequence of that one dimensional array.
  Now, you may be wondered what correct sorting actually is?
  It is the sorting which we do to order to achieve the answer. Like, increasing, non-increasing sorting. Without any further discussion, let's dig into Intuition followed by algorithm.

Algorithm

- We sort the array in increasing order of width. And if two widths are same, we need to sort height in decreasing order.
- Now why we need to sort in decreasing order if two widths are same. By this practice, we're assuring that no width will get counted more than one time. Let's take an example
  envelopes=[[3, 5], [6, 7], [7, 13], [6, 10], [8, 4], [7, 11]]

Now, if you see for a while,6 and 7 is counted twice while we're calculating the length of LIS, which will give the wrong ans. As question is asking, if any width/height are less than or equal, then, it is not possible to russian doll these envelopes.

Now, we know the problem. So, how can we tackle these conditions when two width are same, so that it won't affect our answer. We can simple reverse sort the height if two width are equal, to remove duplicacy.

Now, you may question, how reverse sorting the height would remove duplicity? As the name itself says, Longest Increasing Subsequence, the next coming height would be less than the previous one. Hence, forbidding it to increase length count.

If you don't understand how LIS is calculated here, I strongly refer you to follow the prerequisite.

Now, we have successfully reduced the problem to LIS! All you need to apply classical LIS on heights, to calculate the ans. This would be the maximum number of envelopes can be Russians doll.
```
class Solution { 
    public int binarySearch(int[] dp, int val){ 
        int lo=0,hi=dp.length-1,res=0; 
        while(lo<=hi){ 
            int mid=(lo+hi)/2; 
            if(dp[mid]<val){ 
                res=mid; 
                lo=mid+1; 
            }else{ 
                hi=mid-1; 
            } 
        } 
        return res+1; 
    } 
    public int maxEnvelopes(int[][] envelopes) { 
        Arrays.sort(envelopes,(a,b)->a[0]==b[0]?b[1]-a[1]:a[0]-b[0]); 
        int[] LIS=new int[envelopes.length+1]; 
        Arrays.fill(LIS,Integer.MAX_VALUE); 
        LIS[0]=Integer.MIN_VALUE; 
        int ans=0; 
        for(int i=0;i<envelopes.length;i++){ 
            int val=envelopes[i][1]; 
            int insertIndex=binarySearch(LIS,val); 
            ans=Math.max(ans,insertIndex); 
            if(LIS[insertIndex]>=val){ 
                LIS[insertIndex]=val; 
            } 
        } 
        return ans; 
    } 
}
```
Now, if you compare the code of this problem with the classical LIS, it is very similar. 
In fact, we have added only one line to get the maximum Russian Doll.
Arrays.sort(envelopes,(a,b)->a[0]==b[0]?b[1]-a[1]:a[0]-b[0]);
Time Complexity- O(nlogn)
Space Complexity- O(n)

---
Find longest increasing subsequence on the second dimension exactly same way as L300.Longest Increasing Subsequence

Refer to
L300. Longest Increasing Subsequence Binary Search Solution
```
class Solution {  
    public int lengthOfLIS(int[] nums) {  
        List<Integer> list = new ArrayList<Integer>();  
        for(int cur : nums) {  
            if(list.size() == 0 || list.get(list.size() - 1) < cur) {  
                list.add(cur);  
            } else {  
                int index = binarySearch(list, cur);  
                list.set(index, cur);  
            }  
        }  
        return list.size();  
    }  
    // Find the index of first element larger than 'target'  
    private int binarySearch(List<Integer> list, int target) {  
        int start = 0;  
        int end = list.size() - 1;  
        while(start <= end) {  
            int mid = start + (end - start) / 2;  
            if(list.get(mid) >= target) {  
                end = mid - 1;  
            } else {  
                start = mid + 1;  
            }  
        }  
        return start;  
    }  
}
```

Refer to
https://leetcode.com/problems/russian-doll-envelopes/solutions/82763/java-nlogn-solution-with-explanation/comments/87032
```
// binary search solution: O(nlogn) 
// width is increasing, but if two widths are the same, the height is decreasing 
// after sorting, all envolopes are valid 'based on width', so we just binary search based on 'heights' 
// to choose 'some of them' to meet the requirement 
// Ex. after sorting: (1,3), (3,5), (6,8), (6,7), (8,4), (9,5) 
// transform to question find LIS: [3,5,8,7,4,5] => like '300. Longest Increasing Subsequence' 
public class Solution { 
    public int maxEnvelopes(int[][] envelopes) { 
        if(envelopes.length < 2) return envelopes.length; 
         
        Arrays.sort(envelopes, new EnvelopeComparator()); 
        int[] dp = new int[envelopes.length]; 
        int size = 0; 
         
        for(int[] envelope: envelopes) { 
            // binary search 
            int left = 0, right = size, middle = 0;     // right = size 
            while(left < right) { 
                middle = left + (right - left) / 2; 
                if(dp[middle] < envelope[1]) left = middle + 1; 
                else right = middle; 
            } 
             
            // left is the right position to 'replace' in dp array 
            dp[left] = envelope[1]; 
            if(left == size) size++; 
        } 
        return size; 
    } 
     
    class EnvelopeComparator implements Comparator<int[]> { 
        public int compare(int[] e1, int[] e2) { 
            return e1[0] == e2[0] ? e2[1] - e1[1] : e1[0] - e2[0]; 
        } 
    } 
}
```
