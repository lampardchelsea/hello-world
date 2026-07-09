https://leetcode.com/problems/minimize-result-by-adding-parentheses-to-expression/description/
You are given a 0-indexed string expression of the form "<num1>+<num2>" where <num1> and <num2> represent positive integers.
Add a pair of parentheses to expression such that after the addition of parentheses, expression is a valid mathematical expression and evaluates to the smallest possible value. The left parenthesis must be added to the left of '+' and the right parenthesis must be added to the right of '+'.
Return expression after adding a pair of parentheses such that expression evaluates to the smallest possible value. If there are multiple answers that yield the same result, return any of them.
The input has been generated such that the original value of expression, and the value of expression after adding any pair of parentheses that meets the requirements fits within a signed 32-bit integer.
 
Example 1:
Input: expression = "247+38"
Output: "2(47+38)"
Explanation: The expression evaluates to 2 * (47 + 38) = 2 * 85 = 170.
Note that "2(4)7+38" is invalid because the right parenthesis must be to the right of the '+'.
It can be shown that 170 is the smallest possible value.
Example 2:
Input: expression = "12+34"
Output: "1(2+3)4"
Explanation: The expression evaluates to 1 * (2 + 3) * 4 = 1 * 5 * 4 = 20.

Example 3:
Input: expression = "999+999"
Output: "(999+999)"
Explanation: The expression evaluates to 999 + 999 = 1998.
 
Constraints:
- 3 <= expression.length <= 10
- expression consists of digits from '1' to '9' and '+'.
- expression starts and ends with digits.
- expression contains exactly one '+'.
- The original value of expression, and the value of expression after adding any pair of parentheses that meets the requirements fits within a signed 32-bit integer.
--------------------------------------------------------------------------------
Attempt 1: 2026-07-09
Solution 1: Math (10 min)
Refer to Deepseek
以下是 LeetCode 2232 "Minimize Result by Adding Parentheses to Expression" 的 Java 解法，使用枚举所有可能的括号位置，计算最小结果并返回对应的表达式字符串。
class Solution {
    public String minimizeResult(String expression) {
        int plus = expression.indexOf('+');
        String leftNum = expression.substring(0, plus);
        String rightNum = expression.substring(plus + 1);
        
        long min = Long.MAX_VALUE;
        String best = "";
        
        // 枚举左括号的位置：在 leftNum 的第 i 个字符之后（i 从 0 到 len-1）
        for (int i = 0; i < leftNum.length(); i++) {
            String leftPart = leftNum.substring(0, i);      // 括号外的左边部分，可为空
            String midLeft = leftNum.substring(i);          // 括号内的左边部分，非空
            
            // 枚举右括号的位置：在 rightNum 的第 j 个字符之前（j 从 1 到 len）
            for (int j = 1; j <= rightNum.length(); j++) {
                String midRight = rightNum.substring(0, j); // 括号内的右边部分，非空
                String rightPart = rightNum.substring(j);   // 括号外的右边部分，可为空
                
                // 构建表达式字符串
                String expr = leftPart + "(" + midLeft + "+" + midRight + ")" + rightPart;
                
                // 计算表达式的值
                long leftVal = leftPart.isEmpty() ? 1 : Long.parseLong(leftPart);
                long midSum = Long.parseLong(midLeft) + Long.parseLong(midRight);
                long rightVal = rightPart.isEmpty() ? 1 : Long.parseLong(rightPart);
                long total = leftVal * midSum * rightVal;
                
                // 更新最小值
                if (total < min) {
                    min = total;
                    best = expr;
                }
            }
        }
        return best;
    }
}
算法说明
- 表达式固定为 num1 + num2，只能添加一对括号，且括号必须包含加号。
- 枚举左括号在 num1 中的插入位置（在某个数字字符之后），右括号在 num2 中的插入位置（在某个数字字符之前）。
- 对于每种分割，表达式为 leftPart + "(" + midLeft + "+" + midRight + ")" + rightPart，其中：
- leftPart 是 num1 在左括号前的部分，可能为空。
- midLeft 是 num1 在左括号后的部分（非空）。
- midRight 是 num2 在右括号前的部分（非空）。
- rightPart 是 num2 在右括号后的部分，可能为空。
- 计算值：将空字符串视为 1，其余部分转为整数，整体值为 leftVal * (midLeft + midRight) * rightVal。
- 记录所有合法分割中的最小值，并返回对应的表达式字符串。
复杂度
- 时间复杂度：O(L * R)，其中 L 和 R 分别是左右数字的长度（最多 10 位），枚举次数很少。
- 空间复杂度：O(1)。
该解法在 LeetCode 上可通过所有测试用例。

Refer to Kimi
Here is the Java solution for LeetCode 2232. Minimize Result by Adding Parentheses to Expression.
Approach: Brute Force Enumeration
Since expression.length <= 10, we can simply try all valid placements:
- Left parenthesis ( must be placed before the +
- Right parenthesis ) must be placed after the +
For each valid placement, the expression evaluates as:
a * (b + c) * d
Where:
- a = number before ( (or 1 if nothing)
- b = left part inside ()
- c = right part inside ()
- d = number after ) (or 1 if nothing)
Time Complexity: O(L * R) where L = digits before +, R = digits after +
Space Complexity: O(1)
class Solution {
    public String minimizeResult(String expression) {
        int plusIndex = expression.indexOf('+');
        String left = expression.substring(0, plusIndex);
        String right = expression.substring(plusIndex + 1);
        
        int minVal = Integer.MAX_VALUE;
        String ans = "";
        
        // Try all valid placements of parentheses
        // i: where to place '(' in left part (before digit i)
        // j: where to place ')' in right part (after digit j)
        for (int i = 0; i < left.length(); i++) {
            for (int j = 0; j < right.length(); j++) {
                // a: prefix before '(' (1 if empty)
                int a = (i == 0) ? 1 : Integer.parseInt(left.substring(0, i));
                // b: left part inside ()
                int b = Integer.parseInt(left.substring(i));
                // c: right part inside ()
                int c = Integer.parseInt(right.substring(0, j + 1));
                // d: suffix after ')' (1 if empty)
                int d = (j == right.length() - 1) ? 1 : Integer.parseInt(right.substring(j + 1));
                
                int val = a * (b + c) * d;
                
                if (val < minVal) {
                    minVal = val;
                    ans = left.substring(0, i) + "(" + left.substring(i) + "+" 
                        + right.substring(0, j + 1) + ")" + right.substring(j + 1);
                }
            }
        }
        
        return ans;
    }
}
Walkthrough: expression = "247+38"
ijabcdval = a*(b+c)*dExpression001247381*(247+3)*8 = 2000(247+3)80112473811*(247+38)*1 = 285(247+38)10247382*(47+3)*8 = 8002(47+3)8112473812*(47+38)*1 = 170 ✅2(47+38)202473824*(7+3)*8 = 192024(7+3)82124738124*(7+38)*1 = 108024(7+38)
Minimum: 170 with "2(47+38)" ✅

Refer to
L2019.The Score of Students Solving Math Expression (Ref.L227,L241)
