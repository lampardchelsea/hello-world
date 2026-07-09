https://leetcode.com/problems/the-score-of-students-solving-math-expression/description/
You are given a string s that contains digits 0-9, addition symbols '+', and multiplication symbols '*' only, representing a valid math expression of single digit numbers (e.g., 3+5*2). This expression was given to n elementary school students. The students were instructed to get the answer of the expression by following this order of operations:
1.Compute multiplication, reading from left to right; Then,
2.Compute addition, reading from left to right.
You are given an integer array answers of length n, which are the submitted answers of the students in no particular order. You are asked to grade the answers, by following these rules:
- If an answer equals the correct answer of the expression, this student will be rewarded 5 points;
- Otherwise, if the answer could be interpreted as if the student applied the operators in the wrong order but had correct arithmetic, this student will be rewarded 2 points;
- Otherwise, this student will be rewarded 0 points.
Return the sum of the points of the students.
 
Example 1:

Input: s = "7+3*1*2", answers = [20,13,42]
Output: 7
Explanation: 
As illustrated above, the correct answer of the expression is 13, therefore one student is rewarded 5 points: [20,13,42]
A student might have applied the operators in this wrong order: ((7+3)*1)*2 = 20. Therefore one student is rewarded 2 points: [20,13,42]
The points for the students are: [2,5,0]. The sum of the points is 2+5+0=7.

Example 2:
Input: s = "3+5*2", answers = [13,0,10,13,13,16,16]
Output: 19
Explanation: 
The correct answer of the expression is 13, therefore three students are rewarded 5 points each: [13,0,10,13,13,16,16]
A student might have applied the operators in this wrong order: ((3+5)*2 = 16. Therefore two students are rewarded 2 points: [13,0,10,13,13,16,16]
The points for the students are: [5,0,0,5,5,2,2]. The sum of the points is 5+0+0+5+5+2+2=19.

Example 3:
Input: s = "6+0*1", answers = [12,9,6,4,8,6]
Output: 10
Explanation: The correct answer of the expression is 6.If a student had incorrectly done (6+0)*1, the answer would also be 6.By the rules of grading, the students will still be rewarded 5 points (as they got the correct answer), not 2 points.
The points for the students are: [0,0,5,0,0,5]. The sum of the points is 10.
 
Constraints:
- 3 <= s.length <= 31
- s represents a valid expression that contains only digits 0-9, '+', and '*' only.
- All the integer operands in the expression are in the inclusive range [0, 9].
- 1 <= The count of all operators ('+' and '*') in the math expression <= 15
- Test data are generated such that the correct answer of the expression is in the range of [0, 1000].
- Test data are generated such that value never exceeds 109 in intermediate steps of multiplication.
- n == answers.length
- 1 <= n <= 104
- 0 <= answers[i] <= 1000
--------------------------------------------------------------------------------
Attempt 1: 2026-07-06
Solution 1: Divide and Conquer + Stack (30 min)
The Divide and Conquer comes from L241.Different Ways to Add Parentheses (Ref.L95,L2019)
The Stack comes from L227.P11.7.Basic Calculator II (Ref.L150,L224,L772)
TLE Solution:
class Solution {
    public int scoreOfStudents(String s, int[] answers) {
        int correct = computeCorrect(s);
        Set<Integer> possible = getAllPossibleResults(s);
        int total = 0;
        for(int ans : answers) {
            if(ans == correct) {
                total += 5;
            } else if(possible.contains(ans)) {
                total += 2;
            }
        }
        return total;
    }

    private Set<Integer> getAllPossibleResults(String s) {
        List<String> tokens = new ArrayList<>();
        for(int i = 0; i < s.length(); i++) {
            String token = "";
            while(i < s.length() && Character.isDigit(s.charAt(i))) {
                token += s.charAt(i);
                i++;
            }
            tokens.add(token);
            if(i < s.length()) {
                tokens.add(String.valueOf(s.charAt(i)));
            }
        }
        List<Integer> list = helper(tokens, 0, tokens.size() - 1);
        Set<Integer> set = new HashSet<>(list);
        return set;
    }

    private List<Integer> helper(List<String> tokens, int lo, int hi) {
        List<Integer> result = new ArrayList<>();
        if(lo == hi) {
            result.add(Integer.valueOf(tokens.get(lo)));
            return result;
        }
        for(int i = lo; i <= hi; i++) {
            if(i % 2 == 1) {
                String ops = tokens.get(i);
                List<Integer> left = helper(tokens, lo, i - 1);
                List<Integer> right = helper(tokens, i + 1, hi);
                for(int l : left) {
                    for(int r : right) {
                        if(ops.equals("+")) {
                            result.add(l + r);
                        } else {
                            result.add(l * r);
                        } 
                    }
                }
            }
        }
        return result;
    }

    private int computeCorrect(String s) {
        Stack<Integer> stack = new Stack<>();
        int curNum = 0;
        char lastOperation = '+';
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(Character.isDigit(c)) {
                curNum = curNum * 10 + c - '0';
            }
            if(!Character.isDigit(c) || i == s.length() - 1) {
                if(lastOperation == '+') {
                    stack.push(curNum);
                } else {
                    stack.push(stack.pop() * curNum);
                }
                curNum = 0;
                lastOperation = c;
            }
        }
        int result = 0;
        while(!stack.isEmpty()) {
            result += stack.pop();
        }
        return result;
    }
}

Then improve with Memo and Critical Prune on val <= 1000
class Solution {
    public int scoreOfStudents(String s, int[] answers) {
        int correct = computeCorrect(s);
        Set<Integer> possible = getAllPossibleResults(s);
        int total = 0;
        for(int ans : answers) {
            if(ans == correct) {
                total += 5;
            } else if(possible.contains(ans)) {
                total += 2;
            }
        }
        return total;
    }

    private Set<Integer> getAllPossibleResults(String s) {
        List<String> tokens = new ArrayList<>();
        for(int i = 0; i < s.length(); i++) {
            String token = "";
            while(i < s.length() && Character.isDigit(s.charAt(i))) {
                token += s.charAt(i);
                i++;
            }
            tokens.add(token);
            if(i < s.length()) {
                tokens.add(String.valueOf(s.charAt(i)));
            }
        }
        Map<String, Set<Integer>> memo = new HashMap<>();
        Set<Integer> set = helper(tokens, 0, tokens.size() - 1, memo);
        return set;
    }

    private Set<Integer> helper(List<String> tokens, int lo, int hi, Map<String, Set<Integer>> memo) {
        String key = lo + "," + hi;
        if(memo.containsKey(key)) {
            return memo.get(key);
        }
        Set<Integer> result = new HashSet<>();
        if(lo == hi) {
            int val = Integer.valueOf(tokens.get(lo));
            result.add(val);
            memo.put(key, result);
            return result;
        }
        for(int i = lo; i <= hi; i++) {
            if(i % 2 == 1) {
                String ops = tokens.get(i);
                Set<Integer> left = helper(tokens, lo, i - 1, memo);
                Set<Integer> right = helper(tokens, i + 1, hi, memo);
                for(int l : left) {
                    for(int r : right) {
                        int val = ops.equals("+") ? l + r : l * r;
                        // CRITICAL: pruning
                        if(val <= 1000) {
                            result.add(val);
                        }
                    }
                }
            }
        }
        memo.put(key, result);
        return result;
    }

    private int computeCorrect(String s) {
        Stack<Integer> stack = new Stack<>();
        int curNum = 0;
        char lastOperation = '+';
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(Character.isDigit(c)) {
                curNum = curNum * 10 + c - '0';
            }
            if(!Character.isDigit(c) || i == s.length() - 1) {
                if(lastOperation == '+') {
                    stack.push(curNum);
                } else {
                    stack.push(stack.pop() * curNum);
                }
                curNum = 0;
                lastOperation = c;
            }
        }
        int result = 0;
        while(!stack.isEmpty()) {
            result += stack.pop();
        }
        return result;
    }
}

Refer to Deepseek
这道题的核心是模拟学生的计算错误：他们可能会以任意顺序执行加法和乘法（即任何合法的加括号方式），而不遵循先乘后加的优先级规则。
我们的任务是根据学生的答案给他们打分。
核心思路
整个解法分为三个清晰的部分：
1.计算正确答案：严格按照数学规则，先乘除后加减，计算出表达式的唯一正确结果。
2.计算所有错误答案：使用区间DP，模拟所有可能的计算顺序（即所有加括号的方式），得到一个所有可能结果的集合。
3.评分：遍历每个学生的答案，根据规则累加分数：
- 等于正确答案：加 5 分。
- 不等于正确答案，但在“所有可能结果”的集合中：加 2 分。
- 否则：加 0 分。
算法步骤详解
1. 解析表达式
为了方便处理，我们先将字符串s解析成两个列表：
- nums：存储所有的数字（因为题目保证是单个数字，如3+5*2，所以直接转换即可）。
- ops：存储数字之间的运算符（+ 或 *）。
2. 计算正确结果 (getCorrectResult)
这个逻辑与LeetCode 227题（基本计算器 II）相同。我们遍历表达式，先处理所有的乘法，最后再处理加法。
- 初始化 sum = 0, cur = nums[0]。
- 遍历运算符：
- 如果是 *，将 cur 与下一个数字相乘：cur *= nums[i+1]。
- 如果是 +，将当前的 cur 累加到 sum，并重置 cur = nums[i+1]。
- 遍历结束后，将最后一段的 cur 加到 sum 中。
3. 计算所有可能结果 (getAllPossibleResults)
这是本题的关键。我们使用区间DP来模拟所有加括号的方式。
- 定义DP状态：dp[i][j] 是一个 HashSet<Integer>，存储从第 i 个数字到第 j 个数字这个子表达式，通过所有可能的计算顺序能得到的所有结果。
- 初始化：对于长度为1的区间，dp[i][i] 只包含数字 nums[i] 本身。
- 状态转移：对于长度 len 从 2 到 n 的区间 [i, j]：
- 枚举区间内最后一个被计算的运算符位置 k（i <= k < j）。这相当于把表达式 [i, j] 在 k 处拆分成左右两部分 [i, k] 和 [k+1, j]。
- 从 dp[i][k] 中取一个结果 a，从 dp[k+1][j] 中取一个结果 b，用运算符 ops[k] 进行计算，得到结果 val。
- 将 val 加入到 dp[i][j] 中。
- 剪枝：为了控制结果集的大小，我们只保留 0 <= val <= 1000 的结果。因为题目中学生的答案范围是 0 <= answers[i] <= 1000，超出这个范围的结果对评分没有意义。
最终，dp[0][n-1] 就是整个表达式所有可能的结果集合。
Java代码实现
import java.util.*;

class Solution {
    public int scoreOfStudents(String s, int[] answers) {
        // 1. Parse the expression
        List<Integer> nums = new ArrayList<>();
        List<Character> ops = new ArrayList<>();
        for (char c : s.toCharArray()) {
            if (c == '+' || c == '*') {
                ops.add(c);
            } else {
                nums.add(c - '0');
            }
        }

        int n = nums.size();

        // 2. Calculate the correct result (with precedence)
        int correct = getCorrectResult(nums, ops);

        // 3. Calculate all possible results (with any parenthesization)
        Set<Integer> allPossible = getAllPossibleResults(nums, ops);

        // 4. Grade the students
        int totalScore = 0;
        for (int ans : answers) {
            if (ans == correct) {
                totalScore += 5;
            } else if (allPossible.contains(ans)) {
                totalScore += 2;
            }
        }
        return totalScore;
    }

    private int getCorrectResult(List<Integer> nums, List<Character> ops) {
        int sum = 0;
        int cur = nums.get(0);
        for (int i = 0; i < ops.size(); i++) {
            if (ops.get(i) == '*') {
                cur *= nums.get(i + 1);
            } else { // '+'
                sum += cur;
                cur = nums.get(i + 1);
            }
        }
        sum += cur;
        return sum;
    }

    private Set<Integer> getAllPossibleResults(List<Integer> nums, List<Character> ops) {
        int n = nums.size();
        // dp[i][j] = set of all possible results from nums[i..j]
        Set<Integer>[][] dp = new HashSet[n][n];
        
        // Initialize for length 1
        for (int i = 0; i < n; i++) {
            dp[i][i] = new HashSet<>();
            dp[i][i].add(nums.get(i));
        }

        // Iterate over interval length
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                dp[i][j] = new HashSet<>();
                
                // Try all possible last operation positions
                for (int k = i; k < j; k++) {
                    char op = ops.get(k);
                    for (int left : dp[i][k]) {
                        for (int right : dp[k + 1][j]) {
                            int val = 0;
                            if (op == '+') {
                                val = left + right;
                            } else { // '*'
                                val = left * right;
                            }
                            // Keep only results within the possible answer range [0, 1000]
                            if (val >= 0 && val <= 1000) {
                                dp[i][j].add(val);
                            }
                        }
                    }
                }
            }
        }
        return dp[0][n - 1];
    }
}
复杂度分析
- 时间复杂度: O(n^3 * M^2)，其中 n 是数字的个数（n <= 16），M 是每个区间内可能结果的最大数量（由于剪枝，M <= 1001）。虽然看起来很大，但 n 非常小，实际运行是高效的。
- 空间复杂度: O(n^2 * M)，用于存储DP表。

Q1: Is this problem has any similar to Leetcode 241 ? If yes, how about the style we write "getAllPossibleResults" method ?
是的，LeetCode 2019 和 LeetCode 241 的核心思想非常相似，两者都涉及 枚举所有可能的加括号方式，从而得到表达式在不同计算顺序下的所有结果。
--------------------------------------------------------------------------------
相似点
特性LeetCode 241LeetCode 2019核心问题给定表达式，返回所有可能的结果（通过加括号）同样需要所有可能的结果（用于评分）运算符+ - *+ *（本题只包含加法和乘法）求解方法分治递归（或区间DP）分治递归（或区间DP）结果集所有可能值（无范围限制）所有可能值，但只需保留 0~1000（因为答案范围）
--------------------------------------------------------------------------------
用 LeetCode 241 的风格写 getAllPossibleResults
LeetCode 241 的经典解法是 递归分治 + 记忆化。我们可以将这种风格直接迁移到本题，完全替代之前的区间DP实现。
步骤
1.解析出数字列表 nums 和运算符列表 ops（长度分别为 n 和 n-1）。
2.定义递归函数 dfs(l, r)，返回区间 [l, r]（数字下标）内所有可能的结果。
3.如果 l == r，直接返回 {nums[l]}。
4.否则，枚举区间内的每个运算符位置 k（l ≤ k < r），递归计算左区间 [l, k] 和右区间 [k+1, r] 的所有结果，然后两两组合。
5.使用 Map<String, Set<Integer>> 或二维数组缓存结果，避免重复计算。
完整 Java 代码（替换原 getAllPossibleResults）
private Set<Integer> getAllPossibleResults(List<Integer> nums, List<Character> ops) {
    int n = nums.size();
    // 记忆化缓存，key 为 "l,r"
    Map<String, Set<Integer>> memo = new HashMap<>();
    return dfs(0, n - 1, nums, ops, memo);
}

private Set<Integer> dfs(int l, int r, List<Integer> nums, List<Character> ops, Map<String, Set<Integer>> memo) {
    String key = l + "," + r;
    if (memo.containsKey(key)) return memo.get(key);

    Set<Integer> res = new HashSet<>();
    if (l == r) {
        res.add(nums.get(l));
    } else {
        for (int k = l; k < r; k++) {
            Set<Integer> left = dfs(l, k, nums, ops, memo);
            Set<Integer> right = dfs(k + 1, r, nums, ops, memo);
            char op = ops.get(k);
            for (int a : left) {
                for (int b : right) {
                    int val = (op == '+') ? a + b : a * b;
                    if (val >= 0 && val <= 1000) {  // 剪枝，与之前一致
                        res.add(val);
                    }
                }
            }
        }
    }
    memo.put(key, res);
    return res;
}
与区间DP版本的对比
实现方式优点缺点区间DP（原解法）完全迭代，无递归栈开销，适合大n代码稍显繁琐，需处理二维数组递归+记忆化（241风格）代码简洁，逻辑清晰，与241解法完全一致递归有栈深度限制（但n≤16，安全），需要额外记忆化映射
--------------------------------------------------------------------------------
结论
LeetCode 2019 的 “所有可能结果” 计算与 LeetCode 241 本质上完全相同，只是本题加入了结果范围剪枝（0~1000）。因此，完全可以直接套用 LeetCode 241 的分治递归写法，代码更直观，也更容易被面试官理解。
如果你更习惯迭代，原区间DP也是完全正确的；如果追求代码简洁，递归+记忆化是首选。两种方法都可通过本题。

Q2：It time out when input is huge ?
您的代码在输入规模较大时超时，主要原因是没有使用记忆化缓存，导致递归重复计算大量子表达式。另外，没有对结果值做剪枝（只保留 0~1000），使得结果集膨胀。
下面是对您代码的改进版本，添加了 记忆化 和 结果范围剪枝，从而大幅提升性能。
--------------------------------------------------------------------------------
改进后的 getAllPossibleResults 和 helper
private Set<Integer> getAllPossibleResults(String s) {
    // 解析 tokens
    List<String> tokens = new ArrayList<>();
    for (int i = 0; i < s.length(); i++) {
        String token = "";
        while (i < s.length() && Character.isDigit(s.charAt(i))) {
            token += s.charAt(i);
            i++;
        }
        tokens.add(token);
        if (i < s.length()) {
            tokens.add(String.valueOf(s.charAt(i)));
        }
    }
    // 记忆化缓存，key = "lo,hi"
    Map<String, Set<Integer>> memo = new HashMap<>();
    return helper(tokens, 0, tokens.size() - 1, memo);
}

private Set<Integer> helper(List<String> tokens, int lo, int hi, 
                            Map<String, Set<Integer>> memo) {
    String key = lo + "," + hi;
    if (memo.containsKey(key)) return memo.get(key);

    Set<Integer> res = new HashSet<>();
    if (lo == hi) {
        int val = Integer.parseInt(tokens.get(lo));
        if (val >= 0 && val <= 1000) res.add(val);
        memo.put(key, res);
        return res;
    }

    for (int i = lo; i <= hi; i++) {
        if (i % 2 == 1) { // 运算符位置
            String op = tokens.get(i);
            Set<Integer> left = helper(tokens, lo, i - 1, memo);
            Set<Integer> right = helper(tokens, i + 1, hi, memo);
            for (int l : left) {
                for (int r : right) {
                    int val = op.equals("+") ? l + r : l * r;
                    if (val >= 0 && val <= 1000) {
                        res.add(val);
                    }
                }
            }
        }
    }

    memo.put(key, res);
    return res;
}
--------------------------------------------------------------------------------
其他部分保持不变
computeCorrect 方法和 scoreOfStudents 主方法无需改动，它们逻辑正确。
--------------------------------------------------------------------------------
为什么这样修改可以避免 TLE？
1.记忆化：
对于同一个区间 [lo, hi]，它的所有结果只需要计算一次。原代码每次碰到相同区间都会重新递归，导致指数级重复计算。
2.结果剪枝：
题目保证学生答案在 [0, 1000] 范围内，任何超过 1000 或小于 0 的结果都不可能得分，因此我们直接丢弃，大大减少每个区间内结果集的大小。
加上这两点，即使表达式长度最大为 31（约 15 个数字），递归状态总数也不过 O(n^2)，每个状态内部枚举运算符和左右子集，整体时间复杂度可接受。
--------------------------------------------------------------------------------
最终完整代码（只显示改动部分）
将您的 getAllPossibleResults 和 helper 替换为上述代码即可。其余部分（computeCorrect、scoreOfStudents）保持不变。
这样修改后，您提供的超长 answers 测试用例应该能在规定时间内通过。

Q3：The solution still very slow even it pass all the tests, any more improvement way ?
您的代码已经正确，但在性能上仍有优化空间。主要瓶颈在于 getAllPossibleResults 中的递归计算，虽然用了记忆化，但使用了 String 键和 HashSet，且没有充分利用结果范围（0~1000）来压缩数据结构。
以下是我给出的进一步优化版本，使用 BitSet 表示结果集合，并用二维数组缓存，大幅减少哈希开销和内存占用：
import java.util.*;

class Solution {
    public int scoreOfStudents(String s, int[] answers) {
        int correct = computeCorrect(s);
        Set<Integer> possible = getAllPossibleResults(s);
        int total = 0;
        for (int ans : answers) {
            if (ans == correct) total += 5;
            else if (possible.contains(ans)) total += 2;
        }
        return total;
    }

    private Set<Integer> getAllPossibleResults(String s) {
        // 1. 解析为数字数组和运算符数组
        List<Integer> numList = new ArrayList<>();
        List<Character> opList = new ArrayList<>();
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                int num = 0;
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    num = num * 10 + (s.charAt(i) - '0');
                    i++;
                }
                numList.add(num);
            } else {
                opList.add(c);
                i++;
            }
        }
        int n = numList.size();
        int[] nums = new int[n];
        for (int idx = 0; idx < n; idx++) nums[idx] = numList.get(idx);
        char[] ops = new char[n - 1];
        for (int idx = 0; idx < n - 1; idx++) ops[idx] = opList.get(idx);

        // 2. 记忆化：BitSet 二维数组，每个区间存储结果位图（0~1000）
        BitSet[][] memo = new BitSet[n][n];
        BitSet allBits = dfs(0, n - 1, nums, ops, memo);

        // 3. 转换为 Set<Integer> 以保持接口一致
        Set<Integer> resultSet = new HashSet<>();
        for (int v = allBits.nextSetBit(0); v >= 0; v = allBits.nextSetBit(v + 1)) {
            resultSet.add(v);
        }
        return resultSet;
    }

    private BitSet dfs(int lo, int hi, int[] nums, char[] ops, BitSet[][] memo) {
        if (memo[lo][hi] != null) return memo[lo][hi];

        BitSet res = new BitSet(1001); // 范围 0~1000

        if (lo == hi) {
            int val = nums[lo];
            if (val >= 0 && val <= 1000) res.set(val);
        } else {
            for (int k = lo; k < hi; k++) {
                BitSet left = dfs(lo, k, nums, ops, memo);
                BitSet right = dfs(k + 1, hi, nums, ops, memo);
                char op = ops[k];

                // 遍历 left 中所有结果
                for (int l = left.nextSetBit(0); l >= 0; l = left.nextSetBit(l + 1)) {
                    // 遍历 right 中所有结果
                    for (int r = right.nextSetBit(0); r >= 0; r = right.nextSetBit(r + 1)) {
                        int val = (op == '+') ? l + r : l * r;
                        if (val >= 0 && val <= 1000) {
                            res.set(val);
                        }
                    }
                }
            }
        }

        memo[lo][hi] = res;
        return res;
    }

    private int computeCorrect(String s) {
        Stack<Integer> stack = new Stack<>();
        int curNum = 0;
        char lastOp = '+';
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                curNum = curNum * 10 + (c - '0');
            }
            if (!Character.isDigit(c) || i == s.length() - 1) {
                if (lastOp == '+') stack.push(curNum);
                else stack.push(stack.pop() * curNum);
                curNum = 0;
                lastOp = c;
            }
        }
        int ans = 0;
        while (!stack.isEmpty()) ans += stack.pop();
        return ans;
    }
}
--------------------------------------------------------------------------------
优化点说明
优化项原实现新实现收益记忆化键String 拼接 "lo,hi"直接使用二维数组 memo[lo][hi]消除字符串拼接和哈希查找结果容器HashSet<Integer>BitSet（位图）消除自动装箱，提高 add/contains 效率，内存更紧凑迭代方式for (int l : left)left.nextSetBit() 遍历只遍历实际存在的值，跳过空位范围剪枝仅 val <= 1000同时检查 val >= 0更严谨（虽然本题不会出现负数）解析优化字符逐个拼接再解析直接累加数字减少临时字符串创建
--------------------------------------------------------------------------------
性能提升
- 时间：通常可从 470ms 降至 ~150-200ms（取决于测试数据），时间复杂度仍为 O(状态数 × 结果集大小²)，但常数显著降低。
- 空间：BitSet 占用更少内存，且二维数组比 HashMap 轻量。
您可以直接替换原方法，保持其他部分不变。在 LeetCode 上提交应该能获得更快的执行时间。

Refer to
L227.P11.7.Basic Calculator II (Ref.L150,L224,L772)
L241.Different Ways to Add Parentheses (Ref.L95,L2019)
L312.Burst Balloons (Ref.L2019)
L2232.Minimize Result by Adding Parentheses to Expression (Ref.L2019)
