https://leetcode.com/problems/ipo/description/
Suppose LeetCode will start its IPO soon. In order to sell a good price of its shares to Venture Capital, LeetCode would like to work on some projects to increase its capital before the IPO. Since it has limited resources, it can only finish at most k distinct projects before the IPO. Help LeetCode design the best way to maximize its total capital after finishing at most k distinct projects.
You are given n projects where the ith project has a pure profit profits[i] and a minimum capital of capital[i] is needed to start it.
Initially, you have w capital. When you finish a project, you will obtain its pure profit and the profit will be added to your total capital.
Pick a list of at most k distinct projects from given projects to maximize your final capital, and return the final maximized capital.
The answer is guaranteed to fit in a 32-bit signed integer.

Example 1:
Input: k = 2, w = 0, profits = [1,2,3], capital = [0,1,1]
Output: 4
Explanation: Since your initial capital is 0, you can only start the project indexed 0.
After finishing it you will obtain profit 1 and your capital becomes 1.
With capital 1, you can either start the project indexed 1 or the project indexed 2.
Since you can choose at most 2 projects, you need to finish the project indexed 2 to get the maximum capital.
Therefore, output the final maximized capital, which is 0 + 1 + 3 = 4.

Example 2:
Input: k = 3, w = 0, profits = [1,2,3], capital = [0,1,2]
Output: 6
 
Constraints:
- 1 <= k <= 10^5
- 0 <= w <= 10^9
- n == profits.length
- n == capital.length
- 1 <= n <= 10^5
- 0 <= profits[i] <= 10^4
- 0 <= capital[i] <= 10^9
--------------------------------------------------------------------------------
Attempt 1: 2023-03-12
Solution 1: Priority Queue (180 min)
class Solution {
    /**
    (1) when we start the project, we need to consider if our current capital is allowed 
    to start the project, so one condition should be satisfied, curCapital >= capital[i];
    (2) when the project is completed, the profit here is already the NET PROFIT, so we 
    need to update the curCapital with just profit[i] by curCapital += profit[i]; 
    DO NOT use profit[i] - capital[i] (this was where I get stuck);
    By understanding the previous two points, we will find the justification of using 
    greedy algorithm;
    (a) we need to find all the projects that are allowed to start under our current 
    capital capability
    (b) among them, pick the one with the largest profit, so that we can maximize our 
    capital capability to allow more potential projects.
     */
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        // Create(capital, profit) pairs and put them into PriorityQueue capMinPQ. 
        // This PriorityQueue sort by capital increasingly.
        PriorityQueue<int[]> capMinPQ = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        // Create PriorityQueue proMaxPQ which sort by profit decreasingly.
        PriorityQueue<int[]> proMaxPQ = new PriorityQueue<>((a, b) -> b[1] - a[1]);
        int n = capital.length;
        for(int i = 0; i < n; i++) {
            capMinPQ.offer(new int[]{capital[i], profits[i]});
        }
        // Pick a list of at most k distinct projects 
        while(k > 0) {
            // Keep polling pairs from capMinPQ until the project out of current 
            // capital capability. Put them into PriorityQueue proMaxPQ which sort 
            // by profit decreasingly.
            while(!capMinPQ.isEmpty() && capMinPQ.peek()[0] <= w) {
                proMaxPQ.offer(capMinPQ.poll());
            }
            // If no candidate project stop
            // Test out by k = 1, w = 0, profits = {1,2,3}, captial = {1,1,2}
            if(proMaxPQ.isEmpty()) {
                break;
            }
            // Poll one from proMaxPQ, it's guaranteed to be the project with max profit 
            // and within current capital capability. Add the profit to capital w.
            w += proMaxPQ.poll()[1];
            k--;
        }
        return w;
    }
}

Time Complexity: O(N*log(N) + k*log(K))
Space Complexity: O(N + k)

Refer to
Very Simple (Greedy) Java Solution using two PriorityQueues
https://leetcode.com/problems/ipo/solutions/98210/very-simple-greedy-java-solution-using-two-priorityqueues/
The idea is each time we find a project with max profit and within current capital capability.
Algorithm:
1.Create (capital, profit) pairs and put them into PriorityQueue pqCap. This PriorityQueue sort by capital increasingly.
2.Keep polling pairs from pqCap until the project out of current capital capability. Put them into
3.PriorityQueue pqPro which sort by profit decreasingly.
4.Poll one from pqPro, it's guaranteed to be the project with max profit and within current capital capability. Add the profit to capital W.
5.Repeat step 2 and 3 till finish k steps or no suitable project (pqPro.isEmpty()).
Time Complexity: For worst case, each project will be inserted and polled from both PriorityQueues once, so the overall runtime complexity should be O(NlgN), N is number of projects.
public class Solution {
    public int findMaximizedCapital(int k, int W, int[] Profits, int[] Capital) {
        PriorityQueue<int[]> pqCap = new PriorityQueue<>((a, b) -> (a[0] - b[0]));
        PriorityQueue<int[]> pqPro = new PriorityQueue<>((a, b) -> (b[1] - a[1]));
        
        for (int i = 0; i < Profits.length; i++) {
            pqCap.add(new int[] {Capital[i], Profits[i]});
        }
        
        for (int i = 0; i < k; i++) {
            while (!pqCap.isEmpty() && pqCap.peek()[0] <= W) {
                pqPro.add(pqCap.poll());
            }
            
            if (pqPro.isEmpty()) break;
            
            W += pqPro.poll()[1];
        }
        
        return W;
    }
}
(1) when we start the project, we need to consider if our current capital is allowed to start the project, so one condition should be satisfied, curCapital >= capital[i];
(2) when the project is completed, the profit here is already the NET PROFIT, so we need to update the curCapital with just profit[i] by curCapital += profit[i]; DO NOT use profit[i] - capital[i] (this was where I get stuck);
By understanding the previous two points, we will find the justification of using greedy algorithm;
(a) we need to find all the projects that are allowed to start under our current capital capability
(b) among them, pick the one with the largest profit, so that we can maximize our capital capability to allow more potential projects.
P.S. This problem is totally different form the gas station problem...

Refer to
https://algo.monster/liteproblems/502
Problem Description
LeetCode is preparing for its Initial Public Offering (IPO) and wants to take on some projects to boost its overall capital before going public. However, there are constraints on how many projects it can undertake due to limited resources. Specifically, LeetCode can only complete at most k distinct projects. The task at hand is to choose a subset of these projects to maximize the total capital, given that each project has its own potential profit and a minimum required capital to start. Initially, the available capital is w. Once a project is completed, its profit increases the total capital. The goal is to find out the maximum capital that can be reached after completing at most k projects.
The input includes several pieces of information: the integer k representing the maximum number of projects LeetCode can complete, the initial capital w, a list of integers profits which denotes the profit from each project, and a list capital showing the minimum capital required to start each project. The problem guarantees that the final answer will be small enough to fit into a 32-bit signed integer.
Intuition
The solution to this problem involves sorting projects by their capital requirements and using a greedy approach to pick projects that can be started with the current available capital and yield the highest profit.
Firstly, we need to categorize projects based on whether they are within our current capital capabilities. We use two heaps (priority queues) to do this: h1 for projects we cannot afford yet, and h2 for profitable projects we can do right away. h1 is min-heaped on capital required, so projects requiring less capital are at the top. Heap h2 is max-heaped on profits (we use negative values to achieve this in Python's min-heap), so the most profitable projects we can afford are at the top.
The algorithm proceeds as follows:
1.Pair each project's capital and profit and push them into h1.
2.Heapify h1 to structure it according to the minimum capital required.
3.For up to k projects, do the following:
- Move projects from h1 to h2 if they can be afforded with the current capital w.
- If h2 has projects, pop the project with the maximum profit, add that profit to w.
- If h2 is empty, it means there are no more projects that can be done with the current capital, and we break the loop.
- Decrease k by 1 because a project has been completed.
By following this approach, we ensure that with each iteration, we are choosing the most profitable project that can be started with the available capital. Once k reaches zero or there are no projects left that can be afforded, the algorithm ends. The value of w at this point is the maximized capital we aimed to find.
Solution Approach
The given Python solution makes use of a min-heap and a max-heap to efficiently determine which project to pick next. The overall approach can be broken down into several algorithmic steps using two primary data structures — heaps (implemented via Python's priority queue).
Here's the step-by-step implementation:
1.Zip the profits and capital lists together so that each tuple (c, p) in the new list h1 consists of the capital required and the profit of a project. This allows us to keep each project's financials together for easy access later on.
2.Transform the list h1 into a min-heap in-place using Python's heapify function. This operation reorders the elements so that the project with the smallest capital requirement is at the root of the heap, which allows us to quickly find and extract the project with the lowest capital threshold.
3.Initialize an empty list h2 to use as a max-heap for profits, which we'll need later to keep track of the most profitable projects that we can afford within our current capital.
4.Begin a loop that will run at most k times, where k is the maximum number of projects that can be completed.
5.Within the loop, move projects from the min-heap h1 to the max-heap h2 as long as the project's capital requirement is smaller than or equal to the current capital w. We use the heappop function to extract the project with the smallest capital requirement from h1 and the heappush function to push its profit (as a negative number to facilitate max-heap behavior in Python's min-heap) into h2.
6.If h2 is not empty, it means we have at least one project that can be started given the current capital. Use heappop again, this time on h2, to extract the project with the largest profit (remembering to negate the value to transform it back from the negative value we used earlier).
7.Update the current capital w by adding the profit of the chosen project to it.
8.If h2 is empty and we can no longer afford any projects with the current capital, exit the loop as we can't do any more projects.
9.After at most k iterations or when no further projects can be done, exit the loop and return the value of w, which now represents the maximized capital.
The use of heaps allows the solution to be efficient even when dealing with a large number of projects, as projects that can't be afforded yet are quickly filtered out, and finding the most profitable of the remaining projects can be done in constant time (O(1)) due to the heap's properties. The heap insertions and deletions (heappush and heappop) operate in logarithmic time (O(log n)), ensuring that each project insertion or extraction does not slow down the algorithm significantly even for large numbers of projects.
Example Walkthrough
Let's walk through a small example to illustrate the solution approach.
Consider the following scenario where LeetCode can complete at most k = 2 projects, the initial capital is w = 0, the list of profits is profits = [1, 2, 3], and the list of minimum required capital for each project is capital = [0, 1, 1].
We will apply the described algorithm step by step:
1.Pair each project's capital and profit: h1 = [(0, 1), (1, 2), (1, 3)].
2.Heapify h1 to structure it according to the minimum capital required. After heapifying, h1 still looks the same since the elements were already in the correct order: h1 = [(0, 1), (1, 2), (1, 3)].
3.Initialize an empty list h2 to use as a max-heap for available profitable projects.
Now we begin our main loop which runs at most k times:
- For the first iteration, w = 0. We move projects from h1 to h2 as long as the project's capital requirement is smaller than or equal to w. Since the first project requires 0 capital, which is equal to our current capital, we add it to h2 as (-1, 0) (we use negative profit for max-heap behavior). Now h1 = [(1, 2), (1, 3)] and h2 = [(-1, 0)].
- Pop the most profitable project from h2, which gives us (-1, 0). This means we earn a profit of 1 (negating the popped value). Our capital is now w = 1.
- We have completed one project. Decrease k by 1, now k = 1.
- For the second iteration, w = 1. We transfer all projects which fit into our current capital w from h1 to h2. Now we can afford the remaining projects as they both require a capital of 1. Add them to h2: (-2, 1), (-3, 1) (again using negative profits). h1 is now empty and h2 is [-3, -2] after re-heapifying for max-profit.
- Pop the most profitable project from h2, which is (-3, 1). We earn a profit of 3, so now our capital is w = 4.
- We have completed the second project. Decrease k by 1, now k = 0.
- The loop ends as k is now 0.
With no more projects that can be completed due to the limit k, we have finished our iterations. The maximized capital is now w = 4, which is the value we return.
By processing the projects in this manner, LeetCode chose the most profitable projects available within their capital limits, thus maximizing their capital by the end of the process.
Java Solution
class Solution {
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        int n = capital.length; // Number of projects
      
        // Create a min-heap (priority queue) to keep track of projects based on required capital
        PriorityQueue<int[]> minCapitalHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
      
        // Populate the min-heap with project information - each item is an array where
        // the first element is the capital required and the second element is the profit.
        for (int i = 0; i < n; ++i) {
            minCapitalHeap.offer(new int[] {capital[i], profits[i]});
        }
      
        // Create a max-heap (priority queue) to keep track of profitable projects that we can afford
        PriorityQueue<Integer> maxProfitHeap = new PriorityQueue<>((a, b) -> b - a);
      
        // Iterate k times, which represents the maximum number of projects we can select
        while (k-- > 0) {
            // Move all the projects that we can afford (w >= required capital) to the max profit heap
            while (!minCapitalHeap.isEmpty() && minCapitalHeap.peek()[0] <= w) {
                maxProfitHeap.offer(minCapitalHeap.poll()[1]);
            }
            // If the max profit heap is empty, it means there are no projects we can afford, so we break
            if (maxProfitHeap.isEmpty()) {
                break;
            }
            // Otherwise, take the most profitable project from the max profit heap and add its profit to our total capital
            w += maxProfitHeap.poll();
        }
        return w; // Return the maximized capital after picking up to k projects
    }
}
Time and Space Complexity
Time Complexity
The time complexity of the code can be analyzed as follows:
Preparing the heap h1 involves pairing each element from capital with profits. This takes O(N) time where N is the number of projects.
The heapify function on h1 runs in O(N) time.
In the worst case, the loop runs k times. In each iteration, the inner while loop can push up to N elements to h2 (over all iterations), this gives us O(Nlog(N)) for all heappush operations since each heappush operation takes O(log(N)).Each heappop operation on h1 takes O(log(N)) time, resulting in O(Nlog(N)) for all heappop operations from h1.
Each heappop from h2 takes O(log(K)). Since there are at most k such operations, the total time taken by all heappop operations from h2 is O(klog(K)).Summing up all the operations, the resulting time complexity would be the sum of the heap operations across both heaps, which in big-O notation simplifies to the largest term. This would be O(Nlog(N) + k*log(K)).
Space Complexity
The space complexity can be analyzed as follows:
Two extra heaps h1 and h2 are used, of which h1 can contain up to N elements and h2 can contain up to k elements.
There are no other significant uses of space beyond the input storage.
This gives us a space complexity of O(N + k). Since heaps use space proportionate to the number of elements they contain.
