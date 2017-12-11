/**
 * Refer to
 * https://leetcode.com/problems/excel-sheet-column-number/description/
 * Related to question Excel Sheet Column Title

    Given a column title as appear in an Excel sheet, return its corresponding column number.

    For example:

        A -> 1
        B -> 2
        C -> 3
        ...
        Z -> 26
        AA -> 27
        AB -> 28 
 * 
 * 
 * Solution
 * https://discuss.leetcode.com/topic/36499/my-2ms-java-solution
*/
public class ExcelSheetColumnNumber {
    // Style 1
	  public int titleToNumber(String s) {
		    if(s == null || s.length() == 0) {
			      return 0;
		    }
		    char[] chars = s.toCharArray();
		    int result = 0;
		    int digit = 1;
		    for(int i = chars.length - 1; i >= 0; i--) {
			      char c = chars[i];
			      int diff = c - 'A' + 1;
			      result += digit * diff;
			      digit *= 26;
		    }
		    return result;
	  }
	
	// Style 1 promotion
	public int titleToNumber3(String s) {
		  if(s == null || s.length() == 0) {
			    return 0;
		  }
		  int result = 0;
		  int digit = 1;
		  for(int i = s.length() - 1; i >= 0; i--) {
			    result += digit * (s.charAt(i) - 'A' + 1);
			    digit *= 26;
		  }
		  return result;
	}
	
	// Style 2
	// Refer to
	// https://discuss.leetcode.com/topic/36499/my-2ms-java-solutione
	// Compare to Style 1, which the order to compute digit start from
	// lowest digit, here the Style 2 start from highest digit, so the
	// order to compute 'result * 26' and 'result += digit * diff' is
	// also reversed, and even more start from highest digit will save
	// space by get rid of 'digit'
	public int titleToNumber2(String s) {
		  if(s == null || s.length() == 0) {
			    return 0;
		  }
		  int result = 0;
	    for(int i = 0 ; i < s.length(); i++) {
	    	  result *= 26;
	    	  result += (s.charAt(i) - 'A' + 1); 
	    }
	    return result;
	}
  
  public static void main(String[] args) {
		  ExcelSheetColumnNumber e = new ExcelSheetColumnNumber();
		  String s = "ABC";
	    int result = e.titleToNumber2(s);
	    System.out.println(result);
	}

}
