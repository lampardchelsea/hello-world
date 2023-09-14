/**
Refer to
https://leetcode.com/problems/find-the-duplicate-number/
Given an array of integers nums containing n + 1 integers where each integer is in the range [1, n] inclusive.

There is only one duplicate number in nums, return this duplicate number.

Follow-ups:
How can we prove that at least one duplicate number must exist in nums? 
Can you solve the problem without modifying the array nums?
Can you solve the problem using only constant, O(1) extra space?
Can you solve the problem with runtime complexity less than O(n2)?

Example 1:
Input: nums = [1,3,4,2,2]
Output: 2

Example 2:
Input: nums = [3,1,3,4,2]
Output: 3

Example 3:
Input: nums = [1,1]
Output: 1

Example 4:
Input: nums = [1,1,2]
Output: 1

Constraints:
2 <= n <= 3 * 104
nums.length == n + 1
1 <= nums[i] <= n
All the integers in nums appear only once except for precisely one integer which appears two or more times.
*/

// Refer to
// https://leetcode.com/problems/find-the-duplicate-number/solution/
/**
Note
The first two approaches mentioned do not satisfy the constraints given in the prompt, but they are solutions that you might be 
likely to come up with during a technical interview. As an interviewer, I personally would not expect someone to come up with 
the cycle detection solution unless they have heard it before.

Proof
Proving that at least one duplicate must exist in nums is simple application of the pigeonhole principle. Here, each number in 
nums is a "pigeon" and each distinct number that can appear in nums is a "pigeonhole". Because there are n+1 numbers are n distinct 
possible numbers, the pigeonhole principle implies that at least one of the numbers is duplicated.
**/

/**
Approach 1: Sorting
Intuition
If the numbers are sorted, then any duplicate numbers will be adjacent in the sorted array.
Algorithm
Given the intuition, the algorithm follows fairly simply. First, we sort the array, and then we compare each element to the previous 
element. Because there is exactly one duplicated element in the array, we know that the array is of at least length 2, and we can 
return the duplicate element as soon as we find it.
class Solution {
    public int findDuplicate(int[] nums) {
        Arrays.sort(nums);
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i-1]) {
                return nums[i];
            }
        }
        return -1;
    }
}
Complexity Analysis
Time complexity : O(nlgn)
The sort invocation costs O(nlgn) time in Python and Java, so it dominates the subsequent linear scan.
Space complexity : O(1) or O(n)
Here, we sort nums in place, so the memory footprint is constant. If we cannot modify the input array, then we must allocate linear 
space for a copy of nums and sort that instead.
*/
class Solution {
    public int findDuplicate(int[] nums) {
        Arrays.sort(nums);
        for(int i = 1; i < nums.length; i++) {
            if(nums[i] == nums[i - 1]) {
                return nums[i];
            }
        }
        return -1;
    }
}

/**
Approach 2: Set
Intuition
If we store each element as we iterate over the array, we can simply check each element as we iterate over the array.
Algorithm
In order to achieve linear time complexity, we need to be able to insert elements into a data structure (and look them up) 
in constant time. A Set satisfies these constraints nicely, so we iterate over the array and insert each element into seen. 
Before inserting it, we check whether it is already there. If it is, then we found our duplicate, so we return it.
Complexity Analysis
Time complexity : O(n)
Set in both Python and Java rely on underlying hash tables, so insertion and lookup have amortized constant time complexities. 
The algorithm is therefore linear, as it consists of a for loop that performs constant work nn times.
Space complexity : O(n)
In the worst case, the duplicate element appears twice, with one of its appearances at array index n−1. In this case, 
seen will contain n−1 distinct values, and will therefore occupy O(n) space.
*/
class Solution {
    public int findDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<Integer>();
        for (int num : nums) {
            if (seen.contains(num)) {
                return num;
            }
            seen.add(num);
        }
        return -1;
    }
}

/**
Approach 3: Floyd's Tortoise and Hare (Cycle Detection)
Refer to
https://github.com/lampardchelsea/hello-world/blob/master/leetcode/TwoPointers/Document/Find%20the%20Duplicate%20Number.docx
class Solution {
  public int findDuplicate(int[] nums) {
    // Find the intersection point of the two runners.
    int tortoise = nums[0];
    int hare = nums[0];
    do {
      tortoise = nums[tortoise];
      hare = nums[nums[hare]];
    } while (tortoise != hare);

    // Find the "entrance" to the cycle.
    tortoise = nums[0];
    while (tortoise != hare) {
      tortoise = nums[tortoise];
      hare = nums[hare];
    }

    return hare;
  }
}
*/











































































































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
