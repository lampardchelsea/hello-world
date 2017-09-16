/**
 * Refer to
 * https://leetcode.com/problems/implement-queue-using-stacks/description/
 * Implement the following operations of a queue using stacks.

    push(x) -- Push element x to the back of queue.
    pop() -- Removes the element from in front of queue.
    peek() -- Get the front element.
    empty() -- Return whether the queue is empty.
    Notes:
    You must use only standard operations of a stack -- which means only push to top, peek/pop from top, 
    size, and is empty operations are valid.
    Depending on your language, stack may not be supported natively. You may simulate a stack by using 
    a list or deque (double-ended queue), as long as you use only standard operations of a stack.
    You may assume that all operations are valid (for example, no pop or peek operations will be 
    called on an empty queue).

 *
 * Solution (Time Complexity O(1))
 * https://discuss.leetcode.com/topic/17974/short-o-1-amortized-c-java-ruby/2
 * I have one input stack, onto which I push the incoming elements, and one output stack, 
   from which I peek/pop. I move elements from input stack to output stack when needed, 
   i.e., when I need to peek/pop but the output stack is empty. When that happens, I move 
   all elements from input to output stack, thereby reversing the order so it's the correct 
   order for peek/pop.
   The loop in peek does the moving from input to output stack. Each element only ever gets 
   moved like that once, though, and only after we already spent time pushing it, so the 
   overall amortized cost for each operation is O(1).
*/
class MyQueue {
    Stack<Integer> input;
    Stack<Integer> output;
    
    /** Initialize your data structure here. */
    public MyQueue() {
        input = new Stack<Integer>();
        output = new Stack<Integer>();
    }
    
    /** Push element x to the back of queue. */
    public void push(int x) {
        input.push(x);
    }
    
    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        peek();
        return output.pop();
    }
    
    /** Get the front element. */
    public int peek() {
        if(output.isEmpty()) {
            while(!input.isEmpty()) {
                output.push(input.pop());
            }
        }
        return output.peek();
    }
    
    /** Returns whether the queue is empty. */
    public boolean empty() {
        return input.isEmpty() && output.isEmpty();
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */
