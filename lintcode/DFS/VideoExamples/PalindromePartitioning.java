
import java.util.ArrayList;
import java.util.List;

/**
 * Refer to
 * https://leetcode.com/problems/palindrome-partitioning/description/
 * Given a string s, partition s such that every substring of the partition is a palindrome.
 * 
	Return all possible palindrome partitioning of s.
	
	For example, given s = "aab",
	Return
	
	[
	  ["aa","b"],
	  ["a","a","b"]
	]
 * 
 * 
 * Solution
 * https://discuss.leetcode.com/topic/46159/a-general-approach-to-backtracking-questions-in-java-subsets-permutations-combination-sum-palindrome-partitioning/2
 *
 */
public class PalindromePartitioning {
	public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<List<String>>();
        if(s == null || s.length() == 0) {
            return result;
        }
        List<String> combination = new ArrayList<String>();
        helper(s, result, combination, 0);
        return result;
    }
    
    private void helper(String s, List<List<String>> result, List<String> combination, int startIndex) {
        if(startIndex == s.length()) {
            result.add(new ArrayList<String>(combination));
        }
        for(int i = startIndex; i < s.length(); i++) {
            if(isPalindrome(s, startIndex, i)) {
                combination.add(s.substring(startIndex, i + 1));
                helper(s, result, combination, i + 1);
                combination.remove(combination.size() - 1);
            }
        }
    }
    
    private boolean isPalindrome(String s, int startIndex, int endIndex) {
        while(startIndex < endIndex) {
            if(s.charAt(startIndex++) != s.charAt(endIndex--)) {
                return false;
            }
        }
        return true;
    }
    
    public static void main(String[] args) {
    	PalindromePartitioning p = new PalindromePartitioning();
    	String s = "aab";
    	List<List<String>> result = p.partition(s);
    	for(List<String> r : result) {
    		System.out.println("---------");
    		for(String i : r) {
    			System.out.println(i);
    		}
        	System.out.println("---------");
    	}
    }
    
}
