/**
 * Compare two version numbers version1 and version2.
 * If version1 > version2 return 1, if version1 < version2 return -1, otherwise return 0.
 * You may assume that the version strings are non-empty and contain only digits and the . character.
 * The . character does not represent a decimal point and is used to separate number sequences.
 * For instance, 2.5 is not "two and a half" or "half way to version three", it is the fifth 
 * second-level revision of the second first-level revision.
 * Here is an example of version numbers ordering:
 * 0.1 < 1.1 < 1.2 < 13.37
*/
// Wrong Solution:
public class CompareVersionNumbers {
	public int compareVersion(String version1, String version2) {
        String[] s1 = version1.split("\\.");
        String[] s2 = version2.split("\\.");
        int s1Len = s1.length;
        int s2Len = s2.length;
        int minLen = s1Len < s2Len ? s1Len : s2Len;
        int maxLen = s1Len < s2Len ? s2Len : s1Len;
        int temp = 0;
        
        if(minLen <= 1) {
        	if(compareIntegerSection(s1[0], s2[0]) > 0) {
        		return 1;
        	} else if(compareIntegerSection(s1[0], s2[0]) < 0) {
        		return -1;
        	} else {
        		if(maxLen == 1) {
        			// Both version1 and version2 only contain integer part
        			return compareIntegerSection(s1[0], s2[0]);
        		} else {
        			if(s1Len == maxLen) {
        				// version1 has decimal part
        				for(int i = 1; i < s1Len; i++) {
        					if(!s1[i].equals("0")) {
        						return 1;
        					}
        				}
        			} else if(s2Len == maxLen) {
        				// version2 has decimal part
        				for(int i = 1; i < s2Len; i++) {
        					if(!s2[i].equals("0")) {
        						return -1;
        					}
        				}
        			}
        			return 0;
        		}
        	}
        } else {
        	if(compareIntegerSection(s1[0], s2[0]) > 0) {
        		return 1;
        	} else if(compareIntegerSection(s1[0], s2[0]) < 0) {
        		return -1;
        	} else {
        		for(int i = 1; i < minLen; i++) {
                	if(compareDecimalSection(s1[i], s2[i]) > 0) {
                		return 1;
                	} else if(compareDecimalSection(s1[i], s2[i]) < 0) {
                		return -1;
                	} else {
                		temp = 0;
                	}
                }
                if(temp == 0 && maxLen - minLen > 0) {
                	for(int j = 0; j < maxLen - minLen; j++) {
                		if(s1Len == maxLen) {
                			// version1 has more section
                			if(!s1[minLen + j].equals("0")) {
                				return 1;
                			}
                		} else if(s2Len == maxLen) {
                			// version2 has more section
                			if(!s2[minLen + j].equals("0")) {
                				return -1;
                			}
                		}
                	}
                }
                return 0;
        	}
        }
        
//        for(int i = 0; i < minLen; i++) {
//        	if(compareDecimalSection(s1[i], s2[i]) > 0) {
//        		return 1;
//        	} else if(compareDecimalSection(s1[i], s2[i]) < 0) {
//        		return -1;
//        	} else {
//        		temp = 0;
//        	}
//        }
//        if(temp == 0 && maxLen - minLen > 0) {
//        	for(int j = 0; j < maxLen - minLen; j++) {
//        		if(s1Len == maxLen) {
//        			// version1 has more section
//        			if(!s1[minLen + j].equals("0")) {
//        				return 1;
//        			}
//        		} else if(s2Len == maxLen) {
//        			// version2 has more section
//        			if(!s2[minLen + j].equals("0")) {
//        				return -1;
//        			}
//        		}
//        	}
//        }
//        return 0;
    }
	
	public int compareIntegerSection(String s1, String s2) {
		int a = Integer.valueOf(s1);
		int b = Integer.valueOf(s2);
		if(a == b) {
			return 0;
		} else if( a > b) {
			return 1;
		} else {
			return -1;
		}
	}

	public int compareDecimalSection(String s1, String s2) {
		int s1ZeroAhead = 0;
		int s2ZeroAhead = 0;
		for(int i = 0; i < s1.length(); i++) {
			if(s1.charAt(i) != '0') {
				break;
			} else {
				s1ZeroAhead++;
			}
		}
		for(int j = 0; j < s2.length(); j++) {
			if(s2.charAt(j) != '0') {
				break;
			} else {
				s2ZeroAhead++;
			}
		}
		if(s1ZeroAhead > s2ZeroAhead) {
			return -1;
		} else if(s1ZeroAhead < s2ZeroAhead) {
			return 1;
		} else {
			return compareIntegerSection(s1, s2);
		}
	}
	
	
	public static void main(String[] args) {
		// Test 1: If not consider no dot situation or array length 
		// not equal situation, it will be a problem
//		String version1 = "01";
//		String version2 = "1";
		// Test 2: Return 1 is wrong as not consider s1[1] = 0
		// equal to nothing, even fix this issue with additional
		// check on s1[1] == 0 in two branches, still fail on
		// case 3
//		String version1 = "1.0";
//		String version2 = "1";
		// Test 3: Return 0 is wrong as treat '01' same as '1'
		// because of compare() method using Integer.valueOf()
//		String version1 = "1.01";
//		String version2 = "1.1";
		// Test 4: Test decimal number without zero ahead
//		String version1 = "1.15";
//		String version2 = "1.5";
		// Test 5: Find while loop increase index cause ArrayIndexOutOfBound
//		String version1 = "1.1";
//		String version2 = "1.0";
		// Test 6: Find error on only consider one dot
//		String version1 = "1.0";
//		String version2 = "1.0.1";
		// Test 7: 
//		String version1 = "01";
//		String version2 = "1.0";
		// Test 8: Find error in compareDecimalSection(), not reflect
		// find all ahead '0' until first number not '0', just calculate
		// numbers of '0'
//		String version1 = "1.1";
//		String version2 = "1.10";
		// Test 9: Return 1 is wrong as treat '1' and '01' as different
		// right answer should be 0, the decimal part are equal
		String version1 = "1.1";
		String version2 = "1.01";
		CompareVersionNumbers compareVersionNumbers = new CompareVersionNumbers();
		int result = compareVersionNumbers.compareVersion(version1, version2);
		System.out.println(result);
	}
}
