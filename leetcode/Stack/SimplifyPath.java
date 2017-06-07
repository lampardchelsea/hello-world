import java.util.Stack;

/**
 * Refer to
 * https://leetcode.com/problems/simplify-path/#/description
 * Given an absolute path for a file (Unix-style), simplify it.
 * For example,
	path = "/home/", => "/home"
	path = "/a/./b/../../c/", => "/c"
	
	click to show corner cases.
	Corner Cases:

    Did you consider the case where path = "/../"?
    In this case, you should return "/".
    Another corner case is the path might contain multiple slashes '/' together, such as "/home//foo/".
    In this case, you should ignore redundant slashes and return "/home/foo".
 *     
 * Solution
 * https://discuss.leetcode.com/topic/20502/can-someone-please-explain-what-does-simplify-means-in-this-context
 * https://segmentfault.com/a/1190000003815508
 * 栈法
 * 复杂度
 * 时间 O(N) 空间 O(N)
 * 思路
 * 思路很简单，先将整个路径按照/分开来，然后用一个栈，遇到..时弹出一个，遇到.和空字符串则不变，遇到正常路径则压入栈中。
 * 注意
 * 如果结果为空，要返回一个/
 * 弹出栈时要先检查栈是否为空
 * 
 * https://discuss.leetcode.com/topic/7675/java-10-lines-solution-with-stack
 * The main idea is to push to the stack every valid file name (not in {"",".",".."}), popping only if there's 
 * some thing to pop and we met "..". I don't feel like the code below needs any additional comments.
 * 
 * https://stackoverflow.com/questions/5931261/java-use-stringbuilder-to-insert-at-the-beginning
 */
public class SimplifyPath {
    public String simplifyPath(String path) {
        String[] tokens = path.split("/");
        Stack<String> stack = new Stack<String>();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < tokens.length; i++) {
            if(tokens[i].equals(".") || tokens[i].equals("")) {
                //continue;
            } else if(tokens[i].equals("..")) {
                if(!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                stack.push(tokens[i]);
            }
        }
        String result = "";
        for(String s : stack) {
            //sb.insert(0, "/").insert(0, s);
        	// No need to reverse when for loop scan
            // result = "/" + s + result;
            result = result + "/" + s;
        }
        return result.length() == 0 ? "/" : result;
    }
    
    public static void main(String[] args) {
    	
    }
}

