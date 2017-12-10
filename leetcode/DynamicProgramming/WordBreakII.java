/**
 * Refer to
 * https://leetcode.com/problems/word-break-ii/description/
 * Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, 
   add spaces in s to construct a sentence where each word is a valid dictionary word. 
   You may assume the dictionary does not contain duplicate words.

    Return all such possible sentences.

    For example, given
    s = "catsanddog",
    dict = ["cat", "cats", "and", "sand", "dog"].

    A solution is ["cats and dog", "cat sand dog"].

    UPDATE (2017/1/4):
    The wordDict parameter had been changed to a list of strings (instead of a set of strings). 
    Please reload the code definition to get the latest changes.
 *
 * Solution
 * https://www.youtube.com/watch?v=GXB4rrd68OQ&t=827s
 * https://discuss.leetcode.com/topic/27855/my-concise-java-solution-based-on-memorized-dfs
*/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordBreakII {
     /**
	    s = "catsanddog",
	    dict = ["cat", "cats", "and", "sand", "dog"].
	    
	    Analysis
	    c a t s a n d d o g
	    0 1 2 3 4 5 6 7 8 9
	    start:0   map:empty
	    end:3 -> dfs
	           start:3   map:empty
	           end:7 -> dfs
	                  start:7  map:empty
	                  end:10 -> dfs
	                          start:10 == s.length()
	                          rest.add("")
	                          map.put(10, "")
	                          return rest
	  ------------------------------------------------
	                  start:7  map:{10=[]}
	                  end:10   rest.add("dog")
	                           map.put(7,"dog")
	                           return rest
	           start:3   map:{7=[dog],10=[]}
	           end:7     rest.add("sand dog")
	                     map.put(3,"sand dog")
	                     return rest
	     start:0   map:{3=[sand dog],7=[dog],10=[]}
	     end:3     rest.add("cat sand dog") --> 1st solution
	               map.put(0,"cat sand dog")
	               return rest
	     ---------------------------------------------
	     start:0   map:{3=[sand dog],7=[dog],10=[]}
	     end:4 -> dfs
	            start:4  map:{3=[sand dog],7=[dog],10=[]}
	            end:7 -> dfs
	                   start:7
	                   end:10 -> dfs -> return map.get(7) -> [dog] --> DP quick return
	     ---------------------------------------------
	            start:4  map:{3=[sand dog],7=[dog],10=[]}
	            end:7    rest.add("and" + " " + "dog")
	                     map.put(4, "and dog")
	                     return rest
	      start:0  map:{3=[sand dog], 4=[and dog], 7=[dog], 10=[]}
	      end:4    rest.add("cats" + " " + "and dog") -> 2nd solution
	               map.put(0, "cats and dog")
	               return  rest
	      --------------------------------------------
	      finally map:{0=[cat sand dog, cats and dog], 3=[sand dog], 4=[and dog], 7=[dog], 10=[]}
	              rest:[cat sand dog, cats and dog]
	  */
    Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();
    public List<String> wordBreak(String s, List<String> wordDict) {
        return dfs(s, wordDict, 0);
    }
    
    private List<String> dfs(String s, List<String> wordDict, int start) {
	// For DP quick return
        if(map.containsKey(start)) {
            return map.get(start);
        }
	// Base case
	// e.g for given string as "catsanddog",
	// when start = 10, rest.add("") is prepare for
	// concatenation "dog" ahead.
        List<String> rest = new ArrayList<String>();
        if(start == s.length()) {
            rest.add("");
        }
        for(int end = start + 1; end <= s.length(); end++) {
            if(wordDict.contains(s.substring(start, end))) {
                List<String> list = dfs(s, wordDict, end);
                for(String temp : list) {
                    rest.add(s.substring(start, end) + (temp.equals("") ? "" : " ") + temp);
                }
            }
        }
        map.put(start, rest);
        return rest;
    }
   
    public static void main(String[] args) {
    	WordBreakII w = new WordBreakII();
    	String s = "catsanddog";
    	List<String> wordDict = new ArrayList<String>();
    	wordDict.add("cat");
    	wordDict.add("cats");
    	wordDict.add("and");
    	wordDict.add("sand");
    	wordDict.add("dog");
    	List<String> result = w.wordBreak(s, wordDict);
    	System.out.println(result.size());
    }
}
