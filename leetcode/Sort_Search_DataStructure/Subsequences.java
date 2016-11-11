/**
 * http://web.mit.edu/6.005/www/fa15/classes/10-recursion/
 * For example, subsequences("abc") might return "abc,ab,bc,ac,a,b,c,". 
 * Note the trailing comma preceding the empty subsequence, which is also a valid subsequence.
 * 
 * Solution:
 * This problem lends itself to an elegant recursive decomposition. Take the first letter of the word. 
 * We can form one set of subsequences that include that letter, and another set of subsequences that 
 * exclude that letter, and those two sets completely cover the set of possible subsequences.
 * 
 1 public static String subsequences(String word) {
 2     if (word.isEmpty()) {
 3         return ""; // base case
 4     } else {
 5         char firstLetter = word.charAt(0);
 6         String restOfWord = word.substring(1);
 7         
 8         String subsequencesOfRest = subsequences(restOfWord);
 9         
10         String result = "";
11         for (String subsequence : subsequencesOfRest.split(",", -1)) {
12             result += "," + subsequence;
13             result += "," + firstLetter + subsequence;
14         }
15         result = result.substring(1); // remove extra leading comma
16         return result;
17     }
18 }

 * Two examples:
    1. What does subsequences("c") return?
    subsequences("c") first calls subsequences(""), which just returns "".
    We then split this empty string on ",", which returns an array of one element, the empty string.
    
    The for loop then iterates over this array and constructs the two ways that subsequences of "c" can be formed 
    (with or without the letter ‘c’). It appends each new subsequence to result, starting it with a comma. 
    This means we’ll end up with an extra comma at the beginning of result, which we have to remove after the for loop.
    result = "" (line 10)
    result = "," (line 12)
    result = ",,c" (line 13)
    result = ",c" (line 15)
    Finally subsequences("c") returns ",c" representing the empty string and the string "c", the two possible 
    subsequences of the one-character string "c".
    
    2. What does subsequences("gc") return?
    subsequences("gc") first calls subsequences("c"), which returns ",c" as we saw in the previous question.
    We then split this string on ",", which produces an array of two elements, "" and "c".
    
    The for loop then iterates over this array, producing two new subsequences from each element:
    result = "" (line 10)
    result = "," (line 12)
    result = ",,g" (line 13)
    result = ",,g,c" (line 12)
    result = ",,g,c,gc" (line 13)
    result = ",g,c,gc" (line 15)
    This final result is returned from subsequences("gc").
 *    
 * The important tips of design recursive algorithm
 * Structure of Recursive Implementations
 * A recursive implementation always has two parts:
 * base case, which is the simplest, smallest instance of the problem, that can’t be decomposed any further. 
 * Base cases often correspond to emptiness – the empty string, the empty list, the empty set, the empty tree, zero, etc.
 * recursive step, which decomposes a larger instance of the problem into one or more simpler or smaller instances 
 * that can be solved by recursive calls, and then recombines the results of those subproblems to produce the solution 
 * to the original problem.
 * It’s important for the recursive step to transform the problem instance into something smaller, otherwise the 
 * recursion may never end. If every recursive step shrinks the problem, and the base case lies at the bottom, 
 * then the recursion is guaranteed to be finite.
 * A recursive implementation may have more than one base case, or more than one recursive step. For example, 
 * the Fibonacci function has two base cases, n=0 and n=1.
*/
