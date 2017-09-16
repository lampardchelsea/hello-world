/**
 * Refer to
 * http://www.lintcode.com/en/problem/implement-queue-by-two-stacks/
 * As the title described, you should only use two stacks to implement a queue's actions.

    The queue should support push(element), pop() and top() where pop is pop the first(a.k.a front) element in the queue.

    Both pop and top methods should return the value of first element.

    Have you met this question in a real interview? Yes
    Example
    push(1)
    pop()     // return 1
    push(2)
    push(3)
    top()     // return 2
    pop()     // return 2
 *
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
public class MyQueue {
    private Stack<Integer> input;
    private Stack<Integer> output;
    public MyQueue() {
        // do intialization if necessary
        input = new Stack<Integer>();
        output = new Stack<Integer>();
    }

    /*
     * @param element: An integer
     * @return: nothing
     */
    public void push(int element) {
        input.push(element);
    }

    /*
     * @return: An integer
     */
    public int pop() {
        top();
        return output.pop();
    }

    /*
     * @return: An integer
     */
    public int top() {
        if(output.isEmpty()) {
            while(!input.isEmpty()) {
                output.push(input.pop());
            }
        }
        return output.peek();
    }
}
