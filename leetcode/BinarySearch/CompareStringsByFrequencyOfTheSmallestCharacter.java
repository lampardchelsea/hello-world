/**
 Refer to
 https://leetcode.com/problems/compare-strings-by-frequency-of-the-smallest-character/
 Let's define a function f(s) over a non-empty string s, which calculates the frequency of the smallest 
 character in s. For example, if s = "dcce" then f(s) = 2 because the smallest character is "c" and its frequency is 2.

Now, given string arrays queries and words, return an integer array answer, where each answer[i] is the 
number of words such that f(queries[i]) < f(W), where W is a word in words.

Example 1:
Input: queries = ["cbd"], words = ["zaaaz"]
Output: [1]
Explanation: On the first query we have f("cbd") = 1, f("zaaaz") = 3 so f("cbd") < f("zaaaz").

Example 2:
Input: queries = ["bbb","cc"], words = ["a","aa","aaa","aaaa"]
Output: [1,2]
Explanation: On the first query only f("bbb") < f("aaaa"). On the second query both f("aaa") and f("aaaa") are both > f("cc").
 
Constraints:
1 <= queries.length <= 2000
1 <= words.length <= 2000
1 <= queries[i].length, words[i].length <= 10
queries[i][j], words[i][j] are English lowercase letters.
*/

// Solution 1: Native freq array, time complexity O(n^2)
class Solution {
    public int[] numSmallerByFrequency(String[] queries, String[] words) {
        int[] result = new int[queries.length];
        for(int i = 0; i < queries.length; i++) {
            String query = queries[i];
            int queryFreq = smallestCharFreq(query);
            int count = 0;
            for(int j = 0; j < words.length; j++) {
                String word = words[j];
                int wordFreq = smallestCharFreq(word);
                if(queryFreq < wordFreq) {
                    count++;
                }
            }
            result[i] = count;
        }
        return result;
    }
    
    private int smallestCharFreq(String str) {
        int[] freq = new int[26];
        for(int i = 0; i < str.length(); i++) {
            freq[str.charAt(i) - 'a']++;
        }
        for(int i = 0; i < 26; i++) {
            if(freq[i] != 0) {
                return freq[i];
            }
        }
        return 0;
    }
}

// Solution 2: Binary Search, time complexity (O(nlogn))
// Refer to
// https://leetcode.com/problems/compare-strings-by-frequency-of-the-smallest-character/discuss/366353/java-binary-search
// Runtime: 5 ms, faster than 78.98% of Java online submissions for Compare Strings by Frequency of the Smallest Character.
// Memory Usage: 38 MB, less than 100.00% of Java online submissions for Compare Strings by Frequency of the Smallest Character.
class Solution {
    public int[] numSmallerByFrequency(String[] queries, String[] words) {
        int[] result = new int[queries.length];
        int[] wordsMaxFreq = new int[words.length];
        int[] queriesMaxFreq = new int[queries.length];
        for(int i = 0; i < words.length; i++) {
            wordsMaxFreq[i] = smallestCharFreq(words[i]);
        }
        Arrays.sort(wordsMaxFreq);
        for(int i = 0; i < queries.length; i++) {
            queriesMaxFreq[i] = smallestCharFreq(queries[i]);
        }
        for(int i = 0; i < queriesMaxFreq.length; i++) {
            int count = queriesMaxFreq[i];
            int l = 0;
            int r = wordsMaxFreq.length - 1;
            while(l <= r) {
                int mid = l + (r - l) / 2;
                if(wordsMaxFreq[mid] <= count) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            result[i] = wordsMaxFreq.length - l;
        }
        return result;
    }
    
    private int smallestCharFreq(String str) {
        int[] freq = new int[26];
        for(int i = 0; i < str.length(); i++) {
            freq[str.charAt(i) - 'a']++;
        }
        for(int i = 0; i < 26; i++) {
            if(freq[i] != 0) {
                return freq[i];
            }
        }
        return 0;
    }
}
