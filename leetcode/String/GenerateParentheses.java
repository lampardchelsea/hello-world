/**
 * Refer to
 * https://leetcode.com/problems/generate-parentheses/#/description
 *  Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
    For example, given n = 3, a solution set is:
    [
      "((()))",
      "(()())",
      "(())()",
      "()(())",
      "()()()"
    ]
 * 
 * Solution
 * https://discuss.leetcode.com/topic/8724/easy-to-understand-java-backtracking-solution
 * https://segmentfault.com/a/1190000003884251
 * 回溯法
 * 复杂度
 * 时间 O(N) 空间 O(N) 递归栈
 * 思路
 * 当我们放置一个新的符号时，我们有两个选择，放置左括号，或者放置右括号，但是按照题意我们最多放置n个左括号，
 * 放一个剩余可放置左括号就少一个，但剩余可放置右括号则多了一个。而对于右括号，必须前面放了一个左括号，
 * 我们才能放一个右括号。所以我们根据剩余可放置左括号，和剩余可放置右括号，代入递归，就可以求解。
 * public class Solution {
      List<String> res = new LinkedList<String>();
      public List<String> generateParenthesis(int n) {
          helper(n, 0, "");
          return res;
      }
      private void helper(int left, int right, String tmp){
          // 如果左括号右括号都放完了，则找到一个结果
          if(left == 0 && right == 0){
              res.add(tmp);
              return;
          }
          // 对于每个位置，我们有两种选择，要么放左括号，要么放右括号
          if (left > 0){
              helper(left - 1, right + 1, tmp+"(");
          }
          if (right > 0){
              helper(left, right - 1, tmp+")");
          }
      }
  }
 * 
*/

// The explain for why we need if(open > close) {...} ?
/**
https://leetcode.com/problems/generate-parentheses/discuss/10100/Easy-to-understand-Java-backtracking-solution/10980
I would like to share my understanding about this algorithm. Hope will make people easier to understand the beauty of this code.

The goal is to print a string of "(" ,")" in certain order. The length of string is 2n. The constraints are that "("s need to match ")"s.
Without constraints, we just simply print out "(" or ")" until length hits n. So the base case will be length == 2 n, recursive case is 
print out "(" and ")". The code will look like

//base case
if(string length == 2*n) {
add(string);
return;
}
//recursive case
add a "("
add a ")"

Let’s add in constraints now. We need to interpret the meanings of constraints. First, the first character should be "(". 
Second, at each step, you can either print "(" or ")", but print ")" only when there are more "("s than ")"s to make sure we only construct "()". 
Stop printing out "(" when the number of "(" s hit n. The first actually merges into the second condition.

The code will be:
//base case
if(string length == 2*n) {
add(string);
return;
}
//recursive case
if(number of "("s < n){
add a "("
}
if(number of "("s > number of ")"s){
add a ")"
}
*/

// Solution 1: Style 1
// https://leetcode.com/problems/generate-parentheses/discuss/10098/Java-DFS-way-solution/10940
class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<String>();
        helper(result, n, n, "");
        return result;
    }
    
    private void helper(List<String> result, int open, int close, String temp) {
        if(open == 0 && close == 0) {
            result.add(temp);
        }
        // Has left Parenthesis
        if(open > 0) {
            helper(result, open - 1, close, temp + "(");
        }
        // Has more right Parenthesis
        if(close > open) {
            helper(result, open, close - 1, temp + ")");
        }
    }
}


// Solution 2: Style 2
public class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<String>();
        helper(result, "", 0, 0, n);
        return result;
    }
    
    public void helper(List<String> result, String str, int open, int close, int max) {
        if(str.length() == max * 2) {
            result.add(str);
            return;
        }
        if(open < max) {
            helper(result, str + "(", open + 1, close, max);
        }
        if(close < open) {
            helper(result, str + ")", open, close + 1, max);
        }
    }
    
}
