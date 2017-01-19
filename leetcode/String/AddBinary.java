// Solution 1:
/**
 * Refer to
 * https://discuss.leetcode.com/topic/13698/short-ac-solution-in-java-with-explanation
 */
public class AddBinary {
	public String addBinary(String a, String b) {
		int[] aRev = reverseString(a);
		int[] bRev = reverseString(b);
        int aLen = aRev.length;
        int bLen = bRev.length;
        int minLen = aLen < bLen ? aLen : bLen;
        int maxLen = aLen < bLen ? bLen : aLen;
        StringBuilder sb = new StringBuilder();
        boolean carry_over = false;
        int i = 0;
        for(; i < minLen; i++) {
        	int digit = 0;
            if(!carry_over) {
            	digit = aRev[i] + bRev[i];
            } else {
            	digit = aRev[i] + bRev[i] + 1;
            }
            // In case of 1 + 1 + 1(carryover) = 3 > 2
            if(digit >= 2) {
            	carry_over = true;
            	digit = digit % 2;
            } else {
            	carry_over = false;
            }
            sb.append(digit);
            // The tricky part is only when minLen == maxLen,
            // we need to handle this case, otherwise we will
            // go into next for loop (either aLen > bLen or
            // bLen > aLen both cause maxLen - minLen > 0 and 
            // move to go on into next loop with same index i)
            // , when index i move to last position and carryover
            // exist, then we append one more '1'(e.g 1 + 1 = 10
            // or 1 + 1 + 1 = 11)
            if(minLen == maxLen && i == minLen - 1 && carry_over) {
            	sb.append(1);
            }
        }
        
        for(; i < maxLen; i++) {
        	int digit = 0;
        	if(aLen == maxLen) {
        		if(!carry_over) {
        			digit = aRev[i];
        		} else {
        			digit = aRev[i] + 1;
        		}
        	} else if(bLen == maxLen) {
        		if(!carry_over) {
        			digit = bRev[i];
        		} else {
        			digit = bRev[i] + 1;
        		}
        	}
            if(digit >= 2) {
            	carry_over = true;
            	digit = digit % 2;
            } else {
            	carry_over = false;
            }
        	sb.append(digit);
            if(i == maxLen - 1 && carry_over) {
            	sb.append(1);
            } 
        }
        // Reverse final result back to original order
        return sb.reverse().toString();
    }
	
	// Reverse string into integer array, which least significant digit
	// locate at index 0 of array, used for simulate adding process from
	// least significant digit to most significant digit, it require
	// reverse back to original order after calculation
	public int[] reverseString(String s) {
		char[] chars = s.toCharArray();
		int[] result = new int[chars.length];
		for(int m = 0; m < chars.length; m++) {
			result[m] = Character.getNumericValue(chars[m]);
		}
		int i = 0;
		int j = s.length() - 1;
		while(i < j) {
			int temp = result[i];
			result[i] = result[j];
			result[j] = temp;
			i++;
			j--;
		}
		return result;
	}
	
	public static void main(String[] args) {
		AddBinary addBinary = new AddBinary();
		String a = "11010";
		String b = "11";
//		String a = "1111";
//		String b = "1111";
		String result = addBinary.addBinary(a, b);
		System.out.println(result);
	}
}

// Solution 2:
/**
 * https://segmentfault.com/a/1190000003707317
*/
public class Solution {
     public String addBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();
        char[] aChars = a.toCharArray();
        char[] bChars = b.toCharArray();
        int i = aChars.length - 1;
        int j = bChars.length - 1;
        int carryover = 0;
	// Elegant design as using '||' to handle condition on different length loop
	// also use trinary expression to set position without value (either i or j
	// run out of length) as 0 
        while(i >= 0 || j >= 0) {
            int m = i >= 0 ? aChars[i] - '0' : 0;
            int n = j >= 0 ? bChars[j] - '0' : 0;
            int digit = m + n + carryover;
            if(digit >= 2) {
                carryover = 1;
                digit = digit % 2;
            } else {
                carryover = 0;
            }
	    // Also, use insert() to auto add digit onto the first position
            sb.insert(0, digit);
            i--;
            j--;
        }
        if(carryover != 0) {
            sb.insert(0, 1);
        }
        return sb.toString();
    }
}
