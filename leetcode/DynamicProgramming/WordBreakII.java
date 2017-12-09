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
class Solution {
    
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
                    
                    
    */
    
    Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();
    public List<String> wordBreak(String s, List<String> wordDict) {
        return dfs(s, wordDict, 0);
    }
    
    private List<String> dfs(String s, List<String> wordDict, int start) {
        if(map.containsKey(start)) {
            return map.get(start);
        }
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
}
