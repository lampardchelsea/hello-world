/**
Refer to
https://leetcode.com/problems/evaluate-the-bracket-pairs-of-a-string/
You are given a string s that contains some bracket pairs, with each pair containing a non-empty key.

For example, in the string "(name)is(age)yearsold", there are two bracket pairs that contain the keys "name" and "age".
You know the values of a wide range of keys. This is represented by a 2D string array knowledge where each knowledge[i] 
= [keyi, valuei] indicates that key keyi has a value of valuei.

You are tasked to evaluate all of the bracket pairs. When you evaluate a bracket pair that contains some key keyi, you will:

Replace keyi and the bracket pair with the key's corresponding valuei.
If you do not know the value of the key, you will replace keyi and the bracket pair with a question mark "?" (without the quotation marks).
Each key will appear at most once in your knowledge. There will not be any nested brackets in s.

Return the resulting string after evaluating all of the bracket pairs.

Example 1:
Input: s = "(name)is(age)yearsold", knowledge = [["name","bob"],["age","two"]]
Output: "bobistwoyearsold"
Explanation:
The key "name" has a value of "bob", so replace "(name)" with "bob".
The key "age" has a value of "two", so replace "(age)" with "two".

Example 2:
Input: s = "hi(name)", knowledge = [["a","b"]]
Output: "hi?"
Explanation: As you do not know the value of the key "name", replace "(name)" with "?".

Example 3:
Input: s = "(a)(a)(a)aaa", knowledge = [["a","yes"]]
Output: "yesyesyesaaa"
Explanation: The same key can appear multiple times.
The key "a" has a value of "yes", so replace all occurrences of "(a)" with "yes".
Notice that the "a"s not in a bracket pair are not evaluated.

Example 4:
Input: s = "(a)(b)", knowledge = [["a","b"],["b","a"]]
Output: "ba"

Constraints:
1 <= s.length <= 105
0 <= knowledge.length <= 105
knowledge[i].length == 2
1 <= keyi.length, valuei.length <= 10
s consists of lowercase English letters and round brackets '(' and ')'.
Every open bracket '(' in s will have a corresponding close bracket ')'.
The key in each bracket pair of s will be non-empty.
There will not be any nested bracket pairs in s.
keyi and valuei consist of lowercase English letters.
Each keyi in knowledge is unique.
*/

// Solution 1: Find ')' first then delete backwards till '('
class Solution {
    public String evaluate(String s, List<List<String>> knowledge) {
        Map<String, String> map = new HashMap<String, String>();
        for(List<String> a : knowledge) {
            map.put(a.get(0), a.get(1));
        }
        StringBuilder sb = new StringBuilder();
        StringBuilder tmp = new StringBuilder();
        int n = s.length();
        int j = 0;
        for(int i = 0; i < n; i++) {
            char c = s.charAt(i);
            // Find ')' then delete backwards till '('
            if(c == ')') {
                // Record ')' position, after remove (...) directly skip section
                // by repointing to ')' next position
                j = i;
                // Skip ')' to check actual characters before ')'
                i--;
                while(s.charAt(i) != '(') {
                    // Store the target string section to replace
                    tmp.insert(0, s.charAt(i));
                    sb.setLength(sb.length() - 1);
                    i--;
                }
                // Remove '('
                sb.setLength(sb.length() - 1);
                // Replace target string section with mapping string or "?" as default
                sb.append(map.getOrDefault(tmp.toString(), "?"));
                // Clear target string for next round
                tmp.setLength(0);
                // Repointing to ')' position, then together with next round i++
                // to move to ')' next position
                i = j;
            } else {
            	sb.append(c);
            }
        }
        return sb.toString();
    }
}

// Solution 2: Find '(' first then delete forwards till ')'
// Refer to
// https://leetcode.com/problems/evaluate-the-bracket-pairs-of-a-string/discuss/1130604/Simple-Solution-using-Hashmap-w-explanation-or-Linear-Complexity
/**
There isn't much to this problem. We just need to realize that we can simple map each key from knowledge with its 
corresponding value to make the task of finding the key-value pair into amortized O(1) time complexity.

After this, we just need to iterate over the string s, find the bracket pairs and map the word inside it to either 
its corresponding value (if it exists) or ?.

string evaluate(string s, vector<vector<string>>& knowledge) {
	unordered_map<string, string> mp;   // stores key-val pair from knowledge
	for(auto& pair : knowledge) mp[pair[0]] = pair[1];
	int n = size(s); string ans = "";
	for(int i = 0; i < n; i++)
		if(s[i] == '('){                
			string key = "";			
			while(++i < n && s[i] != ')') key += s[i];   // finding the word inside bracket pair
			ans += (mp.count(key) ? mp[key] : "?");      // if key is found, append corresponding value else append ?
		}
		else ans += s[i];  // for rest of the string, keep it as it is
	
	return ans;
}

Time Complexity : O(N + M), where N is the length of string and M is the number of pairs in knowledge
Space Complexity : O(N + M)
*/
class Solution {
    public String evaluate(String s, List<List<String>> knowledge) {
        Map<String, String> map = new HashMap<String, String>();
        for(List<String> a : knowledge) {
            map.put(a.get(0), a.get(1));
        }
        StringBuilder sb = new StringBuilder();
        StringBuilder tmp = new StringBuilder();
        int n = s.length();
        for(int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if(c == '(') {
                i++;
                while(s.charAt(i) != ')') {
                    tmp.append(s.charAt(i));
                    i++;
                }
                sb.append(map.getOrDefault(tmp.toString(), "?"));
                tmp.setLength(0);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
