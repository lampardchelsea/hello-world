/**
 * The count-and-say sequence is the sequence of integers beginning as follows:
 * 1, 11, 21, 1211, 111221, ...
   1 is read off as "one 1" or 11.
   11 is read off as "two 1s" or 21.
   21 is read off as "one 2, then one 1" or 1211.
 * Given an integer n, generate the nth sequence.
 * Note: The sequence of integers will be represented as a string.
*/

// Wrong Solution Based On Ambiguous Description: Try to use two pointers
// This will always missing the last section, as loop will terminate when find 1st character
// in last section not equal to its previous section
// e.g Here the result is 111023, because the while loop detect last section start with '3',
// not equal to its previous section character '1'
public class CountAndSay {
	public String countAndSay(int n) {
		String result = "";
		if(n == 0) {
			return result + 0;
		}
		
		String s = transferIntToStr(n);
		char[] chars = s.toCharArray();
		int length = chars.length;
		
		if(length == 1) {
			return result + n + "1";
		}
		
		int i = 0;
		int count = 1;
		StringBuilder sb = new StringBuilder();
		while(i + count < length) {
			if(chars[i] == chars[i + count]) {
				count++;
			} else {
				sb.append(count).append(chars[i]);
				i += count;
				count = 1;
			}
		}
		result = sb.toString();
		
//		int i = 0;
//		int j = 1;
//		StringBuilder sb = new StringBuilder();
//		while(i < length && j < length) {
//			if(chars[j] == chars[i]) {
//				j++;
//			} else {
//				sb.append(j - i).append(chars[i]);
//				i = j;
//				//j = 1;
//			}
//		}
//		result = sb.toString();
		return result;
	}
	
	public String transferIntToStr(int n) {
		String s = "";
		while(n > 0) {
			s = n % 10 + s;
		    n = n / 10;
		}	
		return s;
	}
	
	public static void main(String[] args) {
		CountAndSay countAndSay = new CountAndSay();
		String result = countAndSay.countAndSay(1033111);
		System.out.println(result);
	}
}



// Right Solution Based On Ambiguous Description
// Refer to
// https://discuss.leetcode.com/topic/1296/please-change-the-misleading-description
// The description here is totally misleading to wrong solution,
// e.g input as '1', as description below should generate "11", but leetcode
// expect "1" based on "Given an integer n, generate the nth sequence."

// Wrong Description:
// The count-and-say sequence is the sequence of integers beginning as follows:
// 1, 11, 21, 1211, 111221, ...
// 1 is read off as "one 1" or 11.
// 11 is read off as "two 1s" or 21.
// 21 is read off as "one 2, then one 1" or 1211.

// This solution refer to
// https://discuss.leetcode.com/topic/14543/straightforward-java-solution
// http://www.cnblogs.com/springfor/p/3889221.html
public class CountAndSay {
	public String countAndSay(int n) {
		String result = "";
		if(n == 0) {
			return result + 0;
		}
		
		String s = transferIntToStr(n);
		char[] chars = s.toCharArray();
		int length = chars.length;
		
		if(length == 1) {
			return result + n + "1";
		}
		
		// Same way use only 1 pointer
//		int i = 0;
//		int count = 1;
//		StringBuilder sb = new StringBuilder();
//		char curr = chars[0];
//		while(i + count < length) {
//			curr = chars[i];
//			if(chars[i] == chars[i + count]) {
//				count++;
//			} else {
//				sb.append(count).append(chars[i]);
//				i += count;
//				count = 1;
//			}
//		}
//		sb.append(count).append(curr);
//		result = sb.toString();
		
		int i = 0;
		int j = 1;
		StringBuilder sb = new StringBuilder();
		// Used for record last section character
		char curr = chars[0];
		while(i < length && j < length) {
			curr = chars[i];
			if(chars[j] == chars[i]) {
				j++;
			} else {
				sb.append(j - i).append(chars[i]);
				i = j;
				
			}
		}
		// Tricky part, need to handle the last section
		// append last section character numbers with
		// (j - i), then append its value which stored previously
		sb.append(j - i).append(curr);
		result = sb.toString();
		return result;
	}
	
	public String transferIntToStr(int n) {
		String s = "";
		while(n > 0) {
			s = n % 10 + s;
		    n = n / 10;
		}	
		return s;
	}
	
	public static void main(String[] args) {
		CountAndSay countAndSay = new CountAndSay();
		String result = countAndSay.countAndSay(1033111);
		System.out.println(result);
	}
}


// The Real Example Of Nth Sequence Without Wrong Description
// Refer to
// https://discuss.leetcode.com/topic/2264/examples-of-nth-sequence
// https://segmentfault.com/a/1190000003849544
/**
 * At the beginning, I got confusions about what is the nth sequence. 
 * Well, my solution is accepted now, so I'm going to give some examples of nth sequence here. 
 * The following are sequence from n=1 to n=10:
	 1.     1
	 2.     11
	 3.     21
	 4.     1211
	 5.     111221 
	 6.     312211
	 7.     13112221
	 8.     1113213211
	 9.     31131211131221
	 10.   13211311123113112211
 * From the examples you can see, the (i+1)th sequence is the "count and say" of the ith sequence!
 */
public class CountAndSay {
	public String countAndSay(int n) {
		if(n == 0) {
			return "";
		}
		String result = "1";
		if(n == 1) {
			return result;
		}
		// Loop start from 1 because n is {1, 2, 3...} never equal to 0
		for(int i = 1; i < n; i++) {
			int count = 1;
			// Record initial character for compare
			char iniForCompare = result.charAt(0);
			StringBuilder sb = new StringBuilder();
			// Loop start from 1 because not include initial character
			for(int j = 1; j < result.length(); j++) {
				if(result.charAt(j) == iniForCompare) {
					count++;
				} else {
					sb.append(count).append(iniForCompare);
					// Update initial character for compare
					iniForCompare = result.charAt(j);
					count = 1;
				}
			}
			// Remember to add last character
			sb.append(count).append(iniForCompare);
			result = sb.toString();
		}
		return result;
	}
	
	public static void main(String[] args) {
		CountAndSay countAndSay = new CountAndSay();
		String result = countAndSay.countAndSay(6);
		System.out.println(result);
	}
}




