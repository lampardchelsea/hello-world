/**
 * Refer to
 * https://leetcode.com/problems/validate-ip-address/#/description
 *  Write a function to check whether an input string is a valid IPv4 address or IPv6 address or neither.
 *  IPv4 addresses are canonically represented in dot-decimal notation, which consists of four decimal numbers, 
 *  each ranging from 0 to 255, separated by dots ("."), e.g.,172.16.254.1;
 *  Besides, leading zeros in the IPv4 is invalid. For example, the address 172.16.254.01 is invalid.
 *  IPv6 addresses are represented as eight groups of four hexadecimal digits, each group representing 16 bits. 
 *  The groups are separated by colons (":"). For example, the address 2001:0db8:85a3:0000:0000:8a2e:0370:7334 
 *  is a valid one. Also, we could omit some leading zeros among four hexadecimal digits and some low-case 
 *  characters in the address to upper-case ones, so 2001:db8:85a3:0:0:8A2E:0370:7334 is also a valid IPv6 
 *  address(Omit leading zeros and using upper cases).
 *  However, we don't replace a consecutive group of zero value with a single empty group using two consecutive 
 *  colons (::) to pursue simplicity. For example, 2001:0db8:85a3::8A2E:0370:7334 is an invalid IPv6 address.
 *  Besides, extra leading zeros in the IPv6 is also invalid. 
 *  For example, the address 02001:0db8:85a3:0000:0000:8a2e:0370:7334 is invalid.
 *  Note: You may assume there is no extra space or special characters in the input string.

	Example 1:	
	Input: "172.16.254.1"
	Output: "IPv4"
	Explanation: This is a valid IPv4 address, return "IPv4".
	
	Example 2:
	Input: "2001:0db8:85a3:0:0:8A2E:0370:7334"
	Output: "IPv6"
	Explanation: This is a valid IPv6 address, return "IPv6".
	
	Example 3:
	Input: "256.256.256.256"
	Output: "Neither"
	Explanation: This is neither a IPv4 address nor a IPv6 address.
 *
 * Solution
 * https://discuss.leetcode.com/topic/71444/java-simple-solution
 */
public class ValidateIPAddress {
    public String validIPAddress(String IP) {
        if(isValidIPv4(IP)) {
            return "IPv4";
        } else if(isValidIPv6(IP)) {
            return "IPv6";
        } else {
            return "Neither";
        }
    }
    
    // IPv4 addresses are canonically represented in dot-decimal notation, 
    // which consists of four decimal numbers, each ranging from 0 to 255, 
    // separated by dots ("."), e.g.,172.16.254.1;
    // Besides, leading zeros in the IPv4 is invalid. 
    // For example, the address 172.16.254.01 is invalid. 
    public boolean isValidIPv4(String ip) {
        if(ip.length() < 7 || ip.charAt(0) == '.' || ip.charAt(ip.length() - 1) == '.') {
            return false;
        }
        // Refer to
        // https://stackoverflow.com/questions/7935858/the-split-method-in-java-does-not-work-on-a-dot
        // java.lang.String.split splits on regular expressions, 
        // and . in a regular expression means "any character". 
        // Try temp.split("\\.")
        String[] tokens = ip.split("\\.");
        if(tokens.length != 4) {
            return false;
        }
        for(String token : tokens) {
            if(!isValidIPv4Token(token)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isValidIPv4Token(String token) {
        if(token.startsWith("0") && token.length() > 1) {
            return false;
        }
        // Try with "1e1.4.5.6" as example
        try {
            int tokenVal = Integer.parseInt(token);
            if(tokenVal < 0 || tokenVal > 255) {
                return false;
            }
            // Try with "0.0.0.-0" as example, because
            // Integer.parseInt("-0") will also return
            // '-0', this is not what we expected
            if(tokenVal == 0 && token.charAt(0) != '0') {
                return false;
            }
        } catch(NumberFormatException e) {
        	return false;
        }
        return true;
    }
    
    
    // IPv6 addresses are represented as eight groups of four hexadecimal digits, 
    // each group representing 16 bits. The groups are separated by colons (":"). 
    // For example, the address 2001:0db8:85a3:0000:0000:8a2e:0370:7334 is a valid 
    // one. Also, we could omit some leading zeros among four hexadecimal digits 
    // and some low-case characters in the address to upper-case ones, so 
    // 2001:db8:85a3:0:0:8A2E:0370:7334 is also a valid IPv6 address
    // (Omit leading zeros and using upper cases).
    // However, we don't replace a consecutive group of zero value with a single 
    // empty group using two consecutive colons (::) to pursue simplicity. 
    // For example, 2001:0db8:85a3::8A2E:0370:7334 is an invalid IPv6 address.
    // Besides, extra leading zeros in the IPv6 is also invalid. For example, 
    // the address 02001:0db8:85a3:0000:0000:8a2e:0370:7334 is invalid. 
    public boolean isValidIPv6(String ip) {
        if(ip.length() < 15 || ip.charAt(0) == ':' || ip.charAt(ip.length() - 1) == ':') {
            return false;
        }
        String[] tokens = ip.split(":");
        if(tokens.length != 8) {
            return false;
        }
        for(String token : tokens) {
            if(!isValidIPv6Token(token)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isValidIPv6Token(String token) {
        if(token.length() > 4 || token.length() == 0) {
            return false;
        }
        char[] chars = token.toCharArray();
        for(char c : chars) {
        	// Refer to
        	// https://en.wikipedia.org/wiki/Hexadecimal
            boolean isDigit = c >= 48 && c <= 57; // 0 - 9
            boolean isUpperCaseAF = c >= 65 && c <= 70; // A - F
            boolean isLowerCaseAF = c >= 97 && c <= 102; // a - f
            if(!(isDigit || isUpperCaseAF || isLowerCaseAF)) {
                return false;
            }
        }
        return true;
    } 
    
    public static void main(String[] args) {
    	ValidateIPAddress v = new ValidateIPAddress();
    	//String IP = "172.16.254.1";
    	//String IP = "1e1.4.5.6";
    	String IP = "0.0.0.-0";
    	String result = v.validIPAddress(IP);
    	System.out.println(result);
    }
}

