/**
Refer to
https://leetcode.com/problems/change-minimum-characters-to-satisfy-one-of-three-conditions/
You are given two strings a and b that consist of lowercase letters. In one operation, you can change any character in a or b to any lowercase letter.

Your goal is to satisfy one of the following three conditions:

Every letter in a is strictly less than every letter in b in the alphabet.
Every letter in b is strictly less than every letter in a in the alphabet.
Both a and b consist of only one distinct letter.
Return the minimum number of operations needed to achieve your goal.

Example 1:
Input: a = "aba", b = "caa"
Output: 2
Explanation: Consider the best way to make each condition true:
1) Change b to "ccc" in 2 operations, then every letter in a is less than every letter in b.
2) Change a to "bbb" and b to "aaa" in 3 operations, then every letter in b is less than every letter in a.
3) Change a to "aaa" and b to "aaa" in 2 operations, then a and b consist of one distinct letter.
The best way was done in 2 operations (either condition 1 or condition 3).

Example 2:
Input: a = "dabadd", b = "cda"
Output: 3
Explanation: The best way is to make condition 1 true by changing b to "eee".

Constraints:
1 <= a.length, b.length <= 105
a and b consist only of lowercase letters.
*/

// Solution 1: Find the Boundary Letter for two cases
// Refer to
// https://leetcode.com/problems/change-minimum-characters-to-satisfy-one-of-three-conditions/discuss/1032042/Java-Detailed-Explanation-Find-the-Boundary-Letter
/**
Key Notes:

Condition 1 and condition 2 are symmetrical, so generally we have 2 operations: make a below b and make a and b only one distinct letter.
Operation 1 => make a below b:
We don't need to care how we make it, but there is definately a character, which separates the string a and string b
We can try this boundaray chararacter from b to z, and see how many steps to make string a below it, how many steps to make string b above or equal it. And just return the smallest steps among all boundary characters.
Operation 2 => make a and b only one distince letter:
We just need to count the most frequent character of string(a + b)
The operation we need to take is: a.length() + b.length() - count
public int minCharacters(String a, String b) {
        
	return Math.min(Math.min(operation1(a, b), operation1(b, a)), operation2(a, b));
}

// operation to make a below b
private int operation1(String a, String b) {

	int res = Integer.MAX_VALUE;
	for (int i = 1; i < 26; ++i) {  // start from 'b', since no char below 'a'

		int count = 0;
		for (char c : a.toCharArray()) {
			if ((c - 'a') >= i) ++count;
		}
		for (char c : b.toCharArray()) {
			if ((c - 'a') < i) ++count;
		}
		res = Math.min(res, count);
	}

	return res;
}

// operation to make only one distinct letter
private int operation2(String a, String b) {

	int[] A = new int[26];
	for (char c : a.toCharArray()) A[c - 'a']++;
	for (char c : b.toCharArray()) A[c - 'a']++;

	int res = 0;
	for (int i = 0; i < 26; ++i) {
		res = Math.max(res, A[i]);
	}

	return a.length() + b.length() - res;
}
*/
class Solution {
    public int minCharacters(String a, String b) {
        return Math.min(Math.min(option_one(a, b), option_one(b, a)), option_two(a, b));
    }
    
    private int option_one(String a, String b) {
        int result = Integer.MAX_VALUE;
        for(int i = 1; i < 26; i++) {
            int count = 0;
            for(char c : a.toCharArray()) {
                if(c - 'a' >= i) {
                    count++;
                }
            }
            for(char c : b.toCharArray()) {
                if(c - 'a' < i) {
                    count++;
                }
            }
            result = Math.min(result, count);
        }
        return result;
    }
    
    private int option_two(String a, String b) {
        int[] freq = new int[26];
        for(char c : a.toCharArray()) {
            freq[c - 'a']++;
        }
        for(char c : b.toCharArray()) {
            freq[c - 'a']++;
        }
        int maxFreq = 0;
        for(int i = 0; i < 26; i++) {
            maxFreq = Math.max(maxFreq, freq[i]);
        }
        return a.length() + b.length() - maxFreq;
    }
}

