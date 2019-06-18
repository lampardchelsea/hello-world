import java.util.Stack;

/**
 * Refer to
 * https://leetcode.com/problems/basic-calculator/#/description
 * Implement a basic calculator to evaluate a simple expression string.
 * The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, 
 * non-negative integers and empty spaces .
 * You may assume that the given expression is always valid.

	Some examples:
	
	"1 + 1" = 2
	" 2-1 + 2 " = 3
	"(1+(4+5+2)-3)+(6+8)" = 23

 * Note: Do not use the eval built-in library function. 
 * 
 * Solution 1:
 * https://discuss.leetcode.com/topic/15816/iterative-java-solution-with-stack/7
 * Principle:
    (Sign before '+'/'-') = (This context sign);
    (Sign after '+'/'-') = (This context sign) * (1 or -1);
   Algorithm:
    Start from +1 sign and scan s from left to right;
    if c == digit: This number = Last digit * 10 + This digit;
    if c == '+': Add num to result before this sign; This sign = Last context sign * 1; clear num;
    if c == '-': Add num to result before this sign; This sign = Last context sign * -1; clear num;
    if c == '(': Push this context sign to stack;
    if c == ')': Pop this context and we come back to last context;
    Add the last num. This is because we only add number after '+' / '-'.
   Implementation:
    public int calculate(String s) {
	    if(s == null) return 0;
	        
	    int result = 0;
	    int sign = 1;
	    int num = 0;
	            
	    Stack<Integer> stack = new Stack<Integer>();
	    stack.push(sign);
	            
	    for(int i = 0; i < s.length(); i++) {
	        char c = s.charAt(i);
	                
	        if(c >= '0' && c <= '9') {
	            num = num * 10 + (c - '0');
	                    
	        } else if(c == '+' || c == '-') {
	            result += sign * num;
	            sign = stack.peek() * (c == '+' ? 1: -1); 
	            num = 0;
	                    
	        } else if(c == '(') {
	            stack.push(sign);
	                    
	        } else if(c == ')') {
	            stack.pop();
	        }
	    }
	            
	    result += sign * num;
	    return result;
	}
 *
 * https://segmentfault.com/a/1190000003796804
 * 栈法
 * 复杂度
 * 时间 O(N) 空间 O(N)
 * 思路
 * 很多人将该题转换为后缀表达式后（逆波兰表达式）求解，其实不用那么复杂。题目条件说明只有加减法和括号，由于加减法是相同顺序的，
 * 我们大可以直接把所有数顺序计算。难点在于多了括号后如何处理正负号。我们想象一下如果没有括号这题该怎们做：因为只有加减号，
 * 我们可以用一个变量sign来记录上一次的符号是加还是减，这样把每次读到的数字乘以这个sign就可以加到总的结果中了。
 * 有了括号后，整个括号内的东西可一看成一个东西，这些括号内的东西都会受到括号所在区域内的正负号影响（比如括号前面是个负号，
 * 然后括号所属的括号前面也是个负号，那该括号的符号就是正号）。但是每多一个括号，都要记录下这个括号所属的正负号，而每当一个
 * 括号结束，我们还要知道出来以后所在的括号所属的正负号。根据这个性质，我们可以使用一个栈，来记录这些括号所属的正负号。
 * 这样我们每遇到一个数，都可以根据当前符号，和所属括号的符号，计算其真实值。
 * 注意
 * 先用String.replace()去掉所有的空格
 * 
 * 
 * Solution 2:
 * https://discuss.leetcode.com/topic/15816/iterative-java-solution-with-stack
 * http://www.cnblogs.com/grandyang/p/4570699.html
 * 这道题让我们实现一个基本的计算器来计算简单的算数表达式，而且题目限制了表达式中只有加减号，数字，括号和空格，没有乘除，
 * 那么就没啥计算的优先级之分了。于是这道题就变的没有那么复杂了。我们需要一个栈来辅助计算，用个变量sign来表示当前的符号，
 * 我们遍历给定的字符串s，如果遇到了数字，由于可能是个多位数，所以我们要用while循环把之后的数字都读进来，然后用sign*num
 * 来更新结果res；如果遇到了加号，则sign赋为1，如果遇到了符号，则赋为-1；如果遇到了左括号，则把当前结果res和符号sign压入栈，
 * res重置为0，sign重置为1；如果遇到了右括号，结果res乘以栈顶的符号，栈顶元素出栈，结果res加上栈顶的数字，栈顶元素出栈
 */
public class BasicCalculator {
	/**
	 * E.g given string = "1+(2-(34-5))"
		(0) push initial sign = 1 onto stack
		        ------
			   1
			------
			  
		(1) encounter '1'
		    num = 1
		    current_context_sign = 1
		    stack.peek() = 1
		    result += 1 * 1 * 1 -> 1
		
		(2) encounter '+'
		    current_context_sign = 1
			
		(3) encounter '('
		    push -> current_context_sign * stack.peek() = 1 * 1 = 1
			------
			   1
			------
			   1
			------
			reset -> current_context_sign = 1
			
		(4) encounter '2'
		    num = 2
			current_context_sign = 1
			stack.peek() = 1
			result += 2 * 1 * 1 -> 1 + 2 = 3
			
		(5) encounter '-'
		    current_context_sign = -1
			
		(6) encounter '('
		    push -> current_context_sign * stack.peek() = -1 * 1 = -1
		        ------
			  -1
			------
			   1
			------
			   1
			------
		    reset -> current_context_sign = 1
		 
		(7) encounter '34'
		    num = 34
			current_context_sign = 1
			stack.peek() = -1
			result += 34 * 1 * -1 -> 3 - 34 = -31
			
		(8) encounter '-'
		    current_context_sign = -1
			
		(9) encounter '5'
			num = 5
			current_context_sign = -1
			stack.peek() = -1
			result += 5 * -1 * -1 -> -31 + 5 = -26
			
		(10) encounter ')'
		     pop -1
		        ------	 
			   1
			------
			   1
			------
			
		(11) encounter ')'
		     pop 1
			------
		           1
		        ------
	    stack back to initial status
	 */
	
    public int calculate(String s) {
        // Remove all redundant spaces
        s = s.replace(" ", "");
	// Stack used to store sign symbol, the most inside (brace pair to identify the level) 
	// sign symbol will store on stack.peek()
        Stack<Integer> stack = new Stack<Integer>();
        int i = 0;
        int current_context_sign = 1;
        int result = 0;
        // Initial start with sign as 1, imagine there is a
        // outside brace of given string, even given string
        // start with '-1', we will handle 
        stack.push(current_context_sign);
        while(i < s.length()) {
            if(s.charAt(i) == '+') {
                current_context_sign = 1;
                i++;
            } else if(s.charAt(i) == '-') {
                current_context_sign = -1;
                i++;
            } else if(s.charAt(i) == '(') {
                // Always use stack.peek() to get last context sign
                stack.push(current_context_sign * stack.peek());
		// Very Important !!!
                // Reset current context sign to 1, as entering
                // a new cotext start with '(', and treat as current 
                // context, the last context sign(all sign(computed) 
                // together) will store on stack 
                current_context_sign = 1;
                i++;
            } else if(s.charAt(i) == ')') {
                // Pop this context and we come back to last context
                stack.pop();
                i++;
            } else {
                int num = 0;
                // In case of continuous digits
                StringBuilder sb = new StringBuilder();
                while(i < s.length() && Character.isDigit(s.charAt(i))) {
                    sb.append(s.charAt(i));
                    i++;
                }
                num = Integer.parseInt(sb.toString());
                // When we calculate sign for current number, it has
                // two parts, current context sign, which means sign
                // for current brace, and previous context sign, which
                // means the computed sign before current brace
                result += num * current_context_sign * stack.peek();
            }
        }
        return result;
    }
    
    public static void main(String[] args) {
    	BasicCalculator b = new BasicCalculator();
//    	String s = "0";
    	String s = "1 + (2 - (34 - 5))";
    	int result = b.calculate(s);
    	System.out.println(result);
    }
}

