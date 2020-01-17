/**
 Refer to
 https://leetcode.com/problems/number-of-matching-subsequences/
 Given string S and a dictionary of words words, find the number of words[i] that is a subsequence of S.

Example :
Input: 
S = "abcde"
words = ["a", "bb", "acd", "ace"]
Output: 3
Explanation: There are three words in words that are a subsequence of S: "a", "acd", "ace".
Note:

All words in words and S will only consists of lowercase letters.
The length of S will be in the range of [1, 50000].
The length of words will be in the range of [1, 5000].
The length of words[i] will be in the range of [1, 50].
 
 Solution:
 https://leetcode.com/problems/number-of-matching-subsequences/discuss/117634/Efficient-and-simple-go-through-words-in-parallel-with-explanation
 Explanation:
I go through S once, and while I'm doing that, I move through all words accordingly. That is, I keep track of how much of each word I've already seen, and with each letter of S, I advance the words waiting for that letter. To quickly find the words waiting for a certain letter, I store each word (and its progress) in a list of words waiting for that letter. Then for each of the lucky words whose current letter just occurred in S, I update their progress and store them in the list for their next letter.

Let's go through the given example:

S = "abcde"
words = ["a", "bb", "acd", "ace"]
I store that "a", "acd" and "ace" are waiting for an 'a' and "bb" is waiting for a 'b' (using parentheses to show how far I am in each word):

'a':  ["(a)", "(a)cd", "(a)ce"]
'b':  ["(b)b"]
Then I go through S. First I see 'a', so I take the list of words waiting for 'a' and store them as waiting under their next letter:

'b':  ["(b)b"]
'c':  ["a(c)d", "a(c)e"]
None: ["a"]
You see "a" is already waiting for nothing anymore, while "acd" and "ace" are now waiting for 'c'. Next I see 'b' and update accordingly:

'b':  ["b(b)"]
'c':  ["a(c)d", "a(c)e"]
None: ["a"]
Then 'c':

'b':  ["b(b)"]
'd':  ["ac(d)"]
'e':  ["ac(e)"]
None: ["a"]
Then 'd':

'b':  ["b(b)"]
'e':  ["ac(e)"]
None: ["a", "acd"]
Then 'e':

'b':  ["b(b)"]
None: ["a", "acd", "ace"]
And now I just return how many words aren't waiting for anything anymore.
*/
class Solution {
    public int numMatchingSubseq(String S, String[] words) {
        List<Integer[]>[] waiting = new List[128];
        for(int c = 0; c <= 'z'; c++) {
            waiting[c] = new ArrayList<Integer[]>();
        }
        for(int i = 0; i < words.length; i++) {
            waiting[words[i].charAt(0)].add(new Integer[]{i, 1});
        }
        for(char c : S.toCharArray()) {
            List<Integer[]> advance = new ArrayList<Integer[]>(waiting[c]);
            waiting[c].clear();
            for(Integer[] temp : advance) {
                if(temp[1] < words[temp[0]].length()) {
                    waiting[words[temp[0]].charAt(temp[1]++)].add(temp);
                } else {
                    waiting[0].add(temp);
                }
            }
        }
        return waiting[0].size();
    }
}
