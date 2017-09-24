/**
 * Refer to
 * http://www.lintcode.com/en/problem/find-the-missing-number-ii/
 * Giving a string with number from 1-n in random order, but miss 1 number.Find that number.

	 Notice
	
	n <= 30
	
	Have you met this question in a real interview? Yes
	Example
	Given n = 20, str = 19201234567891011121314151618
	
	return 17
 * 
 * 
 * Solution
 * http://www.jiuzhang.com/solution/find-the-missing-number-ii/
 * http://blog.hyoung.me/cn/2017/02/find-the-missing-number/
 * https://xizha677.gitbooks.io/codenotes/content/find-the-missing-number-ii.html
 */
public class FindTheMissingNumberII {
	// Style 1: Return as void and pass dict to record everything, purely traverse way
	int result = 0;
    public int findMissing2(int n, String str) {
        boolean[] dict = new boolean[n + 1];
        dict[0] = true;
        dfs(n, str, 0, dict);
//        int i;
//        for(i = 1; i < dict.length; i++) {
//            if(!dict[i]) {
//                return i;
//            }
//        }
        return result;
    }
    
    private void dfs(int n, String s, int beginIndex, boolean[] dict) {
        // E.g 30 and "1110986543271213130292826252423222120191817161514" expected 27
    	//     to test out must include 'count' and 'firstI'
        //if(beginIndex >= s.length() || s.charAt(beginIndex) == '0') {
        if(beginIndex >= s.length()) {
            // for(int i = 1; i < dict.length; i++) {
            //     if(!dict[i]) {
            //         result = i;
            //     }
            // }
            int count = 0;
            int firstI = 0;
            for (int i = 1; i <= n; i++) {
                if (!dict[i]) {
                    count++;
                    firstI = i;
                }
            }
            if (count == 1) {
                result = firstI;
            }
            return;
        } 
        int val = Integer.valueOf(s.substring(beginIndex, beginIndex + 1));
        if(!dict[val]) {
            dict[val] = true;
            dfs(n, s, beginIndex + 1, dict);
            dict[val] = false;
        }
        // E.g 20 and "12131415161819201234568910117" expected 17
        if(beginIndex + 1 >= s.length()) {
            return;
        }
        val = Integer.valueOf(s.substring(beginIndex, beginIndex + 2));
        if(val <= n && !dict[val]) {
            dict[val] = true;
            dfs(n, s, beginIndex + 2, dict);
            dict[val] = false;
        }
    }
    
    
    // Style 2: Return boolean but also pass dict to record everything, mix divide and conquer with traverse way 
    public int findMissing2_2(int n, String str) {
        boolean[] dict = new boolean[n + 1];
        dict[0] = true;
        dfs_2(n, str, 0, dict);
        int i;
        for(i = 1; i < dict.length; i++) {
            if(!dict[i]) {
                return i;
            }
        }
        return -1;
    }
    
    private boolean dfs_2(int n, String s, int beginIndex, boolean[] dict) {
        if(beginIndex >= s.length()) {
            return true;
        }
        // E.g 11 and "111098765432" to test out
        if(s.charAt(beginIndex) == '0') {
            return false;
        }
        int val = Integer.valueOf(s.substring(beginIndex, beginIndex + 1));
        if(!dict[val]) {
            dict[val] = true;
            // The if condition and return add here will avoid hit dict[val] = false,
            // when current dfs path is on right way, not like style 1 without
            // if condition and return always hit dict[val] = false, which will
            // cause issue on revert all positions back to false even dfs on the
            // right way and just not right on current step
            if(dfs_2(n, s, beginIndex + 1, dict)) {
                return true;
            }
            dict[val] = false;
        }
        // E.g 13 and "1110987654321213" to test out there is a big difference between
        //     cpp and java:
        /**
         *  C++ 的 substr() 会自动把超长的字符串截掉。
			http://en.cppreference.com/w/cpp/string/basic_string/substr
			If the requested substring extends past the end of the string, the returned substring is [pos, size()).
			
			但是 JAVA 的 substring() 会 throw exception
			https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#substring-int-int-
			Throws: IndexOutOfBoundsException - if the beginIndex is negative, or endIndex is larger 
			than the length of this String object, or beginIndex is larger than endIndex.
         */
        if(beginIndex + 1 >= s.length()) {
            return false;
        }
        val = Integer.valueOf(s.substring(beginIndex, beginIndex + 2));
        if(val <= n && !dict[val]) {
            dict[val] = true;
            // The if condition and return add here will avoid hit dict[val] = false,
            // when current dfs path is on right way, not like style 1 without
            // if condition and return always hit dict[val] = false, which will
            // cause issue on revert all positions back to false even dfs on the
            // right way and just not right on current step
            if(dfs_2(n, s, beginIndex + 2, dict)) {
                return true;
            }
            dict[val] = false;
        }
        return false;
    }
    
    
    public static void main(String[] args) {
    	FindTheMissingNumberII f = new FindTheMissingNumberII();
//    	String str = "19201234567891011121314151618";
//    	int n = 20;
//    	String str = "12345679";
//    	int n = 9;
    	String str = "1110986543271213130292826252423222120191817161514";
    	int n = 30;
    	int result = f.findMissing2(n, str);
    	System.out.print(result);
    }
}
