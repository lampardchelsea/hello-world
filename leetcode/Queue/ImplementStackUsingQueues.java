/**
 * Refer to
 * https://leetcode.com/problems/implement-stack-using-queues/description/
 * Implement the following operations of a stack using queues.

    push(x) -- Push element x onto stack.
    pop() -- Removes the element on top of the stack.
    top() -- Get the top element.
    empty() -- Return whether the stack is empty.
    Notes:
    You must use only standard operations of a queue -- which means only push to back, peek/pop from front, 
    size, and is empty operations are valid.
    Depending on your language, queue may not be supported natively. You may simulate a queue by using a 
    list or deque (double-ended queue), as long as you use only standard operations of a queue.
    You may assume that all operations are valid (for example, no pop or top operations will be called 
    on an empty stack).
 *
 * Solution
 * https://leetcode.com/problems/implement-stack-using-queues/solution/
*/

// Solution 1:
/**
 * Approach #1 (Two Queues, push - O(1), pop O(n))
   Intuition
   Stack is LIFO (last in - first out) data structure, in which elements are added and 
   removed from the same end, called top. In general stack is implemented using array 
   or linked list, but in the current article we will review a different approach for 
   implementing stack using queues. In contrast queue is FIFO (first in - first out) 
   data structure, in which elements are added only from the one side - rear and removed 
   from the other - front. In order to implement stack using queues, we need to maintain 
   two queues q1 and q2. Also we will keep top stack element in a constant memory.
   Algorithm
   Push
   The new element is always added to the rear of queue q1 and it is kept as top stack element
   Complexity Analysis
   Time complexity : O(1). Queue is implemented as linked list and add operation has O(1) time complexity.
   Space complexity : O(1)
   Pop
   We need to remove the element from the top of the stack. This is the last inserted 
   element in q1. Because queue is FIFO (first in - first out) data structure, the last 
   inserted element could be removed only after all elements, except it, have been removed. 
   For this reason we need to maintain additional queue q2, which will serve as a temporary 
   storage to enqueue the removed elements from q1. The last inserted element in q2 is kept 
   as top. Then the algorithm removes the last element in q1. We swap q1 with q2 to avoid 
   copying all elements from q2 to q1.
   We need to remove the element from the top of the stack. This is the last inserted element 
   in q1. Because queue is FIFO (first in - first out) data structure, the last inserted 
   element could be removed only after all elements, except it, have been removed. For this 
   reason we need to maintain additional queue q2, which will serve as a temporary storage 
   to enqueue the removed elements from q1. The last inserted element in q2 is kept as top. 
   Then the algorithm removes the last element in q1. We swap q1 with q2 to avoid copying 
   all elements from q2 to q1.
   Complexity Analysis
   Time complexity : O(n). The algorithm dequeues n elements from q1 and enqueues n - 1  elements 
   to q2, where n is the stack size. This gives 2n - 1 operations.
   Space complexity : O(1).
*/
class MyStack {
    Queue<Integer> q1;
    Queue<Integer> q2;
    int top;

    /** Initialize your data structure here. */
    public MyStack() {
        q1 = new LinkedList<Integer>();
        q2 = new LinkedList<Integer>();
    }
    
    /** Push element x onto stack. */
    // O(1)
    public void push(int x) {
        q1.add(x);
        top = x;
    }
    
    /** Removes the element on top of the stack and returns that element. */
    // O(n)
    public int pop() {
        while(q1.size() > 1) {
            top = q1.remove();
            q2.add(top);
        }
        int result = q1.remove();
        // We swap q1 with q2 to avoid copying all elements from q2 to q1.
        Queue<Integer> temp = q1;
        q1 = q2;
        q2 = temp;
        return result;
    }
    
    /** Get the top element. */
    public int top() {
        return top;
    }
    
    /** Returns whether the stack is empty. */
    public boolean empty() {
        return q1.size() == 0;
    }
}


// Solution 2:
/**
 * Algorithm
   Push
   The algorithm inserts each new element to queue q2 and keep it as the top element. 
   In case queue q1 is not empty (there are elements in the stack), we remove all elements 
   from q1 and add them to q2. In this way the new inserted element (top element in the stack) 
   will be always positioned at the front of q2. We swap q1 with q2 to avoid copying all 
   elements from q2 to q1.
   Complexity Analysis
   Time complexity : O(n). The algorithm removes n elements from q1 and inserts n + 1 
   elements to q2, where n is the stack size. This gives 2n + 12n+1 operations. The operations 
   add and remove in linked lists has O(1) complexity.
   Space complexity : O(1).
   Pop
   The algorithm dequeues an element from queue q1 and keeps front element of q1 as top.
   Complexity Analysis
   Time complexity : O(1)
   Space complexity : O(1)
   In both approaches empty and top operations have the same implementation.
   Empty
   Queue q1 always contains all stack elements, so the algorithm checks q1 size to 
   return if the stack is empty.
   Time complexity : O(1).
   Space complexity : O(1).
   Top
   The top element is kept in constant memory and is modified each time when we push or pop an element.
   Time complexity : O(1)
   The top element has been calculated in advance and only returned in top operation.
   Space complexity : O(1)
*/
class MyStack {
    Queue<Integer> q1;
    Queue<Integer> q2;
    int top;

    /** Initialize your data structure here. */
    public MyStack() {
        q1 = new LinkedList<Integer>();
        q2 = new LinkedList<Integer>();
    }
    
    /** Push element x onto stack. */
    public void push(int x) {
        q2.add(x);
        top = x;
        while(!q1.isEmpty()) {
            q2.add(q1.remove());
        }
        Queue<Integer> temp = q1;
        q1 = q2;
        q2 = temp;
    }
    
    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        // Since already swap q2 to q1, just return q1's first element,
        // also need to update top to next element as q1.peek()
        int result = q1.remove(); 
        if(!q1.isEmpty()) {
            top = q1.peek();
        }
        return result;
    }
    
    /** Get the top element. */
    public int top() {
        return top;
    }
    
    /** Returns whether the stack is empty. */
    public boolean empty() {
        return q1.size() == 0;
    }
}



