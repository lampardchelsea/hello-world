/**
Refer to
https://leetcode.com/problems/word-subsets/
We are given two arrays words1 and words2 of words.  Each word is a string of lowercase letters.

Now, say that word b is a subset of word a if every letter in b occurs in a, including multiplicity.  
For example, "wrr" is a subset of "warrior", but is not a subset of "world".

Now say a word a from words1 is universal if for every b in words2, b is a subset of a. 

Return a list of all universal words in words1.  You can return the words in any order.

Example 1:
Input: words1 = ["amazon","apple","facebook","google","leetcode"], words2 = ["e","o"]
Output: ["facebook","google","leetcode"]

Example 2:
Input: words1 = ["amazon","apple","facebook","google","leetcode"], words2 = ["l","e"]
Output: ["apple","google","leetcode"]

Example 3:
Input: words1 = ["amazon","apple","facebook","google","leetcode"], words2 = ["e","oo"]
Output: ["facebook","google"]

Example 4:
Input: words1 = ["amazon","apple","facebook","google","leetcode"], words2 = ["lo","eo"]
Output: ["google","leetcode"]

Example 5:
Input: words1 = ["amazon","apple","facebook","google","leetcode"], words2 = ["ec","oc","ceo"]
Output: ["facebook","leetcode"]

Note:
1 <= words1.length, words2.length <= 10000
1 <= words1[i].length, words2[i].length <= 10
words1[i] and words2[i] consist only of lowercase letters.
All words in words1[i] are unique: there isn't i != j with words1[i] == words1[j].
*/

// Solution 1: Normal freq map but TLE
class Solution {
    public List<String> wordSubsets(String[] words1, String[] words2) {
        List<String> result = new ArrayList<String>();
        for(String w1 : words1) {
            if(allSubset(w1, words2)) {
                result.add(w1);
            }
        }
        return result;
    }
    
    private boolean allSubset(String w1, String[] words2) {
        for(String w2 : words2) {
            if(!isSubset(w1, w2)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isSubset(String w1, String w2) {
        int[] freq = new int[26];
        for(char c : w1.toCharArray()) {
            freq[c - 'a']++;
        }
        for(char c : w2.toCharArray()) {            
            if(freq[c - 'a'] <= 0) {
                return false;
            } else {
                freq[c - 'a']--;
            }
        }
        return true;
    }
}

// Solution 2: count the most frequent char of words in B
// Style 1: break out only on inside for loop but with global variable to control skip out for loop
// Refer to
// https://leetcode.com/problems/word-subsets/discuss/175854/JavaC%2B%2BPython-Straight-Forward
/**
Explanation
For each word b in B,
we use function counter to count occurrence of each letter.
We take the maximum occurrences of counts, and use it as a filter of A.

Java:

    public List<String> wordSubsets(String[] A, String[] B) {
        int[] count = new int[26], tmp;
        int i;
        for (String b : B) {
            tmp = counter(b);
            for (i = 0; i < 26; ++i)
                count[i] = Math.max(count[i], tmp[i]);
        }
        List<String> res = new ArrayList<>();
        for (String a : A) {
            tmp = counter(a);
            for (i = 0; i < 26; ++i)
                if (tmp[i] < count[i])
                    break;
            if (i == 26) res.add(a);
        }
        return res;
    }

    int[] counter(String word) {
        int[] count = new int[26];
        for (char c : word.toCharArray()) count[c - 'a']++;
        return count;
    }
*/
class Solution {
    public List<String> wordSubsets(String[] words1, String[] words2) {
        List<String> result = new ArrayList<String>();
        int[] maxFreqWords2 = new int[26];
        for(String w2 : words2) {
            int[] cur = count(w2);
            for(int i = 0; i < 26; i++) {
                maxFreqWords2[i] = Math.max(maxFreqWords2[i], cur[i]);
            }
        }
        int i;
        for(String w1 : words1) {
            int[] cur = count(w1);
            for(i = 0; i < 26; i++) {
                if(cur[i] < maxFreqWords2[i]) {
                    break;
                }
            }
            if(i == 26) {
                result.add(w1);
            }
        }
        return result;
    }
    
    private int[] count(String s) {
        int[] result = new int[26];
        for(char c : s.toCharArray()) {
            result[c - 'a']++;
        }
        return result;
    }
}

// Style 2: Using Java outer label to directly break out to outer for loop
// Refer to
// https://stackoverflow.com/questions/3821827/loop-in-java-code-what-is-this-and-why-does-it-compile
// https://stackoverflow.com/a/3821865/6706875
/**
As other posters have said, it is a label, not a keyword. Using labels allows you to do things like:
outer: for(;;) {
   inner: for(;;) {
     break outer;
   }
}
This allows for breaking of the outer loop.
*/

// Refer to
// https://leetcode.com/problems/word-subsets/discuss/175850/JavaPython-3-Time-O(A-%2B-B)-clean-codes-count-the-most-frequent-char-of-words-in-B
/**
e.g., if B = ["o", "oo"], count['o' - 'a'] = 2 (NOT 3);
Do NOT use count[0][o - 'a'] = 1, count[1][o - 'a'] = 2, which would cost too much time. I failed by using such way with a TLE, before correcting it.

    public List<String> wordSubsets(String[] A, String[] B) {
        int[] count = new int[26];
        for (String b : B) { 
            int[] bCnt = new int[26];
            for (char c : b.toCharArray()) { // count b's char.  
                ++bCnt[c - 'a']; 
            }
            for (int i = 0; i < 26; ++i) { // count the max frequency. 
                count[i] = Math.max(count[i], bCnt[i]); 
            }
        } 
        List<String> ans = new ArrayList<>();
        outer: for (String a : A) {
            int[] aCnt = new int[26]; 
            for (char c : a.toCharArray()) { // count a's char. 
                ++aCnt[c - 'a']; 
            }
            // if the occurrency of char ('a' + i) in B is more frequent than 
            // that in a, ignore it.
            for (int i = 0; i < 26; ++i) { 
                if (count[i] > aCnt[i]) 
                    continue outer; 
            }    
            ans.add(a);
        }
        return ans;
    }

Analysis:
Time & space: O(A + B), where A & B are the number of total characters in A & B, respectively.
*/
class Solution {
    public List<String> wordSubsets(String[] words1, String[] words2) {
        List<String> result = new ArrayList<String>();
        int[] maxFreqWords2 = new int[26];
        for(String w2 : words2) {
            int[] cur = count(w2);
            for(int i = 0; i < 26; i++) {
                maxFreqWords2[i] = Math.max(maxFreqWords2[i], cur[i]);
            }
        }
        outer: for(String w1 : words1) {
            int[] cur = count(w1);
            for(int i = 0; i < 26; i++) {
                if(cur[i] < maxFreqWords2[i]) {
                    continue outer;
                }
            }
            result.add(w1);
        }
        return result;
    }
    
    private int[] count(String s) {
        int[] result = new int[26];
        for(char c : s.toCharArray()) {
            result[c - 'a']++;
        }
        return result;
    }
}







































































https://leetcode.com/problems/word-subsets/description/
You are given two string arrays words1 and words2.
A string b is a subset of string a if every letter in b occurs in a including multiplicity.
For example, "wrr" is a subset of "warrior" but is not a subset of "world".
A string a from words1 is universal if for every string b in words2, b is a subset of a.
Return an array of all the universal strings in words1. You may return the answer in any order.

Example 1:
Input: words1 = ["amazon","apple","facebook","google","leetcode"], words2 = ["e","o"]
Output: ["facebook","google","leetcode"]

Example 2:
Input: words1 = ["amazon","apple","facebook","google","leetcode"], words2 = ["l","e"]
Output: ["apple","google","leetcode"]
 
Constraints:
- 1 <= words1.length, words2.length <= 10^4
- 1 <= words1[i].length, words2[i].length <= 10
- words1[i] and words2[i] consist only of lowercase English letters.
- All the strings of words1 are unique.
--------------------------------------------------------------------------------
Attempt 1: 2024-11-30
Solution 1: Hash Table (10 min)
class Solution {
    public List<String> wordSubsets(String[] words1, String[] words2) {
        int[] freq = new int[26];
        for(String word : words2) {
            int[] tmp = new int[26];
            for(char c : word.toCharArray()) {
                tmp[c - 'a']++;
            }
            for(int i = 0; i < 26; i++) {
                freq[i] = Math.max(freq[i], tmp[i]);
            }
        }
        List<String> result = new ArrayList<>();
        for(String word : words1) {
            int[] tmp = new int[26];
            for(char c : word.toCharArray()) {
                tmp[c - 'a']++;
            }
            int i;
            for(i = 0; i < 26; i++) {
                if(tmp[i] < freq[i]) {
                    break;
                }
            }
            if(i == 26) {
                result.add(word);
            }
        }
        return result;
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Refer to
https://leetcode.com/problems/word-subsets/solutions/175854/java-c-python-straight-forward/
Explanation
For each word b in B,
we use function counter to count occurrence of each letter.
We take the maximum occurrences of counts, and use it as a filter of A.
    public List<String> wordSubsets(String[] A, String[] B) {
        int[] count = new int[26], tmp;
        int i;
        for (String b : B) {
            tmp = counter(b);
            for (i = 0; i < 26; ++i)
                count[i] = Math.max(count[i], tmp[i]);
        }
        List<String> res = new ArrayList<>();
        for (String a : A) {
            tmp = counter(a);
            for (i = 0; i < 26; ++i)
                if (tmp[i] < count[i])
                    break;
            if (i == 26) res.add(a);
        }
        return res;
    }

    int[] counter(String word) {
        int[] count = new int[26];
        for (char c : word.toCharArray()) count[c - 'a']++;
        return count;
    }


