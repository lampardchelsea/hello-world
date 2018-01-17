/**
 * Refer to
 * https://segmentfault.com/a/1190000005762481
 * Write a function to generate the generalized abbreviations of a word.
 * Example:
 * Given word = "word", return the following list (order does not matter):
 * ["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"] 
 * 
 * 
 * Solution
 * https://discuss.leetcode.com/topic/32765/java-14ms-beats-100
 * For each char c[i], either abbreviate it or not.

	Abbreviate: count accumulate num of abbreviating chars, but don't append it yet.
	Not Abbreviate: append accumulated num as well as current char c[i].
	In the end append remaining num.
	Using StringBuilder can decrease 36.4% time.
	
	This comes to the pattern I find powerful:
	int len = sb.length(); // decision point
	... backtracking logic ...
	sb.setLength(len);     // reset to decision point
	
	Similarly, check out remove parentheses and add operators.
 * 
 * https://discuss.leetcode.com/topic/32270/java-backtracking-solution
 * The idea is: for every character, we can keep it or abbreviate it. To keep it, we add it to the current 
 * solution and carry on backtracking. To abbreviate it, we omit it in the current solution, but increment 
 * the count, which indicates how many characters have we abbreviated. When we reach the end or need to put 
 * a character in the current solution, and count is bigger than zero, we add the number into the solution
 * 
 * https://discuss.leetcode.com/topic/32270/java-backtracking-solution/12
 * I made two improvements in your code:
	(1) use StringBuilder rather than the concatenation of String.
	(2) use char[] to represent String rather than the original String.
	these two improvements make run time from 17ms to 14ms. Here is the code.
 * 	
 * https://segmentfault.com/a/1190000005762481
 * 注意
 * 对每个char，如果选择缩写它，不要立刻把他写成1append在stringbuilder里，因为万一后面的也选择缩写那么这两个字符就得缩写成"2"。
 * 所以一旦选择缩写一个字符，别把他变成数字，记下来，累积着，直到对于下一个字符我们选择不缩写(或到头了，即一直缩写到最后一个字符)，
 * 再把之前累计的缩写的个数append在stringbuilder里。
 * 
 * http://www.cnblogs.com/EdwardLiu/p/5092886.html
 * 这道题肯定是DFS/Backtracking, 但是怎么DFS不好想，跟Leetcode: Remove Invalid Parentheses的backtracking很像。
 * Generalized Abbreviation这道题是当前这个字母要不要abbreviate，要或者不要两种选择，
 * Parentheses那道题是当前括号要不要keep在StringBuffer里，要或不要同样是两种选择。
 * Syntax：注意27行使用StringBuffer.setLength(), 因为count一直累加可能变成两位数三位数，
 * delete stringbuffer最后一个字母可能不行，所以干脆设置为最初进recursion的长度	
 *
 * https://www.youtube.com/watch?v=ZMclRBDIGco
 * 
 * About StringBuffer setLength() method
 * https://www.tutorialspoint.com/java/lang/stringbuffer_setlength.htm
 */
public class Solution {
    public List<String> generateAbbreviations(String word) {
        List<String> res = new ArrayList<String>();
        StringBuilder tmpRes = new StringBuilder();
        char[] wordArray = word.toCharArray();
        dfs(res, tmpRes, wordArray, 0, 0);
        return res;
    }

    private void dfs(List<String> res, StringBuilder tmpRes, char[] wordArray, int pos, int numCount) {
        if(pos == wordArray.length) {
            if(numCount > 0) {
                tmpRes.append(numCount);
            }
            res.add(tmpRes.toString());
            return;     
        }

        // use number
        int len = tmpRes.length();
        dfs(res, tmpRes, wordArray, pos + 1, numCOunt + 1);
        tmpRes.setLength(len);  // backtracking

        // use characater
        len = tmpRes.length();
        if(numCount > 0) {
            tmpRes.append(numCount).append(wordArray[pos]);
            dfs(res, tmpRes, wordArray, pos + 1, 0);
        } else {
            tmpRes.append(wordArray[pos]);
            dfs(res, tmpRes, wordArray, pos + 1, 0);
        }
        tmpRes.setLength(len);  // backtracking
    }
}

