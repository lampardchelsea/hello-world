/**
Refer to
https://leetcode.com/problems/online-stock-span/
Write a class StockSpanner which collects daily price quotes for some stock, and returns the span of that 
stock's price for the current day.

The span of the stock's price today is defined as the maximum number of consecutive days (starting from today 
and going backwards) for which the price of the stock was less than or equal to today's price.

For example, if the price of a stock over the next 7 days were [100, 80, 60, 70, 60, 75, 85], then the stock 
spans would be [1, 1, 1, 2, 1, 4, 6].

Example 1:
Input: ["StockSpanner","next","next","next","next","next","next","next"], [[],[100],[80],[60],[70],[60],[75],[85]]
Output: [null,1,1,1,2,1,4,6]
Explanation: 
First, S = StockSpanner() is initialized.  Then:
S.next(100) is called and returns 1,
S.next(80) is called and returns 1,
S.next(60) is called and returns 1,
S.next(70) is called and returns 2,
S.next(60) is called and returns 1,
S.next(75) is called and returns 4,
S.next(85) is called and returns 6.

Note that (for example) S.next(75) returned 4, because the last 4 prices
(including today's price of 75) were less than or equal to today's price.

Note:
Calls to StockSpanner.next(int price) will have 1 <= price <= 10^5.
There will be at most 10000 calls to StockSpanner.next per test case.
There will be at most 150000 calls to StockSpanner.next across all test cases.
The total time limit for this problem has been reduced by 75% for C++, and 50% for all other languages.
*/

// Solution 1: Brute Force (TLE)
class StockSpanner {
    Stack<Integer> stack;
    Queue<Integer> q;
    public StockSpanner() {
        stack = new Stack<Integer>();
        q = new LinkedList<Integer>();
    }
    
    public int next(int price) {
        while(!stack.isEmpty() && price >= stack.peek()) {
            q.offer(stack.pop());
        }
        int size = q.size() + 1;
        while(!q.isEmpty()) {
            stack.push(q.poll());
        }
        stack.push(price);
        return size;
    }
}

// Solution 2: Monotonic stack with extra counter added to sum up previous non-larger counts
// Refer to
// https://leetcode.com/problems/online-stock-span/discuss/640358/JAVA-Solution-With-visualization-and-easy-explained!
/**
 Monotonic stack changes step by step by inserting [100, 80, 60, 70, 60, 75, 85]

                                          (60,1)
                      (60,1)    (70,2)    (70,2)    (75,4)
            (80,1)    (80,1)    (80,1)    (80,1)    (80,1)    (85,6)
  (100,1)   (100,1)   (100,1)   (100,1)   (100,1)   (100,1)   (100,1)
 ---------------------------------------------------------------------
 The tricky part is:
 1. We create a monotonic stack based on price, only smaller price will add on top of stack, otherwise pop out
 2. we add 'count' after the actual price instead of using queue to fully pop out and push back,
 the counter will be calculated in accumulative way to sum up all previous smaller price's count when they pop
 out, and if no smaller price previously means current price is a new start, no need to pop out.
*/
class StockSpanner {
    Stack<int[]> stack;
    public StockSpanner() {
        stack = new Stack<int[]>();
    }

    public int next(int price) {
        int count = 0;
        while(!stack.isEmpty() && stack.peek()[0] <= price) {
            count += stack.pop()[1];
        }
        stack.push(new int[] {price, count + 1});
        return stack.peek()[1];
    }
}

/**
 * Your StockSpanner object will be instantiated and called as such:
 * StockSpanner obj = new StockSpanner();
 * int param_1 = obj.next(price);
 */
