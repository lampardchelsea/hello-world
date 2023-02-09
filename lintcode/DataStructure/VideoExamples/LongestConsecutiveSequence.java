/**
 * Refer to
 * http://www.lintcode.com/en/problem/longest-consecutive-sequence/
 * Given an unsorted array of integers, find the length of the longest consecutive elements sequence.
    Have you met this question in a real interview? Yes
    Clarification
    Your algorithm should run in O(n) complexity.

    Example
    Given [100, 4, 200, 1, 3, 2],
    The longest consecutive elements sequence is [1, 2, 3, 4]. Return its length: 4.
 *
 * Solution
 * https://discuss.leetcode.com/topic/25493/simple-fast-java-solution-using-set
 * http://www.cnblogs.com/springfor/p/3869981.html
 * 这道题利用HashSet的唯一性解决，能使时间复杂度达到O(n)。首先先把所有num值放入HashSet，
   然后遍历整个数组，如果HashSet中存在该值，就先向下找到边界，找的同时把找到的值一个一个
   从set中删去，然后再向上找边界，同样要把找到的值都从set中删掉。所以每个元素最多会被遍
   历两边，时间复杂度为O(n)。
*/
public class Solution {
    /*
     * @param num: A list of integers
     * @return: An integer
     */
    public int longestConsecutive(int[] num) {
        if(num == null||num.length == 0)
            return 0;
        
        HashSet<Integer> hs = new HashSet<Integer>();  
        
        for (int i = 0 ;i<num.length; i++)   
            hs.add(num[i]);  
         
        int max = 0;  
        for(int i=0; i<num.length; i++){  
            if(hs.contains(num[i])){
                int count = 1;  
                hs.remove(num[i]);
                
                int low = num[i] - 1; 
                while(hs.contains(low)){  
                    hs.remove(low);  
                    low--;  
                    count++;  
                }
                
                int high = num[i] + 1;  
                while(hs.contains(high)){  
                    hs.remove(high);  
                    high++;  
                    count++;  
                }  
                max = Math.max(max, count);  
            }  
        }  
        return max;
    }
}











































https://leetcode.com/problems/longest-consecutive-sequence/

Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.

You must write an algorithm that runs in O(n) time.

Example 1:
```
Input: nums = [100,4,200,1,3,2]
Output: 4
Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
```

Example 2:
```
Input: nums = [0,3,7,2,5,8,4,6,0,1]
Output: 9
```

Constraints:
- 0 <= nums.length <= 105
- -109 <= nums[i] <= 109
---
Attempt 1: 2023-02-08

Solution 1:  Native for loop O(N^3) (10 min, TLE)
```
class Solution { 
    public int longestConsecutive(int[] nums) { 
        int longest = 0; 
        for(int num : nums) { 
            int curLongest = 1; 
            int curNum = num; 
            while(contains(curNum + 1, nums)) { 
                curNum++; 
                curLongest++; 
            } 
            longest = Math.max(longest, curLongest); 
        } 
        return longest; 
    }

    private boolean contains(int curNum, int[] nums) { 
        for(int num : nums) { 
            if(num == curNum) { 
                return true; 
            } 
        } 
        return false; 
    } 
}

Time Complexity: O(n^3)   
Space Complexity: O(1)
```

Refer to
https://leetcode.com/problems/longest-consecutive-sequence/solutions/127576/longest-consecutive-sequence/

Approach 1: Brute Force

Intuition
Because a sequence could start at any number in nums, we can exhaust the entire search space by building as long a sequence as possible from every number.

Algorithm
The brute force algorithm does not do anything clever - it just considers each number in nums, attempting to count as high as possible from that number using only numbers in nums. After it counts too high (i.e. currentNum refers to a number that nums does not contain), it records the length of the sequence if it is larger than the current best. The algorithm is necessarily optimal because it explores every possibility.
```
class Solution { 
    private boolean arrayContains(int[] arr, int num) { 
        for (int i = 0; i < arr.length; i++) { 
            if (arr[i] == num) { 
                return true; 
            } 
        } 
        return false; 
    } 
    public int longestConsecutive(int[] nums) { 
        int longestStreak = 0; 
        for (int num : nums) { 
            int currentNum = num; 
            int currentStreak = 1; 
            while (arrayContains(nums, currentNum + 1)) { 
                currentNum += 1; 
                currentStreak += 1; 
            } 
            longestStreak = Math.max(longestStreak, currentStreak); 
        } 
        return longestStreak; 
    } 
}
```
Complexity Analysis
- Time complexity : O(n^3)
  The outer loop runs exactly n times, and because current Num increments by 1 during each iteration of the while loop, it runs in O(n) time. Then, on each iteration of the while loop, an O(n) lookup in the array is performed. Therefore, this brute force algorithm is really three nested O(n) loops, which compound multiplicatively to a cubic runtime.
- Space complexity : O(1).
  The brute force algorithm only allocates a handful of integers, so it uses constant additional space.
---
Solution 2: Sorting (30 min)
```
class Solution { 
    public int longestConsecutive(int[] nums) { 
        if(nums.length == 0) { 
            return 0; 
        } 
        Arrays.sort(nums); 
        int longest = 1; 
        int curLongest = 1; 
        for(int i = 1; i < nums.length; i++) { 
            // Test case: [1,2,0,1] 
            // Have to skip all duplicate elements after sorting 
            // [1,2,0,1] -> [0,1,1,2], have to skip second 1 to 
            // get the longest consecutive sequence as [0,1,2] 
            if(nums[i] == nums[i - 1]) { 
                continue; 
            } 
            if(nums[i] == nums[i - 1] + 1) { 
                curLongest++; 
            } else { 
                longest = Math.max(longest, curLongest); 
                curLongest = 1; 
            } 
        } 
        // Test case: [0,3,7,2,5,8,4,6,0,1] 
        // There is case longest not update till the end since 
        // the whole array is consecutive sequence  
        return Math.max(longest, curLongest); 
    } 
}

Time Complexity: O(nlogn)    
Space Complexity: O(1)
```

Refer to
https://leetcode.com/problems/longest-consecutive-sequence/solutions/127576/longest-consecutive-sequence/

Approach 2: Sorting

Intuition
If we can iterate over the numbers in ascending order, then it will be easy to find sequences of consecutive numbers. To do so, we can sort the array.

Algorithm
Before we do anything, we check for the base case input of the empty array. The longest sequence in an empty array is, of course, 0, so we can simply return that. For all other cases, we sort nums and consider each number after the first (because we need to compare each number to its previous number). If the current number and the previous are equal, then our current sequence is neither extended nor broken, so we simply move on to the next number. If they are unequal, then we must check whether the current number extends the sequence (i.e. nums[i] == nums[i-1] + 1). If it does, then we add to our current count and continue. Otherwise, the sequence is broken, so we record our current sequence and reset it to 1 (to include the number that broke the sequence). It is possible that the last element of nums is part of the longest sequence, so we return the maximum of the current sequence and the longest one.


Here, an example array is sorted before the linear scan identifies all consecutive sequences. The longest sequence is colored in red.
```
class Solution { 
    public int longestConsecutive(int[] nums) { 
        if (nums.length == 0) { 
            return 0; 
        } 
        Arrays.sort(nums); 
        int longestStreak = 1; 
        int currentStreak = 1; 
        for (int i = 1; i < nums.length; i++) { 
            if (nums[i] != nums[i-1]) { 
                if (nums[i] == nums[i-1]+1) { 
                    currentStreak += 1; 
                } 
                else { 
                    longestStreak = Math.max(longestStreak, currentStreak); 
                    currentStreak = 1; 
                } 
            } 
        } 
        return Math.max(longestStreak, currentStreak); 
    } 
}
```
Complexity Analysis
- Time complexity : O(nlgn).
  The main for loop does constant work n times, so the algorithm's time complexity is dominated by the invocation of sort, which will run in O(nlgn) time for any sensible implementation.
- Space complexity : O(1) (or O(n)).
  For the implementations provided here, the space complexity is constant because we sort the input array in place. If we are not allowed to modify the input array, we must spend linear space to store a sorted copy.
---
Solution 3: Hash Table (30 min)
```
class Solution {
    public int longestConsecutive(int[] nums) {
        int longest = 0;
        Set<Integer> set = new HashSet<Integer>();
        for(int num : nums) {
            set.add(num);
        }
        for(int num : nums) {
            if(!set.contains(num - 1)) {
                int curLongest = 1;
                int curNum = num;
                while(set.contains(curNum + 1)) {
                    curLongest++;
                    curNum++;
                }
                longest = Math.max(longest, curLongest);
            }
        }
        return longest;
    }
}

Time Complexity: O(n)     
Space Complexity: O(n)
```

Refer to
https://leetcode.com/problems/longest-consecutive-sequence/solutions/127576/longest-consecutive-sequence/

Approach 3: HashSet and Intelligent Sequence Building

Intuition
It turns out that our initial brute force solution was on the right track, but missing a few optimizations necessary to reach O(n) time complexity.

Algorithm
This optimized algorithm contains only two changes from the brute force approach: the numbers are stored in a HashSet(or Set, in Python) to allow O(1) lookups, and we only attempt to build sequences from numbers that are not already part of a longer sequence. This is accomplished by first ensuring that the number that would immediately precede the current number in a sequence is not present, as that number would necessarily be part of a longer sequence.
```
class Solution { 
    public int longestConsecutive(int[] nums) { 
        Set<Integer> num_set = new HashSet<Integer>(); 
        for (int num : nums) { 
            num_set.add(num); 
        } 
        int longestStreak = 0; 
        for (int num : num_set) { 
            if (!num_set.contains(num-1)) { 
                int currentNum = num; 
                int currentStreak = 1; 
                while (num_set.contains(currentNum+1)) { 
                    currentNum += 1; 
                    currentStreak += 1; 
                } 
                longestStreak = Math.max(longestStreak, currentStreak); 
            } 
        } 
        return longestStreak; 
    } 
}
```
Complexity Analysis
- Time complexity : O(n).
  Although the time complexity appears to be quadratic due to the while loop nested within the for loop, closer inspection reveals it to be linear. Because the while loop is reached only when current Num marks the beginning of a sequence (i.e. currentNum-1is not present in nums), the while loop can only run for n iterations throughout the entire runtime of the algorithm. This means that despite looking like O(n⋅n) complexity, the nested loops actually run in O(n+n)=O(n)time. All other computations occur in constant time, so the overall runtime is linear.
- Space complexity : O(n).
  In order to set up O(1) containment lookups, we allocate linear space for a hash table to store the O(n)O(n)O(n)numbers in nums. Other than that, the space complexity is identical to that of the brute force solution

