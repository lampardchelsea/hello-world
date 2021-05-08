/**
Refer to
https://leetcode.com/problems/decoded-string-at-index/
An encoded string S is given.  To find and write the decoded string to a tape, the encoded string is read one 
character at a time and the following steps are taken:

If the character read is a letter, that letter is written onto the tape.
If the character read is a digit (say d), the entire current tape is repeatedly written d-1 more times in total.
Now for some encoded string S, and an index K, find and return the K-th letter (1 indexed) in the decoded string.

Example 1:
Input: S = "leet2code3", K = 10
Output: "o"
Explanation: 
The decoded string is "leetleetcodeleetleetcodeleetleetcode".
The 10th letter in the string is "o".

Example 2:
Input: S = "ha22", K = 5
Output: "h"
Explanation: 
The decoded string is "hahahaha".  The 5th letter is "h".

Example 3:
Input: S = "a2345678999999999999999", K = 1
Output: "a"
Explanation: 
The decoded string is "a" repeated 8301530446056247680 times.  The 1st letter is "a".

Constraints:
2 <= S.length <= 100
S will only contain lowercase letters and digits 2 through 9.
S starts with a letter.
1 <= K <= 10^9
It's guaranteed that K is less than or equal to the length of the decoded string.
The decoded string is guaranteed to have less than 2^63 letters.
*/

// Solution 1: Stack with tricky to push empty string initially for organize the pop out previous string logic first
// Failed test: Memory leak
/**
Input: S = "a2345678999999999999999", K = 1
Output: "a"
Explanation: 
The decoded string is "a" repeated 8301530446056247680 times.  The 1st letter is "a".
*/
class Solution {
    public String decodeAtIndex(String S, int K) {
        Stack<String> stack = new Stack<String>();
        // Push empty string initially for organize the pop out previous string logic first
        stack.push("");
        StringBuilder sb = new StringBuilder();
        int n = S.length();
        // Initialize k = -1 (not 0) to build logic for S.substring(k + 1, i) to get each round append string section
        int k = -1;
        for(int i = 0; i < n; i++) {
            char c = S.charAt(i);
            if(Character.isDigit(c)) {
                // Since we have push empty string "" first, so even first round we can pop out
                String tmp = stack.pop() + S.substring(k + 1, i);
                for(int j = 0; j < c - '0'; j++) {
                    sb.append(tmp);
                }
                stack.push(sb.toString());
                sb.setLength(0);
                k = i;
            }
        }
        return String.valueOf(stack.peek().charAt(K - 1));
    }
}

// Solution 2: Calculate decode string size then scan backwards
// Refer to
// https://leetcode.com/problems/decoded-string-at-index/solution/
/**
Approach 1: Work Backwards
Intuition
If we have a decoded string like appleappleappleappleappleapple and an index like K = 24, the answer is the same if K = 4.

In general, when a decoded string is equal to some word with size length repeated some number of times (such as apple with 
size = 5 repeated 6 times), the answer is the same for the index K as it is for the index K % size.

We can use this insight by working backwards, keeping track of the size of the decoded string. Whenever the decoded string 
would equal some word repeated d times, we can reduce K to K % (word.length).

Algorithm
First, find the length of the decoded string. After, we'll work backwards, keeping track of size: the length of the decoded 
string after parsing symbols S[0], S[1], ..., S[i].

If we see a digit S[i], it means the size of the decoded string after parsing S[0], S[1], ..., S[i-1] will be 
size / Integer(S[i]). Otherwise, it will be size - 1.
*/

// Refer to
// https://leetcode.com/problems/decoded-string-at-index/discuss/156747/JavaC%2B%2BPython-O(N)-Time-O(1)-Space
/**
Explanation
We decode the string and N keeps the length of decoded string, until N >= K.
Then we go back from the decoding position.
If it's S[i] = d is a digit, then N = N / d before repeat and K = K % N is what we want.
If it's S[i] = c is a character, we return c if K == 0 or K == N

Complexity
Time O(N)
Space O(1)

Java
by @blackspinner

    public String decodeAtIndex(String S, int K) {
        int i;
        long N = 0;
        for (i = 0; N < K; i++) {
            N = Character.isDigit(S.charAt(i)) ? N * (S.charAt(i) - '0') : N + 1;
        }
        for (i--; i > 0; i--) {
            if (Character.isDigit(S.charAt(i))) {
                N /= S.charAt(i) - '0';
                K %= N;
            }
            else {
                if (K % N == 0) {
                    break;
                }
                N--;
            }
        }
        return Character.toString(S.charAt(i));
    }
*/
// https://leetcode.com/problems/decoded-string-at-index/discuss/980084/Simple-java-solution0ms100-fastero(n)-time
/**
In repeated string k%size will give exact answer.
1.divide the problme into two parts- first find the size of the string after decoding. to find that we dont need to decode string. 
  just multiply the digit to current size else size++.
2.now in second loop start backwards and if digit found decrease the length by dividing size with digit. 
  and re-evaluate k again according to new length.
3.if current character is not a digit and k=0 or k=size return current character. 
  else decrement size by 1 as only character is reduced from search space.

class Solution {
    public String decodeAtIndex(String str, int k) {
        long size =0 ;
        for(int i=0;i<str.length();i++) {
            char ch=str.charAt(i);
            if(Character.isDigit(ch)) {
                size*=ch-'0';
            } else {
                size++;
            }
        }
        
       for (int i = str.length()-1; i >= 0; --i) {
            char c = str.charAt(i);
            if (Character.isDigit(c)) {
                size /= c - '0';
                k %= size;
            } else {
                if(k==0 || k==size) {
                    return Character.toString(c);
                }
                size--;
            }
                
        }
        return "";
    }
}
*/

// What is Reverse Polish notation ?
// https://en.wikipedia.org/wiki/Reverse_Polish_notation 
/**
In reverse Polish notation, the operators follow their operands; for instance, to add 3 and 4 together, one would write 3 4 + rather 
than 3 + 4. If there are multiple operations, operators are given immediately after their second operands; so the expression written 
3 − 4 + 5 in conventional notation would be written 3 4 − 5 + in reverse Polish notation: 4 is first subtracted from 3, then 5 is 
added to it. An advantage of reverse Polish notation is that it removes the need for parentheses that are required by infix notation. 
While 3 − 4 × 5 can also be written 3 − (4 × 5), that means something quite different from (3 − 4) × 5. In reverse Polish notation, 
the former could be written 3 4 5 × −, which unambiguously means 3 (4 5 ×) − which reduces to 3 20 − (which can further be reduced 
to -17); the latter could be written 3 4 − 5 × (or 5 3 4 − ×, if keeping similar formatting), which unambiguously means (3 4 −) 5 ×.
*/
class Solution {
    public String decodeAtIndex(String S, int K) {
        long size = 0;
        for(int i = 0; i < S.length(); i++) {
            if(Character.isDigit(S.charAt(i))) {
                size *= S.charAt(i) - '0';
            } else {
                size++;
            }
        }
        for(int i = S.length() - 1; i >= 0; i--) {
            if(Character.isDigit(S.charAt(i))) {
                size /= S.charAt(i) - '0';
                K %= size;
            } else {
                // Test case for K == 0 --> leet2code3 and K = 4, expected 't'
                // Test case for K == size --> leet2code3 and K = 3, expected 'e'
                if(K == 0 || K == size) {
                    return String.valueOf(S.charAt(i));
                }
                size--;
            }
        }
        return "";
    }
}
