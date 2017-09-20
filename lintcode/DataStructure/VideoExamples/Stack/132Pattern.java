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
	
	public static void main(String[] args) {
		One_Three_Two_Pattern o = new One_Three_Two_Pattern();
		int[] nums = {6,12,3,4,6,11,20};
		boolean result = o.find132pattern(nums);
		System.out.print(result);
	}
}
