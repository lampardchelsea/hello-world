/**
 * Refer to
 * http://www.lintcode.com/en/problem/find-the-duplicate-number/#
 * https://leetcode.com/problems/find-the-duplicate-number/#/description
 * Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive), 
 * prove that at least one duplicate number must exist. Assume that there is only one duplicate 
 * number, find the duplicate one.
 * 
 * Note:
 * You must not modify the array (assume the array is read only).
 * You must use only constant, O(1) extra space.
 * Your runtime complexity should be less than O(n2).
 * There is only one duplicate number in the array, but it could be repeated more than once.
 * 
 */
public class FindTheDuplicateNumber {
	// Solution 1: Binary Search
	// Refer to
	// https://segmentfault.com/a/1190000003817671
	// http://www.cnblogs.com/grandyang/p/4843654.html
	// https://discuss.leetcode.com/topic/25580/two-solutions-with-explanation-o-nlog-n-and-o-n-time-o-1-space-without-changing-the-input-array
	/**
	 * This solution is based on binary search.
	 * At first the search space is numbers between 1 to n. Each time I select a number mid (which is the one 
	 * in the middle) and count all the numbers equal to or less than mid. Then if the count is more than mid, 
	 * the search space will be [1 mid] otherwise [mid+1 n]. I do this until search space is only one number.
	 * Let's say n=10 and I select mid=5. Then I count all the numbers in the array which are less than equal mid. 
	 * If the there are more than 5 numbers that are less than 5, then by Pigeonhole Principle 
	 * (https://en.wikipedia.org/wiki/Pigeonhole_principle) one of them has occurred more than once. So I shrink 
	 * the search space from [1 10] to [1 5]. Otherwise the duplicate number is in the second half so for the next 
	 * step the search space would be [6 10].
	 */
	// https://discuss.leetcode.com/topic/25580/two-solutions-with-explanation-o-nlog-n-and-o-n-time-o-1-space-without-changing-the-input-array/11
	/**
	 * If the there are more than 5 numbers that are less than 5, then by Pigeonhole Principle one of them has occurred more than once. [...]. 
	 * Otherwise the duplicate number is in the second half
	 * While that's all correct, it's incomplete. The pigeonhole principle is only one way. It does tell you that there's a duplicate if 
	 * there are "too many" items. But it doesn't tell you that there isn't a duplicate if there aren't "too many" items. 
	 * So how do you know the duplicate is in the second half?
	 * I'd put it this way:
	 * Let count be the number of elements in the range 1 .. mid, as in your solution.
	 * If count > mid, then there are more than mid elements in the range 1 .. mid and thus that range contains a duplicate.
	 * If count <= mid, then there are n+1-count elements in the range mid+1 .. n. That is, at least n+1-mid elements in a 
	 * range of size n-mid. Thus this range must contain a duplicate.
	 * Or less formally:
	 * We know that the whole range is "too crowded" and thus that the first half or the second half of the range is too crowded 
	 * (if both weren't, then neither would be the whole range). So you check to know whether the first half is too crowded, 
	 * and if it isn't, you know that the second half is. 
	 * Also, better say "less than or equal to 5", not just "less than 5".
	 * Btw, you could also use count = sum(i <= mid for i in nums) or even directly if sum(i <= mid for i in nums) <= mid:.
	 */
    public int findDuplicate(int[] nums) {
//        int lo = 0;
//        int hi = nums.length - 1;
//        while(lo <= hi) {
//            int mid = lo + (hi - lo) / 2;
//            int count = 0;
//            for(int i = 0; i < nums.length; i++) {
//                if(nums[i] <= mid) {
//                    count++;
//                }
//            }
//            if(count > mid) {
//                hi = mid - 1;
//            } else {
//                lo = mid + 1;
//            }
//        }
//        return lo;
    	// Represent 1 to n, lo = 1, hi = n(as array length is n + 1)
        int lo = 1;
        int hi = nums.length - 1;
        // loop condition not contains lo = hi
        while(lo < hi) {
            int mid = lo + (hi - lo) / 2;
            int count = 0;
            for(int i = 0; i < nums.length; i++) {
                if(nums[i] <= mid) {
                    count++;
                }
            }
            // When current subarray nums size > mid value,
            // duplicate number happen in first half of
            // current subarray, else it happens on second
            // half of current subarray.
            if(count > mid) {
            	// Special as not set as hi = mid - 1
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }
    
    // Solution 2: Same as Find Circle In Linked List II
    // Refer to
    // https://segmentfault.com/a/1190000003817671
    /**
     * 复杂度
     * 时间 O(N) 空间 O(1)
     * 思路
     * 假设数组中没有重复，那我们可以做到这么一点，就是将数组的下标和1到n每一个数一对一的映射起来。比如数组是213,则映射关系
     * 为0->2, 1->1, 2->3。假设这个一对一映射关系是一个函数f(n)，其中n是下标，f(n)是映射到的数。如果我们从下标
     * 为0出发，根据这个函数计算出一个值，以这个值为新的下标，再用这个函数计算，以此类推，直到下标超界。实际上可以产生一个类似
     * 链表一样的序列。比如在这个例子中有两个下标的序列，0->2->3。
     * 但如果有重复的话，这中间就会产生多对一的映射，比如数组2131,则映射关系为0->2, {1，3}->1, 2->3。这样，我们
     * 推演的序列就一定会有环路了，这里下标的序列是0->2->3->1->1->1->1->...，而环的起点就是重复的数。
     * 所以该题实际上就是找环路起点的题，和Linked List Cycle II一样。我们先用快慢两个下标都从0开始，快下标每轮映射两次，
     * 慢下标每轮映射一次，直到两个下标再次相同。这时候保持慢下标位置不变，再用一个新的下标从0开始，这两个下标都继续每轮映射一次，
     * 当这两个下标相遇时，就是环的起点，也就是重复的数。对这个找环起点算法不懂的
     */
    // https://discuss.leetcode.com/topic/25913/my-easy-understood-solution-with-o-n-time-and-o-1-space-without-modifying-the-array-with-clear-explanation
    /**
     * The main idea is the same with problem Linked List Cycle II,https://leetcode.com/problems/linked-list-cycle-ii/. 
     * Use two pointers the fast and the slow. The fast one goes forward two steps each time, while the slow one goes 
     * only step each time. They must meet the same item when slow==fast. In fact, they meet in a circle, the duplicate 
     * number must be the entry point of the circle when visiting the array from nums[0]. Next we just need to find the entry 
     * point. We use a point(we can use the fast one before) to visit form beginning with one step each time, do the same job 
     * to slow. When fast==slow, they meet at the entry point of the circle. The easy understood code is as follows.
     */
    public int findDuplicate2(int[] nums) {
    	int walker = 0;
    	int runner = 0;
    	// We can use while(true) because as description from question there must be a duplicate
    	// value cause loop situation and break out of while loop will definitely happen
    	while(true) {
    		walker = nums[walker];
    		runner = nums[nums[runner]];
    		if(walker == runner) {
    			break;
    		}
    	}
    	// Reset either one pointer to 0(same as head node), then find out the meeting point
    	// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/LinkedList/LinkedListCycleII.java
    	walker = 0;
    	while(true) {
    		walker = nums[walker];
    		runner = nums[runner];
    		if(walker == runner) {
    			break;
    		}
    	}
    	return walker;
    }
    
    // JiuZhang Template
    public int findDuplicate3(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        // Caution: start is 1, not present the index, present the real integer
        //          start value, and end present real interger end value as
        //          nums.length - 1, '-1' because we know 1 more duplicate number
        //          e.g 1,2,3,4,5,5 --> length = 6, but integer range is 1 to 5
        //              start as 1, end as 5
        int start = 1;
        int end = nums.length - 1;
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            // Find how many numbers(count) no large(<=) than 'mid' in given array,
            // if count_mid <= mid --> duplicate number happen in [mid, end]
            // if count_mid > mid --> duplicate number happen in [start, mid]
            // e.g 1,2,2,3,4 -> mid = 2, count = 3, count > mid, 
            //     duplicate happen in first half, change end to mid
            //     1,2,3,3,4 -> mid = 2, count = 2, count = mid,
            //     first recognize duplicate happen in first half, change end to mid
            //               -> mid = 3, count = 4, count > mid,
            //     second recognize duplicate happen in first half, change end to mid
            int count = count_no_large_than(nums, mid);
            if(count <= mid) {
                start = mid;
            } else if(count > mid) {
                end = mid;
            }
        }
        // Still use 1,2,3,3,4 as example, finally narrow to start = 2, end = 3
        // Still following the same rule to judge the final two values: start & end
        // if count_mid <= mid --> duplicate number happen in [mid, end]
        // here, we have count_start <= start --> duplicate happen in [end]
        // if count_mid > mid --> duplicate number happen in [start, mid]
        // here, we have count_start > start --> duplicate happen in [start]
        if(count_no_large_than(nums, start) <= start) {
            return end;
        } else {
            return start;
        }
    }
    
    private int count_no_large_than(int[] nums, int mid) {
        int count = 0;
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] <= mid) {
                count++;
            }
        }
        return count;
    }
    
    
    
    public static void main(String[] args) {
    	int[] nums = {1, 2, 3, 3, 4};
    	FindTheDuplicateNumber f = new FindTheDuplicateNumber();
//    	int result = f.findDuplicate(nums);
    	int result = f.findDuplicate3(nums);
    	System.out.println(result);
    }
}


























































































https://leetcode.com/problems/find-the-duplicate-number/

Given an array of integers nums containing n + 1 integers where each integer is in the range [1, n] inclusive.

There is only one repeated number in nums, return this repeated number.

You must solve the problem without modifying the array nums and uses only constant extra space.

Example 1:
```
Input: nums = [1,3,4,2,2]
Output: 2
```

Example 2:
```
Input: nums = [3,1,3,4,2]
Output: 3
```

Constraints:
- 1 <= n <= 105
- nums.length == n + 1
- 1 <= nums[i] <= n
- All the integers in nums appear only once except for precisely one integer which appears two or more times.

Follow up:
- How can we prove that at least one duplicate number must exist in nums?
- Can you solve the problem in linear runtime complexity?
---
Attempt 1: 2023-09-13

Solution 1: Efficient way to flip original element to negative (30 min)
```
class Solution {
    public int findDuplicate(int[] nums) {
        int n = nums.length;
        // e.g nums = [1,3,4,2,2]
        // i = 0 -> nums = [-1,3,4,2,2]
        // i = 1 -> nums = [-1,3,-4,2,2]
        // i = 2 -> nums = [-1,3,-4,-2,2]
        // i = 3 -> nums = [-1,-3,-4,-2,2]
        // i = 4 -> nums = [-1,3,-4,-2,2]
        // -3 -> turn back to positive 3 again now when i = 4
        // the root cause is nums[i] = nums[4] = 2, which means
        // we flip on index = 2 element once again, the "once
        // again" exactly means a duplicate element at current
        // index as i happen and able to point to index = 2,
        // we found the repeat element as nums[i]
        for(int i = 0; i < n; i++) {
            int index = Math.abs(nums[i]) - 1;
            nums[index] = -nums[index];
            if(nums[index] > 0) {
                return Math.abs(nums[i]);
            }
        }
        return 0;
    }
}

Time Complexity: O(N)
Space Complexity: O(1)
```

Refer to
https://leetcode.com/problems/find-the-duplicate-number/solutions/560089/c-4-different-solutions-beats-100-detailed-explanantion/
```
/*
    https://leetcode.com/problems/find-the-duplicate-number/
*/
class Solution {
public:
    // TC: O(N), SC: O(1)
    /*
        Since the numbers are [1:N], so we use the array indices for storing the
        visited state of each number, only the duplicate will be visited twice.
        For each number we goto its index position and multiply by -1. In case
        of duplicate it will be multiplied twice and the number will be +ve.
    */
    int indexSolution(vector<int>& nums) {
        for(int i = 0; i < nums.size(); i++) {
            int index = abs(nums[i]) - 1;
            
            // mark as visited
            nums[index] *= -1;
            // incase of duplicate, this will become +ve 
            if(nums[index] > 0)
                return abs(nums[i]);
        }    
        return -1;
    }
    
    int findDuplicate(vector<int>& nums) {
        // return sortingSolution(nums);
        // return binarySearchSolution(nums);
        //return floydSolution(nums);
        return indexSolution(nums);
    }
};
```

---
Solution 2:  Two Pointers (60 min, slow + fast)
```
class Solution {
    // Assume each nums value as an address just like 
    // in linked list node addr. Then since there is one 
    // number with duplicates, that means there are 
    // multiple instances of the same address, so it is 
    // a cycle just like in linked list. Do the same 
    // thing as in linked list. we just need to return
    // the address where cycle start.
    // Same as L142.Linked List Cycle II
    // 把数组的值看成 next 指针，数组的下标看成节点的索引。
    // 因为数组中至少有两个值一样，也说明有两个节点指向同
    // 一个位置，所以一定会出现环。我们只需要返回环的起点。
    // 处理方法和L142.Linked List Cycle II一样
    public int findDuplicate(int[] nums) {
        if(nums.length == 1) {
            return -1;
        }
        // 寻找相遇点
        int slow = nums[0];
        int fast = nums[nums[0]];
        while(slow != fast) {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }
        // slow 从起点出发, fast 从相遇点出发, 一次走一步
        slow = 0;
        while(slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }
}
 
Time Complexity: O(N)
Space Complexity: O(1)
```

Refer to
https://leetcode.com/problems/find-the-duplicate-number/solutions/560089/c-4-different-solutions-beats-100-detailed-explanantion/
```
// Using Floyd Cycle detection 
// TC: O(N), SC: O(1)

/*
        Assume each nums value as an address just like in linked list node addr.
        Then since there is one number with duplicates, that means there are multiple instances
        of the same address, so it is a cycle just like in linked list. Do the same thing
        as in linked list.
    */
    int floydSolution(vector<int>& nums) {
        // Each index is taken 1 based, as if it is zero based, then
        // for nums[2] = 3, if we goto nums[nums[2] - 1], it is again nums[2]
        // infinite loop
        int hare = nums[0], tortoise = nums[0];
        
        do {
            hare = nums[nums[hare]];
            tortoise = nums[tortoise];
        } while(hare != tortoise);
        
        // to find the starting of cycle, make tortoise to start from begining
        tortoise = nums[0];
        while(hare != tortoise) {
            hare = nums[hare];
            tortoise = nums[tortoise];
        }
        
        return hare;
    }
```

Refer to
https://leetcode.com/problems/find-the-duplicate-number/solutions/72846/my-easy-understood-solution-with-o-n-time-and-o-1-space-without-modifying-the-array-with-clear-explanation/
The main idea is the same with problem Linked List Cycle II,https://leetcode.com/problems/linked-list-cycle-ii/. Use two pointers the fast and the slow. The fast one goes forward two steps each time, while the slow one goes only step each time. They must meet the same item when slow==fast. In fact, they meet in a circle, the duplicate number must be the entry point of the circle when visiting the array from nums[0]. Next we just need to find the entry point. We use a point(we can use the fast one before) to visit form begining with one step each time, do the same job to slow. When fast==slow, they meet at the entry point of the circle. The easy understood code is as follows.
```
int findDuplicate3(vector<int>& nums)
{
	if (nums.size() > 1)
	{
		int slow = nums[0];
		int fast = nums[nums[0]];
		while (slow != fast)
		{
			slow = nums[slow];
			fast = nums[nums[fast]];
		}
		fast = 0;
		while (fast != slow)
		{
			fast = nums[fast];
			slow = nums[slow];
		}
		return slow;
	}
	return -1;
}
```

Refer to
https://leetcode.wang/leetcode-287-Find-the-Duplicate-Number.html

解法五

参考 这里-time-and-O(1)-space-without-modifying-the-array.-With-clear-explanation.) ，一个神奇的解法了。

把数组的值看成 next 指针，数组的下标看成节点的索引。因为数组中至少有两个值一样，也说明有两个节点指向同一个位置，所以一定会出现环。

举个例子，3 1 3 4 2 可以看成下图的样子。



```
nums[0] = 3
nums[3] = 4
nums[4] = 2
nums[2] = 3
```
所以我们要做的就是找到上图中有环链表的入口点 3，也就是 142 题 。

具体证明不说了，只介绍方法，感兴趣的话可以到 142 题 看一下。

我们需要快慢指针，同时从起点出发，慢指针一次走一步，快指针一次走两步，然后记录快慢指针相遇的点。

之后再用两个指针，一个指针从起点出发，一个指针从相遇点出发，当他们再次相遇的时候就是入口点了。
```
public int findDuplicate(int[] nums) {
    int slow = nums[0];
    int fast = nums[nums[0]];
    //寻找相遇点
    while (slow != fast) {
        slow = nums[slow];
        fast = nums[nums[fast]];
    }
    //slow 从起点出发, fast 从相遇点出发, 一次走一步
    slow = 0;
    while (slow != fast) {
        slow = nums[slow];
        fast = nums[fast];
    }
    return slow;
}
```

---
Solution 3: Binary Search (30 min)
```
class Solution {
    public int findDuplicate(int[] nums) {
        int lo = 0;
        int hi = nums.length - 1;
        while(lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int count = 0;
            for(int i = 0; i < nums.length; i++) {
                if(nums[i] <= mid) {
                    count++;
                }
            }
            if(count > mid) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }
}

Time Complexity: O(N*logN) 
Space Complexity: O(1)
```

Refer to
https://leetcode.com/problems/find-the-duplicate-number/solutions/560089/c-4-different-solutions-beats-100-detailed-explanantion/
```
 // Using Binary search
    // TC: O(nlogn), SC: O(1)
    int binarySearchSolution(vector<int>& nums) {
        // The idea is that it is known that there is a duplicate, so 
        // we can use pigeon hole concept here. We do binary search on the search
        // space of [1:N], then count the number of elements <= middle. If the 
        // duplicate is on the left side, then count should be more than mid,
        // else it is on the right side. Initially search space is [1: N], then
        // each time narrow down the search space
        int left = 1, right = nums.size() - 1;
        while(left < right) {
            int mid = left + (right - left) / 2;
            
            // count the number of elements smaller/ equal than middle element
            int c = 0;
            for(const int& el: nums)
                if(el <= mid)
                    ++c;
            
            if(c > mid)
                right = mid;
            else
                left = mid + 1;
        }
        return left;
    }
```

Refer to
https://leetcode.wang/leetcode-287-Find-the-Duplicate-Number.html

解法三 二分查找

参考 这里%3A-O(nlog(n)) 。

我们知道二分查找要求有序，但是给定的数组不是有序的，那么怎么用二分查找呢？

原数组不是有序，但是我们知道重复的那个数字肯定是 1 到 n 中的某一个，而 1,2...,n 就是一个有序序列。因此我们可以对 1,2...,n 进行二分查找。

mid = (1 + n) / 2，接下来判断最终答案是在 [1, mid] 中还是在 [mid + 1, n] 中。

我们只需要统计原数组中小于等于 mid 的个数，记为 count。

如果 count > mid ，鸽巢原理，在 [1,mid] 范围内的数字个数超过了 mid ，所以一定有一个重复数字。

否则的话，既然不在 [1,mid] ，那么最终答案一定在 [mid + 1, n] 中。
```
public int findDuplicate(int[] nums) {
    int n = nums.length - 1;
    int low = 1;
    int high = n;
    while (low < high) {
        int mid = (low + high) >>> 1;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= mid) {
                count++;
            }
        }
        if (count > mid) {
            high = mid;
        } else {
            low = mid + 1;
        }
    }
    return low;
}
```

---
Solution 4: Sorting (10 min)
```
class Solution {
    public int findDuplicate(int[] nums) {
        Arrays.sort(nums);
        for(int i = 1; i < nums.length; i++) {
            if(nums[i] == nums[i - 1]) {
                return nums[i - 1];
            }
        }
        return -1;
    }
}

Time Complexity: O(N*logN) 
Space Complexity: O(1)
```

Refer to
https://leetcode.com/problems/find-the-duplicate-number/solutions/560089/c-4-different-solutions-beats-100-detailed-explanantion/
```
// Using Sorting
    // TC: O(nlogn)
    int sortingSolution(vector<int>& nums) {
        // sort the numbers and find the number which has duplicate
        // by checking the one which has the same at next position
        sort(nums.begin(), nums.end());
        
        int duplicate = 0;
        for(int i = 0; i < nums.size() - 1; i++)
            if(nums[i] == nums[i+1]) {
                duplicate = nums[i];
                break;
            }
        return duplicate;
    }
```

---
Solution 5: Bit Manipulation (??? min)

Refer to
https://leetcode.wang/leetcode-287-Find-the-Duplicate-Number.html

解法四 二进制

参考 这里-solution-using-bit-manipulation-in-10-lines)。137 题 以及 169 题 其实已经用过这个思想，但还是不容易往这方面想。

主要就是我们要把数字放眼到二进制。

然后依次统计数组中每一位 1 的个数，记为 a[i]。再依次统计 1 到 n 中每一位 1 的个数，记为 b[i]。i 代表的是哪一位，因为是 int，所以范围是 0 到 32。

记重复的数字是 res。

如果 a[i] > b[i] 也就意味着 res 当前位是 1。

否则的话，res 当前位就是 0。

举个例子吧，1 3 4 2 2。
```
1 3 4 2 2 写成 2 进制
1 [0 0 1]
3 [0 1 1]
4 [1 0 0]
2 [0 1 0]
2 [0 1 0]

把 1 到 n,也就是 1 2 3 4 也写成 2 进制
1 [0 0 1]
2 [0 1 0]
3 [0 1 1]
4 [1 0 0]

依次统计每一列 1 的个数, res = XXX

原数组最后一列 1 的个数是 2
1 到 4 最后一列 1 的个数是 2
2 不大于 2,所以当前位是 0, res = XX0

原数组倒数第二列 1 的个数是 3
1 到 4 倒数第二列 1 的个数是 2
3 大于 2,所以当前位是 1, res = X10

原数组倒数第三列 1 的个数是 1
1 到 4 倒数第三列 1 的个数是 1
1 不大于 1,所以当前位是 0, res = 010

所以 res = 010, 也就是 2
```
上边是重复数字的重复次数是 2 的情况，如果重复次数大于 2 的话上边的结论依旧成立。

简单的想一下，1 3 4 2 2 ，因为 2 的倒数第二位的二进制位是 1，所以原数组在倒数第二列中 1 的个数会比1 到 4 这个序列倒数第二列中 1 的个数多 1 个。如果原数组其他的数变成了 2 呢？也就2 的重复次数大于 2。

如果是 1 变成了 2，数组变成 2 3 4 2 2 ， 那么倒数第二列中 1 的个数又会增加 1。

如果是 3 变成了 2，数组变成 1 2 4 2 2 ， 那么倒数第二列中 1 的个数不会变化。

所以不管怎么样，如果重复数字的某一列是 1，那么当前列 1 的个数一定会比 1 到 n 序列中 1 的个数多。
```
public int findDuplicate(int[] nums) {
    int res = 0;
    int n = nums.length; 
    //统计每一列 1 的个数
    for (int i = 0; i < 32; i++) {
        int a = 0;
        int b = 0;
        int mask = (1 << i);
        for (int j = 0; j < n; j++) {
            //统计原数组当前列 1 的个数
            if ((nums[j] & mask) > 0) {
                a++;
            }
            //统计 1 到 n 序列中当前列 1 的个数
            if ((j & mask) > 0) {
                b++;
            }
        }
        if (a > b) {
            res = res | mask;
        }
    }
    return res;
}
```
