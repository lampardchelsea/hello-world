https://leetcode.com/problems/exclusive-time-of-functions/description/
On a single-threaded CPU, we execute a program containing n functions. Each function has a unique ID between 0 and n-1.
Function calls are stored in a call stack: when a function call starts, its ID is pushed onto the stack, and when a function call ends, its ID is popped off the stack. The function whose ID is at the top of the stack is the current function being executed. Each time a function starts or ends, we write a log with the ID, whether it started or ended, and the timestamp.
You are given a list logs, where logs[i] represents the ith log message formatted as a string "{function_id}:{"start" | "end"}:{timestamp}". For example, "0:start:3" means a function call with function ID 0 started at the beginning of timestamp 3, and "1:end:2" means a function call with function ID 1 ended at the end of timestamp 2. Note that a function can be called multiple times, possibly recursively.
A function's exclusive time is the sum of execution times for all function calls in the program. For example, if a function is called twice, one call executing for 2 time units and another call executing for 1 time unit, the exclusive time is 2 + 1 = 3.
Return the exclusive time of each function in an array, where the value at the ith index represents the exclusive time for the function with ID i.
 
Example 1:

Input: n = 2, logs = ["0:start:0","1:start:2","1:end:5","0:end:6"]
Output: [3,4]
Explanation:
Function 0 starts at the beginning of time 0, then it executes 2 for units of time and reaches the end of time 1.
Function 1 starts at the beginning of time 2, executes for 4 units of time, and ends at the end of time 5.
Function 0 resumes execution at the beginning of time 6 and executes for 1 unit of time.
So function 0 spends 2 + 1 = 3 units of total time executing, and function 1 spends 4 units of total time executing.

Example 2:
Input: n = 1, logs = ["0:start:0","0:start:2","0:end:5","0:start:6","0:end:6","0:end:7"]
Output: [8]
Explanation:
Function 0 starts at the beginning of time 0, executes for 2 units of time, and recursively calls itself.
Function 0 (recursive call) starts at the beginning of time 2 and executes for 4 units of time.
Function 0 (initial call) resumes execution then immediately calls itself again.
Function 0 (2nd recursive call) starts at the beginning of time 6 and executes for 1 unit of time.
Function 0 (initial call) resumes execution at the beginning of time 7 and executes for 1 unit of time.
So function 0 spends 2 + 4 + 1 + 1 = 8 units of total time executing.

Example 3:
Input: n = 2, logs = ["0:start:0","0:start:2","0:end:5","1:start:6","1:end:6","0:end:7"]
Output: [7,1]
Explanation:
Function 0 starts at the beginning of time 0, executes for 2 units of time, and recursively calls itself.
Function 0 (recursive call) starts at the beginning of time 2 and executes for 4 units of time.
Function 0 (initial call) resumes execution then immediately calls function 1.
Function 1 starts at the beginning of time 6, executes 1 unit of time, and ends at the end of time 6.
Function 0 resumes execution at the beginning of time 6 and executes for 2 units of time.
So function 0 spends 2 + 4 + 1 = 7 units of total time executing, and function 1 spends 1 unit of total time executing.
 
Constraints:
- 1 <= n <= 100
- 2 <= logs.length <= 500
- 0 <= function_id < n
- 0 <= timestamp <= 109
- No two start events will happen at the same timestamp.
- No two end events will happen at the same timestamp.
- Each function has an "end" log for each "start" log.
--------------------------------------------------------------------------------
Attempt 1: 2025-11-30
Solution 1: Stack (60 min)
class Solution {
    public int[] exclusiveTime(int n, List<String> logs) {
        Stack<Integer> stack = new Stack<>();
        int[] result = new int[n];
        int prevTime = 0;
        for(String log : logs) {
            String[] tokens = log.split(":");
            String type = tokens[1];
            int id = Integer.parseInt(tokens[0]);
            int time = Integer.parseInt(tokens[2]);
            if(type.equals("start")) {
                // If there's a function running, update its time up to this point
                if(!stack.isEmpty()) {
                    // Find the previous function running time before interrupt by
                    // current function
                    result[stack.peek()] += time - prevTime;
                }
                prevTime = time;
                stack.push(id);
            } else {
                // The current function ends, calculate its total time
                result[stack.pop()] += time - prevTime + 1;
                // Next function starts at timestamp + 1
                prevTime = time + 1;
            }
        }
        return result;
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Refer to Deepseek
Based on the search results, I found a Java solution for LeetCode 636 that uses a stack to track function execution. Here is the solution along with a step-by-step explanation to help you understand the approach.
💡 Solution Code
class Solution {
    public int[] exclusiveTime(int n, List<String> logs) {
        // Initialize the result array to store exclusive times for n functions
        int[] res = new int[n];
        // Stack to keep track of function IDs
        Stack<Integer> jobs = new Stack<Integer>();
        // Stack to keep track of the start timestamps for the corresponding functions
        Stack<Integer> times = new Stack<Integer>();
        
        for (String log : logs) {
            // Split each log entry into its components
            String[] tokens = log.split(":");
            int id = Integer.parseInt(tokens[0]);
            String op = tokens[1];
            int timestamp = Integer.parseInt(tokens[2]);
            
            if (op.equals("start")) {
                // If there is a function already running, calculate its runtime up to now
                if (!jobs.isEmpty()) {
                    int spent = timestamp - times.peek();
                    res[jobs.peek()] += spent;
                }
                // Push the new function and its start time onto the stacks
                jobs.push(id);
                times.push(timestamp);
            } else { // "end" operation
                // Calculate the time spent for the ending function
                int spent = timestamp - times.peek() + 1;
                res[id] += spent;
                // Pop the ended function and its start time from the stacks
                jobs.pop();
                times.pop();
                // If there is a function that was interrupted, update its start time
                if (!times.isEmpty()) {
                    times.pop();
                    times.push(timestamp + 1);
                }
            }
        }
        return res;
    }
}
🧠 How the Solution Works
The core of this solution involves using two stacks to simulate the function call stack of a single-threaded CPU.
- Data Structures: The solution uses two stacks: one (jobs) to track the IDs of currently executing functions, and another (times) to track their start timestamps.
- Processing Logs: It processes each log entry in sequence. For a "start" event, it pauses the currently running function (if any) by calculating its runtime up to the current timestamp, then pushes the new function onto the stacks. For an "end" event, it calculates the runtime of the current function and pops it from the stacks, then resumes the previous function by updating its start time.
Here is a breakdown of the key steps:
1.Handling "start" events:
- If another function is already running (the stack is not empty), calculate how long it has run from its start time (times.peek()) to the current timestamp. Add this time to its exclusive time in the result array.
- Push the new function's ID and its start timestamp onto their respective stacks.
2.Handling "end" events:
- Calculate the time the current function has run from its start time to the current timestamp, adding 1 because the "end" happens at the end of the timestamp, making it inclusive.
- Add this time to the function's exclusive time.
- Pop the function and its start time from the stacks.
- If a function remains in the stack (the interrupted function), update its start time in the times stack to timestamp + 1, as it will resume execution at the next time unit.
I hope this detailed explanation helps you understand the solution. If you have any further questions, feel free to ask!

Do we really need 2 stacks ? Can we only use 1 stack ?
Great question! You're absolutely right - we don't need two stacks. We can solve this with just one stack that stores function IDs, and we can keep track of the previous timestamp to calculate time intervals.
🔥 Optimized Solution with Single Stack
class Solution {
    public int[] exclusiveTime(int n, List<String> logs) {
        int[] result = new int[n];
        Stack<Integer> stack = new Stack<>(); // Stores function IDs
        int prevTime = 0; // Tracks the last timestamp we processed
        
        for (String log : logs) {
            String[] tokens = log.split(":");
            int id = Integer.parseInt(tokens[0]);
            String type = tokens[1];
            int timestamp = Integer.parseInt(tokens[2]);
            
            if (type.equals("start")) {
                // If there's a function running, update its time up to this point
                if (!stack.isEmpty()) {
                    result[stack.peek()] += timestamp - prevTime;
                }
                stack.push(id);
                prevTime = timestamp;
            } else { // "end"
                // The current function ends, calculate its total time
                result[stack.pop()] += timestamp - prevTime + 1;
                prevTime = timestamp + 1; // Next function starts at timestamp + 1
            }
        }
        
        return result;
    }
}
🧠 How the Single Stack Solution Works
The key insight is that we only need to track which function is currently running (using the stack) and the last timestamp we processed to calculate time intervals.
For "start" events:
- If another function is running, we calculate how long it ran from prevTime to current timestamp and add that time
- Push the new function onto stack
- Update prevTime to current timestamp
For "end" events:
- Calculate how long the current function ran from prevTime to timestamp (add 1 because end is inclusive)
- Pop the function from stack
- Update prevTime to timestamp + 1 (next time unit)
📊 Example Walkthrough
Let's trace through example: n = 2, logs = ["0:start:0","1:start:2","1:end:5","0:end:6"]
Time | Log          | Stack | prevTime | result[0] | result[1] | Action
-----|--------------|-------|----------|-----------|-----------|-----------
0    | 0:start:0    | [0]   | 0        | 0         | 0         | Push 0
2    | 1:start:2    | [0,1] | 2        | 2         | 0         | result[0] += (2-0) = 2
5    | 1:end:5      | [0]   | 6        | 2         | 4         | result[1] += (5-2)+1 = 4
6    | 0:end:6      | []    | 7        | 3         | 4         | result[0] += (6-6)+1 = 1
Final result: [3, 4]
⚡ Why Single Stack is Better
1.Space Efficiency: Uses only O(n) space instead of O(2n)
2.Simpler Logic: Fewer variables to track and manipulate
3.Cleaner Code: More intuitive time calculation
4.Better Performance: Fewer stack operations
The single stack approach is definitely the preferred solution for this problem! The key is maintaining that prevTime to track where we left off in the timeline.

Is below input a valid input ?
n = 3
logs = ["0:start:0","1:start:2","2:start:5","1:end:6","2:end:6","0:end:7"
Let me analyze this input carefully:
Input:
n = 3
logs = ["0:start:0","1:start:2","2:start:5","1:end:6","2:end:7","0:end:8"]
🔍 Analysis
NO, this input is NOT valid. Here's why:
❌ Problem: Incorrect End Order (Same fundamental issue)
Let's trace the execution step by step:
1."0:start:0" - Function 0 starts
- Stack: [0]
2."1:start:2" - Function 1 starts (nested within function 0)
- Stack: [0, 1]
3."2:start:5" - Function 2 starts (nested within function 1)
- Stack: [0, 1, 2]
4."1:end:6" - INVALID! Function 1 ends, but function 2 is still running!
- Stack: [0, 1, 2] → trying to pop function 1, but function 2 is on top
🚫 Violation: Broken LIFO Principle
The fundamental issue remains the same - functions must end in the reverse order of how they started:
- Start order: 0 → 1 → 2
- Valid end order must be: 2 → 1 → 0
- Your end order: 1 → 2 → 0 ❌
Even though you fixed the timestamps to be sequential (6, 7, 8 instead of all 6), the order of end operations is still wrong.
⚠️ The Critical Issue
When function 1 tries to end at time 6:
- The stack is: [0, 1, 2] (function 2 is on top)
- We can only end the topmost function (function 2), not function 1
- Ending function 1 while function 2 is active would corrupt the call stack
✅ Valid Alternatives
Option 1 (Proper nesting):
n = 3
logs = ["0:start:0","1:start:2","2:start:5","2:end:6","1:end:7","0:end:8"]
Option 2 (If you want overlapping but not nested):
n = 3
logs = ["0:start:0","0:end:2","1:start:3","1:end:5","2:start:6","2:end:8"]
🎯 Key Insight
The order of end operations is determined by the call stack, not by timestamps. Even with proper sequential timestamps, you cannot end a function that's not at the top of the stack.
So this input is invalid for the same reason as the previous one - incorrect end order violating LIFO principle!
