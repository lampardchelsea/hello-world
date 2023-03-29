import java.util.Stack;

/**
 * Refer to
 * https://leetcode.com/problems/132-pattern/description/
 * Given a sequence of n integers a1, a2, ..., an, a 132 pattern is a subsequence ai, aj, ak such that i < j < k 
 * and ai < ak < aj. Design an algorithm that takes a list of n numbers as input and checks whether 
 * there is a 132 pattern in the list.

	Note: n will be less than 15,000.
	
	Example 1:
	Input: [1, 2, 3, 4]
	
	Output: False
	
	Explanation: There is no 132 pattern in the sequence.
	Example 2:
	Input: [3, 1, 4, 2]
	
	Output: True
	
	Explanation: There is a 132 pattern in the sequence: [1, 4, 2].
	Example 3:
	Input: [-1, 3, 2, 0]

    Output: True
    Explanation: There are three 132 patterns in the sequence: [-1, 3, 2], [-1, 3, 0] and [-1, 2, 0].
 * 
 * 
 * Solution
 * https://leetcode.com/articles/132-pattern/
 * https://discuss.leetcode.com/topic/68193/java-o-n-solution-using-stack-in-detail-explanation
 * https://discuss.leetcode.com/topic/68193/java-o-n-solution-using-stack-in-detail-explanation/5
 * https://discuss.leetcode.com/topic/68193/java-o-n-solution-using-stack-in-detail-explanation/11
 */
public class One_Three_Two_Pattern {
	/**
	 * Refer to
	 * Why we come up with Stack ?
	 * https://discuss.leetcode.com/topic/68193/java-o-n-solution-using-stack-in-detail-explanation/5
	 * You are absolutely right because intuition doesn't lead us to Stack solution for this problem.
	 * At the very beginning, I tried to use two variables, i.e. min, max, to denote the min-max intervals, 
	 * and update them as I encounter new numbers, but I soon found that only two variables cannot easily 
	 * maintain the position information (as you update min and max, the previous ones lost).
	 * So we need to keep "multiple min-max pairs" here. Then what can we use? We can use a List, which 
	 * is definitely feasible. And what is the advantage of List? That is we can randomly access any 
	 * position, but as I go along my thinking process to figure out how to maintain/merge different 
	 * intervals, I found that we only need to fetch those intervals from the List sequentially, 
	 * so List seems to be too "powerful" at this time, and Stack is a perfect LIFO container.
	 * BTW, for all Stack questions, we can always use List, but Stack makes it easier to code and maintain.
	 */
	// Solution 1: Stack
	// Refer to
	// https://discuss.leetcode.com/topic/68193/java-o-n-solution-using-stack-in-detail-explanation
	// Complexity Analysis
	// Time complexity : O(n). We traverse over the nums array of size n once to fill the min array. 
	//                         After this, we traverse over nums to find the nums[k]. During this process, we also push and 
	//                         pop the elements on the stack. But, we can note that at most n elements can be pushed and 
	//                         popped off the stack in total. Thus, the second traversal requires only O(n) time.
	// Space complexity : O(n). The stack can grow up to a maximum depth of n. Further, min array of size n is used.
	private class Pair{
        int min, max;
        public Pair(int min, int max){
            this.min = min;
            this.max = max;
        }
    }

	// A good test case for this method: 
	// Refer to
	// https://leetcode.com/articles/132-pattern/
	// int[] nums = {6,12,3,4,6,11,20};
    public boolean find132pattern(int[] nums) {
        Stack<Pair> stack = new Stack<Pair>();
        // n present the current number(consider as third one, in description is kth number)
        for(int n: nums){
        	// Keep push smaller min Pair onto stack to make sure its descending order
        	// based on min value, which means the peek Pair's min value will always
        	// globally smallest one, this will help to make sure we have largest range
            // (stored as Pair.min/Pair.max) to find possible third number when construct
        	// 132 Pattern
        	// 保持stack 中的每一段都是以其第一个值单调递减的
            if(stack.isEmpty() || n < stack.peek().min) {
            	stack.push(new Pair(n, n));
            // 此时 n 大于 stack top的最小值
            } else if(n > stack.peek().min) { 
                Pair last = stack.pop();
                // If find 132 Pattern, directly return true
                // 如果 n 同时小于 stack top的最大值，那么返回True
                if(n < last.max) {
                	return true;
                // If not find, detail check relation between current number n and previous
                // Pair stored on stack peek
                } else {
                	// Update(merge) previous stack peek Pair's smaller max value with current number n
                	// 否则的话，把stack top的最大值更新为n， 注意此时stack top的最小值，是全部stack元素的最小值
                    last.max = n;
                    // This while loop target is to find the possible existing Pair stored
                    // on stack which satisfy 132 Pattern condition as 
                    // [stack.peek().min < n < stack.peek().max], to approach this case,
                    // we recursively pop out all Pairs that satisfy n >= stack.peek().max
                    // 如果 n 比stack 栈顶元素的最大值还要大，那么说明当前的range包含于pop出来的元素的range， 所以pop栈顶
                    while(!stack.isEmpty() && n >= stack.peek().max) stack.pop();
                    // At this time, if find one Pair n < stack.peek().max (if stack not empty)
                    // it means 132 Pattern condition [stack.peek().min < n < stack.peek().max]
                    // exist, return true
                    // 此时的情况是如果栈顶最小值比n小，同时栈顶的最大值比n大
                    if(!stack.isEmpty() && stack.peek().min < n) {
                    	return true;
                    }
                    // If still not find 132 Pattern, push back the updated(merge previous stack
                    // peek Pair's smaller max value with current number n)
                    stack.push(last);
                } 
            }                
            // And for n == stack.peek().min case, we should continue
            // E.g int[] nums = {-2, 1, -2} can test this case out
//            else if(n == stack.peek().min) {
//            	continue;
//            }
        }
        return false;
    }
    
    // Solution 2: Brute force
    // Refer to
    // https://leetcode.com/articles/132-pattern/
    // The simplest solution is to consider every triplet (i, j, k)(i,j,k) and check if the 
    // corresponding numbers satisfy the 132 criteria. If any such triplet is found, we can 
    // return a True value. If no such triplet is found, we need to return a False value.
    // Complexity Analysis
    // Time complexity : O(n^3) Three loops are used to consider every possible triplet. 
    //                          Here, nn refers to the size of numsnums array.
    // Space complexity : O(1). Constant extra space is used.
    public boolean find132pattern_brute_force(int[] nums) {
        if(nums == null || nums.length == 0) {
            return false;
        }
        
        for(int i = 0; i < nums.length - 2; i++) {
            for(int j = i + 1; j < nums.length - 1; j++) {
                for(int k = j + 1; k < nums.length; k++) {
                    if(nums[k] > nums[i] && nums[k] < nums[j]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    // Solution 3: Better brute force
    // Refer to
    // https://leetcode.com/articles/132-pattern/
    // We can improve the last approach to some extent, if we make use of some observations. 
    // We can note that for a particular number nums[j] chosen as 2nd element in the 
    // 132 pattern, if we don't consider nums[k](the 3rd element) for the time being, 
    // our job is to find out the first element, nums[i](i) which is lesser than nums[j].
    // Now, assume that we have somehow found a nums[i],nums[j] pair. Our task now reduces to 
    // finding out a nums[k](k>j>i), which falls in the range (nums[i], nums[j]). Now, 
    // to maximize the likelihood of a nums[k] falling in this range, we need to increase 
    // this range as much as possible.
    // Since, we started off by fixing a nums[j], the only option in our hand is to choose a 
    // minimum value of nums[i] given a particular nums[j]. Once, this pair nums[i],nums[j]
    // , has been found out, we simply need to traverse beyond the index j to find if a nums[k] 
    // exists for this pair satisfying the 132 criteria.
    // Based on the above observations, while traversing over the nums array choosing various 
    // values of nums[j], we simultaneously keep a track of the minimum element found 
    // so far(excluding nums[j]). This minimum element always serves as the nums[i] for 
    // the current nums[j]. Thus, we only need to traverse beyond the jth index to check the 
    // nums[k]'s to determine if any of them satisfies the 132 criteria.
    public boolean find132pattern_better_brute_force(int[] nums) {
        if(nums == null || nums.length == 0) {
            return false;
        }
        int min_i = Integer.MAX_VALUE;
        for(int j = 0; j < nums.length; j++) {
        	min_i = Math.min(min_i, nums[j]);
        	for(int k = j + 1; k < nums.length; k++) {
        		if(nums[k] < nums[j] && nums[k] > min_i) {
        			return true;
        		}
        	}
        }
        return false;
    }
	
	public static void main(String[] args) {
		One_Three_Two_Pattern o = new One_Three_Two_Pattern();
		int[] nums = {6,12,3,4,6,11,20};
		boolean result = o.find132pattern(nums);
		System.out.print(result);
	}
}

























































































https://leetcode.com/problems/132-pattern/

Given an array of n integers nums, a 132 pattern is a subsequence of three integers nums[i], nums[j] and nums[k] such that i < j < k and nums[i] < nums[k] < nums[j].

Return true if there is a 132 pattern in nums, otherwise, return false.

Example 1:
```
Input: nums = [1,2,3,4]
Output: false
Explanation: There is no 132 pattern in the sequence.
```

Example 2:
```
Input: nums = [3,1,4,2]
Output: true
Explanation: There is a 132 pattern in the sequence: [1, 4, 2].
```

Example 3:
```
Input: nums = [-1,3,2,0]
Output: true
Explanation: There are three 132 patterns in the sequence: [-1, 3, 2], [-1, 3, 0] and [-1, 2, 0].
```

Constraints:
- n == nums.length
- 1 <= n <= 2 * 105
- -109 <= nums[i] <= 109
---
Attempt 1: 2023-03-27

Solution 1: Monotonic Decreasing Stack (120 min)

Style 1: Scan from left to right (too hard to come up with)
Refer to
https://leetcode.com/problems/132-pattern/solutions/906789/short-java-o-n-solution-with-detailed-explanation-sample-test-case-stack-100/comments/1139841
```
class Solution {
    public boolean find132pattern(int[] nums) {
        Deque<int[]> stack = new ArrayDeque<>();
        for(int min = nums[0], i = 1; i < nums.length; i++) {
            while(!stack.isEmpty() && nums[i] >= stack.peek()[0]) {
                stack.pop();
            }
            if(!stack.isEmpty() && nums[i] > stack.peek()[1]) {
                return true;
            }
            stack.push(new int[]{nums[i], min = Math.min(nums[i], min)});
        }
        return false;
    }
}
```

https://leetcode.com/problems/132-pattern/solutions/94077/java-on-solution-using-stack-in-detail-explanation/
```
class Solution { 
    class Pair{ 
        int min, max; 
        public Pair(int min, int max){ 
            this.min = min; 
            this.max = max; 
        } 
    }
    // Test with {6,12,3,4,6,11,20}
    public boolean find132pattern(int[] nums) { 
        Stack<Pair> stack = new Stack(); 
        for(int n : nums) { 
            // Keep push smaller min Pair onto stack to make sure its descending order 
            // based on min value, which means the peek Pair's min value will always 
            // globally smallest one, this will help to make sure we have largest range 
            // (stored as Pair.min/Pair.max) to find possible third number when construct 
            // 132 Pattern 
            // 保持stack 中的每一段都是以其第一个值单调递减的 
            if(stack.isEmpty() || n < stack.peek().min) { 
                stack.push(new Pair(n, n)); 
            // 此时 n 大于 stack top的最小值 
            } else if(n > stack.peek().min) { 
                Pair last = stack.pop(); 
                // If find 132 Pattern, directly return true 
                // 如果 n 同时小于stack top的最大值，那么返回True 
                if(n < last.max) { 
                    return true; 
                // If not find, detail check relation between current number n and previous 
                // Pair stored on stack peek 
                } else { 
                    // Update(merge) previous stack peek Pair's smaller max value with current number n 
                    // 否则的话，把stack top的最大值更新为n， 注意此时stack top的最小值，是全部stack元素的最小值 
                    last.max = n; 
                    // This while loop target is to find the possible existing Pair stored 
                    // on stack which satisfy 132 Pattern condition as  
                    // [stack.peek().min < n < stack.peek().max], to approach this case, 
                    // we recursively pop out all Pairs that satisfy n >= stack.peek().max 
                    // 如果 n 比stack栈顶元素的最大值还要大，那么说明当前的range包含于pop出来的元素的range，所以pop栈顶 
                    while(!stack.isEmpty() && n >= stack.peek().max) { 
                        stack.pop(); 
                    } 
                    // At this time, if find one Pair n < stack.peek().max (if stack not empty) 
                    // it means 132 Pattern condition [stack.peek().min < n < stack.peek().max] 
                    // exist, return true 
                    // 此时的情况是如果栈顶最小值比n小，同时栈顶的最大值比n大 
                    if(!stack.isEmpty() && stack.peek().min < n) { 
                        return true; 
                    } 
                    // If still not find 132 Pattern, push back the updated(merge previous stack 
                    // peek Pair's smaller max value with current number n) 
                    stack.push(last); 
                } 
            } 
        } 
        return false; 
    } 
}
```

---
Style 2: Scan from right to left
```
class Solution {
    public boolean find132pattern(int[] nums) {
        Stack<Integer> stack = new Stack<Integer>();
        int second = Integer.MIN_VALUE;
        for(int i = nums.length - 1; i >= 0; i--) {
            if(nums[i] < second) {
                return true;
            }
            while(!stack.isEmpty() && stack.peek() < nums[i]) {
                second = stack.pop();
            }
            stack.push(nums[i]);
        }
        return false;
    }
}
```

Refer to
https://leetcode.com/problems/132-pattern/solutions/906789/short-java-o-n-solution-with-detailed-explanation-sample-test-case-stack-100/
EXPLANATION:
Here, if we fix the peak, i.e. 3 in the 132 pattern, then we can determine if any numbers on its left and right satisfy the given pattern. We will do this with the help of a stack. Our stack implementation will take care of the 32 pattern and then we will iterate over the array to find if any number satisfies the 1 pattern. See the algorithm below for better understanding.

Algorithm:
1. Create a stack and initialize a variable second with INT_MIN value.
2. Start traversing from the end of array.
3. Check if the current number is lesser than second. If it is, then it means our 132 pattern is satisfied as the stack is taking care of the 32 pattern and the current number satisfies the entire pattern. So return true.
4. If the above is not true, update the peak value in the stack. Keep popping from the stack until stack is empty OR the top value is greater than the current value.
5. Push the current number into the stack.
6. If the loop terminates, it means that the pattern was not found in the array. So, return false.

Take the sample input as [3, 6, 4, 1, 2] and try out this algorithm using a pen & paper. You will be able to visualize the method then.
Basically, when scan from right to left till num = 4, the top of stack is containing the highest number so far, i.e. 4, and second is containing the second highest number after the highest number, i.e. 2. So, this satisfies the 32 pattern. Now, we will just keep updating second and stack top when we encounter a number which is greater than the highest number.
```
class Solution { 
    public boolean find132pattern(int[] nums) { 
        Stack<Integer> stack = new Stack(); 
        int second = Integer.MIN_VALUE; 
        for(int i = nums.length - 1; i >= 0; i--) { 
            if(nums[i] < second) { 
                return true; 
            } 
            while(!stack.isEmpty() && nums[i] > stack.peek()) { 
                second = stack.pop(); 
            } 
            stack.push(nums[i]); 
        } 
        return false; 
    } 
}
```

---
Solution 2: Brute Force (10 min, TLE)
```
class Solution {
    public boolean find132pattern(int[] nums) {
        for(int i = 0; i < nums.length; i++) {
            for(int j = i + 1; j < nums.length; j++) {
                for(int k = j + 1; k < nums.length; k++) {
                    if(nums[i] < nums[k] && nums[k] < nums[j]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
```

Refer to
https://leetcode.com/problems/132-pattern/solutions/94089/java-solutions-from-o-n-3-to-o-n-for-132-pattern-updated-with-one-pass-slution/
I. Naive O(n^3) solution
The naive O(n^3) solution is a no-brainer --- simply check every (i, j, k) combination to see if there is any 132 pattern.
```
public boolean find132pattern(int[] nums) {
    for (int i = 0; i < nums.length; i++) {
        for (int j = i + 1; j < nums.length; j++) {
            for (int k = j + 1; k < nums.length; k++) {
                if (nums[i] < nums[k] && nums[k] < nums[j]) return true;
            }
        }
    }
    return false;
}
```
And of course it will get rejected due to TLE. So let's see how we can do better.
