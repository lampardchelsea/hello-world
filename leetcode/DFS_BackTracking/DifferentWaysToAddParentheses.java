/**
 * Refer to
 * https://leetcode.com/problems/different-ways-to-add-parentheses/description/ 
 * Given a string of numbers and operators, return all possible results from computing all the 
   different possible ways to group numbers and operators. The valid operators are +, - and *.

    Example 1
    Input: "2-1-1".

    ((2-1)-1) = 0
    (2-(1-1)) = 2
    Output: [0, 2]


    Example 2
    Input: "2*3-4*5"

    (2*(3-(4*5))) = -34
    ((2*3)-(4*5)) = -14
    ((2*(3-4))*5) = -10
    (2*((3-4)*5)) = -10
    (((2*3)-4)*5) = 10
    Output: [-34, -14, -10, -10, 10]
 *
 * Solution
 * https://discuss.leetcode.com/topic/19901/a-recursive-java-solution-284-ms?page=1
 * https://discuss.leetcode.com/topic/19906/c-4ms-recursive-dp-solution-with-brief-explanation
 * https://discuss.leetcode.com/topic/25490/share-a-clean-and-short-java-solution/2
 * https://www.youtube.com/watch?v=gxYV8eZY0eQ
 * https://en.wikipedia.org/wiki/Cartesian_product
*/
// Solution 1: DFS (Divide and Conquer)
class Solution {
    public List<Integer> diffWaysToCompute(String input) {
        List<Integer> result = new ArrayList<Integer>();
        if(input == null || input.length() == 0) {
            return result;
        }
        for(int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if(c == '+' || c == '*' || c == '-') {
                String part1 = input.substring(0, i);
                String part2 = input.substring(i + 1);
                List<Integer> part1Result = diffWaysToCompute(part1);
                List<Integer> part2Result = diffWaysToCompute(part2);
                // Use basic rule as 'Cartesian Product'
                // Refer to
                // https://www.youtube.com/watch?v=gxYV8eZY0eQ
                // https://en.wikipedia.org/wiki/Cartesian_product
                for(int m : part1Result) {
                    for(int n : part2Result) {
                        int temp = 0;
                        if(c == '+') {
                            temp = m + n;
                        } else if(c == '-') {
                            temp = m - n;
                        } else if(c == '*') {
                            temp = m * n;
                        }
                        result.add(temp);
                    }
                }
            }
        }
        // Note: Critical terminate condition as store only numeric number
        // present in current level 'result' (recursively start from deepest
        // level as single number) for upper level compute
        if(result.size() == 0) {
            result.add(Integer.valueOf(input));
        }
        return result;
    }
}


// New try with DFS, similar way as 95. Unique Binary Search Tree II
// Refer to
// https://leetcode.com/problems/different-ways-to-add-parentheses/discuss/66333/Java-recursive-(9ms)-and-dp-(4ms)-solution
/**
 I think it's more efficient to pre-parse the string because String.substring() is costly. 
 I store the parsed string in a list, for example, if the string is 1+2+3+4, then the list will contain:

"1", "+", "2", "+", "3", "+", "4"
Personally I feel this is also more convenient because all integers occurs at even indices (0, 2, 4, 6) 
and all operators are at odd indices (1, 3, 5).

Then the problem is very similar to "Unique Binary Search Trees II". For each operator in the list, 
we compute all possible results for entries to the left of that operator, which is List<Integer> left, 
and also all possible results for entries to the right of that operator, namely List<Integer> right, 
and combine the results. It can be achieved by recursion or more efficiently by dp.
*/
class Solution {
    public List<Integer> diffWaysToCompute(String input) {
        List<Integer> result = new ArrayList<Integer>();
        if(input == null || input.length() == 0) {
            return result;    
        }
        // Split string into numbers and operations
        // be careful the numbers could be continuous digits
        List<String> tokens = new ArrayList<String>();
        for(int i = 0; i < input.length(); i++) {
            String token = "";
            while(i < input.length() && Character.isDigit(input.charAt(i))) {
                token += input.charAt(i);
                i++;
            }
            // Add number section
            tokens.add(token);
            // Add operate (and it could not be the last character in string)
            if(i < input.length()) {
            	tokens.add(String.valueOf(input.charAt(i)));
            }         
        }
        result = helper(tokens, 0, tokens.size() - 1);
        return result;
    }
    
    private List<Integer> helper(List<String> tokens, int lo, int hi) {
        List<Integer> result = new ArrayList<Integer>();
        // Base case: only one number left when lo = hi
        if(lo == hi) {
            result.add(Integer.valueOf(tokens.get(lo)));
            return result;
        }
        // Same process as 95.Unique Binary Search Tree II
        // Scan all combinations between left and right sub-list divide by operate
        for(int i = lo; i <= hi; i++) {
            if(i % 2 == 1) {
                String ops = tokens.get(i);
                List<Integer> left = helper(tokens, lo, i - 1);
                List<Integer> right = helper(tokens, i + 1, hi);
                for(int l : left) {
                    for(int r : right) {
                        if(ops.equals("+")) {
                            result.add(l + r);
                        }
                        if(ops.equals("*")) {
                            result.add(l * r);
                        }
                        if(ops.equals("-")) {
                            result.add(l - r);
                        }
                    }
                }
            }
        }
        return result;
    }
}

// Add memoization
// Refer to
// https://leetcode.com/problems/different-ways-to-add-parentheses/discuss/66395/Java-recursive-solution-with-memorization
class Solution {
    public List<Integer> diffWaysToCompute(String input) {
        List<Integer> result = new ArrayList<Integer>();
        if(input == null || input.length() == 0) {
            return result;    
        }
        // Split string into numbers and operations
        // be careful the numbers could be continuous digits
        List<String> tokens = new ArrayList<String>();
        for(int i = 0; i < input.length(); i++) {
            String token = "";
            while(i < input.length() && Character.isDigit(input.charAt(i))) {
                token += input.charAt(i);
                i++;
            }
            // Add number section
            tokens.add(token);
            // Add operate (and it could not be the last character in string)
            if(i < input.length()) {
            	tokens.add(String.valueOf(input.charAt(i)));
            }         
        }
        // memo stores all possible results from the i-th 
        // integer to the j-th integer (inclusive) in the list.
        Map<String, List<Integer>> memo = new HashMap<String, List<Integer>>();
        result = helper(tokens, 0, tokens.size() - 1, memo);
        return result;
    }
    
    private List<Integer> helper(List<String> tokens, int lo, int hi, Map<String, List<Integer>> memo) {
        List<Integer> result = new ArrayList<Integer>();
        // If already stored result, return quickly
        String str = lo + "_" + hi;
        if(memo.containsKey(str)) {
            return memo.get(str);
        }
        // Base case: only one number left when lo = hi
        if(lo == hi) {
            result.add(Integer.valueOf(tokens.get(lo)));
            return result;
        }
        // Same process as 95.Unique Binary Search Tree II
        // Scan all combinations between left and right sub-list divide by operate
        for(int i = lo; i <= hi; i++) {
            if(i % 2 == 1) {
                String ops = tokens.get(i);
                List<Integer> left = helper(tokens, lo, i - 1, memo);
                List<Integer> right = helper(tokens, i + 1, hi, memo);
                for(int l : left) {
                    for(int r : right) {
                        if(ops.equals("+")) {
                            result.add(l + r);
                        }
                        if(ops.equals("*")) {
                            result.add(l * r);
                        }
                        if(ops.equals("-")) {
                            result.add(l - r);
                        }
                    }
                }
            }
        }
        // map.put() automatically contains update operation
        memo.put(str, result);
        return result;
    }
}
