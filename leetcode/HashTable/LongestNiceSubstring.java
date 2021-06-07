/**
Refer to
https://leetcode.com/problems/longest-nice-substring/
A string s is nice if, for every letter of the alphabet that s contains, it appears both in uppercase and lowercase. 
For example, "abABB" is nice because 'A' and 'a' appear, and 'B' and 'b' appear. However, "abA" is not because 'b' appears, but 'B' does not.

Given a string s, return the longest substring of s that is nice. If there are multiple, return the substring of the 
earliest occurrence. If there are none, return an empty string.

Example 1:
Input: s = "YazaAay"
Output: "aAa"
Explanation: "aAa" is a nice string because 'A/a' is the only letter of the alphabet in s, and both 'A' and 'a' appear.
"aAa" is the longest nice substring.

Example 2:
Input: s = "Bb"
Output: "Bb"
Explanation: "Bb" is a nice string because both 'B' and 'b' appear. The whole string is a substring.

Example 3:
Input: s = "c"
Output: ""
Explanation: There are no nice substrings.

Example 4:
Input: s = "dDzeE"
Output: "dD"
Explanation: Both "dD" and "eE" are the longest nice substrings.
As there are multiple longest nice substrings, return "dD" since it occurs earlier.

Constraints:
1 <= s.length <= 100
s consists of uppercase and lowercase English letters.
*/

// Solution 1: Brute Force
// Refer to
// https://leetcode.com/problems/longest-nice-substring/discuss/1075274/Java-Solution-with-Set-comment-explanation
/**
class Solution {
    public String longestNiceSubstring(String s) {
        String result = "";
        // take first index, go from 0 to length-1 of the string
		for (int i = 0;i<s.length(); i++){        
            // take second index, this should go up to the length of the string <=
			for (int j = i+1;j<=s.length(); j++){
                //get the substring for the index range from i to j
				String temp = s.substring(i, j);
                // if length of the substring should be greater than 1
				// if the length should be greater that the previous computed result
				// if the substring is valid Nice String
				// then update the result with the current substring from range i and j
				if (temp.length() > 1 && result.length() < temp.length() && checkNice(temp)) result = temp;
            }    
        }
        return result;
    }
    
	//validate Nice String check
    public boolean checkNice(String temp){
        //add substring to the set
		Set<Character> s = new HashSet<>();
        for (char ch : temp.toCharArray()) s.add(ch);
        
		// return false If you do not find both lower case and upper case in the sub string
		//for e.g 'aAa' substring added to set will have both a and A in the substring which is valid
		// 'azaA' substring will fail for 'z'
		// 'aaaaaaaa' will return "" as result
		//make sure that the substring contains both lower and upper case
        for (char ch : s)
            if (s.contains(Character.toUpperCase(ch)) != s.contains(Character.toLowerCase(ch))) return false;  
        return true;
    }
}
*/
class Solution {
    public String longestNiceSubstring(String s) {
        String result = "";
        int n = s.length();
        for(int i = 0; i <= n; i++) {
            for(int j = i + 1; j <= n; j++) {
                String str = s.substring(i, j);
                if(str.length() > 1 && str.length() > result.length() && checkNice(str)) {
                    result = str;
                }
            }
        }
        return result;
    }
    
    private boolean checkNice(String s) {
        Set<Character> set = new HashSet<Character>();
        for(char c : s.toCharArray()) {
            set.add(c);
        }
        for(char c : s.toCharArray()) {
            if(set.contains(Character.toUpperCase(c)) != set.contains(Character.toLowerCase(c))) {
                return false;
            }
        }
        return true;
    }
}

// Solution 2: Divide and Conquer
// Refer to
// https://leetcode.com/problems/longest-nice-substring/discuss/1074589/JavaStraightforward-Divide-and-Conquer
/**
class Solution {
    public String longestNiceSubstring(String s) {
        if (s.length() < 2) return "";
        char[] arr = s.toCharArray();
        Set<Character> set = new HashSet<>();
        for (char c: arr) set.add(c);
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            if (set.contains(Character.toUpperCase(c)) && set.contains(Character.toLowerCase(c))) continue;
            String sub1 = longestNiceSubstring(s.substring(0, i));
            String sub2 = longestNiceSubstring(s.substring(i+1));
            return sub1.length() >= sub2.length() ? sub1 : sub2;
        }
        return s; 
    }
}

I think the time complexity is O(nlogn), please correct me if wrong!

1st round, we have 1 string of length n
2nd round, 2 strings with sum length of n
3rd round, 4 strings with sum length of n
...
So, there could be logn rounds in total, and overall complexity is O(nlogn)
As @lenchen1112 pointed out, the # of rounds is bound to 26, so this should be O(n)
*/

// https://leetcode.com/problems/longest-nice-substring/discuss/1074677/This-is-a-good-problem-but-it's-bad-to-use-small-constraint-and-mark-it-as-an-easy-problem
/**
This is typically a divide and conquer problem, and can be solve with the time complexity O(N)

But in the contest, many people just use brute force and solve it using O(N^2). This method can save them a lot of time, 
and get better standing. And in the practise, many people just get accepted using brute force and move on.

However, this is not good for them because in the real interview, the interviewer will not satisfy if you only present him 
or her brute force method. It can only waste him or her good chance to practise divide and conquer method.

So, please add some large test cases 1<=n<=10^5, and mark this problem an medium problem.

Here is my O(N) solution, for each s, we try to find the location of any character for appear only in either uppercase or 
lowercase, and split the string by these characters. Then for each splited substring, do the same thing, until it can not 
be splited. return the longest substring with first appearance.

(As some people point out, this is actually NlgN because of the sorting part)

        def getsplit(subs):
            if len(subs)<2: return ""
            lcase = [[] for i in range(26)]
            ucase = [[] for i in range(26)]
			
			#lcase is the lowercase and ucase is the upper case
			
            for i in range(len(subs)):
                if ord(subs[i])-97>=0:  lcase[ord(subs[i])-97].append(i)
                else: ucase[ord(subs[i])-65].append(i)        
            part = [-1,len(subs)]
			
			# part is the index which  letter only appear in either upper case or lower case, and we append -1 and the length of substring for convenience 
                
            for k in range(26):
                if len(ucase[k])*len(lcase[k])==0 and len(ucase[k]) + len(lcase[k])>0:
                    for ele in ucase[k]+lcase[k]:
                        part.append(ele)              
						
            if len(part)==2: return subs         
            part = sorted(part)
            output = ""      
            for i in range(len(part)-1):
                newsub = subs[ (part[i]+1):part[i+1]]
                temp = getsplit(newsub)
                if len(temp)>len(output):
                    output = temp  
            return output
        
        return getsplit(s)

updated: Since this is an OA problem, no need to add more test cases. It would be better if problem maker add a follow up 
below this problem: Can you solve it in O(n) time?
*/
class Solution {
    public String longestNiceSubstring(String s) {
        if (s.length() < 2) return "";
        char[] arr = s.toCharArray();
        Set<Character> set = new HashSet<>();
        for (char c: arr) set.add(c);
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            if (set.contains(Character.toUpperCase(c)) && set.contains(Character.toLowerCase(c))) continue;
            String sub1 = longestNiceSubstring(s.substring(0, i));
            String sub2 = longestNiceSubstring(s.substring(i+1));
            return sub1.length() >= sub2.length() ? sub1 : sub2;
        }
        return s; 
    }
}
