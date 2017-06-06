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
