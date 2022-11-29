
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





























https://leetcode.com/problems/letter-combinations-of-a-phone-number/

Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent. Return the answer in any order.

A mapping of digits to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.


Example 1:
```
Input: digits = "23"
Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
```

Example 2:
```
Input: digits = ""
Output: []
```

Example 3:
```
Input: digits = "2"
Output: ["a","b","c"]
```

Constraints:
- 0 <= digits.length <= 4
- digits[i] is a digit in the range ['2', '9'].
---
Attempt 1: 2022-11-26

Solution 1:  Backtracking

Style 1: Recursion with StringBuilder (10 min)
```
class Solution { 
    public List<String> letterCombinations(String digits) { 
        List<String> result = new ArrayList<String>(); 
        if(digits.length() == 0) { 
            return result; 
        } 
        String[] dictionary = new String[] {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"}; 
        helper(digits, 0, dictionary, result, new StringBuilder()); 
        return result; 
    } 
     
    private void helper(String digits, int index, String[] dictionary, List<String> result, StringBuilder sb) { 
        if(index == digits.length()) { 
            result.add(sb.toString()); 
            return; 
        } 
        String str = dictionary[digits.charAt(index) - '0']; 
        for(char c : str.toCharArray()) { 
            sb.append(c); 
            helper(digits, index + 1, dictionary, result, sb); 
            sb.setLength(sb.length() - 1); 
        } 
    } 
}

Time Complexity: O(4^n)
Since there are no more than 4 possible characters for each digit, the number of recursive calls, T(n), satisfies T(n) < 4T(n - 1), where n is the number of digits in the number. This solves to T(n) = O(4^n). 
Each base case entails making a copy of a string and adding it to the result. Since each such string has length n, each base case takes time O(n). Therefore, the time complexity is O(n * 4^n).

Space Complexity: O(4^n)
```

Style 2: Recursion with String (10 min)
```
class Solution { 
    public List<String> letterCombinations(String digits) { 
        List<String> result = new ArrayList<String>(); 
        if(digits.length() == 0) { 
            return result; 
        } 
        String[] dictionary = new String[] {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"}; 
        helper(digits, 0, dictionary, result, ""); 
        return result; 
    } 
     
    private void helper(String digits, int index, String[] dictionary, List<String> result, String tmp) { 
        if(index == digits.length()) { 
            result.add(tmp); 
            return; 
        } 
        String str = dictionary[digits.charAt(index) - '0']; 
        for(char c : str.toCharArray()) { 
            helper(digits, index + 1, dictionary, result, tmp + c); 
        } 
    } 
}

Time Complexity: O(4^n)
Since there are no more than 4 possible characters for each digit, the number of recursive calls, T(n), satisfies T(n) < 4T(n - 1), where n is the number of digits in the number. This solves to T(n) = O(4^n). 
Each base case entails making a copy of a string and adding it to the result. Since each such string has length n, each base case takes time O(n). Therefore, the time complexity is O(n * 4^n).

Space Complexity: O(4^n)
```

Refer to
https://leetcode.com/problems/letter-combinations-of-a-phone-number/discuss/780232/Backtracking-Python-problems%2B-solutions-interview-prep
```
class Solution(object): 
    def letterCombinations(self, digits): 
        """ 
        :type digits: str 
        :rtype: List[str] 
        """ 
        dic = { "2": "abc", "3": "def", "4":"ghi", "5":"jkl", "6":"mno", "7":"pqrs", "8":"tuv", "9":"wxyz"} 
         
        res=[] 
        if len(digits) ==0: 
            return res 
             
        self.dfs(digits, 0, dic, '', res) 
        return res 
     
    def dfs(self, nums, index, dic, path, res): 
        if index >=len(nums): 
            res.append(path) 
            return 
        string1 =dic[nums[index]] 
        for i in string1: 
            self.dfs(nums, index+1, dic, path + i, res)
```

Solution 2:  BFS
```
class Solution {
    public List<String> letterCombinations(String digits) {
		LinkedList<String> result = new LinkedList<String>();
		if(digits.isEmpty()) return result;
		String[] dictionary = new String[] {"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
		result.add("");
		for(int i =0; i < digits.length(); i++){
			int x = digits.charAt(i) - '0';
			while(result.peek().length() == i){
				String tmp = result.remove();
				for(char s : dictionary[x].toCharArray())
					result.add(tmp + s);
			}
		}
		return result;
    }
}
```

