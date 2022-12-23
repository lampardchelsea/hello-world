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

        // use number instead of character at position 'pos'
        int len = tmpRes.length();
        dfs(res, tmpRes, wordArray, pos + 1, numCOunt + 1);
        tmpRes.setLength(len);  // backtracking

        // still use characater at position 'pos'
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






































https://www.lintcode.com/problem/779/

Description
Write a function to generate the generalized abbreviations of a word.(order does not matter)
The two numbers after the abbreviation cannot be adjacent.

Example
Example 1:
```
Input: 
word = "word", 
Output: 
["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]
```

Example 2:
```
Input: 
word = "today" 
Output: 
["1o1a1","1o1ay","1o2y","1o3","1od1y","1od2","1oda1","1oday","2d1y","2d2","2da1","2day","3a1","3ay","4y","5","t1d1y","t1d2","t1da1","t1day","t2a1","t2ay","t3y","t4","to1a1","to1ay","to2y","to3","tod1y","tod2","toda1","today"]
```

---
Attempt 1: 2022-12-21

Solution 1: Backtracking (10 min)

Style 1: Use string
```
public class Solution { 
    /** 
     * @param word: the given word 
     * @return: the generalized abbreviations of a word 
     *          we will sort your return value in output 
     */ 
    public List<String> generateAbbreviations(String word) { 
        List<String> result = new ArrayList<String>(); 
        helper(word, 0, 0, "", result); 
        return result; 
    } 
    private void helper(String word, int index, int count, String str, List<String> result) { 
        if(index == word.length()) { 
            if(count > 0) { 
                str += count; 
            } 
            result.add(str); 
            return; 
        } 
        helper(word, index + 1, count + 1, str, result); 
        if(count > 0) { 
            helper(word, index + 1, 0, str + count + word.charAt(index), result); 
        } else { 
            helper(word, index + 1, 0, str + "" + word.charAt(index), result); 
        } 
    } 
}

Time Complexity : O(2^N), because for each character we have two choices, abbreviate or not
Space Complexity : O(2^N)
```

Thinking process

Initial template but lacking of two critical actions
```
public class Solution { 
    /** 
     * @param word: the given word 
     * @return: the generalized abbreviations of a word 
     *          we will sort your return value in output 
     */ 
    public List<String> generateAbbreviations(String word) { 
        List<String> result = new ArrayList<String>(); 
        helper(word, 0, 0, "", result); 
        return result; 
    } 
    private void helper(String word, int index, int count, String str, List<String> result) { 
        // Pending action: handle index reach the end but count still have value (count > 0), 
        if(index == word.length()) { 
            result.add(str); 
            return; 
        } 
        helper(word, index + 1, count + 1, str, result); 
        // Pending action: for count == 0 
        helper(word, index + 1, 0, str + count + word.charAt(index), result); 
    } 
}
```

Solution introduce two critical actions 

Refer to
https://protegejj.gitbook.io/algorithm-practice/leetcode/backtracking/320-generalized-abbreviation
https://www.lintcode.com/problem/779/solution/32011
```
class Solution { 
    public List<String> generateAbbreviations(String word) { 
        List<String> res = new ArrayList<>(); 
        getAbbreviations(word, 0, 0, "", res); 
        return res; 
    }

    public void getAbbreviations(String word, int pos, int count, String curStr, List<String> res) { 
        // 当pos为字符串末尾时，判断是否有压缩（count > 0），否则无需拼接字符串 
        if (pos == word.length()) { 
            // Later added special handling for pos reach the end but count still have value (count > 0), 
            // e.g "today" -> "1o3", when pos = 5, count = 3, if not include count will be only "1o" 
            // initial thought will only be result.add(curStr) 
            if (count > 0) { 
                curStr += count; 
            } 
            // Change result.add(curStr) to result.add(0, curStr) to reverse the output order 
            res.add(0, curStr); 
            return; 
        } 
        // 每次递归回溯时，当前位置压缩或不压缩 
        // Abbreviate the current character (Abbreviate word.charAt(pos)) 
        // e.g. one extreme case if keep accumulate count without any characters 
        // "today": "t" -> count = 1, "to" -> count = 2, "tod" -> count = 3 -> ... 
        getAbbreviations(word, pos + 1, count + 1, curStr, res); 
        // Keep the current character (Keep word.charAt(pos), so consume the count to build result string) 
        // Later added special handling for count == 0, initial thought not able to include this point 
        if(count > 0) { 
            getAbbreviations(word, pos + 1, 0, curStr + count + word.charAt(pos), res); 
        } else { 
            // 回溯时，如果回到最上一层，即count == 0，此时cur无需拼接数字 
            // e.g. one extreme case if count keep as 0 since always concatenate characters 
            // "today": "" + "t" = "t" -> "t" + "" + "o" = "to" -> "to" + "" + "d" = "tod" -> ... 
            getAbbreviations(word, pos + 1, 0, curStr + "" + word.charAt(pos), res); 
        } 
    } 
}
```

Video explain
https://www.youtube.com/watch?v=rO0NGn3ztXo
https://www.youtube.com/watch?v=rO0NGn3ztXo
---
Style 2: Use StringBuilder
```
public class Solution {
    /**
     * @param word: the given word
     * @return: the generalized abbreviations of a word
     *          we will sort your return value in output
     */
    public List<String> generateAbbreviations(String word) {
        List<String> result = new ArrayList<String>();
        helper(word, 0, 0, new StringBuilder(), result);
        return result;
    }



    private void helper(String word, int index, int count, StringBuilder sb, List<String> result) {
        if(index == word.length()) {
            int len1 = sb.length();
            if(count > 0) {
                sb.append(count);
            }
            result.add(sb.toString());
            sb.setLength(len1);
            return;
        }
        int len = sb.length(); // this line can relocate to the beginning inside helper method 
        helper(word, index + 1, count + 1, sb, result);
        if(count > 0) {
            helper(word, index + 1, 0, sb.append(count).append(word.charAt(index)), result);
        } else {
            helper(word, index + 1, 0, sb.append(word.charAt(index)), result);
        }
        sb.setLength(len);
    }
}

======================================================================================================
Or we can allocate "int len = sb.length()" to the beginning inside helper method, since "len1" and "len"
actually share the same value
public class Solution {
    /**
     * @param word: the given word
     * @return: the generalized abbreviations of a word
     *          we will sort your return value in output
     */
    public List<String> generateAbbreviations(String word) {
        List<String> result = new ArrayList<String>();
        helper(word, 0, 0, new StringBuilder(), result);
        return result;
    }



    private void helper(String word, int index, int count, StringBuilder sb, List<String> result) {
        int len = sb.length();
        if(index == word.length()) {
            if(count > 0) {
                sb.append(count);
            }
            result.add(sb.toString());
            sb.setLength(len);
            return;
        }
        helper(word, index + 1, count + 1, sb, result);
        if(count > 0) {
            helper(word, index + 1, 0, sb.append(count).append(word.charAt(index)), result);
        } else {
            helper(word, index + 1, 0, sb.append(word.charAt(index)), result);
        }
        sb.setLength(len);
    }
}
```

Refer to
https://www.lintcode.com/problem/779/solution/18864
Solved by DFS with backtracking.
Although problem description mentioned order doesn't matter, online judge still checks result's order.
Therefore, Collections.reverse() is necessary.
```
public class Solution {
    /**
     * @param word: the given word
     * @return: the generalized abbreviations of a word
     */
    public List<String> generateAbbreviations(String word) {
        List<String> res = new ArrayList<>();
        dfs(res, word, new StringBuilder(), 0, 0);
        Collections.reverse(res);
        return res;
    }
    
    private void dfs(List<String> res, String word, StringBuilder sb, 
int i, int count) {
        if (i == word.length()) {
            res.add(sb.toString());
            return;
        }
        
        sb.append(word.charAt(i));
        dfs(res, word, sb, i + 1, 0);
        sb.deleteCharAt(sb.length() - 1);
        
        removeTailDigits(sb);
        sb.append(count + 1);
        dfs(res, word, sb, i + 1, count + 1);
        removeTailDigits(sb);
    }
    
    private void removeTailDigits(StringBuilder sb) {
        while (sb.length() != 0 
               && Character.isDigit(sb.charAt(sb.length() - 1))) {
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
```

Refer to
https://walkccc.me/LeetCode/problems/0320/
```
class Solution {
  public List<String> generateAbbreviations(String word) {
    List<String> ans = new ArrayList<>();
    dfs(word, 0, 0, new StringBuilder(), ans);
    return ans;
  }



  private void dfs(final String word, int i, int count, StringBuilder sb, List<String> ans) {
    if (i == word.length()) {
      final int length = sb.length();
      ans.add(sb.append(getCountString(count)).toString());
      sb.setLength(length);
      return;
    }

    // Abbreviate word.charAt(i)
    dfs(word, i + 1, count + 1, sb, ans);
    // Keep word.charAt(i), so consume the count as a string
    final int length = sb.length();
    // Reset count to 0
    dfs(word, i + 1, 0, sb.append(getCountString(count)).append(word.charAt(i)), ans);
    sb.setLength(length);
  }



  private String getCountString(int count) {
    return count > 0 ? String.valueOf(count) : "";
  }
}
```
