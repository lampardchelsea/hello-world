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
        
        if(s1.length == 1 && s2.length == 1) {
        	if(compareIntegerPart(s1[0], s2[0]) > 0) {
            	return 1;
            } else if(compareIntegerPart(s1[0], s2[0]) < 0) {
            	return -1;
            } else {
            	return 0;
            }
        } else if(s1.length == 2 && s2.length == 2) {
        	if(compareIntegerPart(s1[0], s2[0]) > 0) {
            	return 1;
            } else if(compareIntegerPart(s1[0], s2[0]) < 0) {
            	return -1;
            } else {
            	if(compareDecimalPart(s1[1], s2[1]) == 0) {
            		return 0;
            	} else if(compareDecimalPart(s1[1], s2[1]) > 0) {
            		return 1;
            	} else {
            		return -1;
            	}
            }
        } else if(s1.length == 1 && s2.length == 2) {
        	if(compareIntegerPart(s1[0], s2[0]) > 0) {
            	return 1;
            } else if(compareIntegerPart(s1[0], s2[0]) == 0 && Integer.valueOf(s2[1]) == 0) {
            	return 0;
            }
        	return -1;
        } else {
        	if(compareIntegerPart(s1[0], s2[0]) < 0) {
            	return -1;
            } else if(compareIntegerPart(s1[0], s2[0]) == 0 && Integer.valueOf(s1[1]) == 0) {
            	return 0;
            }
        	return 1;
        }
    }
	
	public int compareIntegerPart(String s1, String s2) {
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
	
	public int compareDecimalPart(String s1, String s2) {
		int s1ZeroAhead = 0;
		int s2ZeroAhead = 0;
		for(int i = 0; i < s1.length(); i++) {
			if(s1.charAt(i) == '0') {
				s1ZeroAhead++;
			}
		}
		for(int j = 0; j < s2.length(); j++) {
			if(s2.charAt(j) == '0') {
				s2ZeroAhead++;
			}
		}
//		while(s1.charAt(s1ZeroAhead) == '0') {
//			s1ZeroAhead++;
//		}
//		while(s2.charAt(s2ZeroAhead) == '0') {
//			s2ZeroAhead++;
//		}
		if(s1ZeroAhead > s2ZeroAhead) {
			return -1;
		} else if(s1ZeroAhead < s2ZeroAhead) {
			return 1;
		} else {
			return compareIntegerPart(s1, s2);
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
		String version1 = "1";
		String version2 = "1.0.1";
		CompareVersionNumbers compareVersionNumbers = new CompareVersionNumbers();
		int result = compareVersionNumbers.compareVersion(version1, version2);
		System.out.println(result);
	}
}
