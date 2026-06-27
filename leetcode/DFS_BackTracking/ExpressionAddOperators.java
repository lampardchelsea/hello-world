https://leetcode.com/problems/expression-add-operators/description/
Given a string num that contains only digits and an integer target, return all possibilities to insert the binary operators '+', '-', and/or '*' between the digits of num so that the resultant expression evaluates to the target value.
Note that operands in the returned expressions should not contain leading zeros.

Example 1:
Input: num = "123", target = 6
Output: ["1*2*3","1+2+3"]
Explanation: Both "1*2*3" and "1+2+3" evaluate to 6.

Example 2:
Input: num = "232", target = 8
Output: ["2*3+2","2+3*2"]
Explanation: Both "2*3+2" and "2+3*2" evaluate to 8.

Example 3:
Input: num = "3456237490", target = 9191
Output: []
Explanation: There are no expressions that can be created from "3456237490" to evaluate to 9191.

Constraints:
- 1 <= num.length <= 10
- num consists of only digits.
- -2^31 <= target <= 2^31 - 1
--------------------------------------------------------------------------------
Attempt 1: 2024-07-05
Solution 1: Backtracking (120 min)
class Solution {
    public List<String> addOperators(String num, int target) {
        List<String> result = new ArrayList<>();
        helper(num, target, 0, 0, 0, new StringBuilder(), result);
        return result;
    }

    private void helper(String num, int target, int index, long preOperand, long curTotal, StringBuilder sb, List<String> result) {
        if(index == num.length() && curTotal == target) {
            result.add(sb.toString());
            return;
        }
        for(int i = index; i < num.length(); i++) {
            // Skip numbers with leading zeros (unless the number itself is zero)
            /**
             * Refer to
             * https://discuss.leetcode.com/topic/24523/java-standard-backtrace-ac-solutoin-short-and-clear/63?page=4
             * explain this condition:
                if(i != pos && num.charAt(pos) == '0') break;
                I think this condition it to exclude numbers with leading zeros
                Valid answers are:
                "105", 5 -> ["1*0+5","10-5"]
                "00", 0 -> ["0+0", "0-0", "0*0"]
                but with leading zeros we will have
                "105", 5 -> ["1*0+5","10-5", "1*05"]
                "00", 0 -> ["0+0", "0-0", "0*0", "00"]
            */
            // Refer to
            // https://leetcode.com/problems/expression-add-operators/discuss/71895/Java-Standard-Backtrace-AC-Solutoin-short-and-clear/239225
            // corner case: if current position is 0, we can only use it as a single digit number, should be 0
            // if it is not a single digit number with leading 0, it should be considered as an invalid number 
            if(i != index && num.charAt(index) == '0') {
                break;
            }
            // Reserve current StringBuilder length, prepare for backtracking
            /**
             * Refer to
             * https://discuss.leetcode.com/topic/32068/java-simple-solution-beats-96-56
             * What's different is, I use backtracking with StringBuilder instead of directly String addition.
             * This increase speed by 20%.
            */
            int len = sb.length();
            // Parse the current number substring
            long curOperand = Long.parseLong(num.substring(index, i + 1));
            // If this is the first operand (no operator before it)
            if(index == 0) {
                helper(num, target, i + 1, curOperand, curOperand, sb.append(curOperand), result);
                sb.setLength(len);
            } else {
                // Try adding the '+' operator
                helper(num, target, i + 1, curOperand, curTotal + curOperand, sb.append("+").append(curOperand), result);
                sb.setLength(len);
                // Try adding the '-' operator
                helper(num, target, i + 1, -curOperand, curTotal - curOperand, sb.append("-").append(curOperand), result);
                sb.setLength(len);
                // Try adding the '*' operator
                /**
                 * Refer to
                 * https://discuss.leetcode.com/topic/24523/java-standard-backtrace-ac-solutoin-short-and-clear/12?page=1
                 * for example, if you have a sequence of 12345 and you have proceeded to 1 + 2 + 3, 
                   now your eval is 6 right? If you want to add a * between 3 and 4, you would take 3 
                   as the digit to be multiplied, so you want to take it out from the existing eval. 
                   You have 1 + 2 + 3 * 4 and the eval now is (1 + 2 + 3) - 3 + (3 * 4). Hope this could help : )
                */
                helper(num, target, i + 1, preOperand * curOperand, curTotal - preOperand + preOperand * curOperand, sb.append("*").append(curOperand), result);
                sb.setLength(len);              
            }
        }
    }
}

Time Complexity: O(3^N)
Space Complexity: O(N)

Refer to Deepseek
以下是 LeetCode 282 "Expression Add Operators" 的 Java 解法，使用深度优先搜索（DFS）+ 回溯，支持加、减、乘三种运算符，并处理前导零和整数溢出问题。
import java.util.*;

class Solution {
    private List<String> result = new ArrayList<>();
    private String num;
    private int target;

    public List<String> addOperators(String num, int target) {
        this.num = num;
        this.target = target;
        dfs(0, 0, 0, new StringBuilder());
        return result;
    }

    /**
     * 深度优先搜索
     * @param index     当前处理到的字符位置
     * @param eval      当前表达式的计算结果
     * @param prev      上一个操作数（用于乘法回退）
     * @param path      当前构建的表达式字符串
     */
    private void dfs(int index, long eval, long prev, StringBuilder path) {
        if (index == num.length()) {
            if (eval == target) {
                result.add(path.toString());
            }
            return;
        }

        // 枚举当前数字的所有可能长度（从当前位置开始）
        for (int i = index; i < num.length(); i++) {
            // 处理前导零：如果当前数字长度 > 1 且第一位是 '0'，则跳过
            // 例如 "05" 是非法的，但 "0" 是合法的
            if (i > index && num.charAt(index) == '0') break;

            // 获取当前数字（从 index 到 i 的子串）
            long curNum = Long.parseLong(num.substring(index, i + 1));
            int curLen = path.length(); // 记录当前 path 长度，用于回溯

            // 第一个数字（表达式开头），不需要加运算符
            if (index == 0) {
                path.append(curNum);
                dfs(i + 1, curNum, curNum, path);
                path.setLength(curLen); // 回溯
            } else {
                // 尝试加 '+'
                path.append('+').append(curNum);
                dfs(i + 1, eval + curNum, curNum, path);
                path.setLength(curLen);

                // 尝试减 '-'
                path.append('-').append(curNum);
                dfs(i + 1, eval - curNum, -curNum, path);
                path.setLength(curLen);

                // 尝试乘 '*'
                path.append('*').append(curNum);
                dfs(i + 1, eval - prev + prev * curNum, prev * curNum, path);
                path.setLength(curLen);
            }
        }
    }
}
--------------------------------------------------------------------------------
算法核心思想
1.DFS 枚举所有可能的数字分割
从当前位置 index 开始，取子串 num.substring(index, i+1) 作为一个完整的数字（长度从 1 到剩余部分），避免前导零。
2.维护三个关键变量
- eval：当前表达式的计算结果（按常规运算优先级处理）。
- prev：上一个操作数，用于处理乘法操作（因为乘法优先级高，需要回退上一次运算）。
- path：当前构造的表达式字符串。
3.处理乘法
当添加 * curNum 时，之前的上一步计算为 eval = eval - prev + prev * curNum，其中 prev 是上一个操作数，curNum 是当前数。同时将 prev 更新为 prev * curNum，用于后续可能的连续乘法。
4.前导零处理
如果当前数字长度大于 1 且首位是 '0'，则直接跳出循环（因为再往后只会产生更多前导零，如 "05" 非法），这确保了每个数字要么是单个 '0'，要么不以 '0' 开头。
5.回溯
在每次递归后，通过 path.setLength(curLen) 恢复字符串状态，复用同一个 StringBuilder。
--------------------------------------------------------------------------------
示例
输入：num = "123", target = 6
输出：["1*2*3", "1+2+3"]
输入：num = "105", target = 5
输出：["1*0+5", "10-5"]（注意 "1*05" 不会出现，因为前导零被阻止）
--------------------------------------------------------------------------------
复杂度分析
- 时间复杂度：最坏情况下为 O(4^N)，其中 N 是数字长度。每个数字之间可能有 4 种选择（不加运算符（即合并数字）、加、减、乘），但实际合并数字的情况被枚举为数字分割，整体为 O(4^N)。
- 空间复杂度：O(N)（递归深度 + 字符串构建），结果存储空间不计入。
--------------------------------------------------------------------------------
注意事项
- 使用 long 类型避免中间计算溢出。
- 在递归过程中，curNum 是从子串解析得到的，可能很大但 long 足够。
- 空字符串或长度 0 的情况已被函数入口处理（若 num 为空，直接返回空列表）。

追问：详细举例解释：
处理乘法
当添加 * curNum 时，之前的上一步计算为 eval = eval - prev + prev * curNum，其中 prev 是上一个操作数，curNum 是当前数。同时将 prev 更新为 prev * curNum，用于后续可能的连续乘法。
好的，我们来详细解释 LeetCode 282 解法中乘法处理的精髓，特别是 eval 和 prev 的含义以及为什么乘法需要特殊处理。
--------------------------------------------------------------------------------
1. 核心问题：运算符优先级
在表达式 1 + 2 * 3 中，数学上先算乘法再算加法，结果是 7。
但如果我们在递归中从左到右顺序计算，直接 eval = eval + curNum 或 eval = eval * curNum 会出错，因为乘法的优先级高于加减法。
例如，按顺序计算：
1 → eval = 1
+ 2 → eval = 1 + 2 = 3
* 3 → 如果直接 eval = eval * 3 = 9，错误！
显然，我们需要一种机制来处理乘法的优先级，即在遇到乘法时，撤销上一步的加法（或减法），把上一步的操作数取出来与当前数相乘，再加回之前的结果。
--------------------------------------------------------------------------------
2. 变量含义
在 DFS 递归中，我们维护两个关键变量：
- eval：当前已经确定的表达式值（考虑了已处理部分的优先级）。
- prev：上一个操作数（即当前表达式最后一个被加/减/乘的数）。它用于在遇到乘法时进行“回退”操作。
具体来说：
- 当上一个操作是 +num，prev = num。
- 当上一个操作是 -num，prev = -num（记录负值）。
- 当上一个操作是 *num，prev 已经被更新为 prev_old * num。
这样，eval 始终保持为“当前已计算部分的值”，而 prev 则是参与最近一次计算的操作数。
--------------------------------------------------------------------------------
3. 乘法处理公式
当我们尝试在表达式末尾添加 * curNum 时：
eval_new = eval - prev + prev * curNum
prev_new = prev * curNum
为什么这样更新？
- 旧的 eval 包含了上一次运算的结果（比如 1+2 → eval = 3，prev = 2）。
- 我们要把上一次的运算取消（eval - prev 回到上一步之前的数值，即 1），然后将 prev 与 curNum 相乘得到新的项，再加回 eval - prev 中。
- 同时，新的 prev 变为乘积 prev * curNum，以支持后续连续乘法。
--------------------------------------------------------------------------------
4. 具体例子分析
例子 1：表达式 1 + 2 * 3
步骤跟踪（假设数字逐个解析）
1.初始状态（第一个数字 1）：
- eval = 1
- prev = 1
- 表达式路径："1"
2.添加 +2：
- 操作：加法
- eval = eval + 2 = 3
- prev = 2  （因为加法的操作数是 2）
- 路径："1+2"
3.现在尝试添加 *3（乘法分支）：
- 当前 eval = 3，prev = 2
- 应用公式：
eval_new = 3 - 2 + 2 * 3 = 1 + 6 = 7
prev_new = 2 * 3 = 6
- 新状态：eval = 7，prev = 6，路径："1+2*3"
- 此时递归到末尾，eval == target，记录结果 "1+2*3"
解释：eval - prev 回退到上一次加法之前的值（3 - 2 = 1，即初始数字 1），然后加上乘法结果 2*3=6，最终得到 7。完美处理了乘法优先级。
--------------------------------------------------------------------------------
例子 2：连续乘法 1 + 2 * 3 * 4
我们跟踪连续乘法的情况。
1.添加 +2 后：
- eval = 3, prev = 2
2.添加 *3（第一次乘法）：
- eval = 3 - 2 + 2*3 = 7
- prev = 2*3 = 6
- 路径："1+2*3"
3.再添加 *4（第二次乘法）：
- 当前 eval = 7，prev = 6
- 应用公式：
eval_new = 7 - 6 + 6 * 4 = 1 + 24 = 25
prev_new = 6 * 4 = 24
- 最终结果为 1 + 2*3*4 = 25，正确！
解释：第二次乘法时，eval - prev 回退到“上一次乘法之前”的值，即 7 - 6 = 1（也就是最初的 1），然后加上 6*4=24，得到 25。连续乘法时，prev 总是累积乘积，所以每次乘法都能正确回退到最近的项。
--------------------------------------------------------------------------------
例子 3：减法和乘法混合 2 - 3 * 4
1.初始 2：eval=2, prev=2
2.添加 -3：eval = 2 - 3 = -1, prev = -3（因为减法的操作数是负的）
3.添加 *4：
- 当前 eval = -1，prev = -3
- 应用公式：
eval_new = -1 - (-3) + (-3) * 4 = -1 + 3 - 12 = 2 - 12 = -10
prev_new = -3 * 4 = -12
- 结果为 2 - 3*4 = -10，正确。
关键：prev 保留了负号，使得回退时能正确处理减法。
--------------------------------------------------------------------------------
5. 为什么 prev 要存储“上一个操作数”而不是整个表达式？
因为乘法的优先级只影响最近一个操作数，我们不需要回溯整个表达式，只需要回退最后一次加减法造成的增量，把那个操作数拿出来与当前数相乘即可。这种设计让算法在 O(1) 时间内完成乘法处理，且状态极小。
--------------------------------------------------------------------------------
6. 总结
- eval：当前已计算值（按正确优先级）。
- prev：最后一个操作数（用于乘法回退）。
- 乘法更新：
eval = eval - prev + prev * curNum;
prev = prev * curNum;
这个公式巧妙地撤销了上一步的加减法，然后把乘法结果合并进去。
通过这种方式，DFS 能够正确地枚举所有加、减、乘的组合，并得到正确的计算结果。这就是 LeetCode 282 题解中最精妙的部分。



Refer to
https://leetcode.com/problems/expression-add-operators/solutions/71895/java-standard-backtrace-ac-solutoin-short-and-clear/
https://leetcode.com/problems/expression-add-operators/solutions/71895/java-standard-backtrace-ac-solutoin-short-and-clear/comments/74405
https://leetcode.com/problems/expression-add-operators/solutions/71895/java-standard-backtrace-ac-solutoin-short-and-clear/comments/74388
https://leetcode.com/problems/expression-add-operators/solutions/71895/java-standard-backtrace-ac-solutoin-short-and-clear/comments/239225
https://leetcode.com/problems/expression-add-operators/solutions/71895/java-standard-backtrace-ac-solutoin-short-and-clear/comments/74377
https://leetcode.com/problems/expression-add-operators/solutions/71921/java-simple-solution-beats-96-56/
This problem has a lot of edge cases to be considered:
1.overflow: we use a long type once it is larger than Integer.MAX_VALUE or minimum, we get over it.
2.0 sequence: because we can't have numbers with multiple digits started with zero, we have to deal with it too.
3.a little trick is that we should save the value that is to be multiplied in the next recursion.
4.However, adding String is extremely expensive. Speed can be increased by 20% if you use StringBuilder instead. Before: 238ms (67.31%), Now: 189ms (96.56%)
class Solution {
    /**
      * Refer to
      * https://discuss.leetcode.com/topic/24523/java-standard-backtrace-ac-solutoin-short-and-clear?page=1
      * This problem has a lot of edge cases to be considered:
        overflow: we use a long type once it is larger than Integer.MAX_VALUE or minimum, we get over it.
        0 sequence: because we can't have numbers with multiple digits started with zero, we have to deal with it too.
        a little trick is that we should save the value that is to be multiplied in the next recursion.

    */
    public List<String> addOperators(String num, int target) {
        List<String> result = new ArrayList<String>();
        if(num == null || num.length() == 0) {
            return result;
        }
        StringBuilder sb = new StringBuilder();
        dfs(result, sb, num, 0, target, 0, 0);
        
        return result;
    }
    
    private void dfs(List<String> result, StringBuilder sb, String num, int pos, int target, long prev, long multi) {
        if(pos == num.length() && target == prev) {
            result.add(sb.toString());
            return;
        }
        for(int i = pos; i < num.length(); i++) {
            /**
             * Refer to
             * https://discuss.leetcode.com/topic/24523/java-standard-backtrace-ac-solutoin-short-and-clear/63?page=4
             * explain this condition:
                if(i != pos && num.charAt(pos) == '0') break;
                I think this condition it to exclude numbers with leading zeros

                Valid answers are:

                "105", 5 -> ["1*0+5","10-5"]
                "00", 0 -> ["0+0", "0-0", "0*0"]
                but with leading zeros we will have

                "105", 5 -> ["1*0+5","10-5", "1*05"]
                "00", 0 -> ["0+0", "0-0", "0*0", "00"]
            */
            // Refer to
            // https://leetcode.com/problems/expression-add-operators/discuss/71895/Java-Standard-Backtrace-AC-Solutoin-short-and-clear/239225
            // corner case: if current position is 0, we can only use it as a single digit number, should be 0
            // if it is not a single digit number with leading 0, it should be considered as an invalid number 
            if(num.charAt(pos) == '0' && i != pos) {
                break;
            }
            // This process is quite similar to 93. Restore IP Address
            long curr = Long.parseLong(num.substring(pos, i + 1));
            /**
             * Refer to
             * https://discuss.leetcode.com/topic/32068/java-simple-solution-beats-96-56
             * What's different is, I use backtracking with StringBuilder instead of directly String addition.
             * This increase speed by 20%.
            */
            int len = sb.length();
            if(pos == 0) {
                dfs(result, sb.append(curr), num, i + 1, target, curr, curr);
                sb.setLength(len);
            } else {
                dfs(result, sb.append("+").append(curr), num, i + 1, target, prev + curr, curr);
                sb.setLength(len);
                dfs(result, sb.append("-").append(curr), num, i + 1, target, prev - curr, -curr);
                sb.setLength(len);
                /**
                 * Refer to
                 * https://discuss.leetcode.com/topic/24523/java-standard-backtrace-ac-solutoin-short-and-clear/12?page=1
                 * for example, if you have a sequence of 12345 and you have proceeded to 1 + 2 + 3, 
                   now your eval is 6 right? If you want to add a * between 3 and 4, you would take 3 
                   as the digit to be multiplied, so you want to take it out from the existing eval. 
                   You have 1 + 2 + 3 * 4 and the eval now is (1 + 2 + 3) - 3 + (3 * 4). Hope this could help : )
                */
                dfs(result, sb.append("*").append(curr), num, i + 1, target, prev - multi + multi * curr, multi * curr);
                sb.setLength(len);
            }
        }
    }

Refer to
https://algo.monster/liteproblems/282
Problem Description
The task is to take a string num that is composed exclusively of digits and an integer target, then find all the different ways to insert the binary operators '+', '-', and '*' between the digits in num so that the resulting expression equals the target. For example, if num = "123" and target = 6, one way to reach the target is to insert the '+' operator as "1+2+3".
A few important points to note for this problem:
- You can insert an operator between any two digits in the string num.
- The expressions that you create should not have numbers with leading zeroes.
- The order of digits in the num string must remain the same.
The challenge is to generate all such valid expressions that evaluate to target.
Intuition
To solve the puzzle efficiently, we use a Depth First Search (DFS) approach.
1.Breaking Down the Problem: Since you have to evaluate all possible combinations of digits and operators, this naturally forms a tree structure with each node representing a decision to include a '+', '-', '*', or no operator before the current digit.
2.Handling '0's: No number in the expression can have leading zeroes. This is managed by ensuring that whenever a '0' appears as a current digit, it doesn't process further if it is the start of a new operand, thus avoiding expressions with numbers like "01" or "002".
3.Evaluating Expressions: Keep track of the current expression's value and update it as you insert new operators. This involves tracking the last operand to correctly evaluate multiplication which has higher precedence than addition and subtraction.
4.Depth First Search (DFS) Algorithm: The key to the solution is a recursive DFS function that tries all possible combinations of operators and operand lengths. At each recursive call, the current value of the expression, path (the actual expression built so far), value of the previous operand (to handle multiplication correctly), and position in the string are updated.
5.Base Case: Once the end of the string is reached, check whether the current value of the expression is equal to the target. If it is, add the path to the list of possible solutions.
6.DFS Recursion: The DFS function:
- Includes the current digit as is and moves to the next step without adding any operators if it's the first digit (to avoid leading '+', '-', or '*' characters).
- Tries adding each of the operators (+, -, *) between the current and the next digit if it's not the first digit, carefully updating the expression's value and path. It is also critical to adjust the value of the previous operand when a multiplication is encountered due to precedence.
By implementing this DFS approach, we exhaustively search through all the possible expressions and return those that meet the target value.
Solution Approach
The implementation of the solution uses a recursive function to perform a depth-first search through the possible combinations of binary operators and operands. The DFS searches through the num string, trying to add an operator or not at each step, to explore all potential valid expressions that can be formed. Here is the breakdown of the key parts of the implementation:
- Recursive Depth-First Search (DFS): A recursive function dfs is defined which facilitates the depth-first search. This recursive function takes four parameters:
- u: The current index in the string num.
- prev: The value of the last operand included in the expression (necessary for handling precedence in multiplication).
- curr: The current value of the expression as it's been evaluated so far.
- path: The current form of the expression as a string, which shows exactly how the numbers and operators are combined.
- Base Case: The base case for recursion occurs when the end of the string num is reached (i.e., u == len(num)). Here, if the current value equals the target, the current path (the expression) is added to the answer list ans.
- Loop Through Possible Operands: The function loops from the current index u to the end of the num string, incrementing i at each iteration. This loop determines the next operand by considering one digit at a time and then multiple digits as a single operand until a leading zero would be created, which is avoided.
- Skip Leading Zeros: If the current digit is '0' and it's the start of a new operand, it will break the loop to avoid operands with leading zeroes.
- Operand Conversion: The substring from the current index u up to and including i is converted to an integer named next. This integer will become the next operand for the operators to act upon.
- No Operator for First Digit: If we are at the start of the string (when u is 0), no operator is added. The DFS function is called with the next index and values are set to include the first operand.
- Adding Operators: If not at the start, three recursive calls are made, one for each operator ('+', '-', '*'):
- Addition (+): The DFS is called with the cumulative value updated by adding next.
- Subtraction (-): The DFS is called with the cumulative value updated by subtracting next.
- Multiplication (*): The DFS is called with a more complex value update, where the last operand is first removed from the cumulative value (curr - prev) and then the multiplication of prev and next is added back to it to maintain the correct order of operations.
The dfs function is initially called with starting values, including an empty path string. Finally, the list ans is returned, containing all the paths (expressions) that evaluate to the target.
By methodically and recursively exploring each combination and path, we can generate a comprehensive list of all expressions that meet the criteria. The provided solution is both elegant and efficient in its approach to solving this problem.
Example Walkthrough
Let's walk through a simple example to illustrate the solution approach. Consider num = "105" and target = 5. We want to find all valid expressions that can be created from "105" which evaluate to 5.
Now, we'll go through each step as per the DFS algorithm strategy:
1.Initialization: Start with an empty path, the curr value as 0, prev as 0, and an index u at 0.
2.First Call to DFS: Since u is at the start, no operator is added. The first digit, '1', is converted to an integer and added to prev and curr. The new path becomes "1", and recursion continues to the next digit with u incremented.
3.Second Digit - '0': At u = 1, we have to consider that '0' might be a leading zero for an operand. Since it's not the first digit, we explore adding '+', '-', and '*':
- No Operator: Skipping any operator, prev and curr remain the same, path becomes "10", and u goes to the next digit.
- Add '+': The function is called with curr as 10, prev as 10, and path as "10+".
- Add '-': The function is called with curr as 10, prev as -10, and path as "10-".
- Add '*': We don't consider multiplication here because it would lead to a result that is certainly not going to match our target of 5 (since we'd be multiplying by 0).
4.Third Digit - '5': Now, u = 2 and the possible operands are '05' which we ignore to avoid leading zero, or just '5'.
- We again try all three possibilities from the conditions met, now with the path that already includes '10':
- Path "10+5": For the '+' operator from the previous state "10+", adding 5 results in 15, which does not match the target. This path is abandoned.
- Path "10-5": For the '-' operator from the previous state "10-", subtracting 5 results in 5, which matches our target. We add "10-5" to our list of answers.
- Multiplied by '5': Multiplication would only happen if the previous operator was a '*', which wasn't a valid option in step 3, so we don't consider it here.
Since we managed to find one valid solution, "10-5" evaluates to the target of 5, which means our answer list would be ["10-5"].
By iterating through each digit in num and exploring all possible operator insertions in this manner, we systematically discover all expressions that evaluate to the given target. The DFS algorithm ensures we explore every possibility, and our logic for handling leading zeroes and operator precedence ensures all expressions are valid and correctly evaluated.
Solution Implementation
import java.util.ArrayList;
import java.util.List;

public class Solution {
    private List<String> answers;  // Holds the valid expressions
    private String number;         // The input number as a String
    private int targetValue;       // The target value for the expressions

    // Main function to find expressions that evaluate to the target value
    public List<String> addOperators(String num, int target) {
        answers = new ArrayList<>();
        number = num;
        targetValue = target;
        recursiveSearch(0, 0, 0, "");
        return answers;
    }

    // Helper function to perform depth-first search
    private void recursiveSearch(int index, long prevOperand, long currentTotal, String expression) {
        // If we've reached the end of the string, check if the currentTotal equals the target value
        if (index == number.length()) {
            if (currentTotal == targetValue) {
                answers.add(expression);
            }
            return;
        }

        // Try all possible splits of the remainder of the string
        for (int i = index; i < number.length(); i++) {
            // Skip numbers with leading zeros (unless the number itself is zero)
            if (i != index && number.charAt(index) == '0') {
                break;
            }

            // Parse the current number substring
            long nextOperand = Long.parseLong(number.substring(index, i + 1));

            // If this is the first operand (no operator before it)
            if (index == 0) {
                recursiveSearch(i + 1, nextOperand, nextOperand, expression + nextOperand);
            } else {
                // Try adding the '+' operator
                recursiveSearch(i + 1, nextOperand, currentTotal + nextOperand, expression + "+" + nextOperand);
                // Try adding the '-' operator
                recursiveSearch(i + 1, -nextOperand, currentTotal - nextOperand, expression + "-" + nextOperand);
                // Try adding the '*' operator
                recursiveSearch(i + 1, prevOperand * nextOperand, currentTotal - prevOperand + prevOperand * nextOperand, expression + "*" + nextOperand);
            }
        }
    }
}
Time and Space Complexity
The provided Python function addOperators seeks to insert arithmetic operators into a string representing a number in all possible ways such that the resulting expression evaluates to a given target value. To analyze its time and space complexity, we have to understand how the recursion is structured.
The recursion explores adding '+', '-', or '*' between every two digits in the string. For a string of length n, there are n-1 interstitial positions where operators can be placed. Since there are 3 different operators to choose from at each interstitial position, in the worst case, the number of different expressions generated is up to O(3^(n-1)). Thus, the time complexity of this recursive solution can be expressed as O(3^n) to account for this explosive growth due to branching at each character position. This is assuming the time taken to compute the next component of the expression and concatenate strings is negligible in comparison to the recursion itself.
Now, considering the space complexity, there are two aspects to consider:
1.The recursion stack depth, which in the worst case can go up to n - the depth equals the length of the string if the recursion explores adding an operator after every digit. This contributes O(n) to the space complexity.
2.The space for storing the paths (partial expressions). Since the function concatenates strings to build up expressions, the maximum length of a path could be 2n-1 (for n digits, and n-1 operators). Since it only keeps a single path in memory at any one time during the recursion, the space taken by the path does not have a multiplicative effect based on the number of function calls.
Taking into account both the recursion stack and the path length, the space complexity of the algorithm can be concluded to be O(n).
In summary:
Time Complexity: O(3^n)
Space Complexity: O(n)

Refer to
L93.Restore IP Addresses (Ref.L282,L751)
