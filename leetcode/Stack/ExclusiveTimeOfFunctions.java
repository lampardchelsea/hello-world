/**
 Refer to
 https://leetcode.com/problems/exclusive-time-of-functions/
 On a single threaded CPU, we execute some functions.  Each function has a unique id between 0 and N-1.

We store logs in timestamp order that describe when a function is entered or exited.

Each log is a string with this format: "{function_id}:{"start" | "end"}:{timestamp}".  
For example, "0:start:3" means the function with id 0 started at the beginning of timestamp 3. 
"1:end:2" means the function with id 1 ended at the end of timestamp 2.

A function's exclusive time is the number of units of time spent in this function.  
Note that this does not include any recursive calls to child functions.

The CPU is single threaded which means that only one function is being executed at a given time unit.

Return the exclusive time of each function, sorted by their function id.

Example 1:
Input:
n = 2
logs = ["0:start:0","1:start:2","1:end:5","0:end:6"]
Output: [3, 4]
Explanation:
Function 0 starts at the beginning of time 0, then it executes 2 units of time and reaches the end of time 1.
Now function 1 starts at the beginning of time 2, executes 4 units of time and ends at time 5.
Function 0 is running again at the beginning of time 6, and also ends at the end of time 6, thus executing for 1 unit of time. 
So function 0 spends 2 + 1 = 3 units of total time executing, and function 1 spends 4 units of total time executing.

Note:
1 <= n <= 100
Two functions won't start or end at the same time.
Functions will always log when they exit.
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/exclusive-time-of-functions/solution/
/**
Approach #1 Using Stack [Time Limit Exceeded]
Before starting off with the solution, let's discuss a simple idea. Suppose we have three functions func_1, func_2 and func_3, 
such that func_1 calls func_2 and then func_2 calls func_3. In this case, func_3 starts at the end and ends first, func_2 
starts at 2nd position and ends at the 2nd last step. Similarly, func_1 starts first and ends at the last position. Thus, 
we can conclude that the function which is entered at the end finishes first and the one which is entered first ends at 
the last position.

From the above discussion, we can conclude that we can make use of a stack to solve the given problem. We can start 
by pushing the first function's id from the given logslogs list onto the array. We also keep a track of the current 
time. We also make use of a res array, such that res[i] is to keep a track of the exclusive time spent by the 
Fucntion with function id i till the current time.

Now, we can move on to the next function in logslogs. The start/end time of the next function will obviously be larger 
than the start time of the function on the stack. We keep on incrementing the current timetime and the exclusive 
time for the function on the top of the stack till the current time becomes equal to the start/end time of the 
next function in the logs list.

Thus, now, we've reached a point, where the control shifts from the last function to a new function, due to a function 
call(indicated by a start label for the next function), or the last function could exit(indicated by the end label 
for the next function). Thus, we can no longer continue with the same old function.

If the next function includes a start label, we push this function on the top of the stack, since the last function 
would need to be revisited again in the future. On the other hand, if the next function includes an end label, it means 
the last function on the top of the stack is terminating.

We also know that an end label indicates that this function executes till the end of the given time. Thus, we need to 
increment the current time and the exclusive time of the last function as well to account for this fact. Now, we 
can remove(pop) this function from the stack. We can continue this process for every function in the logs list.

At the end, the resres array gives the exclusive times for each function.

Summarizing the above process, we need to do the following:
(1)Push the function id of the first function in the logs list on the stack.
(2)Keep incrementing the exlusive time(along with the current time) corresponding to the function on the top 
of the stack(in the resres array), till the current time equals the start/end time corresponding to the 
next function in the logs list.
(3)If the next function has a 'start' label, push this function's id onto the stack. Otherwise, increment the 
last function's exclusive time(along with the current time), and pop the function id from the top of the stack.
(4)Repeat steps 2 and 3 till all the functions in the logs list have been considered.

Return the resultant exlcusive time(resres).
Complexity Analysis
Time complexity : O(t). We increment the time till all the functions are done with the execution. 
Here, t refers to the end time of the last function in the logslogs list.

Space complexity : O(n). The stack can grow upto a depth of atmost n/2. Here, n refers to the number of 
elements in the given logslogs list.

Approach #2 Better Approach [Accepted]
Algorithm
In the last approach, for every function on the top of the stack, we incremented the current time and the 
exclusive time of this same function till the current time became equal to the start/end time of the next function.

Instead of doing this incrementing step by step, we can directly use the difference between the next function's 
start/stop time and the current function's start/stop time. The rest of the process remains the same as in the 
last approach.

Complexity Analysis
Time complexity : O(n). We iterate over the entire logs array just once. Here, nn refers to the number of 
elements in the logs list.

Space complexity : The stack can grow upto a depth of atmost n/2. Here, nn refers to the number of 
elements in the given logs list. 
*/
class Solution {
    public int[] exclusiveTime(int n, List<String> logs) {      
        int[] result = new int[n];
        Stack<Integer> stack = new Stack<Integer>();
        String[] s = logs.get(0).split(":");
        stack.push(Integer.parseInt(s[0]));
        int prev = Integer.parseInt(s[2]);
        for(int i = 1; i < logs.size(); i++) {
            s = logs.get(i).split(":");
            if(s[1].equals("start")) {
                if(!stack.isEmpty()) {
                    result[stack.peek()] += Integer.parseInt(s[2]) - prev;
                }
                stack.push(Integer.parseInt(s[0]));
                prev = Integer.parseInt(s[2]);
            } else {
                result[stack.peek()] += Integer.parseInt(s[2]) - prev + 1;
                stack.pop();
                prev = Integer.parseInt(s[2]) + 1;
            }
        }
        return result;
    }
}
