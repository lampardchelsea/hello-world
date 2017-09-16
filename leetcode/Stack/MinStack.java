// Refer to
// http://www.jiuzhang.com/solutions/min-stack/
// Difference between Solution 1 and Solution 2:
// Solution 1 is intuitive solution, always push value onto minStack at same time as Stack, just differ on push
// given input x or original minStack top value
// Solution 2 has optimization as no need to push value onto minStack each time when push on Stack, just push
// on minStack if given input x no larger than (<=) minStack top value
// Solution 2 saves more space than Solution 1, but not change extra O(n) space requirement

// Solution 1: Intuitive way (O(n) extra space required)
class MinStack {
    Stack<Integer> stack;
    Stack<Integer> minStack; 
    /** initialize your data structure here. */
    public MinStack() {
        stack = new Stack<Integer>();
        minStack = new Stack<Integer>(); 
    }
    
    public void push(int x) {
        // Style 1:
        if(minStack.isEmpty() || minStack.peek() >= x) {
            minStack.push(x);
        } else {
            minStack.push(minStack.peek());
        }
        // Style 2:
        // Refer to
        // http://www.jiuzhang.com/solutions/min-stack/
        // if (minStack.isEmpty()) {
        //     minStack.push(number);
        // } else {
        //     minStack.push(Math.min(number, minStack.peek()));
        // }
        stack.push(x);
    }
    
    public void pop() {
        minStack.pop();
        stack.pop();
    }
    
    public int top() {
        return stack.peek();
    }
    
    public int getMin() {
        return minStack.peek();
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */



// Solution 2: Optimizaiton to save more extra space (still O(n) extra space required)
import java.util.Stack;

/**
 * Refer to
 * https://leetcode.com/problems/min-stack/description/
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.

	push(x) -- Push element x onto stack.
	pop() -- Removes the element on top of the stack.
	top() -- Get the top element.
	getMin() -- Retrieve the minimum element in the stack.
	Example:
	MinStack minStack = new MinStack();
	minStack.push(-2);
	minStack.push(0);
	minStack.push(-3);
	minStack.getMin();   --> Returns -3.
	minStack.pop();
	minStack.top();      --> Returns 0.
	minStack.getMin();   --> Returns -2.
 * 
 * 
 * Solution
 * http://www.cnblogs.com/yuzhangcmu/p/4106783.html
 * 比较直观。用一个min stack专门存放最小值，如果有比它小 或是相等的（有多个平行的最小值都要单独存放，否则pop后会出问题），
 */
public class MinStack {
    Stack<Integer> stack;
    Stack<Integer> minStack;
        
    /** initialize your data structure here. */
    public MinStack() {
        stack = new Stack<Integer>();
        minStack = new Stack<Integer>();
    }
    
    public void push(int x) {
    	/**
    	 * Why must use >= instead of > here ?
    	 * E.g
    	 * Runtime Error Message:
			Line 30: java.util.EmptyStackException
			Last executed input:
			["MinStack","push","push","push","getMin","pop","getMin"]
			[[],[0],[1],[0],[],[],[]]
    	 */
    	//if(minStack.isEmpty() || minStack.peek() > x) {
        if(minStack.isEmpty() || minStack.peek() >= x) {
            minStack.push(x);
        }
        stack.push(x);
    }
    
    public void pop() {
    	/**
    	 * Why must use equals here ?
    	 * Java: Integer equals vs. == 
    	 * https://stackoverflow.com/questions/3637936/java-integer-equals-vs
    	 * You can't compare two Integer with a simple == they're objects so most of 
    	 * the time references won't be the same.
    	 * There is a trick, with Integer between -128 and 127, references will be 
    	 * the same as autoboxing uses Integer.valueOf() which caches small integers.
    	 * 
    	 * E.g
    	 *  Input:
			["MinStack","push","push","push","push","pop","getMin","pop","getMin","pop","getMin"]
			[[],[512],[-1024],[-1024],[512],[],[],[],[],[],[]]
			Output:
			[null,null,null,null,null,null,-1024,null,-1024,null,-1024]
			Expected:
			[null,null,null,null,null,null,-1024,null,-1024,null,512]
			
			When compare -1024 == -1024, it returns false, then minStack will not pop out
    	 */
    	//if(stack.peek() == minStack.peek()) {
        if(stack.peek().equals(minStack.peek())) {
            minStack.pop();
        }
        /** 
          stack pop() must execute after checking minStack pop(),
          otherwise the top entry of stack already change to next
          entry will cause error (in push don't have this execution
          order issue)
          E.g
          Runtime Error Message:
			Line 31: java.util.EmptyStackException
			Last executed input:
			["MinStack","push","push","push","push","pop","getMin","pop","getMin","pop","getMin"]
			[[],[512],[-1024],[-1024],[512],[],[],[],[],[],[]]
        */
        stack.pop();
    }
    
    public int top() {
        return stack.peek();
    }
    
    public int getMin() {
        return minStack.peek();
    }
    
    public static void main(String[] args) {
    	MinStack m = new MinStack();
//        // Test for == vs equals
//    	m.push(512);
//    	m.push(-1024);
//    	m.push(-1024);
//    	m.push(512);
//    	m.pop();
//    	int i1 = m.getMin();
//    	System.out.println(i1);
//    	m.pop();
//    	int i2 = m.getMin();
//    	System.out.println(i2);
//    	m.pop();
//    	int i3 = m.getMin();
//    	System.out.println(i3);
    	
    	// Test for >= vs >
    	m.push(0);
    	m.push(1);
    	m.push(0);
    	int i1 = m.getMin();
    	System.out.println(i1);
    	m.pop();
    	int i2 = m.getMin();
    	System.out.println(i2);
    }
}
