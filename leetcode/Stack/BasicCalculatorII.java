import java.util.Stack;

/**
 * Refer to
 * https://leetcode.com/problems/basic-calculator-ii/#/description
 * Implement a basic calculator to evaluate a simple expression string.
 * The expression string contains only non-negative integers, +, -, *, / operators and 
 * empty spaces . The integer division should truncate toward zero.
 * 数轴上向零的方向取整（Truncate toward Zero）
 * 
 * You may assume that the given expression is always valid.
	Some examples:
	
	"3+2*2" = 7
	" 3/2 " = 1
	" 3+5 / 2 " = 5

 * Note: Do not use the eval built-in library function. 
 * 
 * Solution
 * https://segmentfault.com/a/1190000003796804
 * 栈法
 * 复杂度
 * 时间 O(N) 空间 O(N)
 * 思路
 * 因为乘法和除法不仅要知道下一个数，也要知道上一个数。所以我们用一个栈把上次的数存起来，遇到加减法就直接将数字压入栈中，
 * 遇到乘除法就把栈顶拿出来乘或除一下新数，再压回去。最后我们把栈里所有数加起来就行了。
 * 注意
 * 先用String.replace()去掉所有的空格
 */
public class BasicCalculatorII {
	public int calculate(String s) {
		String tmp = s.replace(" ", "");
		Stack<Integer> stack = new Stack<Integer>();
		char[] chars = s.toCharArray();
		/**
		 * For strategy, this problem is a little different than 
		 * the problem MiniParser which get useful substring value
		 * by setting two pointers [left, right]. The BasicCalculatorII
		 * must have a number before first operation, and the next
		 * number will right after first operation, so divide by
		 * operation, we need to find and calculate both numbers, the
		 * [left, right] won't help on this way.
		 */
		// Find and push the first number onto stack
		String firstNum = getNum(0, tmp);
		stack.push(Integer.parseInt(firstNum));
		// Find the position right after first number, it will
		// be the first operation character
		int i = firstNum.length() + 1;
		while(i < s.length()) {
			// Find the next number start right after previous operation
			// symbol based on (i + 1) as character at position i is 
			// surely an operation symbol
			String nextNum = getNum(i + 1, tmp);
			if(chars[i] == '+') {
				stack.push(Integer.parseInt(nextNum));
			} else if(chars[i] == '-') {
				stack.push(Integer.parseInt(nextNum));
			} else if(chars[i] == '*') {
				stack.push(stack.pop() * (Integer.parseInt(nextNum)));
			} else if(chars[i] == '/') {
				stack.push(stack.pop() / (Integer.parseInt(nextNum)));
			}
			// Next scan will start from the position right after
			// next number
			i += (nextNum.length() + 1);
		}
		int result = 0;
		while(!stack.isEmpty()) {
			result += stack.pop();
		}
		return result;
	}
	
	public String getNum(int start, String s) {
		StringBuilder sb = new StringBuilder();
		while(start < s.length() && Character.isDigit(s.charAt(start))) {
			sb.append(s.charAt(start));
			start++;
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		BasicCalculatorII b = new BasicCalculatorII();
		String s = "  30";
		int result = b.calculate(s);
		System.out.println(result);
	}
	
}

