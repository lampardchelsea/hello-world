import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Refer to
 * https://leetcode.com/problems/mini-parser/#/description
 * Given a nested list of integers represented as a string, implement a parser to deserialize it.
 * Each element is either an integer, or a list -- whose elements may also be integers or other lists.
 * Note: You may assume that the string is well-formed:
    String is non-empty.
    String does not contain white spaces.
    String contains only digits 0-9, [, - ,, ].

	Example 1:
	Given s = "324",
	You should return a NestedInteger object which contains a single integer 324.
	
	Example 2:
	Given s = "[123,[456,[789]]]",
	Return a NestedInteger object containing a nested list with 2 elements:
	1. An integer containing value 123.
	2. A nested list containing two elements:
	    i.  An integer containing value 456.
	    ii. A nested list with one element:
	         a. An integer containing value 789.
 * 
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *     // Constructor initializes an empty nested list.
 *     public NestedInteger();
 *
 *     // Constructor initializes a single integer.
 *     public NestedInteger(int value);
 *
 *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds, if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // Set this NestedInteger to hold a single integer.
 *     public void setInteger(int value);
 *
 *     // Set this NestedInteger to hold a nested list and adds a nested integer to it.
 *     public void add(NestedInteger ni);
 *
 *     // @return the nested list that this NestedInteger holds, if it holds a nested list
 *     // Return null if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 * 
 * Solution
 * https://discuss.leetcode.com/topic/54270/an-java-iterative-solution
 * This approach will just iterate through every char in the string (no recursion).
    If encounters '[', push current NestedInteger to stack and start a new one.
    If encounters ']', end current NestedInteger and pop a NestedInteger from stack to continue.
    If encounters ',', append a new number to curr NestedInteger, if this comma is not right after a brackets.
    Update index l and r, where l shall point to the start of a integer substring, while r shall points to the end+1 of substring.
 * 
 * 
 * https://discuss.leetcode.com/topic/54268/straightforward-java-solution-with-explanation-and-a-simple-implementation-of-nestedinteger-for-your-ease-of-testing
 * The idea is very straightforward:
    if it's '[', we just construct a new nested integer and push it onto the stack
    if it's a number, we parse the whole number and add to the previous nested integer object
    if it's ',', we'll just continue;
    if it's ']', we'll just pop one nested integer from the working stack and assign it to the result
    Also, we'll pay attention to this corner case or understand the input: the input could be "324", "[324]", 
    they are different: 
    the former should return a nested integer with one single integer, 
    e.g NestedInteger nestedInteger = new NestedInteger(324);
    the latter should return a nested integer with a list, 
    e.g NestedInteger nestedInteger = new NestedInteger(); 
        nestedInteger.add(new NestedInteger(324));
 */
public class MiniParser {
	// Refer to
	// https://discuss.leetcode.com/topic/54268/straightforward-java-solution-with-explanation-and-a-simple-implementation-of-nestedinteger-for-your-ease-of-testing/2
	private class NestedInteger {
	    private List<NestedInteger> list;
	    private Integer integer;
	    
	    public NestedInteger(List<NestedInteger> list){
	        this.list = list;
	    }
	    
	    public void add(NestedInteger nestedInteger) {
	        if(this.list != null){
	            this.list.add(nestedInteger);
	        } else {
	            this.list = new ArrayList();
	            this.list.add(nestedInteger);
	        }
	    }

	    public void setInteger(int num) {
	        this.integer = num;
	    }

	    public NestedInteger(Integer integer){
	        this.integer = integer;
	    }

	    public NestedInteger() {
	        this.list = new ArrayList();
	    }

	    public boolean isInteger() {
	        return integer != null;
	    }

	    public Integer getInteger() {
	        return integer;
	    }

	    public List<NestedInteger> getList() {
	        return list;
	    }
	    
	    public String printNi(NestedInteger thisNi, StringBuilder sb){
	        if(thisNi.isInteger()) {
	            sb.append(thisNi.integer);
	            sb.append(",");
	        }
	        sb.append("[");
	        for(NestedInteger ni : thisNi.list){
	            if(ni.isInteger()) {
	                sb.append(ni.integer);
	                sb.append(",");
	            }
	            else {
	                printNi(ni, sb);
	            }
	        }
	        sb.append("]");
	        return sb.toString();
	    }
	}
	
	
    public NestedInteger deserialize(String s) {
        if(s.isEmpty()) {
            return null;
        }
        // A single number just return a NestedInteger object which contains a single integer
        if(s.charAt(0) != '[') {
            return new NestedInteger(Integer.parseInt(s));
        }
        char[] chars = s.toCharArray();
        NestedInteger curr = null;
        // Create a stack to store each NestedInteger object
        Stack<NestedInteger> stack = new Stack<NestedInteger>();
        // left point to the start of current substring
        // right point to the (end + 1) of current substring, in case of String.substring()
        // works as [Inclusive...exclusive) by default 
        int left = 0;
        for(int right = 0; right < chars.length; right++) {
            if(chars[right] == '[') {
                if(curr != null) {
                    stack.push(curr);
                }
                curr = new NestedInteger();
                // The possible next num start after '[', use 'left' to nominate
                //left = right + 1;
            } else if(chars[right] == ']') {
                String num = s.substring(left, right);
                if(!num.isEmpty()) {
                    curr.add(new NestedInteger(Integer.parseInt(num)));
                }
                // If not adding check as "!stack.isEmpty()"
                // E.g
                // Runtime Error Message:Line 60: java.util.EmptyStackException
                // Last executed input:"[]"
                // Because if input as '[]', you have nothing stored on stack for pop out
                if(!stack.isEmpty()) {
                    NestedInteger pop = stack.pop();
                    pop.add(curr);
                    curr = pop;
                }
                //left = right + 1;
            } else if(chars[right] == ',') {
                // If not adding check as "chars[right - 1] != ']'"
            	// E.g
                // Runtime Error Message:Line 70: java.lang.NumberFormatException: For input string: ""
                // Last executed input:"[123,456,[788,799,833],[[]],10,[]]"
                // If encounters ',', append a new number to current NestedInteger, 
                // if this comma is not right after a brackets.
                // We check this way because of if encounters ']', end current NestedInteger 
                // and pop a NestedInteger from stack to continue.
                if(chars[right - 1] != ']') {
                    String num = s.substring(left, right);
                    curr.add(new NestedInteger(Integer.parseInt(num)));
                }
                //left = right + 1;
            }
            // Don't move 'left = right + 1' out of above if branch
            // Because for example like char = '-', if we don't move 'left = right + 1' 
            // out of if branch, the '-' will be ignored and no operation on execute
            // 'left = right + 1', which means not change the pointer value for start
            // position of current number. But if move it out, we will always execute 
            // 'left = right + 1', no matter encounter '-' or not, current number start
            // position will always increased, that's not right
            // E.g
            // Runtime Error Message:Line 62: java.lang.NumberFormatException: For input string: ""
            // Last executed input:"[-1,-2]"
            left = right + 1;
        }
        return curr;
    }
    
    public static void main(String[] args) {
    	String s = "[-1,-2]";
    	MiniParser m = new MiniParser();
    	@SuppressWarnings("unused")
	NestedInteger n = m.deserialize(s);
    }
    
}
