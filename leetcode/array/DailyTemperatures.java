/**
 Refer to
 https://leetcode.com/problems/daily-temperatures/
 Given a list of daily temperatures T, return a list such that, for each day in the input, 
 tells you how many days you would have to wait until a warmer temperature. If there is no 
 future day for which this is possible, put 0 instead.

For example, given the list of temperatures T = [73, 74, 75, 71, 69, 72, 76, 73], your output 
should be [1, 1, 4, 2, 1, 1, 0, 0].

Note: The length of temperatures will be in the range [1, 30000]. Each temperature will be 
an integer in the range [30, 100].
*/
// Solution 1: Native O(n^2) while loop check
// Refer to
// https://leetcode.com/problems/daily-temperatures/discuss/175128/Java-Solution-without-stack
class Solution {
    public int[] dailyTemperatures(int[] T) {
        int[] result = new int[T.length];
        for(int i = 0; i <= T.length - 2; i++) {
            int count = 1;
            while(i + count <= T.length - 1 && T[i] >= T[i + count]) {
                count++;
            }
            // Handling 2 major cases:
            // (1) If scan to the end of array (index = T.length - 1) 
            // but still not able to find the required temperature, 
            // set to 0, the additional '-1' in 'i + count - 1' to 
            // offset one more time 'count++' in while loop
            // e.g [73,74,75,71,69,72,76,73]
            // i = T.length - 2, value = 76, after while loop count
            // increase to 2, which means i + count have to minus 1
            // to build the equation between relation of T.length - 1
            // (2) Scan and find larger temperature, set to count
            if(i + count - 1 == T.length - 1) {
                result[i] = 0;
            } else {
                result[i] = count;
            }
        }
        return result;
    }
}

// Solution 2: Elegant using stack to scan array with O(n) time
// Refer to
// https://leetcode.com/problems/daily-temperatures/discuss/109832/Java-Easy-AC-Solution-with-Stack
class Solution {
    public int[] dailyTemperatures(int[] T) {
        Stack<Integer> stack = new Stack<Integer>();
        int[] result = new int[T.length];
        for(int i = 0; i < T.length; i++) {
            while(!stack.isEmpty() && T[i] > T[stack.peek()]) {
                int index = stack.pop();
                result[index] = i - index;
            }
            stack.push(i);
        }
        return result;
    }
}
