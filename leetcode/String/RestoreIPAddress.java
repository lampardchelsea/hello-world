import java.util.ArrayList;
import java.util.List;

/**
 * Refer to
 * https://leetcode.com/problems/restore-ip-addresses/#/description
 * Given a string containing only digits, restore it by returning all possible 
 * valid IP address combinations.
 * For example: Given "25525511135",
 * return ["255.255.11.135", "255.255.111.35"]. (Order does not matter)
 * 
 * Solution
 * https://discuss.leetcode.com/topic/3919/my-code-in-java
 * https://segmentfault.com/a/1190000003704558
 * 四分法
 * 复杂度
 * 时间 O(N^2) 空间 O(1)
 * 思路
 * 用三个点将字符串分成四段，验证每一段是否是有效的。我们只要控制这三个分割点就行了，注意约束条件有两个，
 * 一个是一段字符串不超过3个字母，另一个是控制好每段字符串最远结束的位置，比如第一个字符串最多延伸到
 * 倒数第4个字母。
 * 注意
 * 使用Integer.valueOf()时要确保所得到数不会超界。
 */
public class RestoreIPAddress {
    public List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<String>();
        int len = s.length();
        for(int i = 1; i < 4 && i < len - 2; i++) {
            for(int j = i + 1; j < i + 4 && j < len - 1; j++) {
                for(int k = j + 1; k < j + 4 && k < len; k++) {
                    String s1 = s.substring(0, i);
                    String s2 = s.substring(i, j);
                    String s3 = s.substring(j, k);
                    String s4 = s.substring(k, len);
                    if(isValid(s1) && isValid(s2) && isValid(s3) && isValid(s4)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(s1).append(".").append(s2).append(".").append(s3).append(".").append(s4);
                        result.add(sb.toString());
                    }
                }
            }
        }
        return result;
    }
    
    public boolean isValid(String s) {
        if(s.length() <= 3 && (s.charAt(0) != '0' && Integer.valueOf(s) <= 255 || s.equals("0"))) {
            return true;
        }
        return false;
    }
    
    public static void main(String[] args) {
    	
    }
}



// New try with DFS + backtracking
// Definition of valid IP address
// Refer to
// https://leetcode.com/problems/restore-ip-addresses/discuss/31165/What-is-the-definition-of-a-valid-IP-address
/**
 Definition of valid IP address:
1. The length of the ip without '.' should be equal to the length of s;
2. The digit order of ip should be same as the digit order of s;
3. Each part separated by the '.' should not start with '0' except only '0';
4. Each part separared by the '.' should not larger than 255;
*/
// Solution 1: DFS + backtracking + StringBuilder + index increasing from 0 to string length
// Refer to
// https://leetcode.com/problems/restore-ip-addresses/discuss/31095/Backtracking-solution-in-Java-easy-to-understand/29885
class Solution {
    public List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<String>();
        if(s == null || s.length() == 0 || s.length() > 12) {
            return result;
        }
        helper(result, new StringBuilder(), s, 0, 0);
        return result;
    }
    
    private void helper(List<String> result, StringBuilder sb, String s, int index, int dotCount) {
        if(dotCount > 3) {
            return;
        }
        if(index == s.length() && dotCount == 3) {
            result.add(sb.toString());
            return;
        }
        for(int i = index; i < s.length(); i++) {
            String token = s.substring(index, i + 1);
            if(isValid(token)) {
                int len = sb.length();
                sb.append(token);
                if(i + 1 != s.length()) {
                    sb.append(".");
                    helper(result, sb, s, i + 1, dotCount + 1);                   
                } else {
                    helper(result, sb, s, i + 1, dotCount);
                }
                sb.setLength(len);
            }
        }
    }
    
    /**
     Definition of valid IP address:
     1. The length of the ip without '.' should be equal to the length of s;
     2. The digit order of ip should be same as the digit order of s;
     3. Each part separated by the '.' should not start with '0' except only '0';
     4. Each part separared by the '.' should not larger than 255;
     5. Valid IP address should contain 3 dots (4 tokens)
    */
    private boolean isValid(String token) {
        if(token.length() > 3 || Integer.valueOf(token) > 255 
           || (token.length() > 1 && token.charAt(0) == '0')) {
            return false;
        }
        return true;
    }
}

// Style 2:
// Refer to
// https://massivealgorithms.blogspot.com/2014/06/leetcode-restore-ip-addresses-darrens.html
