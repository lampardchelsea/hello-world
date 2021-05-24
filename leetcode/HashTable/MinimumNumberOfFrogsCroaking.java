/**
Refer to
https://leetcode.com/problems/minimum-number-of-frogs-croaking/
Given the string croakOfFrogs, which represents a combination of the string "croak" from different frogs, that is, 
multiple frogs can croak at the same time, so multiple “croak” are mixed. Return the minimum number of different 
frogs to finish all the croak in the given string.

A valid "croak" means a frog is printing 5 letters ‘c’, ’r’, ’o’, ’a’, ’k’ sequentially. The frogs have to print 
all five letters to finish a croak. If the given string is not a combination of valid "croak" return -1.

Example 1:
Input: croakOfFrogs = "croakcroak"
Output: 1 
Explanation: One frog yelling "croak" twice.

Example 2:
Input: croakOfFrogs = "crcoakroak"
Output: 2 
Explanation: The minimum number of frogs is two. 
The first frog could yell "crcoakroak".
The second frog could yell later "crcoakroak".

Example 3:
Input: croakOfFrogs = "croakcrook"
Output: -1
Explanation: The given string is an invalid combination of "croak" from different frogs.

Example 4:
Input: croakOfFrogs = "croakcroa"
Output: -1

Constraints:
1 <= croakOfFrogs.length <= 10^5
All characters in the string are: 'c', 'r', 'o', 'a' or 'k'.
*/

// Graph explain
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/HashTable/Document/Minimum_Number_of_Frogs_Croaking.docx

// Solution 1: Speical use of freq map
// Style 1:
// Refer to
// https://leetcode.com/problems/minimum-number-of-frogs-croaking/discuss/586653/C%2B%2B-Python-Java-Lucid-code-with-documened-comments-%2B-Visualization
// 
/**
Whenever a 'c' is encountered, you know that a frog has started to croak. This frog can be a new frog or one of the existing one.
Whenever a 'k' is encountered, you know that one of the frogs have stopped croaking, hence decrement the count of frogs so that 
whenever a new 'c' is encountered, we can reuse the same frog.

This is the basic idea.
For 'c', increment in use frogs by one.
For 'k' decrement the in use count by one.
The maximum value reached by the in use count is the answer.

Please upvote if you liked the solution, it would be encouraging.

C++: Variation 1

class Solution {
    unordered_map<char, int> frequency; // Stores how many times each sound has occured. Sounds are c, r, o, a, k. 
    // 
    //  At any time, for a sequence to be valid, number of 'c' must not be less than 'r'.
    //  Similarly, #'r' shouldn't  less than #'o', and so on.
    bool isStateValid() {
         return (frequency['c'] >= frequency['r']) &&
                (frequency['r'] >= frequency['o']) &&
                (frequency['o'] >= frequency['a']) && 
                (frequency['a'] >= frequency['k']);
    }
    
public:
     // Minimum number of frogs that we need is maximum number of frogs that are croaking
     // simultaneously.
     // Each "croaking" is a sequence of c, r, o, a, and k.
     // Sound is a character in croakSequence.
    int minNumberOfFrogs(string croakSequence) {
        int numCroakingFrogs = 0; // Number of distinct frogs that are croaking at any given time.
        int answer = 0; // Hold the final answer.
        for (char &sound: croakSequence) { // for each sound i.e. character.
            frequency[sound]++; // Note the sound.
            if (!isStateValid()) { // Make sure we are still in valid state.
                return -1;
            }
            if (sound == 'c') { // New "croaking" always begins at 'c'.
                numCroakingFrogs++; // Addional frog for the new "croaking".
            } else if (sound == 'k') { // A "croaking" ends at 'k'.
                numCroakingFrogs--; // Some frog has stopped croaking now.
            }
            answer = max(answer, numCroakingFrogs); // Maximum number of frogs that are croaking 
                                                    // simultaneously over a period.
        }
        return numCroakingFrogs == 0 ? answer : -1; // Make sure all frogs have completed the croaking.
    }
};
*/
class Solution {
    public int minNumberOfFrogs(String croakOfFrogs) {
        int[] freq = new int[26];
        int n = croakOfFrogs.length();
        int cur_frogs = 0;
        int max_frogs = 0;
        for(int i = 0; i < n; i++) {
            char c = croakOfFrogs.charAt(i);
            freq[c - 'a']++;
            if(!isValid(freq)) {
                return - 1; 
            }
            if(c == 'c') {
                cur_frogs++;
            } else if(c == 'k') {
                cur_frogs--;
            }
            max_frogs = Math.max(max_frogs, cur_frogs);
        }
        return cur_frogs == 0 ? max_frogs : -1;
    }
    
    // "croak"
    // At any time, for a sequence to be valid, number of 'c' 
    // must not be less than 'r', since in required sequence,
    // if no wrong order, we will encounter Similarly, number
    // of 'r' shouldn't less than number of 'o', and so on.
    private boolean isValid(int[] freq) {
        return freq['c' - 'a'] >= freq['r' - 'a'] 
            && freq['r' - 'a'] >= freq['o' - 'a']
            && freq['o' - 'a'] >= freq['a' - 'a']
            && freq['a' - 'a'] >= freq['k' - 'a'];
    }
}

// Style 2:
// Refer to
// https://leetcode.com/problems/minimum-number-of-frogs-croaking/discuss/586543/C%2B%2BJava-with-picture-simulation
/**
We can track how many frogs are 'singing' each letter in cnt:

Increase number of frogs singing this letter, and decrease number singing previous letter.
When a frog sings 'c', we increase the number of (simultaneous) frogs.
When a frog sings 'k', we decrease the number of (simultaneous) frogs.
If some frog is singing a letter, but no frog sang the previous letter, we return -1.

Track and return the maximum number of frogs ever signing together.

Catch: if some frog hasn't finished croaking, we need to return -1.

Java
public int minNumberOfFrogs(String croakOfFrogs) {
    int cnt[] = new int[5];
    int frogs = 0, max_frogs = 0;
    for (var i = 0; i < croakOfFrogs.length(); ++i) {
        var ch = croakOfFrogs.charAt(i);
        var n = "croak".indexOf(ch);
        ++cnt[n];
        if (n == 0)
            max_frogs = Math.max(max_frogs, ++frogs);
        else if (--cnt[n - 1] < 0)
            return -1;
        else if (n == 4)
            --frogs;
    }
    return frogs == 0 ? max_frogs : -1;    
}
*/
class Solution {
    public int minNumberOfFrogs(String croakOfFrogs) {
        int cnt[] = new int[5];
        int frogs = 0, max_frogs = 0;
        for (var i = 0; i < croakOfFrogs.length(); ++i) {
            var ch = croakOfFrogs.charAt(i);
            var n = "croak".indexOf(ch);
            ++cnt[n];
            if (n == 0)
                max_frogs = Math.max(max_frogs, ++frogs);
            else if (--cnt[n - 1] < 0)
                return -1;
            else if (n == 4)
                --frogs;
        }
        return frogs == 0 ? max_frogs : -1;    
    }
}
