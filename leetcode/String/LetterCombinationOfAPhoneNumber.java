
import java.util.ArrayList;
import java.util.List;

/**
 * Refer to
 * https://leetcode.com/problems/letter-combinations-of-a-phone-number/#/description
 * Given a digit string, return all possible letter combinations that the number could represent.
 * A mapping of digit to letters (just like on the telephone buttons) is given below.
	
	Input:Digit string "23"
	Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
 * 
 * Note:
 * Although the above answer is in lexicographical order, your answer could be in 
 * any order you want. 
 * 
 * Solution
 * https://segmentfault.com/a/1190000003766442
 * 深度优先搜索
 * 杂度
 * 时间 O(N) 空间 O(N) 递归栈空间
 * 思路
 * 首先建一个表，来映射号码和字母的关系。然后对号码进行深度优先搜索，对于每一位，从表中找出数字对应的字母，
 * 这些字母就是本轮搜索的几种可能。
 * 注意
 * 用StringBuilder构建临时字符串
 * 当临时字符串为空时，不用将其加入结果列表中
 * 
 * https://discuss.leetcode.com/topic/8465/my-java-solution-with-fifo-queue
 * 
 */
public class LetterCombinationOfAPhoneNumber {
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<String>();
        String[] table = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        StringBuilder sb = new StringBuilder();
        dfs(result, table, sb, digits, 0);
        //dfs1(result, table, sb, digits, 0);
        return result;
    }
    
    // Solution 1.1
    public void dfs(List<String> result, String[] table, StringBuilder sb, String digits, int idx) {
        if(idx == digits.length()) {
            if(sb.length() != 0) {
                result.add(sb.toString());
            }
            // This 'return' is important, no matter what kind of input, even input as digits = "", "12"...
            // we must return, in case of if digits = "", we continue running without return onto digits.charAt(idx),
            // will throw exception as below:
            // Runtime Error Message:Line 18: java.lang.StringIndexOutOfBoundsException: String index out of range: 0
            // Last executed input:""
            // Solution 1.2 can avoid this case by using if-else branch to separate out digits = "" and
            // other kind of input
            return;
        }
        int tableIdx = digits.charAt(idx) - '0';
        String str = table[tableIdx];
        for(int i = 0; i < str.length(); i++) {
            sb.append(str.charAt(i));
            dfs(result, table, sb, digits, idx + 1);
            // Don't forget to remove the current digit which just added onto StringBuilder
            // Refer to
            // Remove last character of a StringBuilder ?
            // https://stackoverflow.com/questions/3395286/remove-last-character-of-a-stringbuilder
            sb.deleteCharAt(sb.length() - 1);
        }     
    }
    
    // Solution 1.2
    public void dfs1(List<String> result, String[] table, StringBuilder sb, String digits, int idx) {
        if(idx == digits.length()) {
            if(sb.length() != 0) {
                result.add(sb.toString());
            }
        } else {
	        int tableIdx = digits.charAt(idx) - '0';
	        String str = table[tableIdx];
	        for(int i = 0; i < str.length(); i++) {
	            sb.append(str.charAt(i));
	            dfs1(result, table, sb, digits, idx + 1);
	            sb.deleteCharAt(sb.length() - 1);
	        }
        }
    }
    
    // Solution 2: BFS (Iterative Solution)
    public List<String> letterCombinations2(String digits) {
        LinkedList<String> result = new LinkedList<String>();
        String[] table = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        // Refer to
        // https://discuss.leetcode.com/topic/8465/my-java-solution-with-fifo-queue/6
        // This is a iterative solution. For each digit added, remove and copy every element in the 
        // queue and add the possible letter to each element, then add the updated elements back into 
        // queue again. Repeat this procedure until all the digits are iterated.
        // I did a experiment to compare backtracking(DFS) method and this iterative method. 
        // It turns out iterative one is 4 times faster.
        // One minor bug here.
        // We need to add some code to test whether the input is empty or not.
        // Above ans.add(""); 
        // add
		// if (digits.length()==0){
		// 	return ans;
		// }
        if(digits.length() == 0) {
            return result;
        }
        result.add("");
        for(int i = 0; i < digits.length(); i++) {
            int idx = digits.charAt(i) - '0';
            // To use peek() must initialize 'result' as LinkedList as more specific type
            // than generic type List
            while(result.peek().length() == i) {
                String str = result.remove();
                for(char c : table[idx].toCharArray()) {
                    result.add(str + c);
                }
            }
        }
        return result;        
    }
    
    
    public static void main(String[] args) {
    	LetterCombinationOfAPhoneNumber a = new LetterCombinationOfAPhoneNumber();
    	String digits = "23348";
    	List<String> result = a.letterCombinations(digits);
    	for(String s : result) {
    		System.out.println("--" + s + "--");
    	}
    }
}
