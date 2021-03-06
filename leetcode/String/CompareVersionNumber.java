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
// Best Solution:
// Refer to
// https://leetcode.com/problems/compare-version-numbers/discuss/50774/Accepted-small-Java-solution.
class Solution {
    public int compareVersion(String version1, String version2) {
        String[] str1 = version1.split("\\.");
        String[] str2 = version2.split("\\.");
        int len = Math.max(str1.length, str2.length);
        for(int i = 0; i < len; i++) {
            Integer a = i < str1.length ? Integer.parseInt(str1[i]) : 0;
            Integer b = i < str2.length ? Integer.parseInt(str2[i]) : 0;
            int temp = a.compareTo(b);
            if(temp != 0) {
                return temp;
            }
        }
        return 0;
    }
}


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


// Right but complex solution
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
        		// Change based on TEST6, need to consider all homogeneous
        		// sections and additional sections in version string has
        		// more sections
        		for(int i = 1; i < minLen; i++) {
                	if(compareDecimalSection(s1[i], s2[i]) > 0) {
                		return 1;
                	} else if(compareDecimalSection(s1[i], s2[i]) < 0) {
                		return -1;
                	} else {
                		temp = 0;
                	}
                }
        		// Deal with additional sections in version string has 
        		// more sections
                if(temp == 0 && maxLen - minLen > 0) {
                	for(int j = 0; j < maxLen - minLen; j++) {
                		if(s1Len == maxLen) {
                			// version1 has more section
                			if(!onlyContainZero(s1[minLen + j])) {
                				// Change based on Test10
                				// If the additional section not only
                				// contain '0', return 1
                				return 1;
                			}
                		} else if(s2Len == maxLen) {
                			// version2 has more section
                			if(!onlyContainZero(s2[minLen + j])) {
                				// Change based on Test10
                				// If the additional section not only
                				// contain '0', return -1
                				return -1;
                			}
                		}
                	}
                }
                return 0;
        	}
        }
    }
	
	// Change based on TEST7, separately judge Integer section(1st section)
	// and Decimal section(if have, sections after 1st section)
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
	
	// Change based on TEST7, separately judge Integer section(1st section)
	// and Decimal section(if have, sections after 1st section)
	public int compareDecimalSection(String s1, String s2) {
		int s1ZeroAhead = 0;
		int s2ZeroAhead = 0;
		// Change based on TEST4, TEST8 and TEST9, skip all zeros ahead
		// and find remain substring in section
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

		String a = "";
		String b = "";
		// Change based on TEST5, in case of all decimal digits are 0
		// set m = 0 and n = 0 as default, correspond to 
		// s1ZeroAhead == s1.length() or s2ZeroAhead == s2.length()
		int m = 0;
		int n = 0;
		if(s1ZeroAhead < s1.length()) {
			a = s1.substring(s1ZeroAhead);
			m = Integer.valueOf(a);
		}
		if(s2ZeroAhead < s2.length()) {
			b = s2.substring(s2ZeroAhead);
			n = Integer.valueOf(b);
		}
		if(m < n) {
			return -1;
		} else if(m > n) {
			return 1;
		} else {
			return 0;
		}
	}
	
	// Change based on Test10
	public boolean onlyContainZero(String s) {
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) != '0') {
				return false;
			}
		}
		return true;
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
		// Also find issue on no condition to avoid all decimal number are 0,
		// when Integer.valueOf("") will throw NumberFormatException
//		String version1 = "1.1";
//		String version2 = "1.0";
		// Test 6: Find error on only consider one dot, it can have multiple
		// dots, so actually not a simple Integer & Decimal section
//		String version1 = "1.0";
//		String version2 = "1.0.1";
		// Test 7: Find Integer and Decimal part should check separately
//		String version1 = "01";
//		String version2 = "1.0";
		// Test 8: Find error in compareDecimalSection(), not reflect
		// find all ahead '0' until first number not '0', just calculate
		// numbers of '0'
//		String version1 = "1.1";
//		String version2 = "1.10";
		// Test 9: Return 1 is wrong as treat '1' and '01' as different
		// right answer should be 0, the decimal part are equal:
//		String version1 = "1.1";
//		String version2 = "1.01";
		// Test 10: Find section like "00000" should equal to "0", but
		// current logic judge as not equal based on wrong statement as
		// s1[minLen + j].equals("0") and s2[minLen + j].equals("0")
		// which used on additional sections in version has more sections
		// like here version2 has 153 sections, version1 has 151 sections,
		// the last two section contain "00000" which find this issue
		String version1 = "19.8.3.17.5.01.0.0.4.0.0.0.0.0.0.0.0.0.0.0.0.0.00.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.000000.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.000000";
		String version2 = "19.8.3.17.5.01.0.0.4.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0000.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.000000";
		CompareVersionNumbers compareVersionNumbers = new CompareVersionNumbers();
		int result = compareVersionNumbers.compareVersion(version1, version2);
		System.out.println(result);
	}
}

// Right Solution With Concise Solution (Same way as above solution)
// Refer to
// https://segmentfault.com/a/1190000003803133
/**
 * 迭代法
 * 复杂度
 * 时间 O(N) 空间 O(N)
 * 思路
 * 这题的难点在于几个corner case：
 * (1) 1.0和1是一个版本，意味即使长度不一样，也要检查后面是否都是0
 * (2) 1.15要大于1.5，因为前者是第15个子版本，而后者是第5个
 * 最简单的方法就是用split方法按照.分割，然后比对相应的每一个子串。
 * 注意
 * 因为split方法输入的是一个正则表达式所以不能直接用.，而是要用\.，而java的\要转义，所有要用\\.
*/
public class Solution {
	public int compareVersion(String version1, String version2) {
	    // Split string with '.'
        String[] s1 = version1.split("\\.");
        String[] s2 = version2.split("\\.");
        // Set up pointer to loop through both strings
        int i = 0;
        // First compare the common sections of both strings
        for(; i < s1.length && i < s2.length; i++) {
            int v1 = Integer.valueOf(s1[i]);
            int v2 = Integer.valueOf(s2[i]);
            if(v1 < v2) {
                return -1;
            }
            if(v1 > v2) {
                return 1;
            }
        }
        // If one string is longer, then find if any additional
        // section is 0 or not, if not 0 then higher version
        if(s2.length > s1.length) {
            for(; i < s2.length; i++) {
                int val = Integer.valueOf(s2[i]);
                if(val != 0) {
                    return -1;
                }    
            }
        } else {
            for(; i < s1.length; i++) {
                int val = Integer.valueOf(s1[i]);
                if(val != 0) {
                    return 1;
                }
            }
        }
        return 0;
    }
}


