https://leetcode.ca/all/772.html
https://www.lintcode.com/problem/849/description
The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces .
The expression string contains only non-negative integers, +, -, *, / operators , open ( and closing parentheses ) and empty spaces . The integer division should truncate toward zero.
You may assume that the given expression is always valid. All intermediate results will be in the range of [-2147483648, 2147483647].
Some examples:
"1 + 1" = 2
" 6-4 / 2 " = 4
"2*(5+5*2)/3+(6/2+8)" = 21
"(2+6* 3+5- (3*14/7+2)*5)+3"=-12
Note: Do not use the eval built-in library function.
--------------------------------------------------------------------------------
L772的解法可以同时作用于L224, L227, 属于通用解法
Attempt 1: 2024-07-02
Solution 1: Two Stacks (720 min)
public class Solution {
    /**
     * @param s: the expression string
     * @return: the answer
     */
    public int calculate(String s) {
        Stack<Integer> nums = new Stack<>();
        Stack<Character> ops = new Stack<>();
        int n = s.length();
        for(int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if(c == ' ') {
                continue;
            }
            if(Character.isDigit(c)) {
                int num = 0;
                while(i < n && Character.isDigit(s.charAt(i))) {
                    num = num * 10 + s.charAt(i) - '0';
                    i++;
                }
                nums.push(num);
                i--;
            } else if(c == '(') {
                ops.push(c);
            } else if(c == ')') {
                while(ops.peek() != '(') {
                    int b = nums.pop();
                    int a = nums.pop();
                    nums.push(apply_ops(ops.pop(), a, b));
                }
                ops.pop(); // pop '('
            } else {
                // Handle + - * /
                // Check if previous operator not '(' and higher precedence than current operator,
                // then we have to calculate based on previous operator first.
                // e.g 2*3+4, we will see '*' as previous operator at stack peek when we encounter '+',
                // so we have to calculate based on '*' first by pop out 2 and 3 to get multiple as 6,
                // rather than calcualte '+' with 4
                while(!ops.isEmpty() && ops.peek() != '(' 
                && precedence(ops.peek()) >= precedence(c)) {
                    int b = nums.pop();
                    int a = nums.pop();
                    nums.push(apply_ops(ops.pop(), a, b));
                }
                ops.push(c);
            }
        }
        while(!ops.isEmpty()) {
            int b = nums.pop();
            int a = nums.pop();
            nums.push(apply_ops(ops.pop(), a, b));
        }
        return nums.peek();
    }

    private int precedence(char op) {
        if(op == '(' || op == ')') {
            return 0;
        } else if(op == '+' || op == '-') {
            return 1; // '+' and '-' has lower precedence as 1
        } else {
            return 2; // '*' and '/' has higher precedence as 2
        }
    }

    private int apply_ops(char op, int a, int b) {
        if(op == '+') {
            return a + b;
        } else if(op == '-') {
            return a - b;
        } else if(op == '*') {
            return a * b;
        } else {
            return a / b;
        }
    }
}

Solution 2: DFS + Stack (720 min)
class Solution {
    int index = 0;
    String s;
    
    public int calculate(String s) {
        this.s = s;
        this.index = 0;
        return dfs();
    }
    
    private int dfs() {
        Deque<Integer> stack = new ArrayDeque<>();  // ← 来自 227 的思想
        int num = 0;
        char sign = '+';
        
        while (index < s.length()) {
            char c = s.charAt(index);
            
            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            }
            
            if (c == '(') {
                index++;           // ← 来自 224 的思想 // 跳过 '('
                num = dfs();       // 递归算括号内，结果当数字
            }
            
            // 遇到运算符、右括号、或结束，处理上一个 sign
            if (c == '+' || c == '-' || c == '*' || c == '/' || c == ')' || index == s.length() - 1) {
                switch (sign) {
                    case '+' -> stack.push(num);      // ← 来自 227
                    case '-' -> stack.push(-num);
                    case '*' -> stack.push(stack.pop() * num);
                    case '/' -> stack.push(stack.pop() / num);
                }
                num = 0;
                sign = c;
            }
            
            if (c == ')') {
                index++; // 跳过 ')'
                break;   // ← 来自 224：结束当前递归层
            }
            index++;
        }
        
        // 栈求和 ← 来自 227
        int res = 0;
        for (int n : stack) res += n;
        return res;
    }
}


--------------------------------------------------------------------------------
Refer to
https://algo.monster/liteproblems/772
Problem Explanation
You are required to implement a basic calculator that evaluates simple expression strings. The expression string may contain open and closing parentheses, plus and minus signs, non-negative integers, multiplication and division operators, and empty spaces. For integer division, you should truncate towards zero. The expression is always valid and all intermediate results will be within the range of [-2147483648, 2147483647].
Here are some examples:
"1 + 1" should output: 2
" 6-4 / 2 " should output: 4
"2*(5+5*2)/3+(6/2+8)" should output: 21
"(2+6* 3+5- (3*14/7+2)*5)+3" should output: -12
We are not allowed to use the eval built-in library function for solving this problem.
Solution Approach
In this solution, a stack data structure is used to store operands and operators until they are calculated. The algorithm goes through the string expression and breaks it down into numbers and operators.
1.Create two stacks, one for numbers and the other for operators.
2.Loop through the string.
3.If the current character is a number, calculate the number and push it on the numbers stack.
4.If the current character is an operator or a parenthesis, do the following:
- If it's an opening parenthesis, push it onto the operators stack.
- If it's a closing parenthesis, perform calculations until an opening parenthesis is found and then pop the opening parenthesis from the stack.
- If it's an operator '+', '-', '*', '/', check the precedence of the operator in stack with the current operator. If the operator in the stack has higher or equal precedence, perform calculation and then push the current operator in the stack.
5.When the string is exhausted, calculate the remaining expressions in the stack.
6.The top of the numbers stack will hold the final result.
Sample Step
Let's take the example of "6-4 /2".
- Initially, both stacks are empty.
- The first character '6' is a number, so push it in the number stack. number stack: [6].
- The next character '-' is an operator, so push it in the operator stack. operator stack: ['-'].
- The next characters '4', '/', and '2' similarly end up in the stack. number stack: [6, 4, 2], operator stack: ['-', '/'].
- We have finished parsing the string, pop items out of the stack and calculate. The '/' has higher precedence than '-', so pop out 4 and 2, calculate 4/2 with integer division and get 2, then push 2 back to number stack: [6,2]. The operator stack is now ['-'].
- Continue to calculate, pop out 6 and 2 from number stack, and '-' from operator stack, calculate 6 - 2 = 4. Now both stacks are empty and we return 4.
Java Solution
class Solution {
    /**
     * @param s: the expression string
     * @return: the answer
     */
    private int precedence(char op) {
        if (op == '(' || op == ')') return 0;
        else if (op == '+' || op == '-') return 1; // '+' and '-' has lower precedence as 1
        else return 2; // '*' and '/' has higher precedence as 2
    }

    private int apply_operand(char op, int a, int b) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            default:
                return a / b;
        }
    }

    public int calculate(String s) {
        Stack<Integer> nums = new Stack<>();
        Stack<Character> ops = new Stack<>();
        
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') continue;

            if (Character.isDigit(s.charAt(i))) {
                int num = 0;
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    num = num * 10 + s.charAt(i) - '0';
                    i++;
                }
                nums.push(num);
                i--;
            }
            else if (s.charAt(i) == '(') {
                ops.push(s.charAt(i));
            }
            else if (s.charAt(i) == ')') {
                while (ops.peek() != '(') {
                    int b = nums.pop();
                    int a = nums.pop();
                    nums.push(apply_operand(ops.pop(), a, b));
                }
                // Pop out corresponding open bracket '(' for current close bracket ')'
                ops.pop();
            } else {
                // Check if previous operator not '(' and higher precedence than current operator,
                // then we have to calculate based on previous operator first.
                // e.g 2*3+4, we will see '*' as previous operator at stack peek when we encounter '+',
                // so we have to calculate based on '*' first by pop out 2 and 3 to get multiple as 6,
                // rather than calcualte '+' with 4
                while (!ops.empty() && ops.peek() != '(' 
                && this.precedence(ops.peek()) >= this.precedence(s.charAt(i))) {
                    int b = nums.pop();
                    int a = nums.pop();
                    nums.push(this.apply_operand(ops.pop(), a, b));
                }
                ops.push(s.charAt(i));
            }
        }
        
        while (!ops.empty()) {
            int b = nums.pop();
            int a = nums.pop();
            nums.push(this.apply_operand(ops.pop(), a, b));
        }

        return nums.peek();
    }
}

--------------------------------------------------------------------------------
Follow up: If input contains negative numbers, how to handle ?
Test out by input = "1-(     -2)" against above original solution 
java.util.EmptyStackException
  at line 103, java.base/java.util.Stack.peek
  at line 85, java.base/java.util.Stack.pop
  at line 63, Solution.calculate
  at line 56, __DriverSolution__.__helper__
  at line 86, __Driver__.main
We have to add negative case handling, when detect the '-' as first char of the input or the first char after '(', the handling logic will invoke as "push a 0 onto 'num' stack" to simulate the action '0 - n = -n' to get negative value
public class Solution {
    /**
     * @param s: the expression string
     * @return: the answer
     */
    public int calculate(String s) {
        Stack<Integer> nums = new Stack<>();
        Stack<Character> ops = new Stack<>();
        int n = s.length();
        for(int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if(c == ' ') {
                continue;
            }
            if(Character.isDigit(c)) {
                int num = 0;
                while(i < n && Character.isDigit(s.charAt(i))) {
                    num = num * 10 + s.charAt(i) - '0';
                    i++;
                }
                nums.push(num);
                i--;
            } else if(c == '(') {
                ops.push(c);
            } else if(c == ')') {
                while(ops.peek() != '(') {
                    int b = nums.pop();
                    int a = nums.pop();
                    nums.push(apply_ops(ops.pop(), a, b));
                }
                ops.pop();
            } else {
                // Check if previous operator not '(' and higher precedence than current operator,
                // then we have to calculate based on previous operator first.
                // e.g 2*3+4, we will see '*' as previous operator at stack peek when we encounter '+',
                // so we have to calculate based on '*' first by pop out 2 and 3 to get multiple as 6,
                // rather than calcualte '+' with 4
                while(!ops.isEmpty() && ops.peek() != '(' 
                && precedence(ops.peek()) >= precedence(c)) {
                    int b = nums.pop();
                    int a = nums.pop();
                    nums.push(apply_ops(ops.pop(), a, b));
                }
                // Negative scenario
                // Test out by input = "1-(     -2)" against above original solution
                if(c == '-') {
                    // The first num is negative
                    if(nums.isEmpty()) {
                        nums.push(0);
                    // The first num in parentheses is negative
                    } else {
                        int index = i - 1;
                        while(index >= 0 && s.charAt(index) == ' ') {
                            index--;
                        }
                        if(s.charAt(index) == '(') {
                            nums.push(0);
                        }
                    }
                }
                ops.push(c);
            }
        }
        while(!ops.isEmpty()) {
            int b = nums.pop();
            int a = nums.pop();
            nums.push(apply_ops(ops.pop(), a, b));
        }
        return nums.peek();
    }

    private int precedence(char op) {
        if(op == '(' || op == ')') {
            return 0;
        } else if(op == '+' || op == '-') {
            return 1; // '+' and '-' has lower precedence as 1
        } else {
            return 2; // '*' and '/' has higher precedence as 2
        }
    }

    private int apply_ops(char op, int a, int b) {
        if(op == '+') {
            return a + b;
        } else if(op == '-') {
            return a - b;
        } else if(op == '*') {
            return a * b;
        } else {
            return a / b;
        }
    }
}

Refer to
https://www.lintcode.com/problem/849/solution/17761
精选的答案无法处理负数情况，需要添加负数的判断。
1.第一个数字就是负数
2.括号里的第一个数字是负数
这个代码是根据lc上的讨论 添加了一个判断。
class Solution {
    public int calculate(String s) {
        if (s == null || s.length() == 0) return 0;
        Stack<Integer> nums = new Stack<>();   
        Stack<Character> ops = new Stack<>();   
        int num = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' '){
                continue;
            }
            if (Character.isDigit(c)) {            
                num = c - '0';
                while (i < s.length() - 1 && Character.isDigit(s.charAt(i + 1))) {
                    num = num * 10 + (s.charAt(i+1) - '0');
                    i++;
                }
                nums.push(num);            
                num = 0; 
            } else if (c == '(') {        
                ops.push(c);
            } else if (c == ')') {        
                while (ops.peek() != '('){        
                    nums.push(operation(ops.pop(), nums.pop(), nums.pop()));
                }
                ops.pop(); 
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {   
                while (!ops.isEmpty() && precedence(c, ops.peek())){        
                    nums.push(operation(ops.pop(), nums.pop(),nums.pop()));
                }
                if(c == '-'){//negative scenario
                    if(nums.isEmpty()){ //the first num is negative
                        nums.push(0);
                    }else{// the first num in parenthess is negative
                        int index = i - 1;
                        while(index >= 0 && s.charAt(index) == ' '){
                            index--;
                        }
                        if(s.charAt(index) == '('){
                            nums.push(0);
                        }
                    }
                }
                
                ops.push(c);
            }
        }
        while (!ops.isEmpty()) {    
            nums.push(operation(ops.pop(), nums.pop(), nums.pop()));
        }
        return nums.pop();
        
    }
    
    private boolean precedence(char ops1, char ops2){
        if(ops2 == '(' || ops2 == ')'){
            return false;
        }
        if((ops1 == '*' || ops1 == '/') && (ops2 == '+' || ops2 == '-')){
            return false;
        }
        return true;
    }
    
    private static int operation(char op, int b, int a) {
        switch (op) {
            case '+': return a + b;   //加法
            case '-': return a - b;   //减法
            case '*': return a * b;   //乘法
            case '/': return a / b;   //除法
        }
        return 0;
    }
}

Refer to Deepseek
Q1: Now can you compare both Leetcode 224 and 227 to Leetcode 772. Basic Calculator III ?
LeetCode 的这三道“基本计算器”题目，可以看作一个逐步升级的系列。它们的核心区别在于所支持的运算符和是否包含括号，这直接导致了题目难度和解法的不同。
下面是这三道题的详细对比：
特性224. 基本计算器 (Basic Calculator)227. 基本计算器 II (Basic Calculator II)772. 基本计算器 III (Basic Calculator III)难度困难 (Hard)中等 (Medium)困难 (Hard)支持运算符+、-+、-、*、/+、-、*、/支持括号✅ 支持 ( 和 )❌ 不支持✅ 支持 ( 和 )核心难点括号处理、符号翻转运算优先级（乘除优先于加减）结合前两者：括号处理 + 运算优先级核心解法栈，遇到括号时保存状态并重置栈，遇到乘除立即计算递归下降 或 双栈（运算符栈 + 数字栈）
--------------------------------------------------------------------------------
📝 详细解读
- LeetCode 227 (Basic Calculator II)：它是基础版。因为没有括号，所以难点在于如何处理 * 和 / 的高优先级。解法是使用一个栈，遇到 + 或 - 时，将数字（注意正负号）压栈；遇到 * 或 / 时，从栈顶弹出数字进行运算，再将结果压栈。最后把栈里所有数字加起来就是答案。
- LeetCode 224 (Basic Calculator I)：它只有加减法和括号。因为括号的存在，难点变成了如何处理括号嵌套以及括号前的正负号（符号翻转）。解法通常也是用栈，但栈里存的是遇到 ( 之前的结果和符号。遇到 ( 时，将当前结果和符号压栈并重置；遇到 ) 时，弹出栈顶的符号和之前的结果，与当前括号内的结果合并。
- LeetCode 772 (Basic Calculator III)：这是前两个问题的结合体。它同时拥有括号和乘除运算符，所以需要同时处理括号嵌套和运算符优先级。最自然的解法是递归下降（Recursive Descent）或双栈（运算符栈 + 数字栈）。当遇到 ( 时，递归调用自身来计算括号内的子表达式；在计算子表达式时，则使用处理 + - * / 的逻辑。
--------------------------------------------------------------------------------
💎 总结
简单来说，这就像建造一个计算器：
1.227 先实现了基础的四则运算。
2.224 实现了括号功能。
3.772 则是将两者合并，实现了一个完整的计算器。
因此，LeetCode 772 的解法通常可以看作是在 LeetCode 227 的解法上，增加了处理括号的递归或逻辑。理解了前两道题的解法，第三道题的思路也就清晰了。

Q2: Recursive Descent Parsing Solution for LeetCode 772
以下是 LeetCode 772 "Basic Calculator III" 的详细 Java 解法，使用递归下降解析（Recursive Descent Parsing），支持加、减、乘、除和括号，并自动跳过空格。
class Solution {
    private String s;
    private int idx; // 当前解析位置

    public int calculate(String s) {
        this.s = s.replaceAll(" ", ""); // 移除所有空格（也可在扫描时跳过）
        this.idx = 0;
        return parseExpression();
    }

    // 解析表达式（处理加减）
    private int parseExpression() {
        int val = parseTerm(); // 先解析第一个项
        while (idx < s.length() && (s.charAt(idx) == '+' || s.charAt(idx) == '-')) {
            char op = s.charAt(idx++);
            int nextVal = parseTerm();
            if (op == '+') {
                val += nextVal;
            } else {
                val -= nextVal;
            }
        }
        return val;
    }

    // 解析项（处理乘除）
    private int parseTerm() {
        int val = parseFactor(); // 先解析第一个因子
        while (idx < s.length() && (s.charAt(idx) == '*' || s.charAt(idx) == '/')) {
            char op = s.charAt(idx++);
            int nextVal = parseFactor();
            if (op == '*') {
                val *= nextVal;
            } else {
                val /= nextVal; // 整数除法向零截断，Java 的 / 满足
            }
        }
        return val;
    }

    // 解析因子（数字或括号表达式）
    private int parseFactor() {
        if (s.charAt(idx) == '(') {
            idx++; // 跳过 '('
            int val = parseExpression();
            idx++; // 跳过 ')'
            return val;
        } else {
            // 解析数字（可能多位）
            int num = 0;
            while (idx < s.length() && Character.isDigit(s.charAt(idx))) {
                num = num * 10 + (s.charAt(idx) - '0');
                idx++;
            }
            return num;
        }
    }
}
--------------------------------------------------------------------------------
算法思路（递归下降）
1.语法规则（根据运算符优先级定义）：
Expression = Term { (+|-) Term }
Term       = Factor { (*|/) Factor }
Factor     = Number | '(' Expression ')'
2.解析流程：
- parseExpression() 调用 parseTerm() 获取第一个项的值，然后循环处理 + 或 - 运算符，每次取下一个项进行加减运算。
- parseTerm() 调用 parseFactor() 获取第一个因子的值，然后循环处理 * 或 / 运算符，每次取下一个因子进行乘除运算。
- parseFactor() 判断当前字符：
- 如果是 '('，则递归调用 parseExpression() 计算括号内的值，然后跳过 ')'。
- 否则，解析连续的数字字符得到整数。
3.处理细节：
- 空格在构造时已全部移除，也可以不删除，在解析时跳过空格字符（推荐在循环中跳过，但为了简单这里替换了）。
- 括号嵌套自动处理，因为递归调用会返回子表达式的值。
- 整数除法向零截断：Java 中 a / b 对于整数会自动向零舍入，符合题目要求。
--------------------------------------------------------------------------------
示例演示
输入："2*(3+4)-5/2"
解析过程：
1.parseExpression() 调用 parseTerm()：
- parseTerm() 调用 parseFactor()：读取数字 2，然后遇到 *，再调用 parseFactor() 遇到 (，递归计算 3+4=7，得到 2*7=14。
2.回到 parseExpression()，遇到 -，调用 parseTerm()：
- parseTerm() 读取 5，遇到 /，读取 2，得到 5/2=2。
3.最终 14 - 2 = 12。
--------------------------------------------------------------------------------
复杂度分析
- 时间：O(n)，每个字符最多被解析一次。
- 空间：O(n)，递归深度取决于嵌套层数，最坏为 O(n)（例如 "((((...))))"）。
--------------------------------------------------------------------------------
与 LeetCode 224、227 的关系
- 224（仅加减括号）：用单栈即可，因为无乘除，无需区分项和因子。
- 227（无括号四则运算）：可用单栈处理乘除，但递归下降也可用（只需实现 parseExpression 和 parseTerm，不处理括号）。
- 772：结合了两者，递归下降是最自然、最清晰的解法，能同时处理括号和运算符优先级。
--------------------------------------------------------------------------------
注意事项
- 如果输入包含空格，可以在循环中跳过 while (idx < s.length() && s.charAt(idx) == ' ') idx++; 而不必预先替换，避免复制字符串。
- 题目保证输入合法，不会出现负数或一元负号（如 "-1+2" 可能不会出现，但若出现，可通过处理 parseFactor 中的负号来扩展，但本题通常不包含）。
此解法在 LeetCode 上可通过所有测试。

Q3: Two Stacks Solution for LeetCode 772
以下是 LeetCode 772 "Basic Calculator III" 的 双栈（运算符栈 + 数字栈） 解法，它能够正确处理括号和运算符优先级，是一种通用表达式求值方法。
import java.util.Deque;
import java.util.ArrayDeque;

class Solution {
    public int calculate(String s) {
        // 数字栈：存储操作数
        Deque<Integer> numStack = new ArrayDeque<>();
        // 运算符栈：存储 '+', '-', '*', '/', '('
        Deque<Character> opStack = new ArrayDeque<>();
        int n = s.length();
        int i = 0;

        while (i < n) {
            char c = s.charAt(i);
            if (c == ' ') {
                i++;
                continue;
            }
            if (Character.isDigit(c)) {
                // 解析完整数字（可能多位）
                int num = 0;
                while (i < n && Character.isDigit(s.charAt(i))) {
                    num = num * 10 + (s.charAt(i) - '0');
                    i++;
                }
                numStack.push(num);
                // 注意：i 已指向下一个字符，循环继续
            } else if (c == '(') {
                opStack.push(c);
                i++;
            } else if (c == ')') {
                // 计算直到遇到左括号
                while (!opStack.isEmpty() && opStack.peek() != '(') {
                    applyOp(numStack, opStack);
                }
                // 弹出左括号
                if (!opStack.isEmpty() && opStack.peek() == '(') {
                    opStack.pop();
                }
                i++;
            } else { // 运算符 '+', '-', '*', '/'
                // 当前运算符优先级低于栈顶运算符时，先计算栈顶
                while (!opStack.isEmpty() && precedence(opStack.peek()) >= precedence(c)) {
                    applyOp(numStack, opStack);
                }
                // 然后将当前运算符压栈
                opStack.push(c);
                i++;
            }
        }

        // 处理剩余运算符
        while (!opStack.isEmpty()) {
            applyOp(numStack, opStack);
        }

        // 最终数字栈中剩下的唯一元素即为结果
        return numStack.pop();
    }

    // 计算优先级
    private int precedence(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        return 0; // '(' 优先级为 0，但'('不会参与比较（只在括号弹出时处理）
    }

    // 执行一次运算：弹出右操作数、左操作数、运算符，计算结果压回数字栈
    private void applyOp(Deque<Integer> numStack, Deque<Character> opStack) {
        char op = opStack.pop();
        int b = numStack.pop(); // 右操作数
        int a = numStack.pop(); // 左操作数
        int result = 0;
        switch (op) {
            case '+': result = a + b; break;
            case '-': result = a - b; break;
            case '*': result = a * b; break;
            case '/': result = a / b; break; // 整数除法向零截断，Java 满足
        }
        numStack.push(result);
    }
}
--------------------------------------------------------------------------------
核心思路（双栈通用表达式求值）
1.数字栈 (numStack)：存储所有操作数（包括中间计算结果）。
2.运算符栈 (opStack)：存储运算符 +、-、*、/ 和左括号 (。
处理规则
- 数字：解析完整整数（可能多位），压入数字栈。
- (：直接压入运算符栈。
- )：不断弹出运算符并计算（applyOp），直到遇到左括号，然后弹出左括号。
- 运算符：当当前运算符的优先级 ≤ 栈顶运算符优先级时，先执行栈顶运算（因为栈顶运算符优先级更高或相等，需要先计算）。然后将当前运算符压栈。
- 结束：遍历完表达式后，依次弹出运算符栈中剩余的运算符并计算，最后数字栈的栈顶即为结果。
优先级定义
- +、-：优先级 1
- *、/：优先级 2
- (：优先级 0（但不会参与比较，因为括号在遇到 ) 时才处理）
关键点
- 运算符处理中的 while 循环确保了乘除先于加减计算。
- 括号内的表达式作为一个整体，先计算完毕再与外部结合。
--------------------------------------------------------------------------------
示例演示
输入："2*(3+4)-5/2"
步骤扫描字符numStackopStack操作12[2]数字入栈2*[2][*]运算符入栈3([2][*, (]'(' 入栈43[3,2][*, (]数字入栈5+[3,2][*, (, +]'+' 入栈（栈顶 '(' 优先级低，不计算）64[4,3,2][*, (, +]数字入栈7)--遇到 ')'，弹出 '+' 计算 3+4=7 → numStack=[7,2]，弹出 '('8-[7,2][*]当前 '-' 优先级 1，栈顶 '' 优先级 2 > 1，先计算 ''：2*7=14，numStack=[14]，然后 '-' 入栈95[5,14][-]数字入栈10/[5,14][-, /]'/' 优先级 2，栈顶 '-' 优先级 1 < 2，不计算，直接入栈112[2,5,14][-, /]数字入栈12结束--剩余运算符：先 '/' → 5/2=2, numStack=[2,14]；再 '-' → 14-2=12，numStack=[12]
最终结果 12。
--------------------------------------------------------------------------------
复杂度分析
- 时间：O(n)，每个字符最多入栈出栈一次。
- 空间：O(n)，最坏情况下栈空间与表达式长度成正比。
--------------------------------------------------------------------------------
与递归下降对比
- 双栈：迭代实现，无需递归，适合较深嵌套；代码稍复杂，但通用性强（可用于任何支持四则运算和括号的表达式）。
- 递归下降：简洁直观，利用函数调用栈处理括号嵌套，但容易栈溢出（对于超长嵌套，但 LeetCode 限制通常足够）。
两者都能 AC，双栈是更底层的通用解法，值得掌握。

Refer to Kimi
Q1: Now can you compare both Leetcode 224 and 227 to Leetcode 772. Basic Calculator III ?
下面从 问题定义、核心难点、解法思路 三个维度，详细对比 224、227、772 这三道题的关系和演进。
--------------------------------------------------------------------------------
一、三题对比总览
维度224. Basic Calculator227. Basic Calculator II772. Basic Calculator III运算符+, -+, -, *, /+, -, *, /括号✅ 有 ()❌ 无✅ 有 ()核心难点括号嵌套、符号翻转乘除优先级（先乘除后加减）同时处理优先级 + 括号嵌套典型输入"1+(2-3)""3+5/2""2*(5+5*2)/3+(6/2+8)"本质只有同优先级运算 + 作用域切换不同优先级运算 + 无作用域完整计算器与前面关系基础版加入优先级224 + 227 的合体
--------------------------------------------------------------------------------
二、你的 224 解法 vs 227 解法
你的 224 解法核心逻辑
// 遇到 '('：保存当前上下文（result + sign），进入新作用域
stack.push(result);   // 括号前的累加结果
stack.push(sign);     // 括号前的符号
result = 0; sign = 1; // 新作用域重新开始

// 遇到 ')'：恢复上下文
result = result * stack.pop() + stack.pop();  
// 先弹出 sign，再弹出之前的 result
关键假设：所有运算都是 同一优先级（只有加减），所以可以一路累加 result。
为什么不能把 224 直接套用到 227？
因为 227 引入了 * 和 /，打破了"同优先级"假设：
"3+5*2"  
如果按 224 思路：读到 3，result=3；读到 +5，result=8；读到 *2，不能 result*=2
因为 5*2 应该先算，不能把前面的 3 也卷进去
227 必须解决的核心问题：当遇到 *// 时，需要**"撤销"上一次加减操作**，先算乘除。
--------------------------------------------------------------------------------
三、772 是两者的"合体"
772 同时面临两个挑战：
1.224 的挑战：括号嵌套，需要保存/恢复上下文
2.227 的挑战：乘除优先级，需要先算乘除、后算加减
772 的两种主流解法
解法 A：递归 DFS + 单栈（推荐，最优雅）
这是 224 的递归思想 和 227 的栈处理优先级 的结合。
核心思路：
- 用递归处理括号（像 224 一样，遇到 ( 进入新作用域）
- 用栈处理优先级（像 227 一样，*// 立即计算，+/- 入栈延后）
class Solution {
    public int calculate(String s) {
        return dfs(new ArrayDeque<>(Arrays.asList(s.split(""))));
    }
    
    private int dfs(Deque<String> q) {
        int num = 0;
        char sign = '+';
        Deque<Integer> stack = new ArrayDeque<>();
        
        while (!q.isEmpty()) {
            String c = q.poll();
            
            if (Character.isDigit(c.charAt(0))) {
                num = num * 10 + (c.charAt(0) - '0');
            }
            
            if (c.equals("(")) {
                num = dfs(q);  // 递归算括号内，把结果当数字
            }
            
            // 遇到运算符、右括号、或队列空了，处理上一个 sign
            if ("+-*/)".contains(c) || q.isEmpty()) {
                switch (sign) {
                    case '+' -> stack.push(num);
                    case '-' -> stack.push(-num);
                    case '*' -> stack.push(stack.pop() * num);
                    case '/' -> stack.push(stack.pop() / num);
                }
                num = 0;
                sign = c.charAt(0);
            }
            
            if (c.equals(")")) break;  // 结束当前递归层
        }
        
        // 栈里存的是所有加减项，求和即结果
        int res = 0;
        for (int n : stack) res += n;
        return res;
    }
}
与 224 的对比：
class Solution {
    int index = 0;  // 全局指针，模拟"消费"字符串
    
    public int calculate(String s) {
        this.index = 0;
        return dfs(s);
    }
    
    private int dfs(String s) {
        int result = 0;
        int sign = 1;  // +1 或 -1
        
        while (index < s.length()) {
            char c = s.charAt(index);
            
            if (c == ' ') {
                index++;
            } else if (c == '+') {
                sign = 1;
                index++;
            } else if (c == '-') {
                sign = -1;
                index++;
            } else if (c == '(') {
                index++;           // 跳过 '('
                int inner = dfs(s); // 递归算括号内
                result += sign * inner;
            } else if (c == ')') {
                index++;           // 跳过 ')'
                return result;      // 结束当前递归层
            } else if (Character.isDigit(c)) {
                int num = 0;
                while (index < s.length() && Character.isDigit(s.charAt(index))) {
                    num = num * 10 + (s.charAt(index) - '0');
                    index++;
                }
                result += sign * num;
            }
        }
        return result;
    }
}

224 (你的写法)772 (递归栈写法)括号处理stack.push(result); stack.push(sign)num = dfs(q) 递归返回值优先级处理无（只有加减）*// 立即算，+/- 入栈状态保存手动 push/pop 两个变量递归调用栈自动保存最终结果一个 result 变量sum(stack)
与 227 的对比：
227772 (递归栈)作用域只有一个全局作用域每层递归是一个独立作用域栈的用途保存操作数，处理优先级保存操作数，处理优先级 + 括号括号无递归天然处理
--------------------------------------------------------------------------------
解法 B：双栈法（Operator Stack + Operand Stack）
这是更"正统"的编译原理式解法，用 Shunting Yard 思想。
class Solution {
    public int calculate(String s) {
        Deque<Integer> nums = new ArrayDeque<>();
        Deque<Character> ops = new ArrayDeque<>();
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') continue;
            
            if (Character.isDigit(c)) {
                int num = c - '0';
                while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                    num = num * 10 + (s.charAt(i++ + 1) - '0');
                }
                nums.push(num);
            } else if (c == '(') {
                ops.push(c);
            } else if (c == ')') {
                while (ops.peek() != '(') calc(nums, ops);
                ops.pop(); // pop '('
            } else { // + - * /
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(c)) {
                    calc(nums, ops);
                }
                ops.push(c);
            }
        }
        while (!ops.isEmpty()) calc(nums, ops);
        return nums.pop();
    }
    
    private void calc(Deque<Integer> nums, Deque<Character> ops) {
        int b = nums.pop(), a = nums.pop();
        char op = ops.pop();
        switch (op) {
            case '+' -> nums.push(a + b);
            case '-' -> nums.push(a - b);
            case '*' -> nums.push(a * b);
            case '/' -> nums.push(a / b);
        }
    }
    
    private int precedence(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        return 0;
    }
}
与 224 的对比：
- 224 用栈保存 "计算上下文"（result + sign）
- 772 双栈用栈保存 "操作数"和"操作符"，按优先级出栈计算
与 227 的对比：
- 227 可以简化为单栈或 O(1) 空间，因为没有括号
- 772 必须双栈，因为括号会打断优先级比较
--------------------------------------------------------------------------------
四、一句话总结三者的关系
224 (只有加减+括号)          227 (加减乘除，无括号)

        ↘                           ↙
                    772 (加减乘除+括号)
                           ↓
              需要同时处理"优先级"和"作用域嵌套"
题目相当于224只有 +- 的"简单作用域切换"问题227只有 +-*/ 的"优先级"问题772完整的表达式求值，必须同时解决上面两个问题
--------------------------------------------------------------------------------
五、从 224 到 772 的思维升级路径
如果你已经理解了 224 的 stack.push(result); stack.push(sign) 技巧，那么理解 772 只需要再迈一步：
224 的栈保存的是"外层结果和符号"，772 的递归栈保存的是"整个外层计算状态"，而内层用同样的逻辑处理优先级。
换句话说：
- 224：遇到 ( → 保存 result 和 sign，进去重新开始
- 772：遇到 ( → 递归调用，子函数内部用 227 的逻辑处理 * /，返回一个数字给外层
这就是 772 最优雅解法的本质：227 的逻辑作为递归函数体，224 的递归作为作用域切换机制。


Refer to
L224.P11.7.Basic Calculator (Ref.L150,L227,L772)
L227.P11.7.Basic Calculator II (Ref.L150,L224,L772)
