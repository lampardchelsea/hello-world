/**
 * The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows 
 * like this: (you may want to display this pattern in a fixed font for better legibility)
    P   A   H   N
    A P L S I I G
    Y   I   R
 * And then read line by line: "PAHNAPLSIIGYIR"
 * Write the code that will take a string and make this conversion given a number of rows:
 * string convert(string text, int nRows);
 * convert("PAYPALISHIRING", 3) should return "PAHNAPLSIIGYIR".
*/
public class Solution {
    public String convert(String s, int numRows) {
        char[] c = s.toCharArray();
    	int length = c.length;
    	StringBuffer[] sb = new StringBuffer[numRows];
    	for(int i = 0; i < sb.length; i++) {
    		sb[i] = new StringBuffer();
    	}
    	int i = 0;
    	while(i < length) {
        	// Vertically down
        	for(int idx = 0; idx < numRows && i < length; idx++) {
        		sb[idx].append(c[i++]);
        	}
        	// Obliquely up (Minus the first and last row, last is obliquely part)
        	for(int idx = numRows - 2; idx >= 1 && i < length; idx--) {
        		sb[idx].append(c[i++]);
        	}
    	}
    	for(int j = 1; j < sb.length; j++) {
    		sb[0].append(sb[j]);
    	}
    	return sb[0].toString();
    }
}
