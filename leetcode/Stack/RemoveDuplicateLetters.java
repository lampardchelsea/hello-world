import java.util.Stack;

/**
 * Refer to
 * https://leetcode.com/problems/remove-duplicate-letters/description/
 * Given a string which contains only lowercase letters, remove duplicate letters 
 * so that every letter appear once and only once. You must make sure your result 
 * is the smallest in lexicographical order among all possible results.

	Example:
	Given "bcabc"
	Return "abc"
	
	Given "cbacdcbc"
	Return "acdb"
 * 
 * Solution
 * https://discuss.leetcode.com/topic/32259/java-solution-using-stack-with-comments
 */
public class RemoveDuplicateLetters {
public String removeDuplicateLetters(String s) {
		// Build dictionary
        int[] dict = new int[26];
        char[] ch = s.toCharArray();
        for(char c : ch) {
            dict[c - 'a']++;
        }
        // Build stack for result
        Stack<Character> stack = new Stack<Character>();
        // Recording current character in stack or not
        boolean[] visited = new boolean[26];
        for(char c : ch) {
            int index = c - 'a';
            // Decrement number of characters remaining in the string to be analysed
            dict[index]--;
            // If character is already present in stack, dont bother
            if(visited[index]) {
                continue;
            }
            // If current character < peek character of stack and that peek character
            // still have additional supplyment in dictionary, we can pop out peek character
            // Another explaination:
            // if current character is smaller than last character in stack which occurs 
            // later in the string again
            // it can be removed and  added later e.g stack = bc remaining string abc then 
            // a can pop b and then c
            while(!stack.isEmpty() && (index < stack.peek() - 'a') && dict[stack.peek() - 'a'] > 0) {
            	// Reset the visited status since we will pop out the peek value
                visited[stack.peek() - 'a'] = false;
                stack.pop();
                // Or in simple we can write as
                // visited[stack.pop() - 'a'] = false;
            }
            stack.push(c);
            visited[index] = true;
        }
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()) {
            sb.insert(0, stack.pop());
        }
        return sb.toString();
	}
	
	public static void main(String[] args) {
		RemoveDuplicateLetters r = new RemoveDuplicateLetters();
		String s = "bcabc";
		String result = r.removeDuplicateLetters(s);
		System.out.println(result);
	}
}
