/**
Refer to
https://codedestine.com/single-row-keyboard-string-problem/
Problem Statement
There is a special keyboard with all keys in a single row.

You have given a string keyboard of length 26 indicating the layout of the keyboard (indexed from 0 to 25), 
initially your finger is at index 0. To type a character, you have to move your finger to the index of the 
desired character. The time taken to move your finger from index i to index j is |i – j|.

You want to type a string word. Write a program to calculate how much time it takes to type it with one finger.
pqrstuvwxyzabcdefghijklmno
----------------->18
               3<---
               ------>7
                      -->3
Total = 18 + 3 + 7 + 3 = 31

Example:
Input :- Keyboard = "abcdefghijklmnopqrstuvwxyz", Word = "cba"
Output :- 4
Input :- Keyboard = "pqrstuvwxyzabcdefghijklmno", Word = "hello"
Output :- 31
*/

// Solution 1: Record previous character position
// Refer to
// https://codedestine.com/single-row-keyboard-string-problem/
/**
Solution
This problem can be solved in following steps :-
1.Create the character array from both the input String keyboard and word.
2.Create an integer array (count array) of size 26 (Total number of characters in the English language), 
  This array will be used to store the index of each character in the keyboard string.
3.Traverse the character array keyboard from start (index 0) to end (n-1, where n is the length of an array) 
  and convert each character into an index of count array by subtracting the character from ‘a’ and converting 
  it into ASCII code. Assign the keyboard index to the calculated index.
4.Traverse the character array word from start (index 0) to end (n-1, where n is the length of an array) and 
  for each character calculate the difference between new position of the character and the position of the finger. 
  Update the position of the finger as new position of the character.
5.Add all the difference calculated in the last step and return it as an result.

class Solution {
    public int calculateTime(String keyboard, String word) {
        char[] chars = keyboard.toCharArray();      
        int[] countArray = new int[26];
        for(int i=0; i<countArray.length; i++) {
            countArray[chars[i] - 'a'] = i;
        }
        int result = 0, position = 0;       
        char[] charsWord = word.toCharArray();
        for(int i=0; i<charsWord.length; i++) {
            result = result +  Math.abs(countArray[charsWord[i] - 'a'] - position);
            position = countArray[charsWord[i] - 'a'];
        }
        return result;
    }
}
*/
class Solution {
    public int calculateTime(String keyboard, String word) {
        int[] index = new int[26];
        for(int i = 0; i < keyboard.length(); i++) {
            index[keyboard.charAt(i) - 'a'] = i;
        }
        int result = 0;
        int pre_pos = 0;
        for(char c : word.toCharArray()) {
            result += Math.abs(index[c - 'a'] - pre_pos);
            pre_pos = index[c - 'a'];
        }
        return result;
    }
}
