/**
 * Refer to
 * https://leetcode.com/problems/expression-add-operators/description/ 
 * Given a string that contains only digits 0-9 and a target value, return all possibilities to 
   add binary operators (not unary) +, -, or * between the digits so they evaluate to the target value.

    Examples: 
    "123", 6 -> ["1+2+3", "1*2*3"] 
    "232", 8 -> ["2*3+2", "2+3*2"]
    "105", 5 -> ["1*0+5","10-5"]
    "00", 0 -> ["0+0", "0-0", "0*0"]
    "3456237490", 9191 -> []
 *
 * Solution
 * https://discuss.leetcode.com/topic/24523/java-standard-backtrace-ac-solutoin-short-and-clear?page=1
 * https://discuss.leetcode.com/topic/24523/java-standard-backtrace-ac-solutoin-short-and-clear/38
 * https://discuss.leetcode.com/topic/32068/java-simple-solution-beats-96-56
 * https://discuss.leetcode.com/topic/24523/java-standard-backtrace-ac-solutoin-short-and-clear/11?page=1
 * https://discuss.leetcode.com/topic/24523/java-standard-backtrace-ac-solutoin-short-and-clear/63?page=4
 * 
*/
class Solution {
    /**
      * Refer to
      * https://discuss.leetcode.com/topic/24523/java-standard-backtrace-ac-solutoin-short-and-clear?page=1
      * This problem has a lot of edge cases to be considered:
        overflow: we use a long type once it is larger than Integer.MAX_VALUE or minimum, we get over it.
        0 sequence: because we can't have numbers with multiple digits started with zero, we have to deal with it too.
        a little trick is that we should save the value that is to be multiplied in the next recursion.

    */
    public List<String> addOperators(String num, int target) {
        List<String> result = new ArrayList<String>();
        if(num == null || num.length() == 0) {
            return result;
        }
        StringBuilder sb = new StringBuilder();
        dfs(result, sb, num, 0, target, 0, 0);
        
        return result;
    }
    
    private void dfs(List<String> result, StringBuilder sb, String num, int pos, int target, long prev, long multi) {
        if(pos == num.length() && target == prev) {
            result.add(sb.toString());
            return;
        }
        for(int i = pos; i < num.length(); i++) {
            /**
             * Refer to
             * https://discuss.leetcode.com/topic/24523/java-standard-backtrace-ac-solutoin-short-and-clear/63?page=4
             * explain this condition:
                if(i != pos && num.charAt(pos) == '0') break;
                I think this condition it to exclude numbers with leading zeros

                Valid answers are:

                "105", 5 -> ["1*0+5","10-5"]
                "00", 0 -> ["0+0", "0-0", "0*0"]
                but with leading zeros we will have

                "105", 5 -> ["1*0+5","10-5", "1*05"]
                "00", 0 -> ["0+0", "0-0", "0*0", "00"]
            */
            if(num.charAt(pos) == '0' && i != pos) {
                break;
            }
            long curr = Long.parseLong(num.substring(pos, i + 1));
            /**
             * Refer to
             * https://discuss.leetcode.com/topic/32068/java-simple-solution-beats-96-56
             * What's different is, I use backtracking with StringBuilder instead of directly String addition.
             * This increase speed by 20%.
            */
            int len = sb.length();
            if(pos == 0) {
                dfs(result, sb.append(curr), num, i + 1, target, curr, curr);
                sb.setLength(len);
            } else {
                dfs(result, sb.append("+").append(curr), num, i + 1, target, prev + curr, curr);
                sb.setLength(len);
                dfs(result, sb.append("-").append(curr), num, i + 1, target, prev - curr, -curr);
                sb.setLength(len);
                /**
                 * Refer to
                 * https://discuss.leetcode.com/topic/24523/java-standard-backtrace-ac-solutoin-short-and-clear/12?page=1
                 * for example, if you have a sequence of 12345 and you have proceeded to 1 + 2 + 3, 
                   now your eval is 6 right? If you want to add a * between 3 and 4, you would take 3 
                   as the digit to be multiplied, so you want to take it out from the existing eval. 
                   You have 1 + 2 + 3 * 4 and the eval now is (1 + 2 + 3) - 3 + (3 * 4). Hope this could help : )
                */
                dfs(result, sb.append("*").append(curr), num, i + 1, target, prev - multi + multi * curr, multi * curr);
                sb.setLength(len);
            }
        }
    }
} 
